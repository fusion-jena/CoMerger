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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.owl.explanation.impl.rootderived.CompleteRootDerivedReasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import com.clarkparsia.owlapi.explanation.BlackBoxExplanation;
import com.clarkparsia.owlapi.explanation.HSTExplanationGenerator;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.general.HSave;
import fusion.comerger.algorithm.merger.holisticMerge.general.SaveTxt;
import fusion.comerger.algorithm.merger.holisticMerge.general.Zipper;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.model.HModel;
import fusion.comerger.servlets.MatchingProcess;
import fusion.comerger.servlets.MergingProcess;

public class ConsistencyProcess {

	public static void main(String[] args) throws Exception {
		String directory = "C:\\YOUR_LOCAL_FOLDER\\";
		String ontList = "C:\\YOUR_LOCAL_FOLDER\\cmt.owl";
		ontList = ontList + ";" + "C:\\YOUR_LOCAL_FOLDER\\conference.owl";
		ontList = ontList + ";" + "C:\\YOUR_LOCAL_FOLDER\\confOf.owl";
		String matchType = "Jacard"; // "SeeCOnt";
		String mappingFile = MatchingProcess.CreateMap(ontList, "1", "1", directory, matchType);
		String[][] Rules = new String[8][2];
		Rules[0][0] = "ClassCheck";
		Rules[1][0] = "RealtionCheck";
		Rules[2][0] = "InstanceCheck";
		Rules[3][0] = "CommentCheck";
		Rules[4][0] = "OverlapCheck";
		Rules[5][0] = "PropertyCheck";
		Rules[6][0] = "CycleCheck";
		Rules[7][0] = "ConnectCheck";
		for (int i = 0; i < 8; i++)
			Rules[i][1] = "off";
		Rules[7][1] = "on";
		Rules[6][1] = "on";
		String userItem = "ClassCheck,CycleCheck";
		String[] EvalDim = new String[10];
		for (int i = 0; i < 10; i++)
			EvalDim[i] = "on";
		HModel ontM = MergingProcess.DoMerge(ontList, mappingFile, directory, userItem, "equal", "RDF/XML");

		String param = "root,5";
		// Run
		ontM = DoConsistencyCheck(ontM, param);

		String[] res = ontM.getConsResult();
		for (int i = 0; i < res.length; i++) {
			System.out.println(res[i]);
		}
		System.out.println("done!");

	}

	public static HModel DoConsistencyCheck(HModel ontM, String userConsParam)
			throws OWLOntologyCreationException, IOException, OWLOntologyStorageException {
		long startTimecc = System.currentTimeMillis();

		// User enters two parameters, parameter[0]=decision on processing all
		// unsatisfiable classes or root classes, parameter[1]= number of
		// explanation require for reasoner
		ErrornousAxioms tempErrAx = new ErrornousAxioms();
		ArrayList<ErrornousAxioms> allErrAx = new ArrayList<ErrornousAxioms>();
		// The result of consistency is stored as a string array and will set
		// into the merged Model. Later, the merge model can present the result
		// in the respective JSP files.
		String[] res = new String[20];
		String[] param = userConsParam.split(",");
		String rootAll = param[0];
		int maxExpl = Integer.parseInt(param[1]);

		File fileM = new File(ontM.getOntName());
		OWLOntologyManager ontManager = OWLManager.createOWLOntologyManager();
		OWLOntology ont = ontManager.loadOntologyFromOntologyDocument(fileM);

		long startTime = System.currentTimeMillis();
		// run an reasoner
		res[0] = "Pellet"; // name of reasnoer
		res[1] = "Pellet is one of promising reasoner."; // description
		OWLReasonerFactory reasonerFactory = new PelletReasonerFactory();
		Configuration configuration = new Configuration();
		configuration.throwInconsistentOntologyException = false;
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ont, configuration);

		System.out.println("finished creating reasoner");
		System.out.println("start to test reasoner.isConsistent()");
		if (reasoner.isConsistent()) {
			// an ontology can be consistent, but have unsatisfiable classes.
			System.out.println("start to test getUnsatisfiableClasses");
			Set<OWLClass> rs = reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom();
			if (rs.size() > 0) {
				// means: an ontology is consistent but unsatisfiable!
				res[2] = "<span style=\"font-weight: bold; color: red; width: 100%;\"> FAILED </span>";
				res[3] = "Your ontology is consistent but unsatisfiable!";
				StatisticTest.result.put("satisfiability", "FAILED");
				System.out.println("Failed. start to get unsatisfibale classes");
				Set<OWLClass> unClassList = rs;
				int ns = unClassList.size();
				res[4] = Integer.toString(ns);
				System.out.println(
						"The ontology FAILED satisfiability test with Pellet reasoner. \n Unsatisfiable classes detected: "
								+ ns);
				StatisticTest.result.put("unsatisfiable_class", String.valueOf(ns));
				Iterator<OWLClass> aList = unClassList.iterator();
				String list = "";
				while (aList.hasNext()) {
					String s = aList.next().toString();
					list = list + "  -> " + s.substring(1, s.length() - 1) + "<br>";
				}
				res[5] = "The list of unsatisfiable classes: <br>" + list;

				System.out.println("Create BlackBoxExplanation to find roots.....");
				BlackBoxExplanation bb = new BlackBoxExplanation(ont, reasonerFactory, reasoner);
				HSTExplanationGenerator multExplanator = new HSTExplanationGenerator(bb);

				if (rootAll.equals("root")) {
					System.out.println("Strat to find roots.....");
					CompleteRootDerivedReasoner rdr = new CompleteRootDerivedReasoner(ontManager, reasoner,
							reasonerFactory);
					int numRoot = 0;
					String nameRoot = "";
					Set<OWLClass> clsSet = rdr.getRootUnsatisfiableClasses();
					System.out.println("Strat to get explanastions for roots.....");
					Iterator<OWLClass> clsList = clsSet.iterator();
					while (clsList.hasNext()) {
						OWLClass cls = clsList.next();
						Set<Set<OWLAxiom>> exSet = multExplanator.getExplanations(cls, maxExpl);
						tempErrAx = new ErrornousAxioms();
						tempErrAx.setID(numRoot);
						tempErrAx.setErrorAxioms(exSet);
						tempErrAx.setErrorClass(cls);
						allErrAx.add(tempErrAx);
						numRoot++;
						nameRoot = nameRoot + "  -> " + cls.toString().substring(1, cls.toString().length() - 1)
								+ "<br>";

					}
					System.out.println("Root: " + numRoot);
					res[6] = Integer.toString(numRoot);
					res[7] = "The root classes, where are the main caused for inconsistencies are: <br>" + nameRoot;
					StatisticTest.result.put("root_unsatisfiable_class", String.valueOf(numRoot));

				} else { // calculates all unsatisfiable concepts
					Iterator<OWLClass> clsList = unClassList.iterator();
					int id = 0;
					while (clsList.hasNext()) {
						OWLClass cls = clsList.next();
						Set<Set<OWLAxiom>> exSet = multExplanator.getExplanations(cls, maxExpl);
						tempErrAx = new ErrornousAxioms();
						tempErrAx.setID(id);
						tempErrAx.setErrorAxioms(exSet);
						tempErrAx.setErrorClass(cls);
						allErrAx.add(tempErrAx);
						id++;
					}

					res[6] = "-";
					res[7] = "You did not select the root calculation.";
				}
				double[] jSize = getJustSize(allErrAx);
				System.out.println(" J size: " + jSize[0] + "\t axioms size: " + jSize[1]);
				res[8] = Double.toString(jSize[0]);
				res[9] = "A justification is a minimal subset of an ontology that causes it to be inconsistent.";
				res[10] = Double.toString(jSize[1]);
				res[11] = "Total number of axioms which caused errors";

				StatisticTest.result.put("justification", String.valueOf(jSize[0]));
				StatisticTest.result.put("conflicting_axioms", String.valueOf(jSize[1]));

				long stopTimeReasoner = System.currentTimeMillis();
				long elapsedTimeReasoner = stopTimeReasoner - startTime;
				System.out.println(" Reasoner time: " + elapsedTimeReasoner);

				// Rank the erroneous axioms based on the Subjective Logic
				// theory.
				System.out.println("Start to rank....");
				RankerSL sr = new RankerSL();
				allErrAx = sr.rankAxiom(allErrAx, jSize, ontM);
				long stopTimeRanker = System.currentTimeMillis();
				long elapsedTimeRanker = stopTimeRanker - stopTimeReasoner;
				System.out.println(" Ranker time: " + elapsedTimeRanker);
				ontM.setErrorAxioms(allErrAx);

				// Find a revised plan for erroneous axioms
				String plan = PlanGenerator.RunPlan(ontM, allErrAx);
				long stopTimePlan = System.currentTimeMillis();
				long elapsedTimePlan = stopTimePlan - stopTimeRanker;
				System.out.println(" Reasoner time: " + elapsedTimeReasoner + "\n Ranker time: " + elapsedTimeRanker
						+ "\n Plan time;" + elapsedTimePlan);

				res[12] = Long.toString(elapsedTimeReasoner);
				res[13] = Long.toString(elapsedTimeRanker);
				res[14] = Long.toString(elapsedTimePlan);

				StatisticTest.result.put("reasoner_time", String.valueOf(elapsedTimeReasoner));
				StatisticTest.result.put("ranker_time", String.valueOf(elapsedTimeRanker));
				StatisticTest.result.put("plan_time", String.valueOf(elapsedTimePlan));

				res[15] = "<span style=\"font-weight: bold; color: red;\">You need to revise below axioms. </span> <br> The most untrustable axioms show on the top. <br>";
				res[16] = plan;
			} else {
				res[2] = "<span style=\"font-weight: bold; color: green; width: 100%;\"> PASSED </span>";
				res[3] = "Your ontology is consistent and satisfiable!";
				for (int j = 4; j < res.length; j++)
					res[4] = "-";
				res[5] = "There is no unsatisfiable classes.";
				res[7] = "There is no root unsatisfiable classes.";
				res[15] = "Your ontology does not need any more revise edition.";
				res[16] = "  ";
				StatisticTest.result.put("satisfiability", "PASSED");
			}
		} else {
			/*
			 * the ontology is inconsistent, so with normal reasoner we cannot
			 * find its unsatisfiable classes
			 */
			// System.out.println("calling new function");
			GetInconsistency.run(ontM);

			res[2] = "<span style=\"font-weight: bold; color: red;   width: 100%;\"> FAILED </span>";
			res[3] = "The ontology FAILED the consistency test, but it does not have any unsatisfiable classes! \n please review the Axioms or debug using Protege.";
			res[4] = "-";
			res[6] = "-";
			res[5] = "The ontology is inconsistent, but it does not have any unsatisfiable classes!";
			res[7] = "There is no unsatisfiable root!";
			res[12] = "0";
			res[13] = "0";
			res[14] = "0";
			res[15] = "There is no revise plan. ";
			res[16] = "  ";
			StatisticTest.result.put("satisfiability", "FAILED-NotUnsatisfiable");
		}
		reasoner.dispose();

		ontM.setConsistencyResultTxt(SaveTxt.ConsistencyResultToTxt(ontM, res));

		// Save zip ontology
		if (ontM.getOntZipName().length() < 1) {
			HSave hs = new HSave();
			ontM = hs.run(ontM, ontM.getMergeOutputType());

			String MergedOntZip = Zipper.zipFiles(ontM.getOntName());
			ontM.setOntZipName(MergedOntZip);
		}

		ontM.setConsResult(res);

		long stopTimecc = System.currentTimeMillis();
		long elapsedTimecc = stopTimecc - startTimecc;
		MyLogging.log(Level.INFO, "*** done! Congratulation. Consistency test has been done successfully. Total time  "
				+ elapsedTimecc + " ms. \n");

		return ontM;
	}

	private static double[] getJustSize(ArrayList<ErrornousAxioms> allErrAx) {
		double[] res = new double[2];
		double axSize = 0.0;
		for (int i = 0; i < allErrAx.size(); i++) {
			ErrornousAxioms ex = allErrAx.get(i);
			Set<Set<OWLAxiom>> x = ex.getErrorAxioms();
			res[0] = res[0] + x.size();
			Iterator<Set<OWLAxiom>> iter = x.iterator();
			while (iter.hasNext()) {
				axSize = axSize + iter.next().size();
			}
		}
		res[1] = axSize;
		return res;
	}

	public static HModel DoReviseConsistency(HModel ontM, String userPlan, String userConsParam)
			throws OWLOntologyCreationException, IOException, OWLOntologyStorageException {
		long startTime = System.currentTimeMillis();

		// This function gets the user selected plan, and applies them on the
		// merged ontolog. At the end, again the consistency checker function
		// will run and the result will be presented to the user.
		String[] res = new String[10];
		ArrayList<ErrornousAxioms> errAx = ontM.getErrorAxioms();
		OWLOntologyManager manager = ontM.getManager();
		OWLOntology ont = ontM.getOwlModel();

		String[] plan = userPlan.split(",");

		for (int k = 0; k < plan.length; k++) {
			String axiomID = plan[k];
			OWLAxiom ax = findAxiom(axiomID, errAx);
			if (ax != null) {
				String toDo = plan[k + 1];
				if (toDo.startsWith("rewrite")) {
					// do rewrite
					String reWriteText = plan[k + 2];
					OWLAxiom newAxiom = ontM.getSuggestedAxioms().get(reWriteText);
					if (newAxiom != null)
						manager.addAxiom(ont, newAxiom);
					manager.removeAxiom(ont, ax);

				} else {
					// do delete
					manager.removeAxiom(ont, ax);
				}

			}
			k = k + 2;
		}

		ontM.SetOwlModel(ont);
		ontM.SetManager(manager);

		// 5 -- Save
		HSave hs = new HSave();
		ontM = hs.run(ontM, "RDF/XML");
		String MergedOntZip = Zipper.zipFiles(ontM.getOntName());
		String[] tempRes = ontM.getEvalResult();
		// if (tempRes == null) {
		// tempRes = new String[1][1];
		// }
		// tempRes[0][0] = MergedOntZip;
		ontM.setEvalResult(tempRes);
		ontM.setOntZipName(MergedOntZip);

		res[0] = "<div style=\"background-color:#f7f7f7;\" ><span style=\"font-weight: bold;\"><br>   &nbsp; Your revised plan has been applied, and the consistency test is checked again. See the result of consistency:<br> <br></span></div>";
		ontM.setReviseResult(res);
		ontM = DoConsistencyCheck(ontM, userConsParam);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"*** done! Congratulation. Revising the consistency issues has been done automatically by the system. Total time  "
						+ elapsedTime + " ms. \n");
		return ontM;
	}

	private static OWLAxiom findAxiom(String axiomID, ArrayList<ErrornousAxioms> errAx) {
		for (int i = 0; i < errAx.size(); i++) {
			ArrayList<ArrayList<Opinion>> axx = errAx.get(i).getRankedAxioms();
			for (int j = 0; j < axx.size(); j++) {
				ArrayList<Opinion> arryO = axx.get(j);
				for (int k = 0; k < arryO.size(); k++) {
					Opinion o = arryO.get(k);
					if (o.getAxiomID().equals(axiomID)) {
						return o.getAxiom();
					}
				}
			}
		}
		return null;
	}

}
