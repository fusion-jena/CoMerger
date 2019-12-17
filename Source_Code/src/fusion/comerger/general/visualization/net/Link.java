package fusion.comerger.general.visualization.net;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

/**
 * A relation in a Pajek network.
 */
public abstract class Link {

	public static enum Type {
		EDGE, ARC
	};

	private Vertex srcVertex;

	private Vertex dstVertex;

	private float strength;

	private String type;

	private String label;

	/**
	 * Creates a link.
	 * 
	 * @param srcVertex
	 *            the source vertex
	 * @param dstVertex
	 *            the destination vertex
	 * @param strength
	 *            the strength
	 * @param type
	 *            the type
	 * @param label
	 *            the label
	 */
	public Link(Vertex srcVertex, Vertex dstVertex, float strength,
			String type, String label) {
		this.srcVertex = srcVertex;
		this.dstVertex = dstVertex;
		this.strength = strength;
		this.type = type;
		this.label = label;
	}

	/**
	 * Returns the source vertex.
	 * 
	 * @return the source vertex
	 */
	protected Vertex getSrcVertex() {
		return srcVertex;
	}

	/**
	 * Returns the label.
	 * 
	 * @return the label
	 */

	public String getLabel() {
		return label;
	}

	/**
	 * Returns the strength.
	 * 
	 * @return the strength
	 */
	public float getStrength() {
		return strength;
	}

	/**
	 * Returns the destination vertex.
	 * 
	 * @return the destination vertex
	 */
	protected Vertex getDstVertex() {
		return dstVertex;
	}

	/**
	 * Returns the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the strength.
	 * 
	 * @param strength
	 *            the strength
	 */
	public void setStrength(float strength) {
		this.strength = strength;
	}

}
