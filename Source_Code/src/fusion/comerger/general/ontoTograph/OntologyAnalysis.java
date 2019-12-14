package  fusion.comerger.general.ontoTograph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import org.apache.jena.util.iterator.*;
import org.apache.jena.ontology.*;
public class OntologyAnalysis 
{

	OntModel model ;

    public OntologyAnalysis (OntModel model)
    {       
    	this.model= model;    	
    }

    // **********R�cup�rer la liste des classes de l'ontologie *****************
    public Set <OntClass>  getClasses()
    {       
      Iterator<OntClass> listConcept= model.listClasses();
       Set <OntClass>  concepts =new HashSet<OntClass> ();
     
        while(listConcept.hasNext())
        {  
        	OntClass concept =listConcept.next();   
        	
        	
        	if(concept.getLocalName()!=null)
        		concepts.add(concept);        	
        		
        }  
        return concepts;
    }
    
    // ********** get the list of the enumeratedClass of the ontology **************
    public Iterator<EnumeratedClass>  getEnumerateClasses()
    {       
        return model.listEnumeratedClasses();
      
    }
    
    
    // ********** get the list of the UnionClass of the ontology ******************
    public Iterator<UnionClass>  getUnionClasses()
    {       
        return model.listUnionClasses();
      
    }
    
    // ********** get the list of the intersetionClass of the ontology **************
    public Iterator<IntersectionClass>  getIntersectionClasses()
    {       
        return model.listIntersectionClasses();
      
    }
    
    // ********** get the list of the datatypeProperty of the ontology ************** 
    public Iterator <DatatypeProperty> getDatatypeProperties()
    { 
	   return model.listDatatypeProperties(); 
    }
    
    // ********** get the list of the objectproperty of the ontology ************** 
    public Iterator <ObjectProperty> getObjectProperties()
    { 
	   return model.listObjectProperties(); 
    }
    
    // ********** get the list of the individuals of the ontology **************
    public Iterator <Individual> getIndividuals()
    { 
	   return model.listIndividuals(); 
    }
    
    // ********** get the list of the restriction of the ontology **************
    public Iterator <Restriction> getRestrictions()
    { 
	   return model.listRestrictions(); 
    }
    
    // ********** get the list of the datatype of the ontology ****************
    public Iterator <DataRange> getDataType()
    { 
	   return model.listDataRanges(); 
    }   
   
}