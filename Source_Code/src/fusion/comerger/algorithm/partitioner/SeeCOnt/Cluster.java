
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
