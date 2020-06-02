package fusion.comerger.algorithm.merger.holisticMerge.evaluator;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import fusion.comerger.servlets.MergingProcess;
import fusion.comerger.algorithm.merger.holisticMerge.SingleBuilder;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.GenerateOutput;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.model.HModel;
import fusion.comerger.algorithm.merger.model.ModelReader;

public class LocalRun_Eval {

	public static void main(String[] args) throws OWLOntologyCreationException {
		/**
		 * @param inputOnts
		 *            List of source ontologies
		 * @param mergedOnt
		 *            a merged ontology that want to be evaluated
		 * @param mappingFile
		 *            List of the alignment/mapping files between the source
		 *            ontologies
		 * @param DIRECTORY
		 *            Base directory of the files (required for output
		 *            generation)
		 * @param ResultPath
		 *            a name and full path a CSV file to save the output
		 * @param evalDim
		 *            a set of evaluation dimension
		 * @param preferredOnt
		 *            name of the preferred ontology
		 */

		HModel mergedModel = new HModel();
		String inputOnts = "C:\\YOUR_LOCAL_FOLDER\\cmt.owl";
		inputOnts = inputOnts + ";" + "C:\\YOUR_LOCAL_FOLDER\\conference.owl";
		String mergedOnt = "C:\\YOUR_LOCAL_FOLDER\\cmt.owl";
		String mappingFile = "C:\\YOUR_LOCAL_FOLDER\\cmt-conference.rdf";
		String preferredOnt = "equal";

		// set a path and file name for x in x , the output will be saved as a
		// csv file
		String ResultPath = "C:\\YOUR_LOCAL_FOLDER\\testt.csv";
		StatisticTest.result = new HashMap<String, String>();
		String MergeOutputType = "RDF/XML";
		// Set the required evaluation dimension
		String evalDim = "CompletenessCheck";
		evalDim = evalDim + "," + "MinimalityCheck";
		evalDim = evalDim + "," + "DeductionCheck";
		evalDim = evalDim + "," + "ConstraintCheck";
		evalDim = evalDim + "," + "AcyclicityCheck";
		evalDim = evalDim + "," + "ConnectivityCheck";
		evalDim = evalDim + "," + "UsabilityProfileCheck";
		evalDim = evalDim + "," + "MapperCheck";
		evalDim = evalDim + "," + "CompactnessCheck";
		evalDim = evalDim + "," + "CoverageCheck";

		
		mergedModel = SingleBuilder.run(mergedOnt, inputOnts, mappingFile, "equal", MergeOutputType);
		try {
			mergedModel = MergingProcess.DoMergeEval(mergedModel, evalDim);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}

		// save the output as csv file
		GenerateOutput.createOutputHeader(ResultPath);
		try {
			GenerateOutput.saveResult(ResultPath, "versionEval", StatisticTest.result);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Evaluation of the merged ontology is done! please see the CSV file for the output result.");
	}

}
