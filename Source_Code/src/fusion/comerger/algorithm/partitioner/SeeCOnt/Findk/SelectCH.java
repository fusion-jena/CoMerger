package fusion.comerger.algorithm.partitioner.SeeCOnt.Findk;

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

import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Data;
import fusion.comerger.general.gui.MainFrame;
import fusion.comerger.general.gui.OPATgui;




public class SelectCH
{
//	protected static SelectCHWindows SelectCHWindows;
	
    
 public  static String Run (Data data){
//	 String ModuleName = null;
//	 String ontName = data.getOntName();
//	 String tempDir = data.getPath();//It is equal to "./temp/" 
//			 	 
//	 MainFrame mainframe = null;
//	 SelectCHWindows = new SelectCHWindows(mainframe);
//	 SelectCHWindows SelectCHWindows = new SelectCHWindows();
		 
	 String CHs= RunSelectCH(data);
//	 SelectCHWindows.Show(); //Samira deactive this in web version
//	 SelectCHWindows.setVisible(true);	
	 return CHs;
	 
}
private static String RunSelectCH(Data data){
//	SelectCHWindows.ListArrayCH.clear();
		
//	 String CHs = FindOptimalClusterIntractive.CHset[0];
	  String CHs = null;
	  for (int i=0;i<FindOptimalClusterIntractive.CHset.length;i++){
		  if (FindOptimalClusterIntractive.CHset[i] != null) {
			  //CHs = CHs+ " & "+ FindOptimalClusterIntractive.CHset[i]; 
// 			ArrayList<String> s = BuildModel.entities.get(BuildModel.findIndex(FindOptimalClusterIntractive.CHset[i])).getLabel();
 			String s = data.getEntities().get(BuildModel.findIndex(FindOptimalClusterIntractive.CHset[i])).getLocalName(); //new:samira
     		if (s == null){
     			if (CHs != null){
     				CHs = CHs + " & " + FindOptimalClusterIntractive.CHset[i];
     			}else{
     				CHs = FindOptimalClusterIntractive.CHset[i];
     			}
     			
     		}else{
     			if (CHs != null ){
//     				CHs = CHs+ " & "+ s.get(0);
     				CHs = CHs + " & " + s ;
     			}else{
     				CHs = s ;
     			}
     		}
		  }
	  }
	  
//	  SelectCHWindows.CHLabel.setText(CHs); //it should have scroll
//	  SelectCHWindows.NumCHLabel.setText(data.getNumCH()+" ");
	  
	  /*
	  //show Tree
	  ShowTree.ShowTreeCalculating();
	  
	  //JOptionPane.showMessageDialog(null, "The optimal number of clusters for your ontology is  "+ optimalNumCH + "\n Your Cluster Heads are: \n"+ CHs, "Help", JOptionPane.INFORMATION_MESSAGE);
	  
	   	String info = null;
       	for (int in=0; in<data.getSortedNameOnt().length; in++){
       		//info = info + " , " + BuildModel.SortedNameOnt[in]; //should we show the value of rank function
       		//if (BuildModel.SortedNameOnt[in] != null) SelectCHWindows.ListArrayCH.addElement(BuildModel.SortedNameOnt[in]); //+score#
       		//if (BuildModel.SortedNameOnt[in] != null) SelectCHWindows.ListArrayCH.addElement(BuildModel.entities.get(BuildModel.findIndex(BuildModel.SortedNameOnt[in])).getLabel().toString());
       		
       		if (data.getSortedNameOnt()[in] != null) {
       			int a= BuildModel.findIndex(data.getSortedNameOnt()[in]);
       			
//       			ArrayList<String> s= BuildModel.entities.get(a).getLabel();
       			String s= data.getEntities().get(a).getLocalName();
	       		if (s == null){
	       			SelectCHWindows.ListArrayCH.addElement(data.getSortedNameOnt()[in]);
	       		}else{
//	       			SelectCHWindows.ListArrayCH.addElement(s.get(0));
	       			SelectCHWindows.ListArrayCH.addElement(s);
	       		}
       		}
       		
       	}
       	//JOptionPane.showMessageDialog(null, "The concepts with our ranking score are:"+ info, "Information", JOptionPane.INFORMATION_MESSAGE); // open a new window like visulaization window
       	//String[] ListTypeComboBoxTitle = new String[] {info};
       
       	//SelectCHWindows.CHComboBoxTitle = new String [] {info};
       	
       	
       	
       	
       	//SelectCHWindows.ListArrayCH.addElement(info);
       	
       	
       	//save user CH
       	String UserCH = CHs; //it should be those one which user select from combobox
       	
       	//save user CH and then call Seecont
       	//CentralClustering SeeCOntObj= new CentralClustering ();
       	//LinkedHashMap<Integer, Cluster> Clusters;
       	//Clusters = SeeCOntObj.SeeCOntAlogorithmIntract(UserCH);
          
       

//when it finilized, it should also show in partitoing panle
//PartitioningPanel.NumCHTextField.setText(String.valueOf(Coordinator.KNumCH));
       	*/
return CHs;
}
}