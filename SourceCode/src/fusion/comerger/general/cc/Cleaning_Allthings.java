
package fusion.comerger.general.cc;
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
import fusion.comerger.general.gui.AnalysisPanel;
import fusion.comerger.general.gui.EvalPanelPartitioning;
import fusion.comerger.general.gui.OPATgui;
import fusion.comerger.general.gui.PartitioningPanel;



public class Cleaning_Allthings
{    
    public Cleaning_Allthings()
    {
        
       }
    public static  void clean(int NumTab)
    {
    	int wait = 0;
    	switch (NumTab){
    	case 0: // if this function called by the button on tab 1 (Analyzing)   	
    		OPATgui.GeneralTextArea1.setText(null);
        	OPATgui.GeneralTextArea2.setText(null);
        	OPATgui.ConsistencyTextArea1.setText(null);
        	OPATgui.ConsistencyTextArea2.setText(null);
        	OPATgui.RichnessTextArea1.setText(null);
        	OPATgui.RichnessTextArea2.setText(null);
        	if (OPATgui.NameAddressOnt1 != null && OPATgui.NameAddressOnt1.equals(OPATgui.ont1stTextField.getText())){
        		//do not need re-built, since the ontology files is same as before
        	}else{
//	        	Controller.CheckBuildModel = false;
//	        	BuildModel.entities=null;
        	}

    		break;

    	case 1: // if this function called by the button on tab 1 (Partitioning)	
    		OPATgui.Result1_Details.setText(null);
    		OPATgui.Result3_Details.setText(null);
    		OPATgui.Result2_Details.setText(null);
    		OPATgui.AvgTextBox1.setText(null);
    		OPATgui.AvgTextBox2.setText(null);
    		OPATgui.AvgTextBox3.setText(null);
//    		OPATgui.OutputPartitioningTextArea.setText(null); TO DO: should correct    	
        	if ((OPATgui.NameAddressOnt != null && OPATgui.NameAddressOnt.equals(OPATgui.ontologyFileTextField.getText())) ||
        	(OPATgui.NameAddressOnt1 != null && OPATgui.NameAddressOnt1.equals(OPATgui.ontologyFileTextField.getText()))){
        		//do not need re-built, since the ontology files is same as before
        	}else{
//	        	Controller.CheckBuildModel = false;
//	        	BuildModel.entities=null;
        	}

    		break;
    	case 2:// if this function called by the button on tab 2 (Partitioning Evaluation)
//    		Controller.CheckBuildModel = false;
//        	BuildModel.entities=null;
    		break;
    		
    	case 3:// if this function called by the button on tab 3(Matching)
    		//result table should be empty
//    		Controller.CheckBuildModel = false;
//        	BuildModel.entities=null;
    		break;
    		
    		
    	case 4:// if this function called by the button on tab 4 (Matching Evaluation)
//    		Controller.CheckBuildModel = false;
//        	BuildModel.entities=null;
    		break;
    	
    	case 5:// if this function called by the button on tab 5 (Merging)
//    		Controller.CheckBuildModel = false;
//        	BuildModel.entities=null;
    		break;
    		
    	case 6:// if this function called by the button on tab 6 (Merging Evaluation)
//    		Controller.CheckBuildModel = false;
//        	BuildModel.entities=null;
    		break;
    	
    	}
    }    
}

