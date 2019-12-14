package fusion.comerger.algorithm.merger.holisticMerge.consistency;

/**
 * CoMerger: Holistic Multiple Ontology Merger. Consistency checker sub package
 * based on the Subjective Logic theory
 * 
 * @author Samira Babalou (samira[dot]babalou[at]uni_jean[dot]de)
 */

public class DeductionSL {
	// This class implement the conditional Subjective logic Theory

	public static void main(String[] args) {
	}

	public Opinion getWyx(Opinion y, Opinion x) {
		Opinion w = new Opinion();
		w.setBelieve(x.getBelieve() / y.getBelieve());
		w.setDisbelieve(x.getDisbelieve() / y.getDisbelieve());
		w.setUncertainty(x.getUncertainty() / y.getUncertainty());
		w.setaPriori(x.getaPriori() / y.getaPriori());

		return w;
	}

	public Opinion getWyxx(Opinion y, Opinion x) {
		Opinion w = new Opinion();
		w.setBelieve(x.getBelieve() / (1 - y.getBelieve()));
		w.setDisbelieve(x.getDisbelieve() / (1 - y.getDisbelieve()));
		w.setUncertainty(x.getUncertainty() / (1 - y.getUncertainty()));
		w.setaPriori(x.getaPriori() / (1 - y.getaPriori()));

		return w;
	}

	public Opinion getDeduction(Opinion wx, Opinion w_yx, Opinion w_yxx) {
		Opinion P = new Opinion();
		double bI = 0.0, dI = 0.0, uI = 0.0, aI = 0.0;
		double ay = wx.getaPriori();
		bI = (wx.getBelieve() * w_yx.getBelieve() + wx.getDisbelieve() * w_yxx.getBelieve() + wx.getUncertainty()
				* (w_yx.getBelieve() * wx.getaPriori() + w_yxx.getBelieve() * (1 - wx.getaPriori())));

		dI = (wx.getBelieve() * w_yx.getDisbelieve() + wx.getDisbelieve() * w_yxx.getDisbelieve() + wx.getUncertainty()
				* (w_yx.getDisbelieve() * wx.getaPriori() + w_yxx.getDisbelieve() * (1 - wx.getaPriori())));

		uI = (wx.getBelieve() * w_yx.getUncertainty() + wx.getDisbelieve() * w_yxx.getUncertainty()
				+ wx.getUncertainty()
						* (w_yx.getUncertainty() * wx.getaPriori() + w_yxx.getUncertainty() * (1 - wx.getaPriori())));

		double KK = detectCase(wx, ay, w_yx, w_yxx, bI, dI, uI);

		bI = bI - ay * KK;
		dI = dI - (1 - ay) * KK;
		uI = uI + KK;
		aI = ay;

		P.setBelieve(Math.abs(bI));
		P.setDisbelieve(Math.abs(dI));
		P.setUncertainty(Math.abs(uI));
		P.setaPriori(Math.abs(aI));
		P.setAxiom(wx.getAxiom());
		P.setTrust(Math.abs(P.getTrust()));

		return P;
	}

	private double detectCase(Opinion wx, double ay, Opinion w_yx, Opinion w_yxx, double bI, double dI, double uI) {
		double KK = 0.0;
		double E_vac = w_yx.getBelieve() * wx.getaPriori() + w_yxx.getBelieve() * (1 - wx.getaPriori())
				+ ay * (w_yx.getUncertainty() * wx.getaPriori() + w_yxx.getUncertainty() * (1 - wx.getaPriori()));
		// Case I
		if (((w_yx.getBelieve() > w_yxx.getBelieve()) && (w_yx.getDisbelieve() > w_yxx.getDisbelieve()))
				|| ((w_yx.getBelieve() <= w_yxx.getBelieve()) && (w_yx.getDisbelieve() <= w_yxx.getDisbelieve()))) {
			KK = 0.0;
			return KK;

		}
		// Case II.A.1
		else if (((w_yx.getBelieve() > w_yxx.getBelieve()) && (w_yx.getDisbelieve() <= w_yxx.getDisbelieve()))
				&& (E_vac <= (w_yxx.getBelieve() + ay * (1 - w_yxx.getBelieve() - w_yx.getDisbelieve())))
				&& (wx.getTrust() <= wx.getaPriori())) {

			KK = (wx.getaPriori() * wx.getUncertainty() * (bI - w_yxx.getBelieve()))
					/ ((wx.getBelieve() + wx.getaPriori() * wx.getUncertainty()) * ay);
			return KK;
		}
		// Case II.A.2
		else if (((w_yx.getBelieve() > w_yxx.getBelieve()) && (w_yx.getDisbelieve() <= w_yxx.getDisbelieve()))
				&& (E_vac <= (w_yxx.getBelieve() + ay * (1 - w_yxx.getBelieve() - w_yx.getDisbelieve())))
				&& (wx.getTrust() > wx.getaPriori())) {
			KK = (wx.getaPriori() * wx.getUncertainty() * (dI - w_yx.getDisbelieve())
					* (w_yx.getBelieve() - w_yxx.getBelieve()))
					/ (wx.getDisbelieve() + (1 - wx.getaPriori()) * wx.getUncertainty()) * ay
					* (w_yxx.getDisbelieve() - w_yx.getDisbelieve());
			return KK;
		}
		// Case II.B.1
		else if (((w_yx.getBelieve() > w_yxx.getBelieve()) && (w_yx.getDisbelieve() <= w_yxx.getDisbelieve()))
				&& (E_vac > (w_yxx.getBelieve() + ay * (1 - w_yxx.getBelieve() - w_yx.getDisbelieve())))
				&& (wx.getTrust() <= wx.getaPriori())) {
			KK = ((1 - wx.getaPriori()) * wx.getUncertainty() * (bI - w_yx.getDisbelieve()))
					/ ((wx.getBelieve() + wx.getaPriori() * wx.getUncertainty()) * (1 - ay)
							* (w_yx.getBelieve() - w_yxx.getBelieve()));
			return KK;
		}
		// Case II.B.2
		else if (((w_yx.getBelieve() > w_yxx.getBelieve()) && (w_yx.getDisbelieve() <= w_yxx.getDisbelieve()))
				&& (E_vac > (w_yxx.getBelieve() + ay * (1 - w_yxx.getBelieve() - w_yx.getDisbelieve())))
				&& (wx.getTrust() > wx.getaPriori())) {
			KK = ((1 - wx.getaPriori()) * wx.getUncertainty() * (dI - w_yx.getDisbelieve()))
					/ ((wx.getDisbelieve() + (1 - wx.getaPriori()) * wx.getUncertainty()) * (1 - ay));
			return KK;
		}
		// Case III.A.1
		else if (((w_yx.getBelieve() <= w_yxx.getBelieve()) && (w_yx.getDisbelieve() > w_yxx.getDisbelieve()))
				&& (E_vac <= (w_yx.getDisbelieve() + ay * (1 - w_yx.getBelieve() - w_yxx.getDisbelieve())))
				&& (wx.getTrust() < wx.getaPriori())) {
			KK = ((1 - wx.getaPriori()) * wx.getUncertainty() * (dI - w_yxx.getDisbelieve())
					* (w_yxx.getBelieve() - w_yx.getBelieve()))
					/ ((wx.getBelieve() + wx.getaPriori() * wx.getUncertainty()) * ay
							* (w_yx.getDisbelieve() - w_yxx.getDisbelieve()));
			return KK;
		}
		// Case III.A.2
		else if (((w_yx.getBelieve() <= w_yxx.getBelieve()) && (w_yx.getDisbelieve() > w_yxx.getDisbelieve()))
				&& (E_vac <= (w_yx.getBelieve() + ay * (1 - w_yx.getBelieve() - w_yxx.getDisbelieve())))
				&& (wx.getTrust() > wx.getaPriori())) {
			KK = ((1 - wx.getaPriori()) * wx.getUncertainty() * (bI - w_yx.getBelieve()))
					/ ((wx.getDisbelieve() + (1 - wx.getaPriori()) * wx.getUncertainty()) * ay);
			return KK;
		}
		// Case III.B.1
		else if (((w_yx.getBelieve() <= w_yxx.getBelieve()) && (w_yx.getDisbelieve() > w_yxx.getDisbelieve()))
				&& (E_vac > (w_yx.getBelieve() + ay * (1 - w_yx.getBelieve() - w_yxx.getDisbelieve())))
				&& (wx.getTrust() <= wx.getaPriori())) {
			KK = (wx.getaPriori() * wx.getUncertainty() * (dI - w_yxx.getDisbelieve()))
					/ ((wx.getBelieve() + wx.getaPriori() * wx.getUncertainty()) * (1 - ay));
			return KK;
		}
		// Case III.B.2
		else if (((w_yx.getBelieve() <= w_yxx.getBelieve()) && (w_yx.getDisbelieve() > w_yxx.getDisbelieve()))
				&& (E_vac > (w_yx.getBelieve() + ay * (1 - w_yx.getBelieve() - w_yxx.getDisbelieve())))
				&& (wx.getTrust() > wx.getaPriori())) {
			KK = (wx.getaPriori() * wx.getUncertainty() * (bI - w_yx.getBelieve())
					* (w_yx.getDisbelieve() - w_yxx.getDisbelieve()))
					/ ((wx.getDisbelieve() + (1 - wx.getaPriori()) * wx.getUncertainty()) * (1 - ay)
							* (w_yxx.getBelieve() - w_yx.getBelieve()));
			return KK;
		}
		return KK;
	}
}
