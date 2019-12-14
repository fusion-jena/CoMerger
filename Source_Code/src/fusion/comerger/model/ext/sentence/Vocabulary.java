
package fusion.comerger.model.ext.sentence;

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
