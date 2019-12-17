
package fusion.comerger.model;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/
public class NodeCategory {
	
	 public static int getCategory(Node node)
	    {
	        if (node.getNodeType() == Node.STATEMENT) {
	            return Constant.STMT;
	        } else if (node.getNodeLevel() == Node.LANGUAGE_LEVEL || 
	        		node.getNodeLevel() == Node.EXTERNAL ||
	        		node.getCategory() == Node.LITERAL) {
	            if (node.getCategory() == Node.CLASS) {
	                return Constant.EXTERNAL_CLASS;
	            } else if (node.getCategory() == Node.INSTANCE || 
	            		node.getCategory() == Node.LITERAL) {
	                return Constant.EXTERNAL_INSTANCE;
	            } else {
	                return Constant.EXTERNAL_PROPERTY;
	            }
	        } else {
	            if (node.getCategory() == Node.CLASS) {
	                return Constant.ONTOLOGY_CLASS;
	            } else if (node.getCategory() == Node.INSTANCE || 
	            		node.getCategory() == Node.LIST) {
	                return Constant.ONTOLOGY_INSTANCE;
	            } else {
	                return Constant.ONTOLOGY_PROPERTY;
	            }
	        }
	    }

	    public static int getCategoryWithoutExternal(Node node)
	    {
	        int category = getCategory(node);
	        if (node.getNodeLevel() == Node.EXTERNAL) {
	            if (category == Constant.EXTERNAL_CLASS) {
	                return Constant.ONTOLOGY_CLASS;
	            } else if (category == Constant.EXTERNAL_INSTANCE) {
	                return Constant.ONTOLOGY_INSTANCE;
	            } else if (category == Constant.EXTERNAL_PROPERTY) {
	                return Constant.ONTOLOGY_PROPERTY;
	            }
	        }
	        return category;
	    }

	    public static int getLanguageCategory(String ns, String localName)
	    {
	        if (ns.equals(Constant.XSD_NS)) {
	            return Node.CLASS;
	        } else if (ns.equals(Constant.RDF_NS)) {
	            if (localName.equals("type") ||
	            		localName.equals("value") || 
	            		localName.equals("first") ||
	            		localName.equals("rest")) {
	                return Node.PROPERTY;
	            } else if (localName.equals("List") || 
	            		localName.equals("XMLLiteral") || 
	            		localName.equals("Property")) {
	                return Node.CLASS;
	            } else if (localName.equals("nil")) {
	                return Node.INSTANCE;
	            }
	        } else if (ns.equals(Constant.RDFS_NS)) {
	            if (localName.equals("domain") || 
	            		localName.equals("range") || 
	            		localName.equals("subClassOf") ||
	            		localName.equals("subPropertyOf")) {
	                return Node.PROPERTY;
	            } else if (localName.equals("Class") || 
	            		localName.equals("Resource")) {
	                return Node.CLASS;
	            }
	        } else if (ns.equals(Constant.OWL_NS)) { // langProfile = "OWL"
	            if (localName.equals("Class") || 
	            		localName.equals("Restriction") || 
	            		localName.equals("ObjectProperty") || 
	            		localName.equals("DatatypeProperty") || 
	            		localName.equals("AnnotationProperty") ||
	            		localName.equals("TransitiveProperty") ||
	            		localName.equals("SymmetricProperty") ||
	            		localName.equals("InverseFunctionalProperty") ||
	            		localName.equals("FunctionalProperty") ||
	            		localName.equals("Ontology") ||
	            		localName.equals("DeprecatedClass") ||
	            		localName.equals("DeprecatedProperty") ||
	            		localName.equals("AllDifferent")) {
	                return Node.CLASS;
	            } else if (localName.equals("onProperty") ||
	            		localName.equals("oneOf") ||
	            		localName.equals("allValuesFrom") ||
	            		localName.equals("someValuesFrom") ||
	            		localName.equals("hasValue") ||
	            		localName.equals("cardinality") ||
	            		localName.equals("maxCardinality") ||
	            		localName.equals("minCardinality") ||
	            		localName.equals("equivalentClass") ||
	            		localName.equals("equivalentProperty") ||
	            		localName.equals("sameAs") ||
	            		localName.equals("inverseOf") ||
	            		localName.equals("unionOf") ||
	            		localName.equals("intersectionOf") ||
	            		localName.equals("differentFrom") ||
	            		localName.equals("distinctMembers") ||
	            		localName.equals("disjointWith")) {
	                return Node.PROPERTY;
	            } else if (localName.equals("Thing") ||
	            		localName.equals("Nothing") ||
	            		localName.equals("priorVersion") ||
	            		localName.equals("backwardCompatibleWith") ||
	            		localName.equals("incompatibleWith") ||
	            		localName.equals("imports")) {
	                return Node.INSTANCE;
	            }
	        }
	        return Node.UNDEFINED;
	    }

}
