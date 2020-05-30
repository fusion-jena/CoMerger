
package fusion.comerger.general.analysis;
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
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import fusion.comerger.general.cc.Data;


public class AnalyzingTest
{	
	public AnalyzingTest()
	{
		
	}
	public static  String[] AnalyzingTestRun(String filename, Data data){ // should return data
	 //information of first tab : General information
	 String[] res = new String [40];
	 GeneralAnalyzing GA=new GeneralAnalyzing(data);
	 GA.computeStatics(); //should return data
	 String[] tem =  GA.GeneralTest(); // should return data
	 for (int i=0; i<=24; i++){
		 res[i] = tem[i];
	 }
	 

	 
	 // information of third tab: Richness tab
	 RichnessAnalyzing RA=new RichnessAnalyzing(data);
	 String [] tem2= RA.RichnessTest1();
	 int j = 0;
	  for (int i=25; i<=37; i++){
			 res[i] = tem2[j];
			 j++;
		 }
	 
		 //information of second tab: Consistency check
		 try {
			  double start=System.currentTimeMillis();
			  res[38] = ConsistencyAnalyzing.ConsistencyTest1(filename);
			  double end1=System.currentTimeMillis();
//			  OPATgui.ConsistencyTextArea1.append("\n"+ "---> The reasoning time: "+(end1-start)*.001 +"\t sec");
//			  res[39] = res [1] + "\n"+ "The reasoning time: "+(end1-start)*.001 +"\t sec";
			  double tm = (end1-start)*.001 ;
			  res[39] = String.valueOf( Math.round(tm* 10000.0) / 10000.0)+"\t sec";
			 
		 } catch (OWLOntologyCreationException e) {
				e.printStackTrace();
			}
			 
	 return res;
 } 
}