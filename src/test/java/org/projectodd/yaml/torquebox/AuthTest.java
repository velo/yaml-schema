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

import org.junit.Test;

public class AuthTest extends AbstractBaseTorqueBoxTest {

    @Test
    public void validFull() throws Exception {
        schema.validate( loadResource( "auth/valid-full-doc.yml" ) );
    }

    @Test
    public void invalidArbitraryKey() throws Exception {
        try {
            schema.validate( loadResource( "auth/invalid-arbitrarykey-doc.yml" ) );
        } catch (Exception e) {
            assertEquals( "Unrecognized field: springfield", e.getCause().getMessage() );
        }
    }

    @Test
    public void invalidBadDomain() throws Exception {
        try {
            schema.validate( loadResource( "auth/invalid-baddomain-doc.yml" ) );
        } catch (Exception e) {
            assertEquals( "String field domain only accepts scalar values.", e.getCause().getMessage() );
        }
    }

    @Test
    public void invalidBadValueType() throws Exception {
        try {
            schema.validate( loadResource( "auth/invalid-badvaluetype-doc.yml" ) );
        } catch (Exception e) {
            assertEquals( "Schema for field auth does not accept [yellow, blue, red] of type class " +
                    "java.util.ArrayList as input for type map",
                    e.getCause().getMessage() );
        }
    }

}
