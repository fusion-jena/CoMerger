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
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

import au.com.bytecode.opencsv.CSVReader;
import fusion.comerger.algorithm.merger.holisticMerge.general.Zipper;

public class EvalQuery {
	static String fileOutput = "";

	public static void main(String[] args) throws IOException {
		Run();

	}

	private static void Run() throws IOException {
		String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
		com.hp.hpl.jena.query.Query query = null;

		// create output: a CSV file with header
		fileOutput = "C:\\Users\\Samira\\Desktop\\Eval_HolisticDataSet\\Query\\OutputEvalQuery.csv";
		GenerateOutput.saveEvalQuery2Result_Header(fileOutput);

		int currentNumberDataSet = 10;
		int start = 10;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\Eval_HolisticDataSet\\";
		for (int j = start; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = start; i <= currentNumberDataSet; i++) {
			int TP = 0, FP = 0, FN = 0, TN = 0;

			File folderCQ = new File(address[i] + "\\Query");
			String QueryFile = StatisticTest.listFilesForFolder(folderCQ);

			File folderM = new File(address[i] + "\\mergedFiles");
			String OmName = StatisticTest.listFilesForFolder(folderM);
			OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
			if (OmName.endsWith(".zip")) {
				OmName = Zipper.unzip(OmName);
			}
			FileManager.get().readModel(model, OmName);
			int size = 0;
			CSVReader reader = null;
			try {
				reader = new CSVReader(new FileReader(QueryFile));
				String[] line;
				reader.readNext();
				while ((line = reader.readNext()) != null) {
					Boolean IntendedAnswer=true, nonIntendedAnswer=false;
					if (line != null) {
						String Answer = line[1];
						if (Answer.equals("TRUE")){
							IntendedAnswer = true;
							nonIntendedAnswer =false;
						}else{
							IntendedAnswer = false;
							nonIntendedAnswer =true;
						}
						
						String Q = line[2];
						size++;
						try {
							if (Q.length() > 0) {
								String q = prefix + Q;
								query = QueryFactory.create(q);
								QueryExecution qe = QueryExecutionFactory.create(query, model);
								boolean resultsAsk = qe.execAsk();
								System.out.println(resultsAsk);
								if (resultsAsk == IntendedAnswer) {
									TP++;
								} else if (resultsAsk == nonIntendedAnswer) {
									FP++;
								}else if (resultsAsk != IntendedAnswer) {
									FN++;
								} else if (resultsAsk != nonIntendedAnswer) {
									TN++;
								}

								qe.close();
							}

						} catch (Exception e) {
							System.out.println("Query is not processable");
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println(" ----Finished dataset" + i);

			String d = "$d_" + i + "$";
			HashMap<String, String> result = new HashMap<String, String>();
			result.put("dataset", d);
			result.put("QuerySize", String.valueOf(size));
			result.put("TP", String.valueOf(TP));
			result.put("FP", String.valueOf(FP));
			result.put("FN", String.valueOf(FN));
			result.put("TN", String.valueOf(TN));

			double precision = ((double) (TP)) / ((double) (TP + FP));
			double recall = ((double) (TP)) / ((double) (TP + FN));
			double accuracy = precision * recall;

			String label = "";
			if (precision >= 0.5 && recall >= 0.5) {
				label = "Good";
			} else if (precision < 0.5 && recall >= 0.5) {
				label = "Less Good";
			} else if (precision >= 0.5 && recall < 0.5) {
				label = "Bad";
			} else if (precision < 0.5 && recall < 0.5) {
				label = "Worse";
			}
			result.put("label", label);

			result.put("precision", String.valueOf(precision));
			result.put("recall", String.valueOf(recall));
			result.put("accuracy", String.valueOf(accuracy));

			GenerateOutput.saveEvalQuery2Result(result, fileOutput);
		}

		System.out.println("---The End!");

	}
}
