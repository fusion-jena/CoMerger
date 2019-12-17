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
 
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import org.junit.Assert;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import fusion.comerger.algorithm.merger.model.HModel;

public class Entailmenter {

	public static void main(String[] args) {

		String OntName1 = "C:\\LOCAL_FOLDER\\cmt.owl";
		String OntName2 = "C:\\LOCAL_FOLDER\\conference.owl";
		String OntM = "C:\\LOCAL_FOLDER\\MergedOnt652.owl";
		File file1 = new File(OntName1);
		File file2 = new File(OntName2);
		File fileM = new File(OntM);

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ont1 = null, ont2 = null, ontM = null;

		try {
			ont1 = manager.loadOntologyFromOntologyDocument(file1);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		try {
			ont2 = manager.loadOntologyFromOntologyDocument(file2);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

		try {
			ontM = manager.loadOntologyFromOntologyDocument(fileM);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

	}

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
				if (eqAx != null)
					pos = eqAx;

				try {
					Assert.assertTrue(reasoner.isEntailed(pos));
					trueAnsSubsumption++;
					/*
					 * assertTrue: Asserts that a condition is true. If it isn't
					 * it throws an AssertionError without a message.
					 */

				} catch (AssertionError e) {
					// e.printStackTrace();
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

}
