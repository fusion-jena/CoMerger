
package fusion.comerger.general.cc;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.jena.ontology.OntModel;

//import fusion.comerger.algorithm.partitioner.AxiomClustering.AxiomCluster;
//import fusion.comerger.algorithm.partitioner.AxiomClustering.AxiomClustering;
import fusion.comerger.algorithm.partitioner.SeeCOnt.CClustering;
import fusion.comerger.algorithm.partitioner.SeeCOnt.Cluster;
import fusion.comerger.algorithm.partitioner.SeeCOnt.CreateModule;
import fusion.comerger.algorithm.partitioner.SeeCOnt.EvaluationSeeCOnt;
import fusion.comerger.algorithm.partitioner.pbm.Partitioner;
import fusion.comerger.algorithm.partitioner.pbm.pbmCluster;
import fusion.comerger.general.cc.Parameters;
import fusion.comerger.general.gui.PartitioningPanel;
import fusion.comerger.model.RBGModel;


public class Controller
{
//	public static boolean CheckBuildModel ;
//	private  String filepath1 = null;   
//	public  OntModel OntModel = null;
 
 public Controller()
 {
//     filepath1 = Parameters.onto1;
    }
/*
 public Controller(String fp1) 
 {
	 CheckBuildModel=false;
 	if(fp1.endsWith(".owl") && CheckBuildModel==false)
 	{
 		 filepath1 = fp1;
 		 BuildModel.BuildModelOnt(fp1);
 		 Controller.CheckBuildModel =true;
 		 OntModel=BuildModel.OntModel;
 	}
 	else if(fp1.endsWith(".gz")&& CheckBuildModel==false)
 	{
 	
 		InputStream fileStream=null;
		try {
			fileStream = new FileInputStream(new File(fp1));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	    InputStream gzips=null;
		try {
			gzips = new GZIPInputStream(fileStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		Controller.CheckBuildModel =true;
		filepath1=fp1.substring(0, fp1.lastIndexOf("."));
		BuildModel.BuildModelOnt(fp1);
		OntModel=BuildModel.OntModel;
		    
 	}
 	
 }
 
 public OntModel getOntModel()
 {
     return OntModel;
 }
   */  

public  void InitialRun (String selectedType, Data data){
   
	if (data.getCheckBuildModel() ==false){
		BuildModel.BuildModelOnt(data);
	}
 	         
     switch (selectedType){
     case "SeeCOnt": 
     	CClustering C_Ont1= new CClustering ();
	    C_Ont1.StepsCClustering(data, 0);   //0 means call from Execute button to partition the whole ontology (normal case)	 
//        EvaluationSeeCOnt.Evaluation_SeeCont();
        break;
         
//     case "AxCOnt":
//    	AxiomClustering axiom_Ont1= new AxiomClustering ( data.getRbgModel(),  data.getOntName(), 500, data.getPath());
//    	axiom_Ont1.StepsCClustering();
//      	break;
     
     case "PBM":
    	 Partitioner p1 = new Partitioner(data.getRbgModel(),  data.getOntName(), 500, data.getPath());
         p1.partition();
    	 break;
     
     } 
 }
   	
}
