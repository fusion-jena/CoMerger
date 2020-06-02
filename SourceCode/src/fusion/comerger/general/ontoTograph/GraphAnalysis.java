package fusion.comerger.general.ontoTograph;
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
import java.util.Iterator;
import java.util.List;

import agg.xt_basis.Arc;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.Node;
import agg.xt_basis.Type;

public class GraphAnalysis 
{	    
	// Method that return the node equivalent to the name the name specified in the parameters 
	public Node getNode(Graph graph,  String nodeName, String nameAttribute)
	{    	
		Iterator <Node>nodeSet =graph.getNodesSet().iterator();
	    	
	    while( nodeSet.hasNext())                 
	    {
	    	Node  node= nodeSet.next();
	    	
	    	if(node.getType().getStringRepr().equals(nodeName)) 
	    		if(nameAttribute ==null)
	    			return node;
	    	
	    		else if((node.getAttribute().getMemberAt(0).toString()).equals("\""+nameAttribute+"\""))	    			
	    			return node; 
	    	}
	    	
	    	return null;
	    }    	    
	
	 	// Method return the type of a node 	
		public Type getTypNode(Graph typeGraph, String nodeName)
	    {
	    	Iterator <Node>nodeSet =typeGraph.getNodesSet().iterator();
	    	
	    	while( nodeSet.hasNext())                
	    	{
	    		Node  node= nodeSet.next();
	    		if(node.getType().getStringRepr().equals(nodeName))    		
	    			return node.getType();
	    	}
	    	return null;
	    }      
	    
	    // Method that return the type of an edge
		public Type getArcType(Graph graph, String arcName)
	    {
	    	Iterator <Arc>arcSet =graph.getArcsSet().iterator();  
	    	while( arcSet.hasNext())                
	    	{
	    		Arc  arc= arcSet.next();
	    		if(arc.getType().getStringRepr().equals( arcName))	    	
	        		return arc.getType();	    		
	    	}
	    	return null;   	
	    } 
	    
	   
	    // Method that returns the different datatype in the graph	    
	    public List <Node> getDataTypGraph(GraGra gram)
	    {
	    	return gram.getGraph(0).getNodes(gram.getTypeSet().getTypeByName("DataType"));	    	
	    }
}
