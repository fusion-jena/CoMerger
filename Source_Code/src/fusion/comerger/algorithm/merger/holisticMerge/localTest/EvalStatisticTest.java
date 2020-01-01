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

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import fusion.comerger.algorithm.merger.holisticMerge.HolisticMerger;
import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.evaluator.HEvaluator;
import fusion.comerger.algorithm.merger.model.HModel;

public class EvalStatisticTest {
	// public static HashMap<String, String> result = new HashMap<String,
	// String>();

	public static void main(String[] args)
			throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {

		runTestStatisticVersion3();

	}

	public static void runTestStatisticVersion3()
			throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		// run one time without manual changes, save the files. again, run it
		// and uncomment manual changes and record output

		HolisticMerger MA = new HolisticMerger();
		HEvaluator Eval = new HEvaluator();
		// String Refinement_without_local = SetRefinement();
		// String Refinement_with_local = Refinement_without_local + "," +
		// "localRefinement";
		String prefferdOnt = "equal";
		String evalDimension = SetEvalDimension();

		String ResultPath = "C:\\Users\\Samira\\Desktop\\Eval_HolisticDataSet\\EvalResult.csv";
		GenerateOutput.createEvalOutputHeader(ResultPath);

		int currentNumberDataSet = 10;
		int startIndex = 1;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\Eval_HolisticDataSet\\";
		for (int j = startIndex; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= currentNumberDataSet; i++) {
			File folder = new File(address[i] + "\\originalFiles");
			String ontList = StatisticTest.listFilesForFolder(folder);
			folder = new File(address[i] + "\\align_perfect");
			String mappingFilePerfect = StatisticTest.listFilesForFolder(folder);
			// folder = new File(address[i] + "\\align_nonPerfect");
			// String mappingFileNonPerfect =
			// StatisticTest.listFilesForFolder(folder);
			String UPLOAD_DIRECTORY = address[i] + "\\";

			// run version 3: perfect mapping without any refinement
			// (without local refinement)
			StatisticTest.result = new HashMap<String, String>();
			String testVersion = "$d_{" + i +"}$";//+ "V_3$";
			new MyLogging(UPLOAD_DIRECTORY);
			MyLogging.log(Level.INFO, "Log information for test_version: " + testVersion + "\n \n");
			MA = new HolisticMerger();
			HModel ontM = MA.run(ontList, mappingFilePerfect, UPLOAD_DIRECTORY, null, prefferdOnt, "RDFtype");
			Eval = new HEvaluator();
			Eval.run(ontM, evalDimension);
			GenerateOutput.saveEvalResult(ResultPath, testVersion, StatisticTest.result);

			System.out.println("\t ---------********finished " + "d" + i);

		}
		System.out.println("The End!");
	}

	public static String SetEvalDimension() {
		String eDim = new String();
		eDim = "CompletenessCheck";
		eDim = eDim + "," + "MinimalityCheck";
		eDim = eDim + "," + "DeductionCheck";
		eDim = eDim + "," + "ConstraintCheck";
		eDim = eDim + "," + "AcyclicityCheck";
		eDim = eDim + "," + "ConnectivityCheck";
		eDim = eDim + "," + "MapperCheck";
		eDim = eDim + "," + "CompactnessCheck";
		eDim = eDim + "," + "CoverageCheck";
		eDim = eDim + "," + "UsabilityProfileCheck";

		return eDim;
	}
}
