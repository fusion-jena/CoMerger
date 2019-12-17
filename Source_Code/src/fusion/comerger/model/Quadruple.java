package fusion.comerger.model;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/


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
