 
package fusion.comerger.general.cc;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.Iterator;

import fusion.comerger.general.cc.Parameters;
import fusion.comerger.general.output.Alignment;
import fusion.comerger.general.output.Mapping;
import fusion.comerger.model.Constant;
import fusion.comerger.model.Node;
import fusion.comerger.model.NodeCategory;
import fusion.comerger.model.RBGModel;


public class StruComp
{
    private RBGModel modelA = null,  modelB = null;

    public StruComp(RBGModel modelA, RBGModel modelB)
    {
        this.modelA = modelA;
        this.modelB = modelB;
    }

    public int estimate1()
    {
        double percentA = percent(modelA);
        double percentB = percent(modelB);
        double pab = percentA / percentB, pba = percentB / percentA;
        if (pab >= Parameters.structPercent || pba >= Parameters.structPercent) {
            return Parameters.lowComp;
        } else {
            int countA[] = count(modelA);
            int countB[] = count(modelB);
            double temp1 = 0, temp2 = 0, temp3 = 0;
            int size = countA.length;
            for (int i = 0; i < size; i++) {
                temp1 += countA[i] * countB[i];
                temp2 += countA[i] * countA[i];
                temp3 += countB[i] * countB[i];
            }
            if (temp2 == 0 || temp3 == 0) {
                return Parameters.lowComp;
            } else {
                double value = temp1 / Math.sqrt(temp2 * temp3);
                if (value >= Parameters.structHighValue) {
                    return Parameters.highComp;
                } else if (value >= Parameters.structLowValue) {
                    return Parameters.mediumComp;
                } else {
                    return Parameters.lowComp;
                }
            }
        }
    }

    public int estimate2(Alignment as1, Alignment as2)
    {
        if (as1.size() == 0) {
            return Parameters.lowComp;
        }
        int count = 0;
        for (int i = 0, m = as1.size(); i < m; i++) {
            Mapping map1 = as1.getMapping(i);
            for (int j = 0, n = as2.size(); j < n; j++) {
                Mapping map2 = as2.getMapping(j);
                if (map1.equals(map2)) {
                    count++;
                }
            }
        }
        double value = count / (double) as1.size();
        if (value > Parameters.structHighRate) {
            return Parameters.highComp;
        } else if (value > Parameters.structLowRate) {
            return Parameters.mediumComp;
        } else {
            return Parameters.lowComp;
        }
    }

    private double percent(RBGModel model)
    {
        int stmtNum = 0, namedClassNum = 0, propertyNum = 0, namedInstanceNum = 0;
        for (Iterator<Node> iter = model.listNodes(); iter.hasNext();) {
            Node node = iter.next();
            int category = NodeCategory.getCategoryWithoutExternal(node);
            if (category == Constant.ONTOLOGY_CLASS) {
                if (!node.isAnon()) {
                    namedClassNum++;
                }
            } else if (category == Constant.ONTOLOGY_PROPERTY) {
                if (!node.isAnon()) {
                    propertyNum++;
                }
            } else if (category == Constant.ONTOLOGY_INSTANCE) {
                if (!node.isAnon()) {
                    namedInstanceNum++;
                }
            } else if (node.getNodeType() == Node.STATEMENT) {
                stmtNum++;
            }
        }
        return stmtNum / (double) (namedClassNum + propertyNum + namedInstanceNum);
    }

    private int[] count(RBGModel model)
    {
        int count[] = new int[9];
        int subClassOfNum = 0, domainNum = 0, rangeNum = 0, onPropertyNum = 0;
        int equivalentClassNum = 0, disjointWithNum = 0, 
                intersectionOfNum = 0, unionOfNum = 0, complementOfNum = 0;
        for (Iterator<Node> iter = model.listStmtNodes(); iter.hasNext();) {
            Node predicate = iter.next().getPredicate();
            String temp = predicate.toString();
            if (temp.equals(Constant.RDFS_NS + "subClassOf")) {
                subClassOfNum++;
            } else if (temp.equals(Constant.RDFS_NS + "domain")) {
                domainNum++;
            } else if (temp.equals(Constant.RDFS_NS + "range")) {
                rangeNum++;
            } else if (temp.equals(Constant.OWL_NS + "onProperty")) {
                onPropertyNum++;
            } else if (temp.equals(Constant.OWL_NS + "equivalentClass")) {
                equivalentClassNum++;
            } else if (temp.equals(Constant.OWL_NS + "disjointWith")) {
                disjointWithNum++;
            } else if (temp.equals(Constant.OWL_NS + "intersectionOf")) {
                intersectionOfNum++;
            } else if (temp.equals(Constant.OWL_NS + "unionOf")) {
                unionOfNum++;
            } else if (temp.equals(Constant.OWL_NS + "complementOf")) {
                complementOfNum++;
            }
        }
        count[0] = subClassOfNum;
        count[1] = domainNum;
        count[2] = rangeNum;
        count[3] = onPropertyNum;
        count[4] = equivalentClassNum;
        count[5] = disjointWithNum;
        count[6] = intersectionOfNum;
        count[7] = unionOfNum;
        count[8] = complementOfNum;
        return count;
    }
}
