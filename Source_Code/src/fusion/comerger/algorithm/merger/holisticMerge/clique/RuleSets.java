package fusion.comerger.algorithm.merger.holisticMerge.clique;

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
