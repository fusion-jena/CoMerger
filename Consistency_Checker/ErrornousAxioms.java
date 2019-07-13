package fusion.comerger.algorithm.merger.holisticMerge.consistency;
/**
 * CoMerger: Holistic Multiple Ontology Merger.
 * Consistency checker sub package based on the Subjective Logic theory
 * @author Samira Babalou (samira[dot]babalou[at]uni_jean[dot]de)
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;

public class ErrornousAxioms {
	int id;
	OWLClass cl;
	Set<Set<OWLAxiom>> errAx = new HashSet<Set<OWLAxiom>>();
	ArrayList<ArrayList<Opinion>> rankedAxioms;

	public int getID() {
		return id;
	}

	public void setID(int i) {
		id = i;
	}

	public OWLClass getErrorClass() {
		return cl;
	}

	public void setErrorClass(OWLClass c) {
		cl = c;
	}

	public Set<Set<OWLAxiom>> getErrorAxioms() {
		return errAx;
	}

	public void setErrorAxioms(Set<Set<OWLAxiom>> ex) {
		errAx = ex;
	}

	public ArrayList<ArrayList<Opinion>> getRankedAxioms() {
		return rankedAxioms;
	}

	public void setRankedAxioms(ArrayList<ArrayList<Opinion>> rEx) {
		rankedAxioms = rEx;
	}
}
