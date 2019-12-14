package fusion.comerger.general.visualization.net;

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
