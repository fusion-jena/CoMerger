
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
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;

import fusion.comerger.model.coordination.Coordinator;

import java.util.ArrayList;

public class Deprecated implements Coordinator
{
    // owl:DeprecatedClass, owl:DeprecatedProperty
    public Model coordinate(Model model)
    {
        ArrayList<Statement> removedStmts = new ArrayList<Statement>();
        ArrayList<Statement> addedStmts = new ArrayList<Statement>();

        StmtIterator deprecatedClass = model.listStatements(
        		(Resource) null, RDF.type, OWL.DeprecatedClass);
        while (deprecatedClass.hasNext()) {
            Statement stmt = deprecatedClass.nextStatement();
            removedStmts.add(stmt);
            addedStmts.add(model.createStatement(
            		stmt.getSubject(), RDF.type, OWL.Class));
        }
        StmtIterator deprecatedProperty = model.listStatements(
        		(Resource) null, RDF.type, OWL.DeprecatedProperty);
        while (deprecatedProperty.hasNext()) {
            Statement stmt = deprecatedProperty.nextStatement();
            removedStmts.add(stmt);
            addedStmts.add(model.createStatement(
            		stmt.getSubject(), RDF.type, OWL.ObjectProperty));
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
