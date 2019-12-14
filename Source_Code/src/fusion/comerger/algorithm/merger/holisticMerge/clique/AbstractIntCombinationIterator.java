package fusion.comerger.algorithm.merger.holisticMerge.clique;

import java.util.List;
import java.util.Objects;

/**
 * This abstract class defines the API and common internals for integer array
 * combination iterator.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 24, 2017)
 */
public abstract class AbstractIntCombinationIterator {

    /**
     * The integer array from which to build the combinations.
     */
    protected final int[] allInts;

    /**
     * The array of indices into {@code allInts}.
     */
    protected final int[] indices;

    /**
     * The current combination size.
     */
    protected int currentCombinationSize;

    /**
     * Constructs an iterator from the input integer array.
     * 
     * @param allInts the array of integers to combine.
     */
    public AbstractIntCombinationIterator(int[] allInts) {
        this.allInts = Objects.requireNonNull(
                        allInts,
                        "The input integer array is null.");
        this.indices = new int[allInts.length];
    }

    /**
     * Returns the current size of combinations.
     * 
     * @return combination size.
     */
    public int combinationSize() {
        return currentCombinationSize;
    }

    /**
     * Attempts to build and load the next combination.
     * 
     * @param list the list into which to store the integer combination.
     * 
     * @return {@code true} only if building the next combination was 
     *         successful.
     */
    public boolean loadNextCombination(List<Integer> list) {
        if (done()) {
            return false;
        }

        // Load 'list' with the next combination:
        loadCombination(list);

        // Now update the indices:
        updateIndices();
        return true;
    }

    /**
     * Loads the current combination into the input list.
     * 
     * @param list the list holding the combination.
     */
    private void loadCombination(List<Integer> list) {
        list.clear();

        for (int i = 0; i < currentCombinationSize; ++i) {
            list.add(allInts[indices[i]]);
        }
    }

    /**
     * Iterates towards the next combination.
     */
    protected abstract void updateIndices();

    /**
     * Returns {@code true} only if there is no more combinations to iterate.
     * 
     * @return {@code true} only if there is more combinations to build.
     */
    protected abstract boolean done();
}