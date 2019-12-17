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
 
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
	public BFS() {
	}

	/////////////////////////////////////////
	/////////////// BFS /////////////////////
	/////////////////////////////////////////
	public static int[] BFS(int[][] matrix, int index1, int index2) {
		Queue queue = new LinkedList();
		Queue<String> queuePath = new LinkedList();
		Queue queueDistance = new LinkedList();
		String path = "";
		queue.add(index1);
		queuePath.add(path);
		queueDistance.add(0);
		int jj = 0;
		int numberOfChildren = 0;
		int[] arrayChildIndex = new int[numberOfChildren];

		String[] arrayPathEndToIndex2 = new String[matrix.length]; // it must be
																	// an
																	// ArrayList
		int[] arrayDistancePathToIndex2 = new int[matrix.length]; // it must be
																	// an
																	// ArrayList
		for (int i = 0; i < matrix.length; i++) {
			arrayDistancePathToIndex2[i] = -1;
		}

		while (index1 != -1) {
			arrayChildIndex = arrayChildIndex(matrix, index1);
			numberOfChildren = numberOfChildren(matrix, index1);
			path = path.concat(Integer.toString(index1));
			for (int i = 0; i < numberOfChildren; i++) {
				if (!(path.contains(Integer.toString(arrayChildIndex[i])))) { // if
																				// the
																				// child
																				// is'nt
																				// in
																				// the
																				// path
					queue.add(arrayChildIndex[i]);
					queuePath.add(path);
					queueDistance.add(path.length());

					if (arrayChildIndex[i] == index2) { // if we receive to
														// index2
						if (jj < matrix.length) {
							arrayPathEndToIndex2[jj] = path;
							arrayDistancePathToIndex2[jj] = arrayPathEndToIndex2[jj].length();
							jj++;
						}
					}
				}
			}

			queue.remove();
			queuePath.remove();
			queueDistance.remove();
			if (queue.isEmpty()) {
				index1 = -1;
			} else {
				index1 = (int) queue.element(); // update index1
				path = (String) queuePath.element(); // update path
			}
		}

		int shortestPathLength = -1;
		int numberOfShortestPath = 0;
		if (arrayPathEndToIndex2[0] != null) {
			shortestPathLength = arrayDistancePathToIndex2[0];
			numberOfShortestPath++;
			for (int i = 1; i < matrix.length; i++) {
				// find Min of distance of shortest path
				if (arrayDistancePathToIndex2[i] != -1 && arrayDistancePathToIndex2[i] < shortestPathLength) {
					shortestPathLength = arrayDistancePathToIndex2[i];
				}
				if (arrayDistancePathToIndex2[i] == shortestPathLength) {
					numberOfShortestPath++;
				}
			}
		}

		int[] NumAndLength = new int[2];
		NumAndLength[0] = numberOfShortestPath;
		NumAndLength[1] = shortestPathLength;

		arrayChildIndex = null;
		arrayPathEndToIndex2 = null;
		arrayDistancePathToIndex2 = null;

		return NumAndLength;
	}

	//////////////////////////////////////////////////////////////////////////////////
	//// output: an array which contains the index of children of input
	////////////////////////////////////////////////////////////////////////////////// index//////////
	//////////////////////////////////////////////////////////////////////////////////
	public static int[] arrayChildIndex(int[][] matrix, int index) {
		int[] LongArrayChildIndex = new int[matrix.length];
		for (int i = 0; i < matrix.length; i++)
			LongArrayChildIndex[i] = -1;

		int numberOfChildren = 0;
		for (int j = 0; j < matrix.length; j++) {
			;
			if (matrix[index][j] == 1) {
				LongArrayChildIndex[numberOfChildren] = j;
				numberOfChildren++;
			}
		}

		// truncate "LongArrayChildIndex" to eliminate -1
		int[] ArrayChildIndex = new int[numberOfChildren];
		for (int i = 0; i < numberOfChildren; i++)
			ArrayChildIndex[i] = LongArrayChildIndex[i];

		LongArrayChildIndex = null;
		return ArrayChildIndex;
	}

	///////////////////////////////////////////////////////////////////
	////////// output: number of children of input index////////////////
	///////////////////////////////////////////////////////////////////
	public static int numberOfChildren(int[][] matrix, int index) {
		int[] LongArrayChildIndex = new int[matrix.length];
		for (int i = 0; i < matrix.length; i++)
			LongArrayChildIndex[i] = -1;

		int numberOfChildren = 0;
		for (int j = 0; j < matrix.length; j++)
			if (matrix[index][j] == 1)
				numberOfChildren++;

		return numberOfChildren;
	}
}
