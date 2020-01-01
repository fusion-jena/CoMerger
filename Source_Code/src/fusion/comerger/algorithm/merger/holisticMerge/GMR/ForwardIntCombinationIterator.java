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
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 24, 2017)
 * @source: https://codereview.stackexchange.com/questions/171029/finding-largest-graph-cliques-in-java
 */
 
 import fusion.comerger.algorithm.merger.holisticMerge.GMR.AbstractIntCombinationIterator;

public final class ForwardIntCombinationIterator 
        extends AbstractIntCombinationIterator {

    public ForwardIntCombinationIterator(int[] allInts) {
        super(allInts);
        this.currentCombinationSize = 1;
    }

    @Override
    protected boolean done() {
        return currentCombinationSize == allInts.length + 1;
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

        ++currentCombinationSize;

        if (currentCombinationSize <= allInts.length) {
            for (int i = 0; i < currentCombinationSize; ++i) {
                indices[i] = i;
            }
        }
    }
}