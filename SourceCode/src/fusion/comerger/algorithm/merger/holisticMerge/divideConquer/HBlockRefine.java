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
import java.util.logging.Level;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.model.HModel;

public class HBlockRefine {

	public HModel run(HModel ontM, String selectedRules) {
		long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long startTime = System.currentTimeMillis();

		MyLogging.log(Level.INFO,
				"Keeping a history of changes in the refinement step after clustering (for each cluster separately) in following. \n ");

		ontM.setRefineActionOnCluster(0);

		String rules[] = selectedRules.split(",");
		for (int i = 0; i < rules.length; i++) {
			switch (rules[i]) {
			case "ClassCheck":
				ontM = HBlockRefinePacket.ClassesPreservation(ontM);
				break;

			case "ProCheck":
				ontM = HBlockRefinePacket.PropertiesPreservation(ontM);
				break;

			case "InstanceCheck":
				ontM = HBlockRefinePacket.InstancesPreservation(ontM);
				break;

			case "CorresCheck":
				ontM = HBlockRefinePacket.CorrespondencesPreservation(ontM);
				break;

			case "CorssPropCheck":
				ontM = HBlockRefinePacket.CorrespondPropertyPreservation(ontM);
				break;

			case "ValueCheck":
				ontM = HBlockRefinePacket.ValuePreservation(ontM);
				break;

			case "StrCheck":
				ontM = HBlockRefinePacket.StructurePreservation(ontM);
				break;

			case "ClRedCheck":
				ontM = HBlockRefinePacket.ClassRedundancyProhibition(ontM);
				break;

			case "ProRedCheck":
				ontM = HBlockRefinePacket.PropertyRedundancyProhibition(ontM);
				break;

			case "InstRedCheck":
				ontM = HBlockRefinePacket.InstanceRedundancyProhibition(ontM);
				break;

			case "ExtCheck":
				ontM = HBlockRefinePacket.ExtraneousEntityProhibition(ontM);
				break;

			case "EntCheck":
				ontM = HBlockRefinePacket.EntailmentSatisfaction(ontM);
				break;

			case "TypeCheck":
				ontM = HBlockRefinePacket.OneTypeRestriction(ontM);
				break;

			case "ConstValCheck":
				ontM = HBlockRefinePacket.ValueConstraint(ontM);
				break;

			case "DomRangMinCheck":
				ontM = HBlockRefinePacket.DomainRangeMinimality(ontM);
				break;

			case "AcyClCheck":
				ontM = HBlockRefinePacket.ClassAcyclicity(ontM);
				break;

			case "AcyProCheck":
				ontM = HBlockRefinePacket.PropertyAcyclicity(ontM);
				break;

			case "RecProCheck":
				ontM = HBlockRefinePacket.InversePropertyProhibition(ontM);
				break;

			case "UnconnClCheck":
				ontM = HBlockRefinePacket.UnconnectedClassProhibition(ontM);
				break;

			case "UnconnProCheck":
				ontM = HBlockRefinePacket.UnconnectedPropertyProhibition(ontM);
				break;

			}
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"\t ---------> Refinement step after clustering (for each cluster separately) has been done successfully. Total time  "
						+ elapsedTime + " ms. \n");

		StatisticTest.result.put("time_cluster_refinement", String.valueOf(elapsedTime));

		StatisticTest.result.put("refine_action_on_cluster", String.valueOf(ontM.getRefineActionOnCluster()));

		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long actualMemUsed = afterUsedMem - beforeUsedMem;
		StatisticTest.result.put("MU_ClusterRefine_Total", String.valueOf(actualMemUsed));
		return ontM;
	}

}
