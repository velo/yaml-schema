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

import java.util.List;
import java.util.Map;

import org.projectodd.yaml.SchemaException;
import org.projectodd.yaml.schema.metadata.DependencyIndexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumType extends AbstractBaseType {

    private List<Object> values;

    @Override
    protected boolean acceptsConfiguration(Object yamlData) throws SchemaException {
        return yamlData instanceof Map;
    }

    @SuppressWarnings("unchecked")
    @Override
    AbstractBaseType build(Object yamlData) throws SchemaException {
        List<Object> values = null;
        if (yamlData instanceof Map) {
            Map<String, Object> yamlMap = (Map<String, Object>) yamlData;
            if (!(yamlMap.get( "values" ) instanceof List)) {
                throw new SchemaException( "The schema for an enum type must supply a values field with a list of values." );
            }
            values = (List<Object>) yamlMap.get( "values" );
        }
        else {
            values = (List<Object>) yamlData;
        }
        if (values == null) {
            throw new SchemaException( "Cannot build enum type without values" );
        }
        this.values = values;
        return this;
    }

    @Override
    public void validateType(DependencyIndexer indexer, Object value) throws SchemaException {
        log.debug( "Validating value {} against enum values {}.", value, values );
        boolean found = false;
        for (int i = 0; i < values.size() && !found; i++) {
            Object enumValue = values.get( i );
            if (enumValue.equals( value ) ||
                    (value != null && enumValue.toString().equals( value.toString() ))) {
                found = true;
            }
        }
        if (!found) {
            throw new SchemaException( value + " is not a valid value for the enumeration on field " + getName() );
        }

    }

    private static Logger log = LoggerFactory.getLogger( EnumType.class );

}
