
package fusion.comerger.model.coordination.rule;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
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
