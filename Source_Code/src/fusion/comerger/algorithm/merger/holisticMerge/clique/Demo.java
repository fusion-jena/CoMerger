package fusion.comerger.algorithm.merger.holisticMerge.clique;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import au.com.bytecode.opencsv.CSVReader;
import fusion.comerger.algorithm.merger.holisticMerge.clique.DenseGraphLargestCliqueFinder;
import fusion.comerger.algorithm.merger.holisticMerge.clique.SparseGraphLargestCliqueFinder;

public class Demo {
//	static HashMap<String, Integer> RulesListInfo = new HashMap<String, Integer>();
	static int [][] RulesListInfo = new int [250][3];
	static boolean RuleListCreator = false;

    public static void main(String[] args) {
        System.out.println("--- Dense graph ---");
        UndirectedGraph denseGraph = getDenseGraph();

        long start = System.currentTimeMillis();
        int[] clique1 = new SparseGraphLargestCliqueFinder()
                           .computeLargestClique(denseGraph);
        ArrayList<ArrayList<Integer>> allSet = SparseGraphLargestCliqueFinder.getAllClique();
        ArrayList<Integer> userGMR = new ArrayList<Integer> ();
        userGMR.add(1);
        userGMR.add(13);
        userGMR.add(8);
        int numSuggestion = 5;
        ArrayList<GMR> userClique = UserClique.findUserClique(userGMR,allSet,numSuggestion);
        for (int i=0; i<userClique.size();i++){
        	System.out.println(userClique.get(i));
        }
        
        long end = System.currentTimeMillis();

        System.out.println("SparseGraphLargestCliqueFinder in " +
                (end - start) + " milliseconds. Clique: " + 
                Arrays.toString(clique1) + ", clique size: " + clique1.length);

        start = System.currentTimeMillis();
        int[] clique2 = new DenseGraphLargestCliqueFinder()
                           .computeLargestClique(denseGraph);
        end = System.currentTimeMillis();

        System.out.println("DenseGraphLargestCliqueFinder in " +
                (end - start) + " milliseconds. Clique: " + 
                Arrays.toString(clique2) + ", clique size: " + clique2.length);

       System.out.println("--- Sparse graph ---");
        UndirectedGraph sparseGraph = getSparseGraph();

        start = System.currentTimeMillis();
        clique1 = new SparseGraphLargestCliqueFinder()
                           .computeLargestClique(sparseGraph);
        end = System.currentTimeMillis();

        System.out.println("SparseGraphLargestCliqueFinder in " +
                (end - start) + " milliseconds. Clique: " + 
                Arrays.toString(clique1) + ", clique size: " + clique1.length);

        start = System.currentTimeMillis();
        clique2 = new DenseGraphLargestCliqueFinder()
                           .computeLargestClique(sparseGraph);
        end = System.currentTimeMillis();

        System.out.println("DenseGraphLargestCliqueFinder in " +
                (end - start) + " milliseconds. Clique: " + 
                Arrays.toString(clique2) + ", clique size: " + clique2.length);
                
    }

    public static UndirectedGraph getDenseGraph() {
        UndirectedGraph graph = new UndirectedGraph();
        Random random = new Random();
        int nodes = 20;
        int edges = nodes * nodes / 3;

        for (int i = 0; i < nodes; ++i) {
            graph.addNode(i);
        }

        /*
        for (int i = 0; i < edges; ++i) {
           int node1 = random.nextInt(nodes);
           int node2 =random.nextInt(nodes);
        	graph.connect(node1, node2);
        }*/
        String RuleInfo = "C:\\uploads\\RulesConflictNum.csv";
        if (RuleListCreator == false) {
			RulesListInfo = CSVtoArray(RuleInfo);
		}
        for (int i=0; i<RulesListInfo.length; i++){
        	if (RulesListInfo[i][2]== 1){
        		graph.connect(RulesListInfo[i][0], RulesListInfo[i][1]);
        	}
        }
       
        
        
        return graph;
    }

    private static UndirectedGraph getSparseGraph() {
        UndirectedGraph graph = new UndirectedGraph();
        Random random = new Random();
        int nodes = 7;
        int edges = 5 * nodes / 4;

        for (int i = 0; i < nodes; ++i) {
            graph.addNode(i);
        }

        /*for (int i = 0; i < edges; ++i) {
            graph.connect(random.nextInt(nodes), random.nextInt(nodes));
        }*/
       
        
        graph.connect(1, 2);
        graph.connect(1, 3);
        graph.connect(1, 8);
        graph.connect(2, 3);
        graph.connect(2, 8);
        graph.connect(3, 8);
        graph.connect(3, 13);
        graph.connect(2, 17);
        graph.connect(3, 17);
        graph.connect(8, 17);
        graph.connect(1, 5);
        graph.connect(2, 5);
        graph.connect(3, 5);
        graph.connect(5, 17);
        
        
        return graph;
    }
    public static int[][] CSVtoArray(String csvFile) {
		// int i = 0;
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			int i=0;
			while ((line = reader.readNext()) != null) {
				// String[] te = line[0].split("\t");
				//String item = line[0] + "-" + line[1];
				if (line[2] != null) {
					int [] temp = new int [3];
					temp[0] =Integer.parseInt(line[0]);
					temp[1] =Integer.parseInt(line[1]);
					temp[2] =Integer.parseInt(line[2]);
					RulesListInfo[i]= temp;//.put(item, Integer.valueOf(line[2]));
					i++;
					// if (line[1] != null)
					// QueryRef[i][1] = line[1];
					// i++;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		RuleListCreator = true;
		return RulesListInfo;
	}
}
//https://codereview.stackexchange.com/questions/171029/finding-largest-graph-cliques-in-java