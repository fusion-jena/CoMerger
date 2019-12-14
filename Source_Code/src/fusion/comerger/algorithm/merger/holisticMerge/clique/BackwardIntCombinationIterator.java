package fusion.comerger.algorithm.merger.holisticMerge.clique;

import fusion.comerger.algorithm.merger.holisticMerge.clique.AbstractIntCombinationIterator;

public final class BackwardIntCombinationIterator 
extends AbstractIntCombinationIterator {

    public BackwardIntCombinationIterator(int[] allInts) {
        super(allInts);
        super.currentCombinationSize = allInts.length;

        for (int i = 0; i < indices.length; ++i) {
            indices[i] = i;
        }
    }

    @Override
    protected void updateIndices() {
        if (indices[currentCombinationSize - 1] < indices.length - 1) {
            indices[currentCombinationSize - 1]++;
            return;
        }

        for (int i = currentCombinationSize - 2; i >= 0; --i) {
            if (indices[i] < indices[i + 1] - 1) {
                indices[i] ++;

                for (int j = i + 1; j < currentCombinationSize; ++j) {
                    indices[j] = indices[j - 1] + 1;
                }

                return;
            }
        }

        --currentCombinationSize;

        for (int i = 0; i < currentCombinationSize; ++i) {
            indices[i] = i;
        }
    }

    @Override
    protected boolean done() {
        return currentCombinationSize == 0;
    }
}