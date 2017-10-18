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
package org.projectodd.yaml.schema.metadata;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.projectodd.yaml.SchemaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DependencyIndexer {

    private Map<String, String> nodes = new LinkedHashMap<String, String>( 20 );

    private boolean ready = false;

    private boolean verifyingDependencies;

    public boolean isNodeDefined(String node) throws SchemaException {
        if (!ready) {
            throw new SchemaException( "Cannot query dependency indexer prior to scanning." );
        }
        return nodes.containsKey( node );
    }

    public void scan(Object yamlObject) {
        if (ready) {
            log.debug( "Indexer has already scanned this doc - no need to rescan." );
            return;
        }
        if (yamlObject != null) {
            scan( null, null, yamlObject );
        }
    }

    @SuppressWarnings("unchecked")
    private synchronized void scan(String parentName, String name, Object yamlObject) {
        if (yamlObject instanceof Map) {
            Map<String, Object> yamlMap = (Map<String, Object>) yamlObject;
            if (yamlMap.isEmpty()) {
                nodes.put( appendName( parentName, name ), "" );
            }
            else {
                for (String key : yamlMap.keySet()) {
                    scan( appendName( parentName, name ), key, yamlMap.get( key ) );
                }
            }
        }
        else if (yamlObject instanceof List) {
            List<?> yamlList = (List<?>) yamlObject;
            for (int i = 0; i < yamlList.size(); i++) {
                Object item = yamlList.get( i );
                scan( appendName( parentName, name ), buildListName( i ), item );
            }
        }
        else {
            nodes.put( appendName( parentName, name ), "" );
        }
        ready = true;
    }

    private String appendName(String parent, String name) {
        String result = (parent == null ? "" : parent)
                + (parent != null && parent.equals( "/" ) ? "" : '/')
                + (name == null ? "" : name);
        return result;
    }

    private String buildListName(int index) {
        return "#" + index;
    }

    Map<String, String> getDocumentNodes() {
        return nodes;
    }

    public boolean isReady() {
        return ready;
    }

    public boolean isVerifyingDependencies() {
        return verifyingDependencies;
    }

    public void setVerifyingDependencies(boolean verifyingDependencies) {
        this.verifyingDependencies = verifyingDependencies;
    }

    private static final Logger log = LoggerFactory.getLogger( DependencyIndexer.class );

}
