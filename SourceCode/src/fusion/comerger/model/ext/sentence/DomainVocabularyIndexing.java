
package fusion.comerger.model.ext.sentence;
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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;


public class DomainVocabularyIndexing
{
    private ArrayList<String> domainVocURIs = null;
    private LinkedHashMap<String, Integer> URIToDomainVocID = null;

    public DomainVocabularyIndexing(RDFSentenceGraph sg)
    {
        domainVocURIs = new ArrayList<String>(sg.getNumberOfVocabularies());
        URIToDomainVocID = new LinkedHashMap<String, Integer>(2 * sg.getNumberOfVocabularies());
        for (Iterator<RDFSentence> i = sg.getRDFSentences().iterator(); i.hasNext();) {
            RDFSentence sentence = i.next();
            ArrayList<String> vocabularies = sentence.getDomainVocabularyURIs();
            for (Iterator<String> j = vocabularies.iterator(); j.hasNext();) {
                String vocURI = sg.getVocabulary(j.next()).getURI();
                if (!URIToDomainVocID.containsKey(vocURI)) {
                    URIToDomainVocID.put(vocURI, domainVocURIs.size());
                    domainVocURIs.add(vocURI);
                }
            }
        }
    }

    public DomainVocabularyIndexing(String indexFileURL)
    {
        try {
            BufferedReader indexFile = new BufferedReader(
            		new FileReader(indexFileURL));
            String line = indexFile.readLine();
            int size = Integer.parseInt(line.substring(line.indexOf('#') + 1));
            domainVocURIs = new ArrayList<String>(size);
            URIToDomainVocID = new LinkedHashMap<String, Integer>(size * 2);
            line = indexFile.readLine();
            while (line != null && !line.equals("")) {
                URIToDomainVocID.put(line, domainVocURIs.size());
                domainVocURIs.add(line);
                line = indexFile.readLine();
            }
            indexFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public ArrayList<String> getDomainVocabularies()
    {
        return domainVocURIs;
    }

    public int getDomainVocabularyID(String URI)
    {
        Integer ID = URIToDomainVocID.get(URI);
        if (ID != null) {
            return ID.intValue();
        }
        return -1;
    }

    public String getDomainVocabularyURI(int ID)
    {
        return domainVocURIs.get(ID);
    }

    public int getNumberOfDomainVocabularies()
    {
        return domainVocURIs.size();
    }

    public void printIndexToFile(String indexFileURL)
    {
        PrintWriter indexWriter = null;
        try {
            indexWriter = new PrintWriter(indexFileURL);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        indexWriter.println("size#" + String.valueOf(domainVocURIs.size()));
        for (int i = 0; i < domainVocURIs.size(); i++) {
            indexWriter.println(domainVocURIs.get(i));
        }
        indexWriter.close();
    }
}
