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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.projectodd.yaml.SchemaException;
import org.projectodd.yaml.schema.metadata.DependencyIndexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComplexType extends AbstractBaseType {

    private List<AbstractBaseType> types = new ArrayList<AbstractBaseType>( 10 );

    @Override
    protected boolean acceptsConfiguration(Object yamlData) throws SchemaException {
        return yamlData instanceof Map;
    }

    @SuppressWarnings("unchecked")
    @Override
    ComplexType build(Object yamlData) throws SchemaException {
        log.debug( "Parsing type options for complex type with data: {}", yamlData );
        Map<String, Object> yamlMap = (Map<String, Object>) yamlData;
        Object type = yamlMap.get( "type" );
        if (type instanceof Map) {
            return buildTypeFromMap( (Map<String, Object>) type );
        }
        else if (type instanceof List) {
            return buildTypeFromList( (List<?>) type );
        }
        else {
            throw new SchemaException( "Complex type can only take a list or map as input." );
        }
    }

    @Override
    public void validateType(DependencyIndexer indexer, Object value) throws SchemaException {
        TypeUtils.ensureOneTypeValid( indexer, getName(), types, value );
    }

    @SuppressWarnings("unchecked")
    private ComplexType buildTypeFromList(List<?> typeList) throws SchemaException {
        TypeFactory tf = TypeFactory.instance();
        for (Object typeId : typeList) {
            log.debug( "Adding type from list: {}.", typeId );
            AbstractBaseType type = null;
            if (typeId instanceof String) {
                type = tf.buildType( getName(), (String) typeId, null );
            }
            else if (typeId instanceof Map) {
                Map<String, Object> typeData = (Map<String, Object>) typeId;
                type = tf.buildType( getName(), tf.getSchemaTypeId( ListType.class ), typeData );
            }
            type.setRequired( this.isRequired() );
            types.add( type );
        }
        return this;
    }

    private ComplexType buildTypeFromMap(Map<String, Object> typeMap) throws SchemaException {
        for (String key : typeMap.keySet()) {
            log.debug( "Adding type from map {}.", key );
            AbstractBaseType type = TypeFactory.instance().buildType( getName(), key, typeMap.get( key ) );
            type.setRequired( this.isRequired() );
            types.add( type );
        }
        return this;
    }

    private static final Logger log = LoggerFactory.getLogger( ComplexType.class );

}
