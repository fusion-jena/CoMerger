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
import java.util.HashMap;
import java.util.logging.Level;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import fusion.comerger.algorithm.merger.holisticMerge.HolisticMerger;
import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.evaluator.HEvaluator;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.model.HModel;

public class HolisticSixVersion {

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
		 *                  In each di folder create 3 sub-folders:
		 * @source_ontologies put all source ontologies
		 * @mapping_perfect put all perfect mapping files
		 * @mapping_imperfect put all imperfect mapping files
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
		HModel ontM = new HModel();
		String Refinement_without_local = StatisticTest.SetRefinement();
		String Refinement_with_local = Refinement_without_local + "," + "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = StatisticTest.SetEvalDimension();

		String ResultPath = "C:\\YOUR_lOCAL_FOLDER\\result.csv";
		GenerateOutputForCSV.createOutputHeader(ResultPath);

		int numberDataSet = 12;
		int startIndex = 1;
		String[] address = new String[numberDataSet + 1];
		String baseAddress = "C:\\YOUR_lOCAL_FOLDER\\";
		for (int j = startIndex; j <= numberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= numberDataSet; i++) {
			File folder = new File(address[i] + "\\source_ontologies");
			String ontList = StatisticTest.listFilesForFolder(folder);
			folder = new File(address[i] + "\\mapping_perfect");
			String mappingFilePerfect = StatisticTest.listFilesForFolder(folder);
			folder = new File(address[i] + "\\mapping_imperfect");
			String mappingFileNonPerfect = StatisticTest.listFilesForFolder(folder);
			String UPLOAD_DIRECTORY = address[i] + "\\";

			/*
			 * run version 1: perfect mapping with refinement with local
			 * refinement
			 */
			StatisticTest.result = new HashMap<String, String>();
			String testVersion = "d" + i + "V1";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			ontM = MA.run(ontList, mappingFilePerfect, UPLOAD_DIRECTORY, Refinement_with_local, prefferdOnt, "RDFtype");

			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutputForCSV.saveResult(ResultPath, testVersion, StatisticTest.result);

			/*
			 * run version 2: perfect mapping with refinement without local
			 * refinement
			 */
			StatisticTest.result = new HashMap<String, String>();
			testVersion = "d" + i + "V2";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			ontM = MA.run(ontList, mappingFilePerfect, UPLOAD_DIRECTORY, Refinement_without_local, prefferdOnt,
					"RDFtype");

			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutputForCSV.saveResult(ResultPath, testVersion, StatisticTest.result);

			/*
			 * run version 3: perfect mapping without any refinement (without
			 * local refinement)
			 */
			StatisticTest.result = new HashMap<String, String>();
			testVersion = "d" + i + "V3";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			ontM = MA.run(ontList, mappingFilePerfect, UPLOAD_DIRECTORY, null, prefferdOnt, "RDFtype");

			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutputForCSV.saveResult(ResultPath, testVersion, StatisticTest.result);

			/*
			 * run version 4: non-perfect mapping with refinement with local
			 * refinement
			 */
			StatisticTest.result = new HashMap<String, String>();
			testVersion = "d" + i + "V4";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			ontM = MA.run(ontList, mappingFileNonPerfect, UPLOAD_DIRECTORY, Refinement_with_local, prefferdOnt,
					"RDFtype");

			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutputForCSV.saveResult(ResultPath, testVersion, StatisticTest.result);

			/*
			 * run version 5: non-perfect mapping with refinement without local
			 * refinement
			 */
			StatisticTest.result = new HashMap<String, String>();
			testVersion = "d" + i + "V5";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			ontM = MA.run(ontList, mappingFileNonPerfect, UPLOAD_DIRECTORY, Refinement_without_local, prefferdOnt,
					"RDFtype");

			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutputForCSV.saveResult(ResultPath, testVersion, StatisticTest.result);

			/*
			 * run version 6: non-perfect mapping without any refinement
			 * (without local refinement)
			 */
			StatisticTest.result = new HashMap<String, String>();
			testVersion = "d" + i + "V6";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			ontM = MA.run(ontList, mappingFileNonPerfect, UPLOAD_DIRECTORY, null, prefferdOnt, "RDFtype");

			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutputForCSV.saveResult(ResultPath, testVersion, StatisticTest.result);

			System.out.println("\t ---------********finished " + "d" + i);

		}
		System.out.println("The End!");
	}

}
