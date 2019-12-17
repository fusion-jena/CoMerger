package fusion.comerger.servlets;
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

import javax.servlet.http.HttpServletRequest;

import fusion.comerger.algorithm.merger.holisticMerge.clique.RuleSets;

public class ConfigServlet {
	public static HttpServletRequest requestConfigMerge2(HttpServletRequest req) {

		req.setAttribute("SecTotal", "show");
		req.setAttribute("SecMapper", "showxx");
		req.setAttribute("SecCompactness", "show");
		req.setAttribute("SecCoverage", "show");
		req.setAttribute("SecRedundancy", "show");
		req.setAttribute("SecAccuracy", "show");
		req.setAttribute("SecConsistency", "show");
		req.setAttribute("SecReadability", "show");
		req.setAttribute("SecUsabilityProfile", "show");

		return req;
	}

	public static HttpServletRequest requestConfigMerge(HttpServletRequest req, String type, String MergeOutputType,
			String selectedUserItem) {
		if (MergeOutputType.equals("OWLtype"))
			req.setAttribute("OWLsel", "Selected");
		if (MergeOutputType.equals("RDFtype"))
			req.setAttribute("RDFsel", "Selected");

		if (type.equals("RuleMerge"))
			req.setAttribute("RuleMergeSel", "Selected");
		if (type.equals("HolisticMerge"))
			req.setAttribute("HolisticMergeSel", "Selected");

		if (type.equals("GraphMerge"))
			req.setAttribute("GraphMergeSel", "Selected");
		if (type.equals("APIMerge"))
			req.setAttribute("APIMergeSel", "Selected");
		if (type.equals("MatchingMerge"))
			req.setAttribute("MatchingMergeSel", "Selected");
		if (type.equals("GenericMerge"))
			req.setAttribute("GenericMergeSel", "Selected");

		req = checkedUserSelectedBox(req, selectedUserItem);
		return req;

	}

	private static HttpServletRequest checkedUserSelectedBox(HttpServletRequest req, String selectedUserItem) {
		String[] item = selectedUserItem.split(",");
		for (int i = 0; i < item.length; i++) {
			switch (item[i]) {
			case "ClassCheck":
				req.setAttribute("ClassCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "ProCheck":
				req.setAttribute("ProCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "InstanceCheck":
				req.setAttribute("InstanceCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "CorresCheck":
				req.setAttribute("CorresCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "CorssPropCheck":
				req.setAttribute("CorssPropCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "ValueCheck":
				req.setAttribute("ValueCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "StrCheck":
				req.setAttribute("StrCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "ClRedCheck":
				req.setAttribute("ClRedCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "ProRedCheck":
				req.setAttribute("ProRedCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "InstRedCheck":
				req.setAttribute("InstRedCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "ExtCheck":
				req.setAttribute("ExtCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "EntCheck":
				req.setAttribute("EntCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "TypeCheck":
				req.setAttribute("TypeCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "ConstValCheck":
				req.setAttribute("ConstValCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "DomRangMinCheck":
				req.setAttribute("DomRangMinCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "AcyClCheck":
				req.setAttribute("AcyClCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "AcyProCheck":
				req.setAttribute("AcyProCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "RecProCheck":
				req.setAttribute("RecProCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "UnconnClCheck":
				req.setAttribute("UnconnClCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "UnconnProCheck":
				req.setAttribute("UnconnProCh", "checked");
				req.setAttribute("SecGMR", "SecGMRshow");
				break;

			case "CompletenessCheck":
				req.setAttribute("CompletenessCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;

			case "MinimalityCheck":
				req.setAttribute("MinimalityCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;

			case "DeductionCheck":
				req.setAttribute("DeductionCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;

			case "ConstraintCheck":
				req.setAttribute("ConstraintCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;

			case "AcyclicityCheck":
				req.setAttribute("AcyclicityCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;

			case "ConnectivityCheck":
				req.setAttribute("ConnectivityCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;

			case "MapperCheck":
				req.setAttribute("MapperCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;

			case "CompactnessCheck":
				req.setAttribute("CompactnessCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;

			case "CoverageCheck":
				req.setAttribute("CoverageCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;

			case "RedundancyCheck":
				req.setAttribute("RedundancyCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;

			case "AccuracyCheck":
				req.setAttribute("AccuracyCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;

			case "ConsistencyCheck":
				req.setAttribute("ConsistencyCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;

			case "ReadabilityCheck":
				req.setAttribute("ReadabilityCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;

			case "UsabilityProfileCheck":
				req.setAttribute("UsabilityProfileCh", "checked");
				req.setAttribute("SecEval", "SecEvalshow");
				break;
			}
		}
		return req;
	}

	public static HttpServletRequest requestConfigMergeEval(HttpServletRequest req, String selectedUserItem) {

		req = checkedUserSelectedBox(req, selectedUserItem);
		return req;

	}

	public static String[][] setDefaultRules() {
		String[][] Rules = new String[23][2];
		Rules[0][0] = "ClassCheck";
		Rules[1][0] = "ProCheck";
		Rules[2][0] = "InstanceCheck";
		Rules[3][0] = "CorresCheck";
		Rules[4][0] = "CorssPropCheck";
		Rules[5][0] = "ValueCheck";
		Rules[6][0] = "StrCheck";
		Rules[7][0] = "ClRedCheck";
		Rules[8][0] = "ProRedCheck";
		Rules[9][0] = "InstRedCheck";
		Rules[10][0] = "PathRedCheck";
		Rules[11][0] = "ExtCheck";
		Rules[12][0] = "DomRangMinCheck";
		Rules[13][0] = "AcyClCheck";
		Rules[14][0] = "AcyProCheck";
		Rules[15][0] = "RecProCheck";
		Rules[16][0] = "UnconnClCheck";
		Rules[17][0] = "UnconnProCheck";
		Rules[18][0] = "NullDomCheck";
		Rules[19][0] = "EntCheck";
		Rules[20][0] = "TypeCheck";
		Rules[21][0] = "ConstValCheck";
		Rules[22][0] = "CardCheck";

		for (int i = 0; i < 23; i++)
			Rules[i][1] = "off";
		return Rules;
	}

	public static String[][] setUserSelectedRules(String selectedUserItem) {
		String[][] Rules = new String[23][2];
		String[] arr = selectedUserItem.split(",");
		Rules[0][0] = "ClassCheck";
		Rules[1][0] = "ProCheck";
		Rules[2][0] = "InstanceCheck";
		Rules[3][0] = "CorresCheck";
		Rules[4][0] = "CorssPropCheck";
		Rules[5][0] = "ValueCheck";
		Rules[6][0] = "StrCheck";
		Rules[7][0] = "ClRedCheck";
		Rules[8][0] = "ProRedCheck";
		Rules[9][0] = "InstRedCheck";
		Rules[10][0] = "PathRedCheck";
		Rules[11][0] = "ExtCheck";
		Rules[12][0] = "DomRangMinCheck";
		Rules[13][0] = "AcyClCheck";
		Rules[14][0] = "AcyProCheck";
		Rules[15][0] = "RecProCheck";
		Rules[16][0] = "UnconnClCheck";
		Rules[17][0] = "UnconnProCheck";
		Rules[18][0] = "NullDomCheck";
		Rules[19][0] = "EntCheck";
		Rules[20][0] = "TypeCheck";
		Rules[21][0] = "ConstValCheck";
		Rules[22][0] = "CardCheck";

		for (int j = 0; j < 23; j++)
			Rules[j][1] = "off";
		for (int i = 0; i < arr.length; i++) {
			switch (arr[i]) {
			case "ClassCheck":
				Rules[0][1] = "on";
				break;

			case "ProCheck":
				Rules[1][1] = "on";
				break;

			case "InstanceCheck":
				Rules[2][1] = "on";
				break;

			case "CorresCheck":
				Rules[3][1] = "on";
				break;

			case "CorssPropCheck":
				Rules[4][1] = "on";
				break;

			case "ValueCheck":
				Rules[5][1] = "on";
				break;

			case "StrCheck":
				Rules[6][1] = "on";
				break;

			case "ClRedCheck":
				Rules[7][1] = "on";
				break;

			case "ProRedCheck":
				Rules[8][1] = "on";
				break;

			case "InstRedCheck":
				Rules[9][1] = "on";
				break;

			case "PathRedCheck":
				Rules[10][1] = "on";
				break;

			case "ExtCheck":
				Rules[11][1] = "on";
				break;

			case "DomRangMinCheck":
				Rules[12][1] = "on";
				break;

			case "AcyClCheck":
				Rules[13][1] = "on";
				break;

			case "AcyProCheck":
				Rules[14][1] = "on";
				break;

			case "RecProCheck":
				Rules[15][1] = "on";
				break;

			case "UnconnClCheck":
				Rules[16][1] = "on";
				break;

			case "UnconnProCheck":
				Rules[17][1] = "on";
				break;

			case "NullDomCheck":
				Rules[18][1] = "on";
				break;

			case "EntCheck":
				Rules[19][1] = "on";
				break;

			case "TypeCheck":
				Rules[20][1] = "on";
				break;

			case "ConstValCheck":
				Rules[21][1] = "on";
				break;

			case "CardCheck":
				Rules[22][1] = "on";
				break;
			}

		}

		return Rules;
	}

	public static String[] setUserSelectedEvalDim(String selectedUserItem) {

		String[] res = new String[13];
		for (int j = 0; j < 13; j++)
			res[j] = "off";

		String[] arr = selectedUserItem.split(",");
		for (int i = 0; i < arr.length; i++) {
			switch (arr[i]) {
			case "CompletenessCheck":
				res[0] = "on";
				break;

			case "MinimalityCheck":
				res[1] = "on";
				break;

			case "DeductionCheck":
				res[2] = "on";
				break;

			case "ConstraintCheck":
				res[3] = "on";
				break;

			case "AcyclicityCheck":
				res[4] = "on";
				break;

			case "ConnectivityCheck":
				res[5] = "on";
				break;

			case "MapperCheck":
				res[6] = "on";
				break;

			case "CompactnessCheck":
				res[7] = "on";
				break;

			case "CoverageCheck":
				res[8] = "on";
				break;

			case "RedundancyCheck":
				res[9] = "on";
				break;

			case "AccuracyCheck":
				res[10] = "on";
				break;

			case "ConsistencyCheck":
				res[11] = "on";
				break;

			case "ReadabilityCheck":
				res[12] = "on";
				break;

			case "UsabilityProfileCheck":
				res[12] = "on";
				break;
			}

		}

		return res;
	}

	public static int setnumSuggestion(String selectedUserItem) {
		String[] arr = selectedUserItem.split(",");
		int num = 5;
		try {
			num = Integer.parseInt(arr[0]);
		} catch (NumberFormatException nfe) {
			// bad data - set to default
			num = 5;
		}

		return num;
	}

	public static HttpServletRequest mergeEvaluationResultConfig(HttpServletRequest req, String selectedUserItem) {
		String[] item = selectedUserItem.split(",");
		// default: hide
		req.setAttribute("SecCompleteness", "hideSecCompleteness");
		req.setAttribute("SecMinimality", "hideSecMinimality");
		req.setAttribute("SecDeduction", "hideSecDeduction");
		req.setAttribute("SecConstraint", "hideSecConstraint");
		req.setAttribute("SecAcyclicity", "hideSecAcyclicity");
		req.setAttribute("SecConnectivity", "hideSecConnectivity");
		req.setAttribute("SecMapper", "hideSecMapper");
		req.setAttribute("SecCompactness", "hideSecCompactness");
		req.setAttribute("SecCoverage", "hideSecCoverage");
		req.setAttribute("SecRedundancy", "hideSecRedundancy");
		req.setAttribute("SecAccuracy", "hideSecAccuracy");
		req.setAttribute("SecConsistency", "hideSecConsistency");
		req.setAttribute("SecReadability", "hideSecReadability");
		req.setAttribute("SecUsabilityProfile", "hideSecUsabilityProfile");

		for (int i = 0; i < item.length; i++) {
			switch (item[i]) {
			case "CompletenessCheck":
				req.setAttribute("SecCompleteness", "showSecCompleteness");
				break;

			case "MinimalityCheck":
				req.setAttribute("SecMinimality", "showSecMinimality");
				break;

			case "DeductionCheck":
				req.setAttribute("SecDeduction", "showSecDeduction");
				break;

			case "ConstraintCheck":
				req.setAttribute("SecConstraint", "showSecConstraint");
				break;

			case "AcyclicityCheck":
				req.setAttribute("SecAcyclicity", "showSecAcyclicity");
				break;

			case "ConnectivityCheck":
				req.setAttribute("SecConnectivity", "showSecConnectivity");
				break;

			case "MapperCheck":
				req.setAttribute("SecMapper", "showSecMapper");
				break;

			case "CompactnessCheck":
				req.setAttribute("SecCompactness", "showSecCompactness");
				break;

			case "CoverageCheck":
				req.setAttribute("SecCoverage", "showSecCoverage");
				break;

			case "RedundancyCheck":
				req.setAttribute("SecRedundancy", "showSecRedundancy");
				break;

			case "AccuracyCheck":
				req.setAttribute("SecAccuracy", "showSecAccuracy");
				break;

			case "ConsistencyCheck":
				req.setAttribute("SecConsistency", "showSecConsistency");
				break;

			case "ReadabilityCheck":
				req.setAttribute("SecReadability", "showSecReadability");
				break;

			case "UsabilityProfileCheck":
				req.setAttribute("SecUsabilityProfile", "showSecUsabilityProfile");
				break;
			}

		}
		return req;
	}

	public static HttpServletRequest setGMRcheckboxes(HttpServletRequest req, RuleSets RSets) {
		// uncheck all at first
		req.setAttribute("ClassCh", "");
		req.setAttribute("ProCh", "");
		req.setAttribute("InstanceCh", "");
		req.setAttribute("CorresCh", "");
		req.setAttribute("CorssPropCh", "");
		req.setAttribute("ValueCh", "");
		req.setAttribute("StrCh", "");
		req.setAttribute("ClRedCh", "");
		req.setAttribute("ProRedCh", "");
		req.setAttribute("InstRedCh", "");
		req.setAttribute("ExtCh", "");
		req.setAttribute("EntCh", "");
		req.setAttribute("TypeCh", "");
		req.setAttribute("ConstValCh", "");
		req.setAttribute("DomRangMinCh", "");
		req.setAttribute("AcyClCh", "");
		req.setAttribute("AcyProCh", "");
		req.setAttribute("RecProCh", "");
		req.setAttribute("UnconnClCh", "");
		req.setAttribute("UnconnProCh", "");

		ArrayList<Integer> rs = RSets.getBestSet();
		if (rs == null) {
			return req;
		}
		for (int i = 0; i < rs.size(); i++) {
			int r = rs.get(i);
			switch (r) {
			case 1:
				req.setAttribute("ClassCh", "checked");
				break;

			case 2:
				req.setAttribute("ProCh", "checked");
				break;

			case 3:
				req.setAttribute("InstanceCh", "checked");
				break;

			case 4:
				req.setAttribute("CorresCh", "checked");
				break;

			case 5:
				req.setAttribute("CorssPropCh", "checked");
				break;

			case 6:
				req.setAttribute("ValueCh", "checked");
				break;

			case 7:
				req.setAttribute("StrCh", "checked");
				break;

			case 8:
				req.setAttribute("ClRedCh", "checked");
				break;

			case 9:
				req.setAttribute("ProRedCh", "checked");
				break;

			case 10:
				req.setAttribute("InstRedCh", "checked");
				break;

			case 11:
				req.setAttribute("ExtCh", "checked");
				break;

			case 12:
				req.setAttribute("EntCh", "checked");
				break;

			case 13:
				req.setAttribute("TypeCh", "checked");
				break;

			case 14:
				req.setAttribute("ConstValCh", "checked");
				break;

			case 15:
				req.setAttribute("DomRangMinCh", "checked");
				break;

			case 16:
				req.setAttribute("AcyClCh", "checked");
				break;

			case 17:
				req.setAttribute("AcyProCh", "checked");
				break;

			case 18:
				req.setAttribute("RecProCh", "checked");
				break;

			case 19:
				req.setAttribute("UnconnClCh", "checked");
				break;

			case 20:
				req.setAttribute("UnconnProCh", "checked");
				break;
			}
		}
		return req;
	}
}
