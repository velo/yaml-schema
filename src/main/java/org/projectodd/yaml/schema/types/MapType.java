/**
 * Copyright (C) 2017 Marvin Herman Froeder (marvin@marvinformatics.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.projectodd.yaml.schema.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.projectodd.yaml.SchemaException;
import org.projectodd.yaml.schema.metadata.DependencyIndexer;

public class MapType extends AbstractCollectionType {

    private boolean allowingArbitraryKeys = false;

    private Map<String, AbstractBaseType> children = new HashMap<String, AbstractBaseType>( 10 );

    @Override
    protected boolean acceptsConfiguration(Object yamlData) throws SchemaException {
        return yamlData instanceof Map;
    }

    @Override
    protected boolean acceptsValue(Object yamlData) {
        return yamlData instanceof Map;
    }

    @SuppressWarnings("unchecked")
    @Override
    MapType build(Object yamlData) throws SchemaException {
        Map<String, Object> data = (Map<String, Object>) yamlData;
        if (data.containsKey( "arbitrary" )) {
            allowingArbitraryKeys = (Boolean) data.get( "arbitrary" );
            data.remove( "arbitrary" );
        }
        if (data.containsKey( "value-types" )) {
            if (!this.allowingArbitraryKeys) {
                throw new SchemaException( "value-types can only be specified if arbitrary keys are allowed." );
            }
            buildValueTypes( data.get( "value-types" ) );
            data.remove( "value-types" );
        }
        for (String key : data.keySet()) {
            Object value = data.get( key );
            AbstractBaseType type = TypeFactory.instance().buildType( (String) key, value );
            children.put( type.getName(), type );
        }
        return this;
    }

    public Map<String, AbstractBaseType> getChildren() {
        return children;
    }

    public Boolean isAllowingArbitraryKeys() {
        return this.allowingArbitraryKeys;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validateType(DependencyIndexer indexer, Object value) throws SchemaException {
        Map<String, Object> yamlData = (Map<String, Object>) value;
        List<AbstractBaseType> valueTypes = this.getValueTypes();

        for (String child : children.keySet()) {
            if (children.get( child ).isRequired() && !yamlData.containsKey( child )) {
                throw new SchemaException( "Value " + value + " for field " + getName() + " does not contain required field " + child );
            }
        }

        if (yamlData != null && !yamlData.isEmpty()) {
            for (String key : yamlData.keySet()) {
                AbstractBaseType type = children.get( key );
                if (type == null) {
                    if (!allowingArbitraryKeys) {
                        throw new SchemaException( "Unrecognized field: " + key );
                    }
                    else {
                        log.debugf( "Map for field %s allows arbitrary keys.", getName() );
                        if (valueTypes != null) {
                            validateValueTypes( indexer, key, yamlData );
                        }
                    }
                }
                else
                    type.validate( indexer, yamlData.get( key ) );
            }
        }
    }

    private void validateValueTypes(DependencyIndexer indexer, String key, Map<String, Object> yamlData) throws SchemaException {
        boolean foundValid = false;
        SchemaException cause = null;
        List<AbstractBaseType> valueTypes = this.getValueTypes();
        for (int i = 0; i < valueTypes.size() && !foundValid; i++) {
            AbstractBaseType valueType = valueTypes.get( i );
            try {
                valueType.validate( indexer, yamlData.get( key ) );
                foundValid = true;
            } catch (SchemaException e) {
                cause = e;
            }
        }
        if (!foundValid) {
            throw new SchemaException( "No valid value found for field " + key, cause );
        }
    }

    private static final Logger log = Logger.getLogger( MapType.class );

}
