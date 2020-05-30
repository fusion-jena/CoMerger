package fusion.comerger.algorithm.merger.holisticMerge.evaluator;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import fusion.comerger.algorithm.merger.model.HModel;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;

public class Entailmenter {

	public static int[] isEntailed(HModel ontM, OWLOntology OM, ArrayList<OWLOntology> AllOi) {
		int[] result = new int[4];
		OWLReasonerFactory reasonerFactory = new PelletReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(OM);

		int trueAnsSubsumption = 0, falseAnsSubsumption = 0, trueAnsEquivalence = 0, falseAnsEquivalence = 0;
		for (OWLOntology Oi : AllOi) {
			/* Subsumption entailment */
			Set<OWLSubClassOfAxiom> subsumptions = Oi.getAxioms(AxiomType.SUBCLASS_OF);
			for (OWLAxiom pos : subsumptions) {
				OWLAxiom eqAx = ontM.getEqAxioms().get(pos);
				if (eqAx != null) {
					pos = eqAx;
				} else {
					OWLAxiom eqqAx = buildIsaEqualAxiom(ontM, pos);
					if (eqqAx != null)
						pos = eqqAx;
				}

				try {
					Assert.assertTrue(reasoner.isEntailed(pos));
					trueAnsSubsumption++;
					/*
					 * assertTrue: Asserts that a condition is true. If it isn't
					 * it throws an AssertionError without a message.
					 */

				} catch (AssertionError e) {
					falseAnsSubsumption++;
				} catch (InconsistentOntologyException e) {
					result[0] = -1;
					return result;
				}
			}

			/* Equivalnce Entailment */
			Set<OWLEquivalentClassesAxiom> equivalenceClass = Oi.getAxioms(AxiomType.EQUIVALENT_CLASSES);
			for (OWLAxiom pos : equivalenceClass) {
				OWLAxiom eqAx = ontM.getEqAxioms().get(pos);
				if (eqAx != null) {
					pos = eqAx;
				} else {
					OWLAxiom eqqAx = buildEquivalentEqualAxiom(ontM, pos);
					if (eqqAx != null)
						pos = eqqAx;
				}

				try {
					Assert.assertTrue(reasoner.isEntailed(pos));
					trueAnsEquivalence++;
					/*
					 * assertTrue: Asserts that a condition is true. If it isn't
					 * it throws an AssertionError without a message.
					 */

				} catch (AssertionError e) {
					falseAnsEquivalence++;
				} catch (InconsistentOntologyException e) {
					result[0] = -1;
					return result;
				}
			}
			Set<OWLEquivalentObjectPropertiesAxiom> equivalenceObj = Oi
					.getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES);
			for (OWLAxiom pos : equivalenceObj) {
				OWLAxiom eqAx = ontM.getEqAxioms().get(pos);
				if (eqAx != null)
					pos = eqAx;

				try {
					Assert.assertTrue(reasoner.isEntailed(pos));
					trueAnsEquivalence++;
					/*
					 * assertTrue: Asserts that a condition is true. If it isn't
					 * it throws an AssertionError without a message.
					 */

				} catch (AssertionError e) {
					falseAnsEquivalence++;
				} catch (InconsistentOntologyException e) {
					result[0] = -1;
					return result;
				}
			}
			Set<OWLEquivalentDataPropertiesAxiom> equivalenceDpro = Oi.getAxioms(AxiomType.EQUIVALENT_DATA_PROPERTIES);
			for (OWLAxiom pos : equivalenceDpro) {
				OWLAxiom eqAx = ontM.getEqAxioms().get(pos);
				if (eqAx != null)
					pos = eqAx;

				try {
					Assert.assertTrue(reasoner.isEntailed(pos));
					trueAnsEquivalence++;
					/*
					 * assertTrue: Asserts that a condition is true. If it isn't
					 * it throws an AssertionError without a message.
					 */

				} catch (AssertionError e) {
					falseAnsEquivalence++;
				}
			}
		}

		// Assert.assertEquals(numberOfUnifiers, actualNumberOfUnifiers);
		reasoner.dispose();

		result[0] = trueAnsSubsumption;
		result[1] = falseAnsSubsumption;
		result[2] = trueAnsEquivalence;
		result[3] = falseAnsEquivalence;
		return result;
	}

	private static OWLAxiom buildEquivalentEqualAxiom(HModel ontM, OWLAxiom ax) {
		OWLAxiom eqAxiom = null;
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		boolean changes = false;
		OWLClassExpression allCinGetOperand = null;
		OWLClass eqClass1 = null;
		OWLObjectExactCardinality cardi = null;
		OWLObjectMinCardinality cardiMin = null;
		Set<OWLClass> eqClass2 = new HashSet<OWLClass>();
		for (OWLClassExpression cls : ((OWLEquivalentClassesAxiom) ax).getClassExpressions()) {
			if (cls != null) {
				if (cls instanceof OWLClassImpl) {
					OWLClass refClass1 = ontM.getKeyValueEqClass().get(cls.asOWLClass());
					if (refClass1 != null) {
						eqClass1 = refClass1;
						changes = true;
					} else {
						eqClass1 = cls.asOWLClass();
					}
				} else if (cls instanceof OWLObjectUnionOf) {
					Iterator<OWLClass> clscls = cls.getClassesInSignature().iterator();
					while (clscls.hasNext()) {
						OWLClass cl = clscls.next();
						OWLClass refClass2 = ontM.getKeyValueEqClass().get(cl.asOWLClass());
						if (refClass2 != null) {
							eqClass2.add(refClass2);
							changes = true;
						} else {
							eqClass2.add(cl.asOWLClass());
						}
					}
					OWLObjectUnionOf uniClassObj = factory.getOWLObjectUnionOf(eqClass2);
					allCinGetOperand = uniClassObj;
				} else if (cls instanceof OWLObjectSomeValuesFrom) {
					OWLObjectSomeValuesFrom objectSome = null;
					OWLClass aClass = null;
					OWLObjectProperty aProperty = null;
					OWLClassExpression rang = ((OWLObjectSomeValuesFrom) cls).getFiller();
					Iterator<OWLClass> ac = ((OWLObjectSomeValuesFrom) cls).getClassesInSignature().iterator();
					Iterator<OWLObjectProperty> ap = ((OWLObjectSomeValuesFrom) cls).getObjectPropertiesInSignature()
							.iterator();

					while (ac.hasNext()) {
						OWLClass acl = ac.next();
						OWLClass refClass2 = ontM.getKeyValueEqClass().get(acl);
						if (refClass2 != null) {
							aClass = refClass2;
							changes = true;
						} else {
							aClass = acl;
						}
					}
					while (ap.hasNext()) {
						OWLObjectProperty apro = ap.next();
						OWLObjectProperty refClass2 = ontM.getKeyValueEqObjProperty().get(apro.asOWLObjectProperty());
						if (refClass2 != null) {
							aProperty = refClass2;
							changes = true;
						} else {
							aProperty = apro;
						}
					}
					if (rang instanceof OWLObjectUnionOf) {
						Iterator<OWLClass> clscls = rang.getClassesInSignature().iterator();
						while (clscls.hasNext()) {
							OWLClass cl = clscls.next();
							OWLClass refClass2 = ontM.getKeyValueEqClass().get(cl.asOWLClass());
							if (refClass2 != null) {
								eqClass2.add(refClass2);
								changes = true;
							} else {
								eqClass2.add(cl.asOWLClass());
							}
						}
						OWLObjectUnionOf uniClassObj = factory.getOWLObjectUnionOf(eqClass2);
						objectSome = factory.getOWLObjectSomeValuesFrom(aProperty, uniClassObj);
						// aProperty = uniClassObj;
						allCinGetOperand = objectSome;
					} else {

						objectSome = factory.getOWLObjectSomeValuesFrom(aProperty, aClass);
						allCinGetOperand = objectSome;
					}
				} else if (cls instanceof OWLObjectIntersectionOf) {
					Set<OWLClassExpression> tempAll = new HashSet<OWLClassExpression>();
					Iterator<OWLClassExpression> cll = ((OWLObjectIntersectionOf) cls).getOperands().iterator();

					while (cll.hasNext()) {
						OWLClassExpression c = cll.next();
						if (c instanceof OWLClassImpl) {
							// no need
						} else if (c instanceof OWLObjectUnionOf) {
							Set<OWLClassExpression> tempObj = new HashSet<OWLClassExpression>();
							Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) c).getOperands().iterator();
							while (rangExist.hasNext()) {
								OWLClassExpression ex = rangExist.next();
								if (ex instanceof OWLClassImpl) {
									OWLClass eq = ex.asOWLClass();
									OWLClass refClass = ontM.getKeyValueEqClass().get(eq);
									if (refClass != null) {
										eq = refClass;
										changes = true;
									}

									tempObj.add(eq);
								}
							}
							OWLObjectUnionOf obNew = factory.getOWLObjectUnionOf(tempObj);
							tempAll.add(obNew);
						} else if (c instanceof OWLObjectSomeValuesFrom) {
							OWLClass aaClass = null;
							OWLObjectProperty aaProperty = null;
							Iterator<OWLClass> ac = ((OWLObjectSomeValuesFrom) c).getClassesInSignature().iterator();
							Iterator<OWLObjectProperty> ap = ((OWLObjectSomeValuesFrom) c)
									.getObjectPropertiesInSignature().iterator();
							while (ac.hasNext()) {
								OWLClass acl = ac.next();
								OWLClass refClass2 = ontM.getKeyValueEqClass().get(acl);
								if (refClass2 != null) {
									aaClass = refClass2;
									changes = true;
								} else {
									aaClass = acl;
								}
							}
							while (ap.hasNext()) {
								OWLObjectProperty apro = ap.next();
								OWLObjectProperty refClass2 = ontM.getKeyValueEqObjProperty()
										.get(apro.asOWLObjectProperty());
								if (refClass2 != null) {
									aaProperty = refClass2;
									changes = true;
								} else {
									aaProperty = apro;
								}
							}
							OWLObjectSomeValuesFrom objectSome = factory.getOWLObjectSomeValuesFrom(aaProperty,
									aaClass);
							tempAll.add(objectSome);
						}
					}
					OWLObjectIntersectionOf intObj = factory.getOWLObjectIntersectionOf(tempAll);
					allCinGetOperand = intObj;
				} else if (cls instanceof OWLObjectExactCardinality) {
					int cd = ((OWLObjectExactCardinality) cls).getCardinality();
					OWLObjectPropertyExpression SuperClassProp = ((OWLObjectExactCardinality) cls).getProperty();
					OWLObjectPropertyExpression eqSuperClassPro = SuperClassProp;
					OWLObjectProperty RefSuperClassPro = ontM.getKeyValueEqObjProperty().get(SuperClassProp);
					if (RefSuperClassPro != null) {
						eqSuperClassPro = RefSuperClassPro;
						changes = true;
					}
					if (changes) {
						cardi = factory.getOWLObjectExactCardinality(cd, eqSuperClassPro);
					}

				} else if (cls instanceof OWLObjectMinCardinality) {
					int cd = ((OWLObjectMinCardinality) cls).getCardinality();
					OWLObjectPropertyExpression SuperClassProp = ((OWLObjectMinCardinality) cls).getProperty();
					OWLObjectPropertyExpression eqSuperClassPro = SuperClassProp;
					OWLObjectProperty RefSuperClassPro = ontM.getKeyValueEqObjProperty().get(SuperClassProp);
					if (RefSuperClassPro != null) {
						eqSuperClassPro = RefSuperClassPro;
						changes = true;
					}
					if (changes) {
						cardiMin = factory.getOWLObjectMinCardinality(cd, eqSuperClassPro);
					}

				} else {
					int w = 0;
					// System.out.println("Process me in
					// HMerging_EquivalentClassesProcessor, type of " + cls);
				}
			}
		}
		// here we have all

		if (changes && eqClass1 != null && allCinGetOperand != null) {
			eqAxiom = factory.getOWLEquivalentClassesAxiom(eqClass1, allCinGetOperand);
		} else if (changes && eqClass1 != null && cardi != null) {
			eqAxiom = factory.getOWLEquivalentClassesAxiom(eqClass1, cardi);
		} else if (changes && eqClass1 != null && cardiMin != null) {
			eqAxiom = factory.getOWLEquivalentClassesAxiom(eqClass1, cardiMin);
		}

		return eqAxiom;
	}

	private static OWLAxiom buildIsaEqualAxiom(HModel ontM, OWLAxiom ax) {
		OWLAxiom eqAxiom = null;
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();

		OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) ax).getSuperClass();
		OWLClassExpression SubClass = ((OWLSubClassOfAxiom) ax).getSubClass();
		boolean changes = false;
		if (SuperClass instanceof OWLClassImpl && SubClass instanceof OWLClassImpl) {
			OWLClass eqSup = SuperClass.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSup);
			if (refClass != null) {
				eqSup = refClass;
				changes = true;
			}

			OWLClass eqSub = SubClass.asOWLClass();
			refClass = ontM.getKeyValueEqClass().get(eqSub);
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}

			if (changes)
				eqAxiom = factory.getOWLSubClassOfAxiom(eqSub, eqSup);

		} else if (SuperClass instanceof OWLObjectUnionOf && SubClass instanceof OWLClassImpl) {
			OWLClass eqSub = SubClass.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub);
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}

			Set<OWLClassExpression> tempObj = new HashSet<OWLClassExpression>();
			Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) SuperClass).getOperands().iterator();
			while (rangExist.hasNext()) {
				OWLClassExpression ex = rangExist.next();
				if (ex instanceof OWLClassImpl) {
					OWLClass eq = ex.asOWLClass();
					refClass = ontM.getKeyValueEqClass().get(ex);
					if (refClass != null) {
						eq = refClass;
						changes = true;
					}

					tempObj.add(eq);
				}
			}
			if (changes) {
				OWLObjectUnionOf ob = factory.getOWLObjectUnionOf(tempObj);
				eqAxiom = factory.getOWLSubClassOfAxiom(eqSub, ob);
			}
		} else if (SubClass instanceof OWLObjectUnionOf) {
			// never happend
		} else if (SuperClass instanceof OWLDataExactCardinality && SubClass instanceof OWLClassImpl) {
			int cd = ((OWLDataExactCardinality) SuperClass).getCardinality();
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLDataPropertyExpression SuperClassProp = ((OWLDataExactCardinality) SuperClass).getProperty();
			OWLDataPropertyExpression eqSuperClassPro = SuperClassProp;
			OWLDataProperty RefSuperClassPro = ontM.getKeyValueEqDataPro().get(SuperClassProp);
			if (RefSuperClassPro != null) {
				eqSuperClassPro = RefSuperClassPro;
				changes = true;
			}
			if (changes) {
				OWLDataExactCardinality cardi = factory.getOWLDataExactCardinality(cd, eqSuperClassPro);
				eqAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
			}
		} else if (SuperClass instanceof OWLObjectExactCardinality && SubClass instanceof OWLClassImpl) {
			int cd = ((OWLObjectExactCardinality) SuperClass).getCardinality();
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLObjectPropertyExpression SuperClassProp = ((OWLObjectExactCardinality) SuperClass).getProperty();
			OWLObjectPropertyExpression eqSuperClassPro = SuperClassProp;
			OWLObjectProperty RefSuperClassPro = ontM.getKeyValueEqObjProperty().get(SuperClassProp);
			if (RefSuperClassPro != null) {
				eqSuperClassPro = RefSuperClassPro;
				changes = true;
			}
			if (changes) {
				OWLObjectExactCardinality cardi = factory.getOWLObjectExactCardinality(cd, eqSuperClassPro);
				eqAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
			}
		} else if (SuperClass instanceof OWLObjectMaxCardinality && SubClass instanceof OWLClassImpl) {
			int cd = ((OWLObjectMaxCardinality) SuperClass).getCardinality();
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLObjectPropertyExpression SuperClassProp = ((OWLObjectMaxCardinality) SuperClass).getProperty();
			OWLObjectPropertyExpression eqSuperClassPro = SuperClassProp;
			OWLObjectProperty RefSuperClassPro = ontM.getKeyValueEqObjProperty().get(SuperClassProp);
			if (RefSuperClassPro != null) {
				eqSuperClassPro = RefSuperClassPro;
				changes = true;
			}
			if (changes) {
				OWLObjectMaxCardinality cardi = factory.getOWLObjectMaxCardinality(cd, eqSuperClassPro);
				eqAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
			}
		} else if (SuperClass instanceof OWLDataMaxCardinality && SubClass instanceof OWLClassImpl) {
			int cd = ((OWLDataMaxCardinality) SuperClass).getCardinality();
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLDataPropertyExpression SuperClassProp = ((OWLDataMaxCardinality) SuperClass).getProperty();
			OWLDataPropertyExpression eqSuperClassPro = SuperClassProp;
			OWLDataProperty RefSuperClassPro = ontM.getKeyValueEqDataPro().get(SuperClassProp);
			if (RefSuperClassPro != null) {
				eqSuperClassPro = RefSuperClassPro;
				changes = true;
			}
			if (changes) {
				OWLDataMaxCardinality cardi = factory.getOWLDataMaxCardinality(cd, eqSuperClassPro);
				eqAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
			}
		} else if (SuperClass instanceof OWLDataMinCardinality && SubClass instanceof OWLClassImpl) {
			int cd = ((OWLDataMinCardinality) SuperClass).getCardinality();
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLDataPropertyExpression SuperClassProp = ((OWLDataMinCardinality) SuperClass).getProperty();
			OWLDataPropertyExpression eqSuperClassPro = SuperClassProp;
			OWLDataProperty RefSuperClassPro = ontM.getKeyValueEqDataPro().get(SuperClassProp);
			if (RefSuperClassPro != null) {
				eqSuperClassPro = RefSuperClassPro;
				changes = true;
			}
			if (changes) {
				OWLDataMinCardinality cardi = factory.getOWLDataMinCardinality(cd, eqSuperClassPro);
				eqAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
			}
		} else if (SuperClass instanceof OWLObjectSomeValuesFrom && SubClass instanceof OWLClassImpl) {
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLObjectPropertyExpression pro = ((OWLObjectSomeValuesFrom) SuperClass).getProperty();
			OWLObjectPropertyExpression eqPro = pro;
			OWLObjectPropertyExpression pp = ontM.getKeyValueEqObjProperty().get(pro);
			if (pp != null) {
				eqPro = pp;
				changes = true;
			}
			Set<OWLClass> clSet = ((OWLObjectSomeValuesFrom) SuperClass).getClassesInSignature();
			Iterator<OWLClass> cl = clSet.iterator();
			OWLClass cls = null;
			Set<OWLClass> eqClSet = new HashSet<OWLClass>();
			OWLClass eqCls = null;
			OWLObjectSomeValuesFrom sv = null;
			if (clSet.size() > 1) {
				while (cl.hasNext()) {
					OWLClass c = cl.next();
					OWLClass cq = ontM.getKeyValueEqClass().get(c);
					if (cq != null) {
						eqClSet.add(cq);
						changes = true;
					} else {
						eqClSet.add(c);
					}
				}
				OWLObjectUnionOf uo = factory.getOWLObjectUnionOf(eqClSet);
				sv = factory.getOWLObjectSomeValuesFrom(eqPro, uo);
			} else {
				if (cl.hasNext())
					cls = cl.next();
				eqCls = cls;
				OWLClass cc = ontM.getKeyValueEqClass().get(cls);
				if (cc != null) {
					eqCls = cc;
					changes = true;
				}
				sv = factory.getOWLObjectSomeValuesFrom(eqPro, eqCls);
			}
			if (changes)
				eqAxiom = factory.getOWLSubClassOfAxiom(eqSub, sv);

		} else if (SuperClass instanceof OWLObjectAllValuesFrom && SubClass instanceof OWLClassImpl) {
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLObjectPropertyExpression pro = ((OWLObjectAllValuesFrom) SuperClass).getProperty();
			OWLObjectPropertyExpression eqPro = pro;
			OWLObjectPropertyExpression pp = ontM.getKeyValueEqObjProperty().get(pro);
			if (pp != null) {
				eqPro = pp;
				changes = true;
			}
			Set<OWLClass> clSet = ((OWLObjectAllValuesFrom) SuperClass).getClassesInSignature();
			Iterator<OWLClass> cl = clSet.iterator();
			OWLClass cls = null;
			Set<OWLClass> eqClSet = new HashSet<OWLClass>();
			OWLClass eqCls = null;
			OWLObjectAllValuesFrom sv = null;
			if (clSet.size() > 1) {
				while (cl.hasNext()) {
					OWLClass c = cl.next();
					OWLClass cq = ontM.getKeyValueEqClass().get(c);
					if (cq != null) {
						eqClSet.add(cq);
						changes = true;
					} else {
						eqClSet.add(c);
					}
				}
				OWLObjectUnionOf uo = factory.getOWLObjectUnionOf(eqClSet);
				sv = factory.getOWLObjectAllValuesFrom(eqPro, uo);
			} else {
				if (cl.hasNext())
					cls = cl.next();
				eqCls = cls;
				OWLClass cc = ontM.getKeyValueEqClass().get(cls);
				if (cc != null) {
					eqCls = cc;
					changes = true;
				}
				sv = factory.getOWLObjectAllValuesFrom(eqPro, eqCls);
			}
			if (changes)
				eqAxiom = factory.getOWLSubClassOfAxiom(eqSub, sv);

		} else if (SuperClass instanceof OWLObjectMinCardinality && SubClass instanceof OWLClassImpl) {
			int cd = ((OWLObjectMinCardinality) SuperClass).getCardinality();
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLObjectPropertyExpression SuperClassProp = ((OWLObjectMinCardinality) SuperClass).getProperty();
			OWLObjectPropertyExpression eqSuperClassPro = SuperClassProp;
			OWLObjectProperty RefSuperClassPro = ontM.getKeyValueEqObjProperty().get(SuperClassProp);
			if (RefSuperClassPro != null) {
				eqSuperClassPro = RefSuperClassPro;
				changes = true;
			}
			if (changes) {
				OWLObjectMinCardinality cardi = factory.getOWLObjectMinCardinality(cd, eqSuperClassPro);
				eqAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
			}
		} else {
			int w = 0;
			// System.out.println("Process me in HMerging.java in
			// SubClassProcessor");
		}

		return eqAxiom;
	}

}
