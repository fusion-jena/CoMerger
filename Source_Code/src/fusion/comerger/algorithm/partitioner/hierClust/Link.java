package fusion.comerger.algorithm.partitioner.hierClust;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/


import org.semanticweb.owlapi.model.OWLClass;

//import opt.general.model.impl.NodeImpl;



public class Link
{
    private OWLClass node1 = null,  node2 = null;
    private double similarity = 0;

    public Link(OWLClass ver1, OWLClass ver2, double similarity)
    {
        this.node1 = ver1;
        this.node2 = ver2;
        this.similarity = similarity;
    }

    public OWLClass getVertex1()
    {
        return node1;
    }

    public OWLClass getVertex2()
    {
        return node2;
    }

    public double getSimilarity()
    {
        return similarity;
    }

    public void setSimilarity(double similarity)
    {
        this.similarity = similarity;
    }
}
