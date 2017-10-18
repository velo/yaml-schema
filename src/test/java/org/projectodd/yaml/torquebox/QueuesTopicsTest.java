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
import org.projectodd.yaml.AbstractBaseTest;
import org.projectodd.yaml.Schema;
import org.projectodd.yaml.SchemaException;

public class QueuesTopicsTest extends AbstractBaseTest {

    @Test
    public void testInvalidKey() throws Exception {
        for (String key : new String[] { "queue", "topic" }) {
            try {
                Schema schema = new Schema( loadResource( "schema.yml" ) );
                schema.validate( loadResource( "queues_and_topics/invalid-key-" + key + "-torquebox.yml" ) );
                fail( "Should have failed." );
            } catch (SchemaException e) {
                assertEquals( "No valid value found for field /" + key + "/foo", e.getMessage() );
                assertEquals( "Unrecognized field: homer", e.getCause().getMessage() );
            }
        }
    }

    @Test
    public void testValidSimple() throws SchemaException {
        for (String key : new String[] { "queue", "topic" }) {
            Schema schema = new Schema( loadResource( "schema.yml" ) );
            schema.validate( loadResource( "queues_and_topics/valid-simple-" + key + "-torquebox.yml" ) );
        }
    }

    @Test
    public void testValidComplex() throws SchemaException {
        for (String key : new String[] { "queue", "topic" }) {
            Schema schema = new Schema( loadResource( "schema.yml" ) );
            schema.validate( loadResource( "queues_and_topics/valid-complex-" + key + "-torquebox.yml" ) );
        }
    }

    @Override
    public String getType() {
        return "torquebox";
    }

}
