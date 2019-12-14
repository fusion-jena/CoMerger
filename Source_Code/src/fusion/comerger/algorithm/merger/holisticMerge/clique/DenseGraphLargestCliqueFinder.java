package fusion.comerger.algorithm.merger.holisticMerge.clique;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fusion.comerger.algorithm.merger.holisticMerge.clique.AbstractLargestCliqueFinder;
import fusion.comerger.algorithm.merger.holisticMerge.clique.UndirectedGraph;

/**
 * This class implements a clique-finding algorithm that proceeds from larger
 * cliques candidates towards smaller. By construction, the very first clique 
 * found is guaranteed to be the largest.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 24, 2017)
 */
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