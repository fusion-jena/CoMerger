package fusion.comerger.algorithm.partitioner.hierClust;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
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
import java.util.HashMap;
import java.util.Map;

/**
 * Maps object values to an index
 */
public class ObjectToIndexMapping<T> implements java.io.Serializable {


    private static final long serialVersionUID = 2031098306406708902L;

    /*
     * Index value that will be returned for the next new value.
     */
    private int nextIndex = 0;

    /*
     * Maintains mapping from object to index.
     */
    private Map<T, Integer> objMapping = new HashMap<T, Integer>();

    /*
     * Maintains mapping from index to value.
     */
    private Map<Integer, T> indexMapping = new HashMap<Integer, T>();

    public ObjectToIndexMapping() {
        // empty
    }

    /**
     * Returns value mapped to the index or null if mapping doesn't exist.
     */
    public T getObject(int index) {
        return indexMapping.get(index);
    }

    /**
     * Returns index assigned to the value. For new values new index will be assigned
     * and returned.
     */
    public int getIndex(T value) {
        Integer index = objMapping.get(value);
        if( index == null ) {
            index = nextIndex;
            objMapping.put(value, index);
            indexMapping.put(index, value);
            nextIndex++;
        }
        return index;
    }

    /**
     * Current number of elements.
     */
    public int getSize() {
        return objMapping.size();
    }
}

