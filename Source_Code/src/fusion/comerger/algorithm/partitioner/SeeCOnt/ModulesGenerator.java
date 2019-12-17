package fusion.comerger.algorithm.partitioner.SeeCOnt;

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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;



import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;

import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Data;
import fusion.comerger.model.Node;
import fusion.comerger.model.ext.sentence.RDFSentence;
import fusion.comerger.model.ext.sentence.RDFSentenceGraph;
import fusion.comerger.model.ext.sentence.filter.OntologyHeaderFilter;
import fusion.comerger.model.ext.sentence.filter.PureSchemaFilter;

/*import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;*/

public class ModulesGenerator {
	
//	private LinkedHashMap<Integer, Cluster> clusters;
//	private ArrayList<OntModel> modules;
//	private OntModel model;
//	private String ontName = null;
//	public  String tempDir = null;
	
	public ModulesGenerator()
	{
//		 To Do: these varibale should be delete or put on coordinator file (not in ever place we define them)
//		model=BuildModel.OntModel;
//		clusters=Coordinator.getClusters();
//		modules=new ArrayList<OntModel>();
//		tempDir = BuildModel.wd;
//	    ontName = BuildModel.ontologyName;
	}
//	
//	public ArrayList<OntModel> getModules()
//	{
//		return modules; //we have this function in coordinator TO DO: delete
//	}
//	
	public void buildModules(Data data)
	{
		//first is to build links between set of concepts inside set of clusters
		createLink(data);
		
		//second create the output by constructing the set of modules
		createOutput(data);
	}
	
	private  void createLink(Data data){
		LinkedHashMap<Integer, Cluster> clusters = data.getClusters();
	    for (Iterator<Cluster> i = clusters.values().iterator(); i.hasNext();) {
	    	Cluster icluster = i.next();  //System.out.println(icluster.getClusterID()+"\t"+icluster.getElements().size());
	    	addProperties(icluster, data);
	    }
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void addProperties(Cluster icluster, Data data){
		Iterator<Node> stm= data.getRbgModel().listStmtNodes();
		Cluster tempCluster = new Cluster(0);
		while (stm.hasNext()){
			Node istm= stm.next();
			Node iobject = istm.getObject();
			Node isubject = istm.getSubject();
			Node ipredicate = istm.getPredicate();
			if ((ipredicate.getLocalName() != null) && (ipredicate.getLocalName().toLowerCase().toString().equals("domain") || ipredicate.getLocalName().toLowerCase().toString().equals("range")))
			{
				int u= clusterExistence(iobject, isubject,  icluster);
				if (u == 1){
						//icluster.putElement(isubject.toString(), isubject);
						tempCluster.putElement(isubject.toString(), isubject);
				}else if (u == 2){
						//icluster.putElement(iobject.toString(), iobject);
						tempCluster.putElement(iobject.toString(), iobject);
				}
			
			}
		}
		Iterator<Node> ilist =tempCluster.listElements();
		while (ilist.hasNext()){
			Node ind = ilist.next();
			icluster.putElement(ind.toString(), ind);
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int clusterExistence (Node inode, Node jnode , Cluster icluster){
		int u=0;
		Iterator<Node> ls= icluster.listElements();
		while (ls.hasNext()){
			Node nd = ls.next();
			if (nd.equals(inode) ){
				u=1;
			}else if (nd.equals(jnode) ){
				u=2;
			}
		}
		return u;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////CreateOutput_Phase ////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 private   ArrayList<OntModel> createOutput(Data data){
		LinkedHashMap<Integer, Cluster> clusters = data.getClusters();	
		 int NumCH=clusters.size();
		 int numEntity=data.getNumEntities();
		 int [][] NumLickConcept = new int [NumCH][numEntity+1]; // This array store the number of link for each element in each cluster
	  
	 ArrayList<Cluster> list = new ArrayList<Cluster>();
	 for (Iterator<Cluster> i = clusters.values().iterator(); i.hasNext();) {
	     list.add(i.next());
	 }
	 LinkedHashMap<String, Integer> uriToClusterID = new LinkedHashMap<String, Integer>();
	 for (int i = 0, n = list.size(); i < n; i++) {
	     Cluster cluster = list.get(i);
	     int clusterID = cluster.getClusterID();
	     for (Iterator<Node> iter = cluster.listElements(); iter.hasNext();) {
	         Node inode= iter.next();
	    	 String uri = inode.toString(); 
	         uriToClusterID.put(uri, clusterID);
	     }
	 }
	 
	 RDFSentenceGraph sg = new RDFSentenceGraph(data.getRbgModel().getOntModel());
	 sg.build(); //build with list statement of original ontology model
	 ArrayList <String> iii= sg.getOntologyURIs();
	 sg.filter(new OntologyHeaderFilter(sg.getOntologyURIs()));
	 sg.filter(new PureSchemaFilter());
	 //Creating one model for each partition to store them as separated files
	 ArrayList<OntModel> modules = new ArrayList<OntModel>(list.size()); // create models (Array list with OntModel type with number of partition (NumCH))
	 LinkedHashMap<Integer, Integer> clusterIDToOntModelID = new LinkedHashMap<Integer, Integer>();
	 for (int i = 0, n = list.size(); i < n; i++) {
		 modules.add(ModelFactory.createOntologyModel()); //Create as RDF format
	     int cid = list.get(i).getClusterID();
	     clusterIDToOntModelID.put(cid, i);
	 }
	 for (int i = 0, n = sg.getRDFSentences().size(); i < n; i++) {
	     RDFSentence sentence = sg.getRDFSentence(i);     
	     ArrayList<String> uris = sentence.getSubjectDomainVocabularyURIs(); 
	     LinkedHashMap<Integer, Object> uniqueURIs = new LinkedHashMap<Integer, Object>();
	     for (int j = 0, m = uris.size(); j < m; j++) {
	         Integer clusterID = uriToClusterID.get(uris.get(j));
	         if (clusterID != null) {
	             uniqueURIs.put(clusterID, null);
	         }
	     }
	     if (uniqueURIs.size() == 1) {
	         Integer cid = uniqueURIs.keySet().iterator().next();
	         Integer mid = clusterIDToOntModelID.get(cid);
	         OntModel block =  modules.get(mid); //mid is the cluster index
	         ArrayList<Statement> statements = sentence.getStatements();
	         
	         for (int j = 0, m = statements.size(); j < m; j++) {
	        	 block.add(statements.get(j));  
	        	 // if one statement add to the file, we should for its subject-object save the number of  link
	        	 RDFNode ObjectURI = statements.get(j).getObject();
	             RDFNode SubjectURI = statements.get(j).getSubject();
	             RDFNode PropertyURI = statements.get(j).getPredicate();  
	             if (ObjectURI.isURIResource() && SubjectURI.isURIResource() ){
		             String[] iProperty = PropertyURI.toString().split("\\#");
		       		 String[] iSubject = SubjectURI.toString().split("\\#");
		       		 String[] iObject = ObjectURI.toString().split("\\#");
		       		 if(iProperty!=null && iSubject!=null && iObject!=null)
		       		 {
		       		 if (iProperty.length>1){
			       		 if (iProperty[1].toLowerCase().equals("subclassof") || iProperty[1].toLowerCase().equals("haspropoerty")) { // TO DO: we should those acceptable property in this line such as SubclassOf 
				         	 int indexSubjectName =0;
				         	 if(iSubject.length >1) indexSubjectName=BuildModel.findIndex(iSubject[1]); 
					         if (indexSubjectName >0 )
					             {NumLickConcept[mid][indexSubjectName] = NumLickConcept[mid][indexSubjectName] +1; }  // mid is the index of CH
					      	 int indexObjectName =0;
					      	 if(iObject.length>1) BuildModel.findIndex(iObject[1]); 
					      	 if (indexObjectName >0 )
					         	 {NumLickConcept[mid][indexObjectName] = NumLickConcept[mid][indexObjectName] +1; }
				          }
		       		 	}
		       		 }
		       	}
	         }
	     }
	 }
	 


	 
	 // adding root 1- for Root concept (RootTag=false) ,  2- for those element with numLink=1
	 	//First phase (1-for Root concept (RootTag=false))
		//if the node does not have superNode, we suppose it is Root and it is alone, so we call addRoot() function for it
		for (int ia=0; ia<data.getNumEntities(); ia++){
			 if (data.getEntities().get(ia).getNamedSupers() == null){
				 Node alone_element= data.getEntities().get(ia); 
				 int indexCH_aloneElement = uriToClusterID.get(data.getEntities().get(ia).toString());
				 addRoot(alone_element.toString(),indexCH_aloneElement , data); //add this element in the ch block
				 //since we add one link in the file (alone_elemenet, subClassOf, "Thing"), so, we should count one link for alone_element in the NumLickConcept array 
				 NumLickConcept[indexCH_aloneElement][ia] = NumLickConcept[indexCH_aloneElement][ia] +2; //in the next lines, if this array home==1, then it thinks it is alone and does not link to Thing class, so, we add (+2) till it does not be equal 1
				 // we count those classes that are connected to alone_element
				 Iterator<Node> listStm = data.getRbgModel().listStmtNodes();
				 while (listStm.hasNext()){
					 Node st= listStm.next();
					if (st.getPredicate().getLocalName().toLowerCase().equals("subclassof") ){
					 if (st.getSubject().getLocalName() != null && st.getObject().getLocalName() != null){
						 if (st.getSubject() == alone_element){
							 int isx= BuildModel.findIndex(st.getObject().getLocalName());
							 if ((isx>0) && (uriToClusterID.get(st.getObject().toString()) != null) )  NumLickConcept[indexCH_aloneElement][isx] = NumLickConcept[indexCH_aloneElement][isx] +2;
						 }
						 if (st.getObject() == alone_element){
							 int isx= BuildModel.findIndex(st.getSubject().getLocalName());
							 if ((isx>0) && (uriToClusterID.get(st.getSubject().toString()) != null) )  NumLickConcept[indexCH_aloneElement][isx] = NumLickConcept[indexCH_aloneElement][isx] +2;
						 }
					 }
					}
				 }
			}
		}
	 
		//Second phase (2- for those element with numLink=1)		
		for (int i=0; i<NumCH; i++){
			for (int j=0; j<data.getNumEntities(); j++){
				if (NumLickConcept[i][j] == 1) {
					// add it in NumLinkConcept till do not add twice one statements to Thing
					 // we count those classes that are connected to alone_element
					 Iterator<Node> listStm = data.getRbgModel().listStmtNodes();
					 while (listStm.hasNext()){
						 Node st= listStm.next();
						if (st.getPredicate().getLocalName().toLowerCase().equals("subclassof") ){
						 if (st.getSubject().getLocalName() != null && st.getObject().getLocalName() != null){
							 if (st.getSubject() == data.getEntities().get(j)){
								 int isx= BuildModel.findIndex(st.getObject().getLocalName());
								 if ((isx>0) && (uriToClusterID.get(st.getObject().toString()) != null))  NumLickConcept[i][isx] = NumLickConcept[i][isx] +2;
							 }
							 if (st.getObject() == data.getEntities().get(j)){
								 int isx= BuildModel.findIndex(st.getSubject().getLocalName());
								 if ((isx>0) && (uriToClusterID.get(st.getSubject().toString()) != null) )  NumLickConcept[i][isx] = NumLickConcept[i][isx] +2;
							 }
						 }
						}
					 }
				}
			}
		}
	 
	 
	 
	 
	 //Creating Files in Temp folder
	 for (int i = 0, n =  modules.size(); i < n; i++) {
	     int cid = list.get(i).getClusterID();
	     String filepath = data.getPath() + data.getOntName() + "_block_" + cid + ".owl";
	     File file = new File(filepath);
	     if (file.exists()) {
	         file.delete();
	     } 
	     OntModel block =  modules.get(i); // Write one block as one model in owl file
	     try {
	         FileOutputStream fos = new FileOutputStream(filepath);
	         BufferedOutputStream bos = new BufferedOutputStream(fos);
	         block.write(bos, "RDF/XML-ABBREV"); //XML format
	         //block.write(bos, "Turtle"); //compact and more readable
	         bos.close();
	         fos.close();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	     block.close();
	 }

	 for (int i = 0, n =  modules.size(); i < n; i++) {
	 	System.out.println(); System.out.println();
	 	System.out.println("Partition  "+ i); 
	 	int cid = list.get(i).getClusterID();
	     String filepath2 = data.getPath() + data.getOntName() + "_block_" + cid + ".owl";
	     try (BufferedReader br = new BufferedReader(new FileReader(filepath2))) {
	     	   String line = null;
	     	   while ((line = br.readLine()) != null) {
	     		   System.out.println(line);
	        	   }
	     	} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}      
	 }
	 return  modules;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////AddingRoot_Phase //////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public  void addRoot(String concept, int NumCluster, Data data){
		
		OntModel block =  data.getModules().get(NumCluster);
		Resource subjectNew =  ResourceFactory.createResource(concept); 
		Property predicateNew  = ResourceFactory.createProperty("http://www.w3.org/2000/01/rdf-schema#subClassOf");
		RDFNode objectNew = ResourceFactory.createResource("http://www.w3.org/2002/07/owl#Thing");
		Statement statementNew = block.createStatement(subjectNew,  predicateNew, objectNew);
		block.add(statementNew);
	}



}
