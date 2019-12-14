package example;

import java.util.Collection;
import org.apache.commons.lang3.StringUtils;

public class TestString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = "topic", b = "subjectarea", c = "topic";
		// String res= StringUtils.getCommonPrefix(a,b,c);
		// String res= StringUtils.joinWith(a, b);
		// System.out.println(res);
		// System.out.println(longestSubstr(a, b));

		String[] arr = new String[3];
		arr[0] = a;
		arr[1] = b;
		arr[2] = a;
		String res = identifyCommonSubStrOfNStr(arr);
		if (res.length() < 3) {
			arr= deleteOneInput(arr);
		}
		System.out.println(res);
		System.out.println("done!");
	}

	// public static String getLongestCommonSubstring(Collection<String>
	// strings) {
	// LCSubstringSolver solver = new LCSubstringSolver(new
	// DefaultCharSequenceNodeFactory());
	// for (String s : strings) {
	// solver.add(s);
	// }
	// return solver.getLongestCommonSubstring().toString();
	// }

	private static String[] deleteOneInput(String[] arr) {
		String[] res = null ;
		
		return arr;
	}

	public static int longestSubstr(String first, String second) {
		int maxLen = 0;
		int fl = first.length();
		int sl = second.length();
		int[][] table = new int[fl + 1][sl + 1];

		for (int i = 1; i <= fl; i++) {
			for (int j = 1; j <= sl; j++) {
				if (first.charAt(i - 1) == second.charAt(j - 1)) {
					table[i][j] = table[i - 1][j - 1] + 1;
					if (table[i][j] > maxLen)
						maxLen = table[i][j];
				}
			}
		}
		return maxLen;
	}

	public static String identifyCommonSubStrOfNStr(String[] strArr) {

		String commonStr = "";
		String smallStr = "";

		// identify smallest String
		smallStr = strArr[0];
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
		return commonStr;
	}
}
