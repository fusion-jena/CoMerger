package fusion.comerger.servlets;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;

import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import fusion.comerger.algorithm.matcher.Matching;
import fusion.comerger.algorithm.matcher.jacard.JacardSim;
import fusion.comerger.algorithm.matcher.jacard.Mapp;
import fusion.comerger.algorithm.matcher.jacard.Parameters;
import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.general.cc.Configuration;
import fusion.comerger.general.output.Alignment;
import fusion.comerger.general.output.AlignmentOwl;
import fusion.comerger.general.output.AlignmentReader2;
import fusion.comerger.general.output.AlignmentWriter2;
import fusion.comerger.general.output.AlignmentWriterOwl;
import fusion.comerger.general.output.Evaluator;
import fusion.comerger.general.output.MappingOwl;
import fusion.comerger.general.output.ResultData;

public class MatchingProcess {
	private static Alignment alignment = null;

	public static String DoMatch(String path, String ont1, String ont2, String NumCHMatchFile1,
			String NumCHMatchFile2) {
		// final TablePanel alignTable = new TablePanel();
		// alignTable.setPreferredSize(new Dimension(600, 200));
		String alignPath = "";
		final String s1 = ont1.trim();
		final String s2 = ont2.trim();
		if (s1.length() > 0 && s2.length() > 0) {
			// alignTable.setAlignment(null);
			// alignTable.repaintTable(); // for being empty the result

			Configuration config = new Configuration();
			config.init();
			String fp1 = s1, fp2 = s2;
			if (!fp1.startsWith("file:")) {
				fp1 = "file:" + fp1;
			}
			if (!fp2.startsWith("file:")) {
				fp2 = "file:" + fp2;
			}
			// Controller controller = new Controller(fp1, fp2);
			Matching controller = new Matching(fp1, fp2, NumCHMatchFile1, NumCHMatchFile2);
			// replace new matching here
			alignment = controller.run(path); // result are in alignmnet
			// String fp = "./temp/tempResult.rdf";
			alignPath = path + "align.rdf";
			AlignmentWriter2 writer = new AlignmentWriter2(alignment, alignPath);
			writer.write("onto1", "onto2", "onto1", "onto2"); // write to file,
																// save
			// alignTable.setAlignment(alignment);
			// alignTable.repaintTable();
			// lastResult = alignPath;
		}

		// return alignment;
		return alignPath;
	}

	public static String[] DoEvalMatch(String refFile, String alignfile) {
		String[] resEvalMatch = new String[6];
		if (alignfile.length() > 0 && refFile.length() > 0) {
			Evaluator evaluater = new Evaluator();
			AlignmentReader2 reader1 = new AlignmentReader2(Matching.model1, Matching.model2, alignfile);
			AlignmentReader2 reader2 = new AlignmentReader2(Matching.model1, Matching.model2, refFile);
			// System.out.println(s1 + "\n" + s2);
			ResultData result = evaluater.compare(reader1.read(), reader2.read());
			resEvalMatch[0] = new Integer(result.getFound()).toString();
			resEvalMatch[1] = new Integer(result.getExist()).toString();
			resEvalMatch[2] = new Integer(result.getCorrect()).toString();
			resEvalMatch[3] = new Float(Math.round(result.getPrecision() * 10000.0) / 10000.0).toString();
			resEvalMatch[4] = new Float(Math.round(result.getRecall() * 10000.0) / 10000.0).toString();
			resEvalMatch[5] = new Float(Math.round(result.getFMeasure() * 10000.0) / 10000.0).toString();
			// TO DO: this result also should show to user
			Alignment correctAlignment = result.getCorrectAlignment();
			Alignment errorAlignment = result.getErrorAlignment();
			Alignment lostAlignment = result.getLostAlignment();
		}
		return resEvalMatch;
	}

	public static String CreateMap(String OntList, String ch1, String ch2, String path, String MatchType)
			throws OWLOntologyCreationException {
		String MapFileAddress = "";
		if (MatchType.equals("SeeCOnt")) {
			MapFileAddress = CreateSeeCOntMap(OntList, ch1, ch2, path);
		} else {
			MapFileAddress = CreateJacardMap(OntList, path);
		}
		return MapFileAddress;
	}

	private static String CreateJacardMap(String OntList, String path) throws OWLOntologyCreationException {
		long startTime = System.currentTimeMillis();
		double sim = Parameters.simThreshold;
		// read all ontologies only one time
		String[] nm = OntList.split(";");
		// OWLOntology[] OwlOnt = new OWLOntology[nm.length]();
		ArrayList<OWLOntology> OwlOnt = new ArrayList<OWLOntology>();

		for (int i = 0; i < nm.length; i++) {
			String Ont = nm[i];
			if (!Ont.equals("null")) {
				File file = new File(Ont);

				OWLOntology tempOntology;

				OWLOntologyManager tempManager = OWLManager.createOWLOntologyManager();

				OWLOntologyLoaderConfiguration loadingConfig = new OWLOntologyLoaderConfiguration();
				loadingConfig = loadingConfig.setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
				tempOntology = tempManager.loadOntologyFromOntologyDocument(new FileDocumentSource(file),
						loadingConfig);
				OwlOnt.add(tempOntology);

			}
		}
		Mapp temp = null;
		ArrayList<Mapp> mappedClasses = new ArrayList<Mapp>();
		for (int i = 0; i < OwlOnt.size(); i++) {
			OWLOntology Ont1 = OwlOnt.get(i);
			if (!Ont1.equals("null")) {
				for (int j = i + 1; j < OwlOnt.size(); j++) {
					OWLOntology Ont2 = OwlOnt.get(j);
					if (!Ont2.equals("null")) {
						// compare classes
						Iterator<OWLClass> Class1 = Ont1.getClassesInSignature().iterator();
						while (Class1.hasNext()) {
							OWLClass c1 = Class1.next();
							temp = new Mapp();

							Iterator<OWLClass> Class2 = Ont2.getClassesInSignature().iterator();
							while (Class2.hasNext()) {
								OWLClass c2 = Class2.next();
								double jac = JacardSim.run(c1.getIRI().getFragment(), c2.getIRI().getFragment());
								if (jac > sim) {
									sim = jac;
									temp = new Mapp();
									temp.setEntity1(c1.getIRI().toString());
									temp.setEntity2(c2.getIRI().toString());
									temp.setSim(sim);
								}
							}
							// it add the highest similar entities
							if (temp.getEntity1() != null)
								mappedClasses.add(temp);
						}
						// compare object properties
						Iterator<OWLObjectProperty> pro1 = Ont1.getObjectPropertiesInSignature().iterator();
						while (pro1.hasNext()) {
							OWLObjectProperty p1 = pro1.next();
							temp = new Mapp();
							Iterator<OWLObjectProperty> pro2 = Ont2.getObjectPropertiesInSignature().iterator();
							while (pro2.hasNext()) {
								OWLObjectProperty p2 = pro2.next();
								double jac = JacardSim.run(p1.getIRI().getFragment(), p2.getIRI().getFragment());
								if (jac > sim) {
									sim = jac;
									temp = new Mapp();
									temp.setEntity1(p1.getIRI().toString());
									temp.setEntity2(p2.getIRI().toString());
									temp.setSim(sim);
								}
							}
							// it add the highest similar entities
							if (temp.getEntity1() != null)
								mappedClasses.add(temp);
						}

						// compare datatype properties
						Iterator<OWLDataProperty> dPro1 = Ont1.getDataPropertiesInSignature().iterator();
						while (pro1.hasNext()) {
							OWLDataProperty p1 = dPro1.next();
							temp = new Mapp();
							Iterator<OWLDataProperty> dPro2 = Ont2.getDataPropertiesInSignature().iterator();
							while (dPro2.hasNext()) {
								OWLDataProperty p2 = dPro2.next();
								double jac = JacardSim.run(p1.getIRI().getFragment(), p2.getIRI().getFragment());
								if (jac > sim) {
									sim = jac;
									temp = new Mapp();
									temp.setEntity1(p1.getIRI().toString());
									temp.setEntity2(p2.getIRI().toString());
									temp.setSim(sim);
								}
							}
							// it add the highest similar entities
							if (temp.getEntity1() != null)
								mappedClasses.add(temp);
						}
					}
				}
			}
		}
		// createAlignmentFile for them
		// each classes may map to several classes, so we keep the highest value
		Iterator<Mapp> mappedClassesRefined = getHighestMap(mappedClasses).iterator();
		AlignmentOwl align = new AlignmentOwl();
		while (mappedClassesRefined.hasNext()) {
			Mapp mp = mappedClassesRefined.next();
			if (mp != null) {
				String mp1 = mp.getEntity1();
				String mp2 = mp.getEntity2();
				double simi = mp.getSim();
				align.addMapping(new MappingOwl(mp1, mp2, simi));
			}
		}
		Random rand = new Random();
		int id = rand.nextInt(1000 - 0 + 1) + 0;
		String MapFileAddress = path + "align" + id + ".rdf";
		AlignmentWriterOwl writer = new AlignmentWriterOwl(align, MapFileAddress);
		writer.write("onto1", "onto2", "onto1", "onto2");

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"*** done! Congratulation. Generating the alignment between input ontologies has been done automatically by the system. Total time  "
						+ elapsedTime + " ms. \n");
		System.gc();
		return MapFileAddress;
	}

	private static HashSet<Mapp> getHighestMap(ArrayList<Mapp> mc) {
		HashSet<Mapp> res = new HashSet<Mapp>();
		ArrayList<Mapp> duplicated = new ArrayList<Mapp>();
		Mapp temp = new Mapp();
		for (int i = 0; i < mc.size(); i++) {
			temp = new Mapp();
			for (int j = i + 1; j < mc.size(); j++) {
				if (mc.get(i).getEntity1().equals(mc.get(j).getEntity1())
						|| mc.get(i).getEntity1().equals(mc.get(j).getEntity2())
						|| mc.get(i).getEntity2().equals(mc.get(j).getEntity1())
						|| mc.get(i).getEntity2().equals(mc.get(j).getEntity2())) {
					if (mc.get(i).getSim() >= mc.get(j).getSim()) {
						if (mc.get(i).getSim() >= temp.getSim()) {
							temp = mc.get(i);
							duplicated.add(mc.get(j));
						} else {
							duplicated.add(mc.get(i));
							duplicated.add(mc.get(j));
						}
					} else if (mc.get(j).getSim() > mc.get(i).getSim()) {
						if (mc.get(j).getSim() >= temp.getSim()) {
							temp = mc.get(j);
							duplicated.add(mc.get(i));
						} else {
							duplicated.add(mc.get(i));
							duplicated.add(mc.get(j));
						}
					}

				}
			}
			if (temp.getEntity1() == null) {
				if (!duplicated.contains(mc.get(i))) {
					res.add(mc.get(i));
				}
			} else if (!duplicated.contains(temp)) {
				res.add(temp);
			}
		}

		return res;
	}

	private static String CreateSeeCOntMap(String OntList, String ch1, String ch2, String path) {
		// Partition and match ontologies with CH=1
		long startTime = System.currentTimeMillis();

		String MapFileAddress = "";
		String[] nm = OntList.split(";");
		for (int i = 0; i < nm.length; i++) {
			String Ont1 = nm[i];
			if (!Ont1.equals("null")) {
				for (int j = i + 1; j < nm.length; j++) {
					String Ont2 = nm[j];
					if (!Ont2.equals("null")) {
						String tempAddress = null;

						Configuration config = new Configuration();
						config.init();
						if (!Ont1.startsWith("file:")) {
							Ont1 = "file:" + Ont1;
						}
						if (!Ont2.startsWith("file:")) {
							Ont2 = "file:" + Ont2;
						}
						Matching controller = new Matching(Ont1, Ont2, ch1, ch2);
						alignment = controller.run(path);
						tempAddress = path + "align" + i + j + ".rdf";
						AlignmentWriter2 writer = new AlignmentWriter2(alignment, tempAddress);
						writer.write("onto1", "onto2", "onto1", "onto2");
						if (MapFileAddress == null) {
							MapFileAddress = tempAddress;
						} else {
							MapFileAddress = MapFileAddress + ";" + tempAddress;
						}
					}
				}
			}
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"*** done! Congratulation. Generating the alignment between input ontologies has been done automatically by the system. Total time  "
						+ elapsedTime + " ms. \n");
		System.gc();
		return MapFileAddress;
	}

	public static void main(String[] args) throws Exception {

		TestAlignmentAccuracy();
		// TestMatching();

	}

	private static void TestMatching() {
		String ont1 = "C:\\LOCAL_FOLDER\\confOf.owl";
		String ont2 = "C:\\LOCAL_FOLDER\\edas.owl";
		String alignRef = "C:\\LOCAL_FOLDER\\confOf-edas.rdf";
		String path = "C:\\LOCAL_FOLDER\\";
		String fileResult = DoMatch(path, ont1, ont2, "1", "1");

		OntDocumentManager mgr = new OntDocumentManager();
		mgr.setProcessImports(false);
		OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
		spec.setDocumentManager(mgr);
		System.out.println(ont1 + "\t the file path\t" + ont2);
		Matching.model1 = ModelFactory.createOntologyModel(spec, null);
		Matching.model1.read(ont1);
		Matching.model2 = ModelFactory.createOntologyModel(spec, null);
		Matching.model2.read(ont2);

		String res[] = DoEvalMatch(alignRef, fileResult);
		System.out.println("Found: " + res[0]);
		System.out.println("Exist: " + res[1]);
		System.out.println("Correct: " + res[2]);
		System.out.println("Percision: " + res[3]);
		System.out.println("Recall: " + res[4]);
		System.out.println("FMeasure: " + res[5]);
		System.out.println("Done!");

	}

	private static void TestAlignmentAccuracy() {
		String align = "C:\\LOCAL_FOLDER\\all.rdf";
		String alignRef = "C:\\LOCAL_FOLDER\\AllBench.rdf";

		String ont1 = "C:\\LOCAL_FOLDER\\cmt.owl";
		String ont2 = "C:\\LOCAL_FOLDER\\conference.owl";

		OntDocumentManager mgr = new OntDocumentManager();
		mgr.setProcessImports(false);
		OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
		spec.setDocumentManager(mgr);
		System.out.println(ont1 + "\t the file path\t" + ont2);
		Matching.model1 = ModelFactory.createOntologyModel(spec, null);
		Matching.model1.read(ont1);
		Matching.model2 = ModelFactory.createOntologyModel(spec, null);
		Matching.model2.read(ont2);

		String res[] = DoEvalMatch(alignRef, align);
		System.out.println("Found: " + res[0]);
		System.out.println("Exist: " + res[1]);
		System.out.println("Correct: " + res[2]);
		System.out.println("Percision: " + res[3]);
		System.out.println("Recall: " + res[4]);
		System.out.println("FMeasure: " + res[5]);
		System.out.println("Done!");

	}

}
