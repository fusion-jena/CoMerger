
package fusion.comerger.algorithm.matcher;
/* 
 * This package is downloaded from the FALCON-AO tool, 
 * available in http://ws.nju.edu.cn/falcon-ao/
 * For more information of this method, please refer to
 * Hu, W. and Qu, Y., 2008. Falcon-AO: A practical ontology matching system. Journal of web semantics, 6(3), pp.237-239.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.util.FileManager;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.SetOntologyID;

import fusion.comerger.algorithm.matcher.gmo.ExternalMatch;
import fusion.comerger.algorithm.matcher.gmo.GmoMatcher;
import fusion.comerger.algorithm.matcher.post.Patcher;
import fusion.comerger.algorithm.matcher.string.StringMatcher;
import fusion.comerger.algorithm.matcher.vdoc.VDocMatcher;
import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.partitioner.SeeCOnt.CClustering;
import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Configuration;
import fusion.comerger.general.cc.Data;
import fusion.comerger.general.cc.LingComp;
import fusion.comerger.general.cc.StruComp;
import fusion.comerger.general.gernalAnalysis.matrix.NamedMatrix;
import fusion.comerger.general.gernalAnalysis.similarBlock.BlockMapping;
import fusion.comerger.general.gernalAnalysis.similarBlock.FindingSimilarBlock;
import fusion.comerger.general.output.Alignment;
import fusion.comerger.general.output.AlignmentReader2;
import fusion.comerger.general.output.AlignmentSelector;
import fusion.comerger.general.output.AlignmentWriter2;
import fusion.comerger.general.output.Evaluator;
import fusion.comerger.general.output.Mapping;
import fusion.comerger.general.output.ResultData;
import fusion.comerger.model.Constant;
import fusion.comerger.model.Node;
import fusion.comerger.model.NodeCategory;
import fusion.comerger.model.RBGModel;
import fusion.comerger.model.RBGModelFactory;

public class Matching {
	private String filepath1 = null, filepath2 = null;
	private String NumCHMatchFile1 = null, NumCHMatchFile2 = null;
	private Alignment alignRes = null;
	private int stringComp1 = -1;
	private int vdocComp1 = -1, vdocComp2 = -1;
	private int gmoComp1 = -1, gmoComp2 = -1;

	private int namedClassNumA = 0, namedClassNumB = 0;
	private int namedPropertyNumA = 0, namedPropertyNumB = 0;
	private int namedInstanceNumA = 0, namedInstanceNumB = 0;

	// private OntModel model1 = null, model2 = null;
	public static OntModel model1 = null, model2 = null;
//	org.apache.jena.rdf.model.Model model1 =null, model2=null;

	public Matching(String fp1, String fp2, String CHMatchFile1, String CHMatchFile2) {
		filepath1 = fp1;
		filepath2 = fp2;
		NumCHMatchFile1 = CHMatchFile1;
		NumCHMatchFile2 = CHMatchFile2;
	}

	public OntModel getOntModel1() {
		return model1;
	}

	public OntModel getOntModel2() {
		return model2;
	}

	public Alignment run(String path) {
		OntDocumentManager mgr = new OntDocumentManager();
		mgr.setProcessImports(true);
		OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
		spec.setDocumentManager(mgr);

		boolean model1NotBuild = false, model2NotBuild = false;
		
		try {
			model1 = ModelFactory.createOntologyModel(spec, null);
			model1.read(filepath1);
			model1NotBuild = true;
		} catch (Exception e) {
			model1NotBuild = false;
		}
		if (model1NotBuild == false) {
			try {
				 String f = filepath1.substring(5, filepath1.length());
				model1 = ModelFactory.createOntologyModel(spec);// OntModelSpec.OWL_DL_MEM);
				model1.read(new FileInputStream(f), null, RDFLanguages.strLangTurtle);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		try {
			model2 = ModelFactory.createOntologyModel(spec, null);
			model2.read(filepath2);

			model2NotBuild = true;
		} catch (Exception e) {
			model2NotBuild = false;
		}
		if (model2NotBuild == false) {
			try {
				String f = filepath2.substring(5, filepath2.length());
				model2 = ModelFactory.createOntologyModel(spec);// OntModelSpec.OWL_DL_MEM);
				model2.read(new FileInputStream(f), null, RDFLanguages.strLangTurtle);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}


		int size1 = model1.listStatements().toList().size(), size2 = model2.listStatements().toList().size();
		Parameters.largeOnto = 100000000;
		if (size1 >= Parameters.largeOnto || size2 >= Parameters.largeOnto) {
			// If the ontology size is small, it use GMO and VDOC , otherwise it
			// uses SeeCOnt

			RBGModel rbgm1 = RBGModelFactory.createModel("PBM_MODEL");
			rbgm1.setOntModel(model1);
			RBGModel rbgm2 = RBGModelFactory.createModel("PBM_MODEL");
			rbgm2.setOntModel(model2);

			// String wd = "./temp/";
			String wd = path;
			File file = new File(wd);
			if (file.exists() == false) {
				file.mkdir();
			}
			String fn1 = filepath1.substring(5, filepath1.length());
			String fn2 = filepath2.substring(5, filepath2.length());

			/*
			 * // In Falcon, It call PBM method and get anchors and block
			 * mapping, instead of it, I used SeeCOnt PbmMatcher pbm = new
			 * PbmMatcher(rbgm1, rbgm2, fn1, fn2, wd); pbm.match(); Alignment
			 * anchors = pbm.getAnchors(); ArrayList<BlockMapping> blockMappings
			 * = pbm.getBlockMappings();
			 */

			//////// ****call finding similar block to get "BlockMap" /////////
			// For getting cluster, I forced to call SeeCOnt, and in the SeeCOnt
			//////// we need the number of clusters.So, I asked the user to say
			//////// the ontologies should have how many partitions?
			// String NumCHUser= JOptionPane.showInputDialog("Please input the
			// Number of Partition for 1st ontology ");
			String fileName1;
			String fileName2;
		
			fileName1 = fn1;
			Data data1 = new Data();
			data1.setOntName(fileName1);
			data1.setPath(path);
			BuildModel.BuildModelOnt(data1);
			String name1 = data1.getOntName();
			data1.setNumCH(Integer.parseInt(NumCHMatchFile1));
			CClustering C_Ont1 = new CClustering(); 
			C_Ont1.StepsCClustering(data1, 0); // 0 means call to main
												// partitioning process

			// NumCHUser= JOptionPane.showInputDialog("Please input the Number
			// of Partition for 2st ontology ");
			Data data2 = new Data();
			data2.setOntName(fn2);
			data2.setPath(path);
			BuildModel.BuildModelOnt(data2);
			String name2 = data2.getOntName();// BuildModel.fn1;
			data2.setNumCH(Integer.parseInt(NumCHMatchFile2));
			CClustering C_Ont2 = new CClustering();
			C_Ont2.StepsCClustering(data2, 0);// 0 means call to main
												// partitioning process
			// System.out.println("the partitining is done \n");
			// System.out.println("========= Starting Matching ====== \n");
			FindingSimilarBlock FSB = new FindingSimilarBlock();
			// FSB.FSBlock(rbgm1, rbgm2,C_Ont1.clusters, C_Ont2.clusters, name1,
			// name2, BuildModel.wd);
			FSB.FSBlock(rbgm1, rbgm2, data1.getClusters(), data2.getClusters(), name1, name2, data1.getPath());
			Alignment anchors = FSB.getAnchors();
			ArrayList<BlockMapping> blockMappings = FSB.getBlockMappings();
			////////////////////////////////////////////////////////////////
			// System.out.println("Similar blocks:\t" + blockMappings.size());

			Alignment alignSet = new Alignment();
			for (int i = 0; i < blockMappings.size(); i++) {
				BlockMapping bm = (BlockMapping) blockMappings.get(i);
				String f1 = bm.getClusterName1();
				String f2 = bm.getClusterName2();
				// System.out.println(f2 + "\t the files\t" + f1);
				OntModel m1 = ModelFactory.createOntologyModel(spec, null);
				OntModel m2 = ModelFactory.createOntologyModel(spec, null);
				m1.read("file:" + f1);
				m2.read("file:" + f2);
				// System.out.println("running " + i + " .... " + f1 + " & " +
				// f2);
				Alignment tempSet = run(path, m1, m2);
				for (int j = 0; j < tempSet.size(); j++) {
					alignSet.addMapping(tempSet.getMapping(j));
				}
			}
			for (int i = 0, n = anchors.size(); i < n; i++) {
				Mapping anchor = anchors.getMapping(i);
				boolean flag = true;
				for (int k = 0; k < alignSet.size(); k++) {
					Mapping temp = alignSet.getMapping(k);
					if (anchor.equals(temp)) {
						flag = false;
						if (temp.getSimilarity() < anchor.getSimilarity()) {
							temp.setSimilarity(anchor.getSimilarity());
						}
					}
				}
				if (flag == true) {
					alignSet.addMapping(anchor);
				}
			}
			Patcher patcher = new Patcher(alignSet);
			patcher.match();

			alignRes = alignSet;
		} else {
			alignRes = run(path, model1, model2);
		}
		return alignRes;
	}

	private void convertOntModelToOwl() {
		OWLOntology ontology = readOWLfile();

		
		org.apache.jena.rdf.model.Model model2 =  ModelFactory.createDefaultModel();
		InputStream file = FileManager.get().open( filepath1);
		model2.read(file,null);
		model2.write(System.out);
	
		org.apache.jena.rdf.model.Model model = ModelFactory.createDefaultModel();

		    try (PipedInputStream is = new PipedInputStream(); PipedOutputStream os = new PipedOutputStream(is)) {
		        new Thread(new Runnable() {
		            @Override
		            public void run() {
		                try {
//		                	OWLOntologyFormat turtle = new  TurtleDocumentFormat();
//		                	TurtleDocumentFormat fmt = new TurtleDocumentFormat();
//		                			OWLDocumentFormat ontologyFormat
//		                	OWLDocumentFormat ontologyFormat= new FunctionalSyntaxDocumentFormat();
		                    ontology.getOWLOntologyManager().saveOntology(ontology, os);
		                    os.close();
		                } catch (OWLOntologyStorageException | IOException e) {
		                    e.printStackTrace();
		                }
		            }
		        }).start();
		        model.read(is, null, "TURTLE");
//		        return model;
		    } catch (Exception e) {
		        throw new RuntimeException("Could not convert OWL API ontology to JENA API model.", e);
		    }
		
	}

	private OWLOntology readOWLfile() {
//		OWLOntology ontology = null ;
		OWLOntologyManager MergedManager = OWLManager.createOWLOntologyManager();
		File fileM = new File(filepath1);
		 OWLOntology ontology = null;

		try {
			ontology = MergedManager.createOntology(IRI.create(fileM));
			// MergedOntology.setir(IRI.create("http://merged#"))
			// versionIRI can be null
			OWLOntologyID newOntologyID = new OWLOntologyID(IRI.create("http://merged#"), IRI.create("1.0"));
			// Create the change that will set our version IRI
			SetOntologyID setOntologyID = new SetOntologyID(ontology, newOntologyID);
			// Apply the change
			MergedManager.applyChange(setOntologyID);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
			MyLogging.log(Level.SEVERE,
					"OWLOntologyCreationException in reading input ontologies" + e.toString() + "\n");
		}
		return ontology;
		
	}

	private Alignment run(String path, OntModel model1, OntModel model2) {
		
		RBGModel rbgmString1 = RBGModelFactory.createModel("STRING_MODEL");
		rbgmString1.setOntModel(model1);
		RBGModel rbgmString2 = RBGModelFactory.createModel("STRING_MODEL");
		rbgmString2.setOntModel(model2);
		StringMatcher string = new StringMatcher(rbgmString1, rbgmString2);
		string.match();
		Alignment classStringAS = string.getClassAlignment();
		Alignment propertyStringAS = string.getPropertyAlignment();
		Alignment schemaStringAS = combine(classStringAS, propertyStringAS);
		LingComp comp2 = new LingComp(rbgmString1, rbgmString2);
		stringComp1 = comp2.estimate1(schemaStringAS);

		return schemaStringAS;
		
		/*
		RBGModel rbgmGmo1 = RBGModelFactory.createModel("GMO_MODEL");
		rbgmGmo1.setOntModel(model1);
		RBGModel rbgmGmo2 = RBGModelFactory.createModel("GMO_MODEL");
		rbgmGmo2.setOntModel(model2);
		StruComp comp3 = new StruComp(rbgmGmo1, rbgmGmo2);
		gmoComp1 = comp3.estimate1();
		countNodesByCategory(rbgmGmo1, rbgmGmo2);
		
		
		
		
		if (gmoComp1 == Parameters.highComp) {
			if (stringComp1 == Parameters.highComp || vdocComp1 == Parameters.highComp) {
				if (stringComp1 == vdocComp1) {
					boolean flag1 = isAllFound(vdoc);
					boolean flag2 = isAllFound(string);
					if (flag1 == true && flag2 == true) {
						NamedMatrix nmcVDoc = vdoc.getClassMatrix();
						NamedMatrix nmcString = string.getClassMatrix();
						NamedMatrix nmc = combine(nmcVDoc, nmcString);
						Alignment escCombine = AlignmentSelector.select(nmc, Parameters.lingLowSim);
						NamedMatrix nmpVDoc = vdoc.getPropertyMatrix();
						NamedMatrix nmpString = string.getPropertyMatrix();
						NamedMatrix nmp = combine(nmpVDoc, nmpString);
						Alignment espCombine = AlignmentSelector.select(nmp, Parameters.lingLowSim);
						return combine(escCombine, espCombine);
					} else if (flag1 == true) {
						return schemaStringAS.cut(Parameters.lingLowSim);
					} else if (flag2 == true) {
						return schemaVDocAS.cut(Parameters.lingLowSim);
					} else {
						GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
						ExternalMatch ext = new ExternalMatch(rbgmGmo2, rbgmGmo1);
						NamedMatrix nmcVDoc = vdoc.getClassMatrix();
						NamedMatrix nmcString = string.getClassMatrix();
						NamedMatrix nmc = combine(nmcVDoc, nmcString);
						Alignment escCombine = AlignmentSelector.select(nmc, Parameters.lingLowSim);
						NamedMatrix nmpVDoc = vdoc.getPropertyMatrix();
						NamedMatrix nmpString = string.getPropertyMatrix();
						NamedMatrix nmp = combine(nmpVDoc, nmpString);
						Alignment espCombine = AlignmentSelector.select(nmp, Parameters.lingLowSim);
						NamedMatrix nmiVDoc = vdoc.getInstanceMatrix();
						NamedMatrix nmiString = string.getInstanceMatrix();
						NamedMatrix nmi = combine(nmiVDoc, nmiString);
						Alignment esiCombine = AlignmentSelector.select(nmi, Parameters.lingLowSim);
						ext.setClassSim(escCombine);
						ext.setPropertySim(espCombine);
						ext.setInstanceSim(esiCombine);
						gmo.setExternalMatch(ext);
						gmo.match();
						Alignment escGmo = gmo.getClassAlignment();
						Alignment espGmo = gmo.getPropertyAlignment();
						Alignment esGmo = combine(escGmo, espGmo);
						return combine(combine(escCombine, espCombine), esGmo);
					}
				} else if (stringComp1 > vdocComp1) {
					boolean flag = isAllFound(string);
					if (flag == true) {
						return schemaStringAS.cut(Parameters.lingLowSim);
					} else {
						GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
						ExternalMatch ext = new ExternalMatch(rbgmGmo2, rbgmGmo1);
						Alignment escString = classStringAS.cut(Parameters.lingLowSim);
						Alignment espString = propertyStringAS.cut(Parameters.lingLowSim);
						Alignment esiString = string.getInstanceAlignment().cut(Parameters.lingLowSim);
						ext.setClassSim(escString);
						ext.setPropertySim(espString);
						ext.setInstanceSim(esiString);
						gmo.setExternalMatch(ext);
						gmo.match();
						Alignment escGmo = gmo.getClassAlignment();
						Alignment espGmo = gmo.getPropertyAlignment();
						Alignment esGmo = combine(escGmo, espGmo);
						return combine(combine(escString, espString), esGmo);
					}
				} else {
					boolean flag = isAllFound(vdoc);
					if (flag == true) {
						return schemaVDocAS.cut(Parameters.lingLowSim);
					} else {
						GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
						ExternalMatch ext = new ExternalMatch(rbgmGmo2, rbgmGmo1);
						Alignment escVDoc = classVDocAS.cut(Parameters.lingLowSim);
						Alignment espVDoc = propertyVDocAS.cut(Parameters.lingLowSim);
						Alignment esiVDoc = vdoc.getInstanceAlignment().cut(Parameters.lingLowSim);
						ext.setClassSim(escVDoc);
						ext.setPropertySim(espVDoc);
						ext.setInstanceSim(esiVDoc);
						gmo.setExternalMatch(ext);
						gmo.match();
						Alignment escGmo = gmo.getClassAlignment();
						Alignment espGmo = gmo.getPropertyAlignment();
						Alignment esGmo = combine(escGmo, espGmo);
						return combine(combine(escVDoc, espVDoc), esGmo);
					}
				}
			} else if (stringComp1 == Parameters.mediumComp || vdocComp1 == Parameters.mediumComp) {
				if (stringComp1 == vdocComp1) {
					NamedMatrix nmcVDoc = vdoc.getClassMatrix();
					NamedMatrix nmcString = string.getClassMatrix();
					NamedMatrix nmc = combine(nmcVDoc, nmcString);
					Alignment escCombine = AlignmentSelector.select(nmc, Parameters.lingHighSim);
					NamedMatrix nmpVDoc = vdoc.getPropertyMatrix();
					NamedMatrix nmpString = string.getPropertyMatrix();
					NamedMatrix nmp = combine(nmpVDoc, nmpString);
					Alignment espCombine = AlignmentSelector.select(nmp, Parameters.lingHighSim);
					Alignment esCombine = combine(escCombine, espCombine);
					GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
					gmo.match();
					Alignment escGmo = gmo.getClassAlignment();
					Alignment espGmo = gmo.getPropertyAlignment();
					Alignment esGmo = combine(escGmo, espGmo);
					gmoComp2 = comp3.estimate2(esCombine, esGmo);
					if (gmoComp2 == Parameters.highComp) {
						return esGmo;
					} else {
						return esCombine;
					}
				} else if (stringComp1 > vdocComp1) {
					Alignment esString = schemaStringAS.cut(Parameters.lingHighSim);
					GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
					gmo.match();
					Alignment escGmo = gmo.getClassAlignment();
					Alignment espGmo = gmo.getPropertyAlignment();
					Alignment esGmo = combine(escGmo, espGmo);
					gmoComp2 = comp3.estimate2(esString, esGmo);
					if (gmoComp2 == Parameters.highComp) {
						return esGmo;
					} else {
						return esString;
					}
				} else {
					Alignment esVDoc = schemaVDocAS.cut(Parameters.lingHighSim);
					GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
					gmo.match();
					Alignment escGmo = gmo.getClassAlignment();
					Alignment espGmo = gmo.getPropertyAlignment();
					Alignment esGmo = combine(escGmo, espGmo);
					gmoComp2 = comp3.estimate2(esVDoc, esGmo);
					if (gmoComp2 == Parameters.highComp) {
						return esGmo;
					} else {
						return esVDoc;
					}
				}
			} else if (vdocComp2 > Parameters.lowComp) {
				Alignment esVDoc = schemaVDocAS.cut(Parameters.lingLowSim);
				GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
				gmo.match();
				Alignment escGmo = gmo.getClassAlignment();
				Alignment espGmo = gmo.getPropertyAlignment();
				Alignment esGmo = combine(escGmo, espGmo);
				gmoComp2 = comp3.estimate2(esVDoc, esGmo);
				if (gmoComp2 == Parameters.highComp) {
					return esGmo;
				} else {
					return esVDoc;
				}
			} else {
				// System.out.println("The two ontologies are unmatchable!");
				return new Alignment();
			}
		} else {
			if (stringComp1 == Parameters.lowComp && vdocComp1 == Parameters.lowComp) {
				if (vdocComp2 > Parameters.lowComp) {
					return schemaVDocAS.cut(Parameters.lingLowSim);
				} else {
					// System.out.println("The two ontologies are
					// unmatchable!");
					return new Alignment();
				}
			} else {
				if (stringComp1 == vdocComp1) {
					NamedMatrix nmcVDoc = vdoc.getClassMatrix();
					NamedMatrix nmcString = string.getClassMatrix();
					NamedMatrix nmc = combine(nmcVDoc, nmcString);
					Alignment escCombine = AlignmentSelector.select(nmc, Parameters.lingHighSim);
					NamedMatrix nmpVDoc = vdoc.getPropertyMatrix();
					NamedMatrix nmpString = string.getPropertyMatrix();
					NamedMatrix nmp = combine(nmpVDoc, nmpString);
					Alignment espCombine = AlignmentSelector.select(nmp, Parameters.lingHighSim);
					return combine(escCombine, espCombine);
				} else if (stringComp1 > vdocComp1) {
					return schemaStringAS.cut(Parameters.lingHighSim);
				} else {
					return schemaVDocAS.cut(Parameters.lingHighSim);
				}
			}
		}
		*/
	}
	private Alignment runOld(String path, OntModel model1, OntModel model2) {
		RBGModel rbgmVDoc1 = RBGModelFactory.createModel("VDOC_MODEL");
		rbgmVDoc1.setOntModel(model1);
		RBGModel rbgmVDoc2 = RBGModelFactory.createModel("VDOC_MODEL");
		rbgmVDoc2.setOntModel(model2);
		VDocMatcher vdoc = new VDocMatcher(rbgmVDoc1, rbgmVDoc2);
		vdoc.match();
		Alignment vdocAS = vdoc.getAlignment();
		Alignment classVDocAS = vdoc.getClassAlignment();
		Alignment propertyVDocAS = vdoc.getPropertyAlignment();
		Alignment schemaVDocAS = combine(classVDocAS, propertyVDocAS);
		LingComp comp1 = new LingComp(rbgmVDoc1, rbgmVDoc2);
		vdocComp1 = comp1.estimate1(schemaVDocAS);
		vdocComp2 = comp1.estimate2(vdocAS);

		RBGModel rbgmString1 = RBGModelFactory.createModel("STRING_MODEL");
		rbgmString1.setOntModel(model1);
		RBGModel rbgmString2 = RBGModelFactory.createModel("STRING_MODEL");
		rbgmString2.setOntModel(model2);
		StringMatcher string = new StringMatcher(rbgmString1, rbgmString2);
		string.match();
		Alignment classStringAS = string.getClassAlignment();
		Alignment propertyStringAS = string.getPropertyAlignment();
		Alignment schemaStringAS = combine(classStringAS, propertyStringAS);
		LingComp comp2 = new LingComp(rbgmString1, rbgmString2);
		stringComp1 = comp2.estimate1(schemaStringAS);

		RBGModel rbgmGmo1 = RBGModelFactory.createModel("GMO_MODEL");
		rbgmGmo1.setOntModel(model1);
		RBGModel rbgmGmo2 = RBGModelFactory.createModel("GMO_MODEL");
		rbgmGmo2.setOntModel(model2);
		StruComp comp3 = new StruComp(rbgmGmo1, rbgmGmo2);
		gmoComp1 = comp3.estimate1();
		countNodesByCategory(rbgmGmo1, rbgmGmo2);
		if (gmoComp1 == Parameters.highComp) {
			if (stringComp1 == Parameters.highComp || vdocComp1 == Parameters.highComp) {
				if (stringComp1 == vdocComp1) {
					boolean flag1 = isAllFound(vdoc);
					boolean flag2 = isAllFound(string);
					if (flag1 == true && flag2 == true) {
						NamedMatrix nmcVDoc = vdoc.getClassMatrix();
						NamedMatrix nmcString = string.getClassMatrix();
						NamedMatrix nmc = combine(nmcVDoc, nmcString);
						Alignment escCombine = AlignmentSelector.select(nmc, Parameters.lingLowSim);
						NamedMatrix nmpVDoc = vdoc.getPropertyMatrix();
						NamedMatrix nmpString = string.getPropertyMatrix();
						NamedMatrix nmp = combine(nmpVDoc, nmpString);
						Alignment espCombine = AlignmentSelector.select(nmp, Parameters.lingLowSim);
						return combine(escCombine, espCombine);
					} else if (flag1 == true) {
						return schemaStringAS.cut(Parameters.lingLowSim);
					} else if (flag2 == true) {
						return schemaVDocAS.cut(Parameters.lingLowSim);
					} else {
						GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
						ExternalMatch ext = new ExternalMatch(rbgmGmo2, rbgmGmo1);
						NamedMatrix nmcVDoc = vdoc.getClassMatrix();
						NamedMatrix nmcString = string.getClassMatrix();
						NamedMatrix nmc = combine(nmcVDoc, nmcString);
						Alignment escCombine = AlignmentSelector.select(nmc, Parameters.lingLowSim);
						NamedMatrix nmpVDoc = vdoc.getPropertyMatrix();
						NamedMatrix nmpString = string.getPropertyMatrix();
						NamedMatrix nmp = combine(nmpVDoc, nmpString);
						Alignment espCombine = AlignmentSelector.select(nmp, Parameters.lingLowSim);
						NamedMatrix nmiVDoc = vdoc.getInstanceMatrix();
						NamedMatrix nmiString = string.getInstanceMatrix();
						NamedMatrix nmi = combine(nmiVDoc, nmiString);
						Alignment esiCombine = AlignmentSelector.select(nmi, Parameters.lingLowSim);
						ext.setClassSim(escCombine);
						ext.setPropertySim(espCombine);
						ext.setInstanceSim(esiCombine);
						gmo.setExternalMatch(ext);
						gmo.match();
						Alignment escGmo = gmo.getClassAlignment();
						Alignment espGmo = gmo.getPropertyAlignment();
						Alignment esGmo = combine(escGmo, espGmo);
						return combine(combine(escCombine, espCombine), esGmo);
					}
				} else if (stringComp1 > vdocComp1) {
					boolean flag = isAllFound(string);
					if (flag == true) {
						return schemaStringAS.cut(Parameters.lingLowSim);
					} else {
						GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
						ExternalMatch ext = new ExternalMatch(rbgmGmo2, rbgmGmo1);
						Alignment escString = classStringAS.cut(Parameters.lingLowSim);
						Alignment espString = propertyStringAS.cut(Parameters.lingLowSim);
						Alignment esiString = string.getInstanceAlignment().cut(Parameters.lingLowSim);
						ext.setClassSim(escString);
						ext.setPropertySim(espString);
						ext.setInstanceSim(esiString);
						gmo.setExternalMatch(ext);
						gmo.match();
						Alignment escGmo = gmo.getClassAlignment();
						Alignment espGmo = gmo.getPropertyAlignment();
						Alignment esGmo = combine(escGmo, espGmo);
						return combine(combine(escString, espString), esGmo);
					}
				} else {
					boolean flag = isAllFound(vdoc);
					if (flag == true) {
						return schemaVDocAS.cut(Parameters.lingLowSim);
					} else {
						GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
						ExternalMatch ext = new ExternalMatch(rbgmGmo2, rbgmGmo1);
						Alignment escVDoc = classVDocAS.cut(Parameters.lingLowSim);
						Alignment espVDoc = propertyVDocAS.cut(Parameters.lingLowSim);
						Alignment esiVDoc = vdoc.getInstanceAlignment().cut(Parameters.lingLowSim);
						ext.setClassSim(escVDoc);
						ext.setPropertySim(espVDoc);
						ext.setInstanceSim(esiVDoc);
						gmo.setExternalMatch(ext);
						gmo.match();
						Alignment escGmo = gmo.getClassAlignment();
						Alignment espGmo = gmo.getPropertyAlignment();
						Alignment esGmo = combine(escGmo, espGmo);
						return combine(combine(escVDoc, espVDoc), esGmo);
					}
				}
			} else if (stringComp1 == Parameters.mediumComp || vdocComp1 == Parameters.mediumComp) {
				if (stringComp1 == vdocComp1) {
					NamedMatrix nmcVDoc = vdoc.getClassMatrix();
					NamedMatrix nmcString = string.getClassMatrix();
					NamedMatrix nmc = combine(nmcVDoc, nmcString);
					Alignment escCombine = AlignmentSelector.select(nmc, Parameters.lingHighSim);
					NamedMatrix nmpVDoc = vdoc.getPropertyMatrix();
					NamedMatrix nmpString = string.getPropertyMatrix();
					NamedMatrix nmp = combine(nmpVDoc, nmpString);
					Alignment espCombine = AlignmentSelector.select(nmp, Parameters.lingHighSim);
					Alignment esCombine = combine(escCombine, espCombine);
					GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
					gmo.match();
					Alignment escGmo = gmo.getClassAlignment();
					Alignment espGmo = gmo.getPropertyAlignment();
					Alignment esGmo = combine(escGmo, espGmo);
					gmoComp2 = comp3.estimate2(esCombine, esGmo);
					if (gmoComp2 == Parameters.highComp) {
						return esGmo;
					} else {
						return esCombine;
					}
				} else if (stringComp1 > vdocComp1) {
					Alignment esString = schemaStringAS.cut(Parameters.lingHighSim);
					GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
					gmo.match();
					Alignment escGmo = gmo.getClassAlignment();
					Alignment espGmo = gmo.getPropertyAlignment();
					Alignment esGmo = combine(escGmo, espGmo);
					gmoComp2 = comp3.estimate2(esString, esGmo);
					if (gmoComp2 == Parameters.highComp) {
						return esGmo;
					} else {
						return esString;
					}
				} else {
					Alignment esVDoc = schemaVDocAS.cut(Parameters.lingHighSim);
					GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
					gmo.match();
					Alignment escGmo = gmo.getClassAlignment();
					Alignment espGmo = gmo.getPropertyAlignment();
					Alignment esGmo = combine(escGmo, espGmo);
					gmoComp2 = comp3.estimate2(esVDoc, esGmo);
					if (gmoComp2 == Parameters.highComp) {
						return esGmo;
					} else {
						return esVDoc;
					}
				}
			} else if (vdocComp2 > Parameters.lowComp) {
				Alignment esVDoc = schemaVDocAS.cut(Parameters.lingLowSim);
				GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
				gmo.match();
				Alignment escGmo = gmo.getClassAlignment();
				Alignment espGmo = gmo.getPropertyAlignment();
				Alignment esGmo = combine(escGmo, espGmo);
				gmoComp2 = comp3.estimate2(esVDoc, esGmo);
				if (gmoComp2 == Parameters.highComp) {
					return esGmo;
				} else {
					return esVDoc;
				}
			} else {
				// System.out.println("The two ontologies are unmatchable!");
				return new Alignment();
			}
		} else {
			if (stringComp1 == Parameters.lowComp && vdocComp1 == Parameters.lowComp) {
				if (vdocComp2 > Parameters.lowComp) {
					return schemaVDocAS.cut(Parameters.lingLowSim);
				} else {
					// System.out.println("The two ontologies are
					// unmatchable!");
					return new Alignment();
				}
			} else {
				if (stringComp1 == vdocComp1) {
					NamedMatrix nmcVDoc = vdoc.getClassMatrix();
					NamedMatrix nmcString = string.getClassMatrix();
					NamedMatrix nmc = combine(nmcVDoc, nmcString);
					Alignment escCombine = AlignmentSelector.select(nmc, Parameters.lingHighSim);
					NamedMatrix nmpVDoc = vdoc.getPropertyMatrix();
					NamedMatrix nmpString = string.getPropertyMatrix();
					NamedMatrix nmp = combine(nmpVDoc, nmpString);
					Alignment espCombine = AlignmentSelector.select(nmp, Parameters.lingHighSim);
					return combine(escCombine, espCombine);
				} else if (stringComp1 > vdocComp1) {
					return schemaStringAS.cut(Parameters.lingHighSim);
				} else {
					return schemaVDocAS.cut(Parameters.lingHighSim);
				}
			}
		}
	}
	private void countNodesByCategory(RBGModel modelA, RBGModel modelB) {
		for (Iterator<?> iter = modelA.listNodes(); iter.hasNext();) {
			Node node = (Node) iter.next();
			int category = NodeCategory.getCategoryWithoutExternal(node);
			if (category == Constant.ONTOLOGY_CLASS) {
				if (!node.isAnon()) {
					namedClassNumA++;
				}
			} else if (category == Constant.ONTOLOGY_PROPERTY) {
				if (!node.isAnon()) {
					namedPropertyNumA++;
				}
			} else if (category == Constant.ONTOLOGY_INSTANCE) {
				if (!node.isAnon()) {
					namedInstanceNumA++;
				}
			}
		}
		for (Iterator<?> iter = modelB.listNodes(); iter.hasNext();) {
			Node node = (Node) iter.next();
			int category = NodeCategory.getCategoryWithoutExternal(node);
			if (category == Constant.ONTOLOGY_CLASS) {
				if (!node.isAnon()) {
					namedClassNumB++;
				}
			} else if (category == Constant.ONTOLOGY_PROPERTY) {
				if (!node.isAnon()) {
					namedPropertyNumB++;
				}
			} else if (category == Constant.ONTOLOGY_INSTANCE) {
				if (!node.isAnon()) {
					namedInstanceNumB++;
				}
			}
		}
	}

	private boolean isAllFound(AbstractMatcher matcher) {
		if (Parameters.inclInstMatch == true) {
			int sizeA = namedClassNumA + namedPropertyNumA + namedInstanceNumA;
			int sizeB = namedClassNumB + namedPropertyNumB + namedInstanceNumB;
			int size = matcher.getAlignment().size(Parameters.lingLowSim);
			if (size == sizeA || size == sizeB) {
				return true;
			} else {
				return false;
			}
		} else {
			int sizeA = namedClassNumA + namedPropertyNumA;
			int sizeB = namedClassNumB + namedPropertyNumB;
			int size = matcher.getClassAlignment().size(Parameters.lingLowSim);
			size += matcher.getPropertyAlignment().size(Parameters.lingLowSim);
			if (size == sizeA || size == sizeB) {
				return true;
			} else {
				return false;
			}
		}
	}

	private Alignment combine(Alignment as1, Alignment as2) {
		Alignment alignSet = new Alignment();
		if (as1 != null) {
			for (int i = 0, n = as1.size(); i < n; i++) {
				alignSet.addMapping(as1.getMapping(i));
			}
		}
		if (as2 != null) {
			for (int i = 0, n = as2.size(); i < n; i++) {
				alignSet.addMapping(as2.getMapping(i));
			}
		}
		return alignSet;
	}

	private NamedMatrix combine(NamedMatrix matrix1, NamedMatrix matrix2) {
		if (matrix1.numRows() != matrix2.numRows()) {
			// System.err.println("combineError: Rows are not equal.");
			return null;
		} else if (matrix1.numColumns() != matrix2.numColumns()) {
			// System.err.println("combineError: Columns are not equal.");
			return null;
		} else {
			ArrayList<Object> rowList = matrix1.getRowList();
			ArrayList<Object> colList = matrix1.getColList();
			NamedMatrix matrix = new NamedMatrix(matrix1);
			for (int i = 0, m = rowList.size(); i < m; i++) {
				for (int j = 0, n = colList.size(); j < n; j++) {
					double temp = matrix2.get(rowList.get(i), colList.get(j));
					double value = Parameters.combWeight * matrix.get(i, j);
					value += (1 - Parameters.combWeight) * temp;
					matrix.set(i, j, value);
				}
			}
			return matrix;
		}
	}

	public ResultData evaluate(Alignment as, String fp1, String fp2, String fp4) {
		Alignment refSet = (new AlignmentReader2(fp1, fp2, fp4)).read();
		Evaluator evaluator = new Evaluator();
		return evaluator.compare(as, refSet);
	}

	public ResultData evaluate(Alignment as, String fp4) {
		AlignmentReader2 ar2 = new AlignmentReader2(model1, model2, fp4);
		Alignment rs = ar2.read();
		Evaluator evaluator = new Evaluator();
		return evaluator.compare(as, rs);
	}

	public ResultData evaluate(Alignment as) {
		String fp4 = Parameters.reference;
		AlignmentReader2 ar2 = new AlignmentReader2(model1, model2, fp4);
		Alignment rs = ar2.read();
		Evaluator evaluator = new Evaluator();
		return evaluator.compare(as, rs);
	}

	public String writeToFile(Alignment as, String fp3) {
		AlignmentWriter2 writer = new AlignmentWriter2(as, fp3);
		File file1 = new File(filepath1);
		File file2 = new File(filepath2);
		return writer.write(file1.getName(), file2.getName(), file1.getAbsolutePath(), file2.getAbsolutePath());
	}

	public String writeToFile(Alignment as) {
		AlignmentWriter2 writer = new AlignmentWriter2(as, Parameters.output);
		File file1 = new File(filepath1);
		File file2 = new File(filepath2);
		return writer.write(file1.getName(), file2.getName(), file1.getAbsolutePath(), file2.getAbsolutePath());
	}

	public String writeToFile(Alignment as, String fp3, String onto1, String onto2, String uri1, String uri2) {
		AlignmentWriter2 writer = new AlignmentWriter2(as, fp3);
		return writer.write(onto1, onto2, uri1, uri2);
	}

	public String writeToFile(Alignment as, String onto1, String onto2, String uri1, String uri2) {
		AlignmentWriter2 writer = new AlignmentWriter2(as, Parameters.output);
		return writer.write(onto1, onto2, uri1, uri2);
	}

	public Alignment getAlignment(String path) {
		if (alignRes != null)
			return alignRes;
		Alignment alignment = run(path);
		return alignment;
	}

	public static void main(String args[]) {
		// System.out.println(Calendar.getInstance().getTime().toString() +
		// "\n");

		String NumCHMatchFile1 = "1";
		String NumCHMatchFile2 = "1";
		Configuration config = new Configuration();
		config.init();

		// String onto = "202";
		String fp1 = "C:/Users/Samira/Desktop/HolisticDataSet/d11/originalFiles/bco.owl";// "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d11\\originalFiles\\bco.owl";
		String fp2 = "C:/Users/Samira/Desktop/HolisticDataSet/d11/originalFiles/apo.owl";
		String path = "C:/Users/Samira/Desktop/HolisticDataSet/d11/originalFiles//";
		// String fp3 = "D:/test_ont/test/cmt.owl";
		// String fp4 = "D:/test_ont/test/ekaw.owl";

		// Controller controller = new Controller();
		Matching controller = new Matching(fp1, fp2, NumCHMatchFile1, NumCHMatchFile2);
		Alignment alignment = controller.run(path);
		controller.writeToFile(alignment);
		controller.writeToFile(alignment, fp1);
		if (Parameters.reference != null) {
			controller.evaluate(alignment, fp2);
		}

		// System.out.println("\n" +
		// Calendar.getInstance().getTime().toString()+"\t"+alignment.size());
	}
}
