package fusion.comerger.algorithm.partitioner.hierClust;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.*;

import org.semanticweb.owlapi.model.OWLClass;

//import opt.general.model.impl.NodeImpl;


public class Cluster {

    private int cid;
    private ArrayList<OWLClass> elements;
    private double intraSim;
    private HashMap interSim;


    // Empty cluster with no elements.
    public Cluster() {
        init(-1);
      }

    public Cluster(int id) {
        init(id);
      }
    public Cluster(int id, OWLClass e)
    {
    	init(id);
    	add(e);
    }

    public Cluster(int id, Collection<OWLClass> elements) {
        init(id);
        for(OWLClass e : elements) {
            add(e);
        }
    }

    // New cluster that contains all elements from provided clusters.
    public Cluster( Cluster c1, Cluster c2) {
        init(-1);
        add(c1);
        add(c2);
    }

    public Cluster(Collection<OWLClass> elements) {
        init(-1);
        for(OWLClass e : elements) {
            add(e);
        }
    }

    public Cluster(OWLClass element) {
        init(-1);
        add(element);
    }


    private void init(int label) {
        this.cid = label;
        elements = new ArrayList<OWLClass>();
        intraSim=0;
        interSim=new HashMap();
    }

    public int getLabel() {
        return cid;
    }

    public int size() {
        return elements.size();
    }


    public Cluster copy() {
        Cluster copy = new Cluster();
        for(OWLClass e : this.getElements()) {
            // DataPoint is immutable. No need to create a copy.
            copy.add(e);
        }
        return copy;
    }


    /**
     * Modifies existing cluster by adding all elements from provided cluster.
     *
     * @param c
     */
    public void add(Cluster c) {
        for(OWLClass e : c.getElements() ) {
            elements.add(e);
        }
    }

    /**
     * Modifies existing cluster by adding a new element.
     *
     * @param e
     */
    public void add(OWLClass e) {
        elements.add(e);
    }

    public boolean contains(Cluster c) {
        boolean result = true;
        for(OWLClass e : c.getElements()) {
            if( !contains(e) ) {
                result = false;
                break;
            }
        }
        return result;
    }

    public boolean contains(OWLClass e) {
        return elements.contains(e);
    }

    public ArrayList<OWLClass> getElements() {
        return new ArrayList<OWLClass>(elements);
    }


    public String getElementsAsString() {
        StringBuffer buf = new StringBuffer();
        for(OWLClass e : elements) {
            if( buf.length() > 0 ) {
                buf.append(",\n");
            }
            buf.append(e.toString().substring(e.toString().lastIndexOf("#")+1));
        }
       // buf.append(":"+intraSim);
        return "{" + buf.toString() + "}";
    }

    // this method used through similar cluster finding
     public String getElementsAsStringSim() {
        StringBuffer buf = new StringBuffer();
        for(OWLClass e : elements) {
            if( buf.length() > 0 ) {
                buf.append(" ");
            }
            buf.append(e.toString().substring(e.toString().lastIndexOf("#")+1)); 
        }

        return "{" + buf.toString() + "}";
    }

    @Override
        public String toString() {
        return getElementsAsString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((elements == null) ? 0 : elements.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Cluster other = (Cluster) obj;
        if (elements == null) {
            if (other.elements != null)
                return false;
        } else if (!elements.equals(other.elements))
            return false;
        return true;
    }

    // methods to adapt the intrasimilarity feature

    public void setIntraSim(double sim)
    {
        this.intraSim=sim;
    }

    public double getIntraSim()
    {
        return intraSim;
    }

    public double getInterSim(int id) {
        return (Double)interSim.get(id);
    }
    
    public HashMap getInterSim()
    {
    	return interSim;
    }

    public void setInterSim(int id, double sim) {
        this.interSim.put(id,sim);
    }
}
