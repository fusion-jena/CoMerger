package fusion.comerger.algorithm.merger.holisticMerge.clique;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fusion.comerger.algorithm.merger.holisticMerge.clique.AbstractLargestCliqueFinder;
import fusion.comerger.algorithm.merger.holisticMerge.clique.UndirectedGraph;

/**
 * This clique finder starts to search trivial cliques of one node. The
 * algorithm caches the largest tentative clique of size {@code k}. If at some
 * point it cannot find a clique of size {@code k + 1}, it returns the cached
 * clique. Needless to say, this is algorithm is best applied to sparse graphs.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 24, 2017)
 */
public final class SparseGraphLargestCliqueFinder extends AbstractLargestCliqueFinder {
	static ArrayList<ArrayList<Integer>> allSet = new ArrayList<ArrayList<Integer>>();

	@Override
	public int[] computeLargestClique(UndirectedGraph graph) {

		Objects.requireNonNull(graph, "The input graph is null.");
		checkGraphNotEmpty(graph);

		int[] nodes = getNodeArray(graph);
		List<Integer> clique = new ArrayList<>(graph.size());
		List<Integer> bestClique = new ArrayList<>(graph.size());
		ForwardIntCombinationIterator iterator = new ForwardIntCombinationIterator(nodes);

		while (iterator.loadNextCombination(clique)) {
			if (iterator.combinationSize() > clique.size() + 1) {
				break;
			}
			// @samira: no need to do it, since it only return 1 best set. I
			// need all.
			/*if (isClique(graph, clique) && bestClique.size() < clique.size()) {
				bestClique.clear();
				bestClique.addAll(clique);
			}*/
			// I changed the code, to save all generated clique, not only
			// returns the best clique, but later i filter this allSet to only
			// keep those sets which do not have any subset in the result, i.e.
			// if in the allSet, we have {1,2} and {1,2,3}, I would only keep
			// the maximum set and will delete {1,2}
			if (isClique(graph, clique)) {
				ArrayList<Integer> temp = new ArrayList<Integer>();
				for (int i = 0; i < clique.size(); i++) {
					temp.add(clique.get(i));
					// System.out.print(clique.get(i).toString()+"-");
				}
				// System.out.println("\n next clique");
				allSet.add(temp);
			}
//			else{
//				int w=0; 
//				System.out.println("the clique is not a clique!" );
//			}
		}
		System.out.println("Size of all set:" + allSet.size());
		return intListToIntArray(bestClique);
	}

	public static ArrayList<ArrayList<Integer>> getAllClique() {
		return allSet;
	}
}