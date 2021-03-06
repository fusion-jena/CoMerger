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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;

public class QueryExcecute {
	public static String RunQuery(String ontName, String queryString) throws IOException {
		long startTime = System.currentTimeMillis();
		String res = "";
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		if (ontName.endsWith(".zip")) {
			ontName = unzip(ontName);
		}
		FileManager.get().readModel(model, ontName);

		com.hp.hpl.jena.query.Query query = null;
		try {
			query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

			// ResultSetFormatter.out(System.out, results, query);
			// send result to variable
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			ResultSetFormatter.out(ps, results, query);
			res = new String(baos.toByteArray(), "UTF-8");
			//System.out.println(res);
			qe.close();
		} catch (Exception e) {
			res = "Query is not processable";
		}
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "Query done! Total time  " + elapsedTime + " ms. \n");
		return res;
	}

	public static void main(String[] args) throws IOException {
		// InputStream in1 = FileManager.get().open(
		// Settings.EN_DBPEDIA_DISAMBIGUATION_DATASET );
		// InputStream in2 = FileManager.get().open(
		// Settings.DE_DBPEDIA_DISAMBIGUATION_DATASET );
		// InputStream in3 = FileManager.get().open(
		// Settings.NL_DBPEDIA_DISAMBIGUATION_DATASET );
		// model.read(in1, null, "N-TRIPLES");
		// System.out.println("Loaded English disambiguation dataset.");
		// model.read(in2, null, "N-TRIPLES");
		// System.out.println("Loaded German disambiguation dataset.");
		// model.read(in3, null, "N-TRIPLES");

		String inputFileName = "C:\\YOUR_LOCAL_FOLDER\\MergedOnt.owl";
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		if (inputFileName.endsWith(".zip")) {
			inputFileName = unzip(inputFileName);
		}

		FileManager.get().readModel(model, inputFileName);

		String queryString = "prefix myont: <http://www.co-ode.org/ontologies/test/test.owl#> " + "prefix rdfs: <"
				+ RDFS.getURI() + "> " + "prefix owl: <" + OWL.getURI() + "> "
				+ "select DISTINCT ?myont where {?myont a owl:Class ; " + "rdfs:subClassOf ?restriction. " + "}";

		com.hp.hpl.jena.query.Query query = null;
		try {
			query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

			ResultSetFormatter.out(System.out, results, query);

			qe.close();
		} catch (Exception e) {
			System.out.println("Query is not processable");
		}

	}

	private static String unzip(String zipFilePath) throws IOException {
		Path p = Paths.get(zipFilePath);
		Path folder = p.getParent();
		String destDir = folder.toString();

		File dir = new File(destDir);
		// create output directory if it doesn't exist
		if (!dir.exists())
			dir.mkdirs();
		FileInputStream fis;
		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		File newFile = null;
		try {
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				newFile = new File(destDir + File.separator + fileName);
				// System.out.println("Unzipping to " +
				// newFile.getAbsolutePath());
				// create directories for sub directories in zip
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				// close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			// close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFile.getAbsolutePath();

	}

	public static String[] RunQueryAll(String mergedFile, String inputFile, String queryStringAll) throws IOException {
		long startTime = System.currentTimeMillis();
		String[] Answer = new String[2];
		// Run on merged ont
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);

		// mergedFile = MergeRule.zipFiles(mergedFile);
		// if (mergedFile.endsWith(".zip")) {
		// mergedFile = unzip(mergedFile);
		// }

		FileManager.get().readModel(model, mergedFile);// error

		com.hp.hpl.jena.query.Query query = null;
		try {
			query = QueryFactory.create(queryStringAll);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

			// ResultSetFormatter.out(System.out, results, query);
			// send result to variable
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			ResultSetFormatter.out(ps, results, query);
			String res = new String(baos.toByteArray(), "UTF-8");
			//System.out.println(res);
			qe.close();
			Answer[0] = "<h2>Result on merged ontology</h2> <textarea	id=\"resultQueryM\" name=\"resultQueryM\" style=\"width: 100%; font-size: 14px; height: 660px;\">"
					+ res + "</textarea>";

			String[] fileName = inputFile.split(";");
			for (int i = 0; i < fileName.length; i++) {
				if (fileName[0] != "") {
					model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
					if (fileName[0].endsWith(".zip")) {
						fileName[0] = unzip(fileName[0]);
					}
					FileManager.get().readModel(model, fileName[0]);

					query = QueryFactory.create(queryStringAll);
					qe = QueryExecutionFactory.create(query, model);
					results = qe.execSelect();

					// ResultSetFormatter.out(System.out, results, query);
					// send result to variable
					baos = new ByteArrayOutputStream();
					ps = new PrintStream(baos);
					ResultSetFormatter.out(ps, results, query);
					res = new String(baos.toByteArray(), "UTF-8");
					//System.out.println(res);
					qe.close();
					int indexer = i+1;
					res = "<h2>Result on ontology " + indexer
							+ "</h2> <textarea id=\"resultQueryO\" name=\"resultQueryO\" style=\"width: 100%; font-size: 14px; height: 300px;\">"
							+ res + "</textarea>";
					if (Answer[1] == null) {
						Answer[1] = res;
					} else {
						Answer[1] = Answer[1] + res;
					}

				}
			}

		} catch (Exception e) {
			Answer[0] = "Query is not processable";
			Answer[1] = "Query is not processable";
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO, "Quuery done! Total time  " + elapsedTime + " ms. \n");

		return Answer;
	}
}
