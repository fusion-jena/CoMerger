package fusion.oapt.algorithm.merger.holisticMerge.consistency;
/**
 * CoMerger: Holistic Multiple Ontology Merger.
 * Consistency checker sub package based on the Subjective Logic theory
 * @author Samira Babalou (samira[dot]babalou[at]uni_jean[dot]de)
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import fusion.oapt.algorithm.merger.model.HModel;

public class RankerSL {

	public static void main(String[] args) throws OWLOntologyCreationException, IOException {

	}

	public ArrayList<ErrornousAxioms> rankAxiom(ArrayList<ErrornousAxioms> allErrAx, double[] Jsize, HModel ontM) {
		double errorneousAxiomSize = Jsize[1];
		double justSize = Jsize[0];
		ArrayList<ArrayList<Opinion>> opArray = new ArrayList<ArrayList<Opinion>>();
		ArrayList<Opinion> tempOpArray = new ArrayList<Opinion>();
		for (int i = 0; i < allErrAx.size(); i++) {
			opArray = new ArrayList<ArrayList<Opinion>>();
			Iterator<Set<OWLAxiom>> iterJust = allErrAx.get(i).getErrorAxioms().iterator();
			while (iterJust.hasNext()) {
				tempOpArray = new ArrayList<Opinion>();
				Set<OWLAxiom> currentJust = iterJust.next();
				ArrayList<Opinion> previousJusts = getPreviousDependentJusts(allErrAx, opArray, currentJust);
				Iterator<OWLAxiom> iterJ = currentJust.iterator();
				if (previousJusts.size() < 1) {
					while (iterJ.hasNext()) {
						OWLAxiom currentAx = iterJ.next();
						Opinion o = independentThrust(currentAx, currentJust, allErrAx, errorneousAxiomSize, ontM);
						tempOpArray.add(o);
					}
				} else {
					while (iterJ.hasNext()) {
						OWLAxiom currentAx = iterJ.next();
						Opinion o = dependentThrust(currentAx, currentJust, allErrAx, previousJusts,
								errorneousAxiomSize, ontM);
						tempOpArray.add(o);
					}
				}

				opArray.add(tempOpArray);
			}
			allErrAx.get(i).setRankedAxioms(opArray);
		}
		return allErrAx;
	}

	private static Opinion dependentThrust(OWLAxiom ax, Set<OWLAxiom> justs, ArrayList<ErrornousAxioms> allErrAx,
			ArrayList<Opinion> previousJusts, double errorneousAxiomSize, HModel ontM) {
		ArrayList<Opinion> W__yx = new ArrayList<Opinion>();

		Opinion wy = setOpinion(ax, justs, allErrAx, errorneousAxiomSize, ontM);
		for (int z = 0; z < previousJusts.size(); z++) {
			Opinion wx = previousJusts.get(z);
			if (wx.getAxiom().equals(wy.getAxiom())) {
				Opinion temp = deepCopy(wx);
				W__yx.add(temp);
			} else {
				Opinion wxx = new Opinion(wx.getDisbelieve(), wx.getBelieve(), wx.getUncertainty(),
						1 - wx.getaPriori());
				Opinion w_yx = setConditionalOpinion(wy, wx);
				Opinion w_yxx = setConditionalOpinion(wy, wxx);

				DeductionSL ds = new DeductionSL();
				Opinion w__yx_z = ds.getDeduction(wx, w_yx, w_yxx);
				W__yx.add(w__yx_z);

			}
		}
		W__yx = validateNull(W__yx);
		SLogic s = new SLogic(W__yx);
		Opinion w__yx = s.getConsensus();
		w__yx.setTrust(w__yx.computeTrust());
		w__yx.setAxiomID(UUID.randomUUID().toString());
		w__yx.setAxiom(ax);

		return w__yx;
	}

	public static Opinion deepCopy(Opinion wx) {
		Opinion temp = new Opinion();
		temp.setBelieve(new Double(wx.getBelieve()));
		temp.setDisbelieve(new Double(wx.getDisbelieve()));
		temp.setUncertainty(new Double(wx.getUncertainty()));
		temp.setaPriori(new Double(wx.getaPriori()));
		temp.setTrust(new Double(wx.getTrust()));
		temp.setAxiom(wx.getAxiom());
		temp.setAxiomID(new String(wx.getAxiomID()));
		return temp;
	}

	private static ArrayList<Opinion> validateNull(ArrayList<Opinion> array) {
		ArrayList<Opinion> res = new ArrayList<Opinion>();
		for (int i = 0; i < array.size(); i++) {
			Opinion op = array.get(i);
			if (op.getaPriori() != 0 && op.getUncertainty() != 0) {
				res.add(op);
			}
		}

		if (res.size() < 1) {
			res.add(array.get(0));
		}
		return res;
	}

	private static Opinion setConditionalOpinion(Opinion wy, Opinion wx) {
		Opinion Y = calOpinionAnd(wy, wx);
		Opinion o = dividOpinion(Y, wx);
		return o;

	}

	private static Opinion dividOpinion(Opinion y, Opinion x) {
		double b = 0.0, d = 0.0, u = 0.0, a = 0.0;
		if (y.getBelieve() > x.getBelieve()) {
			b = y.getBelieve() - x.getBelieve();
		} else {
			b = x.getBelieve() - y.getBelieve();
		}

		if (y.getaPriori() > x.getaPriori() && y.getaPriori() - x.getaPriori() != 0) {
			d = (y.getaPriori() * (y.getDisbelieve() + x.getBelieve())
					- x.getaPriori() * (1 + x.getBelieve() - y.getBelieve() - x.getUncertainty()))
					/ (y.getaPriori() - x.getaPriori());

			u = (y.getaPriori() * y.getUncertainty() - x.getaPriori() * x.getUncertainty())
					/ (y.getaPriori() - x.getaPriori());
			a = y.getaPriori() - x.getaPriori();

		} else if (x.getaPriori() > y.getaPriori() && x.getaPriori() - y.getaPriori() != 0) {
			d = (x.getaPriori() * (x.getDisbelieve() + y.getBelieve())
					- y.getaPriori() * (1 + y.getBelieve() - x.getBelieve() - y.getUncertainty()))
					/ (x.getaPriori() - y.getaPriori());
			u = (x.getaPriori() * x.getUncertainty() - y.getaPriori() * y.getUncertainty())
					/ (x.getaPriori() - y.getaPriori());
			a = x.getaPriori() - y.getaPriori();
		}

		Opinion o = new Opinion();
		o.setBelieve(b);
		o.setDisbelieve(d);
		o.setUncertainty(u);
		o.setaPriori(a);
		return o;
	}

	private static Opinion calOpinionAnd(Opinion wy, Opinion wx) {
		Opinion o = new Opinion();
		double b = wy.getBelieve() * wx.getBelieve();
		double d = wy.getDisbelieve() + wx.getDisbelieve() - wy.getDisbelieve() * wx.getDisbelieve();
		double u = wy.getBelieve() * wx.getUncertainty() + wy.getUncertainty() * wx.getBelieve()
				+ wy.getUncertainty() * wx.getUncertainty();
		double a = 0.0;
		double denominator = wy.getBelieve() * wx.getUncertainty() + wy.getUncertainty() * wx.getBelieve()
				+ wy.getUncertainty() * wx.getUncertainty();
		if (denominator != 0) {
			a = (wy.getBelieve() * wx.getUncertainty() * wx.getaPriori()
					+ wy.getUncertainty() * wy.getaPriori() * wx.getBelieve()
					+ wy.getUncertainty() * wy.getaPriori() * wx.getUncertainty() * wx.getaPriori()) / (denominator);
		}
		o.setBelieve(b);
		o.setDisbelieve(d);
		o.setUncertainty(u);
		o.setaPriori(a);
		return o;
	}

	private static ArrayList<Opinion> getPreviousDependentJusts(ArrayList<ErrornousAxioms> allErrAx,
			ArrayList<ArrayList<Opinion>> opArray, Set<OWLAxiom> currentJust) {
		int maxLength = Parameter.previous_Justification_Length;
		ArrayList<Opinion> res = new ArrayList<Opinion>();
		Iterator<OWLAxiom> iterJ = currentJust.iterator();
		while (iterJ.hasNext() && res.size() < maxLength) {
			OWLAxiom currentAx = iterJ.next();
			for (int k = 0; k < allErrAx.size(); k++) {
				ArrayList<ArrayList<Opinion>> ju = allErrAx.get(k).getRankedAxioms();
				if (ju != null && res.size() < maxLength) {
					for (int n = 0; n < ju.size(); n++) {
						ArrayList<Opinion> ax = ju.get(n);
						if (ax != null && res.size() < maxLength) {
							for (int j = 0; j < ax.size(); j++) {
								Opinion o = ax.get(j);
								if (o.getAxiom().equals(currentAx)) {
									Opinion temp = deepCopy(o);
									res.add(temp);
									if (res.size() > maxLength) {
										break;
									}
								}
							}
						}
					}
				}
			}

			if (res.size() < maxLength) {
				for (int i = 0; i < opArray.size(); i++) {
					ArrayList<Opinion> op = opArray.get(i);
					for (int j = 0; j < op.size(); j++) {
						Opinion o = op.get(j);
						if (o.getAxiom().equals(currentAx)) {
							res.add(o);
						}
					}
				}
			}
		}

		return res;
	}

	private static ArrayList<ArrayList<OWLAxiom>> SetToArray(Set<Set<OWLAxiom>> exSet) {
		ArrayList<ArrayList<OWLAxiom>> X = new ArrayList<ArrayList<OWLAxiom>>();
		Iterator<Set<OWLAxiom>> iter = exSet.iterator();
		while (iter.hasNext()) {
			ArrayList<OWLAxiom> temp = new ArrayList<OWLAxiom>();
			Iterator<OWLAxiom> iter2 = iter.next().iterator();
			while (iter2.hasNext()) {
				OWLAxiom ax = iter2.next();
				temp.add(ax);
			}
			X.add(temp);
		}

		return X;
	}

	private static Opinion independentThrust(OWLAxiom proposition, Set<OWLAxiom> just,
			ArrayList<ErrornousAxioms> allErrAx, double errorneousAxiomSize, HModel ontM) {
		Opinion o = setOpinion(proposition, just, allErrAx, errorneousAxiomSize, ontM);
		return o;
	}

	private static Opinion setOpinion(OWLAxiom proposition, Set<OWLAxiom> just, ArrayList<ErrornousAxioms> allErrAx,
			double errorneousAxiomSize, HModel ontM) {
		ArrayList<Opinion> W_O = new ArrayList<Opinion>();
		for (int ontId = 0; ontId < ontM.getInputOntNumber(); ontId++) {
			Opinion W_oi = new Opinion();
			SLogicOperation sl = new SLogicOperation();
			Set<OWLClass> axElem = sl.findAxiomElements(proposition, ontId, ontM);

			double b = sl.CalBelieve(ontId, proposition, just, axElem, allErrAx, errorneousAxiomSize, ontM);
			double u = sl.CalUncertainty(ontId, proposition, axElem, ontM);
			double d = 1 - b - u;
			double a = sl.CalaPriori(ontId, proposition, axElem, ontM);
			W_oi.setBelieve(b);
			W_oi.setDisbelieve(d);
			W_oi.setUncertainty(u);
			W_oi.setaPriori(a);
			W_O.add(W_oi);

		}
		W_O = validateNull(W_O);
		SLogic s = new SLogic(W_O);
		Opinion o = s.getConsensus();
		o.setTrust(o.computeTrust());
		o.setAxiomID(UUID.randomUUID().toString());
		o.setAxiom(proposition);

		return o;
	}
}
