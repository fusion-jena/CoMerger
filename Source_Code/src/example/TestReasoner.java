package example;

import org.semanticweb.owl.explanation.api.*;
import org.semanticweb.owl.explanation.impl.blackbox.checker.InconsistentOntologyExplanationGeneratorFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owl.explanation.api.ExplanationGenerator;
//import org.apache.lucene.search.Explanation;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
//import org.semanticweb.owl.explanation.impl.blackbox.checker.InconsistentOntologyExplanationGeneratorFactory;

//import com.clarkparsia.owlapi.explanation.ExplanationGenerator;
import com.clarkparsia.owlapi.explanation.TransactionAwareSingleExpGen;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import java.io.File;
import java.util.Set;
//import org.semanticweb.owl.explanation.api.Explanation;
//import org.semanticweb.owl.explanation.api.ExplanationGenerator;
//import org.semanticweb.owl.explanation.impl.blackbox.checker.InconsistentOntologyExplanationGeneratorFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
//import uk.ac.manchester.cs.jfact.JFactFactory;

public class TestReasoner {
	public static void main(String[] args) throws Exception {

//		test();
//	}

	

//	public static void test() throws Exception {
		// https://stackoverflow.com/questions/33150182/how-to-get-an-explanation-for-an-inconsistency-using-the-owlexplanation-project
		// OWLReasonerFactory rf = new JFactFactory();

		OWLOntologyManager MergedManager = OWLManager.createOWLOntologyManager();
		String name = "C:\\Users\\Samira\\Downloads\\MergedOnt690\\MergedOnt690.owl";
		File fileM = new File(name);
		OWLOntologyManager tempManager = OWLManager.createOWLOntologyManager();

		OWLReasonerFactory rf = new PelletReasonerFactory();
		OWLOntology ont = tempManager.loadOntologyFromOntologyDocument(fileM);
		OWLOntologyManager m = ont.getOWLOntologyManager();
		OWLDataFactory df = m.getOWLDataFactory();
		OWLClass class1 = df.getOWLClass(IRI.create("urn:test:class1"));
		OWLClass class2 = df.getOWLClass(IRI.create("urn:test:class2"));
		OWLIndividual i = df.getOWLNamedIndividual(IRI.create("urn:test:i"));
		// create an inconsistent ontology by declaring an individual member of
		// two disjoint classes
		m.addAxiom(ont, df.getOWLDisjointClassesAxiom(class1, class2));
		m.addAxiom(ont, df.getOWLClassAssertionAxiom(class1, i));
		m.addAxiom(ont, df.getOWLClassAssertionAxiom(class2, i));
		// create the explanation generator
		ExplanationGenerator<OWLAxiom> explainInconsistency = new InconsistentOntologyExplanationGeneratorFactory(rf,
				1000L).createExplanationGenerator(ont);
		// Ask for an explanation of `Thing subclass of Nothing` - this axiom is
		// entailed in any inconsistent ontology
		Set<Explanation<OWLAxiom>> explanations = explainInconsistency
				.getExplanations(df.getOWLSubClassOfAxiom(df.getOWLThing(), df.getOWLNothing()));
		System.out.println("TestExplanation.main() " + explanations);
	}
	private void test2() throws OWLOntologyCreationException {
		OWLOntologyManager MergedManager = OWLManager.createOWLOntologyManager();
		String m = "C:\\Users\\Samira\\Downloads\\MergedOnt690\\MergedOnt690.owl";
		File fileM = new File(m);
		OWLOntologyManager tempManager = OWLManager.createOWLOntologyManager();
		OWLOntology myont = tempManager.loadOntologyFromOntologyDocument(fileM);

		OWLReasonerFactory reasonerFactory = new PelletReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(myont);
		// TODO: should be here or not?
		// Ask the reasoner to do all the necessary work now
		reasoner.precomputeInferences();

		String answer = "";

		if (reasoner.isConsistent()) {
			// an ontology can be consistent, but have unsatisfiable classes.
			if (reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom().size() > 0) {
				// means: an ontology is consistent but unsatisfiable!
				answer = "Merged ontology FAILED satisfiability test with Pellet reasoner. \n Unsatisfiable classes detected: "
						+ reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom().size();
				Iterator<OWLClass> aList = reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom().iterator();
				// TODO: getUnsatisfiableProperties
				while (aList.hasNext()) {
					OWLClass p = aList.next();
					answer = answer + "\n  -> " + p.toString().substring(1, p.toString().length() - 1);
					Iterator<OWLClassAxiom> ax = myont.getAxioms(p).iterator();
					System.out.println("UnsatisfiableClass::: " + p);
					while (ax.hasNext()) {
						System.out.println("    " + ax.next());
						TransactionAwareSingleExpGen tt = null;
						HSTExplanationGenerator hs = new HSTExplanationGenerator(tt);
						hs.getExplanations(p);

						ExplanationGeneratorFactory<OWLAxiom> genFac = ExplanationManager
								.createExplanationGeneratorFactory(reasonerFactory);

						// Now create the actual explanation generator for our
						// ontology
						ExplanationGenerator<OWLAxiom> gen = genFac.createExplanationGenerator(myont);

						// Ask for explanations for some entailment
						OWLAxiom entailment = null; // Get a reference to the
													// axiom that represents the
													// entailment that we want
													// explanation for

						// Get our explanations. Ask for a maximum of 5.
						Set<Explanation<OWLAxiom>> expl = gen.getExplanations(entailment, 5);
					}
				}
				answer = answer + "\n";

			} else {
				answer = "Merged ontology PASSED the consistency test.";
			}
		} else {
			answer = "Merged ontology FAILED the consistency test, please review the Axioms or debug using Protege";
			// FYI an example how to implement a working debugger can be found
			// on sourceforge's OWL API page under Debugger
		}
		reasoner.dispose();
		System.out.println(answer);

	}
}

// Implemantation of Reiters Algorithm
// http://www.hermit-reasoner.com/download/0.9.2/owlapi/javadoc/com/clarkparsia/explanation/HSTExplanationGenerator.html
// http://soft.vub.ac.be/svn-pub/PlatformKit/platformkit-kb-owlapi3-doc/doc/owlapi3/javadoc/com/clarkparsia/owlapi/explanation/BlackBoxExplanation.html