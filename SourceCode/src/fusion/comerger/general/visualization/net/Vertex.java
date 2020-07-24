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
 * A vertex in a Pajek network.
 */
public class Vertex {

	public static enum Shape {
		UNSPECIFIED, ELLIPSE, BOX
	};

	private String label;

	private Shape shape;

	/**
	 * Creates a vertex.
	 * 
	 * @param label
	 *            the vertex label
	 */
	public Vertex(String label) {
		this(label, Shape.UNSPECIFIED);
	}

	/**
	 * Creates a vertex.
	 * 
	 * @param label
	 *            the vertex label
	 */
	public Vertex(String label, Shape shape) {
		this.label = label;
		this.shape = shape;
	}

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Gets the shape.
	 * 
	 * @return the shape
	 */
	public Shape getShape() {
		return shape;
	}

}