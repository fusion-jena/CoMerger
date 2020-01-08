/* Binary merge: Ladder */ // V8
package fusion.comerger.algorithm.merger.holisticMerge.localTest;
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
import fusion.comerger.algorithm.merger.model.HModel;

public class BinaryHolisticTest {

	public static int k = -1;
	public static String manualChanges = "NoChanges";

	public static void main(String[] args)
			throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		// run();
		// runVersion4();
		runVersion5();
//		runTimeVersion5();
	}

	private static void run() throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {

		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		String Refinement_without_local = StatisticTest.SetRefinement();
		String Refinement_with_local = Refinement_without_local + "," + "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = StatisticTest.SetEvalDimension();

		String ResultPath = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\result_binary_ladder.csv";
		GenerateOutput.createOutputHeader(ResultPath);

		int currentNumberDataSet = 10;
		int startIndex = 3; // in 1 & 2, n=2
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = startIndex; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= currentNumberDataSet; i++) {
			if (i != 8 && i != 9) {
				File folder = new File(address[i] + "\\originalFiles");
				String ontList = StatisticTest.listFilesForFolder(folder);
				String UPLOAD_DIRECTORY = address[i] + "\\";

				String inputOnt[] = ontList.split(";");
				String inputOntList = inputOnt[0];
				String[] ch = setCH(i);
				int totaltime = 0, totalCorrespondClass = 0, totalCorrespondObjPro = 0, totalCorrespondDPro = 0,
						totalLocalRefine = 0, totalGlobalRefine = 0, totalAxioms = 0;
				double totalReWrittedAxioms = 0.0;
				HModel ontM = new HModel();
				String testVersion = "d" + i + "v4_b";
				// run version 4: non-perfect mapping with refinement with local
				// refinement
				UPLOAD_DIRECTORY = UPLOAD_DIRECTORY + "temp\\";
				for (int j = 1; j < inputOnt.length; j++) {
					try {
						inputOntList = inputOntList + ";" + inputOnt[j];
						String mappingFile = "";
						mappingFile = MatchingProcess.CreateMap(inputOntList, ch[0], ch[1], UPLOAD_DIRECTORY);
						System.out.println("Finished create mapping for " + inputOntList);

						StatisticTest.result = new HashMap<String, String>();
						new MyLogging(UPLOAD_DIRECTORY);
						MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
						MA = new HolisticMerger();
						ontM = new HModel();
						ontM = MA.run(inputOntList, mappingFile, UPLOAD_DIRECTORY, Refinement_with_local, prefferdOnt,
								"RDFtype");

						System.out.println("Finished merging for " + inputOntList);

						totalAxioms = totalAxioms + Integer.valueOf(StatisticTest.result.get("total_axioms"));
						totaltime = totaltime + Integer.valueOf(StatisticTest.result.get("time_total"));
						totalCorrespondClass = totalCorrespondClass
								+ Integer.valueOf(StatisticTest.result.get("corresponding_Class"));
						totalCorrespondObjPro = totalCorrespondObjPro
								+ Integer.valueOf(StatisticTest.result.get("corresponding_object_properties"));
						totalCorrespondDPro = totalCorrespondDPro
								+ Integer.valueOf(StatisticTest.result.get("corresponding_data_properties"));
						totalReWrittedAxioms = (totalReWrittedAxioms
								+ Double.valueOf(StatisticTest.result.get("rewritted_axioms"))) / 2;
						if (StatisticTest.result.get("refine_action_on_cluster") != null)
							totalLocalRefine = totalLocalRefine
									+ Integer.valueOf(StatisticTest.result.get("refine_action_on_cluster"));
						totalGlobalRefine = totalGlobalRefine
								+ Integer.valueOf(StatisticTest.result.get("refine_action_on_merge"));

						inputOntList = ontM.getOntName();
					} catch (Exception e) {
						System.out.println("Error for " + inputOntList);
					}
				}
				StatisticTest.result.put("total_axioms", String.valueOf(totalAxioms));
				StatisticTest.result.put("time_total", String.valueOf(totaltime));
				StatisticTest.result.put("corresponding_Class", String.valueOf(totalCorrespondClass));
				StatisticTest.result.put("corresponding_object_properties", String.valueOf(totalCorrespondObjPro));
				StatisticTest.result.put("corresponding_data_properties", String.valueOf(totalCorrespondDPro));
				StatisticTest.result.put("rewritted_axioms", String.valueOf(totalReWrittedAxioms));
				StatisticTest.result.put("refine_action_on_cluster", String.valueOf(totalLocalRefine));
				StatisticTest.result.put("refine_action_on_merge", String.valueOf(totalGlobalRefine));

				Eval = new HEvaluator();
				Eval.run(ontM, evalDimension);
				GenerateOutput.saveResult(ResultPath, testVersion, StatisticTest.result);

				// run version 5: non-perfect mapping with refinement without
				// local refinement
				testVersion = "d" + i + "v5_b";
				inputOntList = inputOnt[0];
				totaltime = 0;
				totalCorrespondClass = 0;
				totalCorrespondObjPro = 0;
				totalCorrespondDPro = 0;
				totalLocalRefine = 0;
				totalGlobalRefine = 0;
				totalReWrittedAxioms = 0.0;
				for (int j = 1; j < inputOnt.length; j++) {
					inputOntList = inputOntList + ";" + inputOnt[j];
					String mappingFile = "";
					mappingFile = MatchingProcess.CreateMap(inputOntList, ch[0], ch[1], UPLOAD_DIRECTORY);

					StatisticTest.result = new HashMap<String, String>();
					new MyLogging(UPLOAD_DIRECTORY);
					MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
					MA = new HolisticMerger();
					ontM = new HModel();
					ontM = MA.run(inputOntList, mappingFile, UPLOAD_DIRECTORY, Refinement_without_local, prefferdOnt,
							"RDFtype");
					totaltime = totaltime + Integer.valueOf(StatisticTest.result.get("time_total"));
					totalCorrespondClass = totalCorrespondClass
							+ Integer.valueOf(StatisticTest.result.get("corresponding_Class"));
					totalCorrespondObjPro = totalCorrespondObjPro
							+ Integer.valueOf(StatisticTest.result.get("corresponding_object_properties"));
					totalCorrespondDPro = totalCorrespondDPro
							+ Integer.valueOf(StatisticTest.result.get("corresponding_data_properties"));
					totalReWrittedAxioms = (totalReWrittedAxioms
							+ Double.valueOf(StatisticTest.result.get("rewritted_axioms"))) / 2;
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

				Eval = new HEvaluator();
				Eval.run(ontM, evalDimension);
				GenerateOutput.saveResult(ResultPath, testVersion, StatisticTest.result);

				// run version 6: non-perfect mapping without any refinement
				// (without local refinement)

				testVersion = "d" + i + "v6_b";
				inputOntList = inputOnt[0];
				totaltime = 0;
				totalCorrespondClass = 0;
				totalCorrespondObjPro = 0;
				totalCorrespondDPro = 0;
				totalLocalRefine = 0;
				totalGlobalRefine = 0;
				totalReWrittedAxioms = 0.0;
				for (int j = 1; j < inputOnt.length; j++) {
					inputOntList = inputOntList + ";" + inputOnt[j];
					String mappingFile = "";
					mappingFile = MatchingProcess.CreateMap(inputOntList, ch[0], ch[1], UPLOAD_DIRECTORY);

					StatisticTest.result = new HashMap<String, String>();
					new MyLogging(UPLOAD_DIRECTORY);
					MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
					MA = new HolisticMerger();
					ontM = MA.run(inputOntList, mappingFile, UPLOAD_DIRECTORY, null, prefferdOnt, "RDFtype");
					totaltime = totaltime + Integer.valueOf(StatisticTest.result.get("time_total"));
					totalCorrespondClass = totalCorrespondClass
							+ Integer.valueOf(StatisticTest.result.get("corresponding_Class"));
					totalCorrespondObjPro = totalCorrespondObjPro
							+ Integer.valueOf(StatisticTest.result.get("corresponding_object_properties"));
					totalCorrespondDPro = totalCorrespondDPro
							+ Integer.valueOf(StatisticTest.result.get("corresponding_data_properties"));
					totalReWrittedAxioms = (totalReWrittedAxioms
							+ Double.valueOf(StatisticTest.result.get("rewritted_axioms"))) / 2;
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
				Eval = new HEvaluator();
				Eval.run(ontM, evalDimension);
				GenerateOutput.saveResult(ResultPath, testVersion, StatisticTest.result);

				// insert empty line in output
				GenerateOutput.inserEmptyLine(ResultPath);

				System.out.println("\t ---------********finished " + "d" + i);
			}
		}

		System.out.println("The End!");

	}

	private static void runVersion4() throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {

		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		String Refinement_without_local = StatisticTest.SetRefinement();
		String Refinement_with_local = Refinement_without_local + "," + "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = StatisticTest.SetEvalDimension();

		String ResultPath = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\result_binary.csv";
		GenerateOutput.createOutputHeader(ResultPath);

		int currentNumberDataSet = 10;
		int startIndex = 3; // in 1 & 2, n=2
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = startIndex; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= currentNumberDataSet; i++) {
			if (i != 8 && i != 9) {
				File folder = new File(address[i] + "\\originalFiles");
				String ontList = StatisticTest.listFilesForFolder(folder);
				String UPLOAD_DIRECTORY = address[i] + "\\";

				String inputOnt[] = ontList.split(";");
				String inputOntList = inputOnt[0];
				String[] ch = setCH(i);
				int totaltime = 0, totalCorrespondClass = 0, totalCorrespondObjPro = 0, totalCorrespondDPro = 0,
						totalLocalRefine = 0, totalGlobalRefine = 0;
				double totalReWrittedAxioms = 0.0;
				HModel ontM = new HModel();
				String testVersion = "d" + i + "v4_b";
				// run version 4: non-perfect mapping with refinement with local
				// refinement
				UPLOAD_DIRECTORY = UPLOAD_DIRECTORY + "temp\\";
				for (int j = 1; j < inputOnt.length; j++) {
					try {
						inputOntList = inputOntList + ";" + inputOnt[j];
						String mappingFile = "";
						mappingFile = MatchingProcess.CreateMap(inputOntList, ch[0], ch[1], UPLOAD_DIRECTORY);
						System.out.println("Finished create mapping for " + inputOntList);

						StatisticTest.result = new HashMap<String, String>();
						new MyLogging(UPLOAD_DIRECTORY);
						MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
						MA = new HolisticMerger();
						ontM = new HModel();
						ontM = MA.run(inputOntList, mappingFile, UPLOAD_DIRECTORY, Refinement_with_local, prefferdOnt,
								"RDFtype");

						System.out.println("Finished merging for " + inputOntList);

						totaltime = totaltime + Integer.valueOf(StatisticTest.result.get("time_total"));
						totalCorrespondClass = totalCorrespondClass
								+ Integer.valueOf(StatisticTest.result.get("corresponding_Class"));
						totalCorrespondObjPro = totalCorrespondObjPro
								+ Integer.valueOf(StatisticTest.result.get("corresponding_object_properties"));
						totalCorrespondDPro = totalCorrespondDPro
								+ Integer.valueOf(StatisticTest.result.get("corresponding_data_properties"));
						// totalReWrittedAxioms = (totalReWrittedAxioms
						// +
						// Double.valueOf(StatisticTest.result.get("rewritted_axioms")))
						// / 2;
						totalReWrittedAxioms = totalReWrittedAxioms
								+ Integer.valueOf(StatisticTest.result.get("rewritted_axioms"));
						if (StatisticTest.result.get("refine_action_on_cluster") != null)
							totalLocalRefine = totalLocalRefine
									+ Integer.valueOf(StatisticTest.result.get("refine_action_on_cluster"));
						totalGlobalRefine = totalGlobalRefine
								+ Integer.valueOf(StatisticTest.result.get("refine_action_on_merge"));

						inputOntList = ontM.getOntName();
					} catch (Exception e) {
						System.out.println("Error for " + inputOntList);
					}
				}
				StatisticTest.result.put("time_total", String.valueOf(totaltime));
				StatisticTest.result.put("corresponding_Class", String.valueOf(totalCorrespondClass));
				StatisticTest.result.put("corresponding_object_properties", String.valueOf(totalCorrespondObjPro));
				StatisticTest.result.put("corresponding_data_properties", String.valueOf(totalCorrespondDPro));
				StatisticTest.result.put("rewritted_axioms", String.valueOf(totalReWrittedAxioms));
				StatisticTest.result.put("refine_action_on_cluster", String.valueOf(totalLocalRefine));
				StatisticTest.result.put("refine_action_on_merge", String.valueOf(totalGlobalRefine));

				Eval = new HEvaluator();
				Eval.run(ontM, evalDimension);
				GenerateOutput.saveResult(ResultPath, testVersion, StatisticTest.result);

				// insert empty line in output
				GenerateOutput.inserEmptyLine(ResultPath);

				System.out.println("\t ---------********finished " + "d" + i);
			}
		}

		System.out.println("The End!");

	}

	private static void runVersion5() throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {

		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		String Refinement_without_local = StatisticTest.SetRefinement();
		String Refinement_with_local = Refinement_without_local + "," + "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = StatisticTest.SetEvalDimension();

		String failedFiles = "";

		String ResultPath = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\result_binary_ladder.csv";
		GenerateOutput.createOutputHeader(ResultPath);

		int currentNumberDataSet = 12;
		int startIndex = 3; // in 1 & 2, n=2
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = startIndex; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= currentNumberDataSet; i++) {
			if (i != 7 && i != 8) {
				File folder = new File(address[i] + "\\originalFiles");
				String ontList = StatisticTest.listFilesForFolder(folder);
				String UPLOAD_DIRECTORY = address[i] + "\\";

				String inputOnt[] = ontList.split(";");
				String inputOntList = inputOnt[0];
				String[] ch = setCH(i);
				int totaltime = 0, totalCorrespondClass = 0, totalCorrespondObjPro = 0, totalCorrespondDPro = 0,
						totalLocalRefine = 0, totalGlobalRefine = 0;
				double totalReWrittedAxioms = 0.0;
				HModel ontM = new HModel();
				String testVersion = "d" + i + "v5_b";
				int mergeCounter = 0;
				// run version 4: non-perfect mapping with refinement with local
				// refinement
				UPLOAD_DIRECTORY = UPLOAD_DIRECTORY + "temp\\";
				for (int j = 1; j < inputOnt.length; j++) {
					String mappingFile = "";
					try {
						inputOntList = inputOntList + ";" + inputOnt[j];
						String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss")
								.format(Calendar.getInstance().getTime());
						System.out.println("start creating mapping for " + inputOntList + "\t" + currentTime);
						System.gc();
						mappingFile = MatchingProcess.CreateMap(inputOntList, ch[0], ch[1], UPLOAD_DIRECTORY);
						System.out.println("Finished create mapping for " + inputOntList);
					} catch (Exception e) {
						System.out.println("Error for " + inputOntList + "\n" + e);
						failedFiles = failedFiles + "-" + inputOntList;
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
					// totalReWrittedAxioms = (totalReWrittedAxioms
					// +
					// Double.valueOf(StatisticTest.result.get("rewritted_axioms")))
					// / 2;
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
				GenerateOutput.saveResult(ResultPath, testVersion, StatisticTest.result);

				// insert empty line in output
				// GenerateOutput.inserEmptyLine(ResultPath);

				System.out.println("\t ---------********finished " + "d" + i);
			}
		}

		System.out.println("Failed Files: " + failedFiles);
		System.out.println("The End!");

	}

	private static void runTimeVersion5()
			throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {

		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		String Refinement_without_local = StatisticTest.SetRefinement();
		String Refinement_with_local = Refinement_without_local + "," + "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = StatisticTest.SetEvalDimension();

		String failedFiles = "";

		String ResultPath = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\result_binary_Time.csv";
		GenerateOutput.createOutputHeader(ResultPath);

		int currentNumberDataSet = 12;
		int startIndex = 3; // in 1 & 2, n=2
		int time = 3;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = startIndex; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= currentNumberDataSet; i++) {
			if (i != 7 && i != 8) {
				for (int t = 0; t < time; t++) {
					File folder = new File(address[i] + "\\originalFiles");
					String ontList = StatisticTest.listFilesForFolder(folder);
					String UPLOAD_DIRECTORY = address[i] + "\\";

					String inputOnt[] = ontList.split(";");
					String inputOntList = inputOnt[0];
					String[] ch = setCH(i);
					int totaltime = 0, totalCorrespondClass = 0, totalCorrespondObjPro = 0, totalCorrespondDPro = 0,
							totalLocalRefine = 0, totalGlobalRefine = 0;
					double totalReWrittedAxioms = 0.0;
					HModel ontM = new HModel();
					String testVersion = "d" + i + "v8_b";
					int mergeCounter = 0;
					// run version 4: non-perfect mapping with refinement with
					// local
					// refinement
					UPLOAD_DIRECTORY = UPLOAD_DIRECTORY + "temp\\";
					for (int j = 1; j < inputOnt.length; j++) {
						String mappingFile = "";
						try {
							inputOntList = inputOntList + ";" + inputOnt[j];
							String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss")
									.format(Calendar.getInstance().getTime());
							System.out.println("start creating mapping for " + inputOntList + "\t" + currentTime);
							System.gc();
							mappingFile = MatchingProcess.CreateMap(inputOntList, ch[0], ch[1], UPLOAD_DIRECTORY);
							System.out.println("Finished create mapping for " + inputOntList);
						} catch (Exception e) {
							System.out.println("Error for " + inputOntList + "\n" + e);
							failedFiles = failedFiles + "-" + inputOntList;
						}
						StatisticTest.result = new HashMap<String, String>();
						new MyLogging(UPLOAD_DIRECTORY);
						MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
						MA = new HolisticMerger();
						ontM = new HModel();
						String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss")
								.format(Calendar.getInstance().getTime());
						System.out.println("Start merging for " + inputOntList + "\t" + currentTime);
						ontM = MA.run(inputOntList, mappingFile, UPLOAD_DIRECTORY, Refinement_without_local,
								prefferdOnt, "RDFtype");
						mergeCounter++;

						System.out.println("Finished merging for " + inputOntList);

						totaltime = totaltime + Integer.valueOf(StatisticTest.result.get("time_total"));
						totalCorrespondClass = totalCorrespondClass
								+ Integer.valueOf(StatisticTest.result.get("corresponding_Class"));
						totalCorrespondObjPro = totalCorrespondObjPro
								+ Integer.valueOf(StatisticTest.result.get("corresponding_object_properties"));
						totalCorrespondDPro = totalCorrespondDPro
								+ Integer.valueOf(StatisticTest.result.get("corresponding_data_properties"));
						// totalReWrittedAxioms = (totalReWrittedAxioms
						// +
						// Double.valueOf(StatisticTest.result.get("rewritted_axioms")))
						// / 2;
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
					GenerateOutput.saveResult(ResultPath, testVersion, StatisticTest.result);

					System.out.println("\t ---------********finished " + "d" + i);
				}
				// insert empty line in output
				GenerateOutput.inserEmptyLine(ResultPath);
			}
		}

		System.out.println("Failed Files: " + failedFiles);
		System.out.println("The End!");

	}

	private static String[] setCH(int datasetId) {
		String[] ch = new String[2];
		ch[0] = "1";
		ch[1] = "1";

		switch (datasetId) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 10:
			return ch;
		case 8:
			ch[0] = "10";
			ch[1] = "10";
			return ch;
		case 9:
			ch[0] = "8";
			ch[1] = "8";
			return ch;

		case 11:
			ch[0] = "2";
			ch[1] = "2";
			return ch;
		}
		return ch;
	}

}
