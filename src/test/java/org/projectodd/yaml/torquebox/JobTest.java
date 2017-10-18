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

public class JobTest extends AbstractBaseTorqueBoxTest {

    @Test
    public void testSimpleValid() throws SchemaException {
        schema.validate( loadResource( "jobs/valid-simple-doc.yml" ) );
    }

    @Test
    public void testFullValid() throws SchemaException {
        schema.validate( loadResource( "jobs/valid-full-doc.yml" ) );
    }

    @Test
    public void testMixedKeysConfig() throws SchemaException {
        schema.validate( loadResource( "jobs/valid-mixedkeysconfig-doc.yml" ) );
    }

    @Test
    public void invalidBadType() throws SchemaException {
        try {
            schema.validate( loadResource( "jobs/invalid-badtype-doc.yml" ) );
            fail( "Should have failed." );
        } catch (SchemaException e) {
            assertEquals( "Schema for field jobs does not accept Jobs::MyJobClass of " +
                    "type class java.lang.String as input for type map",
                    e.getCause().getMessage() );
        }
    }

    @Test
    public void invalidArbitraryKey() throws SchemaException {
        try {
            schema.validate( loadResource( "jobs/invalid-arbitrarykey-doc.yml" ) );
            fail( "Should have failed." );
        } catch (SchemaException e) {
            assertEquals( "Unrecognized field: city", e.getCause().getMessage() );
        }
    }

    @Test
    public void invalidMissingFields() throws SchemaException {
        try {
            schema.validate( loadResource( "jobs/invalid-missingfields-doc.yml" ) );
            fail( "Should have failed." );
        } catch (SchemaException e) {
            assertEquals( "Value {job=Jobs::MyJobClass} for field jobs does not contain " +
                    "required field cron", e.getCause().getMessage() );
        }
    }

}
