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

import org.semanticweb.owlapi.model.OWLAxiom;

public class Opinion {
	double believe;
	double disbelieve;
	double uncertainty;
	double aPriori;
	double trust;
	double normTrust;
	OWLAxiom axiom;
	String ID;

	public Opinion(double b, double d, double u, double a) {
		this.believe = b;
		this.disbelieve = d;
		this.uncertainty = u;
		this.aPriori = a;
	}

	public Opinion() {
	}

	public double getBelieve() {
		return believe;
	}

	public void setBelieve(double b) {
		believe = b;
	}

	public double getDisbelieve() {
		return disbelieve;
	}

	public void setDisbelieve(double d) {
		disbelieve = d;
	}

	public double getUncertainty() {
		return uncertainty;
	}

	public void setUncertainty(double u) {
		uncertainty = u;
	}

	public double getaPriori() {
		return aPriori;
	}

	public void setaPriori(double a) {
		aPriori = a;
	}

	public double computeTrust() {
		trust = believe + aPriori * uncertainty;
		return Math.round(trust * 1000.0) / 1000.0;
	}

	public double getTrust() {
		return trust;
	}

	public void setTrust(double t) {
		trust = t;
	}

	public double getNormTrust() {
		return normTrust;
	}

	public void setNormTrust(double t) {
		normTrust = t;
	}

	public OWLAxiom getAxiom() {
		return axiom;
	}

	public void setAxiom(OWLAxiom ax) {
		axiom = ax;
	}

	public void setAxiomID(String uniqueID) {
		ID = uniqueID;
	}

	public String getAxiomID() {
		return ID;
	}
}