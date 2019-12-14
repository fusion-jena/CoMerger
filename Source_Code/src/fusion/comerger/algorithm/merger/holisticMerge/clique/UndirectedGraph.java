package fusion.comerger.algorithm.merger.holisticMerge.clique;

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