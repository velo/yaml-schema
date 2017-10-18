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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Test;
import org.projectodd.yaml.schema.types.AbstractBaseType;
import org.projectodd.yaml.schema.types.MapType;
import org.projectodd.yaml.schema.types.StringType;

public class StringTest extends AbstractBaseTest {

    @Test
    public void testSimple() throws SchemaException {
        Schema schema = new Schema( loadResource( "basic-schema.yml" ) );
        MapType root = (MapType) schema.getRoot();
        Map<String, AbstractBaseType> children = root.getChildren();
        assertEquals( 1, children.size() );
        StringType foo = (StringType) children.get( "foo" );
        assertTrue( foo.isRequired() );
        assertEquals( "foo", foo.getName() );
        schema.validate( loadResource( "valid-doc.yml" ) );
    }

    @Test
    public void testNonString() throws SchemaException {
        Schema schema = new Schema( loadResource( "basic-schema.yml" ) );
        MapType root = (MapType) schema.getRoot();
        Map<String, AbstractBaseType> children = root.getChildren();
        assertEquals( 1, children.size() );
        StringType foo = (StringType) children.get( "foo" );
        assertTrue( foo.isRequired() );
        assertEquals( "foo", foo.getName() );
        schema.validate( loadResource( "non-string-doc.yml" ) );
    }

    @Test
    public void testComplexInvalidDoc() throws Exception {
        try {
            Schema schema = new Schema( loadResource( "complex-schema.yml" ) );
            MapType root = (MapType) schema.getRoot();
            Map<String, AbstractBaseType> children = root.getChildren();
            assertEquals( 1, children.size() );
            StringType foo = (StringType) children.get( "foo" );
            assertTrue( foo.isRequired() );
            assertEquals( "^[0-9]{1,2}d$", foo.getPattern() );
            assertEquals( "foo", foo.getName() );
            schema.validate( loadResource( "invalid-doc.yml" ) );
            fail( "Invalid doc should have failed." );
        } catch (SchemaException e) {
            assertEquals( "Value abcde for field foo does not match regular expression ^[0-9]{1,2}d$", e.getMessage() );
        }
    }

    @Test
    public void testComplexValidDoc() throws Exception {
        Schema schema = new Schema( loadResource( "complex-schema.yml" ) );
        MapType root = (MapType) schema.getRoot();
        Map<String, AbstractBaseType> children = root.getChildren();
        assertEquals( 1, children.size() );
        StringType foo = (StringType) children.get( "foo" );
        assertTrue( foo.isRequired() );
        assertEquals( "^[0-9]{1,2}d$", foo.getPattern() );
        assertEquals( "foo", foo.getName() );
        schema.validate( loadResource( "valid-doc.yml" ) );
    }

    @Test
    public void testInvalidStringDoc() throws Exception {
        try {
            Schema schema = new Schema( loadResource( "complex-schema.yml" ) );
            schema.validate( loadResource( "invalid-string-doc.yml" ) );
            fail( "Should have failed." );
        } catch (SchemaException e) {
            assertEquals( "String field foo only accepts scalar values.", e.getMessage() );
        }
    }

    @Override
    public String getType() {
        return "string";
    }

}
