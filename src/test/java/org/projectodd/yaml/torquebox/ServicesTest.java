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
import static org.junit.Assert.fail;

import org.junit.Test;
import org.projectodd.yaml.SchemaException;

public class ServicesTest extends AbstractBaseTorqueBoxTest {

    @Test
    public void testInvalidBadSingleton() throws Exception {
        try {
            schema.validate( loadResource( "services/invalid-badsingleton-doc.yml" ) );
            fail( "Should have failed." );
        } catch (SchemaException e) {
            assertEquals( "Schema for field singleton does not accept mrplow of " +
                    "type class java.lang.String as input for type bool",
                    e.getCause().getMessage() );
        }
    }

    @Test
    public void testInvalidBadConfig() throws Exception {
        try {
            schema.validate( loadResource( "services/invalid-badconfig-doc.yml" ) );
            fail( "Should have failed." );
        } catch (SchemaException e) {
            assertEquals( "Schema for field config does not accept pants of " +
                    "type class java.lang.String as input for type map",
                    e.getCause().getMessage() );
        }
    }

    @Test
    public void testInvalidBadService() throws Exception {
        try {
            schema.validate( loadResource( "services/invalid-badservice-doc.yml" ) );
            fail( "Should have failed." );
        } catch (SchemaException e) {
            assertEquals( "String field service only accepts scalar values.", e.getCause().getMessage() );
        }
    }

    @Test
    public void testValidFull() throws Exception {
        schema.validate( loadResource( "services/valid-full-doc.yml" ) );
    }

}
