package fusion.comerger.algorithm.partitioner.pbm;
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
import java.util.Iterator;

import fusion.comerger.model.Node;

public class pbmCluster
{
    private int cid = -1;
    private double cohesion = 0;
    private HashMap<Integer, Double> couplings = null;
    private HashMap<String, Node> elements = null;

    public pbmCluster(int id)
    {
        cid = id;
        couplings = new HashMap<Integer, Double>();
        elements = new HashMap<String, Node>();
    }

    public int getClusterID()
    {
        return cid;
    }

    public double getCohesion()
    {
        return cohesion;
    }

    public HashMap<Integer, Double> getCouplings()
    {
        return couplings;
    }

    public Iterator<Double> listCouplings()
    {
        return couplings.values().iterator();
    }

    public HashMap<String, Node> getElements()
    {
        return elements;
    }

    public Iterator<Node> listElements()
    {
        return elements.values().iterator();
    }

    public void setClusterID(int id)
    {
        cid = id;
    }

    public void setCohesion(double c)
    {
        cohesion = c;
    }

    public void putCoupling(int id, double similarity)
    {
        couplings.put(id, similarity);
    }

    public void putElement(String key, Node node)
    {
        elements.put(key, node);
    }
}
