package fusion.comerger.algorithm.merger.holisticMerge.mapping;
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
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;

public class HMappedClass {
	OWLClass refClass;
	Set<OWLClass> mappedClass = new HashSet<OWLClass>();
	int LenClass = 0;
	double allConnection = 0.0;
	double isaConnection = 0.0;
	Double goodness = 0.0;
	Double centrality = 0.0;
	boolean selected = false;

	public OWLClass getRefClass() {
		return refClass;
	}

	public void setRefClass(OWLClass cl) {
		refClass = cl;
	}

	public Set<OWLClass> getMappedCalss() {
		return mappedClass;
	}

	public void setMappedClass(Set<OWLClass> cList) {
		mappedClass = cList;
	}

	public int getLenClass() {
		return LenClass;
	}

	public void setLenClass(int L) {
		LenClass = L;
	}

	public double getAllConnection() {
		return allConnection;
	}

	public void setAllConnection(double c) {
		allConnection = c;
	}

	public double getIsaConnection() {
		return isaConnection;
	}

	public void setIsaConnection(double c) {
		isaConnection = c;
	}

	public void setGoodness(Double g) {
		goodness = g;

	}

	public double getGoodness() {
		return goodness;

	}

	public void setCentrality(Double cc) {
		centrality = cc;
	}

	public Double getCentrality() {
		return centrality;
	}

	public void setSelectedStatus(boolean b) {
		selected = b;
	}

	public boolean getSelectedStatus() {
		return selected;
	}
}
