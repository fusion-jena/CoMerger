package fusion.comerger.algorithm.partitioner.pbm;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

public class Link
{
    private String uri1 = null,  uri2 = null;
    private double similarity = 0;

    public Link(String uri1, String uri2, double similarity)
    {
        this.uri1 = uri1;
        this.uri2 = uri2;
        this.similarity = similarity;
    }

    public String getURI1()
    {
        return uri1;
    }

    public String getURI2()
    {
        return uri2;
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
