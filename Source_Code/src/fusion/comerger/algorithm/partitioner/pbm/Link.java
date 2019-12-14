package fusion.comerger.algorithm.partitioner.pbm;

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
