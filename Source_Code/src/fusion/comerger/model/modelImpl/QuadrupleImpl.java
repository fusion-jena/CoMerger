package fusion.comerger.model.modelImpl;

import fusion.comerger.model.Node;
import fusion.comerger.model.Quadruple;



public class QuadrupleImpl  implements Quadruple
{
	private Node statement = null;
    private Node subject = null;
    private Node predicate = null;
    private Node object = null;

    public QuadrupleImpl(Node stat, Node s, Node p, Node o)
    {
        statement = stat;
        subject = s;
        predicate = p;
        object = o;
    }

    public Node getStatement()
    {
        return statement;
    }

    public Node getSubject()
    {
        return subject;
    }

    public Node getPredicate()
    {
        return predicate;
    }

    public Node getObject()
    {
        return object;
    }

    public void setStatement(Node stat)
    {
        statement = stat;
    }

    public void setSubject(Node s)
    {
        subject = s;
    }

    public void setPredicate(Node p)
    {
        predicate = p;
    }

    public void setObject(Node o)
    {
        object = o;
    }

    public String toString()
    {
        return statement.toString();
    }

}
