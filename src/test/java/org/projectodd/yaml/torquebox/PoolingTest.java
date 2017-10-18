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
import org.projectodd.yaml.SchemaException;

public class PoolingTest extends AbstractBaseTorqueBoxTest {

    @Test
    public void testSimple() throws SchemaException {
        schema.validate( loadResource( "pooling/valid-simple-doc.yml" ) );
    }

    @Test
    public void testInvalidBadEnumValue() throws Exception {
        try {
            schema.validate( loadResource( "pooling/invalid-badenum-simple-doc.yml" ) );
        } catch (Exception e) {
            assertEquals( "Schema for field pooling does not accept egot of type class java.lang.String " +
                    "as input for type map",
                    e.getCause().getMessage() );
        }
    }

    @Test
    public void testInvalidBadCategory() throws Exception {
        try {
            schema.validate( loadResource( "pooling/invalid-badcategory-simple-doc.yml" ) );
        } catch (Exception e) {
            assertEquals( "Unrecognized field: krustyland", e.getMessage() );
        }
    }

    @Test
    public void testInvalidBounds() throws Exception {
        try {
            schema.validate( loadResource( "pooling/invalid-bounds-simple-doc.yml" ) );
        } catch (Exception e) {
            assertEquals( "Schema for field max does not accept abc of type class java.lang.String as " +
                    "input for type integer",
                    e.getCause().getMessage() );
        }
    }

}
