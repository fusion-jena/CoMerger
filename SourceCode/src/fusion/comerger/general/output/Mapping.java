
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
import fusion.comerger.model.Node;

public class Mapping
{
    private Node entity1 = null;
    private Node entity2 = null;
    private double similarity = 0;
    private String relation = null;

    public Mapping(Node e1, Node e2, double sim, String r)
    {
        entity1 = e1;
        entity2 = e2;
        similarity = sim;
        relation = r;
    }

    public Mapping(Node e1, Node e2, double sim)
    {
        entity1 = e1;
        entity2 = e2;
        similarity = sim;
        relation = "=";
    }

    public Mapping(Node e1, Node e2)
    {
        entity1 = e1;
        entity2 = e2;
        similarity = 1.0;
        relation = "=";
    }

    public Node getEntity1()
    {
        return entity1;
    }

    public void setEntity1(Node e1)
    {
        entity1 = e1;
    }

    public Node getEntity2()
    {
        return entity2;
    }

    public void setEntity2(Node e2)
    {
        entity2 = e2;
    }

    public void setSimilarity(double sim)
    {
        similarity = sim;
    }

    public double getSimilarity()
    {
        return similarity;
    }

    public boolean equals(Mapping map)
    {
        if (entity1.equals(map.getEntity1()) && 
        		entity2.equals(map.getEntity2())) {
            return true;
        } else {
            return false;
        }
    }

    public String getRelation()
    {
        return relation;
    }

    public void setRelation(String r)
    {
        relation = r;
    }

    public String filter(String s)
    {
        if (s == null) {
            return null;
        } else {
            int index = s.lastIndexOf("#");
            if (index >= 0) {
                return s.substring(index + 1);
            } else {
                index = s.lastIndexOf("/");
                if (index >= 0) {
                    return s.substring(index + 1);
                } else { 
                    return s;
                }
            }
        }
    }
}
