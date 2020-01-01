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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;

public class CombineMapping {

	public static void main(String[] args) {
		run();

	}

	private static void run() {
		String fileName = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d12\\align_nonPerfect\\long\\all.csv";
		String fileOut = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d12\\align_nonPerfect\\long\\all2.csv";
		CSVReader reader = null;
		String a1 = "<?xml version='1.0' encoding='utf-8'?>";
		String a2 = "<rdf:RDF xmlns='http://knowledgeweb.semanticweb.org/heterogeneity/alignment'";
		String a3="xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#'";
		String a4="xmlns:xsd='http://www.w3.org/2001/XMLSchema#'>";
		String a5 = "<Alignment>";
		String a6 = "<xml>yes</xml>";
		String a7 = "<level>0</level>";
		String a8 = "<type>??</type>";
		String a9 = "<onto1>onto1</onto1>";
		String a10 = "<onto2>onto2</onto2>";
		String a11= "<uri1>onto1</uri1>";
		String a12 = "<uri2>onto2</uri2>";
		String a13 = "</Alignment>";
		String a14 = "</rdf:RDF>";
		try (PrintWriter writer = new PrintWriter(new File(fileOut))) {

			StringBuilder sb = new StringBuilder();
//			sb.append(a1);
//			sb.append(a2);
//			sb.append(a3);
//			sb.append(a4);
////			sb.append("\n");
//			sb.append(a5);
//			sb.append(a6);
//			sb.append(a7);
//			sb.append(a8);
//			sb.append(a9);
//			sb.append(a10);
//			sb.append(a11);
//			sb.append(a12);
			try {
				reader = new CSVReader(new FileReader(fileName));
				String[] line;
				while ((line = reader.readNext()) != null) {
					if (line[0] != null) {
//						System.out.println(line[0]);
						if (!line[0].trim().equals(a1) && !line[0].trim().equals(a1) && !line[0].trim().equals(a2)
								&& !line[0].trim().equals(a3) && !line[0].trim().equals(a4)
								&& !line[0].trim().equals(a5) && !line[0].trim().equals(a6)
								&& !line[0].trim().equals(a7) && !line[0].trim().equals(a8)
								&& !line[0].trim().equals(a9) && !line[0].trim().equals(a10)
								&& !line[0].trim().equals(a11) && !line[0].trim().equals(a12) && 
								!line[0].trim().equals(a13) &&
								!line[0].trim().equals(a14) &&
								!line[0].trim().equals("")) {

							sb.append(line[0]);
							sb.append('\n');
							

						}
					}
				}
//				sb.append(a13);
//				sb.append(a14);
				writer.write(sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("done!");
	}

}
