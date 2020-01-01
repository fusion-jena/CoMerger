package fusion.comerger.algorithm.merger.query;
/*
 * CoMerger: Holistic Ontology Merging
 * %%
 * Copyright (C) 2019 Heinz Nixdorf Chair for Distributed Information Systems, Friedrich Schiller University Jena
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
* Author: Samira Babalou<br>
* email: samira[dot]babalou[at]uni[dash][dot]jena[dot]de
* Heinz-Nixdorf Chair for Distributed Information Systems<br>
* Institute for Computer Science, Friedrich Schiller University Jena, Germany<br>
* Date: 17/12/2019
*/
import java.io.IOException;

public class LocalRun_Query {

	public static void main(String[] args) {
		// Run a same Query on the merged ontology and all source ontologies
		QueryAll();

		// Run different queries on the merged ontology and all source
		// ontologies
		QueryIndividual();
	}

	private static void QueryIndividual() {
		/**
		 * @param mergedOnt
		 *            the merged ontology
		 * @param sourceOnts
		 *            a list of source ontologies
		 * @param QueryM
		 *            an SPARQL query of the merged ontology
		 * @param QueryO
		 *            an SPARQL query of the source ontologies
		 */

		String mergedOnt = "C:\\YOUR_LOCAL_FOLDER\\cmt.owl";
		String sourceOnts = "C:\\YOUR_LOCAL_FOLDER\\conference.owl";
		sourceOnts = sourceOnts + ";" + "C:\\YOUR_LOCAL_FOLDER\\cmt.owl";

		// write one query such as WQueryAll or AskQueryAll
		String QueryO = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" + "SELECT ?subject ?object \n"
				+ "WHERE { ?subject rdfs:subClassOf ?object }";

		String QueryM = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
				+ "ASK { <http://merged#Reviewer> rdfs:subClassOf <http://merged#Person> }";

		String resM = "";
		try {
			resM = QueryExcecute.RunQuery(mergedOnt, QueryM);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("---Result of query on the merged ontology: \n" + resM);

		String resO = "";
		try {
			resO = QueryExcecute.RunQuery(sourceOnts, QueryO);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("---Result of query on the source ontologies: \n" + resO);
	}

	private static void QueryAll() {
		/**
		 * @param mergedOnt
		 *            the merged ontology
		 * @param sourceOnts
		 *            a list of source ontologies
		 * @param AskQueryAll/WQueryAll
		 *            an SPARQL query running on both merged ontology and all
		 *            source ontologies
		 */

		String mergedOnt = "C:\\YOUR_LOCAL_FOLDER\\cmt.owl";
		String sourceOnts = "C:\\YOUR_LOCAL_FOLDER\\conference.owl";
		sourceOnts = sourceOnts + ";" + "C:\\YOUR_LOCAL_FOLDER\\cmt.owl";

		// write one query such as WQueryAll or AskQueryAll
		String WQueryAll = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" + "SELECT ?subject ?object \n"
				+ "WHERE { ?subject rdfs:subClassOf ?object }";

		String AskQueryAll = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
				+ "ASK { <http://merged#Reviewer> rdfs:subClassOf <http://merged#Person> }";

		String[] res = null;
		try {
			res = QueryExcecute.RunQueryAll(mergedOnt, sourceOnts, AskQueryAll);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("---Result of query on the merged ontology: \n" + res[0]);
		System.out.println("\n----Result of query on the source ontologies: \n" + res[1]);

	}

}
