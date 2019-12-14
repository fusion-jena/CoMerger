package fusion.comerger.model;



public interface Quadruple {
	public Node getStatement();

    public Node getSubject();

    public Node getPredicate();

    public Node getObject();

    public void setStatement(Node statement);

    public void setSubject(Node subject);

    public void setPredicate(Node predicate);

    public void setObject(Node object);

}
