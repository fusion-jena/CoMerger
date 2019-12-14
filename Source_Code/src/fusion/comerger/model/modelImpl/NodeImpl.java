package fusion.comerger.model.modelImpl;

import java.util.ArrayList;
import java.util.Iterator;




import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

import fusion.comerger.model.Node;
import fusion.comerger.model.NodeList;
import fusion.comerger.model.Quadruple;
import fusion.comerger.model.RBGModel;

public  class NodeImpl  implements Node
{
	 private Resource resource = null;
	    private RDFNode rdfnode = null;    
	    private Statement statement = null;
	    private int nodeType = Node.UNDEFINED;
	    private int nodeLevel = Node.UNDEFINED;
	    private int category = Node.UNDEFINED;
	    private ArrayList<String> label = null;
	    private ArrayList<String> comment = null;
	    private String uri = null;
	    private int hierarchy = 0;
	    private NodeList parents = null;
	    private NodeList children = null;
	    private NodeList domains = null;
	    private RBGModel model = null;
	    public int _built_in = 0;
	    public int _as_prop = 0;
	    public int _p_cls = 0;
	    public int _cls_lvl = 0;

	    public NodeImpl(Resource resource)
	    {
	        setNodeValue(resource);
	        setNodeType(SPO_RESOURCE);
	    }

	    public NodeImpl(RDFNode rdfNode)
	    {
	        setNodeValue(rdfNode);
	        setNodeType(SPO_RDFNODE);
	    }

	    public NodeImpl(Statement statement)
	    {
	        setNodeValue(statement);
	        setNodeType(STATEMENT);
	    }

	    public String getLocalName()
	    {
	        if (nodeType == STATEMENT) {
	            return "[STATEMENT]";
	        } else if (nodeType == SPO_RESOURCE) {
	            return resource.getLocalName();
	        } else if (nodeType == SPO_RDFNODE) {
	            if (rdfnode.asNode().isLiteral()) {
	                return rdfnode.asNode().getLiteral().getLexicalForm();
	            } else {
	                return rdfnode.asNode().toString();   
	            }
	        }
	        return null;
	    }

	    public String getNameSpace()
	    {
	        if (nodeType == STATEMENT) {
	            return "STATEMENT";
	        } else if (nodeType == SPO_RESOURCE) {
	            return resource.getNameSpace();
	        } else if (nodeType == SPO_RDFNODE) {
	            if (rdfnode.asNode().isLiteral()) {
	                return rdfnode.asNode().getLiteral().getDatatypeURI();
	            }
	        }
	        return null;
	    }

	    public String getNSQName()
	    {
	        if (nodeType == STATEMENT) {
	            return "STATEMENT";
	        } else if (nodeType == SPO_RESOURCE && getNameSpace() != null) {
	            int index = toString().indexOf(getLocalName());
	            return model.getNsURIPrefix(toString().substring(0, index));
	        }
	        return null;
	    }

	    public ArrayList<String> getLabel()
	    {
	        return label;
	    }

	    public void addLabel(String l)
	    {
	        if (label == null) {
	            label = new ArrayList<String>();
	        }
	        label.add(l);
	    }

	    public ArrayList<String> getComment()
	    {
	        return comment;
	    }

	    public void addComment(String c)
	    {
	        if (comment == null) {
	            comment = new ArrayList<String>();
	        }
	        comment.add(c);
	    }

	    public int getHierarchy()
	    {
	        return hierarchy;
	    }

	    public void setHierarchy(int hier)
	    {
	        hierarchy = hier;
	    }

	    public int getNodeType()
	    {
	        return nodeType;
	    }

	    public void setNodeType(int nt)
	    {
	        nodeType = nt;
	    }

	    public int getNodeLevel()
	    {
	        return nodeLevel;
	    }

	    public void setNodeLevel(int nl)
	    {
	        nodeLevel = nl;
	    }

	    public int getCategory()
	    {
	        return category;
	    }

	    public void setCategory(int c)
	    {
	        category = c;
	    }

	    public RBGModel getRBGModel()
	    {
	        return model;
	    }

	    public void setRBGModel(RBGModel m)
	    {
	        model = m;
	    }

	    public Object getValue()
	    {
	        if (getNodeType() == Node.STATEMENT) {
	            return statement;
	        } else if (getNodeType() == Node.SPO_RDFNODE) {
	            return rdfnode;
	        } else if (getNodeType() == Node.SPO_RESOURCE) {
	            return resource;
	        }
	        return null;
	    }

	    public void setNodeValue(Resource r)
	    {
	        resource = r;
	    }

	    public void setNodeValue(RDFNode n)
	    {
	        rdfnode = n;
	    }

	    public void setNodeValue(Statement s)
	    {
	        statement = s;
	    }

	    public Node getSubject()
	    {
	        if (nodeType == STATEMENT) {
	            return model.getNode(statement.getSubject());
	        }
	        return null;
	    }

	    public Node getPredicate()
	    {
	        if (nodeType == STATEMENT) {
	            return model.getNode(statement.getPredicate());
	        }
	        return null;
	    }

	    public Node getObject()
	    {
	        if (nodeType == STATEMENT) {
	            return model.getNode(statement.getObject());
	        }
	        return null;
	    }

	    public boolean isAnon()
	    {
	        if (nodeType == STATEMENT) {
	            return true;
	        } else if (nodeType == SPO_RESOURCE) {
	            return resource.isAnon();
	        } else if (nodeType == SPO_RDFNODE) {
	            return rdfnode.asNode().isBlank();
	        }
	        return true;
	    }

	    public boolean isLiteral()
	    {
	        if (getCategory() == Node.LITERAL) {
	            return true;
	        }
	        return false;
	    }

	    public boolean isBuiltIn()
	    {
	        if (getNodeLevel() == Node.LANGUAGE_LEVEL && !isLiteral()) {
	            return true;
	        } else {
	            return false;
	        }
	    }

	    public NodeList getAdjacentNodes()
	    {
	        NodeList nodeList = new NodeList();
	        if (nodeType == STATEMENT) {
	            nodeList.add(getSubject());
	            nodeList.add(getPredicate());
	            nodeList.add(getObject());
	        } else {
	            Iterator<Quadruple> quadruples = model.listQuadruples();
	            while (quadruples.hasNext()) {
	                Quadruple quadruple = quadruples.next();
	                if (quadruple.getSubject().equals(this) || 
	                		quadruple.getPredicate().equals(this) || 
	                		quadruple.getObject().equals(this)) {
	                    nodeList.add(quadruple.getStatement());
	                }
	            }
	        }
	        return nodeList;
	    }

	    public Iterator<Node> listAdjacentNodes()
	    {
	        NodeList nodeList = getAdjacentNodes();
	        return nodeList.iterator();
	    }

	    public NodeList getPointedNodes()
	    {
	        NodeList nodeList = new NodeList();
	        if (nodeType == STATEMENT) {
	            nodeList.add(getPredicate());
	            nodeList.add(getObject());
	        } else {
	            Iterator<Quadruple> quadruples = model.listQuadruples();
	            while (quadruples.hasNext()) {
	                Quadruple quadruple = quadruples.next();
	                if (quadruple.getSubject().equals(this)) {
	                    nodeList.add(quadruple.getStatement());
	                }
	            }
	        }
	        return nodeList;
	    }

	    public Iterator<Node> listPointedNodes()
	    {
	        NodeList nodeList = getPointedNodes();
	        return nodeList.iterator();
	    }

	    public Iterator<Node> listNamedSupers()
	    {
	        return getNamedSupers().iterator();
	    }

	    public NodeList getNamedSupers()
	    {
	        return parents;
	    }

	    public void addNamedSuper(Node node)
	    {
	        if (parents == null) {
	            parents = new NodeList();
	        }
	        if (!parents.contains(node)) {
	            parents.add(node);
	        }
	    }
	    
	    public Iterator<Node> listNamedSubs()
	    {
	        return getNamedSubs().iterator();
	    }

	    public NodeList getNamedSubs()
	    {
	        return children;
	    }

	    public void addNamedSub(Node node)
	    {
	        if (children == null) {
	            children = new NodeList();
	        }
	        if (!children.contains(node)) {
	            children.add(node);
	        }
	    }

	    public Iterator<Node> listNamedDomains()
	    {
	        return getNamedDomains().iterator();
	    }

	    public NodeList getNamedDomains()
	    {
	        return domains;
	    }

	    public void addNamedDomain(Node node)
	    {
	        if (domains == null) {
	            domains = new NodeList();
	        }
	        if (!domains.contains(node)) {
	            domains.add(node);
	        }
	    }

	    @Override
	    public String toString()
	    {
	        if (uri != null) {
	            return uri;
	        } else if (nodeType == STATEMENT) {
	            uri = statement.toString();
	        } else if (nodeType == SPO_RESOURCE) {
	            uri = resource.toString();
	        } else if (nodeType == SPO_RDFNODE) {
	            if (rdfnode.asNode().isLiteral()) {
	                uri = rdfnode.asNode().getLiteral().toString();
	            } else {
	                uri = rdfnode.toString();
	            }
	        }
	        return uri;
	    }

	    public boolean equals(Node node)
	    {
	        if (toString().equals(node.toString())) {
	            return true;
	        } else {
	            return false;
	        }
	    }

}
