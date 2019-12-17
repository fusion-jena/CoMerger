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
import java.util.Iterator;

import com.ibm.icu.text.DecimalFormat;

import fusion.comerger.algorithm.partitioner.SeeCOnt.CClustering;
import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Controller;
import fusion.comerger.general.cc.Data;
import fusion.comerger.general.gui.OPATgui;
import fusion.comerger.model.Node;

public class EvaluationSeeCOnt {
	public static String NumConClusString = null;
	public EvaluationSeeCOnt(){
	}
	
public static String []  Evaluation_SeeCont(Data data){ 
	String [] res = new String [6];
	
	// ****************** call evaluation process
	ModuleEvaluation EP = new ModuleEvaluation();
    EP.Eval_SeeCont(data);
	
	// ********************Evaluation of Size****************************
//	OPATgui.Result1_Details.setText(null);
//	OPATgui.Result1_Details.setText(null);
	
	int sumSiz =0;
	res[3] = "Size of Cluster "+ 0+ " :\t "+ String.valueOf(data.getClusters().get(0).getSize())+'\n';
	sumSiz = sumSiz + data.getClusters().get(0).getSize();
	for (int i = 1; i< data.getNumCH(); i++  ){
		//OPATgui.Result1_Details.append("Size of Cluster "+ i+ " :\t "+ String.valueOf(CClustering.sizeCluster[i])+'\n');
//		OPATgui.Result1_Details.append("Size of Cluster "+ i+ " :\t "+ String.valueOf(Coordinator.clusters.get(i).getSize())+'\n');
		res[3] = res[3] + "Size of Cluster "+ i+ " :\t "+ String.valueOf(data.getClusters().get(i).getSize())+'\n';
		sumSiz = sumSiz + data.getClusters().get(i).getSize();
	}
//	OPATgui.AvgTextBox1.setText(String.valueOf(new DecimalFormat("##.###").format(sumSiz / Coordinator.KNumCH)));
	res[0] = String.valueOf(Math.round(sumSiz / data.getNumCH() * 10000.0) / 10000.0);
		
	///////////////////////////////////////////////////////////////////////////////////////
	// ********************Evaluation of Coupling ***************************
//	OPATgui.Result2_Details.setText(null);
	double sumHeMo =0;
	res[4] = "Coupling of Cluster " + 0 + " :\t " + String.valueOf(Math.round(ModuleEvaluation.HeMo[0] * 10000.0) / 10000.0)+'\n';
	sumHeMo = sumHeMo + ModuleEvaluation.HeMo [0];
	for (int i = 1; i< data.getNumCH(); i++  ){
//		OPATgui.Result2_Details.append("Coupling of Cluster " + i + " :\t " + String.valueOf(new DecimalFormat("##.###").format(ModuleEvaluation.HeMo[i])+'\n'));
		res[4] = res[4] + "Coupling of Cluster " + i + " :\t " + String.valueOf(Math.round(ModuleEvaluation.HeMo[i]* 10000.0) / 10000.0)+'\n';
		sumHeMo = sumHeMo + ModuleEvaluation.HeMo [i];
	}
//	OPATgui.AvgTextBox2.setText(String.valueOf(new DecimalFormat("##.###").format(sumHeMo/Coordinator.KNumCH)));
	res[1] = String.valueOf(Math.round(sumHeMo/data.getNumCH()* 10000.0) / 10000.0);
//	res[1] = String.valueOf(new DecimalFormat("##.###").format(sumHeMo/data.getNumCH()));
	
	
	//////////////////////////////////////////////////////////////////////////////////////////
	//********************Evaluation of Cohesion ***************************
//	OPATgui.Result3_Details.setText(null);
	
	double sumHoMo =0;
	res[5] = "Cohesion of Cluster " + 0 + " :\t " + String.valueOf(Math.round(ModuleEvaluation.HoMo[0]* 10000.0)/10000.0)+'\n';
	sumHoMo = sumHoMo + ModuleEvaluation.HoMo[0];
	for (int i = 1; i< data.getNumCH(); i++  ){
//		OPATgui.Result3_Details.append("Cohesion of Cluster " + i + " :\t " + String.valueOf(new DecimalFormat("##.###").format(ModuleEvaluation.HoMo[i])+'\n'));
		res[5] = res[5] +  "Cohesion of Cluster " + i + " :\t " + String.valueOf(Math.round(ModuleEvaluation.HoMo[i]* 10000.0) / 10000.0)+'\n';
		sumHoMo = sumHoMo + ModuleEvaluation.HoMo[i];
	}

//	OPATgui.AvgTextBox3.setText(String.valueOf(new DecimalFormat("##.###").format(sumHoMo/Coordinator.KNumCH)));
	res[2] = String.valueOf(Math.round(sumHoMo/data.getNumCH()* 10000.0) / 10000.0);
	
	return res;
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
private static int findSahreinfo(Node inode, Node jnode){
	int numinfo =0;
	
	if (inode != null && jnode != null)	{
		if (inode != jnode){
			ArrayList<Node> isurround = BuildModel.Connexion(inode);
			ArrayList<Node> jsurround = BuildModel.Connexion(jnode);
			   for(int i=0;i<isurround.size();i++)
			   {
				   Node ind =isurround.get(i);
				   if(jsurround.contains(ind)) numinfo++;
			   }
		}
	}   
	return numinfo;
}

}