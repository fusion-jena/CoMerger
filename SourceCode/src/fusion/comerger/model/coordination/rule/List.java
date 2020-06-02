
package fusion.comerger.model.coordination.rule;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
/*import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;*/







import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import fusion.comerger.model.Constant;
import fusion.comerger.model.coordination.Coordinator;
import fusion.comerger.model.coordination.rule.BinaryTreeNode;

public class List implements Coordinator
{
    public Model coordinate(Model model)
    {
        ArrayList<Statement> removedStmts = new ArrayList<Statement>();
        ArrayList<Statement> addedStmts = new ArrayList<Statement>();

        //HashMap<Resource, BinaryTreeNode> lists = new HashMap<Resource, BinaryTreeNode>();
        LinkedHashMap<Resource, BinaryTreeNode> lists = new LinkedHashMap<Resource, BinaryTreeNode>(); // new samira

        String querystr = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " 
                		+ " SELECT ?x ?y ?z ?type WHERE " 
                		+ " {?x rdf:type ?type. ?x rdf:first ?y. ?x rdf:rest ?z.} ";

        Query query = QueryFactory.create(querystr);
        QueryExecution qe = QueryExecutionFactory.create(query,  model);
        ResultSet results = qe.execSelect();

        for (Iterator<?> iter = results; iter.hasNext();) {
            QuerySolution res = (QuerySolution) iter.next();
            Resource x = (Resource) res.get("x");
            RDFNode y = (RDFNode) res.get("y");
            Resource z = (Resource) res.get("z");
            Resource nodeType = (Resource) res.get("type");
            removedStmts.add(model.createStatement(x, RDF.type, nodeType));
            removedStmts.add(model.createStatement(x, RDF.first, y));
            removedStmts.add(model.createStatement(x, RDF.rest, z));

            BinaryTreeNode tempX = null;
            BinaryTreeNode tempZ = null;

            if (!lists.containsKey(x)) {
                tempX = new BinaryTreeNode(x);
                lists.put(x, tempX);
            } else {
                tempX = lists.get(x);
            }
            tempX.setRight(y);
            tempX.setNodeType(nodeType);
            if (z.toString().equals(Constant.RDF_NS + "nil")) {
                tempX.setLeft(null);
            } else {
                if (!lists.containsKey(z)) {
                    tempZ = new BinaryTreeNode(z);
                    lists.put(z, tempZ);
                } else {
                    tempZ = lists.get(z);
                }
                tempX.setLeft(tempZ);
                tempZ.setFather(tempX);
            }
        }
        Iterator<BinaryTreeNode> listNodes = lists.values().iterator();
        while (listNodes.hasNext()) {
            BinaryTreeNode node = listNodes.next();
            if (node.getLeft() == null) {
                ArrayList<RDFNode> members = new ArrayList<RDFNode>();
                members.add((RDFNode) node.getRight());
                while (node.getFather() != null) {
                    node = node.getFather();
                    members.add((RDFNode) node.getRight());
                }
                for (int i = 0; i < members.size(); i++) {
                    addedStmts.add(model.createStatement((Resource) node.getValue(), 
                            RDFS.member, (RDFNode) members.get(i)));
                }
                if (!node.getNodeType().toString().equals(Constant.RDF_NS + "List")) {
                    addedStmts.add(model.createStatement((Resource) node.getValue(),
                            RDF.type, (Resource) node.getNodeType()));
                }
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

    public Model coordinate2(Model model)
    {
        ArrayList<Statement> removedStmts = new ArrayList<Statement>();
        ArrayList<Statement> addedStmts = new ArrayList<Statement>();

        HashMap<Resource, BinaryTreeNode> lists = new HashMap<Resource, BinaryTreeNode>();

        String querystr = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                		+ " SELECT ?x ?y ?z ?type WHERE " 
                		+ " {?x rdf:type ?type. ?x rdf:first ?y. ?x rdf:rest ?z.} ";

        Query query = QueryFactory.create(querystr);
        QueryExecution qe = QueryExecutionFactory.create(query,  model);
        ResultSet results = qe.execSelect();

        for (Iterator<?> iter = results; iter.hasNext();) {
            QuerySolution res = (QuerySolution) iter.next();
            Resource x = (Resource) res.get("x");
            RDFNode y = (RDFNode) res.get("y");
            Resource z = (Resource) res.get("z");
            removedStmts.add(model.createStatement(x, RDF.first, y));
            removedStmts.add(model.createStatement(x, RDF.rest, z));

            BinaryTreeNode tempX = null;
            BinaryTreeNode tempZ = null;

            if (!lists.containsKey(x)) {
                tempX = new BinaryTreeNode(x);
                lists.put(x, tempX);
            } else {
                tempX = lists.get(x);
            }
            tempX.setRight(y);
            tempX.setNodeType(null);
            if (z.toString().equals(Constant.RDF_NS + "nil")) {
                tempX.setLeft(null);
            } else {
                if (!lists.containsKey(z)) {
                    tempZ = new BinaryTreeNode(z);
                    lists.put(z, tempZ);
                } else {
                    tempZ = lists.get(z);
                }
                tempX.setLeft(tempZ);
                tempZ.setFather(tempX);
            }
        }
        Iterator<BinaryTreeNode> listNodes = lists.values().iterator();
        while (listNodes.hasNext()) {
            BinaryTreeNode node = listNodes.next();
            if (node.getLeft() == null) {
                ArrayList<RDFNode> members = new ArrayList<RDFNode>();
                members.add((RDFNode) node.getRight());
                while (node.getFather() != null) {
                    node = node.getFather();
                    members.add((RDFNode) node.getRight());
                }
                for (int i = 0; i < members.size(); i++) {
                    addedStmts.add(model.createStatement((Resource) node.getValue(), 
                            RDFS.member, (RDFNode) members.get(i)));
                }
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

class BinaryTreeNode
{

    private BinaryTreeNode father = null;
    private BinaryTreeNode left = null;
    private Object right = null;
    private boolean isLeaf = false;
    private Object value;
    private Object nodeType;

    public BinaryTreeNode(Object v)
    {
        value = v;
    }

    public BinaryTreeNode getFather()
    {
        return father;
    }

    public BinaryTreeNode getLeft()
    {
        return left;
    }

    public Object getRight()
    {
        return right;
    }

    public boolean isLeaf()
    {
        return isLeaf;
    }

    public Object getValue()
    {
        return value;
    }

    public Object getNodeType()
    {
        return nodeType;
    }

    public void setFather(BinaryTreeNode node)
    {
        father = node;
    }

    public void setLeft(BinaryTreeNode node)
    {
        left = node;
    }

    public void setRight(Object node)
    {
        right = node;
    }

    public void setIsLeaf(boolean leaf)
    {
        isLeaf = leaf;
    }

    public void setValue(Object v)
    {
        value = v;
    }

    public void setNodeType(Object nt)
    {
        nodeType = nt;
    }
}
