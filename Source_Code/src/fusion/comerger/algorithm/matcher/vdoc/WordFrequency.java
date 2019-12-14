
package fusion.comerger.algorithm.matcher.vdoc;

public class WordFrequency
{
    private String word = "";
    private double frequency = 0;

    public WordFrequency(String w, double f)
    {
        if (w != null) {
            word = w;
        }
        if (f > 0) {
            frequency = f;
        }
    }

    public void setWord(String w)
    {
        if (w != null) {
            word = w;
        }
    }

    public void setFrequency(double f)
    {
        if (f > 0) {
            frequency = f;
        }
    }

    public String getWord()
    {
        return word;
    }

    public double getFrequency()
    {
        return frequency;
    }
}
