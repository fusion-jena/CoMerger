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
 
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import fusion.comerger.algorithm.merger.model.HModel;

public class ClusterShareFunc {
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

}
