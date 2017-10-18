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
package org.projectodd.yaml.torquebox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.projectodd.yaml.schema.types.AbstractBaseType;
import org.projectodd.yaml.schema.types.MapType;
import org.projectodd.yaml.schema.types.StringType;

// TODO: Finish this after all other tests pass.
public class SchemaTest extends AbstractBaseTorqueBoxTest {

    private MapType root;

    @Test
    public void validSchema() throws Exception {
        root = (MapType) schema.getRoot();
        this.validateApplication();

    }

    private void validateApplication() {
        MapType application = getCategory( "application" );
        Map<String, AbstractBaseType> kids = application.getChildren();
        assertEquals( 4, application.getChildren().size() );
        validateString( kids.get( "root" ), "root" );
        validateString( kids.get( "env" ), "env" );
        validateString( kids.get( "RAILS_ROOT" ), "RAILS_ROOT" );
        validateString( kids.get( "RAILS_ENV" ), "RAILS_ENV" );
    }

    private MapType getCategory(String category) {
        AbstractBaseType t = root.getChildren().get( category );
        assertNotNull( t );
        assertTrue( t instanceof MapType );
        return (MapType) t;

    }

    private void validateString(AbstractBaseType type, String name) {
        validateString( type, name, false );
    }

    private void validateString(AbstractBaseType type, String name, boolean required) {
        assertTrue( type instanceof StringType );
        assertEquals( required, type.isRequired() );
        assertEquals( name, type.getName() );
    }
}
