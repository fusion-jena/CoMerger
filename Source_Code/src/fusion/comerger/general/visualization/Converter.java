/*Converts an ontology (written in RDF(S) or OWL) to a Pajek network.*/

package fusion.comerger.general.visualization;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang.StringUtils;
import org.openrdf.model.BNode;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.sesame.Sesame;
import org.openrdf.sesame.admin.DummyAdminListener;
import org.openrdf.sesame.admin.StdOutAdminListener;
import org.openrdf.sesame.config.AccessDeniedException;
import org.openrdf.sesame.config.ConfigurationException;
import org.openrdf.sesame.config.RepositoryConfig;
import org.openrdf.sesame.config.SailConfig;
import org.openrdf.sesame.constants.RDFFormat;
import org.openrdf.sesame.repository.local.LocalRepository;
import org.openrdf.sesame.repository.local.LocalService;
import org.openrdf.sesame.sail.RdfSource;
import org.openrdf.sesame.sail.StatementIterator;

import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.visualization.net.Arc;
import fusion.comerger.general.visualization.net.Edge;
import fusion.comerger.general.visualization.net.Network;
import fusion.comerger.general.visualization.net.Vertex;
import fusion.comerger.general.visualization.net.Vertex.Shape;


public class Converter {

	private Set<String> ignoreResources = new HashSet<String>();

	private LocalRepository repository;

	private RdfSource source;

	private Network network = new Network();

	private float strengthSubclassLinks = 1;

	private float strengthPropertyLinks = 1;

	private float strengthDefinitionLinks = 1;

	private float strengthSubstringLinks = 1;

	private float strengthDistanceLinks = 1;

	private int maxDistance = 3;

	private boolean includeSubclassLinks = true;

	private boolean includePropertyLinks = true;

	private boolean includeDefinitionLinks = true;

	private boolean includeOnlyDefinitionProperties = false;

	private boolean includeSubstringLinks = true;

	private boolean includeDistanceLinks = true;

	private fusion.comerger.general.visualization.net.Link.Type linkType = fusion.comerger.general.visualization.net.Link.Type.EDGE;

	private Map<Resource, Vertex> resourceToVertex = new HashMap<Resource, Vertex>();

	/**
	 * Creates a new object for converting an ontology to a Pajek network.
	 */
	public Converter() {
		// Set up a Sesame repository
		LocalService service = Sesame.getService();
		RepositoryConfig config = new RepositoryConfig("myrepository");
		SailConfig memSail = new SailConfig("org.openrdf.sesame.sailimpl.memory.RdfRepository");
		config.addSail(memSail);
		config.setWorldReadable(true);
		config.setWorldWriteable(true);
		try {
			repository = service.createRepository(config);
			source = (RdfSource) repository.getSail();
		} catch (ConfigurationException e) {
			// Should never occur
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	/**
	 * Reads an ontology from a file.
	 * 
	 * @param file
	 *            the file
	 * @throws FileNotFoundException
	 *             if the named file does not exist, is a directory rather than
	 *             a regular file, or for some other reason cannot be opened for
	 *             reading
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public void readOntology(File file) throws FileNotFoundException,
			IOException {
		try {
			repository.addData(file, file.toURL().toString(), RDFFormat.RDFXML,true, new StdOutAdminListener());
		} catch (AccessDeniedException e) {
			// Should never occur
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	private boolean ignoreResource(Resource resource) {
		if (resource instanceof BNode) {
			return true;
		} else {
			return ignoreResource(resource.toString());
		}
	}

	private boolean ignoreResource(String uri) {
		for (String ignoreString : ignoreResources) {
			if (uri.startsWith(ignoreString)) {
				return true;
			}
		}
		return false;
	}

	private String getLabel(Resource resource) {
		// Return the label that is found first, or the local name if no label
		// is specified.
		String label;
		StatementIterator i = source.getStatements(resource,
				URIImpl.RDFS_LABEL, null);
		if (i.hasNext()) {
			Statement statement = (Statement) i.next();
			label = statement.getObject().toString();
		} else {
			if (resource instanceof BNode) {
				label = resource.toString();
			} else {
				URI uri = (URI) resource;
				label = uri.getLocalName();
			}
		}
		i.close();
		return label;
	}

	private boolean containsVertex(Resource resource) {
		if (resourceToVertex.containsKey(resource)) {
			return true;
		} else {
			return false;
		}
	}

	private Vertex getOrAddVertex(Resource resource,
			fusion.comerger.general.visualization.net.Vertex.Shape shape) {
		Vertex vertex;
		if (containsVertex(resource)) {
			vertex = resourceToVertex.get(resource);
		} else {
			String label = getLabel(resource);
			vertex = network.addVertex(label, shape);
			resourceToVertex.put(resource, vertex);
		}
		int aa= network.getVertices().size();
		int wait=0;
		return vertex;
	}

	private Vertex getOrAddVertex(Resource resource) {
		return getOrAddVertex(resource,
				fusion.comerger.general.visualization.net.Vertex.Shape.UNSPECIFIED);
	}

	private void addRdfsClasses() {

		StatementIterator i = source.getStatements(null, URIImpl.RDF_TYPE,URIImpl.RDFS_CLASS);
		int lev =0;
		while (i.hasNext()) {
			lev++;
			Statement statement = (Statement) i.next();
			if(Coordinator.levelDetail==1 || lev % Coordinator.levelDetail ==0){
				Resource subj = statement.getSubject();
				if (!ignoreResource(subj)) {
					getOrAddVertex(subj, fusion.comerger.general.visualization.net.Vertex.Shape.ELLIPSE);
				}
			}
		}
		i.close();
	}

	private void addOwlClasses() {
		StatementIterator i = source.getStatements(null, URIImpl.RDF_TYPE,new URIImpl("http://www.w3.org/2002/07/owl#Class"));
		int lev=0;
		while (i.hasNext()) {
			lev++;
			Statement statement = (Statement) i.next();
			int te= lev % Coordinator.levelDetail;
			if (Coordinator.levelDetail==1 || lev % Coordinator.levelDetail==0){
				Resource subj = statement.getSubject();
				if (!ignoreResource(subj)) {
					getOrAddVertex(subj, fusion.comerger.general.visualization.net.Vertex.Shape.ELLIPSE);
				}
			}
		}
		int aa= network.getVertices().size();
		i.close();
	}

	private void addLink(Resource domain, Resource range, float strength,
			String label) {
		Vertex domainVertex = getOrAddVertex(domain);
		Vertex rangeVertex = getOrAddVertex(range);
		addLink(domainVertex, rangeVertex, strength, label);
	}

	private void addLink(Vertex domainVertex, Vertex rangeVertex,
			float strength, String label) {
		if (linkType == fusion.comerger.general.visualization.net.Link.Type.EDGE) {
			Edge edge = new Edge(domainVertex, rangeVertex, strength, "l",
					label);
			network.addEdge(edge);
		} else {
			Arc arc = new Arc(domainVertex, rangeVertex, strength, "l", label);
			network.addArc(arc);
		}
	}

	private void addSubclassLinks() {

		StatementIterator i = source.getStatements(null,
				URIImpl.RDFS_SUBCLASSOF, null);
		int lev = 0;
		while (i.hasNext()) {
			lev++;
			Statement statement = (Statement) i.next();
			if (Coordinator.levelDetail==1 || lev % Coordinator.levelDetail==0){
				if (statement.getObject() instanceof Resource) {
					Resource subj = statement.getSubject();
					Resource obj = (Resource) statement.getObject();
					if (!ignoreResource(subj) && !ignoreResource(obj)
							&& (subj != obj)) {
						addLink(subj, obj, strengthSubclassLinks, "isa");
					}
				}
			}
		}
		i.close();
	}

	private void addPropertyLinks() {

		StatementIterator i = source.getStatements(null, URIImpl.RDFS_DOMAIN,
				null);
		int lev = 0;
		while (i.hasNext()) {
			lev++;
			Statement statement = (Statement) i.next();
			if (Coordinator.levelDetail==1 || lev % Coordinator.levelDetail==0){
			if (statement.getObject() instanceof Resource) {
				Resource property = statement.getSubject();
				Resource domain = (Resource) statement.getObject();
				if (!ignoreResource(property) && !ignoreResource(domain)) {
					StatementIterator j = source.getStatements(property,
							URIImpl.RDFS_RANGE, null);
					while (j.hasNext()) {
						statement = (Statement) j.next();
						if (statement.getObject() instanceof Resource) {
							Resource range = (Resource) statement.getObject();
							if (!ignoreResource(range)) {
								addLink(domain, range, strengthPropertyLinks,
										getLabel(property));
							}
						}
					}
					j.close();
				}
			}
		}
		}
		i.close();
	}

	private void addDefinitionLinks() {

		// Iterate over all statements
		StatementIterator i = source.getStatements(null, null, null);
		while (i.hasNext()) {
			Statement statement = (Statement) i.next();
			if (statement.getObject() instanceof BNode) {
				Resource subj = statement.getSubject();
				BNode node = (BNode) statement.getObject();
				if (!ignoreResource(subj)) {
					Set<Resource> resources = getBlankNodeResources(
							new HashSet<BNode>(), node);
					for (Resource obj : resources) {
						if (!containsLink(subj, obj, "definition")) {
							addLink(subj, obj, strengthDefinitionLinks,
									"definition");
						}
					}
				}
			}
		}
		i.close();
	}

	private boolean containsLink(Resource subj, Resource obj, String label) {
		Vertex subjVertex;
		Vertex objVertex;
		if (!containsVertex(subj) || !containsVertex(obj)) {
			return false;
		} else {
			subjVertex = resourceToVertex.get(subj);
			objVertex = resourceToVertex.get(obj);
		}
		if (linkType == fusion.comerger.general.visualization.net.Link.Type.EDGE) {
			for (Edge edge : network.getEdges()) {
				if ((edge.getVertex1().equals(subjVertex) && edge.getVertex2()
						.equals(objVertex))
						|| (edge.getVertex2().equals(subjVertex) && edge
								.getVertex2().equals(objVertex))) {
					if (edge.getLabel().equals(label)) {
						return true;
					}
				}
			}
		} else {
			for (Arc arc : network.getArcs()) {
				if (arc.getFromVertex().equals(subjVertex)
						&& arc.getToVertex().equals(objVertex)) {
					if (arc.getLabel().equals(label)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Return the resources that can be reached from the specified blank node
	 * via child blank nodes.
	 * 
	 * @param path
	 *            the path to the current node (to prevent loops)
	 * @param node
	 *            the current node
	 * @return the list of resources
	 */
	private Set<Resource> getBlankNodeResources(Set<BNode> path, BNode node) {
		Set<Resource> resources = new HashSet<Resource>();
		path.add(node);

		// Iterate over the statements that have the current node as their
		// subject
		StatementIterator i = source.getStatements(node, null, null);
		while (i.hasNext()) {
			Statement statement = (Statement) i.next();
			if ((statement.getObject() instanceof Resource)
					&& !(statement.getObject() instanceof BNode)) {
				URI pred = statement.getPredicate();
				Resource obj = (Resource) statement.getObject();
				if (!isIncludeOnlyDefinitionProperties()
						|| (isIncludeOnlyDefinitionProperties() && pred
								.getURI()
								.equals(
										"http://www.w3.org/2002/07/owl#onProperty"))) {
					if (!ignoreResource(obj)) {
						resources.add(obj);
					}
				}
			} else if (statement.getObject() instanceof BNode) {
				BNode obj = (BNode) statement.getObject();
				if (!path.contains(obj)) {
					resources.addAll(getBlankNodeResources(path, obj));
				}
			}
		}
		i.close();
		return resources;
	}

	/**
	 * Adds links between concepts whose labels are substrings of eachother.
	 */
	private void addSubstringLinks() {

		List<Vertex> vertices = network.getVertices();
		for (int i = 0; i < vertices.size() - 1; i++) {
			Vertex vertex1 = vertices.get(i);
			String label1 = vertex1.getLabel().toLowerCase();
			if (!ignoreResource(label1)) {
				for (int j = i + 1; j < vertices.size(); j++) {
					Vertex vertex2 = vertices.get(j);
					String label2 = vertex2.getLabel().toLowerCase();
					if (!ignoreResource(label2)
							&& (label1.contains(label2) || label2
									.contains(label1))) {
						addLink(vertex1, vertex2, strengthSubstringLinks,
								"substring");
					}
				}
			}
		}
	}

	/**
	 * Adds links between concepts that have a string distance below the maximum
	 * distance.
	 */
	private void addDistanceLinks() {


		List<Vertex> vertices = network.getVertices();
		for (int i = 0; i < vertices.size() - 1; i++) {
			Vertex vertex1 = vertices.get(i);
			String label1 = vertex1.getLabel().toLowerCase();
			if (!ignoreResource(label1)) {
				for (int j = i + 1; j < vertices.size(); j++) {
					Vertex vertex2 = vertices.get(j);
					String label2 = vertex2.getLabel().toLowerCase();
					if (!ignoreResource(label2)
							&& (StringUtils.getLevenshteinDistance(label1,
									label2) <= maxDistance)) {
						addLink(vertex1, vertex2, strengthDistanceLinks,
								"distance");
					}
				}
			}
		}
	}

	/**
	 * Converts the ontology to a network.
	 */
	public void convert() {
		addRdfsClasses();
		int aa= network.getVertices().size(); //0
		addOwlClasses();
		aa= network.getVertices().size(); //6-7
		if (includeSubclassLinks) {
			addSubclassLinks();
		}
		aa= network.getVertices().size();//10
		if (includePropertyLinks) {
			addPropertyLinks();
		}
		aa= network.getVertices().size(); //25(14)
		if (includeDefinitionLinks) {
			addDefinitionLinks(); //To DO: should do for this and other function like the above three func
		}
		aa= network.getVertices().size();
		if (includeSubstringLinks) {
			addSubstringLinks();
		}
		aa= network.getVertices().size();
		if (includeDistanceLinks) {
			addDistanceLinks();
		}
		aa= network.getVertices().size();
	}

	/**
	 * Gets the strength of subclass links.
	 * 
	 * @return the strength
	 */
	public float getStrengthSubclassLinks() {
		return strengthSubclassLinks;
	}

	/**
	 * Sets the strength of subclass links. Default is 1.0.
	 * 
	 * @param strength
	 *            the strength
	 */
	public void setStrengthSubclassLinks(float strength) {
		strengthSubclassLinks = strength;
	}

	/**
	 * Gets the strength of property links.
	 * 
	 * @return the strength
	 */
	public float getStrengthPropertyLinks() {
		return strengthPropertyLinks;
	}

	/**
	 * Sets the strength of property links. Default is 1.0.
	 * 
	 * @param strength
	 *            the strength
	 */
	public void setStrengthPropertyLinks(float strength) {
		strengthPropertyLinks = strength;
	}

	/**
	 * Reads the file containing resources to be ignored.
	 * 
	 * @param file
	 *            the ignore file
	 * @throws FileNotFoundException
	 *             if the named file does not exist, is a directory rather than
	 *             a regular file, or for some other reason cannot be opened for
	 *             reading
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public void readIgnoreFile(File file) throws FileNotFoundException,
			IOException {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line = in.readLine();
		while (line != null) {
			if (!line.equals("")) {
				ignoreResources.add(line);
			}
			line = in.readLine();
		}
		in.close();
	}

	/**
	 * Gets the set of resources to be ignored. If a resource starts with one of
	 * the strings, it is ignored.
	 * 
	 * @return the list of strings
	 */
	public Set<String> getIgnoreResources() {
		return ignoreResources;
	}

	/**
	 * Sets the set of resources to be ignored. If a resource starts with one of
	 * the strings, it is ignored.
	 * 
	 * @param strings
	 *            the list of strings
	 */
	public void setIgnoreResources(Set<String> strings) {
		ignoreResources = strings;
	}

	/**
	 * Gets the link type that is to be created.
	 * 
	 * @return <code>EDGE</code> if edges are to be created; <code>ARC</code>
	 *         if arcs are to be created
	 */
	public fusion.comerger.general.visualization.net.Link.Type getLinkType() {
		return linkType;
	}

	/**
	 * Sets the link type that is to be created.
	 * 
	 * @param linkType
	 *            <code>EDGE</code> if edges are to be created;
	 *            <code>ARC</code> if arcs are to be created
	 */
	public void setLinkType(fusion.comerger.general.visualization.net.Link.Type linkType) {
		this.linkType = linkType;
	}

	/**
	 * Gets the strength of definition links.
	 * 
	 * @return the strength
	 */
	public float getStrengthDefinitionLinks() {
		return strengthDefinitionLinks;
	}

	/**
	 * Sets the strength of definition links. Default is 1.0.
	 * 
	 * @param strength
	 *            the strength
	 */
	public void setStrengthDefinitionLinks(float strength) {
		strengthDefinitionLinks = strength;
	}

	/**
	 * Tests if subclass links are to be included in the conversion.
	 * 
	 * @return <code>true</code> if subclass links are to be included;
	 *         <code>false</code> otherwise
	 */
	public boolean isIncludeSubclassLinks() {
		return includeSubclassLinks;
	}

	/**
	 * Sets if subclass links are to be included in the conversion. Default is
	 * <code>true</code>.
	 * 
	 * @param include
	 *            <code>true</code> if subclass links are to be included;
	 *            <code>false</code> otherwise
	 */
	public void setIncludeSubclassLinks(boolean include) {
		includeSubclassLinks = include;
	}

	/**
	 * Tests if property links are to be included in the conversion.
	 * 
	 * @return <code>true</code> if property links are to be included;
	 *         <code>false</code> otherwise
	 */
	public boolean isIncludePropertyLinks() {
		return includePropertyLinks;
	}

	/**
	 * Sets if property links are to be included in the conversion. Default is
	 * <code>true</code>.
	 * 
	 * @param include
	 *            <code>true</code> if property links are to be included;
	 *            <code>false</code> otherwise
	 */
	public void setIncludePropertyLinks(boolean include) {
		includePropertyLinks = include;
	}

	/**
	 * Tests if definition links are to be included in the conversion.
	 * 
	 * @return <code>true</code> if definition links are to be included;
	 *         <code>false</code> otherwise
	 */
	public boolean isIncludeDefinitionLinks() {
		return includeDefinitionLinks;
	}

	/**
	 * Sets if definition links are to be included in the conversion. Default is
	 * <code>true</code>.
	 * 
	 * @param include
	 *            <code>true</code> if definition links are to be included;
	 *            <code>false</code> otherwise
	 */
	public void setIncludeDefinitionLinks(boolean include) {
		includeDefinitionLinks = include;
	}

	/**
	 * Gets the network.
	 * 
	 * @return the network.
	 */
	public Network getNetwork() {
		return network;
	}

	/**
	 * Clears all internal lists to prepare for a new conversion.
	 */
	public void clear() {
		try {
			repository.clear(new DummyAdminListener());
		} catch (Exception e) {
			// Should never occur
			e.printStackTrace(System.err);
			System.exit(1);
		}
		network.clear();
		ignoreResources.clear();
		resourceToVertex.clear();
	}

	/**
	 * Tests if only properties are to be included when processing definition
	 * links.
	 * 
	 * @return <code>true</code> if only properties are to be included;
	 *         <code>false</code> if all resources are to be included
	 */
	public boolean isIncludeOnlyDefinitionProperties() {
		return includeOnlyDefinitionProperties;
	}

	/**
	 * Sets if only properties are to be included when processing definition
	 * links.
	 * 
	 * @param nodeIncludeOnlyProperties
	 *            <code>true</code> if only properties are to be included;
	 *            <code>false</code> if all resources are to be included
	 */
	public void setIncludeOnlyDefinitionProperties(
			boolean nodeIncludeOnlyProperties) {
		includeOnlyDefinitionProperties = nodeIncludeOnlyProperties;
	}

	/**
	 * Gets the string maximum distance.
	 * 
	 * @return the distance
	 */
	public int getMaxDistance() {
		return maxDistance;
	}

	/**
	 * Sets the string maximum distance. Default is <code>3</code>.
	 * 
	 * @param distance
	 *            the distance
	 */
	public void setMaxDistance(int distance) {
		maxDistance = distance;
	}

	/**
	 * Tests if distance links are to be included in the conversion.
	 * 
	 * @return <code>true</code> if distance links are to be included;
	 *         <code>false</code> otherwise
	 */
	public boolean isIncludeDistanceLinks() {
		return includeDistanceLinks;
	}

	/**
	 * Sets if distance links are to be included in the conversion. Default is
	 * <code>true</code>.
	 * 
	 * @param includeDistanceLinks
	 *            <code>true</code> if distance links are to be included;
	 *            <code>false</code> otherwise
	 */
	public void setIncludeDistanceLinks(boolean includeDistanceLinks) {
		this.includeDistanceLinks = includeDistanceLinks;
	}

	/**
	 * Gets the strength of distance links.
	 * 
	 * @return the strength
	 */
	public float getStrengthDistanceLinks() {
		return strengthDistanceLinks;
	}

	/**
	 * Sets the strength of distance links. Default is 1.0.
	 * 
	 * @param strength
	 *            the strength
	 */
	public void setStrengthDistanceLinks(float strength) {
		strengthDistanceLinks = strength;
	}

	/**
	 * Tests if substring links are to be included in the conversion.
	 * 
	 * @return <code>true</code> if substring links are to be included;
	 *         <code>false</code> otherwise
	 */
	public boolean isIncludeSubstringLinks() {
		return includeSubstringLinks;
	}

	/**
	 * Sets if substring links are to be included in the conversion. Default is
	 * <code>true</code>.
	 * 
	 * @param includeSubstringLinks
	 *            <code>true</code> if substring links are to be included;
	 *            <code>false</code> otherwise
	 */
	public void setIncludeSubstringLinks(boolean includeSubstringLinks) {
		this.includeSubstringLinks = includeSubstringLinks;
	}

	/**
	 * Gets the strength of distance links.
	 * 
	 * @return the strength
	 */
	public float getStrengthSubstringLinks() {
		return strengthSubstringLinks;
	}

	/**
	 * Sets the strength of distance links. Default is 1.0.
	 * 
	 * @param strength
	 *            the strength
	 */
	public void setStrengthSubstringLinks(float strength) {
		strengthSubstringLinks = strength;
	}

}
