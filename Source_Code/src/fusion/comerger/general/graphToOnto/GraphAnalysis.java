package fusion.comerger.general.graphToOnto;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/
import java.util.Enumeration;
import java.util.Iterator;

import java.util.Vector;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;

public class GraphAnalysis 
{	
	private Graph hostGraph;
	
	public GraphAnalysis (GraGra grammar)
	{	
		hostGraph= grammar.getGraph();	
	}
	
	// m�thode qui retourne les noeuds de type Classes
	public  Enumeration<GraphObject> getClasses()
	{
		return hostGraph.getElementsOfType("Class");		
	}
	
	
	public  Enumeration<GraphObject> getComplementC()
	{
		return hostGraph.getElementsOfType("ComplementClass");			
	}
	
	public  Vector <GraphObject>  getRestriction()
	{
		Vector <GraphObject>  rest =  getCardinalityRestriction();
		rest.addAll(getSomeValueFrom());
		rest.addAll(getAllValueFrom());
		rest.addAll(getHasValue());
		
		return rest	;
	}
	
	
	public  Vector<GraphObject>  getCardinalityRestriction()
	{
		return hostGraph.getElementsOfTypeAsVector("CardinalityRestriction");		
	}
	

	public  Vector <GraphObject>  getRestrictionValue()
	{
		return hostGraph.getElementsOfTypeAsVector("RestrictionValue");		
	}
	
	
	public  Vector <GraphObject>  getSomeValueFrom()
	{
		return hostGraph.getElementsOfTypeAsVector("SomeValuesFrom");		
	}
	
	
	public  Vector<GraphObject>  getAllValueFrom()
	{
		return hostGraph.getElementsOfTypeAsVector("AllValuesFrom");		
	}
	
	public  Vector <GraphObject>  getHasValue()
	{
		return hostGraph.getElementsOfTypeAsVector("HasValue");		
	}
		
	public  Enumeration<GraphObject>  getIndividual()
	{
		return hostGraph.getElementsOfType("Individual");
	}	
	
	
	public  Enumeration<GraphObject> getIntersectionC()
	{
		return hostGraph.getElementsOfType( "IntersectionClass");
	}
	
	
	public  Enumeration<GraphObject> getUnionC()
	{
		return hostGraph.getElementsOfType("UnionClass");		
	}
	
	
	public  Enumeration<GraphObject> getEnumC()
	{
		return hostGraph.getElementsOfType( "EnumerateClass");	
	}
	
	
	public  Enumeration<GraphObject> getObjectProeprty()
	{
		return hostGraph.getElementsOfType( "ObjectProperty");
	}
	
	
	public  Enumeration<GraphObject> getDataProperty()
	{
		return hostGraph.getElementsOfType( "DataProperty");
	}
	
	
	public Node getNode(Graph graph,  String nodeName)
	{   			   
		Iterator <Node>nodeSet =graph.getNodesSet().iterator();
	    	
	    while( nodeSet.hasNext())                 
	    {
	    	Node  node= nodeSet.next();	    	
	    	if(node.getType().getStringRepr().equals(nodeName)) 	    		    			
	    			return node; 
	    }
	    	
	    return null;
	 }    	    
	
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
}
