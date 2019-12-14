package fusion.comerger.algorithm.merger.holisticMerge;

public class Parameter {
	static int theta = 1; // for determining cores
	static String filePathDirectory;
	static String logFile;

	public static int getTheta() {
		return theta;
	}

	public static void setTheta(int t) {
		theta = t;
	}

	public static String getFilePathDirectory() {
		return filePathDirectory;
	}

	public static void setFilePathDirectory(String dr) {
		filePathDirectory = dr;
	}

	public static String getLogFile() {
		return logFile;
	}

	public static void setLogFile(String f) {
		logFile = f;
	}
}
