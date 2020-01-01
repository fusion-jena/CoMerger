package fusion.comerger.servlets;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.json.JSONObject;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.Parameter;
import fusion.comerger.algorithm.merger.holisticMerge.GMR.CompatibilityChecker;
import fusion.comerger.algorithm.merger.holisticMerge.GMR.RuleSets;
import fusion.comerger.algorithm.merger.holisticMerge.consistency.ConsistencyProcess;
import fusion.comerger.algorithm.merger.holisticMerge.evaluator.EvaluationRepair;
import fusion.comerger.algorithm.merger.holisticMerge.general.Zipper;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.model.HModel;
import fusion.comerger.algorithm.merger.model.ModelReader;
import fusion.comerger.algorithm.merger.query.QueryExcecute;

/**
 * Servlet implementation class MergingServlet
 */
@WebServlet("/MergingServlet")
public class MergingServlet extends HttpServlet {
	private static final long serialVersionUID = 1001L;
	public static String resMacth = "";
	public static String inputOnts = new String();
	public static String mapOnts = new String();
	public static HModel mergedModel = null;
	public static String mergedOnt = new String();
	public static RuleSets RSets = new RuleSets();
	private static final String secretKey = "6Lc5rrUUAAAAAJN7DNL0lLcdrDt68Bjby2MSrv10";
	String previousUserItem;

	/**
	 * Default constructor.
	 */
	public MergingServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String UPLOAD_DIRECTORY = getServletContext().getRealPath("/") + "uploads\\";
			Parameter.setFilePathDirectory(UPLOAD_DIRECTORY);

			String submitType = "", mergeType = "", MergeOutputType = "", preferedOnt = "equal", queryStringAll = "",
					ListOntsQ = "", queryStringM = "", queryStringO = "", resultQueryM = "", resultQueryO = "",
					selectedRepair = "", selectedUserItem = "", selectedUserPlan = "", userConsParam = "", SelOntQ = "",
					gRecaptchaResponse = "", uploadedInputFiles = null, uploadedmapFiles = null,
					uploadedMergeFile = null;
			int numSuggestion = 5;
			String captcahMsg = "<div	style=\"height:50px; background-color:#f9dbdb; font-weight:bold; color:red;\">	<span style=\"margin:150px;\">Sorry. Google Captcha did not validate you! Please try it again.</span>  </div>";

			StatisticTest.result = new HashMap<String, String>();
			List<FileItem> multiFiles = new ServletFileUpload(new DiskFileItemFactory())
					.parseRequest(new ServletRequestContext(request));

			if (!new File(UPLOAD_DIRECTORY).exists())
				new File(UPLOAD_DIRECTORY).mkdir();
			for (FileItem item : multiFiles) {
				if (item.getFieldName().equals("inputFiles")) {
					if (item.getName() != null) {
						String temp1 = new File(item.getName()).getName();
						if (temp1 != "") {
							String temp2 = UPLOAD_DIRECTORY + temp1;
							item.write(new File(temp2));
							if (inputOnts.length() < 1) {
								inputOnts = temp2;
							} else {
								inputOnts = inputOnts + ";" + temp2;
							}
						}
					}
				}

				else if (item.getFieldName().equals("MergeTypeComboBox"))
					mergeType = item.getString();

				else if (item.getFieldName().equals("mapFiles")) {
					if (item.getName() != null) {
						String name = new File(item.getName()).getName();
						if (name != "") {
							String tempe = UPLOAD_DIRECTORY + name;
							item.write(new File(tempe));
							if (mapOnts.length() < 1) {
								mapOnts = tempe;
							} else {
								mapOnts = mapOnts + ";" + tempe;
							}
						}
					}

				}

				else if (item.getFieldName().equals("mergedFile")) {
					if (item.getName() != null) {
						String name = new File(item.getName()).getName();
						if (name != "") {
							mergedOnt = UPLOAD_DIRECTORY + name;
							item.write(new File(mergedOnt));
						}
					}
				} else if (item.getFieldName().equals("uploadedInputFiles"))
					uploadedInputFiles = item.getString();

				else if (item.getFieldName().equals("uploadedmapFiles"))
					uploadedmapFiles = item.getString();

				else if (item.getFieldName().equals("uploadedMergeFile"))
					uploadedMergeFile = item.getString();

				else if (item.getFieldName().equals("queryStringAll"))
					queryStringAll = item.getString();

				else if (item.getFieldName().equals("queryStringM"))
					queryStringM = item.getString();

				else if (item.getFieldName().equals("queryStringO"))
					queryStringO = item.getString();

				else if (item.getFieldName().equals("resultQueryO"))
					resultQueryO = item.getString();

				else if (item.getFieldName().equals("resultQueryM"))
					resultQueryM = item.getString();

				else if (item.getFieldName().equals("SelOntQ"))
					SelOntQ = item.getString();

				else if (item.getFieldName().equals("ListOntsQ"))
					ListOntsQ = item.getString();

				else if (item.getFieldName().equals("PreferedOnt")) {
					if (item.getString() == null || item.getString().equals("")) {
						preferedOnt = "equal";
					} else {
						preferedOnt = item.getString();
					}
				}

				else if (item.getFieldName().equals("MergeOutputType"))
					MergeOutputType = item.getString();

				else if (item.getFieldName().equals("selectedRepair"))
					selectedRepair = item.getString();

				else if (item.getFieldName().equals("selectedUserPlan"))
					selectedUserPlan = item.getString();

				else if (item.getFieldName().equals("selectedUserItem"))
					selectedUserItem = item.getString();

				else if (item.getFieldName().equals("selectedConsParam"))
					userConsParam = item.getString();

				else if (item.getFieldName().equals("submit"))
					submitType = item.getString();

				else if (item.getFieldName().equals("g-recaptcha-response"))
					gRecaptchaResponse = item.getString();

			}

			numSuggestion = ConfigServlet.setnumSuggestion(selectedUserItem);

			if (submitType == null || submitType.length() < 1) {
				MyLogging.log(Level.INFO, "uploading files");
			} else {
				MyLogging.log(Level.INFO, "Starting doing: " + submitType);
			}

			switch (submitType) {

			case "DoMerge":
				if (isCaptchaValid(gRecaptchaResponse) == false) {
					request.setAttribute("error", captcahMsg);
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				} else {

					if (inputOnts.length() < 1 && uploadedInputFiles != null)
						inputOnts = uploadedInputFiles;
					if (mapOnts.length() < 1 && uploadedmapFiles != null)
						mapOnts = uploadedmapFiles;
					if (inputOnts.length() < 1) {
						request.setAttribute("uploadedmapFiles", mapOnts);
						String msg = "The input ontologies are null.";
						MyLogging.log(Level.WARNING, "The input ontologies are null. \n");
						String logZip = Zipper.zipFiles(Parameter.getLogFile());
						String logFile = "/uploads/" + new File(logZip).getName();
						request.setAttribute("logFile", logFile);
						request.setAttribute("msg", msg);
						request = ConfigServlet.requestConfigMerge(request, mergeType, MergeOutputType,
								selectedUserItem);
						request.setAttribute("NumPrefOnt", preferedOnt);
						request.getRequestDispatcher("/merging.jsp").forward(request, response);
					} else {
						if (mapOnts.length() < 1) {
							String ch1 = "1", ch2 = "1"; // TODO:correct it
							mapOnts = MatchingProcess.CreateMap(inputOnts, ch1, ch2, UPLOAD_DIRECTORY);
						}
						mergedModel = MergingProcess.DoMerge(inputOnts, mapOnts, UPLOAD_DIRECTORY, mergeType,
								selectedUserItem, preferedOnt, MergeOutputType);

						mergedModel = MergingProcess.DoMergeEval(mergedModel, selectedUserItem);
						previousUserItem = selectedUserItem;
						String[] result = mergedModel.getEvalResult();
						for (int i = 0; i < 14; i++)
							request.setAttribute("res" + i, result[i]);

						String chartInfo = mergedModel.getEvalTotalLabel();
						request.setAttribute("chartInfo", chartInfo);
						String MergedOntZip = "/uploads/" + new File(mergedModel.getOntZipName()).getName();
						request.setAttribute("MergedOntZip", MergedOntZip);
						String MergedSubOntZip = "/uploads/" + new File(mergedModel.getSubMergedOntZipName()).getName();
						request.setAttribute("MergedSubOntZip", MergedSubOntZip);
						request.setAttribute("SavedPreferedOnt", preferedOnt);
						request.setAttribute("inputFile", inputOnts);
						String mergeEvalTxt = "/uploads/" + new File(mergedModel.getEvalResultTxt()).getName();
						request.setAttribute("zipResultTxt", mergeEvalTxt);
						request = ConfigServlet.mergeEvaluationResultConfig(request, selectedUserItem);
						String logZip = Zipper.zipFiles(Parameter.getLogFile());
						String logFile = "/uploads/" + new File(logZip).getName();
						request.setAttribute("logFile", logFile);
						request.getRequestDispatcher("/mergeResult.jsp").forward(request, response);
						// clear();
					}
				}
				break;

			case "DoCompatibilityCheck":
				RSets = CompatibilityChecker.RuleConflict(selectedUserItem, numSuggestion);

				String guidline = "<font color=\"green\">green</font>: your compatible GMRs, <font color=\"red\">red</font>:your incompatible GMRs, <font	color=\"orange\">orange</font>: extra compatible GMRs";
				request.setAttribute("guidline", guidline);
				request.setAttribute("uploadedmapFiles", mapOnts);
				request.setAttribute("numberOfSuggestion", numSuggestion);
				request.setAttribute("conflictRes", RSets.getMessage());
				request = ConfigServlet.requestConfigMerge(request, mergeType, MergeOutputType, selectedUserItem);
				request = ConfigServlet.setGMRcheckboxes(request, RSets);
				request.setAttribute("NumPrefOnt", preferedOnt);
				request.setAttribute("SecGMR", "SecGMRshow");
				String logZip = Zipper.zipFiles(Parameter.getLogFile());
				String logFile = "/uploads/" + new File(logZip).getName();
				request.setAttribute("logFile", logFile);
				request.getRequestDispatcher("/merging.jsp").forward(request, response);
				break;

			case "DoCompatibilityCheckAfterMerge":
				RSets = CompatibilityChecker.RuleConflict(selectedUserItem, numSuggestion);
				guidline = "<font color=\"green\">green</font>: your compatible GMRs, <font color=\"red\">red</font>:your incompatible GMRs, <font	color=\"orange\">orange</font>: extra compatible GMRs";
				request.setAttribute("guidline", guidline);
				request.setAttribute("uploadedmapFiles", mapOnts);
				request.setAttribute("numberOfSuggestion", numSuggestion);
				request.setAttribute("conflictRes", RSets.getMessage());
				request = ConfigServlet.requestConfigMerge(request, mergeType, MergeOutputType, selectedUserItem);
				request = ConfigServlet.mergeEvaluationResultConfig(request, selectedUserItem);
				request.setAttribute("NumPrefOnt", preferedOnt);
				request = ConfigServlet.setGMRcheckboxes(request, RSets);
				request.setAttribute("SecGMR", "SecGMRshow");
				logZip = Zipper.zipFiles(Parameter.getLogFile());
				logFile = "/uploads/" + new File(logZip).getName();
				request.setAttribute("logFile", logFile);
				String chartInfo = mergedModel.getEvalTotalLabel();
				request.setAttribute("chartInfo", chartInfo);
				request.getRequestDispatcher("/mergeResult.jsp").forward(request, response);
				break;

			case "DoMergeEval":
				if (isCaptchaValid(gRecaptchaResponse) == false) {
					request.setAttribute("error", captcahMsg);
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				} else {
					if (inputOnts.length() < 1 && uploadedInputFiles != null)
						inputOnts = uploadedInputFiles;
					if (mapOnts.length() < 1 && uploadedmapFiles != null)
						mapOnts = uploadedmapFiles;
					if (mergedOnt.length() < 1 && uploadedMergeFile != null)
						mergedOnt = uploadedMergeFile;

					if (inputOnts.length() < 1) {
						String msg = "The input ontologies are empty.<br>";
						request.setAttribute("msg", msg);
						request.setAttribute("uploadedMergeFile", mergedOnt);
						request.setAttribute("uploadedmapFiles", mapOnts);
						request = ConfigServlet.requestConfigMergeEval(request, selectedUserItem);
						request.setAttribute("NumPrefOnt", preferedOnt);
						MyLogging.log(Level.WARNING, "The input ontologies are empty. \n");
						logZip = Zipper.zipFiles(Parameter.getLogFile());
						logFile = "/uploads/" + new File(logZip).getName();
						request.setAttribute("logFile", logFile);
						request.getRequestDispatcher("/mergingEval.jsp").forward(request, response);

					} else if (mergedOnt.length() < 1) {
						String msg = "The merged ontology is empty. <br>";
						request.setAttribute("msg", msg);
						request.setAttribute("uploadedmapFiles", mapOnts);
						request.setAttribute("uploadedInputFiles", inputOnts);
						request = ConfigServlet.requestConfigMergeEval(request, selectedUserItem);
						request.setAttribute("NumPrefOnt", preferedOnt);
						MyLogging.log(Level.WARNING, "The merged ontology is empty. \n");
						logZip = Zipper.zipFiles(Parameter.getLogFile());
						logFile = "/uploads/" + new File(logZip).getName();
						request.setAttribute("logFile", logFile);
						request.getRequestDispatcher("/mergingEval.jsp").forward(request, response);

					} else {
						if (mapOnts.length() < 1) {
							String ch1 = "1", ch2 = "1";
							mapOnts = MatchingProcess.CreateMap(inputOnts, ch1, ch2, UPLOAD_DIRECTORY);
						}
						mergedModel = ModelReader.createReadModel(inputOnts, mapOnts, mergedOnt, preferedOnt);
						mergedModel = MergingProcess.DoMergeEval(mergedModel, selectedUserItem);
						previousUserItem = selectedUserItem;
						String[] result = mergedModel.getEvalResult();
						for (int i = 0; i < 13; i++)
							request.setAttribute("res" + i, result[i]);
						chartInfo = mergedModel.getEvalTotalLabel();
						request.setAttribute("chartInfo", chartInfo);
						String MergedOntZip = "/uploads/" + new File(mergedModel.getOntZipName()).getName();
						request.setAttribute("MergedOntZip", MergedOntZip);
						request.setAttribute("uploadedmapFiles", mapOnts);
						request.setAttribute("uploadedMergeFile", mergedOnt);// result[9][0]);
						request.setAttribute("uploadedInputFiles", inputOnts);
						request.setAttribute("SecDW", "hide");
						String mergeEvalTxt = "/uploads/" + new File(mergedModel.getEvalResultTxt()).getName();
						request.setAttribute("zipResultTxt", mergeEvalTxt);
						request = ConfigServlet.mergeEvaluationResultConfig(request, selectedUserItem);
						logZip = Zipper.zipFiles(Parameter.getLogFile());
						logFile = "/uploads/" + new File(logZip).getName();
						request.setAttribute("logFile", logFile);
						request.getRequestDispatcher("/mergeResult.jsp").forward(request, response);
					}
				}
				break;

			case "DoEvalAfterMerge":
				mergedModel = MergingProcess.DoMergeEval(mergedModel, selectedUserItem);
				clear();
				previousUserItem = selectedUserItem;
				String[] result = mergedModel.getEvalResult();
				for (int i = 0; i < 14; i++)
					request.setAttribute("res" + i, result[i]);

				chartInfo = mergedModel.getEvalTotalLabel();
				request.setAttribute("chartInfo", chartInfo);
				String MergedOntZip = "/uploads/" + new File(mergedModel.getOntZipName()).getName();
				request.setAttribute("MergedOntZip", MergedOntZip);
				request.setAttribute("SavedPreferedOnt", preferedOnt);
				request.setAttribute("inputFile", inputOnts);
				String mergeEvalTxt = "/uploads/" + new File(mergedModel.getEvalResultTxt()).getName();
				request.setAttribute("zipResultTxt", mergeEvalTxt);
				request = ConfigServlet.mergeEvaluationResultConfig(request, selectedUserItem);
				logZip = Zipper.zipFiles(Parameter.getLogFile());
				logFile = "/uploads/" + new File(logZip).getName();
				request.setAttribute("logFile", logFile);
				request.getRequestDispatcher("/mergeResult.jsp").forward(request, response);
				break;

			case "DoEvalRepairErrors":
				if (selectedRepair.length() > 0) {
					mergedModel = EvaluationRepair.DoRepair(mergedModel, selectedRepair);
					String msg = "Repair on selected errors has been applied and the new build merged ontology again is evaluated.";
					request.setAttribute("msg", msg);
				} else {
					String msg = "No repair on any error is selected. Please select your desire repair option, then press Repair Errors button.";
					request.setAttribute("msg", msg);
				}
				mergedModel = MergingProcess.DoMergeEval(mergedModel, previousUserItem);
				result = mergedModel.getEvalResult();
				for (int i = 0; i < 13; i++)
					request.setAttribute("res" + i, result[i]);

				chartInfo = mergedModel.getEvalTotalLabel();
				request.setAttribute("chartInfo", chartInfo);
				MergedOntZip = "/uploads/" + new File(mergedModel.getOntZipName()).getName();
				request.setAttribute("MergedOntZip", MergedOntZip);
				request.setAttribute("uploadedmapFiles", mapOnts);
				request.setAttribute("uploadedMergeFile", mergedOnt);
				request.setAttribute("uploadedInputFiles", inputOnts);
				request.setAttribute("SecDW", "hide");
				mergeEvalTxt = "/uploads/" + new File(mergedModel.getEvalResultTxt()).getName();
				request.setAttribute("zipResultTxt", mergeEvalTxt);
				request = ConfigServlet.mergeEvaluationResultConfig(request, previousUserItem);
				logZip = Zipper.zipFiles(Parameter.getLogFile());
				logFile = "/uploads/" + new File(logZip).getName();
				request.setAttribute("logFile", logFile);
				request.getRequestDispatcher("/mergeResult.jsp").forward(request, response);

				break;

			case "DoRefine":
				mergedModel = MergingProcess.DoRefine(mergedModel, UPLOAD_DIRECTORY, selectedUserItem, preferedOnt);
				mergedModel = MergingProcess.DoMergeEval(mergedModel, selectedUserItem);
				clear();
				String[] resultEval = mergedModel.getEvalResult();
				for (int i = 0; i < 14; i++)
					request.setAttribute("res" + i, resultEval[i]);

				chartInfo = mergedModel.getEvalTotalLabel();
				request.setAttribute("chartInfo", chartInfo);
				MergedOntZip = "/uploads/" + new File(mergedModel.getOntZipName()).getName();
				request.setAttribute("MergedOntZip", MergedOntZip);
				request.setAttribute("SavedPreferedOnt", preferedOnt);
				request.setAttribute("inputFile", inputOnts);
				mergeEvalTxt = "/uploads/" + new File(mergedModel.getEvalResultTxt()).getName();
				request.setAttribute("zipResultTxt", mergeEvalTxt);
				request = ConfigServlet.mergeEvaluationResultConfig(request, selectedUserItem);
				logZip = Zipper.zipFiles(Parameter.getLogFile());
				logFile = "/uploads/" + new File(logZip).getName();
				request.setAttribute("logFile", logFile);
				request.getRequestDispatcher("/mergeResult.jsp").forward(request, response);

				break;

			case "DoDirectConsistency":
				if (isCaptchaValid(gRecaptchaResponse) == false) {
					request.setAttribute("error", captcahMsg);
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				} else {
					if (inputOnts.length() < 1 && uploadedInputFiles != null)
						inputOnts = uploadedInputFiles;
					if (mapOnts.length() < 1 && uploadedmapFiles != null)
						mapOnts = uploadedmapFiles;
					if (mergedOnt.length() < 1 && uploadedMergeFile != null)
						mergedOnt = uploadedMergeFile;
					if (inputOnts.length() < 1) {
						String msg = "The input ontologies are empty.<br>";
						request.setAttribute("msg", msg);
						request.setAttribute("uploadedMergeFile", mergedOnt);
						request.setAttribute("uploadedmapFiles", mapOnts);
						request = ConfigServlet.requestConfigMergeEval(request, selectedUserItem);
						request.setAttribute("NumPrefOnt", preferedOnt);
						MyLogging.log(Level.WARNING, "The input ontologies are empty. \n");
						logZip = Zipper.zipFiles(Parameter.getLogFile());
						logFile = "/uploads/" + new File(logZip).getName();
						request.setAttribute("logFile", logFile);
						request.getRequestDispatcher("/mergingEval.jsp").forward(request, response);

					} else if (mergedOnt.length() < 1) {
						String msg = "The merged ontology is empty. <br>";
						request.setAttribute("msg", msg);
						request.setAttribute("uploadedmapFiles", mapOnts);
						request.setAttribute("uploadedInputFiles", inputOnts);
						request = ConfigServlet.requestConfigMergeEval(request, selectedUserItem);
						request.setAttribute("NumPrefOnt", preferedOnt);
						MyLogging.log(Level.WARNING, "The merged ontology is empty. \n");
						logZip = Zipper.zipFiles(Parameter.getLogFile());
						logFile = "/uploads/" + new File(logZip).getName();
						request.setAttribute("logFile", logFile);
						request.getRequestDispatcher("/mergingEval.jsp").forward(request, response);
					} else {
						if (mapOnts.length() < 1) {
							String ch1 = "1", ch2 = "1";
							mapOnts = MatchingProcess.CreateMap(inputOnts, ch1, ch2, UPLOAD_DIRECTORY);
						}
						mergedModel = ModelReader.createReadModel(inputOnts, mapOnts, mergedOnt, preferedOnt);
						mergedModel = ConsistencyProcess.DoConsistencyCheck(mergedModel, userConsParam);
						String[] resultC = mergedModel.getConsResult();
						for (int i = 0; i < resultC.length; i++)
							request.setAttribute("res" + i, resultC[i]);
						MergedOntZip = "/uploads/" + new File(mergedModel.getOntZipName()).getName();
						request.setAttribute("MergedOntZip", MergedOntZip);
						String consistencyTxt = "/uploads/" + new File(mergedModel.getConsistencyResultTxt()).getName();
						request.setAttribute("zipResultTxt", consistencyTxt);
						logZip = Zipper.zipFiles(Parameter.getLogFile());
						logFile = "/uploads/" + new File(logZip).getName();
						request.setAttribute("logFile", logFile);
						request.getRequestDispatcher("/consistencyResult.jsp").forward(request, response);
					}
				}
				break;

			case "DoConsistencyChecker":
				mergedModel = ConsistencyProcess.DoConsistencyCheck(mergedModel, userConsParam);
				String[] resultCons = mergedModel.getConsResult();
				for (int i = 0; i < resultCons.length; i++)
					request.setAttribute("res" + i, resultCons[i]);
				MergedOntZip = "/uploads/" + new File(mergedModel.getOntZipName()).getName();
				request.setAttribute("MergedOntZip", MergedOntZip);
				String consistencyTxt = "/uploads/" + new File(mergedModel.getConsistencyResultTxt()).getName();
				request.setAttribute("zipResultTxt", consistencyTxt);
				logZip = Zipper.zipFiles(Parameter.getLogFile());
				logFile = "/uploads/" + new File(logZip).getName();
				request.setAttribute("logFile", logFile);
				request.getRequestDispatcher("/consistencyResult.jsp").forward(request, response);
				break;

			case "DoRevise":
				if (selectedUserPlan.length() > 0) {
					mergedModel = ConsistencyProcess.DoReviseConsistency(mergedModel, selectedUserPlan, userConsParam);
					String[] result1 = mergedModel.getConsResult();
					for (int i = 0; i < result1.length; i++)
						request.setAttribute("res" + i, result1[i]);
					String[] result2 = mergedModel.getReviseResult();
					int k = result1.length + 1;
					for (int i = 0; i < result2.length; i++) {
						request.setAttribute("res" + k, result2[i]);
						k++;
					}
				} else {
					request.setAttribute("res21", "<b>No revised plan has been selected.</b>");
					request.setAttribute("res15", "Your ontology does not need any more revise edition.");
				}
				MergedOntZip = "/uploads/" + new File(mergedModel.getOntZipName()).getName();
				request.setAttribute("MergedOntZip", MergedOntZip);
				consistencyTxt = "/uploads/" + new File(mergedModel.getConsistencyResultTxt()).getName();
				request.setAttribute("zipResultTxt", consistencyTxt);
				logZip = Zipper.zipFiles(Parameter.getLogFile());
				logFile = "/uploads/" + new File(logZip).getName();
				request.setAttribute("logFile", logFile);
				request.getRequestDispatcher("/consistencyReviseResult.jsp").forward(request, response);
				break;
			///////////////////// Begin-QUERY********************************************************************
			case "GoQuerySeveral":
				if (isCaptchaValid(gRecaptchaResponse) == false) {
					request.setAttribute("error", captcahMsg);
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				} else {
					if (inputOnts.length() < 1 && uploadedInputFiles != null)
						inputOnts = uploadedInputFiles;
					if (mapOnts.length() < 1 && uploadedmapFiles != null)
						mapOnts = uploadedmapFiles;
					if (mergedOnt.length() < 1 && uploadedMergeFile != null)
						mergedOnt = uploadedMergeFile;
					if (mergedOnt.length() < 1 && mergedModel != null)
						mergedOnt = mergedModel.getOntName();

					if (inputOnts.length() < 1) {
						String msg = "The input ontologies are empty.<br>";
						request.setAttribute("msg", msg);
						request.setAttribute("uploadedMergeFile", mergedOnt);
						request.setAttribute("uploadedmapFiles", mapOnts);
						request = ConfigServlet.requestConfigMergeEval(request, selectedUserItem);
						request.setAttribute("NumPrefOnt", preferedOnt);
						MyLogging.log(Level.WARNING, "The input ontologies are empty. \n");
						logZip = Zipper.zipFiles(Parameter.getLogFile());
						logFile = "/uploads/" + new File(logZip).getName();
						request.setAttribute("logFile", logFile);
						request.getRequestDispatcher("/mergingEval.jsp").forward(request, response);

					} else if (mergedOnt.length() < 1) {
						String msg = "The merged ontology is empty. <br>";
						request.setAttribute("msg", msg);
						request.setAttribute("uploadedmapFiles", mapOnts);
						request.setAttribute("uploadedInputFiles", inputOnts);
						request = ConfigServlet.requestConfigMergeEval(request, selectedUserItem);
						request.setAttribute("NumPrefOnt", preferedOnt);
						MyLogging.log(Level.WARNING, "The merged ontology is empty. \n");
						logZip = Zipper.zipFiles(Parameter.getLogFile());
						logFile = "/uploads/" + new File(logZip).getName();
						request.setAttribute("logFile", logFile);
						request.getRequestDispatcher("/mergingEval.jsp").forward(request, response);
					} else {
						String[] fileName = inputOnts.split(";");
						String ListOnts = null;
						for (int i = 0; i < fileName.length; i++) {
							if (ListOnts == null) {
								ListOnts = "<option ${SelOnt" + i + "} value=" + i + ">Ontology " + i + "</option>";
							} else {
								ListOnts = ListOnts + "<option ${SelOnt" + i + "} value=" + i + ">Ontology " + i
										+ "</option>";
							}
						}

						request.setAttribute("ListOntsQ", ListOnts);
						request.setAttribute("uploadedmapFiles", mapOnts);
						request.setAttribute("uploadedMergeFile", mergedOnt);
						request.setAttribute("uploadedInputFiles", inputOnts);
						request.getRequestDispatcher("/query2.jsp").forward(request, response);
					}
				}
				break;

			case "GoQueryOne":
				if (isCaptchaValid(gRecaptchaResponse) == false) {
					request.setAttribute("error", captcahMsg);
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				} else {

					if (inputOnts.length() < 1 && uploadedInputFiles != null)
						inputOnts = uploadedInputFiles;
					if (mapOnts.length() < 1 && uploadedmapFiles != null)
						mapOnts = uploadedmapFiles;
					if (mergedOnt.length() < 1 && uploadedMergeFile != null)
						mergedOnt = uploadedMergeFile;
					if (mergedOnt.length() < 1 && mergedModel != null)
						mergedOnt = mergedModel.getOntName();

					if (inputOnts.length() < 1) {
						String msg = "The input ontologies are empty.<br>";
						request.setAttribute("msg", msg);
						request.setAttribute("uploadedMergeFile", mergedOnt);
						request.setAttribute("uploadedmapFiles", mapOnts);
						request = ConfigServlet.requestConfigMergeEval(request, selectedUserItem);
						request.setAttribute("NumPrefOnt", preferedOnt);
						MyLogging.log(Level.WARNING, "The input ontologies are empty. \n");
						logZip = Zipper.zipFiles(Parameter.getLogFile());
						logFile = "/uploads/" + new File(logZip).getName();
						request.setAttribute("logFile", logFile);
						request.getRequestDispatcher("/mergingEval.jsp").forward(request, response);

					} else if (mergedOnt.length() < 1) {
						String msg = "The merged ontology is empty. <br>";
						request.setAttribute("msg", msg);
						request.setAttribute("uploadedmapFiles", mapOnts);
						request.setAttribute("uploadedInputFiles", inputOnts);
						request = ConfigServlet.requestConfigMergeEval(request, selectedUserItem);
						request.setAttribute("NumPrefOnt", preferedOnt);
						MyLogging.log(Level.WARNING, "The merged ontology is empty. \n");
						logZip = Zipper.zipFiles(Parameter.getLogFile());
						logFile = "/uploads/" + new File(logZip).getName();
						request.setAttribute("logFile", logFile);
						request.getRequestDispatcher("/mergingEval.jsp").forward(request, response);
					} else {

						request.setAttribute("uploadedmapFiles", mapOnts);
						request.setAttribute("uploadedMergeFile", mergedOnt);
						request.setAttribute("uploadedInputFiles", inputOnts);
						request.getRequestDispatcher("/query3.jsp").forward(request, response);
					}
				}
				break;

			case "DoQueryAll":
				if (inputOnts.length() < 1 && !uploadedInputFiles.equals(""))
					inputOnts = uploadedInputFiles;
				if (mergedOnt.length() < 1 && !uploadedMergeFile.equals(""))
					mergedOnt = uploadedMergeFile;

				String[] resAll = QueryExcecute.RunQueryAll(mergedOnt, inputOnts, queryStringAll);
				request.setAttribute("uploadedmapFiles", mapOnts);
				request.setAttribute("uploadedMergeFile", mergedOnt);
				request.setAttribute("uploadedInputFiles", inputOnts);
				request.setAttribute("resultQueryM", resAll[0]);
				request.setAttribute("resultQueryO", resAll[1]);
				request.setAttribute("queryStringAll", queryStringAll);
				logZip = Zipper.zipFiles(Parameter.getLogFile());
				logFile = "/uploads/" + new File(logZip).getName();
				request.setAttribute("logFile", logFile);
				request.getRequestDispatcher("/query3.jsp").forward(request, response);
				break;

			case "DoQueryM":
				if (inputOnts.length() < 1 && !uploadedInputFiles.equals(""))
					inputOnts = uploadedInputFiles;
				if (mergedOnt.length() < 1 && !uploadedMergeFile.equals(""))
					mergedOnt = uploadedMergeFile;
				String resM = QueryExcecute.RunQuery(mergedOnt, queryStringM);
				request.setAttribute("uploadedmapFiles", mapOnts);
				request.setAttribute("uploadedMergeFile", mergedOnt);
				request.setAttribute("uploadedInputFiles", inputOnts);
				request.setAttribute("ListOntsQ", ListOntsQ);
				request.setAttribute("resultQueryM", resM);
				request.setAttribute("resultQueryO", resultQueryO);
				request.setAttribute("queryStringM", queryStringM);
				request.setAttribute("queryStringO", queryStringO);
				logZip = Zipper.zipFiles(Parameter.getLogFile());
				logFile = "/uploads/" + new File(logZip).getName();
				request.setAttribute("logFile", logFile);
				request.getRequestDispatcher("/query2.jsp").forward(request, response);
				break;

			case "DoQueryO":
				if (inputOnts.length() < 1 && !uploadedInputFiles.equals(""))
					inputOnts = uploadedInputFiles;
				if (mergedOnt.length() < 1 && !uploadedMergeFile.equals(""))
					mergedOnt = uploadedMergeFile;
				String[] fileName = inputOnts.split(";");
				int ontId = Integer.parseInt(SelOntQ);
				String ontAddress = fileName[ontId];
				request.setAttribute("SelOnt" + ontId, "Selected");
				String resO = QueryExcecute.RunQuery(ontAddress, queryStringO);
				request.setAttribute("uploadedmapFiles", mapOnts);
				request.setAttribute("uploadedMergeFile", mergedOnt);
				request.setAttribute("uploadedInputFiles", inputOnts);
				request.setAttribute("ListOntsQ", ListOntsQ);
				request.setAttribute("resultQueryO", resO);
				request.setAttribute("resultQueryM", resultQueryM);
				request.setAttribute("queryStringO", queryStringO);
				request.setAttribute("queryStringM", queryStringM);

				logZip = Zipper.zipFiles(Parameter.getLogFile());
				logFile = "/uploads/" + new File(logZip).getName();
				request.setAttribute("logFile", logFile);
				request.getRequestDispatcher("/query2.jsp").forward(request, response);
				break;
			///////////////////// End-QUERY**************************
			}

		} catch (Exception ex) {
			clear();
			String errorMsg = "<div	style=\"height:50px; background-color:#f9dbdb; font-weight:bold; color:red;\">	<span style=\"margin:150px;\">An error happened, Please try it again with the correct input files</span> <br><span style=\"margin:150px;\"> Error details: "
					+ ex + "</span> </div>";
			request.setAttribute("error", errorMsg);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			ex.printStackTrace();
		}
	}

	private void clear() {
		resMacth = "";
		inputOnts = new String();
		mapOnts = new String();
		mergedOnt = new String();

	}

	/**
	 * Validates Google reCAPTCHA V2 or Invisible reCAPTCHA.
	 * 
	 * @param secretKey
	 *            Secret key (key given for communication between your site and
	 *            Google)
	 * @param response
	 *            reCAPTCHA response from client side. (g-recaptcha-response)
	 * @return true if validation successful, false otherwise.
	 */
	public synchronized boolean isCaptchaValid(String response) {
		if (response.isEmpty())
			return false;
		try {
			String url = "https://www.google.com/recaptcha/api/siteverify?" + "secret=" + secretKey + "&response="
					+ response;
			InputStream res = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(res, Charset.forName("UTF-8")));

			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			String jsonText = sb.toString();
			res.close();

			JSONObject json = new JSONObject(jsonText);
			// To decide based on the score:
			System.out.println(json.get("score"));
			return json.getBoolean("success");
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return false;
	}
}
