package fusion.comerger.algorithm.merger.holisticMerge.consistency;
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

/**
 * CoMerger: Holistic Multiple Ontology Merger.
 * Consistency checker sub package based on the Subjective Logic theory
 * @author Samira Babalou (samira[dot]babalou[at]uni_jean[dot]de)
 */
import java.io.IOException;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import fusion.comerger.servlets.MatchingProcess;
import fusion.comerger.algorithm.merger.model.HModel;
import fusion.comerger.algorithm.merger.model.ModelReader;

public class LocalRun_Consistency {

	public static void main(String[] args) {
		/**
		 * @param sourceOntologies
		 *            List of source ontologies
		 * @param mergedOnt
		 *            a merged ontology
		 * @param mappingFile
		 *            List of the alignment/mapping files between the source
		 *            ontologies
		 * @param DIRECTORY
		 *            Base directory of the files (required for output
		 *            generation)
		 * @param rootAllClasses
		 *            to rank all unsatisfiable classes (set "all") or only root
		 *            classes (set "root")
		 * @param numJustification
		 *            number of justifications
		 * @param preferredOnt
		 *            name of the preferred ontology
		 */

		// Set the parameters
		String DIRECTORY = "C:\\YOUR_LOCAL_FOLDER\\";
		String sourceOntologies = "C:\\YOUR_LOCAL_FOLDER\\cmt.owl";
		sourceOntologies = sourceOntologies + ";" + "C:\\YOUR_LOCAL_FOLDER\\conference.owl";
		sourceOntologies = sourceOntologies + ";" + "C:\\YOUR_LOCAL_FOLDER\\confOf.owl";
		String mergedOnt = "C:\\YOUR_LOCAL_FOLDER\\MergedOnt745.owl";
		String mappingFile = MatchingProcess.CreateMap(sourceOntologies, "1", "1", DIRECTORY);

		String preferredOnt = "equal";
		// set: to rank all unsatisfiable classes (set "all") or only root
		// classes (set "root")
		String rootAllClasses = "root";
		// String userConsParam="all";

		// set the number of justifications
		String numJustification = "5";

		String userConsParam = rootAllClasses + "," + numJustification;

		HModel mergedModel = ModelReader.createReadModel(sourceOntologies, mappingFile, mergedOnt, preferredOnt);
		try {
			mergedModel = ConsistencyProcess.DoConsistencyCheck(mergedModel, userConsParam);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] res = mergedModel.getConsResult();
		System.out.println("--The consistency test done with: " + res[0] + "\n" + res[1]);
		System.out.println("--The satisfiability test: " + res[2] + "\n" + res[3]);
		System.out.println("--Number of unsatisfiable classes: " + res[4] + "\n" + res[5]);
		System.out.println("--Root of unsatisfiable classes: " + res[6] + "\n" + res[7]);
		System.out.println("--Number of justifications: " + res[8] + "\n" + res[9]);
		System.out.println("--Number of conflicting axioms: " + res[10] + "\n" + res[11]);
		System.out.println("Time for reasoner: " + res[12] + "\t Time for Rank: " + res[13]
				+ "\t Time for generating Plan: " + res[13]);
		System.out.println("\n *** Repair plan **" + res[15] + "\n" + res[16]);
		System.out.println("Put the repair plan in a basic HTML file. \n Done!");
	}

}
