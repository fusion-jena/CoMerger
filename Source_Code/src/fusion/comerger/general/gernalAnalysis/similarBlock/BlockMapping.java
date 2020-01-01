
package fusion.comerger.general.gernalAnalysis.similarBlock;
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
public class BlockMapping
{
    private String cname1 = null;
    private String cname2 = null;
    private double similarity = 0;

    public BlockMapping(String cn1, String cn2, double sim)
    {
        cname1 = cn1;
        cname2 = cn2;
        similarity = sim;
    }

    public String getClusterName1()
    {
        return cname1;
    }

    public void setClusterName1(String cn1)
    {
        cname1 = cn1;
    }

    public String getClusterName2()
    {
        return cname2;
    }

    public void setClusterName2(String cn2)
    {
        cname2 = cn2;
    }

    public void setSimilarity(double sim)
    {
        similarity = sim;
    }

    public double getSimilarity()
    {
        return similarity;
    }
}
