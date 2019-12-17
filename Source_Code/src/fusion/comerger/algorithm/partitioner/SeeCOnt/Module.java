package fusion.comerger.algorithm.partitioner.SeeCOnt;

/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
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
