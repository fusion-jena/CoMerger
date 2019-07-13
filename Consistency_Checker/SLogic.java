package fusion.comerger.algorithm.merger.holisticMerge.consistency;
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
