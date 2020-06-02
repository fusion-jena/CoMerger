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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

public class UserClique {

	public static ArrayList<GMR> findUserClique(ArrayList<Integer> userGMR, ArrayList<ArrayList<Integer>> allSet,
			int numSuggestion) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < allSet.size(); i++) {
			if (containAtLeastOne(allSet.get(i), userGMR)) {
				result.add(allSet.get(i));
			}
		}

		ArrayList<ArrayList<Integer>> resultNew = selectMaxSet(result);
		if (resultNew.size() > 1)
			result = resultNew;
		ArrayList<GMR> rankeResult = selectHighRank(userGMR, result, numSuggestion);

		return rankeResult;
	}

	private static ArrayList<ArrayList<Integer>> selectMaxSet(ArrayList<ArrayList<Integer>> sets) {
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < sets.size(); i++) {
			ArrayList<Integer> currentSet = sets.get(i);
			boolean check = false;
			check = isCurrentSetMax(currentSet, sets);
			if (check == true) {
				res.add(currentSet);
			}
		}

		return res;
	}

	private static boolean isCurrentSetMax(ArrayList<Integer> currentSet, ArrayList<ArrayList<Integer>> sets) {
		for (int i = 0; i < sets.size(); i++) {
			if (sets.get(i) != currentSet) {
				if (sets.get(i).containsAll(currentSet)) {
					return false;
				}
			}
		}
		return true;
	}

	private static ArrayList<GMR> selectHighRank(ArrayList<Integer> userGMR, ArrayList<ArrayList<Integer>> result,
			int numSuggestion) {
		ArrayList<GMR> Res = new ArrayList<GMR>();
		HashMap<String, Double> rankRes = new HashMap<String, Double>();
		Set<String> userAspect = getUserAspect(userGMR);
		// calculate rank
		double W_ratioUser = Parameter.W_ratioUser, W_ratioAspect = Parameter.W_ratioAspect,
				W_compatibleDegreeRtio = Parameter.W_compatibleDegreeRtio;

		for (int i = 0; i < result.size(); i++) {
			double userRatio = ratioUserGMR(result.get(i), userGMR);
			double aspectRatio = ratioAspect(result.get(i), userAspect);
			double compatibleDegreeRtio = ratioCompatibleDegree(result.get(i));
			double rank = W_ratioUser * userRatio + W_ratioAspect * aspectRatio
					+ W_compatibleDegreeRtio * compatibleDegreeRtio;

			String rules = convertToString(result.get(i));
			rankRes.put(rules, rank);
			// System.out.print(rules + rank);

		}

		rankRes = normalize(rankRes);

		// sort
		List<Entry<String, Double>> nn = newSort(rankRes);
		Iterator<Entry<String, Double>> entry = nn.iterator();

		List<String> sortRules = new ArrayList<String>();
		int counter = 0;
		while (entry.hasNext()) {
			Entry<String, Double> b = entry.next();
			sortRules.add(b.getKey());
			counter++;
			if (counter == numSuggestion)
				break;
		}

		Iterator<String> iter = sortRules.iterator();
		while (iter.hasNext()) {
			String st = iter.next();
			GMR gmrSet = new GMR();
			gmrSet.SetRuleSetString(st);
			gmrSet.SetRank(rankRes.get(st));
			String[] strArry = st.split("-");
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (int i = 0; i < strArry.length; i++) {
				temp.add(Integer.parseInt(strArry[i]));
			}
			gmrSet.SetRuleSetInteger(temp);
			Res.add(gmrSet);
		}

		return Res;
	}

	private static HashMap<String, Double> normalize(HashMap<String, Double> rankRes) {
		HashMap<String, Double> normalizedRankRes = new HashMap<String, Double>();
		double[] maxMin = findMaxMin(rankRes);
		double max = maxMin[0];
		double min = maxMin[1];

		for (Map.Entry<String, Double> entry : rankRes.entrySet()) {
			double norm = getNormal(entry.getValue(), min, max);
			normalizedRankRes.put(entry.getKey(), norm);
		}
		return normalizedRankRes;
	}

	private static double[] findMaxMin(HashMap<String, Double> rankRes) {
		double[] maxMin = new double[2];
		double max = 0.0, min = 0.0;
		for (Map.Entry<String, Double> entry : rankRes.entrySet()) {
			if (entry.getValue() > max)
				max = entry.getValue();
			if (entry.getValue() < min)
				min = entry.getValue();
		}
		maxMin[0] = max;
		maxMin[1] = min;
		return maxMin;
	}

	public static double getNormal(double inValue, double min, double max) {
		return (inValue - min) / (max - min);
	}

	private static List<Map.Entry<String, Double>> newSort(HashMap<String, Double> rankRes) {
		List<Map.Entry<String, Double>> entryList = new ArrayList<Map.Entry<String, Double>>(rankRes.entrySet());
		Collections.sort(entryList, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				// return o1.getValue().compareTo(o2.getValue());
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		return entryList;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
		list.sort(Entry.comparingByValue());

		Map<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	public static List<String> topNKeys(final HashMap<String, Double> map, int n) {
		PriorityQueue<String> topN = new PriorityQueue<String>(n, new Comparator<String>() {
			public int compare(String s1, String s2) {
				return Double.compare(map.get(s1), map.get(s2));
			}
		});

		for (String key : map.keySet()) {
			if (topN.size() < n)
				topN.add(key);
			else if (map.get(topN.peek()) < map.get(key)) {
				topN.poll();
				topN.add(key);
			}
		}
		return (List) Arrays.asList(topN.toArray());
	}

	private static String convertToString(ArrayList<Integer> arry) {
		String str = "";
		for (int i = 0; i < arry.size(); i++) {
			str = str + arry.get(i) + "-";
		}
		return str;
	}

	private static double ratioAspect(ArrayList<Integer> currentSet, Set<String> userAspect) {
		if (userAspect.size() < 1)
			return 0.0;
		Set<String> setAspect = new HashSet<String>();
		double totallAspect = 6.0;
		double common = 0.0;
		double res = 0.0;
		for (int i = 0; i < currentSet.size(); i++) {
			String as = getAspect(currentSet.get(i));
			setAspect.add(as);
		}

		Iterator<String> iter = setAspect.iterator();
		while (iter.hasNext()) {
			String s = iter.next();
			if (userAspect.contains(s)) {
				common = common + 1.0;
			}
		}

		res = (common / userAspect.size());
		res = res + (setAspect.size() / totallAspect);
		return res;
	}

	private static Set<String> getUserAspect(ArrayList<Integer> currentSet) {
		Set<String> aspect = new HashSet<String>();

		for (int i = 0; i < currentSet.size(); i++) {
			aspect.add(getAspect(currentSet.get(i)));
		}

		return aspect;
	}

	private static String getAspect(Integer rule) {
		switch (rule) {
		case 1:
			return "completness";
		case 2:
			return "completness";
		case 3:
			return "completness";
		case 4:
			return "completness";
		case 5:
			return "completness";
		case 6:
			return "completness";
		case 7:
			return "completness";
		case 8:
			return "minimality";
		case 9:
			return "minimality";
		case 10:
			return "minimality";
		case 11:
			return "minimality";
		case 12:
			return "deduction";
		case 13:
			return "constraint";
		case 14:
			return "constraint";
		case 15:
			return "constraint";
		case 16:
			return "acycility";
		case 17:
			return "acycility";
		case 18:
			return "acycility";
		case 19:
			return "connectivity";
		case 20:
			return "connectivity";

		}
		return null;
	}

	private static double ratioCompatibleDegree(ArrayList<Integer> currentSet) {
		double ratioU = 0.0;
		double degree = 0.0;
		for (int i = 0; i < currentSet.size(); i++) {
			degree = degree + getCompatibleDegree(currentSet.get(i));
		}
		ratioU = degree / currentSet.size();
		return ratioU;
	}

	private static double getCompatibleDegree(Integer rule) {
		switch (rule) {
		case 1:
			return 0.89;
		case 2:
			return 0.79;
		case 3:
			return 1.0;
		case 4:
			return 0.74;
		case 5:
			return 0.79;
		case 6:
			return 0.89;
		case 7:
			return 0.79;
		case 8:
			return 1.0;
		case 9:
			return 0.89;
		case 10:
			return 1.0;
		case 11:
			return 0.89;
		case 12:
			return 0.74;
		case 13:
			return 0.95;
		case 14:
			return 0.95;
		case 15:
			return 0.84;
		case 16:
			return 0.63;
		case 17:
			return 0.63;
		case 18:
			return 0.63;
		case 19:
			return 0.68;
		case 20:
			return 0.68;
		}
		return 0;
	}

	private static double ratioUserGMR(ArrayList<Integer> currentSet, ArrayList<Integer> userGMR) {
		if (userGMR.size() < 0)
			return 0.0;
		double ratioU = 0.0;
		double common = 0.0;
		double totallGMR = 23.0;
		Set<Integer> userSet = new HashSet<Integer>(userGMR);

		for (int i = 0; i < currentSet.size(); i++) {
			if (userSet.contains(currentSet.get(i))) {
				common = common + 1.0;
			}
		}

		ratioU = (common / userGMR.size());// / totallGMR;
		// we divide the above equation again to totallGMR, since we do not want
		// to have value larger than 1
		// if there is some more extra items, the set is better.
		ratioU = ratioU + (currentSet.size() / totallGMR);
		return ratioU;
	}

	private static boolean containAtLeastOne(ArrayList<Integer> currentSet, ArrayList<Integer> userGMR) {
		for (int i = 0; i < currentSet.size(); i++) {
			for (int j = 0; j < userGMR.size(); j++) {
				if (currentSet.get(i).equals(userGMR.get(j))) {
					return true;
				}
			}
		}
		return false;
	}

}
