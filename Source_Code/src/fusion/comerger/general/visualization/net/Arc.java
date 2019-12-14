package fusion.comerger.general.visualization.net;

/**
 * An arc in a Pajek network.
 */
public class Arc extends Link {

	/**
	 * Creates an arc.
	 * 
	 * @param fromVertex
	 *            the from-vertex
	 * @param toVertex
	 *            the to-vertex
	 * @param strength
	 *            the strenth
	 * @param type
	 *            the type
	 * @param label
	 *            the label
	 */
	public Arc(Vertex fromVertex, Vertex toVertex, float strength, String type,
			String label) {
		super(fromVertex, toVertex, strength, type, label);
	}

	/**
	 * Creates an arc based on a link.
	 * 
	 * @param link
	 *            the link
	 */
	public Arc(Link link) {
		super(link.getSrcVertex(), link.getDstVertex(), link.getStrength(),
				link.getType(), link.getLabel());
	}

	/**
	 * Returns the from-vertex.
	 * 
	 * @return the from-vertex
	 */
	public Vertex getFromVertex() {
		return getSrcVertex();
	}

	/**
	 * Returns the to-vertex.
	 * 
	 * @return the to-vertex
	 */
	public Vertex getToVertex() {
		return getDstVertex();
	}

}
