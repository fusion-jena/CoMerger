package fusion.comerger.model;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;


public interface Node {
	public static final int LANGUAGE_LEVEL = 1;
    public static final int ONTOLOGY_LEVEL = 2;
    public static final int INSTANCE_LEVEL = 4;
    public static final int EXTERNAL = 8;

    // { LANGUAGE_LEVEL, ONTOLOGY_LEVEL, INSTANCE_LEVEL, EXTERNAL }
    public int getNodeLevel();

    public void setNodeLevel(int nodeLevel);
    public static final int STATEMENT = 16;
    public static final int SPO = 32;
    public static final int SPO_RESOURCE = 80;
    public static final int SPO_RDFNODE = 96;

    // { STATEMENT, SPO_RESOURCE, SPO_RDFNODE }
    public int getNodeType();

    public void setNodeType(int nodeType);
    public static final int CLASS = 112;
    public static final int LIST = 113;
    public static final int OBJECTPROPERTY = 128;
    public static final int PROPERTY = 130;
    public static final int DATATYPEPROPERTY = 144;
    public static final int INSTANCE = 160;
    public static final int LITERAL = 176;

    public int getCategory();

    public void setCategory(int category);
    public static final int UNDEFINED = 0;

    public String getNameSpace();

    public String getNSQName();

    public String getLocalName();

    public ArrayList<String> getLabel();

    public ArrayList<String> getComment();

    public void addLabel(String label);

    public void addComment(String comment);

    public int getHierarchy();

    public void setHierarchy(int hierarchy);

    public RBGModel getRBGModel();

    public void setRBGModel(RBGModel model);

    public boolean isAnon();

    public boolean isLiteral();

    public boolean isBuiltIn();

    public void setNodeValue(Resource resource);

    public void setNodeValue(RDFNode rdfNode);

    public void setNodeValue(Statement statement);

    public Object getValue();

    public Node getSubject();

    public Node getPredicate();

    public Node getObject();

    public NodeList getAdjacentNodes();

    public Iterator<Node> listAdjacentNodes();

    public NodeList getPointedNodes();

    public Iterator<Node> listPointedNodes();

    // public Iterator listSuperNodes();
    // public NodeList getSuperNodes();
    
    public Iterator<Node> listNamedSupers();

    public NodeList getNamedSupers();

    public void addNamedSuper(Node node);

    // public Iterator listSubNodes();
    // public NodeList getSubNodes();
    
    public Iterator<Node> listNamedSubs();

    public NodeList getNamedSubs();

    public void addNamedSub(Node node);

    public Iterator<Node> listNamedDomains();

    public NodeList getNamedDomains();

    public void addNamedDomain(Node node);

    public String toString();

    public boolean equals(Node node);

}
