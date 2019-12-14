
package fusion.comerger.model.ext.sentence.recognition;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.jena.rdf.model.Statement;

public interface IndividualRecognition
{
    public Iterator<String> getIndividualURIs(ArrayList<Statement> statements);
}
