package fusion.comerger.algorithm.merger.holisticMerge.general;
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
import java.util.ArrayList;
import java.util.logging.Level;

import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.model.HModel;

public class HSave {

	public HModel run(HModel ontM, String MergeOutputType) throws OWLOntologyStorageException {
		// ontM.SetOwlModel(ontology);
		long startTime = System.currentTimeMillis();

		OWLOntologyManager manager = ontM.getManager();
		// OWLDataFactory factory = manager.getOWLDataFactory();
		OWLOntology ontology = ontM.getOwlModel();

		ontM.SetManager(manager);
		manager = ontM.getManager();
		ontology = ontM.getOwlModel();

		// save ont
		File fl = new File(ontM.getOntName());
		// OWLOntologyFormat format = manager.getOntologyFormat(ontology);
		// manager.saveOntology(ontology, format, IRI.create(fl.toURI()));

		// manager.saveOntology(ontology, System.out);
		if (MergeOutputType.equals("OWLtype")) {
			// save in OWL/XML format
			manager.saveOntology(ontology, new OWLXMLOntologyFormat(), IRI.create(fl.toURI()));

		} else {// ==RDFtype
				// save in RDF/XML format
			manager.saveOntology(ontology, IRI.create(fl.toURI()));
		}

		ontM.SetOwlModel(ontology);
		ontM.SetManager(manager);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "Save the created merged ontology in " + MergeOutputType + " format. Total time  "
				+ elapsedTime + " ms. \n");

		StatisticTest.result.put("time_save", String.valueOf(elapsedTime));

		return ontM;
	}

	public HModel runSubMerged(HModel ontM) {

		long startTime = System.currentTimeMillis();
		ArrayList<String> subMergedOnt = new ArrayList<String>();
		String MergeOutputType = ontM.getMergeOutputType();
		OWLOntologyManager manager = ontM.getManager();
		// OWLDataFactory factory = manager.getOWLDataFactory();
		OWLOntology ontology = null;// ontM.getOwlModel();

		// ontM.SetManager(manager);
		// manager = ontM.getManager();

		for (int i = 0; i < ontM.getClusters().size(); i++) {
			ontology = ontM.getClusters().get(i).getOntology();
			manager = ontology.getOWLOntologyManager();
			// ontology = ontM.getOwlModel();

			// save ont
			String subOntName = "c:\\uploads\\subMergedOnt" + i + ".owl";
			File fl = new File(subOntName);
			// OWLOntologyFormat format = manager.getOntologyFormat(ontology);
			// manager.saveOntology(ontology, format, IRI.create(fl.toURI()));

			// manager.saveOntology(ontology, System.out);
			if (MergeOutputType.equals("OWLtype")) {
				// save in OWL/XML format
				try {
					manager.saveOntology(ontology, new OWLXMLOntologyFormat(), IRI.create(fl.toURI()));
				} catch (OWLOntologyStorageException e) {
					e.printStackTrace();
				}

			} else {// ==RDFtype
					// save in RDF/XML format
				try {
					manager.saveOntology(ontology, IRI.create(fl.toURI()));
				} catch (OWLOntologyStorageException e) {
					e.printStackTrace();
				}
			}
			subMergedOnt.add(subOntName);
		}
		// ontM.SetOwlModel(ontology);
		// ontM.SetManager(manager);

		ontM.setSubMergedOntName(subMergedOnt);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "Save the created merged ontology in " + MergeOutputType + " format. Total time  "
				+ elapsedTime + " ms. \n");

		StatisticTest.result.put("time_save", String.valueOf(elapsedTime));

		return ontM;
	}

}
