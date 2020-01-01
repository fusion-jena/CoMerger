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

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.decorators.EdgeStringer;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.decorators.VertexColorFunction;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.SparseGraph;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.VertexIconShapeTransformer;
import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Coordinator;
import fusion.comerger.general.gui.MainFrame;
import fusion.comerger.general.visualization.net.Arc;
import fusion.comerger.general.visualization.net.Edge;
import fusion.comerger.general.visualization.net.Network;
import fusion.comerger.general.visualization.net.Vertex;
import fusion.comerger.model.NodeList;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JTree;

import com.github.andrewoma.dexx.collection.ArrayList;
import org.apache.jena.ontology.OntModel;


public class HierarchyFrame extends javax.swing.JFrame {
    
	
    private static final long serialVersionUID = 1L;
       
    private MainFrame mainframe;
    
    private Graph graph = new SparseGraph();
    
    private FRLayout layout = new FRLayout(graph);
    

   
    public HierarchyFrame(MainFrame mainframe) {
        initComponents();
        
        this.mainframe = mainframe;

    }
    
    
    

    public static void showHierarchy(int numModule) {
		 String ModuleName = null;
		 String tempDir = BuildModel.wd;//It is equal to "./temp/" 
		 String []stfn = BuildModel.ontologyName.split("\\_"); 
		 String ontName = stfn[0];
		 

	    ModuleName = tempDir + ontName + "_Module_"+ numModule  + ".owl"; // Read our created modules
//	    ModuleName = ontName + numModule  + ".owl"; // Read our created modules
		     
	    //check enter num of cluster is valid

        BuildModel.BuildModelOnt(ModuleName); 
    	HierarchyCalculation.ShowTreeCalculating(BuildModel.OntModel);
	}




    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        hierPanel = new javax.swing.JPanel();   hierPanel.setPreferredSize(new Dimension(300, 500)); hierPanel.setMaximumSize(new Dimension(hierPanel.getWidth(), this.getHeight())); hierPanel.setMinimumSize(new Dimension(300, 500));
        infoPanel = new javax.swing.JPanel();   infoPanel.setPreferredSize(new Dimension(300, 500)); infoPanel.setMaximumSize(new Dimension(300, 500)); infoPanel.setMinimumSize(new Dimension(300, 500));
//        hierPanel1 = new javax.swing.JPanel();   hierPanel1.setPreferredSize(new Dimension(300, 100)); hierPanel1.setMaximumSize(new Dimension(300, 100)); hierPanel1.setMinimumSize(new Dimension(300, 100));
//        hierPanel2 = new javax.swing.JPanel();   hierPanel2.setPreferredSize(new Dimension(300, 400)); hierPanel2.setMaximumSize(new Dimension(300, 400)); hierPanel2.setMinimumSize(new Dimension(300, 400));
        
       
        showButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        moduleLabel = new javax.swing.JLabel();
        hierLabel = new javax.swing.JLabel();
        moduleTextbox = new javax.swing.JTextField();     moduleTextbox.setPreferredSize(new Dimension(60, 20));   moduleTextbox.setMaximumSize(new Dimension(60, 20));   moduleTextbox.setMinimumSize(new Dimension(60, 20));
        TreeTextArea= new JTree();
        hierScrollPane = new javax.swing.JScrollPane();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setTitle("Hierarchy View");
        
        
        hierLabel.setText("Hierarchy view of module 1 ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
//        hierPanel.add(hierLabel, gridBagConstraints);

 
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        //to print in JtextArea the next two line should be work
       // PrintStream printStreamPartitioner = new PrintStream(new CustomOutputStreamm(PartitioningPanel.OutputPartitioningTextArea)); // It is for printing output in textbox, not in the console
       // System.setOut(printStreamPartitioner);
        hierPanel.add(hierScrollPane, gridBagConstraints);
        
 
        
        hierPanel.setLayout(new java.awt.GridBagLayout());
        hierPanel.setBorder(new javax.swing.border.TitledBorder("View"));
     
        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
//        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(hierPanel, gridBagConstraints);
        
        ///////////////////////////////////////////////////////////////////////////
        
      
        moduleLabel.setText("Enter module number: ");
//        moduleLabel.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        infoPanel.add(moduleLabel, gridBagConstraints);

//        moduleTextbox.setEnabled(false);
//        moduleTextbox.setText("    sss");
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(moduleTextbox, gridBagConstraints);

        
        showButton.setText("Show");
        showButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	showButtonActionPerformed(evt); 
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(showButton, gridBagConstraints);
        
        
        
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;//1
        gridBagConstraints.gridy = 5;//1
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
//        infoPanel.add(closeButton, gridBagConstraints);
        
        
        
        
        
        infoPanel.setLayout(new java.awt.GridBagLayout());
        infoPanel.setBorder(new javax.swing.border.TitledBorder("Info"));
        
        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
//        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(infoPanel, gridBagConstraints);
        
        
        
        
        
        
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });





        pack();
    }//GEN-END:initComponents
    

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
//        layout.resize(visualizationViewer.getSize());
//        layout.restart();
    }//GEN-LAST:event_formComponentResized
    
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        //mainframe.graphMenuItem.doClick();
        this.setVisible(false);
    }//GEN-LAST:event_closeButtonActionPerformed
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //mainframe.graphMenuItem.doClick();
    	this.setVisible(true);
    }//GEN-LAST:event_formWindowClosing
    
    private void showButtonActionPerformed (java.awt.event.ActionEvent evt){
    	
    	
    	String StrNumModule = moduleTextbox.getText().trim();
    	int NumModule=Integer.parseInt(StrNumModule);
    	boolean check = checkVlaidNum(NumModule);
    	if (check == false){
    		String msg = "Please select the number of module between 1 and " + Coordinator.KNumCH ;
    		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.INFORMATION_MESSAGE);
    		//show message
    	} else {
//        	NumModule ++;
//	    	String la = "Hierarchy view of module " + NumModule;
//	    	hierLabel.setText("");
//	    	hierLabel.setText(la);
	    	
	    	showHierarchy(NumModule-1);
	    }
    }
    private boolean checkVlaidNum(int n){
    	if (n > 0 && n <= Coordinator.KNumCH){
    		return true;
    	} else{
    		return false;
    	}
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JButton showButton;
    private javax.swing.JPanel hierPanel;
//    private javax.swing.JPanel hierPanel1;
//    private javax.swing.JPanel hierPanel2;
    private javax.swing.JPanel infoPanel;
    public static javax.swing.JScrollPane hierScrollPane;
    private javax.swing.JLabel moduleLabel;
    public static javax.swing.JLabel hierLabel;
    public static javax.swing.JTextField moduleTextbox;
    private JTree TreeTextArea;

    // End of variables declaration//GEN-END:variables
    
}
