package fusion.comerger.algorithm.merger.holisticMerge.mapping;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;
import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.model.HModel;

import org.semanticweb.owlapi.vocab.SKOSVocabulary;

public class HMapping {
	public static void main(String[] args) throws Exception {
		String ontList = "C:\\YOUR_LOCAL_FOLDER\\cmt.owl";
		ontList = ontList + ";" + "C:\\YOUR_LOCAL_FOLDER\\conference.owl";
		ontList = ontList + ";" + "C:\\YOUR_LOCAL_FOLDER\\confOf.owl";

		String mapList = "C:\\YOUR_LOCAL_FOLDER\\cmt-conference.rdf";
		mapList = mapList + ";" + "C:\\YOUR_LOCAL_FOLDER\\cmt-confOf.rdf";

		HModel ontM = new HModel();
		HMapping om = new HMapping();

		// find mapping
		om.run(ontM, mapList);
		System.out.println("Done!");

	}

	public HModel run(HModel ontM, String alignF) {
		long startTime = System.currentTimeMillis();
		ArrayList<HMappedClass> RefClass = new ArrayList<HMappedClass>();
		ArrayList<HMappedObj> RefObjPro = new ArrayList<HMappedObj>();
		ArrayList<HMappedDpro> RefDataPro = new ArrayList<HMappedDpro>();

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = ontM.getOwlModel();
		OWLDataFactory factory = manager.getOWLDataFactory();

		Set<OWLClass> tempClass = new HashSet<OWLClass>();
		Set<OWLObjectProperty> tempObjPro = new HashSet<OWLObjectProperty>();
		Set<OWLDataProperty> tempDataPro = new HashSet<OWLDataProperty>();
		HMappedClass HtempClass = new HMappedClass();
		HMappedObj HtempObjPro = new HMappedObj();
		HMappedDpro HtempDataPro = new HMappedDpro();

		if (alignF != null) {
			String[] nm = alignF.split(";");
			for (int ontID = 0; ontID < nm.length; ontID++) {
				if (!nm[ontID].toString().equals("null") && nm[ontID].toString().length() > 1) {
					String OntName = nm[ontID];
					try {
						File file = new File(OntName);
						SAXReader reader = new SAXReader();
						Document doc = reader.read(file);
						Element root = doc.getRootElement();
						Element align = root.element("Alignment");
						Iterator<?> map = align.elementIterator("map");
						if (!map.hasNext()) {
							// return null;
						}

						while (map.hasNext()) {
							Element cell = ((Element) map.next()).element("Cell");
							if (cell == null) {
								continue;
							}
							String s1 = cell.element("entity1").attributeValue("resource");
							String s2 = cell.element("entity2").attributeValue("resource");

							// TODO: delete this condition
							if ((ontology.containsClassInSignature(IRI.create(s1))
									&& ontology.containsClassInSignature(IRI.create(s2)))
									|| findClassInOS(ontM, s1, s2)) {

								OWLClass cls1 = factory.getOWLClass(IRI.create(s1));
								OWLClass cls2 = factory.getOWLClass(IRI.create(s2));
								if (cls1 == null || cls2 == null) {
									System.err.println("readError: Cannot find such entity.");
									continue;
								}
								// double sim =
								// Double.parseDouble(cell.elementText("measure"));
								// String rel = cell.elementText("relation");
								ArrayList<HMappedClass> check = CheckNotExistClass(RefClass, cls1, cls2);
								if (check.size() == 0) {
									tempClass = new HashSet<OWLClass>();
									HtempClass = new HMappedClass();
									tempClass.add(cls1);
									tempClass.add(cls2);
									HtempClass.setMappedClass(tempClass);
									HtempClass.setLenClass(2);
									RefClass.add(RefClass.size(), HtempClass);
								} else {
									HtempClass = new HMappedClass();
									for (int c = 0; c < check.size(); c++) {
										HMappedClass tem = check.get(c);
										RefClass.remove(tem);
										HtempClass.getMappedCalss().addAll(tem.getMappedCalss());
										HtempClass.getMappedCalss().add(cls1);
										HtempClass.getMappedCalss().add(cls2);
									}
									HtempClass.setLenClass(HtempClass.getMappedCalss().size());
									RefClass.add(RefClass.size(), HtempClass);

								}
							} else if ((ontology.containsObjectPropertyInSignature(IRI.create(s1))
									&& ontology.containsObjectPropertyInSignature(IRI.create(s2)))
									|| findObjInOS(ontM, s1, s2)) {
								OWLObjectProperty pro1 = factory.getOWLObjectProperty(IRI.create(s1));
								OWLObjectProperty pro2 = factory.getOWLObjectProperty(IRI.create(s2));
								if (pro1 == null || pro2 == null) {
									System.err.println("readError: Cannot find such entity.");
									continue;
								}
								// double sim =
								// Double.parseDouble(cell.elementText("measure"));
								// String rel = cell.elementText("relation");
								ArrayList<HMappedObj> check = CheckNotExistObj(RefObjPro, pro1, pro2);
								if (check.size() == 0) {
									tempObjPro = new HashSet<OWLObjectProperty>();
									HtempObjPro = new HMappedObj();
									tempObjPro.add(pro1);
									tempObjPro.add(pro2);
									HtempObjPro.setMappedObj(tempObjPro);
									HtempObjPro.setLenObj(2);
									RefObjPro.add(RefObjPro.size(), HtempObjPro);
								} else {
									HtempObjPro = new HMappedObj();
									for (int c = 0; c < check.size(); c++) {
										HMappedObj tem = check.get(c);
										RefObjPro.remove(tem);
										HtempObjPro.getMappedObj().addAll(tem.getMappedObj());
										HtempObjPro.getMappedObj().add(pro1);
										HtempObjPro.getMappedObj().add(pro2);
									}
									HtempObjPro.setLenObj(HtempClass.getMappedCalss().size());
									RefObjPro.add(RefObjPro.size(), HtempObjPro);
								}

							} else if ((ontology.containsDataPropertyInSignature(IRI.create(s1))
									&& ontology.containsDataPropertyInSignature(IRI.create(s2)))
									|| findDproInOS(ontM, s1, s2)) {
								OWLDataProperty pro1 = factory.getOWLDataProperty(IRI.create(s1));
								OWLDataProperty pro2 = factory.getOWLDataProperty(IRI.create(s2));
								if (pro1 == null || pro2 == null) {
									System.err.println("readError: Cannot find such entity.");
									continue;
								}
								// double sim =
								// Double.parseDouble(cell.elementText("measure"));
								// String rel = cell.elementText("relation");
								ArrayList<HMappedDpro> check = CheckNotExistDp(RefDataPro, pro1, pro2);
								if (check.size() == 0) {
									tempDataPro = new HashSet<OWLDataProperty>();
									HtempDataPro = new HMappedDpro();
									tempDataPro.add(pro1);
									tempDataPro.add(pro2);
									HtempDataPro.setMappedDpro(tempDataPro);
									HtempDataPro.setLenDpro(2);
									RefDataPro.add(RefDataPro.size(), HtempDataPro);
								} else {
									HtempDataPro = new HMappedDpro();
									for (int c = 0; c < check.size(); c++) {
										HMappedDpro tem = check.get(c);
										RefDataPro.remove(tem);
										HtempDataPro.getMappedDpro().addAll(tem.getMappedDpro());
										HtempDataPro.getMappedDpro().add(pro1);
										HtempDataPro.getMappedDpro().add(pro2);
									}
									HtempDataPro.setLenDpro(HtempClass.getMappedCalss().size());
									RefDataPro.add(RefDataPro.size(), HtempDataPro);
								}
							}
						}

					} catch (DocumentException e) {
						e.printStackTrace();
						MyLogging.log(Level.SEVERE, "Exception from reading corresponding entities: " + e.toString());

					}
				}

			}
		}
		// Determine the reference_class and create a key value of them
		ontM = determineRefClass(ontM, RefClass);
		ontM = determineRefObjPro(ontM, RefObjPro);
		ontM = determineRefDataPro(ontM, RefDataPro);

		// if we need only to print pair of corresponding entities
		// printCorr(RefClass,RefObjPro,RefDataPro);

		ontM.SetEqClasses(RefClass);
		ontM.SetEqObjProperties(RefObjPro);
		ontM.SetEqDataProperties(RefDataPro);

		String collapsInfo = RefClass.size() + " classes have been collapsed; \t " + RefObjPro.size()
				+ " object properties have been collapsed; \t" + RefDataPro.size()
				+ " datatype properties have been collapsed. \n";
		MyLogging.log(Level.INFO, collapsInfo);

		StatisticTest.result.put("corresponding_Class", String.valueOf(RefClass.size()));
		StatisticTest.result.put("corresponding_object_properties", String.valueOf(RefObjPro.size()));
		StatisticTest.result.put("corresponding_data_properties", String.valueOf(RefDataPro.size()));

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		MyLogging.log(Level.INFO,
				"Total time for reading and parsing the corresponding entities between the input ontologies is: "
						+ elapsedTime + " ms. \n");
		MyLogging.log(Level.INFO,
				"Number of corresponding classes: " + RefClass.size()
						+ ", \t Number of corresponding object properties: " + RefObjPro.size()
						+ ", \t Number of corresponding dataproperties: " + RefDataPro.size() + ". \n");

		StatisticTest.result.put("time_reading_alignment", String.valueOf(elapsedTime));

		return ontM;

	}

	private boolean findClassInOS(HModel ontM, String s1, String s2) {
		boolean first = false, second = false;
		for (OWLOntology os : ontM.getInputOwlOntModel()) {
			if (os.containsClassInSignature(IRI.create(s1)))
				first = true;

			if (os.containsClassInSignature(IRI.create(s2)))
				second = true;
		}
		if (first && second)
			return true;

		return false;
	}

	private boolean findObjInOS(HModel ontM, String s1, String s2) {

		boolean first = false, second = false;
		for (OWLOntology os : ontM.getInputOwlOntModel()) {
			if (os.containsObjectPropertyInSignature(IRI.create(s1)))
				first = true;

			if (os.containsObjectPropertyInSignature(IRI.create(s2)))
				second = true;
		}
		if (first && second)
			return true;
		
		return false;
	}

	private boolean findDproInOS(HModel ontM, String s1, String s2) {

		boolean first = false, second = false;
		for (OWLOntology os : ontM.getInputOwlOntModel()) {
			if (os.containsDataPropertyInSignature(IRI.create(s1)))
				first = true;

			if (os.containsDataPropertyInSignature(IRI.create(s2)))
				second = true;
		}
		if (first && second)
			return true;
		return false;
	}

	private void printCorr(ArrayList<HMappedClass> refClass, ArrayList<HMappedObj> refObjPro,
			ArrayList<HMappedDpro> refDataPro) {
		for (int i = 0; i < refClass.size(); i++) {
			String refC = refClass.get(i).getRefClass().toString();
			Iterator<OWLClass> cc = refClass.get(i).getMappedCalss().iterator();
			while (cc.hasNext()) {
				String c = cc.next().toString();
				System.out.println(c + "," + refC);
			}
		}
		for (int i = 0; i < refObjPro.size(); i++) {
			String refC = refObjPro.get(i).getRefObj().toString();
			Iterator<OWLObjectProperty> cc = refObjPro.get(i).getMappedObj().iterator();
			while (cc.hasNext()) {
				String c = cc.next().toString();
				System.out.println(c + "," + refC);
			}
		}
		for (int i = 0; i < refDataPro.size(); i++) {
			String refC = refDataPro.get(i).getRefDpro().toString();
			Iterator<OWLDataProperty> cc = refDataPro.get(i).getMappedDpro().iterator();
			while (cc.hasNext()) {
				String c = cc.next().toString();
				System.out.println(c + "," + refC);
			}
		}
	}

	private HModel determineRefDataPro(HModel ontM, ArrayList<HMappedDpro> cList) {
		HashMap<OWLDataProperty, OWLDataProperty> keyValue = new HashMap<OWLDataProperty, OWLDataProperty>();
		for (int i = 0; i < cList.size(); i++) {
			OWLDataProperty c = findRefDataPro(ontM, cList.get(i).getMappedDpro());
			cList.get(i).setRefDpro(c);
			ontM = addAllLabelsToDataPro(ontM, cList.get(i).getMappedDpro(), c);

			Iterator<OWLDataProperty> iter = cList.get(i).getMappedDpro().iterator();
			while (iter.hasNext())
				keyValue.put(iter.next(), cList.get(i).getRefDpro());
		}
		ontM.setKeyValueEqDataPro(keyValue);
		ontM.SetEqDataProperties(cList);
		return ontM;
	}

	private HModel determineRefObjPro(HModel ontM, ArrayList<HMappedObj> cList) {
		HashMap<OWLObjectProperty, OWLObjectProperty> keyValue = new HashMap<OWLObjectProperty, OWLObjectProperty>();
		for (int i = 0; i < cList.size(); i++) {
			OWLObjectProperty c = findRefObjPro(ontM, cList.get(i).getMappedObj());
			cList.get(i).setRefObj(c);
			ontM = addAllLabelsToObjProperties(ontM, cList.get(i).getMappedObj(), c);

			Iterator<OWLObjectProperty> iter = cList.get(i).getMappedObj().iterator();
			while (iter.hasNext())
				keyValue.put(iter.next(), cList.get(i).getRefObj());
		}
		ontM.setKeyValueEqObjProperty(keyValue);
		ontM.SetEqObjProperties(cList);
		return ontM;
	}

	private HModel determineRefClass(HModel ontM, ArrayList<HMappedClass> cList) {
		HashMap<OWLClass, OWLClass> keyValue = new HashMap<OWLClass, OWLClass>();
		for (int i = 0; i < cList.size(); i++) {
			OWLClass c = findRefClass(ontM, cList.get(i).getMappedCalss());
			cList.get(i).setRefClass(c);
			ontM = addAllLabelsToClass(ontM, cList.get(i).getMappedCalss(), c);

			Iterator<OWLClass> iter = cList.get(i).getMappedCalss().iterator();
			while (iter.hasNext())
				keyValue.put(iter.next(), cList.get(i).getRefClass());

		}
		ontM.setKeyValueEqClass(keyValue);
		ontM.SetEqClasses(cList);
		return ontM;
	}

	private HModel addAllLabelsToClass(HModel ontM, Set<OWLClass> cList, OWLClass c) {
		OWLOntology ont = ontM.getOwlModel();
		OWLOntologyManager manager = ont.getOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();

		OWLAnnotationProperty labels = factory.getRDFSLabel();

		Iterator<OWLClass> iter = cList.iterator();
		while (iter.hasNext()) {
			OWLClass cc = iter.next();
			IRI iri = cc.getIRI();

			OWLAnnotation pA1 = factory.getOWLAnnotation(labels, iri);
			OWLAnnotationAssertionAxiom myAxiom1 = factory.getOWLAnnotationAssertionAxiom(c.getIRI(), pA1);
			manager.addAxiom(ont, myAxiom1);
		}

		ontM.SetManager(manager);
		ontM.SetOwlModel(ont);
		return ontM;
	}

	private HModel addAllLabelsToObjProperties(HModel ontM, Set<OWLObjectProperty> set, OWLObjectProperty c) {
		OWLOntology ont = ontM.getOwlModel();
		OWLOntologyManager manager = ont.getOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();

		OWLAnnotationProperty labels = factory.getRDFSLabel();

		Iterator<OWLObjectProperty> iter = set.iterator();
		while (iter.hasNext()) {
			OWLObjectProperty cc = iter.next();
			IRI iri = cc.getIRI();

			OWLAnnotation pA1 = factory.getOWLAnnotation(labels, iri);
			OWLAnnotationAssertionAxiom myAxiom1 = factory.getOWLAnnotationAssertionAxiom(c.getIRI(), pA1);
			manager.addAxiom(ont, myAxiom1);
		}

		ontM.SetManager(manager);
		ontM.SetOwlModel(ont);
		return ontM;
	}

	private HModel addAllLabelsToDataPro(HModel ontM, Set<OWLDataProperty> set, OWLDataProperty c) {
		OWLOntology ont = ontM.getOwlModel();
		OWLOntologyManager manager = ont.getOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();

		OWLAnnotationProperty labels = factory.getRDFSLabel();

		Iterator<OWLDataProperty> iter = set.iterator();
		while (iter.hasNext()) {
			OWLDataProperty cc = iter.next();
			IRI iri = cc.getIRI();

			OWLAnnotation pA1 = factory.getOWLAnnotation(labels, iri);
			OWLAnnotationAssertionAxiom myAxiom1 = factory.getOWLAnnotationAssertionAxiom(c.getIRI(), pA1);
			manager.addAxiom(ont, myAxiom1);
		}

		ontM.SetManager(manager);
		ontM.SetOwlModel(ont);
		return ontM;
	}

	private OWLClass findRefClass(HModel ontM, Set<OWLClass> cList) {
		String pOnt = ontM.getPreferedOnt();
		Iterator<OWLClass> citer = cList.iterator();
		while (citer.hasNext()) {
			OWLClass p = citer.next();
			if (p.getIRI().getNamespace().equals(pOnt))
				return p;
		}

		// if nothing return, means 1- they are equal or 2- the mapped pair does
		// not belong to preferred ontology
		String res = LabelIdentifier.createLabelClass(cList);
		OWLClass refClass = ontM.getManager().getOWLDataFactory().getOWLClass(IRI.create("http://merged#" + res));

		return refClass;

	}

	private OWLObjectProperty findRefObjPro(HModel ontM, Set<OWLObjectProperty> cList) {
		String pOnt = ontM.getPreferedOnt();
		Iterator<OWLObjectProperty> citer = cList.iterator();
		while (citer.hasNext()) {
			OWLObjectProperty p = citer.next();
			if (p.getIRI().getNamespace().equals(pOnt))
				return p;
		}

		// if nothing return, means 1- they are equal or 2- the mapped pair does
		// not belong to preferred ontology

		String res = LabelIdentifier.createLabelObjPro(cList);
		OWLObjectProperty refClass = ontM.getManager().getOWLDataFactory()
				.getOWLObjectProperty(IRI.create("http://merged#" + res));
		return refClass;

	}

	private String[] convertToStringObjPro(Set<OWLObjectProperty> cList) {
		String[] sList = new String[cList.size()];
		int i = 0;
		Iterator<OWLObjectProperty> iterList = cList.iterator();
		while (iterList.hasNext()) {
			OWLObjectProperty c = iterList.next();
			sList[i] = c.getIRI().getFragment().toString().toLowerCase();
			sList[i] = deleteUnChar(sList[i]);
			i++;
		}
		return sList;
	}

	private String deleteUnChar(String s) {
		s = s.replaceAll("__", "_");
		if (s == null || s.length() < 1) {
			return s;
		}
		if (s.substring(0, 1).equals("_"))
			s = s.substring(1);
		if (s.substring(s.length() - 1).equals("_"))
			s = s.substring(0, s.length() - 1);
		int index1 = s.indexOf("_");
		while (index1 >= 0) {
			StringBuilder sb = new StringBuilder(s);
			sb.deleteCharAt(sb.indexOf("_"));
			s = sb.toString();
			s = s.substring(0, index1) + s.substring(index1, index1 + 1).toUpperCase() + s.substring(index1 + 1);
			index1 = s.indexOf("_", index1 + 1);
		}
		return s;
	}

	private OWLDataProperty findRefDataPro(HModel ontM, Set<OWLDataProperty> cList) {
		String pOnt = ontM.getPreferedOnt();
		Iterator<OWLDataProperty> citer = cList.iterator();
		while (citer.hasNext()) {
			OWLDataProperty p = citer.next();
			if (p.getIRI().getNamespace().equals(pOnt))
				return p;
		}

		// if nothing return, means 1- they are equal or 2- the mapped pair does
		// not belong to preferred ontology

		String res = LabelIdentifier.createLabelDPro(cList);
		OWLDataProperty refClass = ontM.getManager().getOWLDataFactory()
				.getOWLDataProperty(IRI.create("http://merged#" + res));
		return refClass;

	}

	private String[] convertToStringDataPro(Set<OWLDataProperty> cList) {
		String[] sList = new String[cList.size()];
		int i = 0;
		Iterator<OWLDataProperty> iterList = cList.iterator();
		while (iterList.hasNext()) {
			OWLDataProperty c = iterList.next();
			sList[i] = c.getIRI().getFragment().toString().toLowerCase();
			sList[i] = deleteUnChar(sList[i]);
			i++;
		}
		return sList;
	}

	private String[] convertToStringClass(Set<OWLClass> cList) {
		String[] sList = new String[cList.size()];
		int i = 0;
		Iterator<OWLClass> iterList = cList.iterator();
		while (iterList.hasNext()) {
			OWLClass c = iterList.next();
			sList[i] = c.getIRI().getFragment().toString();
			if (sList[i].trim().length() > 0)
				sList[i] = deleteUnChar(sList[i]);
			i++;
		}
		return sList;
	}

	private ArrayList<HMappedDpro> CheckNotExistDp(ArrayList<HMappedDpro> refDataPro, OWLDataProperty pro1,
			OWLDataProperty pro2) {
		ArrayList<HMappedDpro> idx = new ArrayList<HMappedDpro>();
		for (int i = 0; i < refDataPro.size(); i++) {
			Set<OWLDataProperty> a = refDataPro.get(i).getMappedDpro();
			if (a.contains(pro1) || a.contains(pro2))
				idx.add(refDataPro.get(i));
		}
		return idx;
	}

	private ArrayList<HMappedObj> CheckNotExistObj(ArrayList<HMappedObj> refObjPro, OWLObjectProperty pro1,
			OWLObjectProperty pro2) {
		ArrayList<HMappedObj> idx = new ArrayList<HMappedObj>();
		for (int i = 0; i < refObjPro.size(); i++) {
			Set<OWLObjectProperty> a = refObjPro.get(i).getMappedObj();
			if (a.contains(pro1) || a.contains(pro2))
				idx.add(refObjPro.get(i));
		}
		return idx;
	}

	private ArrayList<HMappedClass> CheckNotExistClass(ArrayList<HMappedClass> refClass, OWLClass cls1, OWLClass cls2) {
		ArrayList<HMappedClass> idx = new ArrayList<HMappedClass>();
		for (int i = 0; i < refClass.size(); i++) {
			Set<OWLClass> a = refClass.get(i).getMappedCalss();
			if (a.contains(cls1) || a.contains(cls2))
				idx.add(refClass.get(i));
		}

		return idx;
	}
}
