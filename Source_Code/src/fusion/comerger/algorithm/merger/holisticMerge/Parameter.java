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
