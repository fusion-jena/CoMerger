package fusion.comerger.algorithm.merger.holisticMerge.consistency;
/**
 * CoMerger: Holistic Multiple Ontology Merger.
 * Consistency checker sub package based on the Subjective Logic theory
 * @author Samira Babalou (samira[dot]babalou[at]uni_jean[dot]de)
 */

import java.io.FileReader;
import java.io.IOException;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

import au.com.bytecode.opencsv.CSVReader;
import fusion.comerger.algorithm.merger.holisticMerge.general.Zipper;

public class InconsistencyCQTest {
	static int Qsize = 20;
	static String[][] QueryRef = new String[Qsize][2];

	public static void main(String[] args) throws IOException {
		String ontName = "C:\\Users\\Samira\\Desktop\\mergeDataset\\allConf\\consistentOnt\\old\\d4-new-seecont-root-om2.owl";
		// String ontName =
		// "C:\\Users\\Samira\\Desktop\\mergeDataset\\allConf\\ekaw.owl";
		String Q = "C:\\Users\\Samira\\Desktop\\mergeDataset\\allConf\\query\\d4-new-om-query.csv";
		InconsistencyCQTest ct = new InconsistencyCQTest();
		ct.Run(Q, ontName);
		// test(ontName);

	}

	private static void test(String ontName) throws IOException {
		String res = "";
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		if (ontName.endsWith(".zip")) {
			ontName = Zipper.unzip(ontName);
		}
		FileManager.get().readModel(model, ontName);

		String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
		com.hp.hpl.jena.query.Query query = null;

		System.out.println("Answer Query ");
		try {
			String q = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl: <http://www.w3.org/2002/07/owl#> "
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
					+ "SELECT ?which_document_exist  WHERE {?which_document_exist rdfs:subClassOf <http://merged#document>}";
			query = QueryFactory.create(q);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

			ResultSetFormatter.out(System.out, results, query);
			qe.close();
		} catch (Exception e) {
			res = "Query is not processable";

		}

	}

	public String Run(String Q, String ontName) throws IOException {
		String res = "";
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		if (ontName.endsWith(".zip")) {
			ontName = Zipper.unzip(ontName);
		}
		FileManager.get().readModel(model, ontName);

		QueryRef = CSVtoArray(Q);
		String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
		com.hp.hpl.jena.query.Query query = null;
		for (int i = 0; i < Qsize; i++) {
			int counter = i + 1;
			System.out.println("Answer Query " + counter);
			try {
				if (QueryRef[i][0].startsWith("ASK")) {
					for (int j = 0; j < 2; j++) {
						if (QueryRef[i][j].length() > 0) {
							String q = prefix + QueryRef[i][j];
							query = QueryFactory.create(q);
							QueryExecution qe = QueryExecutionFactory.create(query, model);
							boolean resultsAsk = qe.execAsk();

							System.out.println(resultsAsk);
							qe.close();
						}
					}
				} else if (QueryRef[i][0].startsWith("unknown")) {
					System.out.println("unknown");
				} else {
					for (int j = 0; j < 2; j++) {
						if (QueryRef[i][j].length() > 0) {
							String q = prefix + QueryRef[i][j];
							query = QueryFactory.create(q);
							QueryExecution qe = QueryExecutionFactory.create(query, model);
							com.hp.hpl.jena.query.ResultSet results = qe.execSelect();
							ResultSetFormatter.out(System.out, results, query);
							qe.close();
						}
					}
				}
			} catch (Exception e) {
				res = "Query is not processable";
			}
		}
		return res;

	}

	public static String[][] CSVtoArray(String csvFile) {

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			int i = 0;
			while ((line = reader.readNext()) != null) {
				QueryRef[i][0] = line[0];
				if (line[1] != null)
					QueryRef[i][1] = line[1];
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return QueryRef;
	}

}
