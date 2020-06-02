
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
import java.util.ArrayList;
import java.util.Iterator;


import org.apache.jena.rdf.model.Statement;

public class Vocabulary
{
    /* Reserved */
    public static final int Literal = -1;
    public static final int AnonymousResource = 0;
    public static final int DomainVocabulary = 1;
    public static final int BuiltinVocabulary = 2;
    private String URI = null;
    private int ID;
    private ArrayList<Statement> literalStatements = null;
    private int type;
    private boolean isConcept;

    public Vocabulary(String uri, int id)
    {
        URI = uri;
        ID = id;
        type = Integer.MIN_VALUE;
        isConcept = true;
    }

    public void addLiteralStatement(Statement statement)
    {
        if (literalStatements == null) {
            literalStatements = new ArrayList<Statement>(1);
        }
        literalStatements.add(statement);
    }

    public ArrayList<String> getAttributes()
    {
        if (literalStatements == null) {
            return new ArrayList<String>(1);
        }
        ArrayList<String> attributes = new ArrayList<String>(literalStatements.size());
        for (Iterator<Statement> i = literalStatements.iterator(); i.hasNext();) {
            String attr = i.next().getPredicate().toString();
            if (!attributes.contains(attr)) {
                attributes.add(attr);
            }
        }
        return attributes;
    }

    public ArrayList<String> getValues(String attributeURI)
    {
        if (literalStatements == null) {
            return new ArrayList<String>(1);
        }
        ArrayList<String> values = new ArrayList<String>(1);
        for (Iterator<Statement> i = literalStatements.iterator(); i.hasNext();) {
            Statement statement = i.next();
            if (attributeURI.equals(statement.getPredicate().toString())) {
                values.add(statement.getLiteral().getLexicalForm());
            }
        }
        return values;
    }

    public String getURI()
    {
        return URI;
    }

    public int getID()
    {
        return ID;
    }

    public void setType(int t)
    {
        type = t;
    }

    public void setIndividual()
    {
        isConcept = false;
    }

    public boolean isConcept()
    {
        return isConcept;
    }

    public boolean isLiteral()
    {
        return type == Vocabulary.Literal;
    }

    public boolean isAnonymous()
    {
        return type == Vocabulary.AnonymousResource;
    }

    public boolean isURIResource()
    {
        return (type == Vocabulary.DomainVocabulary || 
        		type == Vocabulary.BuiltinVocabulary);
    }

    public boolean isDomainVocabulary()
    {
        return type == Vocabulary.DomainVocabulary;
    }

    public boolean isBuiltinVocabulary()
    {
        return type == Vocabulary.BuiltinVocabulary;
    }
}
