package fusion.comerger.algorithm.merger.holisticMerge.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;
import fusion.comerger.algorithm.merger.model.HModel;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;

public class CentralityCalculator {

	public ArrayList<HMappedClass> run(HModel ontM, ArrayList<HMappedClass> cSet) {
		// Calculate ClossnessCentrailty only for cSet
		HashMap<OWLClass, Integer> indexNodeClassArray = create_index_file(ontM);
		 int[][] AdjacencyMatrixOnt = CreateAdjacencyMatrix(ontM,
		 indexNodeClassArray);
	

		return calculate_Centrality(AdjacencyMatrixOnt, indexNodeClassArray, cSet, ontM);
		// return centrality of cSet with their sort value
	}

	public HashMap<OWLClass, Double> runClossnessForAll(HModel ontM, ArrayList<OWLClass> cSet) {
		// Calculate ClossnessCentrailty only for cSet
		HashMap<OWLClass, Integer> indexNodeClassArray = create_index_file_forAll(ontM);
		int[][] AdjacencyMatrixOnt = CreateAdjacencyMatrix_forAll(ontM, indexNodeClassArray);

		HashMap<OWLClass, Double> CC = calculate_Centrality_ForAll(AdjacencyMatrixOnt, indexNodeClassArray, cSet, ontM);
		// return centrality of cSet with their sort value
		return CC;
	}

	private ArrayList<HMappedClass> calculate_Centrality(int[][] AdjacencyMatrixOnt,
			HashMap<OWLClass, Integer> indexNodeClassArray, ArrayList<HMappedClass> cSet, HModel ontM) {
		ClossnessCentrality cc = new ClossnessCentrality();
		// HashMap<HMappedClass, Double> CC = new HashMap<HMappedClass,
		// Double>();
		return cc.run(AdjacencyMatrixOnt, indexNodeClassArray, cSet);
	}

	private HashMap<OWLClass, Double> calculate_Centrality_ForAll(int[][] AdjacencyMatrixOnt,
			HashMap<OWLClass, Integer> indexNodeClassArray, ArrayList<OWLClass> cSet, HModel ontM) {
		ClossnessCentrality cc = new ClossnessCentrality();
		HashMap<OWLClass, Double> CC = new HashMap<OWLClass, Double>();
		CC = cc.runForAll(AdjacencyMatrixOnt, indexNodeClassArray, cSet);

		return CC;
	}


	private int[][] CreateAdjacencyMatrix(HModel ontM, HashMap<OWLClass, Integer> indexNodeClassArray) {
		int NumNodeClass = ontM.getOwlModel().getClassesInSignature().size();
		int[][] AdjacencyMatrixOnt = new int[NumNodeClass][NumNodeClass];
		int indexSubject = -1;
		int indexObject = -1;

		Iterator<OWLSubClassOfAxiom> is_a = ontM.getOwlModel().getAxioms(AxiomType.SUBCLASS_OF).iterator();
		while (is_a.hasNext()) {
			OWLSubClassOfAxiom axiom = is_a.next();
			OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) axiom).getSuperClass();
			OWLClassExpression SubClass = ((OWLSubClassOfAxiom) axiom).getSubClass();

			if (SuperClass instanceof OWLClassImpl && SubClass instanceof OWLClassImpl) {
			
				OWLClass cRefSuper = ontM.getKeyValueEqClass().get(SuperClass);
				if (cRefSuper != null) {
					indexSubject = findIndex(cRefSuper, indexNodeClassArray);
				} else {
					indexSubject = findIndex(SuperClass, indexNodeClassArray);
				}

				OWLClass cRefSub = ontM.getKeyValueEqClass().get(SubClass);
				if (cRefSub != null) {
					indexSubject = findIndex(cRefSub, indexNodeClassArray);
				} else {
					indexObject = findIndex(SubClass, indexNodeClassArray);
				}
				if (indexSubject >= 0 && indexObject >= 0) {
					if (indexObject != indexSubject) { 
						AdjacencyMatrixOnt[indexSubject][indexObject] = 1;
						indexSubject = -1;
						indexObject = -1;
					}
				}
			}

		}
	

		return AdjacencyMatrixOnt;
	}

	
	private int[][] CreateAdjacencyMatrix_forAll(HModel ontM, HashMap<OWLClass, Integer> indexNodeClassArray) {
		int NumNodeClass = ontM.getOwlModel().getClassesInSignature().size();
		int[][] AdjacencyMatrixOnt = new int[NumNodeClass][NumNodeClass];
		int indexSubject = -1;
		int indexObject = -1;

		Iterator<OWLSubClassOfAxiom> is_a = ontM.getOwlModel().getAxioms(AxiomType.SUBCLASS_OF).iterator();
		while (is_a.hasNext()) {
			OWLSubClassOfAxiom axiom = is_a.next();
			OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) axiom).getSuperClass();
			OWLClassExpression SubClass = ((OWLSubClassOfAxiom) axiom).getSubClass();

			if (SuperClass instanceof OWLClassImpl && SubClass instanceof OWLClassImpl) {
				indexSubject = findIndex(SuperClass, indexNodeClassArray);
				indexObject = findIndex(SubClass, indexNodeClassArray);

				if (indexSubject >= 0 && indexObject >= 0) {
					if (indexObject != indexSubject) { // baraye ein ke ghotre
														// asli
														// bayad 0 bashe
						AdjacencyMatrixOnt[indexSubject][indexObject] = 1;
						indexSubject = -1;
						indexObject = -1;
					}
				}
			}

		}

		return AdjacencyMatrixOnt;
	}

	public static int findIndex(OWLClassExpression cl, HashMap<OWLClass, Integer> indexNodeClassArray) {
		int indexed = -1;
		if (cl != null) {
			if (cl.equals("Thing")) {
				indexed = -1;
			} else {
				if (indexNodeClassArray.get(cl) != null) {
					indexed = indexNodeClassArray.get(cl);
				}
			}
		}
		return indexed;
	}

	private HashMap<OWLClass, Integer> create_index_file(HModel ontM) {
		HashMap<OWLClass, Integer> indexNodeClassArray = indexNodeClassArray = new HashMap<OWLClass, Integer>();

		Iterator<OWLClass> allClass = ontM.getOwlModel().getClassesInSignature().iterator();
		int index = 0;
		while (allClass.hasNext()) {
			OWLClass c = allClass.next();
			// OWLClass cRef = ShareFunctions.hasRefClass(c, ontM);
			OWLClass cRef = ontM.getKeyValueEqClass().get(c);
			if (cRef != null)
				c = cRef;

			indexNodeClassArray.put(c, index);

			index++;
		}
		return indexNodeClassArray;

	}

	private HashMap<OWLClass, Integer> create_index_file_forAll(HModel ontM) {
		HashMap<OWLClass, Integer> indexNodeClassArray = indexNodeClassArray = new HashMap<OWLClass, Integer>();

		Iterator<OWLClass> allClass = ontM.getOwlModel().getClassesInSignature().iterator();
		int index = 0;
		while (allClass.hasNext()) {
			OWLClass c = allClass.next();
			indexNodeClassArray.put(c, index);
			index++;
		}
		return indexNodeClassArray;

	}

}
