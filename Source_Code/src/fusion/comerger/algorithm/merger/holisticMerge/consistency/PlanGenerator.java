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

/**
* Author: Samira Babalou<br>
* email: samira[dot]babalou[at]uni[dash][dot]jena[dot]de
* Heinz-Nixdorf Chair for Distributed Information Systems<br>
* Institute for Computer Science, Friedrich Schiller University Jena, Germany<br>
* Date: 17/12/2019
*/
/**
 * CoMerger: Holistic Multiple Ontology Merger.
 * Consistency checker sub package based on the Subjective Logic theory
 * @author Samira Babalou (samira[dot]babalou[at]uni_jean[dot]de)
 */

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import fusion.comerger.algorithm.merger.model.HModel;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectUnionOfImpl;

public class PlanGenerator {

	public static void main(String[] args) throws OWLOntologyCreationException, IOException {

	}

	public static String RunPlan(HModel ontM, ArrayList<ErrornousAxioms> allErrAx) {
		String plan = "";
		try {
			String group = "";
			int i = 0;
			Map<String, OWLAxiom> SuggestAx = new HashMap<String, OWLAxiom>();
			String batchPlan = "";

			for (int index = 0; index < allErrAx.size(); index++) {
				String rows = "";
				ErrornousAxioms currentGroup = allErrAx.get(index);

				String clss = currentGroup.getErrorClass().toString();
				clss = clss.replace("<", " [");
				clss = clss.replace(">", "] ");
				String classRepresentation = "<tr><td></td><td><span style=\"font-weight: bold;\">For Class: " + clss
						+ "</span></td></tr>";

				ArrayList<ArrayList<Opinion>> rankedList = currentGroup.getRankedAxioms();
				rankedList = doOrderAxiom(rankedList);
				for (int k = 0; k < rankedList.size(); k++) {
					String tdInfo = "";
					ArrayList<Opinion> ax = rankedList.get(k);
					for (int m = 0; m < ax.size(); m++) {
						Opinion axx = ax.get(m);
						OWLAxiom myAxiom = axx.getAxiom();
						DecimalFormat df = new DecimalFormat("#.###");
						double norm = Double.valueOf(df.format(axx.getTrust()));
						String rank = Double.toString(norm);
						String axioms = "";
						String a = myAxiom.toString();
						a = a.replace("<", " [");
						a = a.replace(">", "] ");
						String check = "";
						String rewritedAxiomStr = "";
						String repair = "";
						OWLAxiom rewritedAxiom = null;
						if (m == 0) { // 1st element is the min and should
										// revise
							check = "checked=\"checked\"";
							rewritedAxiom = suggestRewrite(myAxiom, ontM);
							if (rewritedAxiom != null) {
								rewritedAxiomStr = rewritedAxiom.toString();
								rewritedAxiomStr = rewritedAxiomStr.replace("<", " [");
								rewritedAxiomStr = rewritedAxiomStr.replace(">", "] ");
								SuggestAx.put(rewritedAxiomStr, myAxiom);
							}

							if (rewritedAxiom != null) {
								repair = "<input type=\"radio\" name=\"offer" + i + "\" value=\"del" + i + "\" >Delete "
										+ "<input type=\"radio\" name=\"offer" + i + "\" value=\"rewrite" + i
										+ "\" checked>Rewrite <input type=\"text\" name=\"rewriteText" + i
										+ "\" value=\"" + rewritedAxiomStr + "\">";

								// save info for batch file
								String p = "rewrite" + i;
								batchPlan = p + "," + axx.getAxiomID() + "," + rewritedAxiom.toString() + ","
										+ batchPlan;

							} else {
								repair = "<input type=\"radio\" name=\"offer" + i + "\" value=\"del" + i
										+ "\" checked>Delete " + "<input type=\"radio\" name=\"offer" + i
										+ "\" value=\"rewrite" + i
										+ "\">Rewrite <input type=\"text\" name=\"rewriteText" + i + "\" value=\"\">";

								// save info for batch file
								String p = "del" + i;
								batchPlan = p + "," + axx.getAxiomID() + "," + "" + "," + batchPlan;
							}

						} else {// without checked for radio buttons
							repair = "<input type=\"radio\" name=\"offer" + i + "\" value=\"del" + i + "\" >Delete "
									+ "<input type=\"radio\" name=\"offer" + i + "\" value=\"rewrite" + i
									+ "\">Rewrite <input type=\"text\" name=\"rewriteText" + i + "\" value=\"\">";
						}
						axioms = "<input type=\"checkbox\" name=\"ch1\" value=\"" + axx.getAxiomID() + "\" " + check
								+ ">" + a;
						i++;
						tdInfo = tdInfo + "<tr><td style=\"width:99%;\">" + axioms + "</td><td style=\"width:5%;\">"
								+ rank + "</td><td style=\"width:10%;\">" + repair + "</td></tr>";
					}
					rows = rows + "<tr><td><input type=\"checkbox\" name=\"chAll\" value=\"chAll\" checked></td><td>"
							+ "<table style=\"border: 1px solid #BEC3EF;\">" + tdInfo + "</table></td></tr>";
				}
				group = group + classRepresentation + rows;
			}
			String row1 = "<tr><td></td><td><table style=\"border: 1px solid #BEC3EF;\"><tr><td>Axiom </td>"
					+ "<td>Rank</td><td>Repair</td></tr></table></td></tr>";
			plan = "<table style=\"border: 1px solid #BEC3EF; width:99%\">" + row1 + group + "</table>";

			ontM.setSuggestedAxioms(SuggestAx);
			// it will be used for local batch file
			ontM.setSuggestedPlan(batchPlan);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return plan;
	}

	private static ArrayList<ArrayList<Opinion>> doOrderAxiom(ArrayList<ArrayList<Opinion>> axList) {
		ArrayList<ArrayList<Opinion>> res = new ArrayList<ArrayList<Opinion>>();

		for (int k = 0; k < axList.size(); k++) {
			ArrayList<Opinion> list = axList.get(k);
			for (int m = 0; m < list.size(); m++) {
				Opinion temp;
				for (int x = 0; x < list.size(); x++) {
					for (int i = 0; i < list.size() - x - 1; i++) {
						if (compareTo(list.get(i), list.get(i + 1)) > 0) {
							temp = list.get(i);
							list.set(i, list.get(i + 1));
							list.set(i + 1, temp);
						}
					}
				}

			}

			res.add(list);
		}
		return res;
	}

	public static int compareTo(Opinion o1, Opinion o2) {
		int res = 0;
		if (o1.getNormTrust() < o2.getNormTrust()) {
			res = -1;
		}
		if (o1.getNormTrust() > o2.getNormTrust()) {
			res = 1;
		}
		return res;
	}

	private static OWLAxiom suggestRewrite(OWLAxiom ax, HModel ontM) {
		OWLAxiom newAxiom = null;
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		String type = ax.getAxiomType().toString();
		switch (type) {
		case "SubClassOf":
			break;

		case "DisjointClasses":
			break;

		case "EquivalentClasses":
			ArrayList<OWLClass> clList = new ArrayList<OWLClass>();

			for (OWLClassExpression cls : ((OWLEquivalentClassesAxiom) ax).getClassExpressions()) {
				if (cls instanceof OWLClassImpl) {
					clList.add(cls.asOWLClass());

				} else if (cls instanceof OWLObjectUnionOfImpl) {
					newAxiom = factory.getOWLSubClassOfAxiom(clList.get(0), cls);
					return newAxiom;

				} else if (cls instanceof OWLObjectSomeValuesFrom) {
					newAxiom = factory.getOWLSubClassOfAxiom(clList.get(0), cls);
					return newAxiom;

				} else if (cls instanceof OWLObjectIntersectionOf) {
					newAxiom = factory.getOWLSubClassOfAxiom(clList.get(0), cls);
					return newAxiom;
				} else {
					System.out.println("Unprocessed axiom in PlanGenaration.java: " + ax);
				}
			}
			break;
		}

		return newAxiom;
	}

}
