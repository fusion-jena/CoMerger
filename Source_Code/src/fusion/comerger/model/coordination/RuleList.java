
package fusion.comerger.model.coordination;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.ArrayList;

import fusion.comerger.model.coordination.Coordinator;

public class RuleList
{
    private ArrayList<Coordinator> rules;

    public RuleList()
    {
        rules = new ArrayList<Coordinator>();
    }

    public RuleList(int capacity)
    {
        rules = new ArrayList<Coordinator>(capacity);
    }

    public RuleList(RuleList list)
    {
        rules = list.rules;
    }

    public Coordinator get(int i)
    {
        return rules.get(i);
    }

    public void add(Coordinator rule)
    {
        rules.add(rule);
    }

    public int size()
    {
        return rules.size();
    }
}
