
package fusion.comerger.general.output;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

public class ResultData
{
    private int found = 0;
    private int exist = 0;
    private int correct = 0;
    
    private double precision = 0;
    private double recall = 0;
    private double fmeasure = 0;
    
    private Alignment errorAlignment = null;
    private Alignment correctAlignment = null;
    private Alignment lostAlignment = null;

    public Alignment getCorrectAlignment()
    {
        return correctAlignment;
    }

    public void setCorrectAlignment(Alignment ca)
    {
        correctAlignment = ca;
    }

    public Alignment getLostAlignment()
    {
        return lostAlignment;
    }

    public void setLostAlignment(Alignment la)
    {
        lostAlignment = la;
    }

    public Alignment getErrorAlignment()
    {
        return errorAlignment;
    }

    public void setErrorAlignment(Alignment ea)
    {
        errorAlignment = ea;
    }

    public int getCorrect()
    {
        return correct;
    }

    public void setCorrect(int c)
    {
        correct = c;
    }

    public int getFound()
    {
        return found;
    }

    public void setFound(int f)
    {
        found = f;
    }

    public int getExist()
    {
        return exist;
    }

    public void setExist(int e)
    {
        exist = e;
    }

    public double getFMeasure()
    {
        return fmeasure;
    }

    public void setFMeasure(double fm)
    {
        fmeasure = fm;
    }

    public double getPrecision()
    {
        return precision;
    }

    public void setPrecision(double prec)
    {
        precision = prec;
    }

    public double getRecall()
    {
        return recall;
    }

    public void setRecall(double rec)
    {
        recall = rec;
    }
}
