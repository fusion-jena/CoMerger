package fusion.comerger.model;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.ArrayList;
import java.util.Iterator;


import org.apache.jena.ontology.OntModel;

import fusion.comerger.model.coordination.RuleList;



public interface RBGModel {
	public OntModel getOntModel();

    public void setOntModel(OntModel ontModel);

    public RBGModel read(String uri);

    public void clearModel();

    public void setCoordinationRuleList(RuleList rules);

    public Iterator<Quadruple> listQuadruples();

    public ArrayList<Quadruple> getQuadruples();

    public Iterator<Node> listStmtNodes();

    public NodeList getStmtNodes();

    public Iterator<Node> listNodes();

    public NodeList getNodes();

    public Node getNode(Object node);

    public String getNsPrefixURI(String prefix);

    public String getNsURIPrefix(String uri);

    public Iterator<Node> listNamedClassNodes();

    public NodeList getNamedClassNodes();

    public Iterator<Node> listNamedInstanceNodes();

    public NodeList getNamedInstanceNodes();

    public Iterator<Node> listClassNodes();

    public NodeList getClassNodes();

    public Iterator<Node> listPropertyNodes();

    public NodeList getPropertyNodes();

    public Iterator<Node> listInstanceNodes();

    public NodeList getInstanceNodes();

    public Iterator<Node> listLiteralNodes();

    public Iterator<Node> listLanguageLevel();

    public Iterator<Node> listOntologyLevel();

    public Iterator<Node> listInstanceLevel();

    public void setAddInstType(boolean ait);

    public void setClearClassType(boolean cct);

}
