package fusion.comerger.algorithm.partitioner.SeeCOnt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
//import org.semanticweb.owlapi.search.EntitySearcher;

import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Data;
import fusion.comerger.model.Node;
import fusion.comerger.model.NodeList;

public class OWLCreator {
	
	ArrayList<Cluster> list;
	
	public OWLCreator(ArrayList<Cluster> list)
	{
		this.list=list;
	}
	
	public void create(Data data)
	{
		String onName=data.getOntName();
		InputStream in=null;
	 	InputStream fileStream=null;
	 	if(onName.endsWith(".owl"))
		try {
				in = new FileInputStream(onName);
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		}
		else if(onName.endsWith(".gz"))
	 	{
	 		try {
	 				fileStream = new FileInputStream(new File(onName));
	 				in= new GZIPInputStream(fileStream);
			} catch (IOException e) {
					e.printStackTrace();
			}
	 	}
	 	 OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	 	 OWLOntologyManager managerN=null;
	 	 OWLOntology owl = null, owlN=null;
	 	 OntModel model=data.getOntModel();
	 	 OWLDataFactory factory=manager.getOWLDataFactory();
	 	 OWLDataFactory factoryN = null;
	 	 OWLClass thing=factory.getOWLThing();
		 try {
				owl = manager.loadOntologyFromOntologyDocument(in);
		} catch (OWLOntologyCreationException e) {
				e.printStackTrace();
		}
		 NodeList nList=data.getRbgModel().getNamedClassNodes();	  
		 for(int i=0;i<list.size();i++)
		 {
			Cluster cluster = list.get(i);
			managerN=OWLManager.createOWLOntologyManager();
			try {
				owlN=managerN.createOntology();
				factoryN=managerN.getOWLDataFactory();
			} catch (OWLOntologyCreationException e) {
				e.printStackTrace();
			}
			OWLDeclarationAxiom axiom=factoryN.getOWLDeclarationAxiom(thing);
			managerN.addAxiom(owlN, axiom);
			for (Iterator<Node> iter = cluster.listElements(); iter.hasNext();) {
		         Node inode= iter.next();
		         OntClass cls=model.getOntClass(inode.toString());
		         if(nList.contains(inode))
		         {
		             IRI iri= IRI.create(cls.getURI());
		        	 OWLClass ocls=factory.getOWLClass(iri);
		        	 Set<OWLObjectProperty> cop=getObjectPropDomain(owl,manager,ocls,owlN);
		        	 Set<OWLObjectProperty> copR=getObjectPropRange(owl,manager,ocls,owlN);
		        	 Set<OWLDataProperty> dop=getDataPropDomain(owl,manager,ocls,owlN);
		        	 Set<OWLDataProperty> dopR=getDataPropRange(owl,manager,ocls,owlN);   
		           	 axiom=factory.getOWLDeclarationAxiom(ocls);   
		    		 managerN.addAxiom(owlN, axiom);
		        	 Set<OWLClassAxiom> axioms=new HashSet<OWLClassAxiom>();   
		        	 axioms=owl.getAxioms(ocls);   
		        	 managerN.addAxioms(owlN, axioms);
		          	 Set<OWLAnnotationAssertionAxiom> axiomAn= ocls.getAnnotationAssertionAxioms(owl);//EntitySearcher.getAnnotationAssertionAxioms(ocls, owl);//(owl);
		          	 managerN.addAxioms(owlN, axiomAn);
		        	 for(OWLObjectProperty op:cop)
		        	 {
		        		 OWLObjectPropertyDomainAxiom axiomO=factoryN.getOWLObjectPropertyDomainAxiom(op, ocls);	
		        		 //managerN.addAxiom(owlN, axiomO);
		        		 managerN.applyChange(new AddAxiom(owlN, axiomO));
		        	 }
		        	 for(OWLObjectProperty rp:copR)
		        	 {
		        		 OWLObjectPropertyRangeAxiom axiomO=factoryN.getOWLObjectPropertyRangeAxiom(rp, ocls);	
		        		 //managerN.addAxiom(owlN, axiomO);
		        		 managerN.applyChange(new AddAxiom(owlN, axiomO));
		        	 }	 
		        	 for(OWLDataProperty dp:dop)
		        	 {
		        		 OWLDataPropertyDomainAxiom axiomO=factoryN.getOWLDataPropertyDomainAxiom(dp, ocls);	
		        		 //managerN.addAxiom(owlN, axiomO);
		        		 managerN.applyChange(new AddAxiom(owlN, axiomO));
		        	 }	
		        	 for(OWLDataProperty dp:dopR)
		        	 {
		        		 Set<OWLDataPropertyRangeAxiom> axiomO=owl.getDataPropertyRangeAxioms(dp);//factoryN.getOWLDataPropertyRangeAxiom(dp, OWL2Datatype);	
		        		 managerN.addAxioms(owlN, axiomO);
		        	 }	  
		        	 
		         }
		        	         	    	 
		     }
			 //managerN.saveOntology(owlN,  new RDFXMLOntologyFormat(), outputStream);
			 String outPath = data.getPath() + onName + "_Module_" + i + ".owl";
			 saveOntology(owlN,"D:/result/test/"+onName+"_Module_"+i+".owl");
			 
		 }
	}


	private Set<OWLObjectProperty> getObjectPropDomain(OWLOntology ontology, OWLOntologyManager manager,OWLClass ocls, OWLOntology ont)
	{
		 Set<OWLObjectProperty> cop = new HashSet<OWLObjectProperty>();
		 for (OWLObjectPropertyDomainAxiom op : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {                        
	         if (op.getDomain().equals(ocls)) {   
	             for(OWLObjectProperty oop : op.getObjectPropertiesInSignature())
	             {
	            	 OWLFunctionalObjectPropertyAxiom axiom=manager.getOWLDataFactory().getOWLFunctionalObjectPropertyAxiom(oop);
	            	 manager.addAxiom(ont, axiom);
	            	 cop.add(oop); 
	             	
	             }
	             
	         }
	     }
	    return cop;
	}


	private Set<OWLObjectProperty> getObjectPropRange(OWLOntology ontology, OWLOntologyManager manager,OWLClass ocls,OWLOntology ont)
	{
		 Set<OWLObjectProperty> cop = new HashSet<OWLObjectProperty>();
		 for (OWLObjectPropertyRangeAxiom op : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE)) {                        
	         if (op.getRange().equals(ocls)) {   
	             for(OWLObjectProperty oop : op.getObjectPropertiesInSignature())
	             {
	                 cop.add(oop); 
	                 OWLFunctionalObjectPropertyAxiom axiom=manager.getOWLDataFactory().getOWLFunctionalObjectPropertyAxiom(oop);
	            	 manager.addAxiom(ont, axiom);
	             }
	             
	         }
	     }
	    return cop;
	}

	private Set<OWLDataProperty> getDataPropDomain(OWLOntology ontology, OWLOntologyManager manager,OWLClass ocls,OWLOntology ont)
	{
		 Set<OWLDataProperty>  cdp=new HashSet<OWLDataProperty>();;
	       for (OWLDataPropertyDomainAxiom dp : ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN))
	       {
		          if (dp.getDomain().equals(ocls)) {   
		                for(OWLDataProperty odp : dp.getDataPropertiesInSignature())
		                {
		                    cdp.add(odp); 
		                     // OWLFunctionalDataPropertyAxiom axiom=manager.getOWLDataFactory().getOWLFunctionalDataPropertyAxiom(odp);
		               	   // manager.addAxiom(ont, axiom);
		                }
		                
		            }
		       }
	      return cdp;
	}

	private Set<OWLDataProperty> getDataPropRange(OWLOntology ontology, OWLOntologyManager manager,OWLClass ocls,OWLOntology ont)
	{

	   	  Set<OWLDataProperty>  cdp=new HashSet<OWLDataProperty>();;
	       for (OWLDataPropertyRangeAxiom dp : ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE))
	       {
		          if (dp.getClass().equals(ocls)) {    
		                for(OWLDataProperty odp : dp.getDataPropertiesInSignature())
		                {
		                    cdp.add(odp); 
		                  //  OWLFunctionalDataPropertyAxiom axiom=manager.getOWLDataFactory().getOWLFunctionalDataPropertyAxiom(odp);
		               	   // manager.addAxiom(ont, axiom);
		                }
		                
		            }
		       }
	      return cdp;
	}

	 private void saveOntology(OWLOntology owlN, String loc)
	 {
		 OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		 OWLOntologyFormat format=new RDFXMLOntologyFormat();
		 File file=new File(loc);
		 try {
			manager.saveOntology(owlN, format, IRI.create(file.toURI()));
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
	 }

}
