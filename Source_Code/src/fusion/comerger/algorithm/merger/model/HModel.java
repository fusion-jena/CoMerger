package fusion.comerger.algorithm.merger.model;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import fusion.comerger.algorithm.merger.holisticMerge.divideConquer.BlockModel;
import fusion.comerger.algorithm.merger.holisticMerge.consistency.ErrornousAxioms;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedClass;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedDpro;
import fusion.comerger.algorithm.merger.holisticMerge.mapping.HMappedObj;

public class HModel {

	OWLOntology OwlOnt = null;
	ArrayList<BlockModel> clusterList;
	OWLOntologyManager MergedManager;
	OWLOntologyID currentOntologyID;
	ArrayList<HMappedClass> EqClass;
	ArrayList<HMappedObj> EqObjPro;
	ArrayList<HMappedDpro> EqDataPro;
	String PreferedOnt = "ont1";
	String ontName = "";
	String ontZipName = "";
	String listOntName = "";
	String path;
	boolean CheckBuildModel = false;
	ArrayList<OWLClass> RefineClass;
	int InputOntNumber;
	ArrayList<Integer> inputOntClassSize;
	ArrayList<Integer> inputOntObjectSize;
	ArrayList<Integer> inputOntDataSize;
	ArrayList<Integer> inputOntInstanceSize;
	ArrayList<Integer> inputOntAnnoSize;
	ArrayList<OWLOntology> inputOwlOnt;
	HashMap<OWLAxiom, OWLAxiom> eqAxioms = new HashMap<OWLAxiom, OWLAxiom>();
	Set<OWLAxiom> Anno;
	int inputClassSize;
	int inputPropertySize;
	String[] EvalResult;
	String[] EvalGMRResult;
	String[] consResult;
	String[] reviseResult;
	String evalResultTxt;
	String consistencyResultTxt;
	IRI ontIRI;
	ArrayList<ErrornousAxioms> errorAxioms;
	Map<String, OWLAxiom> SuggestAxiom;
	String MergeOutputType;
	HashMap<OWLClass, OWLClass> keyValueCl;
	HashMap<OWLObjectProperty, OWLObjectProperty> keyValueObj;
	HashMap<OWLDataProperty, OWLDataProperty> keyValueDpro;
	int numRewAx;
	HashSet<OWLAxiom> isaBreakingAxioms;
	HashSet<OWLAxiom> otherBreakingAxioms;
	int refineActionCluster;
	int refineActionMerge;
	boolean alter = false;
	HashMap<OWLClassExpression, HashSet<OWLClassExpression>> listParent = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();
	HashMap<OWLClassExpression, HashSet<OWLClassExpression>> listChild = new HashMap<OWLClassExpression, HashSet<OWLClassExpression>>();
	ArrayList<String> subOnt;
	String subOntZip;
	String dimensionLabel;
	HashMap<String, String> evalHash;

	public int getInputClassSizeTotal() {
		return inputClassSize;
	}

	public void SetInputClassSizeTotal(int siz) {
		inputClassSize = siz;
	}

	public int getInputPropertySizeTotal() {
		return inputPropertySize;
	}

	public void SetInputPropertySizeTotal(int siz) {
		inputPropertySize = siz;
	}

	public Set<OWLAxiom> getAllAnnotations() {
		return Anno;
	}

	public void SetAllAnnotations(Set<OWLAxiom> a) {
		Anno = a;
	}

	public int getInputOntNumber() {
		return InputOntNumber;
	}

	public void SetInputOntNumber(int n) {
		InputOntNumber = n;
	}

	public ArrayList<Integer> getInputClassSize() {
		return inputOntClassSize;
	}

	public void SetInputClassSize(ArrayList<Integer> clSiz) {
		inputOntClassSize = clSiz;
	}

	public ArrayList<Integer> getInputObjectSize() {
		return inputOntObjectSize;
	}

	public void SetInputObjectSize(ArrayList<Integer> clSiz) {
		inputOntObjectSize = clSiz;
	}

	public ArrayList<Integer> getInputDataProSize() {
		return inputOntDataSize;
	}

	public void SetInputDataProSize(ArrayList<Integer> clSiz) {
		inputOntDataSize = clSiz;
	}

	public ArrayList<Integer> getInputInstanceSize() {
		return inputOntInstanceSize;
	}

	public void SetInputInstanceSize(ArrayList<Integer> clSiz) {
		inputOntInstanceSize = clSiz;
	}

	public ArrayList<Integer> getInputAnnoSize() {
		return inputOntAnnoSize;
	}

	public void SetInputAnnoSize(ArrayList<Integer> clSiz) {
		inputOntAnnoSize = clSiz;
	}

	public ArrayList<OWLClass> getRefineClasses() {
		return RefineClass;
	}

	public void SetRefineClasses(ArrayList<OWLClass> cl) {
		RefineClass = cl;
	}

	public OWLOntology getOwlModel() {
		return OwlOnt;
	}

	public void SetOwlModel(OWLOntology ontmodel) {
		OwlOnt = ontmodel;
	}

	public OWLOntologyID getOntID() {
		return currentOntologyID;
	}

	public void SetOntID(OWLOntologyID id) {
		currentOntologyID = id;
	}

	public ArrayList<HMappedClass> getEqClasses() {
		return EqClass;
	}

	public void SetEqClasses(ArrayList<HMappedClass> EqCl) {
		EqClass = EqCl;
	}

	public ArrayList<HMappedObj> getEqObjProperties() {
		return EqObjPro;
	}

	public void SetEqObjProperties(ArrayList<HMappedObj> EqCl) {
		EqObjPro = EqCl;
	}

	public ArrayList<HMappedDpro> getEqDataProperties() {
		return EqDataPro;
	}

	public void SetEqDataProperties(ArrayList<HMappedDpro> EqCl) {
		EqDataPro = EqCl;
	}

	public void SetPreferedOnt(String po) {
		PreferedOnt = po;
	}

	public String getPreferedOnt() {
		return PreferedOnt;
	}

	public String getOntName() {
		return ontName;
	}

	public void setOntName(String ontname) {
		ontName = ontname;
	}

	public String getOntZipName() {
		return ontZipName;
	}

	public void setOntZipName(String ontname) {
		ontZipName = ontname;
	}

	public String getlistOntName() {
		return listOntName;
	}

	public void setlistOntName(String listontname) {
		listOntName = listontname;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String addr) {
		path = addr;
	}

	public void setCheckBuildModel(boolean check) {
		CheckBuildModel = check;
	}

	public boolean getCheckBuildModel() {
		return CheckBuildModel;
	}

	public void SetManager(OWLOntologyManager mergedManager2) {
		MergedManager = mergedManager2;
	}

	public OWLOntologyManager getManager() {
		return MergedManager;
	}

	public void SetClusters(ArrayList<BlockModel> cl) {
		clusterList = cl;
	}

	public ArrayList<BlockModel> getClusters() {
		return clusterList;
	}

	public void setEvalResult(String[] res) {
		EvalResult = res;
	}

	public String[] getEvalResult() {
		return EvalResult;
	}

	public void setEvalGMRResult(String[] res) {
		EvalGMRResult = res;
	}

	public String[] getEvalGMRResult() {
		return EvalGMRResult;
	}

	public void setConsResult(String[] res) {
		consResult = res;
	}

	public String[] getConsResult() {
		return consResult;
	}

	public void setOntIRI(IRI iri) {
		ontIRI = iri;
	}

	public IRI getOntIRI() {
		return ontIRI;
	}

	public void setReviseResult(String[] res) {
		reviseResult = res;
	}

	public String[] getReviseResult() {
		return reviseResult;
	}

	public void setErrorAxioms(ArrayList<ErrornousAxioms> allErrAx) {
		errorAxioms = allErrAx;
	}

	public ArrayList<ErrornousAxioms> getErrorAxioms() {
		return errorAxioms;
	}

	public void setSuggestedAxioms(Map<String, OWLAxiom> suggestAx) {
		SuggestAxiom = suggestAx;
	}

	public Map<String, OWLAxiom> getSuggestedAxioms() {
		return SuggestAxiom;
	}

	public void setInputOwlOntModel(ArrayList<OWLOntology> inputOWLmodels) {
		inputOwlOnt = inputOWLmodels;
	}

	public ArrayList<OWLOntology> getInputOwlOntModel() {
		return inputOwlOnt;
	}

	public void setEqAxioms(HashMap<OWLAxiom, OWLAxiom> eqAx) {
		eqAxioms = eqAx;
	}

	public HashMap<OWLAxiom, OWLAxiom> getEqAxioms() {
		return eqAxioms;
	}

	public String getEvalResultTxt() {
		return evalResultTxt;
	}

	public void setEvalResultTxt(String txtFileName) {
		evalResultTxt = txtFileName;
	}

	public String getConsistencyResultTxt() {
		return consistencyResultTxt;
	}

	public void setConsistencyResultTxt(String txtFileName) {
		consistencyResultTxt = txtFileName;
	}

	public void setMergeOutputType(String type) {
		MergeOutputType = type;
	}

	public String getMergeOutputType() {
		return MergeOutputType;
	}

	public void setKeyValueEqClass(HashMap<OWLClass, OWLClass> kvCl) {
		keyValueCl = kvCl;
	}

	public HashMap<OWLClass, OWLClass> getKeyValueEqClass() {
		return keyValueCl;
	}

	public void setKeyValueEqObjProperty(HashMap<OWLObjectProperty, OWLObjectProperty> kvObj) {
		keyValueObj = kvObj;
	}

	public HashMap<OWLObjectProperty, OWLObjectProperty> getKeyValueEqObjProperty() {
		return keyValueObj;
	}

	public void setKeyValueEqDataPro(HashMap<OWLDataProperty, OWLDataProperty> kvDpro) {
		keyValueDpro = kvDpro;
	}

	public HashMap<OWLDataProperty, OWLDataProperty> getKeyValueEqDataPro() {
		return keyValueDpro;
	}

	public int getNumRewriteAxioms() {
		return numRewAx;
	}

	public void setNumRewriteAxioms(int r) {
		numRewAx = r;
	}

	public HashSet<OWLAxiom> getISABreakingAxiom() {
		return isaBreakingAxioms;
	}

	public void SetISABreakingAxiom(HashSet<OWLAxiom> brAx) {
		isaBreakingAxioms = brAx;

	}

	public HashSet<OWLAxiom> getOtherBreakingAxiom() {
		return otherBreakingAxioms;
	}

	public void SetOtherBreakingAxiom(HashSet<OWLAxiom> brAx) {
		otherBreakingAxioms = brAx;

	}

	public int getRefineActionOnCluster() {
		return refineActionCluster;
	}

	public void setRefineActionOnCluster(int act) {
		refineActionCluster = act;
	}

	public int getRefineActionOnMerge() {
		return refineActionMerge;
	}

	public void setRefineActionOnMerge(int act) {
		refineActionMerge = act;
	}

	public boolean getAlterStatus() {
		return alter;
	}

	public void setAlterStatus(boolean a) {
		alter = a;
	}

	public HashMap<OWLClassExpression, HashSet<OWLClassExpression>> getParentList() {
		return listParent;
	}

	public void setParentList(HashMap<OWLClassExpression, HashSet<OWLClassExpression>> p) {
		listParent = p;
	}

	public HashMap<OWLClassExpression, HashSet<OWLClassExpression>> getChildList() {
		return listChild;
	}

	public void setChildList(HashMap<OWLClassExpression, HashSet<OWLClassExpression>> c) {
		listChild = c;
	}

	public ArrayList<String> getSubMergedOntName() {
		return subOnt;
	}

	public void setSubMergedOntName(ArrayList<String> s) { // never used!
		subOnt = s;
	}

	public void setSubMergedOntZipName(String s) {
		subOntZip = s;
	}

	public String getSubMergedOntZipName() {
		return subOntZip;
	}

	public void setEvalTotalLabel(String dimLable) {
		dimensionLabel = dimLable;
	}

	public String getEvalTotalLabel() {
		return dimensionLabel;
	}

	public void setEvalHashResult( HashMap<String, String> e) {
		 evalHash = e;
	}
	
	public HashMap<String, String> getEvalHashResult() {
		return evalHash;
	}
}