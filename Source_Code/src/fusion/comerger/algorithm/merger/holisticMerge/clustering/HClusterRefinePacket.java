package fusion.comerger.algorithm.merger.holisticMerge.clustering;
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
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;
import org.semanticweb.owlapi.util.OWLEntityRemover;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.general.ShareMergeFunction;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedDpro;
import fusion.comerger.algorithm.merger.holisticMerge.merging.CycleSt;
import fusion.comerger.algorithm.merger.model.HModel;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;

public class HClusterRefinePacket {

	// *********************************************************************************************************
	public static HModel ClassesPreservation(HModel ontM) {
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();
		String changesLog = "";
		boolean addedFlag = false;

		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> listParent = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();
		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> listChild = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();
		OWLOntologyManager manager;

		OWLDataFactory factory;
		OWLOntology clusterOnt;

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

		// check is there any classes from Oi which is not in Om
		boolean flag = false;
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLClass> iterCl = Oi.getClassesInSignature().iterator();
			while (iterCl.hasNext()) {
				OWLClass c = iterCl.next();
				OWLClass eqC = ontM.getKeyValueEqClass().get(c);
				flag = false;
				for (int j = 0; j < ontM.getClusters().size(); j++) {
					if (ontM.getClusters().get(j).getClasses().contains(c)
							|| ontM.getClusters().get(j).getClasses().contains(eqC)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					// it means class c does not belong to any cluster. so add
					// it in the suitable one

					OWLClassExpression father = findFirstElement(listParent, c);
					if (father != null) {
						OWLClass refFather = ontM.getKeyValueEqClass().get(father);
						if (refFather != null)
							father = refFather;
						int clusterId = ClusterShareFunc.findClusterOfClass(father, ontM);
						if (clusterId != -1) {

							if (eqC != null)
								c = eqC;

							OWLClass fatherRef = ontM.getKeyValueEqClass().get(father);
							if (fatherRef != null)
								father = fatherRef;

							manager = ontM.getClusters().get(clusterId).getManager();
							factory = manager.getOWLDataFactory();
							OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(c, father);
							clusterOnt = ontM.getClusters().get(clusterId).getOntology();
							manager.addAxiom(clusterOnt, newAxiom);
							ontM.setAlterStatus(true);

							OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(c);
							if (!clusterOnt.getAxioms(AxiomType.DECLARATION).contains(ax))
								manager.addAxiom(clusterOnt, ax);

							changesLog = changesLog + "\n In the cluster" + clusterId + " , add the missing class: "
									+ c.toString().replace("<", "[").replace(">", "]")
									+ " to its respective superclass: "
									+ father.toString().replace("<", "[").replace(">", "]");
							addedFlag = true;
							ontM.getClusters().get(clusterId).setManager(manager);
							ontM.getClusters().get(clusterId).setOntology(clusterOnt);
							int r = ontM.getRefineActionOnCluster();
							ontM.setRefineActionOnCluster(r + 1);
						}
					} else {
						// add it to the child
						OWLClassExpression child = findFirstElement(listChild, c);
						if (child != null) {
							OWLClass refChild = ontM.getKeyValueEqClass().get(child);
							if (refChild != null)
								child = refChild;
							int clusterId = ClusterShareFunc.findClusterOfClass(child, ontM);
							if (clusterId != -1) {

								if (eqC != null)
									c = eqC;

								OWLClass childRef = ontM.getKeyValueEqClass().get(child);
								if (childRef != null)
									child = childRef;

								manager = ontM.getClusters().get(clusterId).getManager();
								factory = manager.getOWLDataFactory();
								OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(child, c);
								clusterOnt = ontM.getClusters().get(clusterId).getOntology();
								manager.addAxiom(clusterOnt, newAxiom);
								ontM.setAlterStatus(true);

								OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(c);
								if (!clusterOnt.getAxioms(AxiomType.DECLARATION).contains(ax))
									manager.addAxiom(clusterOnt, ax);

								changesLog = changesLog + "\n In the cluster" + clusterId + " , add the missing class: "
										+ c.toString().replace("<", "[").replace(">", "]")
										+ " to its respective subclass: "
										+ child.toString().replace("<", "[").replace(">", "]");
								addedFlag = true;
								ontM.getClusters().get(clusterId).setManager(manager);
								ontM.getClusters().get(clusterId).setOntology(clusterOnt);
								int r = ontM.getRefineActionOnCluster();
								ontM.setRefineActionOnCluster(r + 1);
							}
						}
					}
					if (!addedFlag) {
						// if there is no sub or superclass, add c to root or
						// announced it to the user
						String msg = "The class: " + c.toString().replace("<", "[").replace(">", "]")
								+ " could not be added to any clusters!. ";
						MyLogging.log(Level.WARNING, msg);
					}
				}
			}
		}

		if (changesLog.length() > 1) {
			MyLogging.log(Level.INFO, changesLog);
		} else {
			MyLogging.log(Level.INFO,
					"No changes has been done in the class preservation refinement step (after clustering phase)!\n");
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Class preservation refinement step (after clustering phase) has been done successfully. Total time  "
						+ elapsedTime + " ms. \n");

		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - beforeUsedMem;
		StatisticTest.result.put("MU_Cluster_ClassPreservation", String.valueOf(actualMemUsed));

		return ontM;
	}

	private static OWLClassExpression findFirstElement(HashMap<OWLClassExpression, HashSet<OWLClassExpression>> ilist,
			OWLClass c) {
		if (ilist == null)
			return null;

		if (ilist.get(c) != null) {
			Iterator<OWLClassExpression> a = ilist.get(c).iterator();
			if (a.hasNext())
				return a.next();
			// we return the first father/child
		}
		return null;
	}

	// *********************************************************************************************************
	public static HModel PropertiesPreservation(HModel ontM) {
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();
		String changesLog = "";
		boolean addedFlag = false;
		boolean flag = false;
		OWLOntologyManager manager;
		OWLDataFactory factory;
		OWLOntology clusterOnt;

		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			// Step 1: check all object properties
			Iterator<OWLObjectProperty> iterP = Oi.getObjectPropertiesInSignature().iterator();
			while (iterP.hasNext()) {
				OWLObjectProperty p = iterP.next();
				flag = false;
				for (int j = 0; j < ontM.getClusters().size(); j++) {
					OWLObjectProperty eqP = ontM.getKeyValueEqObjProperty().get(p);
					if (ontM.getClusters().get(j).getOntology().getObjectPropertiesInSignature().contains(p)
							|| ontM.getClusters().get(j).getOntology().getObjectPropertiesInSignature().contains(eqP)) {
						flag = true;
						break;
					}
				}

				if (!flag) {
					addedFlag = false;
					// if you find it, add it to Om right now
					Set<OWLClassExpression> domainSet = p.getDomains(Oi);
					if (domainSet.size() != 0) {
						// select the first domain and range
						Iterator<OWLClassExpression> domainIter = p.getDomains(Oi).iterator();
						OWLClassExpression domain = domainIter.next();
						// check domain exists in Om
						// find: this domain exist in which cluster
						if (domain instanceof OWLClassImpl) {
							OWLClass refDomain = ontM.getKeyValueEqClass().get(domain.asOWLClass());
							if (refDomain != null)
								domain = refDomain;
							int clusterId = ClusterShareFunc.findClusterOfClass(domain.asOWLClass(), ontM);
							if (clusterId != -1) {
								manager = ontM.getClusters().get(clusterId).getManager();
								factory = manager.getOWLDataFactory();
								clusterOnt = ontM.getClusters().get(clusterId).getOntology();

								OWLObjectProperty pRef = ontM.getKeyValueEqObjProperty().get(p);
								if (pRef != null)
									p = pRef;

								domain = ShareMergeFunction.getEqualOiClass(domain, ontM);

								OWLAxiom newAxiom1 = factory.getOWLObjectPropertyDomainAxiom(p, domain);
								manager.addAxiom(clusterOnt, newAxiom1);

								OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(p);
								if (!clusterOnt.getAxioms(AxiomType.DECLARATION).contains(ax))
									manager.addAxiom(clusterOnt, ax);

								changesLog = changesLog + "\n In the cluster" + clusterId
										+ " , add the missing ObjectProperty: "
										+ p.toString().replace("<", "[").replace(">", "]")
										+ " to its respective domain: "
										+ domain.toString().replace("<", "[").replace(">", "]");
								addedFlag = true;
								ontM.getClusters().get(clusterId).setManager(manager);
								ontM.getClusters().get(clusterId).setOntology(clusterOnt);
								int r = ontM.getRefineActionOnCluster();
								ontM.setRefineActionOnCluster(r + 1);
							}
						} else {
							// TODO: process it: it requires considering all
							// types! or- it only needs checking
							// ObjectUnionOF
							// case, not more!
							if (!addedFlag) {
								String msg = "The ObjectProperty: " + p.toString().replace("<", "[").replace(">", "]")
										+ "is missing! But the domain of this property is not OWLCLass type, so we skip it.\n";
								MyLogging.log(Level.WARNING, msg);
								addedFlag = true;
							}
						}
					}

					// check range exists in Om
					Set<OWLClassExpression> rangeSet = p.getRanges(Oi);
					if (rangeSet.size() != 0) {
						Iterator<OWLClassExpression> rangeIter = p.getRanges(Oi).iterator();
						OWLClassExpression range = rangeIter.next();
						if (range instanceof OWLClassImpl) {
							OWLClass refRange = ontM.getKeyValueEqClass().get(range.asOWLClass());
							if (refRange != null)
								range = refRange;
							int clusterId = ClusterShareFunc.findClusterOfClass(range.asOWLClass(), ontM);
							if (clusterId != -1) {
								manager = ontM.getClusters().get(clusterId).getManager();
								factory = manager.getOWLDataFactory();
								clusterOnt = ontM.getClusters().get(clusterId).getOntology();

								OWLObjectProperty pRef = ontM.getKeyValueEqObjProperty().get(p);
								if (pRef != null)
									p = pRef;

								range = ShareMergeFunction.getEqualOiClass(range, ontM);

								OWLAxiom newAxiom2 = factory.getOWLObjectPropertyRangeAxiom(p, range);
								manager.addAxiom(clusterOnt, newAxiom2);

								OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(p);
								if (!clusterOnt.getAxioms(AxiomType.DECLARATION).contains(ax))
									manager.addAxiom(clusterOnt, ax);

								changesLog = changesLog + "\n In the cluster" + clusterId
										+ " , add the missing ObjectProperty: "
										+ p.toString().replace("<", "[").replace(">", "]")
										+ " to its respective range: "
										+ range.toString().replace("<", "[").replace(">", "]");
								addedFlag = true;
								ontM.getClusters().get(clusterId).setManager(manager);
								ontM.getClusters().get(clusterId).setOntology(clusterOnt);
								int r = ontM.getRefineActionOnCluster();
								ontM.setRefineActionOnCluster(r + 1);

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
				flag = false;
				for (int j = 0; j < ontM.getClusters().size(); j++) {
					OWLObjectProperty eqP = ontM.getKeyValueEqObjProperty().get(p);
					if (ontM.getClusters().get(j).getOntology().getDataPropertiesInSignature().contains(p)
							|| ontM.getClusters().get(j).getOntology().getDataPropertiesInSignature().contains(eqP)) {
						flag = true;
						break;
					}
				}

				if (!flag) {
					addedFlag = false;
					// if you find it, add it to Om right now
					Iterator<OWLClassExpression> domainIter = p.getDomains(Oi).iterator();
					Iterator<OWLDataRange> rangeIter = p.getRanges(Oi).iterator();
					if (domainIter != null && domainIter.hasNext()) {
						// select the first domain and range
						OWLClassExpression domain = domainIter.next();
						// check domain exists in Om
						if (domain instanceof OWLClassImpl) {
							OWLClass refDomain = ontM.getKeyValueEqClass().get(domain.asOWLClass());
							if (refDomain != null) {
								domain = refDomain;
							}
							int clusterId = ClusterShareFunc.findClusterOfClass(domain.asOWLClass(), ontM);
							if (clusterId != -1) {
								manager = ontM.getClusters().get(clusterId).getManager();
								factory = manager.getOWLDataFactory();
								clusterOnt = ontM.getClusters().get(clusterId).getOntology();

								OWLDataProperty pRef = ontM.getKeyValueEqDataPro().get(p);
								if (pRef != null)
									p = pRef;

								domain = ShareMergeFunction.getEqualOiClass(domain, ontM);

								OWLAxiom newAxiom1 = factory.getOWLDataPropertyDomainAxiom(p, domain);
								manager.addAxiom(clusterOnt, newAxiom1);

								OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(p);
								if (!clusterOnt.getAxioms(AxiomType.DECLARATION).contains(ax))
									manager.addAxiom(clusterOnt, ax);

								changesLog = changesLog + "\n In the cluster" + clusterId
										+ " , add the missing DatatypeProperty: "
										+ p.toString().replace("<", "[").replace(">", "]")
										+ " to its respective domain: "
										+ domain.toString().replace("<", "[").replace(">", "]");
								addedFlag = true;
								ontM.getClusters().get(clusterId).setManager(manager);
								ontM.getClusters().get(clusterId).setOntology(clusterOnt);
								int r = ontM.getRefineActionOnCluster();
								ontM.setRefineActionOnCluster(r + 1);

							}
						} else {
							// TODO: process it: it requires considering all
							// types! or- it only needs checking
							// ObjectUnionOF
							// case, not more!
							if (!addedFlag) {
								String msg = "The DatatypeProperty: " + p.toString().replace("<", "[").replace(">", "]")
										+ "is missing! But the domain of this property is not OWLCLass type, so we skip it.\n";
								MyLogging.log(Level.WARNING, msg);
								addedFlag = true;
							}
						}
					}

					// check range exists in Om
					if (rangeIter != null && rangeIter.hasNext()) {
						OWLDataRange range = rangeIter.next();
						if (range instanceof OWLClassImpl) {
							OWLClass refRange = ontM.getKeyValueEqClass().get(((OWLClassImpl) range).asOWLClass());
							if (refRange != null)
								range = (OWLDataRange) refRange;
							int clusterId = ClusterShareFunc.findClusterOfClass(((OWLClassImpl) range).asOWLClass(),
									ontM);
							if (clusterId != -1) {
								manager = ontM.getClusters().get(clusterId).getManager();
								factory = manager.getOWLDataFactory();
								clusterOnt = ontM.getClusters().get(clusterId).getOntology();

								OWLDataProperty pRef = ontM.getKeyValueEqDataPro().get(p);
								if (pRef != null)
									p = pRef;

								OWLAxiom newAxiom2 = factory.getOWLDataPropertyRangeAxiom(p, range);
								manager.addAxiom(clusterOnt, newAxiom2);

								OWLDeclarationAxiom ax = factory.getOWLDeclarationAxiom(p);
								if (!clusterOnt.getAxioms(AxiomType.DECLARATION).contains(ax))
									manager.addAxiom(clusterOnt, ax);
								changesLog = changesLog + "Add the missing DatatypeProperty: "
										+ p.toString().replace("<", "[").replace(">", "]")
										+ " to its respective range: "
										+ range.toString().replace("<", "[").replace(">", "]");
								addedFlag = true;
								ontM.getClusters().get(clusterId).setManager(manager);
								ontM.getClusters().get(clusterId).setOntology(clusterOnt);
								int r = ontM.getRefineActionOnCluster();
								ontM.setRefineActionOnCluster(r + 1);
							}
						} else if (range instanceof OWLDatatypeImpl) {
							Set<OWLDatatype> dType = range.getDatatypesInSignature(); // ((OWLDatatypeImpl)range).getDataRangeType()
							// OWLAxiom newAxiom1 =
							// factory.getOWLDatatypeDefinitionAxiom(dType.iterator().next(),
							// arg1);
							int w = 0;
							w++;
						}
						if (!addedFlag) {
							String msg = "The DatatypeProperty: " + p.toString().replace("<", "[").replace(">", "]")
									+ "is missing! But none of its domain and range are already exist in the merged ontology. So, the missing property cann't be added.\n";
							MyLogging.log(Level.WARNING, msg);
							// NOTE: It means there is no class for this
							// property.
							// When the class
							// of property does not exist, we cannot force in
							// this
							// function,
							// to add classes, we just skip this property
						}

					} // if rangeIter

				} // if !flag

			} // while
		} // for oi

		// TODO: do it for FunctionalProperty and InverseFunctionalProperty

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
		StatisticTest.result.put("MU_Cluster_PropertyPreservation", String.valueOf(actualMemUsed));

		return ontM;

	}

	// *********************************************************************************************************
	public static HModel InstancesPreservation(HModel ontM) {
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();
		String changesLog = "";
		boolean addedFlag = false;
		boolean flag = false;
		OWLOntologyManager manager;
		OWLDataFactory factory;
		OWLOntology clusterOnt;

		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLNamedIndividual> iterIn = Oi.getIndividualsInSignature().iterator();
			while (iterIn.hasNext()) {
				OWLNamedIndividual In = iterIn.next();
				flag = false;
				for (int j = 0; j < ontM.getClusters().size(); j++) {
					if (ontM.getClusters().get(j).getOntology().getIndividualsInSignature().contains(In)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					// if (!OmIndividuals.contains(In)) {
					addedFlag = false;
					// if you find it, add it to Om right now
					Iterator<OWLClass> cIter = In.getClassesInSignature().iterator();
					if (cIter != null && cIter.hasNext()) {
						// add it to the first class
						OWLClass c = cIter.next();

						OWLClass refC = ontM.getKeyValueEqClass().get(c);
						if (refC != null)
							c = refC;
						int clusterId = ClusterShareFunc.findClusterOfClass(c, ontM);
						if (clusterId != -1) {
							manager = ontM.getClusters().get(clusterId).getManager();
							factory = manager.getOWLDataFactory();
							clusterOnt = ontM.getClusters().get(clusterId).getOntology();
							OWLAxiom newAxiom = factory.getOWLClassAssertionAxiom(c, In);
							manager.addAxiom(clusterOnt, newAxiom);
							changesLog = changesLog + "\n In the cluster" + clusterId + " , add the missing instance: "
									+ In.toString().replace("<", "[").replace(">", "]") + " to its respective class: "
									+ c.toString().replace("<", "[").replace(">", "]");
							addedFlag = true;
							ontM.getClusters().get(clusterId).setManager(manager);
							ontM.getClusters().get(clusterId).setOntology(clusterOnt);
							int r = ontM.getRefineActionOnCluster();
							ontM.setRefineActionOnCluster(r + 1);
						}
						if (!addedFlag) {
							String msg = "The instance: " + In.toString().replace("<", "[").replace(">", "]")
									+ "is missing! But its respective class is not already exist in the merged ontology. So, the missing instance cann't be added.\n";
							MyLogging.log(Level.WARNING, msg);
						}
					}
				}
			}
		}

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
		StatisticTest.result.put("MU_Cluster_InstancePreservation", String.valueOf(actualMemUsed));

		return ontM;
	}

	// *********************************************************************************************************
	public static HModel CorrespondencesPreservation(HModel ontM) {
		// because of re-writing axioms, all corresponding
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
		// because of re-writing axioms, all properties of
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
		boolean flag = false;

		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLObjectProperty> iter3 = Oi.getObjectPropertiesInSignature().iterator();
			while (iter3.hasNext()) {
				OWLObjectProperty pOi = iter3.next();
				flag = false;
				for (int j = 0; j < ontM.getClusters().size(); j++) {
					OWLObjectProperty pOiRef = ontM.getKeyValueEqObjProperty().get(pOi);
					if (ontM.getClusters().get(j).getOntology().getObjectPropertiesInSignature().contains(pOi) || ontM
							.getClusters().get(j).getOntology().getObjectPropertiesInSignature().contains(pOiRef)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					// if (OmPro.contains(pOi) ||
					// ontM.getKeyValueEqObjProperty().get(pOi) != null) {
					// if the property itself exist in Om, then we check the
					// values of that property, whether they are exist in om or
					// not
					OWLObjectProperty pOiRef = pOi;
					if (ontM.getKeyValueEqObjProperty().get(pOi) != null)
						pOiRef = ontM.getKeyValueEqObjProperty().get(pOi);

					Set<OWLDataProperty> dpro = pOi.getDataPropertiesInSignature();
					Set<OWLDataProperty> dproRef = pOiRef.getDataPropertiesInSignature();
					if (!dpro.equals(dproRef)) {
						msg = "The dataproperty: " + " of property: "
								+ pOi.toString().replace("<", "[").replace(">", "]")
								+ " is skiped, and only the dataproperty of the reference equal entity is keeped.\n";
					}

					Set<OWLDatatype> dt = pOi.getDatatypesInSignature();
					Set<OWLDatatype> dtRef = pOiRef.getDatatypesInSignature();
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

		if (msg.equals(""))
			msg = "All Value of properties from the input ontologies have been preserved without any conflict. \n";

		MyLogging.log(Level.INFO, msg);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Property's value preservation refinement step has been done successfully. Total time  " + elapsedTime
						+ " ms. \n");
		return ontM;
	}

	// *********************************************************************************************************
	public static HModel StructurePreservation(HModel ontM) {
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();

		OWLOntologyManager manager = ontM.getManager();

		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLSubClassOfAxiom> iterOi = Oi.getAxioms(AxiomType.SUBCLASS_OF).iterator();
			while (iterOi.hasNext()) {
				OWLSubClassOfAxiom ax = iterOi.next();

				int clusterId = ClusterShareFunc.findClusterOfAxiom(ax, ontM);

				// now we know in which clusters, we have this axiom
				if (clusterId != -1) {
					OWLAxiom eqAx = ontM.getEqAxioms().get(ax);
					clusterId = ClusterShareFunc.findClusterOfAxiom(eqAx, ontM);
				}

				/*
				 * if this axiom or equal_of_axiom does not exist to any
				 * clusters, we should add it. We will add it to the cluster
				 * that has one of the axioms elements
				 */
				if (clusterId != -1) {
					Iterator<OWLClassExpression> elm = ShareMergeFunction.findElmOfAxiom(ax).iterator();
					while (elm.hasNext()) {
						OWLClassExpression el = elm.next();
						clusterId = ClusterShareFunc.findClusterOfClass(el, ontM);
						if (clusterId != -1)
							break;
					}
					if (clusterId != -1) {
						manager = ontM.getClusters().get(clusterId).getManager();
						manager.addAxiom(ontM.getOwlModel(), ax);
						ontM.getClusters().get(clusterId).setManager(manager);
						String msg = "This isA axioms from the input ontology: "
								+ ax.toString().replace("<", "[").replace(">", "]")
								+ " does not exist to any clusters. So it added to it respective cluster to keep the same structure as the input ontology. \n";
						MyLogging.log(Level.WARNING, msg);
						int r = ontM.getRefineActionOnCluster();
						ontM.setRefineActionOnCluster(r + 1);
					}
				}
			}
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "structure preservation refinement step has been done successfully. Total time  "
				+ elapsedTime + " ms. \n");

		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - beforeUsedMem;
		StatisticTest.result.put("MU_Cluster_StrPreservation", String.valueOf(actualMemUsed));

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
		// start from cluster's ontology. if their entities do not belong to any
		// oi, then it means it is extra. then delete it

		boolean exist = false;
		OWLOntology clusterOnt;// Om = ontM.getOwlModel();
		OWLOntologyManager manager;// = ontM.getManager();
		OWLEntityRemover remover;// = new OWLEntityRemover(manager,
									// Collections.singleton(Om));
		for (int k = 0; k < ontM.getClusters().size(); k++) {
			Iterator<OWLClass> iter = ontM.getClusters().get(k).getOntology().getClassesInSignature().iterator();
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
					Set<OWLClass> mappedRefinedClass = getMappedClassesforClass(ontM, cm);
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
						int clusterId = ClusterShareFunc.findClusterOfClass(cm, ontM);
						manager = ontM.getClusters().get(clusterId).getManager();
						clusterOnt = ontM.getClusters().get(clusterId).getOntology();
						remover = new OWLEntityRemover(manager, Collections.singleton(clusterOnt));
						cm.accept(remover);
						manager.applyChanges(remover.getChanges());
						String msg = "In the extranoues prohibition refinement step: the additional classs than input ontologies entities, i.e.  "
								+ cm.toString().replace("<", "[").replace(">", "]") + " has been deleted.\n ";
						MyLogging.log(Level.WARNING, msg);
						ontM.getClusters().get(clusterId).setManager(manager);
						ontM.getClusters().get(clusterId).setOntology(clusterOnt);
					}
				}
			}
		}

	}

	// *********************************************************************************************************

	private static void deleteExtraObjectProperty(HModel ontM) {
		boolean exist = false;
		OWLOntology clusterOnt;
		OWLOntologyManager manager;
		OWLEntityRemover remover;

		for (int k = 0; k < ontM.getClusters().size(); k++) {
			Iterator<OWLObjectProperty> iter = ontM.getClusters().get(k).getOntology().getObjectPropertiesInSignature()
					.iterator();
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
					Set<OWLObjectProperty> mappedRefinedObj = getMappedObjectforObject(ontM, cm);
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
					int clusterId = ClusterShareFunc.findClusterOfPro(cm, ontM);
					manager = ontM.getClusters().get(clusterId).getManager();
					clusterOnt = ontM.getClusters().get(clusterId).getOntology();
					remover = new OWLEntityRemover(manager, Collections.singleton(clusterOnt));
					cm.accept(remover);
					manager.applyChanges(remover.getChanges());
					String msg = "In the extranoues prohibition refinement step: the additional object property than input ontologies entities, i.e.  "
							+ cm.toString().replace("<", "[").replace(">", "]") + " has been deleted. \n ";
					MyLogging.log(Level.WARNING, msg);
					ontM.getClusters().get(clusterId).setManager(manager);
					ontM.getClusters().get(clusterId).setOntology(clusterOnt);

				}
			}
		}
	}

	// *********************************************************************************************************
	private static void deleteExtraDataProperty(HModel ontM) {
		boolean exist = false;
		OWLOntology clusterOnt;
		OWLOntologyManager manager;
		OWLEntityRemover remover;

		for (int k = 0; k < ontM.getClusters().size(); k++) {
			Iterator<OWLDataProperty> iter = ontM.getClusters().get(k).getOntology().getDataPropertiesInSignature()
					.iterator();
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
					Set<OWLDataProperty> mappedRefinedClass = getMappedDproforDpro(ontM, cm);
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
					int clusterId = ClusterShareFunc.findClusterOfDro(cm, ontM);
					manager = ontM.getClusters().get(clusterId).getManager();
					clusterOnt = ontM.getClusters().get(clusterId).getOntology();
					remover = new OWLEntityRemover(manager, Collections.singleton(clusterOnt));
					cm.accept(remover);
					manager.applyChanges(remover.getChanges());
					String msg = "In the extranoues prohibition refinement step: the additional datatypeProperty than input ontologies entities, i.e.  "
							+ cm.toString().replace("<", "[").replace(">", "]") + " has been deleted. \n";
					MyLogging.log(Level.WARNING, msg);
					ontM.getClusters().get(clusterId).setManager(manager);
					ontM.getClusters().get(clusterId).setOntology(clusterOnt);
				}
			}
		}

	}

	// *********************************************************************************************************

	private static void deleteExtraInstances(HModel ontM) {

		boolean exist = false;

		OWLOntology clusterOnt;
		OWLOntologyManager manager;
		OWLEntityRemover remover;

		for (int k = 0; k < ontM.getClusters().size(); k++) {
			Iterator<OWLNamedIndividual> iter = ontM.getClusters().get(k).getOntology().getIndividualsInSignature()
					.iterator();
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
					int clusterId = ClusterShareFunc.findClusterOfIndiv(cm, ontM);
					manager = ontM.getClusters().get(clusterId).getManager();
					clusterOnt = ontM.getClusters().get(clusterId).getOntology();
					remover = new OWLEntityRemover(manager, Collections.singleton(clusterOnt));
					cm.accept(remover);
					manager.applyChanges(remover.getChanges());

					String msg = "In the extranoues prohibition refinement step: the additional instance than input ontologies entities, i.e.  "
							+ cm.toString().replace("<", "[").replace(">", "]") + " has been deleted. \n";
					MyLogging.log(Level.WARNING, msg);
					ontM.getClusters().get(clusterId).setManager(manager);
					ontM.getClusters().get(clusterId).setOntology(clusterOnt);

				}
			}
		}

	}

	// *********************************************************************************************************

	public static HModel DomainRangeMinimality(HModel ontM) {
		// TODO: add log info in this function
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();

		OWLOntology clusterOnt;
		OWLOntologyManager manager;
		OWLDataFactory factory;
		for (int k = 0; k < ontM.getClusters().size(); k++) {
			clusterOnt = ontM.getClusters().get(k).getOntology();
			Iterator<OWLObjectProperty> iter = clusterOnt.getObjectPropertiesInSignature().iterator();
			while (iter.hasNext()) {
				OWLObjectProperty obj = iter.next();
				// For multiple domains
				Set<OWLClassExpression> domain = obj.getDomains(clusterOnt);
				if (domain.size() > 1) {
					// Step 1: Delete their respective axioms which refer to the
					// multiple
					// domains of that object property
					HashSet<OWLAxiom> domAxioms = getRelatedDomainAxioms(domain, obj, clusterOnt);
					Iterator<OWLAxiom> iterAx = domAxioms.iterator();
					manager = clusterOnt.getOWLOntologyManager();
					while (iterAx.hasNext()) {
						OWLAxiom ax = iterAx.next();
						manager.removeAxiom(clusterOnt, ax);
						// int r = ontM.getRefineActionOnCluster();
						// ontM.setRefineActionOnCluster(r + 1);
					}
					// Step 2: add all domains of this object as the union of to
					// the
					// new class cNew
					manager = clusterOnt.getOWLOntologyManager();
					factory = manager.getOWLDataFactory();

					OWLObjectUnionOf uniClass = factory.getOWLObjectUnionOf(domain);
					OWLAxiom newAxiom = factory.getOWLObjectPropertyDomainAxiom(obj, uniClass);

					manager.addAxiom(clusterOnt, newAxiom);
					ontM.getClusters().get(k).setManager(manager);
					ontM.getClusters().get(k).setOntology(clusterOnt);
					int r = ontM.getRefineActionOnCluster();
					ontM.setRefineActionOnCluster(r + 1);
				}

				// For multiple ranges
				Set<OWLClassExpression> range = obj.getRanges(clusterOnt);
				if (range.size() > 1) {
					// Step 1: Delete their respective axioms which refer to the
					// multiple
					// domains of that object property
					// int clusterId = findClusterOfClass(cm, ontM);
					// manager = ontM.getClusters().get(clusterId).getManager();
					manager = clusterOnt.getOWLOntologyManager();
					factory = manager.getOWLDataFactory();
					HashSet<OWLAxiom> rangAxioms = getRelatedRangeAxioms(range, obj, clusterOnt);
					Iterator<OWLAxiom> iterAx = rangAxioms.iterator();
					while (iterAx.hasNext()) {
						OWLAxiom ax = iterAx.next();
						manager.removeAxiom(clusterOnt, ax);
					}
					// Step 2: add all domains of this object as the union of to
					// the
					// new class cNew
					OWLObjectUnionOf uniClass = factory.getOWLObjectUnionOf(range);
					OWLAxiom newAxiom = factory.getOWLObjectPropertyRangeAxiom(obj, uniClass);
					manager.addAxiom(clusterOnt, newAxiom);
					ontM.getClusters().get(k).setManager(manager);
					ontM.getClusters().get(k).setOntology(clusterOnt);
					int r = ontM.getRefineActionOnCluster();
					ontM.setRefineActionOnCluster(r + 1);
				}
			}
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Property's domain and range oneness refinement step has been done successfully. Total time  "
						+ elapsedTime + " ms. \n");

		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - beforeUsedMem;
		StatisticTest.result.put("MU_Cluster_oneness", String.valueOf(actualMemUsed));

		return ontM;
	}
	// *********************************************************************************************************

	public static HModel ClassAcyclicity(HModel ontM) {
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();

		Set<OWLAxiom> res = new HashSet<OWLAxiom>();

		for (int k = 0; k < ontM.getClusters().size(); k++) {
			OWLOntology clusterOnt = ontM.getClusters().get(k).getOntology();
			OWLOntologyManager manager = clusterOnt.getOWLOntologyManager();
			OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(clusterOnt));

			Iterator<OWLClass> cl = clusterOnt.getClassesInSignature().iterator();
			HashMap<OWLClassExpression, ArrayList<OWLClassExpression>> listParent = ShareMergeFunction
					.generateParentChildList(clusterOnt);

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
					Iterator<OWLAxiom> iter = c.getReferencingAxioms(clusterOnt).iterator();
					while (iter.hasNext()) {
						OWLAxiom ax = iter.next();
						if (ax.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
							OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) ax).getSuperClass();
							OWLClassExpression SubClass = ((OWLSubClassOfAxiom) ax).getSubClass();

							if (SuperClass instanceof OWLClassImpl && SubClass instanceof OWLClassImpl) {
								if (SuperClass.equals(c) && SubClass.equals(c)) {
									// remove it
									manager.removeAxiom(clusterOnt, ax);
									res.add(ax);
									int r = ontM.getRefineActionOnCluster();
									ontM.setRefineActionOnCluster(r + 1);
								}
							}
						}
					}
				}
			}

			manager.applyChanges(remover.getChanges());
			ontM.SetOwlModel(clusterOnt);
			ontM.SetManager(manager);
		}

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
		StatisticTest.result.put("MU_Cluster_ClassAcyc", String.valueOf(actualMemUsed));

		return ontM;
	}

	// *********************************************************************************************************
	public static HModel PropertyAcyclicity(HModel ontM) {
		// it detects only self cycle
		long startTime = System.currentTimeMillis();
		Set<OWLAxiom> res = new HashSet<OWLAxiom>();
		OWLOntologyManager manager;
		OWLOntology clusterOnt;
		OWLEntityRemover remover;

		for (int k = 0; k < ontM.getClusters().size(); k++) {
			clusterOnt = ontM.getClusters().get(k).getOntology();
			manager = clusterOnt.getOWLOntologyManager();
			Iterator<OWLSubObjectPropertyOfAxiom> axObjPro = clusterOnt.getAxioms(AxiomType.SUB_OBJECT_PROPERTY)
					.iterator();
			while (axObjPro.hasNext()) {
				OWLSubObjectPropertyOfAxiom axiom = axObjPro.next();

				OWLObjectPropertyExpression SuperPro = ((OWLSubObjectPropertyOfAxiom) axiom).getSubProperty();
				OWLObjectPropertyExpression SubPro = ((OWLSubObjectPropertyOfAxiom) axiom).getSuperProperty();
				if (SuperPro.equals(SubPro)) {
					manager.removeAxiom(clusterOnt, axiom);
					res.add(axiom);
					remover = new OWLEntityRemover(manager, Collections.singleton(clusterOnt));
					manager.applyChanges(remover.getChanges());
					ontM.getClusters().get(k).setManager(manager);
					ontM.getClusters().get(k).setOntology(clusterOnt);
					int r = ontM.getRefineActionOnCluster();
					ontM.setRefineActionOnCluster(r + 1);
				}
			}

			Iterator<OWLSubDataPropertyOfAxiom> axDpro = clusterOnt.getAxioms(AxiomType.SUB_DATA_PROPERTY).iterator();
			while (axDpro.hasNext()) {
				OWLSubDataPropertyOfAxiom axiom = axDpro.next();

				OWLDataPropertyExpression SuperPro = ((OWLSubDataPropertyOfAxiom) axiom).getSubProperty();
				OWLDataPropertyExpression SubPro = ((OWLSubDataPropertyOfAxiom) axiom).getSuperProperty();
				if (SuperPro.equals(SubPro)) {
					manager.removeAxiom(clusterOnt, axiom);
					res.add(axiom);
					remover = new OWLEntityRemover(manager, Collections.singleton(clusterOnt));
					manager.applyChanges(remover.getChanges());
					ontM.getClusters().get(k).setManager(manager);
					ontM.getClusters().get(k).setOntology(clusterOnt);
					int r = ontM.getRefineActionOnCluster();
					ontM.setRefineActionOnCluster(r + 1);
				}
			}
		}

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
		OWLOntologyManager manager;
		OWLOntology clusterOnt;
		OWLEntityRemover remover;

		for (int k = 0; k < ontM.getClusters().size(); k++) {
			clusterOnt = ontM.getClusters().get(k).getOntology();
			manager = clusterOnt.getOWLOntologyManager();
			Iterator<OWLInverseObjectPropertiesAxiom> ax = clusterOnt.getAxioms(AxiomType.INVERSE_OBJECT_PROPERTIES)
					.iterator();
			while (ax.hasNext()) {
				OWLInverseObjectPropertiesAxiom axiom = ax.next();

				OWLObjectPropertyExpression p1 = axiom.getFirstProperty();
				OWLObjectPropertyExpression p2 = axiom.getSecondProperty();

				if (p1.equals(p2)) {
					// delete this axiom
					manager.removeAxiom(clusterOnt, axiom);
					res.add(axiom);
					remover = new OWLEntityRemover(manager, Collections.singleton(clusterOnt));
					manager.applyChanges(remover.getChanges());

					ontM.getClusters().get(k).setManager(manager);
					ontM.getClusters().get(k).setOntology(clusterOnt);
					int r = ontM.getRefineActionOnCluster();
					ontM.setRefineActionOnCluster(r + 1);
				}
			}
		}

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
		OWLOntology clusterOnt;
		OWLOntologyManager manager;
		OWLDataFactory factory;
		String changes = "";
		boolean connected = true;

		for (int k = 0; k < ontM.getClusters().size(); k++) {
			clusterOnt = ontM.getClusters().get(k).getOntology();
			Iterator<OWLClass> lis = clusterOnt.getClassesInSignature().iterator();
			while (lis.hasNext()) {
				OWLClass c = lis.next();
				connected = false;
				if (c.getSubClasses(clusterOnt).size() == 0 && c.getSuperClasses(clusterOnt).size() == 0) {
					// if this class has some connection in the input
					// ontologies, we
					// also here connect it, but if this class even in the input
					// ontologies has no connections, it means this is the
					// problem
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
							if (clusterOnt.getClassesInSignature().contains(childinOm)) {
								break;// select the first one and return6
							}
						}
						if (childinOm != null) {
							manager = clusterOnt.getOWLOntologyManager();
							factory = manager.getOWLDataFactory();
							OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(childinOm, c);
							manager.addAxiom(clusterOnt, newAxiom);
							connected = true;
							changes = changes + "The class: " + c.toString().replace("<", "[").replace(">", "]")
									+ " has been connected to the respective subclass from the input ontology. \n";
							ontM.getClusters().get(k).setManager(manager);
							ontM.getClusters().get(k).setOntology(clusterOnt);
							int r = ontM.getRefineActionOnCluster();
							ontM.setRefineActionOnCluster(r + 1);
						} else {
							// it means none of children of this class do not
							// exist in the merged ontology. so we should
							// connected it to the root
							OWLClass root = new OWLClassImpl(IRI.create(OWL.Thing.getURI()));
							// TODO: get the root of Om , do not create a new
							// root
							manager = clusterOnt.getOWLOntologyManager();
							factory = manager.getOWLDataFactory();
							OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(c, root);
							manager.addAxiom(clusterOnt, newAxiom);
							connected = true;
							changes = changes + "The class: " + c.toString().replace("<", "[").replace(">", "]")
									+ " has been connected to root of ontology,the respective subclass from the input ontology. \n";
							ontM.getClusters().get(k).setManager(manager);
							ontM.getClusters().get(k).setOntology(clusterOnt);
							int r = ontM.getRefineActionOnCluster();
							ontM.setRefineActionOnCluster(r + 1);
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
								if (clusterOnt.getClassesInSignature().contains(fatherinOm)) {
									break;// select the first one and return6
								}
							}
							if (fatherinOm != null) {
								manager = clusterOnt.getOWLOntologyManager();
								factory = manager.getOWLDataFactory();
								OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(c, fatherinOm);
								manager.addAxiom(clusterOnt, newAxiom);
								connected = true;
								changes = changes + "The class: " + c.toString().replace("<", "[").replace(">", "]")
										+ " has been connected to the respective superclass from the input ontology. \n";
								ontM.getClusters().get(k).setManager(manager);
								ontM.getClusters().get(k).setOntology(clusterOnt);
								int r = ontM.getRefineActionOnCluster();
								ontM.setRefineActionOnCluster(r + 1);
							} else {
								// it means none of children of this class do
								// not
								// exist in the merged ontology. so we should
								// connected it to the root
								OWLClass root = new OWLClassImpl(IRI.create(OWL.Thing.getURI()));
								// TODO: get the root of Om , do not create a
								// new
								// root
								manager = clusterOnt.getOWLOntologyManager();
								factory = manager.getOWLDataFactory();
								OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(c, root);
								manager.addAxiom(clusterOnt, newAxiom);
								connected = true;
								changes = changes + "The class: " + c.toString().replace("<", "[").replace(">", "]")
										+ " has been connected to root of ontology,the respective subclass from the input ontology. \n";
								ontM.getClusters().get(k).setManager(manager);
								ontM.getClusters().get(k).setOntology(clusterOnt);
								int r = ontM.getRefineActionOnCluster();
								ontM.setRefineActionOnCluster(r + 1);
							}
						} else {
							changes = changes + "The class: " + c.toString().replace("<", "[").replace(">", "]")
									+ " does not have any connections in the input ontology, that's why it is also unconnected in the merged ontology. \n";
						}
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
		StatisticTest.result.put("MU_Cluster_ClassUncon", String.valueOf(actualMemUsed));

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

		OWLOntology clusterOnt;
		OWLOntologyManager manager;
		String changes = "";
		OWLEntityRemover remover;
		for (int k = 0; k < ontM.getClusters().size(); k++) {
			clusterOnt = ontM.getClusters().get(k).getOntology();
			// For Objectproperties
			Iterator<OWLObjectProperty> lis = clusterOnt.getObjectPropertiesInSignature().iterator();
			while (lis.hasNext()) {
				OWLObjectProperty p = lis.next();
				if (p.getDomains(clusterOnt).size() == 0 && p.getRanges(clusterOnt).size() == 0
						&& p.getSubProperties(clusterOnt).size() == 0 && p.getObjectPropertiesInSignature().size() == 0
						&& p.getDataPropertiesInSignature().size() == 0) {
					// it means this property does not have any class, domain,
					// range
					// etc. it means it is unconnected

					// delete it:
					manager = clusterOnt.getOWLOntologyManager();
					remover = new OWLEntityRemover(manager, Collections.singleton(clusterOnt));
					p.accept(remover);
					changes = "The property: " + p.toString().replace("<", "[").replace(">", "]")
							+ " has been deleted, beacsue it does not have any connections. \n";
					manager.applyChanges(remover.getChanges());
					ontM.getClusters().get(k).setManager(manager);
					ontM.getClusters().get(k).setOntology(clusterOnt);
					int r = ontM.getRefineActionOnCluster();
					ontM.setRefineActionOnCluster(r + 1);

				}
			}

			// For datatypeProperties
			Iterator<OWLDataProperty> listDpro = clusterOnt.getDataPropertiesInSignature().iterator();
			while (listDpro.hasNext()) {
				OWLDataProperty p = listDpro.next();
				if (p.getDomains(clusterOnt).size() == 0 && p.getRanges(clusterOnt).size() == 0
						&& p.getSubProperties(clusterOnt).size() == 0 && p.getObjectPropertiesInSignature().size() == 0
						&& p.getDataPropertiesInSignature().size() == 0) {
					// it means this property does not have any class, domain,
					// range
					// etc. it means it is unconnected

					// delete it:
					manager = clusterOnt.getOWLOntologyManager();
					remover = new OWLEntityRemover(manager, Collections.singleton(clusterOnt));
					p.accept(remover);
					changes = "The property: " + p.toString().replace("<", "[").replace(">", "]")
							+ " has been deleted, beacsue it does not have any connections. \n";
					manager.applyChanges(remover.getChanges());
					ontM.getClusters().get(k).setManager(manager);
					ontM.getClusters().get(k).setOntology(clusterOnt);
					int r = ontM.getRefineActionOnCluster();
					ontM.setRefineActionOnCluster(r + 1);
				}
			}
		}

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
		OWLOntology clusterOnt;
		OWLOntologyManager manager;
		OWLDataFactory factory;

		for (int k = 0; k < ontM.getClusters().size(); k++) {
			clusterOnt = ontM.getClusters().get(k).getOntology();
			Iterator<OWLObjectProperty> iter = clusterOnt.getObjectPropertiesInSignature().iterator();
			while (iter.hasNext()) {
				OWLObjectProperty obj = iter.next();
				Set<OWLClassExpression> domain = obj.getDomains(clusterOnt);
				if (domain.size() == 0) {
					Set<OWLClassExpression> domOi = findDomaininOi(obj, ontM);
					if (domOi != null && domOi.size() > 1) {
						// it means the domain of proerty exist in Oi but does
						// not
						// exist in Om

						OWLClassExpression newDomain = domOi.iterator().next(); // (the
																				// first
																				// domain)
						// add domain to Om
						OWLOntology Oi = findOiofDomain(ontM, newDomain);
						if (Oi != null) {
							Set<OWLClassExpression> superC = newDomain.asOWLClass().getSuperClasses(Oi);
							if (superC != null) {
								// select the first parent
								manager = clusterOnt.getOWLOntologyManager();
								factory = manager.getOWLDataFactory();
								OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(newDomain, superC.iterator().next());
								manager.addAxiom(clusterOnt, newAxiom);
								ontM.getClusters().get(k).setManager(manager);
								ontM.getClusters().get(k).setOntology(clusterOnt);
								int r = ontM.getRefineActionOnCluster();
								ontM.setRefineActionOnCluster(r + 1);
							} else {
								Set<OWLClassExpression> subC = newDomain.asOWLClass().getSubClasses(Oi);
								if (subC != null) {
									// select the first child
									manager = clusterOnt.getOWLOntologyManager();
									factory = manager.getOWLDataFactory();
									OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(superC.iterator().next(),
											newDomain);
									manager.addAxiom(clusterOnt, newAxiom);
									ontM.getClusters().get(k).setManager(manager);
									ontM.getClusters().get(k).setOntology(clusterOnt);
									int r = ontM.getRefineActionOnCluster();
									ontM.setRefineActionOnCluster(r + 1);
								} else {
									// if there is no sub or superclass, add c
									// to root
									manager = clusterOnt.getOWLOntologyManager();
									factory = manager.getOWLDataFactory();
									OWLClass root = factory.getOWLThing();
									OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(newDomain, root);
									manager.addAxiom(clusterOnt, newAxiom);
									ontM.getClusters().get(k).setManager(manager);
									ontM.getClusters().get(k).setOntology(clusterOnt);
									int r = ontM.getRefineActionOnCluster();
									ontM.setRefineActionOnCluster(r + 1);
								}
							}
						} else {
							// add to root
							manager = clusterOnt.getOWLOntologyManager();
							factory = manager.getOWLDataFactory();
							OWLClass root = factory.getOWLThing();
							OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(newDomain, root);
							manager.addAxiom(clusterOnt, newAxiom);
							ontM.getClusters().get(k).setManager(manager);
							ontM.getClusters().get(k).setOntology(clusterOnt);
							int r = ontM.getRefineActionOnCluster();
							ontM.setRefineActionOnCluster(r + 1);
						}
						// finish adding c to Om

						// then add this property to domOi(the first domain)
						OWLAxiom newAxiom = factory.getOWLObjectPropertyDomainAxiom(obj, newDomain);
						manager.addAxiom(clusterOnt, newAxiom);
						ontM.getClusters().get(k).setManager(manager);
						ontM.getClusters().get(k).setOntology(clusterOnt);
						int r = ontM.getRefineActionOnCluster();
						ontM.setRefineActionOnCluster(r + 1);
					} else {
						// create new class and add this obj to this new class
						manager = clusterOnt.getOWLOntologyManager();
						factory = manager.getOWLDataFactory();
						OWLClass cNew = factory
								.getOWLClass(IRI.create("http://merged#ExtraClasses_" + obj.getIRI().getShortForm()));
						OWLAxiom newAxiom = factory.getOWLObjectPropertyDomainAxiom(obj, cNew);
						manager.addAxiom(clusterOnt, newAxiom);
						ontM.getClusters().get(k).setManager(manager);
						ontM.getClusters().get(k).setOntology(clusterOnt);
						int r = ontM.getRefineActionOnCluster();
						ontM.setRefineActionOnCluster(r + 1);
					}
				}
				// do all this process for range

				Set<OWLClassExpression> range = obj.getRanges(clusterOnt);
				if (range.size() == 0) {
					Set<OWLClassExpression> rangeOi = findRangeinOi(obj, ontM);
					if (rangeOi != null && rangeOi.size() > 1) {
						// first add domOi in Om
						OWLClassExpression newRange = rangeOi.iterator().next(); // (the
																					// first
																					// range)
						// add range to Om
						OWLOntology Oi = findOiofDomain(ontM, newRange);
						if (Oi != null) {
							Set<OWLClassExpression> superC = newRange.asOWLClass().getSuperClasses(Oi);
							if (superC != null) {
								// select the first parent
								manager = clusterOnt.getOWLOntologyManager();
								factory = manager.getOWLDataFactory();
								OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(newRange, superC.iterator().next());
								manager.addAxiom(clusterOnt, newAxiom);
								ontM.getClusters().get(k).setManager(manager);
								ontM.getClusters().get(k).setOntology(clusterOnt);
								int r = ontM.getRefineActionOnCluster();
								ontM.setRefineActionOnCluster(r + 1);
							} else {
								Set<OWLClassExpression> subC = newRange.asOWLClass().getSubClasses(Oi);
								if (subC != null) {
									// select the first child
									manager = clusterOnt.getOWLOntologyManager();
									factory = manager.getOWLDataFactory();
									OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(superC.iterator().next(),
											newRange);
									manager.addAxiom(clusterOnt, newAxiom);
									ontM.getClusters().get(k).setManager(manager);
									ontM.getClusters().get(k).setOntology(clusterOnt);
									int r = ontM.getRefineActionOnCluster();
									ontM.setRefineActionOnCluster(r + 1);
								} else {
									// if there is no sub or superclass, add c
									// to root
									manager = clusterOnt.getOWLOntologyManager();
									factory = manager.getOWLDataFactory();
									OWLClass root = factory.getOWLThing();
									OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(newRange, root);
									manager.addAxiom(clusterOnt, newAxiom);
									ontM.getClusters().get(k).setManager(manager);
									ontM.getClusters().get(k).setOntology(clusterOnt);
									int r = ontM.getRefineActionOnCluster();
									ontM.setRefineActionOnCluster(r + 1);
								}
							}
						} else {
							// add to root
							manager = clusterOnt.getOWLOntologyManager();
							factory = manager.getOWLDataFactory();
							OWLClass root = factory.getOWLThing();
							OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(newRange, root);
							manager.addAxiom(clusterOnt, newAxiom);
							ontM.getClusters().get(k).setManager(manager);
							ontM.getClusters().get(k).setOntology(clusterOnt);
							int r = ontM.getRefineActionOnCluster();
							ontM.setRefineActionOnCluster(r + 1);
						}
						// finish adding c to Om

						// then add this property to domOi(the first range)
						OWLAxiom newAxiom = factory.getOWLObjectPropertyRangeAxiom(obj, newRange);
						manager.addAxiom(clusterOnt, newAxiom);
						ontM.getClusters().get(k).setManager(manager);
						ontM.getClusters().get(k).setOntology(clusterOnt);
						int r = ontM.getRefineActionOnCluster();
						ontM.setRefineActionOnCluster(r + 1);
					} else {
						// create new class and add this obj to this new class
						manager = clusterOnt.getOWLOntologyManager();
						factory = manager.getOWLDataFactory();
						OWLClass cNew = factory
								.getOWLClass(IRI.create("http://merged#ExtraClasses_" + obj.getIRI().getShortForm()));
						OWLAxiom newAxiom = factory.getOWLObjectPropertyRangeAxiom(obj, cNew);
						manager.addAxiom(clusterOnt, newAxiom);
						ontM.getClusters().get(k).setManager(manager);
						ontM.getClusters().get(k).setOntology(clusterOnt);
						int r = ontM.getRefineActionOnCluster();
						ontM.setRefineActionOnCluster(r + 1);
					}
				}

			}
		}
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

		for (int id = 0; id < ontM.getClusters().size(); id++) {
			OWLOntology Oi = ontM.getClusters().get(id).getOntology();

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
					// e.printStackTrace();
					// falseAnsSubsumption++;
					// add pos to om
					manager.addAxiom(Om, pos);
					int r = ontM.getRefineActionOnMerge();
					ontM.setRefineActionOnMerge(r + 1);
					changesLog = changesLog + "\n Add the missing entilment: "
							+ pos.toString().replaceAll("<", "[").replaceAll(">", "]") + "in the cluster model.";

				} catch (InconsistentOntologyException e) {
					// result[0] = -1;
					// return result;
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
					// e.printStackTrace();
					// falseAnsEquivalence++;
					manager.addAxiom(Om, pos);
					int r = ontM.getRefineActionOnMerge();
					ontM.setRefineActionOnMerge(r + 1);
					changesLog = changesLog + "\n Add the missing entilment: "
							+ pos.toString().replaceAll("<", "[").replaceAll(">", "]") + "in the cluster model.";
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
					// e.printStackTrace();
					// falseAnsEquivalence++;
					// add pos to om
					manager.addAxiom(Om, pos);
					int r = ontM.getRefineActionOnMerge();
					ontM.setRefineActionOnMerge(r + 1);
					changesLog = changesLog + "\n Add the missing entilment: "
							+ pos.toString().replaceAll("<", "[").replaceAll(">", "]") + "in the cluster model.";

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
					// e.printStackTrace();
					// falseAnsEquivalence++;
					manager.addAxiom(Om, pos);
					int r = ontM.getRefineActionOnMerge();
					ontM.setRefineActionOnMerge(r + 1);
					changesLog = changesLog + "\n Add the missing entilment: "
							+ pos.toString().replaceAll("<", "[").replaceAll(">", "]") + "in the cluster model.";
				}
			}
		}

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

		OWLOntology clusterOnt;
		ArrayList<HMappedDpro> OmEqDataPro = ontM.getEqDataProperties();

		for (int k = 0; k < ontM.getClusters().size(); k++) {
			clusterOnt = ontM.getClusters().get(k).getOntology();
			Iterator<OWLDataProperty> iterr = clusterOnt.getDataPropertiesInSignature().iterator();
			while (iterr.hasNext()) {
				OWLDataProperty dp = iterr.next();
				Set<OWLDatatype> dataType = dp.getDatatypesInSignature();
			}
			// TODO:error :How to get type of datatypeproperties in OWL?
			for (int j = 0; j < OmEqDataPro.size(); j++) {
				OWLDataProperty Dpro = OmEqDataPro.get(j).getRefDpro();
				Set<OWLDatatype> dataTypeRef = Dpro.getDatatypesInSignature();

				Set<OWLDataRange> dataRangeRef = Dpro.getRanges(clusterOnt);
				Set<OWLClassExpression> dataDomainRef = Dpro.getDomains(clusterOnt);

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
							// it means the property is in oi, no need to search
							// in
							// other oi
						}
					}

					// Set<OWLClassExpression> dataDomain =
					// eqDpro.getDomains(Om);

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
		}

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
		OWLOntology clusterOnt;// Om = ontM.getOwlModel();
		OWLOntologyManager manager;// = ontM.getManager();
		OWLDataFactory factory;// = manager.getOWLDataFactory();
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			Iterator<OWLAxiom> axIter = ontM.getInputOwlOntModel().get(i).getAxioms().iterator();
			while (axIter.hasNext()) {
				OWLAxiom axx = axIter.next();
				if (axx.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
					for (int k = 0; k < ontM.getClusters().size(); k++) {
						clusterOnt = ontM.getClusters().get(k).getOntology();
						OWLClassExpression SuperClassOi = ((OWLSubClassOfAxiom) axx).getSuperClass();
						OWLClassExpression SubClassOi = ((OWLSubClassOfAxiom) axx).getSubClass();
						if (SuperClassOi instanceof OWLDataExactCardinality) {
							int sOi = ((OWLDataExactCardinality) SuperClassOi).getCardinality();
							OWLClassExpression SubclassOm = ontM.getKeyValueEqClass().get(SubClassOi);
							if (SubclassOm == null)
								SubclassOm = SubClassOi;
							Iterator<OWLAxiom> omAxIter = clusterOnt.getReferencingAxioms((OWLEntity) SubclassOm)
									.iterator();
							while (omAxIter.hasNext()) {
								OWLAxiom x = omAxIter.next();
								if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
									OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) axx).getSuperClass();
									OWLClassExpression SubClassOm = ((OWLSubClassOfAxiom) axx).getSubClass();
									if (SuperClassOm instanceof OWLDataExactCardinality) {
										int sOm = ((OWLDataExactCardinality) SuperClassOm).getCardinality();
										if ((SubClassOi.equals(SubClassOm)
												|| getMappedClassesforClass(ontM, SubClassOm).contains(SubClassOi))
												&& (sOi != sOm)) {
											changes = changes + "The OWLDataExactCardinality for property: "
													+ SuperClassOm.toString().replace("<", "[").replace(">", "]")
													+ " in the merged ontology was different with the input ontology. So, it sets to the input ontology value. \n";
											// sOm should be set as sOi, but if
											// Oi
											// is the preference one!TODO:check
											// it
											manager = clusterOnt.getOWLOntologyManager();
											factory = manager.getOWLDataFactory();
											OWLDataExactCardinality cardi = factory.getOWLDataExactCardinality(sOi,
													(OWLDataPropertyExpression) SubClassOm);
											OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(SubClassOi, cardi);
											manager.addAxiom(clusterOnt, newAxiom);
											manager.removeAxiom(clusterOnt, x);
											ontM.getClusters().get(k).setManager(manager);
											ontM.getClusters().get(k).setOntology(clusterOnt);
											int r = ontM.getRefineActionOnCluster();
											ontM.setRefineActionOnCluster(r + 1);
										}
									}
								}
							}
						} else if (SuperClassOi instanceof OWLObjectExactCardinality) {
							int sOi = ((OWLObjectExactCardinality) SuperClassOi).getCardinality();
							OWLClassExpression SubclassOm = ontM.getKeyValueEqClass().get(SubClassOi);
							if (SubclassOm == null)
								SubclassOm = SubClassOi;
							Iterator<OWLAxiom> omAxIter = clusterOnt.getReferencingAxioms((OWLEntity) SubclassOm)
									.iterator();
							while (omAxIter.hasNext()) {
								OWLAxiom x = omAxIter.next();
								if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
									OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) axx).getSuperClass();
									OWLClassExpression SubClassOm = ((OWLSubClassOfAxiom) axx).getSubClass();
									if (SuperClassOm instanceof OWLObjectExactCardinality) {
										int sOm = ((OWLObjectExactCardinality) SuperClassOm).getCardinality();
										if ((SubClassOi.equals(SubClassOm)
												|| getMappedClassesforClass(ontM, SubClassOm).contains(SubClassOi))
												&& (sOi != sOm)) {
											changes = changes + "The OWLDataExactCardinality for property: "
													+ SuperClassOm.toString().replace("<", "[").replace(">", "]")
													+ " in the merged ontology was different with the input ontology. So, it sets to the input ontology value. \n";
											// sOm should be set as sOi, but if
											// Oi
											// is the preference one!TODO:check
											// it
											manager = clusterOnt.getOWLOntologyManager();
											factory = manager.getOWLDataFactory();
											OWLObjectExactCardinality cardi = factory.getOWLObjectExactCardinality(sOi,
													(OWLObjectPropertyExpression) SubClassOm);
											OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(SubClassOi, cardi);
											manager.addAxiom(clusterOnt, newAxiom);
											manager.removeAxiom(clusterOnt, x);
											ontM.getClusters().get(k).setManager(manager);
											ontM.getClusters().get(k).setOntology(clusterOnt);
											int r = ontM.getRefineActionOnCluster();
											ontM.setRefineActionOnCluster(r + 1);
										}
									}
								}
							}

						} else if (SuperClassOi instanceof OWLObjectMaxCardinality) {
							int sOi = ((OWLObjectMaxCardinality) SuperClassOi).getCardinality();
							OWLClassExpression SubclassOm = ontM.getKeyValueEqClass().get(SubClassOi);
							if (SubclassOm == null)
								SubclassOm = SubClassOi;
							Iterator<OWLAxiom> omAxIter = clusterOnt.getReferencingAxioms((OWLEntity) SubclassOm)
									.iterator();
							while (omAxIter.hasNext()) {
								OWLAxiom x = omAxIter.next();
								if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
									OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) axx).getSuperClass();
									OWLClassExpression SubClassOm = ((OWLSubClassOfAxiom) axx).getSubClass();
									if (SuperClassOm instanceof OWLObjectMaxCardinality) {
										int sOm = ((OWLObjectMaxCardinality) SuperClassOm).getCardinality();
										if ((SubClassOi.equals(SubClassOm)
												|| getMappedClassesforClass(ontM, SubClassOm).contains(SubClassOi))
												&& (sOi != sOm)) {
											changes = changes + "The OWLDataExactCardinality for property: "
													+ SuperClassOm.toString().replace("<", "[").replace(">", "]")
													+ " in the merged ontology was different with the input ontology. So, it sets to the input ontology value. \n";
											// sOm should be set as sOi, but if
											// Oi
											// is the preference one!TODO:check
											// it
											manager = clusterOnt.getOWLOntologyManager();
											factory = manager.getOWLDataFactory();
											OWLObjectMaxCardinality cardi = factory.getOWLObjectMaxCardinality(sOi,
													(OWLObjectPropertyExpression) SubClassOm);
											OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(SubClassOi, cardi);
											manager.addAxiom(clusterOnt, newAxiom);
											manager.removeAxiom(clusterOnt, x);
											ontM.getClusters().get(k).setManager(manager);
											ontM.getClusters().get(k).setOntology(clusterOnt);
											int r = ontM.getRefineActionOnCluster();
											ontM.setRefineActionOnCluster(r + 1);
										}
									}
								}
							}
						} else if (SuperClassOi instanceof OWLDataMaxCardinality) {
							int sOi = ((OWLDataMaxCardinality) SuperClassOi).getCardinality();
							OWLClassExpression SubclassOm = ontM.getKeyValueEqClass().get(SubClassOi);
							if (SubclassOm == null)
								SubclassOm = SubClassOi;
							Iterator<OWLAxiom> omAxIter = clusterOnt.getReferencingAxioms((OWLEntity) SubclassOm)
									.iterator();
							while (omAxIter.hasNext()) {
								OWLAxiom x = omAxIter.next();
								if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
									OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) axx).getSuperClass();
									OWLClassExpression SubClassOm = ((OWLSubClassOfAxiom) axx).getSubClass();
									if (SuperClassOm instanceof OWLDataMaxCardinality) {
										int sOm = ((OWLDataMaxCardinality) SuperClassOm).getCardinality();
										if ((SubClassOi.equals(SubClassOm)
												|| getMappedClassesforClass(ontM, SubClassOm).contains(SubClassOi))
												&& (sOi != sOm)) {
											changes = changes + "The OWLDataExactCardinality for property: "
													+ SuperClassOm.toString().replace("<", "[").replace(">", "]")
													+ " in the merged ontology was different with the input ontology. So, it sets to the input ontology value. \n";
											// sOm should be set as sOi, but if
											// Oi
											// is the preference one!TODO:check
											// it
											manager = clusterOnt.getOWLOntologyManager();
											factory = manager.getOWLDataFactory();
											OWLDataMaxCardinality cardi = factory.getOWLDataMaxCardinality(sOi,
													(OWLDataPropertyExpression) SubClassOm);
											OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(SubClassOi, cardi);
											manager.addAxiom(clusterOnt, newAxiom);
											manager.removeAxiom(clusterOnt, x);
											ontM.getClusters().get(k).setManager(manager);
											ontM.getClusters().get(k).setOntology(clusterOnt);
											int r = ontM.getRefineActionOnCluster();
											ontM.setRefineActionOnCluster(r + 1);
										}
									}
								}
							}
						} else if (SuperClassOi instanceof OWLDataMinCardinality) {
							int sOi = ((OWLDataMinCardinality) SuperClassOi).getCardinality();
							OWLClassExpression SubclassOm = ontM.getKeyValueEqClass().get(SubClassOi);
							if (SubclassOm == null)
								SubclassOm = SubClassOi;
							Iterator<OWLAxiom> omAxIter = clusterOnt.getReferencingAxioms((OWLEntity) SubclassOm)
									.iterator();
							while (omAxIter.hasNext()) {
								OWLAxiom x = omAxIter.next();
								if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
									OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) axx).getSuperClass();
									OWLClassExpression SubClassOm = ((OWLSubClassOfAxiom) axx).getSubClass();
									if (SuperClassOm instanceof OWLDataMinCardinality) {
										int sOm = ((OWLDataMinCardinality) SuperClassOm).getCardinality();
										if ((SubClassOi.equals(SubClassOm)
												|| getMappedClassesforClass(ontM, SubClassOm).contains(SubClassOi))
												&& (sOi != sOm)) {
											changes = changes + "The OWLDataExactCardinality for property: "
													+ SuperClassOm.toString().replace("<", "[").replace(">", "]")
													+ " in the merged ontology was different with the input ontology. So, it sets to the input ontology value. \n";
											// sOm should be set as sOi, but if
											// Oi
											// is the preference one!TODO:check
											// it
											manager = clusterOnt.getOWLOntologyManager();
											factory = manager.getOWLDataFactory();
											OWLDataMinCardinality cardi = factory.getOWLDataMinCardinality(sOi,
													(OWLDataPropertyExpression) SubClassOm);
											OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(SubClassOi, cardi);
											manager.addAxiom(clusterOnt, newAxiom);
											manager.removeAxiom(clusterOnt, x);
											ontM.getClusters().get(k).setManager(manager);
											ontM.getClusters().get(k).setOntology(clusterOnt);
											int r = ontM.getRefineActionOnCluster();
											ontM.setRefineActionOnCluster(r + 1);
										}
									}
								}
							}
						} else if (SuperClassOi instanceof OWLObjectSomeValuesFrom) {
							// OWLObjectValuesFrom should be preserved same at
							// the
							// merged Ontology. If not, we marked them as
							// something
							// went already wrong for them!
							if (!ontM.getOwlModel().getAxioms().contains(axx)) {
								if (ontM.getEqAxioms().get(axx) == null) {
									changes = changes + "The OWLObjectSomeValuesFrom: "
											+ axx.toString().replace("<", "").replace("<", "")
											+ " restriction from the input ontology has not been preserved in the merged ontolgoy! \n ";
								}
							}
						} else if (SuperClassOi instanceof OWLObjectAllValuesFrom) {
							// OWLObjectValuesFrom should be preserved same at
							// the
							// merged Ontology. If not, we marked them as
							// something
							// went already wrong for them!
							if (!ontM.getOwlModel().getAxioms().contains(axx)) {
								if (ontM.getEqAxioms().get(axx) == null) {
									changes = changes + "The OWLObjectAllValuesFrom: "
											+ axx.toString().replace("<", "").replace("<", "")
											+ " restriction from the input ontology has not been preserved in the merged ontolgoy! \n ";
								}
							}
						}
					}
				}
			}
		}

		if (changes.equals(""))
			changes = "There was not any conflict on all cardinality restriction or OWLObjectSome-AllValuesFrom.";

		MyLogging.log(Level.INFO, changes);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "Property value's constraint refinement step has been done successfully. Total time  "
				+ elapsedTime + " ms. \n");
		return ontM;
	}

	// *********************************************************************************************************
	private Set<OWLClass> getNamedDirectSuperClasses(OWLClass current, OWLOntology model) {
		final Set<OWLClass> dedup = new HashSet<OWLClass>();
		Set<OWLOntology> closure = model.getImportsClosure();
		for (OWLOntology ont : closure) {
			for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSubClass(current)) {
				ax.getSuperClass().accept(new OWLClassExpressionVisitorAdapter() {

					@Override
					public void visit(OWLClass cls) {
						if (cls.isBuiltIn() == true) {// false
							dedup.add(cls);
						}
					}
				});
			}
		}
		return dedup;
	}

	// *********************************************************************************************************
	private OWLObjectProperty[][] FindPropertyDissatisfaction(HModel ontM) {
		OWLObjectProperty[][] res = new OWLObjectProperty[2][ontM.getOwlModel().getClassesInSignature().size()];
		// Step1: Find it
		int mis = 0, multi = 0;
		OWLOntology ont = ontM.getOwlModel();
		Iterator<OWLObjectProperty> plist = ont.getObjectPropertiesInSignature().iterator();
		while (plist.hasNext()) {
			OWLObjectProperty p = plist.next();
			int dom = p.getDomains(ont).size();
			int rang = p.getRanges(ont).size();
			if (dom > 1 || rang > 1) {
				res[0][multi] = p;
				multi++;
			}
			if (dom == 0 || rang == 0) {
				res[1][mis] = p;
				mis++;
			}
		}

		// Step 2: repair it
		// TODO
		return res;
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
	private static OWLClass getClassOiName(OWLOntology Oi, HModel ontM, OWLClass cl) {
		if (Oi.getClassesInSignature().contains(cl))
			return cl;
		ArrayList<HMappedClass> eqClass = ontM.getEqClasses();
		for (int i = 0; i < eqClass.size(); i++) {
			if (eqClass.get(i).getRefClass().equals(cl)) {
				Iterator<OWLClass> s = eqClass.get(i).getMappedCalss().iterator();
				while (s.hasNext()) {
					OWLClass classi = s.next();
					if (Oi.getClassesInSignature().contains(classi))
						return classi;
				}
			}
		}
		return null;
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

	// *********************************************************************************************************
	private static HashSet<OWLAxiom> getRelatedDomainAxioms(Set<OWLClassExpression> domain, OWLObjectProperty obj,
			OWLOntology om) {
		HashSet<OWLAxiom> res = new HashSet<OWLAxiom>();

		Iterator<OWLObjectPropertyDomainAxiom> itr = om.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN).iterator();
		while (itr.hasNext()) {
			OWLObjectPropertyDomainAxiom ax = itr.next();
			OWLObjectPropertyExpression pro = ((OWLObjectPropertyDomainAxiom) ax).getProperty();
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
