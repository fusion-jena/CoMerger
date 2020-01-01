package fusion.comerger.algorithm.merger.holisticMerge.GMR;
/*
 * CoMerger: Holistic Ontology Merging
 * %%
 * Copyright (C) 2019 Heinz Nixdorf Chair for Distributed Information Systems, Friedrich Schiller University Jena
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
/**
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 24, 2017)
 * @source: https://codereview.stackexchange.com/questions/171029/finding-largest-graph-cliques-in-java
 */
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class UndirectedGraph {

    private final Map<Integer, Set<Integer>> adjacencyMap = new HashMap<>();

    public void addNode(int node) {
        if (!adjacencyMap.containsKey(node)) {
            adjacencyMap.put(node, new HashSet<>());
        }
    }

    public void connect(int node1, int node2) {
        addNode(node1);
        addNode(node2);
        adjacencyMap.get(node1).add(node2);
        adjacencyMap.get(node2).add(node1);
    }

    public Set<Integer> nodeSet() {
        return Collections.<Integer>unmodifiableSet(adjacencyMap.keySet());
    }

    public boolean edgeExists(int node1, int node2) {
        if (!adjacencyMap.containsKey(node1)) {
            return false;
        }

        if (!adjacencyMap.containsKey(node2)) {
            return false;
        }

        return adjacencyMap.get(node1).contains(node2);
    }

    public int size() {
        return adjacencyMap.size();
    }
}