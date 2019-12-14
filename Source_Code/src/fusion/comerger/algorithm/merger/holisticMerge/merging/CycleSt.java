package fusion.comerger.algorithm.merger.holisticMerge.merging;

import org.semanticweb.owlapi.model.OWLClass;

public class CycleSt {
	OWLClass c ;
	boolean check = false;
	
	public OWLClass getObject() {
		return c;
	}

	public void SetObject(OWLClass cc) {
		c = cc;
	}
	
	public boolean getValue(){
		return check;
	}
	
	public void SetValue(boolean ch){
		check = ch;
	}
}
