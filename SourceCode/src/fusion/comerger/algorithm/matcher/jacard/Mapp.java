package fusion.comerger.algorithm.matcher.jacard;
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
public class Mapp {
	String ent1;
	String ent2;
	double sim;

	public void setEntity1(String e1) {
		ent1 = e1;
	}

	public void setEntity2(String e2) {
		ent2 = e2;
	}

	public void setSim(double s) {
		sim = s;
	}

	public String getEntity1 (){
		return ent1;
	}
	
	public String getEntity2 (){
		return ent2;
	}
	
	public double getSim (){
		return sim;
	}
}
