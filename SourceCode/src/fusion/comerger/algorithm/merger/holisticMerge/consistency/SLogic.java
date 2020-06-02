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

public class SLogic {
	ArrayList<Opinion> W;

	public SLogic(ArrayList<Opinion> res) {
		this.W = res;
	}

	public SLogic() {
	}

	public Opinion getConsensus() {
		Opinion P = new Opinion();
		if (W.size() > 0) {
			P = W.get(0);

			for (int i = 1; i < W.size(); i++) {
				Opinion Q = W.get(i);
				double k = P.getUncertainty() + Q.getUncertainty() - (P.getUncertainty() * Q.getUncertainty());
				if (k == 0)
					k = 1.0;
				double a = 0.0;
				if (P.getaPriori() == 1.0 && Q.getaPriori() == 1) {
					a = (P.getaPriori() + Q.getaPriori()) / 2;
				} else {
					if (P.getUncertainty() != 0 && Q.getUncertainty() != 0) {
						a = (P.getaPriori() * Q.getUncertainty() + Q.getaPriori() * P.getUncertainty()
								- (P.getaPriori() + Q.getaPriori()) * P.getUncertainty() * Q.getUncertainty())
								/ (P.getUncertainty() + Q.getUncertainty()
										- 2 * P.getUncertainty() * Q.getUncertainty());
					}
				}
				double b = (P.getBelieve() * Q.getUncertainty() + Q.getBelieve() * P.getUncertainty()) / k;
				double d = (P.getDisbelieve() * Q.getUncertainty() + Q.getDisbelieve() * P.getUncertainty()) / k;
				double u = (P.getUncertainty() * Q.getUncertainty()) / k;
				P.setBelieve(b);
				P.setDisbelieve(d);
				P.setUncertainty(u);
				P.setaPriori(a);
			}
		}
		return P;
	}

}
