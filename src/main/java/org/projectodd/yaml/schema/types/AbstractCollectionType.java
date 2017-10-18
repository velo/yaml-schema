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

import org.jboss.logging.Logger;
import org.projectodd.yaml.SchemaException;

public abstract class AbstractCollectionType extends AbstractBaseType {

    private List<AbstractBaseType> valueTypes;

    @SuppressWarnings("unchecked")
    protected void buildValueTypes(Object yamlData) throws SchemaException {
        valueTypes = new ArrayList<AbstractBaseType>( 10 );
        if (yamlData instanceof String) {
            valueTypes.add( TypeFactory.instance().buildType( getName(), (String) yamlData, null ) );
        }
        else if (!(yamlData instanceof Map)) {
            throw new SchemaException( "value-types must be specified in map format." );
        }
        else {
            Map<String, Object> yamlMap = (Map<String, Object>) yamlData;
            for (String typeId : yamlMap.keySet()) {
                log.debugf( "Adding value type: %s for field %s.", typeId, getName() );
                valueTypes.add( TypeFactory.instance().buildType( getName(), typeId, yamlMap.get( typeId ) ) );
            }
        }
    }

    public List<AbstractBaseType> getValueTypes() {
        return valueTypes;
    }

    private static final Logger log = Logger.getLogger( AbstractCollectionType.class );

}
