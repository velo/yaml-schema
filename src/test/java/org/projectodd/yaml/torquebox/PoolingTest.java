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
            assertEquals( "egot is not a valid value for field messaging", e.getMessage() );
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
            assertEquals( "{max=abc} is not a valid value for field messaging", e.getMessage() );
        }
    }

}
