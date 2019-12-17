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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hp.hpl.jena.ontology.OntModel;

import fusion.comerger.general.cc.Data;
import fusion.comerger.model.NodeList;
import fusion.comerger.model.RBGModel;

public class CClustering {
	HashMap<String,List> rankVector = null;
   public void  StepsCClustering (Data data, int arg)
   {
//	   HttpSession session= request.getSession();
//	   session.setAttribute("NumCHsee", NumCHsee);
	   
	   	switch (arg){
	   	case 0: //call from Execute button to partition the whole ontology (normal case)
	   		Rank r= new Rank();	r.rankConcepts(data); rankVector=r.rankVector;
	   		ClusterProcess c = new ClusterProcess();
	   		c.selectClusterHead_Phase(data, rankVector);	
	   		c.createCluster_Phase( data);
	   		c.initialCluster_Phase(data);	
	   		c.finalizeClsuetring_Phase(data, rankVector);    	      
	   		CreateModule m = new CreateModule();
//	   		ArrayList<org.apache.jena.ontology.OntModel> models= m.createOWLFiles_Phase(request,data);
	   		m.createOWLFiles_Phase(data);
	   		
//	   		m.createOutput_old_Phase();
	   		break;

		case 1: //call from Apply button of adding CH (as an interactive process)
			c = new ClusterProcess();
			c.AddCH(data);
			c.createCluster_Phase(data);			
			c.initialCluster_Phase(data);	
			c.finalizeClsuetring_Phase(data, null);      //should be correct null	       
			m = new CreateModule();
			m.createOWLFiles_Phase( data);
			break;
			
		case 2: //call from FindK (first iterative) 
			r= new Rank();  
			//It should be old phase, but beacuse rankVector shouldnot be null in next phase, here, now I call new rank phase
//			r.rank_old_Phase(data); r.sort_old_Phase(data);
			r.rankConcepts(data); rankVector=r.rankVector;
			c = new ClusterProcess();
//			r= new Rank(); r.rankConcepts();
//	   		rankVector=r.rankVector;
//			c = new ClusterProcess(rankVector);
//			c.selectClusterHead_old_Phase();
			c.selectClusterHead_Phase(data,rankVector);//null should be correct
	   		c.createCluster_Phase(data);
//			c.createCluster_old_Phase();
	   		c.initialCluster_Phase(data);
//			c.initialCluster_old_Phase();
	   		c.finalizeClsuetring_old_Phase(data); //better than new one
//	   		c.finalizeClsuetring_Phase();      
	   		m = new CreateModule();
//	   		m.createOutput_old_Phase();
	   		m.createOWLFiles_Phase(data); //better than old one 
	   		break;
	   		
		case 3: //call from FindK (other iterative)	
			c = new ClusterProcess();
//	   		c.selectClusterHead_old_Phase();
			c.selectClusterHead_Phase(data,rankVector); //null should be correct
	   		c.createCluster_Phase(data);
//	   		c.createCluster_old_Phase();
	   		c.initialCluster_Phase(data);
//	   		c.initialCluster_old_Phase();
	   		c.finalizeClsuetring_old_Phase(data);
//	   		c.finalizeClsuetring_Phase();      
	   		m = new CreateModule();
//	   		m.createOutput_old_Phase();
	   		m.createOWLFiles_Phase(data);
	   		break;
		   
	   	}
   }

}