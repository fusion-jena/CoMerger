package fusion.comerger.algorithm.merger.holisticMerge.general;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;

import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;

public class ClassProcess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static Set<OWLClass> getAllSupClasses(OWLClass cl, OWLOntology Om) {
		Set<OWLClass> res = new HashSet<OWLClass>();
		Set<OWLClassExpression> list = cl.getSuperClasses(Om);
		Iterator<OWLClassExpression> iterList = list.iterator();
		Set<OWLClassExpression> newList = new HashSet<OWLClassExpression>();
		Set<OWLClassExpression> L = new HashSet<OWLClassExpression>();
		L = list;
		int deep = 0;
		while (L.size() > 0) {
			while (iterList.hasNext()) {
				OWLClassExpression cc = iterList.next();
				if (cc instanceof OWLClassImpl) {
					OWLClass ccc = cc.asOWLClass();
					res.add(ccc);
					newList.addAll(ccc.getSuperClasses(Om));
				}
			}
			deep++;
			L = newList;
			iterList = newList.iterator();
			newList = new HashSet<OWLClassExpression>();
		}

		// FYI: deep value now shows how many level the class has superclass
		// System.out.println(deep);
		if (res.size() > 1) {
			int wadi = 0;
		}
		return res;

	}

	public static Set<OWLClass> getAllSubClasses(OWLClass cl, OWLOntology Om) {
		Set<OWLClass> res = new HashSet<OWLClass>();
		Set<OWLClassExpression> list = cl.getSubClasses(Om);
		Iterator<OWLClassExpression> iterList = list.iterator();
		Set<OWLClassExpression> newList = new HashSet<OWLClassExpression>();
		Set<OWLClassExpression> L = new HashSet<OWLClassExpression>();
		L = list;
		int deep = 0;
		while (L.size() > 0) {
			while (iterList.hasNext()) {
				OWLClassExpression cc = iterList.next();
				if (cc instanceof OWLClassImpl) {
					OWLClass ccc = cc.asOWLClass();
					res.add(ccc);
					newList.addAll(ccc.getSubClasses(Om));
				}
			}
			deep++;
			L = newList;
			iterList = newList.iterator();
			newList = new HashSet<OWLClassExpression>();
		}

		// FYI: deep value now shows how many level the class has sublcass
		// System.out.println(deep);
		return res;
	}

}
