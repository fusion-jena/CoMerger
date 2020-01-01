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
 * email: samira[dot]babalou[at]uni[dash][dot]jena[dot]de Heinz-Nixdorf Chair
 * for Distributed Information Systems<br>
 * Institute for Computer Science, Friedrich Schiller University Jena, Germany
 * <br>
 * Date: 17/12/2019
 */
public class LocalRun_CompatibilityChecker {

	public static RuleSets RSets = new RuleSets();

	public static void main(String[] args) {

		/**
		 * @param GMRs
		 *            a set of Generic Merge Requirements
		 * @param numSuggestion
		 *            number of suggestion of all possible compatible sets
		 */

		/**
		 * set the additional files to your local path: <br>
		 * 1. go to in class: UserCliqueExtractor, function: readCSV, set the
		 * csvFile variable to the file that you have it in your local folder.
		 * This file exist in the Source_Code\resources folder. <br>
		 * 2. go to class:Demo, function: getDenseGraph, set the RuleInfo
		 * variable to the file that you have it in your local folder. This file
		 * exist in the Source_Code\resources folder
		 */

		// set other parameters
		int numSuggestion = 5;
		String GMRs = "ClassCheck";// R1
		GMRs = GMRs + "," + "ProCheck"; // R2
		GMRs = GMRs + "," + "InstanceCheck"; // R3
		GMRs = GMRs + "," + "CorresCheck"; // R4
		GMRs = GMRs + "," + "CorssPropCheck"; // R5
		GMRs = GMRs + "," + "ValueCheck"; // R6
		GMRs = GMRs + "," + "StrCheck"; // R7
		GMRs = GMRs + "," + "ClRedCheck"; // R8
		GMRs = GMRs + "," + "ProRedCheck"; // R9
		GMRs = GMRs + "," + "InstRedCheck"; // R10
		GMRs = GMRs + "," + "ExtCheck"; // R11
		GMRs = GMRs + "," + "EntCheck"; // R12
		GMRs = GMRs + "," + "TypeCheck"; // R13
		GMRs = GMRs + "," + "ConstValCheck"; // R14
		GMRs = GMRs + "," + "DomRangMinCheck"; // R15
		GMRs = GMRs + "," + "AcyClCheck"; // R16
		GMRs = GMRs + "," + "AcyProCheck"; // R17
		GMRs = GMRs + "," + "RecProCheck"; // R18
		GMRs = GMRs + "," + "UnconnClCheck"; // R19
		GMRs = GMRs + "," + "UnconnProCheck"; // R20

		RSets = CompatibilityChecker.RuleConflict(GMRs, numSuggestion);

		// Please put this output in a basic HMTL page
		System.out.println(RSets.getMessage());
		System.out.println("Done! put the output message in a basic HTML page.");

	}

}
