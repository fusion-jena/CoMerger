
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
import java.util.ArrayList;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import fusion.comerger.model.Constant;
import fusion.comerger.model.coordination.Coordinator;

public class Redefinition implements Coordinator
{
    public Model coordinate(Model model)
    {
        ArrayList<Statement> removedStmt = new ArrayList<Statement>();

        StmtIterator stmts = model.listStatements();
        while (stmts.hasNext()) {
            Statement stmt = stmts.nextStatement();
            Resource subject = stmt.getSubject();
            if (Constant.RDF_NS.equals(subject.getNameSpace()) ||
            		Constant.RDFS_NS.equals(subject.getNameSpace()) || 
            		Constant.OWL_NS.equals(subject.getNameSpace())) {
                removedStmt.add(stmt);
            }
        }

        for (int i = 0; i < removedStmt.size(); i++) {
            model.remove(removedStmt.get(i));
        }
        return model;
    }
}