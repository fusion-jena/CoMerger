package fusion.comerger.general.graphToOnto;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/
// import from jena
import org.apache.jena.ontology.AllValuesFromRestriction;
import org.apache.jena.ontology.CardinalityRestriction;
import org.apache.jena.ontology.ComplementClass;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.EnumeratedClass;
import org.apache.jena.ontology.HasValueRestriction;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.IntersectionClass;
import org.apache.jena.ontology.MaxCardinalityRestriction;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.ontology.Restriction;
import org.apache.jena.ontology.SomeValuesFromRestriction;
import org.apache.jena.ontology.UnionClass;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.ontology.MinCardinalityRestriction;

import org.apache.jena.ontology.*;

//import from AGG
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;
import agg.xt_basis.Arc;



import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class GraphToOntology 
{
	Graph graph, graphType;

	public String removeQuotNodeName(String nodeIRI )	
	{
		if(nodeIRI.endsWith("\""))    		
			nodeIRI= (nodeIRI.substring(1, nodeIRI.length()-1));
		return nodeIRI;
	}
	
	// Method that differentiates anonymous classes to the classical classes
	public  boolean isUrl(String urlString) 
	{
		try 
		{	
			new URI(urlString);
			return true;
		}
		
		catch (URISyntaxException e) {			
			return false;
		}
		
		catch (StackOverflowError kk)
		{
			return false;
		} 
		catch (Exception e)
		{
			return false;
		}
	}	

	// Method that retrieves the ID of the AnonymousEntity
	public String getIDAnon (String EntityName)
	{
		for( Set<String> anon: mapIsAnon)
		{
			if(anon.toArray()[0].equals(EntityName))			
				return anon.toArray()[1].toString() ;
			
			else if(anon.toArray()[1].equals(EntityName))			
				return anon.toArray()[0].toString() ;			
		}
		
		return null;		
	}
	
	
	public OntClass getClassAnon (String nameCls)
	{
		OntClass cls = model.getOntClass(nameCls) ;
		if(cls!=null )
			return  cls;
		
		List <OntClass> listCls= model.listClasses().toList();
		for( OntClass clss : listCls)			
			if(clss.isAnon())				
				if( clss.getId().getLabelString().equals(nameCls))		
				{
					return clss;
				}
		
		return null;
	}
	
	
	public Resource addResource (String typNode, Node node)
	{
		Resource r=null;
		if( typRest.contains( typNode) ) // if the Node is a restriction
			r=addRestriction(node);
		
		else if( typNode.equals("UnionClass"))			    		
			r=addUnionClass(node);
		
		else if( typNode.equals("IntersectionClass"))
			r=addIntersectionClass(node);
		
		else if( typNode.equals("ComplementClass"))			    		
			r=addComplementClass(node);			    		
		
		else if( typNode.equals("EnumerateClass"))
			r=addEnumeratedClass(node);
		
		return r;
	}
	
	
	OntModel model;
	GraGra grammar;
	GraphAnalysis  graphAna;
	Set<Set> mapIsAnon = new HashSet ();
	Set <String> typRest= new  HashSet() ;
	Set<String> typClass= new HashSet();
	
	public GraphToOntology (OntModel model, GraGra grammar) throws Exception
	{	
		this.model = model;
		this.grammar= grammar;
		graphType= grammar.getTypeGraph();		
		graph= grammar.getGraph();	
		
		typRest.add("SomeValuesFrom");
		typRest.add("HasValue");
		typRest.add("AllValuesFrom");
		typRest.add("CardinalityRestriction");
		
		typClass.add("Class");		
		typClass.add("UnionClass");
		typClass.add("IntersectionClass");	
		typClass.add("ComplementClass");
		typClass.add("EnumerateClass");
		typClass.containsAll(typRest);
		
		graphAna= new GraphAnalysis (grammar);
		 
		/**************************************************	
			Add primitives classes to the ontology model
		***************************************************/
		Enumeration<GraphObject> Nodeclass=graphAna.getClasses();
		while( Nodeclass.hasMoreElements())
		{
			Node clas= (Node) Nodeclass.nextElement();	   
	    	String nodeIRI= (String) clas.getAttribute().getValueAt("iri");    	
	    	model.createClass(nodeIRI); 
		}
				
		
		/**************************************************		
			Add ObjectProperties to the model of the ontology 
		**************************************************/		
		
		Enumeration<GraphObject> NodeOP=graphAna.getObjectProeprty();
		while( NodeOP.hasMoreElements())
		{
			Node clas= (Node) NodeOP.nextElement();	   
	    	String nodeIRI= (String) clas.getAttribute().getValueAt("iri"); 
	    	ObjectProperty	objProp = model.createObjectProperty(nodeIRI);  

	    	
	    	boolean functional= (Boolean) clas.getAttribute().getValueAt("functional");
	    	if( functional)
	    		objProp.convertToFunctionalProperty();
	    	
	    	boolean inversFunc= (Boolean) clas.getAttribute().getValueAt("inverseFunctional"); 
	    	if( inversFunc)
	    		objProp.convertToInverseFunctionalProperty();
	    	
	    	boolean transitivity= (Boolean) clas.getAttribute().getValueAt("transitive"); 	    	
	    	if(transitivity)
	    		objProp.convertToTransitiveProperty();	    	
	    	
	    	boolean symmetric= (Boolean) clas.getAttribute().getValueAt("symmetric"); 
	    	if(symmetric)
	    		objProp.convertToSymmetricProperty();
	    	
		}

		/**************************************************		
			Add DataProperties to the model of the ontology 
		**************************************************/		
		
		Enumeration<GraphObject> NodeDP=graphAna.getDataProperty();
		while( NodeDP.hasMoreElements())
		{
			Node clas= (Node) NodeDP.nextElement();	   
	    	String nodeIRI=(String) clas.getAttribute().getValueAt("iri");	 
	    	DatatypeProperty dataProp= model.createDatatypeProperty(nodeIRI);  
	    	
	    	boolean functional= (Boolean) clas.getAttribute().getValueAt("functional");
	    	if( functional)
	    		dataProp.convertToFunctionalProperty();
		}
		

		/**************************************************		
			Add EnumeratedClass to the model of the ontology 
		**************************************************/		
		
		Enumeration<GraphObject> NodeEnumC=graphAna.getEnumC();
		while( NodeEnumC.hasMoreElements())
		{
			Node EnumC= (Node) NodeEnumC.nextElement();	   
			addEnumeratedClass(EnumC);
		} 
		
		/**************************************************		
			Add UnionClass to the model of the ontology 
		**************************************************/		
		
		Enumeration<GraphObject> unionC=graphAna.getUnionC();
		while( unionC.hasMoreElements())
		{
			Node uc=(Node)unionC.nextElement();
			addUnionClass(uc);			
		}
		
		/**************************************************		
			Add IntersectionClass to the model of the ontology 
		**************************************************/		
		
		Enumeration<GraphObject>intersecC=graphAna.getIntersectionC();
		//IntersectionClass oo =null;
		while( intersecC.hasMoreElements())
		{
			Node ic=(Node)intersecC.nextElement();
			 addIntersectionClass( ic);
		}	
		
		/**************************************************		
			Add ComplementClass to the model of the ontology 
		**************************************************/	
		
		Enumeration<GraphObject> compleC=graphAna.getComplementC();
		while( compleC.hasMoreElements())
		{
			Node cc= (Node)compleC.nextElement();	
			addComplementClass(cc);
		}
				
						
		/**************************************************		
			Add Edges: The axioms to the model of the ontology 
		***************************************************/
		
		//extraction of the edges		
		HashSet<Arc> arcs = graph.getArcsSet();
		Iterator <Arc> listArcs = arcs.iterator();
		
		/**************************************************		
		    Add Individual to the model of the ontology 
        **************************************************/	
   
		Vector<Arc> indArc =graph.getArcs(grammar.getTypeSet().getTypeByName("memberOf"));
		for( Arc ind: indArc )
		{
		    String EntityNameS=removeQuotNodeName(ind.getSource().getAttribute().getValueAsString(0));
		    if(  !isUrl(EntityNameS))			    
		    	EntityNameS= getIDAnon(EntityNameS);
		 
		    String EntityNameT=removeQuotNodeName(ind.getTarget().getAttribute().getValueAsString(0));		
		    if(  !isUrl(EntityNameT))	
		    	EntityNameT= getIDAnon(EntityNameT);		    
		    
		    	OntClass classInd=getClassAnon(EntityNameT);	    		
	    		model.createIndividual(EntityNameS,classInd) ;		    
		}
					
		while(listArcs.hasNext())
		{
		    Arc a= listArcs.next();
		    String arcName=a.getType().getStringRepr();	
		    
		    String sourceType= a.getSourceType().getStringRepr();		
		    String targetType= a.getTargetType().getStringRepr();
		
		    String EntityNameS=removeQuotNodeName(a.getSource().getAttribute().getValueAsString(0));
		    if(  !isUrl(EntityNameS))			    
		    	EntityNameS= getIDAnon(EntityNameS);
		 
		    String EntityNameT=removeQuotNodeName(a.getTarget().getAttribute().getValueAsString(0));		
		    if(  !isUrl(EntityNameT))	
		    	EntityNameT= getIDAnon(EntityNameT);
		 
	
		    /**************************************************		
				Add Individual to the model of the ontology 
		    **************************************************/	
		   
		    
		     if(arcName.equals("objectPropertyAssertion"))
		    {
		    	String nameObjProp= (String)a.getAttribute().getValueAt("iriObjProp");		
		    	
		    	Individual ind1= model.getIndividual(EntityNameS);
	  			ind1.setPropertyValue(model.getObjectProperty(nameObjProp), model.getIndividual(EntityNameT));    		    		
		    }
		    
		    else if(arcName.equals("equivalentTo"))
		    {
		    	if(typClass.contains(sourceType) )
		    	{
		    		OntClass resS = getClassAnon(EntityNameS);	
		    		OntClass resT = getClassAnon(EntityNameT);		    		
		    		resS.addEquivalentClass( resT);   
		    	}
		    	
		    	if (sourceType.equals("ObjectProperty"))
			    {
			    	ObjectProperty resS= model.getObjectProperty(EntityNameS);			    	
			    	ObjectProperty resT= model.getObjectProperty(EntityNameT);			    					    		
				    resS.addEquivalentProperty(resT);	
			    }		    	

		    	else if (sourceType.equals("DataProperty"))
				{
		    		DatatypeProperty resS= model.getDatatypeProperty(EntityNameS);				  
					DatatypeProperty  resT= model.getDatatypeProperty(EntityNameT);				   		    		
					resS.addEquivalentProperty( resT );			    	
				}
		    	
		    	else if (sourceType.equals("Individual"))
				{
		    		Individual resS= model.getIndividual(EntityNameS);				  
					Individual resT= model.getIndividual(EntityNameT);				   		    		
					resS.addSameAs(resT);			    	
				}
		    }
		    
		    else if(arcName.equals("disjointWith"))
		    {
		    	if(typClass.contains(sourceType) )
		    	{
		    		OntClass resS = model.getOntClass(EntityNameS);	
		    		OntClass resT = model.getOntClass(EntityNameT);	
		    		resS.addDisjointWith( resT ); 
		    	}
		    	
		    	else if (sourceType.equals("ObjectProperty")|| sourceType.equals("DataProperty"))
			    {
			    	Property resS= model.getProperty(EntityNameS);			    	
			    	Property resT= model.getProperty(EntityNameT);			    					    		
				    ((OntResource) resS).addDifferentFrom(resT);	
			    }		    	
    	
		    	else if (sourceType.equals("Individual"))
				{
		    		Individual resS= model.getIndividual(EntityNameS);				  
					Individual resT= model.getIndividual(EntityNameT);				   		    		
					resS.addDifferentFrom(resT);
				}
		    }
		    
		    else if(arcName.equals("subClassOf"))
		    {		    	
		    	OntClass resS = getClassAnon(EntityNameS);	
		    	OntClass resT = getClassAnon(EntityNameT);	
		    	resT.addSubClass(resS ); 
		    }
		    
		    else if(arcName.equals("subProperty"))
		    {		    	
		    	if (sourceType.equals("ObjectProperty"))
			    {
			    	ObjectProperty resS= model.getObjectProperty(EntityNameS);			    	
			    	ObjectProperty resT= model.getObjectProperty(EntityNameT);			    					    		
				    resS.addSubProperty(resT);	
			    }		    	

		    	else if (sourceType.equals("DataProperty"))
				{
		    		DatatypeProperty resS= model.getDatatypeProperty(EntityNameS);				  
					DatatypeProperty  resT= model.getDatatypeProperty(EntityNameT);				   		    		
					resS.addSubProperty(resT );			    	
				}
		    }
		    
		    else if(arcName.equals("inverseTo"))
		    {		    	
		    	ObjectProperty resS= model.getObjectProperty(EntityNameS);			    	
			    ObjectProperty resT= model.getObjectProperty(EntityNameT);			    					    		
				resS.addInverseOf(resT);				   
		    }
		    			    
		   if (sourceType.equals("ObjectProperty"))
		   {
		    	ObjectProperty res= model.getObjectProperty(EntityNameS);
		    	
		    	if(typClass.contains(targetType))
		    	{		    			
		    		OntClass resT = getClassAnon(EntityNameT);		    		
		    		
			    	if(arcName.equals("domain") )			    		
			    		res.addDomain(resT );			    	
			   
			    	else if(arcName.equals("range") )				    		
			    		res.addRange(resT );
			    }	
		    }
		    	
		   else if (sourceType.equals("DataProperty"))
		   {
			   DatatypeProperty res= model.getDatatypeProperty(EntityNameS);
		    
			   if(typClass.contains(targetType))
			   {			    			
		    		OntClass resT = getClassAnon(EntityNameT);
		    		res.addDomain((OntClass) resT );	
			   }	
			    else if(targetType.equals("DataType"))
			   {	
				   if(arcName.equals("range") )		
				   {
						Resource range= model.getResource(EntityNameT);				    			    		
					    res.addRange(range)	;
				   }
			   } 
		   }	
		}
	
		/**************************************************		
			Add Restrictions
		***************************************************/
		
		Vector <GraphObject> restriction =graphAna.getRestriction();		
		for( GraphObject rest : restriction)
		{
			Node res=(Node)rest ;	
			addRestriction(res);
		}
		
	}
		
	
	/** Method : addRestriction */
	public  Restriction addRestriction(Node res)
	{
		String nodeName= res.getType().getStringRepr();
		String desRes=  (String) res.getAttribute().getValueAt("description");
		Iterator <Arc> arcOut =res.getOutgoingArcs();
    	
    	String cls2="";
    	String prop="";
    	String ind="";
    	Node nodeCls2= null;
    	
    	while( arcOut.hasNext())
    	{
    		Arc arc = arcOut.next();
    		
    		if( arc.getType().getStringRepr().equals("onProperty"))
    			prop= (String)arc.getTarget().getAttribute().getValueAt("iri");	
    		
    		else if( arc.getType().getStringRepr().equals("onClass"))
    		{
    			cls2= (String)arc.getTarget().getAttribute().getValueAt("iri");	
    			nodeCls2 =(Node) arc.getTarget();
    			    			
    			if( !isUrl(cls2))
    			{    				
    				Resource  r;
    				if (getIDAnon(cls2)==null)    
    				{
    					r = addRestriction (nodeCls2);    					
    					if( r==null)
    			    		r= addResource (arc.getTarget().getType().getStringRepr(), nodeCls2);
    			    	
    				    else
    				    	cls2= getIDAnon(r.getId().getLabelString());
    				}
    			}
    		}
    		else if( arc.getType().getStringRepr().equals("hasValue"))
    			ind= removeQuotNodeName(arc.getTarget().getAttribute().getValueAsString(0));	
    	} // end while
    	

    	List <Arc> arcClassP= res.getIncomingArcsVec();	
    
		
    	OntClass class1 = null;
    	if(arcClassP.size()!=0)    	
    	{
    		if( arcClassP.get(0).getType().getStringRepr().equals("hasRestriction"))
    		{
	    		String c1= removeQuotNodeName(arcClassP.get(0).getSource().getAttribute().getValueAsString(0));
		    	class1= getClassAnon(c1);
		    	if( !isUrl(c1))
					cls2= getIDAnon(c1);
    		}
    	}
				
		// CardinalityRestriction
    	if( nodeName.equals("CardinalityRestriction"))
    	{    		
    		// Restriction type	    		
    		String typRestriction= (String)res.getAttribute().getValueAt("type");	
    		int cardinality = Integer.parseInt(res.getAttribute().getValueAsString(2));
    		
    	    		    	
    	    //CardinalityRestriction cardRes;		    	
    		if(typRestriction.equals("min") )
    		{
    			MinCardinalityRestriction cardMinRes= model.createMinCardinalityRestriction( null, model.getProperty(prop), cardinality);
    			
    			if(class1!=null )
    			{
	    			if( desRes.equals("subClassOf"))
	    				class1.addSuperClass(cardMinRes); 
	    			else if( desRes.equals("equivalentTo"))
	    				class1.addEquivalentClass(cardMinRes);
    			}
    			
    			Set<String> idAnon = new HashSet();
    			idAnon.add(nodeName);			
    			idAnon.add(cardMinRes.getId().getLabelString());     			 
    			mapIsAnon.add(idAnon);
    			
    			return cardMinRes;
    		}
    		else if(typRestriction.equals("max") )
    		{
    			MaxCardinalityRestriction cardMaxRes=model.createMaxCardinalityRestriction(null, model.getProperty(prop), cardinality);	  
    			if(class1!=null )
    			{
	    			if( desRes.equals("subClassOf"))
	    				class1.addSuperClass(cardMaxRes); 
	    			 else if( desRes.equals("equivalentTo"))
	    				class1.addEquivalentClass(cardMaxRes);
    			}
    			
    			Set<String> idAnon = new HashSet();
    			idAnon.add(nodeName);			
    			idAnon.add(cardMaxRes.getId().getLabelString());     			 
    			mapIsAnon.add(idAnon);
    			
    			return cardMaxRes;
    		}
    		else
    		{
    			CardinalityRestriction cardRes= model.createCardinalityRestriction(null, model.getProperty(prop), cardinality);	
   
    			if(class1!=null )
    			{
	    			if(desRes.equals("subClassOf"))
	    				class1.addSuperClass(cardRes); 
	    			else if( desRes.equals("equivalentTo"))
	    				class1.addEquivalentClass(cardRes);
    			}
    			
    			Set<String> idAnon = new HashSet();
    			idAnon.add(nodeName);			
    			idAnon.add(cardRes.getId().getLabelString());     			 
    			mapIsAnon.add(idAnon);
    			
    			return cardRes;
    		}    		
    	} 
    	
    	// AllValueFromRestriction    	
    	else if(nodeName.equals("AllValuesFrom")) 
    	{
    		Resource resCls2=null;
    		if( getClassAnon (cls2)==null)
    		{
    			resCls2 = addResource(nodeCls2.getType().getStringRepr(),nodeCls2);    			
    			cls2= resCls2.getId().getLabelString();
    		}
    		else    			
    			resCls2 = getClassAnon(cls2);
    		
    		AllValuesFromRestriction allValRes= model.createAllValuesFromRestriction(null,
    																				model.getProperty(prop), 
    																				getClassAnon(cls2));
    		if(class1!=null )
			{
	    		if( desRes.equals("subClassOf"))
	    			class1.addSuperClass(allValRes); 
	    		else if( desRes.equals("equivalentTo"))
	    			class1.addEquivalentClass(allValRes);
			}
    		
    		Set<String> idAnon = new HashSet();
			idAnon.add(nodeName);			
			idAnon.add(allValRes.getId().getLabelString());     			 
			mapIsAnon.add(idAnon);
			 	
			return allValRes;
    	}

    	// SomeValueFromRestriction
    	else if(nodeName.equals("SomeValuesFrom")) 
    	{
    		Resource resCls2=null;
    		
    		if( getClassAnon (cls2)==null)
    		{
    			resCls2 = addResource(nodeCls2.getType().getStringRepr(),nodeCls2);
    			cls2= resCls2.getId().getLabelString();
    		}
    		else    			
    			resCls2 = getClassAnon(cls2);    		
    		  		
    		SomeValuesFromRestriction somValRes= model.createSomeValuesFromRestriction(null,
    																					model.getProperty(prop),     																					
    																					resCls2);    		
    		Set<String> idAnon = new HashSet();
			idAnon.add(desRes);			
			idAnon.add(somValRes.getId().getLabelString());     			 
			mapIsAnon.add(idAnon);
			 	
    		if(class1!=null )
			{
	    		if( desRes.equals("subClassOf"))
	    			class1.addSuperClass(somValRes); 
	    		else
	    			class1.addEquivalentClass(somValRes);
			}
    		return somValRes;
    	}
    	
    	// HasValueRestriction
    	else if(nodeName.equals("HasValue")) 
    	{
    		HasValueRestriction hasValRes= model.createHasValueRestriction(null,
    										model.getProperty(prop), 
    										model.getResource(ind));
    		if(class1!=null )
			{
	    		if( desRes.equals("subClassOf"))
	    			class1.addSuperClass(hasValRes); 
	    		else if( desRes.equals("equivalentTo"))
	    			class1.addEquivalentClass(hasValRes);
			}
    		
    		Set<String> idAnon = new HashSet();
			idAnon.add(desRes);			
			idAnon.add(hasValRes.getId().getLabelString());     			 
			mapIsAnon.add(idAnon);
    		return hasValRes;
    	}	
    	return null;
	}
	
	/** Method : AddEumeratedClass */	
	public EnumeratedClass addEnumeratedClass(Node EnumC)
	{
		String nodeIRI= (String)EnumC.getAttribute().getValueAt("iri");
	    Iterator <Arc>listOutArc=  EnumC.getOutgoingArcs();
    	RDFList  membersEnumC;
    	Resource r;
    	RDFNode[]  members=new RDFNode[ EnumC.getNumberOfOutgoingArcs(grammar.getTypeSet().getTypeByName("oneOf"))] ; 
    	int i=0;
    		
    	EnumeratedClass enumCls= null;
    		   
    	while( listOutArc.hasNext())
    	{
    		Arc arc= listOutArc.next();
    		if( arc.getType().getStringRepr().equals("oneOf"))
    		{
    			String individual= arc.getTarget().getAttribute().getValueAsString(0);    			
	    		r=model.createResource(individual.substring(1, individual.length()-1));		    	
	    		members[i] = (RDFNode) r;
	    		i++;
    		}
    	}
    		  
    	membersEnumC= model.createList(members);	
    		
    	if( !isUrl(nodeIRI))
	    {
	    	enumCls =model.createEnumeratedClass(null, membersEnumC); 
	    	Set<String> idAnon = new HashSet();	    	
			idAnon.add(nodeIRI);
			idAnon.add(enumCls.getId().getLabelString()); 
			mapIsAnon.add(idAnon);
	    }
	    		
    	else     
    		enumCls =model.createEnumeratedClass(nodeIRI, membersEnumC);  
    	
    	for(int j=0; j<members.length; j++)
    		model.createIndividual(members[j].asNode().getURI(),  enumCls);   
    		  
    	return enumCls;
	}
	
	/** Method :  Add intersectionClass */
	 public IntersectionClass addIntersectionClass(Node ic)
	 {
		 IntersectionClass iCls= null;
		 Iterator <Arc>listOutArc= ic.getOutgoingArcs();
		 RDFList  membersInterseC;
		 Resource r =null;
		 RDFNode[]  members=new RDFNode[ic.getOutgoingArcsSet().size()] ;
		 List<RDFNode> mem=new ArrayList<RDFNode>();
		 int i=0;
			    		   
		while( listOutArc.hasNext())
		{
			 Arc arc= listOutArc.next();
			 if( arc.getType().getStringRepr().equals("intersectionOf"))
			 {
			    String cls= removeQuotNodeName(arc.getTarget().getAttribute().getValueAsString(0)); 			    
			    r= getClassAnon (cls);			   
			    if( r==null)
			    {
			    	String typNode= arc.getTarget().getType().getStringRepr();    		
			    	r= addResource(typNode, (Node)arc.getTarget());
			    }				   
				else				 
				    r=model.getResource(cls);
			   	members[i] = (RDFNode) r; 
			   	mem.add((RDFNode) r);
				i++;
			 }
		}
		//System.out.println(members+"the model:"+model.createList(mem.iterator()));  
		membersInterseC = model.createList(mem.iterator());	 
		String nodeIRI= removeQuotNodeName(ic.getAttribute().getValueAsString(0));		 
		if( !isUrl(nodeIRI))
		{
			iCls = model.createIntersectionClass(null, membersInterseC);	
			Set<String> idAnon = new HashSet();
		   	idAnon.add(nodeIRI);
		   	idAnon.add(iCls.getId().getLabelString()); 
		   	mapIsAnon.add(idAnon);
		}			
		else 
			iCls = model.createIntersectionClass(nodeIRI, membersInterseC);
			
		return iCls;
	 }
	
	/** Method :  Add UnionClass	*/
	public UnionClass addUnionClass ( Node uc)
	{
		Iterator <Arc>listOutArc= uc.getOutgoingArcs();		    		
		RDFList  membersUnionC;
	    Resource r;
	    RDFNode[]  members=new RDFNode[uc.getNumberOfOutgoingArcs(grammar.getTypeSet().getTypeByName("unionOf"))] ; 
	    int i=0;
	    UnionClass iCls= null;
	   		   
	    while( listOutArc.hasNext())
	    {
	    	Arc arc= listOutArc.next();
	    	if(arc.getType().getStringRepr().equals("unionOf"))
	    	{
	    		String individual= arc.getTarget().getAttribute().getValueAsString(0);    			
	    		r=model.createResource(individual.substring(1, individual.length()-1));			    		
	    		members[i] = (RDFNode) r;
	    		i++;
	    	}
	   }
	    	
	    membersUnionC= model.createList(members);
	    String nodeIRI= removeQuotNodeName(uc.getAttribute().getValueAsString(0));	
	    if( !isUrl(nodeIRI))
	    {
	    	iCls= model.createUnionClass(null, membersUnionC); 
	    	Set<String> idAnon = new HashSet();
	   		idAnon.add(nodeIRI);
	   		idAnon.add(iCls.getId().getLabelString()); 
	   		mapIsAnon.add(idAnon);
	    }
	    else
	    	iCls= model.createUnionClass(nodeIRI, membersUnionC); 
	    
	    return iCls;
	}
	
	/** Method addComplementclass */
	public ComplementClass addComplementClass( Node cc)
	{
		String nodeIRI=removeQuotNodeName(cc.getAttribute().getValueAsString(0));	
		Iterator<Arc> arcC=cc.getOutgoingArcs();
		ComplementClass CompC = null;
			
		while( arcC.hasNext())
		{
			Arc arc= arcC.next();
			String iriCC=removeQuotNodeName( arc.getTarget().getAttribute().getValueAsString(0));
			
			if( !isUrl(nodeIRI))
			{
		    	CompC= model.createComplementClass(null,  model.getResource(iriCC));
		    	Set<String> idAnon = new HashSet();
				idAnon.add(nodeIRI);
				idAnon.add(CompC.getId().getLabelString()); 
				mapIsAnon.add(idAnon);
			}
			else
				CompC= model.createComplementClass(nodeIRI,  model.getResource(iriCC)); 
		}
		
		return CompC; 
	}
    
}
