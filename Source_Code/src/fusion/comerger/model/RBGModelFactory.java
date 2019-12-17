package fusion.comerger.model;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import fusion.comerger.model.coordination.RuleFactory;
import fusion.comerger.model.coordination.RuleList;
import fusion.comerger.model.modelImpl.RBGModelImpl;

public class RBGModelFactory 
{
	public static RBGModel createModel(String modelType)
    {
        RBGModelImpl model = new RBGModelImpl();
        if (modelType.equalsIgnoreCase("GMO_MODEL")) {
        	RuleList rules = new RuleList();
            rules.add(RuleFactory.getAnnotationRule());
            rules.add(RuleFactory.getAllDifferentRule());
            rules.add(RuleFactory.getIntersectionOfRule());
            rules.add(RuleFactory.getListRule());
            rules.add(RuleFactory.getOntologyHeaderRule());
            rules.add(RuleFactory.getRedefinitionRule());
            rules.add(RuleFactory.getUnionOfRule());
            rules.add(RuleFactory.getEquivalentClassRule());
            rules.add(RuleFactory.getEquivalentPropertyRule());
            rules.add(RuleFactory.getSameAsRule());
            model.setCoordinationRuleList(rules);
            model.setClearClassType(true);
            model.setAddInstType(true);
        } else if (modelType.equalsIgnoreCase("VDOC_MODEL")) {
            RuleList rules = new RuleList();
            rules.add(RuleFactory.getAnnotationRule());
            rules.add(RuleFactory.getAllDifferentRule());
            rules.add(RuleFactory.getIntersectionOfRule());
            rules.add(RuleFactory.getListRule());
            rules.add(RuleFactory.getOntologyHeaderRule());
            rules.add(RuleFactory.getRedefinitionRule());
            rules.add(RuleFactory.getUnionOfRule());
            model.setCoordinationRuleList(rules);
            model.setClearClassType(true);
            model.setAddInstType(true);
        } else if (modelType.equalsIgnoreCase("STRING_MODEL")) {
            RuleList rules = new RuleList();
            rules.add(RuleFactory.getAnnotationRule());
            rules.add(RuleFactory.getDeprecatedRule());
            rules.add(RuleFactory.getOntologyHeaderRule());
            rules.add(RuleFactory.getRedefinitionRule());
            model.setCoordinationRuleList(rules);
            model.setClearClassType(true);
            model.setAddInstType(true);
        } else if (modelType.equalsIgnoreCase("PBM_MODEL")) {
            RuleList rules = new RuleList();
            rules.add(RuleFactory.getAnnotationRule());
            rules.add(RuleFactory.getDeprecatedRule());
            rules.add(RuleFactory.getOntologyHeaderRule());
            rules.add(RuleFactory.getRedefinitionRule());
            model.setCoordinationRuleList(rules);
            model.setInitHierarchy(true);
        }
        return model;
    }

}
