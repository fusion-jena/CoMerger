
package fusion.comerger.general.cc;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/
public class Parameters
{
 // comparability = { high, medium, low }
 public static int highComp = 2;
 public static int mediumComp = 1;
 public static int lowComp = 0;    
 
 // highComp = (lingHighValue, 1]
 // mediumComp = (lingLowValue, lingHighValue]
 // lowComp = [0, lingLowValue]
 public static double lingHighValue = 0.9;
 public static double lingLowValue = 0.035; 
 
 // lingHighValue = (lingHighSim, 1] / size
 // lingLowValue = (0, lingLowSim] / size
 public static double lingHighSim = 0.7;
 public static double lingLowSim = 0.0003;
 
 public static double structPercent = 5;    
 
 // highComp = (structHighValue, 1]
 // mediumComp = (structLowValue, structHighValue]
 // lowComp = [0, structLowValue]
 public static double structHighValue = 0.95;
 public static double structLowValue = 0.5;    
 
 // highComp = (structHighRate, 1]
 // mediumComp = (structLowRate, structHighRate]
 // lowComp = [0, structLowRate]
 public static double structHighRate = 0.49;
 public static double structLowRate = 0.25;   
 
 // V-Doc : String = combWeight : (1 - combWeight)
 public static double combWeight = 0.5;
 
 public static boolean inclInstMatch = false;
 
 public static int largeOnto = 5000;
 
 public static String onto1 = null;
 public static String onto2 = null;
 public static String output = null;
 public static String reference = null;
 
 public static String lang = "en";
}
