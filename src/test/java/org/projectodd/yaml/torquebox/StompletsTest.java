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

public class StompletsTest extends AbstractBaseTorqueBoxTest {

    @Test
    public void validFull() throws Exception {
        schema.validate( loadResource( "stomplets/valid-full-doc.yml" ) );
    }

    @Test
    public void invalidBadKey() throws Exception {
        try {
            schema.validate( loadResource( "stomplets/invalid-badkey-doc.yml" ) );
            fail( "Should have failed." );
        } catch (SchemaException e) {
            assertEquals( "Unrecognized field: hummus", e.getCause().getMessage() );
        }
    }

    @Test
    public void invalidBadHost() throws Exception {
        try {
            schema.validate( loadResource( "stomplets/invalid-badhost-doc.yml" ) );
            fail( "Should have failed." );
        } catch (SchemaException e) {
            assertEquals( "String field host only accepts scalar values.", e.getMessage() );
        }
    }

    @Test
    public void invalidBadRoute() throws Exception {
        try {
            schema.validate( loadResource( "stomplets/invalid-badroute-doc.yml" ) );
            fail( "Should have failed." );
        } catch (SchemaException e) {
            assertEquals( "String field route only accepts scalar values.", e.getCause().getMessage() );
        }
    }

    @Test
    public void invalidBadClass() throws Exception {
        try {
            schema.validate( loadResource( "stomplets/invalid-badclass-doc.yml" ) );
            fail( "Should have failed." );
        } catch (SchemaException e) {
            assertEquals( "String field class only accepts scalar values.", e.getCause().getMessage() );
        }
    }

}
