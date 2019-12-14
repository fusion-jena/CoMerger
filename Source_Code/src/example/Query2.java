package example;

import com.hp.hpl.jena.query.*;

public class Query2
{
    public static void main( String[] args ) {
        String s2 = "PREFIX  g:    <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX  onto: <http://dbpedia.org/ontology/>\n" +
                "\n" +
                "SELECT  ?subject ?stadium ?lat ?long\n" +
                "WHERE\n" +
                "  { ?subject g:lat ?lat .\n" +
                "    ?subject g:long ?long .\n" +
                "    ?subject <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> onto:Stadium .\n" +
                "    ?subject rdfs:label ?stadium\n" +
                "    FILTER ( ( ( ( ( ?lat >= 52.4814 ) && ( ?lat <= 57.4814 ) ) && ( ?long >= -1.89358 ) ) && ( ?long <= 3.10642 ) ) && ( lang(?stadium) = \"en\" ) )\n" +
                "  }\n" +
                "LIMIT   5\n" +
                "";

        Query query = QueryFactory.create(s2); //s2 = the query above
        QueryExecution qExe = QueryExecutionFactory.sparqlService( "http://dbpedia.org/sparql", query );
        ResultSet results = qExe.execSelect();
        ResultSetFormatter.out(System.out, results, query) ;
    }
}