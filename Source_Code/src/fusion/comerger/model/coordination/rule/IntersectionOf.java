
package fusion.comerger.model.coordination.rule;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import fusion.comerger.model.coordination.Coordinator;

import java.util.ArrayList;
import java.util.Iterator;

public class IntersectionOf implements Coordinator
{
    /*
     * ( x, owl:intersectionOf, _:1 ), 
     * ( _:1, rdf:first, y ), ( _:1, rdf:rest, _:2 ), 
     * ( _:2, rdf:first, z ), ( _:2, rdf:rest, rdf:nil ). 
     * -> 
     * ( x, rdf:type, owl:Class ), 
     * ( x rdfs:subClassOf, y ), ( x, rdfs:subClassOf, z ).
     */

    public Model coordinate(Model model)
    {
        ArrayList<Statement> removedStmt = new ArrayList<Statement>();
        ArrayList<Statement> addedStmt = new ArrayList<Statement>();

        String querystr = " PREFIX owl: <http://www.w3.org/2002/07/owl#> "
                		+ " SELECT ?x ?y WHERE {?x owl:intersectionOf ?y}";

        Query query = QueryFactory.create(querystr);
        QueryExecution qe = QueryExecutionFactory.create(query,  model);
        ResultSet results = qe.execSelect();

        for (Iterator<?> iter = results; iter.hasNext();) {
            QuerySolution res = (QuerySolution) iter.next();
            Resource x = (Resource) res.get("x");
            Resource y = (Resource) res.get("y");
            removedStmt.add(model.createStatement(x, OWL.intersectionOf, y));
            addedStmt.add(model.createStatement(x, RDF.type, OWL.Class));
            Resource rest = y;
            Resource first = null;
            Resource tempRest = y;
            boolean ended = false;
            while (!ended) {
                first = model.getProperty(rest, RDF.first).getResource();
                tempRest = model.getProperty(rest, RDF.rest).getResource();
                if (first == null || rest == null) {
                    ended = true;
                    continue;
                }
                removedStmt.add(model.createStatement(rest, RDF.first, first));
                removedStmt.add(model.createStatement(rest, RDF.rest, tempRest));
                if (!first.equals(RDF.nil)) {
                    addedStmt.add(model.createStatement(x, RDFS.subClassOf, first));
                }
                rest = tempRest;
                if (rest.equals(RDF.nil)) {
                    ended = true;
                }
            }
        }

        for (int i = 0; i < removedStmt.size(); i++) {
            model.remove(removedStmt.get(i));
        }
        for (int i = 0; i < addedStmt.size(); i++) {
            model.add(addedStmt.get(i));
        }
        return model;
    }
}
