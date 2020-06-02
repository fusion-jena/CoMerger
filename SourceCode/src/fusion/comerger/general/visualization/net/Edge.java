package fusion.comerger.general.visualization.net;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
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
 * An edge in a Pajek network.
 */
public class Edge extends Link {

	/**
	 * Creates an edge.
	 * 
	 * @param vertex1
	 *            the first vertex
	 * @param vertex2
	 *            the second vertex
	 * @param strength
	 *            the strength
	 * @param type
	 *            the type
	 * @param label
	 *            the label
	 */
	public Edge(Vertex vertex1, Vertex vertex2, float strength, String type,
			String label) {
		super(vertex1, vertex2, strength, type, label);
	}

	/**
	 * Creates an edge based on a link.
	 * 
	 * @param link
	 *            the link
	 */
	public Edge(Link link) {
		super(link.getSrcVertex(), link.getDstVertex(), link.getStrength(),
				link.getType(), link.getLabel());
	}

	/**
	 * Returns the first vertex.
	 * 
	 * @return the first vertex
	 */
	public Vertex getVertex1() {
		return getSrcVertex();
	}

	/**
	 * Returns the second vertex.
	 * 
	 * @return the second vertex
	 */
	public Vertex getVertex2() {
		return getDstVertex();
	}

}
