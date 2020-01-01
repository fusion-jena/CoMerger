
package fusion.comerger.algorithm.matcher.vdoc;
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
