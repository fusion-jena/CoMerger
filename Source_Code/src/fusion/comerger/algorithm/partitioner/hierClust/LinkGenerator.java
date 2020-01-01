package fusion.comerger.algorithm.partitioner.hierClust;
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


//import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;




import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;


//import static java.util.stream.Collectors.toList;

public class LinkGenerator
{
    private ArrayList<OWLClass> nodes = null;
    private ArrayList<Link> links = null; // used for hierarchical proximity
    private double [][] simM = null;
    
    OWLOntology rbgm;

    public LinkGenerator(OWLOntology rbgm1)
    {
        links = new ArrayList<Link>();
        nodes = new ArrayList<OWLClass>();
        
        this.rbgm=rbgm1; //rbgm1.setOntModel(sg);  
         Iterator<OWLClass> nlist=rbgm1.getClassesInSignature().iterator();  
         Iterator<OWLDataProperty>itdp=rbgm1.getDataPropertiesInSignature().iterator();
         
         while(nlist.hasNext())
         {
        	 OWLClass n= nlist.next();
        	 if(n.isOWLClass()) nodes.add(n);
         }
        System.out.println("the number of node concepts---"+nodes.size());
        simM=new double[nodes.size()][nodes.size()];
      }


    public ArrayList<OWLClass> getNodes()
    {
        return nodes;
    }
    //sort the elements of the link according to the element simialrity values
    
	public ArrayList<Link> getSortedLink()
    {
       Collections.sort(links,new Comparator<Link>() {
       public int compare(Link o1, Link o2) {
        return (o2.getSimilarity() > o1.getSimilarity())? 1:0;

    }});

        return links;
    }


   // building the links hash table
    public HashMap getLinks()
    {
        generateStructuralLinks();
    	ArrayList<Link> l=getSortedLink();
        HashMap links = new HashMap(l.size());
        for (int i = 0, n = l.size(); i < n; i++) {
            Link link = (Link) l.get(i);
            OWLClass v1=link.getVertex1(); String name1=v1.toString();//.getLocalName(); 
            OWLClass v2=link.getVertex2(); String name2=v2.toString();//.getLocalName();
            if(name1!=null && name2!=null) 
            {
            	String key=name1.concat(name2);
                links.put(key, link);
            }
        }
        return links;
    }

    public double[][] getSimM()
    {
        return simM;
    }

    public void generateStructuralLinks()
    {
      
      double sim=0; 
      for (int i = 0, n = nodes.size(); i < n; i++)
       {
         OWLClass entity1 =  (OWLClass) nodes.get(i); 
         for (int j = i + 1; j < n; j++)
         {
           OWLClass entity2 =  (OWLClass) nodes.get(j);
           sim=computeVertexSim(entity1,entity2);
           //if(sim > 0) 
        	   //System.out.println(i+ "---"+entity1.toString().substring(entity1.toString().lastIndexOf("#")+1)+"------"+entity2.toString()+"---"+sim);
            if (sim > Parameters.linkCutoff)
            {
                  Link link = new Link(entity1, entity2, sim);
                  links.add(link);
            }
            simM[i][j]=sim;
             simM[j][i]=sim;
             }
          }
    }


   
    
    @SuppressWarnings("unused")
	private double computeVertexSim(OWLClass v1,OWLClass v2)
    {
    	//int k=Parameters.context;
    	Set<OWLClass> list1=getContext(v1);
    	Set<OWLClass> list2=getContext(v2);
    	if(list1==null || list2==null) return 0;
    	list1.add(v1);
        list2.add(v2);
        // to be used in java 8
        //List<OWLClass> common = list1.stream().filter(list2::contains).collect(toList());
        List<OWLClass> common= new ArrayList<OWLClass>(list1);
        common.retainAll(list2);
         double sim1=(2*common.size())/(float)(list1.size()+list2.size());
         // System.out.println("hallo---sim-?"+sim1);
          return sim1;   
    }
  
    
    private Set<OWLClass> getContext(OWLClass node)
    {
    	Set<OWLClass> list=new HashSet<OWLClass>();
    	Iterator<OWLClassExpression> it1=  node.getSubClasses(rbgm).iterator(); //EntitySearcher.getSubClasses(node, rbgm).iterator();// node.getSubClasses(rbgm).iterator();  
    	Iterator<OWLClassExpression> it2=node.getSuperClasses(rbgm).iterator();//EntitySearcher.getSuperClasses(node, rbgm).iterator();//node.getSuperClasses(rbgm).iterator();
    	while(it1.hasNext())
    	{
    		OWLClassExpression c=it1.next();
    		list.addAll(c.getClassesInSignature());
    	}
    	
    	while(it2.hasNext())
    	{
    		OWLClassExpression c=it2.next();
    		list.addAll(c.getClassesInSignature());
    	}
    	return list;
    }
   
    public void print()
    {
        for(int i=0;i<links.size();i++)
        {
            Link link=(Link) links.get(i);
            System.out.println("hallo"+link.getVertex1()+":"+link.getVertex2()+":"+link.getSimilarity());
        }
    }

}

