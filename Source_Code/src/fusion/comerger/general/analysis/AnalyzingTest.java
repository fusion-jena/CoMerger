
package fusion.comerger.general.analysis;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import fusion.comerger.general.cc.Data;
import fusion.comerger.general.gui.OPATgui;

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