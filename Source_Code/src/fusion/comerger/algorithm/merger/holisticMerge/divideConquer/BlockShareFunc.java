package fusion.comerger.algorithm.merger.holisticMerge.divideConquer;

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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.model.HModel;

public class BlockShareFunc {
	public static int findClusterOfClass(OWLClassExpression father, HModel ontM) {
		for (int i = 0; i < ontM.getClusters().size(); i++) {
			if (ontM.getClusters().get(i).getClasses().contains(father))
				return i;
		}

		return -1;
	}

	public static int findClusterOfPro(OWLObjectProperty cm, HModel ontM) {
		for (int i = 0; i < ontM.getClusters().size(); i++) {
			if (ontM.getClusters().get(i).getOntology().getObjectPropertiesInSignature().contains(cm))
				return i;
		}

		return -1;
	}

	public static int findClusterOfDro(OWLDataProperty cm, HModel ontM) {
		for (int i = 0; i < ontM.getClusters().size(); i++) {
			if (ontM.getClusters().get(i).getOntology().getDataPropertiesInSignature().contains(cm))
				return i;
		}

		return -1;
	}

	public static int findClusterOfIndiv(OWLNamedIndividual cm, HModel ontM) {
		for (int i = 0; i < ontM.getClusters().size(); i++) {
			if (ontM.getClusters().get(i).getOntology().getIndividualsInSignature().contains(cm))
				return i;
		}

		return -1;
	}

	public static int findClusterOfFuncPro(OWLDataProperty obj, HModel ontM) {
		for (int i = 0; i < ontM.getClusters().size(); i++) {
			Iterator<OWLFunctionalDataPropertyAxiom> iter = ontM.getClusters().get(i).getOntology()
					.getAxioms(AxiomType.FUNCTIONAL_DATA_PROPERTY).iterator();
			while (iter.hasNext()) {
				OWLFunctionalDataPropertyAxiom ax = iter.next();
				OWLDataPropertyExpression pro = ((OWLFunctionalDataPropertyAxiom) ax).getProperty();
				if (pro.equals(obj))
					return i;
			}

		}

		return -1;
	}

	/* ************************************************ */
	public static Set<OWLClassExpression> compareEquality(Set<OWLClassExpression> ciSet, Set<OWLClassExpression> cmSet,
			HModel ontM) {
		Set<OWLClassExpression> res = new HashSet<OWLClassExpression>();

		if ((ciSet.size() != 0 && cmSet.size() == 0) || (ciSet.size() != 0 && cmSet.size() == 0))
			return res;

		Iterator<OWLClassExpression> iterCi = ciSet.iterator();
		while (iterCi.hasNext()) {
			OWLClassExpression ci = iterCi.next();
			OWLClass ciEqual = ontM.getKeyValueEqClass().get(ci);

			boolean find = false;
			Iterator<OWLClassExpression> iterCm = cmSet.iterator();
			while (iterCm.hasNext()) {
				OWLClassExpression cm = iterCm.next();
				if (ci.toString().equals(cm.toString())) {
					find = true;
					break;
				} else if (ciEqual != null && ciEqual.toString().equals(cm.toString())) {
					find = true;
					break;
				}
			}
			if (!find) {
				res.add(ci);
			}
		}
		return res;
	}

	public static int findClusterOfAxiom(OWLAxiom ax, HModel ontM) {
		for (int i = 0; i < ontM.getClusters().size(); i++) {
			if (ontM.getClusters().get(i).getOntology().getAxioms().contains(ax))
				return i;
		}
		return -1;
	}

	public static HashMap<OWLClassExpression, HashSet<OWLClassExpression>> createAdjacentClassesList(HModel ontM) {
		long startTime = System.currentTimeMillis();
		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> res = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();

		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> listParent = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();
		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> listChild = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();
		HashSet<OWLClassExpression> temp = new HashSet<OWLClassExpression>();

		Iterator<OWLSubClassOfAxiom> is_a = ontM.getOwlModel().getAxioms(AxiomType.SUBCLASS_OF).iterator();

		while (is_a.hasNext()) {

			OWLSubClassOfAxiom axiom = is_a.next();
			OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) axiom).getSuperClass();// parent
			OWLClassExpression SubClass = ((OWLSubClassOfAxiom) axiom).getSubClass();// child

			// if (SuperClass instanceof OWLClassImpl && SubClass instanceof
			// OWLClassImpl) {
			// add parent to the child list
			temp = new HashSet<OWLClassExpression>();
			HashSet<OWLClassExpression> cc = listParent.get(SubClass);
			if (cc == null || cc.size() < 1) {
				// it means this class was not already in the list of
				// adjacent
				temp.add(SuperClass);
				listParent.put(SubClass, temp);
			} else {
				cc.add(SuperClass);// never arrive here
				listParent.put(SubClass, cc);
			}
			// add child to parent
			temp = new HashSet<OWLClassExpression>();
			HashSet<OWLClassExpression> ccc = listChild.get(SuperClass);
			if (ccc == null || ccc.size() < 1) {
				// it means this class was not already in the list of
				// adjacent
				temp.add(SubClass);
				listChild.put(SuperClass, temp);
			} else {
				ccc.add(SubClass);// never arrive here
				listChild.put(SuperClass, ccc);
			}
			// }
		}

		ontM.setParentList(listParent);
		ontM.setChildList(listChild);
		ontM.setAlterStatus(false);

		res.putAll(listParent);
		res.putAll(listChild);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"A list of adjacent classes has been successfully created. Total time: " + elapsedTime + " ms. \n");
		return res;
	}
}
