
package fusion.comerger.model.ext.sentence.filter;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.ArrayList;
import java.util.Iterator;

import fusion.comerger.model.ext.sentence.RDFSentence;

public class OntologyHeaderFilter implements RDFSentenceFilter
{
    private ArrayList<String> ontologyURIs = null;

    public OntologyHeaderFilter(ArrayList<String> uris)
    {
        ontologyURIs = uris;
    }

    public ArrayList<RDFSentence> filter(ArrayList<RDFSentence> sentences)
    {
        ArrayList<RDFSentence> result = new ArrayList<RDFSentence>(sentences.size());
        nextSentence:
        for (Iterator<RDFSentence> i = sentences.iterator(); i.hasNext();) {
            RDFSentence sentence = i.next();
            ArrayList<String> subjectURIs = sentence.getSubjectDomainVocabularyURIs();
            for (Iterator<String> j = subjectURIs.iterator(); j.hasNext();) {
                if (ontologyURIs.contains(j.next())) {
                    continue nextSentence;
                }
            }
            result.add(sentence);
        }
        return result;
    }
}
