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
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.util.OWLEntityRemover;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.divideConquer.HBlockRefine;
import fusion.comerger.algorithm.merger.holisticMerge.general.HSave;
import fusion.comerger.algorithm.merger.holisticMerge.general.Zipper;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedDpro;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedObj;
import fusion.comerger.algorithm.merger.model.HModel;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;

public class HMerging {

	public HModel run(HModel ontM, String selectedUserItem)
			throws OWLOntologyStorageException, OWLOntologyCreationException {
		long startTime = System.currentTimeMillis();

		// 1- merge inside a cluster :intra
		ontM = intraMerge(ontM, selectedUserItem);

		// 2- merge between clusters: inter
		ontM = interMerge(ontM, selectedUserItem);

		// Act with equal elements- TO DO: should go to intraMerge
		// it moves to one level before, i.e. before doing clustering and
		// merging
		// ontM = equalProcess(ontM);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "Merger phase (intra and inter merge) has been done successfully. Total time  "
				+ elapsedTime + " ms. \n");

		StatisticTest.result.put("time_merger(intra_inter)", String.valueOf(elapsedTime));

		// System.gc();
		return ontM;
	}

	/* ****************************************************** */
	private HModel intraMerge(HModel ontM, String selectedUserItem) {
		// refine clusters
		if (selectedUserItem != null && selectedUserItem.contains("localRefinement")) {
			if (ontM.getClusters().size() > 1) {
				// if cluster size is 1, we do not
				// refine this one cluster and only let that global refinements
				// will be done
				HBlockRefine hcr = new HBlockRefine();
				ontM = hcr.run(ontM, selectedUserItem);
			}
		} else {
			String msg = "No refinement has been done after clustering, since local refinement did not select.";
			MyLogging.log(Level.INFO, msg);
		}
		return ontM;
	}

	/* ****************************************************** */
	private HModel interMerge(HModel ontM, String selectedUserItem)
			throws OWLOntologyStorageException, OWLOntologyCreationException {
		// save ont. refine after that

		/*
		 * connect clusters to each other: 1-add all classes properties etc. of
		 * clusters to OM, 2- Anchor Phase: add some of breaking axioms as
		 * anchor between clusters
		 */
		int totalAx = 0;// ontM.getOwlModel().getAxioms().size();
		for (int x = 0; x < ontM.getInputOntNumber(); x++) {
			totalAx = totalAx + ontM.getInputOwlOntModel().get(x).getAxiomCount();
		}
		StatisticTest.result.put("total_axioms", String.valueOf(totalAx));
		if (ontM.getClusters() != null && ontM.getClusters().size() == 1) {
			ontM.SetOwlModel(ontM.getClusters().get(0).getOntology());
			ontM.SetManager(ontM.getClusters().get(0).getManager());
			StatisticTest.result.put("Breaking_isA", "0");
			StatisticTest.result.put("Breaking_other", "0");
		} else if (ontM.getClusters() != null) {

			// 1 -- add all cluster to OM (OM is new ontology)
			OWLOntologyManager OMManager = OWLManager.createOWLOntologyManager();
			File fileM = new File(ontM.getOntName());
			OWLOntology OM = OMManager.createOntology(IRI.create(fileM));
			for (int i = 0; i < ontM.getClusters().size(); i++) {
				OWLOntology ClusterOntology = ontM.getClusters().get(i).getOntology();
				Set<OWLAxiom> ClusterAx = ClusterOntology.getAxioms();
				OMManager.addAxioms(OM, ClusterAx);
			}

			// 2 -- add Anchor
			// Add all without any condition
			Set<OWLAxiom> anchorAxioms = ontM.getISABreakingAxiom();
			if (anchorAxioms != null)
				OMManager.addAxioms(OM, anchorAxioms);
			Set<OWLAxiom> otherAxioms = ontM.getOtherBreakingAxiom();
			if (otherAxioms != null)
				OMManager.addAxioms(OM, otherAxioms);

			int brO = ontM.getOtherBreakingAxiom().size();
			double brp = (double) (brO) / (double) (totalAx) * 100;
			brp = Math.round(brp * 100.0) / 100.0;
			StatisticTest.result.put("Breaking_other_percentage", String.valueOf(brp));
			StatisticTest.result.put("Breaking_other", String.valueOf(brO));

			int brIsa = ontM.getISABreakingAxiom().size();
			double brpIsa = (double) (brIsa) / (double) (totalAx) * 100;
			brpIsa = Math.round(brpIsa * 100.0) / 100.0;
			StatisticTest.result.put("Breaking_isA_percentage", String.valueOf(brpIsa));
			StatisticTest.result.put("Breaking_isA", String.valueOf(brIsa));

			ontM.SetOwlModel(OM);
			ontM.SetManager(OMManager);
		}

		// 3 -- Refine Merge
		HMergeRefine hr = new HMergeRefine();
		ontM = hr.run(ontM, selectedUserItem);

		// 5 -- Save OM
		HSave hs = new HSave();
		ontM = hs.run(ontM, ontM.getMergeOutputType());
		String MergedOntZip = Zipper.zipFiles(ontM.getOntName());
		ontM.setOntZipName(MergedOntZip);

		// 5-1: Save sub-merged ontology
		hs = new HSave();
		hs.runSubMerged(ontM);
		String MergedSubOntZip = Zipper.zipAllFiles(ontM);
		ontM.setSubMergedOntZipName(MergedSubOntZip);

		StatisticTest.result.put("refine_action_on_merge", String.valueOf(ontM.getRefineActionOnMerge()));

		return ontM;
	}

	/* ****************************************************** */
	public static HModel equalProcess(HModel ontM) {
		long startTime = System.currentTimeMillis();

		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLOntology ontology = ontM.getOwlModel();
		Iterator<OWLAxiom> axiter = ontM.getOwlModel().getAxioms().iterator();
		while (axiter.hasNext()) {
			OWLAxiom oldAxiom = axiter.next();
			String axiomType = oldAxiom.getAxiomType().toString();

			switch (axiomType) {
			case "SubClassOf":
				manager = SubClassProcessor(oldAxiom, ontM, ontology, manager, factory);
				break;

			case "ObjectPropertyRange":
				manager = ObjectPropertyRangeProcessor(oldAxiom, ontM, ontology, manager, factory);

				break;

			case "EquivalentClasses":
				manager = EquivalentClassesProcessor(oldAxiom, ontM, ontology, manager, factory);
				break;

			case "ObjectPropertyDomain":
				manager = ObjectPropertyDomainProcessor(oldAxiom, ontM, ontology, manager, factory);
				break;

			case "InverseObjectProperties":
				manager = InverseObjectPropertiesProcessor(oldAxiom, ontM, ontology, manager, factory);
				break;

			case "InverseFunctionalObjectProperty":
				manager = InverseFunctionalObjectPropertyProcessor(oldAxiom, ontM, ontology, manager, factory);
				break;

			case "SubObjectPropertyOf":
				manager = SubObjectPropertyOfProcessor(oldAxiom, ontM, ontology, manager, factory);
				break;

			case "FunctionalDataProperty":
				manager = FunctionalDataPropertyProcessor(oldAxiom, ontM, ontology, manager, factory);

				break;

			case "FunctionalObjectProperty":
				manager = FunctionalObjectPropertyProcessor(oldAxiom, ontM, ontology, manager, factory);

				break;

			case "DisjointClasses":
				manager = DisjointClassesProcessor(oldAxiom, ontM, ontology, manager, factory);
				// AllDisjointProperties?
				// DisjointDataProperties
				// DisjointObjectProperties
				break;

			case "DataPropertyDomain":
				manager = DataPropertyDomainProcessor(oldAxiom, ontM, ontology, manager, factory);
				break;

			case "DataPropertyRange":
				manager = DataPropertyRangeProcessor(oldAxiom, ontM, ontology, manager, factory);
				break;

			case "AnnotationAssertion":
				manager = AnnotationAssertionProcessor(oldAxiom, ontM, ontology, manager, factory);
				break;

			case "ClassAssertion": // instances
				manager = InsatncesProcessor(oldAxiom, ontM, ontology, manager, factory);
				break;

			case "DifferentIndividuals":
				manager = DifferentIndividualsProcessor(oldAxiom, ontM, ontology, manager, factory);
				break;

			case "TransitiveObjectProperty":
				manager = TransitiveObjectPropertyProcessor(oldAxiom, ontM, ontology, manager, factory);
				break;

			case "SymmetricObjectProperty":
				manager = SymmetricObjectPropertyProcessor(oldAxiom, ontM, ontology, manager, factory);
				break;

			default:

			{
				if (axiomType != "Declaration")
					System.out.println("procees me in HMerging" + oldAxiom); // TODO

			}
			}
			ontM.SetManager(manager);
		}

		// remove duplication
		ontM.SetManager(manager);
		ontM = removeEqEntities(ontM, factory);

		// print how many axioms have been rewrites:
		int r = ontM.getNumRewriteAxioms();
		String rewriteInfo = r
				+ " axioms have been rewritten based on the corresponding entities during collapsed process.";
		MyLogging.log(Level.INFO, rewriteInfo);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Replacing the corresponding entities with their reference entities (collapsed process) has been done successfully. Total time: "
						+ elapsedTime + " ms. \n");

		int totalAx = 0;
		for (int x = 0; x < ontM.getInputOntNumber(); x++) {
			totalAx = totalAx + ontM.getInputOwlOntModel().get(x).getAxiomCount();
		}

		double rp = (double) (r) / (double) (totalAx) * 100;
		rp = Math.round(rp * 100.0) / 100.0;

		StatisticTest.result.put("rewritted_axioms", String.valueOf(r));
		StatisticTest.result.put("rewritted_axioms_percentage", String.valueOf(rp));
		StatisticTest.result.put("time_collapsed_process", String.valueOf(elapsedTime));

		return ontM;
	}

	private static OWLOntologyManager SymmetricObjectPropertyProcessor(OWLAxiom oldAxiom, HModel ontM,
			OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory) {
		boolean change = false;
		OWLObjectPropertyExpression subProperty = ((OWLSymmetricObjectPropertyAxiom) oldAxiom).getProperty();
		OWLObjectProperty eqObj = ontM.getKeyValueEqObjProperty().get(subProperty.asOWLObjectProperty());
		if (eqObj != null) {
			subProperty = eqObj;
			change = true;
		}

		if (change) {
			OWLAxiom newAxiom = factory.getOWLTransitiveObjectPropertyAxiom(subProperty);
			manager.removeAxiom(ontology, oldAxiom);
			manager.addAxiom(ontology, newAxiom);
			HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			eqAx.put(oldAxiom, newAxiom);

			ontM.setEqAxioms(eqAx);
			int r = ontM.getNumRewriteAxioms();
			ontM.setNumRewriteAxioms(r + 1);
		}
		return manager;

	}

	private static OWLOntologyManager TransitiveObjectPropertyProcessor(OWLAxiom oldAxiom, HModel ontM,
			OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory) {
		boolean change = false;
		OWLObjectPropertyExpression subProperty = ((OWLTransitiveObjectPropertyAxiom) oldAxiom).getProperty();
		OWLObjectProperty eqObj = ontM.getKeyValueEqObjProperty().get(subProperty.asOWLObjectProperty());
		if (eqObj != null) {
			subProperty = eqObj;
			change = true;
		}

		if (change) {
			OWLAxiom newAxiom = factory.getOWLTransitiveObjectPropertyAxiom(subProperty);
			manager.removeAxiom(ontology, oldAxiom);
			manager.addAxiom(ontology, newAxiom);
			HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			eqAx.put(oldAxiom, newAxiom);

			ontM.setEqAxioms(eqAx);
			int r = ontM.getNumRewriteAxioms();
			ontM.setNumRewriteAxioms(r + 1);
		}

		return manager;
	}

	private static OWLOntologyManager FunctionalObjectPropertyProcessor(OWLAxiom oldAxiom, HModel ontM,
			OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory) {
		boolean change = false;
		OWLObjectPropertyExpression subProperty = ((OWLFunctionalObjectPropertyAxiom) oldAxiom).getProperty();
		OWLObjectProperty eqObj = ontM.getKeyValueEqObjProperty().get(subProperty.asOWLObjectProperty());
		if (eqObj != null) {
			subProperty = eqObj;
			change = true;
		}

		if (change) {
			OWLAxiom newAxiom = factory.getOWLFunctionalObjectPropertyAxiom(subProperty);
			manager.removeAxiom(ontology, oldAxiom);
			manager.addAxiom(ontology, newAxiom);
			HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			eqAx.put(oldAxiom, newAxiom);

			ontM.setEqAxioms(eqAx);
			int r = ontM.getNumRewriteAxioms();
			ontM.setNumRewriteAxioms(r + 1);
		}
		return manager;

	}

	private static OWLOntologyManager FunctionalDataPropertyProcessor(OWLAxiom oldAxiom, HModel ontM,
			OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory) {
		boolean change = false;

		OWLDataPropertyExpression subProperty = ((OWLFunctionalDataPropertyAxiom) oldAxiom).getProperty();
		OWLDataProperty eqObj = ontM.getKeyValueEqDataPro().get(subProperty.asOWLDataProperty());
		if (eqObj != null) {
			subProperty = eqObj;
			change = true;
		}

		if (change) {
			OWLAxiom newAxiom = factory.getOWLFunctionalDataPropertyAxiom(subProperty);
			manager.removeAxiom(ontology, oldAxiom);
			manager.addAxiom(ontology, newAxiom);
			HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			eqAx.put(oldAxiom, newAxiom);

			ontM.setEqAxioms(eqAx);
			int r = ontM.getNumRewriteAxioms();
			ontM.setNumRewriteAxioms(r + 1);
		}
		return manager;

	}

	private static OWLOntologyManager AnnotationAssertionProcessor(OWLAxiom oldAxiom, HModel ontM, OWLOntology ontology,
			OWLOntologyManager manager, OWLDataFactory factory) {
		boolean change = false;

		OWLAnnotationProperty property = ((OWLAnnotationAssertionAxiom) oldAxiom).getProperty();
		OWLAnnotationSubject subject = ((OWLAnnotationAssertionAxiom) oldAxiom).getSubject();
		OWLAnnotationValue value = ((OWLAnnotationAssertionAxiom) oldAxiom).getValue();
		if (subject instanceof IRI) {
			OWLClass cl = factory.getOWLClass((IRI) subject);
			if (cl != null) {
				OWLClass eqCls = ontM.getKeyValueEqClass().get(cl);
				if (eqCls != null) {
					subject = eqCls.getIRI();
					change = true;
				}
			}
		}

		if (change) {
			OWLAxiom newAxiom = factory.getOWLAnnotationAssertionAxiom(property, subject, value);
			manager.removeAxiom(ontology, oldAxiom);
			manager.addAxiom(ontology, newAxiom);
			HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			eqAx.put(oldAxiom, newAxiom);

			ontM.setEqAxioms(eqAx);
			int r = ontM.getNumRewriteAxioms();
			ontM.setNumRewriteAxioms(r + 1);
		}

		return manager;

	}

	private static OWLOntologyManager DataPropertyRangeProcessor(OWLAxiom oldAxiom, HModel ontM, OWLOntology ontology,
			OWLOntologyManager manager, OWLDataFactory factory) {
		boolean change = false;
		Set<OWLDataRange> dataRange = null;
		Set<OWLDataProperty> objList = new HashSet<OWLDataProperty>();
		Iterator<OWLDataProperty> iter = oldAxiom.getDataPropertiesInSignature().iterator();
		while (iter.hasNext()) {
			OWLDataProperty obj = iter.next();
			dataRange = obj.getRanges(ontM.getOwlModel());
			// dataRange is similar to range in the below line
			// Set<OWLDatatype> range = oldAxiom.getDatatypesInSignature();

			OWLDataProperty eqObj = ontM.getKeyValueEqDataPro().get(obj.asOWLDataProperty());
			if (eqObj != null) {
				objList.add(eqObj);
				change = true;
			} else {
				objList.add(obj);
			}
		}

		if (objList.size() > 0 && change && dataRange.size() > 0) {
			Iterator<OWLDataProperty> iter2 = objList.iterator();
			Iterator<OWLDataRange> iter3 = dataRange.iterator();
			OWLDataProperty obj1 = iter2.next();
			OWLDataRange rang1 = iter3.next();
			OWLAxiom newAxiom = factory.getOWLDataPropertyRangeAxiom(obj1, rang1);
			manager.removeAxiom(ontology, oldAxiom);
			manager.addAxiom(ontology, newAxiom);
			HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			eqAx.put(oldAxiom, newAxiom);

			ontM.setEqAxioms(eqAx);
			int r = ontM.getNumRewriteAxioms();
			ontM.setNumRewriteAxioms(r + 1);
		}
		return manager;

	}

	private static OWLOntologyManager SubObjectPropertyOfProcessor(OWLAxiom oldAxiom, HModel ontM, OWLOntology ontology,
			OWLOntologyManager manager, OWLDataFactory factory) {
		boolean change = false;
		OWLObjectPropertyExpression subProperty = ((OWLSubObjectPropertyOfAxiom) oldAxiom).getSubProperty();
		OWLObjectPropertyExpression superProperty = ((OWLSubObjectPropertyOfAxiom) oldAxiom).getSuperProperty();
		OWLObjectProperty eqSubObj = ontM.getKeyValueEqObjProperty().get(subProperty.asOWLObjectProperty());
		if (eqSubObj != null) {
			subProperty = eqSubObj;
			change = true;
		}

		OWLObjectProperty eqSuperObj = ontM.getKeyValueEqObjProperty().get(subProperty.asOWLObjectProperty());
		if (eqSuperObj != null) {
			superProperty = eqSuperObj;
			change = true;
		}

		if (change) {
			OWLAxiom newAxiom = factory.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty);
			manager.removeAxiom(ontology, oldAxiom);
			manager.addAxiom(ontology, newAxiom);
			HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			eqAx.put(oldAxiom, newAxiom);

			ontM.setEqAxioms(eqAx);
			int r = ontM.getNumRewriteAxioms();
			ontM.setNumRewriteAxioms(r + 1);
		}
		return manager;

	}

	private static OWLOntologyManager InverseFunctionalObjectPropertyProcessor(OWLAxiom oldAxiom, HModel ontM,
			OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory) {
		boolean change = false;
		Set<OWLObjectProperty> objList = new HashSet<OWLObjectProperty>();
		Iterator<OWLObjectProperty> iter = oldAxiom.getObjectPropertiesInSignature().iterator();
		while (iter.hasNext()) {
			OWLObjectProperty obj = iter.next();
			OWLObjectProperty eqObj = ontM.getKeyValueEqObjProperty().get(obj.asOWLObjectProperty());
			if (eqObj != null) {
				objList.add(eqObj);
				change = true;
			} else {
				objList.add(obj);
			}
		}
		if (objList.size() > 0 && change) {
			Iterator<OWLObjectProperty> iter2 = objList.iterator();
			OWLObjectProperty obj1 = iter2.next();
			OWLAxiom newAxiom = factory.getOWLInverseFunctionalObjectPropertyAxiom(obj1);
			manager.removeAxiom(ontology, oldAxiom);
			manager.addAxiom(ontology, newAxiom);
			HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			eqAx.put(oldAxiom, newAxiom);

			ontM.setEqAxioms(eqAx);
			int r = ontM.getNumRewriteAxioms();
			ontM.setNumRewriteAxioms(r + 1);
		}
		return manager;
	}

	private static OWLOntologyManager InverseObjectPropertiesProcessor(OWLAxiom oldAxiom, HModel ontM,
			OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory) {
		boolean change = false;
		Set<OWLObjectProperty> objList = new HashSet<OWLObjectProperty>();
		Iterator<OWLObjectProperty> iter = oldAxiom.getObjectPropertiesInSignature().iterator();
		while (iter.hasNext()) {
			OWLObjectProperty obj = iter.next();
			OWLObjectProperty eqObj = ontM.getKeyValueEqObjProperty().get(obj.asOWLObjectProperty());
			if (eqObj != null) {
				objList.add(eqObj);
				change = true;
			} else {
				objList.add(obj);
			}
		}
		if (objList.size() > 1 && change) {
			Iterator<OWLObjectProperty> iter2 = objList.iterator();
			OWLObjectProperty obj1 = iter2.next();
			OWLObjectProperty obj2 = iter2.next();
			OWLAxiom newAxiom = factory.getOWLInverseObjectPropertiesAxiom(obj1, obj2);
			manager.removeAxiom(ontology, oldAxiom);
			manager.addAxiom(ontology, newAxiom);
			HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			eqAx.put(oldAxiom, newAxiom);

			ontM.setEqAxioms(eqAx);
			int r = ontM.getNumRewriteAxioms();
			ontM.setNumRewriteAxioms(r + 1);
		}
		return manager;
	}

	private static OWLOntologyManager DifferentIndividualsProcessor(OWLAxiom oldAxiom, HModel ontM,
			OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory) {
		System.out.println(oldAxiom + " process me in DifferentIndividualsProcessor in HMerging.java");
		// TODO
		return manager;
	}

	private static OWLOntologyManager InsatncesProcessor(OWLAxiom oldAxiom, HModel ontM, OWLOntology ontology,
			OWLOntologyManager manager, OWLDataFactory factory) {
		OWLClassAssertionAxiom axx = (OWLClassAssertionAxiom) oldAxiom;
		OWLClassExpression c = ((OWLClassAssertionAxiom) oldAxiom).getClassExpression();
		OWLIndividual ind = axx.getIndividual();

		if (c instanceof OWLClass) {
			OWLClass cc = c.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(cc);
			if (refClass != null) {
				OWLAxiom newAxiom = factory.getOWLClassAssertionAxiom(refClass, ind);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
			}
		} else if (c instanceof OWLObjectSomeValuesFrom) {
			System.out.println("unprocess axioms:" + oldAxiom);
		}

		return manager;
	}

	private static OWLOntologyManager EquivalentClassesProcessor(OWLAxiom oldAxiom, HModel ontM, OWLOntology ontology,
			OWLOntologyManager manager, OWLDataFactory factory) {

		boolean changes = false;
		OWLClassExpression allCinGetOperand = null;
		OWLClass eqClass1 = null;
		OWLObjectExactCardinality cardi = null;
		OWLObjectMinCardinality cardiMin = null;
		Set<OWLClass> eqClass2 = new HashSet<OWLClass>();
		for (OWLClassExpression cls : ((OWLEquivalentClassesAxiom) oldAxiom).getClassExpressions()) {
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
					System.out.println("Process me in HMerging_EquivalentClassesProcessor, type of " + cls);
				}
			}
		}
		// here we have all
		OWLAxiom newAxiom = null;
		if (changes && eqClass1 != null && allCinGetOperand != null) {
			newAxiom = factory.getOWLEquivalentClassesAxiom(eqClass1, allCinGetOperand);
		} else if (changes && eqClass1 != null && cardi != null) {
			newAxiom = factory.getOWLEquivalentClassesAxiom(eqClass1, cardi);
		} else if (changes && eqClass1 != null && cardiMin != null) {
			newAxiom = factory.getOWLEquivalentClassesAxiom(eqClass1, cardiMin);
		}
		if (newAxiom != null) {
			manager.removeAxiom(ontology, oldAxiom);
			manager.addAxiom(ontology, newAxiom);
			HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			eqAx.put(oldAxiom, newAxiom);

			ontM.setEqAxioms(eqAx);
			int r = ontM.getNumRewriteAxioms();
			ontM.setNumRewriteAxioms(r + 1);
		}
		return manager;
	}

	private static OWLOntologyManager DisjointClassesProcessor(OWLAxiom oldAxiom, HModel ontM, OWLOntology ontology,
			OWLOntologyManager manager, OWLDataFactory factory) {

		boolean changes = false;
		Set<OWLClass> clist = oldAxiom.getClassesInSignature();
		Iterator<OWLClass> iter = clist.iterator();
		OWLClass cls1 = iter.next();
		OWLClass eqC1 = cls1;
		OWLClass refClass1 = ontM.getKeyValueEqClass().get(cls1);
		if (refClass1 != null) {
			eqC1 = refClass1;
			changes = true;
		}

		if (clist.size() > 1) {
			OWLClass cls2 = iter.next();
			OWLClass eqC2 = cls2;
			OWLClass refClass2 = ontM.getKeyValueEqClass().get(cls2);
			if (refClass2 != null) {
				eqC2 = refClass2;
				changes = true;
			}

			if (changes) {
				OWLDisjointClassesAxiom newAxiom = factory.getOWLDisjointClassesAxiom(eqC1, eqC2);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
			}
		}

		return manager;
	}

	private static OWLOntologyManager DataPropertyDomainProcessor(OWLAxiom oldAxiom, HModel ontM, OWLOntology ontology,
			OWLOntologyManager manager, OWLDataFactory factory) {
		Iterator<OWLDataProperty> pro = oldAxiom.getDataPropertiesInSignature().iterator();
		OWLDataProperty prop = pro.next();
		OWLClassExpression dom = ((OWLDataPropertyDomainAxiom) oldAxiom).getDomain();

		OWLDataProperty eqPro = prop;
		OWLDataProperty refDpro = ontM.getKeyValueEqDataPro().get(prop.asOWLDataProperty());
		if (refDpro != null) {
			eqPro = refDpro;
		}

		if (dom instanceof OWLClassImpl) {
			OWLClass eqDom = dom.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(dom.asOWLClass());
			if (refClass != null) {
				eqDom = refClass;
			}
			OWLAxiom newAxiom = factory.getOWLDataPropertyDomainAxiom(eqPro, eqDom);
			manager.removeAxiom(ontology, oldAxiom);
			manager.addAxiom(ontology, newAxiom);
			HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			eqAx.put(oldAxiom, newAxiom);

			ontM.setEqAxioms(eqAx);
			int r = ontM.getNumRewriteAxioms();
			ontM.setNumRewriteAxioms(r + 1);

		} else if (dom instanceof OWLObjectUnionOf) {

			Set<OWLClassExpression> tempObj = new HashSet<OWLClassExpression>();
			Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) dom).getOperands().iterator();
			while (rangExist.hasNext()) {
				OWLClassExpression ex = rangExist.next();
				if (ex instanceof OWLClassImpl) {
					OWLClass eq = ex.asOWLClass();
					OWLClass refClass = ontM.getKeyValueEqClass().get(eq);
					if (refClass != null) {
						eq = refClass;
					}

					tempObj.add(eq);
				}
			}
			OWLObjectUnionOf ob = factory.getOWLObjectUnionOf(tempObj);
			OWLAxiom newAxiom = factory.getOWLDataPropertyDomainAxiom(eqPro, ob);
			manager.removeAxiom(ontology, oldAxiom);
			manager.addAxiom(ontology, newAxiom);
			HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			eqAx.put(oldAxiom, newAxiom);

			ontM.setEqAxioms(eqAx);
			int r = ontM.getNumRewriteAxioms();
			ontM.setNumRewriteAxioms(r + 1);

		}

		return manager;
	}

	private static OWLOntologyManager SubClassProcessor(OWLAxiom oldAxiom, HModel ontM, OWLOntology ontology,
			OWLOntologyManager manager, OWLDataFactory factory) {

		OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) oldAxiom).getSuperClass();
		OWLClassExpression SubClass = ((OWLSubClassOfAxiom) oldAxiom).getSubClass();
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

			if (changes) {
				OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, eqSup);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
			}
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
				OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, ob);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
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
				OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
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
				OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
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
				OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
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
				OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
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
				OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
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
			if (changes) {
				OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, sv);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
			}
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
			if (changes) {
				OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, sv);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
			}
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
				OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
			}
		} else {
			System.out.println("Process me in HMerging.java in SubClassProcessor");
		}
		return manager;
	}

	private static OWLOntologyManager ObjectPropertyRangeProcessor(OWLAxiom oldAxiom, HModel ontM, OWLOntology ontology,
			OWLOntologyManager manager, OWLDataFactory factory) {
		OWLObjectPropertyExpression pro = ((OWLObjectPropertyRangeAxiom) oldAxiom).getProperty();
		OWLClassExpression rang = ((OWLObjectPropertyRangeAxiom) oldAxiom).getRange();

		boolean changes = false;

		OWLObjectPropertyExpression eqPro = pro;
		OWLObjectProperty refObj = ontM.getKeyValueEqObjProperty().get(pro.asOWLObjectProperty());
		if (refObj != null) {
			eqPro = refObj;
			changes = true;
		}

		if (rang instanceof OWLClassImpl) {
			OWLClass eqRang = rang.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(rang.asOWLClass());
			if (refClass != null) {
				eqRang = refClass;
				changes = true;
			}

			OWLAxiom newAxiom = factory.getOWLObjectPropertyRangeAxiom(eqPro, eqRang);
			manager.removeAxiom(ontology, oldAxiom);
			manager.addAxiom(ontology, newAxiom);
			HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			eqAx.put(oldAxiom, newAxiom);

			ontM.setEqAxioms(eqAx);
			int r = ontM.getNumRewriteAxioms();
			ontM.setNumRewriteAxioms(r + 1);

		} else if (rang instanceof OWLObjectUnionOf) {
			Set<OWLClassExpression> tempObj = new HashSet<OWLClassExpression>();
			Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) rang).getOperands().iterator();
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
			if (changes) {
				OWLObjectUnionOf ob = factory.getOWLObjectUnionOf(tempObj);
				OWLAxiom newAxiom = factory.getOWLObjectPropertyRangeAxiom(eqPro, ob);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
			}
		} else {
			// never happened
		}

		return manager;
	}

	private static OWLOntologyManager ObjectPropertyDomainProcessor(OWLAxiom oldAxiom, HModel ontM,
			OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory) {

		OWLObjectPropertyExpression pro = ((OWLObjectPropertyDomainAxiom) oldAxiom).getProperty();
		OWLClassExpression dom = ((OWLObjectPropertyDomainAxiom) oldAxiom).getDomain();
		boolean changes = false;

		OWLObjectPropertyExpression eqPro = pro;
		OWLObjectProperty refObj = ontM.getKeyValueEqObjProperty().get(pro.asOWLObjectProperty());
		if (refObj != null) {
			eqPro = refObj;
			changes = true;
		}

		// TO DO: check manner of ObjectUnion
		if (dom instanceof OWLClassImpl) {
			OWLClass eqDom = dom.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(dom.asOWLClass());
			if (refClass != null) {
				eqDom = refClass;
				changes = true;
			}
			if (changes) {
				OWLAxiom newAxiom = factory.getOWLObjectPropertyDomainAxiom(eqPro, eqDom);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
			}
		} else if (dom instanceof OWLObjectUnionOf) {

			Set<OWLClassExpression> tempObj = new HashSet<OWLClassExpression>();
			Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) dom).getOperands().iterator();
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
			if (changes) {
				OWLObjectUnionOf ob = factory.getOWLObjectUnionOf(tempObj);
				OWLAxiom newAxiom = factory.getOWLObjectPropertyDomainAxiom(eqPro, ob);
				manager.removeAxiom(ontology, oldAxiom);
				manager.addAxiom(ontology, newAxiom);
				HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
				eqAx.put(oldAxiom, newAxiom);

				ontM.setEqAxioms(eqAx);
				int r = ontM.getNumRewriteAxioms();
				ontM.setNumRewriteAxioms(r + 1);
			}
		} else {
			// never happened
		}

		return manager;

	}

	public static HModel removeEqEntities(HModel ontM, OWLDataFactory factory) {

		OWLOntology ontology = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();

		OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(ontology));

		ArrayList<HMappedClass> classList = ontM.getEqClasses();
		for (int i = 0; i < classList.size(); i++) {
			Iterator<OWLClass> iter = classList.get(i).getMappedCalss().iterator();
			while (iter.hasNext()) {
				OWLClass c = iter.next();
				if (!c.equals(classList.get(i).getRefClass())) {
					c.accept(remover);
				}
			}
			OWLClass refClass = classList.get(i).getRefClass();
			OWLAxiom decAx = factory.getOWLDeclarationAxiom(refClass);
			if (!ontology.getAxioms(AxiomType.DECLARATION).contains(decAx))
				manager.addAxiom(ontology, decAx);
		}
		manager.applyChanges(remover.getChanges());

		ArrayList<HMappedObj> objList = ontM.getEqObjProperties();
		for (int i = 0; i < objList.size(); i++) {
			Iterator<OWLObjectProperty> iter = objList.get(i).getMappedObj().iterator();
			while (iter.hasNext()) {
				OWLObjectProperty c = iter.next();
				if (!c.equals(objList.get(i).getRefObj())) {
					c.accept(remover);
				}
			}
		}
		manager.applyChanges(remover.getChanges());

		ArrayList<HMappedDpro> objDList = ontM.getEqDataProperties();
		for (int i = 0; i < objDList.size(); i++) {
			Iterator<OWLDataProperty> iter = objDList.get(i).getMappedDpro().iterator();
			while (iter.hasNext()) {
				OWLDataProperty c = iter.next();
				if (!c.equals(objDList.get(i).getRefDpro())) {
					c.accept(remover);
				}
			}
		}

		manager.applyChanges(remover.getChanges());

		ontM.SetOwlModel(ontology);
		ontM.SetManager(manager);
		return ontM;

	}
}
