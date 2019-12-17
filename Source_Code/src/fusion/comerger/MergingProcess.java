package fusion.comerger;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import com.ibm.icu.text.DecimalFormat;

import au.com.bytecode.opencsv.CSVReader;
import fusion.comerger.algorithm.merger.holisticMerge.HolisticMerger;
import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.clique.GMR;
import fusion.comerger.algorithm.merger.holisticMerge.clique.RuleSets;
import fusion.comerger.algorithm.merger.holisticMerge.clique.UserCliqueExtractor;
import fusion.comerger.algorithm.merger.holisticMerge.evaluator.HEvaluator;
import fusion.comerger.algorithm.merger.holisticMerge.general.SaveTxt;
import fusion.comerger.algorithm.merger.model.HModel;

public class MergingProcess {
	static int RulesListInfoSize = 232;
	static HashMap<String, Integer> RulesListInfo = new HashMap<String, Integer>();
	static boolean RuleListCreator = false;

	public static HModel DoMerge(String NameAddressOnt, String alignFile, String UPLOAD_DIRECTORY, String MergeType,
			String selectedUserItem, String preferedOnt, String MergeOutputType) throws Exception {

		HModel ontM = null;
		switch (MergeType) {
		case "RuleMerge":
			// RuleMerger MR = new RuleMerger(NameAddressOnt, alignFile);
			// res = MR.run(NameAddressOnt, alignFile, UPLOAD_DIRECTORY, rules,
			// preferedOnt, MergeOutputType, EvalDim);
			break;

		case "HolisticMerge":
			MyLogging.log(Level.INFO, "Initialise the running of Holistic Merge:");
			HolisticMerger HM = new HolisticMerger();
			ontM = HM.run(NameAddressOnt, alignFile, UPLOAD_DIRECTORY, selectedUserItem, preferedOnt, MergeOutputType);
		}

		return ontM;

	}

	public static HModel DoMergeEval(HModel ontM, String evalDimension)
			throws FileNotFoundException, OWLOntologyStorageException {
		long startTime = System.currentTimeMillis();

		HEvaluator hr = new HEvaluator();
		String[] res = hr.run(ontM, evalDimension);

		SaveTxt st = new SaveTxt();
		String txtFileName = st.ConvertEvalResultToTxt(ontM, res);
		ontM.setEvalResultTxt(txtFileName);

		ontM.setEvalResult(res);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"*** done! Congratulation. Evaluation of the merged ontology has been done successfully. Total time  "
						+ elapsedTime + " ms. \n");

		return ontM;
	}

	public static RuleSets RuleConflict(String userSelectedItem, int numSuggestion) {
		// find incompatible pairs on user-selected-rules, to call for them the
		// whole process and find all possible solution with deleting one of
		// them
		long startTime = System.currentTimeMillis();

		RuleSets allRuleSets = new RuleSets();
		ArrayList<ArrayList<Integer>> rs = new ArrayList<ArrayList<Integer>>();
		String message = "";
		ArrayList<Integer> userGMR = findUserRules(userSelectedItem);
		if (userGMR.size() < 1 || userGMR == null) {
			String msg = "<span	style=\"font-weight: bold; color:red;\"> No rules are selected. </span>";
			allRuleSets.setMessage(msg);
			return allRuleSets;
		}
		ArrayList<GMR> RSet = UserCliqueExtractor.extractor(userGMR, numSuggestion);
		for (int i = 0; i < RSet.size(); i++) {
			GMR gmr = RSet.get(i);
			rs.add(gmr.getRuleSetInteger());
			double rank = Double.parseDouble(new DecimalFormat("##.####").format(gmr.getRank()));
			ArrayList<Integer> set = gmr.getRuleSetInteger();
			ArrayList<ArrayList<Integer>> colorSet = findColorofSet(set, userGMR);
			ArrayList<Integer> compatible = colorSet.get(0);
			ArrayList<Integer> incompatible = colorSet.get(1);
			String ids = createIds(incompatible, set);
			if (i == 0) {
				message = message + "<input type=\"checkbox\" name=\"ruleSet\" ids=\"" + ids
						+ "\" checked onclick=\"handleClick(this)\";  >";
				System.out.println(message);
				allRuleSets.setBestSet(gmr.getRuleSetInteger());
			} else {
				message = message + "<input type=\"checkbox\" name=\"ruleSet\" ids=\"" + ids
						+ "\" onclick=\"handleClick(this)\";>";
			}
			message = message + "<span style=\"font-weight: bold;\">The maximum compatible set: {";
			String temp = "";
			for (int j = 0; j < set.size(); j++) {
				if (compatible.contains(set.get(j))) {
					temp = temp + "-" + "<font color=\"green\">" + set.get(j) + "</font>";
				} else if (!incompatible.contains(set.get(j))) {
					temp = temp + "-" + "<font color=\"orange\">" + set.get(j) + "</font>";
				}
			}
			message = message + temp.substring(1) + "},  </span>";
			if (incompatible.size() > 0) {
				message = message + "<span style=\"font-weight: bold;\">Incomaptible rules: {";
				String inCompTemp = "";
				for (int j = 0; j < incompatible.size(); j++) {
					inCompTemp = inCompTemp + "-" + "<font color=\"red\">" + incompatible.get(j) + "</font>";
				}
				message = message + inCompTemp.substring(1) + "}, </span>";
			} else {
				message = message + "<span style=\"font-weight: bold;\"> no incompatible rule. </span>";
			}

			message = message + "<span style=\"font-weight: bold;\"> Rank:" + rank + "</span><br>";
		}
		allRuleSets.setAllRuleSets(rs);
		allRuleSets.setMessage(message);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"*** done! Congratulation. Compatibility checker has been done successfully. Total time  " + elapsedTime
						+ " ms. \n");

		return allRuleSets;
	}

	private static String createIds(ArrayList<Integer> incompatible, ArrayList<Integer> set) {
		String ids = "";
		for (int c = 0; c < set.size(); c++) {
			if (!incompatible.contains(set.get(c))) {
				ids = ids + "_" + set.get(c);
			}
		}
		ids = ids.substring(1);
		return ids;
	}

	private static ArrayList<ArrayList<Integer>> findColorofSet(ArrayList<Integer> set, ArrayList<Integer> userGMR) {
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> compatible = new ArrayList<Integer>();
		ArrayList<Integer> incompatible = new ArrayList<Integer>();
		for (int i = 0; i < userGMR.size(); i++) {
			if (set.contains(userGMR.get(i))) {
				compatible.add(userGMR.get(i));
			}
		}

		for (int i = 0; i < userGMR.size(); i++) {
			if (!compatible.contains(userGMR.get(i))) {
				incompatible.add(userGMR.get(i));
			}
		}

		res.add(compatible);
		res.add(incompatible);

		return res;
	}

	private static ArrayList<Integer> findUserRules(String userSelectedRule) {
		ArrayList<Integer> onRules = new ArrayList<Integer>();

		String[] item = userSelectedRule.split(",");
		for (int i = 0; i < item.length; i++)
			switch (item[i]) {
			case "ClassCheck":
				onRules.add(1);
				break;

			case "ProCheck":
				onRules.add(2);
				break;

			case "InstanceCheck":
				onRules.add(3);
				break;

			case "CorresCheck":
				onRules.add(4);
				break;

			case "CorssPropCheck":
				onRules.add(5);
				break;

			case "ValueCheck":
				onRules.add(6);
				break;

			case "StrCheck":
				onRules.add(7);
				break;

			case "ClRedCheck":
				onRules.add(8);
				break;

			case "ProRedCheck":
				onRules.add(9);
				break;

			case "InstRedCheck":
				onRules.add(10);
				break;

			case "ExtCheck":
				onRules.add(11);
				break;

			case "EntCheck":
				onRules.add(12);
				break;

			case "TypeCheck":
				onRules.add(13);
				break;

			case "ConstValCheck":
				onRules.add(14);
				break;

			case "DomRangMinCheck":
				onRules.add(15);
				break;

			case "AcyClCheck":
				onRules.add(16);
				break;

			case "AcyProCheck":
				onRules.add(17);
				break;

			case "RecProCheck":
				onRules.add(18);
				break;

			case "UnconnClCheck":
				onRules.add(19);
				break;

			case "UnconnProCheck":
				onRules.add(20);
				break;
			}

		return onRules;

	}

	private static ArrayList<Set<String>> generateCandidateSet(ArrayList<String> selectedRules) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		ArrayList<Set<String>> cSet = new ArrayList<Set<String>>();
		int totalRules = 22;
		ArrayList<String> inCompPairs = new ArrayList<String>();
		ArrayList<String> compatibleGroup = new ArrayList<String>();

		String RuleInfo = "C:\\uploads\\RulesConflict.csv";
		if (RuleListCreator == false) {
			RulesListInfo = CSVtoArray(RuleInfo);
		}

		Set<String> inCompatibleSet = new HashSet<String>();
		for (int i = 0; i < selectedRules.size(); i++) {
			for (int j = i + 1; j < selectedRules.size(); j++) {
				String item = selectedRules.get(i) + "-" + selectedRules.get(j);
				if (RulesListInfo.get(item) != null && RulesListInfo.get(item).equals(1)) {
				} else {
					cSet.addAll(minesRules(selectedRules, selectedRules.get(i), selectedRules.get(j)));

				}

			}

		}

		return cSet;
	}

	private static ArrayList<Set<String>> minesRules(ArrayList<String> set, String R1, String R2) {
		ArrayList<Set<String>> cSet = new ArrayList<Set<String>>();
		Set<String> set1 = new HashSet<String>();
		for (int i = 0; i < set.size(); i++) {
			if (!set.get(i).equals(R1))
				set1.add(set.get(i));
		}
		cSet.add(set1);

		set1 = new HashSet<String>();
		for (int i = 0; i < set.size(); i++) {
			if (!set.get(i).equals(R2))
				set1.add(set.get(i));
		}
		cSet.add(set1);
		return cSet;
	}

	private static ArrayList<String[][]> findAllSubset(ArrayList<String> selectedRules) {
		String set[] = selectedRules.toArray(new String[selectedRules.size()]);

		ArrayList<String[][]> allGroup = new ArrayList<String[][]>();
		int n = set.length;
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < (1 << n); i++) {
			temp = new ArrayList<String>();
			for (int j = 0; j < n; j++)
				if ((i & (1 << j)) > 0) {
					temp.add(set[j]);
				}

			if (temp.size() > 0) {
				String[][] tempArray = new String[22][2];
				for (int j = 0; j < temp.size(); j++) {
					int ruleNumber = Integer.valueOf(temp.get(j).substring(1));
					tempArray[ruleNumber - 1][0] = temp.get(j);
					tempArray[ruleNumber - 1][1] = "on";
				}

				if (tempArray.length > 0)
					allGroup.add(tempArray);
			}
		}
		return allGroup;

	}

	private static ArrayList<String> findDifference(ArrayList<String> inCompatible, List<String> compatibleGroup,
			ArrayList<String> selectedRules) {
		Set<String> res = new HashSet<String>();
		for (int i = 0; i < selectedRules.size(); i++) {
			if (!compatibleGroup.contains(selectedRules.get(i)))
				res.add(selectedRules.get(i));
		}

		for (int i = 0; i < inCompatible.size(); i++)
			res.add(inCompatible.get(i));

		Iterator<String> iter = res.iterator();
		ArrayList<String> result = new ArrayList<String>();
		while (iter.hasNext())
			result.add(iter.next());

		return result;
	}

	private static ArrayList<ArrayList<String>> CalculateOnePossible(ArrayList<String> selectedRules) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		int totalRules = 22;
		ArrayList<String> inCompPairs = new ArrayList<String>();
		ArrayList<String> compatibleGroup = new ArrayList<String>();
		String RuleInfo = "C:\\uploads\\RulesConflict.csv";
		if (RuleListCreator == false) {
			RulesListInfo = CSVtoArray(RuleInfo);
		}

		Set<String> inCompatibleSet = new HashSet<String>();
		for (int i = 0; i < selectedRules.size(); i++) {
			for (int j = i + 1; j < selectedRules.size(); j++) {
				String item = selectedRules.get(i) + "-" + selectedRules.get(j);
				if (RulesListInfo.get(item) != null && RulesListInfo.get(item).equals(1)) {
					if (checkNotExist(compatibleGroup, selectedRules.get(i))) {
						if (checkPreviousComp(compatibleGroup, selectedRules.get(i))) {
							compatibleGroup.add(selectedRules.get(i));
							// TODO: here always add the first item! correct it
						}
					}
					if (checkNotExist(compatibleGroup, selectedRules.get(j))) {
						if (checkPreviousComp(compatibleGroup, selectedRules.get(j))) {
							compatibleGroup.add(selectedRules.get(j));
						}
					}
				} else {
					inCompatibleSet.add(selectedRules.get(i));
					inCompatibleSet.add(selectedRules.get(j));
					inCompPairs.add(item);
				}

			}

		}

		if (compatibleGroup.size() < 1 && selectedRules.size() > 0) {
			compatibleGroup.add(selectedRules.get(0));
			inCompatibleSet.remove(selectedRules.get(0));

		}

		// find which one was incompatible
		ArrayList<String> inCompatible = new ArrayList<String>();
		for (int i = 0; i < selectedRules.size(); i++) {
			boolean exist = false;
			for (int j = 0; j < compatibleGroup.size(); j++) {
				if (selectedRules.get(i).equals(compatibleGroup.get(j))) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				inCompatible.add(selectedRules.get(i));
			}

		}

		// find other related compatible pairs (related to user selected rules
		if (compatibleGroup.size() > 0) {
			for (int i = 0; i < compatibleGroup.size(); i++) {
				String item = compatibleGroup.get(i);
				int ruleNumber = Integer.valueOf(item.substring(1));
				for (int j = ruleNumber + 1; j < totalRules; j++) {
					String R = "R" + j;
					if (checkNotExist(compatibleGroup, R)) {
						if (checkallCompatible(compatibleGroup, R))
							compatibleGroup.add(R);
					}
				}
			}
		}

		// print result
		System.out.print("\n user selected item: \t");
		for (int i = 0; i < selectedRules.size(); i++)
			System.out.print(selectedRules.get(i) + "-");

		System.out.print("\n compatible group: \t");
		for (int i = 0; i < compatibleGroup.size(); i++)
			System.out.print(compatibleGroup.get(i) + "-");

		System.out.print("\n Incompatible group: \t");
		for (int i = 0; i < inCompatible.size(); i++)
			System.out.print(inCompatible.get(i) + "-");

		result.add(compatibleGroup);
		result.add(inCompatible);
		result.add(inCompPairs);
		return result;
	}

	private static boolean checkPreviousComp(ArrayList<String> group, String r) {
		// This function checks, the new R should not have conflict with all
		// previous item in the compatible groups
		for (int j = 0; j < group.size(); j++) {
			String item = group.get(j) + "-" + r;
			if (!(RulesListInfo.get(item) != null && RulesListInfo.get(item).equals(1))) {
				return false;
			}
		}
		return true;
	}

	private static boolean checkallCompatible(ArrayList<String> group, String r) {
		for (int i = 0; i < group.size(); i++) {
			String item = group.get(i) + "-" + r;
			if (RulesListInfo.get(item) == null) {
				item = r + "-" + group.get(i);
				if (!RulesListInfo.get(item).equals(1))
					return false;

			} else if (!RulesListInfo.get(item).equals(1)) {
				return false;
			}

		}
		return true;
	}

	private static boolean checkNotExist(ArrayList<String> compatibleGroup, String item) {
		for (int i = 0; i < compatibleGroup.size(); i++) {
			if (compatibleGroup.get(i).equals(item))
				return false;
		}
		return true;
	}

	public static HashMap<String, Integer> CSVtoArray(String csvFile) {
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			while ((line = reader.readNext()) != null) {
				String item = line[0] + "-" + line[1];
				if (line[2] != null) {
					RulesListInfo.put(item, Integer.valueOf(line[2]));

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		RuleListCreator = true;
		return RulesListInfo;
	}

	public static HModel DoRefine(HModel ontM, String uPLOAD_DIRECTORY, String selectedUserItem, String preferedOnt)
			throws OWLOntologyStorageException, FileNotFoundException {
		long startTime = System.currentTimeMillis();

		HolisticMerger hr = new HolisticMerger();
		ontM = hr.refine(ontM, selectedUserItem);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"*** done! Congratulation. Refinment of the merged ontology has been done successfully. Total time  "
						+ elapsedTime + " ms. \n");

		return ontM;
	}

}
