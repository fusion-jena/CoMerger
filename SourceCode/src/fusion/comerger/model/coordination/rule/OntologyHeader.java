
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
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import fusion.comerger.model.coordination.Coordinator;

public class OntologyHeader implements Coordinator
{
    public Model coordinate(Model model)
    {
        ArrayList<Statement> removedStmt = new ArrayList<Statement>();

        String querystr = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                		+ " PREFIX owl: <http://www.w3.org/2002/07/owl#> " 
                		+ " SELECT ?x WHERE {?x rdf:type owl:Ontology} ";

        Query query = QueryFactory.create(querystr);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        for (Iterator<?> iter = results; iter.hasNext();) {
            QuerySolution res = (QuerySolution) iter.next();
            Resource x = (Resource) res.get("x");
            StmtIterator stmts = model.listStatements();
            while (stmts.hasNext()) {
                Statement s = stmts.nextStatement();
                if (s.getSubject().toString().equals(x.toString())) {
                    removedStmt.add(s);
                }
            }
        }

        for (int i = 0; i < removedStmt.size(); i++) {
            model.remove(removedStmt.get(i));
        }
        return model;
    }
}