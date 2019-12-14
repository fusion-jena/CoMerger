package fusion.comerger.algorithm.merger.holisticMerge.clique;

import java.util.List;

/**
 * This abstract class defines the API and common internals of largest clique
 * finding algorithms.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 24, 2017)
 */
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