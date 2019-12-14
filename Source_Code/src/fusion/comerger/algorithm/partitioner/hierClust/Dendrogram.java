package fusion.comerger.algorithm.partitioner.hierClust;


import java.util.*;
public class Dendrogram {

    /*
     * Clusters by level.
     */
    private Map<Integer, ClusterSet> entryMap;
    private Map<Integer, String> levelLabels;
    private Integer nextLevel;
    private String levelLabelName;
   // private HashMap<Integer, Double> module;
    private HashMap<Integer, Double> intraSim;


    public Dendrogram(String levelLabelName) {
        entryMap = new LinkedHashMap<Integer, ClusterSet>();
        levelLabels = new LinkedHashMap<Integer, String>();
        nextLevel = 1;
        this.levelLabelName = levelLabelName;
     //   module=new HashMap();
        intraSim=new HashMap();

    }

    public int addLevel(String label, Cluster cluster) {
        List<Cluster> values = new ArrayList<Cluster>();
        values.add(cluster);
        return addLevel(label, values);
    }

  /*  public void addModel( double mod)
    {
        module.put(nextLevel,(Double)mod);

    }*/

    public void addIntra(int l,double sim)
    {
       intraSim.put(l,sim);
    }

    /**
     * Creates a new dendrogram level using copies of provided clusters.
     */
    public int addLevel(String label, Collection<Cluster> clusters) {

        ClusterSet clusterSet = new ClusterSet();

        for(Cluster c : clusters) {
            // copy cluster before adding - over time cluster elements may change
            // but for dendrogram we want to keep current state.
            clusterSet.add(c.copy());
        }

        int level = nextLevel;
        entryMap.put(level, clusterSet);
        levelLabels.put(level, label);
        nextLevel++;
        return level;
    }

    /**
     * Replaces clusters in the specified level. If level doesn't exist it will
     * be created.
     *
     * @param level dendrogram level.
     * @param label level description.
     * @param clusters clusters for the level.
     * @return
     */
    public void setLevel(int level, String label, Collection<Cluster> clusters) {

        ClusterSet clusterSet = new ClusterSet();
        for(Cluster c : clusters) {
            clusterSet.add(c.copy());
        }

        System.out.println("Setting cluster level: "+level);
        entryMap.put(level, clusterSet);
        levelLabels.put(level, label);

        if( level >= nextLevel ) {
            nextLevel = level + 1;
        }
    }


    public void printAll() {
        for(Map.Entry<Integer, ClusterSet> e : entryMap.entrySet()) {
            Integer level = e.getKey();
            print(level);
        }
    }

    public void print(int level) {
        String label = levelLabels.get(level);
        ClusterSet clusters = entryMap.get(level);
        System.out.println("Clusters for: level=" + level + ", " +
                levelLabelName + "=" + label);
        for(Cluster c : clusters.getAllClusters()) {
                if (c.getElements().size() >= 1) {
                System.out.println("____________________________________________________________\n");
            System.out.println(c.getElementsAsString());
               // System.out.println("____________________________________________________________\n\n");
               }
        }
        System.out.println(intraSim.get(level)+"____________________________________________________________\n\n");
    }

    public int getTopLevel() {
        return nextLevel - 1;
    }

    public List<Integer> getAllLevels() {
        return new ArrayList<Integer>( entryMap.keySet() );
    }

    public String getLabelForLevel(int level) {
        return levelLabels.get(level);
    }

    public List<Cluster> getClustersForLevel(int level) {
        ClusterSet cs = entryMap.get(level);
        return cs.getAllClusters();
    }

    public int getBestLevel()
    {
          Iterator values=intraSim.values().iterator();
          double max=0; int i=0,k=0;
          while(values.hasNext())
          {
              double sim=(Double)values.next();
              if(sim>max) {k=i;}// max=sim;}   //not correct should be corrected
              i++;
          }
        return k;
    }


  /*  public double calcModule(int level, SchemaGraph sg)
    {
         ClusterSet cs = entryMap.get(level);
        List<Cluster> list=cs.getAllClusters();
        double L=(double)sg.getEdgesCount();
        double sum=0;
        for(int i=0;i<list.size();i++)
        {
           Cluster c1=list.get(i);
            int deg=0,ls=0;
           Iterator  element=c1.getElements().iterator();
            while(element.hasNext())
            {
                VertexImpl vertex=(VertexImpl)element.next();
                ls+=sg.getEdges(vertex).size();
                deg+=sg.getDegree(vertex);
                //System.out.println(vertex.getLabel()+"= = ="+ls+"----"+deg);
            }
            double s= ls/L - Math.pow(deg/(2*L), 2);
            sum+=s;
        }
        return sum;
    }  */


    public double computeIntraSim(List<Cluster> list)
    {

        double sum=0; int count=0;
        for(Cluster c:list) //int i=0;i<list.size();i++)
        {
           if(c.size()>1)
            {
              sum+=c.getIntraSim()*c.size();
              count+=c.size();
            }

        }
      return sum/(double)(count);
    }

   public double computeInterSim(List<Cluster> clusters)
        {
            double finalSim = 0.0;

            for(Cluster clusterA : clusters)
            {
                for(Cluster clusterB : clusters)
                {
                   // finalSim+=(((clusterA.size()+1.0)*interClusterSimilarity(clusterA, clusterB))/_instances.size());
                }
            }
            return finalSim;
        }




}
