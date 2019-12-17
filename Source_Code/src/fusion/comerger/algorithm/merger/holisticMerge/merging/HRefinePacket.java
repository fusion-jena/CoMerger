package fusion.comerger.algorithm.merger.holisticMerge.merging;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import org.apache.jena.vocabulary.OWL;
import org.junit.Assert;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.OWLEntityRemover;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.clustering.ClusterShareFunc;
import fusion.comerger.algorithm.merger.holisticMerge.general.ShareMergeFunction;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedDpro;
import fusion.comerger.algorithm.merger.model.HModel;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;

public class HRefinePacket {

	// *********************************************************************************************************
	public static HModel ClassesPreservation(HModel ontM) {
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();
		String changesLog = "";
		boolean addedFlag = false;

		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> listParent = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();
		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> listChild = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();

		OWLOntology ont = ontM.getOwlModel();

		if (ontM.getAlterStatus() == false) {
			// create
			listParent = ShareMergeFunction.createParentList(ontM.getOwlModel());
			ontM.setParentList(listParent);
			listChild = ShareMergeFunction.createChildList(ontM.getOwlModel());
			ontM.setChildList(listChild);
			ontM.setAlterStatus(false);
			// We set it to false, because now it is created and no changes come
			// on it
		} else {
			// load
			listParent = ontM.getParentList();
			listChild = ontM.getChildList();
		}

		// %TODO: this should start from root and add the
		// classes
		// check is there any classes from Oi which is not in Om
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		Set<OWLClass> OmClasses = ontM.getOwlModel().getClassesInSignature();// ontM.getRefineClasses();
		for (int i = 0; i < ontM.getInputOwlOntModel().size(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLClass> iterCl = Oi.getClassesInSignature().iterator();
			// createdParent = false;
			// createdChild = false;
			while (iterCl.hasNext()) {
				OWLClass c = iterCl.next(); // cannot add merged classes1
				boolean found = false;
				OWLClass cRef = ontM.getKeyValueEqClass().get(c);
				if (cRef != null) {
					if (OmClasses.contains(cRef))
						found = true;
				} else {
					if (OmClasses.contains(c))
						found = true;
				}
				if (!found) {
					addedFlag = false;
					// if you find it, add it to Om right now
					// if (createdParent == false) {
					// listParent = ShareMergeFunction.createParentList(Oi);
					// createdParent = true;
					// }
					//
					// if (createdChild == false) {
					// listChild = ShareMergeFunction.createChildList(ontM);
					// createdChild = true;
					// }

					OWLClassExpression father = findFirstElement(listParent, c);
					if (father != null) {
						if (cRef != null)
							c = cRef;

						OWLClass fatherRef = ontM.getKeyValueEqClass().get(father);
						if (fatherRef != null)
							father = fatherRef;

						OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(c, father);
						manager.addAxiom(Om, newAxiom);
						ontM.setAlterStatus(true);

						OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(c);
						if (!Om.getAxioms(AxiomType.DECLARATION).contains(ax))
							manager.addAxiom(Om, ax);

						changesLog = changesLog + "\n Add the missing class: "
								+ c.toString().replace("<", "[").replace(">", "]") + " to its respective superclass: "
								+ father.toString().replace("<", "[").replace(">", "]");

						addedFlag = true;
						int r = ontM.getRefineActionOnMerge();
						ontM.setRefineActionOnMerge(r + 1);

					} else {
						// add it to the child
						OWLClassExpression child = findFirstElement(listChild, c);
						if (child != null) {
							if (cRef != null)
								c = cRef;

							OWLClass childRef = ontM.getKeyValueEqClass().get(child);
							if (childRef != null)
								child = childRef;

							OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(child, c);
							manager.addAxiom(Om, newAxiom);
							ontM.setAlterStatus(true);

							OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(c);
							if (!Om.getAxioms(AxiomType.DECLARATION).contains(ax))
								manager.addAxiom(Om, ax);

							changesLog = changesLog + "\n Add the missing class: "
									+ c.toString().replace("<", "[").replace(">", "]") + " to its respective subclass: "
									+ child.toString().replace("<", "[").replace(">", "]");
							addedFlag = true;
							int r = ontM.getRefineActionOnMerge();
							ontM.setRefineActionOnMerge(r + 1);
						}
					}
					if (!addedFlag) {
						// if there is no sub or superclass, add c to root
						OWLClass root = factory.getOWLThing();

						if (cRef != null)
							c = cRef;

						OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(c, root);
						manager.addAxiom(Om, newAxiom);
						ontM.setAlterStatus(true);

						OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(c);
						if (!Om.getAxioms(AxiomType.DECLARATION).contains(ax))
							manager.addAxiom(Om, ax);

						changesLog = changesLog + "\n Add the missing class: "
								+ c.toString().replace("<", "[").replace(">", "]")
								+ " to the root, since there is no respective sub-super class for it. \n";
						addedFlag = true;
						int r = ontM.getRefineActionOnMerge();
						ontM.setRefineActionOnMerge(r + 1);
					}
				}
			}
		}

		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		if (changesLog.length() > 1) {
			MyLogging.log(Level.INFO, changesLog);
		} else {
			MyLogging.log(Level.INFO, "No changes has been done in the class preservation refinement step!\n");
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "Class preservation refinement step has been done successfully. Total time  "
				+ elapsedTime + " ms. \n");

		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - beforeUsedMem;
		StatisticTest.result.put("MU_OM_ClassPreservation", String.valueOf(actualMemUsed));
		System.gc();
		return ontM;
	}
	// **************************************************************************************

	private static OWLClassExpression findFirstElement(HashMap<OWLClassExpression, HashSet<OWLClassExpression>> ilist,
			OWLClassExpression c) {
		if (ilist == null)
			return null;
		OWLClassExpression res = null;
		if (ilist.get(c) != null) {
			Iterator<OWLClassExpression> a = ilist.get(c).iterator();
			if (a.hasNext())
				return a.next();
			// we return the first father/child
		}
		return res;
	}

	private static void printHierarchy(OWLReasoner reasoner, OWLClass clazz) {
		// This function should retunr the class hirerchy via reasoner strating
		// from top
		/*
		 * Only print satisfiable classes -- otherwise we end up with bottom
		 * everywhere
		 */
		// if (reasoner.isSatisfiable(clazz)) {
		System.out.println(clazz);
		/* Find the children and recurse */

		for (OWLClass child : reasoner.getSubClasses(clazz, true).getFlattened()) {
			if (!child.equals(clazz)) {
				printHierarchy(reasoner, child);
			}
		}
		// }

	}

	// *********************************************************************************************************
	public static HModel PropertiesPreservation(HModel ontM) {
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();
		String changesLog = "";
		boolean addedFlag = false;
		// check is there any classes from Oi which is not in Om
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		Set<OWLObjectProperty> OmProp = ontM.getOwlModel().getObjectPropertiesInSignature();
		Set<OWLDataProperty> OmDProp = ontM.getOwlModel().getDataPropertiesInSignature();
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			// Step 1: check all object properties
			Iterator<OWLObjectProperty> iterP = Oi.getObjectPropertiesInSignature().iterator();
			while (iterP.hasNext()) {
				OWLObjectProperty p = iterP.next();
				if (!OmProp.contains(p) && ontM.getKeyValueEqObjProperty().get(p) == null) {
					addedFlag = false;
					// if you find it, add it to Om right now
					Set<OWLClassExpression> domainSet = p.getDomains(Oi);
					if (domainSet.size() > 0) {
						// select the first domain and range
						Iterator<OWLClassExpression> domainIter = domainSet.iterator();
						OWLClassExpression domain = domainIter.next();
						// check domain exists in Om
						if (ontM.getOwlModel().getClassesInSignature().contains(domain)) {
							OWLObjectProperty pRef = ontM.getKeyValueEqObjProperty().get(p);
							if (pRef != null)
								p = pRef;
							domain = ShareMergeFunction.getEqualOiClass(domain, ontM);

							OWLAxiom newAxiom1 = factory.getOWLObjectPropertyDomainAxiom(p, domain);// add
																									// extra
																									// pro
							manager.addAxiom(Om, newAxiom1);

							OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(p);
							if (!Om.getAxioms(AxiomType.DECLARATION).contains(ax))
								manager.addAxiom(Om, ax);

							changesLog = changesLog + "\n Add the missing ObjectProperty: "
									+ p.toString().replace("<", "[").replace(">", "]") + " to its respective domain: "
									+ domain.toString().replace("<", "[").replace(">", "]");
							addedFlag = true;
							int r = ontM.getRefineActionOnMerge();
							ontM.setRefineActionOnMerge(r + 1);
						} else {
							// check the equal classes
							if (domain instanceof OWLClassImpl) {
								OWLObjectProperty pRef = ontM.getKeyValueEqObjProperty().get(p);
								if (pRef != null)
									p = pRef;

								OWLClass refDomain = ontM.getKeyValueEqClass().get(domain.asOWLClass());
								if (refDomain != null)
									domain = refDomain;

								OWLAxiom newAxiom1 = factory.getOWLObjectPropertyDomainAxiom(p, domain);
								manager.addAxiom(Om, newAxiom1);

								OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(p);
								if (!Om.getAxioms(AxiomType.DECLARATION).contains(ax))
									manager.addAxiom(Om, ax);

								changesLog = changesLog + "\n Add the missing ObjectProperty: "
										+ p.toString().replace("<", "[").replace(">", "]")
										+ " to its respective domain:"
										+ refDomain.toString().replace("<", "[").replace(">", "]");
								addedFlag = true;
								int r = ontM.getRefineActionOnMerge();
								ontM.setRefineActionOnMerge(r + 1);
							} else {
								// TODO: process it: it requires considering all
								// types! or- it only needs checking
								// ObjectUnionOF
								// case, not more!
								if (!addedFlag) {
									String msg = "The ObjectProperty: "
											+ p.toString().replace("<", "[").replace(">", "]")
											+ "is missing! But the domain of this property is not OWLCLass type, so we skip it.\n";
									MyLogging.log(Level.WARNING, msg);
									addedFlag = true;
								}
							}
						}
					}

					// check range exists in Om
					Set<OWLClassExpression> rangeSet = p.getRanges(Oi);
					if (rangeSet.size() > 0) {
						Iterator<OWLClassExpression> rangeIter = rangeSet.iterator();
						OWLClassExpression range = rangeIter.next();
						if (ontM.getOwlModel().getClassesInSignature().contains(range)) {
							OWLObjectProperty pRef = ontM.getKeyValueEqObjProperty().get(p);
							if (pRef != null)
								p = pRef;

							range = ShareMergeFunction.getEqualOiClass(range, ontM);

							OWLAxiom newAxiom2 = factory.getOWLObjectPropertyRangeAxiom(p, range);
							manager.addAxiom(Om, newAxiom2);

							OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(p);
							if (!Om.getAxioms(AxiomType.DECLARATION).contains(ax))
								manager.addAxiom(Om, ax);

							changesLog = changesLog + "\n Add the missing ObjectProperty: "
									+ p.toString().replace("<", "[").replace(">", "]") + " to its respective range: "
									+ range.toString().replace("<", "[").replace(">", "]");
							addedFlag = true;
							int r = ontM.getRefineActionOnMerge();
							ontM.setRefineActionOnMerge(r + 1);
						} else {
							// check the equal classes
							if (range instanceof OWLClassImpl) {
								OWLObjectProperty pRef = ontM.getKeyValueEqObjProperty().get(p);
								if (pRef != null)
									p = pRef;

								OWLClass refRange = ontM.getKeyValueEqClass().get(range.asOWLClass());
								if (refRange != null)
									range = refRange;

								OWLAxiom newAxiom1 = factory.getOWLObjectPropertyRangeAxiom(p, range);
								manager.addAxiom(Om, newAxiom1);

								OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(p);
								if (!Om.getAxioms(AxiomType.DECLARATION).contains(ax))
									manager.addAxiom(Om, ax);

								changesLog = changesLog + "\n Add the missing ObjectProperty: "
										+ p.toString().replace("<", "[").replace(">", "]")
										+ " to its respective range: "
										+ refRange.toString().replace("<", "[").replace(">", "]");
								addedFlag = true;
								int r = ontM.getRefineActionOnMerge();
								ontM.setRefineActionOnMerge(r + 1);

							} else {
								// TODO: process it: it requires considering all
								// types! or- it only needs checking
								// ObjectUnionOF
								// case, not more!
								if (!addedFlag) {
									String msg = "The ObjectProperty: "
											+ p.toString().replace("<", "[").replace(">", "]")
											+ "is missing! But the range of this property is not OWLCLass type, so we skip it.\n";
									MyLogging.log(Level.WARNING, msg);
									addedFlag = true;
								}
							}
						}
					}
					if (!addedFlag) {
						String msg = "The ObjectProperty: " + p.toString().replace("<", "[").replace(">", "]")
								+ "is missing! But none of its domain and range are already exist in the merged ontology. So, the missing property can't be added.\n";
						MyLogging.log(Level.WARNING, msg);
						// NOTE: It means there is no class for this property.
						// When the class
						// of property does not exist, we cannot force in this
						// function,
						// to add classes, we just skip this property
					}

				}
			}

			// Step 2: check all datatype properties
			Iterator<OWLDataProperty> iterDbPro = Oi.getDataPropertiesInSignature().iterator();
			while (iterDbPro.hasNext()) {
				OWLDataProperty p = iterDbPro.next();
				if (!OmDProp.contains(p) && ontM.getKeyValueEqDataPro().get(p) == null) {
					addedFlag = false;
					// if you find it, add it to Om right now
					Iterator<OWLClassExpression> domainIter = p.getDomains(Oi).iterator();
					Iterator<OWLDataRange> rangeIter = p.getRanges(Oi).iterator();
					if (domainIter != null && domainIter.hasNext()) {
						// select the first domain and range
						OWLClassExpression domain = domainIter.next();
						// check domain exists in Om
						if (ontM.getOwlModel().getClassesInSignature().contains(domain)) {
							OWLDataProperty pRef = ontM.getKeyValueEqDataPro().get(p);
							if (pRef != null)
								p = pRef;

							domain = ShareMergeFunction.getEqualOiClass(domain, ontM);

							OWLAxiom newAxiom1 = factory.getOWLDataPropertyDomainAxiom(p, domain);
							manager.addAxiom(Om, newAxiom1);

							OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(p);
							if (!Om.getAxioms(AxiomType.DECLARATION).contains(ax))
								manager.addAxiom(Om, ax);

							changesLog = changesLog + "\n Add the missing DatatypeProperty: "
									+ p.toString().replace("<", "[").replace(">", "]") + " to its respective domain: "
									+ domain.toString().replace("<", "[").replace(">", "]");
							addedFlag = true;
							int r = ontM.getRefineActionOnMerge();
							ontM.setRefineActionOnMerge(r + 1);
						} else {
							// check the equal classes
							if (domain instanceof OWLClassImpl) {
								OWLDataProperty pRef = ontM.getKeyValueEqDataPro().get(p);
								if (pRef != null)
									p = pRef;

								OWLClass refDomain = ontM.getKeyValueEqClass().get(domain.asOWLClass());
								if (refDomain != null)
									domain = refDomain;

								OWLAxiom newAxiom1 = factory.getOWLDataPropertyDomainAxiom(p, domain);
								manager.addAxiom(Om, newAxiom1);

								OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(p);
								if (!Om.getAxioms(AxiomType.DECLARATION).contains(ax))
									manager.addAxiom(Om, ax);

								changesLog = changesLog + "\n Add the missing DatatypeProperty: "
										+ p.toString().replace("<", "[").replace(">", "]")
										+ " to its respective domain:"
										+ refDomain.toString().replace("<", "[").replace(">", "]");
								addedFlag = true;
								int r = ontM.getRefineActionOnMerge();
								ontM.setRefineActionOnMerge(r + 1);

							} else {
								// TODO: process it: it requires considering all
								// types! or- it only needs checking
								// ObjectUnionOF
								// case, not more!
								if (!addedFlag) {
									String msg = "The DatatypeProperty: "
											+ p.toString().replace("<", "[").replace(">", "]")
											+ "is missing! But the domain of this property is not OWLCLass type, so we skip it.\n";
									MyLogging.log(Level.WARNING, msg);
									addedFlag = true;
								}
							}
						}
					}

					// check range exists in Om
					if (rangeIter != null && rangeIter.hasNext()) {
						OWLDataRange range = rangeIter.next();
						if (ontM.getOwlModel().getClassesInSignature().contains(range)) {
							OWLDataProperty pRef = ontM.getKeyValueEqDataPro().get(p);
							if (pRef != null)
								p = pRef;

							OWLAxiom newAxiom2 = factory.getOWLDataPropertyRangeAxiom(p, range);
							manager.addAxiom(Om, newAxiom2);

							OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(p);
							if (!Om.getAxioms(AxiomType.DECLARATION).contains(ax))
								manager.addAxiom(Om, ax);

							changesLog = changesLog + "Add the missing DatatypeProperty: "
									+ p.toString().replace("<", "[").replace(">", "]") + " to its respective range: "
									+ range.toString().replace("<", "[").replace(">", "]");
							addedFlag = true;
							int r = ontM.getRefineActionOnMerge();
							ontM.setRefineActionOnMerge(r + 1);
						} else {
							// We do not have equal/mapped entity for
							// OWLDataRange check the equal classes
						}
					}
					if (!addedFlag) {
						String msg = "The DatatypeProperty: " + p.toString().replace("<", "[").replace(">", "]")
								+ "is missing! But none of its domain and range are already exist in the merged ontology. So, the missing property can't be added.\n";
						MyLogging.log(Level.WARNING, msg);
						// NOTE: It means there is no class for this property.
						// When the class
						// of property does not exist, we cannot force in this
						// function,
						// to add classes, we just skip this property
					}

				}
			}

		}

		// TODO: do it for FunctionalProperty and InverseFunctionalProperty
		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		if (changesLog.length() > 1) {
			MyLogging.log(Level.INFO, changesLog);
		} else {
			MyLogging.log(Level.INFO, "No changes has been done in the property preservation refinement step!\n");
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "Property preservation refinement step has been done successfully. Total time  "
				+ elapsedTime + " ms. \n");

		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - beforeUsedMem;
		StatisticTest.result.put("MU_OM_PropertyPreservation", String.valueOf(actualMemUsed));
		System.gc();
		return ontM;
	}

	// *********************************************************************************************************
	public static HModel InstancesPreservation(HModel ontM) {
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();

		String changesLog = "";
		boolean addedFlag = false;
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		Set<OWLNamedIndividual> OmIndividuals = ontM.getOwlModel().getIndividualsInSignature();
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLNamedIndividual> iterIn = Oi.getIndividualsInSignature().iterator();
			while (iterIn.hasNext()) {
				OWLNamedIndividual In = iterIn.next();
				if (!OmIndividuals.contains(In)) {
					addedFlag = false;
					// if you find it, add it to Om right now
					Iterator<OWLClass> cIter = In.getClassesInSignature().iterator();
					if (cIter != null && cIter.hasNext()) {
						// add it to the first class
						OWLClass c = cIter.next();
						OWLClass cRef = ontM.getKeyValueEqClass().get(c);
						if (cRef != null)
							c = cRef;
						// check c exists in Om
						if (ontM.getOwlModel().getClassesInSignature().contains(c)) {
							OWLAxiom newAxiom = factory.getOWLClassAssertionAxiom(c, In);
							manager.addAxiom(Om, newAxiom);
							changesLog = changesLog + "Add the missing instance: "
									+ In.toString().replace("<", "[").replace(">", "]") + " to its respective class: "
									+ c.toString().replace("<", "[").replace(">", "]");
							addedFlag = true;
							int r = ontM.getRefineActionOnMerge();
							ontM.setRefineActionOnMerge(r + 1);
						}
						if (!addedFlag) {
							String msg = "The instance: " + In.toString().replace("<", "[").replace(">", "]")
									+ "is missing! But its respective class is not already exist in the merged ontology. So, the missing instance can't be added.\n";
							MyLogging.log(Level.WARNING, msg);
						}
					}
				}
			}
		}

		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		if (changesLog.length() > 1) {
			MyLogging.log(Level.INFO, changesLog);
		} else {
			MyLogging.log(Level.INFO, "No changes has been done in the instance preservation refinement step!\n");
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Instance refinement step has been done successfully. Total time  " + elapsedTime + " ms. \n");

		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - beforeUsedMem;
		StatisticTest.result.put("MU_OM_InstancePreservation", String.valueOf(actualMemUsed));
		System.gc();
		return ontM;
	}

	// *********************************************************************************************************
	public static HModel CorrespondencesPreservation(HModel ontM) {
		// TODO because of re-writing axioms, all corresponding
		// classes are already assigned to the reference classes
		long startTime = System.currentTimeMillis();

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Correspondence preservation refinement step has been done successfully. No changes has been done in this step. Total time  "
						+ elapsedTime + " ms. \n");

		return ontM;
	}

	// *********************************************************************************************************
	public static HModel CorrespondPropertyPreservation(HModel ontM) {
		// TODO because of re-writing axioms, all properties of
		// corresponding classes are already assigned to the reference classes

		long startTime = System.currentTimeMillis();

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Correspondence property preservation refinement step has been done successfully. No changes has been done in this step. Total time  "
						+ elapsedTime + " ms. \n");
		return ontM;
	}
	// *********************************************************************************************************

	public static HModel ValuePreservation(HModel ontM) {
		// Properties' values from the (all/target) input ontologies should
		// be preserved in the merged ontology [6,7]. In case of conflicts a
		// resolution strategy is required.

		// 1- we should check that all properties's value from the input
		// ontologies should be existed in the merged ontology.
		// 2- for the corresponding properties, their value should keep same

		// first what is propert value? label of properties is also a value
		// getpropertiesvalue function

		long startTime = System.currentTimeMillis();
		String msg = "";
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		Set<OWLObjectProperty> OmPro = Om.getObjectPropertiesInSignature();
		// TODO: do it for object and datatype etc. property
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLObjectProperty> iter3 = Oi.getObjectPropertiesInSignature().iterator();
			while (iter3.hasNext()) {
				OWLObjectProperty pOi = iter3.next();
				if (OmPro.contains(pOi) || ontM.getKeyValueEqObjProperty().get(pOi) != null) {
					// if the property itself exist in Om, then we check the
					// values of that property, whether they are exist in om or
					// not
					OWLObjectProperty pOiRef = pOi;
					if (ontM.getKeyValueEqObjProperty().get(pOi) != null)
						pOiRef = ontM.getKeyValueEqObjProperty().get(pOi);

					Set<OWLDataProperty> dpro = pOi.getDataPropertiesInSignature();
					Set<OWLDataProperty> dproRef = pOiRef.getDataPropertiesInSignature();
					System.out.println("OWLDataProperty:" + dpro + "\t" + dproRef);
					if (!dpro.equals(dproRef)) {
						msg = "The dataproperty: " + " of property: "
								+ pOi.toString().replace("<", "[").replace(">", "]")
								+ " is skiped, and only the dataproperty of the reference equal entity is keeped.\n";
					}

					Set<OWLDatatype> dt = pOi.getDatatypesInSignature();
					Set<OWLDatatype> dtRef = pOiRef.getDatatypesInSignature();
					System.out.println("OWLDatatype:" + dt + "\t" + dtRef);
					if (!dt.equals(dtRef)) {
						msg = "The datatype: " + " of property: " + pOi.toString().replace("<", "[").replace(">", "]")
								+ " is skiped, and only the datatype of the reference equal entity is keeped.\n";
					}
				}
				// p.getpropertyvalue
				// Searcher.values(Om.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION),
				// p);
			}
		}

		ArrayList<HMappedDpro> OmEqDataPro = ontM.getEqDataProperties();
		// TODO:error :How to get type of datatypeproperties in OWL?
		for (int j = 0; j < OmEqDataPro.size(); j++) {
			OWLDataProperty Dpro = OmEqDataPro.get(j).getRefDpro();
			Set<OWLDataRange> dataRangeRef = Dpro.getRanges(Om);
			Set<OWLClassExpression> dataDomainRef = Dpro.getDomains(Om);

			Iterator<OWLDataProperty> iter = OmEqDataPro.get(j).getMappedDpro().iterator();
			while (iter.hasNext()) {
				OWLDataProperty eqDpro = iter.next();
				Set<OWLDataRange> dataRange = null;
				for (int i = 0; i < ontM.getInputOntNumber(); i++) {
					OWLOntology oi = ontM.getInputOwlOntModel().get(i);
					dataRange = eqDpro.getRanges(oi);
					if (dataRange != null && dataRange.size() > 0) {
						break;
						// it means the property is in oi, no need to search in
						// other oi
					}
				}

				// Set<OWLClassExpression> dataDomain = eqDpro.getDomains(Om);

				if (!dataRange.containsAll(dataRangeRef)) {
					// It means the type of datatypeProperty is different
					// then we only should keep the value of one of them.
					msg = "The dataype: " + " of datatypeProperty: "
							+ Dpro.toString().replace("<", "[").replace(">", "]")
							+ " is skiped, and only the datatype of the reference equal entity is keeped.\n";

				}

			}
		}

		if (msg.equals(""))
			msg = "All Value of properties from the input ontologies have been preserved without any conflict. \n";

		MyLogging.log(Level.INFO, msg);

		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Property's value preservation refinement step has been done successfully. Total time  " + elapsedTime
						+ " ms. \n");
		return ontM;
	}

	public static HModel StructurePreservation(HModel ontM) {
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();
		OWLOntologyManager manager = ontM.getManager();
		Set<OWLSubClassOfAxiom> OmSubclassAxiom = ontM.getOwlModel().getAxioms(AxiomType.SUBCLASS_OF);
		HashMap<OWLAxiom, OWLAxiom> equalAxioms = ontM.getEqAxioms();

		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLSubClassOfAxiom> iterOi = Oi.getAxioms(AxiomType.SUBCLASS_OF).iterator();
			while (iterOi.hasNext()) {
				OWLSubClassOfAxiom ax = iterOi.next();
				if (!OmSubclassAxiom.contains(ax)) {
					OWLAxiom as = equalAxioms.get(ax);
					if (as == null) {
						/*
						 * it means this subclass axiom or its equal axiom does
						 * not exist in Om, so it should be added now
						 */
						manager.addAxiom(ontM.getOwlModel(), ax);

						String msg = "In the merged ontology, the isA axioms: "
								+ ax.toString().replace("<", "[").replace(">", "]")
								+ " does not exist. So it added to the merged ontology to keep the same structure as the input ontology. \n";
						MyLogging.log(Level.WARNING, msg);
						int r = ontM.getRefineActionOnMerge();
						ontM.setRefineActionOnMerge(r + 1);
					}
				}

			}
		}

		ontM.SetManager(manager);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "structure preservation refinement step has been done successfully. Total time  "
				+ elapsedTime + " ms. \n");

		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - beforeUsedMem;
		StatisticTest.result.put("MU_OM_StrPreservation", String.valueOf(actualMemUsed));
		System.gc();
		return ontM;
	}

	public static HModel StructurePreservationOldNew(HModel ontM) {
		long startTime = System.currentTimeMillis();

		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		Set<OWLClass> OmClasses = ontM.getOwlModel().getClassesInSignature();
		OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(Om));

		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> OmParentList = ShareMergeFunction.createParentList(Om);
		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> OiParentList = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();

		if (ontM.getAlterStatus() == false) {
			// create
			OmParentList = ShareMergeFunction.createParentList(ontM.getOwlModel());
			ontM.setParentList(OmParentList);
			// listChild =
			// ShareMergeFunction.createChildList(ontM.getOwlModel());
			// ontM.setChildList(listChild);
			ontM.setAlterStatus(false);
			// We set it to false, because now it is created and no changes come
			// on it
		} else {
			// load
			OmParentList = ontM.getParentList();
			// listChild = ontM.getChildList();
		}

		Set<OWLClassExpression> equality = null;
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			OiParentList.putAll(ShareMergeFunction.createParentList(Oi));
			Iterator<OWLClass> iter = Oi.getClassesInSignature().iterator();
			while (iter.hasNext()) {
				OWLClass ci = iter.next();

				boolean completlyDifferentStructure = false;
				boolean deleteAllFromOm = false;
				boolean addAllToOm = false;

				OWLClass cm = ontM.getKeyValueEqClass().get(ci);
				if (OmClasses.contains(ci) || cm != null) {
					if (cm == null)
						cm = ci;
					HashSet<OWLClassExpression> superCi = OiParentList.get(ci);
					Set<OWLClassExpression> superCm = OmParentList.get(cm);

					if (superCi != null) {
						if (superCm == null) {
							completlyDifferentStructure = true;
							addAllToOm = true;
						}
					} else if (superCm != null) {
						if (superCi == null) {
							completlyDifferentStructure = true;
							deleteAllFromOm = true;
						}
					}

					if (completlyDifferentStructure) {
						if (addAllToOm && superCi != null) {
							Set<OWLClassExpression> SuperCiEq = ShareMergeFunction.getEqualOiSetClass(superCi, ontM);
							Iterator<OWLClassExpression> ciIter = SuperCiEq.iterator();
							while (ciIter.hasNext()) {
								OWLClassExpression ciS = ciIter.next();
								OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(cm, ciS);
								manager.addAxiom(Om, newAxiom);
							}
							ontM.setAlterStatus(true);
							String msg = "In the merged ontology, the hierarchy structure for class: "
									+ ci.toString().replace("<", "[").replace(">", "]")
									+ " is not kept same as input ontology. So all isA hierarchies have been added to add to the merged ontology for that class.\n";
							MyLogging.log(Level.WARNING, msg);

						}

						if (deleteAllFromOm && superCm != null) {
							Iterator<OWLClassExpression> cmIter = superCm.iterator();
							while (cmIter.hasNext()) {
								OWLClassExpression father = cmIter.next();
								OWLSubClassOfAxiom axiom = factory.getOWLSubClassOfAxiom(father, ci);
								manager.removeAxiom(Om, axiom);
							}
							ontM.setAlterStatus(true);
							String msg = "In the merged ontology, the hierarchy structure for class: "
									+ ci.toString().replace("<", "[").replace(">", "]")
									+ " is not kept same as input ontology. So all isA hierarchy have been deleted from the merged ontology for that class.\n";
							MyLogging.log(Level.WARNING, msg);

						}
					} else if (superCi != null && superCm != null) {
						equality = ClusterShareFunc.compareEquality(superCi, superCm, ontM);

						Iterator<OWLClassExpression> addedAxioms = equality.iterator();
						while (addedAxioms.hasNext()) {
							OWLClassExpression ciS = addedAxioms.next();
							OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(cm, ciS);
							manager.addAxiom(Om, newAxiom);
							ontM.setAlterStatus(true);
							String msg = "In the merged ontology, the hierarchy structure for class: "
									+ ci.toString().replace("<", "[").replace(">", "]")
									+ " is not kept same as input ontology. So the missing isA hierarchy has been added to the merged ontology for that class.\n";
							MyLogging.log(Level.WARNING, msg);

						}
					}
				} else {
					String msg = "The class: " + ci.toString().replace("<", "[").replace(">", "]")
							+ " is missing in the merged ontology. So, the hierarchy structure of that class also cannot be kept.\n";
					MyLogging.log(Level.WARNING, msg);
				}
			}
		}

		manager.applyChanges(remover.getChanges());
		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "structure preservation refinement step has been done successfully. Total time  "
				+ elapsedTime + " ms. \n");

		return ontM;

	}

	// *********************************************************************************************************
	public static HModel ClassRedundancyProhibition(HModel ontM) {
		long startTime = System.currentTimeMillis();

		Set<OWLClass> OmClasses = ontM.getOwlModel().getClassesInSignature();
		// since it is a SET, so there is no repeated class inside the ontology

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Class redundancy refinement step has been done successfully. There was no redundant classes in the merged ontology. Total time  "
						+ elapsedTime + " ms. \n");
		return ontM;
	}
	// *********************************************************************************************************

	public static HModel PropertyRedundancyProhibition(HModel ontM) {
		long startTime = System.currentTimeMillis();

		Set<OWLObjectProperty> OmProperties = ontM.getOwlModel().getObjectPropertiesInSignature();
		// since it is a SET, so there is no repeated class inside the ontology

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Property redundancy refinement step has been done successfully. There was no redundant properties in the merged ontology. Total time  "
						+ elapsedTime + " ms. \n");
		return ontM;
	}
	// *********************************************************************************************************

	public static HModel InstanceRedundancyProhibition(HModel ontM) {
		long startTime = System.currentTimeMillis();
		Set<OWLNamedIndividual> a = ontM.getOwlModel().getIndividualsInSignature();
		// since it is a SET, so there is no repeated class inside the ontology

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Instance redundancy refinement step has been done successfully. There was no redundant instances in the merged ontology. Total time  "
						+ elapsedTime + " ms. \n");
		return ontM;
	}

	// *********************************************************************************************************

	public static HModel ExtraneousEntityProhibition(HModel ontM) {
		long startTime = System.currentTimeMillis();
		// Step 1 : Check for classes
		deleteExtraClasses(ontM);

		// Step 2: Check for object properties and datatype properties
		deleteExtraObjectProperty(ontM);
		deleteExtraDataProperty(ontM);

		// Step 3: check for instances
		deleteExtraInstances(ontM);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Extraneous entity prohibition refinement step has been done successfully. Total time  " + elapsedTime
						+ " ms. \n");
		return ontM;
	}

	// *********************************************************************************************************
	private static void deleteExtraClasses(HModel ontM) {
		Iterator<OWLClass> iter = ontM.getOwlModel().getClassesInSignature().iterator();
		boolean exist = false;
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(Om));
		while (iter.hasNext()) {
			OWLClass cm = iter.next();
			exist = false;
			// check cm exist in one of Oi
			for (int i = 0; i < ontM.getInputOntNumber(); i++) {
				if (ontM.getInputOwlOntModel().get(i).getClassesInSignature().contains(cm)) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				Set<OWLClass> mappedRefinedClass = ShareMergeFunction.getMappedClassesforClass(ontM, cm);
				if (mappedRefinedClass != null) {
					Iterator<OWLClass> iterMap = mappedRefinedClass.iterator();
					while (iterMap.hasNext()) {
						if (exist)
							break;
						OWLClass c = iterMap.next();
						for (int i = 0; i < ontM.getInputOntNumber(); i++) {
							if (ontM.getInputOwlOntModel().get(i).getClassesInSignature().contains(c)) {
								exist = true;
								break;
							}
						}
					}
				}
			}

			if (!exist) {
				// delete cm from Om
				if (!cm.getIRI().toString().equals(OWL.Thing.getURI().toString())) {
					cm.accept(remover);
					manager.applyChanges(remover.getChanges());
					ontM.SetOwlModel(Om);
					ontM.SetManager(manager);
					String msg = "In the extranoues prohibition refinement step: the additional classs than input ontologies entities, i.e.  "
							+ cm.toString().replace("<", "[").replace(">", "]") + " has been deleted.\n ";
					MyLogging.log(Level.WARNING, msg);
				}
			}
		}
		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);
	}

	// *********************************************************************************************************

	private static void deleteExtraObjectProperty(HModel ontM) {
		Iterator<OWLObjectProperty> iter = ontM.getOwlModel().getObjectPropertiesInSignature().iterator();
		boolean exist = false;
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(Om));
		while (iter.hasNext()) {
			OWLObjectProperty cm = iter.next();
			exist = false;
			// check cm exist in one of Oi
			for (int i = 0; i < ontM.getInputOntNumber(); i++) {
				if (ontM.getInputOwlOntModel().get(i).getObjectPropertiesInSignature().contains(cm)) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				Set<OWLObjectProperty> mappedRefinedObj = ShareMergeFunction.getMappedObjectforObject(ontM, cm);
				if (mappedRefinedObj != null) {
					Iterator<OWLObjectProperty> iterMap = mappedRefinedObj.iterator();
					while (iterMap.hasNext()) {
						if (exist)
							break;
						OWLObjectProperty c = iterMap.next();
						for (int i = 0; i < ontM.getInputOntNumber(); i++) {
							if (ontM.getInputOwlOntModel().get(i).getObjectPropertiesInSignature().contains(c)) {
								exist = true;
								break;
							}
						}
					}
				}
			}

			if (!exist) {
				// delete cm from Om
				cm.accept(remover);
				manager.applyChanges(remover.getChanges());
				ontM.SetOwlModel(Om);
				ontM.SetManager(manager);
				String msg = "In the extranoues prohibition refinement step: the additional object property than input ontologies entities, i.e.  "
						+ cm.toString().replace("<", "[").replace(">", "]") + " has been deleted. \n ";
				MyLogging.log(Level.WARNING, msg);

			}
		}
		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);
	}

	// *********************************************************************************************************

	private static void deleteExtraDataProperty(HModel ontM) {
		Iterator<OWLDataProperty> iter = ontM.getOwlModel().getDataPropertiesInSignature().iterator();
		boolean exist = false;
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(Om));
		while (iter.hasNext()) {
			OWLDataProperty cm = iter.next();
			exist = false;
			// check cm exist in one of Oi
			for (int i = 0; i < ontM.getInputOntNumber(); i++) {
				if (ontM.getInputOwlOntModel().get(i).getDataPropertiesInSignature().contains(cm)) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				Set<OWLDataProperty> mappedRefinedClass = ShareMergeFunction.getMappedDproforDpro(ontM, cm);
				if (mappedRefinedClass != null) {
					Iterator<OWLDataProperty> iterMap = mappedRefinedClass.iterator();
					while (iterMap.hasNext()) {
						if (exist)
							break;
						OWLDataProperty c = iterMap.next();
						for (int i = 0; i < ontM.getInputOntNumber(); i++) {
							if (ontM.getInputOwlOntModel().get(i).getDataPropertiesInSignature().contains(c)) {
								exist = true;
								break;
							}
						}
					}
				}
			}

			if (!exist) {
				// delete cm from Om
				cm.accept(remover);
				manager.applyChanges(remover.getChanges());
				ontM.SetOwlModel(Om);
				ontM.SetManager(manager);
				String msg = "In the extranoues prohibition refinement step: the additional datatypeProperty than input ontologies entities, i.e.  "
						+ cm.toString().replace("<", "[").replace(">", "]") + " has been deleted. \n";
				MyLogging.log(Level.WARNING, msg);

			}
		}
		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);
	}

	// *********************************************************************************************************

	private static void deleteExtraInstances(HModel ontM) {
		Iterator<OWLNamedIndividual> iter = ontM.getOwlModel().getIndividualsInSignature().iterator();
		boolean exist = false;
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(Om));
		while (iter.hasNext()) {
			OWLNamedIndividual cm = iter.next();
			exist = false;
			// check cm exist in one of Oi
			for (int i = 0; i < ontM.getInputOntNumber(); i++) {
				if (ontM.getInputOwlOntModel().get(i).getIndividualsInSignature().contains(cm)) {
					exist = true;
					break;
				}
			}

			if (!exist) {
				// delete cm from Om
				cm.accept(remover);
				manager.applyChanges(remover.getChanges());
				ontM.SetOwlModel(Om);
				ontM.SetManager(manager);
				String msg = "In the extranoues prohibition refinement step: the additional instance than input ontologies entities, i.e.  "
						+ cm.toString().replace("<", "[").replace(">", "]") + " has been deleted. \n";
				MyLogging.log(Level.WARNING, msg);

			}
		}
		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);
	}

	// *********************************************************************************************************

	public static HModel DomainRangeMinimality(HModel ontM) {
		// TODO: add log info in this function
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		Iterator<OWLObjectProperty> iter = Om.getObjectPropertiesInSignature().iterator();
		while (iter.hasNext()) {
			OWLObjectProperty obj = iter.next();
			// For multiple domains
			Set<OWLClassExpression> domain = obj.getDomains(Om);
			if (domain.size() > 1) {
				// Step 1: Delete their respective axioms which refer to the
				// multiple
				// domains of that object property
				HashSet<OWLAxiom> domAxioms = getRelatedDomainAxioms(domain, obj, Om);
				Iterator<OWLAxiom> iterAx = domAxioms.iterator();
				while (iterAx.hasNext()) {
					OWLAxiom ax = iterAx.next();
					manager.removeAxiom(Om, ax);
					// int r = ontM.getRefineActionOnMerge();
					// ontM.setRefineActionOnMerge(r + 1);
				}
				// Step 2: add all domains of this object as the union of to the
				// new class cNew
				OWLObjectUnionOf uniClass = factory.getOWLObjectUnionOf(domain);
				OWLAxiom newAxiom = factory.getOWLObjectPropertyDomainAxiom(obj, uniClass);
				manager.addAxiom(Om, newAxiom);
				int r = ontM.getRefineActionOnMerge();
				ontM.setRefineActionOnMerge(r + 1);
			}

			// For multiple ranges
			Set<OWLClassExpression> range = obj.getRanges(Om);
			if (range.size() > 1) {
				// Step 1: Delete their respective axioms which refer to the
				// multiple
				// domains of that object property
				HashSet<OWLAxiom> rangAxioms = getRelatedRangeAxioms(range, obj, Om);
				Iterator<OWLAxiom> iterAx = rangAxioms.iterator();
				while (iterAx.hasNext()) {
					OWLAxiom ax = iterAx.next();
					manager.removeAxiom(Om, ax);
					// int r = ontM.getRefineActionOnMerge();
					// ontM.setRefineActionOnMerge(r + 1);
				}
				// Step 2: add all ranges of this object as the union of to the
				// new class cNew
				OWLObjectUnionOf uniClass = factory.getOWLObjectUnionOf(range);
				OWLAxiom newAxiom = factory.getOWLObjectPropertyRangeAxiom(obj, uniClass);
				manager.addAxiom(Om, newAxiom);
				int r = ontM.getRefineActionOnMerge();
				ontM.setRefineActionOnMerge(r + 1);
			}
		}

		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Property's domain and range oneness refinement step has been done successfully. Total time  "
						+ elapsedTime + " ms. \n");

		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - beforeUsedMem;
		StatisticTest.result.put("MU_OM_oneness", String.valueOf(actualMemUsed));
		System.gc();
		return ontM;
	}

	// *********************************************************************************************************

	public static HModel ClassAcyclicity(HModel ontM) {
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();

		OWLOntology Om = ontM.getOwlModel();
		Set<OWLAxiom> res = new HashSet<OWLAxiom>();

		OWLOntologyManager manager = ontM.getManager();
		OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(Om));

		Iterator<OWLClass> cl = Om.getClassesInSignature().iterator();

		HashMap<OWLClassExpression, ArrayList<OWLClassExpression>> listParent = ShareMergeFunction
				.generateParentChildList(Om);

		while (cl.hasNext()) {
			OWLClass c = cl.next();
			boolean hasCyle = false;
			ArrayList<OWLClassExpression> cParent = listParent.get(c);
			if (cParent != null && ShareMergeFunction.hasDuplicate(cParent))
				hasCyle = true;

			if (cParent != null && cParent.contains(c))
				hasCyle = true;

			if (hasCyle) {
				// Delete it
				Iterator<OWLAxiom> iter = c.getReferencingAxioms(Om).iterator();
				while (iter.hasNext()) {
					OWLAxiom ax = iter.next();
					if (ax.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
						OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) ax).getSuperClass();
						OWLClassExpression SubClass = ((OWLSubClassOfAxiom) ax).getSubClass();

						if (SuperClass instanceof OWLClassImpl && SubClass instanceof OWLClassImpl) {
							if (SuperClass.equals(c) && SubClass.equals(c)) {
								// remove it
								manager.removeAxiom(Om, ax);
								res.add(ax);
								int r = ontM.getRefineActionOnMerge();
								ontM.setRefineActionOnMerge(r + 1);
							}
						}
					}
				}
			}

		}

		manager.applyChanges(remover.getChanges());
		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		String msg = "";
		if (res.size() > 0) {
			msg = "To apply acyclicity in the class hirearchy refinement, the below axioms have been deleted: \n";
			Iterator<OWLAxiom> iter = res.iterator();
			while (iter.hasNext()) {
				OWLAxiom ax = iter.next();
				msg = msg + ax.toString().replace("<", "[").replace(">", "]");
			}
		} else {
			msg = "There was no cycle in the class hierarchy in the merged ontology. \n";
		}
		MyLogging.log(Level.INFO, msg);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Acyclicity in the class hierarchy refinement step has been done successfully. Total time  "
						+ elapsedTime + " ms. \n");

		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - beforeUsedMem;
		StatisticTest.result.put("MU_OM_ClassAcyc", String.valueOf(actualMemUsed));
		System.gc();

		return ontM;
	}

	public static HModel ClassAcyclicityOld(HModel ontM) {
		long startTime = System.currentTimeMillis();
		OWLOntology Om = ontM.getOwlModel();
		Set<OWLAxiom> res = new HashSet<OWLAxiom>();
		ArrayList<CycleSt> tempParent;

		OWLOntologyManager manager = ontM.getManager();
		OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(Om));
		boolean findCycle = false;
		Iterator<OWLClass> cl = Om.getClassesInSignature().iterator();

		while (cl.hasNext()) {
			tempParent = new ArrayList<CycleSt>();
			OWLClass c = cl.next();
			CycleSt e = new CycleSt();
			e.SetObject(c);
			e.SetValue(false);
			tempParent.add(e);
			findCycle = false;

			for (int i = 0; i < tempParent.size(); i++) {
				if (findCycle == true) {
					break;
				} else {

					OWLClass key = tempParent.get(i).getObject();
					Boolean value = tempParent.get(i).getValue();

					if (value == false) {
						tempParent.get(i).SetValue(true);
						Iterator<OWLSubClassOfAxiom> list = Om.getSubClassAxiomsForSuperClass(key).iterator();
						while (list.hasNext()) {
							OWLSubClassOfAxiom cc = list.next();
							OWLClassExpression R = cc.getSuperClass();
							OWLClassExpression D = cc.getSubClass();
							if (c.equals((OWLClass) D)) {
								// means cycle
								// TODO: self cycle
								findCycle = true;
								res.add(cc);
								manager.removeAxiom(Om, cc);
								break;
							}
							if (existinList(tempParent, (OWLClass) D) == false) {
								// TODO: maybe here if it is true means cycle?
								CycleSt ee = new CycleSt();
								ee.SetObject((OWLClass) D);
								ee.SetValue(false);
								tempParent.add(ee);
								// TODO: Check it later
								if (D.containsEntityInSignature(c)) {
									res.add(cc);
									manager.removeAxiom(Om, cc);
								}
							}
						}
					}
				}
			}
		}

		manager.applyChanges(remover.getChanges());
		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		String msg = "";
		if (res.size() > 0) {
			msg = "To apply acyclicity in the class hirearchy refinement, the below axioms have been deleted: \n";
			Iterator<OWLAxiom> iter = res.iterator();
			while (iter.hasNext()) {
				OWLAxiom ax = iter.next();
				msg = msg + ax.toString().replace("<", "[").replace(">", "]");
			}
		} else {
			msg = "There was no cycle in the class hierarchy in the merged ontology. \n";
		}
		MyLogging.log(Level.INFO, msg);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Acyclicity in the class hierarchy refinement step has been done successfully. Total time  "
						+ elapsedTime + " ms. \n");
		return ontM;

	}

	// *********************************************************************************************************
	public static HModel PropertyAcyclicity(HModel ontM) {
		// it detects only self cycle
		long startTime = System.currentTimeMillis();
		Set<OWLAxiom> res = new HashSet<OWLAxiom>();
		OWLOntologyManager manager = ontM.getManager();
		OWLOntology Om = ontM.getOwlModel();
		OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(Om));

		Iterator<OWLSubObjectPropertyOfAxiom> axObjPro = ontM.getOwlModel().getAxioms(AxiomType.SUB_OBJECT_PROPERTY)
				.iterator();
		while (axObjPro.hasNext()) {
			OWLSubObjectPropertyOfAxiom axiom = axObjPro.next();

			OWLObjectPropertyExpression SuperPro = ((OWLSubObjectPropertyOfAxiom) axiom).getSubProperty();
			OWLObjectPropertyExpression SubPro = ((OWLSubObjectPropertyOfAxiom) axiom).getSuperProperty();
			if (SuperPro.equals(SubPro)) {
				manager.removeAxiom(Om, axiom);
				res.add(axiom);
				int r = ontM.getRefineActionOnMerge();
				ontM.setRefineActionOnMerge(r + 1);
			}
		}

		Iterator<OWLSubDataPropertyOfAxiom> axDpro = ontM.getOwlModel().getAxioms(AxiomType.SUB_DATA_PROPERTY)
				.iterator();
		while (axDpro.hasNext()) {
			OWLSubDataPropertyOfAxiom axiom = axDpro.next();

			OWLDataPropertyExpression SuperPro = ((OWLSubDataPropertyOfAxiom) axiom).getSubProperty();
			OWLDataPropertyExpression SubPro = ((OWLSubDataPropertyOfAxiom) axiom).getSuperProperty();
			if (SuperPro.equals(SubPro)) {
				manager.removeAxiom(Om, axiom);
				res.add(axiom);
				int r = ontM.getRefineActionOnMerge();
				ontM.setRefineActionOnMerge(r + 1);
			}
		}

		// or:
		// cm.accept(remover);
		manager.applyChanges(remover.getChanges());
		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		String msg = "";
		if (res.size() > 0) {
			msg = "To apply acyclicity in the property hirearchy refinement, the below axioms have been deleted: \n";
			Iterator<OWLAxiom> iter = res.iterator();
			while (iter.hasNext()) {
				OWLAxiom ax = iter.next();
				msg = msg + ax.toString().replace("<", "[").replace(">", "]");
			}
		} else {
			msg = "There was no cycle in the property hierarchy in the merged ontology.\n";
		}
		MyLogging.log(Level.INFO, msg);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Acyclicity in the property hierarchy refinement step has been done successfully. Total time  "
						+ elapsedTime + " ms. \n");
		return ontM;
	}
	// *********************************************************************************************************

	public static HModel InversePropertyProhibition(HModel ontM) {
		long startTime = System.currentTimeMillis();
		Set<OWLAxiom> res = new HashSet<OWLAxiom>();
		OWLOntologyManager manager = ontM.getManager();
		OWLOntology Om = ontM.getOwlModel();
		OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(Om));
		Iterator<OWLInverseObjectPropertiesAxiom> ax = ontM.getOwlModel().getAxioms(AxiomType.INVERSE_OBJECT_PROPERTIES)
				.iterator();
		while (ax.hasNext()) {
			OWLInverseObjectPropertiesAxiom axiom = ax.next();
			OWLObjectPropertyExpression p1 = axiom.getFirstProperty();
			OWLObjectPropertyExpression p2 = axiom.getSecondProperty();

			if (p1.equals(p2)) {
				manager.removeAxiom(Om, axiom);
				res.add(axiom);
				int r = ontM.getRefineActionOnMerge();
				ontM.setRefineActionOnMerge(r + 1);
			}
		}
		// cm.accept(remover);
		manager.applyChanges(remover.getChanges());
		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		String msg = "";
		if (res.size() > 0) {
			msg = "To apply prohibition of properties being inverses of themselves refinement, the below axioms have been deleted: \n";
			Iterator<OWLAxiom> iter = res.iterator();
			while (iter.hasNext()) {
				OWLAxiom axx = iter.next();
				msg = msg + axx.toString().replace("<", "[").replace(">", "]");
			}
		} else {
			msg = "There was no property with inverse relation of themselves in the merged ontology. \n";
		}
		MyLogging.log(Level.INFO, msg);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Prohibition of properties being inverse of themselves refinement step has been done successfully. Total time  "
						+ elapsedTime + " ms. \n");
		return ontM;
	}

	// *********************************************************************************************************

	public static HModel UnconnectedClassProhibition(HModel ontM) {
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();

		ArrayList<OWLClass> res = new ArrayList<OWLClass>();
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		String changes = "";
		boolean connected = true;
		Iterator<OWLClass> lis = Om.getClassesInSignature().iterator();
		while (lis.hasNext()) {
			OWLClass c = lis.next();
			connected = false;
			if (c.getSubClasses(Om).size() == 0 && c.getSuperClasses(Om).size() == 0) {
				// if this class has some connection in the input ontologies, we
				// also here connect it, but if this class even in the input
				// ontologies has no connections, it means this is the problem
				// of the input ontologies modeling, and we skip it here.
				//
				Set<OWLClassExpression> Subs = null;
				for (int i = 0; i < ontM.getInputOntNumber(); i++) {
					OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
					if (c.getSubClasses(Oi).size() != 0) {
						Subs = c.getSubClasses(Oi);
						break;
					}
				}
				if (Subs == null) {
					Set<OWLClass> eqC = findEqClassinOi(ontM, c);
					if (eqC != null) {
						Iterator<OWLClass> eqCIter = eqC.iterator();
						while (eqCIter.hasNext()) {
							OWLClass eq = eqCIter.next();
							for (int i = 0; i < ontM.getInputOntNumber(); i++) {
								OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
								if (eq.getSubClasses(Oi).size() != 0) {
									Subs = eq.getSubClasses(Oi);
									break;
								}
							}
						}
					}
				}
				if (Subs != null) {
					Iterator<OWLClassExpression> sub = Subs.iterator();
					OWLClassExpression childinOm = null;
					while (sub.hasNext()) {
						childinOm = sub.next();
						if (ontM.getKeyValueEqClass().get(childinOm) != null) {
							childinOm = ontM.getKeyValueEqClass().get(childinOm);
						}
						if (Om.getClassesInSignature().contains(childinOm)) {
							break;// select the first one and return6
						}
					}
					if (childinOm != null) {
						OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(childinOm, c);
						manager.addAxiom(Om, newAxiom);
						connected = true;
						changes = changes + "The class: " + c.toString().replace("<", "[").replace(">", "]")
								+ " has been connected to the respective subclass from the input ontology. \n";
						int r = ontM.getRefineActionOnMerge();
						ontM.setRefineActionOnMerge(r + 1);
					} else {
						// it means none of children of this class do not
						// exist in the merged ontology. so we should
						// connected it to the root
						OWLClass root = new OWLClassImpl(IRI.create(OWL.Thing.getURI()));
						// TODO: get the root of Om , do not create a new
						// root
						OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(c, root);
						manager.addAxiom(Om, newAxiom);
						connected = true;
						changes = changes + "The class: " + c.toString().replace("<", "[").replace(">", "]")
								+ " has been connected to root of ontology,the respective subclass from the input ontology. \n";
						int r = ontM.getRefineActionOnMerge();
						ontM.setRefineActionOnMerge(r + 1);
					}
				} else if (!connected) {
					// do for parent
					Set<OWLClassExpression> Supers = null;
					for (int i = 0; i < ontM.getInputOntNumber(); i++) {
						OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
						if (c.getSuperClasses(Oi).size() != 0) {
							Supers = c.getSuperClasses(Oi);
							break;
						}
					}
					if (Supers == null) {
						Set<OWLClass> eqC = findEqClassinOi(ontM, c);
						if (eqC != null) {
							Iterator<OWLClass> eqCIter = eqC.iterator();
							while (eqCIter.hasNext()) {
								OWLClass eq = eqCIter.next();
								for (int i = 0; i < ontM.getInputOntNumber(); i++) {
									OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
									if (eq.getSubClasses(Oi).size() != 0) {
										Supers = eq.getSubClasses(Oi);
										break;
									}
								}
							}
						}
					}
					if (Supers != null) {
						Iterator<OWLClassExpression> sup = Supers.iterator();
						OWLClassExpression fatherinOm = null;
						while (sup.hasNext()) {
							fatherinOm = sup.next();
							if (ontM.getKeyValueEqClass().get(fatherinOm) != null) {
								fatherinOm = ontM.getKeyValueEqClass().get(fatherinOm);
							}
							if (Om.getClassesInSignature().contains(fatherinOm)) {
								break;// select the first one and return6
							}
						}
						if (fatherinOm != null) {
							OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(c, fatherinOm);
							manager.addAxiom(Om, newAxiom);
							connected = true;
							changes = changes + "The class: " + c.toString().replace("<", "[").replace(">", "]")
									+ " has been connected to the respective superclass from the input ontology. \n";
							int r = ontM.getRefineActionOnMerge();
							ontM.setRefineActionOnMerge(r + 1);
						} else {
							// it means none of children of this class do not
							// exist in the merged ontology. so we should
							// connected it to the root
							OWLClass root = new OWLClassImpl(IRI.create(OWL.Thing.getURI()));
							// TODO: get the root of Om , do not create a new
							// root
							OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(c, root);
							manager.addAxiom(Om, newAxiom);
							connected = true;
							changes = changes + "The class: " + c.toString().replace("<", "[").replace(">", "]")
									+ " has been connected to root of ontology. \n";
							int r = ontM.getRefineActionOnMerge();
							ontM.setRefineActionOnMerge(r + 1);
						}
					} else {
						OWLClass root = new OWLClassImpl(IRI.create(OWL.Thing.getURI()));
						// TODO: get the root of Om , do not create a new
						// root
						OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(c, root);
						manager.addAxiom(Om, newAxiom);
						connected = true;
						changes = changes + "The class: " + c.toString().replace("<", "[").replace(">", "]")
								+ " has been connected to root of ontology. \n";
						int r = ontM.getRefineActionOnMerge();
						ontM.setRefineActionOnMerge(r + 1);

						// changes = changes + "The class: " +
						// c.toString().replace("<", "[").replace(">", "]")
						// + " does not have any connections in the input
						// ontology, that's why it is also unconnected in the
						// merged ontology. \n";
					}
				}
			}
		}

		if (changes.length() < 0)
			changes = "There was no unconnected class in the merged ontology.\n";

		MyLogging.log(Level.INFO, changes);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Unconnected class prohibition refinement step has been done successfully. Total time  " + elapsedTime
						+ " ms. \n");

		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - beforeUsedMem;
		StatisticTest.result.put("MU_OM_ClasssUncon", String.valueOf(actualMemUsed));
		// System.gc();
		return ontM;
	}
	// *********************************************************************************************************

	public static HModel UnconnectedPropertyProhibition(HModel ontM) {
		long startTime = System.currentTimeMillis();
		// TODO correct it
		// every property that had some connection before the merging, should
		// have some connection after
		// Properties should not be unconnected- means it should be connected to
		// atleast one class of the ontology. Similar to the classes.

		// ArrayList<OWLClass> res = new ArrayList<OWLClass>();
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		String changes = "";
		OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(Om));
		// For Objectproperties
		Iterator<OWLObjectProperty> lis = Om.getObjectPropertiesInSignature().iterator();
		while (lis.hasNext()) {
			OWLObjectProperty p = lis.next();
			if (p.getDomains(Om).size() == 0 && p.getRanges(Om).size() == 0 && p.getSubProperties(Om).size() == 0
					&& p.getObjectPropertiesInSignature().size() == 0 && p.getDataPropertiesInSignature().size() == 0) {
				// it means this property does not have any class, domain, range
				// etc. it means it is unconnected

				// delete it:
				p.accept(remover);
				changes = "The property: " + p.toString().replace("<", "[").replace(">", "]")
						+ " has been deleted, beacsue it does not have any connections. \n";
				int r = ontM.getRefineActionOnMerge();
				ontM.setRefineActionOnMerge(r + 1);

			}
		}

		// For datatypeProperties
		Iterator<OWLDataProperty> listDpro = Om.getDataPropertiesInSignature().iterator();
		while (listDpro.hasNext()) {
			OWLDataProperty p = listDpro.next();
			if (p.getDomains(Om).size() == 0 && p.getRanges(Om).size() == 0 && p.getSubProperties(Om).size() == 0
					&& p.getObjectPropertiesInSignature().size() == 0 && p.getDataPropertiesInSignature().size() == 0) {
				// it means this property does not have any class, domain, range
				// etc. it means it is unconnected

				// delete it:
				p.accept(remover);
				changes = "The property: " + p.toString().replace("<", "[").replace(">", "]")
						+ " has been deleted, beacsue it does not have any connections. \n";
				int r = ontM.getRefineActionOnMerge();
				ontM.setRefineActionOnMerge(r + 1);
			}
		}

		manager.applyChanges(remover.getChanges());

		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		if (changes.equals(""))
			changes = "There was no unconnected property in the merged ontology.\n";

		MyLogging.log(Level.INFO, changes);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Unconnected property prohibition refinement step has been done successfully. Total time  "
						+ elapsedTime + " ms. \n");
		return ontM;
	}

	public static HModel NullDomainRangeProhinition(HModel ontM) { // DanglingReferenceProhibition
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		Iterator<OWLObjectProperty> iter = Om.getObjectPropertiesInSignature().iterator();
		while (iter.hasNext()) {
			OWLObjectProperty obj = iter.next();
			Set<OWLClassExpression> domain = obj.getDomains(Om);
			if (domain.size() == 0) {
				Set<OWLClassExpression> domOi = findDomaininOi(obj, ontM);
				if (domOi != null && domOi.size() > 1) {
					// it means the domain of proerty exist in Oi but does not
					// exist in Om
					// TODO: should we force this function to add missed
					// classes? or we should conncet them to root or other
					// parent of the missed class
					// TODO correct it do not need to add domain into om
					// first add domOi in Om
					OWLClassExpression newDomain = domOi.iterator().next();

					// add domain to Om
					OWLOntology Oi = findOiofDomain(ontM, newDomain);
					if (Oi != null) {
						Set<OWLClassExpression> superC = newDomain.asOWLClass().getSuperClasses(Oi);
						if (superC != null) {
							// select the first parent
							OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(newDomain, superC.iterator().next());
							manager.addAxiom(Om, newAxiom);
							int r = ontM.getRefineActionOnMerge();
							ontM.setRefineActionOnMerge(r + 1);
						} else {
							Set<OWLClassExpression> subC = newDomain.asOWLClass().getSubClasses(Oi);
							if (subC != null) {
								// select the first child
								OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(superC.iterator().next(), newDomain);
								manager.addAxiom(Om, newAxiom);
								int r = ontM.getRefineActionOnMerge();
								ontM.setRefineActionOnMerge(r + 1);
							} else {
								// if there is no sub or superclass, add c
								// to root
								OWLClass root = factory.getOWLThing();
								OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(newDomain, root);
								manager.addAxiom(Om, newAxiom);
								int r = ontM.getRefineActionOnMerge();
								ontM.setRefineActionOnMerge(r + 1);
							}
						}
					} else {
						// add to root
						OWLClass root = factory.getOWLThing();
						OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(newDomain, root);
						manager.addAxiom(Om, newAxiom);
						int r = ontM.getRefineActionOnMerge();
						ontM.setRefineActionOnMerge(r + 1);
					}
					// finish adding c to Om

					// then add this property to domOi(the first domain)
					OWLAxiom newAxiom = factory.getOWLObjectPropertyDomainAxiom(obj, newDomain);
					manager.addAxiom(Om, newAxiom);
					int r = ontM.getRefineActionOnMerge();
					ontM.setRefineActionOnMerge(r + 1);
				} else {
					// create new class and add this obj to this new class
					OWLClass cNew = factory
							.getOWLClass(IRI.create("http://merged#ExtraClasses_" + obj.getIRI().getShortForm()));
					OWLAxiom newAxiom = factory.getOWLObjectPropertyDomainAxiom(obj, cNew);
					manager.addAxiom(Om, newAxiom);
					int r = ontM.getRefineActionOnMerge();
					ontM.setRefineActionOnMerge(r + 1);
				}
			}
			// do all this process for range

			Set<OWLClassExpression> range = obj.getRanges(Om);
			if (range.size() == 0) {
				Set<OWLClassExpression> rangeOi = findRangeinOi(obj, ontM);
				if (rangeOi != null && rangeOi.size() > 1) {
					// first add domOi in Om
					OWLClassExpression newRange = rangeOi.iterator().next();
					// add range to Om
					OWLOntology Oi = findOiofDomain(ontM, newRange);
					if (Oi != null) {
						Set<OWLClassExpression> superC = newRange.asOWLClass().getSuperClasses(Oi);
						if (superC != null) {
							// select the first parent
							OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(newRange, superC.iterator().next());
							manager.addAxiom(Om, newAxiom);
							int r = ontM.getRefineActionOnMerge();
							ontM.setRefineActionOnMerge(r + 1);
						} else {
							Set<OWLClassExpression> subC = newRange.asOWLClass().getSubClasses(Oi);
							if (subC != null) {
								// select the first child
								OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(superC.iterator().next(), newRange);
								manager.addAxiom(Om, newAxiom);
								int r = ontM.getRefineActionOnMerge();
								ontM.setRefineActionOnMerge(r + 1);
							} else {
								// if there is no sub or superclass, add c
								// to root
								OWLClass root = factory.getOWLThing();
								OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(newRange, root);
								manager.addAxiom(Om, newAxiom);
								int r = ontM.getRefineActionOnMerge();
								ontM.setRefineActionOnMerge(r + 1);
							}
						}
					} else {
						// add to root
						OWLClass root = factory.getOWLThing();
						OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(newRange, root);
						manager.addAxiom(Om, newAxiom);
						int r = ontM.getRefineActionOnMerge();
						ontM.setRefineActionOnMerge(r + 1);
					}
					// finish adding c to Om

					// then add this property to domOi(the first range)
					OWLAxiom newAxiom = factory.getOWLObjectPropertyRangeAxiom(obj, newRange);
					manager.addAxiom(Om, newAxiom);
					int r = ontM.getRefineActionOnMerge();
					ontM.setRefineActionOnMerge(r + 1);
				} else {
					// create new class and add this obj to this new class
					OWLClass cNew = factory
							.getOWLClass(IRI.create("http://merged#ExtraClasses_" + obj.getIRI().getShortForm()));
					OWLAxiom newAxiom = factory.getOWLObjectPropertyRangeAxiom(obj, cNew);
					manager.addAxiom(Om, newAxiom);
					int r = ontM.getRefineActionOnMerge();
					ontM.setRefineActionOnMerge(r + 1);
				}
			}
		}

		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);
		return ontM;
	}

	// *********************************************************************************************************

	public static HModel EntailmentSatisfaction(HModel ontM) {
		long startTime = System.currentTimeMillis();
		OWLReasonerFactory reasonerFactory = new PelletReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontM.getOwlModel());

		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();

		String changesLog = "";
		boolean inconsistentOM = false;

		ArrayList<OWLOntology> AllOi = ontM.getInputOwlOntModel();
		for (OWLOntology Oi : AllOi) {
			if (inconsistentOM == true)
				break;
			/* Subsumption entailment */
			Set<OWLSubClassOfAxiom> subsumptions = Oi.getAxioms(AxiomType.SUBCLASS_OF);
			for (OWLAxiom pos : subsumptions) {
				OWLAxiom eqAx = ontM.getEqAxioms().get(pos);
				if (eqAx != null)
					pos = eqAx;

				try {
					Assert.assertTrue(reasoner.isEntailed(pos));
					// trueAnsSubsumption++;
					/*
					 * assertTrue: Asserts that a condition is true. If it isn't
					 * it throws an AssertionError without a message.
					 */

				} catch (AssertionError e) {
					// falseAnsSubsumption++;
					// add pos to om
					manager.addAxiom(Om, pos);
					int r = ontM.getRefineActionOnMerge();
					ontM.setRefineActionOnMerge(r + 1);
					changesLog = changesLog + "\n Add the missing entilment: "
							+ pos.toString().replaceAll("<", "[").replaceAll(">", "]") + "in the merged ontology";

				} catch (InconsistentOntologyException e) {
					inconsistentOM = true;
					changesLog = changesLog
							+ "The ontology is inconsistent, and the entailment test faces the InconsistentOntologyException. So this cannot be checked";
					break;
				}
			}

			/* Equivalnce Entailment */
			Set<OWLEquivalentClassesAxiom> equivalenceClass = Oi.getAxioms(AxiomType.EQUIVALENT_CLASSES);
			for (OWLAxiom pos : equivalenceClass) {
				if (inconsistentOM == true)
					break;

				OWLAxiom eqAx = ontM.getEqAxioms().get(pos);
				if (eqAx != null)
					pos = eqAx;

				try {
					Assert.assertTrue(reasoner.isEntailed(pos));
					// trueAnsEquivalence++;
					/*
					 * assertTrue: Asserts that a condition is true. If it isn't
					 * it throws an AssertionError without a message.
					 */

				} catch (AssertionError e) {

					manager.addAxiom(Om, pos);
					int r = ontM.getRefineActionOnMerge();
					ontM.setRefineActionOnMerge(r + 1);
					changesLog = changesLog + "\n Add the missing entilment: "
							+ pos.toString().replaceAll("<", "[").replaceAll(">", "]") + "in the merged ontology";
				} catch (InconsistentOntologyException e) {

					changesLog = changesLog
							+ "The ontology is inconsistent, and the entailment test faces the InconsistentOntologyException. So this cannot be checked";
					inconsistentOM = true;
					break;
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
					// trueAnsEquivalence++;
					/*
					 * assertTrue: Asserts that a condition is true. If it isn't
					 * it throws an AssertionError without a message.
					 */

				} catch (AssertionError e) {

					// add pos to om
					manager.addAxiom(Om, pos);
					int r = ontM.getRefineActionOnMerge();
					ontM.setRefineActionOnMerge(r + 1);
					changesLog = changesLog + "\n Add the missing entilment: "
							+ pos.toString().replaceAll("<", "[").replaceAll(">", "]") + "in the merged ontology";

				} catch (InconsistentOntologyException e) {

					changesLog = changesLog
							+ "The ontology is inconsistent, and the entailment test faces the InconsistentOntologyException. So this cannot be checked";
					break;
				}
			}
			Set<OWLEquivalentDataPropertiesAxiom> equivalenceDpro = Oi.getAxioms(AxiomType.EQUIVALENT_DATA_PROPERTIES);
			for (OWLAxiom pos : equivalenceDpro) {
				OWLAxiom eqAx = ontM.getEqAxioms().get(pos);
				if (eqAx != null)
					pos = eqAx;

				try {
					Assert.assertTrue(reasoner.isEntailed(pos));
					// trueAnsEquivalence++;
					/*
					 * assertTrue: Asserts that a condition is true. If it isn't
					 * it throws an AssertionError without a message.
					 */

				} catch (AssertionError e) {

					manager.addAxiom(Om, pos);
					int r = ontM.getRefineActionOnMerge();
					ontM.setRefineActionOnMerge(r + 1);
					changesLog = changesLog + "\n Add the missing entilment: "
							+ pos.toString().replaceAll("<", "[").replaceAll(">", "]") + "in the merged ontology";
				}
			}
		}

		// Assert.assertEquals(numberOfUnifiers, actualNumberOfUnifiers);
		reasoner.dispose();

		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		if (changesLog.length() > 1) {
			MyLogging.log(Level.INFO, changesLog);
		} else {
			MyLogging.log(Level.INFO, "No changes has been done in the entailment preservation step!\n");
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "Entailment deduction refinement step has been done successfully. Total time  "
				+ elapsedTime + " ms. \n");
		return ontM;
	}
	// *********************************************************************************************************

	public static HModel OneTypeRestriction(HModel ontM) {
		// We should check the value of the datatypeProperty: but, only for the
		// corresponding entities. if they have different value, this conflict
		// should be solved. To solve it, we can select those value based on the
		// preferred ontology. But if the ontologies are equal, then?

		long startTime = System.currentTimeMillis();

		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		ArrayList<HMappedDpro> OmEqDataPro = ontM.getEqDataProperties();

		Iterator<OWLDataProperty> iterr = ontM.getOwlModel().getDataPropertiesInSignature().iterator();
		while (iterr.hasNext()) {
			OWLDataProperty dp = iterr.next();
			Set<OWLDatatype> dataType = dp.getDatatypesInSignature();
		}
		// TODO:error :How to get type of datatypeproperties in OWL?
		for (int j = 0; j < OmEqDataPro.size(); j++) {
			OWLDataProperty Dpro = OmEqDataPro.get(j).getRefDpro();
			Set<OWLDatatype> dataTypeRef = Dpro.getDatatypesInSignature();

			Set<OWLDataRange> dataRangeRef = Dpro.getRanges(Om);
			Set<OWLClassExpression> dataDomainRef = Dpro.getDomains(Om);

			Iterator<OWLDataProperty> iter = OmEqDataPro.get(j).getMappedDpro().iterator();
			while (iter.hasNext()) {
				OWLDataProperty eqDpro = iter.next();
				// check for datatype
				Set<OWLDatatype> dataType = eqDpro.getDatatypesInSignature();
				if (!dataType.containsAll(dataTypeRef)) {
					// It means the type of datatypeProperty is different
					// then we only should keep the value of one of them.
					String msg = "The dataype: " + " of datatypeProperty: "
							+ Dpro.toString().replace("<", "[").replace(">", "]")
							+ " is skiped, and only the datatype: " + " of the reference equal entity is keeped.\n";
					MyLogging.log(Level.WARNING, msg);
				}

				// check for data range
				Set<OWLDataRange> dataRange = null;
				for (int i = 0; i < ontM.getInputOntNumber(); i++) {
					OWLOntology oi = ontM.getInputOwlOntModel().get(i);
					dataRange = eqDpro.getRanges(oi);
					if (dataRange != null && dataRange.size() > 0) {
						break;
						// it means the property is in oi, no need to search in
						// other oi
					}
				}

				// Set<OWLClassExpression> dataDomain = eqDpro.getDomains(Om);

				if (!dataRange.containsAll(dataRangeRef)) {
					// It means the type of datatypeProperty is different
					// then we only should keep the value of one of them.
					String msg = "The dataype: " + " of datatypeProperty: "
							+ Dpro.toString().replace("<", "[").replace(">", "]")
							+ " is skiped, and only the datatype: " + " of the reference equal entity is keeped.\n";
					MyLogging.log(Level.WARNING, msg);
				}

			}
		}

		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "One type restriction refinement step has been done successfully. Total time  "
				+ elapsedTime + " ms. \n");
		return ontM;
	}
	// *********************************************************************************************************

	public static HModel ValueConstraint(HModel ontM) {
		// TODO correct it
		long startTime = System.currentTimeMillis();
		String changes = "";
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			// Iterator<OWLAxiom> axIter =
			// ontM.getInputOwlOntModel().get(i).getAxioms().iterator();
			Iterator<OWLSubClassOfAxiom> axIter = ontM.getInputOwlOntModel().get(i).getAxioms(AxiomType.SUBCLASS_OF)
					.iterator();
			while (axIter.hasNext()) {
				OWLAxiom axx = axIter.next();
				// if (axx.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
				OWLClassExpression SuperClassOi = ((OWLSubClassOfAxiom) axx).getSuperClass();
				OWLClassExpression SubClassOi = ((OWLSubClassOfAxiom) axx).getSubClass();
				if (SuperClassOi instanceof OWLDataExactCardinality) {
					int sOi = ((OWLDataExactCardinality) SuperClassOi).getCardinality();
					OWLClassExpression SubclassOm = ontM.getKeyValueEqClass().get(SubClassOi);
					if (SubclassOm == null)
						SubclassOm = SubClassOi;
					Iterator<OWLAxiom> omAxIter = ontM.getOwlModel().getReferencingAxioms((OWLEntity) SubclassOm)
							.iterator();
					while (omAxIter.hasNext()) {
						OWLAxiom x = omAxIter.next();
						if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
							OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) axx).getSuperClass();
							OWLClassExpression SubClassOm = ((OWLSubClassOfAxiom) axx).getSubClass();
							if (SuperClassOm instanceof OWLDataExactCardinality) {
								int sOm = ((OWLDataExactCardinality) SuperClassOm).getCardinality();
								if ((SubClassOi.equals(SubClassOm) || ShareMergeFunction
										.getMappedClassesforClass(ontM, SubClassOm).contains(SubClassOi))
										&& (sOi != sOm)) {
									changes = changes + "The OWLDataExactCardinality for property: "
											+ SuperClassOm.toString().replace("<", "[").replace(">", "]")
											+ " in the merged ontology was different with the input ontology. So, it sets to the input ontology value. \n";
									// sOm should be set as sOi, but if Oi
									// is the preference one!TODO:check it
									OWLDataExactCardinality cardi = factory.getOWLDataExactCardinality(sOi,
											(OWLDataPropertyExpression) SubClassOm);
									OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(SubClassOi, cardi);
									manager.addAxiom(Om, newAxiom);
									manager.removeAxiom(Om, x);
									int r = ontM.getRefineActionOnMerge();
									ontM.setRefineActionOnMerge(r + 1);
								}
							}
						}
					}
				} else if (SuperClassOi instanceof OWLObjectExactCardinality) {
					int sOi = ((OWLObjectExactCardinality) SuperClassOi).getCardinality();
					OWLClassExpression SubclassOm = ontM.getKeyValueEqClass().get(SubClassOi);
					if (SubclassOm == null)
						SubclassOm = SubClassOi;
					Iterator<OWLAxiom> omAxIter = ontM.getOwlModel().getReferencingAxioms((OWLEntity) SubclassOm)
							.iterator();
					while (omAxIter.hasNext()) {
						OWLAxiom x = omAxIter.next();
						if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
							OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) axx).getSuperClass();
							OWLClassExpression SubClassOm = ((OWLSubClassOfAxiom) axx).getSubClass();
							if (SuperClassOm instanceof OWLObjectExactCardinality) {
								int sOm = ((OWLObjectExactCardinality) SuperClassOm).getCardinality();
								if ((SubClassOi.equals(SubClassOm) || ShareMergeFunction
										.getMappedClassesforClass(ontM, SubClassOm).contains(SubClassOi))
										&& (sOi != sOm)) {
									changes = changes + "The OWLDataExactCardinality for property: "
											+ SuperClassOm.toString().replace("<", "[").replace(">", "]")
											+ " in the merged ontology was different with the input ontology. So, it sets to the input ontology value. \n";
									// sOm should be set as sOi, but if Oi
									// is the preference one!TODO:check it
									OWLObjectExactCardinality cardi = factory.getOWLObjectExactCardinality(sOi,
											(OWLObjectPropertyExpression) SubClassOm);
									OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(SubClassOi, cardi);
									manager.addAxiom(Om, newAxiom);
									manager.removeAxiom(Om, x);
									int r = ontM.getRefineActionOnMerge();
									ontM.setRefineActionOnMerge(r + 1);
								}
							}
						}
					}

				} else if (SuperClassOi instanceof OWLObjectMaxCardinality) {
					int sOi = ((OWLObjectMaxCardinality) SuperClassOi).getCardinality();
					OWLClassExpression SubclassOm = ontM.getKeyValueEqClass().get(SubClassOi);
					if (SubclassOm == null)
						SubclassOm = SubClassOi;
					Iterator<OWLAxiom> omAxIter = ontM.getOwlModel().getReferencingAxioms((OWLEntity) SubclassOm)
							.iterator();
					while (omAxIter.hasNext()) {
						OWLAxiom x = omAxIter.next();
						if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
							OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) axx).getSuperClass();
							OWLClassExpression SubClassOm = ((OWLSubClassOfAxiom) axx).getSubClass();
							if (SuperClassOm instanceof OWLObjectMaxCardinality) {
								int sOm = ((OWLObjectMaxCardinality) SuperClassOm).getCardinality();
								if ((SubClassOi.equals(SubClassOm) || ShareMergeFunction
										.getMappedClassesforClass(ontM, SubClassOm).contains(SubClassOi))
										&& (sOi != sOm)) {
									changes = changes + "The OWLDataExactCardinality for property: "
											+ SuperClassOm.toString().replace("<", "[").replace(">", "]")
											+ " in the merged ontology was different with the input ontology. So, it sets to the input ontology value. \n";
									// sOm should be set as sOi, but if Oi
									// is the preference one!TODO:check it
									OWLObjectMaxCardinality cardi = factory.getOWLObjectMaxCardinality(sOi,
											(OWLObjectPropertyExpression) SubClassOm);
									OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(SubClassOi, cardi);
									manager.addAxiom(Om, newAxiom);
									manager.removeAxiom(Om, x);
									int r = ontM.getRefineActionOnMerge();
									ontM.setRefineActionOnMerge(r + 1);
								}
							}
						}
					}
				} else if (SuperClassOi instanceof OWLDataMaxCardinality) {
					int sOi = ((OWLDataMaxCardinality) SuperClassOi).getCardinality();
					OWLClassExpression SubclassOm = ontM.getKeyValueEqClass().get(SubClassOi);
					if (SubclassOm == null)
						SubclassOm = SubClassOi;
					Iterator<OWLAxiom> omAxIter = ontM.getOwlModel().getReferencingAxioms((OWLEntity) SubclassOm)
							.iterator();
					while (omAxIter.hasNext()) {
						OWLAxiom x = omAxIter.next();
						if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
							OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) axx).getSuperClass();
							OWLClassExpression SubClassOm = ((OWLSubClassOfAxiom) axx).getSubClass();
							if (SuperClassOm instanceof OWLDataMaxCardinality) {
								int sOm = ((OWLDataMaxCardinality) SuperClassOm).getCardinality();
								if ((SubClassOi.equals(SubClassOm) || ShareMergeFunction
										.getMappedClassesforClass(ontM, SubClassOm).contains(SubClassOi))
										&& (sOi != sOm)) {
									changes = changes + "The OWLDataExactCardinality for property: "
											+ SuperClassOm.toString().replace("<", "[").replace(">", "]")
											+ " in the merged ontology was different with the input ontology. So, it sets to the input ontology value. \n";
									// sOm should be set as sOi, but if Oi
									// is the preference one!TODO:check it
									OWLDataMaxCardinality cardi = factory.getOWLDataMaxCardinality(sOi,
											(OWLDataPropertyExpression) SubClassOm);
									OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(SubClassOi, cardi);
									manager.addAxiom(Om, newAxiom);
									manager.removeAxiom(Om, x);
									int r = ontM.getRefineActionOnMerge();
									ontM.setRefineActionOnMerge(r + 1);
								}
							}
						}
					}
				} else if (SuperClassOi instanceof OWLDataMinCardinality) {
					int sOi = ((OWLDataMinCardinality) SuperClassOi).getCardinality();
					OWLClassExpression SubclassOm = ontM.getKeyValueEqClass().get(SubClassOi);
					if (SubclassOm == null)
						SubclassOm = SubClassOi;
					Iterator<OWLAxiom> omAxIter = ontM.getOwlModel().getReferencingAxioms((OWLEntity) SubclassOm)
							.iterator();
					while (omAxIter.hasNext()) {
						OWLAxiom x = omAxIter.next();
						if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
							OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) axx).getSuperClass();
							OWLClassExpression SubClassOm = ((OWLSubClassOfAxiom) axx).getSubClass();
							if (SuperClassOm instanceof OWLDataMinCardinality) {
								int sOm = ((OWLDataMinCardinality) SuperClassOm).getCardinality();
								if ((SubClassOi.equals(SubClassOm) || ShareMergeFunction
										.getMappedClassesforClass(ontM, SubClassOm).contains(SubClassOi))
										&& (sOi != sOm)) {
									changes = changes + "The OWLDataExactCardinality for property: "
											+ SuperClassOm.toString().replace("<", "[").replace(">", "]")
											+ " in the merged ontology was different with the input ontology. So, it sets to the input ontology value. \n";
									// sOm should be set as sOi, but if Oi
									// is the preference one!TODO:check it
									OWLDataMinCardinality cardi = factory.getOWLDataMinCardinality(sOi,
											(OWLDataPropertyExpression) SubClassOm);
									OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(SubClassOi, cardi);
									manager.addAxiom(Om, newAxiom);
									manager.removeAxiom(Om, x);
									int r = ontM.getRefineActionOnMerge();
									ontM.setRefineActionOnMerge(r + 1);
								}
							}
						}
					}
				} else if (SuperClassOi instanceof OWLObjectSomeValuesFrom) {
					// OWLObjectValuesFrom should be preserved same at the
					// merged Ontology. If not, we marked them as something
					// went already wrong for them!
					if (!ontM.getOwlModel().getAxioms().contains(axx)) {
						if (ontM.getEqAxioms().get(axx) == null) {
							changes = changes + "The OWLObjectSomeValuesFrom: "
									+ axx.toString().replace("<", "").replace("<", "")
									+ " restriction from the input ontology has not been preserved in the merged ontolgoy! \n ";
						}
					}
				} else if (SuperClassOi instanceof OWLObjectAllValuesFrom) {
					// OWLObjectValuesFrom should be preserved same at the
					// merged Ontology. If not, we marked them as something
					// went already wrong for them!
					if (!ontM.getOwlModel().getAxioms().contains(axx)) {
						if (ontM.getEqAxioms().get(axx) == null) {
							changes = changes + "The OWLObjectAllValuesFrom: "
									+ axx.toString().replace("<", "").replace("<", "")
									+ " restriction from the input ontology has not been preserved in the merged ontolgoy! \n ";
						}
					}
				}

				// }
			}
		}

		if (changes.equals(""))
			changes = "There was not any conflict on all cardinality restriction or OWLObjectSome-AllValuesFrom.";

		MyLogging.log(Level.INFO, changes);

		ontM.SetOwlModel(Om);
		ontM.SetManager(manager);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "Property value's constraint refinement step has been done successfully. Total time  "
				+ elapsedTime + " ms. \n");
		return ontM;
	}

	// *********************************************************************************************************
	public static boolean existinList(ArrayList<CycleSt> tempParent, OWLClass d) {
		for (int i = 0; i < tempParent.size(); i++) {
			if (tempParent.get(i).getObject().equals(d))
				return true;
		}
		return false;
	}

	// *********************************************************************************************************
	public static Set<OWLClassExpression> findDomaininOi(OWLObjectProperty obj, HModel ontM) {
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLObjectProperty> iter = Oi.getObjectPropertiesInSignature().iterator();
			while (iter.hasNext()) {
				OWLObjectProperty ob = iter.next();
				if (obj.equals(ob)) {
					Set<OWLClassExpression> domain = obj.getDomains(Oi);
					return domain;
				} else {
					Set<OWLObjectProperty> refObj = findRefereObject(ontM, obj);
					if (refObj.contains(obj)) {
						Set<OWLClassExpression> domain = obj.getDomains(Oi);
						return domain;
					}
				}
			}
		}
		return null;
	}

	// *********************************************************************************************************
	private static Set<OWLObjectProperty> findRefereObject(HModel ontM, OWLObjectProperty obj) {
		Set<OWLObjectProperty> res = new HashSet<OWLObjectProperty>();
		for (int i = 0; i < ontM.getEqObjProperties().size(); i++) {
			Set<OWLObjectProperty> a = ontM.getEqObjProperties().get(i).getMappedObj();
			if (a.contains(obj)) {
				if (!ontM.getEqObjProperties().get(i).getRefObj().equals(obj)) {
					res.add(ontM.getEqObjProperties().get(i).getRefObj());
					res.addAll(a);
					return res;
				}
			} else if (ontM.getEqObjProperties().get(i).getMappedObj().equals(obj)) {
				res.add(ontM.getEqObjProperties().get(i).getRefObj());
				res.addAll(a);
				return res;
			}
		}
		return null;
	}

	// *********************************************************************************************************
	public static OWLOntology findOiofDomain(HModel ontM, OWLClassExpression newDomain) {
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			if (ontM.getInputOwlOntModel().get(i).getClassesInSignature().contains(newDomain))
				return ontM.getInputOwlOntModel().get(i);
		}
		return null;
	}

	// *********************************************************************************************************
	public static Set<OWLClassExpression> findRangeinOi(OWLObjectProperty obj, HModel ontM) {
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLObjectProperty> iter = Oi.getObjectPropertiesInSignature().iterator();
			while (iter.hasNext()) {
				OWLObjectProperty ob = iter.next();
				if (obj.equals(ob)) {
					Set<OWLClassExpression> range = obj.getRanges(Oi);
					return range;
				} else {
					Set<OWLObjectProperty> refObj = findRefereObject(ontM, obj);
					if (refObj.contains(obj)) {
						Set<OWLClassExpression> range = obj.getRanges(Oi);
						return range;
					}
				}
			}
		}
		return null;
	}

	// *********************************************************************************************************
	private static HashSet<OWLAxiom> getRelatedDomainAxioms(Set<OWLClassExpression> domain, OWLObjectProperty obj,
			OWLOntology om) {
		HashSet<OWLAxiom> res = new HashSet<OWLAxiom>();

		Iterator<OWLObjectPropertyDomainAxiom> itr = om.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN).iterator();
		while (itr.hasNext()) {
			OWLObjectPropertyDomainAxiom ax = itr.next();
			OWLObjectPropertyExpression pro = ((OWLObjectPropertyDomainAxiom) ax).getProperty();
			// OWLClassExpression dom = ((OWLObjectPropertyDomainAxiom)
			// ax).getDomain();
			if (pro.equals(obj)) {
				res.add(ax);
			}
		}
		return res;
	}

	// *********************************************************************************************************
	private static HashSet<OWLAxiom> getRelatedRangeAxioms(Set<OWLClassExpression> domain, OWLObjectProperty obj,
			OWLOntology om) {
		HashSet<OWLAxiom> res = new HashSet<OWLAxiom>();

		Iterator<OWLObjectPropertyRangeAxiom> itr = om.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE).iterator();
		while (itr.hasNext()) {
			OWLObjectPropertyRangeAxiom ax = itr.next();
			OWLObjectPropertyExpression pro = ((OWLObjectPropertyRangeAxiom) ax).getProperty();

			if (pro.equals(obj)) {
				res.add(ax);
			}
		}

		return res;
	}

	// *********************************************************************************************************
	private static Set<OWLClass> findEqClassinOi(HModel ontM, OWLClass c) {
		ArrayList<HMappedClass> arr = ontM.getEqClasses();
		for (int i = 0; i < arr.size(); i++) {
			HMappedClass a = arr.get(i);
			if (a.getMappedCalss().contains(c)) {
				return a.getMappedCalss();
			}
			if (a.getRefClass().equals(c)) {
				return a.getMappedCalss();
			}
		}
		return null;
	}

}
