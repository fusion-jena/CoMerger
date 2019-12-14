
package fusion.comerger.model.ext.sentence.filter;

import java.util.ArrayList;
import java.util.Iterator;

import fusion.comerger.model.ext.sentence.RDFSentence;
import fusion.comerger.model.ext.sentence.RDFSentenceGraph;
import fusion.comerger.model.ext.sentence.Vocabulary;
import fusion.comerger.model.ext.sentence.filter.RDFSentenceFilter;
public class InstanceLevelFilter implements RDFSentenceFilter
{
    private RDFSentenceGraph graph = null;

    public InstanceLevelFilter(RDFSentenceGraph g)
    {
        graph = g;
    }

    public ArrayList<RDFSentence> filter(ArrayList<RDFSentence> sentences)
    {
        ArrayList<RDFSentence> result = new ArrayList<RDFSentence>(sentences.size());
        nextSentence:
        for (Iterator<RDFSentence> i = sentences.iterator(); i.hasNext();) {
            RDFSentence sentence = i.next();
            ArrayList<String> uris = sentence.getAllURIs();
            for (Iterator<String> j = uris.iterator(); j.hasNext();) {
                Vocabulary vocabulary = graph.getVocabulary(j.next());
                if (!vocabulary.isConcept()) {
                    continue nextSentence;
                }
            }
            result.add(sentence);
        }
        return result;
    }
}
