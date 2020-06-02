
package fusion.comerger.algorithm.matcher.gmo;
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
import fusion.comerger.general.output.Alignment;
import fusion.comerger.general.output.Mapping;
import fusion.comerger.model.Constant;
import fusion.comerger.model.Node;
import fusion.comerger.model.NodeCategory;
import fusion.comerger.model.RBGModel;

public class ExternalMatch {
	private Alignment classSim = new Alignment();
	private Alignment propertySim = new Alignment();
	private Alignment instanceSim = new Alignment();
	private RBGModel rbgModelA = null;
	private RBGModel rbgModelB = null;

	public ExternalMatch(RBGModel modelA, RBGModel modelB) {
		rbgModelA = modelA;
		rbgModelB = modelB;
	}

	public RBGModel getModelA() {
		return rbgModelA;
	}

	public RBGModel getModelB() {
		return rbgModelB;
	}

	public double getSimilarity(Node left, Node right) {
		double sim = 0;
		if (left.isLiteral() && right.isLiteral()) {
			sim = DataLiteralSim.getDataLiteralSim(left, right);
		} else if (NodeCategory.getCategory(left) == Constant.ONTOLOGY_INSTANCE
				&& NodeCategory.getCategory(right) == Constant.ONTOLOGY_INSTANCE) {
			if (instanceSim != null) {
				if (left.isAnon() || right.isAnon()) {
					sim = 0;
				} else {
					sim = instanceSim.getSimilarity(left, right);
					if (sim <= 0) {
						sim = instanceSim.getSimilarity(right, left);
					}
				}
			}
		} else if (left.getNodeLevel() == Node.LANGUAGE_LEVEL && right.getNodeLevel() == Node.LANGUAGE_LEVEL
				&& left.getCategory() == right.getCategory()) {
			if (Constant.XSD_NS.equals(left.getNameSpace()) && Constant.XSD_NS.equals(right.getNameSpace())) {
				sim = DatatypeSim.getSimilarity(left.toString(), right.toString());
			} else if (Constant.XSD_NS.equals(left.getNameSpace()) || Constant.XSD_NS.equals(right.getNameSpace())) {
				sim = 0;
			} else {
				sim = BuiltInVocSim.getSimilarity(left.toString(), right.toString());

			}
		} else if (left.getNodeLevel() == Node.EXTERNAL && right.getNodeLevel() == Node.EXTERNAL) {
			if (left.getCategory() == Node.CLASS && right.getCategory() == Node.CLASS) {
				if (classSim != null) {
					if (left.isAnon() || right.isAnon()) {
						sim = 0;
					} else {
						sim = classSim.getSimilarity(left, right);
						if (sim <= 0) {
							sim = classSim.getSimilarity(right, left);
						}
					}
				}
			} else if (left.getCategory() >= Node.OBJECTPROPERTY && left.getCategory() <= Node.DATATYPEPROPERTY
					&& right.getCategory() >= Node.OBJECTPROPERTY && right.getCategory() <= Node.DATATYPEPROPERTY) {
				if (propertySim != null) {
					sim = propertySim.getSimilarity(left, right);
					if (sim <= 0) {
						sim = propertySim.getSimilarity(right, left);
					}
				}
			}
		} else if (left.toString().equals(right.toString())) {
			sim = 1;
		}
		if (sim < 0) {
			sim = 0;
		}
		return sim;
	}

	public void setInstanceSim(Alignment as) {
		if (as == null || as.size() == 0) {
			return;
		}
		for (int i = 0, n = as.size(); i < n; i++) {
			Mapping map = as.getMapping(i);
			Node nodeA = null;
			Node nodeB = null;
			nodeA = rbgModelA.getNode(map.getEntity1().toString());
			if (nodeA == null) {
				nodeA = rbgModelA.getNode(map.getEntity2().toString());
				nodeB = rbgModelB.getNode(map.getEntity1().toString());
			} else {
				nodeB = rbgModelB.getNode(map.getEntity2().toString());
			}
			instanceSim.addMapping(new Mapping(nodeA, nodeB, map.getSimilarity()));
		}
	}

	public Alignment getInstanceSim() {
		return instanceSim;
	}

	public void setClassSim(Alignment as) {
		if (as == null || as.size() == 0) {
			return;
		}
		for (int i = 0, n = as.size(); i < n; i++) {
			Mapping map = as.getMapping(i);
			Node nodeA = null;
			Node nodeB = null;
			nodeA = rbgModelA.getNode(map.getEntity1().toString());
			if (nodeA == null) {
				nodeA = rbgModelA.getNode(map.getEntity2().toString());
				nodeB = rbgModelB.getNode(map.getEntity1().toString());
			} else {
				nodeB = rbgModelB.getNode(map.getEntity2().toString());
			}
			if (nodeA != null && nodeB != null) {
				nodeA.setNodeLevel(Node.EXTERNAL);
				nodeB.setNodeLevel(Node.EXTERNAL);
				classSim.addMapping(new Mapping(nodeA, nodeB, map.getSimilarity()));
			}
		}
	}

	public Alignment getClassSim() {
		return classSim;
	}

	public void setPropertySim(Alignment as) {
		if (as == null || as.size() == 0) {
			return;
		}
		for (int i = 0, n = as.size(); i < n; i++) {
			Mapping map = as.getMapping(i);
			Node nodeA = null;
			Node nodeB = null;
			nodeA = rbgModelA.getNode(map.getEntity1().toString());
			if (nodeA == null) {
				nodeA = rbgModelA.getNode(map.getEntity2().toString());
				nodeB = rbgModelB.getNode(map.getEntity1().toString());
			} else {
				nodeB = rbgModelB.getNode(map.getEntity2().toString());
			}
			nodeA.setNodeLevel(Node.EXTERNAL);
			nodeB.setNodeLevel(Node.EXTERNAL);
			propertySim.addMapping(new Mapping(nodeA, nodeB, map.getSimilarity()));
		}
	}

	public Alignment getPropertySim() {
		return propertySim;
	}
}
