
package fusion.comerger.model.coordination;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import fusion.comerger.model.coordination.Coordinator;
import fusion.comerger.model.coordination.rule.AllDifferent;
import fusion.comerger.model.coordination.rule.Annotation;
import fusion.comerger.model.coordination.rule.EquivalentClass;
import fusion.comerger.model.coordination.rule.EquivalentProperty;
import fusion.comerger.model.coordination.rule.IntersectionOf;
import fusion.comerger.model.coordination.rule.List;
import fusion.comerger.model.coordination.rule.OntologyHeader;
import fusion.comerger.model.coordination.rule.Redefinition;
import fusion.comerger.model.coordination.rule.SameAs;
import fusion.comerger.model.coordination.rule.UnionOf;

public class RuleFactory
{
    public static Coordinator getAnnotationRule()
    {
        return new Annotation();
    }

    public static Coordinator getDeprecatedRule()
    {
        return new fusion.comerger.model.coordination.rule.Deprecated();
    }

    public static Coordinator getListRule()
    {
        return new List();
    }

    public static Coordinator getOntologyHeaderRule()
    {
        return new OntologyHeader();
    }

    public static Coordinator getIntersectionOfRule()
    {
        return new IntersectionOf();
    }

    public static Coordinator getAllDifferentRule()
    {
        return new AllDifferent();
    }

    public static Coordinator getUnionOfRule()
    {
        return new UnionOf();
    }

    public static Coordinator getRedefinitionRule()
    {
        return new Redefinition();
    }

    public static Coordinator getEquivalentClassRule()
    {
        return new EquivalentClass();
    }

    public static Coordinator getEquivalentPropertyRule()
    {
        return new EquivalentProperty();
    }

    public static Coordinator getSameAsRule()
    {
        return new SameAs();
    }
}
