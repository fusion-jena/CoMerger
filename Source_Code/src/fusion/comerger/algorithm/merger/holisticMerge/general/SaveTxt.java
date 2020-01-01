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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import fusion.comerger.algorithm.merger.model.HModel;

public class SaveTxt {
	private static String zipFiles(String... filePaths) {
		String zipFileName = "";
		try {
			File firstFile = new File(filePaths[0]);
			
			zipFileName = firstFile.getPath().substring(0, firstFile.getPath().length() - 4).concat(".zip");

			FileOutputStream fos = new FileOutputStream(zipFileName);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (String aFile : filePaths) {
				zos.putNextEntry(new ZipEntry(new File(aFile).getName()));

				byte[] bytes = Files.readAllBytes(Paths.get(aFile));
				zos.write(bytes, 0, bytes.length);
				zos.closeEntry();
			}

			zos.close();

		} catch (FileNotFoundException ex) {
			System.err.println("A file does not exist: " + ex);
		} catch (IOException ex) {
			System.err.println("I/O error: " + ex);
		}

		return zipFileName;
	}

	public static String ConvertEvalResultToTxt(HModel ontM) throws FileNotFoundException {
		String filzip = ontM.getOntName().substring(0, ontM.getOntName().length() - 4) + "_evaluation.zip";
		String filename = ontM.getOntName().substring(0, ontM.getOntName().length() - 4) + "_evaluation.txt";
		// to be faster for test, temporally i delete these
		HashMap<String, String> res = ontM.getEvalHashResult();
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(filename, "UTF-8");
			if (res.get("class_preservation") != null)
				writer.println("\n *********Class Preservation************* \n" + res.get("class_preservation"));

			if (res.get("PropertyPreservation") != null)
				writer.println("\n *********Property Preservation************* \n" + res.get("PropertyPreservation"));

			if (res.get("InstancePreservationEval") != null)
				writer.println(
						"\n *********Instance Preservation************* \n" + res.get("InstancePreservationEval"));

			if (res.get("ValuePreservationEval") != null)
				writer.println("\n *********Value Preservation ************* \n" + res.get("ValuePreservationEval"));

			if (res.get("StructurePreservation") != null)
				writer.println("\n *********Structure Preservation************* \n" + res.get("StructurePreservation"));

			if (res.get("ClassRedundancy") != null)
				writer.println("\n *********Class Redundancy************* \n" + res.get("ClassRedundancy"));

			if (res.get("PropertyRedundancy") != null)
				writer.println("\n *********Property Redundancy************* \n" + res.get("PropertyRedundancy"));

			if (res.get("InstanceRedundancy") != null)
				writer.println("\n *********Instance Redundancy************* \n" + res.get("InstanceRedundancy"));

			if (res.get("ExtraneousEntityProhibition") != null)
				writer.println("\n *********Extraneous Entity Prohibition************* \n"
						+ res.get("ExtraneousEntityProhibition"));

			if (res.get("ClassAcyclicity") != null)
				writer.println("\n *********Class Acyclicity************* \n" + res.get("ClassAcyclicity"));

			if (res.get("PropertyAcyclicity") != null)
				writer.println("\n *********Property Acyclicity************* \n" + res.get("PropertyAcyclicity"));

			if (res.get("InversePropertyProhibition") != null)
				writer.println("\n *********Inverse Property Prohibition************* \n"
						+ res.get("InversePropertyProhibition"));

			if (res.get("UnconnectedClassProhibition") != null)
				writer.println("\n ********Unconnected Class Prohibition************* \n"
						+ res.get("UnconnectedClassProhibition"));

			if (res.get("UnconnectedPropertyProhibition") != null)
				writer.println("\n *********Unconnected Property Prohibition************* \n"
						+ res.get("UnconnectedPropertyProhibition"));

			if (res.get("EntailmentSatisfaction") != null)
				writer.println(
						"\n *********Entailment Satisfaction************* \n" + res.get("EntailmentSatisfaction"));

			if (res.get("OneTypeRestriction") != null)
				writer.println("\n **********OneType Restriction************ \n" + res.get("OneTypeRestriction"));

			if (res.get("ValueConstraint") != null)
				writer.println("\n **********Value Constraint************ \n" + res.get("ValueConstraint"));

			if (res.get("DomainRangeMinimality") != null)
				writer.println("\n *********DomainRange Minimality************* \n" + res.get("DomainRangeMinimality"));

			writer.println("\n");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}

		// zip create
		filzip = zipFiles(filename);

		return filzip;
	}

	public static String ConsistencyResultToTxt(HModel ontM, String[] res) throws FileNotFoundException {
		String filzip = ontM.getOntName().substring(0, ontM.getOntName().length() - 4) + "_consistency.zip";
		String filename = ontM.getOntName().substring(0, ontM.getOntName().length() - 4) + "_consistency.txt";
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(filename, "UTF-8");
			writer.println("The consistency done with " + res[0] + ". " + res[1]);
			if (res[2].contains("FAILED")) {
				writer.println("The satisfiability test  is " + res[2].substring(58, Math.min(60 + 6, res[2].length()))
						+ ". " + res[3]);
			} else {
				writer.println("The satisfiability test  is " + res[2].substring(60, Math.min(60 + 8, res[2].length()))
						+ ". " + res[3]);
			}
			writer.println("Number of unsatisfiable classes " + res[4] + ". " + res[5].replaceAll("<br>", "\n"));
			writer.println("Root of unsatisfiable classes " + res[6] + ". " + res[7].replaceAll("<br>", "\n"));
			writer.println("Number of justifications " + res[8] + ". " + res[9]);
			writer.println("Number of erroneous axioms " + res[10] + ". " + res[11]);
			writer.println("Elapsed time for detecting by the reasoner " + res[12] + " ms, time to rank  " + res[13]
					+ " ms, and time to generate repair plan  " + res[14] + " ms.");


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}

		// zip create
		filzip = zipFiles(filename);

		return filzip;
	}
}
