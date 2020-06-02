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
 * This abstract class defines the API and common internals of largest clique
 * finding algorithms.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 24, 2017)
 *  * @source: https://codereview.stackexchange.com/questions/171029/finding-largest-graph-cliques-in-java
 */

import java.util.List;

public abstract class AbstractLargestCliqueFinder {

    /**
     * Returns the first found largest clique.
     * 
     * @param graph the graph to search.
     * @return an array of nodes belonging to a largest clique.
     */
    public abstract int[] computeLargestClique(UndirectedGraph graph);

    /**
     * Checks whether the nodes in {@code clique} form a clique.
     * 
     * @param graph  the graph.
     * @param clique the list of nods of the graph.
     * @return {@code true} only if the node list is a clique in the graph.
     */
    protected boolean isClique(UndirectedGraph graph,
                               List<Integer> clique) {
        for (int i = 0; i < clique.size(); ++i) {
            for (int j = i + 1; j < clique.size(); ++j) {
                if (!graph.edgeExists(clique.get(i), clique.get(j))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Converts the entire graph to an array of nodes.
     * 
     * @param graph the graph to convert.
     * @return the node array.
     */
    protected int[] getNodeArray(UndirectedGraph graph) {
        int[] nodeArray = new int[graph.size()];
        int index = 0;

        for (int node : graph.nodeSet()) {
            nodeArray[index++] = node;
        }

        return nodeArray;
    }

    /**
     * Checks that the graph is not empty.
     * 
     * @param graph the graph to check.
     */
    protected void checkGraphNotEmpty(UndirectedGraph graph) {
        if (graph.size() == 0) {
            throw new IllegalArgumentException("The input graph is empty.");
        }
    }

    /**
     * Converts an integer list to an integer array.
     * 
     * @param list the list to convert.
     * @return the array of integers.
     */
    protected int[] intListToIntArray(List<Integer> list) {
        int[] array = new int[list.size()];
        int index = 0;

        for (int i : list) {
            array[index++] = i;
        }

        return array;
    }
}