package fusion.oapt.algorithm.merger.holisticMerge.consistency;
/**
 * CoMerger: Holistic Multiple Ontology Merger.
 * Consistency checker sub package based on the Subjective Logic theory.
 * @author Samira Babalou (samira[dot]babalou[at]uni_jean[dot]de)
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

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
import com.hp.hpl.jena.rdf.model.Model;

import fusion.oapt.MatchingProcess;
import fusion.oapt.MergingProcess;
import fusion.oapt.algorithm.merger.holisticMerge.HolisticMerger;
import fusion.oapt.algorithm.merger.holisticMerge.general.HSave;
import fusion.oapt.algorithm.merger.holisticMerge.general.SaveTxt;
import fusion.oapt.algorithm.merger.model.HModel;

public class ConsistencyProcess {

		public static HModel DoConsistencyCheck(HModel ontM, String userConsParam)
			throws OWLOntologyCreationException, IOException, OWLOntologyStorageException {
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
		res[1] = "Pellet is one of promising reasoner"; // description
		OWLReasonerFactory reasonerFactory = new PelletReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ont);
		if (reasoner.isConsistent()) {
			// an ontology can be consistent, but have unsatisfiable classes.
			if (reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom().size() > 0) {
				//It means: an ontology is consistent but unsatisfiable!
				res[2] = "<span style=\"font-weight: bold; color: red; width: 100%;\"> FAILED </span>";
				res[3] = "Your ontology is consistent but unsatisfiable!";
				Set<OWLClass> unClassList = reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom();
				int ns = unClassList.size();
				res[4] = Integer.toString(ns);
				System.out.println(
						"The ontology FAILED satisfiability test with Pellet reasoner. \n Unsatisfiable classes detected: "
								+ ns);

				Iterator<OWLClass> aList = unClassList.iterator();
				String list = "";
				while (aList.hasNext()) {
					String s = aList.next().toString();
					list = list + "  -> " + s.substring(1, s.length() - 1) + "<br>";
				}
				res[5] = "The list of unsatisfiable classes: <br>" + list;

				BlackBoxExplanation bb = new BlackBoxExplanation(ont, reasonerFactory, reasoner);
				HSTExplanationGenerator multExplanator = new HSTExplanationGenerator(bb);

				if (rootAll.equals("root")) {
					CompleteRootDerivedReasoner rdr = new CompleteRootDerivedReasoner(ontManager, reasoner,
							reasonerFactory);
					int numRoot = 0;
					String nameRoot = "";
					Set<OWLClass> clsSet = rdr.getRootUnsatisfiableClasses();
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
				res[9] = "Justification is a group of axioms...";
				res[10] = Double.toString(jSize[1]);
				res[11] = "Total number of axioms which caused errors";
				long stopTimeReasoner = System.currentTimeMillis();
				long elapsedTimeReasoner = stopTimeReasoner - startTime;
				System.out.println(" Reasoner time: " + elapsedTimeReasoner);

				// Rank the erroneous axioms based on the Subjective Logic theory.
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
			}
		} else {
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
		}
		reasoner.dispose();

		SaveTxt st = new SaveTxt();
		ontM.setConsistencyResultTxt(st.ConsistencyResultToTxt(ontM, res));

		// Save 
		if (ontM.getOntZipName().length() < 1) {
			HSave hs = new HSave();
			ontM = hs.run(ontM, "RDF/XML"); 
			String MergedOntZip = HolisticMerger.zipFiles(ontM.getOntName());
			ontM.setOntZipName(MergedOntZip);
		}

		ontM.setConsResult(res);
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
		String MergedOntZip = HolisticMerger.zipFiles(ontM.getOntName());
		String[][] tempRes = ontM.getEvalResult();
		if (tempRes == null) {
			tempRes = new String[1][1];
		}
		tempRes[0][0] = MergedOntZip;
		ontM.setEvalResult(tempRes);
		ontM.setOntZipName(MergedOntZip);

		res[0] = "<div style=\"background-color:#f7f7f7;\" ><span style=\"font-weight: bold;\"><br>   &nbsp; Your revised plan has been applied, and the consistency test is checked again. See the result of consistency:<br> <br></span></div>";
		ontM.setReviseResult(res);
		ontM = DoConsistencyCheck(ontM, userConsParam);
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
