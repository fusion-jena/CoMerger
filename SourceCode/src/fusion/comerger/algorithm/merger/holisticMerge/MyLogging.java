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
import java.io.IOException;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogging {
	static Logger logger;
	public Handler fileHandler;
	Formatter plainText;

	public MyLogging() throws IOException {

		try {

			// instance the logger
			logger = Logger.getLogger(MyLogging.class.getName());
			plainText = new SimpleFormatter();

			Random rand = new Random();
			int id = rand.nextInt(1000 - 0 + 1) + 0;
			String FILE_DIRECTORY = Parameter.getFilePathDirectory();
			if (FILE_DIRECTORY == null)
				FILE_DIRECTORY = "C:\\uploads";
			/*
			 * a sample local folder if the code does not run from the servlet
			 */
			String fileName = FILE_DIRECTORY + "\\MyLogFile" + id + ".log";
			Parameter.setLogFile(fileName);
			System.out.println(fileName);

			// create fileHandler
			fileHandler = new FileHandler(fileName);
			fileHandler.setFormatter(plainText);
			logger.addHandler(fileHandler);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);

			// To remove the console handler, use
			logger.setUseParentHandlers(false);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public MyLogging(String path) throws IOException {

		try {
			logger = Logger.getLogger(MyLogging.class.getName());
			plainText = new SimpleFormatter();

			Random rand = new Random();
			int id = rand.nextInt(1000 - 0 + 1) + 0;
			String FILE_DIRECTORY = path;
			if (FILE_DIRECTORY == null)
				FILE_DIRECTORY = "C:\\uploads";
			String fileName = FILE_DIRECTORY + "\\MyLogFile" + id + ".log";
			Parameter.setLogFile(fileName);
			System.out.println(fileName);

			// create fileHandler
			fileHandler = new FileHandler(fileName);
			fileHandler.setFormatter(plainText);
			logger.addHandler(fileHandler);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);

			// To remove the console handler, use
			logger.setUseParentHandlers(false);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Logger getLogger() {
		if (logger == null) {
			try {
				new MyLogging();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return logger;
	}

	public static void log(Level level, String msg) {
		getLogger().log(level, msg);
	}

}
