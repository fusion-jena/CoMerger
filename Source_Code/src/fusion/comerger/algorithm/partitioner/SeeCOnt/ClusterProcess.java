package fusion.comerger.algorithm.partitioner.SeeCOnt;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;

import fusion.comerger.algorithm.matcher.string.EditDistance;
import fusion.comerger.algorithm.matcher.string.ISub;
import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Data;
import fusion.comerger.general.gui.SelectCHWindows;
import fusion.comerger.model.Node;
import fusion.comerger.model.NodeList;
import fusion.comerger.model.RBGModel;
import fusion.comerger.model.ext.sentence.RDFSentence;
import fusion.comerger.model.ext.sentence.RDFSentenceGraph;
import fusion.comerger.model.ext.sentence.filter.OntologyHeaderFilter;
import fusion.comerger.model.ext.sentence.filter.PureSchemaFilter;

public class ClusterProcess {
	// private static LinkedHashMap<Integer, Cluster> clusters;
	// private static OntModel model1;
	// private static NodeList entitiesCH;
	public static String tempDir = null;
	public static ArrayList<OntModel> models;
	// private static int NumCH;
	private static boolean CheckAdd[] = null;
	private static LinkedHashMap<String, Integer> uriToClusterID = null;
	public List opList = null;
	public static List classList = null;
	// private static int numEntity;
//	static HashMap<String, List> rankVector;

	/*
	 * public ClusterProcess(HashMap<String, List> rankVector, String
	 * NameAddressOnt, int NumCH) {
	 * 
	 * this.model1 = BuildModel.OntModel; // NumCH = Coordinator.KNumCH;
	 * this.numEntity = BuildModel.NumEntity; opList =
	 * BuildModel.OntModel.listObjectProperties().toList(); classList =
	 * BuildModel.OntModel.listNamedClasses().toList(); tempDir = BuildModel.wd;
	 * models = new ArrayList<OntModel>(); this.rankVector = rankVector;// new
	 * HashMap<String,List>(); }
	 * 
	 * public ClusterProcess() { // To Do: these varibale should be delete or
	 * put on coordinator file // (not in ever place we define them) this.model1
	 * = BuildModel.OntModel; // NumCH = Coordinator.KNumCH; this.numEntity =
	 * BuildModel.NumEntity; opList =
	 * BuildModel.OntModel.listObjectProperties().toList(); classList =
	 * BuildModel.OntModel.listNamedClasses().toList(); tempDir = BuildModel.wd;
	 * models = new ArrayList<OntModel>(); this.rankVector = new HashMap<String,
	 * List>(); }
	 */
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// SelectingClusterHead_Phase
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void selectClusterHead_Phase(Data data, HashMap<String,List> rankVector) {
		NodeList entitiesCH = new NodeList();
		int indexCH = 0;
		String[] sortN = data.getSortedNameOnt();
		String s = sortN[0];
		RBGModel rbgmodel = data.getRbgModel();
		Node NodeTemp = rbgmodel.getNode(s);
		entitiesCH.add(NodeTemp);
		indexCH++;

		for (int i = 1; i < sortN.length && indexCH < data.getNumCH(); i++) {
			NodeTemp = rbgmodel.getNode(sortN[i]);
			boolean check = test(entitiesCH, NodeTemp, rankVector);
			if (check) {
				entitiesCH.add(NodeTemp);
				indexCH++;
			}
		}
		data.setCHs(entitiesCH);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static boolean test(NodeList list, Node temp, HashMap<String,List> rankVector) {
		// ArrayList<Node> nodeList=getContext(temp);
		for (int i = 0; i < list.size(); i++) {
			Node chnode = list.get(i);
			List<String> llist = rankVector.get(chnode.getLocalName());
			if (llist != null) {
				if (llist.contains(temp.getLocalName()))
					return false;
			}

		}
		return true;
	}

	private ArrayList<Node> getContext(Node node) {
		ArrayList<Node> nodeList = new ArrayList<Node>();
		nodeList.add(node);

		NodeList surround = node.getNamedSubs();
		if (surround != null) {
			for (int i = 0; i < surround.size(); i++) {
				Node inode = surround.get(i);
				nodeList.add(inode);
				NodeList nn = inode.getNamedSubs();
				if (nn != null) {
					for (int j = 0; j < nn.size(); j++) {
						Node iinode = nn.get(j);
						nodeList.add(iinode);
					}
				}
			}
		}
		NodeList par = node.getNamedSupers();
		if (par != null) {
			for (int i = 0; i < par.size(); i++) {
				Node nnn = par.get(i);
				nodeList.add(nnn);
			}
		}
		return nodeList;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// SelectingClusterHead_old_Phase
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void selectClusterHead_old_Phase(Data data) {
		NodeList entitiesCH = new NodeList();
		int indexCH = 0;
		Node NodeTemp = null;
		// select first CH without any condition
		// System.out.println("model\t"+data.getEntities().size());
		NodeTemp = data.getEntities().get(BuildModel.findIndex(data.getSortedNameOnt()[indexCH]));
		entitiesCH.add(NodeTemp);
		ArrayList<Node> CandidCH = BuildModel.Connexion(NodeTemp);
		indexCH++;

		// select other CH with condition. They should not exist in neighbor
		// nodes of previous CHs
		for (int i = 1; i < data.getEntities().size(); i++) {
			if (data.getSortedNameOnt()[i] != null) {
				if (indexCH >= data.getNumCH())
					break;
				NodeTemp = null;
				NodeTemp = data.getEntities().get(BuildModel.findIndex(data.getSortedNameOnt()[i]));
				boolean existance = false;
				existance = CandidCH.contains(NodeTemp);
				if (existance == false) { // if it will be false shows that till
											// now it does not neighbor of
											// previous CHs
					entitiesCH.add(NodeTemp);
					ArrayList<Node> temp = BuildModel.Connexion(NodeTemp);
					for (int j = 0; j < temp.size(); j++) {
						CandidCH.add(temp.get(j));
					}
					indexCH++;
				}
			}
		}
		data.setCHs(entitiesCH);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// ADD CH
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void AddCH(Data data) {
		NodeList eCHs = new NodeList();
		int NumCH = 0;
		for (int i = 0; i < SelectCHWindows.selectedValuesList.size(); i++) {
			int idx = BuildModel.findIndexName(SelectCHWindows.selectedValuesList.get(i)); // caseSensitive
			if (idx != -1) { // if it is -1, it means not find or it is null
				eCHs.add(data.getEntities().get(idx));
				NumCH = NumCH + 1;
			}
		}

		data.setNumCH(NumCH);
		data.setCHs(eCHs);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// CreateCluster_Phase
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void createCluster_Phase(Data data) {

		// entitiesCH = Coordinator.CH;
		NodeList chs = data.getCHs();
		LinkedHashMap<Integer, Cluster> cls = new LinkedHashMap<Integer, Cluster>(chs.size());

		uriToClusterID = new LinkedHashMap<String, Integer>();
		// CheckAdd = new boolean [numEntity+1];
		CheckAdd = new boolean[data.getEntities().size()];

		for (int cid = 0, n = chs.size(); cid < n; cid++) {
			if (cid <= chs.size()) {
				Node entity = chs.get(cid);
				Cluster cluster = new Cluster(cid);
				cluster.setCH(entity);
				cluster.putElement(entity.toString(), entity);
				cls.put(cid, cluster);
				cluster.setCH(entity);
				uriToClusterID.put(entity.toString(), cid);
				// int index=BuildModel.findIndex(entity.getLocalName());
				int index = BuildModel.findIndex(entity.toString());// new:samira
				if (index < data.getEntities().size())
					CheckAdd[index] = true;
			}
		}
		// System.out.println("The initial clusters are created:");
		// Coordinator.clusters = clusters;
		data.setClusters(cls);
		// HttpSession session= request.getSession();
		// session.setAttribute("clusters", clusters);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// CreateCluster_Phase
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void createCluster_old_Phase(Data data) {
		NodeList entitiesCH = data.getCHs();
		LinkedHashMap<Integer, Cluster> clusters = new LinkedHashMap<Integer, Cluster>(entitiesCH.size());
		uriToClusterID = new LinkedHashMap<String, Integer>();
		CheckAdd = new boolean[data.getEntities().size()];

		for (int cid = 0, n = entitiesCH.size(); cid < n; cid++) {
			if (cid <= entitiesCH.size()) {
				Node entity = entitiesCH.get(cid);
				Cluster cluster = new Cluster(cid);
				cluster.setCH(entity);
				cluster.putElement(entity.toString(), entity);
				clusters.put(cid, cluster);
				cluster.setCH(entity);
				uriToClusterID.put(entity.toString(), cid);
				// if (BuildModel.findIndex(entity.getLocalName())!= -1)
				// CheckAdd[BuildModel.findIndex(entity.getLocalName())] = true;
				if (BuildModel.findIndex(entity.toString()) != -1)
					CheckAdd[BuildModel.findIndex(entity.toString())] = true;// new:samira
			}
		}
		data.setClusters(clusters);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// InitialCluster_Phase with
	/////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void initialCluster_Phase(Data data) {
		NodeList entitiesCH = data.getCHs();
		LinkedHashMap<Integer, Cluster> cls = data.getClusters();
		uriToClusterID = new LinkedHashMap<String, Integer>();
		NodeList Temp = null;
		for (int i = 0; i < data.getNumCH(); i++) {
			if (i < entitiesCH.size()) {
				Node ch = entitiesCH.get(i);
				Cluster x = cls.get(i);
				Temp = ch.getNamedSubs();
				if (Temp != null) {
					for (int j = 0; j < Temp.size(); j++) {
						// int index =
						// BuildModel.findIndex(Temp.get(j).getLocalName());
						int index = BuildModel.findIndex(Temp.get(j).toString());// new:samira
						if (index > -1 && index < data.getNumEntities()) {
							if (CheckAdd[index] == false) {
								x.putElement(Temp.get(j).toString(), Temp.get(j));
								CheckAdd[index] = true;
							}
						}
					}
					// System.out.println("========\t " +
					// x.getCH().getLocalName() + "\n");
					// x.printCluster();
				}
			}
		}
		// System.out.println("The direct spread phase is done:");
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// InitialCluster_Phase with
	/////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void initialCluster_old_Phase(Data data) {
		NodeList entitiesCH = data.getCHs();
		LinkedHashMap<Integer, Cluster> clusters = data.getClusters();
		// add direct child of CH to each their cluster
		uriToClusterID = new LinkedHashMap<String, Integer>();
		ArrayList<Node> temp = null;
		for (int i = 0; i < data.getNumCH(); i++) {
			if (i < entitiesCH.size()) {
				temp = BuildModel.Connexion(entitiesCH.get(i));
				for (int j = 0; j < temp.size(); j++) {
					// int index =
					// BuildModel.findIndex(temp.get(j).getLocalName());
					int index = BuildModel.findIndex(temp.get(j).toString());// new:samira
					if (index > -1) {
						if (CheckAdd[index] == false) {
							Cluster x = clusters.get(i);
							x.putElement(temp.get(j).toString(), temp.get(j));
							CheckAdd[index] = true;
						}
					}
				}
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// finalizeClsuetring_old_Phase
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void finalizeClsuetring_old_Phase(Data data) {
		NodeList entitiesCH = data.getCHs();
		LinkedHashMap<Integer, Cluster> clusters = data.getClusters();
		float MemebershipsConcepts[] = null;
		for (int i = 0; i < data.getEntities().size(); i++) {
			Node iNode = data.getEntities().get(i);
			// int indxiNode= BuildModel.findIndex(iNode.getLocalName());
			int indxiNode = BuildModel.findIndex(iNode.toString());// new:samira
			if (indxiNode > -1) {

				if (CheckAdd[indxiNode] == false) {
					MemebershipsConcepts = new float[data.getNumCH()];
					for (int m = 0; m < data.getNumCH(); m++) {
						if (entitiesCH.get(m) != null) {
							Node CHnode = entitiesCH.get(m);
							MemebershipsConcepts[m] = MemFuncCToCH3(iNode, CHnode);
						}
					}
					MemebershipsConcepts = SolveEqualValue(MemebershipsConcepts, data.getNumCH(), entitiesCH, iNode);
					int indexCH = FindIndexOfMax(MemebershipsConcepts, data.getNumCH(), data);

					CheckAdd[indxiNode] = true;
					Cluster x = clusters.get(indexCH);
					x.putElement(iNode.toString(), iNode);
				}
			}
		}
		data.setClusters(clusters);
	}

	//////////////////////////////////////////// MemberShipFunctionConceptToClusterHead
	//////////////////////////////////////////// /////////////////////////////////////////////////////////////////////////
	public static float MemFuncCToCH3(Node NodeConcept, Node NodeCH) {
		float MemShip = 0;
		double StringSim = 0;
		// LevenshteinDistance st= new LevenshteinDistance();
		EditDistance st = new EditDistance();
		String StrConcept = NodeConcept.getLocalName().toString();
		String StrCH = NodeCH.getLocalName().toString();
		if (StrConcept != null && StrCH != null)
			StringSim = st.getSimilarity(StrConcept, StrCH);// .getDistance(StrConcept,
															// StrCH);

		MemShip = (float) (NumberOfShareNeighbor(NodeConcept, NodeCH) + StringSim);

		return MemShip;
	}

	//////////////////////////////////////////// NumberOfShareNeighbor
	//////////////////////////////////////////// /////////////////////////////////////////////////////////////////////////////////////////////
	public static float NumberOfShareNeighbor(Node iclass, Node jclass) {
		ArrayList<Node> neighbor1 = BuildModel.Connexion(iclass);
		ArrayList<Node> neighbor2 = BuildModel.Connexion(jclass);
		float shareNeighbor = 0;
		int count = 0;

		for (int i = 0; i < neighbor1.size(); i++) {
			for (int j = 0; j < neighbor2.size(); j++) {
				if (neighbor1.get(i) != null && neighbor2.get(j) != null && neighbor1.get(i).equals(neighbor2.get(j))) {
					count++;
					break;
				}
			}
		}

		if (neighbor1.size() > 0 && neighbor2.size() > 0) {
			shareNeighbor = count / (neighbor1.size() + neighbor2.size());
		}
		return shareNeighbor;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// finalizeClsuetring_Phase
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void finalizeClsuetring_Phase(Data data, HashMap<String, List> rankVector) {
		int NumCH = data.getNumCH();
		NodeList entitiesCH = data.getCHs();
		LinkedHashMap<Integer, Cluster> cls = data.getClusters();
		classList = data.getOntModel().listNamedClasses().toList();
		
		int opCount = data.getOntModel().listObjectProperties().toList().size();
		int entCount = data.getNumEntities();
		float MemebershipsConcepts[] = null;
		HashMap<Integer, Double> memShipList = null;
		for (int i = 0; i < entCount; i++) {
			Node iNode = data.getEntities().get(i);
			// int indxiNode= BuildModel.findIndex(iNode.getLocalName());
			int indxiNode = BuildModel.findIndex(iNode.toString());
			if (indxiNode > -1 && indxiNode < data.getNumEntities()) {
				if (CheckAdd[indxiNode] == false) {
					MemebershipsConcepts = new float[NumCH];
					memShipList = new HashMap<Integer, Double>();
					for (int m = 0; m < entitiesCH.size(); m++) {
						Node CHnode = entitiesCH.get(m);
						if (opCount > 0.1 * entCount) {
							MemebershipsConcepts[m] = computMemFun_sem(iNode, CHnode, data, rankVector);
							memShipList.put((Integer) m, (double) MemebershipsConcepts[m]);
						} else {
							MemebershipsConcepts[m] = computMemFun_str(iNode, CHnode, data, rankVector);
							memShipList.put(m, (double) MemebershipsConcepts[m]);
						}

					}
					// memShipList=solveDuplicate(memShipList,iNode);
					// MemebershipsConcepts =
					// SolveEqualValue(MemebershipsConcepts, NumCH, entitiesCH ,
					// iNode);
					int indexCH = FindIndexCH(memShipList, data);
					CheckAdd[indxiNode] = true;
					Cluster x = cls.get(indexCH);
					x.putElement(iNode.toString(), iNode);
				}
			}
		}

		// print inside the clusters
		for (int i = 0; i < cls.size(); i++) {
			Cluster x = cls.get(i);
			Node node = x.getCH();
//			System.out.println("the cluster head====\t " + node.getLocalName());
//			x.printCluster();
//			System.out.println("===========\t ");
		}
		// Coordinator.clusters = clusters;
//		HttpSession session = request.getSession();
//		session.setAttribute("clusters", cls);
//		String sch = Integer.toString(NumCH);
//		session.setAttribute("NumCH", sch);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////// this is an old implementation of dealing with duplicate
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ////////////
	public static float[] SolveEqualValue(float iArray[], int NumCH, NodeList entitiesCH, Node iNode) {
		// if we have equal value we should consider another measure to know
		// which one is better
		int size = entitiesCH.size();
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				if (iArray[i] == iArray[j]) {
					iArray[j] = iArray[j] + (float) SecCompare(entitiesCH.get(j), iNode);
					iArray[i] = iArray[i] + (float) SecCompare(entitiesCH.get(i), iNode);
				}
			}
		}
		return iArray;
	}

	/////////// Another implementation for duplicate values detection between a
	/////////// node and the set of cluster heads
	private HashMap<Integer, Double> solveDuplicate(HashMap<Integer, Double> mem, Node iNode, Data data) {
		NodeList entitiesCH = data.getCHs();
		HashSet<Double> set = new HashSet<Double>(mem.values());
		if (set.size() == mem.size())
			return mem;
		else {
			Iterator it = mem.keySet().iterator();
			while (it.hasNext()) {
				int key = (int) it.next();
				double value = mem.get(key);
				Set Keys = null;
				for (Entry<Integer, Double> e : mem.entrySet())
					if (e.getValue().equals(value)) {
						Keys.add(e.getKey());
					}
				/*
				 * Set Keys= (mem.entrySet() .stream() .filter(entry ->
				 * Objects.equals(entry.getValue(), value))
				 * .map(Map.Entry::getKey) .collect(Collectors.toSet()));
				 */

				if (Keys.size() > 1) {
					Iterator keys = Keys.iterator();
					while (keys.hasNext()) {
						int kk = (int) keys.next();
						if (key != kk) {
							double xx = mem.get(kk) + SecCompare(entitiesCH.get(kk), iNode);
							mem.put(kk, xx);
							double x = mem.get(key) + SecCompare(entitiesCH.get(key), iNode);
							mem.put(key, x);
						}
					}
				}
			}
		}
		return mem;
	}

	private static int FindIndexCH(HashMap<Integer, Double> mem, Data data) {
		LinkedHashMap<Integer, Cluster> clusters = data.getClusters();
		int index = 0;
		HashMap<Integer, Double> sortedMap = sortByValue(mem);
		/*
		 * mem.entrySet().stream() .sorted(Entry.comparingByValue())
		 * .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) ->
		 * e1, LinkedHashMap::new));
		 */
		/*
		 * for (Map.Entry<Integer,Double> entry :sortedMap.entrySet()) {
		 * System.out.println(entry.getKey()+" : "+entry.getValue()); }
		 */

		Set<Integer> it = sortedMap.keySet();
		List<Integer> newIT = new ArrayList<Integer>(it);
		for (int i = newIT.size(); i > 0; i--) {
			index = (Integer) newIT.get(i - 1);
			Cluster x = clusters.get(index);
			if (x.getSize() < 1.4 * data.getNumEntities() / data.getNumCH())
				break;
		}
		return index;
	}

	private static <K, V extends Comparable<? super V>> HashMap<K, V> sortByValue(HashMap<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		HashMap<K, V> result = new LinkedHashMap<>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static int FindIndexOfMax(float[] XArray, int NumCH, Data data) {
		LinkedHashMap<Integer, Cluster> clusters = data.getClusters();
		double max = 0;
		int IndexOfMax = 0;
		for (int i = 0; i < NumCH; i++)
			if (XArray[i] > max) {
				max = XArray[i];
				Cluster x = clusters.get(IndexOfMax);
				if (data.getNumEntities() > 500) {
					if (x.getSize() < data.getNumEntities()/ NumCH)
						IndexOfMax = i;
				} else
					IndexOfMax = i;
			}
		return IndexOfMax;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// MemberShipFunctionConceptToClusterHead
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static float computMemFun_sem(Node NodeConcept, Node NodeCH, Data data,  HashMap<String, List> rankVector) {

		float semSim = 0, strSim = 0, sem = 0;
		int count = 0;
		strSim = computePathNameSim(NodeConcept, NodeCH, rankVector);
		semSim = computeStrSim(NodeConcept, NodeCH, rankVector);
		String namespace = data.getOntModel().getNsPrefixURI("");
		OntClass chClass = data.getOntModel().getOntClass(namespace + NodeCH.getLocalName());
		OntClass iClass = data.getOntModel().getOntClass(namespace + NodeConcept.getLocalName());
		if (classList.contains(chClass) && classList.contains(iClass)) {
			List chList = chClass.listDeclaredProperties(true).toList();
			if (chList != null) {
				for (int i = 0; i < chList.size(); i++) {
					OntProperty onP = (OntProperty) chList.get(i);
					List dClasses = onP.listDomain().toList();//
					List dcl = onP.listRange().toList();
					if (dClasses.contains(iClass) || dcl.contains(iClass))
						count++;
				}
				sem = count / (float) (chList.size());
			}
		}
		// Path path=OntTools.findShortestPath(model1,
		// chClass,iClass,Filter.any);
		int size = 0;
		// if(path!=null) size=path.size();
		float memShip = 0;
		if (size == 0)
			memShip = (float) (0.4 * semSim + 0.2 * sem + 0.4 * strSim);
		else
			memShip = (float) (0.3 * semSim + 0.2 * sem + 0.2 * strSim + .03 / size);
		// System.out.println(NodeConcept.getLocalName()+"\t the
		// simi"+NodeCH.getLocalName()+"\t"+memShip);
		return memShip;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static float computMemFun_str(Node NodeConcept, Node NodeCH, Data data,  HashMap<String, List> rankVector) {

		float semSim = 0, strSim = 0;
		strSim = computePathNameSim(NodeConcept, NodeCH, rankVector);
		semSim = computeStrSim(NodeConcept, NodeCH, rankVector);
		String namespace = data.getOntModel().getNsPrefixURI("");
		OntClass chClass = data.getOntModel().getOntClass(namespace + NodeCH.getLocalName());
		OntClass iClass = data.getOntModel().getOntClass(namespace + NodeConcept.getLocalName());
		// Path path=OntTools.findShortestPath(model1,
		// chClass,iClass,Filter.any);
		int size = 0;
		// if(path!=null) size=path.size();
		float memShip = 0;
		if (size == 0)
			memShip = (float) (0.6 * semSim + 0.4 * strSim);
		else
			memShip = (float) (0.4 * semSim + 0.3 * strSim + 0.3 / size);

		return memShip;
	}

	/////////////// computing the string similarity between the name path of a
	/////////////// node and a cluster head node
	public static float computePathNameSim(Node NodeConcept, Node NodeCH, HashMap<String, List> rankVector) {
		String StrConcept = getString(NodeConcept, rankVector );
		String StrCH = getString(NodeCH, rankVector);
		float sim = (float) ISub.getSimilarity(StrConcept, StrCH);
		// float sim=(float) LevenshteinDistance.getDistance(StrConcept, StrCH);
		return sim;
	}

	private static String getString(Node node, HashMap<String, List> rankVector) {
		List list = rankVector.get(node.getLocalName());
		String name = node.getLocalName();
		if (list != null)
			for (int i = 0; i < list.size(); i++) {
				name = name.concat((String) list.get(i));
			}
		return name;
	}

	////// computing the semantic similarity between a node and cluster head
	public static float computeStrSim(Node iclass, Node jclass, HashMap<String, List> rankVector) {

		float shareNeighbor = 0;
		List list1 = rankVector.get(iclass.getLocalName());
		List list2 = rankVector.get(jclass.getLocalName());
		List list3 = new ArrayList();
		if (list1 != null && list2 != null) {
			list3.retainAll(list1);
			if (list1.size() + list2.size() > 0)
				shareNeighbor = list3.size() / (list1.size() + list2.size());
		}

		return shareNeighbor;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static double SecCompare(Node CHnode, Node iNode) {
		double c = 0;
		double ich = chartoint(CHnode.getLocalName());
		double in = chartoint(iNode.getLocalName());
		c = (double) ((ich + in) + (CHnode.getHierarchy() + iNode.getHierarchy())) / 100000;
		return c;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static double chartoint(String iname) {
		double r = 0;
		iname = iname.toLowerCase();
		for (int i = 0; i < iname.length(); i++) {
			char xx = iname.charAt(i);
			r = r + (int) xx;
		}
		r = r / iname.length();
		return r;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// CreateOutput_Phase
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static void createLink_Phase(Data data) {
		LinkedHashMap<Integer, Cluster> clusters = data.getClusters();
		for (Iterator<Cluster> i = clusters.values().iterator(); i.hasNext();) {
			Cluster icluster = i.next();
			// System.out.println("Cluster:\t"+i);icluster.printCluster();
			addProperties(icluster, data);
			// System.out.println(icluster.getClusterID()+"\t"+icluster.getElements().size());
			// icluster.printCluster();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void addProperties(Cluster icluster, Data data) {
		Iterator<Node> stm = data.getRbgModel().listStmtNodes();
		boolean isDatatype = false;
		Cluster tempCluster = new Cluster(0);
		while (stm.hasNext()) {
			Node istm = stm.next();
			Node iobject = istm.getObject();
			Node isubject = istm.getSubject();
			Node ipredicate = istm.getPredicate();
			if ((ipredicate.getLocalName() != null)
					&& (ipredicate.getLocalName().toLowerCase().toString().equals("domain")
							|| ipredicate.getLocalName().toLowerCase().toString().equals("range"))) {
				/*
				 * isDatatype = false; ExtendedIterator datalist=
				 * BuildModel.OntModel.listDatatypeProperties(); while
				 * (datalist.hasNext()){ Object id= datalist.next(); if
				 * (id.toString() == ipredicate.toString() || id.toString()
				 * ==isubject.toString() ){ isDatatype = true; } } if
				 * (isDatatype == false){
				 */
				int u = clusterExistence(iobject, isubject, icluster);
				if (u == 1) {
					// icluster.putElement(isubject.toString(), isubject);
					tempCluster.putElement(isubject.toString(), isubject);
				} else if (u == 2) {
					// icluster.putElement(iobject.toString(), iobject);
					tempCluster.putElement(iobject.toString(), iobject);
				}
				// }

			}
		}
		Iterator<Node> ilist = tempCluster.listElements();
		while (ilist.hasNext()) {
			Node ind = ilist.next();
			icluster.putElement(ind.toString(), ind);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int clusterExistence(Node inode, Node jnode, Cluster icluster) {
		int u = 0;

		Iterator<Node> ls = icluster.listElements();
		while (ls.hasNext()) {
			Node nd = ls.next();
			if (nd.equals(inode)) {
				u = 1;
			} else if (nd.equals(jnode)) {
				u = 2;
			}
		}
		return u;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// CreateOutput_old_Phase
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<OntModel> createOutput_old_Phase(Data data) {

		LinkedHashMap<Integer, Cluster> clusters = data.getClusters();
		ArrayList<Cluster> list = new ArrayList<Cluster>();
		for (Iterator<Cluster> i = clusters.values().iterator(); i.hasNext();) {
			list.add(i.next());
		}
		LinkedHashMap<String, Integer> uriToClusterID = new LinkedHashMap<String, Integer>();
		for (int i = 0, n = list.size(); i < n; i++) {
			Cluster cluster = list.get(i);
			int clusterID = cluster.getClusterID();
			for (Iterator<Node> iter = cluster.listElements(); iter.hasNext();) {
				String uri = iter.next().toString();
				uriToClusterID.put(uri, clusterID);
			}
		}
		RDFSentenceGraph sg = new RDFSentenceGraph(data.getRbgModel().getOntModel());
		sg.build(); // build with list statement
		sg.filter(new OntologyHeaderFilter(sg.getOntologyURIs()));
		sg.filter(new PureSchemaFilter());
		// Creating one model for each partition to store them as separated
		// files
		models = new ArrayList<OntModel>(list.size()); // create models (Array
														// list with OntModel
														// type with number of
														// partition (NumCH))
		LinkedHashMap<Integer, Integer> clusterIDToOntModelID = new LinkedHashMap<Integer, Integer>();
		for (int i = 0, n = list.size(); i < n; i++) {
			models.add(ModelFactory.createOntologyModel()); // Create as RDF
															// format
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
				OntModel block = models.get(mid);
				ArrayList<Statement> statements = sentence.getStatements();
				for (int j = 0, m = statements.size(); j < m; j++) {
					block.add(statements.get(j));
				}
			}
		}
		return models;
	}
	/*
	 * public ArrayList<OntModel> getOntModels() { if(models==null) return
	 * createOWLFiles_Phase();//createOutput_Phase3(); else return models; }
	 */

}
