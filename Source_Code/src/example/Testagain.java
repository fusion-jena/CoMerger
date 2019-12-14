package example;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owl.explanation.api.Explanation;
import org.semanticweb.owl.explanation.api.ExplanationGenerator;
import org.semanticweb.owl.explanation.impl.blackbox.checker.InconsistentOntologyExplanationGeneratorFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

//import uk.ac.manchester.cs.jfact.JFactFactory;
public class Testagain {
	public static void main(String[] args) throws Exception {
		 
		
		//OWLReasonerFactory rf = new JFactFactory();
		OWLReasonerFactory rf = new PelletReasonerFactory();

		
		String mm = "C:\\Users\\Samira\\Downloads\\MergedOnt690\\MergedOnt690.owl";
		File fileM = new File(mm);
		OWLOntologyManager tempManager = OWLManager.createOWLOntologyManager();
		OWLOntology ont = tempManager.loadOntologyFromOntologyDocument(fileM);
		

		// OWLReasoner owlreasoner = new
		// Reasoner.ReasonerFactory().createReasoner(ont);
		// OWLOntology ont =
		// OWLManager.createOWLOntologyManager().createOntology();

		OWLDataFactory df = ont.getOWLOntologyManager().getOWLDataFactory();
//		OWLClass class1 = df.getOWLClass(IRI.create("urn:test:class1"));
//		OWLClass class2 = df.getOWLClass(IRI.create("urn:test:class2"));
//		OWLIndividual i = df.getOWLNamedIndividual(IRI.create("urn:test:i"));
		// create an inconsistent ontology by declaring an individual member of
		// two disjoint classes
		// m.addAxiom(ont, df.getOWLDisjointClassesAxiom(class1, class2));
		// m.addAxiom(ont, df.getOWLClassAssertionAxiom(class1, i));
		// m.addAxiom(ont, df.getOWLClassAssertionAxiom(class2, i));
		// create the explanation generator
		ExplanationGenerator<OWLAxiom> explainInconsistency = new InconsistentOntologyExplanationGeneratorFactory(rf,
				1000L).createExplanationGenerator(ont);
		// Ask for an explanation of `Thing subclass of Nothing` - this axiom is
		// entailed in any inconsistent ontology
		Set<Explanation<OWLAxiom>> explanations = explainInconsistency
				.getExplanations(df.getOWLSubClassOfAxiom(df.getOWLThing(), df.getOWLNothing()));
		System.out.println("TestExplanation.main() " + explanations);

		OWLReasoner reasoner = rf.createNonBufferingReasoner(ont);		
		if (reasoner.isConsistent()) {
			// an ontology can be consistent, but have unsatisfiable classes.
			if (reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom().size() > 0) {
				// means: an ontology is consistent but unsatisfiable!
				System.out.println(
						"Merged ontology FAILED satisfiability test with Pellet reasoner. \n Unsatisfiable classes detected: "
								+ reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom().size());
				Iterator<OWLClass> aList = reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom().iterator();
				// TODO: getUnsatisfiableProperties
				while (aList.hasNext()) {
				}
			}
		}
		

		test();
		
	}
	 public static void test() throws Exception {
		    OWLReasonerFactory rf = new   PelletReasonerFactory();
//		    OWLOntology ont = OWLManager.createOWLOntologyManager().createOntology();
		    String mm = "C:\\Users\\Samira\\Downloads\\MergedOnt690\\MergedOnt690.owl";
			File fileM = new File(mm);
			OWLOntology ont = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(fileM);
		    
		    
		    OWLOntologyManager m = ont.getOWLOntologyManager();
		    OWLDataFactory df = m.getOWLDataFactory();
		    /*
		    OWLClass class1 = df.getOWLClass(IRI.create("urn:test:class1"));
		    OWLClass class2 = df.getOWLClass(IRI.create("urn:test:class2"));
		    OWLIndividual i = df.getOWLNamedIndividual(IRI.create("urn:test:i"));
		    // create an inconsistent ontology by declaring an individual member of two disjoint classes
		    m.addAxiom(ont, df.getOWLDisjointClassesAxiom(class1, class2));
		    m.addAxiom(ont, df.getOWLClassAssertionAxiom(class1, i));
		    m.addAxiom(ont, df.getOWLClassAssertionAxiom(class2, i));
		    */
		    // create the explanation generator
		    ExplanationGenerator<OWLAxiom> explainInconsistency = new InconsistentOntologyExplanationGeneratorFactory(rf,
		        1000L).createExplanationGenerator(ont);
		    // Ask for an explanation of `Thing subclass of Nothing` - this axiom is entailed in any inconsistent ontology
		    Set<Explanation<OWLAxiom>> explanations = explainInconsistency.getExplanations(df.getOWLSubClassOfAxiom(df
		        .getOWLThing(), df.getOWLNothing()));
		    System.out.println("TestExplanation.main() " + explanations);
		  }
}