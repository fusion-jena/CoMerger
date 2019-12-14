
package fusion.comerger.algorithm.matcher.vdoc;

public class Parameters
{
    public final static boolean inclBlankNode = true;
    public final static boolean inclNeighbor = true;
    public final static boolean inclInstMatch = true;
    
    public static double localnameWeight = 1.0;
    public static double labelWeight = 0.5;
    public static double commentWeight = 0.25;
    
    public final static double initBlankNode = 1;
    public final static double iterValue = 2;
    
    public static double basicWeight = 1.0;
    public static double subjectWeight = 0.1;
    public static double predicateWeight = 0.1;
    public static double objectWeight = 0.1;
    public final static double threshold = 0;
}
