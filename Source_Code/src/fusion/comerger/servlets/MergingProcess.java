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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import com.ibm.icu.text.DecimalFormat;

import au.com.bytecode.opencsv.CSVReader;
import fusion.comerger.algorithm.merger.holisticMerge.HolisticMerger;
import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.GMR.GMR;
import fusion.comerger.algorithm.merger.holisticMerge.GMR.RuleSets;
import fusion.comerger.algorithm.merger.holisticMerge.GMR.UserCliqueExtractor;
import fusion.comerger.algorithm.merger.holisticMerge.evaluator.HEvaluator;
import fusion.comerger.algorithm.merger.holisticMerge.general.SaveTxt;
import fusion.comerger.algorithm.merger.model.HModel;

public class MergingProcess {

	public static HModel DoMerge(String NameAddressOnt, String alignFile, String UPLOAD_DIRECTORY, String MergeType,
			String selectedUserItem, String preferedOnt, String MergeOutputType) throws Exception {

		HModel ontM = null;
		switch (MergeType) {
		case "HolisticMerge":
			MyLogging.log(Level.INFO, "Initialise the running of Holistic Merge:");
			HolisticMerger HM = new HolisticMerger();
			ontM = HM.run(NameAddressOnt, alignFile, UPLOAD_DIRECTORY, selectedUserItem, preferedOnt, MergeOutputType);
		}

		return ontM;

	}

	public static HModel DoMergeEval(HModel ontM, String evalDimension)
			throws FileNotFoundException, OWLOntologyStorageException {
		long startTime = System.currentTimeMillis();

		HashMap<String, String> evalHashResult = new HashMap<String,String>();
		ontM.setEvalHashResult(evalHashResult);
		
		HEvaluator hr = new HEvaluator();
		String[] res = hr.run(ontM, evalDimension);

		SaveTxt st = new SaveTxt();
		String txtFileName = st.ConvertEvalResultToTxt(ontM);
		ontM.setEvalResultTxt(txtFileName);

		ontM.setEvalResult(res);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"*** done! Congratulation. Evaluation of the merged ontology has been done successfully. Total time  "
						+ elapsedTime + " ms. \n");

		return ontM;
	}

	public static HModel DoRefine(HModel ontM, String uPLOAD_DIRECTORY, String selectedUserItem, String preferedOnt)
			throws OWLOntologyStorageException, FileNotFoundException {
		long startTime = System.currentTimeMillis();

		HolisticMerger hr = new HolisticMerger();
		ontM = hr.refine(ontM, selectedUserItem);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"*** done! Congratulation. Refinment of the merged ontology has been done successfully. Total time  "
						+ elapsedTime + " ms. \n");

		return ontM;
	}

}
