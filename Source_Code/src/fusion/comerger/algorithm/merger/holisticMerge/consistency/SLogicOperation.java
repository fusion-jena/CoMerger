package fusion.comerger.algorithm.merger.holisticMerge.consistency;
/*
 * CoMerger: Holistic Ontology Merging
 * %%
 * Copyright (C) 2019 Heinz Nixdorf Chair for Distributed Information Systems, Friedrich Schiller University Jena
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
* Author: Samira Babalou<br>
* email: samira[dot]babalou[at]uni[dash][dot]jena[dot]de
* Heinz-Nixdorf Chair for Distributed Information Systems<br>
* Institute for Computer Science, Friedrich Schiller University Jena, Germany<br>
* Date: 17/12/2019
*/
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

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import fusion.comerger.algorithm.merger.holisticMerge.general.ClassProcess;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;
import fusion.comerger.algorithm.merger.model.HModel;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;

public class SLogicOperation {

	public static void main(String[] args) {
	}

	public double CalBelieve(double r, double s) {
		double res = 0.0;
		res = r / (r + s + 2);
		return res;
	}

	public Set<OWLClass> findAxiomElements(OWLAxiom currentAxiom, int ontId, HModel ontM) {
		Set<OWLClass> elm = new HashSet<OWLClass>();
		String axiomType = currentAxiom.getAxiomType().toString();

		switch (axiomType) {
		case "SubClassOf":
			elm.addAll(SubClassElm(currentAxiom));
			break;

		case "ObjectPropertyRange":
			elm.addAll(ObjectProperrtRangeElm(currentAxiom));
			break;

		case "EquivalentClasses":
			elm.addAll(EquivalentClassesElm(currentAxiom));
			break;

		case "ObjectPropertyDomain":
			elm.addAll(ObjectPropertyDomainElm(currentAxiom));

			break;

		case "InverseObjectProperties":
			// it does not have OWLClass axiom
			break;

		case "InverseFunctionalObjectProperty":
			// it does not have OWLClass axiom
			break;

		case "SubObjectPropertyOf":
			// it does not have OWLClass axiom
			break;

		case "FunctionalDataProperty":
			// it does not have OWLClass axiom
			break;

		case "FunctionalObjectProperty":
			// it does not have OWLClass axiom
			break;

		case "DisjointClasses":
			elm.addAll(DisjointClassesElm(currentAxiom));
			break;

		case "DataPropertyDomain":
			elm.addAll(DataPropertyDomainElm(currentAxiom));
			break;

		case "DataPropertyRange":
			// it does not have OWLClass axiom
			break;

		case "AnnotationAssertion":
			// it does not have OWLClass axiom
			// OWLAnnotationProperty property = ((OWLAnnotationAssertionAxiom)
			// currentAxiom).getProperty();
			// OWLAnnotationSubject subject = ((OWLAnnotationAssertionAxiom)
			// currentAxiom).getSubject();
			// OWLAnnotationValue value = ((OWLAnnotationAssertionAxiom)
			// currentAxiom).getValue();
			// if (subject instanceof IRI) {
			// OWLDataFactory df = ontM.getManager().getOWLDataFactory();
			// OWLClass cl = df.getOWLClass((IRI) subject);
			// if (cl != null) {
			// elm.add(cl);
			// }
			// }
			break;

		case "ClassAssertion":
			elm.addAll(ClassAssertionElm(currentAxiom));
			break;

		case "DifferentIndividuals":
			elm.addAll(DifferentIndividualsElm(currentAxiom));
			break;

		case "TransitiveObjectProperty":
			// it does not have OWLClass axiom
			break;

		case "SymmetricObjectProperty":
			// it does not have OWLClass axiom
			break;

		default:
		}

		elm = addEqualElements(elm, ontId, ontM);
		return elm;
	}

	private Set<OWLClass> ClassAssertionElm(OWLAxiom ax) {
		Set<OWLClass> elm = new HashSet<OWLClass>();
		OWLClassExpression c = ((OWLClassAssertionAxiom) ax).getClassExpression();
		if (c instanceof OWLClass) {
			elm.add(c.asOWLClass());

		}

		return elm;
	}

	private Set<OWLClass> DifferentIndividualsElm(OWLAxiom ax) {
		Set<OWLClass> elm = new HashSet<OWLClass>();
		// TODO
		return elm;
	}

	private Set<OWLClass> DataPropertyDomainElm(OWLAxiom ax) {
		Set<OWLClass> elm = new HashSet<OWLClass>();

		OWLClassExpression dom = ((OWLDataPropertyDomainAxiom) ax).getDomain();
		if (dom instanceof OWLClassImpl) {
			elm.add(dom.asOWLClass());

		} else if (dom instanceof OWLObjectUnionOf) {

			Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) dom).getOperands().iterator();
			while (rangExist.hasNext()) {
				OWLClassExpression ex = rangExist.next();
				if (ex instanceof OWLClassImpl) {
					elm.add(ex.asOWLClass());
				}
			}
		}
		return elm;
	}

	private Set<OWLClass> DisjointClassesElm(OWLAxiom ax) {
		Set<OWLClass> elm = new HashSet<OWLClass>();
		elm.addAll(ax.getClassesInSignature());
		return elm;
	}

	private Set<OWLClass> ObjectPropertyDomainElm(OWLAxiom ax) {
		Set<OWLClass> elm = new HashSet<OWLClass>();

		OWLClassExpression dom = ((OWLObjectPropertyDomainAxiom) ax).getDomain();
		if (dom instanceof OWLClassImpl) {
			elm.add(dom.asOWLClass());

		} else if (dom instanceof OWLObjectUnionOf) {
			Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) dom).getOperands().iterator();
			while (rangExist.hasNext()) {
				OWLClassExpression ex = rangExist.next();
				if (ex instanceof OWLClassImpl)
					elm.add(ex.asOWLClass());
			}
		}

		return elm;
	}

	private Set<OWLClass> EquivalentClassesElm(OWLAxiom ax) {
		Set<OWLClass> elm = new HashSet<OWLClass>();

		for (OWLClassExpression cls : ((OWLEquivalentClassesAxiom) ax).getClassExpressions()) {
			if (cls != null) {
				if (cls instanceof OWLClassImpl) {
					elm.add(cls.asOWLClass());
				} else if (cls instanceof OWLObjectUnionOf) {
					elm.addAll(cls.getClassesInSignature());
				} else if (cls instanceof OWLObjectSomeValuesFrom) {
					OWLClassExpression rang = ((OWLObjectSomeValuesFrom) cls).getFiller();
					elm.add(rang.asOWLClass());
					elm.addAll(((OWLObjectSomeValuesFrom) cls).getClassesInSignature());
					if (rang instanceof OWLObjectUnionOf)
						elm.addAll(rang.getClassesInSignature());

				} else if (cls instanceof OWLObjectIntersectionOf) {

					elm.addAll(((OWLObjectIntersectionOf) cls).getClassesInSignature());
					Iterator<OWLClassExpression> cll = ((OWLObjectIntersectionOf) cls).getOperands().iterator();

					while (cll.hasNext()) {
						OWLClassExpression c = cll.next();
						if (c instanceof OWLClassImpl) {
							elm.add(c.asOWLClass());
						} else if (c instanceof OWLObjectUnionOf) {
							Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) c).getOperands().iterator();
							while (rangExist.hasNext()) {
								OWLClassExpression ex = rangExist.next();
								if (ex instanceof OWLClassImpl)
									elm.add(ex.asOWLClass());
							}

						} else if (c instanceof OWLObjectSomeValuesFrom) {

							Iterator<OWLClass> ac = ((OWLObjectSomeValuesFrom) c).getClassesInSignature().iterator();

							while (ac.hasNext()) {
								OWLClass acl = ac.next();

								if (acl instanceof OWLClassImpl)
									elm.add(acl.asOWLClass());
							}

						}
					}

				}
			}
		}

		return elm;
	}

	private Set<OWLClass> ObjectProperrtRangeElm(OWLAxiom ax) {
		Set<OWLClass> elm = new HashSet<OWLClass>();
		OWLClassExpression rang = ((OWLObjectPropertyRangeAxiom) ax).getRange();
		if (rang instanceof OWLClassImpl) {
			elm.add(rang.asOWLClass());
		} else if (rang instanceof OWLObjectUnionOf) {

			Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) rang).getOperands().iterator();
			while (rangExist.hasNext()) {
				OWLClassExpression c = rangExist.next();
				if (c instanceof OWLClassImpl)
					elm.add(c.asOWLClass());
			}

		}
		return elm;
	}

	private Set<OWLClass> SubClassElm(OWLAxiom ax) {
		Set<OWLClass> elm = new HashSet<OWLClass>();
		OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) ax).getSuperClass();
		OWLClassExpression SubClass = ((OWLSubClassOfAxiom) ax).getSubClass();

		if (SuperClass instanceof OWLClassImpl && SubClass instanceof OWLClassImpl) {
			elm.add(SuperClass.asOWLClass());
			elm.add(SubClass.asOWLClass());

		} else if (SuperClass instanceof OWLObjectUnionOf && SubClass instanceof OWLClassImpl) {
			elm.add(SubClass.asOWLClass());
			Iterator<OWLClassExpression> r = ((OWLObjectUnionOf) SuperClass).getOperands().iterator();
			while (r.hasNext()) {
				OWLClassExpression c = r.next();
				if (c instanceof OWLClassImpl)
					elm.add(c.asOWLClass());
			}

		} else if (SuperClass instanceof OWLObjectSomeValuesFrom && SubClass instanceof OWLClassImpl) {
			elm.add(SubClass.asOWLClass());
			elm.addAll(((OWLObjectSomeValuesFrom) SuperClass).getClassesInSignature());

		} else if (SuperClass instanceof OWLObjectAllValuesFrom && SubClass instanceof OWLClassImpl) {
			elm.add(SubClass.asOWLClass());
			elm.addAll(((OWLObjectAllValuesFrom) SuperClass).getClassesInSignature());

		} else if (SubClass instanceof OWLClassImpl) {
			elm.add(SubClass.asOWLClass());

		}
		return elm;
	}

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

	public double CalUncertainty(double r, double s) {
		double res = 0.0;
		res = 2 / (r + s + 2);
		return res;
	}

	private boolean existenceCheck(OWLAxiom ax, Set<OWLAxiom> axList, OWLOntology oi, HModel ontM) {
		if (axList.contains(ax))
			return true;

		if (ontM.getEqAxioms().get(ax) != null)
			return true;
		// ArrayList<ArrayList<OWLAxiom>> eqAx = ontM.getEqAxioms();
		// for (int i = 0; i < eqAx.size(); i++) {
		// ArrayList<OWLAxiom> eqAxx = eqAx.get(i);
		// if (eqAxx.contains(ax)) {
		// for (int j = 0; j < eqAxx.size(); j++) {
		// if (oi.getAxioms().contains(eqAxx.get(j)))
		// return true;
		// }
		// }
		// }

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
