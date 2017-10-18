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

import org.junit.Before;
import org.projectodd.yaml.AbstractBaseTest;
import org.projectodd.yaml.Schema;
import org.projectodd.yaml.SchemaException;

public abstract class AbstractBaseTorqueBoxTest extends AbstractBaseTest {

    protected Schema schema;

    @Before
    public void before() throws SchemaException {
        schema = new Schema( loadResource( "schema.yml" ) );
    }

    @Override
    public String getType() {
        return "torquebox";
    }

}
