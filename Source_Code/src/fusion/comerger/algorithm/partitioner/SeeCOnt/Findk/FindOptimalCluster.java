package fusion.comerger.algorithm.partitioner.SeeCOnt.Findk;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.Iterator;
import java.util.LinkedHashMap;


import org.apache.jena.ontology.OntModel;

import org.apache.jena.ontology.OntTools.Path;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import fusion.comerger.algorithm.partitioner.SeeCOnt.CClustering;
import fusion.comerger.algorithm.partitioner.SeeCOnt.Cluster;
import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Controller;
import fusion.comerger.general.cc.Data;
import fusion.comerger.model.Node;


public class FindOptimalCluster 
{
	private static LinkedHashMap<Integer, Cluster> Clusters;
	static float LogAIC []= null;
	static int OptKAIC=1;
	static float LogBIC []= null;
	static int OptK=1;
	static boolean flagStop = false;
	static OntModel model;
    static int numEnt;
	public FindOptimalCluster(Data data){
		if (data.getCheckBuildModel() == false) 
	 	{
	 		BuildModel.BuildModelOnt(data);
	 		model= data.getOntModel();
	 		numEnt= data.getNumEntities();
	 	}
	}

 public static int FindOptimalClusterFunc(Data data)
 {
	 if (data.getCheckBuildModel() == false) 
 	{
 		BuildModel.BuildModelOnt(data);
 	}
	 if(data.getEntities().size()==0) return 0; 	
 	/////////////////////////////////////////////////////////////////////
 	//determining the lower and upper bound to know how many model should be create 
 	int lowupValue []= new int [2];
 	lowupValue = LowUppCalculation(data);
 	int LowerBound =1;// lowupValue[0];
 	int UpperBound = lowupValue[1];
 	//int UpperBound =BuildModel.entities.size();

 	
 	//////////////////////////////////////////////////////////////////////
	 //Creating the models and calculated their BIC
	 int index = 0;
	 int NumModel = UpperBound - LowerBound + 1;
	 LogBIC = new  float [NumModel];
	 
	 //first one with full SeeCOnt steps (7steps)
	 data.setNumCH(LowerBound);
	 
 	 CClustering cc = new CClustering();
 	 cc.StepsCClustering(data, 2); //call from FindK (first iterative)
 	 Clusters = data.getClusters();
 	 
 	 LogBIC[index] = BIC (LowerBound, Clusters,  data.getEntities().size());
     index ++;
	 
     // the next ones with SeeCOnt with 5 steps
	 for (int k=LowerBound+1; k < UpperBound + 1; k++){
	 		data.setNumCH(k);	    	
	    	cc.StepsCClustering(data, 3); //call from FindK (other iterative)
	    	Clusters = data.getClusters();
	    	LogBIC[index] = BIC (k, Clusters,  data.getEntities().size());
	        index ++;
	 	}

 	// select optimal k with the lowest BIC value
 	float minBIC = LogBIC[0]; OptK= LowerBound;  	
 	int indexM= LowerBound ; 
 	for (int i=0; i <NumModel ; i++){
 		if (LogBIC [i] < minBIC) {
 			minBIC = LogBIC[i];
 			OptK= indexM; 
 		}
 		indexM ++;
 	}

 	return OptK;
 }
////////////////////////////////////////////////////LowUpp ///////////////////////////////////////////////////////
 public static int[] LowUppCalculation (Data data){
	 int LU []= new int [2];
	 int NConcepts = data.getEntities().size();
	 float Sum=0;
	 for (int j=0; j<data.getEntities().size(); j++) {
	    	Sum= Sum + data.getEntities().get(j).getHierarchy();
		}
	 float Mean= Sum / NConcepts;
 
	 float xxx= (float) (Math.log10(NConcepts)*Mean);
	 xxx= Math.round(xxx);
	 
	 LU[0]= (int) (xxx-5); if (LU[0] <1) LU[0]=1; 
	 LU[1]= (int) (xxx+5);
	 
	 return LU;
 }
//////////////////////////////////////////////////// BIC ///////////////////////////////////////////////////////
public static float BIC (int k, LinkedHashMap<Integer, Cluster> Clusters, int NumConcept){
	float BICvalue=1;
	float RSS= RSScalculate(k,Clusters, NumConcept);
	BICvalue = (float) ((float) (NumConcept * Math.log (RSS/NumConcept) ) + (k * Math.log (NumConcept)));
	
	return BICvalue;
}

/////////////////////////////////////////////////// RSS ////////////////////////////////////////////////////////
public static float RSScalculate (int k, LinkedHashMap<Integer, Cluster> Clusters, int NumConcept){
	float RSSvalue = 0;
	float dist=0;  int size=0;
	for (Iterator<Cluster> i = Clusters.values().iterator(); i.hasNext();) {
	    Cluster icluster = i.next();
	    Node ch=icluster.getCH();
	    //float d=computeDistance(icluster,ch);
	    size+=icluster.getSize();
	    //dist+=d*icluster.getSize();
		float MeanCluster = MeanClaculate (icluster);
	    for (Iterator<Node> iter = icluster.listElements(); iter.hasNext();) {
			Node concept = iter.next();
			RSSvalue = RSSvalue + ((concept.getHierarchy()- MeanCluster) * (concept.getHierarchy()- MeanCluster));
		}
	 }
	//return dist/(float)size;	
	return RSSvalue;
}

private static float computeDistance(Data data, Cluster cluster, Node ch)
{
	//System.out.println("the ont\t"+ch.toString());
	Resource cc1 = data.getOntModel().getOntResource(ch.toString());
	float dis=0;
	for (Iterator<Node> iter = cluster.listElements(); iter.hasNext();) {
		Node concept = iter.next(); 
		//OntClass cl2=BuildModel.OntModel.getOntClass(concept.getLocalName());
		 RDFNode cc2 =  (RDFNode) data.getOntModel().getOntClass(concept.toString());
		//RDFNode cc2 =  (RDFNode) model.getOntClass(ch.getLocalName()).asNode();
		 Path shortestPath = null;//OntTools.findShortestPath(BuildModel.OntModel, cc1, cc2,x);
		//Path shortestPath = OntTools.findShortestPath(BuildModel.OntModel, cc1, cc2,null);
		if(shortestPath!=null) dis+=shortestPath.size();
		if(shortestPath==null) dis+=10;
	}
	return dis;
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
public static float MeanClaculate (Cluster icluster){
	float MeanValue = 0; float Sumheir =0;
	
	for (Iterator<Node> iter = icluster.listElements(); iter.hasNext();) {
		Node concept = iter.next();
		Sumheir = Sumheir + concept.getHierarchy();
	}
	MeanValue = Sumheir / icluster.getElements().size();
	return MeanValue;
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static float MeanClaculateNew (Cluster icluster){
	   float MeanValue=0; int sum=0;
	   Iterator<Node> ListNode = icluster.listElements();
	   Node CHnode = icluster.getCH();
	   int LCH= CHnode.getHierarchy();
	   while (ListNode.hasNext()){
		   Node iNode = ListNode.next();
		   int LNode = iNode.getHierarchy();
		   int dist = Math.abs(LCH - LNode);
		   sum = sum + dist;
		   }
	   
	   MeanValue = sum / icluster.getElements().size();
	   
	   return MeanValue;
	}


}


