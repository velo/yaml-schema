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

public class ListType extends AbstractCollectionType {

    @Override
    protected boolean acceptsConfiguration(Object yamlData) throws SchemaException {
        return yamlData instanceof Map;
    }

    @Override
    protected boolean acceptsValue(Object yamlData) {
        return yamlData instanceof List;
    }

    @SuppressWarnings("unchecked")
    @Override
    AbstractBaseType build(Object yamlData) throws SchemaException {
        Map<String, Object> data = (Map<String, Object>) yamlData;
        if (data.containsKey( "value-types" )) {
            buildValueTypes( data.get( "value-types" ) );
            data.remove( "value-types" );
        }
        return this;
    }

    @Override
    public void validateType(DependencyIndexer indexer, Object value) throws SchemaException {
        List<AbstractBaseType> valueTypes = this.getValueTypes();
        if (valueTypes != null) {
            // lists only validate if value-types are specified.
            List<?> yamlDataList = (List<?>) value;
            for (Object yamlData : yamlDataList) {
                TypeUtils.ensureOneTypeValid( indexer, getName(), this.getValueTypes(), yamlData );
            }
        }
    }

}
