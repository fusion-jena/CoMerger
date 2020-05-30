package fusion.comerger.algorithm.merger.holisticMerge.consistency;

import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owl.explanation.api.Explanation;
import org.semanticweb.owl.explanation.api.ExplanationGenerator;
import org.semanticweb.owl.explanation.impl.blackbox.checker.InconsistentOntologyExplanationGeneratorFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

//import uk.ac.manchester.cs.jfact.JFactFactory;

public class TestExplanation {
	public static void main(String[] args) throws Exception {

		getExplanation();
	}

	private static void getExplanation() throws Exception {
		OWLReasonerFactory rf = new PelletReasonerFactory();
		// OWLReasoner reasoner = rf.createNonBufferingReasoner(ont);

		// OWLReasonerFactory rf = new JFactFactory();
		OWLOntology ont = OWLManager.createOWLOntologyManager().createOntology();
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
		
		System.out.println("\n details: \n");
		Iterator<Explanation<OWLAxiom>> iter = explanations.iterator();
		while (iter.hasNext()){
			Explanation<OWLAxiom> a = iter.next();
			Iterator<OWLAxiom> axiom = a.getAxioms().iterator();
			while (axiom.hasNext()){
				OWLAxiom ax = axiom.next();
				System.out.println(ax);
			}
			
		}
	}

	public static void getInconsistentOntExplanation(OWLOntology ont, OWLOntologyManager m) throws Exception {
		OWLReasonerFactory rf = new PelletReasonerFactory();
		// OWLReasoner reasoner = rf.createNonBufferingReasoner(ont);

		// OWLReasonerFactory rf = new JFactFactory();
		// OWLOntology ont =
		// OWLManager.createOWLOntologyManager().createOntology();
		// OWLOntologyManager m = ont.getOWLOntologyManager();
		 OWLDataFactory df = m.getOWLDataFactory();
		// OWLClass class1 = df.getOWLClass(IRI.create("urn:test:class1"));
		// OWLClass class2 = df.getOWLClass(IRI.create("urn:test:class2"));
		// OWLIndividual i = df.getOWLNamedIndividual(IRI.create("urn:test:i"));
		// create an inconsistent ontology by declaring an individual member of
		// two disjoint classes
		// m.addAxiom(ont, df.getOWLDisjointClassesAxiom(class1, class2));
		// m.addAxiom(ont, df.getOWLClassAssertionAxiom(class1, i));
		// m.addAxiom(ont, df.getOWLClassAssertionAxiom(class2, i));
		// create the explanation generator
		 System.out.println("Start to create new ExplanationGenerator");
		ExplanationGenerator<OWLAxiom> explainInconsistency = new InconsistentOntologyExplanationGeneratorFactory(rf,
				Long.MAX_VALUE).createExplanationGenerator(ont);//Long.MAX_VALUE
		// Ask for an explanation of `Thing subclass of Nothing` - this axiom is
		// entailed in any inconsistent ontology
		System.out.println("Start to getExplanations");
		Set<Explanation<OWLAxiom>> explanations = explainInconsistency
				.getExplanations(df.getOWLSubClassOfAxiom(df.getOWLThing(), df.getOWLNothing()));
		
		System.out.println("finished getExplanations");
		
		System.out.println("TestExplanation.main() " + explanations);
		
		Iterator<Explanation<OWLAxiom>> iter = explanations.iterator();
		while (iter.hasNext()){
			Explanation<OWLAxiom> a = iter.next();
			Iterator<OWLAxiom> axiom = a.getAxioms().iterator();
			while (axiom.hasNext()){
				OWLAxiom ax = axiom.next();
				System.out.println(ax);
				//each axiom here should be add to the jsutification set or conflicting axioms
			}
			
		}
	}
}

