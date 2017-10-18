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

public abstract class TestUtils {

    public static String join(String[] pieces, String delim) {
        StringBuffer buffer = new StringBuffer( "" );
        for (int i = 0; i < pieces.length; i++) {
            buffer.append( pieces[i] );
            if (i < pieces.length - 1) {
                buffer.append( delim );
            }
        }
        return buffer.toString();
    }

}
