package fusion.comerger.algorithm.merger.holisticMerge.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.apache.jena.ontology.OntModel;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import fusion.comerger.algorithm.merger.holisticMerge.clustering.ClusterModel;
import fusion.comerger.algorithm.partitioner.SeeCOnt.Cluster;
import fusion.comerger.model.NodeList;
import fusion.comerger.model.RBGModel;
import fusion.comerger.model.ext.sentence.RDFSentence;
import fusion.comerger.model.ext.sentence.RDFSentenceGraph;

public class HMappedDpro {
	OWLDataProperty refDpro;
	Set<OWLDataProperty> mappedDpro = new HashSet<OWLDataProperty>();
	int LenDpro = 0;

	public OWLDataProperty getRefDpro() {
		return refDpro;
	}

	public void setRefDpro(OWLDataProperty cl) {
		refDpro = cl;
	}

	public Set<OWLDataProperty> getMappedDpro() {
		return mappedDpro;
	}

	public void setMappedDpro(Set<OWLDataProperty> cList) {
		mappedDpro = cList;
	}

	public int getLenDpro() {
		return LenDpro;
	}

	public void setLenDpro(int L) {
		LenDpro = L;
	}

}