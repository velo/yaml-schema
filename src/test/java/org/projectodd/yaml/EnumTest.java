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
package org.projectodd.yaml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Test;
import org.projectodd.yaml.schema.types.AbstractBaseType;
import org.projectodd.yaml.schema.types.EnumType;
import org.projectodd.yaml.schema.types.MapType;

public class EnumTest extends AbstractBaseTest {

    @Test
    public void testBasic() throws SchemaException {
        Schema schema = new Schema( loadResource( "basic-schema.yml" ) );
        MapType root = (MapType) schema.getRoot();
        Map<String, AbstractBaseType> children = root.getChildren();
        assertEquals( 2, children.size() );
        EnumType foo = (EnumType) children.get( "foo" );
        assertTrue( foo.isRequired() );
        assertEquals( "foo", foo.getName() );
        EnumType bar = (EnumType) children.get( "bar" );
        assertFalse( bar.isRequired() );
        assertEquals( "foo", foo.getName() );
        schema.validate( loadResource( "valid-doc.yml" ) );
    }

    @Test
    public void testValidStringValue() throws SchemaException {
        Schema schema = new Schema( loadResource( "basic-schema.yml" ) );
        schema.validate( loadResource( "valid-stringvalue-doc.yml" ) );
    }

    @Test
    public void testBadSchemaValues() {
        try {
            Schema schema = new Schema( loadResource( "invalid-values-schema.yml" ) );
            MapType root = (MapType) schema.getRoot();
            Map<String, AbstractBaseType> children = root.getChildren();
            assertEquals( 1, children.size() );
            EnumType foo = (EnumType) children.get( "foo" );
            assertTrue( foo.isRequired() );
            assertEquals( "foo", foo.getName() );
            schema.validate( loadResource( "valid-doc.yml" ) );
            fail( "Error message about schema expected." );
        } catch (SchemaException e) {
            assertEquals( e.getCause().getMessage(), "The schema for an enum type must supply a values field with a list of values." );
        }
    }

    @Test
    public void testBadDocValue() {
        try {
            Schema schema = new Schema( loadResource( "basic-schema.yml" ) );
            schema.validate( loadResource( "invalid-doc.yml" ) );
            fail( "Error message about schema expected." );
        } catch (SchemaException e) {
            assertEquals( e.getMessage(), "1.2 is not a valid value for the enumeration on field foo" );
        }
    }

    @Override
    public String getType() {
        return "enum";
    }

}
