package fusion.comerger.algorithm.partitioner.hierClust;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;




public class HierarchicalAlgorithm {

    private ArrayList<OWLClass>  elements;
    private HashMap links;
    private ClusterSet allClusters;
    private int index;
    public HierarchicalAlgorithm(){}
    
    public HierarchicalAlgorithm(OWLOntology sg)
    {
    	LinkGenerator LG=new LinkGenerator(sg);
    	links=LG.getLinks();
    	//System.out.println("The number of links---"+links.size());
    	elements=LG.getNodes();
    	this.allClusters=new ClusterSet();
    	index=0;
    }
    
     

    public Dendrogram cluster()
    {
       Dendrogram dnd = new Dendrogram("Distance");
        double d = 0.0;

        // step1: initially load all elements as individual clusters
        for(OWLClass e : elements)
        {
            Cluster c = new Cluster(e);
            allClusters.add(c);
         }
         int l=dnd.addLevel(String.valueOf(d), allClusters.getAllClusters());
         double m=0;//dnd.calcIntra(allClusters.getAllClusters());  // the bottom level
         dnd.addIntra(l,m);

        //step2: building the cluster hierarchy
        d = 1.0;
       //System.out.println("the cluster----"+allClusters.size());
       while( allClusters.size() > 1)
       {
            int K = allClusters.size();
            mergeClusters(d);
            // it is possible that there were no clusters to merge for current d.
            if( K > allClusters.size())
            {
            	//System.out.println("the cluster--size--"+K);
            	l=dnd.addLevel(String.valueOf(d), allClusters.getAllClusters());
                m=dnd.computeIntraSim(allClusters.getAllClusters());
                if(m < Parameters.thr_intra) break;
                dnd.addIntra(l,m);
                //System.out.println("hi---"+m);
                K = allClusters.size();
            }
            if(index==0) break;  //
            d = d + Parameters.dendLevel;
        }
        process();
        return dnd;
    }

    private void mergeClusters(double distanceThreshold)
    {
        int nClusters = allClusters.size();
     //  List<Cluster> clusters=allClusters.getAllClusters();
        ObjectToIndexMapping<Cluster> idxMapping = new ObjectToIndexMapping<Cluster>();

        double[][] clusterSim = new double[nClusters][nClusters];
        for(int i = 0, n = elements.size(); i < n; i++) {
        	OWLClass e1 = elements.get(i);
        	for(int j = i + 1, k = elements.size(); j < k; j++) {
        		OWLClass e2 = elements.get(j);
                String key=e1.toString().concat(e2.toString());//.getLabel().concat(",").concat(e2.getLabel());
                String key2=e2.toString().concat(e1.toString());//.getLabel().concat(",").concat(e1.getLabel());
                Link link=(Link)links.get(key);
                Link link2=(Link)links.get(key2);
                if(link!=null){
                double d = link.getSimilarity();    // double d2 = link2.getSimilarity();
                if( d > Parameters.linkCutoff  ) {
                    Cluster c1 = allClusters.findClusterByElement(e1);
                    Cluster c2 = allClusters.findClusterByElement(e2);
                    if( !c1.equals(c2) ) {
                        int ci = idxMapping.getIndex(c1);
                        int cj = idxMapping.getIndex(c2);
                        clusterSim[ci][cj] += d;
                        clusterSim[cj][ci] += d;
                    }
                }
            }
               else if(link2!=null){
                double d2 = link2.getSimilarity();    // double d2 = link2.getSimilarity();
                if( d2 > Parameters.linkCutoff  ) {
                    Cluster c1 = allClusters.findClusterByElement(e1);
                    Cluster c2 = allClusters.findClusterByElement(e2);
                    if( !c1.equals(c2) ) {
                        int ci = idxMapping.getIndex(c1);
                        int cj = idxMapping.getIndex(c2);
                        clusterSim[ci][cj] += d2;
                        clusterSim[cj][ci] += d2;
                    }
                }
            }
        }
        }
        index=idxMapping.getSize();
        boolean[] merged = new boolean[clusterSim.length];
        for(int i = 0, n = clusterSim.length; i < n; i++) {
            for(int j = i + 1, k = clusterSim.length; j < k; j++) {
                Cluster ci = idxMapping.getObject(i);
                Cluster cj = idxMapping.getObject(j);
                if(ci!=null && cj!=null){
                int ni = ci.size();
                int nj = cj.size();
                clusterSim[i][j] = clusterSim[i][j] / (ni+nj);
                clusterSim[j][i] = clusterSim[i][j];
                // merge clusters if distance is below the threshold
                if( merged[i] == false && merged[j] == false ) {
                    if( clusterSim[i][j] >= 1/distanceThreshold) {
                    	Cluster mergedCluster = new Cluster(ci, cj);
                    	allClusters.remove(ci);
                        allClusters.remove(cj);
                        //  double s1=calcIntraSim(mergedCluster);
                        mergedCluster.setIntraSim(clusterSim[i][j]);
                        allClusters.add(mergedCluster);
                        merged[i] = true;
                        merged[j] = true;
                    }
                }
            }
            }
        }
    }


   public double calcIntraSim(Cluster c)
    {

       ArrayList<OWLClass> list1=c.getElements();
       if(list1.size()<=1) return 0;
       double sum=0,max;
        int count=0;
        for(int i=0;i<list1.size()-1;i++)
        {
        	OWLClass e1=list1.get(i);
            max=0;
             for(int j=i+1;j<list1.size();j++)
            {
            	 OWLClass e2=(OWLClass)list1.get(j);
                String key=e1.toString().concat(e2.toString());//.getLabel().concat(",").concat(e2.getLabel());
                String key1=e2.toString().concat(e1.toString());//.getLabel().concat(",").concat(e1.getLabel());
                Link link=(Link)links.get(key);
                Link link1=(Link)links.get(key1);
                if(link!=null){ double d = link.getSimilarity(); if(d>max) max=d;count++;}
                else if(link1!=null){ double d = link1.getSimilarity(); if(d>max) max=d;count++;}
            }
            sum+=max;
        }
       return sum/(double)count;
    }  

     public double calcInterSim(Cluster c1, Cluster c2,HashMap links)
    {

        ArrayList<OWLClass> elements1=c1.getElements();
        ArrayList<OWLClass> elements2=c2.getElements();
        double sum=0;
        for(OWLClass e1:elements1)
        {

             for(OWLClass e2:elements2)
            {
                String key=e1.toString().concat(e2.toString());// .getLabel().concat(",").concat(e2.getLabel());
                String key1=e2.toString().concat(e1.toString());//.getLabel().concat(",").concat(e1.getLabel());
                Link link=(Link)links.get(key);
                Link link1=(Link)links.get(key1);
                if(link!=null){ double d = link.getSimilarity();sum+=d;}
                else if(link1!=null){ double d = link1.getSimilarity(); sum+=d;}
            }

        }
       return sum/(double)(c1.size()+c2.size());
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

