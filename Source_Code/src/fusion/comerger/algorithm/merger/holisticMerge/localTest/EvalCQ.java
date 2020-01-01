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
import java.util.HashMap;

public class EvalCQ {
	static int Qsize = 30;
	static String fileOutput = "";
	static HashMap<String, String> equalLabelList;
	static String POSITIVE = "POSITIVE";
	static String NEGATIVE = "NEGATIVE";

	public static void main(String[] args) throws IOException {
		Run();

	}

	private static void Run() throws IOException {

		// pre process
		// set perfect mapping equal list
		String csvFile = "C:\\Users\\Samira\\Desktop\\Eval_HolisticDataSet\\CQ\\EqLabelListPerfect.csv";
		HashMap<String, String> eqList = QueryTest.readEquaLabel(csvFile);
		equalLabelList = eqList;

		// create output: a CSV file with header
		fileOutput = "C:\\Users\\Samira\\Desktop\\Eval_HolisticDataSet\\CQ\\OutputEvalQuery.csv";
		GenerateOutput.saveEvalQueryResult_Header(fileOutput);

		int currentNumberDataSet = 8;
		String[] address = new String[currentNumberDataSet + 1];
		String baseAddress = "C:\\Users\\Samira\\Desktop\\Eval_HolisticDataSet\\";
		for (int j = 1; j <= currentNumberDataSet; j++)
			address[j] = baseAddress + "d" + j;

		for (int i = 1; i <= currentNumberDataSet; i++) {
			File folder = new File(address[i] + "\\originalFiles");
			String OiNameList = StatisticTest.listFilesForFolder(folder);
			String[] OiAllResult = QueryTest.SetOiResult(OiNameList);

			File folderM = new File(address[i] + "\\mergedFiles");
			String OmName = StatisticTest.listFilesForFolder(folderM);

			File folderCQ = new File(address[i] + "\\OmQuery");
			String QueryOmFile = StatisticTest.listFilesForFolder(folderCQ);

			String[] OmAllResult = QueryTest.RunQueryForOm(QueryOmFile, OmName);
			compareCQResultTP(OiAllResult, OmAllResult, OmName);

//			GenerateOutput.inserEmptyLineQuery(fileOutput);
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

	private static void compareCQResultTP(String[] OiAllResult, String[] OmAllResult, String OmName)
			throws IOException {
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

		int TP = 0, FP = 0, FN = 0, TN = 0;

		// for test
		// System.out.println("********* OM Answer");
		// for (int i = 0; i < OmAllResult.length; i++)
		// System.out.println("--Answer" + i + OmAllResult[i]);
		//
		// System.out.println("********* Comparision Result");

		for (int i = 0; i < OmAllResult.length; i++) {
			String answerOm = OmAllResult[i].trim();
			String OiAns = OiAllResult[i].trim();

			String OiPositivenegative = setPositiveNegativeForOiAns(OiAns);

			notFound = 0;
			found = 0;
			trueAnswer = 0;
			falseAnswer = 0;

			switch (answerOm) {
			case ("[unknown]"):
				if (OiPositivenegative.equals(POSITIVE)) {
					// it means, OS can answer this query but Om return unknown
					// answer
					FP++;
					unknownAnswer++;
					System.out.println("--comparision Q" + i + " unknown \t FP" );
				} else {
					FN++;
					unknownAnswer++;
					System.out.println("--comparision Q" + i + " unknown \t FN");
				}
				
				break;

			case ("[]"):
				if (OiPositivenegative.equals(POSITIVE)) {
					// it means, OS can answer this query but Om return unknown
					// answer
					FP++;
					nullAnswer++;
					System.out.println("--comparision Q" + i + " nullAnswer \t FP");
				} else {
					FN++;
					nullAnswer++;
					System.out.println("--comparision Q" + i + " nullAnswer \t FN");
				}
				
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
				if (OiPositivenegative.equals(POSITIVE)) {
					// it means, OS can answer this query but Om return unknown
					// answer

					if ((trueAnswer != 0 && falseAnswer == 0) || (trueAnswer > falseAnswer)) {
						if (answerOm.contains("true")) {
							compeleteAnswer++;
							TP++;
							System.out.println("--comparision Q" + i + " compeleteAnswer \t TP");
						} else if (answerOm.contains("false")) {
							wrongAnswer++;
							FP++;
							System.out.println("--comparision Q" + i + " wrongAnswer \t FP");
						}

					} else if ((trueAnswer == 0 && falseAnswer != 0) || (falseAnswer > trueAnswer)) {
						if (answerOm.contains("false")) {
							compeleteAnswer++;
							TP++;
							System.out.println("--comparision Q" + i + " compeleteAnswer \t TP");
						} else if (answerOm.contains("true")) {
							wrongAnswer++;
							FP++;
							System.out.println("--comparision Q" + i + " wrongAnswer \t FP");
						}
						// } else if (OiAns.toLowerCase().contains("unknown")) {
						//// Om answer T/F, but Oi are unknown
						//// if Oi = unknown, then OiPositivenegative is
						// negative
						// unknownAnswer++;
						// System.out.println("--comparision Q" + i + "
						// unknownAnswer");
					} else {
						wrongAnswer++;
						System.out.println("--comparision Q" + i + " wrongAnswer");
					}
				} else {
					// i.e. OiPositivenegative=NEGATIVE
					// Om returns something, but OS has null/negative answer
					TN++;
					System.out.println("--comparision Q" + i + " TN");
				}

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

				if (OiPositivenegative.equals(POSITIVE)) {
					if (found > 0 && notFound == 0) {
						compeleteAnswer++;
						TP++;
						System.out.println("--comparision Q" + i + " compeleteAnswer \t TP");
					} else if (found > notFound) {
						semiCompeletAnswer++;
						TP++;
						System.out.println("--comparision Q" + i + " semiCompeletAnswer \t TP");
					} else if (notFound > found) {
						partialAnswer++;
						TP++;
						System.out.println("--comparision Q" + i + " partialAnswer \t TP");
					} else if (includeUnknownAnswer > 0) {
						// cannot happen here, because then OiPositivenegative
						// should be negative
						unknownAnswer++;
						System.out.println("--comparision Q" + i + " unknownAnswer");
					} else if (found == notFound) {
						partialAnswer++;
						TP++;
						System.out.println("--comparision Q" + i + " partialAnswer \t TP");
						// it can be also as semiCompelete
					} else {
						int w = 0;
						System.out.println("--comparision Q" + i + " !!!!!");
						somthingIsWrong++;
					}
				} else {
					// i.e. OiPositivenegative = NEGATIVE, oi return unknown but Om return something
					TN++;
					System.out.println("--comparision Q" + i + " TN");
				}

				break;
			}
		}

		// partial answer: e.g. found = 1, notFound =2. It means only a few of
		// Oi answers have been found in Om, and many of them cannot be found.
		// semiCompelete answer: e.g. found= 5, notFound =2
		HashMap<String, String> result = new HashMap<String, String>();
//		result.put("unknownAnswer", String.valueOf(unknownAnswer));
//		result.put("nullAnswer", String.valueOf(nullAnswer));
//		result.put("compeleteAnswer", String.valueOf(compeleteAnswer));
//		result.put("partialAnswer", String.valueOf(partialAnswer));
//		result.put("wrongAnswer", String.valueOf(wrongAnswer));
//		result.put("semiCompeletAnswer", String.valueOf(semiCompeletAnswer));
//		result.put("somthingIsWrong", String.valueOf(somthingIsWrong));

		result.put("TP", String.valueOf(TP));
		result.put("FP", String.valueOf(FP));
		result.put("FN", String.valueOf(FN));
		result.put("TN", String.valueOf(TN));

		double precision = ((double) (TP)) / ((double) (TP + FP));
		double recall = ((double) (TP)) / ((double) (TP + FN));
		double accuracy = precision * recall;
		
		String label="";
		if (precision>=0.5 && recall >=0.5){
			label ="Good";
		}else if(precision<0.5 && recall>=0.5){
			label="Less Good";
		}else if(precision>=0.5 && recall<0.5){
			label="Bad";
		}else if(precision<0.5 && recall <0.5){
			label ="Worse";
		}
		result.put("label", label);

		result.put("precision", String.valueOf(precision));
		result.put("recall", String.valueOf(recall));
		result.put("accuracy", String.valueOf(accuracy));

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
		String OmVersion = null;// OntIndexer.get(OmNameFile);
		GenerateOutput.saveEvalQueryResult(fileOutput, result, OmNameFile, OmVersion);

	}

	private static String setPositiveNegativeForOiAns(String oiAns) {
		String ans = POSITIVE;
		String[] oiA = oiAns.split(";");
		for (int i = 0; i < oiA.length; i++) {
			if (oiA[i].length() > 0) {
				switch (oiA[i]) {
				case ("[unknown]"):
				case ("unknown"):
				case ("[]"):
					ans = NEGATIVE;
					break;

				case ("[false]"):
				case ("[true]"):
					ans = POSITIVE;
					break;

				default:
					ans = POSITIVE;
				}
			}
		}
		return ans;
	}
}
