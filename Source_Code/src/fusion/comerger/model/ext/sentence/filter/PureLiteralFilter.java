
package fusion.comerger.model.ext.sentence.filter;

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
