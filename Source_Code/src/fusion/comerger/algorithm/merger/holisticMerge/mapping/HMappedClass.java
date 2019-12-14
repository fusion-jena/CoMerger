package fusion.comerger.algorithm.merger.holisticMerge.mapping;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;

public class HMappedClass {
	OWLClass refClass;
	Set<OWLClass> mappedClass = new HashSet<OWLClass>();
	int LenClass = 0;
	double connection = 0.0;
	Double goodness = 0.0;
	Double centrality = 0.0;
	boolean selected = false;

	public OWLClass getRefClass() {
		return refClass;
	}

	public void setRefClass(OWLClass cl) {
		refClass = cl;
	}

	public Set<OWLClass> getMappedCalss() {
		return mappedClass;
	}

	public void setMappedClass(Set<OWLClass> cList) {
		mappedClass = cList;
	}

	public int getLenClass() {
		return LenClass;
	}

	public void setLenClass(int L) {
		LenClass = L;
	}

	public double getConnection() {
		return connection;
	}

	public void setConnection(double c) {
		connection = c;
	}

	public void setGoodness(Double g) {
		goodness = g;

	}

	public double getGoodness() {
		return goodness;

	}

	public void setCentrality(Double cc) {
		centrality = cc;
	}

	public Double getCentrality() {
		return centrality;
	}

	public void setSelectedStatus(boolean b) {
		selected = b;		
	}
	
	public boolean getSelectedStatus(){
		return selected;
	}
}
