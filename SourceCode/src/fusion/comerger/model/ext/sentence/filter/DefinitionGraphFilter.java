
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
