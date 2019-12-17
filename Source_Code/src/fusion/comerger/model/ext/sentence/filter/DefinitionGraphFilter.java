
package fusion.comerger.model.ext.sentence.filter;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import fusion.comerger.model.ext.sentence.RDFSentence;

public class DefinitionGraphFilter implements RDFSentenceFilter
{
    public ArrayList<RDFSentence> filter(ArrayList<RDFSentence> sentences)
    {
        LinkedHashMap<String, RDFSentence> sURIToSentences = 
        	new LinkedHashMap<String, RDFSentence>(sentences.size());
        int bNodeCount = 0;
        nextSentence:
        for (Iterator<RDFSentence> i = sentences.iterator(); i.hasNext();) {
            RDFSentence sentence = i.next();
            Iterator<String> j = sentence.getSubjectDomainVocabularyURIs().iterator();
            if (!j.hasNext()) {
                sURIToSentences.put("DefB%" + bNodeCount, sentence);
                bNodeCount++;
                continue nextSentence;
            }
            String firstURI = j.next();
            RDFSentence first = sURIToSentences.get(firstURI);
            if (first == null) {
                sURIToSentences.put(firstURI, sentence);
                first = sentence;
            } else {
                first.addStatements(sentence.getStatements());
            }
            while (j.hasNext()) {
                String uri = j.next();
                RDFSentence current = sURIToSentences.get(uri);
                if (current == null) {
                    sURIToSentences.put(uri, first);
                } else if (current != first) {
                    first.addStatements(current.getStatements());
                    sURIToSentences.put(uri, first);
                }
            }
        }
        ArrayList<RDFSentence> result = new ArrayList<RDFSentence>(sURIToSentences.values());
        sURIToSentences = null;
        return result;
    }
}
