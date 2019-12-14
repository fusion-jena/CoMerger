package fusion.comerger.algorithm.merger.holisticMerge.consistency;
/**
 * CoMerger: Holistic Multiple Ontology Merger.
 * Consistency checker sub package based on the Subjective Logic theory
 * @author Samira Babalou (samira[dot]babalou[at]uni_jean[dot]de)
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

import fusion.comerger.algorithm.merger.holisticMerge.general.ClassProcess;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;
import fusion.comerger.algorithm.merger.model.HModel;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectSomeValuesFromImpl;

public class SLogicOperation {

	public static void main(String[] args) {
	}

	public double CalBelieve(double r, double s) {
		double res = 0.0;
		res = r / (r + s + 2);
		return res;
	}

	private Set<OWLAnnotation> findAnno(Set<OWLClass> axElem, int ontId, HModel ontM) {
		Set<OWLAnnotation> Anno = new HashSet<OWLAnnotation>();
		OWLOntology oi = ontM.getInputOwlOntModel().get(ontId);
		Iterator<OWLClass> iterE = axElem.iterator();
		while (iterE.hasNext()) {
			OWLClass cl = iterE.next();
			Set<OWLAnnotation> an = cl.getAnnotations(oi);
			Anno.addAll(an);
		}
		return Anno;

	}

	private Set<OWLIndividual> findInstances(Set<OWLClass> axElem, int ontId, HModel ontM) {
		Set<OWLIndividual> ins = new HashSet<OWLIndividual>();
		Iterator<OWLClass> iterE = axElem.iterator();
		while (iterE.hasNext()) {
			OWLClass cl = iterE.next();
			ins.addAll(cl.getIndividuals(ontM.getInputOwlOntModel().get(ontId)));
		}
		return ins;
	}

	public Set<OWLClass> findAxiomElements(OWLAxiom currentAxiom, int ontId, HModel ontM) {
		Set<OWLClass> elm = new HashSet<OWLClass>();
		String axiomType = currentAxiom.getAxiomType().toString();

		switch (axiomType) {
		case "SubClassOf":
			OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) currentAxiom).getSuperClass();
			OWLClassExpression SubClass = ((OWLSubClassOfAxiom) currentAxiom).getSubClass();
			if (SuperClass instanceof OWLClassImpl) {
				elm.add(SuperClass.asOWLClass());
			} else {

			}
			if (SubClass instanceof OWLClassImpl) {
				elm.add(SubClass.asOWLClass());
			} else {

			}

			break;
		case "ObjectPropertyRange":
			OWLObjectPropertyExpression pro = ((OWLObjectPropertyRangeAxiom) currentAxiom).getProperty();
			OWLClassExpression rang = ((OWLObjectPropertyRangeAxiom) currentAxiom).getRange();

			if (rang instanceof OWLClassImpl) {
				elm.add(rang.asOWLClass());
			} else {
			}

			break;

		case "EquivalentClasses":
			Iterator<OWLClassExpression> a = ((OWLEquivalentClassesAxiom) currentAxiom).getClassExpressions()
					.iterator();
			while (a.hasNext()) {
				OWLClassExpression c1 = a.next();

				if (c1 instanceof OWLClassImpl) {
					elm.add(c1.asOWLClass());
				} else if (c1 instanceof OWLObjectSomeValuesFromImpl) {
					Iterator<OWLClass> ac = ((OWLObjectSomeValuesFrom) c1).getClassesInSignature().iterator();
					while (ac.hasNext()) {
						elm.add(ac.next());
					}
				} else if (c1 instanceof OWLObjectIntersectionOf) {
					Iterator<OWLClass> cl = ((OWLObjectIntersectionOf) c1).getClassesInSignature().iterator();
					while (cl.hasNext()) {
						elm.add(cl.next());
					}
				} else if (c1 instanceof OWLObjectUnionOf) {
					Iterator<OWLClassExpression> cf = ((OWLObjectUnionOf) c1).getOperands().iterator();
					while (cf.hasNext()) {
						elm.add(cf.next().asOWLClass());
					}
				}
			}

			break;

		case "ObjectPropertyDomain":
			OWLObjectPropertyExpression pro2 = ((OWLObjectPropertyDomainAxiom) currentAxiom).getProperty();
			OWLClassExpression dom = ((OWLObjectPropertyDomainAxiom) currentAxiom).getDomain();
			if (dom instanceof OWLClassImpl) {
				elm.add(dom.asOWLClass());
			} else {

			}

			break;

		case "InverseObjectProperties":
			Iterator<OWLObjectProperty> iter = currentAxiom.getObjectPropertiesInSignature().iterator();
			// TODO: find a new plan
			break;

		case "InverseFunctionalObjectProperty":
			Iterator<OWLObjectProperty> iter3 = currentAxiom.getObjectPropertiesInSignature().iterator();
			// TODO: find a new plan
			break;

		case "SubObjectPropertyOf":
			OWLObjectPropertyExpression subProperty = ((OWLSubObjectPropertyOfAxiom) currentAxiom).getSubProperty();
			OWLObjectPropertyExpression superProperty = ((OWLSubObjectPropertyOfAxiom) currentAxiom).getSuperProperty();
			// TODO: find a new plan
			break;

		case "FunctionalDataProperty":
			OWLDataPropertyExpression pro4 = ((OWLFunctionalDataPropertyAxiom) currentAxiom).getProperty();
			// elm.add(pro4.asOWLObjectProperty());//TODO:correct it
			break;

		case "FunctionalObjectProperty":
			OWLObjectPropertyExpression pro3 = ((OWLFunctionalObjectPropertyAxiom) currentAxiom).getProperty();
			// elm.add(pro3.asOWLObjectProperty());//TODO:correct it
			break;

		case "DisjointClasses":
			Iterator<OWLClass> clist = currentAxiom.getClassesInSignature().iterator();
			OWLClass cls1 = clist.next();
			OWLClass cls2 = clist.next();
			if (cls1 instanceof OWLClassImpl) {
				elm.add(cls1.asOWLClass());
			} else {

			}
			if (cls2 instanceof OWLClassImpl) {
				elm.add(cls2.asOWLClass());
			} else {

			}

			break;

		case "DataPropertyDomain":
			Iterator<OWLDataProperty> pro33 = currentAxiom.getDataPropertiesInSignature().iterator();
			OWLDataProperty prop = pro33.next();
			OWLClassExpression dom3 = ((OWLDataPropertyDomainAxiom) currentAxiom).getDomain();
			if (dom3 instanceof OWLClassImpl) {
				elm.add(dom3.asOWLClass());
			} else {

			}

			break;

		case "DataPropertyRange":
			Iterator<OWLDataProperty> iter33 = currentAxiom.getDataPropertiesInSignature().iterator();
			while (iter33.hasNext()) {
				OWLDataProperty obj = iter33.next();
				// elm.add(obj);//TODO: correct it
			}
			break;

		case "AnnotationAssertion":
			OWLAnnotationProperty property = ((OWLAnnotationAssertionAxiom) currentAxiom).getProperty();
			OWLAnnotationSubject subject = ((OWLAnnotationAssertionAxiom) currentAxiom).getSubject();
			OWLAnnotationValue value = ((OWLAnnotationAssertionAxiom) currentAxiom).getValue();
			if (subject instanceof IRI) {
				OWLDataFactory df = ontM.getManager().getOWLDataFactory();
				OWLClass cl = df.getOWLClass((IRI) subject);
				if (cl != null) {
					elm.add(cl);
				}
			}
			break;

		case "ClassAssertion":
			OWLClassAssertionAxiom axx = (OWLClassAssertionAxiom) currentAxiom;
			OWLClassExpression c = ((OWLClassAssertionAxiom) currentAxiom).getClassExpression();
			OWLIndividual ind = axx.getIndividual();
			OWLClass cc = c.asOWLClass();
			if (cc instanceof OWLClass) {
				elm.add(cc);// TODO: should add individula also to this
							// list?
			} else if (cc instanceof OWLObjectSomeValuesFrom) {
				System.out.println("unprocess axioms:" + currentAxiom);
			}
			break;

		case "DifferentIndividuals":
			break;

		case "TransitiveObjectProperty":
			OWLObjectPropertyExpression sub3Property = ((OWLTransitiveObjectPropertyAxiom) currentAxiom).getProperty();
			// elm.add(subProperty.asOWLObjectProperty()));//TODO:correct it
			break;

		case "SymmetricObjectProperty":
			OWLObjectPropertyExpression pr3operty = ((OWLSymmetricObjectPropertyAxiom) currentAxiom).getProperty();
			// elm.add(property);//TODO: correct it
			break;

		default:// DataPropertyDomain
			// TODO
		}

		elm = addEqualElements(elm, ontId, ontM);
		return elm;
	}

//	private Set<OWLAxiom> getAllEqaxioms(OWLAxiom myAxiom, HModel ontM) {
//		Set<OWLAxiom> res = new HashSet<OWLAxiom>();
//		ArrayList<ArrayList<OWLAxiom>> ax = ontM.getEqAxioms();
//		for (int i = 0; i < ax.size(); i++) {
//			ArrayList<OWLAxiom> axx = ax.get(i);
//			for (int j = 0; j < axx.size(); j++) {
//				if (axx.get(j).equals(myAxiom)) {
//					res.addAll(axx);
//					return res;
//				}
//			}
//		}
//		res.add(myAxiom);
//		return res;
//	}

	private Set<OWLClass> addEqualElements(Set<OWLClass> elm, int ontId, HModel ontM) {
		Set<OWLClass> res = new HashSet<OWLClass>();
		// res.addAll(elm);
		Iterator<OWLClass> iter = elm.iterator();
		while (iter.hasNext()) {
			OWLClass c = iter.next();
			if (ontM.getInputOwlOntModel().get(ontId).getClassesInSignature().contains(c))
				res.add(c);
		}
		ArrayList<HMappedClass> eqClass = ontM.getEqClasses();
		Iterator<OWLClass> iterElm = elm.iterator();
		while (iterElm.hasNext()) {
			OWLClass cl = iterElm.next();
			for (int j = 0; j < eqClass.size(); j++) {
				HMappedClass e = eqClass.get(j);
				Set<OWLClass> m = e.getMappedCalss();
				if ((m.contains(cl) || e.getRefClass().equals(cl))) {
					res.add(e.getRefClass());
					Iterator<OWLClass> iterM = m.iterator();
					while (iterM.hasNext()) {
						OWLClass mm = iterM.next();
						if (ontM.getInputOwlOntModel().get(ontId).getClassesInSignature().contains(mm)) {
							res.add(mm);
							break;
						}
					}
				}
			}
		}
		return res;
	}

	private static double findRefferedOnt(HModel ontM, OWLClassExpression cl) {
		if (cl instanceof OWLClassImpl) {
			String irCL = ((OWLClass) cl).getIRI().getNamespace();
			if (irCL.equals(ontM.getOntIRI().toString())) {
				for (HMappedClass eqList : ontM.getEqClasses()) {
					double num = eqList.getLenClass();
					return num;
				}
			}
		}
		return 1.0f;
	}

	public double CalUncertainty(double r, double s) {
		double res = 0.0;
		res = 2 / (r + s + 2);
		return res;
	}

	private boolean existenceCheck(OWLAxiom ax, Set<OWLAxiom> axList, OWLOntology oi, HModel ontM) {
		if (axList.contains(ax))
			return true;

		if (ontM.getEqAxioms().get(ax)!= null)
			return true;
//		ArrayList<ArrayList<OWLAxiom>> eqAx = ontM.getEqAxioms();
//		for (int i = 0; i < eqAx.size(); i++) {
//			ArrayList<OWLAxiom> eqAxx = eqAx.get(i);
//			if (eqAxx.contains(ax)) {
//				for (int j = 0; j < eqAxx.size(); j++) {
//					if (oi.getAxioms().contains(eqAxx.get(j)))
//						return true;
//				}
//			}
//		}

		return false;
	}

	public double CalaPriori(int ontId, OWLAxiom proposition, Set<OWLClass> axElem, HModel ontM) {
		double res = 0.0;
		OWLOntology oi = ontM.getInputOwlOntModel().get(ontId);
		Iterator<OWLClass> iterAxElem = axElem.iterator();
		while (iterAxElem.hasNext()) {
			OWLClass cl = iterAxElem.next();
			Set<OWLClass> subList = ClassProcess.getAllSubClasses(cl, oi);
			res = res + subList.size();

			Set<OWLClass> supList = ClassProcess.getAllSupClasses(cl, oi);
			res = res + supList.size();

		}
		res = res / oi.getClassesInSignature().size();
		return res;
	}

	public double countJust(OWLAxiom proposition, ArrayList<ErrornousAxioms> allErrAx, HModel ontM) {
		double counter = 0.0f;
		for (int i = 0; i < allErrAx.size(); i++) {
			Iterator<Set<OWLAxiom>> justIter = allErrAx.get(i).getErrorAxioms().iterator();
			while (justIter.hasNext()) {
				Iterator<OWLAxiom> juIter = justIter.next().iterator();
				while (juIter.hasNext()) {
					OWLAxiom ax = juIter.next();
					if (ax.equals(proposition))
						counter++;// 1.0f;
				}
			}
		}
		return counter;
	}

	public double CalR(int ontId, OWLAxiom proposition, Set<OWLClass> axElem, HModel ontM) {
		double res = 0.0f;
		double alpha = 1.0, beta = 0.5;
		OWLOntology oi = ontM.getInputOwlOntModel().get(ontId);

		Set<OWLAxiom> axiomUsage = new HashSet<OWLAxiom>();
		Iterator<OWLAxiom> as = oi.getAxioms().iterator();
		while (as.hasNext()) {
			OWLAxiom x = as.next();
			Iterator<OWLClass> iterAxElem = axElem.iterator();
			while (iterAxElem.hasNext()) {
				OWLClass cl = iterAxElem.next();
				if (x.containsEntityInSignature(cl)) {
					axiomUsage.add(x);
				}
			}
		}

		double ExistenceWeight = 1.0;
		boolean existAx = existenceCheck(proposition, axiomUsage, oi, ontM);
		if (!existAx) {
			ExistenceWeight = beta;
		} else {
			ExistenceWeight = alpha;
		}

		double totalAxiom = oi.getAxiomCount();
		double axElmSiz = 0.0;
		if (totalAxiom > 0)
			axElmSiz = axiomUsage.size() / totalAxiom;
		res = ExistenceWeight * axElmSiz;

		return res;

	}

	public double CalS(int ontId, OWLAxiom currentAxiom, Set<OWLAxiom> just, Set<OWLClass> axElem,
			ArrayList<ErrornousAxioms> allErrAx, double errorneousAxiomSize, HModel ontM) {
		double res = 0.0;
		double counter = countJust(currentAxiom, allErrAx, ontM);
		double totalAx = countAxOi(allErrAx, ontM.getInputOwlOntModel().get(ontId), ontM);
		if (totalAx > 0)
			res = counter / totalAx;
		return res;
	}

	private double countAxOi(ArrayList<ErrornousAxioms> allErrAx, OWLOntology oi, HModel ontM) {
		// ArrayList<ArrayList<OWLAxiom>> eqAx = ontM.getEqAxioms();
		HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
		double counter = 0.0;
		for (int k = 0; k < allErrAx.size(); k++) {
			Iterator<Set<OWLAxiom>> x = allErrAx.get(k).getErrorAxioms().iterator();
			while (x.hasNext()) {
				Iterator<OWLAxiom> xx = x.next().iterator();
				while (xx.hasNext()) {
					OWLAxiom axiom = xx.next();
					OWLAxiom allAx = eqAx.get(axiom);
					if (oi.getAxioms().contains(allAx))
						counter = counter + 1.0;
					// ArrayList<OWLAxiom> allAx = findEqAx(eqAx, axiom);
					// for (int j = 0; j < allAx.size(); j++) {
					// if (oi.getAxioms().contains(allAx.get(j)))
					// counter = counter + 1.0;
					// }
				}
			}
		}

		return counter;
	}

	private ArrayList<OWLAxiom> findEqAx(ArrayList<ArrayList<OWLAxiom>> eqAx, OWLAxiom axiom) {
		ArrayList<OWLAxiom> res = new ArrayList<OWLAxiom>();
		for (int i = 0; i < eqAx.size(); i++) {
			ArrayList<OWLAxiom> ax = eqAx.get(i);
			for (int j = 0; j < ax.size(); j++) {
				OWLAxiom a = ax.get(j);
				if (a.equals(axiom))
					res.addAll(ax);
			}
		}
		return res;
	}

	public double CalDisbelieve(double r, double s) {
		double res = 0.0;
		res = s / (r + s + 2);
		return res;
	}

	public double CalBelieve(int ontId, OWLAxiom proposition, Set<OWLAxiom> just, Set<OWLClass> axElem,
			ArrayList<ErrornousAxioms> allErrAx, double errorneousAxiomSize, HModel ontM) {
		double res = 0.0;
		double counter = countJust(proposition, allErrAx, ontM);
		double totalAx = countAxOi(allErrAx, ontM.getInputOwlOntModel().get(ontId), ontM);
		if (totalAx > 0)
			res = counter / totalAx;
		return res;
	}

	public double CalUncertainty(int ontId, OWLAxiom proposition, Set<OWLClass> axElem, HModel ontM) {
		double res = 0.0f;
		double alpha = Parameter.alpha, beta = Parameter.beta;
		OWLOntology oi = ontM.getInputOwlOntModel().get(ontId);

		Set<OWLAxiom> axiomUsage = new HashSet<OWLAxiom>();
		Iterator<OWLAxiom> as = oi.getAxioms().iterator();
		while (as.hasNext()) {
			OWLAxiom x = as.next();
			Iterator<OWLClass> iterAxElem = axElem.iterator();
			while (iterAxElem.hasNext()) {
				OWLClass cl = iterAxElem.next();
				if (x.containsEntityInSignature(cl)) {
					axiomUsage.add(x);
				}
			}
		}

		double ExistenceWeight = 1.0;
		boolean existAx = existenceCheck(proposition, axiomUsage, oi, ontM);
		if (!existAx) {
			ExistenceWeight = beta;
		} else {
			ExistenceWeight = alpha;
		}

		double totalAxiom = oi.getAxiomCount();
		double axElmSiz = 0.0;
		if (totalAxiom > 0)
			axElmSiz = axiomUsage.size() / totalAxiom;
		res = ExistenceWeight * axElmSiz;

		return res;

	}
}
