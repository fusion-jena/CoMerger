package fusion.comerger.algorithm.merger.holisticMerge.clique;

import java.util.ArrayList;


public class GMR {
	String ruleSetStr;
	ArrayList<Integer> ruleSetInt;
	double rank;

	public String getRuleSetString() {
		return ruleSetStr;
	}

	public void SetRuleSetString(String rs) {
		ruleSetStr = rs;
	}

	public ArrayList<Integer> getRuleSetInteger() {
		return ruleSetInt;
	}

	public void SetRuleSetInteger(ArrayList<Integer> rs) {
		ruleSetInt = rs;
	}
	
	public double getRank() {
		return rank;
	}

	public void SetRank(double r) {
		rank = r;
	}
}
