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
package org.projectodd.yaml.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.projectodd.yaml.AbstractBaseTest;
import org.projectodd.yaml.Schema;
import org.projectodd.yaml.SchemaException;
import org.projectodd.yaml.schema.types.AbstractBaseType;
import org.projectodd.yaml.schema.types.MapType;

public class ValueTypesTest extends AbstractBaseTest {

    private Schema schema;

    @Test
    public void testInvalidBadType() throws Exception {
        try {
            schema.validate( loadResource( "value-types/invalid-badtype-doc.yml" ) );
            fail( "Should have failed." );
        } catch (SchemaException e) {
            assertEquals( "No valid value found for field fooferaw", e.getMessage() );
        }
    }

    @Test
    public void testValidMixedType() throws Exception {
        schema.validate( loadResource( "value-types/valid-mixedtype-doc.yml" ) );
    }

    @Test
    public void testValid() throws Exception {
        schema.validate( loadResource( "value-types/valid-doc.yml" ) );
    }

    @Before
    public void before() throws Exception {
        schema = new Schema( loadResource( "value-types/schema.yml" ) );
        MapType root = (MapType) schema.getRoot();
        Map<String, AbstractBaseType> children = root.getChildren();
        assertEquals( 2, children.size() );
        MapType bob = (MapType) children.get( "bob" );
        assertTrue( bob.isAllowingArbitraryKeys() );
        assertFalse( bob.isRequired() );
        List<AbstractBaseType> valueTypes = bob.getValueTypes();
        assertEquals( 2, valueTypes.size() );
        MapType valueType = (MapType) valueTypes.get( 0 );
        assertEquals( 3, valueType.getChildren().size() );
    }

    @Override
    public String getType() {
        return "map";
    }

}
