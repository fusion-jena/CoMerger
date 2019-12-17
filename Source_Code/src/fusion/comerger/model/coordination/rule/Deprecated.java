
package fusion.comerger.model.coordination.rule;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
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
