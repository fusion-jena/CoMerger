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

import java.io.InputStream;
import java.util.*;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

//import opt.general.model.impl.NodeImpl;
//import opt.general.model.impl.RBGModelImpl;

import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
//import com.wcohen.ss.*;
import com.hp.hpl.jena.util.FileManager;


/**
 * Created by IntelliJ IDEA.
 * User: algergawy
 * Date: 26.10.2015
 * Time: 11:13:36
 * To change this template use File | Settings | File Templates.
 */
public class TestCluster {

    public static void main(String[] args) {
        //Define data
        String source="D:/sources/bco.owl";
        String target="D:/sources/gfo.owl";
        InputStream testFileIn1 = null,testFileIn2=null;
        try 
        {
      
       testFileIn1 = FileManager.get().open(source);
       testFileIn2 = FileManager.get().open(target);
       } catch(IllegalArgumentException ex) {
          System.out.println( "File not found");
          System.exit(0);
       }
     
     //try to use OWL API
        OWLOntology ont1=null, ont2=null;
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        try {
			 ont1 = m.loadOntologyFromOntologyDocument(testFileIn1);
			 ont2=m.loadOntologyFromOntologyDocument(testFileIn2);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
       
       partition(ont1,ont2);  
             
     }

    public static void partition(OWLOntology ont1, OWLOntology ont2)
    {
    	System.out.println(ont2.getClassesInSignature().size()+"\tthe number of classes \t"+ont1.getClassesInSignature().size());
    	HierarchicalAlgorithm ha=new HierarchicalAlgorithm(ont1);
    	Dendrogram dn1=ha.cluster();
    	//dn1.print(dn1.getBestLevel());
    	 LinkGenerator LG=new LinkGenerator(ont1);
    	 HashMap links=LG.getLinks();
    	 System.out.println("the numbr of links\t"+links.size());
    	 ClusterGenerator CG=new  ClusterGenerator();
    	 CG.init(LG.getNodes(), LG.getSortedLink(), links);
    	 Dendrogram dn=CG.cluster();
    	 List<Cluster> list=dn1.getClustersForLevel(dn1.getBestLevel());
    	 for(int i=0;i<list.size();i++)
    		 System.out.println(i+"\t the number of elements in each cluster \t"+list.get(i).size());
    	 //dn.print(dn.getTopLevel()-2);
    	 System.out.println("the numbr of partitions\t"+dn.getClustersForLevel(dn.getTopLevel()-1).size());
    }
   /* public static void match(RBGModelImpl sg1, RBGModelImpl sg2)
    {
        // step1: obtain links and elements for each schema graph

        // step 2: get a set of clusters for each schema grapha
    	HierarchicalAlgorithm ca=new HierarchicalAlgorithm(sg1);
        Dendrogram dnd1 = ca.cluster();
        List<Cluster> clusterSet1=dnd1.getClustersForLevel(dnd1.getBestLevel());
       // dnd1.printAll();
        // dnd1.print(dnd1.getBestLevel());

        //clustering of the second schema graph
        ca=new HierarchicalAlgorithm(sg2);
      //  Dendrogram dnd2 = ca.cluster();
      //  List<Cluster> clusterSet2=dnd2.getClustersForLevel(dnd2.getBestLevel());
       // dnd2.printAll();
        // dnd2.print(dnd2.getBestLevel());
      //   System.out.println("the number of partitions----"+dnd2.getClustersForLevel(dnd2.getTopLevel()).size());
         //Another method for partitioing 
         LinkGenerator LG=new LinkGenerator(sg2);
         ArrayList<NodeImpl> nodes=LG.getNodes(); 
         HashMap links=LG.getLinks();
         ArrayList link=LG.getSortedLink();
         ClusterGenerator CG=new ClusterGenerator();
         CG.init(nodes, link, links);
         Dendrogram dn=CG.cluster();
        // dn.print(dn.getTopLevel()-1);
         System.out.println("the number of partitions----"+dn.getClustersForLevel(dn.getBestLevel()).size());
        // finding the similar fragments (clusters)
       // getSimilarClusters(clusterSet1,clusterSet2);
   }*/

  /*  public static void getSimilarClusters(List<Cluster> clusters1, List<Cluster> clusters2)
    {
        Jaccard sim=new Jaccard();
        for(int i=0;i<clusters1.size();i++)
        {
            Cluster c1=clusters1.get(i);
            String s1=c1.getElementsAsStringSim();
            //list1.add(i,c1.getElementsAsString());
            double max=0; int k=-1;
            Cluster c=null;
            for(int j=0;j<clusters2.size();j++)
            {
                Cluster c2=clusters2.get(j);
                String s2=c2.getElementsAsStringSim();
                //list1.add(j,c2.getElementsAsString());
                double s= sim.score(s1, s2);
                if(s>max) {max=s; k=j;c=c2;}
                //System.out.println("the similarity between:"+i+"---"+j+"---"+s);
            }
          //  nodeMatch(c1,c);
            //nodeMatch(c1,clusters2.get(k));
            System.out.println("the similarity between:"+i+"---"+k+"---"+max);
       }
   }

    
	}*/


   }
