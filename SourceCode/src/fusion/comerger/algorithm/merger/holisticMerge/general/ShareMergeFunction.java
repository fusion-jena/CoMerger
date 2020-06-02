package fusion.comerger.algorithm.merger.holisticMerge.general;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import fusion.comerger.algorithm.merger.model.HModel;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyImpl;

public class ShareMergeFunction {
	// *********************************************************************************************************
	public static Set<OWLClass> getMappedClassesforClass(HModel ontM, OWLClassExpression domain) {
		Set<OWLClass> res = new HashSet<OWLClass>();
		for (int i = 0; i < ontM.getEqClasses().size(); i++) {
			if (ontM.getEqClasses().get(i).getMappedCalss().contains(domain)) {
				res.addAll(ontM.getEqClasses().get(i).getMappedCalss());
			}

			if (ontM.getEqClasses().get(i).getRefClass().equals(domain)) {
				res.addAll(ontM.getEqClasses().get(i).getMappedCalss());
			}
		}
		return res;
	}

	// *********************************************************************************************************
	public static Set<OWLObjectProperty> getMappedObjectforObject(HModel ontM, OWLObjectProperty cm) {
		Set<OWLObjectProperty> res = new HashSet<OWLObjectProperty>();
		for (int i = 0; i < ontM.getEqObjProperties().size(); i++) {
			if (ontM.getEqObjProperties().get(i).getMappedObj().contains(cm)) {
				res.addAll(ontM.getEqObjProperties().get(i).getMappedObj());
			}

			if (ontM.getEqObjProperties().get(i).getRefObj().equals(cm)) {
				res.addAll(ontM.getEqObjProperties().get(i).getMappedObj());
			}
		}
		return res;
	}

	// *********************************************************************************************************
	public static Set<OWLDataProperty> getMappedDproforDpro(HModel ontM, OWLDataProperty cm) {
		Set<OWLDataProperty> res = new HashSet<OWLDataProperty>();
		for (int i = 0; i < ontM.getEqDataProperties().size(); i++) {
			if (ontM.getEqDataProperties().get(i).getMappedDpro().contains(cm)) {
				res.addAll(ontM.getEqDataProperties().get(i).getMappedDpro());
			}

			if (ontM.getEqDataProperties().get(i).getRefDpro().equals(cm)) {
				res.addAll(ontM.getEqDataProperties().get(i).getMappedDpro());
			}
		}
		return res;
	}

	// ***********************************************************************************************************
	public static OWLClassExpression getEqualOiClass(OWLClassExpression c, HModel ontM) {
		OWLClassExpression res = null;

		if (c instanceof OWLClassImpl) {
			OWLClass eqC = ontM.getKeyValueEqClass().get(c);
			if (eqC != null) {
				res = eqC;
			} else {
				res = c;
			}

		} else if (c instanceof OWLObjectUnionOf) {
			Iterator<OWLClassExpression> operand = ((OWLObjectUnionOf) c).getOperands().iterator();
			Set<OWLClassExpression> temp = new HashSet<OWLClassExpression>();

			while (operand.hasNext()) {
				OWLClassExpression cc = operand.next();
				if (cc instanceof OWLClassImpl) {
					OWLClass eqC = ontM.getKeyValueEqClass().get(cc);
					if (eqC != null) {
						temp.add(eqC);
					} else {
						temp.add(cc);
					}

				} else if (cc instanceof OWLObjectPropertyImpl) {
					OWLObjectProperty eqC = ontM.getKeyValueEqObjProperty()
							.get(((OWLObjectProperty) cc).asOWLObjectProperty());
					if (eqC != null) {
						temp.add((OWLClassExpression) eqC);
					} else {
						temp.add(cc);
					}
				} else if (cc instanceof OWLDataProperty) {
					OWLDataProperty eqC = ontM.getKeyValueEqDataPro().get(((OWLDataProperty) cc).asOWLDataProperty());
					if (eqC != null) {
						temp.add((OWLClassExpression) eqC);
					} else {
						temp.add(cc);
					}
				} else {
					temp.add(c);
				}
			}
			OWLObjectUnionOf uniCl = ontM.getOwlModel().getOWLOntologyManager().getOWLDataFactory()
					.getOWLObjectUnionOf(temp);
			res = (OWLClassExpression) uniCl;

		} else if (c instanceof OWLDataExactCardinality) {
			res = c;
		} else if (c instanceof OWLObjectExactCardinality) {
			res = c;
		} else if (c instanceof OWLObjectMaxCardinality) {
			res = c;
		} else if (c instanceof OWLDataMaxCardinality) {
			res = c;
		} else if (c instanceof OWLDataMinCardinality) {
			res = c;
		} else if (c instanceof OWLObjectSomeValuesFrom) {
			OWLClassExpression cl = ((OWLObjectSomeValuesFrom) c).getFiller();// .getClassesInSignature().iterator();
			OWLObjectPropertyExpression pr = ((OWLObjectSomeValuesFrom) c).getProperty();

			if (cl instanceof OWLClassExpression) {
				OWLClass refCl = ontM.getKeyValueEqClass().get(cl);
				if (refCl != null)
					cl = refCl;
			} else if (cl instanceof OWLObjectProperty) {
				OWLObjectProperty refObj = ontM.getKeyValueEqObjProperty().get(cl);
				if (refObj != null)
					cl = (OWLClassExpression) refObj;
			} else if (cl instanceof OWLDataProperty) {
				OWLDataProperty refDPro = ontM.getKeyValueEqDataPro().get(cl);
				if (refDPro != null)
					cl = (OWLClassExpression) refDPro;
			}

			OWLObjectProperty refP = ontM.getKeyValueEqObjProperty().get(pr);
			if (refP != null)
				pr = refP;

			OWLObjectSomeValuesFrom SV = ontM.getOwlModel().getOWLOntologyManager().getOWLDataFactory()
					.getOWLObjectSomeValuesFrom(pr, cl);
			res = (OWLClassExpression) SV;

		} else if (c instanceof OWLObjectAllValuesFrom) {
			OWLClassExpression cl = ((OWLObjectAllValuesFrom) c).getFiller();// .getClassesInSignature().iterator();
			OWLObjectPropertyExpression pr = ((OWLObjectAllValuesFrom) c).getProperty();

			if (cl instanceof OWLClassExpression) {
				OWLClass refCl = ontM.getKeyValueEqClass().get(cl);
				if (refCl != null)
					cl = refCl;
			} else if (cl instanceof OWLObjectProperty) {
				OWLObjectProperty refObj = ontM.getKeyValueEqObjProperty().get(cl);
				if (refObj != null)
					cl = (OWLClassExpression) refObj;
			} else if (cl instanceof OWLDataProperty) {
				OWLDataProperty refDPro = ontM.getKeyValueEqDataPro().get(cl);
				if (refDPro != null)
					cl = (OWLClassExpression) refDPro;
			}

			OWLObjectProperty refP = ontM.getKeyValueEqObjProperty().get(pr);
			if (refP != null)
				pr = refP;

			OWLObjectAllValuesFrom SV = ontM.getOwlModel().getOWLOntologyManager().getOWLDataFactory()
					.getOWLObjectAllValuesFrom(pr, cl);
			res = (OWLClassExpression) SV;
		} else {
			res = c;
		}

		// test
		// System.out.println("*** " + res + "\n " + mySet);
		return res;

	}

	
	public static Set<OWLClassExpression> getEqualOiSetClass(Set<OWLClassExpression> mySet, HModel ontM) {
		Set<OWLClassExpression> res = new HashSet<OWLClassExpression>();

		Iterator<OWLClassExpression> iter = mySet.iterator();
		while (iter.hasNext()) {
			OWLClassExpression c = iter.next();
			if (c instanceof OWLClassImpl) {
				OWLClass eqC = ontM.getKeyValueEqClass().get(c);
				if (eqC != null) {
					res.add(eqC);
				} else {
					res.add(c);
				}

			} else if (c instanceof OWLObjectUnionOf) {
				Iterator<OWLClassExpression> operand = ((OWLObjectUnionOf) c).getOperands().iterator();
				Set<OWLClassExpression> temp = new HashSet<OWLClassExpression>();

				while (operand.hasNext()) {
					OWLClassExpression cc = operand.next();
					if (cc instanceof OWLClassImpl) {
						OWLClass eqC = ontM.getKeyValueEqClass().get(cc);
						if (eqC != null) {
							temp.add(eqC);
						} else {
							temp.add(cc);
						}

					} else if (cc instanceof OWLObjectPropertyImpl) {
						OWLObjectProperty eqC = ontM.getKeyValueEqObjProperty()
								.get(((OWLObjectProperty) cc).asOWLObjectProperty());
						if (eqC != null) {
							temp.add((OWLClassExpression) eqC);
						} else {
							temp.add(cc);
						}
					} else if (cc instanceof OWLDataProperty) {
						OWLDataProperty eqC = ontM.getKeyValueEqDataPro()
								.get(((OWLDataProperty) cc).asOWLDataProperty());
						if (eqC != null) {
							temp.add((OWLClassExpression) eqC);
						} else {
							temp.add(cc);
						}
					} else {
						temp.add(c);
					}
				}
				OWLObjectUnionOf uniCl = ontM.getOwlModel().getOWLOntologyManager().getOWLDataFactory()
						.getOWLObjectUnionOf(temp);
				res.add((OWLClassExpression) uniCl);

			} else if (c instanceof OWLDataExactCardinality) {
				res.add(c);
			} else if (c instanceof OWLObjectExactCardinality) {
				res.add(c);
			} else if (c instanceof OWLObjectMaxCardinality) {
				res.add(c);
			} else if (c instanceof OWLDataMaxCardinality) {
				res.add(c);
			} else if (c instanceof OWLDataMinCardinality) {
				res.add(c);
			} else if (c instanceof OWLObjectSomeValuesFrom) {
				OWLClassExpression cl = ((OWLObjectSomeValuesFrom) c).getFiller();// .getClassesInSignature().iterator();
				OWLObjectPropertyExpression pr = ((OWLObjectSomeValuesFrom) c).getProperty();

				if (cl instanceof OWLClassExpression) {
					OWLClass refCl = ontM.getKeyValueEqClass().get(cl);
					if (refCl != null)
						cl = refCl;
				} else if (cl instanceof OWLObjectProperty) {
					OWLObjectProperty refObj = ontM.getKeyValueEqObjProperty().get(cl);
					if (refObj != null)
						cl = (OWLClassExpression) refObj;
				} else if (cl instanceof OWLDataProperty) {
					OWLDataProperty refDPro = ontM.getKeyValueEqDataPro().get(cl);
					if (refDPro != null)
						cl = (OWLClassExpression) refDPro;
				}

				OWLObjectProperty refP = ontM.getKeyValueEqObjProperty().get(pr);
				if (refP != null)
					pr = refP;

				OWLObjectSomeValuesFrom SV = ontM.getOwlModel().getOWLOntologyManager().getOWLDataFactory()
						.getOWLObjectSomeValuesFrom(pr, cl);
				res.add((OWLClassExpression) SV);

			} else if (c instanceof OWLObjectAllValuesFrom) {
				OWLClassExpression cl = ((OWLObjectAllValuesFrom) c).getFiller();// .getClassesInSignature().iterator();
				OWLObjectPropertyExpression pr = ((OWLObjectAllValuesFrom) c).getProperty();

				if (cl instanceof OWLClassExpression) {
					OWLClass refCl = ontM.getKeyValueEqClass().get(cl);
					if (refCl != null)
						cl = refCl;
				} else if (cl instanceof OWLObjectProperty) {
					OWLObjectProperty refObj = ontM.getKeyValueEqObjProperty().get(cl);
					if (refObj != null)
						cl = (OWLClassExpression) refObj;
				} else if (cl instanceof OWLDataProperty) {
					OWLDataProperty refDPro = ontM.getKeyValueEqDataPro().get(cl);
					if (refDPro != null)
						cl = (OWLClassExpression) refDPro;
				}

				OWLObjectProperty refP = ontM.getKeyValueEqObjProperty().get(pr);
				if (refP != null)
					pr = refP;

				OWLObjectAllValuesFrom SV = ontM.getOwlModel().getOWLOntologyManager().getOWLDataFactory()
						.getOWLObjectAllValuesFrom(pr, cl);
				res.add((OWLClassExpression) SV);
			} else {
				res.add(c);
			}
		}

		return res;
	}

	// ******************************************************************************************
	public static HashMap<OWLClassExpression, HashSet<OWLClassExpression>> createParentList(OWLOntology Ont) {

		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> res = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();

		HashSet<OWLClassExpression> temp = new HashSet<OWLClassExpression>();
		Iterator<OWLSubClassOfAxiom> is_a = Ont.getAxioms(AxiomType.SUBCLASS_OF).iterator();

		while (is_a.hasNext()) {

			OWLSubClassOfAxiom axiom = is_a.next();
			OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) axiom).getSuperClass();// parent
			OWLClassExpression SubClass = ((OWLSubClassOfAxiom) axiom).getSubClass();// child

			// add parent to the child list
			temp = new HashSet<OWLClassExpression>();
			HashSet<OWLClassExpression> cc = res.get(SubClass);
			if (cc == null || cc.size() < 1) {
				// it means this class was not already in the list of
				// adjacent
				temp.add(SuperClass);
				res.put(SubClass, temp);
			} else {
				cc.add(SuperClass);
				res.put(SubClass, cc);
			}
		}

		return res;
	}

	// ******************************************************************************************
	public static HashMap<OWLClassExpression, ArrayList<OWLClassExpression>> generateParentChildList(OWLOntology Om) {

		HashMap<OWLClassExpression, ArrayList<OWLClassExpression>> res = new HashMap<OWLClassExpression, ArrayList<OWLClassExpression>>();
		// findAdjacentClass2(ontM);? or

		ArrayList<OWLClassExpression> temp = new ArrayList<OWLClassExpression>();
		Iterator<OWLSubClassOfAxiom> is_a = Om.getAxioms(AxiomType.SUBCLASS_OF).iterator();
		while (is_a.hasNext()) {

			OWLSubClassOfAxiom axiom = is_a.next();

			OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) axiom).getSuperClass();// parent
			OWLClassExpression SubClass = ((OWLSubClassOfAxiom) axiom).getSubClass();// child

			// add parent to the child list
			temp = new ArrayList<OWLClassExpression>();
			ArrayList<OWLClassExpression> cc = res.get(SubClass);
			if (cc == null || cc.size() < 1) {
				// it means this class was not already in the list of
				// adjacent
				temp.add(SuperClass);
				res.put(SubClass, temp);
			} else {
				cc.add(SuperClass);
				res.put(SubClass, cc);
			}
			// add child to parent
			temp = new ArrayList<OWLClassExpression>();
			ArrayList<OWLClassExpression> ccc = res.get(SuperClass);
			if (ccc == null || ccc.size() < 1) {
				// it means this class was not already in the list of
				// adjacent
				temp.add(SubClass);
				res.put(SuperClass, temp);
			} else {
				ccc.add(SubClass);
				res.put(SuperClass, ccc);
			}
		}

		return res;
	}

	/*
	 * ************************************************************
	 */
	public static HashMap<OWLClassExpression, HashSet<OWLClassExpression>> createChildList(OWLOntology Ont) {

		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> res = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();
		// findAdjacentClass2(ontM);? or

		HashSet<OWLClassExpression> temp = new HashSet<OWLClassExpression>();
		Iterator<OWLSubClassOfAxiom> is_a = Ont.getAxioms(AxiomType.SUBCLASS_OF).iterator();

		while (is_a.hasNext()) {

			OWLSubClassOfAxiom axiom = is_a.next();
			OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) axiom).getSuperClass();// parent
			OWLClassExpression SubClass = ((OWLSubClassOfAxiom) axiom).getSubClass();// child

			// add child to parent
			temp = new HashSet<OWLClassExpression>();
			HashSet<OWLClassExpression> ccc = res.get(SuperClass);
			if (ccc == null || ccc.size() < 1) {
				// it means this class was not already in the list of
				// adjacent
				temp.add(SubClass);
				res.put(SuperClass, temp);
			} else {
				ccc.add(SubClass);
				res.put(SuperClass, ccc);
			}
		}

		return res;
	}

	/* ********************************************************************* */
	public static boolean hasDuplicate(ArrayList<OWLClassExpression> cParent) {
		for (int i = 0; i < cParent.size(); i++) {
			for (int j = i + 1; j < cParent.size(); j++) {
				if (cParent.get(i).equals(cParent.get(j))) {
					return true;
				}
			}
		}

		return false;
	}

	public static Set<OWLClassExpression> findElmOfAxiom(OWLSubClassOfAxiom axiom) {
		Set<OWLClassExpression> res = new HashSet<OWLClassExpression>();
		OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) axiom).getSuperClass();
		OWLClassExpression SubClass = ((OWLSubClassOfAxiom) axiom).getSubClass();
		if (SuperClass != null)
			res.add(SuperClass);
		if (SubClass != null)
			res.add(SubClass);

		return res;
	}
}
