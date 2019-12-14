package fusion.comerger.algorithm.merger.holisticMerge.general;

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

import fusion.comerger.algorithm.merger.model.HModel;
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

public class SaveTxt {
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

	public static String ConvertEvalResultToTxt(HModel ontM, String[] res) throws FileNotFoundException {
		String filzip = ontM.getOntName().substring(0, ontM.getOntName().length() - 4) + "_evaluation.zip";
		String filename = ontM.getOntName().substring(0, ontM.getOntName().length() - 4) + "_evaluation.txt";
		//to be faster for test, temporally i delete these
//		PrintWriter writer = null;
//		try {
//			writer = new PrintWriter(filename, "UTF-8");
//			writer.println("Total compactness \t" + res[2][0]);
//			writer.println("Total coverage \t" + res[3][0]);
//			writer.println("Total Redundancy \t" + res[4][0]);
//			writer.println("Total  accuracy \t" + res[5][0]);
//			writer.println("Total consistency \t" + res[6][0]);
//			writer.println("Total readibility \t" + res[7][0]);
//
//			// writer.println("\n Detail results: \n");
//
//			// writer.println("\n **** 1- compactness metrics: ****"); //TODO:
//			// say what is this metric also
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				writer.close();
//			} catch (Exception e) {
//			}
//		}
//
//		// zip create
//		filzip = zipFiles(filename);

		return filzip;
	}

	public static String ConsistencyResultToTxt(HModel ontM, String[] res) throws FileNotFoundException {
		String filzip = ontM.getOntName().substring(0, ontM.getOntName().length() - 4) + "_consistency.zip";
		String filename = ontM.getOntName().substring(0, ontM.getOntName().length() - 4) + "_consistency.txt";
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(filename, "UTF-8");
			writer.println("The consistency done with " + res[0] + ". " + res[1]);
			if (res[2].contains("FAILED")) {
				writer.println("The satisfiability test  is " + res[2].substring(58, Math.min(60 + 6, res[2].length()))
						+ ". " + res[3]);// .split(".")[1]);
			} else {
				writer.println("The satisfiability test  is " + res[2].substring(60, Math.min(60 + 8, res[2].length()))
						+ ". " + res[3]);// .split(".")[1]);
			}
			writer.println("Number of unsatisfiable classes " + res[4] + ". " + res[5].replaceAll("<br>", "\n"));
			writer.println("Root of unsatisfiable classes " + res[6] + ". " + res[7].replaceAll("<br>", "\n"));
			writer.println("Number of justifications " + res[8] + ". " + res[9]);
			writer.println("Number of erroneous axioms " + res[10] + ". " + res[11]);
			writer.println("Elapsed time for detecting by the reasoner " + res[12] + " ms, time to rank  " + res[13]
					+ " ms, and time to generate repair plan  " + res[14] + " ms.");

			// writer.println("\n Repair plan details" + res[15] + "\n" +
			// res[16]);

			// writer.println("\n Detail results: \n");

			// writer.println("\n **** 1- compactness metrics: ****"); //TODO:
			// say what is this metric also

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

	public void CreateBarChartInfo(HModel ontM, String[][] res) {
		String filename = "C:\\Doc\\GitRepo\\PhdProject\\samira-project\\Merge\\WebContent\\data2.tsv";
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(filename, "UTF-8");
			writer.println("Compactness\t" + res[2][0]);
			writer.println("Coverage\t" + res[3][0]);
			writer.println("Redundancy\t" + res[4][0]);
			writer.println("Accuracy\t" + res[5][0]);
			writer.println("Readability\t" + res[6][0]);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}
	}
}
