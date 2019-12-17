package fusion.comerger.algorithm.partitioner.SeeCOnt;

/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.shared.BadURIException;
import org.apache.jena.vocabulary.OWL;

import java.io.BufferedReader;
import java.io.FileReader;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Data;
import fusion.comerger.model.Node;
import fusion.comerger.model.NodeList;
import fusion.comerger.model.ext.sentence.RDFSentence;
import fusion.comerger.model.ext.sentence.RDFSentenceGraph;
import fusion.comerger.model.ext.sentence.filter.OntologyHeaderFilter;
import fusion.comerger.model.ext.sentence.filter.PureSchemaFilter;

public class CreateModule {
	// private static LinkedHashMap<Integer, Cluster> clusters;
	// private OntModel model1;
	// private static String ontName = null;
	// public static String tempDir = null;
	// public static ArrayList<OntModel> models ; #
	private LinkedHashMap<String, Integer> uriToClusterID = null;
	public static int[][] NumLickConcept = null;
	// private int numEntity;
	 public static int [] numAloneElemnt;
	// public static double[] numTree;
	 public static ArrayList<OntModel> modules; 
	public CreateModule() {
		// To Do: these varibale should be delete or put on coordinator file
		// (not in ever place we define them)
		// this.model1=BuildModel.OntModel;
		// this.numEntity=BuildModel.NumEntity;
		// tempDir = BuildModel.wd;
		// ontName = BuildModel.ontologyName;
		// models=new ArrayList<OntModel>();
	}

	// public ArrayList<OntModel> getOntModels()
	// {
	// if(models==null)
	// return createOWLFiles_Phase(request);//createOutput_Phase3(); TO DO:
	// should save in coordinator
	// else
	// return models;
	// }

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// CreateOutput_old_Phase
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// public static ArrayList<OntModel> createOutput_old_Phase(Data data){
	public void createOutput_old_Phase(Data data) {
		LinkedHashMap<Integer, Cluster> clusters = data.getClusters();
		ArrayList<Cluster> list = new ArrayList<Cluster>();
		for (Iterator<Cluster> i = clusters.values().iterator(); i.hasNext();) {
			list.add(i.next());
		}
		LinkedHashMap<String, Integer> uriToClusterID = new LinkedHashMap<String, Integer>();
		for (int i = 0, n = list.size(); i < n; i++) {
			Cluster cluster = list.get(i);
			int clusterID = cluster.getClusterID();
			for (Iterator<Node> iter = cluster.listElements(); iter.hasNext();) {
				String uri = iter.next().toString();
				uriToClusterID.put(uri, clusterID);
			}
		}
		RDFSentenceGraph sg = new RDFSentenceGraph(data.getRbgModel().getOntModel());
		sg.build(); // build with list statement
		sg.filter(new OntologyHeaderFilter(sg.getOntologyURIs()));
		sg.filter(new PureSchemaFilter());
		// Creating one model for each partition to store them as separated
		// files
		ArrayList<OntModel> modules = new ArrayList<OntModel>(list.size()); 
		LinkedHashMap<Integer, Integer> clusterIDToOntModelID = new LinkedHashMap<Integer, Integer>();
		for (int i = 0, n = list.size(); i < n; i++) {
			modules.add(ModelFactory.createOntologyModel()); // Create as RDF
																// format
			int cid = list.get(i).getClusterID();
			clusterIDToOntModelID.put(cid, i);
		}
		for (int i = 0, n = sg.getRDFSentences().size(); i < n; i++) {
			RDFSentence sentence = sg.getRDFSentence(i);
			ArrayList<String> uris = sentence.getSubjectDomainVocabularyURIs();
			LinkedHashMap<Integer, Object> uniqueURIs = new LinkedHashMap<Integer, Object>();
			for (int j = 0, m = uris.size(); j < m; j++) {
				Integer clusterID = uriToClusterID.get(uris.get(j));
				if (clusterID != null) {
					uniqueURIs.put(clusterID, null);
				}
			}
			if (uniqueURIs.size() == 1) {
				Integer cid = uniqueURIs.keySet().iterator().next();
				Integer mid = clusterIDToOntModelID.get(cid);
				OntModel block = modules.get(mid);
				ArrayList<Statement> statements = sentence.getStatements();
				for (int j = 0, m = statements.size(); j < m; j++) {
					block.add(statements.get(j));
				}
			}
		}

		double[] numTree = new double[modules.size()];
		for (int i = 0; i < modules.size(); i++) {
			List Trlist = new ArrayList();
			OntModel mod = modules.get(i);
			OntClass thng = mod.getOntClass(OWL.Thing.getURI());
			Trlist = thng.listSubClasses(true).toList();
			if (list != null)
				numTree[i] = Trlist.size();
		}
		data.setNumTree(numTree);
		// return modules;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// CreateOutput_Phase
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// another implementation for owl file for each module

	// public static ArrayList<OntModel> createOWLFiles_Phase(HttpServletRequest
	// request, Data data)
	public static void createOWLFiles_Phase(Data data) {
		LinkedHashMap<Integer, Cluster> clusters = data.getClusters();
		ArrayList<Cluster> list = new ArrayList<Cluster>();
		for (Iterator<Cluster> i = clusters.values().iterator(); i.hasNext();) {
			list.add(i.next());
		}
		String onName = data.getOntName();
		InputStream in = null;
		InputStream fileStream = null;
		if (onName.endsWith(".owl"))
			try {
				in = new FileInputStream(onName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		else if (onName.endsWith(".gz")) {
			try {
				fileStream = new FileInputStream(new File(onName));
				in = new GZIPInputStream(fileStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
		config = config.setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
		StreamDocumentSource documentSource = new StreamDocumentSource(in);
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntologyManager managerN = null;
		OWLOntology owl = null, owlN = null;
		OntModel model = data.getOntModel();
		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLDataFactory factoryN = null;
		OWLClass thing = factory.getOWLThing();
		try {
			owl = manager.loadOntologyFromOntologyDocument(documentSource, config); // .loadOntologyFromOntologyDocument(in);
			/*
			 * OWLReasonerFactory reasonerFactory = new ElkReasonerFactory();
			 * OWLReasoner reasoner = reasonerFactory.createReasoner(owl);
			 * reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
			 */
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		NodeList nList = data.getRbgModel().getClassNodes();
		modules = new ArrayList<OntModel>();
		for (int i = 0; i < list.size(); i++) {
			Cluster cluster = list.get(i);
			managerN = OWLManager.createOWLOntologyManager();
			try {
				owlN = managerN.createOntology();
				factoryN = managerN.getOWLDataFactory();
			} catch (OWLOntologyCreationException e) {
				e.printStackTrace();
			}
			OWLDeclarationAxiom axiomT = factoryN.getOWLDeclarationAxiom(thing);
			AddAxiom adx = new AddAxiom(owlN, axiomT);
			managerN.applyChange(adx);
			Set<OWLClass> redClass = new HashSet();
			for (Iterator<Node> iter = cluster.listElements(); iter.hasNext();) {
				Node inode = iter.next();
				OntClass cls = model.getOntClass(inode.toString());
				// System.out.println("the new items\t"+cls.getLocalName());
				if (nList.contains(inode) && cls != null) {
					IRI iri = IRI.create(cls.getURI());
					OWLClass ocls = factory.getOWLClass(iri);
					OWLDeclarationAxiom axiom = factory.getOWLDeclarationAxiom(ocls);
					AddAxiom addAxiom = new AddAxiom(owlN, axiom);
					managerN.applyChange(addAxiom);
					Set<OWLClassAxiom> axioms = new HashSet<OWLClassAxiom>();
					axioms = owl.getAxioms(ocls);
					Iterator<OWLClassAxiom> it = axioms.iterator();
					while (it.hasNext()) {
						OWLClassAxiom ax = it.next();
						Iterator<OWLEntity> sg = ax.getSignature().iterator();
						while (sg.hasNext()) {
							String name = sg.next().getIRI().getShortForm().toString();
							if (cluster.getlistName().contains(name))// (clss))
								axioms.remove(ax); // Samira:why remove?
						}

					}
					managerN.addAxioms(owlN, axioms);
					Iterator<OWLClassExpression> ssub = ocls.getSubClasses(owl).iterator();
					while (ssub.hasNext()) {
						OWLClassExpression osubs = ssub.next();
						addAxiom = new AddAxiom(owlN, factory.getOWLSubClassOfAxiom(osubs, ocls));
						managerN.applyChange(addAxiom);
					}
					Set<OWLClassExpression> ssup = ocls.getSuperClasses(owl);
					Set<OWLAnnotationAssertionAxiom> axiomAn = ocls.getAnnotationAssertionAxioms(owl);
					managerN.addAxioms(owlN, axiomAn);
					addObjectPropDomain(owl, factory, managerN, ocls, owlN);
					// addObjectPropRange(owl,managerN,ocls,owlN);
					addDataPropDomain(owl, factory, managerN, ocls, owlN);
					// addDataPropRange(owl,managerN,ocls,owlN);
					Iterator<OWLIndividual> cid = ocls.getIndividuals(owl).iterator();
					while (cid.hasNext()) {
						OWLIndividual inv = cid.next();
						addAxiom = new AddAxiom(owlN, factory.getOWLClassAssertionAxiom(ocls, inv));
						managerN.applyChange(addAxiom);
					}
					if (!ssup.isEmpty()) {
						redClass.add(ocls);
					}
					if (ocls.getSuperClasses(owlN) == null)
						System.out.println(ocls.getIRI().toString());
				}

			}
			// OWLOntologyID newOntologyID = new OWLOntologyID(ontologyIRI,
			// versionIRI);
			// SetOntologyID setOntologyID = new SetOntologyID(owlN,
			// newOntologyID);
			// Apply the change
			// manager.applyChange(setOntologyID);
			// processAloneElments();
			// owlN=postProc(redClass,thing,owlN);
			/*
			 * OWLReasonerFactory reasonerFactory = new ElkReasonerFactory();
			 * OWLReasoner reasoner = reasonerFactory.createReasoner(owlN);
			 * reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
			 */
			// String outPath = tempDir + ontName + "_Module_" + i + ".owl";
			// String outPath = tempDir + ontName + "_Module_" + i + ".txt";
			// saveOntology(owlN,outPath);//"D:/result/test/"+ontName+"_Module_"+i+".owl");
			// //(owlN,outPath);
			
			String outPath = data.getOntName().substring(0,data.getOntName().length()-4) + "_Module_" + i + ".owl"; // TO DO#
			saveOntology(owlN, outPath, data);
			
//			HttpSession session = request.getSession();
//			session.setAttribute("owlfile", owlN);
		}
		data.setModules(modules);

		// used during modules quality evaluation
		double[] numTree = new double[data.getModules().size()];
		for (int i = 0; i < data.getModules().size(); i++) {
			List Trlist = new ArrayList();
			OntModel mod = data.getModules().get(i);
			OntClass thng = mod.getOntClass(OWL.Thing.getURI());
			Trlist = thng.listSubClasses(true).toList();
			if (list != null)
				numTree[i] = Trlist.size();
		}
		data.setNumTree(numTree);
//		HttpSession session = request.getSession();
//		session.setAttribute("numTree", numTree);

//		System.out.println("Modularization is done!!");

		// convert owl to txt and save in session
		// HttpSession session= request.getSession();
//		session.setAttribute("models", data.getModules());

		// return models; //or return modules? TO DO: should save in coordinator
		// (getmodules should work with it)
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void addObjectPropDomain(OWLOntology ontology, OWLDataFactory factory, OWLOntologyManager manager,
			OWLClass ocls, OWLOntology ont) {

		for (OWLObjectPropertyDomainAxiom op : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
			if (op.getDomain().equals(ocls)) {
				for (OWLObjectProperty oop : op.getObjectPropertiesInSignature()) {
					OWLAxiom axio = factory.getOWLObjectPropertyDomainAxiom(oop, ocls);
					AddAxiom ax = new AddAxiom(ont, axio);
					manager.applyChange(ax);
					Set<OWLObjectPropertyRangeAxiom> axiomO = ontology.getObjectPropertyRangeAxioms(oop);
					manager.addAxioms(ont, axiomO);

					// Set<OWLObjectPropertyDomainAxiom>
					// axiomO=ontology.getObjectPropertyDomainAxioms(oop);
					// manager.addAxioms(ont, axiomO);
					if (oop.isFunctional(ontology)) {
						OWLFunctionalObjectPropertyAxiom axiom = factory.getOWLFunctionalObjectPropertyAxiom(oop);
						AddAxiom addAxiom = new AddAxiom(ont, axiom);
						manager.applyChange(addAxiom);
					}

					if (oop.isInverseFunctional(ontology)) {
						OWLInverseFunctionalObjectPropertyAxiom axiom = factory
								.getOWLInverseFunctionalObjectPropertyAxiom(oop);
						AddAxiom addAxiom = new AddAxiom(ont, axiom);
						manager.applyChange(addAxiom);
					}
					if (oop.isReflexive(ontology)) {
						OWLReflexiveObjectPropertyAxiom axiom = factory.getOWLReflexiveObjectPropertyAxiom(oop);
						AddAxiom addAxiom = new AddAxiom(ont, axiom);
						manager.applyChange(addAxiom);
					}
					if (oop.isIrreflexive(ontology)) {
						OWLIrreflexiveObjectPropertyAxiom axiom = factory.getOWLIrreflexiveObjectPropertyAxiom(oop);
						AddAxiom addAxiom = new AddAxiom(ont, axiom);
						manager.applyChange(addAxiom);
					}
					if (oop.isSymmetric(ontology)) {
						OWLSymmetricObjectPropertyAxiom axiom = factory.getOWLSymmetricObjectPropertyAxiom(oop);
						AddAxiom addAxiom = new AddAxiom(ont, axiom);
						manager.applyChange(addAxiom);
					}
					if (oop.isTransitive(ontology)) {
						OWLTransitiveObjectPropertyAxiom axiom = factory.getOWLTransitiveObjectPropertyAxiom(oop);
						AddAxiom addAxiom = new AddAxiom(ont, axiom);
						manager.applyChange(addAxiom);
					}
					Iterator<OWLObjectPropertyExpression> sop = oop.getSubProperties(ontology).iterator();
					Set<OWLObjectPropertyExpression> eop = oop.getEquivalentProperties(ontology);
					Iterator<OWLObjectPropertyExpression> iop = oop.getInverses(ontology).iterator();
					while (sop.hasNext()) {
						OWLObjectPropertyExpression sopi = sop.next();
						AddAxiom addAxiom = new AddAxiom(ont, factory.getOWLSubObjectPropertyOfAxiom(sopi, oop));
						manager.applyChange(addAxiom);
					}
					while (iop.hasNext()) {
						OWLObjectPropertyExpression iopi = iop.next();
						AddAxiom addAxiom = new AddAxiom(ont, factory.getOWLInverseObjectPropertiesAxiom(oop, iopi));
						manager.applyChange(addAxiom);
					}
					AddAxiom addAxiom = new AddAxiom(ont, factory.getOWLEquivalentObjectPropertiesAxiom(eop));
					manager.applyChange(addAxiom);

				}
			}
		}
	}

	/*
	 * private void addObjectPropRange(OWLOntology ontology, OWLOntologyManager
	 * manager,OWLClass ocls,OWLOntology ont) {
	 * 
	 * for (OWLObjectPropertyRangeAxiom op :
	 * ontology.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE)) { if
	 * (op.getRange().equals(ocls)) { for(OWLObjectProperty oop :
	 * op.getObjectPropertiesInSignature()) { Set<OWLObjectPropertyRangeAxiom>
	 * axiomO=ontology.getObjectPropertyRangeAxioms(oop); manager.addAxioms(ont,
	 * axiomO);
	 * 
	 * }
	 * 
	 * } } }
	 */

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void addDataPropDomain(OWLOntology ontology, OWLDataFactory factory, OWLOntologyManager manager,
			OWLClass ocls, OWLOntology ont) {
		for (OWLDataPropertyDomainAxiom dp : ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN)) {
			if (dp.getDomain().equals(ocls)) {
				for (OWLDataProperty odp : dp.getDataPropertiesInSignature()) {
					OWLDataPropertyDomainAxiom axiomO = manager.getOWLDataFactory().getOWLDataPropertyDomainAxiom(odp,
							ocls);
					AddAxiom ax = new AddAxiom(ont, axiomO);
					manager.applyChange(ax);
					// Set<OWLDataRange> sdr=odp.getRanges(ontology);
					Set<OWLDataPropertyRangeAxiom> axiomDR = ontology.getDataPropertyRangeAxioms(odp);
					manager.addAxioms(ont, axiomDR);
					if (odp.isFunctional(ontology)) {
						AddAxiom axio = new AddAxiom(ont, factory.getOWLFunctionalDataPropertyAxiom(odp));
						manager.applyChange(axio);
					}
					Iterator<OWLDataPropertyExpression> edp = odp.getEquivalentProperties(ontology).iterator();
					Iterator<OWLDataPropertyExpression> sdp = odp.getSubProperties(ontology).iterator();
					while (edp.hasNext()) {
						OWLDataPropertyExpression idp = edp.next();
						AddAxiom axio = new AddAxiom(ont, factory.getOWLSubDataPropertyOfAxiom(idp, odp));
						manager.applyChange(axio);

					}

					while (sdp.hasNext()) {
						OWLDataPropertyExpression idp = sdp.next();
						AddAxiom axio = new AddAxiom(ont, factory.getOWLSubDataPropertyOfAxiom(idp, odp));
						manager.applyChange(axio);
					}
				}

			}
		}
	}

	/*
	 * private void addDataPropRange(OWLOntology ontology, OWLOntologyManager
	 * manager,OWLClass ocls,OWLOntology ont) {
	 * 
	 * for (OWLDataPropertyRangeAxiom dp :
	 * ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE)) { for(OWLDataProperty
	 * odp : dp.getDataPropertiesInSignature()) { Set<OWLDataPropertyRangeAxiom>
	 * axiomO=ontology.getDataPropertyRangeAxioms(odp); manager.addAxioms(ont,
	 * axiomO);
	 * 
	 * } } }
	 */

	private static void saveOntology(OWLOntology owlN, String loc, Data data) {
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntologyFormat format = new RDFXMLOntologyFormat();
		File file = new File(loc);
		try {
			manager.saveOntology(owlN, format, IRI.create(file.toURI()));
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
		OntDocumentManager mgr = new OntDocumentManager();
		mgr.setProcessImports(true);
		OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		spec.setDocumentManager(mgr);
		OntModel model = ModelFactory.createOntologyModel(spec, null);
		model.read("file:" + loc);
		// reasoner.bindSchema(model);
		model.setStrictMode(false);
		modules.add(model);
		

//		HttpSession session = request.getSession();
//		session.setAttribute("model", model);

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void createLink_Phase(Data data) {
		LinkedHashMap<Integer, Cluster> clusters = data.getClusters();
		for (Iterator<Cluster> i = clusters.values().iterator(); i.hasNext();) {
			Cluster icluster = i.next();
			// System.out.println("Cluster:\t"+i);icluster.printCluster();
			addProperties(icluster, data);
			// System.out.println(icluster.getClusterID()+"\t"+icluster.getElements().size());
			// icluster.printCluster();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void addProperties(Cluster icluster, Data data) {
		Iterator<Node> stm = data.getRbgModel().listStmtNodes();
		boolean isDatatype = false;
		Cluster tempCluster = new Cluster(0);
		while (stm.hasNext()) {
			Node istm = stm.next();
			Node iobject = istm.getObject();
			Node isubject = istm.getSubject();
			Node ipredicate = istm.getPredicate();
			if ((ipredicate.getLocalName() != null)
					&& (ipredicate.getLocalName().toLowerCase().toString().equals("domain")
							|| ipredicate.getLocalName().toLowerCase().toString().equals("range"))) {
				/*
				 * isDatatype = false; ExtendedIterator datalist=
				 * BuildModel.OntModel.listDatatypeProperties(); while
				 * (datalist.hasNext()){ Object id= datalist.next(); if
				 * (id.toString() == ipredicate.toString() || id.toString()
				 * ==isubject.toString() ){ isDatatype = true; } } if
				 * (isDatatype == false){
				 */
				int u = clusterExistence(iobject, isubject, icluster);
				if (u == 1) {
					// icluster.putElement(isubject.toString(), isubject);
					tempCluster.putElement(isubject.toString(), isubject);
				} else if (u == 2) {
					// icluster.putElement(iobject.toString(), iobject);
					tempCluster.putElement(iobject.toString(), iobject);
				}
				// }
			}
		}
		Iterator<Node> ilist = tempCluster.listElements();
		while (ilist.hasNext()) {
			Node ind = ilist.next();
			icluster.putElement(ind.toString(), ind);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int clusterExistence(Node inode, Node jnode, Cluster icluster) {
		int u = 0;

		Iterator<Node> ls = icluster.listElements();
		while (ls.hasNext()) {
			Node nd = ls.next();
			if (nd.equals(inode)) {
				u = 1;
			} else if (nd.equals(jnode)) {
				u = 2;
			}
		}
		return u;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// another implmentation of create output
	// public ArrayList<OntModel> createOutput_Phase(Data data)
	public void createOutput_Phase(Data data) {
		LinkedHashMap<Integer, Cluster> clusters = data.getClusters();
		NumLickConcept = new int[data.getNumCH()][data.getNumEntities() + 1]; 
		ArrayList<Cluster> list = new ArrayList<Cluster>();
		for (Iterator<Cluster> i = clusters.values().iterator(); i.hasNext();) {
			list.add(i.next());
		}
		LinkedHashMap<String, Integer> uriToClusterID = new LinkedHashMap<String, Integer>();
		for (int i = 0, n = list.size(); i < n; i++) {
			Cluster cluster = list.get(i);
			int clusterID = cluster.getClusterID();
			for (Iterator<Node> iter = cluster.listElements(); iter.hasNext();) {
				Node inode = iter.next();
				String uri = inode.toString();
				uriToClusterID.put(uri, clusterID);
			}
		}

		RDFSentenceGraph sg = new RDFSentenceGraph(data.getRbgModel().getOntModel());
		sg.build(); // build with list statement of original ontology model
		ArrayList<String> iii = sg.getOntologyURIs();
		sg.filter(new OntologyHeaderFilter(sg.getOntologyURIs()));
		sg.filter(new PureSchemaFilter());

		// Creating one model for each partition to store them as separated
		// files
		ArrayList<OntModel> models = new ArrayList<OntModel>(list.size()); 
		LinkedHashMap<Integer, Integer> clusterIDToOntModelID = new LinkedHashMap<Integer, Integer>();
		for (int i = 0, n = list.size(); i < n; i++) {
			models.add(ModelFactory.createOntologyModel()); // Create as RDF
															// format
			int cid = list.get(i).getClusterID();
			clusterIDToOntModelID.put(cid, i);
		}
		for (int i = 0, n = sg.getRDFSentences().size(); i < n; i++) {
			RDFSentence sentence = sg.getRDFSentence(i);
			ArrayList<String> uris = sentence.getSubjectDomainVocabularyURIs();
			LinkedHashMap<Integer, Object> uniqueURIs = new LinkedHashMap<Integer, Object>();
			for (int j = 0, m = uris.size(); j < m; j++) {
				Integer clusterID = uriToClusterID.get(uris.get(j));
				if (clusterID != null) {
					uniqueURIs.put(clusterID, null);
				}
			}
			if (uniqueURIs.size() == 1) {
				Integer cid = uniqueURIs.keySet().iterator().next();
				Integer mid = clusterIDToOntModelID.get(cid);
				OntModel block = models.get(mid); // mid is the cluster index
				ArrayList<Statement> statements = sentence.getStatements();

				for (int j = 0, m = statements.size(); j < m; j++) {
					block.add(statements.get(j));
					// if one statement add to the file, we should for its
					// subject-object save the number of link
					RDFNode ObjectURI = statements.get(j).getObject();
					RDFNode SubjectURI = statements.get(j).getSubject();
					RDFNode PropertyURI = statements.get(j).getPredicate();
					if (ObjectURI.isURIResource() && SubjectURI.isURIResource()) {
						String[] iProperty = PropertyURI.toString().split("\\#");
						String[] iSubject = SubjectURI.toString().split("\\#");
						String[] iObject = ObjectURI.toString().split("\\#");
						if (iProperty != null && iSubject != null && iObject != null) {
							if (iProperty.length > 1) {
								if (iProperty[1].toLowerCase().equals("subclassof")
										|| iProperty[1].toLowerCase().equals("haspropoerty")) { 
									int indexSubjectName = 0;
									if (iSubject.length > 1)
										indexSubjectName = BuildModel.findIndex(iSubject[1]);
									if (indexSubjectName > 0) {
										NumLickConcept[mid][indexSubjectName] = NumLickConcept[mid][indexSubjectName]
												+ 1;
									} // mid is the index of CH
									int indexObjectName = 0;
									if (iObject.length > 1)
										BuildModel.findIndex(iObject[1]);
									if (indexObjectName > 0) {
										NumLickConcept[mid][indexObjectName] = NumLickConcept[mid][indexObjectName] + 1;
									}
								}
							}
						}
					}
				}
			}
		}

		// adding root 1- for Root concept (RootTag=false) , 2- for those
		// element with numLink=1
		// First phase (1-for Root concept (RootTag=false))
		// if the node does not have superNode, we suppose it is Root and it is
		// alone, so we call addRoot() function for it
		numAloneElemnt = new int[data.getNumCH()];
		for (int ia = 0; ia < data.getNumEntities(); ia++) {
			if (data.getEntities().get(ia).getNamedSupers() == null) {
				Node alone_element = data.getEntities().get(ia);
				int indexCH_aloneElement = uriToClusterID.get(data.getEntities().get(ia).toString());
				addRoot(alone_element.toString(), indexCH_aloneElement, data); 
				numAloneElemnt[indexCH_aloneElement] = numAloneElemnt[indexCH_aloneElement] + 1;
				// since we add one link in the file (alone_elemenet,
				// subClassOf, "Thing"), so, we should count one link for
				// alone_element in the NumLickConcept array
				NumLickConcept[indexCH_aloneElement][ia] = NumLickConcept[indexCH_aloneElement][ia] + 2; 
				// we count those classes that are connected to alone_element
				Iterator<Node> listStm = data.getRbgModel().listStmtNodes();
				while (listStm.hasNext()) {
					Node st = listStm.next();
					if (st.getPredicate().getLocalName().toLowerCase().equals("subclassof")) {
						if (st.getSubject().getLocalName() != null && st.getObject().getLocalName() != null) {
							if (st.getSubject() == alone_element) {
								int isx = BuildModel.findIndex(st.getObject().getLocalName());
								if ((isx > 0) && (uriToClusterID.get(st.getObject().toString()) != null))
									NumLickConcept[indexCH_aloneElement][isx] = NumLickConcept[indexCH_aloneElement][isx]
											+ 2;
							}
							if (st.getObject() == alone_element) {
								int isx = BuildModel.findIndex(st.getSubject().getLocalName());
								if ((isx > 0) && (uriToClusterID.get(st.getSubject().toString()) != null))
									NumLickConcept[indexCH_aloneElement][isx] = NumLickConcept[indexCH_aloneElement][isx]
											+ 2;
							}
						}
					}
				}
			}
		}

		// Second phase (2- for those element with numLink=1)
		for (int i = 0; i < data.getNumCH(); i++) {
			for (int j = 0; j < data.getNumEntities(); j++) {
				if (NumLickConcept[i][j] == 1) {
					// addRoot(BuildModel.entities.get(j).toString(), i);
					numAloneElemnt[i] = numAloneElemnt[i] + 1;
					// add it in NumLinkConcept till do not add twice one
					// statements to Thing
					// we count those classes that are connected to
					// alone_element
					Iterator<Node> listStm = data.getRbgModel().listStmtNodes();
					while (listStm.hasNext()) {
						Node st = listStm.next();
						if (st.getPredicate().getLocalName().toLowerCase().equals("subclassof")) {
							if (st.getSubject().getLocalName() != null && st.getObject().getLocalName() != null) {
								if (st.getSubject() == data.getEntities().get(j)) {
									int isx = BuildModel.findIndex(st.getObject().getLocalName());
									if ((isx > 0) && (uriToClusterID.get(st.getObject().toString()) != null))
										NumLickConcept[i][isx] = NumLickConcept[i][isx] + 2;
								}
								if (st.getObject() == data.getEntities().get(j)) {
									int isx = BuildModel.findIndex(st.getSubject().getLocalName());
									if ((isx > 0) && (uriToClusterID.get(st.getSubject().toString()) != null))
										NumLickConcept[i][isx] = NumLickConcept[i][isx] + 2;
								}
							}
						}
					}
				}
			}
		}

		double[] numTree = new double[models.size()];
		for (int i = 0; i < models.size(); i++) {
			List Trlist = new ArrayList();
			OntModel model = models.get(i);
			OntClass thing = model.getOntClass(OWL.Thing.getURI());
			Trlist = thing.listSubClasses(true).toList();
			if (list != null)
				numTree[i] = Trlist.size();
		}
		data.setNumTree(numTree);

		// Creating Files in Temp folder
		for (int i = 0, n = models.size(); i < n; i++) {
			int cid = list.get(i).getClusterID();
			String filepath = data.getPath() + data.getOntName() + "_block_" + cid + ".owl";
			File file = new File(filepath);
			if (file.exists()) {
				file.delete();
			}
			Cluster c = list.get(cid);

			OntModel block = models.get(i); // Write one block as one model in
											// owl file
			try {
				FileOutputStream fos = new FileOutputStream(filepath);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				block.write(bos, "RDF/XML"); // XML format
				// block.write(bos, "Turtle"); //compact and more readable
				bos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			block.close();
		}
		for (int i = 0, n = models.size(); i < n; i++) {
			// System.out.println(); System.out.println();
			// System.out.println("Partition "+ i);
			int cid = list.get(i).getClusterID();
			String filepath2 = data.getPath() + data.getOntName() + "_block_" + cid + ".owl";
			try (BufferedReader br = new BufferedReader(new FileReader(filepath2))) {
				String line = null;
				while ((line = br.readLine()) != null) {
					// System.out.println(line);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		// return models;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// another implementation for creating output using OWL API
//	public ArrayList<OntModel> createOutput_Phase3(Data data) {
	public void createOutput_Phase3(Data data) {
		LinkedHashMap<Integer, Cluster> clusters = data.getClusters();
		NumLickConcept = new int[data.getNumCH()][data.getNumEntities() + 1]; 
		ArrayList<Cluster> list = new ArrayList<Cluster>();
		for (Iterator<Cluster> i = clusters.values().iterator(); i.hasNext();) {
			list.add(i.next());
		}
		// >>>start to creating a list of OWLmodels
		ArrayList<OntModel> modules = new ArrayList<OntModel>(list.size()); // create
																			// one
																			// array
																			// of
																			// OWLmodels
		for (int i = 0, n = list.size(); i < n; i++) {
			modules.add(ModelFactory.createOntologyModel());
		}
		// >>>> finish creating a list of OWLmodels

		// >> Saving which nodes exist in which cluster(module), e.g "Paper"
		// node exist in cluster 1 since its clusterID is 1
		for (int i = 0, n = list.size(); i < n; i++) {
			Cluster cluster = list.get(i);
			int clusterID = cluster.getClusterID();
			for (Iterator<Node> iter = cluster.listElements(); iter.hasNext();) {
				Node inode = iter.next();
				String uri = inode.toString();
				uriToClusterID.put(uri, clusterID);
			}
		}
		// >> Finishing the saving index of cluster's node

		// >>>> Start to process all statements of original model, then put each
		// statements in proper modules
		// for (int i = 0, n = Controller.KNumCH; i < n; i++)
		{
			StmtIterator stm = data.getOntModel().listStatements();
			while (stm.hasNext()) {
				Statement istm = stm.nextStatement();
				Resource isubject = istm.getSubject();
				RDFNode iobject = istm.getObject();
				Property ipredicate = istm.getPredicate();
				if (isubject.getLocalName() != null) // we do not add those
														// statements that
														// contain a "Blank
														// nodes"
				{
					Integer clusterID = uriToClusterID.get(isubject.toString());
					if (clusterID != null) {
						modules.get(clusterID).add(istm);
						countLink(istm, clusterID);
					}
				} else if (iobject != null) {
					Integer clusterID = uriToClusterID.get(iobject.toString());
					if (clusterID != null) {
						modules.get(clusterID).add(istm);
						countLink(istm, clusterID);
					}
				} else if (ipredicate.getLocalName() != null) { // we do not add
																// those
																// statements
																// that contain
																// a "Blank
																// nodes"
					Integer clusterID = uriToClusterID.get(ipredicate.toString());
					if (clusterID != null) {
						modules.get(clusterID).add(istm);
						countLink(istm, clusterID);
					}
				}
			}
		}
		// >>>> Finish processing statements
		processAloneElments(data);

		// >>save our generated modules as files in temp folder
		for (int i = 0, n = modules.size(); i < n; i++) {
			int cid = list.get(i).getClusterID();
			String filepath = data.getPath() + data.getOntName() + "_Module_" + cid + ".owl";
			File file = new File(filepath);
			if (file.exists()) {
				file.delete();
			}
			OntModel block = modules.get(i); // Write one block as one model in
												// owl file
			try {
				FileOutputStream fos = new FileOutputStream(filepath);// create
																		// on
																		// the
																		// local
																		// address
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				try {
					Model m = block.write(bos, "RDF/XML"); // XML format
				} catch (BadURIException e) {
					block.write(bos, "TURTLE");
				}
				bos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// block.close(); //TO DO: it should not comment (but if it works,
			// we reach to error in AddRoot function (ClosedException: already
			// closed))
		}
		// >>finishing saving

		// Save values for Evaluating panel
		double[] numTree = new double[modules.size()];
		for (int i = 0; i < modules.size(); i++) {
			List Trlist = new ArrayList();
			OntModel model = modules.get(i);
			OntClass thing = model.getOntClass(OWL.Thing.getURI());
			Trlist = thing.listSubClasses(true).toList();
			if (list != null)
				numTree[i] = Trlist.size();
		}
		data.setNumTree(numTree);

		// return models;

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	private void countLink(Statement statements, Integer clusterID) {
		// if one statement add to the file, we should for its subject-object
		// save the number of link
		RDFNode ObjectURI = statements.getObject();
		RDFNode SubjectURI = statements.getSubject();
		RDFNode PropertyURI = statements.getPredicate();
		if (ObjectURI.isURIResource() && SubjectURI.isURIResource()) {
			String[] iProperty = PropertyURI.toString().split("\\#");
			String[] iSubject = SubjectURI.toString().split("\\#");
			String[] iObject = ObjectURI.toString().split("\\#");
			if (iProperty != null && iSubject != null && iObject != null) {
				if (iProperty.length > 1) {
					if (iProperty[1].toLowerCase().equals("subclassof")
							|| iProperty[1].toLowerCase().equals("haspropoerty")) { // TO
																					// DO:
																					// we
																					// should
																					// those
																					// acceptable
																					// property
																					// in
																					// this
																					// line
																					// such
																					// as
																					// SubclassOf
						int indexSubjectName = 0;
						if (iSubject.length > 1)
							indexSubjectName = BuildModel.findIndex(iSubject[1]);
						if (indexSubjectName > 0) {
							NumLickConcept[clusterID][indexSubjectName] = NumLickConcept[clusterID][indexSubjectName]
									+ 1;
						} // mid is the index of CH
						int indexObjectName = 0;
						if (iObject.length > 1)
							BuildModel.findIndex(iObject[1]);
						if (indexObjectName > 0) {
							NumLickConcept[clusterID][indexObjectName] = NumLickConcept[clusterID][indexObjectName] + 1;
						}
					}
				}
			}
		}

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void processAloneElments(Data data) {
		// adding root 1- for Root concept (RootTag=false) , 2- for those
		// element with numLink=1
		// First phase (1-for Root concept (RootTag=false))
		// if the node does not have superNode, we suppose it is Root and it is
		// alone, so we call addRoot() function for it
		numAloneElemnt = new int[data.getNumCH()]; // delete
		for (int ia = 0; ia < data.getNumEntities(); ia++) {
			Node alone_element = data.getEntities().get(ia);
			if (alone_element != null) {
				// System.out.println(uriToClusterID.size());
				Integer indexCH_aloneElement = uriToClusterID.get(alone_element.toString());
				if (indexCH_aloneElement != null) {
					NodeList supAloneElem = data.getEntities().get(ia).getNamedSupers();
					if (supAloneElem == null) {
						ConnectAloneElement(alone_element, indexCH_aloneElement, ia, data);
					} else { // Maybe one concept has superNode but its
								// superNode exist in another cluster, in this
								// case, this concept is alone in VS
						boolean test = false;
						for (int s = 0; s < supAloneElem.size(); s++) {
							test = ExistinClusterTest(supAloneElem.get(s), indexCH_aloneElement, data);
						}
						if (test == false) {
							ConnectAloneElement(alone_element, indexCH_aloneElement, ia, data);
						}
					}
				}
			}
		}

		// Second phase (2- for those element with numLink=1)
		for (int i = 0; i < data.getNumCH(); i++) {
			for (int j = 0; j < data.getNumEntities(); j++) {
				if (data.getEntities().get(j).getLocalName().toString().toLowerCase().equals("laminar")) {
					int wait2 = 0;
				}
				if (NumLickConcept[i][j] == 1) {
					addRoot(data.getEntities().get(j).toString(), i, data);
					numAloneElemnt[i] = numAloneElemnt[i] + 1;
					// add it in NumLinkConcept till do not add twice one
					// statements to Thing
					// we count those classes that are connected to
					// alone_element
					Iterator<Node> listStm = data.getRbgModel().listStmtNodes();
					while (listStm.hasNext()) {
						Node st = listStm.next();
						if (st.getPredicate().getLocalName().toLowerCase().equals("subclassof")) {
							if (st.getSubject().getLocalName() != null && st.getObject().getLocalName() != null) {
								if (st.getSubject() == data.getEntities().get(j)) {
									int isx = BuildModel.findIndex(st.getObject().getLocalName());
									if ((isx > 0) && (uriToClusterID.get(st.getObject().toString()) != null))
										NumLickConcept[i][isx] = NumLickConcept[i][isx] + 2;
								}
								if (st.getObject() == data.getEntities().get(j)) {
									int isx = BuildModel.findIndex(st.getSubject().getLocalName());
									if ((isx > 0) && (uriToClusterID.get(st.getSubject().toString()) != null))
										NumLickConcept[i][isx] = NumLickConcept[i][isx] + 2;
								}
							}
						}
					}
				}
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private boolean ExistinClusterTest(Node inode, int NumCluster, Data data) {
		boolean test = false;
		List ilist = new ArrayList();
		ilist = data.getModules().get(NumCluster).listClasses().toList();
		for (int i = 0; i < ilist.size(); i++) {
			if (ilist.get(i).toString().equals(inode.toString())) {
				test = true;
				return test;
			}
		}
		// System.out.println(test);
		return test;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void ConnectAloneElement(Node alone_element, int indexCH_aloneElement, int ia, Data data) {
		addRoot(alone_element.toString(), indexCH_aloneElement, data); // add this
																	// element
																	// in the ch
																	// block
		numAloneElemnt[indexCH_aloneElement] = numAloneElemnt[indexCH_aloneElement] + 1;
		// since we add one link in the file (alone_elemenet, subClassOf,
		// "Thing"), so, we should count one link for alone_element in the
		// NumLickConcept array
		NumLickConcept[indexCH_aloneElement][ia] = NumLickConcept[indexCH_aloneElement][ia] + 2; 
		// we count those classes that are connected to alone_element
		Iterator<Node> listStm = data.getRbgModel().listStmtNodes();
		while (listStm.hasNext()) {
			Node st = listStm.next();
			if (st.getPredicate().getLocalName().toLowerCase().equals("subclassof")) {
				if (st.getSubject().getLocalName() != null && st.getObject().getLocalName() != null) {
					if (st.getSubject() == alone_element) {
						int isx = BuildModel.findIndex(st.getObject().getLocalName());
						if ((isx > 0 && isx <= data.getNumEntities()) && (uriToClusterID.get(st.getObject().toString()) != null))
							NumLickConcept[indexCH_aloneElement][isx] = NumLickConcept[indexCH_aloneElement][isx] + 2;
					}
					if (st.getObject() == alone_element) {
						int isx = BuildModel.findIndex(st.getSubject().getLocalName());
						if ((isx > 0 && isx <= data.getNumEntities()) && (uriToClusterID.get(st.getSubject().toString()) != null))
							NumLickConcept[indexCH_aloneElement][isx] = NumLickConcept[indexCH_aloneElement][isx] + 2;
					}
				}
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// AddingRoot_Phase
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void addRoot(String concept, int NumCluster, Data data) {

		OntModel block = data.getModules().get(NumCluster);
		Resource subjectNew = ResourceFactory.createResource(concept);
		Property predicateNew = ResourceFactory.createProperty("http://www.w3.org/2000/01/rdf-schema#subClassOf");
		RDFNode objectNew = ResourceFactory.createResource("http://www.w3.org/2002/07/owl#Thing");
		Statement statementNew = block.createStatement(subjectNew, predicateNew, objectNew);
		block.add(statementNew);
	}

	/////////////////////////// Another create method
	/*
	 * public ArrayList<OntModel> createOutput_Phase2() { ArrayList<Cluster>
	 * list = new ArrayList<Cluster>(); for (Iterator<Cluster> i =
	 * clusters.values().iterator(); i.hasNext();) { list.add(i.next()); }
	 * 
	 * //>>>start to creating a list of OWLmodels OWLOntologyManager manager=
	 * OWLManager.createOWLOntologyManager(); ArrayList<OWLOntology> owlmodels =
	 * new ArrayList<OWLOntology>(list.size()); // create one array of OWLmodels
	 * for (int i = 0, n = list.size(); i < n; i++) { try {
	 * owlmodels.add(manager.createOntology()); } catch
	 * (OWLOntologyCreationException e) { e.printStackTrace(); } } //>>>> finish
	 * creating a list of OWLmodels
	 * 
	 * 
	 * //>> Saving which nodes exist in which cluster(module), e.g "Paper" node
	 * exist in cluster 1 since its clusterID is 1 for (int i = 0, n =
	 * list.size(); i < n; i++) { Cluster cluster = list.get(i); int clusterID =
	 * cluster.getClusterID(); for (Iterator<Node> iter =
	 * cluster.listElements(); iter.hasNext();) { Node inode= iter.next();
	 * String uri = inode.toString(); uriToClusterID.put(uri, clusterID); } }
	 * //>> Finishing the saving index of cluster's node
	 * 
	 * 
	 * //>>>> Start to process all statements of original model, then put each
	 * statements in proper modules for (int i = 0, n = Controller.KNumCH; i <
	 * n; i++) { Iterator<Node> stm= BuildModel.rbgmModel.listStmtNodes();
	 * //Read all statements from original model , then start to processing it
	 * OWLDataFactory factory = manager.getOWLDataFactory(); while
	 * (stm.hasNext()){ Node istm= stm.next(); Node iobject = istm.getObject();
	 * Node isubject = istm.getSubject(); Node ipredicate = istm.getPredicate();
	 * if ((ipredicate.getLocalName() != null) &&
	 * (ipredicate.getLocalName().toLowerCase().toString().equals("domain"))) {
	 * //create an axiom as subclass link in our new model if
	 * (iobject.getLocalName() != null){ OWLClass clsA =
	 * factory.getOWLClass(IRI.create(iobject.toString())); OWLClass clsB =
	 * factory.getOWLClass(IRI.create(isubject.toString())); OWLAxiom axiom =
	 * factory.getOWLSubClassOfAxiom(clsA,clsB ); Integer clusterID =
	 * uriToClusterID.get(iobject.toString()); if (clusterID != null){ AddAxiom
	 * addAxiom = new AddAxiom((OWLOntology) owlmodels.get(clusterID), axiom);
	 * manager.applyChange(addAxiom); } }
	 * 
	 * }else if ((ipredicate.getLocalName() != null) && (
	 * ipredicate.getLocalName().toLowerCase().toString().equals("range"))) {
	 * //create an axiom as subclass link in our new model if
	 * (isubject.getLocalName() != null ){ OWLClass clsA =
	 * factory.getOWLClass(IRI.create(iobject.toString())); OWLClass clsB =
	 * factory.getOWLClass(IRI.create(isubject.toString())); OWLAxiom axiom =
	 * factory.getOWLSubClassOfAxiom(clsB,clsA ); Integer clusterID =
	 * uriToClusterID.get(isubject.toString()); if (clusterID != null){ AddAxiom
	 * addAxiom = new AddAxiom((OWLOntology) owlmodels.get(clusterID), axiom);
	 * manager.applyChange(addAxiom); } } }else if
	 * (ipredicate.getLocalName().toLowerCase().toString().equals(
	 * "dataproperties")) { int tempp=0;
	 * 
	 * }else if
	 * (ipredicate.getLocalName().toLowerCase().toString().equals("type")) { int
	 * temp=0; }
	 * 
	 * } }
	 * 
	 * //>>save our generated modules as files in temp folder//TO DO: it does
	 * not work for (int i = 0, n = Controller.KNumCH; i < n; i++) { int cid =
	 * list.get(i).getClusterID(); String filepath = tempDir + ontName +
	 * "_block_" + cid + ".owl"; File file = new File(filepath); if
	 * (file.exists()) { file.delete(); file = new File(filepath); //If the file
	 * exist, first delete it, then crate again it, in this case we delete
	 * previous data on the file (if the file was exist before) } try {
	 * FileOutputStream fos = new FileOutputStream(filepath);
	 * BufferedOutputStream bos = new BufferedOutputStream(fos); bos.close();
	 * fos.close(); } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * OWLOntology ontology; try { ontology =
	 * manager.loadOntologyFromOntologyDocument(file); ontology =
	 * owlmodels.get(i); } catch (OWLOntologyCreationException e) {
	 * e.printStackTrace(); } } //>>finishing saving
	 * 
	 * return models; //It should return owlsmodels (but it need changes other
	 * part of project) }
	 */

}
