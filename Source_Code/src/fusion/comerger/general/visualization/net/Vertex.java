package fusion.comerger.general.visualization.net;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
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
