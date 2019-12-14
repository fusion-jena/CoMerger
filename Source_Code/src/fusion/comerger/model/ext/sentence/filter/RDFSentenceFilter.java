
package fusion.comerger.model.ext.sentence.filter;

import java.util.ArrayList;

import fusion.comerger.model.ext.sentence.RDFSentence;

public interface RDFSentenceFilter
{
    public ArrayList<RDFSentence> filter(ArrayList<RDFSentence> sentences);
}
