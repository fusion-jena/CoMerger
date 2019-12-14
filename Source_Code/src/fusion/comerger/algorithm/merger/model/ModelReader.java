package fusion.comerger.algorithm.merger.model;

import java.util.logging.Level;

import fusion.comerger.algorithm.merger.holisticMerge.HBuilder;
import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.general.HSave;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMapping;

public class ModelReader {

		public static HModel createReadModel(String inputFile, String mapFile, String mergeFile, String preferedOnt) {
		long startTime = System.currentTimeMillis();
		
		HModel ontM = new HModel();
		ontM.setlistOntName(inputFile);
		ontM.setOntName(mergeFile);

		ontM.SetPreferedOnt(preferedOnt);
		ontM = HBuilder.run(ontM);
		// Builder.BuildOntEval(ontM);

		// 2 -- Read Mapping
		HMapping hp = new HMapping();
		ontM = hp.run(ontM, mapFile);

		//TODO: in merge process after this phase, the merged ont will be revised, and some axioms will be delete, but here, no!

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,"*** done! Congratulation. Reading the model has been done successfully. Total time  " + elapsedTime+" ms. \n");
		return ontM;
	}

}
