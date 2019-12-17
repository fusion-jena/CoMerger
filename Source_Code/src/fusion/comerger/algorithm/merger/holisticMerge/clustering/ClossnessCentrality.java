package fusion.comerger.algorithm.merger.holisticMerge.clustering;

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
import java.util.Iterator;

import org.semanticweb.owlapi.model.OWLClass;

import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;

public class ClossnessCentrality {
	public ClossnessCentrality() {
	}

	///////////////////////////////////////////////////////////////////
	////////////// CCvalue(i) = 1/sigma d(i,j) <-- float ///////////////////
	///////////////////////////////////////////////////////////////////
	public static float CCforOneNode(int[][] matrix, int index1) {
		BFS bfs = new BFS();
		float CCvalue = 0;
		int sigma = 0; // sigma d(i,j)
		int distance = 0;
		for (int index2 = 0; index2 < matrix.length; index2++) {
			if (index2 != index1) {
				distance = bfs.BFS(matrix, index1, index2)[1];
				if (distance > 0) {
					sigma = sigma + distance;
				}
			}
		}
		if (sigma != 0) {
			CCvalue = (float) (1.0 / sigma);
		} else {
			CCvalue = 0;
		}
		return CCvalue;
	}

	///////////////////////////////////////////////////////////////////
	/////////// output: array of CCvalue of all nodes /////////////////
	///////////////////////////////////////////////////////////////////
	public static ArrayList<HMappedClass> run(int[][] matrix, HashMap<OWLClass, Integer> indexFile,
			ArrayList<HMappedClass> cSet) {
		
		for (int i=0; i<cSet.size(); i++){
			OWLClass c  = cSet.get(i).getRefClass();
			int index = CentralityCalculator.findIndex(c, indexFile);
			double cc = CCforOneNode(matrix, index);
			cSet.get(i).setCentrality(cc);
		}
		
		return cSet;
	}

	
	public static HashMap<OWLClass, Double> runForAll(int[][] matrix, HashMap<OWLClass, Integer> indexFile,
			ArrayList<OWLClass> cSet) {
		HashMap<OWLClass, Double> ClossnessCent = new HashMap<OWLClass, Double>();
		Iterator<OWLClass> iter = cSet.iterator();

		while (iter.hasNext()) {
			OWLClass c = iter.next();
			int index = CentralityCalculator.findIndex(c, indexFile);
			double cc = CCforOneNode(matrix, index);
			ClossnessCent.put(c, cc);

		}
		
		return ClossnessCent;
	}
}
