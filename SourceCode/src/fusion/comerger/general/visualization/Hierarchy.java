
package fusion.comerger.general.visualization;
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
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.github.andrewoma.dexx.collection.ArrayList;

import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Coordinator;
import fusion.comerger.general.gui.HierarchyWindows;
import fusion.comerger.general.gui.MainFrame;
import fusion.comerger.general.gui.MergingPanel;
import fusion.comerger.general.gui.PartitioningPanel;
import fusion.comerger.general.visualization.Converter;
import fusion.comerger.general.visualization.GraphFrame;
import fusion.comerger.general.visualization.HierarchyFrame;
import fusion.comerger.general.visualization.net.Network;
import fusion.comerger.model.Node;
import fusion.comerger.model.NodeList;


public class Hierarchy
{
	protected static  HierarchyFrame HierFrame;
    
 public  static void Run (){

	 MainFrame mainframe = null;
	 HierarchyWindows HierFrame = new HierarchyWindows();
	 
//	 HierFrame = new HierarchyFrame(mainframe);

	 HierFrame.showHierarchy(0); //0 means show the first module
//     HierFrame.setVisible(true);


	 
}

     

}
