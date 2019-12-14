
package fusion.comerger.general.visualization;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Data;
import fusion.comerger.general.gui.MainFrame;
import fusion.comerger.general.gui.MergingPanel;
import fusion.comerger.general.gui.OPATgui;
import fusion.comerger.general.visualization.Converter;
import fusion.comerger.general.visualization.GraphFrame;
import fusion.comerger.general.visualization.net.Network;



public class Visualization
{
	protected static GraphFrame graphFrame;
	private static Converter converter = new Converter();
    
 public  static void Run (Data data, int argnum){
	 
	 
	 if (argnum == 0) { //it calls from partitioning tab
	 
		 String ModuleName = null;
		 String ontName = data.getOntName();
		 String tempDir = data.getPath();//BuildModel.wd;//It is equal to "./temp/" 
		
		 MainFrame mainframe = null;
		 graphFrame = new GraphFrame(mainframe, data);
		 graphFrame.clearGraph();
		 BuildModel.colorIndex = -1;
		 int KNumCH= data.getNumCH();
		 
		 if (OPATgui.showAllModuleCheckBox.isSelected()){
			 for (int cid=0; cid<KNumCH; cid++){
				    ModuleName = tempDir + ontName + "_Module_" + cid + ".owl"; // Read our created modules 
				    String ch= data.getClusters().get(cid).getCH().getLocalName(); 
				    DrawGraph(ModuleName, ch, data);//Coordinator.clusters.get(cid).getCH().getLocalName()); //convert Owl to Net and also show it in graphFrame
			     }
			 graphFrame.setVisible(true);
		 } else {
			 if (OPATgui.NumModuleTextField.getText() == null){
				 JOptionPane.showMessageDialog(null, "Please Select the number of module", "Error", JOptionPane.ERROR_MESSAGE);
			 } else {
				 int numModule=0;
				 String []nm =OPATgui.NumModuleTextField.getText().split(",");
				 for (int t=0; t<nm.length; t++){
					 try{
						 numModule = Integer.parseInt(nm[t]);
					     numModule = numModule -1;
						 if (numModule+1 > KNumCH || numModule+1 <= 0){
							 JOptionPane.showMessageDialog(null, "You have "+ KNumCH +" modules, while you select one value in other range. You can enter between 1 and " + KNumCH, "Error", JOptionPane.ERROR_MESSAGE);
						 }else {
							 ModuleName = tempDir + ontName + "_Module_" + numModule + ".owl"; // Read our created modules 
							 
//							 DrawGraph(ModuleName); //convert Owl to Net and also show it in graphFrame
							 String ch= data.getClusters().get(numModule).getCH().getLocalName();
							 DrawGraph(ModuleName,ch, data);//Coordinator.clusters.get(numModule).getCH().getLocalName()); //convert Owl to Net and also show it in graphFrame
							 graphFrame.setVisible(true);
							 }
					 } catch(NumberFormatException e){
						 JOptionPane.showMessageDialog(null, "You should seperate your selected modules by ',' \n For example enter 2,4 without any space ", "Error", JOptionPane.ERROR_MESSAGE);
					 }
				 }
			 }
		 }
	 } else if (argnum ==1){ //it calls from merge tab
		 String ModuleName = null;
		 String ontName = data.getOntName();
		 //String tempDir = BuildModel.wd;//It is equal to "./temp/" 
		 String tempDir="resources/merge/result/merge"+MergingPanel.numAlgorithm+".owl";//to do
		
		 MainFrame mainframe = null;
		 graphFrame = new GraphFrame(mainframe, data);
		 graphFrame.clearGraph();
		 BuildModel.colorIndex = -1;
		 
	    //ModuleName = tempDir + ontName + "_Module_" + cid + ".owl"; // Read our created modules
	    ModuleName = tempDir ;
//	    # save elements of one ontology
//	    ArrayList<String> elm = elmFunc();
//	    DrawGraph(ModuleName,"CH"); //convert Owl to Net and also show it in graphFrame 
//	    DrawGraph(ModuleName,elm); //convert Owl to Net and also show it in graphFrame
	   
		 ModuleName = MergingPanel.MergeModels.get(0); 
	    DrawGraph(ModuleName, "CH", data);
	    BuildModel.colorIndex  ++;
	    ModuleName = MergingPanel.MergeModels.get(1); 
	    DrawGraph(ModuleName, "CH", data);
	    
	    
	    
	    graphFrame.setVisible(true);

	 } else if (argnum == 2){ //it calls from Optimal number of CH
		 //String ModuleName = null;
		 //String ontName = BuildModel.ontologyName;
		 String ontName = data.getOntName();
		 		 
		 //String tempDir = BuildModel.wd;//It is equal to "./temp/" 
				
		 MainFrame mainframe = null;
		 graphFrame = new GraphFrame(mainframe, data);
		 graphFrame.clearGraph();
		 BuildModel.colorIndex = -1;
		 
//	    ModuleName = tempDir + ontName + "_Module_" + cid + ".owl"; // Read our created modules
//	    ModuleName = tempDir ;
	    DrawGraph(ontName,"CH", data); //convert Owl to Net and also show it in graphFrame
	    // argument "CH" means consider all CHs
	    
		 

		 graphFrame.setVisible(true);
	 } else if (argnum ==3){ //it calls from analyzing tab
//		 String ModuleName = null;
		 String ontName = data.getOntName();
		 //String tempDir = BuildModel.wd;//It is equal to "./temp/" 
//		 String tempDir="resources/merge/result/merge"+MergingPanel.numAlgorithm+".owl";//to do
		
		 MainFrame mainframe = null;
		 graphFrame = new GraphFrame(mainframe, data);
		 graphFrame.clearGraph();
		 BuildModel.colorIndex = -1;
		 
	    //ModuleName = tempDir + ontName + "_Module_" + cid + ".owl"; // Read our created modules
//	    ModuleName = tempDir ;
//	    # save elements of one ontology
//	    ArrayList<String> elm = elmFunc();
//	    DrawGraph(ModuleName,"CH"); //convert Owl to Net and also show it in graphFrame 
//	    DrawGraph(ModuleName,elm); //convert Owl to Net and also show it in graphFrame
	   
//		 ModuleName = MergingPanel.MergeModels.get(0); 
	    DrawGraph(ontName, "CH", data);
//	    BuildModel.colorIndex  ++;
//	    ModuleName = MergingPanel.MergeModels.get(1); 
//	    DrawGraph(ontName, "CH", data);
	    
	    
	    
	    graphFrame.setVisible(true);

	 } 
	 
}

////////////////////////////////////////////////////////////////////////////////////////////
 private static Object DrawGraph(final String ModuleName, String Sch, Data data) {//GEN-FIRST:event_convertButtonActionPerformed
     //This function convert OWL to NET and then Draw its graph in graphframe
	 
        	 String ontologyFilename = ModuleName;
             String networkFilename = data.getPath()+"temp.net";
             File tempFile2 = new File (networkFilename);
             if (tempFile2.exists()) {
            	 tempFile2.delete();
             } 
             File tempFile = new File (networkFilename);
             
             File ontologyFile = new File(ontologyFilename);
             File networkFile = new File(networkFilename);

             if (!ontologyFile.exists()) {
                 JOptionPane.showMessageDialog(null,"The ontology file does not exist.", "Error",JOptionPane.ERROR_MESSAGE);
                 return null;
             }

             // Clear the converter
             converter.clear();

             // Read the ontology file
             try {
                 converter.readOntology(ontologyFile);
             } catch (IOException e) {
                 e.printStackTrace(System.err);
                 JOptionPane.showMessageDialog(null,"An I/O error occurred while reading the ontology file.", "Error", JOptionPane.ERROR_MESSAGE);
                 return null;
             }

             // Set the parameters for the conversion
//             TO DO
             /*
             converter.setIncludeSubclassLinks(OPATgui.includeSubclassLinksCheckBox.isSelected());
             converter.setStrengthSubclassLinks(Float.parseFloat(OPATgui.strengthSubclassLinksSpinner.getValue().toString()));
             converter.setIncludePropertyLinks(OPATgui.includePropertyLinksCheckBox.isSelected());
             converter.setStrengthPropertyLinks(Float.parseFloat(OPATgui.strengthPropertyLinksSpinner.getValue().toString()));
             converter.setIncludeDefinitionLinks(OPATgui.includeDefinitionLinksCheckBox.isSelected());
             converter.setStrengthDefinitionLinks(Float.parseFloat(OPATgui.strengthDefinitionLinksSpinner.getValue().toString()));
             converter.setIncludeOnlyDefinitionProperties(OPATgui.includeDefinitionResourcesComboBox.getSelectedIndex() == 0);
             converter.setIncludeSubstringLinks(OPATgui.includeSubstringLinksCheckBox.isSelected());
             converter.setStrengthSubstringLinks(Float.parseFloat(OPATgui.strengthSubstringLinksSpinner.getValue().toString()));
             converter.setIncludeDistanceLinks(OPATgui.includeDistanceLinksCheckBox.isSelected());
             converter.setStrengthDistanceLinks(Float.parseFloat(OPATgui.strengthDistanceLinksSpinner.getValue().toString()));
             converter.setMaxDistance(Integer.parseInt(OPATgui.maxDistanceSpinner.getValue().toString()));
             
             if (OPATgui.linkTypeComboBox.getSelectedIndex() == 0) {
                 converter.setLinkType(fusion.oapt.general.visualization.net.Link.Type.EDGE);
             } else {
                 converter.setLinkType(fusion.oapt.general.visualization.net.Link.Type.ARC);
             }
             */
             converter.setLinkType(fusion.comerger.general.visualization.net.Link.Type.EDGE);
             // Perform the conversion
             converter.convert();

             // Write the network file
             Network network = converter.getNetwork();
             try {
                 network.write(networkFile);
             } catch (IOException e) {
                 e.printStackTrace(System.err);
                 JOptionPane.showMessageDialog(null,"An I/O error occurred while writing the network file.","Error", JOptionPane.ERROR_MESSAGE);
                 return null;
             }

             
             // Draw the graph in the graph window that corresponds to the network
             
             graphFrame.createGraph(converter.getNetwork(), Sch, data);

             
			return null;
             
         
     }
     

}
