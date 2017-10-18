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

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.projectodd.yaml.SchemaException;
import org.projectodd.yaml.schema.metadata.DependencyIndexer;

public class StringType extends AbstractBaseType {

    private Pattern regex;

    @SuppressWarnings("unchecked")
    @Override
    StringType build(Object yamlData) throws SchemaException {
        if (yamlData instanceof Map) {
            Map<String, Object> yamlMap = (Map<String, Object>) yamlData;
            String pattern = (String) yamlMap.get( "regex" );
            if (pattern != null) {
                try {
                    regex = Pattern.compile( pattern );
                } catch (PatternSyntaxException e) {
                    throw new SchemaException( "Invalid regex pattern: " + regex, e );
                }
            }
        }
        return this;
    }

    public String getPattern() {
        return regex == null ? null : regex.pattern();
    }

    @Override
    public void validateType(DependencyIndexer indexer, Object value) throws SchemaException {
        if (value instanceof Collection || value instanceof Map) {
            throw new SchemaException( "String field " + getName() + " only accepts scalar values." );
        }
        if (regex != null) {
            if (!(value instanceof String)) {
                value = value.toString();
            }
            if (!regex.matcher( (String) value ).matches()) {
                throw new SchemaException( "Value " + value + " for field " + getName() +
                        " does not match regular expression " + regex.pattern() );
            }
        }
    }
}
