package fusion.comerger.algorithm.merger.holisticMerge.consistency;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import fusion.comerger.algorithm.merger.model.HModel;

public class GetInconsistency {

	public static void run(HModel ontM) {
		// save the Tbox and Abox of the original ontology
		Set<OWLAxiom> originalTboxAxioms = ontM.getOwlModel().getTBoxAxioms(true);
		Set<OWLAxiom> originalAboxAxioms = ontM.getOwlModel().getABoxAxioms(true);

		// create new empty ontology
		String name = ontM.getOntName(); // "local_path//name.owl//
		int t = name.indexOf(".owl");
		name = name.substring(0, t) + "new.owl";
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		File fileM = new File(name);
		OWLOntology newOntology = null;

		try {
			newOntology = manager.createOntology(IRI.create(fileM));
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

		// add only Tboxes from the orginal ontology to the new one
		manager.addAxioms(newOntology, originalTboxAxioms);

		// checking the consistency of the new ontology which contain only tbox
		OWLReasonerFactory reasonerFactory = new PelletReasonerFactory();
		Configuration configuration = new Configuration();
		configuration.throwInconsistentOntologyException = false;
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(newOntology, configuration);

		System.out.println("finished creating reasoner in new function");
		System.out.println("start to test reasoner.isConsistent()");
		if (reasoner.isConsistent()) {
			System.out.println("start to test getUnsatisfiableClasses in new function");
			Set<OWLClass> unSat = reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom();
			if (unSat.size() > 0) {
				Iterator<OWLClass> unClassList = unSat.iterator();
				System.out.println("finished getting UnsatisfiableClasses in new function");
				Set<OWLClass> listOfUnsatisfiableClasses = new HashSet<OWLClass>();
				while (unClassList.hasNext()) {
					/*
					 * if the unsatisfiable class appear in the original Abox,
					 * we mark it as an unsatisfiable class
					 */
					OWLClass myClass = unClassList.next();
					
					Iterator<OWLAxiom> iter = originalAboxAxioms.iterator();
					while (iter.hasNext()){
						OWLAxiom ax = iter.next();
						if (ax.getClassesInSignature().contains(myClass)){
							listOfUnsatisfiableClasses.add(myClass);	
						}
					}
					
						
				}
				System.out.println(" \t *******number of unsatisfiable classes: " + listOfUnsatisfiableClasses.size());
			} else {
				System.out.println("The ontology is inconsistent but does not have any unsatisfiable classes!!!!!");
			}
		} else {
			System.out.println("Strange!!!!");
		}
	}
}
