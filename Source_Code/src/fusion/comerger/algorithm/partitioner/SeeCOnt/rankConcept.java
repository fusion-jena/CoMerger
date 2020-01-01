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
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.jena.ontology.ConversionException;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntTools;
import org.apache.jena.ontology.OntTools.Path;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.Filter;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;

import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Data;



public class rankConcept {
	
	OntModel model;
	HashMap<String,List> rankVector;
    HashMap<String,Double> mapClass=null;
    private static float[] importantAll=null ;
	int level;
	String SortedNameOnt[];
	
	public rankConcept(Data data)
	{
		model=data.getOntModel();
		rankVector=new HashMap<String,List>();
		mapClass=new HashMap<String, Double>();
		level=0;
		
	}
	
	public rankConcept(int d, Data data)
	{
		model=data.getOntModel();
		rankVector=new HashMap<String,List>();
		mapClass=new HashMap<String, Double>();
		level=d;
		
	}
	
	public rankConcept(OntModel mo, int d)
	{
		this.model=mo;
		rankVector=new HashMap<String,List>();
		mapClass=new HashMap<String, Double>();
		level=d;
		
	}
	
	//this method to return the set of classes without rank along with their important value
	public HashMap<String, Double> getMapClass(Data data)
	{
		if(mapClass==null)
			rank(data);
		return mapClass;
	}
	
	//this method returns the set of nodes and its surrounding
	public HashMap<String,List> getrankVector(Data data)
	{
		if(rankVector==null)
			rank(data);
		return rankVector;
	}
	
	public String[] getRanked(Data data)
	{
		if(mapClass==null)
			rank(data);
		 HashMap<String, Double> sortedMap = sortByValue(mapClass);
		 Iterator<String> ss=sortedMap.keySet().iterator();
		 int i=sortedMap.size()-1;
		 SortedNameOnt=new String[mapClass.size()];
		 while(ss.hasNext())
		 {
			 String s=ss.next();
			 SortedNameOnt[i]=s;  i--; 
		 }
		 return SortedNameOnt;
	}
	
		
	private static <K, V extends Comparable<? super V>> HashMap<K, V>  sortByValue( Map<K, V> map )
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
	

	
	public void rank(Data data)
	{
		List<OntClass> list=new ArrayList<OntClass>();
		List<String> contList;
		list=data.getOntModel().listNamedClasses().toList();    
		importantAll = new float [list.size()];
		int countSub=0, countSup=0, countPro=0, ecount=0;
	    double size=data.getOntModel().listNamedClasses().toList().size();
		for(int i=0;i<list.size();i++)
	    {
	    try {
			importantAll[i]=0;
	    	OntClass cls=(OntClass) list.get(i);  
	    	ExtendedIterator it=cls.listSubClasses();   
	    	ExtendedIterator  it2=cls.listSuperClasses(true);
	    	List it3=cls.listDeclaredProperties().toList();   
	    	ExtendedIterator it4=cls.listEquivalentClasses(); 
	    	countPro=it3.size();
	    	contList=new ArrayList<String>();
	    	/*Path path=OntTools.findShortestPath(model, cls, OWL.Thing,Filter.any);
	    	
	    	if(path!=null)
	    	{
	    		for(int ii=0;ii<path.size();ii++)
	    		{
	    			Resource cc= path.get(ii).getSubject();
	    			if(!contList.contains(cc.getLocalName())) contList.add(cc.getLocalName());
	    		}
	    	}*/
	    	switch(level)
			{
			case 0: // this is to include only the sub-super classes of the node
				while(it.hasNext())
		    	{
		    		OntClass cl=(OntClass)it.next();
		    		if(list.contains(cl)) 
		    			{countSub++;
		    		   contList.add(cl.getLocalName());}
		       	} 
		    	
		    	while(it2.hasNext())
		    	{
		    		OntClass cl=(OntClass)it2.next();
		    		if(list.contains(cl)) {countSup++; contList.add(cl.getLocalName());}
		       	}
		    	
		    	while(it4.hasNext())
		    	{
		    		OntClass eclass=(OntClass)it4.next();
		    		if(list.contains(eclass)) ecount++;
		    	}
		    	rankVector.put(cls.getLocalName(), contList);
				break;
				
			case 1:  // this is the case when sub-sub and sup-sup are considered
				while(it.hasNext())
		    	{
		    		OntClass cl=(OntClass)it.next();
		    		if(list.contains(cl)) countSub++;
		    		List ccc=cl.listSubClasses().toList();  
		    		for(int j=0;j<ccc.size();j++)
		    		{
		    			OntClass cll=(OntClass) ccc.get(j); 
		    			if(list.contains(cll)) {countSub++; contList.add(cll.getLocalName());}
		    		}
		    	}
		    	
		    	while(it2.hasNext())
		    	{
		    		OntClass cl=(OntClass)it2.next();
		    		if(list.contains(cl)) countSup++;
		    		List ccc=cl.listSuperClasses().toList();  
		    		for(int j=0;j<ccc.size();j++)
		    		{
		    			OntClass cll=(OntClass) ccc.get(j); 
		    			if(list.contains(cll)) {countSup++;contList.add(cll.getLocalName());}
		    		}
		    		
		    	}
		    	
		    	
		    	while(it4.hasNext())
		    	{
		    		OntClass eclass=(OntClass)it4.next();
		    		if(list.contains(eclass)) ecount++;
		    	}
		    	rankVector.put(cls.getLocalName(), contList);
				break;
				
			case 2:  //this is the case when up to level 3 can be considered.
				while(it.hasNext())
		    	{
		    		OntClass cl=(OntClass)it.next();
		    		if(list.contains(cl)) {countSub++; contList.add(cl.getLocalName());}
		    		List ccc=cl.listSubClasses().toList();  
		    		for(int j=0;j<ccc.size();j++)
		    		{
		    			OntClass cll=(OntClass) ccc.get(j); 
		    			if(list.contains(cll)) {countSub++; contList.add(cll.getLocalName());}
		    			List cccc=cll.listSubClasses().toList();
		    			for(int ii=0;ii<cccc.size();ii++)
		    			{
		    				OntClass cl2=(OntClass)cccc.get(ii);
		    				if(list.contains(cl2)) {countSub++; contList.add(cl2.getLocalName());}
		    			}
		    		}
		    	}
		    	
		    	while(it2.hasNext())
		    	{
		    		OntClass cl=(OntClass)it2.next();
		    		if(list.contains(cl)) {countSup++; contList.add(cl.getLocalName());}
		    		List ccc=cl.listSuperClasses().toList();  
		    		for(int j=0;j<ccc.size();j++)
		    		{
		    			OntClass cll=(OntClass) ccc.get(j); 
		    			if(list.contains(cll)) {countSup++; contList.add(cll.getLocalName());}
		    			List cccc=cll.listSuperClasses().toList();
		    			for(int ii=0;ii<cccc.size();ii++)
		    			{
		    				OntClass cl2=(OntClass)cccc.get(ii);
		    				if(list.contains(cl2)) {countSup++; contList.add(cl2.getLocalName());}
		    			}
		    		}
		    	}
		    	while(it4.hasNext())
		    	{
		    		OntClass eclass=(OntClass)it4.next();
		    		if(list.contains(eclass)) ecount++;
		    	}
		    	rankVector.put(cls.getLocalName(), contList);
				break;
			case 3:  //this is the case when up to level 3 can be considered.
				while(it.hasNext())
		    	{
		    		OntClass cl=(OntClass)it.next();
		    		if(list.contains(cl)) {countSub++; contList.add(cl.getLocalName());}
		    		List ccc=cl.listSubClasses().toList();  
		    		for(int j=0;j<ccc.size();j++)
		    		{
		    			OntClass cll=(OntClass) ccc.get(j); 
		    			if(list.contains(cll)) {countSub++; contList.add(cll.getLocalName());}
		    			List cccc=cll.listSubClasses().toList();
		    			for(int ii=0;ii<cccc.size();ii++)
		    			{
		    				OntClass cl2=(OntClass)cccc.get(ii);
		    				if(list.contains(cl2)) {countSub++; contList.add(cl2.getLocalName());}
		    				List ccccc=cl2.listSubClasses().toList();
		    				for(int iii=0;iii<ccccc.size();iii++)
		    				{
		    					OntClass cl3=(OntClass)ccccc.get(iii);   
			    				if(list.contains(cl3)) {countSub++; contList.add(cl3.getLocalName());}
		    				}
		    				
		    			}
		    		}
		    	}
		    	
		    	while(it2.hasNext())
		    	{
		    		OntClass cl=(OntClass)it2.next();
		    		if(list.contains(cl)) {countSup++; contList.add(cl.getLocalName());}
		    		List ccc=cl.listSuperClasses().toList();  
		    		for(int j=0;j<ccc.size();j++)
		    		{
		    			OntClass cll=(OntClass) ccc.get(j); 
		    			if(list.contains(cll)) {countSup++; contList.add(cll.getLocalName());}
		    			List cccc=cll.listSuperClasses().toList();
		    			for(int ii=0;ii<cccc.size();ii++)
		    			{
		    				OntClass cl2=(OntClass)cccc.get(ii);
		    				if(list.contains(cl2)) {countSup++; contList.add(cl2.getLocalName());}
		    				List ccccc=cl2.listSuperClasses().toList();
		    				for(int iii=0;iii<ccccc.size();iii++)
		    				{
		    					OntClass cl3=(OntClass)ccccc.get(iii);
			    				if(list.contains(cl3)) {countSup++; contList.add(cl3.getLocalName());}
		    				}
		    			}
		    		}
		    	}
		    	while(it4.hasNext())
		    	{
		    		OntClass eclass=(OntClass)it4.next();
		    		if(list.contains(eclass)) ecount++;
		    	}
		    	rankVector.put(cls.getLocalName(), contList);
				break;
			default:
				break;
			}
	    	
	    	if(countSub!=0)
	    	{
	    		importantAll [i] = (float) (0.3*countSub+0.3*countSup+0.3*countPro+0.1*ecount)/(float)size; 
	    	}
	    	mapClass.put(cls.getURI(), (Double.valueOf(importantAll[i])));  //System.out.println(cls.getLocalName()+"\t"+Double.valueOf(importantAll[i]));
	    	countSub=0; countSup=0; countPro=0;
	  
	   
	    }catch(ConversionException ee){
	        System.out.println(ee.getMessage());
		}
	}
		
		
   	}
	 public static void main(String args[])
	 {
	     
	     double start=System.currentTimeMillis();
	     String fp1 = "D:/test_ont/test/cmt.owl";
	     String fp2 = "D:/Naouel_GFBio/ontology/ado.owl.gz";
	     BuildModel model=  new BuildModel();
	     Data data = new Data();
	     data.setOntName(fp1);
	     model.BuildModelOnt(data);
	     rankConcept RC=new rankConcept(2,data);
//	     RC.rank();
	     HashMap<String,Double> rank=RC.mapClass;
	     HashMap<String, List> rc=RC.rankVector;
//	     String[] rlist=RC.getRanked();
	     System.out.println("the rank size\t"+rank.size());
	     int i=0;
	     for (HashMap.Entry<String, Double> entry : rank.entrySet()) {
//	    	    System.out.println("the ranking concepts\t"+entry.getKey()+" : "+entry.getValue()+ "\t"+rlist[i]); i++;
	    	}
	     
	     for (HashMap.Entry<String, List> entry : rc.entrySet()) {
	    	    System.out.println("the ranking concepts\t"+entry.getKey()+" : "+entry.getValue()); 
	    	}
	     double end=System.currentTimeMillis();
//	     System.out.println("The optimal num. of partition time---->"+(end-start)*.001+"\t sec \t"+BuildModel.OntModel.size());
	 } 
	
	}
