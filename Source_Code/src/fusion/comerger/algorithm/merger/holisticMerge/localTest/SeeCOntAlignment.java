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

import org.apache.commons.io.FileUtils;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import com.google.common.io.Files;

import fusion.comerger.servlets.MatchingProcess;
import fusion.comerger.algorithm.merger.holisticMerge.HolisticMerger;
import fusion.comerger.algorithm.merger.holisticMerge.evaluator.HEvaluator;
import fusion.comerger.algorithm.merger.model.HModel;

public class SeeCOntAlignment {

	public static void main(String[] args)
			throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		run();
	}

	private static void run() throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {

		String prefferdOnt = "equal";

		int currentNumberDataSet = 11;
		int startIndex = 11; // in 1 & 2, n=2
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = startIndex; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = startIndex; i <= currentNumberDataSet; i++) {
			if (i != 8 && i != 9) {
				File folder = new File(address[i] + "\\originalFilesss");
				String ontList = StatisticTest.listFilesForFolder(folder);
				String UPLOAD_DIRECTORY = address[i] + "\\";

				String inputOnt[] = ontList.split(";");
				for (int ii = 0; ii < inputOnt.length; ii++) {
					for (int jj = ii + 1; jj < inputOnt.length; jj++) {
						String inputOntList = inputOnt[ii] + ";" + inputOnt[jj];

						String[] ch = new String[2];
						ch[0] = "1";
						ch[1] = "1";

						UPLOAD_DIRECTORY = UPLOAD_DIRECTORY;// + "temp\\";

						try {
							String mappingFile = "";
							mappingFile = MatchingProcess.CreateMap(inputOntList, ch[0], ch[1], UPLOAD_DIRECTORY);
							System.out.println("Finished create mapping for " + inputOntList);
							copyRename(mappingFile, inputOntList);
						} catch (Exception e) {
							System.out.println("Error for " + inputOntList);
						}
					}
				}
			}
		}
		System.out.println("done!");
	}

	private static void copyRename(String mappingFile, String list) {
		// set name
		String ont[] = list.split(";");
		File temp1 = new File(ont[0]);
		String name = temp1.getName();
		int id = name.indexOf(".");
		name = name.substring(0, id);
		temp1 = new File(ont[1]);
		String name2 = temp1.getName();
		id = name2.indexOf(".");
		name2 = name2.substring(0, id);
		name = name + "-" + name2+".rdf";
		String address = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d11\\align_nonPerfect\\";
		File source = new File(mappingFile);
		File dest = new File(address+name);
		
		
		try {
			Files.copy(source, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
