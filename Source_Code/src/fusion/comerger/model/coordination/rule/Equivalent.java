
package fusion.comerger.model.coordination.rule;

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
