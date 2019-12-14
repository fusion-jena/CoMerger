//TO DO: add cluster representor
package fusion.comerger.algorithm.merger.holisticMerge.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;

public class ClusterModel {
	int ID;
	int size;
	ArrayList<OWLClassExpression> classes;
	ArrayList<OWLAxiom> axioms;
	HMappedClass core;
	HashMap<HashSet<String>, Integer> elementOriginalCounter;
	OWLOntologyManager ClManager;
	OWLOntology ClOntology;

	public ArrayList<OWLClassExpression> getClasses() {
		return classes;
	}

	public void SetClasses(ArrayList<OWLClassExpression> elm) {
		classes = elm;
	}

	public HMappedClass getCore() {
		return core;
	}

	public void SetCore(HMappedClass c) {
		core = c;
	}

	public int getId() {
		return ID;
	}

	public void SetId(int id) {
		ID = id;
	}

	public void SetAxioms(ArrayList<OWLAxiom> axList) {
		axioms = axList;
	}

	public ArrayList<OWLAxiom> getAxioms() {
		return axioms;
	}

	public void setSize(int siz) {
		size = siz;
	}

	public int getSize() {
		return size;
	}

	public HashMap<HashSet<String>, Integer> getElementOrigin() {
		return elementOriginalCounter;
	}

	public void SetElementOrigin(HashMap<HashSet<String>, Integer> origin) {
		elementOriginalCounter = origin;
	}

	public void setManager(OWLOntologyManager mg) {
		ClManager = mg;
	}

	public OWLOntologyManager getManager() {
		return ClManager;
	}

	public void setOntology(OWLOntology co) {
		ClOntology = co;
	}

	public OWLOntology getOntology() {
		return ClOntology;
	}

}
