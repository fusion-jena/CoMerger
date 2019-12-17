package fusion.comerger.algorithm.partitioner.SeeCOnt.Findk;

/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import fusion.comerger.algorithm.partitioner.SeeCOnt.CClustering;
import fusion.comerger.algorithm.partitioner.SeeCOnt.Cluster;
import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Controller;
import fusion.comerger.general.cc.Data;
import fusion.comerger.model.Node;



public class FindOptimalClusterIntractive 
{
	private static float[] importantAll=null ;
	public static  String [] SortedNameOnt=null;
	public static String [] CHset;
	private static LinkedHashMap<Integer, Cluster> Clusters;
	static float LogAIC []= null;
	static int OptKAIC=1;
	static float LogBIC []= null;
	static int OptK=1;
	static boolean flagStop = false;
	static org.apache.jena.ontology.OntModel model;
    static int numEnt;
	public FindOptimalClusterIntractive(Data data){
		if (data.getCheckBuildModel() == false) 
	 	{
	 		BuildModel.BuildModelOnt(data);
	 		model= data.getOntModel();
	 		numEnt=data.getNumEntities();
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
	String [][] SaveCH= new String [UpperBound][UpperBound];
	CHset = new String[UpperBound];
 	//int UpperBound =BuildModel.entities.size();

 	
 	//////////////////////////////////////////////////////////////////////
	 //Creating the models and calculated their BIC
	 int index = 0;
	 int NumModel = UpperBound - LowerBound + 1;
	 LogBIC = new  float [NumModel];
	 
	 //first one with full SeeCOnt steps (7steps)
//	 Coordinator.KNumCH = LowerBound;
	 data.setNumCH(LowerBound);
	 
	CClustering cc= new CClustering();
	cc.StepsCClustering(data, 2); //call from FindK (first iterative)
	
//	Clusters = Coordinator.clusters;
//	Clusters = data.getClusters();
	//smaira
 	 LogBIC[index] = BIC (data, LowerBound);
     for (int i=0;i<LowerBound;i++)
 		//SaveCH[index][i]=CClusteringk1.entitiesCH.get(i).getLocalName().toString();
//     	SaveCH[index][i]=Coordinator.CH.get(i).getLocalName().toString();
     	SaveCH[index][i]=data.getCHs().get(i).toString(); //new:samira
     
     index ++;
     
     // the next ones with SeeCOnt with 5 steps
	 for (int k=LowerBound+1; k < UpperBound + 1; k++){
	 		data.setNumCH(k);
	 
	 		cc.StepsCClustering(data, 3); //call from FindK (other iterative)
	 		
//	 		Clusters = data.getClusters();
	    	LogBIC[index] = BIC (data, k);
	        for (int i=0;i<k;i++)
	    		//SaveCH[index][i]=CClusteringk2.entitiesCH.get(i).getLocalName().toString();
//	        	SaveCH[index][i]=Coordinator.CH.get(i).getLocalName().toString();
	        	SaveCH[index][i]= data.getCHs().get(i).toString(); //new:samira
	        
	        index ++;
	 }
     
 	// select optimal k with the lowest BIC value
 	float minBIC = LogBIC[0]; 
 	OptK= LowerBound;
	for (int j=0;j<UpperBound;j++)
		if (SaveCH[0][j]!= null) CHset[j] = SaveCH[0][j].toString();
	
 	int indexM= LowerBound ; 
 	for (int i=0; i <NumModel ; i++){
 		if (LogBIC [i] < minBIC) {
 			minBIC = LogBIC[i];
 			OptK= indexM; 
 			CHset = new String[UpperBound];
 			for (int j=0;j<UpperBound;j++){
 				if (SaveCH[i][j]!= null) CHset[j]=SaveCH[i][j];
 			}
 		}
 		indexM ++;
 	}
 	LastCHRank(data, OptK); // In this function I call the rank function for the last find k. If we do not do it, we should save all entities for all BIC model. We save sort node in BuildModel.SortedNameOnt
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
public static float BIC (Data data, int k){
	float BICvalue=1;
	int NumConcept =  data.getEntities().size();
	LinkedHashMap<Integer, Cluster> Clusters = data.getClusters();
	float RSS= RSScalculate(data, k,Clusters, NumConcept);
	BICvalue = (float) ((float) (NumConcept * Math.log (RSS/NumConcept) ) + (k * Math.log (NumConcept)));
	
	return BICvalue;
}

/////////////////////////////////////////////////// RSS ////////////////////////////////////////////////////////
public static float RSScalculate (Data data, int k, LinkedHashMap<Integer, Cluster> Clusters, int NumConcept){
	Clusters = data.getClusters();
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

private static float computeDistance(Cluster cluster, Node ch)
{
	/*
	//System.out.println("the ont\t"+ch.toString());
	OntResource cc1 = BuildModel.OntModel.getOntResource(ch.toString());
	float dis=0;
	for (Iterator<Node> iter = cluster.listElements(); iter.hasNext();) {
		Node concept = iter.next(); 
		//OntClass cl2=BuildModel.OntModel.getOntClass(concept.getLocalName());
		 RDFNode cc2 =  (RDFNode) BuildModel.OntModel.getOntClass(concept.toString());
		//RDFNode cc2 =  (RDFNode) model.getOntClass(ch.getLocalName()).asNode();
		Path shortestPath = OntTools.findShortestPath(BuildModel.OntModel, cc1, cc2,Filter.any);
		if(shortestPath!=null) dis+=shortestPath.size();
		if(shortestPath==null) dis+=10;
	}
	
	return dis;
	*/
	return 0;
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

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static void LastCHRank(Data data, int OptK)
{

	 //CentralClustering SeeCOntObj2= new CentralClustering ();
	 //Clusters = SeeCOntObj2.SeeCOntAlogorithmk1();
//	Coordinator.KNumCH = OptK; //It does not matter what is the number of ch in this phase
	data.setNumCH(OptK);
	Rank_Phase(data);										
	Sort_Phase(data);									
}
public static void Rank_Phase(Data data)
{
	importantAll = new float [data.getEntities().size()];
	for (int i=0; i<data.getEntities().size(); i++){
		ArrayList<Node> temp = BuildModel.Connexion (data.getEntities().get(i));
			if (temp.size() > 0) {
				importantAll[i] = (float) (temp.size() + chartoint(data.getEntities().get(i).getLocalName()));
			}
	}
}
public static void Sort_Phase(Data data){
	
	Sorter str = new Sorter();
	SortedNameOnt = new String [data.getEntities().size()]; 
	SortedNameOnt = str.sort(importantAll, data.getEntities());
	data.setSortedNameOnt(SortedNameOnt);
}

public static double chartoint(String iname ){
	double r=0;
	iname = iname.toLowerCase(); 
	for (int i=0; i<iname.length(); i++){
		char xx= iname.charAt(i);
		r= r+ (int) xx;
	}
	r= r/iname.length();
	return r;
}

}


