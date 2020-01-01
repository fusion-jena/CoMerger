
package fusion.comerger.algorithm.partitioner.SeeCOnt.Findk;
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
import java.util.Arrays;

import fusion.comerger.model.NodeList;


public class Sorter {
         
    private float array[];
    private int length;
    public String SortedName[];
    public float SortedNum[];
///////////////////////////////////////////////////////////////////////////////////////////
    public String [] sort(float[] inputArr, NodeList entities) {
         SortedName = new String [entities.size()];//n
         for (int ii=0; ii<entities.size();ii++){
//            SortedName [ii] = entities.get(ii).getLocalName();
            SortedName [ii] = entities.get(ii).toString();//new:samira
    }
        if (inputArr == null || inputArr.length == 0) {
            return null;
        }
        this.array = inputArr;
        length = inputArr.length;
        quickSort(0, length - 1);
    return SortedName;
    }

    
///////////////////////////////////////////////////////////////////////////////////////////
 private void quickSort(int lowerIndex, int higherIndex) {
         
        int i = lowerIndex;
        int j = higherIndex;
        float pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
        while (i <= j) {
            while (array[i] > pivot) {
                i++;
            }
            while (array[j] < pivot) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j);
                i++;
                j--;
            }
        }
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }
    
/////////////////////////////////////////////////////////////////////////////////////////////
    private void exchangeNumbers(int i, int j) {
        float temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        
        String tempName = SortedName[i];
        SortedName [i] = SortedName[j];
        SortedName[j] = tempName;        
    }
}