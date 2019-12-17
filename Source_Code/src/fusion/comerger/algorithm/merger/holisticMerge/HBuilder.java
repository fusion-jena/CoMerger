package fusion.comerger.algorithm.merger.holisticMerge;
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
import java.util.Set;
import java.util.logging.Level;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SetOntologyID;

import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.model.HModel;

public class HBuilder {
	public static int[] ConnexionArray = null;
	public static float[] MinAngleRule = null;
	public static boolean analysisTest = false;
	public static int colorIndex;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static HModel run(HModel m) {
		long startTime = System.currentTimeMillis();
		ArrayList<Integer> classSize = new ArrayList<Integer>();
		ArrayList<Integer> objectSize = new ArrayList<Integer>();
		ArrayList<Integer> dataProSize = new ArrayList<Integer>();
		ArrayList<Integer> instanceSize = new ArrayList<Integer>();
		ArrayList<Integer> annoSize = new ArrayList<Integer>();
		Set<OWLAxiom> tempAnno = new HashSet<OWLAxiom>();
		ArrayList<OWLOntology> inputOWLmodels = new ArrayList<OWLOntology>();
		int classesSize = 0;
		int propertiesSize = 0;
		String prefOnt = m.getPreferedOnt();
		String msg = "";
		String st = "";

		OWLOntologyManager MergedManager = OWLManager.createOWLOntologyManager();
		File fileM = new File(m.getOntName());
		OWLOntology MergedOntology = null;

		try {
			MergedOntology = MergedManager.createOntology(IRI.create(fileM));
			// MergedOntology.setir(IRI.create("http://merged#"))
			// versionIRI can be null
			OWLOntologyID newOntologyID = new OWLOntologyID(IRI.create("http://merged#"), IRI.create("1.0"));
			// Create the change that will set our version IRI
			SetOntologyID setOntologyID = new SetOntologyID(MergedOntology, newOntologyID);
			// Apply the change
			MergedManager.applyChange(setOntologyID);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
			MyLogging.log(Level.SEVERE,
					"OWLOntologyCreationException in reading input ontologies" + e.toString() + "\n");
		}

		String[] nm = m.getlistOntName().split(";");
		MyLogging.log(Level.INFO, nm.length + " input ontologies has been read and parsed into merged model. \n");

		// test:
		String sucssefullFiles = "", failedFiles = "";
		for (int ontID = 0; ontID < nm.length; ontID++) {
			if (!nm[ontID].toString().equals("null")) {
				try {

					String OntName = nm[ontID];
//					System.out.println("Start reading file: " + ontID + "\t" + OntName);
					File file = new File(OntName);
					msg = msg + "\n Ontology: " + file.getName() + " has been read. \n";
					st = st + file.getName();

					OWLOntologyManager tempManager = OWLManager.createOWLOntologyManager();
					// tempManager.setProcessImports(true);
					OWLOntology tempOntology;

					tempOntology = tempManager.loadOntologyFromOntologyDocument(file);

					inputOWLmodels.add(tempOntology);

					if (prefOnt.contains(".")) {
						String[] sPrefOnt = prefOnt.split("\\.");
						prefOnt = sPrefOnt[0];
					}
					IRI ontIR = tempOntology.getOntologyID().getOntologyIRI();
					if (ontIR != null && ontIR.toString().toLowerCase().contains(prefOnt.toLowerCase())) 
						m.SetPreferedOnt(ontIR.toString() + "#");
					

					Set<OWLAxiom> tempAx = tempOntology.getAxioms();
					MergedManager.addAxioms(MergedOntology, tempAx);

					int cl = tempOntology.getClassesInSignature().size();
					classSize.add(cl);
					msg = msg + "\t Number of classes: " + cl;
					st = st + "-" + cl;

					int obj = tempOntology.getObjectPropertiesInSignature().size();
					objectSize.add(obj);
					msg = msg + "\t Number of objectProperties: " + obj;

					int dp = tempOntology.getDataPropertiesInSignature().size();
					dataProSize.add(dp);
					msg = msg + "\t Number of dataProperties: " + dp;
					st = st + "-" + (obj + dp);

					int ins = tempOntology.getIndividualsInSignature().size();
					instanceSize.add(ins);
					msg = msg + "\t Number of instances: " + ins;
					st = st + "-" + ins;

					classesSize = classesSize + tempOntology.getClassesInSignature().size();
					propertiesSize = propertiesSize + tempOntology.getObjectPropertiesInSignature().size()
							+ tempOntology.getDataPropertiesInSignature().size();

					Set<OWLAnnotationAssertionAxiom> t = tempOntology.getAxioms(AxiomType.ANNOTATION_ASSERTION);
					tempAnno.addAll(t);
					annoSize.add(t.size());
					msg = msg + "\t Number of annotations: " + t.size();

					String ta = printTAbox(OntName, tempOntology);
					msg = msg + "\t " + ta;
					st = st + "-" + tempOntology.getABoxAxioms(true).size() + "-"
							+ tempOntology.getTBoxAxioms(true).size();

					st = st + "-" + tempOntology.getAxiomCount();

					st = st + ";";

					file = null;
					sucssefullFiles = sucssefullFiles + ";" + OntName;
				} catch (OWLOntologyCreationException e) {
					failedFiles = failedFiles + ";" + nm[ontID];
					System.out.println("Error during reading file: \t" + nm[ontID]);
					e.printStackTrace();
					MyLogging.log(Level.SEVERE,
							"OWLOntologyCreationException in reading input ontologies" + e.toString() + "\n");

				}
			}
		}


		StatisticTest.result.put("input_ontology_size", st);
		m.SetInputOntNumber(nm.length);
		m.SetInputClassSize(classSize);
		m.SetInputObjectSize(objectSize);
		m.SetInputDataProSize(dataProSize);
		m.SetInputInstanceSize(instanceSize);
		m.SetInputAnnoSize(annoSize);
		m.setInputOwlOntModel(inputOWLmodels);
		m.SetAllAnnotations(tempAnno);
		m.SetInputClassSizeTotal(classesSize);
		m.SetInputPropertySizeTotal(propertiesSize);
		m.SetManager(MergedManager);
		m.SetOwlModel(MergedOntology);
		m.setCheckBuildModel(true);
		m.setOntIRI(IRI.create("http://merged#"));

		// TO DO: assign roots:
		/*
		 * OWLOntology ont = ontM.getOwlModel(); Set<OWLOntology> ontList = new
		 * HashSet<OWLOntology>(); ontList.add(ont); SimpleRootClassChecker root
		 * = new SimpleRootClassChecker(ontList); boolean a=
		 * root.isRootClass(c); if (a){ int wait=0; }
		 */

		MyLogging.log(Level.INFO, msg);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Total time for reading all input ontologies and save their entities into the merged model is: "
						+ elapsedTime + " ms. \n");

		StatisticTest.result.put("time_reading_input_ontologies", Long.toString(elapsedTime));

		return m;

	}

	public static String printTAbox(String OntName, OWLOntology tempOntology) {
		Set<OWLAxiom> aa = tempOntology.getABoxAxioms(false);
		Set<OWLAxiom> tt = tempOntology.getTBoxAxioms(false);
		String msg = "TBOX size: " + tt.size() + "\t ABOX siz: " + aa.size();
		return msg;
	}

	public static void logCompactnessInfo(HModel ontM) {
		OWLOntology OM = ontM.getOwlModel();
		String msg = "The merged ontology name: ";
		String name = ontM.getOntName();
		int d = name.indexOf("MergedOnt");
		name = name.substring(d);
		msg = msg + name;
		int cl = OM.getClassesInSignature().size();
		msg = msg + "\t Number of classes: " + cl;

		int obj = OM.getObjectPropertiesInSignature().size();
		msg = msg + "\t Number of objectProperties: " + obj;

		int dp = OM.getDataPropertiesInSignature().size();
		msg = msg + "\t Number of dataProperties: " + dp;

		Set<OWLAnnotationAssertionAxiom> t = OM.getAxioms(AxiomType.ANNOTATION_ASSERTION);
		msg = msg + "\t Number of annotations: " + t.size();

		int ins = OM.getIndividualsInSignature().size();
		msg = msg + "\t Number of instances: " + ins;

		String ta = printTAbox(ontM.getOntName(), OM);
		msg = msg + "\t " + ta;

		MyLogging.log(Level.INFO, msg);

		StatisticTest.result.put("Tbox_OM", String.valueOf(OM.getTBoxAxioms(false).size()));
		StatisticTest.result.put("Abox_OM", String.valueOf(OM.getABoxAxioms(false).size()));
		StatisticTest.result.put("class_OM", String.valueOf(cl));
		StatisticTest.result.put("property_OM", String.valueOf((obj + dp)));
		StatisticTest.result.put("instance_OM", String.valueOf(ins));

	}
}
