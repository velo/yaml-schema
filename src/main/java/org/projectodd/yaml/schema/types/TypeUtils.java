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

public class TypeUtils {

    @SuppressWarnings("unchecked")
    public static Map<String, Object> asTypedMap(Object map) throws SchemaException {
        if (!(map instanceof Map)) {
            throw new SchemaException( "Cannot convert object of type " + map.getClass() + " to map." );
        }
        return (Map<String, Object>) map;
    }

    public static void ensureOneTypeValid(DependencyIndexer indexer, String fieldName, List<AbstractBaseType> types, Object yamlData) throws SchemaException {
        boolean foundValid = false;
        for (int i = 0; foundValid == false && i < types.size(); i++) {
            try {
                types.get( i ).validate( indexer, yamlData );
                foundValid = true;
            } catch (final SchemaException e) {
                log.trace( "Type {} was not valid; trying others.", types.get( i ) );
            }
        }
        if (!foundValid) {
            log.error( "Value {} failed validation for all possible types.", yamlData );
            throw new SchemaException( yamlData + " is not a valid value for field " + fieldName );
        }
    }

    private static Logger log = LoggerFactory.getLogger( TypeUtils.class );

}
