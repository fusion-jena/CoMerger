
package fusion.comerger.model.ext.sentence.recognition;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.jena.rdf.model.Statement;

public interface IndividualRecognition
{
    public Iterator<String> getIndividualURIs(ArrayList<Statement> statements);
}
