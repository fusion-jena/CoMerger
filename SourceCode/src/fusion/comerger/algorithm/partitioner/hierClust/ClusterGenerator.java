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
import java.util.ArrayList;
import java.util.HashMap;



import java.util.List;

import org.semanticweb.owlapi.model.OWLClass;

//import opt.general.model.impl.NodeImpl;



	
	/**
	 * Created by IntelliJ IDEA.
	 * User: algergawy
	 * Date: 11.03.2011
	 * Time: 10:27:30
	 * To change this template use File | Settings | File Templates.
	 */
	public class ClusterGenerator {
	    private ClusterSet allClusters;
	    private Dendrogram dnd;
	    private double d;
	    ArrayList<Link> links;
	    HashMap tLinks;

	    public ClusterGenerator()
	    {
	        allClusters=new ClusterSet();
	        dnd=new Dendrogram("Distance");
	        d=0;
	        links=new ArrayList<Link>();
	    }

	    public void init(ArrayList<OWLClass> elements, ArrayList<Link> links,HashMap tLinks)
	    {
	        this.links=links;
	        this.tLinks=tLinks;
	        for (int cid = 0, n = elements.size(); cid < n; cid++) {
	        	OWLClass vertex =  elements.get(cid);
	            Cluster cluster = new Cluster(cid);
	            cluster.setIntraSim(1);        // this is not the correct
	            cluster.add(vertex);
	            allClusters.add(cluster);
	            
	        }
	         dnd.addLevel(String.valueOf(d),allClusters.getAllClusters());
	        // dnd.printAll();
	       // System.out.println("the cluster size--->"+allClusters.size());
	       // System.out.println("the links size--->"+links.size());
	    }

	    public Dendrogram cluster()
	    {
	        int l=dnd.getTopLevel();
	        double m=0;

	       //step2: building the cluster hierarchy
	       d = 1.0;
	       while( allClusters.size() > 1)
	       {
	            int K = allClusters.size();
	            mergeClusters();
	            // it is possible that there were no clusters to merge for current d.
	            if( K > allClusters.size())
	            {
	            	l=dnd.addLevel(String.valueOf(d), allClusters.getAllClusters());
	               // m=dnd.computeIntraSim(allClusters.getAllClusters());
	               // if(m < Parameters.thr_intra) break;
	               // dnd.addIntra(l,m);
	                // dnd.print(l);
	                //System.out.println("hi---"+m);
	                K = allClusters.size();
	            }
	            if(links.size()==0) break;  //
	            d = d + Parameters.dendLevel;
	         // System.out.println(links.size()+"--the cluster--size--"+allClusters.size());
	         }
	        process();
	        System.out.println(links.size()+"--the cluster--size--"+allClusters.size());
	        dnd.addLevel(String.valueOf(d), allClusters.getAllClusters());
	        return dnd;

	    }

	    public void mergeClusters()
	    {
	        ArrayList removal=new ArrayList();
	        ArrayList list=new ArrayList();
	        for(int i=0;i<links.size();i++)
	        {
	            Link link=(Link) links.get(i);
	            OWLClass sv=link.getVertex1();
	            OWLClass tv=link.getVertex2();
	            if(!removal.contains(sv))
	            {
	                if(!removal.contains(tv))
	                {
	                    Cluster c1=allClusters.findClusterByElement(sv);
	                    Cluster c2=allClusters.findClusterByElement(tv);
	                    Cluster c=new Cluster(c1,c2);
	                    if(c.size() <= 500)
	                    {
	                    allClusters.add(c);
	                    allClusters.remove(c1);
	                    allClusters.remove(c2);
	                    }               
	                    removal.add(sv);
	                    removal.add(tv);
	                    if(c1.size()>1 || c2.size()>1)
	                    {
	                        if(c1.size()>1)removal.addAll(c1.getElements());
	                        if(c2.size()>1)removal.addAll(c2.getElements());
	                        ArrayList<OWLClass> cvertex=c1.getElements();
	                        for(int k=0;k<cvertex.size();k++)
	                        {
	                        	OWLClass v1=cvertex.get(k);
	                            ArrayList<OWLClass> cvertex2=c2.getElements();
	                            for(int kk=0;kk<cvertex2.size();kk++)
	                            {
	                            	OWLClass v2=cvertex2.get(kk);
	                                String key1=v1.toString().concat(v2.toString());// .getLabel().concat(",").concat(v2.getLabel());
	                                String key2=v2.toString().concat(v1.toString());//.getLabel().concat(",").concat(v1.getLabel());
	                                Link link1=(Link)tLinks.get(key1);
	                                if(link1!=null)  list.add(link1);
	                                Link link2=(Link)tLinks.get(key2);
	                                if(link2!=null) list.add(link2);
	                            }
	                        }
	                    }

	                 //   System.out.println(c1.size()+": the index:"+c2.size()+":"+c.size()+":"+allClusters.size());
	                    list.add(link);
	              }
	            }
	             // if(removal.contains(sv) && removal.contains(tv))
	              //          list.add(link);

	        }
	        for(int i=0;i<list.size();i++)
	        {
	           links.remove(list.get(i));
	        }
	       // System.out.println(list.size()+":the no. of links:"+links.size());
	    }

	    private void process()
	    {
	       List<Cluster> clusters=allClusters.getAllClusters();
	        Cluster temC=new Cluster();
	        for(int i=0;i<clusters.size();i++)
	        {
	            Cluster c=clusters.get(i);
	            if(c.size()<=5)
	            {
	                //temC.add(c);
	                allClusters.remove(c);
	            }

	        }
	        //if(temC.size()<500) allClusters.add(temC);

	    }
	}


