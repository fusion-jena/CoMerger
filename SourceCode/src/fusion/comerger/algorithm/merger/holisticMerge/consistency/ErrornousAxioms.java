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
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;

public class ErrornousAxioms {
	int id;
	OWLClass cl;
	Set<Set<OWLAxiom>> errAx = new HashSet<Set<OWLAxiom>>();
	ArrayList<ArrayList<Opinion>> rankedAxioms;

	public int getID() {
		return id;
	}

	public void setID(int i) {
		id = i;
	}

	public OWLClass getErrorClass() {
		return cl;
	}

	public void setErrorClass(OWLClass c) {
		cl = c;
	}

	public Set<Set<OWLAxiom>> getErrorAxioms() {
		return errAx;
	}

	public void setErrorAxioms(Set<Set<OWLAxiom>> ex) {
		errAx = ex;
	}

	public ArrayList<ArrayList<Opinion>> getRankedAxioms() {
		return rankedAxioms;
	}

	public void setRankedAxioms(ArrayList<ArrayList<Opinion>> rEx) {
		rankedAxioms = rEx;
	}
}
