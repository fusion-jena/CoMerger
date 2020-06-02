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
import org.apache.jena.ontology.OntModel;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Data;
import fusion.comerger.general.gui.OPATgui;
import fusion.comerger.model.RBGModel;


public class CompareTest {
	
public static void compareFunc (String nameOnt1, String nameOnt2){
    	
    	Data data1 = new Data(); Data data2 = new Data();
    	OntModel model1 ;   OntModel model2 ;
    	RBGModel rbgmModel ;    RBGModel rbgm2 ;
    	    	
    	data1.setOntName(nameOnt1);
    	BuildModel.BuildModelOnt(data1);
    	model1=data1.getOntModel();
    	rbgmModel=data1.getRbgModel();
    	 
         //call general analyze separately for each model
    	GeneralAnalyzing GA=new GeneralAnalyzing(model1);
   	 	GA.computeStatics();
   	 	GA.GeneralTest();
   	 
   	 	    	
    	   	 
	   	 //information of second tab: Consistency check
	   	 try {
	   		  double start=System.currentTimeMillis();
	   		  ConsistencyAnalyzing.ConsistencyTest1(nameOnt1);
	   		  double end1=System.currentTimeMillis();
	   		  OPATgui.ConsistencyTextArea1.append("\n"+ "The reasoning time: "+(end1-start)*.001 +"\t sec");
	   		} catch (OWLOntologyCreationException e) {
	   			e.printStackTrace();
	   		}
	 
	   	 RichnessAnalyzing RA=new RichnessAnalyzing(model1,rbgmModel);
	   	 RA.RichnessTest1();
	   	 
	   	 data2.setOntName(nameOnt2);
         BuildModel.BuildModelOnt(data2);
         model2=data2.getOntModel();
         rbgm2=data2.getRbgModel();
         //CmpGeneralAnalyzing1 GA2=new  CmpGeneralAnalyzing1(model2);
         GA=new GeneralAnalyzing(model2);
    	 GA.computeStatics();
    	 GA.GeneralTest2();
         try {
	   		  double start=System.currentTimeMillis();
	   		  ConsistencyAnalyzing.ConsistencyTest2();
	   		  double end1=System.currentTimeMillis();
	   		  OPATgui.ConsistencyTextArea2.append("\n"+ "The reasoning time: "+(end1-start)*.001 +"\t sec");
	   		} catch (OWLOntologyCreationException e) {
	   			e.printStackTrace();
	   		}
    	  RA=new RichnessAnalyzing(model2,rbgm2);
	   	  RA.RichnessTest2(data1); //or data2?
    	 
	   	 // information of third tab: Richness tab
	   	 
//	   	 BuildModel.OntModel = model1; 
//	   	 BuildModel.rbgmModel = rbgmModel;
	   	 data1.SetOntModel(model1);
	   	 data1.SetRbgModel(rbgmModel);
    }

}
