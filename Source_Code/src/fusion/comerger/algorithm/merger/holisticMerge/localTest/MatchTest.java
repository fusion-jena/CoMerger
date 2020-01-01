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

import fusion.comerger.servlets.MatchingProcess;

public class MatchTest {

	public static void main(String[] args) throws IOException {
		run();
	}

	private static void run() throws IOException {
		String ch1 = "1", ch2 = "1";
		String originalFile = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d1\\subMergedOntologies\\perfect\\";
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d1\\subMergedOntologies\\perfect\\";
		String filenotrun = "";

		File folder = new File(originalFile);
		String ontList = TestOntologies.listFilesForFolder(folder);
		String OList[] = ontList.split(";");

		for (int i = 0; i < OList.length; i++) {
			// int i=0;
			for (int j = i + 1; j < OList.length; j++) {
				try {
					try {
						try {
							String inputOnts = OList[i] + ";" + OList[j];
							System.out.println("start doing: " + OList[i] + "-" + OList[j]);
							String mapOnts = MatchingProcess.CreateMap(inputOnts, ch1, ch2, baseAddress);
							System.out.println(mapOnts);
							File srcFile = new File(mapOnts);
							String name1 = new File(OList[i]).getName();
							name1 = name1.substring(0, name1.indexOf("."));
							String name2 = new File(OList[j]).getName();
							name2 = name2.substring(0, name2.indexOf("."));
							String name = name1 + "-" + name2 + ".rdf";
							String destFileName = baseAddress + name;
							File destFile = new File(destFileName);
							FileUtils.copyFile(srcFile, destFile);
							System.out.println("finished: " + OList[i] + "-" + OList[j]);
						} catch (ExceptionInInitializerError e) {
							filenotrun = filenotrun + ";" + OList[i] + "-" + OList[j];
							System.out.println("caused error at: " + OList[i] + "-" + OList[j]);
						}
					} catch (Exception e) {
						filenotrun = filenotrun + ";" + OList[i] + "-" + OList[j];
						System.out.println("caused error at: " + OList[i] + "-" + OList[j]);
					}
				} catch (NoClassDefFoundError e) {
					filenotrun = filenotrun + ";" + OList[i] + "-" + OList[j];
					System.out.println("caused error at: " + OList[i] + "-" + OList[j]);
				}
			}
		}
		System.out.println("file not run: " + filenotrun);
		System.out.println("--*********************************** The End ***********************************");
	}

}
