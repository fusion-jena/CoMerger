
package fusion.comerger.general.analysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import org.apache.jena.util.iterator.ExtendedIterator;

import org.apache.jena.vocabulary.OWL;

/*import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.OWL;*/
import com.ibm.icu.text.DecimalFormat;

import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Data;
import fusion.comerger.general.gui.OPATgui;
import fusion.comerger.model.Node;
import fusion.comerger.model.NodeList;
import fusion.comerger.model.RBGModel;

public class RichnessAnalyzing {
	public double avDesign;
	public double avKB;
	public double avClass;
	public double TotalRichness;
	OntModel model;
	RBGModel rbgm;

	public RichnessAnalyzing(Data data) {
		model = data.getOntModel();
		rbgm = data.getRbgModel();
		avDesign = 0;
		avKB = 0;
		avClass = 0;
		TotalRichness = 0;
	}

	public RichnessAnalyzing(OntModel mod, RBGModel rbg) {
		rbgm = rbg;
		model = mod;
		avDesign = 0;
		avKB = 0;
		avClass = 0;
		TotalRichness = 0;
	}

	public String[] RichnessTest1() {
		String[] res = new String[13];
		List list = new ArrayList();
		list = model.listClasses().toList();
		int countSub = 0;
		for (int i = 0; i < list.size(); i++) {
			OntClass cls = (OntClass) list.get(i);
			ExtendedIterator it = cls.listSubClasses();
			while (it.hasNext()) {
				countSub++;
				it.next();
			}
		}
		int NumClass = list.size();
		// res = res+ "============ Ontology design metrics========= " +"\n";
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		int llist = model.listAllOntProperties().toList().size();
		list = model.listObjectProperties().toList();
		float RelationshipRichness = 0;
		int NumRelation = list.size();
		RelationshipRichness = (float) NumRelation / (float) (countSub + llist);
		// res = res+ "\n Relationship richness:\t " + String.valueOf(new
		// DecimalFormat("##.####").format(RelationshipRichness)) +"\n"; //TO
		// DO: should correct : error of decimalFormat
		// res = res+ "\n Relationship richness:\t " + RelationshipRichness
		// +"\n";
		res[0] = String.valueOf(Math.round(RelationshipRichness* 10000.0) / 10000.0);
		/////////////////////////////////////////////////////////
		list = model.listDatatypeProperties().toList();
		float attRich = list.size() / (float) (countSub + llist);
		// res = res+ "\n Attribute relationship richness is:\t " +
		// String.valueOf(new DecimalFormat("##.####").format(attRich)) +"\n";
		// res = res+ "\n Attribute relationship richness is:\t " + attRich
		// +"\n";
		res[1] = String.valueOf(Math.round(attRich* 10000.0) / 10000.0);
		/////////////////////////////////////////////////////////////////////////////
		float InheritanceRichness = (float) countSub / (countSub + llist);
		// res = res+ "\n Inheritance richness:\t "+ String.valueOf(new
		// DecimalFormat("##.####").format(InheritanceRichness)) +"\n";
		// res = res+ "\n Inheritance richness:\t "+InheritanceRichness +"\n";
		res[2] = String.valueOf(Math.round(InheritanceRichness* 10000.0) / 10000.0);

		avDesign = (RelationshipRichness + attRich + InheritanceRichness) / 3.0;
		// res = res+ "\n \t \t the average ontolgy design
		// metric:\t"+String.valueOf(new
		// DecimalFormat("##.####").format(avDesign))+"\n";
		// res = res+ "\n The average ontolgy design metric:\t"+avDesign+"\n";
		res[3] = String.valueOf(Math.round(avDesign* 10000.0) / 10000.0);
		////////////////////////////////////////////////////////////////////////////////////////////

		// res = res+ "\n ==================== KB metrics =============" +"\n";
		list = model.listClasses().toList();
		int Incount = rbgm.getInstanceNodes().size();
		/*
		 * for(int i=0;i<list.size();i++) { OntClass node=(OntClass)
		 * list.get(i); ExtendedIterator li=model.listIndividuals(node); for
		 * (ExtendedIterator j =model.listIndividuals(node); j.hasNext(); ) {
		 * Individual instance = (Individual) j.next(); Incount++; }
		 * 
		 * }
		 */
		float classRich = Incount / (float) NumClass;
		if (classRich > 1)
			classRich = 1;
		// res = res+ "\n Class richness:\t "+ String.valueOf(new
		// DecimalFormat("##.####").format(classRich)) +"\n";
		// res = res+ "\n Class richness:\t "+ classRich +"\n";
		res[4] = String.valueOf(Math.round(classRich* 10000.0) / 10000.0);
		double PopulationRichness = 0;
		int NumInstanceNode = model.listIndividuals().toList().size();
		if (NumClass > 0) {
			PopulationRichness = (double) NumInstanceNode / (double) NumClass;
		}
		if (PopulationRichness > 1)
			PopulationRichness = 1;
		// res = res+ "\n Average population richness:\t " + String.valueOf(new
		// DecimalFormat("##.####").format(PopulationRichness)) +"\n";
		// res = res+ "\n Average population richness:\t " + PopulationRichness
		// +"\n";
		res[5] = String.valueOf(Math.round(PopulationRichness* 10000.0) / 10000.0);

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		double ReadabilityRichness = 0;
		int NumComment = 0;
		int NumLabel = 0;
		Iterator<Node> listclass2 = rbgm.listClassNodes();
		while (listclass2.hasNext()) {
			Node iclass = listclass2.next();
			if (iclass.getComment() != null) {
				NumComment++;
			}
			if (iclass.getLabel() != null) {
				NumLabel++;
			}
		}
		ReadabilityRichness = (NumComment + NumLabel) / (float) NumClass;
		if (ReadabilityRichness > 1)
			ReadabilityRichness = 1;
		// res = res+ "\n ----> The Readability Richness is:\t " +
		// String.valueOf(new
		// DecimalFormat("##.####").format(ReadabilityRichness)) +"\n";
		// res = res+ "\n The Readability Richness is:\t " + ReadabilityRichness
		// +"\n";
		res[6] = String.valueOf(Math.round(ReadabilityRichness* 10000.0) / 10000.0);

		avKB = (PopulationRichness + classRich + ReadabilityRichness) / 3.0;
		// res = res+ "\n \t\t the average KB metric:\t"+String.valueOf(new
		// DecimalFormat("##.####").format(avKB))+"\n";
		// res = res+ "\n The average KB metric:\t"+avKB +"\n";
		res[7] = String.valueOf(Math.round(avKB* 10000.0) / 10000.0);
		////////////////////////////////////////////////////////////////////////////////////////////

		// res = res+ "\n ==================== Class metrics ===========" +"\n";

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		double ConnectedRichness = 0;
		int NumRootClass = model.listHierarchyRootClasses().toList().size();// ithing.listSubClasses().toList().size();
		ConnectedRichness = (double) (NumClass - NumRootClass) / (double) NumClass;
		// res = res+ "\n The Connected componnet cohesion is:\t " +
		// String.valueOf(new
		// DecimalFormat("##.####").format(ConnectedRichness)) +"\n" ;
		// res = res+ "\n The Connected componnet cohesion is:\t " +
		// ConnectedRichness +"\n" ;
		res[8] = String.valueOf(Math.round(ConnectedRichness* 10000.0) / 10000.0);

		float[] ImportnaceClass = new float[NumClass];
		Iterator<Node> listclass = rbgm.listClassNodes();
		for (int i = 0; i < NumClass; i++) {
			int NumInsClassi = 0;
			Node iclass = listclass.next();
			NodeList listParent = iclass.getNamedSupers();
			if (listParent != null) {
				for (int j = 0; j < listParent.size(); j++) {
					Node iParent = listParent.get(j);
					Iterator<Node> listInstance = rbgm.listInstanceNodes();
					while (listInstance.hasNext()) {
						Node InsN = listInstance.next();
						if (InsN == iParent)
							NumInsClassi++; // we should calculate the number of
											// instance of class i that exist in
											// the root of class i
					}
				}
				if (NumInstanceNode > 0)
					ImportnaceClass[i] = (float) NumInsClassi / (float) NumInstanceNode;
			}
		}
		float sumImp = 0;
		for (int i = 0; i < NumClass; i++) {
			sumImp = sumImp + ImportnaceClass[i];
		}
		double AvgImprotnace = sumImp / (float) NumClass; // To calculate the
															// average
															// importance of
															// classes ontology
		// res = res+ "\n ----> The Average Importance class is:\t " +
		// String.valueOf(new DecimalFormat("##.####").format(AvgImprotnace))
		// +"\n";
		// res = res+ "\n The Average Importance class is:\t " + AvgImprotnace
		// +"\n";
		res[9] = String.valueOf(Math.round(AvgImprotnace* 10000.0) / 10000.0);

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		float ConceptRelativesRichness = 0;
		float SumAdjN = 0;
		Iterator<Node> listclass3 = rbgm.listClassNodes();
		while (listclass3.hasNext()) {
			Node iclass = listclass3.next();
			int CRRi = 0;
			NodeList subN = iclass.getNamedSubs(); // instead of it we can use
													// getAdjacentNodes?!
			if (subN != null) {
				CRRi = CRRi + subN.size();
			}
			NodeList supN = iclass.getNamedSupers(); // instead of it we can use
														// getAdjacentNodes?!
			if (supN != null) {
				CRRi = CRRi + supN.size();
				// calculating the number of iclass sibling i.e. calculate the
				// number of children for all icalss parents
				for (int i = 0; i < supN.size(); i++) {
					Node iParent = supN.get(i);
					NodeList allParent = iParent.getNamedSupers();
					if (allParent != null) {
						CRRi = CRRi + allParent.size();
					}
				}
			}
			SumAdjN = SumAdjN + ((float) (CRRi) / NumClass);
		}
		ConceptRelativesRichness = SumAdjN / (float) NumClass;
		// res = res+ "\n The Concept relatives Richness is:\t "+
		// String.valueOf(new
		// DecimalFormat("##.####").format(ConceptRelativesRichness)) +"\n";
		// res = res+ "\n The Concept relatives Richness is:\t "+
		// ConceptRelativesRichness +"\n";
		res[10] = String.valueOf(Math.round(ConceptRelativesRichness* 10000.0) / 10000.0);

		avClass = (ConceptRelativesRichness + ConnectedRichness) / 2.0;
		// res = res+ "\n \t\t the average class metric:\t"+String.valueOf(new
		// DecimalFormat("##.####").format(avClass))+"\n";
		// res = res+ "\n The average class metric:\t"+ avClass +"\n";
		res[11] = String.valueOf(Math.round(avClass* 10000.0) / 10000.0);

		// Total Richness
		TotalRichness = (avClass + avKB + avDesign) / 3.0;
		// res = res+ "\n ==================== total ontology richness
		// ===========" +"\n";
		// res = res+ '\n'+ "\t\t The ontology richness:\t "+ String.valueOf(new
		// DecimalFormat("##.####").format(TotalRichness)) +"\n";
		// res = res+ '\n'+ "The ontology richness:\t "+ TotalRichness +"\n";
		res[12] = String.valueOf(Math.round(TotalRichness* 10000.0) / 10000.0);
		return res;
	}

	public double computeDesignMertic() {
		int NumClass = model.listClasses().toList().size();// .listClasses().toList().size();
		// List list=model.listNamedClasses().toList();
		List list = model.listClasses().toList();
		int countSub = 0;
		for (int i = 0; i < list.size(); i++) {
			OntClass cls = (OntClass) list.get(i);
			ExtendedIterator it = cls.listSubClasses();
			while (it.hasNext()) {
				countSub++;
				it.next();
			}
		}
		list = model.listObjectProperties().toList();
		float RelationshipRichness = 0;
		int NumRelation = list.size();
		RelationshipRichness = (float) NumRelation / (float) (countSub + NumRelation);
		list = model.listDatatypeProperties().toList();
		float attRich = list.size() / (float) (countSub + list.size());
		float InheritanceRichness = (float) countSub / (countSub + NumRelation);
		if (InheritanceRichness > 1)
			InheritanceRichness = 1;
		avDesign = (0.3 * RelationshipRichness + 0.3 * attRich + 0.4 * InheritanceRichness);
		avDesign = Math.round(avDesign * 100) / 100.0d;
		return avDesign;
	}

	public double computeKBMetric() {
		// int NumClass=model.listNamedClasses().toList().size();
		int NumClass = model.listClasses().toList().size();
		int instNodes = rbgm.getInstanceNodes().size();
		float classRich = 0;// instNodes / (float)NumClass;
		// if(classRich>1) classRich=1;
		double PopulationRichness = 0;
		int NumInstanceNode = model.listIndividuals().toList().size();
		if (NumClass > 0) {
			PopulationRichness = (double) NumInstanceNode / (double) NumClass;
		}
		if (PopulationRichness > 1)
			PopulationRichness = 1;

		////////////////////////////////////////
		double ReadabilityRichness = 0;
		int NumComment = 0;
		int NumLabel = 0;
		Iterator<Node> listclass2 = rbgm.listClassNodes();
		while (listclass2.hasNext()) {
			Node iclass = listclass2.next();
			if (iclass.getComment() != null) {
				NumComment++;
			}
			if (iclass.getLabel() != null) {
				NumLabel++;
			}
		}
		int annlist = model.listAnnotationProperties().toList().size();
		ReadabilityRichness = (NumComment + NumLabel + annlist) / (float) NumClass;
		if (ReadabilityRichness > 1)
			ReadabilityRichness = 1;
		avKB = (PopulationRichness + classRich + ReadabilityRichness) / 3.0;
		avKB = Math.round(avKB * 100) / 100.0d;
		return avKB;
	}

	public double computeClassmetric() {
		int NumClass = model.listClasses().toList().size();
		// int NumClass=model.listNamedClasses().toList().size();
		double ConnectedRichness = 0;
		int NumRootClass = model.listHierarchyRootClasses().toList().size();
		ConnectedRichness = (double) (Math.abs(NumClass - NumRootClass)) / (double) NumClass;
		if (ConnectedRichness > 1)
			ConnectedRichness = 1;
		float SumAdjN = 0;
		Iterator<Node> listclass3 = rbgm.listClassNodes();
		while (listclass3.hasNext()) {
			Node iclass = listclass3.next();
			int CRRi = 0;
			NodeList subN = iclass.getNamedSubs(); // instead of it we can use
													// getAdjacentNodes?!
			if (subN != null) {
				CRRi = CRRi + subN.size();
			}
			NodeList supN = iclass.getNamedSupers(); // instead of it we can use
														// getAdjacentNodes?!
			if (supN != null) {
				CRRi = CRRi + supN.size();
				// calculating the number of iclass sibling i.e. calculate the
				// number of children for all icalss parents
				for (int i = 0; i < supN.size(); i++) {
					Node iParent = supN.get(i);
					NodeList allParent = iParent.getNamedSupers();
					if (allParent != null) {
						CRRi = CRRi + allParent.size();
					}
				}
			}
			SumAdjN += CRRi * (float) (CRRi) / NumClass;
		}
		SumAdjN /= NumClass;
		if (SumAdjN > 1)
			SumAdjN = 1;
		avClass = (SumAdjN + ConnectedRichness) / 2;
		avClass = Math.round(avClass * 100) / 100.0d;
		return avClass;
	}

	public double computeRichness() {
		TotalRichness = (avClass + avKB + avDesign) / 3.0;
		TotalRichness = Math.round(TotalRichness * 100) / 100.0d;
		return TotalRichness;
	}

	// ------------------------------------------------------------------------------------------------------------------
	public void RichnessTest2(Data data) {
		List list = new ArrayList();
		list = data.getOntModel().listClasses().toList();
		int countSub = 0;
		for (int i = 0; i < list.size(); i++) {
			OntClass cls = (OntClass) list.get(i);
			ExtendedIterator it = cls.listSubClasses();
			while (it.hasNext()) {
				countSub++;
				it.next();
			}
		}
		int NumClass = list.size();
		OPATgui.RichnessTextArea2.append("============ Ontology design  metrics========= " + "\n");
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		list = data.getOntModel().listObjectProperties().toList();
		float RelationshipRichness = 0;
		int NumRelation = list.size();
		RelationshipRichness = (float) NumRelation / (float) (countSub + NumRelation);
		OPATgui.RichnessTextArea2.append("\n Relationship richness:\t "
				+ String.valueOf(new DecimalFormat("##.####").format(RelationshipRichness)) + "\n");

		/////////////////////////////////////////////////////////
		list = data.getOntModel().listDatatypeProperties().toList();
		float attRich = list.size() / (float) NumClass;
		OPATgui.RichnessTextArea2.append("\n Attribute relationship richness is:\t "
				+ String.valueOf(new DecimalFormat("##.####").format(attRich)) + "\n");

		/////////////////////////////////////////////////////////////////////////////
		float InheritanceRichness = (float) countSub / NumClass;
		OPATgui.RichnessTextArea2.append("\n Inheritance richness:\t  "
				+ String.valueOf(new DecimalFormat("##.####").format(InheritanceRichness)) + "\n");
		double avDesign = (RelationshipRichness + attRich + InheritanceRichness) / 3.0;
		OPATgui.RichnessTextArea2.append("\n The average ontolgy design metric:\t"
				+ String.valueOf(new DecimalFormat("##.####").format(avDesign)) + "\n");
		////////////////////////////////////////////////////////////////////////////////////////////

		OPATgui.RichnessTextArea2.append("\n ==================== KB metrics  =============" + "\n");
		list = data.getOntModel().listClasses().toList();
		int Incount = 0;
		for (int i = 0; i < list.size(); i++) {
			OntClass node = (OntClass) list.get(i);
			ExtendedIterator li = data.getOntModel().listIndividuals(node);
			for (ExtendedIterator j = data.getOntModel().listIndividuals(node); j.hasNext();) {
				Individual instance = (Individual) j.next();
				Incount++;
			}

		}
		float classRich = Incount / (float) NumClass;
		OPATgui.RichnessTextArea2.append(
				"\n Class richness:\t  " + String.valueOf(new DecimalFormat("##.####").format(classRich)) + "\n");
		double PopulationRichness = 0;
		int NumInstanceNode = data.getOntModel().listIndividuals().toList().size();
		if (NumClass > 0) {
			PopulationRichness = (double) NumInstanceNode / (double) NumClass;
		}
		OPATgui.RichnessTextArea2.append("\n Average population richness:\t "
				+ String.valueOf(new DecimalFormat("##.####").format(PopulationRichness)) + "\n");

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		double ConnectedRichness = 0;
		OntClass ithing = data.getOntModel().getOntClass(OWL.Thing.getURI());
		int NumRootClass = ithing.listSubClasses().toList().size();
		ConnectedRichness = (double) (NumClass - NumRootClass) / (double) NumClass;
		OPATgui.RichnessTextArea2.append("\n The Connected componnet cohesion is:\t "
				+ String.valueOf(new DecimalFormat("##.####").format(ConnectedRichness)) + "\n");
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		double ReadabilityRichness = 0;
		int NumComment = 0;
		int NumLabel = 0;
		Iterator<Node> listclass2 = data.getRbgModel().listClassNodes();
		while (listclass2.hasNext()) {
			Node iclass = listclass2.next();
			if (iclass.getComment() != null) {
				NumComment++;
			}
			if (iclass.getLabel() != null) {
				NumLabel++;
			}
		}
		ReadabilityRichness = (NumComment + NumLabel) / (float) NumClass;
		OPATgui.RichnessTextArea2.append("\n The Readability Richness is:\t "
				+ String.valueOf(new DecimalFormat("##.####").format(ReadabilityRichness)) + "\n");

		double avKB = (ConnectedRichness + PopulationRichness + classRich + ReadabilityRichness) / 4.0;
		OPATgui.RichnessTextArea2.append(
				"\n The average KB metric:\t" + String.valueOf(new DecimalFormat("##.####").format(avKB)) + "\n");
		////////////////////////////////////////////////////////////////////////////////////////////

		OPATgui.RichnessTextArea2.append("\n ==================== Class metrics ===========" + "\n");

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		float[] ImportnaceClass = new float[NumClass];
		Iterator<Node> listclass = data.getRbgModel().listClassNodes();
		for (int i = 0; i < NumClass; i++) {
			int NumInsClassi = 0;
			Node iclass = listclass.next();
			NodeList listParent = iclass.getNamedSupers();
			if (listParent != null) {
				for (int j = 0; j < listParent.size(); j++) {
					Node iParent = listParent.get(j);
					Iterator<Node> listInstance = data.getRbgModel().listInstanceNodes();
					while (listInstance.hasNext()) {
						Node InsN = listInstance.next();
						if (InsN == iParent)
							NumInsClassi++; // we should calculate the number of
											// instance of class i that exist in
											// the root of class i
					}
				}
				if (NumInstanceNode > 0)
					ImportnaceClass[i] = (float) NumInsClassi / (float) NumInstanceNode;
			}
		}
		float sumImp = 0;
		for (int i = 0; i < NumClass; i++) {
			sumImp = sumImp + ImportnaceClass[i];
		}
		float AvgImprotnace = sumImp / (float) NumClass; // To calculate the
															// average
															// importance of
															// classes ontology
		OPATgui.RichnessTextArea2.append("\n The Average Importance class is:\t  "
				+ String.valueOf(new DecimalFormat("##.####").format(AvgImprotnace)) + "\n");
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		float ConceptRelativesRichness = 0;
		float SumAdjN = 0;
		Iterator<Node> listclass3 = data.getRbgModel().listClassNodes();
		while (listclass3.hasNext()) {
			Node iclass = listclass3.next();
			int CRRi = 0;
			NodeList subN = iclass.getNamedSubs(); // instead of it we can use
													// getAdjacentNodes?!
			if (subN != null) {
				CRRi = CRRi + subN.size();
			}
			NodeList supN = iclass.getNamedSupers(); // instead of it we can use
														// getAdjacentNodes?!
			if (supN != null) {
				CRRi = CRRi + supN.size();
				// calculating the number of iclass sibling i.e. calculate the
				// number of children for all icalss parents
				for (int i = 0; i < supN.size(); i++) {
					Node iParent = supN.get(i);
					NodeList allParent = iParent.getNamedSupers();
					if (allParent != null) {
						CRRi = CRRi + allParent.size();
					}
				}
			}
			SumAdjN = SumAdjN + ((float) (CRRi) / NumClass);
		}
		ConceptRelativesRichness = SumAdjN / (float) NumClass;
		OPATgui.RichnessTextArea2.append("\n The Concept relatives Richness is:\t "
				+ String.valueOf(new DecimalFormat("##.####").format(ConceptRelativesRichness)) + "\n");

		double avClass = (ConceptRelativesRichness + AvgImprotnace) / 2.0;
		OPATgui.RichnessTextArea2.append(
				"\n The average class metric:\t" + String.valueOf(new DecimalFormat("##.####").format(avClass)) + "\n");

		// Total Richness
		double TotalRichness = (avClass + avKB + avDesign) / 3.0;
		OPATgui.RichnessTextArea2.append("\n ==================== total ontology richness ===========" + "\n");
		OPATgui.RichnessTextArea2.append('\n' + "The ontology richness:\t  "
				+ String.valueOf(new DecimalFormat("##.####").format(TotalRichness)) + "\n");

	}

}
