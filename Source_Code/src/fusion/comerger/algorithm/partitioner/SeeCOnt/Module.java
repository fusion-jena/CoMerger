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

import fusion.comerger.model.Node;
import fusion.comerger.model.NodeList;

public class Module {
	
	private int conNum;
	private int opNum;
	private int dpNum;
	private NodeList conceptSet;
	private Node moduleH;
		
	public Module()
	{
		conNum=0;
		opNum=0;
		dpNum=0;
		conceptSet=null;  moduleH=null;
	}
	
	public Module(NodeList concept,Node CH)
	{
		this.conceptSet=concept;
		this.moduleH=CH;
		conNum=concept.size();
		opNum=0;
		dpNum=0;
	}
	
	public void setConcept(NodeList concept)
	{
		this.conceptSet=concept;
		conNum=concept.size();
	}
	
	public NodeList getConcept()
	{
		return conceptSet;
	}
	
	public void setModuleHead(Node CH)
	{
		this.moduleH=CH;
	}
	
	public Node getModuleHead()
	{
		return moduleH;
	}
	
	public int getNumConcept()
	{
		return conNum;
	}
	
	public void setNumOP(int op)
	{
		opNum=op;
	}
	
	public int getNumOP()
	{
		return opNum;
	}
	
	public void setNumDP(int op)
	{
		dpNum=op;
	}
	
	public int getNumDP()
	{
		return dpNum;
	}

}
