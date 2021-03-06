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

import java.util.Map;

import org.projectodd.yaml.SchemaException;
import org.projectodd.yaml.schema.metadata.DependencyIndexer;

public class IntegerType extends AbstractBaseType {

    @Override
    protected boolean acceptsConfiguration(Object yamlData) throws SchemaException {
        return yamlData instanceof Map || yamlData instanceof String;
    }

    @Override
    protected boolean acceptsValue(Object yamlData) {
        return yamlData instanceof Integer;
    }

    @Override
    AbstractBaseType build(Object yamlData) throws SchemaException {
        return this;
    }

    @Override
    public void validateType(DependencyIndexer indexer, Object value) throws SchemaException {
        if (value == null) {
            throw new SchemaException( "Integer field " + getName() + " cannot be null." );
        }
    }

}
