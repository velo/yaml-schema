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

import static org.projectodd.yaml.schema.types.TypeUtils.asTypedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.projectodd.yaml.SchemaException;
import org.projectodd.yaml.schema.metadata.Dependency;
import org.projectodd.yaml.schema.metadata.DependencyIndexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBaseType {

    private static Logger log = LoggerFactory.getLogger( AbstractBaseType.class );

    private static final String OPTIONAL_PREFIX = "-";

    private static final String REQUIRED_PREFIX = "+";

    private String name;

    private String description = "";

    private boolean required = true;

    private final List<Dependency> dependencies = new ArrayList<Dependency>( 10 );

    abstract AbstractBaseType build(Object yamlData) throws SchemaException;

    public String getName() {
        return name;
    }

    protected boolean acceptsConfiguration(Object yamlData) throws SchemaException {
        return true;
    }

    protected boolean acceptsValue(Object yamlData) throws SchemaException {
        return true;
    }

    AbstractBaseType initialize(String name, Object yamlData) throws SchemaException {
        String fieldName = name;
        if (fieldName.startsWith( REQUIRED_PREFIX )) {
            fieldName = fieldName.substring( 1 );
            this.required = true;
        }
        else if (fieldName.startsWith( OPTIONAL_PREFIX )) {
            fieldName = fieldName.substring( 1 );
            this.required = false;
        }
        this.name = fieldName;
        if (!acceptsConfiguration( yamlData )) {
            throw new SchemaException( "Schema for field " + name + " does not accept "
                    + yamlData
                    + (yamlData != null ? " of type " + yamlData.getClass() : "")
                    + " as configuration input for type "
                    + TypeFactory.instance().getSchemaTypeId( this.getClass() ) );
        }

        if (yamlData instanceof Map) {
            final Map<String, Object> yamlMap = asTypedMap( yamlData );
            if (yamlMap.containsKey( "required" )) {
                required = Boolean.valueOf( yamlMap.get( "required" ).toString() );
                yamlMap.remove( "required" );
            }
            if (yamlMap.containsKey( "description" )) {
                description = (String) yamlMap.get( "description" );
                yamlMap.remove( "description" );
            }
            initializeDependencies( yamlMap );
        }
        log.debug( "initialized {} field {}.", (required ? "required" : "optional"), this.name );
        return this;
    }

    public void initializeDependencies(Map<String, Object> yamlMap) throws SchemaException {
        final Object deps = yamlMap.get( "dependencies" );
        if (deps != null) {
            if (deps instanceof Map) {
                final Map<String, Object> mapDeps = asTypedMap( deps );
                for (final String dep : mapDeps.keySet()) {
                    dependencies.add( new Dependency().initialize( getName(), dep ) );
                }
            }
            else if (deps instanceof List) {
                for (final Object dep : (List<?>) deps) {
                    dependencies.add( new Dependency().initialize( getName(), dep ) );
                }
            }
            dependencies.add( new Dependency().initialize( getName(), deps ) );
            yamlMap.remove( "dependencies" );
        }
        else {
            log.trace( "No dependencies for field {}.", getName() );
        }
    }

    public String getDescription() {
        return description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void validate(DependencyIndexer index, Object yamlData) throws SchemaException {
        log.debug( "Validating type {} using value {}.", this.getClass(), yamlData );
        if (yamlData == null && required) {
            throw new SchemaException( "Field " + name + " was required but is not present." );
        }

        if (!acceptsValue( yamlData )) {
            throw new SchemaException( "Schema for field " + name + " does not accept "
                    + yamlData
                    + (yamlData != null ? " of type " + yamlData.getClass() : "")
                    + " as input for type "
                    + TypeFactory.instance().getSchemaTypeId( this.getClass() ) );
        }
        validateDependencies( index );
        validateType( index, yamlData );
    }

    private void validateDependencies(DependencyIndexer indexer) throws SchemaException {
        if (indexer.isVerifyingDependencies()) {
            for (final Dependency dep : dependencies) {
                dep.validate( indexer );
            }
        }
    }

    public abstract void validateType(DependencyIndexer indexer, Object value) throws SchemaException;

}
