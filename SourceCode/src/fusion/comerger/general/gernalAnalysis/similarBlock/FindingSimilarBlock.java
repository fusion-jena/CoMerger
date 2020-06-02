
package fusion.comerger.general.gernalAnalysis.similarBlock;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import fusion.comerger.algorithm.partitioner.SeeCOnt.Cluster;
import fusion.comerger.general.gui.MatchingPanel;
import fusion.comerger.general.output.Alignment;
import fusion.comerger.model.RBGModel;


public class FindingSimilarBlock
{
	private RBGModel rbgModelA = null,  rbgModelB = null;
    private String name1 = null,  name2 = null;
    private int maxSize = 500;
    private String tempDir = null;
    private Alignment alignSet = null;
    private ArrayList<BlockMapping> bmSet = null;


    public void FSBlock(RBGModel Model1, RBGModel Model2, LinkedHashMap<Integer, Cluster> clusters1, LinkedHashMap<Integer, Cluster> clusters2, String name1, String name2, String tDir)
    {
    	rbgModelA = Model1; rbgModelB= Model2;
    	
    	BlockMatcher bm = new BlockMatcher(rbgModelA, rbgModelB, name1, name2, clusters1, clusters2, tDir);
    	bm.blockMatch();

        alignSet = bm.getAnchors();
        bmSet = bm.getBlockMappings();
    }

	public Alignment getAnchors()
	{
	    return alignSet;
	}	
	public ArrayList<BlockMapping> getBlockMappings()
    {
        return bmSet;
    }
    
    }
