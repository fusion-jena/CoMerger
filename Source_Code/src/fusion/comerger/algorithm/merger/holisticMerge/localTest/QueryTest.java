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
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

import au.com.bytecode.opencsv.CSVReader;
import fusion.comerger.algorithm.merger.holisticMerge.general.Zipper;

public class QueryTest {
	static int Qsize = 30;
	static String fileOutput = "";
	static HashMap<String, String> equalLabelList;

	public static void main(String[] args) throws IOException {
		/*
		 * TO Run this test: 1- copy the merged file manually in the mergeFolder
		 * in each dataset. 2- copy the 2 first column from test.csv into
		 * OntNameIndexer.csv
		 */
		Run();

	}

	private static void Run() throws IOException {

		// create output: a CSV file with header
		fileOutput = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\CQ\\OutputQuery.csv";
		GenerateOutput.saveQueryResult_Header(fileOutput);
		HashMap<String, String> OntIndexer = SetOntIndex();

		int currentNumberDataSet = 6;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\";
		for (int j = 1; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = 1; i <= currentNumberDataSet; i++) {
			File folder = new File(address[i] + "\\originalFiles");
			String OiNameList = StatisticTest.listFilesForFolder(folder);
			String[] OiAllResult = SetOiResult(OiNameList);

			File folderM = new File(address[i] + "\\mergedFiles");
			String OmNameList = StatisticTest.listFilesForFolder(folderM);

			String[] OmName = OmNameList.split(";");
			for (int k = 0; k < OmName.length; k++) {
				String om = OmName[k];

				String QueryOmFile = SetOmQueryName(om, OntIndexer);
				String[] OmAllResult = RunQueryForOm(QueryOmFile, om);
				compareCQResult(OiAllResult, OmAllResult, om, OntIndexer);
			}
			GenerateOutput.inserEmptyLineQuery(fileOutput);
			System.out.println(" ----Finished dataset" + i);

		}

		// String QueryOiFile =
		// "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\CQ\\cmtQuery.csv";

		// String OiNameList =
		// "C:\\Users\\Samira\\Desktop\\mergeDataset\\allConf\\cmt.owl;C:\\Users\\Samira\\Desktop\\mergeDataset\\allConf\\conference.owl";
		// String OmName =
		// "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d1\\MergedOnt199.owl";
		// String[] OiAllResult = RunQueryForOi(QueryOiFile, OiNameList);

	

		System.out.println("---The End!");

	}

	public  static HashMap<String, String> readEquaLabel(String fileName) {
		HashMap<String, String> EqList = new HashMap<String, String>();
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(fileName));
			String[] line;
			while ((line = reader.readNext()) != null) {
				if (line[0] != null) {
					String s = line[0].trim();
					int index = s.indexOf("#");
					s = s.substring(index + 1, s.length());
					if (s.contains("<"))
						s = s.replace("<", "").trim();
					if (s.contains(">"))
						s = s.replace(">", "").trim();

					String ss = line[1].trim();
					index = ss.indexOf("#");
					ss = ss.substring(index + 1, ss.length());
					if (ss.contains("<"))
						ss = ss.replace("<", "").trim();
					if (ss.contains(">"))
						ss = ss.replace(">", "").trim();

					EqList.put(s, ss);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return EqList;
	}

	private static String SetOmQueryName(String om, HashMap<String, String> OntIndexer) {
		String baseAddress = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\CQ\\OmQuery\\";
		String query = "";
		File fileM = new File(om);
		String nam = fileM.getName();

		String indexer = OntIndexer.get(nam);
		if (indexer == null) {
			System.out.print("There is no query for the merged ontolgoy: " + om);
		} else {
			String dataetName = indexer.substring(0, 2);
			String testVersion = indexer.substring(3, 4);
			if (Integer.valueOf(testVersion) > 3) {
				query = baseAddress + dataetName + "v456.csv";
			} else {
				query = baseAddress + dataetName + "v123.csv";
			}
		}

		// set equal label list
		equalLabelList = new HashMap<String, String>();
		String d = indexer.substring(1, 2);
		if (Integer.valueOf(d) == 1) {
			// set perfect mapping equal list
			String csvFile = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\CQ\\EqLabelListPerfect.csv";
			HashMap<String, String> eqList = readEquaLabel(csvFile);
			equalLabelList = eqList;
		} else {
			// set non perfect mapping equal list
			String csvFile = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\CQ\\EqLabelListNonPerfect.csv";
			HashMap<String, String> eqList = readEquaLabel(csvFile);
			equalLabelList = eqList;
		}
		return query;
	}

	private static void compareCQResult(String[] OiAllResult, String[] OmAllResult, String OmName,
			HashMap<String, String> OntIndexer) throws IOException {
		int unknownAnswer = 0;
		int nullAnswer = 0;
		int compeleteAnswer = 0;
		int partialAnswer = 0;
		int wrongAnswer = 0;
		int semiCompeletAnswer = 0;
		int somthingIsWrong = 0;

		int notFound = 0;
		int found = 0;
		int trueAnswer = 0;
		int falseAnswer = 0;

		// for test
		// System.out.println("********* OM Answer");
		// for (int i = 0; i < OmAllResult.length; i++)
		// System.out.println("--Answer" + i + OmAllResult[i]);
		//
		// System.out.println("********* Comparision Result");

		for (int i = 0; i < OmAllResult.length; i++) {
			if (i == 16) {
				int we = 0;
			}
			String answerOm = OmAllResult[i].trim();
			String OiAns = OiAllResult[i].trim();

			notFound = 0;
			found = 0;
			trueAnswer = 0;
			falseAnswer = 0;

			switch (answerOm) {
			case ("[unknown]"):
				unknownAnswer++;
				System.out.println("--comparision Q" + i + " unknown");
				break;

			case ("[false]"):
			case ("[true]"):
				String[] s = OiAns.split(";");
				for (int j = 0; j < s.length; j++) {
					if (s[j].toLowerCase().contains("true")) {
						trueAnswer++;
					} else if (s[j].toLowerCase().contains("false")) {
						falseAnswer++;
					}
				}
				if ((trueAnswer != 0 && falseAnswer == 0) || (trueAnswer > falseAnswer)) {
					if (answerOm.contains("true")) {
						compeleteAnswer++;
						System.out.println("--comparision Q" + i + " compeleteAnswer");
					} else if (answerOm.contains("false")) {
						wrongAnswer++;
						System.out.println("--comparision Q" + i + " wrongAnswer");
					}

				} else if ((trueAnswer == 0 && falseAnswer != 0) || (falseAnswer > trueAnswer)) {
					if (answerOm.contains("false")) {
						compeleteAnswer++;
						System.out.println("--comparision Q" + i + " compeleteAnswer");
					} else if (answerOm.contains("true")) {
						wrongAnswer++;
						System.out.println("--comparision Q" + i + " wrongAnswer");
					}
				} else if (OiAns.toLowerCase().contains("unknown")) {
					unknownAnswer++;
					System.out.println("--comparision Q" + i + " unknownAnswer");
				} else {
					wrongAnswer++;
					System.out.println("--comparision Q" + i + " wrongAnswer");
				}

				break;

			case ("[]"):
				nullAnswer++;
				System.out.println("--comparision Q" + i + " nullAnswer");
				break;

			default:

				//////////////////////////////////////////////////
				String[] ss = OiAns.split(";");
				int includeUnknownAnswer = 0;

				for (int j = 0; j < ss.length; j++) {
					String Oians = ss[j];
					boolean foundIt = false;

					if (Oians.length() > 0 && !Oians.toLowerCase().contains("nothing")) {
						if (Oians.toLowerCase().contains("unknown")) {
							includeUnknownAnswer++;
						} else {
							int index = Oians.indexOf("#");
							Oians = Oians.substring(index + 1, Oians.length());

							String[] OmAnswer = answerOm.split(",");
							for (int k = 0; k < OmAnswer.length; k++) {
								String omAns = OmAnswer[k].trim().toLowerCase();
								if (omAns.contains("#") && !omAns.toLowerCase().contains("nothing")) {
									index = omAns.indexOf("#");
									omAns = omAns.substring(index + 1, omAns.length());
									if (omAns.contains("["))
										omAns = omAns.replace("[", "").trim();
									if (omAns.contains("]"))
										omAns = omAns.replace("]", "").trim();

									if (omAns.toLowerCase().toString().equals(Oians.toLowerCase())) {
										foundIt = true;
										break;
									} else {
										String eqOians = equalLabelList.get(Oians);
										if (eqOians != null
												&& omAns.toLowerCase().toString().equals(eqOians.toLowerCase())) {
											foundIt = true;
											break;
										}
									}
								}
							}

							if (foundIt) {
								found++;
							} else {
								notFound++;
							}
						}
					}
				}

				if (found > 0 && notFound == 0) {
					compeleteAnswer++;
					System.out.println("--comparision Q" + i + " compeleteAnswer");
				} else if (found > notFound) {
					semiCompeletAnswer++;
					System.out.println("--comparision Q" + i + " semiCompeletAnswer");
				} else if (notFound > found) {
					partialAnswer++;
					System.out.println("--comparision Q" + i + " partialAnswer");
				} else if (includeUnknownAnswer > 0) {
					unknownAnswer++;
					System.out.println("--comparision Q" + i + " unknownAnswer");
				} else if (found == notFound) {
					partialAnswer++;
					System.out.println("--comparision Q" + i + " partialAnswer");
					//  it can be also as semiCompelete
				} else {
					int w = 0;
					System.out.println("--comparision Q" + i + " !!!!!");
					somthingIsWrong++;
				}

				break;
			}
		}

		// partial answer: e.g. found = 1, notFound =2. It means only a few of
		// Oi answers have been found in Om, and many of them cannot be found.
		// semiCompelete answer: e.g. found= 5, notFound =2
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("unknownAnswer", String.valueOf(unknownAnswer));
		result.put("nullAnswer", String.valueOf(nullAnswer));
		result.put("compeleteAnswer", String.valueOf(compeleteAnswer));
		result.put("partialAnswer", String.valueOf(partialAnswer));
		result.put("wrongAnswer", String.valueOf(wrongAnswer));
		result.put("semiCompeletAnswer", String.valueOf(semiCompeletAnswer));
		result.put("somthingIsWrong", String.valueOf(somthingIsWrong));

		System.out.println("unknownAnswer: " + unknownAnswer);
		System.out.println("nullAnswer: " + nullAnswer);
		System.out.println("compeleteAnswer: " + compeleteAnswer);
		System.out.println("semiCompeletAnswer: " + semiCompeletAnswer);
		System.out.println("partialAnswer: " + partialAnswer);
		System.out.println("wrongAnswer: " + wrongAnswer);
		System.out.println("somthingIsWrong: " + somthingIsWrong);

		// find version of OM
		File fileM = new File(OmName);
		String OmNameFile = fileM.getName();
		String OmVersion = OntIndexer.get(OmNameFile);
		GenerateOutput.saveQueryResult(fileOutput, result, OmNameFile, OmVersion);

	}

	public static String[] RunQueryForOm(String QueryFile, String ontName) throws IOException {
		String[] OmResult = new String[Qsize];
		for (int i = 0; i < OmResult.length; i++)
			OmResult[i] = "";

		HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>();
		ArrayList<String> temp = new ArrayList<String>();
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		if (ontName.endsWith(".zip")) {
			ontName = Zipper.unzip(ontName);
		}
		FileManager.get().readModel(model, ontName);

		String[] QueryRef = CSVtoArray(QueryFile);
		String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
		com.hp.hpl.jena.query.Query query = null;
		for (int i = 0; i < Qsize; i++) {
			int counter = i + 1;
			// System.out.println("Answer Query " + counter);// res.put("CQ1",2
			// )
			try {
				if (QueryRef[i].startsWith("ASK")) {
					if (QueryRef[i].length() > 0) {
						String q = prefix + QueryRef[i];
						query = QueryFactory.create(q);
						QueryExecution qe = QueryExecutionFactory.create(query, model);
						boolean resultsAsk = qe.execAsk();
						temp = new ArrayList<String>(Arrays.asList(String.valueOf(resultsAsk)));
						res.put("CQ" + i, temp);
						// System.out.println(resultsAsk);
						qe.close();
					}
				} else if (QueryRef[i].startsWith("unknown")) {
					temp = new ArrayList<String>(Arrays.asList("unknown"));
					res.put("CQ" + i, temp);
					// System.out.println("unknown");
				} else {
					if (QueryRef[i].length() > 0) {
						String q = prefix + QueryRef[i];
						ArrayList<String> output = new ArrayList<String>();
						query = QueryFactory.create(q);
						QueryExecution qe = QueryExecutionFactory.create(query, model);
						com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

						while (results.hasNext()) {
							final QuerySolution sol = results.nextSolution();
							String name = sol.getResource("x").toString();
							output.add(name);
							// System.out.println(name);
						}
						res.put("CQ" + i, output);
						qe.close();
					}
				}
			} catch (Exception e) {
				System.out.println("Query is not processable");
			}
		}
		for (int k = 0; k < Qsize; k++) {
			OmResult[k] = String.valueOf(res.get("CQ" + k));
		}

		return OmResult;
	}

	public static String[] RunQueryForOi(String QueryFile, String OiOntName) throws IOException {

		String[] OiAllResult = new String[Qsize];
		for (int i = 0; i < Qsize; i++)
			OiAllResult[i] = "";

		HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>();
		ArrayList<String> temp = new ArrayList<String>();

		String OiFiles[] = OiOntName.split(";");
		for (int OiId = 0; OiId < OiFiles.length; OiId++) {
			String ontName = OiFiles[OiId];

			OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
			if (ontName.endsWith(".zip")) {
				ontName = Zipper.unzip(ontName);
			}
			FileManager.get().readModel(model, ontName);

			String[] QueryRef = CSVtoArray(QueryFile);
			String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
			com.hp.hpl.jena.query.Query query = null;
			for (int i = 0; i < Qsize; i++) {
				int counter = i + 1;
				// System.out.println("Answer Query " + counter);//
				// res.put("CQ1",2
				// )
				try {
					if (QueryRef[i].startsWith("ASK")) {

						if (QueryRef[i].length() > 0) {
							String q = prefix + QueryRef[i];
							query = QueryFactory.create(q);
							QueryExecution qe = QueryExecutionFactory.create(query, model);
							boolean resultsAsk = qe.execAsk();
							temp = new ArrayList<String>(Arrays.asList(String.valueOf(resultsAsk)));
							res.put("CQ" + i, temp);
							// System.out.println(resultsAsk);
							qe.close();
						}

					} else if (QueryRef[i].startsWith("unknown")) {
						temp = new ArrayList<String>(Arrays.asList("unknown"));
						res.put("CQ" + i, temp);
						// System.out.println("unknown");
					} else {
						if (QueryRef[i].length() > 0) {
							String q = prefix + QueryRef[i];
							ArrayList<String> output = new ArrayList<String>();
							query = QueryFactory.create(q);
							QueryExecution qe = QueryExecutionFactory.create(query, model);
							com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

							while (results.hasNext()) {
								final QuerySolution sol = results.nextSolution();
								String name = sol.getResource("x").toString();
								output.add(name);
								// System.out.println(name);
							}
							res.put("CQ" + i, output);
							qe.close();
						}
					}
				} catch (Exception e) {
					System.out.println("Query is not processable");
				}
			}

			for (int k = 0; k < Qsize; k++) {
				OiAllResult[k] = OiAllResult[k] + res.get("CQ" + k);
			}
		}
		return OiAllResult;
	}

	public static String[] CSVtoArray(String csvFile) {
		String[] QueryRef = new String[Qsize];
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

	public static String[] SetOiResult(String OiNameList) {
		// String[] res = new String[Qsize];
		String[] QueryRef = new String[Qsize];
		String[] OiName = OiNameList.split(";");
		ArrayList<Integer> OiNameId = new ArrayList<Integer>();
		for (int i = 0; i < OiName.length; i++) {
			File fileM = new File(OiName[i]);
			String nam = fileM.getName();
			switch (nam) {
			case "cmt.owl":
				OiNameId.add(0);
				break;

			case "conference.owl":
				OiNameId.add(1);
				break;

			case "confOf.owl":
				OiNameId.add(2);
				break;

			case "edas.owl":
				OiNameId.add(3);
				break;

			case "ekaw.owl":
				OiNameId.add(4);
				break;

			case "iasted.owl":
				OiNameId.add(5);
				break;

			case "sigkdd.owl":
				OiNameId.add(6);
				break;
			}
		}

		String[] OiResult = new String[Qsize];
		for (int i = 0; i < OiResult.length; i++)
			OiResult[i] = "";

		for (int i = 0; i < QueryRef.length; i++)
			QueryRef[i] = "";

		String oiAnswer = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\CQ\\inputOntologies\\OiAnswer.csv";
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(oiAnswer));
			String[] line;
			int i = 0;
			line = reader.readNext(); // skip first line (header)
			while ((line = reader.readNext()) != null) {
				for (int j = 0; j < OiNameId.size(); j++) {
					QueryRef[i] = QueryRef[i] + ";" + line[j];
				}
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return QueryRef;
	}

	private static HashMap<String, String> SetOntIndex() {
		HashMap<String, String> indexer = new HashMap<String, String>();

		String oiIndexFile = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\ontNameIndexer.csv";
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(oiIndexFile));
			String[] line;

			line = reader.readNext(); // skip first line (header)
			while ((line = reader.readNext()) != null) {
				if (line[0].trim().length() > 0) {
					indexer.put(line[1], line[0]);
					indexer.put(line[0], line[1]);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return indexer;
	}
}
