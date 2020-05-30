package fusion.comerger.algorithm.merger.holisticMerge;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import fusion.comerger.algorithm.merger.holisticMerge.divideConquer.BlockShareFunc;
import fusion.comerger.algorithm.merger.holisticMerge.divideConquer.HDivideConquer;
import fusion.comerger.algorithm.merger.holisticMerge.general.HSave;
import fusion.comerger.algorithm.merger.holisticMerge.general.SaveTxt;
import fusion.comerger.algorithm.merger.holisticMerge.general.Zipper;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMapping;
import fusion.comerger.algorithm.merger.holisticMerge.merging.HMergeRefine;
import fusion.comerger.algorithm.merger.holisticMerge.merging.HMerging;
import fusion.comerger.algorithm.merger.model.HModel;

public class HolisticMerger {
	List<File> filesInFolder;
	String filesPath;

	public HolisticMerger() {

	}

	public HModel run(String nameAddressOnt, String alignFile, String path, String selectedUserItem, String preferedOnt,
			String MergeOutputType) throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();

		Random rand = new Random();
		int id = rand.nextInt(1000 - 0 + 1) + 0;
		HModel ontM = new HModel();
		ontM.setlistOntName(nameAddressOnt);
		String ontName = "MergedOnt" + id + ".owl";
		ontM.setOntName(path + ontName);
		ontM.setPath(path);
		ontM.SetPreferedOnt(preferedOnt);
		ontM.setMergeOutputType(MergeOutputType);

		MyLogging.log(Level.INFO,
				"The preferred ontology has been set to (the priority of the input ontologies): " + preferedOnt + "\n");

		StatisticTest.result.put("Merged_ontology_name", ontName);

		// 1-- PreProcess: Reader Model: (Import all ontologies)
		ontM = HBuilder.run(ontM);

		// 2 -- Read Mapping
		HMapping hp = new HMapping();
		ontM = hp.run(ontM, alignFile);

		// collapse! translate the axioms with their equal elements
		System.out.println("\t Strat to do equal process");
		ontM = HMerging.equalProcess(ontM);
		
		System.out.println("\t Strat to do adjacentList");
		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> adjacentList = BlockShareFunc
				.createAdjacentClassesList(ontM);

		// 3 -- divide and conquer
		System.out.println("\t Strat to do divide");
		HDivideConquer dc = new HDivideConquer();
		ontM = dc.run(ontM, adjacentList);

		// 4 -- Merger
		System.out.println("\t Strat to do merging");
		HMerging hm = new HMerging();
		ontM = hm.run(ontM, selectedUserItem);

		// Log information for compactness
		HBuilder.logCompactnessInfo(ontM);

		//new test
		int totalAx = ontM.getOwlModel().getAxiomCount();
		StatisticTest.result.put("total_axioms", String.valueOf(totalAx));
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"*** done! Congratulation. Merging ontologies has been done successfully. Total time  " + elapsedTime
						+ " ms. \n");

		StatisticTest.result.put("time_total", String.valueOf(elapsedTime));

		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - beforeUsedMem;
		StatisticTest.result.put("MU_TotalMerge", String.valueOf(actualMemUsed));
		return ontM;

	}

	public void saveOntology(OWLOntology ontology, String loc) throws Exception {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		File file = new File(loc);
		RDFXMLOntologyFormat owlxmlFormat = new RDFXMLOntologyFormat();
		manager.saveOntology(ontology, owlxmlFormat, IRI.create(file.toURI()));

	}

	public HModel refine(HModel ontM, String selectedUserItem)
			throws OWLOntologyStorageException, FileNotFoundException {
		String[] result = new String[14];

		// 4-1 -- Refine Merge
		HMergeRefine hr = new HMergeRefine();
		ontM = hr.run(ontM, selectedUserItem);

		// 5 -- Save
		HSave hs = new HSave();
		String mergeOutputType = ontM.getMergeOutputType();
		ontM = hs.run(ontM, mergeOutputType);

		// 5-1 -- Save Zip Result
		String MergedOntZip = Zipper.zipFiles(ontM.getOntName());
		ontM.setOntZipName(MergedOntZip);

		HBuilder.printTAbox(ontM.getOntName(), ontM.getOwlModel());

		
		String txtFileName = SaveTxt.ConvertEvalResultToTxt(ontM);
		ontM.setEvalResultTxt(txtFileName);

		ontM.setEvalResult(result);

		return ontM;
	}

}
