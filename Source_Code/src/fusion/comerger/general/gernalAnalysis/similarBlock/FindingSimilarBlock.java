
package fusion.comerger.general.gernalAnalysis.similarBlock;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import fusion.comerger.algorithm.partitioner.SeeCOnt.Cluster;
import fusion.comerger.general.gui.MatchingPanel;
import fusion.comerger.general.output.Alignment;
import fusion.comerger.model.RBGModel;


public class FindingSimilarBlock
{
	private RBGModel rbgModelA = null,  rbgModelB = null;
    private String name1 = null,  name2 = null;
    private int maxSize = 500;
    private String tempDir = null;
    private Alignment alignSet = null;
    private ArrayList<BlockMapping> bmSet = null;


    public void FSBlock(RBGModel Model1, RBGModel Model2, LinkedHashMap<Integer, Cluster> clusters1, LinkedHashMap<Integer, Cluster> clusters2, String name1, String name2, String tDir)
    {
    	rbgModelA = Model1; rbgModelB= Model2;
    	
    	BlockMatcher bm = new BlockMatcher(rbgModelA, rbgModelB, name1, name2, clusters1, clusters2, tDir);
    	bm.blockMatch();

        alignSet = bm.getAnchors();
        bmSet = bm.getBlockMappings();
    }

	public Alignment getAnchors()
	{
	    return alignSet;
	}	
	public ArrayList<BlockMapping> getBlockMappings()
    {
        return bmSet;
    }
    
    }
