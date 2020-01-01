package fusion.comerger.algorithm.merger.holisticMerge.divideConquer;
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
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;
import fusion.comerger.algorithm.merger.model.HModel;

public class HDivideConquer {

	public HModel run(HModel ontM, HashMap<OWLClassExpression, HashSet<OWLClassExpression>> adjacentList) {
		long startTime = System.currentTimeMillis();
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		/* Step1: determine the cores */
		ArrayList<HMappedClass> coreList = new ArrayList<HMappedClass>();
		coreList = FindCores(ontM, adjacentList);

		/* Step2: divide it */
		ontM = division(ontM, coreList, adjacentList);

		/* Setp 3: add properrties */
		AssignBlockProperties acp = new AssignBlockProperties();
		ontM = acp.run(ontM);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Clustering has been done successfully. Total time for the whole process of clustering:  " + elapsedTime
						+ " ms. \n");

		StatisticTest.result.put("time_whole_clustering", String.valueOf(elapsedTime));
		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - beforeUsedMem;
		StatisticTest.result.put("MU_Clustering", String.valueOf(actualMemUsed));
		// System.gc();
		return ontM;
	}

	private HModel division(HModel ontM, ArrayList<HMappedClass> coreList,
			HashMap<OWLClassExpression, HashSet<OWLClassExpression>> adjacentList) {

		long stTime = System.currentTimeMillis();

		if (coreList.size() < 1)
			return ontM;


		boolean breakDown = true;
		int numBlock = 0;

		ArrayList<BlockModel> ClusterList = new ArrayList<BlockModel>();
		HashSet<OWLClassExpression> alreadyAdded = new HashSet<OWLClassExpression>();

		// creating the 1st block
		try {
			BlockModel cm = new BlockModel();
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			String clusterName = ontM.getPath() + "cluster" + numBlock + ".owl";
			File fileM = new File(clusterName);
			OWLOntology clusterOntology = manager.createOntology(IRI.create(fileM));
			cm.setManager(manager);
			cm.setOntology(clusterOntology);

			HMappedClass c = coreList.get(numBlock);
			coreList.get(numBlock).setSelectedStatus(true);
			cm.SetCore(c);

			ArrayList<OWLClassExpression> elm = new ArrayList<OWLClassExpression>();
			elm.add(c.getRefClass());

			alreadyAdded.add(c.getRefClass());

			HashSet<OWLClassExpression> temp = adjacentList.get(c.getRefClass());
			HashSet<OWLClassExpression> recentlyAdded = temp;

			while (recentlyAdded != null && recentlyAdded.size() > 0) {
				elm.addAll(recentlyAdded);
				Iterator<OWLClassExpression> iter = recentlyAdded.iterator();
				recentlyAdded = new HashSet<OWLClassExpression>();
				while (iter.hasNext()) {
					OWLClassExpression cc = iter.next();
					Iterator<OWLClassExpression> newRecent = adjacentList.get(cc).iterator();
					while (newRecent.hasNext()) {
						OWLClassExpression cep = newRecent.next();
						if (!alreadyAdded.contains(cep)) {
							recentlyAdded.add(cep);
							alreadyAdded.add(cep);
						}
					}
				}
			}

			cm.SetClasses(elm);

			ClusterList.add(cm);

			numBlock++;

		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
			MyLogging.log(Level.SEVERE, "Exception from assigning cluster: " + e.toString());
		}

		// generating other blocks
		if (numBlock > coreList.size())
			breakDown = false;

		try {
			while (breakDown) {
				BlockModel cm = new BlockModel();
				OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
				String clusterName = ontM.getPath() + "cluster" + numBlock + ".owl";
				File fileM = new File(clusterName);
				OWLOntology clusterOntology = manager.createOntology(IRI.create(fileM));
				cm.setManager(manager);
				cm.setOntology(clusterOntology);

				HMappedClass c = selectNextCore(coreList, ClusterList, alreadyAdded);
				if (c == null) {
					breakDown = false;
					break;
				}
				cm.SetCore(c);
				ArrayList<OWLClassExpression> elm = new ArrayList<OWLClassExpression>();
				elm.add(c.getRefClass());

				alreadyAdded.add(c.getRefClass());

				HashSet<OWLClassExpression> temp = adjacentList.get(c.getRefClass());
				HashSet<OWLClassExpression> recentlyAdded = temp;

				while (recentlyAdded != null && recentlyAdded.size() > 0) {
					elm.addAll(recentlyAdded);
					Iterator<OWLClassExpression> iter = recentlyAdded.iterator();
					recentlyAdded = new HashSet<OWLClassExpression>();
					while (iter.hasNext()) {
						OWLClassExpression cc = iter.next();
						Iterator<OWLClassExpression> newRecent = adjacentList.get(cc).iterator();// #adjacentList
																									// has
																									// error
						while (newRecent.hasNext()) {
							OWLClassExpression cep = newRecent.next();
							if (!alreadyAdded.contains(cep)) {
								recentlyAdded.add(cep);
								alreadyAdded.add(cep);
							}
						}
					}
				}

				cm.SetClasses(elm);

				ClusterList.add(cm);

				numBlock++;

				if (numBlock > coreList.size())
					breakDown = false;
			}

		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
			MyLogging.log(Level.SEVERE, "Exception from assigning cluster: " + e.toString());
		}

		ontM.SetClusters(ClusterList);

		StatisticTest.result.put("k", String.valueOf(numBlock));

		String clusterSize = "";
		for (int i = 0; i < ClusterList.size(); i++) {
			if (clusterSize.length() < 1) {
				clusterSize = String.valueOf(ClusterList.get(i).getClasses().size());
			} else {
				clusterSize = clusterSize + ";" + ClusterList.get(i).getClasses().size();
			}

		}

		StatisticTest.result.put("cluster_class_size", String.valueOf(clusterSize));

		long sTime = System.currentTimeMillis();
		long eTime = sTime - stTime;
		MyLogging.log(Level.INFO,
				"Cluster assignment has been done successfully in with 1 iteration. Total time: " + eTime + " \n");

		StatisticTest.result.put("time_clustering_assignment", String.valueOf(eTime));
		return ontM;
	}

	private HMappedClass selectNextCore(ArrayList<HMappedClass> coreList, ArrayList<BlockModel> clusterList,
			HashSet<OWLClassExpression> alreadyAdded) {
		/*
		 * the core list is already soreted based on the goodness. here we only
		 * check if the next core is not already in other blocks
		 */

		Set<OWLClassExpression> allPreviousClasses = new HashSet<OWLClassExpression>();
		for (int i = 0; i < clusterList.size(); i++) {
			allPreviousClasses.addAll(clusterList.get(i).getClasses());
		}

		HMappedClass tempHMapped = new HMappedClass();
		Set<OWLClass> notAddedMappedClass = new HashSet<OWLClass>();

		for (int i = 0; i < coreList.size(); i++) {
			/*
			 * we do not create a block for a mapped entities which does not
			 * have any connection to other classes
			 */
			if (coreList.get(i).getConnection() == 0)
				break;

			if (coreList.get(i).getSelectedStatus() != true) {
				if (alreadyAdded.contains(coreList.get(i).getRefClass()))
					break;

				int found = 0;
				Iterator<OWLClass> iter = coreList.get(i).getMappedCalss().iterator();
				while (iter.hasNext()) {
					OWLClass c = iter.next();
					if (allPreviousClasses.contains(c)) {
						found++;
					} else {
						notAddedMappedClass.add(c);
					}
				}
				if (found == 0) {
					coreList.get(i).setSelectedStatus(true);
					return coreList.get(i);
				} else if (found > 0 && found < coreList.get(i).getLenClass()) {
					// to copy all other setting
					tempHMapped = coreList.get(i);
					// now rewrite the elements of this mappClass with those one
					// which are not already selected
					tempHMapped.setMappedClass(notAddedMappedClass);
					tempHMapped.setSelectedStatus(true);
					return tempHMapped;
				}
			}
		}

		/*
		 * if it returns null, means all corelist elements are already selected,
		 * so the breaking process should be finished also
		 */
		return null;
	}

	private ArrayList<HMappedClass> FindCores(HModel ontM,
			HashMap<OWLClassExpression, HashSet<OWLClassExpression>> adjacentList) {

		long stTime = System.currentTimeMillis();
		ArrayList<HMappedClass> mapList = ontM.getEqClasses();

		if (mapList.size() < 1) {
			Iterator<OWLClass> iter = ontM.getOwlModel().getClassesInSignature().iterator();
			if (iter.hasNext()) {
				OWLClass c = iter.next();

				HMappedClass mp = new HMappedClass();
				mp.setRefClass(c);
				Set<OWLClass> s = new HashSet<OWLClass>();
				s.add(c);
				mp.setMappedClass(s);
				mapList.add(mp);

				return mapList;
			}
		}

		// set goodness degree
		int maxLen = 0;
		for (int i = 0; i < mapList.size(); i++) {
			if (adjacentList.get(mapList.get(i).getRefClass()) != null) {
				int con = adjacentList.get(mapList.get(i).getRefClass()).size();
				mapList.get(i).setConnection(con);
				int len = mapList.get(i).getLenClass();
				if (len > maxLen)
					maxLen = len;
				mapList.get(i).setGoodness((double) (len * con));
			} else {
				mapList.get(i).setGoodness((double) 0.0);
				// since the connection of this class is 0
			}
		}
		StatisticTest.result.put("Max-Cardinality", String.valueOf(maxLen));

		// sort it based on the goodness
		Collections.sort(mapList, new Comparator<HMappedClass>() {
			public int compare(HMappedClass s1, HMappedClass s2) {
				return Double.compare(s2.getGoodness(), s1.getGoodness());
			}
		});
		;

		long sTime = System.currentTimeMillis();
		long eTime = sTime - stTime;

		StatisticTest.result.put("time_determine_cores", String.valueOf(eTime));
		return mapList;
	}

}
