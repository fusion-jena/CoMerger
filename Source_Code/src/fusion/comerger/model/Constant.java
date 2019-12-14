package fusion.comerger.model;

public class Constant {
	public static final String OWL_NS = "http://www.w3.org/2002/07/owl#";
    public static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String RDFS_NS = "http://www.w3.org/2000/01/rdf-schema#";
    public static final String XSD_NS = "http://www.w3.org/2001/XMLSchema#";
    public static final String DC_NS = "http://purl.org/dc/elements/1.1/";
    public static final String FOAF_NS = "http://xmlns.com/foaf/0.1/";
    public static final String ICAL_NS = "http://www.w3.org/2002/12/cal/ical#";
    
    public static final int ONTOLOGY_CLASS = 1;
    public static final int ONTOLOGY_PROPERTY = 8;
    public static final int ONTOLOGY_INSTANCE = 16;
    public static final int EXTERNAL_CLASS = 32;
    public static final int EXTERNAL_PROPERTY = 64;
    public static final int EXTERNAL_INSTANCE = 128;
    public static final int STMT = 256;
    
    public static String BUILTIN_NS[] = {XSD_NS, RDF_NS, RDFS_NS, OWL_NS, 
    									 DC_NS, FOAF_NS, ICAL_NS};

    public static boolean isBuiltInNs(String ns)
    {
        for (int i = 0, n = BUILTIN_NS.length; i < n; i++) {
            if (ns.equals(BUILTIN_NS[i])) {
                return true;
            }
        }
        return false;
    }

}
