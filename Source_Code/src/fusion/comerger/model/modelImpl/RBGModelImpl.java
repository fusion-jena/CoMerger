package fusion.comerger.model.modelImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;

import fusion.comerger.general.cc.Parameters;
import fusion.comerger.model.Constant;
import fusion.comerger.model.Node;
import fusion.comerger.model.NodeCategory;
import fusion.comerger.model.NodeList;
import fusion.comerger.model.Quadruple;
import fusion.comerger.model.RBGModel;
import fusion.comerger.model.coordination.Coordinator;
import fusion.comerger.model.coordination.RuleList;


public class RBGModelImpl implements RBGModel
{
	private HashSet<Quadruple> quadrupleSet = null;
    //private HashMap<Object, Node> nodeSet = null;
    private LinkedHashMap<Object, Node> nodeSet = null;//samira new
    private OntModel ontModel = null;
    private RuleList rules = null;
    private int numOfType = 0;
    private boolean clrClassType = false;
    private boolean addInstType = false; // iType
    private boolean initHier = false;

    public RBGModelImpl()
    {
        OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
        ontModel = ModelFactory.createOntologyModel(spec, null);
        quadrupleSet = new HashSet<Quadruple>();
        //nodeSet = new HashMap<Object, Node>();
        nodeSet = new LinkedHashMap<Object, Node>();//samira new
    }

    public RBGModelImpl(boolean doImports)
    {
        OntDocumentManager mgr = new OntDocumentManager();
        mgr.setProcessImports(doImports);
        OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
        spec.setDocumentManager(mgr);
        ontModel = ModelFactory.createOntologyModel(spec, null);
        quadrupleSet = new HashSet<Quadruple>();
        //nodeSet = new HashMap<Object, Node>();
        nodeSet = new LinkedHashMap<Object, Node>(); //samira new
    }

    public RBGModel read(String uri)
    {
        if (!uri.startsWith("file:")) {
            uri = "file:" + uri;
        }
        ontModel.read(uri);

        coordinate();
        transformToRBG();
        layerNodes();

        if (clrClassType) {
            clearClassType();
        }
        if (addInstType) {
            addInstType();
        }
        if (initHier) {
            constructHierarchy();
        }
        return this;
    }

    public void setOntModel(OntModel model)
    {
        ontModel = model;
        clearModel();

        coordinate();
        transformToRBG();
        layerNodes();

        if (clrClassType) {
            clearClassType();
        }
        if (addInstType) {
            addInstType();
        }
        if (initHier) {
            constructHierarchy();
        }
    }

    public OntModel getOntModel()
    {
        return ontModel;
    }

    public void clearModel()
    {
        quadrupleSet.clear();
        nodeSet.clear();
    }

    public void setCoordinationRuleList(RuleList rl)
    {
        rules = rl;
    }

    private void coordinate()
    {
        if (rules != null) {
            for (int i = 0; i < rules.size(); i++) {
                Coordinator rule = rules.get(i);
                rule.coordinate(ontModel);
            }
            return;
        }
    }

    public void setInitHierarchy(boolean ih)
    {
        initHier = ih;
    }

    public void constructHierarchy()
    {
        for (Iterator<Node> iter = listStmtNodes(); iter.hasNext();) {
            Node statement = iter.next();
            String p = statement.getPredicate().toString();
            if (p.equals(Constant.RDFS_NS + "subClassOf") ||
            		p.equals(Constant.RDFS_NS + "subPropertyOf")) {
                Node subject = statement.getSubject();
                Node object = statement.getObject();
                if (!subject.isBuiltIn() && !subject.isAnon() && 
                		!object.isBuiltIn() && !object.isAnon()) {
                    subject.addNamedSuper(object);
                    object.addNamedSub(subject);
                }
            } else if (p.toString().equals(Constant.RDFS_NS + "domain")) {
                Node subject = statement.getSubject();
                Node object = statement.getObject();
                if (!subject.isBuiltIn() && !subject.isAnon() && 
                		!object.isBuiltIn() && !object.isAnon()) {
                    subject.addNamedDomain(object);
                }
            }
        }
        determineHierarchy();
    }

    private void determineHierarchy()
    { /* hierarchy = max(father) + 1 */
        ArrayList<Node> rootList = new ArrayList<Node>();
        for (Iterator<Node> iter = listNodes(); iter.hasNext();) {
            Node node = iter.next();
            if (node.getNodeType() == Node.SPO_RESOURCE) {
                if (node.getNamedSupers() == null) {
                    node.setHierarchy(1);
                    rootList.add(node);
                }
            }
        }
        while (rootList.size() != 0) {
            ArrayList<Node> tempList = new ArrayList<Node>();
            for (int i = 0, n = rootList.size(); i < n; i++) {
                Node node = rootList.get(i);
                NodeList childList = node.getNamedSubs();
                if (childList != null) {
                    for (int j = 0, m = childList.size(); j < m; j++) {
                        Node tempNode = childList.get(j);
                        if (tempNode.getHierarchy() <= node.getHierarchy()) {
                            tempNode.setHierarchy(node.getHierarchy() + 1);
                            tempList.add(tempNode);
                        }
                    }
                }
            }
            rootList.clear();
            for (int i = 0, n = tempList.size(); i < n; i++) {
                rootList.add(tempList.get(i));
            }
        }
    }

    public void transformToRBG()
    {
        StmtIterator statements = ontModel.listStatements();
        while (statements.hasNext()) {
            Statement stat = statements.nextStatement();
            // subject
            Node sNode = null;
            Resource subject = stat.getSubject();
            if (Constant.RDF_NS.equals(subject.getNameSpace()) ||
            		Constant.RDFS_NS.equals(subject.getNameSpace()) ||
            		Constant.OWL_NS.equals(subject.getNameSpace())) {
                continue;
            }
            if (nodeSet.containsKey(subject)) {
                sNode = nodeSet.get(subject);
            } else {
                sNode = new NodeImpl(subject);
                nodeSet.put(subject, sNode);
            }
            sNode.setRBGModel(this);
            // predicate
            Node pNode = null;
            Resource predicate = stat.getPredicate();
            if (predicate.toString().equals(Constant.RDFS_NS + "label")) {
                if (stat.getObject().asNode().isLiteral()) {
                    String lang = stat.getObject().asNode().getLiteral().language();
                    if (lang.equalsIgnoreCase(Parameters.lang) || lang.equals("")) {
                        sNode.addLabel(
                        		stat.getObject().asNode().getLiteral().getLexicalForm());
                    }
                }
                continue;
            } else if (predicate.toString().equals(Constant.RDFS_NS + "comment")) {
                if (stat.getObject().asNode().isLiteral()) {
                    String lang = stat.getObject().asNode().getLiteral().language();
                    if (lang.equalsIgnoreCase(Parameters.lang) || lang.equals("")) {
                        sNode.addComment(
                        		stat.getObject().asNode().getLiteral().getLexicalForm());
                    }
                }
                continue;
            }
            if (nodeSet.containsKey(predicate)) {
                pNode = nodeSet.get(predicate);
            } else {
                pNode = new NodeImpl(predicate);
                nodeSet.put(predicate, pNode);
            }
            if ((Constant.RDF_NS + "type").equals(pNode.toString())) {
                numOfType++;
            }
            pNode.setRBGModel(this);
            // object
            Node oNode = null;
            RDFNode object = stat.getObject();
            if (object.canAs(Resource.class)) {
                Resource resource = (Resource) object.as(Resource.class);
                if (nodeSet.containsKey(object)) {
                    oNode = nodeSet.get(resource);
                } else {
                    oNode = new NodeImpl(resource);
                    if (clrClassType) {
                        if ((Constant.RDF_NS + "type").equals(predicate.toString())) {
                            if (!(Constant.OWL_NS + "Restriction").equals(oNode.toString()) && 
                            		!(Constant.OWL_NS + "Class").equals(oNode.toString()) && 
                            		!(Constant.RDFS_NS + "Class").equals(oNode.toString()) && 
                            		!(Constant.OWL_NS + "Nothing").equals(oNode.toString()) &&
                            		!(Constant.OWL_NS + "Thing").equals(oNode.toString()) &&
                            		!((Constant.RDF_NS + "List").equals(oNode.toString()))) {
                                nodeSet.put(object, oNode);
                            }
                        } else {
                            nodeSet.put(object, oNode);
                        }
                    } else {
                        nodeSet.put(object, oNode);
                    }
                }
            } else {
                if (nodeSet.containsKey(object)) {
                    oNode = nodeSet.get(object);
                } else {
                    oNode = new NodeImpl(object);
                    nodeSet.put(object, oNode);
                }
                if (object.asNode().isLiteral()) {
                    oNode.setNodeLevel(Node.LANGUAGE_LEVEL);
                    oNode.setCategory(Node.LITERAL);
                }
            }
            oNode.setRBGModel(this);
            // statement
            Node statNode = new NodeImpl(stat);
            nodeSet.put(stat, statNode);
            statNode.setNodeType(Node.STATEMENT);
            statNode.setRBGModel(this);

            Quadruple q = new QuadrupleImpl(statNode, sNode, pNode, oNode);
            quadrupleSet.add(q);
        }
    }

    public void layerNodes()
    {
        Iterator<Quadruple> quadruples = listQuadruples();
        while (quadruples.hasNext()) {
            Quadruple q = quadruples.next();
            NodeImpl s = (NodeImpl) q.getSubject();
            NodeImpl p = (NodeImpl) q.getPredicate();
            NodeImpl o = (NodeImpl) q.getObject();

            setRDFSClassLevel(p);
            setOWLClassLevel(p);
            setRDFSClassLevel(o);
            setOWLClassLevel(o);

            p._as_prop = 1;
            if (p.toString().equals(Constant.RDFS_NS + "domain") || 
            		p.toString().equals(Constant.RDFS_NS + "range")) {
                s._as_prop = 1;
            } else if (p.toString().equals(Constant.RDFS_NS + "subPropertyOf") || 
            		p.toString().equals(Constant.OWL_NS + "equivalentProperty") ||
            		p.toString().equals(Constant.OWL_NS + "inverseOf")) {
                s._as_prop = 1;
                o._as_prop = 1;
            } else if (p.toString().equals(Constant.OWL_NS + "onProperty")) {
                s._as_prop = 1;
            }
        }
        
        boolean updated = true;
        int iter = 0;
        while (updated && iter < 1000) { 
            updated = false;
            iter++;
            quadruples = listQuadruples();
            while (quadruples.hasNext()) {
                Quadruple q = quadruples.next();
                NodeImpl s = (NodeImpl) q.getSubject();
                NodeImpl p = (NodeImpl) q.getPredicate();
                NodeImpl o = (NodeImpl) q.getObject();
                if (p.toString().equals(Constant.RDF_NS + "type")) {
                    if (o._p_cls == 1 && s._as_prop != 1) {
                        s._as_prop = 1;
                        updated = true;
                    }
                } else if (p.toString().equals(Constant.OWL_NS + "equivalentProperty")) {
                    if ((s._as_prop + o._as_prop) < 2) {
                        s._as_prop = 1;
                        o._as_prop = 1;
                        updated = true;
                    }
                } else if (p.toString().equals(Constant.RDFS_NS + "subClassOf")) {
                    if ((s._p_cls + o._p_cls) == 1) {
                        s._p_cls = 1;
                        o._p_cls = 1;
                        updated = true;
                    }
                } else if (p.toString().equals(Constant.OWL_NS + "sameAs")) {
                    if ((s._as_prop + o._as_prop) == 1) {
                        s._as_prop = 1;
                        o._as_prop = 1;
                        updated = true;
                    }
                    if ((s._p_cls + o._p_cls) == 1) {
                        s._p_cls = 1;
                        o._p_cls = 1;
                        updated = true;
                    }
                }
            }
        }
        
        quadruples = listQuadruples();
        while (quadruples.hasNext()) {
            Quadruple q = quadruples.next();
            NodeImpl s = (NodeImpl) q.getSubject();
            NodeImpl p = (NodeImpl) q.getPredicate();
            NodeImpl o = (NodeImpl) q.getObject();
            if (p.toString().equals(Constant.RDFS_NS + "domain") || 
            		p.toString().equals(Constant.RDFS_NS + "range")) {
                o._cls_lvl = Math.max(1, o._cls_lvl);
            }
            if (p.toString().equals(Constant.OWL_NS + "onProperty")) {
                s._cls_lvl = Math.max(1, s._cls_lvl);
            }
            if (p.toString().equals(Constant.OWL_NS + "someValuesFrom") || 
            		p.toString().equals(Constant.OWL_NS + "allValuesFrom")) {
                o._cls_lvl = Math.max(1, o._cls_lvl);
            }
            if (p.toString().equals(Constant.RDF_NS + "type")) {
                if (o._cls_lvl == 2) {
                    s._cls_lvl = Math.max(1, s._cls_lvl);
                }
            }
        }

        updated = true;
        iter = 0;
        while (updated && iter < 1000) { 
            updated = false;
            iter++;
            quadruples = listQuadruples();
            while (quadruples.hasNext()) {
                Quadruple q = quadruples.next();
                NodeImpl s = (NodeImpl) q.getSubject();
                NodeImpl p = (NodeImpl) q.getPredicate();
                NodeImpl o = (NodeImpl) q.getObject();
                if (p.toString().equals(Constant.RDFS_NS + "subClassOf")) {
                    int temp = Math.max(1, Math.max(s._cls_lvl, o._cls_lvl));
                    if (s._cls_lvl < temp) {
                        s._cls_lvl = temp;
                        updated = true;
                    }
                    if (o._cls_lvl < temp && o._built_in == 0) {
                        o._cls_lvl = s._cls_lvl;
                        updated = true;
                    }
                } else if (p.toString().equals(Constant.RDF_NS + "type")) {
                    if (s._cls_lvl < (o._cls_lvl - 1)) {
                        s._cls_lvl = o._cls_lvl - 1;
                        updated = true;
                    } else if (o._cls_lvl <= s._cls_lvl && o._built_in == 0) {
                        o._cls_lvl = s._cls_lvl + 1;
                        updated = true;
                    }
                } else if (p.toString().equals(Constant.OWL_NS + "disjointWith") || 
                		p.toString().equals(Constant.OWL_NS + "equivalentClass") || 
                		p.toString().equals(Constant.OWL_NS + "complementOf") || 
                		p.toString().equals(Constant.OWL_NS + "intersectionOf") || 
                		p.toString().equals(Constant.OWL_NS + "unionOf")) {
                    if (s._cls_lvl != o._cls_lvl || 
                    		(s._cls_lvl == o._cls_lvl && s._cls_lvl == 0)) {
                        int temp = Math.max(1, Math.max(s._cls_lvl, o._cls_lvl));
                        if (s._cls_lvl < temp) {
                            s._cls_lvl = temp;
                            updated = true;
                        }
                        if (o._cls_lvl < s._cls_lvl && o._built_in == 0) {
                            o._cls_lvl = s._cls_lvl;
                            updated = true;
                        }
                    }
                } else if (p.toString().equals(Constant.OWL_NS + "oneOf")) {
                    if (s._cls_lvl <= o._cls_lvl) {
                        s._cls_lvl = o._cls_lvl + 1;
                        updated = true;
                    } else if (o._cls_lvl < s._cls_lvl - 1) {
                        o._cls_lvl = s._cls_lvl - 1;
                        updated = true;
                    }
                } else if (p.toString().equals(Constant.OWL_NS + "sameAs")) {
                    if (s._cls_lvl != o._cls_lvl) {
                        int temp = Math.max(s._cls_lvl, o._cls_lvl);
                        if (s._cls_lvl < temp) {
                            s._cls_lvl = temp;
                            updated = true;
                        }
                        if (o._cls_lvl < s._cls_lvl && o._built_in == 0) {
                            o._cls_lvl = s._cls_lvl;
                            updated = true;
                        }
                    }
                }
            }
        }
        
        updated = true;
        iter = 0;
        while (updated && iter < 1000) {
            updated = false;
            iter++;
            quadruples = listQuadruples();
            while (quadruples.hasNext()) {
                Quadruple q = quadruples.next();
                NodeImpl s = (NodeImpl) q.getSubject();
                NodeImpl p = (NodeImpl) q.getPredicate();
                NodeImpl o = (NodeImpl) q.getObject();
                if (p._built_in > 0) {
                    if (p.toString().equals(Constant.RDFS_NS + "domain") || 
                    		p.toString().equals(Constant.RDFS_NS + "range")) {
                        if (s._as_prop < o._cls_lvl) {
                            s._as_prop = o._cls_lvl;
                            updated = true;
                        }
                    } else if (p.toString().equals(Constant.OWL_NS + "inverseOf") || 
                    		p.toString().equals(Constant.RDFS_NS + "subPropertyOf")) {
                        if (s._as_prop < o._as_prop) {
                            s._as_prop = o._as_prop;
                            updated = true;
                        } else if (o._as_prop < s._as_prop) {
                            o._as_prop = s._as_prop;
                            updated = true;
                        }
                    }
                } else {
                    if (p._as_prop < Math.max(s._cls_lvl, o._cls_lvl) + 1) {
                        p._as_prop = Math.max(s._cls_lvl, o._cls_lvl) + 1;
                        updated = true;
                    }
                }
            }
        }

        setCategoryLevel();
    }

    private void setCategoryLevel()
    {
        Iterator<Node> nodes = listNodes();
        while (nodes.hasNext()) {
            NodeImpl node = (NodeImpl) nodes.next();
            if (node._cls_lvl >= 1) {
                node.setCategory(Node.CLASS);
                node.setNodeLevel(Node.ONTOLOGY_LEVEL);
            } else if (node._as_prop >= 1) {
                node.setCategory(Node.PROPERTY);
                node.setNodeLevel(Node.ONTOLOGY_LEVEL);
            } else if (node._cls_lvl == 0 && node._as_prop == 0 && 
            		node.getNodeType() != Node.STATEMENT) {
                if (node.getNodeLevel() == Node.UNDEFINED) {
                    node.setNodeLevel(Node.INSTANCE_LEVEL);
                }
                if (node.getCategory() == Node.UNDEFINED) {
                    node.setCategory(Node.INSTANCE);
                }
            }
            if (node.getNameSpace() != null && 
            		Constant.isBuiltInNs(node.getNameSpace())) {
                node.setNodeLevel(Node.LANGUAGE_LEVEL);
            }
        }

        Iterator<Quadruple> tempQuadruples = listQuadruples();
        while (tempQuadruples.hasNext()) {
            Quadruple q = tempQuadruples.next();
            Node p = q.getPredicate();
            if (p.toString().equals(Constant.RDF_NS + "type")) {
                Node o = q.getObject();
                if (o.toString().equals(Constant.OWL_NS + "ObjectProperty")) {
                    q.getSubject().setCategory(Node.OBJECTPROPERTY);
                } else if (o.toString().equals(Constant.OWL_NS + "DatatypeProperty")) {
                    q.getSubject().setCategory(Node.DATATYPEPROPERTY);
                }
            }
        }
    }

    private void setRDFSClassLevel(NodeImpl node)
    {
        if (node.getNameSpace() != null && 
        		(node.getNameSpace().equals(Constant.RDFS_NS) || 
        				node.getNameSpace().equals(Constant.RDF_NS))) {
            String localName = node.getLocalName();
            if (localName.equals("Resource")) {
                node._built_in = 2;
                node._cls_lvl = 1;
            } else if (localName.equals("Class")) {
                node._built_in = 2;
                node._cls_lvl = 2;
            } else if (localName.equals("Property")) {
                node._built_in = 2;
                node._cls_lvl = 1;
                node._p_cls = 1;
            } else if (localName.equals("Datatype")) {
                node._built_in = 1;
                node._cls_lvl = 2;
            } else if (localName.equals("Literal")) {
                node._built_in = 1;
                node._cls_lvl = 1;
            } else if (localName.equals("subClassOf")) {
                node._built_in = 1;
                node._cls_lvl = 0;
            } else if (localName.equals("subPropertyOf")) {
                node._built_in = 1;
                node._cls_lvl = 0;
            } else if (localName.equals("domain")) {
                node._built_in = 1;
                node._cls_lvl = 0;
            } else if (localName.equals("range")) {
                node._built_in = 1;
                node._cls_lvl = 0;
            } else if (localName.equals("type")) {
                node._built_in = 1;
                node._cls_lvl = 0;
            } else if (localName.equals("first")) {
                node._built_in = 1;
                node._cls_lvl = 0;
            } else if (localName.equals("rest")) {
                node._built_in = 1;
                node._cls_lvl = 0;
            } else if (localName.equals("nil")) {
                node._built_in = 1;
                node._cls_lvl = 0;
            } else if (localName.equals("List")) {
                node._built_in = 2;
                node._cls_lvl = 1;
            } else if (localName.equals("XMLLiteral")) {
                node._built_in = 1;
                node._cls_lvl = 1;
            }
        }
    }

    private void setOWLClassLevel(NodeImpl node)
    {
        if (node.getNameSpace() != null && 
        		node.getNameSpace().equals(Constant.OWL_NS)) {
            String localName = node.getLocalName();
            /* if (localName == null || localName.equals("") || 
            		localName.charAt(0) <= 'z' && localName.charAt(0) >= 'a') {
            } */
            if (localName.equals("Class")) {
                node._built_in = 4;
                node._cls_lvl = 2;
            } else if (localName.equals("Thing")) {
                node._built_in = 4;
                node._cls_lvl = 1;
            } else if (localName.equals("Nothing")) {
                node._built_in = 3;
                node._cls_lvl = 1;
            } else if (localName.equals("Restriction")) {
                node._built_in = 4;
                node._cls_lvl = 2;
            } else if (localName.equals("ObjectProperty")) {
                node._built_in = 3;
                node._cls_lvl = 1;
                node._p_cls = 1;
            } else if (localName.equals("DatatypeProperty")) {
                node._built_in = 3;
                node._cls_lvl = 1;
                node._p_cls = 1;
            } else if (localName.equals("TransitiveProperty")) {
                node._built_in = 3;
                node._cls_lvl = 1;
                node._p_cls = 1;
            } else if (localName.equals("SymmetricProperty")) {
                node._built_in = 3;
                node._cls_lvl = 1;
                node._p_cls = 1;
            } else if (localName.equals("InverseFunctionalProperty")) {
                node._built_in = 3;
                node._cls_lvl = 1;
                node._p_cls = 1;
            } else if (localName.equals("FunctionalProperty")) {
                node._built_in = 3;
                node._cls_lvl = 1;
                node._p_cls = 1;
            } else if (localName.equals("AnnotationProperty")) {
                node._built_in = 3;
                node._cls_lvl = 1;
                node._p_cls = 1;
            } else if (localName.equals("OntologyProperty")) {
                node._built_in = 3;
                node._cls_lvl = 1;
                node._p_cls = 1;
            } else if (localName.equals("DeprecatedProperty")) {
                node._built_in = 3;
                node._cls_lvl = 1;
                node._p_cls = 1;
            } else if (localName.equals("DataRange")) {
                node._built_in = 3;
                node._cls_lvl = 2;
            } else if (localName.equals("AllDifferent")) {
                node._built_in = 4;
                node._cls_lvl = 1;
            } else if (localName.equals("DeprecatedClass")) {
                node._built_in = 4;
                node._cls_lvl = 2;
            }
        }
    }

    public Iterator<Quadruple> listQuadruples()
    {
        return quadrupleSet.iterator();
    }

    public ArrayList<Quadruple> getQuadruples()
    {
        Iterator<Quadruple> i = listQuadruples();
        ArrayList<Quadruple> quadruples = new ArrayList<Quadruple>();
        while (i.hasNext()) {
            quadruples.add(i.next());
        }
        return quadruples;
    }

    public Iterator<Node> listStmtNodes()
    {
        NodeList stmtList = getStmtNodes();
        return stmtList.iterator();
    }

    public NodeList getStmtNodes()
    {
        NodeList stmtList = new NodeList();
        Iterator<Quadruple> quadruples = quadrupleSet.iterator();
        while (quadruples.hasNext()) {
            stmtList.add(quadruples.next().getStatement());
        }
        return stmtList;
    }

    public NodeList getNodes()
    {
        NodeList nodeList = new NodeList();
        Iterator<Node> nodes = nodeSet.values().iterator();
        while (nodes.hasNext()) {
            nodeList.add(nodes.next());
        }
        return nodeList;
    }

    public Iterator<Node> listNodes()
    {
        return nodeSet.values().iterator();
    }

    public Node getNode(Object node)
    {
        if (node instanceof String) {
            return nodeSet.get(ontModel.createResource(node.toString()));
        } else if (node instanceof Node) {
            Node tempNode = (Node) node;
            return nodeSet.get(tempNode.getValue());
        }
        return nodeSet.get(node);
    }

    public String getNsPrefixURI(String prefix)
    {
        return ontModel.getNsPrefixURI(prefix);
    }

    public String getNsURIPrefix(String uri)
    {
        return ontModel.getNsURIPrefix(uri);
    }

    public Iterator<Node> listNamedClassNodes()
    {
        return getNamedClassNodes().iterator();
    }

    public NodeList getNamedClassNodes()
    {
        NodeList classList = new NodeList();
        Iterator<Node> nodeList = listNodes();
        while (nodeList.hasNext()) {
            Node node = nodeList.next();
            if (!node.isAnon() && 
            		NodeCategory.getCategoryWithoutExternal(node) == 
            			Constant.ONTOLOGY_CLASS) {
                classList.add(node);
            }
        }
        return classList;
    }

    public Iterator<Node> listClassNodes()
    {
        return getClassNodes().iterator();
    }

    public NodeList getClassNodes()
    {
        NodeList classList = new NodeList();
        Iterator<Node> nodeList = listNodes();
        while (nodeList.hasNext()) {
            Node node = nodeList.next();
            if (NodeCategory.getCategoryWithoutExternal(node) == 
            		Constant.ONTOLOGY_CLASS) {
                classList.add(node);
            }
        }
        return classList;
    }

    public Iterator<Node> listNamedInstanceNodes()
    {
        return getNamedInstanceNodes().iterator();
    }

    public NodeList getNamedInstanceNodes()
    {
        NodeList instanceList = new NodeList();
        Iterator<Node> nodeList = listNodes();
        while (nodeList.hasNext()) {
            Node node = (Node) nodeList.next();
            if (!node.isAnon() && 
            		NodeCategory.getCategoryWithoutExternal(node) == 
            			Constant.ONTOLOGY_INSTANCE) {
                instanceList.add(node);
            }
        }
        return instanceList;
    }

    public Iterator<Node> listInstanceNodes()
    {
        return getInstanceNodes().iterator();
    }

    public NodeList getInstanceNodes()
    {
        NodeList instanceList = new NodeList();
        Iterator<Node> nodeList = listNodes();
        while (nodeList.hasNext()) {
            Node node = (Node) nodeList.next();
            if (NodeCategory.getCategoryWithoutExternal(node) == 
            		Constant.ONTOLOGY_INSTANCE) {
                instanceList.add(node);
            }
        }
        return instanceList;
    }

    public Iterator<Node> listPropertyNodes()
    {
        return getPropertyNodes().iterator();
    }

    public NodeList getPropertyNodes()
    {
        NodeList propertyList = new NodeList();
        Iterator<Node> nodeList = listNodes();
        while (nodeList.hasNext()) {
            Node node = (Node) nodeList.next();
            if (NodeCategory.getCategoryWithoutExternal(node) == 
            		Constant.ONTOLOGY_PROPERTY) {
                propertyList.add(node);
            }
        }
        return propertyList;
    }

    public Iterator<Node> listLanguageLevel()
    {
        NodeList list = new NodeList();
        Iterator<Node> nodeList = listNodes();
        while (nodeList.hasNext()) {
            Node node = (Node) nodeList.next();
            if (node.getNodeLevel() == Node.LANGUAGE_LEVEL) {
                list.add(node);
            }
        }
        return list.iterator();
    }

    public Iterator<Node> listOntologyLevel()
    {
        NodeList list = new NodeList();
        Iterator<Node> nodeList = listNodes();
        while (nodeList.hasNext()) {
            Node node = (Node) nodeList.next();
            if (node.getNodeLevel() == Node.ONTOLOGY_LEVEL) {
                list.add(node);
            }
        }
        return list.iterator();
    }

    public Iterator<Node> listInstanceLevel()
    {
        NodeList list = new NodeList();
        Iterator<Node> nodeList = listNodes();
        while (nodeList.hasNext()) {
            Node node = (Node) nodeList.next();
            if (node.getNodeLevel() == Node.INSTANCE_LEVEL) {
                list.add(node);
            }
        }
        return list.iterator();
    }

    public Iterator<Node> listLiteralNodes()
    {
        NodeList list = new NodeList();
        Iterator<Node> nodeList = listNodes();
        while (nodeList.hasNext()) {
            Node node = (Node) nodeList.next();
            if (node.getCategory() == Node.LITERAL) {
                list.add(node);
            }
        }
        return list.iterator();
    }

    public void setAddInstType(boolean ait)
    {
        addInstType = ait;
    }

    private void addInstType()
    {
        Iterator<Quadruple> quadruples = listQuadruples();
        while (quadruples.hasNext()) {
            Quadruple q = quadruples.next();
            if (q.getPredicate().toString().equals(Constant.RDF_NS + "type")) {
                if (NodeCategory.getCategoryWithoutExternal(q.getObject()) == 
                		Constant.ONTOLOGY_CLASS) {
                    Property p = ontModel.createProperty(Constant.RDF_NS + "iType");
                    NodeImpl iTypeNode = null;
                    if (!nodeSet.containsKey(p)) {
                        iTypeNode = new NodeImpl(p);
                        iTypeNode.setCategory(Node.PROPERTY);
                        iTypeNode.setNodeLevel(Node.LANGUAGE_LEVEL);
                        nodeSet.put(p, iTypeNode);
                    } else {
                        iTypeNode = (NodeImpl) nodeSet.get(p);
                    }
                    q.setPredicate(iTypeNode);
                }
            }
        }
    }

    public void setClearClassType(boolean cct)
    {
        clrClassType = cct;
    }

    public void clearClassType()
    {
        ArrayList<Quadruple> removedQuadruples = new ArrayList<Quadruple>();
        Iterator<Quadruple> quadruples = listQuadruples();
        while (quadruples.hasNext()) {
            Quadruple q = quadruples.next();
            if (q.getPredicate().toString().equals(Constant.RDF_NS + "type")) {
                NodeImpl object = (NodeImpl) q.getObject();
                if (object.toString().equals(Constant.OWL_NS + "Class") || 
                		object.toString().equals(Constant.OWL_NS + "Restriction") ||
                		object.toString().equals(Constant.RDFS_NS + "Class") ||
                		object.toString().equals(Constant.OWL_NS + "Thing") ||
                		object.toString().equals(Constant.OWL_NS + "Nothing") || 
                		object.toString().equals(Constant.RDF_NS + "List")) {
                    removedQuadruples.add(q);
                    numOfType--;
                }
            }
        }
        if (numOfType < 1) {
            nodeSet.remove(RDF.type);
        }
        for (int i = 0; i < removedQuadruples.size(); i++) {
            Node node = removedQuadruples.get(i).getStatement();
            nodeSet.remove(node.getValue());
            quadrupleSet.remove(removedQuadruples.get(i));
        }
    }
}
