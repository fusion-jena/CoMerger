
package fusion.comerger.model.coordination;

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
