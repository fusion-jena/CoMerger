package fusion.comerger.algorithm.merger.holisticMerge.GMR;
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

public class RuleSets {
	ArrayList<ArrayList<Integer>> ruleSets;
	ArrayList<Integer> bestSet;
	String message;

	public void setAllRuleSets(ArrayList<ArrayList<Integer>> rs) {
		ruleSets = rs;
	}

	public ArrayList<ArrayList<Integer>> getAllRuleSets() {
		return ruleSets;
	}

	public void setBestSet(ArrayList<Integer> Brs) {
		bestSet = Brs;
	}

	public ArrayList<Integer> getBestSet() {
		return bestSet;
	}

	public void setMessage(String msg) {
		message = msg;
	}

	public String getMessage() {
		return message;
	}

}
