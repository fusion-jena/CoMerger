
package fusion.comerger.general.visualization;

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
