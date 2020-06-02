
package fusion.comerger.algorithm.partitioner.SeeCOnt;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.OntModel;

import fusion.comerger.algorithm.partitioner.SeeCOnt.Findk.Sorter;
import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Data;
import fusion.comerger.model.Node;

public class Rank {
	
	public static LinkedHashMap<Integer, Cluster> clusters; 
	private static OntModel model1;
	public static ArrayList<OntModel> models ;
	private static float[] importantAll=null ;
	public static  String [] SNameOnt=null;
	public static HashMap<String,Double> mapClass=null;
	private int numEntity;
	static HashMap<String,List> rankVector;
	
	public  Rank ()
	{
//	 this.model1=BuildModel.OntModel;
//	 this.model1=data.OntModel;
//     this.numEntity=data.getNumEntities();
     clusters=new LinkedHashMap<Integer, Cluster>();
     models=new ArrayList<OntModel>();
     rankVector=new HashMap<String,List>();
	}
	
	public  Rank (int ch)
	{
//	 this.model1=BuildModel.OntModel;
//     this.numEntity=data.getNumEntities();
     clusters=new LinkedHashMap<Integer, Cluster>();
	}
 

@SuppressWarnings("unchecked")
public static void rankConcepts(Data data)
{
//	String [] SortedNameOnt = null;
//	rankConcept RC=new rankConcept(model1,3);
	rankConcept RC=new rankConcept(data.getOntModel(),3);
	mapClass=new HashMap<String, Double>();
	RC.rank(data);
	mapClass=RC.getMapClass(data);
	rankVector=RC.getrankVector(data);
	SNameOnt=RC.getRanked(data);
//	BuildModel.SortedNameOnt= SNameOnt;
	data.setSortedNameOnt(SNameOnt);
	
   
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////Rank_old_Phase ////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static void rank_old_Phase(Data data)
{
	importantAll = new float [data.getNumEntities()];
	for (int i=0; i<data.getNumEntities(); i++){
		ArrayList<Node> temp = BuildModel.Connexion (data.getEntities().get(i));
		if (temp.size() > 0) {
			importantAll[i] = (float) (temp.size() + chartoint(data.getEntities().get(i).getLocalName()));
		}
	}
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////Sort_old_Phase ////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static void sort_old_Phase(Data data){

	Sorter str = new Sorter();
	SNameOnt = new String [data.getNumEntities()]; 
	SNameOnt = str.sort(importantAll, data.getEntities());
	data.setSortedNameOnt(SNameOnt);
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////Sort_Phase ////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static void sort_Phase(Data data){
	SNameOnt = new String [mapClass.size()]; 
    HashMap<String, Double> sortedMap = sortByValue(mapClass);
    //comment in case using java 7
	/*     mapClass.entrySet().stream()
	    .sorted(Entry.comparingByValue())
	    .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
	                              (e1, e2) -> e1, LinkedHashMap::new));*/
	Iterator<String> ss=sortedMap.keySet().iterator();
	 int i=mapClass.size()-1;
	 while(ss.hasNext())
	 {
		 String s=ss.next();
		 SNameOnt[i]=s; i--;  
	 }
	  
	 data.setSortedNameOnt(SNameOnt);
}
  
  //to be used in case of using Java 7
  private static <K, V extends Comparable<? super V>> HashMap<K, V>  sortByValue( HashMap<K, V> map )
{
  List<Map.Entry<K, V>> list =
      new LinkedList<>( map.entrySet() );
  Collections.sort( list, new Comparator<Map.Entry<K, V>>()
  {
      @Override
      public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
      {
          return ( o1.getValue() ).compareTo( o2.getValue() );
      }
  } );

  HashMap<K, V> result = new LinkedHashMap<>();
  for (Map.Entry<K, V> entry : list)
  {
      result.put( entry.getKey(), entry.getValue() );
  }
  return result;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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