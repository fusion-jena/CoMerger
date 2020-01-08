package fusion.comerger.algorithm.merger.holisticMerge.localTest;
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
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedDpro;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedObj;

public class CountMapping {

	public static void main(String[] args) {
		run();

	}

	private static void run() {
//		 String address =
//		 "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d10\\subMergedOntologies\\nonPerfect\\";
		String address = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d11\\subMergedOntologies\\perfect\\";
		File folder = new File(address);
		String subOntList = StatisticTest.listFilesForFolder(folder);
//		 String mappFile =
//		 "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d10\\align_nonPerfect\\all.rdf";
		String mappFile = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d11\\align_Perfect\\all.rdf";

		ArrayList<String> strClass = new ArrayList<String>();
		ArrayList<ArrayList<String>> allStrClass = new ArrayList<ArrayList<String>>();
		String[] ont = subOntList.split(";");
		ArrayList<OWLOntology> allSubOnt = new ArrayList<OWLOntology>();
		for (String subOnt : ont) {
			// read subOnt
			OWLOntologyManager tempManager = OWLManager.createOWLOntologyManager();
			// tempManager.setProcessImports(true);
			OWLOntology tempOntology = null;
			File file = new File(subOnt);
			try {
				tempOntology = tempManager.loadOntologyFromOntologyDocument(file);
				strClass = new ArrayList<String>();
				Set<OWLClass> allClass = tempOntology.getClassesInSignature();
				for (OWLClass cl : allClass) {
					String st = cl.toString();
					int id = st.indexOf("#");
					if (id != -1) {

						st = st.substring(id + 1, st.length() - 1);
						String[] str = st.split("_");
						for (String s : str)
							strClass.add(s);
					} else {
						int w = 0;
					}
				}
				allStrClass.add(strClass);
			} catch (OWLOntologyCreationException e) {
				e.printStackTrace();
			}

			allSubOnt.add(tempOntology);
		}
		int brokenMapping = 0;
		// read mapping
		try {
			File file = new File(mappFile);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			Element align = root.element("Alignment");
			Iterator<?> map = align.elementIterator("map");
			if (!map.hasNext()) {
				// return null;
			}

			while (map.hasNext()) {
				Element cell = ((Element) map.next()).element("Cell");
				if (cell == null) {
					continue;
				}
				String map1 = cell.element("entity1").attributeValue("resource");
				String map2 = cell.element("entity2").attributeValue("resource");

				String s1 = map1;
				String s2 = map2;

				// TO DO: delete this condition

				// for (int i = 0; i < allStrClass.size(); i++) {
				int id1 = s1.indexOf("#");
				if (id1 != -1)
					s1 = s1.substring(id1 + 1, s1.length());

				int id2 = s2.indexOf("#");
				if (id2 != -1)
					s2 = s2.substring(id2 + 1, s2.length());

				int idOntS1 = -1, idOntS2 = -1;
				for (int j = 0; j < allStrClass.size(); j++) {
					if (allStrClass.get(j).contains(s1)) {
						idOntS1 = j;
					}

					if (allStrClass.get(j).contains(s2)) {
						idOntS2 = j;
					}
				}
				if (idOntS1 != -1 && idOntS2 != -1 && idOntS1 != idOntS2) {
					brokenMapping++;
					System.out.println(map1 + "\t" + map2);
				}

				// if ((allStrClass.get(i).contains(s1) &&
				// !allStrClass.get(i).contains(s2))
				// || (allStrClass.get(i).contains(s2) &&
				// !allStrClass.get(i).contains(s1))) {
				//
				// brokenMapping++;
				// }

				// }
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			MyLogging.log(Level.SEVERE, "Exception from reading corresponding entities: " + e.toString());

		}
		System.out.println("breaking mapping: " + brokenMapping);
	}

}
