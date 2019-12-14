package fusion.comerger.algorithm.merger.holisticMerge.evaluator;

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
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
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
				tempRes = ClassPreservationEval(ontM);
				tempResAspect[0] = tempRes[0];
				tempResAspect[1] = tempRes[1];

				tempRes = PropertyPreservationEval(ontM);
				tempResAspect[2] = tempRes[0];
				tempResAspect[3] = tempRes[1];

				tempRes = InstancePreservationEval(ontM);
				tempResAspect[4] = tempRes[0];
				tempResAspect[5] = tempRes[1];

				tempRes = CorrespondencePreservationEval(ontM);
				tempResAspect[6] = tempRes[0];
				tempResAspect[7] = tempRes[1];

				tempRes = CorrespondencePropertyPreservationEval(ontM);
				tempResAspect[8] = tempRes[0];
				tempResAspect[9] = tempRes[1];

				tempRes = ValuePreservationEval(ontM);
				tempResAspect[10] = tempRes[0];
				tempResAspect[11] = tempRes[1];

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

				tempRes = ClassAcyclicityEval(ontM);
				tempResAspect[0] = tempRes[0];
				tempResAspect[1] = tempRes[1];

				tempRes = PropertyAcyclicityEval(ontM);
				tempResAspect[2] = tempRes[0];
				tempResAspect[3] = tempRes[1];

				tempRes = InversePropertyProhibitionEval(ontM);
				tempResAspect[4] = tempRes[0];
				tempResAspect[5] = tempRes[1];

				gui[4] = GuiOutput.createAcyclicityGui(tempResAspect);
				lable = setEvalLabel(tempResAspect);
				dimensionLabel.put("AcyclicityCheck", lable);

				break;

			case "ConnectivityCheck":
				tempResAspect = new String[40];
				tempRes = UnconnectedClassProhibitionEval(ontM);
				tempResAspect[0] = tempRes[0];
				tempResAspect[1] = tempRes[1];

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
				tempResAspect = EvalCompactness(ontM);
				gui[8] = GuiOutput.createCompactnessGui(tempResAspect);
				break;

			case "CoverageCheck":
				tempResAspect = EvalCoverage(ontM);
				gui[9] = GuiOutput.createCoverageGui(tempResAspect);
				break;

			}

		}

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
			} else {
				int w = 0;
			}
		}
		if (correct > 0 && incorrect == 0) {
			lable = "100"; // "VERYGOOD"; // 100
		} else if (incorrect > 0 && correct == 0) {
			lable = "20"; // "VERYBAD"; // 20
		} else if (correct > incorrect) {
			lable = "80";// "GOOD"; // 80
		} else if (incorrect > correct) {
			lable = "40";// "BAD";// 40
		} else { // correct==incorrect or other cases
			lable = "50";// "NORMAL";// 50
		}
		return lable;
	}

	private String[] EvalUsabilityProfile(HModel ontM) {
		String[] res = new String[40];
		double sum = 0.0, ans = 0.0;
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

		// 1- Existence of annotation
		// String[] temp = HMergeEvaluation.ExisAnno(ontM);
		// 2- Naming conventions
		// temp = HMergeEvaluation.NamingConvCal(ontM);
		// 3- Correctness of ontology URI
		// ans = HMergeEvaluation.CorrectURI(ontM);

		// 5-Ontology declaration and availability
		// ans = HMergeEvaluation.OntDeclaration(ontM);
		// P41: No license declared:

		return res;
	}

	private String[] EvalCoverage(HModel ontM) {
		String[] res = new String[40];
		int classSize = ontM.getOwlModel().getClassesInSignature().size();
		int proSize = ontM.getOwlModel().getObjectPropertiesInSignature().size();
		proSize = proSize + ontM.getOwlModel().getDataPropertiesInSignature().size();
		int instanceSize = ontM.getOwlModel().getIndividualsInSignature().size();
		// int annoSize = ontM.getAllAnnotations().size();
		int eqClass = findNumberOfEqCl(ontM.getEqClasses());
		int eqPro = findNumberOfEqOpro(ontM.getEqObjProperties());
		eqPro = eqPro + findNumberOfEqDpro(ontM.getEqDataProperties());
		// TO DO: save sum at ontM not these arrays,+, no need to have numOnt
		int numOnt = ontM.getInputOntNumber();
		double sum = 0, sumCov = 0.0;
		// Full Coverage
		for (int i = 0; i < ontM.getInputClassSize().size(); i++)
			sum = sum + ontM.getInputClassSize().get(i);
		double temp = sum - eqClass;// - 1;
		if (sum > 0) {
			// temporal correctnest
			if (classSize - temp == 1 || temp - classSize == 1)
				temp = classSize;
			double s = Math.round((classSize / temp) * 100.0) / 100.0;
			res[1] = String.valueOf(s);
			res[4] = "<br> ** Number of classes in input ontologies minus their equal classes is " + temp
					+ " and number of classes in the merged ontology is " + classSize;
			sumCov = sumCov + s;
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
			// temporal correctnest
			if (proSize - temp == 1 || temp - proSize == 1)
				temp = proSize;
			double s = Math.round((proSize / temp) * 100.0) / 100.0;
			res[2] = String.valueOf(s);
			res[4] = res[4] + "<br> ** Number of properties in input ontologies minus their equal properties is " + temp
					+ " and number of classes in the merged ontology is " + proSize;
			sumCov = sumCov + s;
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
			sumCov = sumCov + s;
		} else {
			res[3] = "-";
			res[4] = res[4] + "<br> ** There is no instance in the input ontologies!";
		}

		res[0] = String.valueOf((double) Math.round((sumCov / 4) * 100.0) / 100.0);

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

		// res[0] = String.valueOf((double) Math.round((double) (classSize +
		// objectSize + dataProSiz + instanceSize + annoSize) / 5 * 10000d) /
		// 10000d)+ "%";
		sum = Double.parseDouble(res[9]) + Double.parseDouble(res[11]) + Double.parseDouble(res[13])
				+ Double.parseDouble(res[15]);
		res[0] = String.valueOf((double) Math.round((double) (sum) / 4 * 10000d) / 10000d);
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
		// TO DO: calculate the number of equal annotations and individuals
		res[5] = String.valueOf(0);
		// res[7] = String.valueOf(0);
		// TO DO: num is-a
		res[9] = String.valueOf(0);
		res[11] = "1:1 Mapping"; // TO DO

		return res;
	}

	// ************************************************************************
	private String[] ClassPreservationEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<OWLClass> MissCl = new HashSet<OWLClass>();
		int missClasses = 0;
		double totalOiClass = 0.0;

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
		} else {
			res[0] = tick;
		}

		if (missClasses > 0) {
			res[1] = "<span style=\"color: red;\">Missing classes are:";
			Iterator<OWLClass> arrayIter = MissCl.iterator();
			while (arrayIter.hasNext()) {
				OWLClass cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.toString().replaceAll("<", "[").replaceAll(">", "]");
			}
			res[1] = res[1] + "</span>";
			String id = "ClassCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;

		} else {
			res[1] = "<span style=\"color: green;\">There is no missing classes. </span>";
		}
		return res;
	}
	// ************************************************************************

	private String[] PropertyPreservationEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissPro = new HashSet<String>();
		int missPro = 0;
		double totalOiPro = 0.0;

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
		} else {
			res[0] = tick;
		}

		if (missPro > 0) {
			res[1] = "<span style=\"color: red;\">Missing properties are:";
			Iterator<String> arrayIter = MissPro.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replaceAll("<", "[").replaceAll(">", "]");
			}
			res[1] = res[1] + "</span>";
			String id = "ProCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no missing property. </span>";
		}
		return res;
	}

	// ************************************************************************
	private String[] InstancePreservationEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int missIns = 0;
		double totalOiIns = 0.0;

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
		} else {
			res[0] = tick;
		}

		if (missIns > 0) {
			res[1] = "<span style=\"color: red;\">Missing individual are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replaceAll("<", "[").replaceAll(">", "]");
			}
			res[1] = res[1] + "</span>";
			String id = "InstanceCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no missing individual.</span>";
		}
		return res;
	}

	// ************************************************************************
	private String[] CorrespondencePreservationEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
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
		HashSet<String> MissArray = new HashSet<String>();
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

		String msg = "";
		OWLOntology Om = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		Set<OWLObjectProperty> OmPro = Om.getObjectPropertiesInSignature();
		// TODO: do it for object and datatype etc. property
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
		// TODO:error :How to get type of datatypeproperties in OWL?
		for (int j = 0; j < OmEqDataPro.size(); j++) {
			OWLDataProperty Dpro = OmEqDataPro.get(j).getRefDpro();
			Set<OWLDataRange> dataRangeRef = Dpro.getRanges(Om);
			Set<OWLClassExpression> dataDomainRef = Dpro.getDomains(Om);

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
		} else {
			res[0] = tick;
		}

		double totalOiDatatype = ontM.getOwlModel().getDatatypesInSignature().size();

		if (conflictType > 0) {
			res[1] = "<span style=\"color: red;\">Conflicting datatypeProperty for the corressponding entities are:";
			Iterator<String> arrayIter = conflictArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replaceAll("<", "[").replaceAll(">", "]");
			}
			res[1] = res[1] + "</span>";
			String id = "ValueCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">All properties' value have been preserved without conflict.</span>";
		}

		return res;
	}

	// ************************************************************************
	private String[] StructurePreservationEval(HModel ontM) {
		String[] res = new String[2];
		int missStr = 0;
		HashSet<String> MissArray = new HashSet<String>();

		OWLOntologyManager manager = ontM.getManager();
		Set<OWLSubClassOfAxiom> OmSubclassAxiom = ontM.getOwlModel().getAxioms(AxiomType.SUBCLASS_OF);
		HashMap<OWLAxiom, OWLAxiom> equalAxioms = ontM.getEqAxioms();

		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			OWLOntology Oi = ontM.getInputOwlOntModel().get(i);
			Iterator<OWLSubClassOfAxiom> iterOi = Oi.getAxioms(AxiomType.SUBCLASS_OF).iterator();
			while (iterOi.hasNext()) {
				OWLSubClassOfAxiom ax = iterOi.next();
				if (!OmSubclassAxiom.contains(ax)) {
					OWLAxiom as = equalAxioms.get(ax);
					if (as == null) {
						/*
						 * it means this subclass axiom or its equal axiom does
						 * not exist in Om.
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
		} else {
			res[0] = tick;
		}

		double classCounter = ontM.getOwlModel().getAxioms(AxiomType.SUBCLASS_OF).size();

		if (missStr > 0) {
			res[1] = "<span style=\"color: red;\">The classes which do not follow the same structrue as the input ontologies' hierarchy are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replaceAll("<", "[").replaceAll(">", "]");
			}
			res[1] = res[1] + "</span>";
			String id = "StrCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">All classes preserve the same structure as the input models.</span>";
		}
		return res;
	}

	// ************************************************************************
	private String[] ClassRedundancyEval(HModel ontM) {
		Set<OWLClass> OmClasses = ontM.getOwlModel().getClassesInSignature();
		// since it is a SET, so there is no repeated class inside the ontology
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		res[0] = tick;
		res[1] = "<span style=\"color: green;\">There is no redundant class in the merged ontology.</span>";
		StatisticTest.result.put("class_redundancy", String.valueOf(0));
		return res;
	}

	// ************************************************************************

	private String[] PropertyRedundancyEval(HModel ontM) {
		Set<OWLObjectProperty> OmPro = ontM.getOwlModel().getObjectPropertiesInSignature();
		// since it is a SET, so there is no repeated class inside the ontology
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		res[0] = tick;
		res[1] = "<span style=\"color: green;\">There is no redundant property in the merged ontology.</span>";
		StatisticTest.result.put("property_redundancy", String.valueOf(0));
		return res;
	}

	// ************************************************************************
	private String[] InstanceRedundancyEval(HModel ontM) {
		Set<OWLNamedIndividual> OmIns = ontM.getOwlModel().getIndividualsInSignature();
		// since it is a SET, so there is no repeated class inside the ontology
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		res[0] = tick;
		res[1] = "<span style=\"color: green;\">There is no redundant instance in the merged ontology.</span>";
		StatisticTest.result.put("instance_redundancy", String.valueOf(0));
		return res;
	}

	// ************************************************************************
	private String[] ExtraneousEntityProhibitionEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> extraEntity = new HashSet<String>();
		double ratioClass = 1.0, ratioObjPro = 1.0, ratioDPro = 1.0, ratioIns = 1.0;

		// Step 1 : Check for classes
		HashSet<String> extraClass = findExtraClasses(ontM);
		extraEntity.addAll(extraClass);
		double totalCl = ontM.getOwlModel().getClassesInSignature().size();
		if (totalCl != 0)
			ratioClass = 1 - (extraClass.size() / totalCl);

		// Step 2: Check for object properties and datatype properties
		HashSet<String> extraObjPro = findExtraObjectProperty(ontM);
		HashSet<String> extraDPro = findExtraDataProperty(ontM);
		extraEntity.addAll(extraObjPro);
		extraEntity.addAll(extraDPro);

		double totalObjPro = ontM.getOwlModel().getObjectPropertiesInSignature().size();
		if (totalObjPro != 0)
			ratioObjPro = 1 - (extraObjPro.size() / totalObjPro);

		double totalDPro = ontM.getOwlModel().getDataPropertiesInSignature().size();
		if (totalDPro != 0)
			ratioDPro = 1 - (extraDPro.size() / totalDPro);

		// Step 3: check for instances
		HashSet<String> extraInst = findExtraInstances(ontM);
		extraEntity.addAll(extraInst);

		double totalIns = ontM.getOwlModel().getDataPropertiesInSignature().size();
		if (totalIns != 0)
			ratioIns = 1 - (extraInst.size() / totalIns);

		if (extraEntity.size() > 0) {
			if (extraEntity.size() == 1) {
				res[0] = extraEntity.size() + " case " + cross;
			} else {
				res[0] = extraEntity.size() + " cases " + cross;
			}
		} else {
			res[0] = tick;
		}

		if (extraEntity.size() > 0) {
			res[1] = "<span style=\"color: red;\">Extra entities are:";
			Iterator<String> arrayIter = extraEntity.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replaceAll("<", "[").replaceAll(">", "]");
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

		return res;
	}

	// ************************************************************************
	private String[] ClassAcyclicityEval(HModel ontM) {

		OWLOntology Om = ontM.getOwlModel();
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int counter = 0;

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
		} else {
			res[0] = tick;
		}

		if (counter > 0) {
			res[1] = "<span style=\"color: red;\">Classes that cause a cycle:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc;
			}
			res[1] = res[1] + "</span>";
			String id = "AcyClCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no cycle in the class hirerachy.</span>";
		}

		return res;
	}

	// ************************************************************************
	private String[] PropertyAcyclicityEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int counter = 0;

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
		} else {
			res[0] = tick;
		}

		if (counter > 0) {
			res[1] = "<span style=\"color: red;\">Properties that cause a cycle are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replace("<", "").replace(">", "");
			}
			res[1] = res[1] + "</span>";
			String id = "AcyProCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no cycle in the property hirerchy.</span>";
		}

		return res;
	}

	// ************************************************************************
	private String[] InversePropertyProhibitionEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int counter = 0;

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
		} else {
			res[0] = tick;
		}

		if (counter > 0) {
			res[1] = "<span style=\"color: red;\">Properties with iverse of themselves are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replace("<", "").replace(">", "");
			}
			res[1] = res[1] + "</span>";
			String id = "RecProCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no inverse propertiy themselves.</span>";
		}

		return res;
	}

	// ************************************************************************
	private String[] UnconnectedClassProhibitionEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int counter = 0;

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

		} else {
			res[0] = tick;
		}

		if (counter > 0) {
			res[1] = "<span style=\"color: red;\">Unconnected classes are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc;
			}
			res[1] = res[1] + "</span>";
			String id = "UnconnClCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;

		} else {
			res[1] = "<span style=\"color: green;\">There is no unconnceted class.</span>";
		}

		return res;
	}

	// ************************************************************************
	private String[] UnconnectedPropertyProhibitionEval(HModel ontM) {
		// TODO correct it
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int counter = 0;
		double total = 0.0;

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
		} else {
			res[0] = tick;
		}

		if (counter > 0) {
			res[1] = "<span style=\"color: red;\">Unconnected properties are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc;
			}
			res[1] = res[1] + "</span>";
			String id = "UnconnProCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no unconnected property.</span>";
		}

		return res;
	}

	// ************************************************************************
	private String[] EntailmentSatisfactionEval(HModel ontM) {
		String[] res = new String[4];
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
		} else {
			res[0] = tick;
		}

		if (totalFalse > 0) {
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
