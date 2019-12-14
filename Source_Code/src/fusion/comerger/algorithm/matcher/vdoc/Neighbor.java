
package fusion.comerger.algorithm.matcher.vdoc;

import java.util.HashMap;

public class Neighbor
{
    private String uri = "";
    private HashMap<String, TokenNode> subjects = new HashMap<String, TokenNode>();
    private HashMap<String, TokenNode> predicates = new HashMap<String, TokenNode>();
    private HashMap<String, TokenNode> objects = new HashMap<String, TokenNode>();

    public Neighbor(String u)
    {
        uri = u;
    }

    public void setUri(String u)
    {
        uri = u;
    }

    public void addSubject(TokenNode node)
    {
        subjects.put(node.getURI(), node);
    }

    public void addPredicate(TokenNode node)
    {
        predicates.put(node.getURI(), node);
    }

    public void addObject(TokenNode node)
    {
        objects.put(node.getURI(), node);
    }

    public String getURI()
    {
        return uri;
    }

    public HashMap<String, TokenNode> getSubjects()
    {
        return subjects;
    }

    public HashMap<String, TokenNode> getPredicates()
    {
        return predicates;
    }

    public HashMap<String, TokenNode> getObjects()
    {
        return objects;
    }
}
