
package fusion.comerger.model.ext.sentence.filter;
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
