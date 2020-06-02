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
 * Date: 30/05/2020
 */
import java.util.logging.Level;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMapping;
import fusion.comerger.algorithm.merger.model.HModel;

public class SingleBuilder {

	public static HModel run(String mergedFile, String inputFiles, String mapFile, String preferedOnt,
			String MergeOutputType) throws OWLOntologyCreationException {

		long startTime = System.currentTimeMillis();

		HModel ontM = new HModel();
		ontM.setlistOntName(inputFiles);
		ontM.setOntName(mergedFile);

		ontM.SetPreferedOnt(preferedOnt);
		ontM.setMergeOutputType(MergeOutputType);
		ontM = HBuilder.singleRun(ontM);
		StatisticTest.result.put("Merged_ontology_name", mergedFile);
		// Builder.BuildOntEval(ontM);

		// 2 -- Read Mapping
		HMapping hp = new HMapping();
		ontM = hp.run(ontM, mapFile);

			
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "*** done! Congratulation. Reading the model has been done successfully. Total time  "
				+ elapsedTime + " ms. \n");

		return ontM;
	}
}
