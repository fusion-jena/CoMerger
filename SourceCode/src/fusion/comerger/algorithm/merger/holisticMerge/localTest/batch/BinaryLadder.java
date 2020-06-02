/* Binary merge: Ladder */ // V8
package fusion.comerger.algorithm.merger.holisticMerge.localTest.batch;
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
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import fusion.comerger.servlets.MatchingProcess;
import fusion.comerger.algorithm.merger.holisticMerge.HolisticMerger;
import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.evaluator.HEvaluator;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.model.HModel;

public class BinaryLadder {

	public static void main(String[] args)
			throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {

		/**
		 * @folderStructure Put files in a folder under name d1, or d2, or d3
		 *                  etc. Then, determine the number of folders that you
		 *                  have in numberDataSet variable This code will be run
		 *                  for folders starting from startIndex variable till
		 *                  numberDataSet For each folder (di), all six versions
		 *                  will be run, then the next folder (di+1) will be
		 *                  run. The last result will be saved as a CSV file
		 * 
		 *                  In each di folder create a sub-folder:
		 * @source_ontologies put all source ontologies
		 * 
		 * 
		 *                    For more information, look to folder structure in
		 *                    https://github.com/fusion-jena/CoMerger/tree/
		 *                    master /Ontology_Merging_Datasets
		 */

		/**
		 * @param ResultPath
		 *            name and path of a output CSV file
		 * @param baseAddress
		 *            the base address for all folders that you determined. e.g.
		 *            if your di folder is allocated in "C:\\temp\\dataset\\d1",
		 *            then the base address should be "C:\\temp\\dataset\\"
		 * @param prefferdOnt
		 *            the preferred ontology
		 * @param numberDataSet
		 *            set the number of dataset as the number of your di folders
		 */

		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		String Refinement_without_local = StatisticTest.SetRefinement();
		String prefferdOnt = "equal";
		String evalDimension = StatisticTest.SetEvalDimension();

		String ResultPath = "C:\\YOUR_LOCAL_FOLDER\\result_binary_ladder.csv";
		GenerateOutputForCSV.createOutputHeader(ResultPath);

		int currentNumberDataSet = 12;
		int startIndex = 3;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\YOUR_LOCAL_FOLDER\\";
		for (int j = startIndex; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= currentNumberDataSet; i++) {
			if (i != 7 && i != 8) {
				File folder = new File(address[i] + "\\source_ontologies");
				String ontList = StatisticTest.listFilesForFolder(folder);
				String UPLOAD_DIRECTORY = address[i] + "\\";

				String inputOnt[] = ontList.split(";");
				String inputOntList = inputOnt[0];
				String[] ch = new String[2];
				ch[0] = "1";
				ch[1] = "1";
				int totaltime = 0, totalCorrespondClass = 0, totalCorrespondObjPro = 0, totalCorrespondDPro = 0,
						totalLocalRefine = 0, totalGlobalRefine = 0;
				double totalReWrittedAxioms = 0.0;
				HModel ontM = new HModel();
				String testVersion = "d" + i + "v5_b";
				int mergeCounter = 0;
				/*
				 * run version 4: non-perfect mapping with refinement with local
				 * refinement
				 */
				UPLOAD_DIRECTORY = UPLOAD_DIRECTORY + "temp\\";
				String matchType = "Jacard"; // "SeeCOnt";
				for (int j = 1; j < inputOnt.length; j++) {
					String mappingFile = "";
					try {
						inputOntList = inputOntList + ";" + inputOnt[j];
						String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss")
								.format(Calendar.getInstance().getTime());
						System.out.println("start creating mapping for " + inputOntList + "\t" + currentTime);
						System.gc();
						mappingFile = MatchingProcess.CreateMap(inputOntList, ch[0], ch[1], UPLOAD_DIRECTORY,
								matchType);
						System.out.println("Finished create mapping for " + inputOntList);
					} catch (Exception e) {
						System.out.println("Error for " + inputOntList + "\n" + e);

					}
					StatisticTest.result = new HashMap<String, String>();
					new MyLogging(UPLOAD_DIRECTORY);
					MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
					MA = new HolisticMerger();
					ontM = new HModel();
					String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss")
							.format(Calendar.getInstance().getTime());
					System.out.println("Start merging for " + inputOntList + "\t" + currentTime);
					ontM = MA.run(inputOntList, mappingFile, UPLOAD_DIRECTORY, Refinement_without_local, prefferdOnt,
							"RDFtype");
					mergeCounter++;

					System.out.println("Finished merging for " + inputOntList);

					totaltime = totaltime + Integer.valueOf(StatisticTest.result.get("time_total"));
					totalCorrespondClass = totalCorrespondClass
							+ Integer.valueOf(StatisticTest.result.get("corresponding_Class"));
					totalCorrespondObjPro = totalCorrespondObjPro
							+ Integer.valueOf(StatisticTest.result.get("corresponding_object_properties"));
					totalCorrespondDPro = totalCorrespondDPro
							+ Integer.valueOf(StatisticTest.result.get("corresponding_data_properties"));

					totalReWrittedAxioms = totalReWrittedAxioms
							+ Integer.valueOf(StatisticTest.result.get("rewritted_axioms"));
					if (StatisticTest.result.get("refine_action_on_cluster") != null)
						totalLocalRefine = totalLocalRefine
								+ Integer.valueOf(StatisticTest.result.get("refine_action_on_cluster"));
					totalGlobalRefine = totalGlobalRefine
							+ Integer.valueOf(StatisticTest.result.get("refine_action_on_merge"));

					inputOntList = ontM.getOntName();

				}
				StatisticTest.result.put("time_total", String.valueOf(totaltime));
				StatisticTest.result.put("corresponding_Class", String.valueOf(totalCorrespondClass));
				StatisticTest.result.put("corresponding_object_properties", String.valueOf(totalCorrespondObjPro));
				StatisticTest.result.put("corresponding_data_properties", String.valueOf(totalCorrespondDPro));
				StatisticTest.result.put("rewritted_axioms", String.valueOf(totalReWrittedAxioms));
				StatisticTest.result.put("refine_action_on_cluster", String.valueOf(totalLocalRefine));
				StatisticTest.result.put("refine_action_on_merge", String.valueOf(totalGlobalRefine));
				StatisticTest.result.put("num_merge_operation", String.valueOf(mergeCounter));

				Eval = new HEvaluator();
				Eval.run(ontM, evalDimension);
				GenerateOutputForCSV.saveResult(ResultPath, testVersion, StatisticTest.result);

				System.out.println("\t ---------********finished " + "d" + i);
			}
		}

		System.out.println("The End!");

	}

}
