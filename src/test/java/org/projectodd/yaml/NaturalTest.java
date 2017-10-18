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
import org.projectodd.yaml.schema.types.NaturalType;
import org.projectodd.yaml.schema.types.MapType;

public class NaturalTest extends AbstractBaseTest {

    @Test
    public void testBasic() throws SchemaException {
        Schema schema = new Schema( loadResource( "basic-schema.yml" ) );
        MapType root = (MapType) schema.getRoot();
        Map<String, AbstractBaseType> children = root.getChildren();
        assertEquals( 3, children.size() );
        NaturalType foo = (NaturalType) children.get( "foo" );
        assertTrue( foo.isRequired() );
        assertEquals( "foo", foo.getName() );
        NaturalType bar = (NaturalType) children.get( "bar" );
        assertTrue( bar.isRequired() );
        assertEquals( "bar", bar.getName() );
        NaturalType baz = (NaturalType) children.get( "baz" );
        assertFalse( baz.isRequired() );
        assertEquals( "baz", baz.getName() );
        schema.validate( loadResource( "valid-doc.yml" ) );
    }

    @Test
    public void testComplex() throws SchemaException {
        Schema schema = new Schema( loadResource( "complex-schema.yml" ) );
        MapType root = (MapType) schema.getRoot();
        Map<String, AbstractBaseType> children = root.getChildren();
        assertEquals( 3, children.size() );
        NaturalType foo = (NaturalType) children.get( "foo" );
        assertTrue( foo.isRequired() );
        assertEquals( "foo", foo.getName() );
        NaturalType bar = (NaturalType) children.get( "bar" );
        assertTrue( bar.isRequired() );
        assertEquals( "bar", bar.getName() );
        NaturalType baz = (NaturalType) children.get( "baz" );
        assertFalse( baz.isRequired() );
        assertEquals( "baz", baz.getName() );
        schema.validate( loadResource( "valid-doc.yml" ) );
    }

    @Test
    public void testInvalid() throws Exception {
        try {
            Schema schema = new Schema( loadResource( "complex-schema.yml" ) );
            schema.validate( loadResource( "invalid-doc.yml" ) );
            fail( "Invalid doc should have failed." );
        } catch (SchemaException e) {
            assertEquals( "Natural field foo must have a value >= 1.", e.getMessage() );
        }
    }

    @Test
    public void testInvalidNullValue() throws Exception {
        try {
            Schema schema = new Schema( loadResource( "complex-schema.yml" ) );
            schema.validate( loadResource( "invalid-nullvalue-doc.yml" ) );
            fail( "Invalid doc should have failed." );
        } catch (SchemaException e) {
            assertEquals( "Schema for field baz does not accept null as input for type natural",
                    e.getMessage() );
        }
    }

    @Override
    public String getType() {
        return "natural";
    }

}
