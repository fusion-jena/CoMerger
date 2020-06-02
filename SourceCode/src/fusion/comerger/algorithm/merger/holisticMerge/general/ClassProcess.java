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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;

import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;

public class ClassProcess {

	public static Set<OWLClass> getAllSupClasses(OWLClass cl, OWLOntology Om) {
		Set<OWLClass> res = new HashSet<OWLClass>();
		Set<OWLClassExpression> list = cl.getSuperClasses(Om);
		Iterator<OWLClassExpression> iterList = list.iterator();
		Set<OWLClassExpression> newList = new HashSet<OWLClassExpression>();
		Set<OWLClassExpression> L = new HashSet<OWLClassExpression>();
		L = list;

		while (L.size() > 0) {
			while (iterList.hasNext()) {
				OWLClassExpression cc = iterList.next();
				if (cc instanceof OWLClassImpl) {
					OWLClass ccc = cc.asOWLClass();
					res.add(ccc);
					newList.addAll(ccc.getSuperClasses(Om));
				}
			}

			L = newList;
			iterList = newList.iterator();
			newList = new HashSet<OWLClassExpression>();
		}

		return res;

	}

	public static Set<OWLClass> getAllSubClasses(OWLClass cl, OWLOntology Om) {
		Set<OWLClass> res = new HashSet<OWLClass>();
		Set<OWLClassExpression> list = cl.getSubClasses(Om);
		Iterator<OWLClassExpression> iterList = list.iterator();
		Set<OWLClassExpression> newList = new HashSet<OWLClassExpression>();
		Set<OWLClassExpression> L = new HashSet<OWLClassExpression>();
		L = list;

		while (L.size() > 0) {
			while (iterList.hasNext()) {
				OWLClassExpression cc = iterList.next();
				if (cc instanceof OWLClassImpl) {
					OWLClass ccc = cc.asOWLClass();
					res.add(ccc);
					newList.addAll(ccc.getSubClasses(Om));
				}
			}

			L = newList;
			iterList = newList.iterator();
			newList = new HashSet<OWLClassExpression>();
		}

		return res;
	}

}
