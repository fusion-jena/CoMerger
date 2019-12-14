//TO DO: delete and go to PartitioningServlet
package fusion.comerger;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.io.IOUtils;

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
import fusion.comerger.general.gui.PartitioningPanel;

public class AnalyzeProcess {
	private static String zipFiles(String... filePaths) {
		String zipFileName = "";
		try {
			File firstFile = new File(filePaths[0]);
			// String zipFileName = firstFile.getName().concat(".zip");
			// String zipFileName = firstFile.getName().substring(0,
			// firstFile.getName().length() - 6).concat(".zip");
			zipFileName = firstFile.getPath().substring(0, firstFile.getPath().length() - 4).concat(".zip");

			FileOutputStream fos = new FileOutputStream(zipFileName);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (String aFile : filePaths) {
				zos.putNextEntry(new ZipEntry(new File(aFile).getName()));

				byte[] bytes = Files.readAllBytes(Paths.get(aFile));
				zos.write(bytes, 0, bytes.length);
				zos.closeEntry();
			}

			zos.close();

		} catch (FileNotFoundException ex) {
			System.err.println("A file does not exist: " + ex);
		} catch (IOException ex) {
			System.err.println("I/O error: " + ex);
		}

		return zipFileName;
	}

	public static String[] DoAnalysis(Data data) {
		String[] res;

		// TO DO: Cleaning_Allthings.clean(0);
		// TO DO: not empty filename

		// File ontologyFile = new File(data.getOntName());
		// if (!ontologyFile.exists()) {
		// JOptionPane.showMessageDialog(null, "The ontology file does not
		// exist.", "Error",
		// JOptionPane.ERROR_MESSAGE);
		// // return; //To do : should correct
		// } else

		// LockedGUI();
		// Build the model
		// if (Controller.CheckBuildModel == false) {
		BuildModel.BuildModelOnt(data);
		// Controller.CheckBuildModel = true;
		// }

		// TO DO: Add this selected ontology to the next tab

		AnalyzingTest AT = new AnalyzingTest();
		res = AT.AnalyzingTestRun(data.getOntName(), data);
		BuildModel.analysisTest = true;
		// Un_LockedGUI();
		return res;
	}

	public static String saveAnalyze(Data data, String[] res) throws FileNotFoundException {
		// if many request come?
		// String filename = "c:/doc/analyze.txt";
		String filzip = data.getOntName().substring(0, data.getOntName().length() - 4) + "_analyze.zip";
		String filename = data.getOntName().substring(0, data.getOntName().length() - 4) + "_analyze.txt";
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(filename, "UTF-8");
			writer.println("The analyze result: \n");
			writer.println("\n **** 1- General metrics: ****");
			writer.println("--- Statistical metrics");
			writer.println("Number of named classes:  " + res[0]);
			writer.println("Number of all classes (+ blank nodes):  " + res[1]);
			writer.println("Number of all properties:  " + res[2]);
			writer.println("Number of data properties:  " + res[3]);
			writer.println("Number of object properties:  " + res[4]);
			writer.println("Number of functional properties:  " + res[5]);
			writer.println("Number of individuals:  " + res[6]);
			writer.println("Number of statements:  " + res[7]);
			writer.println("Number of subjects:  " + res[8]);
			writer.println("Number of objects:  " + res[9]);
			writer.println("Number of comments:  " + res[10]);
			writer.println("Number of labels:  " + res[11]);
			writer.println("--- Class axiom metrics");
			writer.println("Number of sub classes:  " + res[12]);
			writer.println("Number of equivalent classes:  " + res[13]);
			writer.println("Number of disjoint classes:  " + res[14]);
			writer.println("--- Object property axiom metrics");
			writer.println("Number of sub object properties:  " + res[15]);
			writer.println("Number of equivalent object properties:  " + res[16]);
			writer.println("Number of inverse object properties:  " + res[17]);
			writer.println("Number of object properties domain:  " + res[18]);
			writer.println("Number of object properties range:  " + res[19]);
			writer.println("--- Functional property axiom metrics");
			writer.println("Number of object functional properties:  " + res[20]);
			writer.println("Number of inverse object functional properties:  " + res[21]);
			writer.println("Number of object properties domain:  " + res[22]);
			writer.println("Number of object properties range:  " + res[23]);
			writer.println("Number of imported ontologies:  " + res[24]);
			writer.println("\n **** 2- Ontology Design Metrics: ****");
			writer.println("Relationship richness:  " + res[25]);
			writer.println("Attribute relationship richness:  " + res[26]);
			writer.println("Inheritance richness:  " + res[27]);
			writer.println("Average ontology design metric:  " + res[28]);
			writer.println("--- KB metrics");
			writer.println("Class richness:  " + res[29]);
			writer.println("Average population richness:  " + res[30]);
			writer.println("Readability richness:  " + res[31]);
			writer.println("Average KB metric:  " + res[32]);
			writer.println("--- Class metrics");
			writer.println("Connected component cohesion:  " + res[33]);
			writer.println("Average importance class:  " + res[34]);
			writer.println("Concept relatives richness:  " + res[35]);
			writer.println("Average class metric:  " + res[36]);
			writer.println("--- Total ontology richness");
			writer.println("Ontology richness:  " + res[37]);
			writer.println("\n **** 3- Consistency Checker: ****");
			writer.println("Reasoning test:  " + res[38]);
			writer.println("Reasoning time:  " + res[39]);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}

		// zip create
		filzip = zipFiles(filename);

		return filzip;
	}

	public static String saveCompare(Data dataC1, String[] res1C, String[] res2C) {
		String filzip = dataC1.getOntName().substring(0, dataC1.getOntName().length() - 4) + "_comparison.zip";
		String filename = dataC1.getOntName().substring(0, dataC1.getOntName().length() - 4) + "_comparison.txt";
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(filename, "UTF-8");
			writer.println("The Comparison result for both ontology: \n");
			writer.println("\n **** 1- General metrics: ****");
			writer.println("--- Statistical metrics");
			writer.println("Number of named classes:  " + res1C[0] + ", \t" + res2C[0]);
			writer.println("Number of all classes (+ blank nodes):  " + res1C[1] + ", \t" + res2C[1]);
			writer.println("Number of all properties:  " + res1C[2] + ", \t" + res2C[2]);
			writer.println("Number of data properties:  " + res1C[3] + ", \t" + res2C[3]);
			writer.println("Number of object properties:  " + res1C[4] + ", \t" + res2C[4]);
			writer.println("Number of functional properties:  " + res1C[5] + ", \t" + res2C[5]);
			writer.println("Number of individuals:  " + res1C[6] + ", \t" + res2C[6]);
			writer.println("Number of statements:  " + res1C[7] + ", \t" + res2C[7]);
			writer.println("Number of subjects:  " + res1C[8] + ", \t" + res2C[8]);
			writer.println("Number of objects:  " + res1C[9] + ", \t" + res2C[9]);
			writer.println("Number of comments:  " + res1C[10] + ", \t" + res2C[10]);
			writer.println("Number of labels:  " + res1C[11] + ", \t" + res2C[11]);
			writer.println("--- Class axiom metrics");
			writer.println("Number of sub classes:  " + res1C[12] + ", \t" + res2C[12]);
			writer.println("Number of equivalent classes:  " + res1C[13] + ", \t" + res2C[13]);
			writer.println("Number of disjoint classes:  " + res1C[14] + ", \t" + res2C[14]);
			writer.println("--- Object property axiom metrics");
			writer.println("Number of sub object properties:  " + res1C[15] + ", \t" + res2C[15]);
			writer.println("Number of equivalent object properties:  " + res1C[16] + ", \t" + res2C[16]);
			writer.println("Number of inverse object properties:  " + res1C[17] + ", \t" + res2C[17]);
			writer.println("Number of object properties domain:  " + res1C[18] + ", \t" + res2C[18]);
			writer.println("Number of object properties range:  " + res1C[19] + ", \t" + res2C[19]);
			writer.println("--- Functional property axiom metrics");
			writer.println("Number of object functional properties:  " + res1C[20] + ", \t" + res2C[20]);
			writer.println("Number of inverse object functional properties:  " + res1C[21] + ", \t" + res2C[21]);
			writer.println("Number of object properties domain:  " + res1C[22] + ", \t" + res2C[22]);
			writer.println("Number of object properties range:  " + res1C[23] + ", \t" + res2C[23]);
			writer.println("Number of imported ontologies:  " + res1C[24] + ", \t" + res2C[24]);
			writer.println("\n **** 2- Ontology Design Metrics: ****");
			writer.println("Relationship richness:  " + res1C[25] + ", \t" + res2C[25]);
			writer.println("Attribute relationship richness:  " + res1C[26] + ", \t" + res2C[26]);
			writer.println("Inheritance richness:  " + res1C[27] + ", \t" + res2C[27]);
			writer.println("Average ontology design metric:  " + res1C[28] + ", \t" + res2C[28]);
			writer.println("--- KB metrics");
			writer.println("Class richness:  " + res1C[29] + ", \t" + res2C[29]);
			writer.println("Average population richness:  " + res1C[30] + ", \t" + res2C[30]);
			writer.println("Readability richness:  " + res1C[31] + ", \t" + res2C[31]);
			writer.println("Average KB metric:  " + res1C[32] + ", \t" + res2C[32]);
			writer.println("--- Class metrics");
			writer.println("Connected component cohesion:  " + res1C[33] + ", \t" + res2C[33]);
			writer.println("Average importance class:  " + res1C[34] + ", \t" + res2C[34]);
			writer.println("Concept relatives richness:  " + res1C[35] + ", \t" + res2C[35]);
			writer.println("Average class metric:  " + res1C[36] + ", \t" + res2C[36]);
			writer.println("--- Total ontology richness");
			writer.println("Ontology richness:  " + res1C[37] + ", \t" + res2C[37]);
			writer.println("\n **** 3- Consistency Checker: ****");
			writer.println("Reasoning test:  " + res1C[38] + ", \t" + res2C[38]);
			writer.println("Reasoning time:  " + res1C[39] + ", \t" + res2C[39]);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}

		// zip create
		filzip = zipFiles(filename);

		return filzip;
	}
	
	public static void main(String[] args) {
		String ont= "C:\\Users\\Samira\\Desktop\\mergeDataset\\BioPortal\\4ont\\DCO.owl.gz";
		Data d = new Data();
		d.setOntName(ont);
		d.setPath("C:\\Users\\Samira\\Desktop\\mergeDataset\\BioPortal\\4ont\\");
		DoAnalysis(d);

	}
}
