
package fusion.comerger.algorithm.matcher.vdoc;
/* 
 * This package is downloaded from the FALCON-AO tool, 
 * available in http://ws.nju.edu.cn/falcon-ao/
 * For more information of this method, please refer to
 * Hu, W. and Qu, Y., 2008. Falcon-AO: A practical ontology matching system. Journal of web semantics, 6(3), pp.237-239.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
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

public class NeighborSet
{
    private HashMap<String, Neighbor> neighbors = new HashMap<String, Neighbor>();

    public void addNeighbor(Neighbor neighbor)
    {
        neighbors.put(neighbor.getURI(), neighbor);
    }

    public Neighbor getNeighbor(String uri)
    {
        return neighbors.get(uri);
    }

    public HashMap<String, Neighbor> getNeighbors()
    {
        return neighbors;
    }

    public int size()
    {
        return neighbors.size();
    }

    public void show()
    {
        Iterator<Neighbor> iter = neighbors.values().iterator();
        while (iter.hasNext()) {
            Neighbor neighbor = iter.next();
            System.out.println(neighbor.getURI());
            Iterator<TokenNode> i1 = neighbor.getSubjects().values().iterator();
            while (i1.hasNext()) {
                TokenNode node = i1.next();
                System.out.println("->[s]->" + node.getURI());
            }
            Iterator<TokenNode> i2 = neighbor.getPredicates().values().iterator();
            while (i2.hasNext()) {
                TokenNode node = i2.next();
                System.out.println("->[p]->" + node.getURI());
            }
            Iterator<TokenNode> i3 = neighbor.getObjects().values().iterator();
            while (i3.hasNext()) {
                TokenNode node = i3.next();
                System.out.println("->[o]->" + node.getURI());
            }
            System.out.println();
        }
    }
}
