
package fusion.comerger.general.output;
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
public class Evaluator
{
    public ResultData compare(Alignment as1, Alignment as2)
    {
        if (as1 == null || as2 == null) {
            return null;
        }
        int found = as1.size();
        int exist = as2.size();
        int correct = 0;

        if (found == 0 || exist == 0) {
            return null;
        }
        
        ResultData result = new ResultData();
        Alignment errorAlignment = new Alignment();
        Alignment correctAlignment = new Alignment();
        Alignment lostAlignment = new Alignment();
        
        for (int i = 0; i < found; i++) {
            Mapping map1 = as1.getMapping(i);
            boolean flag = true;
            for (int j = 0; j < exist; j++) {
                Mapping map2 = as2.getMapping(j);
                if (map1.equals(map2)) {
                    correct++;
                    correctAlignment.addMapping(map1);
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                errorAlignment.addMapping(map1);
            }
        }
        for (int i = 0; i < exist; i++) {
            Mapping map1 = as2.getMapping(i);
            boolean flag = true;
            for (int j = 0; j < found; j++) {
                Mapping map2 = as1.getMapping(j);
                if (map1.equals(map2)) {
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                lostAlignment.addMapping(map1);
            }
        }
        System.out.println("Found: " + found + 
        		", Exist: " + exist + ", Correct: " + correct);

        double prec = (double) correct / found;
        double rec = (double) correct / exist;
        System.out.println("Precision: " + prec + ", Recall: " + rec);
        // F-measure
        double fm = (1 + Parameters.alpha) * (prec * rec) / (Parameters.alpha * prec + rec);
        System.out.println("F-Measure: " + fm);

        result.setFound(found);
        result.setExist(exist);
        result.setCorrect(correct);
        
        result.setPrecision(prec);
        result.setRecall(rec);
        result.setFMeasure(fm);
        
        result.setCorrectAlignment(correctAlignment);
        result.setErrorAlignment(errorAlignment);
        result.setLostAlignment(lostAlignment);
        
        return result;
    }
}
