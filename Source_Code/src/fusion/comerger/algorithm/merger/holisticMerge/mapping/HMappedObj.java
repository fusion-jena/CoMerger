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

public class HMappedObj {
	OWLObjectProperty refObj;
	Set<OWLObjectProperty> mappedObj = new HashSet<OWLObjectProperty>();
	int LenObj = 0;

	public OWLObjectProperty getRefObj() {
		return refObj;
	}

	public void setRefObj(OWLObjectProperty cl) {
		refObj = cl;
	}

	public Set<OWLObjectProperty> getMappedObj() {
		return mappedObj;
	}

	public void setMappedObj(Set<OWLObjectProperty> cList) {
		mappedObj = cList;
	}

	public int getLenObj() {
		return LenObj;
	}

	public void setLenObj(int L) {
		LenObj = L;
	}

}