package fusion.comerger.algorithm.merger.holisticMerge.evaluator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedDpro;
import fusion.comerger.algorithm.merger.model.HModel;

public class ConstraintEval {
	public static String tick = "<img src=\"/layout/images/tick.jpg\">";
	public static String cross = "<img src=\"/layout/images/cross.jpg\">";
	static String msgCorrectness = "Repair it!";

	// ************************************************************************
	public static String[] OneTypeRestrictionEval(HModel ontM) {
		// TODO correct it
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();

		int counter = 0;
		ArrayList<HMappedDpro> eqDpro = ontM.getEqDataProperties();
		for (int i = 0; i < eqDpro.size(); i++) {
			HMappedDpro d = eqDpro.get(i);
			Iterator<OWLDataProperty> dproIter = d.getMappedDpro().iterator();
			while (dproIter.hasNext()) {
				OWLDataProperty dp = dproIter.next();
				OWLDataProperty eqDp = ontM.getKeyValueEqDataPro().get(dp);
				if (eqDp != null)
					dp = eqDp;
				Iterator<OWLDataRange> a = dp.getRanges(ontM.getOwlModel()).iterator();
				// dp.getDomains(ontM.getOwlModel())
				ArrayList<OWLDataRange> temp = new ArrayList<OWLDataRange>();
				while (a.hasNext()) {
					OWLDataRange b = a.next();
					temp.add(b);
				}
				for (int j = 0; j < temp.size(); j++) {
					for (int k = j + 1; k < temp.size(); k++) {
						if (!temp.get(j).equals(temp.get(k))) {
							// you find it!
							counter++;
							MissArray.add(dp.toString());
						}
					}
				}
			}
		}

		res[0] = "-";
		res[1] = "-";
		StatisticTest.result.put("oneType_restriction", String.valueOf(0));
		return res;
	}

	// ************************************************************************
	public static String[] ValueConstraintEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int counter = 0;

		for (int i = 0; i < ontM.getInputOntNumber(); i++) {
			Iterator<OWLSubClassOfAxiom> axIter = ontM.getInputOwlOntModel().get(i).getAxioms(AxiomType.SUBCLASS_OF)
					.iterator();
			while (axIter.hasNext()) {
				OWLAxiom axx = axIter.next();
				OWLClassExpression SuperClassOi = ((OWLSubClassOfAxiom) axx).getSuperClass();
				String type = SuperClassOi.getClassExpressionType().getName();

				switch (type) {

				case "ObjectMaxCardinality":
					int sOi = ((OWLObjectMaxCardinality) SuperClassOi).getCardinality();
					if (SuperClassOi instanceof OWLObjectMaxCardinality) {
						OWLObjectPropertyExpression objOi = ((OWLObjectMaxCardinality) SuperClassOi).getProperty();
						OWLObjectPropertyExpression SuperclassOm = ontM.getKeyValueEqObjProperty().get(objOi);
						if (SuperclassOm == null)
							SuperclassOm = objOi;
						Iterator<OWLAxiom> omAxIter = ontM.getOwlModel().getReferencingAxioms((OWLEntity) SuperclassOm)
								.iterator();
						while (omAxIter.hasNext()) {
							OWLAxiom x = omAxIter.next();
							if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
								OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) x).getSuperClass();
								if (SuperClassOm instanceof OWLObjectMaxCardinality) {
									int sOm = ((OWLObjectMaxCardinality) SuperClassOm).getCardinality();
									if (sOi != sOm) {
										// You find it;
										MissArray.add(SuperclassOm.toString());
										// to correct it, set a new cardinality
										// for this property:
										// OWLObjectMaxCardinality cardi =
										// factory.getOWLObjectMaxCardinality(sOi,
										// (OWLObjectPropertyExpression)
										// SubClassOm);
										// OWLAxiom newAxiom =
										// factory.getOWLSubClassOfAxiom(SubClassOi,
										// cardi);
										// manager.addAxiom(Om, newAxiom);
										// manager.removeAxiom(Om, x);
									}

								}
							}
						}
					}

					break;

				case "ObjectMinCardinality":
					sOi = ((OWLObjectMinCardinality) SuperClassOi).getCardinality();
					if (SuperClassOi instanceof OWLObjectMinCardinality) {
						OWLObjectPropertyExpression objOi = ((OWLObjectMinCardinality) SuperClassOi).getProperty();
						OWLObjectPropertyExpression SuperclassOm = ontM.getKeyValueEqObjProperty().get(objOi);
						if (SuperclassOm == null)
							SuperclassOm = objOi;
						Iterator<OWLAxiom> omAxIter = ontM.getOwlModel().getReferencingAxioms((OWLEntity) SuperclassOm)
								.iterator();
						while (omAxIter.hasNext()) {
							OWLAxiom x = omAxIter.next();
							if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
								OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) x).getSuperClass();
								if (SuperClassOm instanceof OWLObjectMinCardinality) {
									int sOm = ((OWLObjectMinCardinality) SuperClassOm).getCardinality();
									if (sOi != sOm) {
										// You find it;
										MissArray.add(SuperclassOm.toString());
										// repair it?
									}

								}
							}
						}
					}
					break;

				case "ObjectExactCardinality":
					sOi = ((OWLObjectExactCardinality) SuperClassOi).getCardinality();
					if (SuperClassOi instanceof OWLObjectExactCardinality) {
						OWLObjectPropertyExpression objOi = ((OWLObjectExactCardinality) SuperClassOi).getProperty();
						OWLObjectPropertyExpression SuperclassOm = ontM.getKeyValueEqObjProperty().get(objOi);
						if (SuperclassOm == null)
							SuperclassOm = objOi;
						Iterator<OWLAxiom> omAxIter = ontM.getOwlModel().getReferencingAxioms((OWLEntity) SuperclassOm)
								.iterator();
						while (omAxIter.hasNext()) {
							OWLAxiom x = omAxIter.next();
							if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
								OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) x).getSuperClass();
								if (SuperClassOm instanceof OWLObjectExactCardinality) {
									int sOm = ((OWLObjectExactCardinality) SuperClassOm).getCardinality();
									if (sOi != sOm) {
										// You find it;
										MissArray.add(SuperclassOm.toString());
										// repair it?
									}

								}
							}
						}
					}
					break;

				case "DataMaxCardinality":
					sOi = ((OWLDataMaxCardinality) SuperClassOi).getCardinality();
					if (SuperClassOi instanceof OWLDataMaxCardinality) {
						OWLDataPropertyExpression objOi = ((OWLDataMaxCardinality) SuperClassOi).getProperty();
						OWLDataPropertyExpression SuperclassOm = ontM.getKeyValueEqDataPro().get(objOi);
						if (SuperclassOm == null)
							SuperclassOm = objOi;
						Iterator<OWLAxiom> omAxIter = ontM.getOwlModel().getReferencingAxioms((OWLEntity) SuperclassOm)
								.iterator();
						while (omAxIter.hasNext()) {
							OWLAxiom x = omAxIter.next();
							if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
								OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) x).getSuperClass();
								if (SuperClassOm instanceof OWLDataMaxCardinality) {
									int sOm = ((OWLDataMaxCardinality) SuperClassOm).getCardinality();
									if (sOi != sOm) {
										// You find it;
										MissArray.add(SuperclassOm.toString());
										// repair it?
									}

								}
							}
						}
					}
					break;

				case "DataMinCardinality":
					sOi = ((OWLDataMinCardinality) SuperClassOi).getCardinality();
					if (SuperClassOi instanceof OWLDataMinCardinality) {
						OWLDataPropertyExpression objOi = ((OWLDataMinCardinality) SuperClassOi).getProperty();
						OWLDataPropertyExpression SuperclassOm = ontM.getKeyValueEqDataPro().get(objOi);
						if (SuperclassOm == null)
							SuperclassOm = objOi;
						Iterator<OWLAxiom> omAxIter = ontM.getOwlModel().getReferencingAxioms((OWLEntity) SuperclassOm)
								.iterator();
						while (omAxIter.hasNext()) {
							OWLAxiom x = omAxIter.next();
							if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
								OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) x).getSuperClass();
								if (SuperClassOm instanceof OWLDataMinCardinality) {
									int sOm = ((OWLDataMinCardinality) SuperClassOm).getCardinality();
									if (sOi != sOm) {
										// You find it;
										MissArray.add(SuperclassOm.toString());
										// repair it?
									}

								}
							}
						}
					}
					break;

				case "DataExactCardinality":
					sOi = ((OWLDataExactCardinality) SuperClassOi).getCardinality();
					if (SuperClassOi instanceof OWLDataExactCardinality) {
						OWLDataPropertyExpression objOi = ((OWLDataExactCardinality) SuperClassOi).getProperty();
						OWLDataPropertyExpression SuperclassOm = ontM.getKeyValueEqDataPro().get(objOi);
						if (SuperclassOm == null)
							SuperclassOm = objOi;
						Iterator<OWLAxiom> omAxIter = ontM.getOwlModel().getReferencingAxioms((OWLEntity) SuperclassOm)
								.iterator();
						while (omAxIter.hasNext()) {
							OWLAxiom x = omAxIter.next();
							if (x.getAxiomType().equals(AxiomType.SUBCLASS_OF)) {
								OWLClassExpression SuperClassOm = ((OWLSubClassOfAxiom) x).getSuperClass();
								if (SuperClassOm instanceof OWLDataExactCardinality) {
									int sOm = ((OWLDataExactCardinality) SuperClassOm).getCardinality();
									if (sOi != sOm) {
										// You find it;
										MissArray.add(SuperclassOm.toString());
										// repair it?
									}

								}
							}
						}
					}
					break;

				case "ObjectSomeValuesFrom":
					OWLObjectPropertyExpression pro = ((OWLObjectSomeValuesFrom) SuperClassOi).getProperty();
					Set<OWLClass> cl = ((OWLObjectSomeValuesFrom) SuperClassOi).getClassesInSignature();

					OWLObjectProperty eqPro = ontM.getKeyValueEqObjProperty().get(pro);
					if (eqPro != null)
						pro = eqPro;
					Iterator<OWLAxiom> omAxIter = ontM.getOwlModel().getReferencingAxioms((OWLEntity) pro).iterator();
					while (omAxIter.hasNext()) {
						OWLAxiom x = omAxIter.next();
						if (x instanceof OWLSubClassOfAxiom) {
							OWLClassExpression sp = ((OWLSubClassOfAxiom) x).getSuperClass();
							if (sp instanceof OWLObjectSomeValuesFrom) {
								Set<OWLClass> clOm = ((OWLObjectSomeValuesFrom) sp).getClassesInSignature();
								boolean differ = compare(cl, clOm, ontM);
								if (differ == false)
									MissArray.add(pro.toString());
							}
						}
					}

					break;

				case "ObjectAllValuesFrom":
					pro = ((OWLObjectAllValuesFrom) SuperClassOi).getProperty();
					cl = ((OWLObjectAllValuesFrom) SuperClassOi).getClassesInSignature();

					eqPro = ontM.getKeyValueEqObjProperty().get(pro);
					if (eqPro != null)
						pro = eqPro;
					omAxIter = ontM.getOwlModel().getReferencingAxioms((OWLEntity) pro).iterator();
					while (omAxIter.hasNext()) {
						OWLAxiom x = omAxIter.next();
						if (x instanceof OWLSubClassOfAxiom) {
							OWLClassExpression sp = ((OWLSubClassOfAxiom) x).getSuperClass();
							if (sp instanceof OWLObjectAllValuesFrom) {
								Set<OWLClass> clOm = ((OWLObjectAllValuesFrom) sp).getClassesInSignature();
								boolean differ = compare(cl, clOm, ontM);
								if (differ == false)
									MissArray.add(pro.toString());
							}
						}
					}
					break;

				}
			}
		}

		// Report the result
		counter = MissArray.size();
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
			res[1] = "<span style=\"color: red;\">Properties ith conflict on their contraints are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replaceAll("<", "[").replaceAll(">", "]");
			}
			res[1] = res[1] + "</span>";
			String id = "ConstValCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">There is no property with conflict constraint.</span>";
		}

		StatisticTest.result.put("value_constraint", String.valueOf(counter));
		return res;
	}

	private static boolean compare(Set<OWLClass> cl, Set<OWLClass> clOm, HModel ontM) {
		Iterator<OWLClass> iterOi = cl.iterator();
		while (iterOi.hasNext()) {
			OWLClass c = iterOi.next();
			OWLClass eqC = ontM.getKeyValueEqClass().get(c);
			if (eqC != null)
				c = eqC;
			if (!clOm.contains(c)) {
				return false;
			}
		}
		return true;
	}

	// ************************************************************************
	public static String[] DomainRangeMinimalityEval(HModel ontM) {
		String[] res = new String[2];
		HashSet<String> MissArray = new HashSet<String>();
		int counter = 0;
		OWLOntology Om = ontM.getOwlModel();
		double total = Om.getObjectPropertiesInSignature().size();
		Iterator<OWLObjectProperty> iter = Om.getObjectPropertiesInSignature().iterator();
		while (iter.hasNext()) {
			OWLObjectProperty obj = iter.next();
			// For multiple domains
			Set<OWLClassExpression> domain = obj.getDomains(Om);
			if (domain.size() > 1) {
				counter++;
				MissArray.add(obj.toString());
			}

			// For multiple ranges
			Set<OWLClassExpression> range = obj.getRanges(Om);
			if (range.size() > 1) {
				counter++;
				MissArray.add(obj.toString());
			}
		}

		StatisticTest.result.put("oness", String.valueOf(counter));

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
			res[1] = "<span style=\"color: red;\">Properties with multiple domains or ranges are:";
			Iterator<String> arrayIter = MissArray.iterator();
			while (arrayIter.hasNext()) {
				String cc = arrayIter.next();
				res[1] = res[1] + "<br>  -> " + cc.replaceAll("<", "[").replaceAll(">", "]");
			}
			res[1] = res[1] + "</span>";
			String id = "DomRangMinCheck";
			String correctIt = "<br> <br> <p><b><u><input type=\"checkbox\" name=\"repairs\" value=\"" + id + "\" "
					+ ">" + msgCorrectness + "</u></b></p>";
			res[1] = res[1] + correctIt;
		} else {
			res[1] = "<span style=\"color: green;\">All properties have unique domain and range.</span>";
		}

		return res;
	}
}
