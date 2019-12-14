package fusion.comerger.algorithm.merger.holisticMerge.clique;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVReader;

public class UserCliqueExtractor {

	public static ArrayList<GMR> extractor(ArrayList<Integer> userGMR, int numSuggestion) {
		ArrayList<ArrayList<Integer>> allSet = new ArrayList<ArrayList<Integer>>();
		if (Parameter.buildAllClique == true) {
			allSet = readCSV();
		} else {
			UndirectedGraph denseGraph = Demo.getDenseGraph();
			int[] clique1 = new SparseGraphLargestCliqueFinder().computeLargestClique(denseGraph);
			allSet = SparseGraphLargestCliqueFinder.getAllClique();
			// save as csv for other runs
			SaveCSV(allSet);
			Parameter.buildAllClique = true;
		}

		// check to be sure:
		if (allSet.size() < 1) {
			UndirectedGraph denseGraph = Demo.getDenseGraph();
			int[] clique1 = new SparseGraphLargestCliqueFinder().computeLargestClique(denseGraph);
			allSet = SparseGraphLargestCliqueFinder.getAllClique();
			// save as csv for other runs
			SaveCSV(allSet);
			Parameter.buildAllClique = true;
		}

		ArrayList<GMR> userClique = UserClique.findUserClique(userGMR, allSet, numSuggestion);
		return userClique;
	}

	private static ArrayList<ArrayList<Integer>> readCSV() {
		String csvFile = "c:\\LOCAL_FOLDER\\allCliques.csv";
		ArrayList<ArrayList<Integer>> allSet = new ArrayList<ArrayList<Integer>>();
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			while ((line = reader.readNext()) != null) {
				ArrayList<Integer> temp = new ArrayList<Integer>();
				for (int k = 0; k < line.length; k++) {
					int set = Integer.parseInt(line[k]);
					temp.add(set);

				}
				allSet.add(temp);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allSet;

	}

	private static void SaveCSV(ArrayList<ArrayList<Integer>> allSet) {
		String fileName = "c:\\LOCAL_FOLDER\\allCliques.csv";
		Path fileToDeletePath = Paths.get(fileName);
		try {
			Files.delete(fileToDeletePath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try (PrintWriter writer = new PrintWriter(new File(fileName))) {
			for (int i = 0; i < allSet.size(); i++) {
				StringBuilder sb = new StringBuilder();
				String text = "";
				ArrayList<Integer> arry = allSet.get(i);
				for (int j = 0; j < arry.size(); j++) {
					int set = arry.get(j);
					text = text + "," + Integer.toString(set);
				}
				text = text.substring(1);
				sb.append(text);
				sb.append('\n');
				writer.write(sb.toString());
			}
			System.out.println("done!");
			writer.close();

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

}
