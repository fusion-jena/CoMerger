
package fusion.comerger.model.coordination.rule;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import fusion.comerger.model.coordination.rule.Equivalent;

import java.util.ArrayList;
import java.util.Iterator;


public class EquivalentClass extends Equivalent
{
    private ArrayList<SameNode> allEquivalent = null;
    private int index = 0;

    public void addEquivalent(Object a, Object b)
    {
        SameNode aNode = null;
        SameNode bNode = null;

        if (allValues.containsKey(a)) {
            aNode = allValues.get(a);
        } else {
            aNode = new SameNode(a);
            allValues.put((Resource) a, (SameNode) aNode);
        }
        if (allValues.containsKey(b)) {
            bNode = allValues.get(b);
        } else {
            bNode = new SameNode(b);
            allValues.put((Resource) b, (SameNode) bNode);
        }

        if (aNode.setNum < bNode.setNum) {
            if (bNode.setNum == SameNode.MaxInteger) {
                bNode.setNum = aNode.setNum;
                return;
            } else {
                setAllEquivalent(bNode.setNum, aNode.setNum);
            }
        } else if (bNode.setNum < aNode.setNum) {
            if (aNode.setNum == SameNode.MaxInteger) {
                aNode.setNum = bNode.setNum;
                return;
            } else {
                setAllEquivalent(aNode.setNum, bNode.setNum);
            }
        } else if (aNode.setNum == SameNode.MaxInteger) {
            index++;
            aNode.setNum = index;
            bNode.setNum = index;
        }
    }

    private void setAllEquivalent(int oldNum, int newNum)
    {
        Iterator<SameNode> nodeList = allValues.values().iterator();
        while (nodeList.hasNext()) {
            SameNode node = nodeList.next();
            if (node.setNum == oldNum) {
                node.setNum = newNum;
            }
        }
    }

    public ArrayList<SameNode> getAllEquivalent()
    {
        if (allEquivalent == null) {
            allEquivalent = new ArrayList<SameNode>();
            Iterator<SameNode> nodeList = allValues.values().iterator();
            while (nodeList.hasNext()) {
                allEquivalent.add(nodeList.next());
            }
        }
        return allEquivalent;
    }

    public Object getEquivalent(Object left)
    {
        if (allValues.containsKey(left)) {
            getAllEquivalent();
            for (int i = 0; i < allEquivalent.size(); i++) {
                SameNode node = (SameNode) allEquivalent.get(i);
                if (node.setNum == allValues.get(left).setNum) {
                    return node.value;
                }
            }
        }
        return left;
    }

    public boolean isEquivalent(Object o)
    {
        return allValues.containsKey(o);
    }

    public Model coordinate(Model model)
    {
        ArrayList<Statement> removedStmts = new ArrayList<Statement>();
        ArrayList<Statement> addedStmts = new ArrayList<Statement>();

        StmtIterator stmts = model.listStatements(
        		(Resource) null, OWL.equivalentClass, (Resource) null);
        while (stmts.hasNext()) {
            Statement statement = stmts.nextStatement();
            addEquivalent(statement.getSubject(), statement.getObject());
            removedStmts.add(statement);
        }
        StmtIterator statements = model.listStatements();
        while (statements.hasNext()) {
            Statement statement = statements.nextStatement();
            Resource subject = statement.getSubject();
            Property predicate = statement.getPredicate();
            RDFNode object = statement.getObject();
            if (isEquivalent(subject)) {
                if (!predicate.equals(RDF.type) && 
                		!predicate.equals(OWL.equivalentClass)) {
                    removedStmts.add(statement);
                    addedStmts.add(model.createStatement(
                            (Resource) getEquivalent(subject), predicate, object));
                }
            } else if (isEquivalent(object)) {
                removedStmts.add(statement);
                addedStmts.add(model.createStatement(
                		subject, predicate, (RDFNode) getEquivalent(object)));
            }
        }

        for (int i = 0; i < removedStmts.size(); i++) {
            model.remove(removedStmts.get(i));
        }
        for (int i = 0; i < addedStmts.size(); i++) {
            model.add(addedStmts.get(i));
        }
        return model;
    }
}
