
package fusion.comerger.general.gernalAnalysis.similarBlock;

public class BlockMapping
{
    private String cname1 = null;
    private String cname2 = null;
    private double similarity = 0;

    public BlockMapping(String cn1, String cn2, double sim)
    {
        cname1 = cn1;
        cname2 = cn2;
        similarity = sim;
    }

    public String getClusterName1()
    {
        return cname1;
    }

    public void setClusterName1(String cn1)
    {
        cname1 = cn1;
    }

    public String getClusterName2()
    {
        return cname2;
    }

    public void setClusterName2(String cn2)
    {
        cname2 = cn2;
    }

    public void setSimilarity(double sim)
    {
        similarity = sim;
    }

    public double getSimilarity()
    {
        return similarity;
    }
}
