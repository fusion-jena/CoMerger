
package fusion.comerger.model.coordination.rule;
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
import java.util.LinkedHashMap;

import org.apache.jena.rdf.model.Resource;

import fusion.comerger.model.coordination.Coordinator;



public abstract class Equivalent implements Coordinator
{
    //HashMap<Resource, SameNode> allValues = new HashMap<Resource, SameNode>();
    LinkedHashMap<Resource, SameNode> allValues = new LinkedHashMap<Resource, SameNode>();//new samira

    public boolean isEmpty()
    {
        if (allValues != null) {
            return allValues.isEmpty();
        }
        return true;
    }

    public abstract boolean isEquivalent(Object o);

    public abstract Object getEquivalent(Object left);

    public class SameNode
    {
        public static final int MaxInteger = 5000000;
        public Object value = null;
        public int setNum = MaxInteger;

        public SameNode(Object v)
        {
            value = v;
        }
    }
}
