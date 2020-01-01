
package fusion.comerger.algorithm.matcher;
/* 
 * This package is downloaded from the FALCON-AO tool, 
 * available in http://ws.nju.edu.cn/falcon-ao/
 * For more information of this method, please refer to
 * Hu, W. and Qu, Y., 2008. Falcon-AO: A practical ontology matching system. Journal of web semantics, 6(3), pp.237-239.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    
    public static int largeOnto = 500;
    
    public static String onto1 = null;
    public static String onto2 = null;
    public static String output = "temp/algin.rdf";
    public static String reference = null;
    
    public static String lang = "en";
}
