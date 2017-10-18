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

public class RubyTest extends AbstractBaseTorqueBoxTest {

    @Test
    public void testValid() throws Exception {
        schema.validate( loadResource( "ruby/valid-full-doc.yml" ) );
    }

    @Test
    public void testInvalidCompileMode() throws Exception {
        try {
            schema.validate( loadResource( "ruby/invalid-badcompilemode-doc.yml" ) );
            fail( "Should have failed." );
        } catch (Exception e) {
            assertEquals( "goober is not a valid value for the enumeration on field compile_mode", e.getMessage() );
        }
    }

    @Test
    public void testInvalidBadVersion() throws Exception {
        try {
            schema.validate( loadResource( "ruby/invalid-badversion-doc.yml" ) );
            fail( "Should have failed." );
        } catch (Exception e) {
            assertEquals( "2.1 is not a valid value for the enumeration on field version", e.getMessage() );
        }
    }

    @Test
    public void testInvalidBadDebug() throws Exception {
        try {
            schema.validate( loadResource( "ruby/invalid-baddebug-doc.yml" ) );
            fail( "Should have failed." );
        } catch (Exception e) {
            assertEquals( "Schema for field debug does not accept cmontgomery-burns of type class " +
                    "java.lang.String as input for type bool",
                    e.getMessage() );
        }
    }

    @Test
    public void testInvalidBadInteractive() throws Exception {
        try {
            schema.validate( loadResource( "ruby/invalid-badinteractive-doc.yml" ) );
            fail( "Should have failed." );
        } catch (Exception e) {
            assertEquals( "Schema for field interactive does not accept ham of type class " +
                    "java.lang.String as input for type bool",
                    e.getMessage() );
        }
    }

    @Test
    public void testInvalidBadProfileApi() throws Exception {
        try {
            schema.validate( loadResource( "ruby/invalid-badprofileapi-doc.yml" ) );
            fail( "Should have failed." );
        } catch (Exception e) {
            assertEquals( "Schema for field profile_api does not accept cheese of type class " +
                    "java.lang.String as input for type bool",
                    e.getMessage() );
        }
    }

}
