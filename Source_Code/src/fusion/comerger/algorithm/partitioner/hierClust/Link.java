package fusion.comerger.algorithm.partitioner.hierClust;


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
