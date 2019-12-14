package fusion.comerger.algorithm.merger.holisticMerge.mapping;

import java.util.HashSet;
import java.util.Set;

public class LabelIdentifier {
	static Set<String> generatedLabels = new HashSet<String>();

	public static String identifyCommonWordsOld(String[] strArr, String type) {

		String commonStr = "";
		String smallStr = "";

		smallStr = strArr[0];
		// identify smallest String
		for (String s : strArr) {
			if (smallStr.length() > s.length()) {
				smallStr = s;
			}
		}

		String tempCom = "";
		char[] smallStrChars = smallStr.toCharArray();
		for (char c : smallStrChars) {
			tempCom += c;

			for (String s : strArr) {
				if (!s.contains(tempCom)) {
					tempCom = String.valueOf(c);// = c;
					for (String ss : strArr) {
						if (!ss.contains(tempCom)) {
							tempCom = "";
							break;
						}
					}
					break;
				}
			}

			if (tempCom != "" && tempCom.length() > commonStr.length()) {
				commonStr = tempCom;
			}
		}

		int avgLen = 5;
		switch (type) {
		case "class":
			if (commonStr.length() > 0)
				commonStr = commonStr.substring(0, 1).toUpperCase() + commonStr.substring(1);
			// TO DO: it should be corrected
			if (commonStr.length() < avgLen) {
				commonStr = strArr[0];
			}
			break;

		case "object":
			// TO DO: it should be corrected
			if (commonStr.length() < avgLen) {
				commonStr = strArr[0];
			}
			if ((strArr[0].startsWith("has") || strArr[strArr.length - 1].startsWith("has"))
					&& !commonStr.startsWith("has")) {
				commonStr = "has" + commonStr;
			} else if ((strArr[0].startsWith("is") || strArr[strArr.length - 1].startsWith("is"))
					&& !commonStr.startsWith("is")) {
				commonStr = "is" + commonStr;

			}
			break;
		}

		// check for uniqueness
		if (generatedLabels.contains(commonStr)) {
			commonStr = strArr[0];
		}

		generatedLabels.add(commonStr);
		return commonStr;
	}

	private static int getavgLength(String[] strArr) {
		int L = 0, Avg = 0;
		for (int i = 0; i < strArr.length; i++) {
			L += strArr[i].length();
		}

		Avg = L / (strArr.length);
		return Avg;

	}

	public static String createLabel(String[] sList, String type) {
		String label = "";
		int max = 0;
		for (String s : sList) {
			if (s.length() > max) {
				max = s.length();
				label = s;
			}
		}
		for (String s : sList) {
			if (!label.toLowerCase().contains(s.toLowerCase())) {
				label = label + "_" + s;
			}
		}

		switch (type) {
		case "class":
			if (label.length() > 0)
				label = label.substring(0, 1).toUpperCase() + label.substring(1);
			break;

		case "object":
			label = label.substring(0, 1).toLowerCase() + label.substring(1);
			break;
		}
		return label;
	}

}
