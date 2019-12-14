
package fusion.comerger.general.cc;

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

