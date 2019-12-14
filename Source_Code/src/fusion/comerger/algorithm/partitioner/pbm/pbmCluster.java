package fusion.comerger.algorithm.partitioner.pbm;

import java.util.HashMap;
import java.util.Iterator;

import fusion.comerger.model.Node;

public class pbmCluster
{
    private int cid = -1;
    private double cohesion = 0;
    private HashMap<Integer, Double> couplings = null;
    private HashMap<String, Node> elements = null;

    public pbmCluster(int id)
    {
        cid = id;
        couplings = new HashMap<Integer, Double>();
        elements = new HashMap<String, Node>();
    }

    public int getClusterID()
    {
        return cid;
    }

    public double getCohesion()
    {
        return cohesion;
    }

    public HashMap<Integer, Double> getCouplings()
    {
        return couplings;
    }

    public Iterator<Double> listCouplings()
    {
        return couplings.values().iterator();
    }

    public HashMap<String, Node> getElements()
    {
        return elements;
    }

    public Iterator<Node> listElements()
    {
        return elements.values().iterator();
    }

    public void setClusterID(int id)
    {
        cid = id;
    }

    public void setCohesion(double c)
    {
        cohesion = c;
    }

    public void putCoupling(int id, double similarity)
    {
        couplings.put(id, similarity);
    }

    public void putElement(String key, Node node)
    {
        elements.put(key, node);
    }
}
