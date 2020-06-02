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

import java.io.IOException;
import java.util.HashMap;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;

/**
 * Author: Samira Babalou<br>
 * email: samira[dot]babalou[at]uni[dash][dot]jena[dot]de Heinz-Nixdorf Chair
 * for Distributed Information Systems<br>
 * Institute for Computer Science, Friedrich Schiller University Jena, Germany
 * <br>
 * Date: 17/12/2019
 */
public class LocalRun_HolisticMerger {

	public static void main(String[] args) {
		/**
		 * @param ontList
		 *            List of source ontologies
		 * @param mappingFile
		 *            List of the alignment/mapping files between the source
		 *            ontologies
		 * @param DIRECTORY
		 *            Base directory of the files (required for output
		 *            generation)
		 * @param Rules
		 *            a set of Generic Merge Requirements to refine the merged
		 *            result
		 * @param outputType
		 *            the type of merged ontology (OWL/XML or RDF/XML)
		 * @param preferredOnt
		 *            name of the preferred ontology
		 */
		String ontList = "C:\\YOUR_LOCAL_FOLDER\\cmt.owl";
		ontList = ontList + ";" + "C:\\YOUR_LOCAL_FOLDER\\conference.owl";
		String DIRECTORY = "C:\\YOUR_LOCAL_FOLDER\\";
		String mappingFile = "C:\\YOUR_LOCAL_FOLDER\\cmt-conference.rdf";

		HolisticMerger MA = new HolisticMerger();

		String Rules = new String();
		Rules = "ClassCheck";
		Rules = Rules + "," + "ProCheck";
		Rules = Rules + "," + "InstanceCheck";
		Rules = Rules + "," + "CorresCheck";
		Rules = Rules + "," + "CorssPropCheck";
		Rules = Rules + "," + "ValueCheck";
		Rules = Rules + "," + "StrCheck";
		Rules = Rules + "," + "ClRedCheck";
		Rules = Rules + "," + "ProRedCheck";
		Rules = Rules + "," + "InstRedCheck";
		Rules = Rules + "," + "PathRedCheck";
		Rules = Rules + "," + "ExtCheck";
		Rules = Rules + "," + "DomRangMinCheck";
		Rules = Rules + "," + "AcyClCheck";
		Rules = Rules + "," + "AcyProCheck";
		Rules = Rules + "," + "RecProCheck";
		Rules = Rules + "," + "UnconnClCheck";
		Rules = Rules + "," + "UnconnProCheck";
		Rules = Rules + "," + "EntCheck";
		Rules = Rules + "," + "TypeCheck";
		Rules = Rules + "," + "ConstValCheck";
		Rules = Rules + "," + "CardCheck";

		String preferredOnt = "equal";

		String outputType = "RDFtype";
		StatisticTest.result = new HashMap<String, String>();

		try {
			MA.run(ontList, mappingFile, DIRECTORY, Rules, preferredOnt, outputType);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done!");

	}

}
