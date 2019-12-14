package  fusion.comerger.general.ontoTograph;

import agg.attribute.AttrInstance;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.xt_basis.Arc;
import agg.xt_basis.Graph;
import agg.xt_basis.Node;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;


public class GraphConstruction 
{
	
	// Method that creates an arc
    public void createArc (Graph graph, Type arcName, Node nodeS, Node nodeT) throws TypeException
    {    	
    	if(nodeS!=null && nodeT!=null)
    	graph.createArc(arcName, nodeS, nodeT);
    }
    
    // Method that creates a typed arc	
    public void createArc (Graph graph, Type arcName, Node nodeS, Node nodeT, String value) throws TypeException
    {    	
    	if(nodeS!=null && nodeT!=null)
    	{
    	Arc arc= graph.createArc(arcName, nodeS, nodeT); 

    	// Set attribute values
    	AttrInstance attrInstance = null;
    	ValueTuple vt = null;
    	ValueMember vm = null;		

    	if (arc != null) 
    	{
    		attrInstance = arc.getAttribute();
    		// Set values of attributes
    		vt = (ValueTuple) attrInstance;
    		vm = (ValueMember) vt.getMemberAt(0);	
    		vm.setExprAsObject(value);			
    		vm.checkValidity();    				
    	}
     }
    }
    
    // Method that creates a node without attributes
    public Node createNode (Graph graph, Type nodeType)
    {    	
		Node node= null;
		
		try 
		{
			node = graph.createNode(nodeType);
		} 
		catch (TypeException ex) {
			System.out.println("Create node1 failed! " + ex.getMessage());
		}
		
		return node;
    }    
    
    // Method that creates a node with an one attribute
    public Node createNode (Graph graph, Type nodeType, String nodeName)
    {   
    	Node node=createNode (graph, nodeType);		
    	
		// Set attribute values
		AttrInstance attrInstance = null;
		ValueTuple vt = null;
		ValueMember vm = null;	

		if (node != null) 
		{
			attrInstance = node.getAttribute();
			// Set values of attributes
			vt = (ValueTuple) attrInstance;
		
			vm= (ValueMember) vt.getMemberAt(0);	
			vm.setExprAsObject(nodeName);			
			vm.checkValidity();				
		}
		return node;
    }
    
    // Method that creates a node with attributes : case of "Entity" Node 
    public Node createNode (Graph graph, Type nodeType, String nodeIRI, String nodeName)
    {   
    	Node node=createNode (graph, nodeType, nodeIRI);    	
		
		// Set attribute values
		AttrInstance attrInstance = null;
		ValueTuple vt = null;	
		ValueMember vm2 = null;	

		if (node != null) 
		{
			attrInstance = node.getAttribute();
			// Set values of attributes		
			vt = (ValueTuple) attrInstance;			
			vm2 = (ValueMember) vt.getMemberAt(1);	
			vm2.setExprAsObject(nodeName);			
			vm2.checkValidity();
		}
		
		return node;
    }   
      
       
    // Method that creates a node with attributes : case of "CardinalityRestrction"
    public Node createNode (Graph graph, Type nodeType, String description,String typeCardinality, int valueCardinality)
    {   
    	Node node=createNode ( graph,  nodeType, description);
		
		// Set attribute values
		AttrInstance attrInstance1 = null;
		AttrInstance attrInstance2 = null;
		ValueTuple vt1 = null;
		ValueMember vm1 = null;	
		
		ValueTuple vt2 = null;
		ValueMember vm2 = null;	

		if (node != null) 
		{
			attrInstance1 = node.getAttribute();
			// Set values of attributes
			vt1 = (ValueTuple) attrInstance1;
			vm1 = (ValueMember) vt1.getMemberAt(1);			
			vm1.setExprAsObject(typeCardinality);			
			vm1.checkValidity();	
			
			attrInstance2 = node.getAttribute();
			// Set values of attributes
			vt2 = (ValueTuple) attrInstance2;
			vm2 = (ValueMember) vt2.getMemberAt(2);			
			vm2.setExprAsObject(valueCardinality);			
			vm2.checkValidity();	
		}
		return node;
    }  
    
    // Method that creates a node with attributes : case of "Property"
    public Node createNode (Graph graph, Type nodeType, String nodeIRI, String nodeName, boolean propFunctional)
    {   
    	Node node=createNode ( graph,  nodeType, nodeIRI, nodeName);
    		
    	// Set attribute values
    	AttrInstance attrInstance = null;
    	ValueTuple vt = null;
    	ValueMember vm = null;		

    	if (node != null) 
    	{
    		attrInstance = node.getAttribute();
    		// Set values of attributes
    		vt = (ValueTuple) attrInstance;
    		vm = (ValueMember) vt.getMemberAt(2);			
    		vm.setExprAsObject(propFunctional);			
    		vm.checkValidity();	
    	}
    	return node;
    }  
       
    //Method that creates a node with attributes : case of "ObjectProperties"
    public Node createNode (Graph graph, Type nodeType, String nodeIRI, String nodeName, boolean [] propObjProp)
    {   
    	Node node=createNode ( graph, nodeType,nodeIRI, nodeName);
    		
        for(int i=0; i< propObjProp.length; i++)
        {
        	// Set attribute values
        	AttrInstance attrInstance = null;
        	ValueTuple vt = null;
        	ValueMember vm = null;		

        	if (node != null) 
        	{
        		attrInstance = node.getAttribute();
        		// Set values of attributes
    			vt = (ValueTuple) attrInstance;
    			vm = (ValueMember) vt.getMemberAt(i+2);			
    			vm.setExprAsObject(propObjProp[i]);			
    			vm.checkValidity();	
        	}
        }
    	return node;
    }  
}
