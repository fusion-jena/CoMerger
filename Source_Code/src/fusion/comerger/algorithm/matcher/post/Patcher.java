
package fusion.comerger.algorithm.matcher.post;

import java.util.HashMap;

import fusion.comerger.algorithm.matcher.AbstractMatcher;
import fusion.comerger.general.output.Alignment;
import fusion.comerger.general.output.Mapping;

public class Patcher implements AbstractMatcher
{
    private Alignment alignSet = null;

    public Patcher(Alignment as)
    {
        alignSet = as;
    }

    public void match()
    {
        HashMap<String, Mapping> am = new HashMap<String, Mapping>(alignSet.size());
        for (int i = 0; i < alignSet.size(); i++) {
            Mapping map = alignSet.getMapping(i);
            String s1 = map.getEntity1().toString();
            Mapping temp = am.get(s1);
            if (temp == null) {
                am.put(s1, map);
            } else {
                double sim1 = temp.getSimilarity(), sim2 = map.getSimilarity();
                if (sim1 < sim2) {
                    temp.setEntity2(map.getEntity2());
                    temp.setSimilarity(sim2);
                    temp.setRelation(map.getRelation());
                }
                if (sim2 != 1.0) {
                    alignSet.removeMapping(i);
                    i--;
                }
            }
        }
        
        am.clear();
        for (int i = 0; i < alignSet.size(); i++) {
            Mapping map = alignSet.getMapping(i);
            String s2 = map.getEntity2().toString();
            Mapping temp = am.get(s2);
            if (temp == null) {
                am.put(s2, map);
            } else {
                double sim1 = temp.getSimilarity(), sim2 = map.getSimilarity();
                if (sim1 < sim2) {
                    temp.setEntity1(map.getEntity1());
                    temp.setSimilarity(sim2);
                    temp.setRelation(map.getRelation());
                }
                if (sim2 != 1.0) {
                    alignSet.removeMapping(i);
                    i--;
                }
            }
        }
        
        am.clear();
        for (int i = 0; i < alignSet.size(); i++) {
            Mapping map = alignSet.getMapping(i);
            for (int j = i + 1; j < alignSet.size(); j++) {
                Mapping temp = alignSet.getMapping(j);
                if (map.equals(temp)) {
                    alignSet.removeMapping(j);
                    j--;
                }
            }
        }
    }

    public Alignment getAlignment()
    {
        return null;
    }

    public Alignment getClassAlignment()
    {
        return null;
    }

    public Alignment getPropertyAlignment()
    {
        return null;
    }

    public Alignment getInstanceAlignment()
    {
        return null;
    }
}
