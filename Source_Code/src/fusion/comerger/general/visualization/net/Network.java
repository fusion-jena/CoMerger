
package fusion.comerger.general.visualization.net;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fusion.comerger.general.visualization.Coordinator;

//import fusion.oapt.general.cc.Coordinator;

/**
 * A Pajek network.
 */
public class Network {

	private List<Vertex> vertices = new ArrayList<Vertex>();

	private List<Arc> arcs = new ArrayList<Arc>();

	private List<Edge> edges = new ArrayList<Edge>();

	private int nextVertexId = 1;

	public static final Pattern P_VERTICES = Pattern.compile(
			"^\\s*\\*vertices", Pattern.CASE_INSENSITIVE);

	public static final Pattern P_ARCS = Pattern.compile("^\\s*\\*arcs",
			Pattern.CASE_INSENSITIVE);

	public static final Pattern P_EDGES = Pattern.compile("^\\s*\\*edges",
			Pattern.CASE_INSENSITIVE);

	public static final Pattern P_EMPTY = Pattern.compile("^\\s*$");

	public static final Pattern P_VERTEX_1 = Pattern
			.compile("^\\s*(\\d+)\\s+\"(.*)\"");

	public static final Pattern P_VERTEX_2 = Pattern
			.compile("^\\s*(\\d+)\\s+(.*)");

	public static final Pattern P_VERTEX_3 = Pattern
			.compile("^\\s*(\\d+)\\s+\"(.*)\"\\s+(.*)");

	public static final Pattern P_LINK_1 = Pattern
			.compile("^\\s*(\\d+)\\s+(\\d+)\\s+([\\d\\.]+)\\s+(\\S+)\\s+\"(.*)\"");

	public static final Pattern P_LINK_2 = Pattern
			.compile("^\\s*(\\d+)\\s+(\\d+)\\s+([\\d\\.]+)\\s+(\\S+)\\s+(.*)");

	// Maps vertices to their stepwise increasing IDs
	private Map<Vertex, Integer> vertexToId = new HashMap<Vertex, Integer>();

	private Map<Integer, Vertex> idToVertex = new HashMap<Integer, Vertex>();

	private static enum ParserState {
		TOP, PARSE_VERTICES, PARSE_ARCS, PARSE_EDGES
	};

	/**
	 * Returns the arcs in this network.
	 * 
	 * @return the arcs
	 */
	public List<Arc> getArcs() {
		return arcs;
	}

	/**
	 * Sets the arcs of this network.
	 * 
	 * @param arcs
	 *            the arcs
	 */
	public void setArcs(List<Arc> arcs) {
		this.arcs = arcs;
	}

	/**
	 * Returns the edges in this network.
	 * 
	 * @return the edges
	 */
	public List<Edge> getEdges() {
		return edges;
	}

	/**
	 * Sets the edges of this network.
	 * 
	 * @param edges
	 *            the edges
	 */
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	/**
	 * Gets the vertices in this network.
	 * 
	 * @return the vertices
	 */
	public List<Vertex> getVertices() {
		return vertices;
	}

	/**
	 * Sets the vertices of this network.
	 * 
	 * @param vertices
	 *            the vertices
	 */
	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices;

		// Give each vertex a new ID
		vertexToId.clear();
		idToVertex.clear();
		nextVertexId = 1;
		for (Vertex vertex : vertices) {
			vertexToId.put(vertex, nextVertexId);
			idToVertex.put(nextVertexId, vertex);
			nextVertexId++;
		}
	}

	/**
	 * Adds a vertex to this network.
	 * 
	 * @param vertex
	 *            the vertex
	 */
	public void addVertex(Vertex vertex) {
		vertices.add(vertex);
		vertexToId.put(vertex, nextVertexId);
		idToVertex.put(nextVertexId, vertex);
		nextVertexId++;
	}

	/**
	 * Adds a vertex with the specified label to this network.
	 * 
	 * @param label
	 *            a label
	 * @return the vertex
	 */
	public Vertex addVertex(String label) {
		Vertex vertex = new Vertex(label);
		addVertex(vertex);
		return vertex;
	}

	/**
	 * Adds a vertex with the specified label and shape to this network.
	 * 
	 * @param label
	 *            a label
	 * @return the vertex
	 */
	public Vertex addVertex(String label, Vertex.Shape shape) {
		Vertex vertex = new Vertex(label, shape);
		addVertex(vertex);
		return vertex;
	}

	/**
	 * Adds an arc to this network.
	 * 
	 * @param arc
	 *            the arc
	 */
	public void addArc(Arc arc) {
		arcs.add(arc);
	}

	/**
	 * Adds an edge to this network.
	 * 
	 * @param edge
	 *            an edge
	 */
	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	/**
	 * Gets the next free vertex ID.
	 * 
	 * @return the vertex ID
	 */
	public int getNextVertexId() {
		return nextVertexId;
	}

	/**
	 * Gets the vertex with the specified label.
	 * 
	 * @param label
	 *            a vertex label
	 * @return the vertex, or <code>null</code> if no vertex with the
	 *         specified label exists in this network
	 */
	public Vertex getVertex(String label) {
		for (Vertex vertex : vertices) {
			if (vertex.getLabel().equals(label)) {
				return vertex;
			}
		}
		return null;
	}

	/**
	 * Gets the vertex with the specified ID.
	 * 
	 * @param id
	 *            a vertex ID
	 * @return the vertex, or <code>null</code> if no vertex with the
	 *         specified ID exists in this network
	 */
	public Vertex getVertex(int id) {
		return idToVertex.get(id);
	}

	/**
	 * Tests if a vertex with the specified label is contained in this network.
	 * 
	 * @param label
	 *            a vertex label
	 * @return <code>true</code> if the vertex is contained,
	 *         <code>false</code> otherwise
	 */
	public boolean containsVertex(String label) {
		for (Vertex vertex : vertices) {
			if (vertex.getLabel().equals(label)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Writes this network in Pajek format to a file.
	 * 
	 * @param file
	 *            a file
	 * @throws IOException
	 *             if an I/O error occurs
	 * @see #write(Writer)
	 */
	public void write(File file) throws IOException {
		Writer out = new FileWriter(file);
		write(out);
		out.close();
	}

	/**
	 * Writes this network in Pajek format to an output stream. The vertex IDs
	 * are renumbered, starting from 1. If the vertex IDs were already stepwise
	 * increasing, the result will be the same.
	 * 
	 * @param out
	 *            a character stream writer
	 */
	public void write(Writer out) {
		// Create a print writer
		PrintWriter printOut = new PrintWriter(out);

		// Write the vertices
		printOut.println("*Vertices " + vertices.size());
		int lev =0;
		for (Vertex vertex : vertices) {
			lev ++;
			if(Coordinator.levelDetail==1 || lev % Coordinator.levelDetail ==0){	
				writeVertex(vertex, printOut);
			}
		}

		// Write the arcs
		printOut.println("*Arcs");
		for (Arc arc : arcs) {
			writeLink(arc, printOut);
		}

		// Write the edges
		printOut.println("*Edges");
		for (Edge edge : edges) {
			writeLink(edge, printOut);
		}

		// Close the writer
		printOut.close();

	}

	private void writeVertex(Vertex vertex, PrintWriter out) {
		out.printf("%d \"%s\"", vertexToId.get(vertex), vertex.getLabel());
		switch (vertex.getShape()) {
		case ELLIPSE:
			out.print(" ellipse");
			break;
		case BOX:
			out.print(" box");
			break;
		}
		out.println();
	}

	private void writeLink(Link link, PrintWriter out) {
		out.printf(Locale.US, "%d %d %.7f %s \"%s\"%n", vertexToId.get(link
				.getSrcVertex()), vertexToId.get(link.getDstVertex()), link
				.getStrength(), link.getType(), link.getLabel());
	}

	/**
	 * Reads a network in Pajek format from a file.
	 * 
	 * @param file
	 *            a file
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public void read(File file) throws IOException {

		read(new FileReader(file));
	}

	/**
	 * Reads a network in Pajek format from a character stream.
	 * 
	 * @param reader
	 *            a character stream reader
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public void read(Reader reader) throws IOException {
		LineNumberReader in = new LineNumberReader(reader);
		in.setLineNumber(1);
		parseLines(in);
		in.close();

	}

	private void parseLines(LineNumberReader in) throws IOException {
		// Initialize the parser state
		ParserState state = ParserState.TOP;

		// Read the first line
		String line = in.readLine();

		// Process all lines
		Matcher m;
		while (line != null) {
			switch (state) {
			case TOP:
				if (P_VERTICES.matcher(line).lookingAt()) {
					// Beginning of vertices
					state = ParserState.PARSE_VERTICES;
				} else if (P_ARCS.matcher(line).lookingAt()) {
					// Beginning of arcs
					state = ParserState.PARSE_ARCS;
				} else if (P_EDGES.matcher(line).lookingAt()) {
					// Beginning of edges
					state = ParserState.PARSE_EDGES;
				} else if (P_EMPTY.matcher(line).matches()) {
					// Empty line
				} else {
					// Parse error
					System.err.printf("Warning: Parse error at line %d", in
							.getLineNumber());
				}

				// Read the next line
				line = in.readLine();
				break;

			case PARSE_VERTICES:
				m = P_VERTEX_1.matcher(line);
				if (!m.lookingAt()) {
					m = P_VERTEX_2.matcher(line);
				}
				if (m.lookingAt()) {
					// Get the ID and the label of the vertex
					int vertexId = Integer.parseInt(m.group(1));
					String vertexLabel = m.group(2);

					// Test if the shape of the vertex is also specified
					m = P_VERTEX_3.matcher(line);
					fusion.comerger.general.visualization.net.Vertex.Shape vertexShape;
					if (m.lookingAt()) {
						String vertexShapeString = m.group(3);
						if (vertexShapeString.equals("ellipse")) {
							vertexShape = fusion.comerger.general.visualization.net.Vertex.Shape.ELLIPSE;
						} else if ((vertexShapeString.equals("box"))) {
							vertexShape = fusion.comerger.general.visualization.net.Vertex.Shape.BOX;
						} else {
							vertexShape = fusion.comerger.general.visualization.net.Vertex.Shape.UNSPECIFIED;
						}
					} else {
						vertexShape = fusion.comerger.general.visualization.net.Vertex.Shape.UNSPECIFIED;
					}

					// Add the vertex to this network
					Vertex vertex = new Vertex(vertexLabel, vertexShape);
					addVertex(vertex);

					// Read the next line
					line = in.readLine();
				} else if (P_EMPTY.matcher(line).matches()) {
					// Empty line
					line = in.readLine();
				} else {
					state = ParserState.TOP;
				}
				break;

			case PARSE_ARCS:
				m = P_LINK_1.matcher(line);
				if (!m.lookingAt()) {
					m = P_LINK_2.matcher(line);
				}
				if (m.lookingAt()) {
					int vertex1Id = Integer.parseInt(m.group(1));
					Vertex vertex1 = getVertex(vertex1Id);
					if (vertex1 == null) {
						System.err
								.printf(
										"Warning: First vertex at line %d is not defined%n",
										in.getLineNumber());
					} else {
						int vertex2Id = Integer.parseInt(m.group(2));
						Vertex vertex2 = getVertex(vertex2Id);
						if (vertex2 == null) {
							System.err
									.printf(
											"Warning: Second vertex at line %d is not defined%n",
											in.getLineNumber());
						} else {
							float strength = Float.parseFloat(m.group(3));
							String type = m.group(4);
							String label = m.group(5);
							Arc arc = new Arc(vertex1, vertex2, strength, type,
									label);
							addArc(arc);
						}
					}

					// Read the next line
					line = in.readLine();
				} else if (P_EMPTY.matcher(line).matches()) {
					// Empty line
					line = in.readLine();
				} else {
					state = ParserState.TOP;
				}
				break;

			case PARSE_EDGES:
				m = P_LINK_1.matcher(line);
				if (!m.lookingAt()) {
					m = P_LINK_2.matcher(line);
				}
				if (m.lookingAt()) {
					int vertex1Id = Integer.parseInt(m.group(1));
					Vertex vertex1 = getVertex(vertex1Id);
					if (vertex1 == null) {
						System.err
								.printf(
										"Warning: First vertex at line %d is not defined%n",
										in.getLineNumber());
					} else {
						int vertex2Id = Integer.parseInt(m.group(2));
						Vertex vertex2 = getVertex(vertex2Id);
						if (vertex2 == null) {
							System.err
									.printf(
											"Warning: Second vertex at line %d is not defined%n",
											in.getLineNumber());
						} else {
							float strength = Float.parseFloat(m.group(3));
							String type = m.group(4);
							String label = m.group(5);
							Edge edge = new Edge(vertex1, vertex2, strength,
									type, label);
							addEdge(edge);
						}
					}

					// Read the next line
					line = in.readLine();
				} else if (P_EMPTY.matcher(line).matches()) {
					// Empty line
					line = in.readLine();
				} else {
					state = ParserState.TOP;
				}
				break;
			}
		}
	}

	/**
	 * Gets the ID of a vertex in this network. This may not be the same as the
	 * ID stored in the vertex. In a network, the vertex IDs start from 1 and
	 * increase by 1.
	 * 
	 * @param vertex
	 *            a vertex
	 * @return the vertex ID in this network
	 */
	public int getVertexId(Vertex vertex) {
		return vertexToId.get(vertex);
	}

	/**
	 * Gets the vertex with the specified label if it already exists in this
	 * network; otherwise create and add a new vertex.
	 * 
	 * @param label
	 *            a vertex label
	 * @return the vertex
	 */
	public Vertex getOrAddVertex(String label) {
		if (containsVertex(label)) {
			return getVertex(label);
		} else {
			Vertex vertex = addVertex(label);
			return vertex;
		}
	}

	/**
	 * Removes all of the elements (vertices, arcs and edges) from this network.
	 */
	public void clear() {
		vertices.clear();
		arcs.clear();
		edges.clear();
		nextVertexId = 1;
		vertexToId.clear();
		idToVertex.clear();
	}

	/**
	 * Gets the arc that connects the specified vertices.
	 * 
	 * @param fromVertex
	 *            the from-vertex
	 * @param toVertex
	 *            the to-vertex
	 * @return the arc, or <code>null</code> if no such arc exists
	 */
	public Arc getArc(Vertex fromVertex, Vertex toVertex) {
		for (Arc arc : arcs) {
			Vertex aFromVertex = arc.getFromVertex();
			Vertex aToVertex = arc.getToVertex();
			if (aFromVertex.equals(fromVertex) && aToVertex.equals(toVertex)) {
				return arc;
			}
		}
		return null;
	}

	/**
	 * Gets the edge that connects the vertices with the specified labels.
	 * 
	 * @param vertex1
	 *            the first vertex
	 * @param vertex2
	 *            the second vertex
	 * @return the edge, or <code>null</code> if no such edge exists
	 */
	public Edge getEdge(Vertex vertex1, Vertex vertex2) {
		for (Edge edge : edges) {
			Vertex eVertex1 = edge.getVertex1();
			Vertex eVertex2 = edge.getVertex2();
			if ((eVertex1.equals(vertex1) || eVertex1.equals(vertex2))
					&& (eVertex2.equals(vertex1) || eVertex2.equals(vertex2))) {
				return edge;
			}
		}
		return null;
	}

	/**
	 * Gets the arc that connects the vertices with the specified labels.
	 * 
	 * @param fromLabel
	 *            the label of the from-vertex
	 * @param toLabel
	 *            the label of the to-vertex
	 * @return the arc, or <code>null</code> if no such arc exists
	 */
	public Arc getArc(String fromLabel, String toLabel) {
		for (Arc arc : arcs) {
			String aFromLabel = arc.getFromVertex().getLabel();
			String aToLabel = arc.getToVertex().getLabel();
			if (aFromLabel.equals(fromLabel) && aToLabel.equals(toLabel)) {
				return arc;
			}
		}
		return null;
	}

	/**
	 * Gets the edge that connects the vertices with the specified labels.
	 * 
	 * @param label1
	 *            the first label
	 * @param label2
	 *            the second label
	 * @return the edge, or <code>null</code> if no such edge exists
	 */
	public Edge getEdge(String label1, String label2) {
		for (Edge edge : edges) {
			String eLabel1 = edge.getVertex1().getLabel();
			String eLabel2 = edge.getVertex2().getLabel();
			if ((eLabel1.equals(label1) || eLabel1.equals(label2))
					&& (eLabel2.equals(label1) || eLabel2.equals(label2))) {
				return edge;
			}
		}
		return null;
	}

	/**
	 * Tests if an arc between the specified vertices is contained in this
	 * network.
	 * 
	 * @param fromVertex
	 *            the from-vertex
	 * @param toVertex
	 *            the to-vertex
	 * @return <code>true</code> if the arc is contained, <code>false</code>
	 *         otherwise
	 */
	public boolean containsArc(Vertex fromVertex, Vertex toVertex) {
		for (Arc arc : arcs) {
			Vertex aFromVertex = arc.getFromVertex();
			Vertex aToVertex = arc.getToVertex();
			if (aFromVertex.equals(fromVertex) && aToVertex.equals(toVertex)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests if an edge between the specified vertices is contained in this
	 * network.
	 * 
	 * @param vertex1
	 *            the first vertex
	 * @param vertex2
	 *            the second vertex
	 * @return <code>true</code> if the edge is contained, <code>false</code>
	 *         otherwise
	 */
	public boolean containsEdge(Vertex vertex1, Vertex vertex2) {
		for (Edge edge : edges) {
			Vertex eVertex1 = edge.getVertex1();
			Vertex eVertex2 = edge.getVertex2();
			if ((eVertex1.equals(vertex1) && eVertex2.equals(vertex2))
					|| (eVertex2.equals(vertex1) && eVertex1.equals(vertex2))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests if an arc that connects the vertices with the specified labels is
	 * contained in this network.
	 * 
	 * @param fromLabel
	 *            the label of the from-vertex
	 * @param toLabel
	 *            the label of the to-vertex
	 * @return <code>true</code> if the arc is contained, <code>false</code>
	 *         otherwise
	 */
	public boolean containsArc(String fromLabel, String toLabel) {
		for (Arc arc : arcs) {
			String aFromLabel = arc.getFromVertex().getLabel();
			String aToLabel = arc.getToVertex().getLabel();
			if (aFromLabel.equals(fromLabel) && aToLabel.equals(toLabel)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests if an edge that connects the vertices with the specified labels is
	 * contained in this network.
	 * 
	 * @param label1
	 *            the first label
	 * @param label2
	 *            the second label
	 * @return <code>true</code> if the edge is contained, <code>false</code>
	 *         otherwise
	 */
	public boolean containsEdge(String label1, String label2) {
		for (Edge edge : edges) {
			String eLabel1 = edge.getVertex1().getLabel();
			String eLabel2 = edge.getVertex2().getLabel();
			if ((eLabel1.equals(label1) || eLabel1.equals(label2))
					&& (eLabel2.equals(label1) || eLabel2.equals(label2))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the edges that are connected to the specified vertex.
	 * 
	 * @param vertex
	 *            the vertex
	 * @return the edges
	 */
	public List<Edge> getVertexEdges(Vertex vertex) {
		List<Edge> vertexEdges = new ArrayList<Edge>();
		for (Edge edge : edges) {
			if (edge.getVertex1().equals(vertex)
					|| edge.getVertex2().equals(vertex)) {
				vertexEdges.add(edge);
			}
		}
		return vertexEdges;
	}

	/**
	 * Gets the arcs that are connected to the specified vertex.
	 * 
	 * @param vertex
	 *            a vertex
	 * @return the arcs
	 */
	public List<Arc> getVertexArcs(Vertex vertex) {
		List<Arc> vertexArcs = new ArrayList<Arc>();
		for (Arc arc : arcs) {
			if (arc.getFromVertex().equals(vertex)
					|| arc.getToVertex().equals(vertex)) {
				vertexArcs.add(arc);
			}
		}
		return vertexArcs;
	}

	/**
	 * Gets the edges of this network as arcs. For each undirected edge, two
	 * directed arcs are returned.
	 * 
	 * @return the arcs
	 */
	public List<Arc> getEdgesAsArcs() {
		List<Arc> edgesAsArcs = new ArrayList<Arc>();
		for (Edge edge : edges) {
			Arc arc1 = new Arc(edge);
			Arc arc2 = new Arc(edge.getVertex2(), edge.getVertex1(), edge
					.getStrength(), edge.getType(), edge.getLabel());
			edgesAsArcs.add(arc1);
			edgesAsArcs.add(arc2);
		}
		return edgesAsArcs;
	}

	/**
	 * Gets both edges and arcs of this network as arcs. For each undirected
	 * edge, two directed arcs are returned.
	 * 
	 * @return the arcs (including the edges)
	 */
	public List<Arc> getAllAsArcs() {
		List<Arc> allArcs = new ArrayList<Arc>();
		allArcs.addAll(getEdgesAsArcs());
		allArcs.addAll(arcs);
		return allArcs;
	}

	/**
	 * Gets only those vertices with the specified shape.
	 * 
	 * @param shape
	 *            a shape
	 * @return the vertices
	 */
	public List<Vertex> getVertices(fusion.comerger.general.visualization.net.Vertex.Shape shape) {
		List<Vertex> myVertices = new ArrayList<Vertex>();
		for (Vertex vertex : vertices) {
			if (vertex.getShape().equals(shape)) {
				myVertices.add(vertex);
			}
		}
		return myVertices;
	}

	/**
	 * Tests if a vertex with the specified label is contained in the specified
	 * vertex list.
	 * 
	 * @param label
	 *            a vertex label
	 * @return <code>true</code> if the vertex is contained,
	 *         <code>false</code> otherwise
	 */
	public static boolean containsVertex(List<Vertex> vertices, String label) {
		for (Vertex vertex : vertices) {
			if (vertex.getLabel().equals(label)) {
				return true;
			}
		}
		return false;
	}

}
