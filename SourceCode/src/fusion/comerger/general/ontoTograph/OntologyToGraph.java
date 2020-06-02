package  fusion.comerger.general.ontoTograph;
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
// import  from jena 
import org.apache.jena.graph.*;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.iterator.*;

// import  from AGG
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;
import agg.xt_basis.Node;

import java.util.*;

public class OntologyToGraph
{	
	private OntModel model; 
		
	private GraGra graphGrammar;
	private Graph typeGrap;
	private Graph graphOnto;
	
	private GraphAnalysis graphAna;
	private GraphConstruction graphCon;
	private OntologyAnalysis ontoAna;
	private String OntName;
	private String fileName;
    public OntologyToGraph(OntModel model, Graph typeGraph, String Name ) throws TypeException 
    {
    	this.model= model;    	
    	
    	graphAna= new GraphAnalysis ();
    	graphCon = new GraphConstruction ();    
    	ontoAna= new OntologyAnalysis (model);    	
    	
    	//A new graph grammar is created
    	graphGrammar =  new GraGra(true);
    	
    	graphGrammar.importTypeGraph(typeGraph, true);
    	typeGrap= graphGrammar.getTypeGraph();
    	typeGrap.setName("Metamodel");
    	
    	//Host graph
    	graphOnto=graphGrammar.getGraph(0);
    	graphOnto.setName("Ontology");
    	OntName=Name;
    	
		/************** Create the graph  ********************/
    	
		addClassAndIndividual();
		addAxiomIndividual();	
	
		addDataProperties() ;	
		addDataPropAxiom();	
		
		addObjectProperties();
		addObPropAxiom();
		
		addRestrictipon(ResWithoutDescription);
		addClassComplex();
		addClassAxiom();
		addAssertionIndividual(); 
		
		addRestrictipon(ResWithDescription);
	
		
		//******* Save the graph 
		String fn = "resources/mergeTemp/"+OntName+"GGX";
		fileName=fn+".ggx";
		graphGrammar.save(fn);	
		System.out.println("Graph saved in "+fn + ".ggx");	
    }
    
    public String getFileName()
    {
    	return fileName;
    }
    public String typeClass(OntClass clas)
    {
    	if( clas.isUnionClass())    		
    		return "UnionClass";    	
    	if ( clas.isIntersectionClass())
    		return "IntersectionClass";
    	if( clas.isComplementClass())
    		return "ComplementClass";
    	if( clas.isEnumeratedClass())    	
    		return "EnumerateClass";  
    	
    	if (clas.isRestriction())
    	{
    		Restriction res = clas.asRestriction();
    		if( res.isSomeValuesFromRestriction())
    			return "SomeValuesFrom";
    		else if( res.isAllValuesFromRestriction())
    			return "AllValuesFrom";
    		else if (res.isHasValueRestriction())
    			return "HasValue";
    		else 
    			return "CardinalityRestriction"; 
    	}    	
    	return "Class";
    }
    
    public String getIRIClass (OntClass clas)
    {
    	String classIRI= clas.getURI();
    	if(classIRI==null)
    		return clas.getId().getLabelString();
    	return classIRI;
    }
    
    public String getlocalNameC (OntClass clas)
    {
    	String localNameC= clas.getLocalName();
    	if(localNameC==null)
    		return clas.getId().getLabelString();
    	return localNameC;
    }
  
    
    private List  <Restriction> ResWithDescription = new LinkedList();
    private List <Restriction> ResWithoutDescription = new LinkedList();
    

    // Method that adds the classes    
    public void addClassAndIndividual() throws TypeException
    {
    	Iterator <OntClass>  listCOnto = model.listNamedClasses();// .listClasses();
        while (listCOnto.hasNext())
        {
        	OntClass  clas= listCOnto.next(); 
        	
        	if( clas.isRestriction() )
        	{
        		if( clas.getEquivalentClass()!=null || clas.getSuperClass()!=null)
        			ResWithDescription.add(clas.asRestriction());
        		else 
        			ResWithoutDescription.add(clas.asRestriction());
        		continue;
        	}
        	
        	//Create a node of the type "Class"  
        	Node nodeC= graphCon.createNode(graphOnto, 
        						graphAna.getTypNode(typeGrap, typeClass(clas)),
        						getIRIClass(clas),     // affect the iri of the class to the attribute iri of the node 
        						getlocalNameC(clas));  // affect the localname to the attribute name     
        		
      
        	//Add the individual of the class
        	Iterator <?>instance=clas.listInstances(true); // true prevents the inference : an individual will be affect only to the his original type
        	
  		 	while(instance.hasNext())
  		 	{	
  		 		Individual ins= (Individual) instance.next();	
  		 		
  		        //Create a node of the type "Individual"
  		        Node nodeInd= graphCon.createNode(graphOnto, 
  		        						graphAna.getTypNode(typeGrap, "Individual"),
  		        						ins.getURI(),
  		        						ins.getLocalName());  
  		        
  		        if( clas.isEnumeratedClass())
  		        {
  		        	// create the edge "memberOf" for link the individual to this enumeratedClass
  		        	graphCon.createArc(graphOnto,
  		        				graphAna.getArcType(typeGrap, "oneOf"),          				
  		        				nodeC, 
  		        				nodeInd); 
  		        }  			 
  		        else 
  		        {
  		        	// create the edge "memberOf" for link the individual to this type
  	  		 		graphCon.createArc(graphOnto,
  	  		 					graphAna.getArcType(typeGrap, "memberOf"), 
  	  		 					nodeInd,
  	  		 					nodeC); 
  	  		 	}
  		 	}  		 	
        }
    }
   
   
    // Method that adds the axiomes of the classes
    private void addClassAxiom() throws TypeException
    {

    	Iterator <OntClass>  listCOnto = model.listClasses(); 
		for( listCOnto= ontoAna.getClasses().iterator();listCOnto.hasNext();)		
		{			
        	OntClass cOnto= listCOnto.next();  
        	
        	if( cOnto.isRestriction())
        		continue;
        	
        	//Add equivalentTo axiom
        	Iterator<OntClass> equivAxiom=cOnto.listEquivalentClasses();   
        	       		
        	while( equivAxiom.hasNext())
        	{  
        		OntClass equivClass=equivAxiom.next();
        		
        		Type arcType= graphAna.getArcType(typeGrap, "equivalentTo");
        		Node nodeS= graphAna.getNode(graphOnto, typeClass(cOnto),getIRIClass(cOnto));
        		Node nodeT= graphAna.getNode(graphOnto, typeClass(equivClass),equivClass.getURI());        		
        		
        		graphCon.createArc(graphOnto, arcType, nodeS,nodeT);
        	}
        	
            //Add disjointwith axiom
        	Iterator<OntClass>  disjointAxiom=cOnto.listDisjointWith();     
        	
        	while(disjointAxiom.hasNext())
            {
        		OntClass disjClass=disjointAxiom.next();       		
        		
        		graphCon.createArc(graphOnto,
        				graphAna.getArcType(typeGrap, "disjointWith"),
        				graphAna.getNode(graphOnto,typeClass(cOnto),getIRIClass(cOnto)),
        				graphAna.getNode(graphOnto,typeClass(disjClass),disjClass.getURI()));
            }        	
        	 
            //Add the subClassOf axiom  
        	Iterator<OntClass>  subClassAxiom=cOnto.listSubClasses(true);  
        
        	while(subClassAxiom.hasNext())
            { 
        		OntClass subClass=subClassAxiom.next();        				
        		graphCon.createArc(graphOnto,
        				graphAna.getArcType(typeGrap, "subClassOf"), 
        				graphAna.getNode(graphOnto,typeClass(subClass),subClass.getURI()),
        				graphAna.getNode(graphOnto,typeClass(cOnto),getIRIClass(cOnto)) );
            }        	
        }		
    }
    
    public void addClassComplex() throws TypeException
    {
		//add CompelmentClass 
   		Iterator <ComplementClass> complClass= model.listComplementClasses();
   		
   		while(complClass.hasNext())
   		{
   			ComplementClass compC= complClass.next();
   		
   			Iterator <? extends OntClass>memberComp= compC.listOperands();
   			while( memberComp.hasNext())
   			{
   				OntClass cc= memberComp.next();
   				graphCon.createArc(graphOnto,
        				graphAna.getArcType(typeGrap, "complementOf"), 
        				graphAna.getNode(graphOnto,"ComplementClass",getIRIClass(compC)),
        				graphAna.getNode(graphOnto,typeClass(cc),getIRIClass(cc)));
   			}   
   		}
   		   		
   		//Add UnionClass 
   		Iterator <UnionClass> unionClass= model.listUnionClasses();

   		while(unionClass.hasNext())
   		{
   			UnionClass unionC= unionClass.next();   		
   			
   			Set <?>membersUnion=  unionC.listOperands().toSet();
   			if(membersUnion!=null)
   			{
   				Iterator <?> memberUnion=membersUnion.iterator();
   				while( memberUnion.hasNext())
   				{
   					OntClass uc= (OntClass)memberUnion.next();
   				  				
   					graphCon.createArc(graphOnto,
        				graphAna.getArcType(typeGrap, "unionOf"), 
        				graphAna.getNode(graphOnto,"UnionClass",getIRIClass( unionC)),
        				graphAna.getNode(graphOnto,typeClass(uc),getIRIClass(uc)));
   				}  
   			}
   		}
   		
    	// add IntersectionClass 
   		Iterator <IntersectionClass> intersClass= model.listIntersectionClasses();
   		while(intersClass.hasNext() )
   		{
   			IntersectionClass intersC=intersClass.next();
   			
   			Iterator <? extends OntClass>memberInters= intersC.listOperands();
   			
   			while( memberInters.hasNext())
   			{
   				OntClass ic= memberInters.next();
   				graphCon.createArc(graphOnto,
        				graphAna.getArcType(typeGrap, "intersectionOf"), 
        				graphAna.getNode(graphOnto,"IntersectionClass",getIRIClass(intersC)),
        				graphAna.getNode(graphOnto,typeClass(ic),getIRIClass(ic)));   
   			}  
   		}
    }

    // Method that adds dataProperties
    private void addDataProperties() throws TypeException
    {
 	    Iterator<DatatypeProperty>listDPOnto= ontoAna.getDatatypeProperties(); 
    	 while (listDPOnto.hasNext())
         {
    		 DatatypeProperty dataProp= listDPOnto.next();  	
    		 
    		 // create a node of the type "DataProperty"
    		 Node nodeDataProp= graphCon.createNode(graphOnto, 
         			graphAna.getTypNode(typeGrap, "DataProperty"),
         			dataProp.getURI(),dataProp.getLocalName(), dataProp.isFunctionalProperty()); 
    		 
    		
             //add the domain and the range of the dataProperty
             Iterator <? extends OntResource> domains= dataProp.listDomain();
             Iterator<? extends OntResource> ranges= dataProp.listRange(); 
             
             while (domains.hasNext())
             {
            	 OntClass domain= (OntClass) domains.next();
                 	
            	 // create an edge to link the dataproperty to its domain
            	 graphCon.createArc(graphOnto,
         				graphAna.getArcType(typeGrap, "domain"), 
         				nodeDataProp,
         				graphAna.getNode(graphOnto,typeClass(domain), domain.getURI()));   
             }
               
             while (ranges.hasNext())
             {
            	 Resource range= ranges.next();  
            	 Node  dataRange =null;
            	List <Node> dataTyp=  graphAna.getDataTypGraph(graphGrammar);
            	
            	if( dataTyp!=null && dataTyp.size()!=0)
            	b2:
            	for( Node dp : dataTyp)
            	{
            		String valueDT=  dp.getAttribute().getMemberAt(0).toString();
            		if(valueDT.endsWith("\""))    		
            			valueDT= (valueDT.substring(1, valueDT.length()-1));
            		//System.out.println("A test\t"+range.getURI());
            		if( range.getURI()!=null &&range.getURI().equals( valueDT) )
            		{
            			dataRange= dp;
            			break b2;
            		}            		
            	}
            	    
            	if(dataRange ==null )
            		dataRange=graphCon.createNode(graphOnto,
               			 graphAna.getTypNode(typeGrap, "DataType"),
               			 range.getURI(), 
               			 range.getLocalName());  
                 	
            	 //create an edge to link the datatproperty to its datatype:            	
            	 graphCon.createArc(graphOnto,
         				graphAna.getArcType(typeGrap, "range"), 
         				nodeDataProp,
         				dataRange);
            }   
    	 }
    }     
    
    //Method that add the axioms of the dataProperties  
    private void addDataPropAxiom() throws TypeException
    {
    	Iterator <DatatypeProperty>listDPOnto= ontoAna.getDatatypeProperties();
		for( listDPOnto= model.listDatatypeProperties();listDPOnto.hasNext();)		
		{			
        	DatatypeProperty dPOnto= listDPOnto.next();   
        	
        	//add EquivalentProperty axioms 
        	Iterator<? extends OntProperty> equivAxiom=dPOnto.listEquivalentProperties();
        	
        	       		
        	while( equivAxiom.hasNext())
        	{ 
        		OntProperty equivDP=equivAxiom.next();
        
        		Type arcType= graphAna.getArcType(typeGrap, "equivalentTo");
        		Node nodeS= graphAna.getNode(graphOnto,"DataProperty",dPOnto.getURI());
        		Node nodeT= graphAna.getNode(graphOnto,"DataProperty",equivDP.getURI());
        		
        		graphCon.createArc(graphOnto, arcType, nodeS,nodeT);
        	}
        	
            //add disjointWith axioms
        	Iterator<? extends Resource> disjointAxiom=dPOnto.listDifferentFrom();     
        	
        	while(disjointAxiom.hasNext())
            {
        		DatatypeProperty disjDProp=(DatatypeProperty)disjointAxiom.next();       		
        		
        		graphCon.createArc(graphOnto,
        				graphAna.getArcType(typeGrap, "disjointWith"),
        				graphAna.getNode(graphOnto,"DatatypeProperty",dPOnto.getURI()),
        				graphAna.getNode(graphOnto,"DatatypeProperty",disjDProp.getURI()));
            }        	
        	 
           
        	Iterator<? extends OntProperty> subDPropAxiom=dPOnto.listSubProperties();     
        	
        	while( subDPropAxiom.hasNext())
            { 
        		DatatypeProperty subObjProp= subDPropAxiom.next().asDatatypeProperty();
        		        		
        		graphCon.createArc(graphOnto,
        				graphAna.getArcType(typeGrap, "subProperty"), 
        				graphAna.getNode(graphOnto,"DatatypeProperty",dPOnto.getURI()),
        				graphAna.getNode(graphOnto,"DatatypeProperty",subObjProp.getURI()));
            } 
        }
	}
    
    // Method that adds objectPtoperties   
    private void addObjectProperties() throws TypeException
    {	 
    	Iterator <ObjectProperty>listOPOnto= ontoAna.getObjectProperties();
    	 while (listOPOnto.hasNext())
         {
    		 ObjectProperty objProp= listOPOnto.next(); 
   		 //retrive the characteristics  of the objectproperties		 
    		 boolean [] propObjProp = {objProp.isFunctionalProperty(),objProp.isInverseFunctionalProperty(), objProp.isTransitiveProperty(), objProp.isSymmetricProperty()};
    		  
    		    		 
    		 // create a node of the type objectproperty    		
    		 Node nodeOP= graphCon.createNode(graphOnto, 
         			graphAna.getTypNode(typeGrap, "ObjectProperty"),
         			objProp.getURI(),objProp.getLocalName(),  propObjProp); 
    
            Iterator <? extends OntResource> domains=  objProp.listDomain();
            Iterator<? extends OntResource> ranges= objProp.listRange(); 
             
             while (domains.hasNext())
             {
            	 OntClass domain= (OntClass) domains.next();
                 	
            	 // create an edge to link the objectproperties to their domains
            	 graphCon.createArc(graphOnto,
         				graphAna.getArcType(typeGrap, "domain"), 
         				nodeOP,
         				graphAna.getNode(graphOnto,typeClass(domain), domain.getURI()));
             }
               
             while (ranges.hasNext())
             {
            	 OntClass range= (OntClass) ranges.next();
            	
            	 // create an edge to link the objectproperties to their ranges
            	 graphCon.createArc(graphOnto,
         				graphAna.getArcType(typeGrap, "range"), 
         				nodeOP,
         				graphAna.getNode(graphOnto,typeClass(range), range.getURI()));
            }   
    	}
    }  
  
    
    //Method that adds the objectProperties axiom
    private void addObPropAxiom() throws TypeException
    {
    	Iterator <ObjectProperty>listOPOnto= ontoAna.getObjectProperties();
		for( listOPOnto= model.listObjectProperties();listOPOnto.hasNext();)		
		{			
        	ObjectProperty oPOnto= listOPOnto.next();   
        	
        	//add equivalentProperty axiom
        	Iterator<? extends OntProperty> equivAxiom=oPOnto.listEquivalentProperties();   
        	       		
        	while( equivAxiom.hasNext())
        	{  
        		ObjectProperty equivObjProp=(ObjectProperty)equivAxiom.next();
        		
        		Type arcType= graphAna.getArcType(typeGrap, "equivalentTo");
        		Node nodeS= graphAna.getNode(graphOnto,"ObjectProperty",oPOnto.getURI());
        		Node nodeT= graphAna.getNode(graphOnto,"ObjectProperty",equivObjProp.getURI());
        		
        		graphCon.createArc(graphOnto, arcType, nodeS,nodeT);
        	}
        	
            //add disjointWith axiom
        	Iterator<? extends Resource> disjointAxiom=oPOnto.listDifferentFrom();     
        	
        	while(disjointAxiom.hasNext())
            {
        		ObjectProperty disjObjProp=(ObjectProperty)disjointAxiom.next();       		
        		
        		graphCon.createArc(graphOnto,
        				graphAna.getArcType(typeGrap, "disjointWith"),
        				graphAna.getNode(graphOnto,"ObjectProperty",oPOnto.getURI()),
        				graphAna.getNode(graphOnto,"ObjectProperty",disjObjProp.getURI()));
            }        	
        	 
            // add subPropertyOf axiom
        	Iterator<? extends OntProperty> subObjPropAxiom=oPOnto.listSubProperties(true);     
        	
        	while( subObjPropAxiom.hasNext())
            { 
        		OntProperty subObjProp= subObjPropAxiom.next();
        		        		
        		graphCon.createArc(graphOnto,
        				graphAna.getArcType(typeGrap, "subProperty"), 
        				graphAna.getNode(graphOnto,"ObjectProperty",oPOnto.getURI()),
        				graphAna.getNode(graphOnto,"ObjectProperty",subObjProp.getURI()));
        		
            }
        	
        	// add inverseTo axiom       	
        	Iterator<? extends OntProperty> inverseObjPropAxiom=oPOnto.listInverse();     
        	
        	while( inverseObjPropAxiom.hasNext())
            { 
        		OntProperty invObjProp= inverseObjPropAxiom.next();        		        		
        		graphCon.createArc(graphOnto,
        				graphAna.getArcType(typeGrap, "inverseTo"), 
        				graphAna.getNode(graphOnto,"ObjectProperty",oPOnto.getURI()),
        				graphAna.getNode(graphOnto,"ObjectProperty",invObjProp.getURI()));
            }
        }
	}
    
   
    //Method that adds assertion of the individuals 
    public void addAssertionIndividual() throws TypeException
    {      	
    	Iterator<Individual>listIOnto= ontoAna.getIndividuals(); 
    	while (listIOnto.hasNext())
        {
        	Individual ind= listIOnto.next();  
        	
            //*****add the assertions              
            StmtIterator propInd= ind.listProperties();
                    
            while( propInd.hasNext())
            {            
            	Triple triple= ((FrontsTriple) propInd.next()).asTriple();
            	String predicat=triple.getPredicate().getURI();
            	org.apache.jena.graph.Node object=triple.getObject();
            	
            	
            	if(graphAna.getNode(graphOnto,"ObjectProperty",predicat)!=null)
            	{              		
                    graphCon.createArc(graphOnto,
                 				graphAna.getArcType(typeGrap, "objectPropertyAssertion"), 
                 				graphAna.getNode(graphOnto,"Individual",ind.getURI()),
                 				graphAna.getNode(graphOnto,"Individual", object.getURI()),
                 				predicat);                 
            	}
                    
            	
            	else if(graphAna.getNode(graphOnto,"DataProperty",predicat)!=null)
            	{
                    graphCon.createArc(graphOnto,
                 				graphAna.getArcType(typeGrap, "dataPropertyAssertion"), 
                 				graphAna.getNode(graphOnto,"Individual", ind.getURI()),
                 				graphAna.getNode(graphOnto,"DataProperty",predicat),
                 				object.toString());                    
            	}                   
            }
    	}
    }   
    
    
    //Method that adds the  Individuals axioms
    public void addAxiomIndividual() throws TypeException
    { 
    	Iterator<Individual>listIOnto= ontoAna.getIndividuals(); 
    	while (listIOnto.hasNext())
        {
        	Individual ind= listIOnto.next(); 
        	Iterator<?extends Resource> listIndSyn= ind.listSameAs();
        	while( listIndSyn.hasNext())
        	{
        		Resource indSyn= listIndSyn.next();
        		
                graphCon.createArc(graphOnto,
             				graphAna.getArcType(typeGrap, "equivalentTo"), 
             				graphAna.getNode(graphOnto,"Individual",ind.getURI()),
             				graphAna.getNode(graphOnto,"Individual", indSyn.getURI()));  
        	}
        	
        	Iterator< Resource> listIndDiff= (Iterator<Resource>) ind.listDifferentFrom();
        	while( listIndDiff.hasNext())
        	{
        	
        		Resource indDiff= listIndDiff.next();
        		
                graphCon.createArc(graphOnto,
             				graphAna.getArcType(typeGrap, "disjointWith"), 
             				graphAna.getNode(graphOnto,"Individual",ind.getURI()),
             				graphAna.getNode(graphOnto,"Individual", indDiff.getURI()));  
        	}
        }
    }
   
    // Method that adds the restrictions     
    private void addRestrictipon(List <Restriction > resList) throws TypeException
    { 
    	for( Restriction res: resList)   
		addRestrictipon( res);	
    }
    
    private void addRestrictipon(Restriction res) throws TypeException
    {	
    	   		
    		String nodeName = null;    		 
    		int cardinality=-1;
    		String typeCardinality=null;
    		OntClass onClass =null;
    		Boolean typeDatType= false;
    		    	
    		if (res.isAllValuesFromRestriction())     
    		{    			
    			nodeName="AllValuesFrom";   
    			onClass=(OntClass) res.asAllValuesFromRestriction().getAllValuesFrom();  
    			if(!model.listClasses().toSet().contains(onClass))
            		typeDatType= true;
    		}
            
           if (res.isSomeValuesFromRestriction())
            {
        	   nodeName="SomeValuesFrom";
            	onClass=(OntClass) res.asSomeValuesFromRestriction().getSomeValuesFrom();   
            	if(!model.listClasses().toSet().contains(onClass))
            		typeDatType= true;
            }   
    		
            else if (res.isHasValueRestriction())
            {      
            
            	nodeName="HasValue";
            }   
    		
            else if(res.isCardinalityRestriction())
            {        	
            	typeCardinality="exactly";
            	cardinality=res.asCardinalityRestriction().getCardinality();
            	nodeName="CardinalityRestriction"; // ou bien HasValue
            }
    		
            else if(res.isMinCardinalityRestriction())
            {
            	nodeName="CardinalityRestriction";
            	typeCardinality= "min";
            	cardinality=res.asMinCardinalityRestriction().getMinCardinality();             	
            }    
    	
            else if(res.isMaxCardinalityRestriction())
            {
            	nodeName="CardinalityRestriction";
            	typeCardinality= "max";
            	cardinality=res.asMaxCardinalityRestriction().getMaxCardinality(); 
            }    		
          
    		
    		if(nodeName!=null)
    		{    		    		
    			//***Create the nodes of the type restriction    			
    			String restrictionDescription;
    			if( res.getSubClass()!=null)
    				restrictionDescription="subClassOf";    			
    			else  if(res.getEquivalentClass()!=null)  			
    				restrictionDescription="equivalenTo";  
    			else
    				restrictionDescription=res.getId().getLabelString();
    			
    			Node node;
    			
    			// case of cardinality
    			if(cardinality!=-1)    				
    				 node=  graphCon.createNode(graphOnto, 
    	           		graphAna.getTypNode(typeGrap, nodeName),restrictionDescription,typeCardinality, cardinality);  
    			    			
    			else    				
    				node= graphCon.createNode(graphOnto, 
    					graphAna.getTypNode(typeGrap, nodeName), restrictionDescription); 
    			
    			//create an edge to link the restriction to its classe 
    			if( res.getSubClass()!=null) 
    			{
    				graphCon.createArc(graphOnto,
        				graphAna.getArcType(typeGrap, "hasRestriction"), 
        				graphAna.getNode(graphOnto,typeClass(res.getSubClass()),res.getSubClass().getURI()),
        				node);  
    			}
    			else if( res.getEquivalentClass()!=null )
    			{
    	    		graphCon.createArc(graphOnto,
    	        				graphAna.getArcType(typeGrap, "hasRestriction"), 
    	        				graphAna.getNode(graphOnto,typeClass(res.getEquivalentClass()),res.getEquivalentClass().getURI()),
    	        				node);  
    			}
    			    		 
    			//create an edge to link the restriction to its property : objectProperty or dataProperty    	
    			OntProperty prop= res.getOnProperty();
    			String typeProp;
    			if(prop.isObjectProperty())
    				typeProp="ObjectProperty";
    			else
    				typeProp="DataProperty";
    			
    			graphCon.createArc( graphOnto,
        							graphAna.getArcType(typeGrap, "onProperty"), 
        							node,
        							graphAna.getNode(graphOnto,typeProp, res.getOnProperty().getURI()));    		
    			
    			if(nodeName.equals("HasValue") )
    			{    				
        			graphCon.createArc(graphOnto,
            				graphAna.getArcType(typeGrap, "hasValue"), 
            				node,
            				graphAna.getNode(graphOnto,"Individual",res.asHasValueRestriction().getURI())); 
    			}    			    			
    	
    			if(nodeName.equals("SomeValuesFrom") ||nodeName.equals("AllValuesFrom") )
    			{ 
    				if( typeDatType)        				
        				graphCon.createArc(graphOnto,
                				graphAna.getArcType(typeGrap, "hasValue"), 
                				node,
                				graphAna.getNode(graphOnto,"DataType",getIRIClass( onClass))); 
    				else
    				{  
    					graphCon.createArc(graphOnto,
        				graphAna.getArcType(typeGrap, "onClass"), 
        				node,
        				graphAna.getNode(graphOnto,typeClass(onClass),getIRIClass( onClass)));
    					
    				}
    			}
    		}
    	}
}
