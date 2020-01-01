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
import java.util.HashMap;
import java.util.logging.Level;

import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import fusion.comerger.servlets.MatchingProcess;
import fusion.comerger.algorithm.merger.holisticMerge.HolisticMerger;
import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.evaluator.HEvaluator;
import fusion.comerger.algorithm.merger.model.HModel;

public class TestOntologies {

	public static void main(String[] args) {
		Merge();
//		Match();
	}

	private static void Match() {
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\Test\\new";
		String OutputAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\Test\\new";
		String baseOnt = "C:\\Users\\Samira\\Desktop\\mergeDataset\\allConf\\cmt.owl";
		String runningFile = "";
		String errorFile="";
		File folder = new File(baseAddress);
		String ontList = listFilesForFolder(folder);
		String OList[] = ontList.split(";");

		for (int i = 0; i < OList.length; i++) {
			try {
				String testOnt = baseOnt + ";" + OList[i];
				String ch1 = "1", ch2 = "1"; // TODO:correct it
				System.out.println("\n running: "+ OList[i]);
				String mapOnts = MatchingProcess.CreateMap(testOnt, ch1, ch2, OutputAddress);
			} catch (Exception e) {
				System.out.println("Error for: " + OList[i]);
				errorFile = errorFile + ";" + OList[i];
			}

		}
		
		System.out.println("*********** \n"+runningFile);
		System.out.println("*********** \n \n ErrorFiles: " +errorFile);


	}

	private static void Merge() {
		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		// String Refinement_without_local = SetRefinement();
		// String Refinement_with_local = Refinement_without_local + "," +
		// "localRefinement";
		String prefferdOnt = "equal";
		// String evalDimension = SetEvalDimension();

		// String ResultPath =
		// "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\result.csv";
		// GenerateOutput.createOutputHeader(ResultPath);

		// int currentNumberDataSet = 10;
		// String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d11\\originalFiles\\";
		String OutputAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d11\\originalFiles\\";
		String baseOnt = "C:\\Users\\Samira\\Desktop\\mergeDataset\\allConf\\cmt.owl";
		String mapFileFake = "C:\\Users\\Samira\\Desktop\\mergeDataset\\allConf\\alignment\\cmt-conference.rdf";
		String runningFile = "";
		String errorFile="";
		// StatisticTest.result = new
		// for (int j = 1; j <= currentNumberDataSet; j++)
		// address[j] = baseAddress + "d" + j;

		// for (int i = 1; i <= currentNumberDataSet; i++) {
		// if (i == 4) {
		// // skip;
		// } else {
		File folder = new File(baseAddress);
		String ontList = listFilesForFolder(folder);
		String OList[] = ontList.split(";");

		for (int i = 0; i < OList.length; i++) {
			try {
				String testOnt = baseOnt + ";" + OList[i];
				// result = new HashMap<String, String>();
				System.out.println("running merge for: " + OList[i]);
				MA = new HolisticMerger();
				try {
					HModel ontM = MA.run(testOnt, mapFileFake, OutputAddress, "", prefferdOnt, "RDFtype");
					System.out.println("success run for: " + OList[i]);
					runningFile = runningFile + ";" + OList[i];
				} catch (OWLOntologyCreationException e) {
					e.printStackTrace();
				} catch (OWLOntologyStorageException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.println("Error for: " + OList[i]);
				errorFile = errorFile + ";" + OList[i];
			}

		}
		
		System.out.println("*********** \n"+runningFile);
		System.out.println("*********** \n \n ErrorFiles: " +errorFile);

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
