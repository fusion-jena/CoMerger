package fusion.comerger.algorithm.merger.holisticMerge.general;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.model.HModel;

public class Zipper {

	
	public static String unzip(String zipFilePath) throws IOException {
		Path p = Paths.get(zipFilePath);
		Path folder = p.getParent();
		String destDir = folder.toString();

		File dir = new File(destDir);
		// create output directory if it doesn't exist
		if (!dir.exists())
			dir.mkdirs();
		FileInputStream fis;
		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		File newFile = null;
		try {
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				newFile = new File(destDir + File.separator + fileName);
				// System.out.println("Unzipping to " +
				// newFile.getAbsolutePath());
				// create directories for sub directories in zip
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				// close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			// close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFile.getAbsolutePath();

	}

	public static String zipFiles(String... filePaths) {
		long startTime = System.currentTimeMillis();

		String zipFileName = "";
		try {
			File firstFile = new File(filePaths[0]);
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
			MyLogging.log(Level.SEVERE,
					"Exception from creating the zip file: A file does not exist. " + ex.toString() + "\n");
		} catch (IOException ex) {
			System.err.println("I/O error: " + ex);
			MyLogging.log(Level.SEVERE, "Exception from creating the zip file: I/O error. " + ex.toString() + "\n");
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		String MergedOntZipName =  new File(zipFileName).getName();
		MyLogging.log(Level.INFO,
				"Create a zip file of the created merged ontology with name: " + MergedOntZipName + ". Total time  " + elapsedTime + " ms. \n");
		
		StatisticTest.result.put("time_create_zip", String.valueOf(elapsedTime));
		
		return zipFileName;
	}
	
	public static String zipAllFiles(HModel ontM ) {
		long startTime = System.currentTimeMillis();
		ArrayList<String> filePaths = ontM.getSubMergedOntName();
		String zipFileName  =ontM.getPath() + "subMergedOntologies.zip";
		
		try {
//			File firstFile = new File(filePaths.get(0));
//			zipFileName = firstFile.getPath().substring(0, firstFile.getPath().length() - 4).concat(".zip");

			
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
			MyLogging.log(Level.SEVERE,
					"Exception from creating the zip file: A file does not exist. " + ex.toString() + "\n");
		} catch (IOException ex) {
			System.err.println("I/O error: " + ex);
			MyLogging.log(Level.SEVERE, "Exception from creating the zip file: I/O error. " + ex.toString() + "\n");
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		String MergedOntZipName =  new File(zipFileName).getName();
		MyLogging.log(Level.INFO,
				"Create a zip file of the created merged ontology with name: " + MergedOntZipName + ". Total time  " + elapsedTime + " ms. \n");
		
		StatisticTest.result.put("time_create_zip", String.valueOf(elapsedTime));
		
		return zipFileName;
	}
}
