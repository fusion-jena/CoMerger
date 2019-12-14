package fusion.comerger.algorithm.merger.holisticMerge.clustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.util.OWLEntityRemover;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedDpro;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedObj;
import fusion.comerger.algorithm.merger.model.HModel;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectUnionOfImpl;

public class AssignClusterProperties {
	public HModel run(HModel ontM) {
		long startTime = System.currentTimeMillis();
		ArrayList<ClusterModel> clusterList = ontM.getClusters();

		/*
		 * In this function, we add the properties of the classes to each
		 * cluster.
		 */

		/*
		 * if the properties refer to classes which these classes are separated
		 * between clusters (i.e. all are not in one cluster), then we call them
		 * breaking axioms. We keep them for our statistics
		 */
		HashSet<OWLAxiom> brIsaAx = new HashSet<OWLAxiom>();
		HashSet<OWLAxiom> brAllAx = new HashSet<OWLAxiom>();
		ontM.SetOtherBreakingAxiom(brAllAx);
		ontM.SetISABreakingAxiom(brIsaAx);
		if (ontM.getClusters() != null && ontM.getClusters().size() == 1) {
			ontM.getClusters().get(0).setManager(ontM.getManager());
			ontM.getClusters().get(0).setOntology(ontM.getOwlModel());
			ArrayList<OWLClassExpression> elm = new ArrayList<OWLClassExpression>(
					ontM.getOwlModel().getClassesInSignature());
			ontM.getClusters().get(0).SetClasses(elm);
		} else if (ontM.getClusters() != null) {
			ontM = processAxiomsWithOrder(ontM);
		}

		ontM.SetClusters(clusterList);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Adding the properties of the classes to each cluster has been done successfully. Total time:  "
						+ elapsedTime + " ms. \n");
		StatisticTest.result.put("time_assign_pro_to_cluster", String.valueOf(elapsedTime));

		return ontM;
	}

	private HModel processAxiomsWithOrder(HModel ontM) {

		OWLOntology Om = ontM.getOwlModel();

		Iterator<OWLSubClassOfAxiom> axiter = Om.getAxioms(AxiomType.SUBCLASS_OF).iterator();
		while (axiter.hasNext())
			ontM = SubClassAdder(axiter.next(), ontM);

		Iterator<OWLEquivalentClassesAxiom> axiter2 = Om.getAxioms(AxiomType.EQUIVALENT_CLASSES).iterator();
		while (axiter2.hasNext())
			ontM = EquivalentClassesAdder(axiter2.next(), ontM);

		Iterator<OWLObjectPropertyRangeAxiom> axiter3 = Om.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE).iterator();
		while (axiter3.hasNext())
			ontM = ObjectPropertyRangeAdder(axiter3.next(), ontM);

		Iterator<OWLObjectPropertyDomainAxiom> axiter4 = Om.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN).iterator();
		while (axiter4.hasNext())
			ontM = ObjectPropertyDomainAdder(axiter4.next(), ontM);

		Iterator<OWLSubObjectPropertyOfAxiom> axiter7 = Om.getAxioms(AxiomType.SUB_OBJECT_PROPERTY).iterator();
		while (axiter7.hasNext())
			ontM = SubObjectPropertyOfAdder(axiter7.next(), ontM);

		Iterator<OWLDataPropertyDomainAxiom> axiter11 = Om.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN).iterator();
		while (axiter11.hasNext())
			ontM = DataPropertyDomainAdder(axiter11.next(), ontM);

		Iterator<OWLDataPropertyRangeAxiom> axiter12 = Om.getAxioms(AxiomType.DATA_PROPERTY_RANGE).iterator();
		while (axiter12.hasNext())
			ontM = DataPropertyRangeAdder(axiter12.next(), ontM);

		Iterator<OWLInverseObjectPropertiesAxiom> axiter5 = Om.getAxioms(AxiomType.INVERSE_OBJECT_PROPERTIES)
				.iterator();
		while (axiter5.hasNext())
			ontM = InverseObjectPropertiesAdder(axiter5.next(), ontM);

		Iterator<OWLInverseFunctionalObjectPropertyAxiom> axiter6 = Om
				.getAxioms(AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY).iterator();
		while (axiter6.hasNext())
			ontM = InverseFunctionalObjectPropertyAdder(axiter6.next(), ontM);

		Iterator<OWLFunctionalDataPropertyAxiom> axiter8 = Om.getAxioms(AxiomType.FUNCTIONAL_DATA_PROPERTY).iterator();
		while (axiter8.hasNext())
			ontM = FunctionalDataPropertyAdder(axiter8.next(), ontM);

		Iterator<OWLFunctionalObjectPropertyAxiom> axiter9 = Om.getAxioms(AxiomType.FUNCTIONAL_OBJECT_PROPERTY)
				.iterator();
		while (axiter9.hasNext())
			ontM = FunctionalObjectPropertyAdder(axiter9.next(), ontM);

		Iterator<OWLDisjointClassesAxiom> axiter10 = Om.getAxioms(AxiomType.DISJOINT_CLASSES).iterator();
		while (axiter10.hasNext())
			ontM = DisjointClassesAdder(axiter10.next(), ontM);

		Iterator<OWLAnnotationAssertionAxiom> axiter13 = Om.getAxioms(AxiomType.ANNOTATION_ASSERTION).iterator();
		while (axiter13.hasNext())
			ontM = AnnotationAssertionAdder(axiter13.next(), ontM);

		Iterator<OWLClassAssertionAxiom> axiter14 = Om.getAxioms(AxiomType.CLASS_ASSERTION).iterator();
		while (axiter14.hasNext())
			ontM = InsatncesAdder(axiter14.next(), ontM);

		Iterator<OWLDifferentIndividualsAxiom> axiter15 = Om.getAxioms(AxiomType.DIFFERENT_INDIVIDUALS).iterator();
		while (axiter15.hasNext())
			ontM = DifferentIndividualsAdder(axiter15.next(), ontM);

		Iterator<OWLTransitiveObjectPropertyAxiom> axiter16 = Om.getAxioms(AxiomType.TRANSITIVE_OBJECT_PROPERTY)
				.iterator();
		while (axiter16.hasNext())
			ontM = TransitiveObjectPropertyAdder(axiter16.next(), ontM);

		Iterator<OWLSymmetricObjectPropertyAxiom> axiter17 = Om.getAxioms(AxiomType.SYMMETRIC_OBJECT_PROPERTY)
				.iterator();
		while (axiter17.hasNext())
			ontM = SymmetricObjectPropertyAdder(axiter17.next(), ontM);

		return ontM;
	}

	/* ****************************************************************** */

	private static HModel ObjectPropertyRangeAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntologyManager ClusterManager = null;
		OWLOntology ClusterOntology = null;

		OWLClassExpression rang = ((OWLObjectPropertyRangeAxiom) myAxiom).getRange();

		if (rang instanceof OWLClassImpl) {
			OWLClass eqRang = rang.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(rang.asOWLClass());
			if (refClass != null) {
				eqRang = refClass;
			}
			int clusterId = ClusterShareFunc.findClusterOfClass(eqRang, ontM);
			if (clusterId != -1) {
				ClusterManager = ontM.getClusters().get(clusterId).getManager();
				ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
				ClusterManager.addAxiom(ClusterOntology, myAxiom);
				ontM.getClusters().get(clusterId).setManager(ClusterManager);
			} else {
				HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
				brAx.add(myAxiom);
				ontM.SetOtherBreakingAxiom(brAx);
			}
		} else if (rang instanceof OWLObjectUnionOf) {
			Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) rang).getOperands().iterator();
			while (rangExist.hasNext()) {
				OWLClassExpression ex = rangExist.next();
				if (ex instanceof OWLClass) {
					OWLClass eq = ex.asOWLClass();
					OWLClass refClass = ontM.getKeyValueEqClass().get(ex.asOWLClass());
					if (refClass != null) {
						eq = refClass;
					}

					int clusterId = ClusterShareFunc.findClusterOfClass(eq, ontM);
					if (clusterId != -1) {
						ClusterManager = ontM.getClusters().get(clusterId).getManager();
						ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
						ClusterManager.addAxiom(ClusterOntology, myAxiom);
						ontM.getClusters().get(clusterId).setManager(ClusterManager);
					} else {
						HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
						brAx.add(myAxiom);
						ontM.SetOtherBreakingAxiom(brAx);
					}
				}
				// no more iterations needed, decide based on the 1st class
				break;
			}

		} else {
			String msg = "The subClassOf axiom: " + myAxiom + " could not be added to any clusters!";
			MyLogging.log(Level.WARNING, msg);
		}

		return ontM;
	}

	private static HModel SymmetricObjectPropertyAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;

		OWLObjectPropertyExpression subProperty = ((OWLSymmetricObjectPropertyAxiom) myAxiom).getProperty();
		OWLObjectProperty eqObj = ontM.getKeyValueEqObjProperty().get(subProperty.asOWLObjectProperty());
		if (eqObj != null)
			subProperty = eqObj;

		int clusterId = ClusterShareFunc.findClusterOfPro(subProperty.asOWLObjectProperty(), ontM);
		if (clusterId != -1) {
			ClusterManager = ontM.getClusters().get(clusterId).getManager();
			ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
			ClusterManager.addAxiom(ClusterOntology, myAxiom);
			ontM.getClusters().get(clusterId).setManager(ClusterManager);
		} else {
			HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
			brAx.add(myAxiom);
			ontM.SetOtherBreakingAxiom(brAx);
		}
		return ontM;

	}

	private static HModel TransitiveObjectPropertyAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;

		OWLObjectPropertyExpression subProperty = ((OWLTransitiveObjectPropertyAxiom) myAxiom).getProperty();
		OWLObjectProperty eqObj = ontM.getKeyValueEqObjProperty().get(subProperty.asOWLObjectProperty());
		if (eqObj != null)
			subProperty = eqObj;
		int clusterId = ClusterShareFunc.findClusterOfPro(subProperty.asOWLObjectProperty(), ontM);
		if (clusterId != -1) {
			ClusterManager = ontM.getClusters().get(clusterId).getManager();
			ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
			ClusterManager.addAxiom(ClusterOntology, myAxiom);
			ontM.getClusters().get(clusterId).setManager(ClusterManager);
		} else {
			HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
			brAx.add(myAxiom);
			ontM.SetOtherBreakingAxiom(brAx);
		}

		return ontM;
	}

	private static HModel FunctionalObjectPropertyAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;

		OWLObjectPropertyExpression subProperty = ((OWLFunctionalObjectPropertyAxiom) myAxiom).getProperty();
		OWLObjectProperty eqObj = ontM.getKeyValueEqObjProperty().get(subProperty.asOWLObjectProperty());
		if (eqObj != null)
			subProperty = eqObj;

		int clusterId = ClusterShareFunc.findClusterOfPro(subProperty.asOWLObjectProperty(), ontM);
		if (clusterId != -1) {
			ClusterManager = ontM.getClusters().get(clusterId).getManager();
			ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
			ClusterManager.addAxiom(ClusterOntology, myAxiom);
			ontM.getClusters().get(clusterId).setManager(ClusterManager);
		} else {
			HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
			brAx.add(myAxiom);
			ontM.SetOtherBreakingAxiom(brAx);
		}

		return ontM;

	}

	private static HModel FunctionalDataPropertyAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;

		OWLDataPropertyExpression subProperty = ((OWLFunctionalDataPropertyAxiom) myAxiom).getProperty();

		OWLDataProperty eqObj = ontM.getKeyValueEqDataPro().get(subProperty.asOWLDataProperty());
		if (eqObj != null)
			subProperty = eqObj;

		int clusterId = ClusterShareFunc.findClusterOfDro(subProperty.asOWLDataProperty(), ontM);
		if (clusterId != -1) {
			ClusterManager = ontM.getClusters().get(clusterId).getManager();
			ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
			ClusterManager.addAxiom(ClusterOntology, myAxiom);
			ontM.getClusters().get(clusterId).setManager(ClusterManager);
		} else {
			HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
			brAx.add(myAxiom);
			ontM.SetOtherBreakingAxiom(brAx);
		}

		return ontM;

	}

	private static HModel AnnotationAssertionAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;
		OWLDataFactory factory;
		boolean flag = false;

		OWLAnnotationSubject subject = ((OWLAnnotationAssertionAxiom) myAxiom).getSubject();

		if (subject instanceof IRI) {
			ClusterOntology = ontM.getOwlModel();
			factory = ClusterOntology.getOWLOntologyManager().getOWLDataFactory();
			OWLClass cl = factory.getOWLClass((IRI) subject);
			flag = false;
			if (cl != null) {
				OWLClass eqCls = ontM.getKeyValueEqClass().get(cl);
				if (eqCls != null) {
					subject = eqCls.getIRI();
				} else {
					eqCls = cl;
				}
				int clusterId = ClusterShareFunc.findClusterOfClass(eqCls, ontM);

				if (clusterId != -1) {
					ClusterManager = ontM.getClusters().get(clusterId).getManager();
					ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
					ClusterManager.addAxiom(ClusterOntology, myAxiom);
					ontM.getClusters().get(clusterId).setManager(ClusterManager);
					flag = true;
				} else {
					OWLObjectProperty proNew = factory.getOWLObjectProperty((IRI) subject);
					clusterId = ClusterShareFunc.findClusterOfPro(proNew, ontM);
					if (clusterId != -1) {
						ClusterManager = ontM.getClusters().get(clusterId).getManager();
						ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
						ClusterManager.addAxiom(ClusterOntology, myAxiom);
						ontM.getClusters().get(clusterId).setManager(ClusterManager);
						flag = true;
					}
				}
			}
			if (!flag) {
				HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
				brAx.add(myAxiom);
				ontM.SetOtherBreakingAxiom(brAx);
			}
		}

		return ontM;

	}

	private static HModel DataPropertyRangeAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;
		OWLDataFactory factory;
		Set<Integer> addToAll = new HashSet<Integer>();

		Set<OWLDataProperty> objList = new HashSet<OWLDataProperty>();
		Iterator<OWLDataProperty> iter = myAxiom.getDataPropertiesInSignature().iterator();
		ArrayList<Integer> clId = new ArrayList<Integer>();
		while (iter.hasNext()) {
			OWLDataProperty obj = iter.next();
			OWLDataProperty eqObj = ontM.getKeyValueEqDataPro().get(obj.asOWLDataProperty());
			if (eqObj != null) {
				objList.add(eqObj);
			} else {
				objList.add(obj);
			}
		}
		Iterator<OWLDataProperty> iterObj = objList.iterator();
		while (iterObj.hasNext()) {
			OWLDataProperty obb = iterObj.next();

			int clusterId = ClusterShareFunc.findClusterOfDro(obb, ontM);
			if (clusterId == -1) {
				factory = ontM.getOwlModel().getOWLOntologyManager().getOWLDataFactory();
				OWLObjectProperty ob = factory.getOWLObjectProperty(obb.getIRI());
				clusterId = ClusterShareFunc.findClusterOfPro(ob, ontM);
			}
			if (clusterId == -1) {
				clusterId = ClusterShareFunc.findClusterOfFuncPro(obb, ontM);
			}

			if (clusterId == -1) {

				Iterator<OWLClassExpression> allCl = obb.getDomains(ontM.getOwlModel()).iterator();
				while (allCl.hasNext()) {
					OWLClassExpression domain = allCl.next();
					Iterator<OWLClass> onlyCl = findAllClasses(domain, ontM).iterator();
					while (onlyCl.hasNext()) {
						OWLClass cc = onlyCl.next();
						clusterId = ClusterShareFunc.findClusterOfClass(cc, ontM);
						if (clusterId != -1)
							addToAll.add(clusterId);
					}
				}
				// add this property for all domain classes
				Iterator<Integer> iterId = addToAll.iterator();
				while (iterId.hasNext()) {
					int id = iterId.next();
					ClusterManager = ontM.getClusters().get(id).getManager();
					ClusterOntology = ontM.getClusters().get(id).getOntology();
					ClusterManager.addAxiom(ClusterOntology, myAxiom);
					ontM.getClusters().get(id).setManager(ClusterManager);
				}
			} else {
				clId.add(clusterId);
			}
		}
		boolean same = areSameId(clId);
		if (clId.size() > 0) {
			int clusterId = clId.get(0);
			if (same && clusterId != -1) {
				ClusterManager = ontM.getClusters().get(clusterId).getManager();
				ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
				ClusterManager.addAxiom(ClusterOntology, myAxiom);
				ontM.getClusters().get(clusterId).setManager(ClusterManager);
			} else if (addToAll.size() < 1) {
				HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
				brAx.add(myAxiom);
				ontM.SetOtherBreakingAxiom(brAx);
			}
		}

		return ontM;

	}

	private static Set<OWLClass> findAllClasses(OWLClassExpression domain, HModel ontM) {
		Set<OWLClass> res = new HashSet<OWLClass>();
		if (domain instanceof OWLClassImpl) {
			res.add(domain.asOWLClass());
		} else if (domain instanceof OWLObjectUnionOfImpl) {
			Iterator<OWLClass> clscls = domain.getClassesInSignature().iterator();
			while (clscls.hasNext()) {
				OWLClass cl = clscls.next();
				OWLClass refClass2 = ontM.getKeyValueEqClass().get(cl.asOWLClass());
				if (refClass2 != null) {
					res.add(refClass2);
				} else {
					res.add(cl.asOWLClass());
				}
			}
			// } else if (domain instanceof OWLObjectSomeValuesFromImpl) {+

			// } else if (domain instanceof OWLObjectAllValuesFromImpl) {+

			// } else if (domain instanceof OWLObjectIntersectionOfImpl) {+

		}
		return res;
	}

	private static HModel SubObjectPropertyOfAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;
		OWLObjectPropertyExpression subProperty = ((OWLSubObjectPropertyOfAxiom) myAxiom).getSubProperty();
		OWLObjectPropertyExpression superProperty = ((OWLSubObjectPropertyOfAxiom) myAxiom).getSuperProperty();

		OWLObjectProperty eqSubObj = ontM.getKeyValueEqObjProperty().get(subProperty.asOWLObjectProperty());
		if (eqSubObj != null)
			subProperty = eqSubObj;

		OWLObjectProperty eqSuperObj = ontM.getKeyValueEqObjProperty().get(subProperty.asOWLObjectProperty());
		if (eqSuperObj != null)
			superProperty = eqSuperObj;

		int clusterIdSub = ClusterShareFunc.findClusterOfPro(subProperty.asOWLObjectProperty(), ontM);
		int clusterIdSuper = ClusterShareFunc.findClusterOfPro(superProperty.asOWLObjectProperty(), ontM);
		if (clusterIdSuper != -1) {
			ClusterManager = ontM.getClusters().get(clusterIdSuper).getManager();
			ClusterOntology = ontM.getClusters().get(clusterIdSuper).getOntology();
			ClusterManager.addAxiom(ClusterOntology, myAxiom);
			ontM.getClusters().get(clusterIdSuper).setManager(ClusterManager);
		} else if (clusterIdSub != -1) {
			ClusterManager = ontM.getClusters().get(clusterIdSub).getManager();
			ClusterOntology = ontM.getClusters().get(clusterIdSub).getOntology();
			ClusterManager.addAxiom(ClusterOntology, myAxiom);
			ontM.getClusters().get(clusterIdSub).setManager(ClusterManager);
		} else {
			HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
			brAx.add(myAxiom);
			ontM.SetOtherBreakingAxiom(brAx);
		}

		return ontM;

	}

	private static HModel InverseFunctionalObjectPropertyAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;

		Set<OWLObjectProperty> objList = new HashSet<OWLObjectProperty>();
		Iterator<OWLObjectProperty> iter = myAxiom.getObjectPropertiesInSignature().iterator();
		ArrayList<Integer> clId = new ArrayList<Integer>();
		while (iter.hasNext()) {
			OWLObjectProperty obj = iter.next();
			OWLObjectProperty eqObj = ontM.getKeyValueEqObjProperty().get(obj.asOWLObjectProperty());
			if (eqObj != null) {
				objList.add(eqObj);
				clId.add(ClusterShareFunc.findClusterOfPro(eqObj, ontM));
			} else {
				objList.add(obj);
				clId.add(ClusterShareFunc.findClusterOfPro(obj, ontM));
			}
		}
		boolean same = areSameId(clId);
		int clusterId = clId.get(0);
		if (same && clusterId != -1) {
			ClusterManager = ontM.getClusters().get(clusterId).getManager();
			ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
			ClusterManager.addAxiom(ClusterOntology, myAxiom);
			ontM.getClusters().get(clusterId).setManager(ClusterManager);
		} else {
			HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
			brAx.add(myAxiom);
			ontM.SetOtherBreakingAxiom(brAx);
		}

		return ontM;
	}

	private static HModel InverseObjectPropertiesAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;

		Set<OWLObjectProperty> objList = new HashSet<OWLObjectProperty>();

		Iterator<OWLObjectProperty> iter = myAxiom.getObjectPropertiesInSignature().iterator();
		ArrayList<Integer> clId = new ArrayList<Integer>();
		while (iter.hasNext()) {
			OWLObjectProperty obj = iter.next();
			OWLObjectProperty eqObj = ontM.getKeyValueEqObjProperty().get(obj.asOWLObjectProperty());
			if (eqObj != null) {
				objList.add(eqObj);
				clId.add(ClusterShareFunc.findClusterOfPro(eqObj, ontM));
			} else {
				objList.add(obj);
				clId.add(ClusterShareFunc.findClusterOfPro(obj, ontM));
			}
		}

		boolean same = areSameId(clId);
		int clusterId = clId.get(0);
		if (same && clusterId != -1) {
			ClusterManager = ontM.getClusters().get(clusterId).getManager();
			ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
			ClusterManager.addAxiom(ClusterOntology, myAxiom);
			ontM.getClusters().get(clusterId).setManager(ClusterManager);
		} else {
			HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
			brAx.add(myAxiom);
			ontM.SetOtherBreakingAxiom(brAx);
		}
		return ontM;
	}

	private static HModel DifferentIndividualsAdder(OWLAxiom myAxiom, HModel ontM) {
		System.out.println(myAxiom + " TODO: process me in DifferentIndividualsProcessor in HMerging.java");
		return ontM;
	}

	private static HModel InsatncesAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;

		OWLClassAssertionAxiom axx = (OWLClassAssertionAxiom) myAxiom;
		OWLClassExpression c = ((OWLClassAssertionAxiom) myAxiom).getClassExpression();
		OWLIndividual ind = axx.getIndividual();
		OWLClass cc = c.asOWLClass();
		if (cc instanceof OWLClass) {
			OWLClass refClass = ontM.getKeyValueEqClass().get(cc);
			if (refClass != null)
				cc = refClass;
			int clusterId = ClusterShareFunc.findClusterOfClass(cc, ontM);

			if (clusterId != -1) {
				ClusterManager = ontM.getClusters().get(clusterId).getManager();
				ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
				ClusterManager.addAxiom(ClusterOntology, myAxiom);
				ontM.getClusters().get(clusterId).setManager(ClusterManager);
			} else {
				HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
				brAx.add(myAxiom);
				ontM.SetOtherBreakingAxiom(brAx);
			}

		} else if (cc instanceof OWLObjectSomeValuesFrom) {
			System.out.println("unprocess axioms:" + myAxiom);
		}

		return ontM;
	}

	private static HModel EquivalentClassesAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;

		OWLClass eqClass1 = null;
		Set<OWLClass> eqClass2 = new HashSet<OWLClass>();
		for (OWLClassExpression cls : ((OWLEquivalentClassesAxiom) myAxiom).getClassExpressions()) {
			if (cls instanceof OWLClassImpl) {
				OWLClass refClass1 = ontM.getKeyValueEqClass().get(cls.asOWLClass());
				if (refClass1 != null) {
					eqClass1 = refClass1;
				} else {
					eqClass1 = cls.asOWLClass();
				}
				int clusterId = ClusterShareFunc.findClusterOfClass(eqClass1, ontM);
				if (clusterId != -1) {
					ClusterManager = ontM.getClusters().get(clusterId).getManager();
					ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
					ClusterManager.addAxiom(ClusterOntology, myAxiom);
					ontM.getClusters().get(clusterId).setManager(ClusterManager);
				} else {
					HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
					brAx.add(myAxiom);
					ontM.SetOtherBreakingAxiom(brAx);
				}
			} else if (cls instanceof OWLObjectUnionOf) {
				Iterator<OWLClass> clscls = cls.getClassesInSignature().iterator();
				ArrayList<Integer> clId = new ArrayList<Integer>();
				while (clscls.hasNext()) {
					OWLClass cl = clscls.next();
					OWLClass refClass2 = ontM.getKeyValueEqClass().get(cl.asOWLClass());
					if (refClass2 != null) {
						eqClass2.add(refClass2);
						clId.add(ClusterShareFunc.findClusterOfClass(refClass2, ontM));
					} else {
						eqClass2.add(cl.asOWLClass());
						clId.add(ClusterShareFunc.findClusterOfClass(cl.asOWLClass(), ontM));
					}
				}
				boolean same = areSameId(clId);
				int clusterId = -1;
				if (clId.size() > 0)
					clusterId = clId.get(0);
				if (same && clusterId != -1) {
					ClusterManager = ontM.getClusters().get(clusterId).getManager();
					ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
					ClusterManager.addAxiom(ClusterOntology, myAxiom);
					ontM.getClusters().get(clusterId).setManager(ClusterManager);
				} else {
					HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
					brAx.add(myAxiom);
					ontM.SetOtherBreakingAxiom(brAx);
				}
			} else {
				String msg = "The subClassOf axiom: " + myAxiom + " could not be added to any clusters!";
				MyLogging.log(Level.WARNING, msg);
			}
		}

		return ontM;
	}

	private static HModel DisjointClassesAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;

		if (myAxiom.getClassesInSignature().size() < 2) {
			HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
			brAx.add(myAxiom);
			ontM.SetOtherBreakingAxiom(brAx);
			return ontM;
		}

		Iterator<OWLClass> clist = myAxiom.getClassesInSignature().iterator();
		OWLClass cls1 = clist.next();
		OWLClass cls2 = clist.next();

		OWLClass eqC1 = cls1;
		OWLClass refClass1 = ontM.getKeyValueEqClass().get(cls1);
		if (refClass1 != null)
			eqC1 = refClass1;

		OWLClass eqC2 = cls2;
		OWLClass refClass2 = ontM.getKeyValueEqClass().get(cls2);
		if (refClass2 != null)
			eqC2 = refClass2;

		int clusterId = ClusterShareFunc.findClusterOfClass(eqC1, ontM);
		int clusterIdSup = ClusterShareFunc.findClusterOfClass(eqC2, ontM);
		if (clusterId == clusterIdSup && clusterId != -1) {
			ClusterManager = ontM.getClusters().get(clusterId).getManager();
			ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
			ClusterManager.addAxiom(ClusterOntology, myAxiom);
			ontM.getClusters().get(clusterId).setManager(ClusterManager);
		} else {
			HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
			brAx.add(myAxiom);
			ontM.SetOtherBreakingAxiom(brAx);
		}

		return ontM;
	}

	private static HModel DataPropertyDomainAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;

		Iterator<OWLDataProperty> pro = myAxiom.getDataPropertiesInSignature().iterator();
		OWLDataProperty prop = pro.next();
		OWLClassExpression dom = ((OWLDataPropertyDomainAxiom) myAxiom).getDomain();

		OWLDataProperty eqPro = prop;
		OWLDataProperty refDpro = ontM.getKeyValueEqDataPro().get(prop.asOWLDataProperty());
		if (refDpro != null)
			eqPro = refDpro;

		if (dom instanceof OWLClassImpl) {
			OWLClass eqDom = dom.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(dom.asOWLClass());
			if (refClass != null)
				eqDom = refClass;

			int clusterId = ClusterShareFunc.findClusterOfClass(eqDom, ontM);

			if (clusterId != -1) {
				ClusterManager = ontM.getClusters().get(clusterId).getManager();
				ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
				ClusterManager.addAxiom(ClusterOntology, myAxiom);
				ontM.getClusters().get(clusterId).setManager(ClusterManager);
			} else {
				HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
				brAx.add(myAxiom);
				ontM.SetOtherBreakingAxiom(brAx);
			}

		} else if (dom instanceof OWLObjectUnionOf) {

			Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) dom).getOperands().iterator();
			ArrayList<Integer> clId = new ArrayList<Integer>();
			while (rangExist.hasNext()) {
				OWLClassExpression ex = rangExist.next();
				OWLClass eq = ex.asOWLClass();
				OWLClass refClass = ontM.getKeyValueEqClass().get(eq.asOWLClass());
				if (refClass != null)
					eq = refClass;
				clId.add(ClusterShareFunc.findClusterOfClass(eq, ontM));
			}
			boolean same = areSameId(clId);
			int clusterId = clId.get(0);
			if (same && clusterId != -1) {
				ClusterManager = ontM.getClusters().get(clusterId).getManager();
				ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
				ClusterManager.addAxiom(ClusterOntology, myAxiom);
				ontM.getClusters().get(clusterId).setManager(ClusterManager);
			} else {
				HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
				brAx.add(myAxiom);
				ontM.SetOtherBreakingAxiom(brAx);
			}

		}

		return ontM;
	}

	/*
	 * ********************************************************************
	 */
	private static HModel SubClassAdder(OWLAxiom myAxiom, HModel ontM) {

		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;
		ArrayList<HMappedClass> EqList = ontM.getEqClasses();
		OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) myAxiom).getSuperClass();
		OWLClassExpression SubClass = ((OWLSubClassOfAxiom) myAxiom).getSubClass();

		if (SuperClass instanceof OWLClassImpl && SubClass instanceof OWLClassImpl) {
			OWLClass eqSup = SuperClass.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSup.asOWLClass());
			if (refClass != null)
				eqSup = refClass;

			OWLClass eqSub = SubClass.asOWLClass();
			refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null)
				eqSub = refClass;

			int clusterIdSub = ClusterShareFunc.findClusterOfClass(eqSub, ontM);
			int clusterIdSup = ClusterShareFunc.findClusterOfClass(eqSup, ontM);
			if (clusterIdSub == clusterIdSup && clusterIdSub != -1) {
				ClusterManager = ontM.getClusters().get(clusterIdSub).getManager();
				ClusterOntology = ontM.getClusters().get(clusterIdSub).getOntology();
				ClusterManager.addAxiom(ClusterOntology, myAxiom);
				ontM.getClusters().get(clusterIdSub).setManager(ClusterManager);
			} else {
				HashSet<OWLAxiom> brAx = ontM.getISABreakingAxiom();
				brAx.add(myAxiom);
				ontM.SetISABreakingAxiom(brAx);
			}

		} else if (SuperClass instanceof OWLObjectUnionOf && SubClass instanceof OWLClassImpl) {

			OWLClass eqSub = SubClass.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub.asOWLClass());
			if (refClass != null)
				eqSub = refClass;
			int clusterIdSub = ClusterShareFunc.findClusterOfClass(eqSub, ontM);

			Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) SuperClass).getOperands().iterator();
			ArrayList<Integer> clId = new ArrayList<Integer>();
			while (rangExist.hasNext()) {
				OWLClassExpression ex = rangExist.next();
				OWLClassExpression eq = ex;
				if (eq instanceof OWLClassImpl) {
					refClass = ontM.getKeyValueEqClass().get(ex.asOWLClass());
					if (refClass != null)
						eq = refClass;
				}
				clId.add(ClusterShareFunc.findClusterOfClass(eq, ontM));
			}
			// if all elements in UnionOf belong to one cluster, we add this
			// subclassAxiom to that cluster, otherwise no

			boolean same = areSame(clId, clusterIdSub);
			if (same && clusterIdSub != -1) {
				ClusterManager = ontM.getClusters().get(clusterIdSub).getManager();
				ClusterOntology = ontM.getClusters().get(clusterIdSub).getOntology();
				ClusterManager.addAxiom(ClusterOntology, myAxiom);
				ontM.getClusters().get(clusterIdSub).setManager(ClusterManager);

			} else {
				HashSet<OWLAxiom> brAx = ontM.getISABreakingAxiom();
				brAx.add(myAxiom);
				ontM.SetISABreakingAxiom(brAx);

			}

		} else if (SubClass instanceof OWLClassImpl) {
			// if only subClass is in one cluster, we add this myAxiom to the
			// cluster, in this case, the OWLDataCardinilaity,subvallueOf etc.
			// feature will add to the respective class belong to cluster

			OWLClass eqSub = SubClass.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(eqSub);
			if (refClass != null)
				eqSub = refClass;
			int clusterIdSub = ClusterShareFunc.findClusterOfClass(eqSub, ontM);

			if (clusterIdSub != -1) {
				ClusterManager = ontM.getClusters().get(clusterIdSub).getManager();
				ClusterOntology = ontM.getClusters().get(clusterIdSub).getOntology();
				ClusterManager.addAxiom(ClusterOntology, myAxiom);
				ontM.getClusters().get(clusterIdSub).setManager(ClusterManager);

			} else {
				HashSet<OWLAxiom> brAx = ontM.getISABreakingAxiom();
				brAx.add(myAxiom);
				ontM.SetISABreakingAxiom(brAx);

			}
		} else {
			String msg = "The subClassOf axiom: " + myAxiom + " could not be added to any clusters!";
			MyLogging.log(Level.WARNING, msg);
		}

		return ontM;
	}

	private static boolean areSame(ArrayList<Integer> clId, int id) {
		if (clId.size() < 1)
			return false;
		if (clId.size() == 1) {
			if (clId.get(0) != -1)
				return true;
		}
		for (int i = 0; i < clId.size(); i++) {
			if (clId.get(i) != id) {
				return false;
			}
		}
		return true;
	}

	private static boolean areSameId(ArrayList<Integer> clId) {
		if (clId.size() < 1)
			return false;
		if (clId.size() == 1) {
			if (clId.get(0) != -1)
				return true;
		}
		int id = clId.get(0);
		for (int i = 0; i < clId.size(); i++) {
			if (clId.get(i) != id) {
				return false;
			}
		}
		return true;
	}

	private static HModel ObjectPropertyDomainAdder(OWLAxiom myAxiom, HModel ontM) {
		OWLOntology ClusterOntology;
		OWLOntologyManager ClusterManager;
		OWLClassExpression dom = ((OWLObjectPropertyDomainAxiom) myAxiom).getDomain();

		if (dom instanceof OWLClassImpl) {
			OWLClass eqDom = dom.asOWLClass();
			OWLClass refClass = ontM.getKeyValueEqClass().get(dom.asOWLClass());
			if (refClass != null)
				eqDom = refClass;

			int clusterId = ClusterShareFunc.findClusterOfClass(eqDom, ontM);
			if (clusterId != -1) {
				ClusterManager = ontM.getClusters().get(clusterId).getManager();
				ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
				ClusterManager.addAxiom(ClusterOntology, myAxiom);
				ontM.getClusters().get(clusterId).setManager(ClusterManager);

			} else {
				HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
				brAx.add(myAxiom);
				ontM.SetOtherBreakingAxiom(brAx);

			}
		} else if (dom instanceof OWLObjectUnionOf) {

			ArrayList<Integer> clId = new ArrayList<Integer>();
			Iterator<OWLClassExpression> rangExist = ((OWLObjectUnionOf) dom).getOperands().iterator();
			while (rangExist.hasNext()) {
				OWLClassExpression ex = rangExist.next();
				OWLClassExpression eq = ex;
				if (ex instanceof OWLClassImpl) {
					OWLClass refClass = ontM.getKeyValueEqClass().get(ex.asOWLClass());
					if (refClass != null)
						eq = refClass;
				}

				clId.add(ClusterShareFunc.findClusterOfClass(eq, ontM));
				boolean same = areSameId(clId);
				int clusterId = clId.get(0);
				if (same && clusterId != -1) {
					ClusterManager = ontM.getClusters().get(clusterId).getManager();
					ClusterOntology = ontM.getClusters().get(clusterId).getOntology();
					ClusterManager.addAxiom(ClusterOntology, myAxiom);
					ontM.getClusters().get(clusterId).setManager(ClusterManager);
				} else {
					HashSet<OWLAxiom> brAx = ontM.getOtherBreakingAxiom();
					brAx.add(myAxiom);
					ontM.SetOtherBreakingAxiom(brAx);
				}
			}
		} else {
			String msg = "The subClassOf axiom: " + myAxiom + " could not be added to any clusters!";
			MyLogging.log(Level.WARNING, msg);
		}

		return ontM;

	}

	public static HModel removeEqEntities(HModel ontM, OWLDataFactory factory) {

		OWLOntology ontology = ontM.getOwlModel();
		OWLOntologyManager manager = ontM.getManager();

		OWLEntityRemover remover = new OWLEntityRemover(manager, Collections.singleton(ontology));

		ArrayList<HMappedClass> classList = ontM.getEqClasses();
		for (int i = 0; i < classList.size(); i++) {
			Iterator<OWLClass> iter = classList.get(i).getMappedCalss().iterator();
			while (iter.hasNext()) {
				OWLClass c = iter.next();
				if (!c.equals(classList.get(i).getRefClass())) {
					c.accept(remover);
				}
			}
		}
		manager.applyChanges(remover.getChanges());

		ArrayList<HMappedObj> objList = ontM.getEqObjProperties();
		for (int i = 0; i < objList.size(); i++) {
			Iterator<OWLObjectProperty> iter = objList.get(i).getMappedObj().iterator();
			while (iter.hasNext()) {
				OWLObjectProperty c = iter.next();
				if (!c.equals(objList.get(i).getRefObj())) {
					c.accept(remover);
				}
			}
		}
		manager.applyChanges(remover.getChanges());

		ArrayList<HMappedDpro> objDList = ontM.getEqDataProperties();
		for (int i = 0; i < objDList.size(); i++) {
			Iterator<OWLDataProperty> iter = objDList.get(i).getMappedDpro().iterator();
			while (iter.hasNext()) {
				OWLDataProperty c = iter.next();
				if (!c.equals(objDList.get(i).getRefDpro())) {
					c.accept(remover);
				}
			}
		}

		manager.applyChanges(remover.getChanges());

		ontM.SetOwlModel(ontology);
		ontM.SetManager(manager);
		return ontM;

	}

}
