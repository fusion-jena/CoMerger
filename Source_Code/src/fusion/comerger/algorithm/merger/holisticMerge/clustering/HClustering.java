package fusion.comerger.algorithm.merger.holisticMerge.clustering;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.Parameter;
import fusion.comerger.algorithm.merger.holisticMerge.general.ClassProcess;
import fusion.comerger.algorithm.merger.holisticMerge.general.ShareMergeFunction;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;
import fusion.comerger.algorithm.merger.model.HModel;

public class HClustering {

	public HModel run(HModel ontM, HashMap<OWLClassExpression, HashSet<OWLClassExpression>> adjacentList)
			throws OWLOntologyStorageException, OWLOntologyCreationException {
		long startTime = System.currentTimeMillis();

		/* step1: determine the number of clusters */
		int numCluster = determineClusterNum(ontM);

		/* step2: determine the cores */
		ArrayList<HMappedClass> coreList = new ArrayList<HMappedClass>();
		coreList = FindCores(ontM, numCluster, adjacentList);

		/* Step 3: initializing the clusters & assign element to clusters */
		ontM = assignClusters(ontM, coreList, adjacentList, numCluster);

		/* Step 4: add related properties to each cluster */
		AssignClusterProperties acp = new AssignClusterProperties();
		ontM = acp.run(ontM);
		// ClusterList = assignPropertiesOfClsuters(ontM);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Clustering has been done successfully. Total time for the whole process of clustering:  " + elapsedTime
						+ " ms. \n");

		StatisticTest.result.put("time_whole_clustering", String.valueOf(elapsedTime));
		return ontM;
	}

	/* ********************************************** */
	private HModel assignClusters(HModel ontM, ArrayList<HMappedClass> coreList,
			HashMap<OWLClassExpression, HashSet<OWLClassExpression>> adjacentList, int numCluster) {
		long stTime = System.currentTimeMillis();

		ArrayList<ClusterModel> ClusterList = new ArrayList<ClusterModel>();
		try {
			if (numCluster == 1) {
				ClusterModel cm = new ClusterModel();
				OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
				String clusterName = ontM.getPath() + "cluster0.owl";
				File fileM = new File(clusterName);
				OWLOntology clusterOntology = manager.createOntology(IRI.create(fileM));
				cm.setManager(manager);
				cm.setOntology(clusterOntology);
				ClusterList.add(cm);

				Set<OWLClass> OiAllClassSet = ontM.getOwlModel().getClassesInSignature();
				ArrayList<OWLClassExpression> OiClassArray = new ArrayList<OWLClassExpression>(OiAllClassSet);
				ClusterList.get(0).SetClasses(OiClassArray);
				ontM.SetClusters(ClusterList);
			
				StatisticTest.result.put("clustering_iteration", String.valueOf(1));
				
				StatisticTest.result.put("cluster_class_size", String.valueOf(cm.getClasses().size()));
				
				ontM.SetClusters(ClusterList);
				
				long sTime = System.currentTimeMillis();
				long eTime = sTime - stTime;
				MyLogging.log(Level.INFO,
						"Cluster assignment has been done successfully in with 1 iteration. Total time: " + eTime
								+ " \n");

				StatisticTest.result.put("time_clustering_assignment", String.valueOf(eTime));			
				return ontM;

			}
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
			MyLogging.log(Level.SEVERE, "Exception from assigning cluster: " + e.toString());
		}

		HashMap<Integer, HashSet<OWLClassExpression>> recentlyAddedClasses = new HashMap<Integer, HashSet<OWLClassExpression>>();
		HashMap<Integer, Boolean> changesAddedClasses = new HashMap<Integer, Boolean>();

		/* step 1: initialize the clusters */
		// create empty n clusters with empty elementsSets, with cores+ add
		// their direct elements
		if (coreList != null) {
			for (int i = 0; i < coreList.size(); i++) {
				try {
					// create an empty cluster model
					ClusterModel cm = new ClusterModel();
					// create manager and ontology for them, they will be used
					// by
					// addProperties step and in refinment
					OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
					String clusterName = ontM.getPath() + "cluster" + i + ".owl";
					File fileM = new File(clusterName);
					OWLOntology clusterOntology = manager.createOntology(IRI.create(fileM));
					// OWLOntology ontology =
					// manager.getOntology(currentOntologyID);
					// OWLDataFactory factory = manager.getOWLDataFactory();
					// set
					cm.setManager(manager);
					cm.setOntology(clusterOntology);

					HMappedClass c = coreList.get(i);
					cm.SetCore(c);
					ArrayList<OWLClassExpression> elm = new ArrayList<OWLClassExpression>();
					elm.add(c.getRefClass());
					cm.SetClasses(elm);
					// HashSet<OWLClass> temp =
					// findAdjacentClass(c.getRefClass(),
					// ontM);
					HashSet<OWLClassExpression> temp = adjacentList.get(c.getRefClass());
					recentlyAddedClasses.put(i, temp);
					changesAddedClasses.put(i, true);
					// cm.SetAxioms(axList);
					// cm.SetClasses(elm);
					ClusterList.add(cm);

				} catch (OWLOntologyCreationException e) {
					e.printStackTrace();
					MyLogging.log(Level.SEVERE, "Exception from assigning cluster: " + e.toString());
				}
			}
			ontM.SetClusters(ClusterList);
		}

		/* Step 2: assign elements to clusters */

		if (ClusterList.size() > 0) {
			if (ClusterList.size() == 1) {
				// all entites are in one cluster, so no need to follow the
				// normal process
				Set<OWLClass> OiAllClassSet = ontM.getOwlModel().getClassesInSignature();
				ArrayList<OWLClassExpression> OiClassArray = new ArrayList<OWLClassExpression>(OiAllClassSet);
				ClusterList.get(0).SetClasses(OiClassArray);
				ontM.SetClusters(ClusterList);
				long sTime = System.currentTimeMillis();
				long eTime = sTime - stTime;
				MyLogging.log(Level.INFO,
						"Cluster assignment has been done successfully in with 1 iteration. Total time: " + eTime
								+ " \n");
			} else {
				// iterative process
				// 1st iter:
				boolean noChanges = true;
				int iteration = 0;
				while (noChanges) {
					for (int clusterIndex = 0; clusterIndex < numCluster; clusterIndex++) {
						ArrayList<OWLClassExpression> tempCl = ClusterList.get(clusterIndex).getClasses();
						HashSet<OWLClassExpression> recent = recentlyAddedClasses.get(clusterIndex);
						if (recent != null && recent.size() > 0) {
							// first add add recentAddedClasses as the element
							// of this cluster
							// from recnt add those that are not added before to
							// any clusters
							HashSet<OWLClassExpression> recent_notVisistedAdjacent = notVisited(recent, ontM);
							tempCl.addAll(recent_notVisistedAdjacent);
							ClusterList.get(clusterIndex).SetClasses(tempCl);
							ontM.SetClusters(ClusterList);
							// add adjacent classes for recently new added
							// classes
							Iterator<OWLClassExpression> iter = recent_notVisistedAdjacent.iterator();
							HashSet<OWLClassExpression> newRecent = new HashSet<OWLClassExpression>();
							while (iter.hasNext()) {
								OWLClassExpression cn = iter.next();
								HashSet<OWLClassExpression> allAdjacent = adjacentList.get(cn);
								if (allAdjacent != null && allAdjacent.size() > 0) {
									HashSet<OWLClassExpression> notVisistedAdjacent = findNotVisited(allAdjacent,
											recentlyAddedClasses, ontM);
									newRecent.addAll(notVisistedAdjacent);
								}
							}
							if (newRecent.size() > 0) {
								recentlyAddedClasses.put(clusterIndex, newRecent);
								changesAddedClasses.put(clusterIndex, true);
							} else {
								recentlyAddedClasses.put(clusterIndex, null);
								changesAddedClasses.put(clusterIndex, false);
							}
							// ontM.SetClusters(ClusterList);
						} else {
							// means no changes
							changesAddedClasses.put(clusterIndex, false);
						}
						// update recent with newRecent
						// recentlyAddedClasses =
						// updateRecent(recentlyAddedClasses,
						// recent, newRecent, clusterIndex);
						// recentlyAddedClasses[clusterIndex] = newRecent;
						// }
					}
					// System.out.println("Clustering assignmnet in iterartion "
					// + iteration);
					iteration++;
					// number of iterations should be same as maximum deep of
					// input
					// ontologies
					noChanges = checkChanges(changesAddedClasses);
				}

				// check some elements left? then add them with the proper
				// cluster
				HashSet<OWLClass> unAssignclass = findUnAssignClass(ontM);
				if (unAssignclass.size() > 0)
					ontM = clusterUnAssignClass(unAssignclass, ontM);

				// log info
				String clusterSizeLog = "";
				String clusterSize = "";
				for (int i = 0; i < ClusterList.size(); i++) {
					if (ClusterList.get(i).getClasses() != null) {
						int siz = ClusterList.get(i).getClasses().size();
						ClusterList.get(i).setSize(siz);
						clusterSizeLog = clusterSizeLog + "\t Size of cluster" + i + " is: " + siz;
						clusterSize = clusterSize + ";" + siz;
					}
				}
				MyLogging.log(Level.INFO, clusterSizeLog);
				StatisticTest.result.put("cluster_class_size", String.valueOf(clusterSize));
				StatisticTest.result.put("clustering_iteration", String.valueOf(iteration));
				
				long sTime = System.currentTimeMillis();
				long eTime = sTime - stTime;
				MyLogging.log(Level.INFO, "Cluster assignment has been done successfully with " + iteration
						+ " iterations. Total time: " + eTime + "ms. \n");
			
				StatisticTest.result.put("time_clustering_assignment", String.valueOf(eTime));
			}
		} else {
			MyLogging.log(Level.WARNING, "No iteration done in the clustering! \n");
			StatisticTest.result.put("clustering_iteration", String.valueOf(0));
			long sTime = System.currentTimeMillis();
			long eTime = sTime - stTime;
			StatisticTest.result.put("time_clustering_assignment", String.valueOf(eTime));
		}

		ontM.SetClusters(ClusterList);
		return ontM;
	}

	/* ********************************************** */
	private HashSet<OWLClassExpression> notVisited(HashSet<OWLClassExpression> recent, HModel ontM) {
		Iterator<OWLClassExpression> iter = recent.iterator();
		HashSet<OWLClassExpression> res = new HashSet<OWLClassExpression>();
		HashSet<OWLClassExpression> allElm = new HashSet<OWLClassExpression>();
		for (int i = 0; i < ontM.getClusters().size(); i++) {
			allElm.addAll(ontM.getClusters().get(i).getClasses());

		}

		while (iter.hasNext()) {
			OWLClassExpression c = iter.next();
			for (int i = 0; i < ontM.getClusters().size(); i++) {
				if (!allElm.contains(c)) {
					// chechk for each element inside cluster, also their eqaul
					// mapping
					res.add(c);
				}
			}
		}
		return res;
	}

	
	public HashMap<OWLClassExpression, HashSet<OWLClassExpression>> createAdjacentClassesList(HModel ontM) {
			long startTime = System.currentTimeMillis();
		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> res = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();
	

		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> listParent = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();
		HashMap<OWLClassExpression, HashSet<OWLClassExpression>> listChild = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();
		HashSet<OWLClassExpression> temp = new HashSet<OWLClassExpression>();

		Iterator<OWLSubClassOfAxiom> is_a = ontM.getOwlModel().getAxioms(AxiomType.SUBCLASS_OF).iterator();

	
		while (is_a.hasNext()) {

			OWLSubClassOfAxiom axiom = is_a.next();
			OWLClassExpression SuperClass = ((OWLSubClassOfAxiom) axiom).getSuperClass();// parent
			OWLClassExpression SubClass = ((OWLSubClassOfAxiom) axiom).getSubClass();// child

			// if (SuperClass instanceof OWLClassImpl && SubClass instanceof
			// OWLClassImpl) {
			// add parent to the child list
			temp = new HashSet<OWLClassExpression>();
			HashSet<OWLClassExpression> cc = listParent.get(SubClass);
			if (cc == null || cc.size() < 1) {
				// it means this class was not already in the list of
				// adjacent
				temp.add(SuperClass);
				listParent.put(SubClass, temp);
			} else {
				cc.add(SuperClass);//never arrive here
				listParent.put(SubClass, cc);
			}
			// add child to parent
			temp = new HashSet<OWLClassExpression>();
			HashSet<OWLClassExpression> ccc = listChild.get(SuperClass);
			if (ccc == null || ccc.size() < 1) {
				// it means this class was not already in the list of
				// adjacent
				temp.add(SubClass);
				listChild.put(SuperClass, temp);
			} else {
				ccc.add(SubClass);//never arrive here
				listChild.put(SuperClass, ccc);
			}
			// }
		}

		ontM.setParentList(listParent);
		ontM.setChildList(listChild);
		ontM.setAlterStatus(false);

		res.putAll(listParent);
		res.putAll(listChild);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"A list of adjacent classes has been successfully created. Total time: " + elapsedTime + " ms. \n");
		return res;
	}

	private HModel clusterUnAssignClass(HashSet<OWLClass> unAssignclass, HModel ontM) {
		ArrayList<ClusterModel> ClusterList = ontM.getClusters();
		HashSet<HashSet<String>> allPrefix = new HashSet<HashSet<String>>();
		HashSet<String> tempPre = new HashSet<String>();
		for (int i = 0; i < ClusterList.size(); i++) {
			HashMap<HashSet<String>, Integer> originCounter = new HashMap<HashSet<String>, Integer>();
			ArrayList<OWLClassExpression> elm = ClusterList.get(i).getClasses();
			for (int j = 0; j < elm.size(); j++) {
				if (elm.get(j) instanceof OWLClass) {
					OWLClass c = elm.get(j).asOWLClass();
					String prefix = c.getIRI().getNamespace();
					tempPre = new HashSet<String>();
					tempPre.add(prefix);
					if (prefix.endsWith("merged#")) {
						Iterator<OWLClass> allClass = ShareMergeFunction.getMappedClassesforClass(ontM, c).iterator();
						tempPre = new HashSet<String>();
						while (allClass.hasNext()) {
							OWLClass cc = allClass.next();
							String ccPrefix = cc.getIRI().getNamespace();
							tempPre.add(ccPrefix);
						}
						if (originCounter.get(tempPre) == null) {
							originCounter.put(tempPre, 1);
						} else {
							int value = originCounter.put(tempPre, 1);
							originCounter.put(tempPre, value + 1);
						}

						allPrefix.add(tempPre);
					} else if (originCounter.get(tempPre) == null) {
						allPrefix.add(tempPre);
						originCounter.put(tempPre, 1);
					} else {
						allPrefix.add(tempPre);
						int value = originCounter.put(tempPre, 1);
						originCounter.put(tempPre, value + 1);
					}
				}
			}
			ClusterList.get(i).SetElementOrigin(originCounter);
		}

		// save the max prefix counter as a whole
		HashMap<HashSet<String>, Integer> prefixCounterInCluster = new HashMap<HashSet<String>, Integer>();
		Iterator<HashSet<String>> iterP = allPrefix.iterator();
		while (iterP.hasNext()) {
			HashSet<String> pre = iterP.next();
			int pMax = -1;
			int maxCluster = 0;
			for (int clusterIndex = 0; clusterIndex < ClusterList.size(); clusterIndex++) {
				HashMap<HashSet<String>, Integer> origin = ClusterList.get(clusterIndex).getElementOrigin();
				if (origin.get(pre) != null) {
					int a = origin.get(pre);
					if (a > pMax) {
						pMax = a;
						maxCluster = clusterIndex;
					}
				}
			}
			prefixCounterInCluster.put(pre, maxCluster);
		}

		Iterator<OWLClass> iter = unAssignclass.iterator();
		while (iter.hasNext()) {
			OWLClass unAssg = iter.next();
			String prefix = unAssg.getIRI().getNamespace();
			if (prefix.endsWith("merged#")) {
				Iterator<OWLClass> allClass = ShareMergeFunction.getMappedClassesforClass(ontM, unAssg).iterator();
				tempPre = new HashSet<String>();
				while (allClass.hasNext()) {
					OWLClass cc = allClass.next();
					String ccPrefix = cc.getIRI().getNamespace();
					tempPre.add(ccPrefix);
				}
			} else {
				tempPre = new HashSet<String>();
				tempPre.add(prefix);
			}

			if (prefixCounterInCluster.get(tempPre) != null) {
				int goalCluster = prefixCounterInCluster.get(tempPre);
				ArrayList<OWLClassExpression> elm = ClusterList.get(goalCluster).getClasses();
				elm.add(unAssg);
				ClusterList.get(goalCluster).SetClasses(elm);
			}
		}

		ontM.SetClusters(ClusterList);
		return ontM;
	}

	private HashSet<OWLClass> findUnAssignClass(HModel ontM) {
		HashSet<OWLClass> res = new HashSet<OWLClass>();
		ArrayList<OWLClassExpression> allClassesInCluster = new ArrayList<OWLClassExpression>();
		for (int i = 0; i < ontM.getClusters().size(); i++) 
			allClassesInCluster.addAll(ontM.getClusters().get(i).getClasses());
		
		
		Iterator<OWLClass> cList = ontM.getOwlModel().getClassesInSignature().iterator();
		while (cList.hasNext()) {
			OWLClass c = cList.next();
			if (!allClassesInCluster.contains(c)) {
				OWLClass refCl = ontM.getKeyValueEqClass().get(c);
				if (refCl == null) {
					res.add(c);
				}
			}
		}
		return res;
	}

	private HashSet<OWLClassExpression> findNotVisited(HashSet<OWLClassExpression> allAdjacent,
			HashMap<Integer, HashSet<OWLClassExpression>> recentlyAddedClasses, HModel ontM) {
		HashSet<OWLClassExpression> res = new HashSet<OWLClassExpression>();
		ArrayList<OWLClassExpression> allClassesInCluster = new ArrayList<OWLClassExpression>();
		for (int i = 0; i < ontM.getClusters().size(); i++) {
			allClassesInCluster.addAll(ontM.getClusters().get(i).getClasses());
		}
		for (int i = 0; i < recentlyAddedClasses.size(); i++) {
			if (recentlyAddedClasses.get(i) != null)
				allClassesInCluster.addAll(recentlyAddedClasses.get(i));
		}

		Iterator<OWLClassExpression> iterAdj = allAdjacent.iterator();
		while (iterAdj.hasNext()) {
			OWLClassExpression c = iterAdj.next();
			if (!allClassesInCluster.contains(c))
				res.add(c);
		}

		return res;
	}

	private boolean checkChanges(HashMap<Integer, Boolean> chArray) {
		for (int i = 0; i < chArray.size(); i++) {
			if (chArray.get(i).equals(true))
				return true;
		}
		return false;
	}


	public ArrayList<HMappedClass> sortArray(ArrayList<HMappedClass> list) {
		HMappedClass temp;

		for (int x = 0; x < list.size(); x++) // bubble sort outer loop
		{
			for (int i = 0; i < list.size() - x - 1; i++) {
				if (list.get(i + 1).getConnection() > list.get(i).getConnection()) {
					temp = list.get(i);
					list.set(i, list.get(i + 1));
					list.set(i + 1, temp);
				}
			}
		}
		return list;

	}

	private ArrayList<HMappedClass> FindCores(HModel ontM, int numCluster,
			HashMap<OWLClassExpression, HashSet<OWLClassExpression>> adjacentList) {
		long startTime = System.currentTimeMillis();
		long stopTime;
		long elapsedTime;

		Parameter.setTheta(numCluster);

		ArrayList<HMappedClass> cores = new ArrayList<HMappedClass>();

		if (numCluster == 1) {
			MyLogging.log(Level.INFO,
					"No cores have been selected, since the number of cluster is 1. Total time 0 ms. \n");
			StatisticTest.result.put("cores", "-");
			stopTime = System.currentTimeMillis();
			elapsedTime = stopTime - startTime;
			StatisticTest.result.put("time_determine_cores", String.valueOf(elapsedTime));
			return cores;
		}

		ArrayList<HMappedClass> cSet = new ArrayList<HMappedClass>();
		ArrayList<HMappedClass> mapList = ontM.getEqClasses();
		int theta = Parameter.getTheta();

		// set goodness degree
		for (int i = 0; i < mapList.size(); i++) {
			if (adjacentList.get(mapList.get(i).getRefClass()) != null) {
				int con = adjacentList.get(mapList.get(i).getRefClass()).size();
				mapList.get(i).setConnection(con);
				int len = mapList.get(i).getLenClass();
				mapList.get(i).setGoodness((double) (len * con));
			} else {
				mapList.get(i).setGoodness((double) 0.0);
				// since the connection of this class is 0
			}
		}

		if (mapList.size() >= numCluster) {

			// sort maplist based on the goodness value
			Collections.sort(mapList, new Comparator<HMappedClass>() {
				public int compare(HMappedClass s1, HMappedClass s2) {
					return Double.compare(s2.getGoodness(), s1.getGoodness());
				}
			});
			;

			// create cSet
			int CandidtaeSetLenght = numCluster + theta;
			if (CandidtaeSetLenght > mapList.size())
				CandidtaeSetLenght = mapList.size();
			for (int i = 0; i < CandidtaeSetLenght; i++) {
				cSet.add(mapList.get(i));
			}

			// calculate cloesness centrality
			CentralityCalculator cn = new CentralityCalculator();
			ArrayList<HMappedClass> ClossnessCentrality = cn.run(ontM, cSet);

			// sort cSet based on cloesness centrality
			Collections.sort(ClossnessCentrality, new Comparator<HMappedClass>() {
				public int compare(HMappedClass s1, HMappedClass s2) {
					return Double.compare(s2.getCentrality(), s1.getCentrality());
				}
			});
			;

			// now, from cSet select cores
			for (int i = 0; i < numCluster; i++) {
				cores.add(ClossnessCentrality.get(i));
			}

		} else {
			// set cSet to all classes
			Iterator<OWLClass> allClass = ontM.getOwlModel().getClassesInSignature().iterator();
			ArrayList<HMappedClass> CoresFromAllClass = new ArrayList<HMappedClass>();
			while (allClass.hasNext()) {
				OWLClass c = allClass.next();
				if (!c.isOWLThing()) {
					HMappedClass hm = new HMappedClass();
					Double goodnessAll = CalculateConnection(c, ontM);
					hm.setRefClass(c);
					Set<OWLClass> setM = new HashSet<OWLClass>();
					setM.add(c);
					hm.setMappedClass(setM);
					hm.setLenClass(1);
					hm.setGoodness(goodnessAll);
					CoresFromAllClass.add(hm);
				}
			}

			// sort CoresFromAllClass based on the goodness value
			Collections.sort(CoresFromAllClass, new Comparator<HMappedClass>() {
				public int compare(HMappedClass s1, HMappedClass s2) {
					return Double.compare(s2.getGoodness(), s1.getGoodness());
				}
			});
			;

			// create cSet:select top numCuster+theta from goodness and put in
			// cSet
			int CandidtaeSetLenght = numCluster + theta;
			for (int i = 0; i < CandidtaeSetLenght; i++) {
				cSet.add(CoresFromAllClass.get(i));
			}

			// calculate cloesness centrality
			CentralityCalculator cn = new CentralityCalculator();
			ArrayList<HMappedClass> ClossnessCentrality = cn.run(ontM, cSet);

			// sort cSet based on cloesness centrality
			Collections.sort(ClossnessCentrality, new Comparator<HMappedClass>() {
				public int compare(HMappedClass s1, HMappedClass s2) {
					return Double.compare(s2.getCentrality(), s1.getCentrality());
				}
			});
			;

			// now, from cSet select cores
			for (int i = 0; i < numCluster; i++) 
				cores.add(ClossnessCentrality.get(i));
			

			// end the case if mapping file is empty
		}

		// convert cores from to core string
		String coresMsg = "The selected cores are: \n";
		String coresName = "";
		for (int i = 0; i < cores.size(); i++) {
			// System.out.println("core " + i + coreList.get(i).getRefClass());
			coresMsg = coresMsg + "\t core" + i + " is: " + cores.get(i).getRefClass().toString();
			coresName = coresName + "-" + cores.get(i).getRefClass().toString();
		}
		MyLogging.log(Level.INFO, coresMsg);
		StatisticTest.result.put("cores", coresName);
		
		stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "The cores has been determined successfully. Total time  " + elapsedTime + " ms. \n");

		StatisticTest.result.put("time_determine_cores", String.valueOf(elapsedTime));

		return cores;

	}

	private double CalculateConnection(OWLClass c, HModel ontM) {
		double conn = 0;
		OWLOntology Om = ontM.getOwlModel();

		int sub = ClassProcess.getAllSubClasses(c, Om).size();
		int sup = ClassProcess.getAllSupClasses(c, Om).size();
		conn = conn + sub + sup;

		if (Om.getClassesInSignature().size() > 0) {
			conn = conn / Om.getClassesInSignature().size();
		}
		return conn;
	}

	private int determineClusterNum(HModel ontM) {
		long startTime = System.currentTimeMillis();
		long stopTime;
		long elapsedTime;

		// For local test setting
		if (StatisticTest.k != -1) {
			StatisticTest.result.put("k", String.valueOf(StatisticTest.k));
			StatisticTest.result.put("time_determine_k", String.valueOf(0));
			return StatisticTest.k;
		}

		int n = ontM.getInputOntNumber();
		int z = ontM.getInputClassSizeTotal();

		if (n <= 2) {
			StatisticTest.result.put("k", String.valueOf(1));
			stopTime = System.currentTimeMillis();
			elapsedTime = stopTime - startTime;
			StatisticTest.result.put("time_determine_k", String.valueOf(elapsedTime));
			return 1;
		}

		double Avg = z / n;

	
		double logAvg = (double) (Math.log(Avg) / Math.log(2));
		double logn = (double) (Math.log(n) / Math.log(2));
		double kk = logAvg / logn;
	
		int k = (int) Math.round(kk);

		if (k == n)
			k = n - 1;

		if (k > n)
			k = n - 1;

		if (k < 1)
			k = 1;

			stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"The number of clusters has been determined as: " + k + " . Total time  " + elapsedTime + " ms. \n");

		StatisticTest.result.put("k", String.valueOf(k));
		StatisticTest.result.put("time_determine_k", String.valueOf(elapsedTime));

		return k;
	}



}
