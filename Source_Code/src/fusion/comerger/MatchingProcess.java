package fusion.comerger;

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
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

import fusion.comerger.algorithm.matcher.Matching;
import fusion.comerger.algorithm.merger.holisticMerge.HolisticMerger;
import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.partitioner.SeeCOnt.EvaluationSeeCOnt;
import fusion.comerger.algorithm.partitioner.SeeCOnt.Findk.FindOptimalClusterIntractive;
import fusion.comerger.algorithm.partitioner.SeeCOnt.Findk.SelectCH;
import fusion.comerger.general.analysis.AnalyzingTest;
import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Cleaning_Allthings;
import fusion.comerger.general.cc.Configuration;
import fusion.comerger.general.cc.Controller;
import fusion.comerger.general.cc.Data;
import fusion.comerger.general.gui.AnalysisPanel;
import fusion.comerger.general.gui.MainFrame;
import fusion.comerger.general.gui.PartitioningPanel;
import fusion.comerger.general.gui.TablePanel;
import fusion.comerger.general.output.Alignment;
import fusion.comerger.general.output.AlignmentReader2;
import fusion.comerger.general.output.AlignmentWriter2;
import fusion.comerger.general.output.Evaluator;
import fusion.comerger.general.output.ResultData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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

	public static String CreateMap(String OntList, String ch1, String ch2, String path) {
		// Partition and match ontologies with CH=1
		long startTime = System.currentTimeMillis();

		String MapFileAddress = null;
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
		String ont1 = "C:\\Users\\Samira\\Desktop\\mergeDataset\\mappingTest\\confOf.owl";
		String ont2 = "C:\\Users\\Samira\\Desktop\\mergeDataset\\mappingTest\\edas.owl";
		String alignRef = "C:\\Users\\Samira\\Desktop\\mergeDataset\\mappingTest\\confOf-edas.rdf";
		String path = "C:\\Users\\Samira\\Desktop\\mergeDataset\\mappingTest\\";
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
		String align = "C:\\Users\\Samira\\Desktop\\mergeDataset\\mappingTest\\SeeCOnt\\inferredMapping\\all.rdf";
		String alignRef = "C:\\Users\\Samira\\Desktop\\mergeDataset\\mappingTest\\SeeCOnt\\inferredMapping\\AllBench.rdf";

		String ont1 = "C:\\Users\\Samira\\Desktop\\mergeDataset\\mappingTest\\cmt.owl";
		String ont2 = "C:\\Users\\Samira\\Desktop\\mergeDataset\\mappingTest\\conference.owl";

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
