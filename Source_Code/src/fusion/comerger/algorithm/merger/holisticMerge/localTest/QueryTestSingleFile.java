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
import java.io.ByteArrayOutputStream;
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
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

import au.com.bytecode.opencsv.CSVReader;
import fusion.comerger.algorithm.merger.holisticMerge.general.Zipper;

public class QueryTestSingleFile {
	static int Qsize = 30;
	static String[] QueryRef = new String[Qsize];

	public static void main(String[] args) throws IOException {

		String QueryOiFile = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\CQ\\inputOntologies\\ekaw-Query.csv";
		String OiNameList = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d8\\originalFiles\\ekaw.owl";
		String[] OiResult = RunQuery(QueryOiFile, OiNameList);

	}

	public static String[] RunQuery(String QueryFile, String ontName) throws IOException {
		String[] QResult = new String[Qsize];

		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		if (ontName.endsWith(".zip")) {
			ontName = Zipper.unzip(ontName);
		}
		FileManager.get().readModel(model, ontName);

		QueryRef = CSVtoArray(QueryFile);
		String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
		com.hp.hpl.jena.query.Query query = null;
		for (int i = 0; i < Qsize; i++) {
			try {
				if (QueryRef[i].startsWith("ASK")) {
					if (QueryRef[i].length() > 0) {
						String q = prefix + QueryRef[i];
						query = QueryFactory.create(q);
						QueryExecution qe = QueryExecutionFactory.create(query, model);
						boolean resultsAsk = qe.execAsk();
						QResult[i] = String.valueOf(resultsAsk);
						qe.close();
					}
				} else if (QueryRef[i].startsWith("unknown")) {
					QResult[i] = "unknown";
				} else {
					if (QueryRef[i].length() > 0) {
						String q = prefix + QueryRef[i];
						String output = "";
						query = QueryFactory.create(q);
						QueryExecution qe = QueryExecutionFactory.create(query, model);
						com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

						while (results.hasNext()) {
							final QuerySolution sol = results.nextSolution();
							String name = sol.getResource("x").toString();
							output = output + ";" + name;
						}
						QResult[i] = output;
						qe.close();
					}
				}
			} catch (Exception e) {
				System.out.println("Query is not processable");
			}
		}
		for (int k = 0; k < Qsize; k++) {
//			System.out.println(" ******Answer "+k);
			System.out.println(QResult[k]);
		}

		return QResult;
	}

	public static String[] CSVtoArray(String csvFile) {

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			int i = 0;
			while ((line = reader.readNext()) != null) {
				QueryRef[i] = line[0];
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return QueryRef;
	}
}
