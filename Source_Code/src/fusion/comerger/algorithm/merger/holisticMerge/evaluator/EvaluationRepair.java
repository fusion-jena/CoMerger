package fusion.comerger.algorithm.merger.holisticMerge.evaluator;

import fusion.comerger.algorithm.merger.holisticMerge.merging.HRefinePacket;
import fusion.comerger.algorithm.merger.model.HModel;

public class EvaluationRepair {

	public static HModel DoRepair(HModel ontM, String repair) {
		if (repair.length() < 1)
			return ontM;
		String rep[] = repair.split(";");
		String task = rep[0];
		switch (task) {
		case "ClassCheck":
			ontM = HRefinePacket.ClassesPreservation(ontM);
			break;

		case "ProCheck":
			ontM = HRefinePacket.PropertiesPreservation(ontM);
			break;

		case "InstanceCheck":
			ontM = HRefinePacket.InstancesPreservation(ontM);
			break;

		case "CorresCheck":
			ontM = HRefinePacket.CorrespondencesPreservation(ontM);
			break;

		case "CorssPropCheck":
			ontM = HRefinePacket.CorrespondPropertyPreservation(ontM);
			break;

		case "ValueCheck":
			ontM = HRefinePacket.ValuePreservation(ontM);
			break;

		case "StrCheck":
			ontM = HRefinePacket.StructurePreservation(ontM);
			break;

		case "ClRedCheck":
			ontM = HRefinePacket.ClassRedundancyProhibition(ontM);
			break;

		case "ProRedCheck":
			ontM = HRefinePacket.PropertyRedundancyProhibition(ontM);
			break;

		case "InstRedCheck":
			ontM = HRefinePacket.InstanceRedundancyProhibition(ontM);
			break;

		case "ExtCheck":
			ontM = HRefinePacket.ExtraneousEntityProhibition(ontM);
			break;

		case "EntCheck":
			ontM = HRefinePacket.EntailmentSatisfaction(ontM);
			break;

		case "TypeCheck":
			ontM = HRefinePacket.OneTypeRestriction(ontM);
			break;

		case "ConstValCheck":
			ontM = HRefinePacket.ValueConstraint(ontM);
			break;

		case "DomRangMinCheck":
			ontM = HRefinePacket.DomainRangeMinimality(ontM);
			break;

		case "AcyClCheck":
			ontM = HRefinePacket.ClassAcyclicity(ontM);
			break;

		case "AcyProCheck":
			ontM = HRefinePacket.PropertyAcyclicity(ontM);
			break;

		case "RecProCheck":
			ontM = HRefinePacket.InversePropertyProhibition(ontM);
			break;

		case "UnconnClCheck":
			ontM = HRefinePacket.UnconnectedClassProhibition(ontM);
			break;

		case "UnconnProCheck":
			ontM = HRefinePacket.UnconnectedPropertyProhibition(ontM);
			break;
		}
		return ontM;
	}

}
