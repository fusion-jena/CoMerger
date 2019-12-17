package fusion.comerger.algorithm.partitioner.SeeCOnt;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;


import java.util.List;

import fusion.comerger.model.Node;


public class Cluster
{
    private int cid = -1;   
    private LinkedHashMap<String, Node> elements = null;
    private Node clusterCH;
    private List<String> listName;
   
    public Cluster(int id)
    {
        cid = id;
        elements = new LinkedHashMap<String, Node>();
        listName=new ArrayList<String>();
     }
    
    public Cluster(int id, Node CH)
    {
    	cid = id;
    	this.clusterCH=CH;
        elements = new LinkedHashMap<String, Node>();
        listName=new ArrayList<String>();
    }
    
    public void setCH(Node CH)
    {
    	this.clusterCH=CH;
    }
    
    public Node getCH()
    {
    	return clusterCH;
    }

    public int getClusterID()
    {
        return cid;
    }

    public LinkedHashMap<String, Node> getElements()
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

    public void putElement(String key, Node node)
    {
        elements.put(key, node);
    }
    
    public void printCluster()
    {
    	Iterator<Node> it=listElements();
    	while(it.hasNext())
    	{
    		Node nn=it.next();
//    		System.out.println(nn.getLocalName()+"\n");
    		System.out.println(nn.getLocalName());
    	}
    	
    }
    
    public List<String> getlistName()
    {
    	Iterator<Node> it=listElements();
    	while(it.hasNext())
    	{
    		Node nn=it.next();
    		listName.add(nn.getLocalName());
    	}
    	return listName;
    }
    
    public int getSize()
    {
    	return elements.size();
    }
}
