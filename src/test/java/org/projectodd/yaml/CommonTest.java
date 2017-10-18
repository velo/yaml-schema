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

import java.util.Map;

import org.junit.Test;
import org.projectodd.yaml.schema.types.AbstractBaseType;
import org.projectodd.yaml.schema.types.ComplexType;
import org.projectodd.yaml.schema.types.MapType;

public class CommonTest extends AbstractBaseTest {

    @Test
    public void testComplexTypeString() throws Exception {
        Schema schema = new Schema( loadResource( "complex-type-schema.yml" ) );
        MapType root = (MapType) schema.getRoot();
        Map<String, AbstractBaseType> children = root.getChildren();
        assertEquals( 1, children.size() );
        ComplexType foo = (ComplexType) children.get( "foo" );
        assertTrue( foo.isRequired() );
        assertEquals( "foo", foo.getName() );
        schema.validate( loadResource( "complex-type-string.yml" ) );
    }

    @Test
    public void testComplexTypeMap() throws Exception {
        Schema schema = new Schema( loadResource( "complex-type-schema.yml" ) );
        MapType root = (MapType) schema.getRoot();
        Map<String, AbstractBaseType> children = root.getChildren();
        assertEquals( 1, children.size() );
        ComplexType foo = (ComplexType) children.get( "foo" );
        assertTrue( foo.isRequired() );
        assertEquals( "foo", foo.getName() );
        schema.validate( loadResource( "complex-type-map.yml" ) );
    }

    @Override
    public String getType() {
        return "common";
    }

}
