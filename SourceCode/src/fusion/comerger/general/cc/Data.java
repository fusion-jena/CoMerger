package fusion.comerger.general.cc;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
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
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.jena.ontology.OntModel;

import fusion.comerger.algorithm.partitioner.SeeCOnt.Cluster;
import fusion.comerger.model.NodeList;
import fusion.comerger.model.RBGModel;

public class Data {
	RBGModel rbgModel = null;
	OntModel ontModel = null;
	String ontName = "";
	int numCH = 1;
	NodeList entities = new NodeList();
	String[] sortedNameOnt;
	NodeList CHs = new NodeList();
	LinkedHashMap<Integer, Cluster> clusters;
	int numEntities;
	ArrayList<OntModel> modules;
	String path;
	double[] numTree;
	boolean CheckBuildModel = false;

	public OntModel getOntModel() {
		return ontModel;
	}

	public void SetOntModel(OntModel ontmodel) {
		ontModel = ontmodel;
	}

	public RBGModel getRbgModel() {
		return rbgModel;
	}

	public void SetRbgModel(RBGModel rbgmodel) {
		rbgModel = rbgmodel;
	}

	public int getNumCH() {
		return numCH;
	}

	public void setNumCH(int numch) {
		numCH = numch;
	}

	public String getOntName() {
		return ontName;
	}

	public void setOntName(String ontname) {
		ontName = ontname;
	}

	public NodeList getEntities() {
		return entities;
	}

	public void setEntities(NodeList ent) {
		entities = ent;
	}

	public void setSortedNameOnt(String[] sortN) {
		sortedNameOnt = sortN;
	}

	public String[] getSortedNameOnt() {
		return sortedNameOnt;
	}

	public void setCHs(NodeList chs) {
		CHs = chs;
	}

	public NodeList getCHs() {
		return CHs;
	}

	public LinkedHashMap<Integer, Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(LinkedHashMap<Integer, Cluster> cl) {
		clusters = cl;
	}

	public int getNumEntities() {
		return numEntities;
	}

	public void setNumEntities(int n) {
		numEntities = n;
	}

	public ArrayList<OntModel> getModules() {
		return modules;
	}

	public void setModules(ArrayList<OntModel> m) {
		modules = m;
	}

	public String getPath() {
//		return "C://Users//Samira//workspaceWeb//OAPT//uploads";
		return path;
	}
	
	public void  setPath(String addr) {
		path = addr;
	}

	public void setNumTree(double[] nt) {
		numTree = nt;
	}
	public double[] getNumTree(){
		return numTree;
	}
	
	public void setCheckBuildModel(boolean check){
		CheckBuildModel = check;
	}
	
	public boolean getCheckBuildModel(){
		return CheckBuildModel;
	}
}