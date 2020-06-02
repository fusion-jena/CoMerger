package fusion.comerger.algorithm.merger.holisticMerge.evaluator;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.jena.vocabulary.OWL;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;

import fusion.comerger.algorithm.merger.holisticMerge.general.ShareMergeFunction;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedDpro;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedObj;
import fusion.comerger.algorithm.merger.model.HModel;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;

public class HEvaluator {
	public static String tick = "<img src=\"/layout/images/tick.jpg\">";
	public static String cross = "<img src=\"/layout/images/cross.jpg\">";
	String msgCorrectness = "Repair it!";

	public String[] run(HModel ontM, String evalDim) {
		long startTime = System.currentTimeMillis();
		HashMap<String, String> eval = new HashMap<String, String>();
		ontM.setEvalHashResult(eval);
		String[] gui = new String[14];
		HashMap<String, String> dimensionLabel = new HashMap<String, String>();
		String[] tempRes = null;
		String[] tempResAspect = new String[40];
		String[] dim = evalDim.split(",");
		String lable = "";
		for (int i = 0; i < dim.length; i++) {
			switch (dim[i]) {
			case "CompletenessCheck":
				tempResAspect = new String[40];
				System.out.println("Start Evaluation for ClassPreservationEval");
				tempRes = ClassPreservationEval(ontM);
				tempResAspect[0] = tempRes[0];
				tempResAspect[1] = tempRes[1];

				System.out.println("Start Evaluation for PropertyPreservationEval");
				tempRes = PropertyPreservationEval(ontM);
				tempResAspect[2] = tempRes[0];
				tempResAspect[3] = tempRes[1];

				System.out.println("Start Evaluation for InstancePreservationEval");
				tempRes = InstancePreservationEval(ontM);
				tempResAspect[4] = tempRes[0];
				tempResAspect[5] = tempRes[1];

				System.out.println("Start Evaluation for CorrespondencePreservationEval");
				tempRes = CorrespondencePreservationEval(ontM);
				tempResAspect[6] = tempRes[0];
				tempResAspect[7] = tempRes[1];

				System.out.println("Start Evaluation for CorrespondencePropertyPreservationEval");
				tempRes = CorrespondencePropertyPreservationEval(ontM);
				tempResAspect[8] = tempRes[0];
				tempResAspect[9] = tempRes[1];

				System.out.println("Start Evaluation for ValuePreservationEval");
				tempRes = ValuePreservationEval(ontM);
				tempResAspect[10] = tempRes[0];
				tempResAspect[11] = tempRes[1];

				System.out.println("Start Evaluation for StructurePreservationEval");
				tempRes = StructurePreservationEval(ontM);
				tempResAspect[12] = tempRes[0];
				tempResAspect[13] = tempRes[1];

				gui[0] = GuiOutput.createCompletenessGui(tempResAspect);
				lable = setEvalLabel(tempResAspect);
				dimensionLabel.put("CompletenessCheck", lable);
				break;

			case "MinimalityCheck":
				tempResAspect = new String[40];
				tempRes = ClassRedundancyEval(ontM);
				tempResAspect[0] = tempRes[0];
				tempResAspect[1] = tempRes[1];

				tempRes = PropertyRedundancyEval(ontM);
				tempResAspect[2] = tempRes[0];
				tempResAspect[3] = tempRes[1];

				tempRes = InstanceRedundancyEval(ontM);
				tempResAspect[4] = tempRes[0];
				tempResAspect[5] = tempRes[1];

				tempRes = ExtraneousEntityProhibitionEval(ontM);
				tempResAspect[6] = tempRes[0];
				tempResAspect[7] = tempRes[1];

				gui[1] = GuiOutput.createMinimalityGui(tempResAspect);
				lable = setEvalLabel(tempResAspect);
				dimensionLabel.put("MinimalityCheck", lable);
				break;

			case "DeductionCheck":
				tempResAspect = new String[40];
				tempRes = EntailmentSatisfactionEval(ontM);
				tempResAspect[0] = tempRes[0];
				tempResAspect[1] = tempRes[1];

				gui[2] = GuiOutput.createDeductionGui(tempResAspect);
				lable = setEvalLabel(tempResAspect);
				dimensionLabel.put("DeductionCheck", lable);
				break;

			case "ConstraintCheck":
				tempResAspect = new String[40];
				tempRes = ConstraintEval.OneTypeRestrictionEval(ontM);
				tempResAspect[0] = tempRes[0];
				tempResAspect[1] = tempRes[1];

				tempRes = ConstraintEval.ValueConstraintEval(ontM);
				tempResAspect[2] = tempRes[0];
				tempResAspect[3] = tempRes[1];

				tempRes = ConstraintEval.DomainRangeMinimalityEval(ontM);
				tempResAspect[4] = tempRes[0];
				tempResAspect[5] = tempRes[1];

				gui[3] = GuiOutput.createConstraintGui(tempResAspect);
				lable = setEvalLabel(tempResAspect);
				dimensionLabel.put("ConstraintCheck", lable);
				break;

			case "AcyclicityCheck":
				tempResAspect = new String[40];
				System.out.println("Start Evaluation for ClassAcyclicityEval");
				tempRes = ClassAcyclicityEval(ontM);
				tempResAspect[0] = tempRes[0];
				tempResAspect[1] = tempRes[1];

				System.out.println("Start Evaluation for PropertyAcyclicityEval");
				tempRes = PropertyAcyclicityEval(ontM);
				tempResAspect[2] = tempRes[0];
				tempResAspect[3] = tempRes[1];

				System.out.println("Start Evaluation for InversePropertyProhibitionEval");
				tempRes = InversePropertyProhibitionEval(ontM);
				tempResAspect[4] = tempRes[0];
				tempResAspect[5] = tempRes[1];

				gui[4] = GuiOutput.createAcyclicityGui(tempResAspect);
				lable = setEvalLabel(tempResAspect);
				dimensionLabel.put("AcyclicityCheck", lable);

				break;

			case "ConnectivityCheck":
				tempResAspect = new String[40];
				System.out.println("Start Evaluation for UnconnectedClassProhibitionEval");
				tempRes = UnconnectedClassProhibitionEval(ontM);
				tempResAspect[0] = tempRes[0];
				tempResAspect[1] = tempRes[1];

				System.out.println("Start Evaluation for UnconnectedPropertyProhibitionEval");
				tempRes = UnconnectedPropertyProhibitionEval(ontM);
				tempResAspect[2] = tempRes[0];
				tempResAspect[3] = tempRes[1];

				gui[5] = GuiOutput.createConnectivityGui(tempResAspect);
				lable = setEvalLabel(tempResAspect);
				dimensionLabel.put("ConnectivityCheck", lable);

				break;
			case "UsabilityProfileCheck":
				tempResAspect = EvalUsabilityProfile(ontM);
				gui[6] = GuiOutput.createUsabilityProfileGui(tempResAspect);
				lable = setEvalLabel(tempResAspect);
				dimensionLabel.put("UsabilityProfileCheck", lable);
				break;

			case "MapperCheck":
				tempResAspect = EvalMap(ontM);
				gui[7] = GuiOutput.createMapperGui(tempResAspect);
				break;

			case "CompactnessCheck":
				System.out.println("Start Evaluation for EvalCompactness");
				tempResAspect = EvalCompactness(ontM);
				gui[8] = GuiOutput.createCompactnessGui(tempResAspect);
				break;

			case "CoverageCheck":
				System.out.println("Start Evaluation for EvalCoverage");
				tempResAspect = EvalCoverage(ontM);
				gui[9] = GuiOutput.createCoverageGui(tempResAspect);
				break;

			}

		}

		System.out.println("Start Evaluation for makesLabels");
		String lables = makesLabels(dimensionLabel);
		ontM.setEvalTotalLabel(lables);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		StatisticTest.result.put("time_eval", String.valueOf(elapsedTime));

		return gui;

	}

	private String makesLabels(HashMap<String, String> dimensionLabel) {
		String res = "";
		if (dimensionLabel.get("CompletenessCheck") != null) {
			res = dimensionLabel.get("CompletenessCheck");
		} else {
			res = "0";
		}
		if (dimensionLabel.get("MinimalityCheck") != null) {
			res = res + ";" + dimensionLabel.get("MinimalityCheck");
		} else {
			res = res + ";" + "0";
		}
		if (dimensionLabel.get("DeductionCheck") != null) {
			res = res + ";" + dimensionLabel.get("DeductionCheck");
		} else {
			res = res + ";" + "0";
		}
		if (dimensionLabel.get("ConstraintCheck") != null) {
			res = res + ";" + dimensionLabel.get("ConstraintCheck");
		} else {
			res = res + ";" + "0";
		}
		if (dimensionLabel.get("AcyclicityCheck") != null) {
			res = res + ";" + dimensionLabel.get("AcyclicityCheck");
		} else {
			res = res + ";" + "0";
		}
		if (dimensionLabel.get("ConnectivityCheck") != null) {
			res = res + ";" + dimensionLabel.get("ConnectivityCheck");
		} else {
			res = res + ";" + "0";
		}
		if (dimensionLabel.get("UsabilityProfileCheck") != null) {
			res = res + ";" + dimensionLabel.get("UsabilityProfileCheck");
		} else {
			res = res + ";" + "0";
		}
		return res;
	}

	private String setEvalLabel(String[] res) {
		String lable = "";
		int correct = 0, incorrect = 0;
		for (int i = 0; i < res.length; i += 2) {
			if (res[i] != null && res[i].contains("cross")) {
				incorrect++;
			} else if (res[i] != null && res[i].contains("tick")) {
				correct++;
			}
		}
		if (correct > 0 && incorrect == 0) {
			lable = "100"; // "VERYGOOD";
		} else if (incorrect > 0 && correct == 0) {
			lable = "20"; // "VERYBAD";
		} else if (correct > incorrect) {
			lable = "80";// "GOOD";
		} else if (incorrect > correct) {
			lable = "40";// "BAD";
		} else { // correct==incorrect or other cases
			lable = "50";// "NORMAL";
		}
		return lable;
	}

	private String[] EvalUsabilityProfile(HModel ontM) {
		String[] res = new String[40];
		// Metadata availability
		String[] temp = HMergeEvaluation.CorrectnessOntologyURI_Namespace(ontM);
		res[0] = temp[0];
		res[1] = temp[1];
		res[2] = temp[2];
		res[3] = temp[3];

		temp = HMergeEvaluation.CorrectnessOntologyDeclaration(ontM);
		res[4] = temp[0];
		res[5] = temp[1];

		temp = HMergeEvaluation.CorrectnessOntologyLicense(ontM);
		res[6] = temp[0];
		res[7] = temp[1];

		// Label uniqueness
		temp = HMergeEvaluation.LabelUniqueness(ontM);
		res[8] = temp[0];
		res[9] = temp[1];

		// Unify naming
		temp = HMergeEvaluation.UnifyNaming(ontM);
		res[10] = temp[0];
		res[11] = temp[1];

		// Entity Type declaration
		temp = HMergeEvaluation.EntityTypeDeclaration(ontM);
		res[12] = temp[0];
		res[13] = temp[1];

		return res;
	}

	private String[] EvalCoverage(HModel ontM) {
		String[] res = new String[40];
		int classSize = ontM.getOwlModel().getClassesInSignature().size();
		int proSize = ontM.getOwlModel().getObjectPropertiesInSignature().size();
		proSize = proSize + ontM.getOwlModel().getDataPropertiesInSignature().size();
		int instanceSize = ontM.getOwlModel().getIndividualsInSignature().size();

		int eqClass = findNumberOfEqCl(ontM.getEqClasses());
		int eqPro = findNumberOfEqOpro(ontM.getEqObjProperties());
		eqPro = eqPro + findNumberOfEqDpro(ontM.getEqDataProperties());

		double sum = 0;

		for (int i = 0; i < ontM.getInputClassSize().size(); i++)
			sum = sum + ontM.getInputClassSize().get(i);
		double temp = sum - eqClass;// - 1;
		if (sum > 0) {
			// temporal correctness
			if (classSize - temp == 1 || temp - classSize == 1)
				temp = classSize;
			double s = Math.round((classSize / temp) * 100.0) / 100.0;
			res[1] = String.valueOf(s);
			res[4] = "<br> ** Number of classes in input ontologies minus their equal classes is " + temp
					+ " and number of classes in the merged ontology is " + classSize;
		} else {
			res[1] = "-";
			res[4] = res[4] + "<br> ** There is no class in the input ontologies!";
		}

		sum = 0;
		for (int i = 0; i < ontM.getInputObjectSize().size(); i++)
			sum = sum + ontM.getInputObjectSize().get(i);
		for (int i = 0; i < ontM.getInputDataProSize().size(); i++)
			sum = sum + ontM.getInputDataProSize().get(i);

		temp = sum - eqPro;// - 1;
		if (sum > 0) {
			// temporal correctness
			if (proSize - temp == 1 || temp - proSize == 1)
				temp = proSize;
			double s = Math.round((proSize / temp) * 100.0) / 100.0;
			res[2] = String.valueOf(s);
			res[4] = res[4] + "<br> ** Number of properties in input ontologies minus their equal properties is " + temp
					+ " and number of classes in the merged ontology is " + proSize;
		} else {
			res[2] = "-";
			res[4] = res[4] + "<br> ** There is no property in the input ontologies!";
		}
		sum = 0;

		for (int i = 0; i < ontM.getInputInstanceSize().size(); i++)
			sum = sum + ontM.getInputInstanceSize().get(i);
		if (sum > 0) {
			double s = Math.round((instanceSize / sum) * 100.0) / 100.0;
			res[3] = String.valueOf(s);
			res[4] = res[4] + "<br> ** Number of instances in input ontologies is " + sum
					+ " and number of instances in the merged ontology is " + instanceSize;

		} else {
			res[3] = "-";
			res[4] = res[4] + "<br> ** There is no instance in the input ontologies!";
		}

		/* **************** overlap***************** */
		double overlap = (double) (eqClass) / (double) (ontM.getInputClassSizeTotal()) * 100;
		overlap = Math.round(overlap * 100.0) / 100.0;
		StatisticTest.result.put("overlap", String.valueOf(overlap));
		System.out.println("class size OS: " + ontM.getInputClassSizeTotal() + "\t property size OS: "
				+ ontM.getInputPropertySizeTotal());
		System.out.println("overlap: " + overlap);

		StatisticTest.result.put("class_coverage", String.valueOf(res[1]));
		StatisticTest.result.put("property_coverage", String.valueOf(res[2]));
		StatisticTest.result.put("instance_coverage", String.valueOf(res[3]));

		return res;
	}

	private int findNumberOfEqCl(ArrayList<HMappedClass> eqList) {
		int siz = 0;
		for (int i = 0; i < eqList.size(); i++) {
			int a = eqList.get(i).getLenClass();
			if (a != 0)
				siz = siz + (a - 1);
		}

		return siz;
	}

	private int findNumberOfEqOpro(ArrayList<HMappedObj> eqList) {
		int siz = 0;
		for (int i = 0; i < eqList.size(); i++) {
			int a = eqList.get(i).getLenObj();
			siz = siz + (a - 1);
		}

		return siz;
	}

	private int findNumberOfEqDpro(ArrayList<HMappedDpro> eqList) {
		int siz = 0;
		for (int i = 0; i < eqList.size(); i++) {
			int a = eqList.get(i).getLenDpro();
			siz = siz + (a - 1);
		}

		return siz;
	}

	private String[] EvalCompactness(HModel ontM) {
		String[] res = new String[40];
		int classSize = ontM.getOwlModel().getClassesInSignature().size();
		int propSize = ontM.getOwlModel().getObjectPropertiesInSignature().size();
		propSize = propSize + ontM.getOwlModel().getDataPropertiesInSignature().size();
		int instanceSize = ontM.getOwlModel().getIndividualsInSignature().size();
		int annoSize = ontM.getAllAnnotations().size();

		res[1] = String.valueOf(classSize);
		res[3] = String.valueOf(propSize);
		res[5] = String.valueOf(instanceSize);
		res[7] = String.valueOf(annoSize);

		StatisticTest.result.put("classSize", String.valueOf(classSize));
		StatisticTest.result.put("propSize", String.valueOf(propSize));
		StatisticTest.result.put("instanceSize", String.valueOf(instanceSize));
		StatisticTest.result.put("annoSize", String.valueOf(annoSize));

		// relative size
		// all +1 till do not face divided by 0.
		double sum = 0.0, temp = 0.0;
		for (int i = 0; i < ontM.getInputClassSize().size(); i++)
			sum = sum + ontM.getInputClassSize().get(i);
		if (classSize == 0) {
			res[9] = "0.0";
		} else {
			temp = (double) Math.round((classSize + 1) / (sum - ontM.getEqClasses().size() + 1) * 10000d) / 10000d;
			res[9] = String.valueOf(temp);
		}
		if (temp < 1.0) {
			res[10] = "<span style=\"font-weight: bold; color: red; width: 100%;\"> The number of classes in your merged ontology ("
					+ classSize + ") is less than the number of classes of all input ontologies ("
					+ (int) (sum - ontM.getEqClasses().size()) + "). </span>";
		} else if (temp > 1.0) {
			res[10] = "<span style=\"font-weight: bold; color: red; width: 100%;\"> The number of classes in your merged ontology ("
					+ classSize + ") is more than the number of classes of all input ontologies ("
					+ (int) (sum - ontM.getEqClasses().size()) + "). </span>";
		}
		sum = 0.0;
		temp = 0.0;

		for (int i = 0; i < ontM.getInputObjectSize().size(); i++)
			sum = sum + ontM.getInputObjectSize().get(i);
		for (int i = 0; i < ontM.getInputDataProSize().size(); i++)
			sum = sum + ontM.getInputDataProSize().get(i);
		if (propSize == 0) {
			res[11] = "0.0";
		} else {
			temp = (double) Math.round((propSize + 1)
					/ (sum - (ontM.getEqObjProperties().size() + ontM.getEqDataProperties().size()) + 1) * 10000d)
					/ 10000d;
			res[11] = String.valueOf(temp);
		}
		if (temp < 1.0) {
			res[12] = "<span style=\"font-weight: bold; color: red; width: 100%;\"> The number of properties in your merged ontology ("
					+ propSize + ") is less than the number of properties of all input ontologies ("
					+ (int) (sum - (ontM.getEqObjProperties().size() + ontM.getEqDataProperties().size()))
					+ "). </span>";
		} else if (temp > 1.0) {
			res[12] = "<span style=\"font-weight: bold; color: red; width: 100%;\"> The number of properties in your merged ontology ("
					+ propSize + ") is more than the number of properties of all input ontologies ("
					+ (int) (sum - (ontM.getEqObjProperties().size() + ontM.getEqDataProperties().size()))
					+ "). </span>";
		}
		sum = 0.0;
		temp = 0.0;

		for (int i = 0; i < ontM.getInputInstanceSize().size(); i++)
			sum = sum + ontM.getInputInstanceSize().get(i);
		if (instanceSize == 0) {
			res[13] = "0.0";
		} else {
			res[13] = String.valueOf((double) Math.round((instanceSize + 1) / (sum + 1) * 10000d) / 10000d);
		}
		sum = 0.0;

		// there is no equal instances and annotations

		for (int i = 0; i < ontM.getInputAnnoSize().size(); i++)
			sum = sum + ontM.getInputAnnoSize().get(i);
		if (annoSize == 0) {
			res[15] = "0.0";
		} else {
			res[15] = String.valueOf((double) Math.round((annoSize + 1) / (sum + 1) * 10000d) / 10000d);
		}
		sum = 0.0;

		// Distributed size
		OWLOntology ont = ontM.getOwlModel();
		Iterator<OWLClass> clist = ontM.getOwlModel().getClassesInSignature().iterator();
		while (clist.hasNext()) {
			OWLClass c = clist.next();
			sum = sum + c.getSubClasses(ont).size();
		}
		res[17] = String.valueOf((double) Math.round(sum / classSize * 10000d) / 10000d);
		sum = 0.0;

		Iterator<OWLObjectProperty> plist = ontM.getOwlModel().getObjectPropertiesInSignature().iterator();
		while (clist.hasNext()) {
			OWLObjectProperty p = plist.next();
			sum = sum + p.getSubProperties(ont).size();
		}
		res[19] = String.valueOf((double) Math.round(sum / propSize * 10000d) / 10000d);
		sum = 0.0;

		res[21] = String.valueOf((double) Math.round(instanceSize / classSize * 10000d) / 10000d);
		res[23] = String.valueOf((double) Math.round(annoSize / classSize * 10000d) / 10000d);

		return res;

	}

	private String[] EvalMap(HModel ontM) {
		String[] res = new String[40];
		int eqClass = ontM.getEqClasses().size();
		int eqObjPro = ontM.getEqObjProperties().size();
		int eqDataPro = ontM.getEqDataProperties().size();
		res[1] = String.valueOf(eqClass);
		res[3] = String.valueOf(eqObjPro + eqDataPro);
		res[4] = "The number of mapped object properties is " + eqObjPro
				+ " and the number of mapped data properties is " + eqDataPro + ".";
		res[5] = String.valueOf(0);
		res[9] = String.valueOf(0);
		res[11] = "1:1 Mapping";

		return res;
	}

	// ************************************************************************
	private String[] ClassPreservationEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<OWLClass> MissCl = new HashSet<OWLClass>();
		int missClasses = 0;
		double totalOiClass = 0.0;
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";

		Set<OWLClass> OmClasses = ontM.getOwlModel().getClassesInSignature();// ontM.getRefineClasses();
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Set<OWLClass> OiClass = Oi.getClassesInSignature();
			totalOiClass = totalOiClass + OiClass.size();
			Iterator<OWLClass> iterCl = OiClass.iterator();
			while (iterCl.hasNext()) {
				OWLClass c = iterCl.next();
				if (!OmClasses.contains(c) && ontM.getKeyValueEqClass().get(c) == null) {
					missClasses++;
					MissCl.add(c);
				}
			}
		}

		StatisticTest.result.put("class_preservation", String.valueOf(missClasses));
		if (missClasses > 0) {
			if (missClasses == 1) {
				res[0] = missClasses + " case " + cross;
			} else {
				res[0] = missClasses + " cases " + cross;
			}
			e1 = missClasses + " classes are not preserved!\n";
		} else {
			res[0] = tick;
			e1 = "All classes are preserved. \n";
		}

		if (missClasses > 0) {
			e2 = "These classes are not preserved:";
			res[1] = "<span style=\"color: red;\">Missing classes are:";
			Iterator<OWLClass> arrayIter = MissCl.iterator();
			while (arrayIter.hasNext()) {
				OWLClass cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.toString().replaceAll("<", "[").replaceAll(">", "]");
				e2 = e2 + "\n " + cc.toString();
			}
			res[1] = res[1] + "</span>";
			String id = "ClassCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;

		} else {
			res[1] = "<span style=\"color: green;\">There is no missing classes. </span>";
		}

		eval.put("ClassPreservation", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}
	// ************************************************************************

	private String[] PropertyPreservationEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissPro = new HashSet<String>();
		int missPro = 0;
		double totalOiPro = 0.0;
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";

		Set<OWLObjectProperty> OmPro = ontM.getOwlModel().getObjectPropertiesInSignature();// ontM.getRefineClasses();
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Set<OWLObjectProperty> OiCl = Oi.getObjectPropertiesInSignature();
			totalOiPro = totalOiPro + OiCl.size();
			Iterator<OWLObjectProperty> iterCl = OiCl.iterator();
			while (iterCl.hasNext()) {
				OWLObjectProperty c = iterCl.next();
				if (!OmPro.contains(c) && ontM.getKeyValueEqObjProperty().get(c) == null) {
					missPro++;
					MissPro.add(c.toString());
				}
			}
		}

		// for data property
		Set<OWLDataProperty> OmDaPro = ontM.getOwlModel().getDataPropertiesInSignature();
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Set<OWLDataProperty> OiCl = Oi.getDataPropertiesInSignature();
			totalOiPro = totalOiPro + OiCl.size();
			Iterator<OWLDataProperty> iterCl = OiCl.iterator();
			while (iterCl.hasNext()) {
				OWLDataProperty c = iterCl.next();
				if (!OmDaPro.contains(c) && ontM.getKeyValueEqDataPro().get(c) == null) {
					missPro++;
					MissPro.add(c.toString());
				}
			}
		}

		// for annotation property
		Set<OWLAnnotationProperty> OmAnnoPro = ontM.getOwlModel().getAnnotationPropertiesInSignature();
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Set<OWLAnnotationProperty> OiCl = Oi.getAnnotationPropertiesInSignature();
			totalOiPro = totalOiPro + OiCl.size();
			Iterator<OWLAnnotationProperty> iterCl = OiCl.iterator();
			while (iterCl.hasNext()) {
				OWLAnnotationProperty c = iterCl.next();
				if (!OmAnnoPro.contains(c)) {
					missPro++;
					MissPro.add(c.toString());
				}
			}
		}

		StatisticTest.result.put("property_preservation", String.valueOf(missPro));
		if (missPro > 0) {
			if (missPro == 1) {
				res[0] = missPro + " case " + cross;
			} else {
				res[0] = missPro + " cases " + cross;
			}
			e1 = missPro + " properties are not preserved!\n";
		} else {
			res[0] = tick;
			e1 = "All properties are preserved. \n";
		}

		if (missPro > 0) {
			e2 = "These properties are not preserved:";
			res[1] = "<span style=\"color: red;\">Missing properties are:";
			Iterator<String> arrayIter = MissPro.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replaceAll("<", "[").replaceAll(">", "]");
				e2 = e2 + "\n " + cc.toString();
			}
			res[1] = res[1] + "</span>";
			String id = "ProCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no missing property. </span>";
		}

		eval.put("PropertyPreservation", e1 + e2);
		ontM.setEvalHashResult(eval);

		return res;
	}

	// ************************************************************************
	private String[] InstancePreservationEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int missIns = 0;
		double totalOiIns = 0.0;
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";

		Set<OWLNamedIndividual> OmIndividuals = ontM.getOwlModel().getIndividualsInSignature();
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Set<OWLNamedIndividual> OiIns = Oi.getIndividualsInSignature();
			totalOiIns = totalOiIns + OiIns.size();
			Iterator<OWLNamedIndividual> iterIn = OiIns.iterator();
			while (iterIn.hasNext()) {
				OWLNamedIndividual In = iterIn.next();
				if (!OmIndividuals.contains(In)) {
					missIns++;
					MissArray.add(In.toString());

				}
			}
		}

		StatisticTest.result.put("instance_preservation", String.valueOf(missIns));
		if (missIns > 0) {
			if (missIns == 1) {
				res[0] = missIns + " case " + cross;
			} else {
				res[0] = missIns + " cases " + cross;
			}
			e1 = missIns + " instances are not preserved!\n";
		} else {
			res[0] = tick;
			e1 = "All instances are preserved. \n";
		}

		if (missIns > 0) {
			e2 = "These instances are not preserved:";
			res[1] = "<span style=\"color: red;\">Missing individual are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replaceAll("<", "[").replaceAll(">", "]");
				e2 = e2 + "\n " + cc.toString();
			}
			res[1] = res[1] + "</span>";
			String id = "InstanceCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no missing individual.</span>";
		}

		eval.put("InstancePreservation", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	// ************************************************************************
	private String[] CorrespondencePreservationEval(HModel ontM) {
		String[] res = new String[2];
		// we are sure that all correspondences already collapsed
		res[0] = "-";
		res[1] = "-";
		StatisticTest.result.put("correspondence_preservation", String.valueOf(0));
		return res;
	}

	// ************************************************************************
	private String[] CorrespondencePropertyPreservationEval(HModel ontM) {
		// because of re-writing axioms, all properties of
		// corresponding classes are already assigned to the reference classes
		String[] res = new String[2];
		res[0] = "-";
		res[1] = "-";
		StatisticTest.result.put("correspondenceProperty_preservation", String.valueOf(0));
		return res;
	}

	// ************************************************************************
	private String[] ValuePreservationEval(HModel ontM) {

		String[] res = new String[2];
		HashSet<String> conflictArray = new HashSet<String>();
		int conflictType = 0;
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";

		OWLOntology Om = ontM.getOwlModel();
		Set<OWLObjectProperty> OmPro = Om.getObjectPropertiesInSignature();
		// do it for object and datatype etc. property
		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLObjectProperty> iter3 = Oi.getObjectPropertiesInSignature().iterator();
			while (iter3.hasNext()) {
				OWLObjectProperty pOi = iter3.next();
				if (OmPro.contains(pOi) || ontM.getKeyValueEqObjProperty().get(pOi) != null) {
					// if the property itself exist in Om, then we check the
					// values of that property, whether they are exist in om or
					// not
					OWLObjectProperty pOiRef = pOi;
					if (ontM.getKeyValueEqObjProperty().get(pOi) != null)
						pOiRef = ontM.getKeyValueEqObjProperty().get(pOi);

					Set<OWLDataProperty> dpro = pOi.getDataPropertiesInSignature();
					Set<OWLDataProperty> dproRef = pOiRef.getDataPropertiesInSignature();
					if (!dpro.equals(dproRef)) {
						conflictArray.add(dpro.toString().replaceAll("<", "[").replaceAll(">", "]"));
						conflictType = conflictType++;
					}

					Set<OWLDatatype> dt = pOi.getDatatypesInSignature();
					Set<OWLDatatype> dtRef = pOiRef.getDatatypesInSignature();
					if (!dt.equals(dtRef)) {
						conflictArray.add(dt.toString().replaceAll("<", "[").replaceAll(">", "]"));
						conflictType = conflictType++;
					}
				}
				// p.getpropertyvalue
				// Searcher.values(Om.getAxioms(AxiomType.OBJECT_PROPERTY_ASSERTION),
				// p);
			}
		}

		ArrayList<HMappedDpro> OmEqDataPro = ontM.getEqDataProperties();
		// to get type of datatypeproperties in OWL?
		for (int j = 0; j < OmEqDataPro.size(); j++) {
			OWLDataProperty Dpro = OmEqDataPro.get(j).getRefDpro();
			Set<OWLDataRange> dataRangeRef = Dpro.getRanges(Om);
			// Set<OWLClassExpression> dataDomainRef = Dpro.getDomains(Om);

			Iterator<OWLDataProperty> iter = OmEqDataPro.get(j).getMappedDpro().iterator();
			while (iter.hasNext()) {
				OWLDataProperty eqDpro = iter.next();
				Set<OWLDataRange> dataRange = null;
				for (int i = 0; i < ontM.getInputOntNumber(); i++) {
					OWLOntology oi = ontM.getInputOwlOntModel().get(i);
					dataRange = eqDpro.getRanges(oi);
					if (dataRange != null && dataRange.size() > 0) {
						break;
						// it means the property is in oi, no need to search in
						// other oi
					}
				}

				// Set<OWLClassExpression> dataDomain = eqDpro.getDomains(Om);

				if (!dataRange.containsAll(dataRangeRef)) {
					// It means the type of datatypeProperty is different
					// then we only should keep the value of one of them.
					conflictArray.add(Dpro.toString().replaceAll("<", "[").replaceAll(">", "]"));
					conflictType = conflictType++;

				}

			}
		}

		StatisticTest.result.put("value_preservation", String.valueOf(conflictType));

		if (conflictType > 0) {
			if (conflictType == 1) {
				res[0] = conflictType + " case " + cross;
			} else {
				res[0] = conflictType + " cases " + cross;
			}
			e1 = conflictType + " values are not preserved!\n";
		} else {
			res[0] = tick;
			e1 = "All values are preserved. \n";
		}

		if (conflictType > 0) {
			e2 = "Conflicting datatypeProperty for the corressponding entities are:";
			res[1] = "<span style=\"color: red;\">Conflicting datatypeProperty for the corressponding entities are:";
			Iterator<String> arrayIter = conflictArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replaceAll("<", "[").replaceAll(">", "]");
				e2 = e2 + "\n " + cc.toString();
			}
			res[1] = res[1] + "</span>";
			String id = "ValueCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">All properties' value have been preserved without conflict.</span>";
		}

		eval.put("ValuePreservation", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	// ************************************************************************
	private String[] StructurePreservationEval(HModel ontM) {
		String[] res = new String[2];
		int missStr = 0;
		HashSet<String> MissArray = new HashSet<String>();
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";

		Set<OWLSubClassOfAxiom> OmSubclassAxiom = ontM.getOwlModel().getAxioms(AxiomType.SUBCLASS_OF);
		// HashMap<OWLAxiom, OWLAxiom> equalAxioms = ontM.getEqAxioms();
		// Set<OWLSubClassOfAxiom> OiAxAll = new HashSet<OWLSubClassOfAxiom>();
		// for (int i = 0; i < ontM.getInputOntNumber(); i++) {
		// OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
		// OiAxAll.addAll(Oi.getAxioms(AxiomType.SUBCLASS_OF));
		// }

		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLSubClassOfAxiom> iterOi = Oi.getAxioms(AxiomType.SUBCLASS_OF).iterator();
			while (iterOi.hasNext()) {
				OWLSubClassOfAxiom ax = iterOi.next();
				if (!OmSubclassAxiom.contains(ax)) {
					OWLAxiom eq = ontM.getEqAxioms().get(ax);
					if (eq == null) {
						/*
						 * it means even the axiom or the equal-form of this
						 * axiom do not exist in Om
						 */
						missStr++;
						MissArray.add(ax.toString());
					}
				}
			}

		}

		StatisticTest.result.put("structure_preservation", String.valueOf(missStr));
		if (missStr > 0) {
			if (missStr == 1) {
				res[0] = missStr + " case " + cross;
			} else {
				res[0] = missStr + " cases " + cross;
			}
			e1 = missStr + " structures are not preserved!\n";
		} else {
			res[0] = tick;
			e1 = "All classes preserve the same structure as the input models. \n";
		}

		if (missStr > 0) {
			e2 = "The classes which do not follow the same structrue as the input ontologies' hierarchy are:";
			res[1] = "<span style=\"color: red;\">The classes which do not follow the same structrue as the input ontologies' hierarchy are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replaceAll("<", "[").replaceAll(">", "]");
				e2 = e2 + "\n " + cc.toString();
			}
			res[1] = res[1] + "</span>";
			String id = "StrCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">All classes preserve the same structure as the input models.</span>";
		}
		eval.put("StructurePreservation", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	private String[] StructurePreservationEvalOld(HModel ontM) {
		String[] res = new String[2];
		int missStr = 0;
		HashSet<String> MissArray = new HashSet<String>();
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";

		Set<OWLSubClassOfAxiom> OmSubclassAxiom = ontM.getOwlModel().getAxioms(AxiomType.SUBCLASS_OF);
		HashMap<OWLAxiom, OWLAxiom> equalAxioms = ontM.getEqAxioms();

		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLSubClassOfAxiom> iterOi = Oi.getAxioms(AxiomType.SUBCLASS_OF).iterator();
			while (iterOi.hasNext()) {
				OWLSubClassOfAxiom ax = iterOi.next();
				if (!OmSubclassAxiom.contains(ax)) {
					if (!structureCheckingOS(ontM, ax)) {
						OWLAxiom as = equalAxioms.get(ax);
						if (as == null) {
							/*
							 * it means this subclass axiom or its equal axiom
							 * does not exist in Om.
							 */
							missStr++;
							MissArray.add(ax.toString());
						}
					}
				}

			}
		}

		StatisticTest.result.put("structure_preservation", String.valueOf(missStr));
		if (missStr > 0) {
			if (missStr == 1) {
				res[0] = missStr + " case " + cross;
			} else {
				res[0] = missStr + " cases " + cross;
			}
			e1 = missStr + " structures are not preserved!\n";
		} else {
			res[0] = tick;
			e1 = "All classes preserve the same structure as the input models. \n";
		}

		if (missStr > 0) {
			e2 = "The classes which do not follow the same structrue as the input ontologies' hierarchy are:";
			res[1] = "<span style=\"color: red;\">The classes which do not follow the same structrue as the input ontologies' hierarchy are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replaceAll("<", "[").replaceAll(">", "]");
				e2 = e2 + "\n " + cc.toString();
			}
			res[1] = res[1] + "</span>";
			String id = "StrCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">All classes preserve the same structure as the input models.</span>";
		}
		eval.put("StructurePreservation", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	private boolean structureCheckingOS(HModel ontM, OWLSubClassOfAxiom currentAxiom) {

		for (OWLOntology OS : ontM.getInputOwlOntModel()) {
			Iterator<OWLSubClassOfAxiom> OsAxiom = OS.getAxioms(AxiomType.SUBCLASS_OF).iterator();
			while (OsAxiom.hasNext()) {
				OWLSubClassOfAxiom osAx = OsAxiom.next();
				if (OS.getAxioms(AxiomType.SUBCLASS_OF).contains(currentAxiom))
					return true;
				if (osAx.equals(currentAxiom))
					return true;
			}
		}

		for (OWLOntology OS : ontM.getInputOwlOntModel()) {
			Iterator<OWLSubClassOfAxiom> OsAxiom = OS.getAxioms(AxiomType.SUBCLASS_OF).iterator();
			while (OsAxiom.hasNext()) {
				OWLSubClassOfAxiom osAx = OsAxiom.next();
				if (checkOSstr(ontM, osAx, currentAxiom))
					return true;
			}
		}
		return false;
	}

	private boolean checkOSstr(HModel ontM, OWLSubClassOfAxiom osAx, OWLSubClassOfAxiom currentAxiom) {
		OWLDataFactory factory = ontM.getManager().getOWLDataFactory();

		OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) osAx).getSuperClass();
		OWLClassExpression SubClass = ((OWLSubClassOfAxiom) osAx).getSubClass();
		boolean changes = false;
		if (SuperClass instanceof OWLClassImpl && SubClass instanceof OWLClassImpl) {
			OWLClass eqSup = SuperClass.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSup);
			if (refClass != null) {
				eqSup = refClass;
				changes = true;
			}

			OWLClass eqSub = SubClass.asOWLClass();
			refClass = ontM.getKeyValueEqClass().get(eqSub);
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}

			// if (changes) {
			// OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, eqSup);
			// manager.removeAxiom(ontology, oldAxiom);
			// manager.addAxiom(ontology, newAxiom);
			// HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			// eqAx.put(oldAxiom, newAxiom);
			//
			// ontM.setEqAxioms(eqAx);
			// int r = ontM.getNumRewriteAxioms();
			// ontM.setNumRewriteAxioms(r + 1);
			// }
		} else if (SuperClass instanceof OWLObjectUnionOf && SubClass instanceof OWLClassImpl) {
			OWLClass eqSub = SubClass.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub);
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}

			Set<OWLClassExpression> tempObj = new HashSet<OWLClassExpression>();
			Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) SuperClass).getOperands().iterator();
			while (rangExist.hasNext()) {
				OWLClassExpression ex = rangExist.next();
				if (ex instanceof OWLClassImpl) {
					OWLClass eq = ex.asOWLClass();
					refClass = ontM.getKeyValueEqClass().get(ex);
					if (refClass != null) {
						eq = refClass;
						changes = true;
					}

					tempObj.add(eq);
				}
			}
			// if (changes) {
			// OWLObjectUnionOf ob = factory.getOWLObjectUnionOf(tempObj);
			// OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, ob);
			// manager.removeAxiom(ontology, oldAxiom);
			// manager.addAxiom(ontology, newAxiom);
			// HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			// eqAx.put(oldAxiom, newAxiom);
			//
			// ontM.setEqAxioms(eqAx);
			// int r = ontM.getNumRewriteAxioms();
			// ontM.setNumRewriteAxioms(r + 1);
			// }
		} else if (SubClass instanceof OWLObjectUnionOf) {
			// never happend
		} else if (SuperClass instanceof OWLDataExactCardinality && SubClass instanceof OWLClassImpl) {
			int cd = ((OWLDataExactCardinality) SuperClass).getCardinality();
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLDataPropertyExpression SuperClassProp = ((OWLDataExactCardinality) SuperClass).getProperty();
			OWLDataPropertyExpression eqSuperClassPro = SuperClassProp;
			OWLDataProperty RefSuperClassPro = ontM.getKeyValueEqDataPro().get(SuperClassProp);
			if (RefSuperClassPro != null) {
				eqSuperClassPro = RefSuperClassPro;
				changes = true;
			}
			// if (changes) {
			// OWLDataExactCardinality cardi =
			// factory.getOWLDataExactCardinality(cd, eqSuperClassPro);
			// OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
			// manager.removeAxiom(ontology, oldAxiom);
			// manager.addAxiom(ontology, newAxiom);
			// HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			// eqAx.put(oldAxiom, newAxiom);
			//
			// ontM.setEqAxioms(eqAx);
			// int r = ontM.getNumRewriteAxioms();
			// ontM.setNumRewriteAxioms(r + 1);
			// }
		} else if (SuperClass instanceof OWLObjectExactCardinality && SubClass instanceof OWLClassImpl) {
			int cd = ((OWLObjectExactCardinality) SuperClass).getCardinality();
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLObjectPropertyExpression SuperClassProp = ((OWLObjectExactCardinality) SuperClass).getProperty();
			OWLObjectPropertyExpression eqSuperClassPro = SuperClassProp;
			OWLObjectProperty RefSuperClassPro = ontM.getKeyValueEqObjProperty().get(SuperClassProp);
			if (RefSuperClassPro != null) {
				eqSuperClassPro = RefSuperClassPro;
				changes = true;
			}
			// if (changes) {
			// OWLObjectExactCardinality cardi =
			// factory.getOWLObjectExactCardinality(cd, eqSuperClassPro);
			// OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
			// manager.removeAxiom(ontology, oldAxiom);
			// manager.addAxiom(ontology, newAxiom);
			// HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			// eqAx.put(oldAxiom, newAxiom);
			//
			// ontM.setEqAxioms(eqAx);
			// int r = ontM.getNumRewriteAxioms();
			// ontM.setNumRewriteAxioms(r + 1);
			// }
		} else if (SuperClass instanceof OWLObjectMaxCardinality && SubClass instanceof OWLClassImpl) {
			int cd = ((OWLObjectMaxCardinality) SuperClass).getCardinality();
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLObjectPropertyExpression SuperClassProp = ((OWLObjectMaxCardinality) SuperClass).getProperty();
			OWLObjectPropertyExpression eqSuperClassPro = SuperClassProp;
			OWLObjectProperty RefSuperClassPro = ontM.getKeyValueEqObjProperty().get(SuperClassProp);
			if (RefSuperClassPro != null) {
				eqSuperClassPro = RefSuperClassPro;
				changes = true;
			}
			// if (changes) {
			// OWLObjectMaxCardinality cardi =
			// factory.getOWLObjectMaxCardinality(cd, eqSuperClassPro);
			// OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
			// manager.removeAxiom(ontology, oldAxiom);
			// manager.addAxiom(ontology, newAxiom);
			// HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			// eqAx.put(oldAxiom, newAxiom);
			//
			// ontM.setEqAxioms(eqAx);
			// int r = ontM.getNumRewriteAxioms();
			// ontM.setNumRewriteAxioms(r + 1);
			// }
		} else if (SuperClass instanceof OWLDataMaxCardinality && SubClass instanceof OWLClassImpl) {
			int cd = ((OWLDataMaxCardinality) SuperClass).getCardinality();
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLDataPropertyExpression SuperClassProp = ((OWLDataMaxCardinality) SuperClass).getProperty();
			OWLDataPropertyExpression eqSuperClassPro = SuperClassProp;
			OWLDataProperty RefSuperClassPro = ontM.getKeyValueEqDataPro().get(SuperClassProp);
			if (RefSuperClassPro != null) {
				eqSuperClassPro = RefSuperClassPro;
				changes = true;
			}
			// if (changes) {
			// OWLDataMaxCardinality cardi =
			// factory.getOWLDataMaxCardinality(cd, eqSuperClassPro);
			// OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
			// manager.removeAxiom(ontology, oldAxiom);
			// manager.addAxiom(ontology, newAxiom);
			// HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			// eqAx.put(oldAxiom, newAxiom);
			//
			// ontM.setEqAxioms(eqAx);
			// int r = ontM.getNumRewriteAxioms();
			// ontM.setNumRewriteAxioms(r + 1);
			// }
		} else if (SuperClass instanceof OWLDataMinCardinality && SubClass instanceof OWLClassImpl) {
			int cd = ((OWLDataMinCardinality) SuperClass).getCardinality();
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLDataPropertyExpression SuperClassProp = ((OWLDataMinCardinality) SuperClass).getProperty();
			OWLDataPropertyExpression eqSuperClassPro = SuperClassProp;
			OWLDataProperty RefSuperClassPro = ontM.getKeyValueEqDataPro().get(SuperClassProp);
			if (RefSuperClassPro != null) {
				eqSuperClassPro = RefSuperClassPro;
				changes = true;
			}
			// if (changes) {
			// OWLDataMinCardinality cardi =
			// factory.getOWLDataMinCardinality(cd, eqSuperClassPro);
			// OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
			// manager.removeAxiom(ontology, oldAxiom);
			// manager.addAxiom(ontology, newAxiom);
			// HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			// eqAx.put(oldAxiom, newAxiom);
			//
			// ontM.setEqAxioms(eqAx);
			// int r = ontM.getNumRewriteAxioms();
			// ontM.setNumRewriteAxioms(r + 1);
			// }
		} else if (SuperClass instanceof OWLObjectSomeValuesFrom && SubClass instanceof OWLClassImpl) {
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLObjectPropertyExpression pro = ((OWLObjectSomeValuesFrom) SuperClass).getProperty();
			OWLObjectPropertyExpression eqPro = pro;
			OWLObjectPropertyExpression pp = ontM.getKeyValueEqObjProperty().get(pro);
			if (pp != null) {
				eqPro = pp;
				changes = true;
			}
			Set<OWLClass> clSet = ((OWLObjectSomeValuesFrom) SuperClass).getClassesInSignature();
			Iterator<OWLClass> cl = clSet.iterator();
			OWLClass cls = null;
			Set<OWLClass> eqClSet = new HashSet<OWLClass>();
			OWLClass eqCls = null;
			OWLObjectSomeValuesFrom sv = null;
			if (clSet.size() > 1) {
				while (cl.hasNext()) {
					OWLClass c = cl.next();
					OWLClass cq = ontM.getKeyValueEqClass().get(c);
					if (cq != null) {
						eqClSet.add(cq);
						changes = true;
					} else {
						eqClSet.add(c);
					}
				}
				OWLObjectUnionOf uo = factory.getOWLObjectUnionOf(eqClSet);
				sv = factory.getOWLObjectSomeValuesFrom(eqPro, uo);
			} else {
				if (cl.hasNext())
					cls = cl.next();
				eqCls = cls;
				OWLClass cc = ontM.getKeyValueEqClass().get(cls);
				if (cc != null) {
					eqCls = cc;
					changes = true;
				}
				sv = factory.getOWLObjectSomeValuesFrom(eqPro, eqCls);
			}
			// if (changes) {
			// OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, sv);
			// manager.removeAxiom(ontology, oldAxiom);
			// manager.addAxiom(ontology, newAxiom);
			// HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			// eqAx.put(oldAxiom, newAxiom);
			//
			// ontM.setEqAxioms(eqAx);
			// int r = ontM.getNumRewriteAxioms();
			// ontM.setNumRewriteAxioms(r + 1);
			// }
		} else if (SuperClass instanceof OWLObjectAllValuesFrom && SubClass instanceof OWLClassImpl) {
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLObjectPropertyExpression pro = ((OWLObjectAllValuesFrom) SuperClass).getProperty();
			OWLObjectPropertyExpression eqPro = pro;
			OWLObjectPropertyExpression pp = ontM.getKeyValueEqObjProperty().get(pro);
			if (pp != null) {
				eqPro = pp;
				changes = true;
			}
			Set<OWLClass> clSet = ((OWLObjectAllValuesFrom) SuperClass).getClassesInSignature();
			Iterator<OWLClass> cl = clSet.iterator();
			OWLClass cls = null;
			Set<OWLClass> eqClSet = new HashSet<OWLClass>();
			OWLClass eqCls = null;
			OWLObjectAllValuesFrom sv = null;
			if (clSet.size() > 1) {
				while (cl.hasNext()) {
					OWLClass c = cl.next();
					OWLClass cq = ontM.getKeyValueEqClass().get(c);
					if (cq != null) {
						eqClSet.add(cq);
						changes = true;
					} else {
						eqClSet.add(c);
					}
				}
				OWLObjectUnionOf uo = factory.getOWLObjectUnionOf(eqClSet);
				sv = factory.getOWLObjectAllValuesFrom(eqPro, uo);
			} else {
				if (cl.hasNext())
					cls = cl.next();
				eqCls = cls;
				OWLClass cc = ontM.getKeyValueEqClass().get(cls);
				if (cc != null) {
					eqCls = cc;
					changes = true;
				}
				sv = factory.getOWLObjectAllValuesFrom(eqPro, eqCls);
			}
			// if (changes) {
			// OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, sv);
			// manager.removeAxiom(ontology, oldAxiom);
			// manager.addAxiom(ontology, newAxiom);
			// HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			// eqAx.put(oldAxiom, newAxiom);
			//
			// ontM.setEqAxioms(eqAx);
			// int r = ontM.getNumRewriteAxioms();
			// ontM.setNumRewriteAxioms(r + 1);
			// }
		} else if (SuperClass instanceof OWLObjectMinCardinality && SubClass instanceof OWLClassImpl) {
			int cd = ((OWLObjectMinCardinality) SuperClass).getCardinality();
			OWLClassExpression eqSub = SubClass;
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null) {
				eqSub = refClass;
				changes = true;
			}
			OWLObjectPropertyExpression SuperClassProp = ((OWLObjectMinCardinality) SuperClass).getProperty();
			OWLObjectPropertyExpression eqSuperClassPro = SuperClassProp;
			OWLObjectProperty RefSuperClassPro = ontM.getKeyValueEqObjProperty().get(SuperClassProp);
			if (RefSuperClassPro != null) {
				eqSuperClassPro = RefSuperClassPro;
				changes = true;
			}
			// if (changes) {
			// OWLObjectMinCardinality cardi =
			// factory.getOWLObjectMinCardinality(cd, eqSuperClassPro);
			// OWLAxiom newAxiom = factory.getOWLSubClassOfAxiom(eqSub, cardi);
			// manager.removeAxiom(ontology, oldAxiom);
			// manager.addAxiom(ontology, newAxiom);
			// HashMap<OWLAxiom, OWLAxiom> eqAx = ontM.getEqAxioms();
			// eqAx.put(oldAxiom, newAxiom);
			//
			// ontM.setEqAxioms(eqAx);
			// int r = ontM.getNumRewriteAxioms();
			// ontM.setNumRewriteAxioms(r + 1);
			// }
		} else {
			int w = 0;
			// System.out.println("Process me in HMerging.java in
			// SubClassProcessor");
		}

		// if (changes==true &&)
		if (osAx.equals(currentAxiom))
			return true;

		return false;
	}

	// ************************************************************************
	private String[] ClassRedundancyEval(HModel ontM) {

		// since ontM.getOwlModel().getClassesInSignature() is a SET, so there
		// is no repeated class inside the ontology
		String[] res = new String[2];
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";
		res[0] = tick;
		e1 = "There is no redundant class. \n";
		res[1] = "<span style=\"color: green;\">There is no redundant class in the merged ontology.</span>";
		StatisticTest.result.put("class_redundancy", String.valueOf(0));
		eval.put("ClassRedundancy", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	// ************************************************************************

	private String[] PropertyRedundancyEval(HModel ontM) {

		// since ontM.getOwlModel().getObjectPropertiesInSignature() is a SET,
		// so there is no repeated class inside the ontology
		String[] res = new String[2];
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";
		res[0] = tick;
		e1 = "There is no redundant property. \n";
		res[1] = "<span style=\"color: green;\">There is no redundant property in the merged ontology.</span>";
		StatisticTest.result.put("property_redundancy", String.valueOf(0));
		eval.put("PropertyRedundancy", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	// ************************************************************************
	private String[] InstanceRedundancyEval(HModel ontM) {
		// since ontM.getOwlModel().getIndividualsInSignature() is a SET, so
		// there is no repeated class inside the ontology
		String[] res = new String[2];
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";
		res[0] = tick;
		e1 = "There is no redundant instance. \n";
		res[1] = "<span style=\"color: green;\">There is no redundant instance in the merged ontology.</span>";
		StatisticTest.result.put("instance_redundancy", String.valueOf(0));
		eval.put("InstanceRedundancy", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	// ************************************************************************
	private String[] ExtraneousEntityProhibitionEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> extraEntity = new HashSet<String>();
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";

		// Step 1 : Check for classes
		HashSet<String> extraClass = findExtraClasses(ontM);
		extraEntity.addAll(extraClass);

		// Step 2: Check for object properties and datatype properties
		HashSet<String> extraObjPro = findExtraObjectProperty(ontM);
		HashSet<String> extraDPro = findExtraDataProperty(ontM);
		extraEntity.addAll(extraObjPro);
		extraEntity.addAll(extraDPro);

		// Step 3: check for instances
		HashSet<String> extraInst = findExtraInstances(ontM);
		extraEntity.addAll(extraInst);

		if (extraEntity.size() > 0) {
			if (extraEntity.size() == 1) {
				res[0] = extraEntity.size() + " case " + cross;
			} else {
				res[0] = extraEntity.size() + " cases " + cross;
			}
			e1 = extraEntity + " extra entities exist!\n";
		} else {
			res[0] = tick;
			e1 = "There is no extra entity. \n";
		}

		if (extraEntity.size() > 0) {
			e2 = "Extra entities are:";
			res[1] = "<span style=\"color: red;\">Extra entities are:";
			Iterator<String> arrayIter = extraEntity.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replaceAll("<", "[").replaceAll(">", "]");
				e2 = e2 + "\n " + cc.toString();
			}
			res[1] = res[1] + "</span>";
			String id = "ExtCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no extra entitiy.</span>";
		}

		StatisticTest.result.put("extraneous_prohibition", String.valueOf(extraEntity.size()));

		eval.put("ExtraneousEntityProhibition", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	// ************************************************************************
	private String[] ClassAcyclicityEval(HModel ontM) {

		OWLOntology Om = ontM.getOwlModel();
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int counter = 0;
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";

		Iterator<OWLClass> cl = Om.getClassesInSignature().iterator();

		HashMap<OWLClassExpression, ArrayList<OWLClassExpression>> listParent = ShareMergeFunction
				.generateParentChildList(Om);

		while (cl.hasNext()) {
			OWLClass c = cl.next();

			boolean hasCyle = false;
			ArrayList<OWLClassExpression> cParent = listParent.get(c);
			if (cParent != null && ShareMergeFunction.hasDuplicate(cParent))
				hasCyle = true;

			if (cParent != null && cParent.contains(c))
				hasCyle = true;

			if (hasCyle) {
				// Delete it
				Iterator<OWLAxiom> iter = c.getReferencingAxioms(Om).iterator();
				while (iter.hasNext()) {
					OWLAxiom ax = iter.next();
					if (ax.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
						OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) ax).getSuperClass();
						OWLClassExpression SubClass = ((OWLSubClassOfAxiom) ax).getSubClass();

						if (SuperClass instanceof OWLClassImpl && SubClass instanceof OWLClassImpl) {
							if (SuperClass.equals(c) && SubClass.equals(c)) {
								// remove it
								counter++;
								MissArray.add(c.toString().replace("<", "").replace(">", ""));
								break;
							}
						}
					}
				}
			}

		}

		StatisticTest.result.put("cycle_class", String.valueOf(counter));

		if (counter > 0) {
			if (counter == 1) {
				res[0] = counter + " case " + cross;
			} else {
				res[0] = counter + " cases " + cross;
			}
			e1 = counter + " cycle in class hierarchy are not preserved!\n";
		} else {
			res[0] = tick;
			e1 = "There is no cycle in the class hierarchy. \n";
		}

		if (counter > 0) {
			e2 = "Classes that cause a cycle:";
			res[1] = "<span style=\"color: red;\">Classes that cause a cycle:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc;
				e2 = e2 + "\n " + cc.toString();
			}
			res[1] = res[1] + "</span>";
			String id = "AcyClCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no cycle in the class hierarchy.</span>";
		}
		eval.put("ClassAcyclicity", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	// ************************************************************************
	private String[] PropertyAcyclicityEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int counter = 0;
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";

		Iterator<OWLSubObjectPropertyOfAxiom> axObjPro = ontM.getOwlModel().getAxioms(AxiomType.SUB_OBJECT_PROPERTY)
				.iterator();
		while (axObjPro.hasNext()) {
			OWLSubObjectPropertyOfAxiom axiom = axObjPro.next();

			OWLObjectPropertyExpression SuperPro = ((OWLSubObjectPropertyOfAxiom) axiom).getSubProperty();
			OWLObjectPropertyExpression SubPro = ((OWLSubObjectPropertyOfAxiom) axiom).getSuperProperty();
			if (SuperPro.equals(SubPro)) {
				counter++;
				MissArray.add(axiom.toString());
			}
		}

		Iterator<OWLSubDataPropertyOfAxiom> axDpro = ontM.getOwlModel().getAxioms(AxiomType.SUB_DATA_PROPERTY)
				.iterator();
		while (axDpro.hasNext()) {
			OWLSubDataPropertyOfAxiom axiom = axDpro.next();

			OWLDataPropertyExpression SuperPro = ((OWLSubDataPropertyOfAxiom) axiom).getSubProperty();
			OWLDataPropertyExpression SubPro = ((OWLSubDataPropertyOfAxiom) axiom).getSuperProperty();
			if (SuperPro.equals(SubPro)) {
				counter++;
				MissArray.add(axiom.toString());
			}
		}

		StatisticTest.result.put("cycle_property", String.valueOf(counter));

		if (counter > 0) {
			if (counter == 1) {
				res[0] = counter + " case " + cross;
			} else {
				res[0] = counter + " cases " + cross;
			}
			e1 = counter + " cycles in the property hierarchy exist!\n";
		} else {
			res[0] = tick;
			e1 = "There is no cycle in the property hierarchy. \n";
		}

		if (counter > 0) {
			e2 = "Properties that cause a cycle:";
			res[1] = "<span style=\"color: red;\">Properties that cause a cycle are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replace("<", "").replace(">", "");
				e2 = e2 + "\n " + cc.toString();
			}
			res[1] = res[1] + "</span>";
			String id = "AcyProCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no cycle in the property hierarchy.</span>";
		}
		eval.put("PropertyAcyclicity", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	// ************************************************************************
	private String[] InversePropertyProhibitionEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int counter = 0;
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";

		Iterator<OWLInverseObjectPropertiesAxiom> ax = ontM.getOwlModel().getAxioms(AxiomType.INVERSE_OBJECT_PROPERTIES)
				.iterator();
		while (ax.hasNext()) {
			OWLInverseObjectPropertiesAxiom axiom = ax.next();

			OWLObjectPropertyExpression p1 = axiom.getFirstProperty();
			OWLObjectPropertyExpression p2 = axiom.getSecondProperty();

			if (p1.equals(p2)) {
				counter++;
				MissArray.add(axiom.toString());
			}
		}

		StatisticTest.result.put("inverse_property", String.valueOf(counter));
		if (counter > 0) {
			if (counter == 1) {
				res[0] = counter + " case " + cross;
			} else {
				res[0] = counter + " cases " + cross;
			}
			e1 = counter + " inverse properties exist!\n";
		} else {
			res[0] = tick;
			e1 = "There is no inverse property. \n";
		}

		if (counter > 0) {
			e2 = "Properties with iverse of themselves are:";
			res[1] = "<span style=\"color: red;\">Properties with iverse of themselves are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replace("<", "").replace(">", "");
				e2 = e2 + "\n " + cc.toString();
			}
			res[1] = res[1] + "</span>";
			String id = "RecProCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no inverse propertiy themselves.</span>";
		}
		eval.put("InversePropertyProhibition", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	// ************************************************************************
	private String[] UnconnectedClassProhibitionEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int counter = 0;

		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";

		OWLOntology Om = ontM.getOwlModel();
		Iterator<OWLClass> lis = Om.getClassesInSignature().iterator();
		while (lis.hasNext()) {
			OWLClass c = lis.next();
			if (c.getSubClasses(Om).size() == 0 && c.getSuperClasses(Om).size() == 0) {
				/*
				 * we mark it as unconnected, if it was already in Oi connected,
				 * now during the merge process, it becomes to unconnected
				 * elements
				 */
				boolean isConnected = false;
				for (int i = 0; i < ontM.getInputOntNumber(); i++) {
					OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
					if (c.getSubClasses(Oi).size() != 0) {
						isConnected = true;
						break;
					}
				}
				if (isConnected == false) {
					counter++;
					MissArray.add(c.toString().replace("<", "").replace(">", ""));
				}
			}
		}

		StatisticTest.result.put("unconnected_class", String.valueOf(counter));
		if (counter > 0) {
			if (counter == 1) {
				res[0] = counter + " case " + cross;
			} else {
				res[0] = counter + " cases " + cross;
			}
			e1 = counter + " unconnceted classes exist!\n";
		} else {
			res[0] = tick;
			e1 = "All classes are connected. \n";
		}

		if (counter > 0) {
			e2 = "Unconnected classes are:";
			res[1] = "<span style=\"color: red;\">Unconnected classes are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc;
				e2 = e2 + "\n " + cc.toString();
			}
			res[1] = res[1] + "</span>";
			String id = "UnconnClCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;

		} else {
			res[1] = "<span style=\"color: green;\">There is no unconnceted class.</span>";
		}
		eval.put("UnconnectedClassProhibition", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	// ************************************************************************
	private String[] UnconnectedPropertyProhibitionEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int counter = 0;
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";

		OWLOntology Om = ontM.getOwlModel();
		// OWLOntologyManager manager = ontM.getManager();
		// OWLDataFactory factory = manager.getOWLDataFactory();
		// String changes = "";
		// For Objectproperties
		Iterator<OWLObjectProperty> lis = Om.getObjectPropertiesInSignature().iterator();
		while (lis.hasNext()) {
			OWLObjectProperty p = lis.next();
			if (p.getDomains(Om).size() == 0 && p.getRanges(Om).size() == 0 && p.getSubProperties(Om).size() == 0
					&& p.getObjectPropertiesInSignature().size() == 0 && p.getDataPropertiesInSignature().size() == 0) {
				// it means this property does not have any class, domain, range
				// etc. it means it is unconnected

				counter++;
				MissArray.add(p.toString().replace("<", "").replace(">", ""));
			}
		}

		// For datatypeProperties
		Iterator<OWLDataProperty> listDpro = Om.getDataPropertiesInSignature().iterator();
		while (listDpro.hasNext()) {
			OWLDataProperty p = listDpro.next();
			if (p.getDomains(Om).size() == 0 && p.getRanges(Om).size() == 0 && p.getSubProperties(Om).size() == 0
					&& p.getObjectPropertiesInSignature().size() == 0 && p.getDataPropertiesInSignature().size() == 0) {
				// it means this property does not have any class, domain, range
				// etc. it means it is unconnected

				counter++;
				MissArray.add(p.toString().replace("<", "").replace(">", ""));
			}
		}

		StatisticTest.result.put("unconnected_property", String.valueOf(counter));
		if (counter > 0) {
			if (counter == 1) {
				res[0] = counter + " case " + cross;
			} else {
				res[0] = counter + " cases " + cross;
			}
			e1 = counter + " unconnected properties exist!\n";
		} else {
			res[0] = tick;
			e1 = "All properties are connected. \n";
		}

		if (counter > 0) {
			e2 = "Unconnected properties are:";
			res[1] = "<span style=\"color: red;\">Unconnected properties are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc;
				e2 = e2 + "\n " + cc.toString();
			}
			res[1] = res[1] + "</span>";
			String id = "UnconnProCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no unconnected property.</span>";
		}

		eval.put("UnconnectedPropertyProhibition", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	// ************************************************************************
	private String[] EntailmentSatisfactionEval(HModel ontM) {
		String[] res = new String[4];
		HashMap<String, String> eval = ontM.getEvalHashResult();
		String e1 = "", e2 = "";

		int[] ans = Entailmenter.isEntailed(ontM, ontM.getOwlModel(), ontM.getInputOwlOntModel());

		if (ans[0] == -1) {
			res[0] = cross;
			res[1] = "<span style=\"color: red;\">The merged ontology's entailment can not be tested, since the merged ontology is inconsistent.</span>";
			StatisticTest.result.put("entailment", "null");
			return res;
		}
		int totalFalse = ans[1] + ans[3];
		if (totalFalse > 0) {
			if (totalFalse == 1) {
				res[0] = totalFalse + " case " + cross;
			} else {
				res[0] = totalFalse + " cases " + cross;
			}
			e1 = totalFalse + " entailments failed!\n";
		} else {
			res[0] = tick;
			e1 = "All entailment are satisfied. \n";
		}

		if (totalFalse > 0) {
			e2 = "The merged ontology's entailment is not same as the input ontologies' entailments: "
					+ "The number of true subsumption entilment is " + ans[0]
					+ " and the number of false subsumption entailment is " + ans[1]
					+ "\n The number of true equivalence entilment is " + ans[2]
					+ " and the number of false equivalence entailment is " + ans[3];

			res[1] = "<span style=\"color: red;\">The merged ontology's entailment is not same as the input ontologies' entailments:";
			res[1] = res[1] + "<br> -> The number of true subsumption entilment is " + ans[0]
					+ " and the number of false subsumption entailment is " + ans[1]
					+ "<br> -> The number of true equivalence entilment is " + ans[2]
					+ " and the number of false equivalence entailment is " + ans[3] + "</span>";
			res[1] = res[1] + "<br> <span>Repair is not implemented yet!</span>";
		} else {
			res[1] = "<span style=\"color: green;\">The merged ontology's entailment is same as the input ontologies' entailments.</span>";
		}

		StatisticTest.result.put("entailment", String.valueOf(totalFalse));// falseAnswer

		eval.put("EntailmentSatisfaction", e1 + e2);
		ontM.setEvalHashResult(eval);
		return res;
	}

	// *********************************************************************************************************
	private static HashSet<String> findExtraClasses(HModel ontM) {
		HashSet<String> res = new HashSet<String>();
		Iterator<OWLClass> iter = ontM.getOwlModel().getClassesInSignature().iterator();
		boolean exist = false;
		while (iter.hasNext()) {
			OWLClass cm = iter.next();
			exist = false;
			// check cm exist in one of Oi
			for (int i = 0; i < ontM.getInputOntNumber(); i++) {
				if (ontM.getInputOwlOntModel().get(i).getClassesInSignature().contains(cm)) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				Set<OWLClass> mappedRefinedClass = ShareMergeFunction.getMappedClassesforClass(ontM, cm);
				if (mappedRefinedClass != null) {
					Iterator<OWLClass> iterMap = mappedRefinedClass.iterator();
					while (iterMap.hasNext()) {
						if (exist)
							break;
						OWLClass c = iterMap.next();
						for (int i = 0; i < ontM.getInputOntNumber(); i++) {
							if (ontM.getInputOwlOntModel().get(i).getClassesInSignature().contains(c)) {
								exist = true;
								break;
							}
						}
					}
				}
			}

			if (!exist) {
				if (!cm.getIRI().toString().equals(OWL.Thing.getURI().toString())) {
					res.add(cm.toString());
				}
			}
		}

		return res;
	}

	// *********************************************************************************************************

	private static HashSet<String> findExtraObjectProperty(HModel ontM) {
		HashSet<String> res = new HashSet<String>();
		Iterator<OWLObjectProperty> iter = ontM.getOwlModel().getObjectPropertiesInSignature().iterator();
		boolean exist = false;
		while (iter.hasNext()) {
			OWLObjectProperty cm = iter.next();
			exist = false;
			// check cm exist in one of Oi
			for (int i = 0; i < ontM.getInputOntNumber(); i++) {
				if (ontM.getInputOwlOntModel().get(i).getObjectPropertiesInSignature().contains(cm)) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				Set<OWLObjectProperty> mappedRefinedObj = ShareMergeFunction.getMappedObjectforObject(ontM, cm);
				if (mappedRefinedObj != null) {
					Iterator<OWLObjectProperty> iterMap = mappedRefinedObj.iterator();
					while (iterMap.hasNext()) {
						if (exist)
							break;
						OWLObjectProperty c = iterMap.next();
						for (int i = 0; i < ontM.getInputOntNumber(); i++) {
							if (ontM.getInputOwlOntModel().get(i).getObjectPropertiesInSignature().contains(c)) {
								exist = true;
								break;
							}
						}
					}
				}
			}

			if (!exist) {
				res.add(cm.toString());

			}
		}

		return res;
	}

	// *********************************************************************************************************

	private static HashSet<String> findExtraDataProperty(HModel ontM) {
		HashSet<String> res = new HashSet<String>();
		Iterator<OWLDataProperty> iter = ontM.getOwlModel().getDataPropertiesInSignature().iterator();
		boolean exist = false;
		while (iter.hasNext()) {
			OWLDataProperty cm = iter.next();
			exist = false;
			// check cm exist in one of Oi
			for (int i = 0; i < ontM.getInputOntNumber(); i++) {
				if (ontM.getInputOwlOntModel().get(i).getDataPropertiesInSignature().contains(cm)) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				Set<OWLDataProperty> mappedRefinedClass = ShareMergeFunction.getMappedDproforDpro(ontM, cm);
				if (mappedRefinedClass != null) {
					Iterator<OWLDataProperty> iterMap = mappedRefinedClass.iterator();
					while (iterMap.hasNext()) {
						if (exist)
							break;
						OWLDataProperty c = iterMap.next();
						for (int i = 0; i < ontM.getInputOntNumber(); i++) {
							if (ontM.getInputOwlOntModel().get(i).getDataPropertiesInSignature().contains(c)) {
								exist = true;
								break;
							}
						}
					}
				}
			}

			if (!exist) {
				res.add(cm.toString());
			}
		}

		return res;
	}

	// *********************************************************************************************************

	private static HashSet<String> findExtraInstances(HModel ontM) {
		HashSet<String> res = new HashSet<String>();
		Iterator<OWLNamedIndividual> iter = ontM.getOwlModel().getIndividualsInSignature().iterator();
		boolean exist = false;
		while (iter.hasNext()) {
			OWLNamedIndividual cm = iter.next();
			exist = false;
			// check cm exist in one of Oi
			for (int i = 0; i < ontM.getInputOntNumber(); i++) {
				if (ontM.getInputOwlOntModel().get(i).getIndividualsInSignature().contains(cm)) {
					exist = true;
					break;
				}
			}

			if (!exist) {
				res.add(cm.toString());
			}
		}

		return res;
	}

	// *********************************************************************************************************

}
