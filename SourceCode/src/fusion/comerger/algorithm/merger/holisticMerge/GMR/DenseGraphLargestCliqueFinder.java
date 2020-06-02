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
 * This class implements a clique-finding algorithm that proceeds from larger
 * cliques candidates towards smaller. By construction, the very first clique 
 * found is guaranteed to be the largest.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 24, 2017)
 * @source: https://codereview.stackexchange.com/questions/171029/finding-largest-graph-cliques-in-java
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fusion.comerger.algorithm.merger.holisticMerge.GMR.AbstractLargestCliqueFinder;
import fusion.comerger.algorithm.merger.holisticMerge.GMR.UndirectedGraph;


public class DenseGraphLargestCliqueFinder extends AbstractLargestCliqueFinder {

    @Override
    public int[] computeLargestClique(UndirectedGraph graph) {
        Objects.requireNonNull(graph, "The input graph is null.");
        checkGraphNotEmpty(graph);

        int[] nodes = getNodeArray(graph);
        BackwardIntCombinationIterator iterator = 
                new BackwardIntCombinationIterator(nodes);
        List<Integer> clique = new ArrayList<>(graph.size());

        while (iterator.loadNextCombination(clique)) {
            if (isClique(graph, clique)) {
                break;
            }
        }

        return intListToIntArray(clique);
    }
}