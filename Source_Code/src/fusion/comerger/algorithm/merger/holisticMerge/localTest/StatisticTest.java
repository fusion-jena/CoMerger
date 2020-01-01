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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import fusion.comerger.servlets.MatchingProcess;
import fusion.comerger.algorithm.merger.holisticMerge.HolisticMerger;
import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.evaluator.HEvaluator;
import fusion.comerger.algorithm.merger.model.HModel;

public class StatisticTest {
	public static HashMap<String, String> result = new HashMap<String, String>();
	public static int k = -1;
	public static String manualChanges = "NoChanges";
	// public static String anchorType = "SimilarAnchor";

	public static void main(String[] args)
			throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {

		// manualChanges = "doChanges";
		// anchorType ="allAnchor";
		runTestStatistic();
//		runTimeVersion5();
		// runTestStatisticVersion3();
		// runTestStatisticVersion4();
		// runTestStatisticVersion5();
		// runTestK();
		// runTestKVersion4();// only version 4
		// singleRepeatedTest(); // for speed up
	}

	private static void singleRepeatedTest()
			throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		String Refinement_without_local = SetRefinement();
		String Refinement_with_local = Refinement_without_local + "," + "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = SetEvalDimension();

		String ResultPath = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\resultSpeedUp.csv";
		GenerateOutput.createOutputHeader(ResultPath);

		int currentNumberDataSet = 8;
		int startIndex = 1;
		int repeated = 5;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = startIndex; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= currentNumberDataSet; i++) {
			File folder = new File(address[i] + "\\originalFiles");
			String ontList = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_perfect");
			String mappingFilePerfect = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_nonPerfect");
			String mappingFileNonPerfect = listFilesForFolder(folder);
			String UPLOAD_DIRECTORY = address[i] + "\\";

			for (int rep = 0; rep < repeated; rep++) {
				// run version 1: perfect mapping with refinement with local
				// refinement
				result = new HashMap<String, String>();
				String testVersion = "$d_" + i + "V_1$";
				new MyLogging(UPLOAD_DIRECTORY);
				MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
				MA = new HolisticMerger();
				HModel ontM = MA.run(ontList, mappingFilePerfect, UPLOAD_DIRECTORY, Refinement_with_local, prefferdOnt,
						"RDFtype");
				Eval = new HEvaluator();
				Eval.run(ontM, evalDimension);
				GenerateOutput.saveResult(ResultPath, testVersion, result);
			}
			// insert empty line in output
			GenerateOutput.inserEmptyLine(ResultPath);

			System.out.println("\t ---------********finished " + "d" + i);

		}
		System.out.println("The End!");

	}

	public static void runTestStatistic()
			throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		// run one time without manual changes, save the files. again, run it
		// and uncomment manual changes and record output

		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		HModel ontM = new HModel();
		String Refinement_without_local = SetRefinement();
		String Refinement_with_local = Refinement_without_local + "," + "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = SetEvalDimension();

		long beforeUsedMem = 0, afterUsedMem = 0, actualMemUsed = 0;

		String ResultPath = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\result.csv";
		GenerateOutput.createOutputHeader(ResultPath);

		int currentNumberDataSet = 12;
		int startIndex = 1;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = startIndex; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= currentNumberDataSet; i++) {
			File folder = new File(address[i] + "\\originalFiles");
			String ontList = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_perfect");
			String mappingFilePerfect = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_nonPerfect");
			String mappingFileNonPerfect = listFilesForFolder(folder);
			String UPLOAD_DIRECTORY = address[i] + "\\";

			// run version 1: perfect mapping with refinement with local
			// refinement
			// Runtime.getRuntime().gc();
			beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			result = new HashMap<String, String>();
			String testVersion = "$d_{" + i + "}V_1$";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			// MA = new HolisticMerger();
			// ontM = new HModel();
			ontM = MA.run(ontList, mappingFilePerfect, UPLOAD_DIRECTORY, Refinement_with_local, prefferdOnt, "RDFtype");
			// //Runtime.getRuntime().gc();

			afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			actualMemUsed = afterUsedMem - beforeUsedMem;
			System.out.println("\t -***Total: \t afterUsedMem: " + afterUsedMem + "\t beforeUsedMem:" + beforeUsedMem
					+ "\t actualMemUsed: " + actualMemUsed + "\n Strat doing evlaution");
			// Runtime.getRuntime().gc();
			// result.put("MU_TotalMerge", String.valueOf(actualMemUsed));
			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutput.saveResult(ResultPath, testVersion, result);
			System.out.println("finsihed saving result in csv");
			// ****
			// run version 2: perfect mapping with refinement without local
			// refinement
			// Runtime.getRuntime().gc();
			beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			result = new HashMap<String, String>();
			testVersion = "$d_{" + i + "}V_2$";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			ontM = MA.run(ontList, mappingFilePerfect, UPLOAD_DIRECTORY, Refinement_without_local, prefferdOnt,
					"RDFtype");

			afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			actualMemUsed = afterUsedMem - beforeUsedMem;
			// Runtime.getRuntime().gc();
			// result.put("MU_TotalMerge", String.valueOf(actualMemUsed));
			System.out.println("\t -***Total: \t afterUsedMem: " + afterUsedMem + "\t beforeUsedMem:" + beforeUsedMem
					+ "\t actualMemUsed: " + actualMemUsed);
			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutput.saveResult(ResultPath, testVersion, result);

			// run version 3: perfect mapping without any refinement
			// (without local refinement)
			// Runtime.getRuntime().gc();
			beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			result = new HashMap<String, String>();
			testVersion = "$d_{" + i + "}V_3$";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			ontM = MA.run(ontList, mappingFilePerfect, UPLOAD_DIRECTORY, null, prefferdOnt, "RDFtype");
			// //Runtime.getRuntime().gc();
			afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			actualMemUsed = afterUsedMem - beforeUsedMem;
			// Runtime.getRuntime().gc();
			// result.put("MU_TotalMerge", String.valueOf(actualMemUsed));
			System.out.println("\t -***Total: \t afterUsedMem: " + afterUsedMem + "\t beforeUsedMem:" + beforeUsedMem
					+ "\t actualMemUsed: " + actualMemUsed);
			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutput.saveResult(ResultPath, testVersion, result);

			// run version 4: non-perfect mapping with refinement with local
			// refinement
			// Runtime.getRuntime().gc();
			beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			result = new HashMap<String, String>();
			testVersion = "$d_{" + i + "}V_4$";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			ontM = MA.run(ontList, mappingFileNonPerfect, UPLOAD_DIRECTORY, Refinement_with_local, prefferdOnt,
					"RDFtype");
			// //Runtime.getRuntime().gc();
			afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			actualMemUsed = afterUsedMem - beforeUsedMem;
			// Runtime.getRuntime().gc();
			// result.put("MU_TotalMerge", String.valueOf(actualMemUsed));
			System.out.println("\t -***Total: \t afterUsedMem: " + afterUsedMem + "\t beforeUsedMem:" + beforeUsedMem
					+ "\t actualMemUsed: " + actualMemUsed);
			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutput.saveResult(ResultPath, testVersion, result);

			// run version 5: non-perfect mapping with refinement without
			// local refinement
			// Runtime.getRuntime().gc();
			beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			result = new HashMap<String, String>();
			testVersion = "$d_{" + i + "}V_5$";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			ontM = MA.run(ontList, mappingFileNonPerfect, UPLOAD_DIRECTORY, Refinement_without_local, prefferdOnt,
					"RDFtype");
			// Runtime.getRuntime().gc();
			afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			actualMemUsed = afterUsedMem - beforeUsedMem;
			// Runtime.getRuntime().gc();
			// result.put("MU_TotalMerge", String.valueOf(actualMemUsed));
			System.out.println("\t -***Total: \t afterUsedMem: " + afterUsedMem + "\t beforeUsedMem:" + beforeUsedMem
					+ "\t actualMemUsed: " + actualMemUsed);
			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutput.saveResult(ResultPath, testVersion, result);

			// run version 6: non-perfect mapping without any refinement
			// (without local refinement)
			// Runtime.getRuntime().gc();
			beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			result = new HashMap<String, String>();
			testVersion = "$d_{" + i + "}V_6$";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			ontM = MA.run(ontList, mappingFileNonPerfect, UPLOAD_DIRECTORY, null, prefferdOnt, "RDFtype");
			// Runtime.getRuntime().gc();
			afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			actualMemUsed = afterUsedMem - beforeUsedMem;
			// Runtime.getRuntime().gc();
			// result.put("MU_TotalMerge", String.valueOf(actualMemUsed));
			System.out.println("\t -***Total: \t afterUsedMem: " + afterUsedMem + "\t beforeUsedMem:" + beforeUsedMem
					+ "\t actualMemUsed: " + actualMemUsed);
			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutput.saveResult(ResultPath, testVersion, result);

			// insert empty line in output
			// GenerateOutput.inserEmptyLine(ResultPath);

			System.out.println("\t ---------********finished " + "d" + i);

		}
		System.out.println("The End!");
	}

	public static void runTimeVersion5() throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		// run one time without manual changes, save the files. again, run it
		// and uncomment manual changes and record output

		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		HModel ontM = new HModel();
		String Refinement_without_local = SetRefinement();
		String Refinement_with_local = Refinement_without_local + "," + "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = SetEvalDimension();

		long beforeUsedMem = 0, afterUsedMem = 0, actualMemUsed = 0;

		String ResultPath = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\resultTime.csv";
		GenerateOutput.createOutputHeader(ResultPath);

		int currentNumberDataSet = 12;
		int startIndex = 1;
		int time = 3;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = startIndex; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= currentNumberDataSet; i++) {
			File folder = new File(address[i] + "\\originalFiles");
			String ontList = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_perfect");
			String mappingFilePerfect = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_nonPerfect");
			String mappingFileNonPerfect = listFilesForFolder(folder);
			String UPLOAD_DIRECTORY = address[i] + "\\";

			for (int t = 0; t < time; t++) {

				// run version 5: non-perfect mapping with refinement without
				// local refinement
				// Runtime.getRuntime().gc();
				beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				result = new HashMap<String, String>();
				String testVersion = "$d_{" + i + "}V_5$";
				new MyLogging(UPLOAD_DIRECTORY);
				MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
				MA = new HolisticMerger();
				ontM = MA.run(ontList, mappingFileNonPerfect, UPLOAD_DIRECTORY, Refinement_without_local, prefferdOnt,
						"RDFtype");
				// Runtime.getRuntime().gc();
				afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				actualMemUsed = afterUsedMem - beforeUsedMem;
				// Runtime.getRuntime().gc();
				// result.put("MU_TotalMerge", String.valueOf(actualMemUsed));
				System.out.println("\t -***Total: \t afterUsedMem: " + afterUsedMem + "\t beforeUsedMem:"
						+ beforeUsedMem + "\t actualMemUsed: " + actualMemUsed);
				Eval = new HEvaluator();
				Eval.run(ontM, evalDimension);
				GenerateOutput.saveResult(ResultPath, testVersion, result);

				System.out.println("\t ---------********finished " + "d" + i);
			}

			// insert empty line in output
			GenerateOutput.inserEmptyLine(ResultPath);
		}
		System.out.println("The End!");
	}

	public static void runTestStatisticVersion3()
			throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		// run one time without manual changes, save the files. again, run it
		// and uncomment manual changes and record output

		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		String Refinement_without_local = SetRefinement();
		String Refinement_with_local = Refinement_without_local + "," + "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = SetEvalDimension();

		String ResultPath = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\result.csv";
		GenerateOutput.createOutputHeader(ResultPath);

		int currentNumberDataSet = 5;
		int startIndex = 1;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = startIndex; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= currentNumberDataSet; i++) {
			File folder = new File(address[i] + "\\originalFiles");
			String ontList = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_perfect");
			String mappingFilePerfect = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_nonPerfect");
			String mappingFileNonPerfect = listFilesForFolder(folder);
			String UPLOAD_DIRECTORY = address[i] + "\\";

			// run version 3: perfect mapping without any refinement
			// (without local refinement)
			result = new HashMap<String, String>();
			String testVersion = "$d_" + i + "V_3$";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			HModel ontM = MA.run(ontList, mappingFilePerfect, UPLOAD_DIRECTORY, null, prefferdOnt, "RDFtype");
			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutput.saveResult(ResultPath, testVersion, result);

			System.out.println("\t ---------********finished " + "d" + i);

		}
		System.out.println("The End!");
	}

	public static void runTestStatisticVersion4()
			throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		// run one time without manual changes, save the files. again, run it
		// and uncomment manual changes and record output

		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		String Refinement_without_local = SetRefinement();
		String Refinement_with_local = Refinement_without_local + "," + "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = SetEvalDimension();

		String ResultPath = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\result.csv";
		GenerateOutput.createOutputHeader(ResultPath);

		int currentNumberDataSet = 9;
		int startIndex = 1;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = startIndex; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= currentNumberDataSet; i++) {
			File folder = new File(address[i] + "\\originalFiles");
			String ontList = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_perfect");
			String mappingFilePerfect = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_nonPerfect");
			String mappingFileNonPerfect = listFilesForFolder(folder);
			String UPLOAD_DIRECTORY = address[i] + "\\";

			// run version 4: non-perfect mapping with refinement with local
			// refinement
			result = new HashMap<String, String>();
			String testVersion = "$d_" + i + "V_4$";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			HModel ontM = MA.run(ontList, mappingFileNonPerfect, UPLOAD_DIRECTORY, Refinement_with_local, prefferdOnt,
					"RDFtype");
			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutput.saveResult(ResultPath, testVersion, result);

			// insert empty line in output
			GenerateOutput.inserEmptyLine(ResultPath);

			System.out.println("\t ---------********finished " + "d" + i);

		}
		System.out.println("The End!");
	}

	public static void runTestStatisticVersion5()
			throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		// run one time without manual changes, save the files. again, run it
		// and uncomment manual changes and record output

		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		String Refinement_without_local = SetRefinement();
		String Refinement_with_local = Refinement_without_local + "," + "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = SetEvalDimension();

		String ResultPath = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\result.csv";
		GenerateOutput.createOutputHeader(ResultPath);

		int currentNumberDataSet = 10;
		int startIndex = 3;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = startIndex; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= currentNumberDataSet; i++) {
			File folder = new File(address[i] + "\\originalFiles");
			String ontList = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_perfect");
			String mappingFilePerfect = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_nonPerfect");
			String mappingFileNonPerfect = listFilesForFolder(folder);
			String UPLOAD_DIRECTORY = address[i] + "\\";

			// run version 4: non-perfect mapping with refinement with local
			// refinement
			result = new HashMap<String, String>();
			String testVersion = "$d_" + i + "V_5$";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			HModel ontM = MA.run(ontList, mappingFileNonPerfect, UPLOAD_DIRECTORY, Refinement_without_local,
					prefferdOnt, "RDFtype");
			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutput.saveResult(ResultPath, testVersion, result);

			// insert empty line in output
			// GenerateOutput.inserEmptyLine(ResultPath);

			System.out.println("\t ---------********finished " + "d" + i);

		}
		System.out.println("The End!");
	}

	public static void runTestK() throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		// run one time without manual changes, save the files. again, run it
		// and uncomment manual changes and record output

		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		HModel ontM;
		String testVersion;

		String Refinement_without_local = SetRefinement();
		String Refinement_with_local = Refinement_without_local + "," + "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = SetEvalDimension();

		String ResultPath = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\result_K_test.csv";
		GenerateOutput.createOutputHeader(ResultPath);

		int currentNumberDataSet = 11;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = 1; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = 1; i <= currentNumberDataSet; i++) {
			if (i == 4) {
				// skip;
			} else {
				File folder = new File(address[i] + "\\originalFiles");
				String ontList = listFilesForFolder(folder);
				folder = new File(address[i] + "\\align_perfect");
				String mappingFilePerfect = listFilesForFolder(folder);
				folder = new File(address[i] + "\\align_nonPerfect");
				String mappingFileNonPerfect = listFilesForFolder(folder);
				String UPLOAD_DIRECTORY = address[i] + "\\";
				int n = ontList.split(";").length;
				// run version 1: perfect mapping with refinement with local
				// refinement
				for (int kk = 1; kk <= n; kk++) {
					k = kk;
					result = new HashMap<String, String>();
					testVersion = "d" + i + "v1" + "k" + k;
					new MyLogging(UPLOAD_DIRECTORY);
					MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
					MA = new HolisticMerger();
					ontM = MA.run(ontList, mappingFilePerfect, UPLOAD_DIRECTORY, Refinement_with_local, prefferdOnt,
							"RDFtype");
					Eval = new HEvaluator();
					Eval.run(ontM, evalDimension);
					GenerateOutput.saveResult(ResultPath, testVersion, result);
				}
				// run version 2: perfect mapping with refinement without local
				// refinement
				for (int kk = 1; kk <= n; kk++) {
					k = kk;
					result = new HashMap<String, String>();
					testVersion = "d" + i + "v2" + "k" + k;
					new MyLogging(UPLOAD_DIRECTORY);
					MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
					MA = new HolisticMerger();
					ontM = MA.run(ontList, mappingFilePerfect, UPLOAD_DIRECTORY, Refinement_without_local, prefferdOnt,
							"RDFtype");
					Eval = new HEvaluator();
					Eval.run(ontM, evalDimension);
					GenerateOutput.saveResult(ResultPath, testVersion, result);
				}

				// run version 3: perfect mapping without any refinement
				// (without local refinement)
				for (int kk = 1; kk <= n; kk++) {
					k = kk;
					result = new HashMap<String, String>();
					testVersion = "d" + i + "v3" + "k" + k;
					new MyLogging(UPLOAD_DIRECTORY);
					MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
					MA = new HolisticMerger();
					ontM = MA.run(ontList, mappingFilePerfect, UPLOAD_DIRECTORY, null, prefferdOnt, "RDFtype");
					Eval = new HEvaluator();
					Eval.run(ontM, evalDimension);
					GenerateOutput.saveResult(ResultPath, testVersion, result);
				}

				// run version 4: non-perfect mapping with refinement with local
				// refinement
				for (int kk = 1; kk <= n; kk++) {
					k = kk;
					result = new HashMap<String, String>();
					testVersion = "d" + i + "v4" + "k" + k;
					new MyLogging(UPLOAD_DIRECTORY);
					MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
					MA = new HolisticMerger();
					ontM = MA.run(ontList, mappingFileNonPerfect, UPLOAD_DIRECTORY, Refinement_with_local, prefferdOnt,
							"RDFtype");
					Eval = new HEvaluator();
					Eval.run(ontM, evalDimension);
					GenerateOutput.saveResult(ResultPath, testVersion, result);
				}

				// run version 5: non-perfect mapping with refinement without
				// local refinement
				for (int kk = 1; kk <= n; kk++) {
					k = kk;
					result = new HashMap<String, String>();
					testVersion = "d" + i + "v5" + "k" + k;
					new MyLogging(UPLOAD_DIRECTORY);
					MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
					MA = new HolisticMerger();
					ontM = MA.run(ontList, mappingFileNonPerfect, UPLOAD_DIRECTORY, Refinement_without_local,
							prefferdOnt, "RDFtype");
					Eval = new HEvaluator();
					Eval.run(ontM, evalDimension);
					GenerateOutput.saveResult(ResultPath, testVersion, result);
				}

				// run version 6: non-perfect mapping without any refinement
				// (without local refinement)
				for (int kk = 1; kk <= n; kk++) {
					k = kk;
					result = new HashMap<String, String>();
					testVersion = "d" + i + "v6" + "k" + k;
					new MyLogging(UPLOAD_DIRECTORY);
					MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
					MA = new HolisticMerger();
					ontM = MA.run(ontList, mappingFileNonPerfect, UPLOAD_DIRECTORY, null, prefferdOnt, "RDFtype");
					Eval = new HEvaluator();
					Eval.run(ontM, evalDimension);
					GenerateOutput.saveResult(ResultPath, testVersion, result);
				}
				// insert empty line in output
				GenerateOutput.inserEmptyLine(ResultPath);

				System.out.println("\t ---------********finished " + "d" + i);
			}
		}
		System.out.println("The End!");
	}

	public static void runTestKVersion4()
			throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		// run one time without manual changes, save the files. again, run it
		// and uncomment manual changes and record output

		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		HModel ontM;
		String testVersion;

		String Refinement_without_local = SetRefinement();
		String Refinement_with_local = Refinement_without_local + "," + "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = SetEvalDimension();

		String ResultPath = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\result_K_test.csv";
		GenerateOutput.createOutputHeader(ResultPath);

		int currentNumberDataSet = 11;
		int start = 11;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = start; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = start; i <= currentNumberDataSet; i++) {
			File folder = new File(address[i] + "\\originalFiles");
			String ontList = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_perfect");
			String mappingFilePerfect = listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_nonPerfect");
			String mappingFileNonPerfect = listFilesForFolder(folder);
			String UPLOAD_DIRECTORY = address[i] + "\\";
			int n = ontList.split(";").length;

			// run version 4: non-perfect mapping with refinement with local
			// refinement
			for (int kk = 1; kk <= n; kk++) {
				k = kk;
				result = new HashMap<String, String>();
				testVersion = "d" + i + "v4" + "k" + k;
				new MyLogging(UPLOAD_DIRECTORY);
				MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
				MA = new HolisticMerger();
				ontM = MA.run(ontList, mappingFileNonPerfect, UPLOAD_DIRECTORY, Refinement_with_local, prefferdOnt,
						"RDFtype");
				Eval = new HEvaluator();
				Eval.run(ontM, evalDimension);
				GenerateOutput.saveResult(ResultPath, testVersion, result);
			}

			// insert empty line in output
			GenerateOutput.inserEmptyLine(ResultPath);

			System.out.println("\t ---------********finished " + "d" + i);

		}
		System.out.println("The End!");
	}

	public static String SetEvalDimension() {
		String eDim = new String();
		eDim = "CompletenessCheck";
		eDim = eDim + "," + "ConstraintCheck";
		eDim = eDim + "," + "AcyclicityCheck";
		eDim = eDim + "," + "ConnectivityCheck";
		eDim = eDim + "," + "CoverageCheck";
		// eDim = eDim + "," + "DeductionCheck";

		return eDim;
	}

	public static String SetRefinement() {
		String Rules = new String();
		Rules = "ClassCheck";
		Rules = Rules + "," + "ProCheck";
		Rules = Rules + "," + "InstanceCheck";
		// Rules = Rules + "," + "CorresCheck";
		// Rules = Rules + "," + "CorssPropCheck";
		// Rules = Rules + "," + "ValueCheck";
		Rules = Rules + "," + "StrCheck";
		// Rules = Rules + "," + "ClRedCheck";
		// Rules = Rules + "," + "ProRedCheck";
		// Rules = Rules + "," + "InstRedCheck";
		// Rules = Rules + "," + "ExtCheck";
		Rules = Rules + "," + "DomRangMinCheck";
		Rules = Rules + "," + "AcyClCheck";
		Rules = Rules + "," + "AcyProCheck";
		Rules = Rules + "," + "RecProCheck";
		Rules = Rules + "," + "UnconnClCheck";
		Rules = Rules + "," + "UnconnProCheck";
		// Rules = Rules + "," + "EntCheck";
		// Rules = Rules + "," + "TypeCheck";
		// Rules = Rules + "," + "ConstValCheck";

		return Rules;
	}

	public static String listFilesForFolder(final File folder) {
		String fileList = "";
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				// listFilesForFolder(fileEntry); // todo: do not need it
			} else {
				// System.out.println(fileEntry.getName());
				// System.out.println(fileEntry);
				if (fileList == null || fileList.length() < 1) {
					fileList = fileEntry.toString();
				} else {
					fileList = fileList + ";" + fileEntry.toString();
				}
			}
		}
		return fileList;
	}

}
