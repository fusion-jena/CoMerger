
package fusion.comerger.model.ext.sentence.filter;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.ArrayList;
import java.util.Iterator;

import fusion.comerger.model.ext.sentence.RDFSentence;

public class PureLiteralFilter implements RDFSentenceFilter
{
    public ArrayList<RDFSentence> filter(ArrayList<RDFSentence> sentences)
    {
        ArrayList<RDFSentence> result = new ArrayList<RDFSentence>(sentences.size());
        nextSentence:
        for (Iterator<RDFSentence> i = sentences.iterator(); i.hasNext();) {
            RDFSentence sentence = i.next();
            if ((sentence.getStatements().size() == 1) && 
            		(sentence.getStatements().get(0).getObject().isLiteral())) {
                continue nextSentence;
            }
            result.add(sentence);
        }
        return result;
    }
}
