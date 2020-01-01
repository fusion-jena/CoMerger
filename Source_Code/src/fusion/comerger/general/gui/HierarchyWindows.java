package fusion.comerger.general.gui;
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
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;

import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Coordinator;
import fusion.comerger.general.visualization.HierarchyCalculation;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HierarchyWindows {

	private JFrame frmHierarchyView;
	public static JScrollPane hierScrollPane;// = new JScrollPane();
	public static JLabel LabelHirViewofModule;
	public static javax.swing.JTextField moduleTextbox;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HierarchyWindows window = new HierarchyWindows();
					window.frmHierarchyView.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HierarchyWindows() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHierarchyView = new JFrame();
		frmHierarchyView.setTitle("Hierarchy View");
		frmHierarchyView.setBounds(100, 100, 532, 628);
		frmHierarchyView.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frmHierarchyView.setVisible(true);
		
		JPanel panel = new JPanel();
		frmHierarchyView.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{284, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "View", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		LabelHirViewofModule = new JLabel("Hierarchy View of Module 1");
		GridBagConstraints gbc_LabelHirViewofModule = new GridBagConstraints();
		gbc_LabelHirViewofModule.insets = new Insets(0, 0, 5, 0);
		gbc_LabelHirViewofModule.gridx = 0;
		gbc_LabelHirViewofModule.gridy = 0;
		panel_1.add(LabelHirViewofModule, gbc_LabelHirViewofModule);
		
		hierScrollPane = new JScrollPane();
		GridBagConstraints gbc_hierScrollPane = new GridBagConstraints();
		gbc_hierScrollPane.fill = GridBagConstraints.BOTH;
		gbc_hierScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_hierScrollPane.gridx = 0;
		gbc_hierScrollPane.gridy = 1;
		panel_1.add(hierScrollPane, gbc_hierScrollPane);
		
		JTextArea textArea = new JTextArea();
		hierScrollPane.setViewportView(textArea);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 0;
		panel.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{119, 125, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblEnterModuleNumber = new JLabel("Enter Module Number :");
		GridBagConstraints gbc_lblEnterModuleNumber = new GridBagConstraints();
		gbc_lblEnterModuleNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterModuleNumber.anchor = GridBagConstraints.EAST;
		gbc_lblEnterModuleNumber.gridx = 0;
		gbc_lblEnterModuleNumber.gridy = 4;
		panel_2.add(lblEnterModuleNumber, gbc_lblEnterModuleNumber);
		
		moduleTextbox = new JTextField();
		GridBagConstraints gbc_moduleTextbox = new GridBagConstraints();
		gbc_moduleTextbox.insets = new Insets(0, 0, 5, 0);
		gbc_moduleTextbox.fill = GridBagConstraints.HORIZONTAL;
		gbc_moduleTextbox.gridx = 1;
		gbc_moduleTextbox.gridy = 4;
		panel_2.add(moduleTextbox, gbc_moduleTextbox);
		moduleTextbox.setColumns(10);
		
		JButton btnShow = new JButton("Show");
		GridBagConstraints gbc_btnShow = new GridBagConstraints();
		gbc_btnShow.gridx = 1;
		gbc_btnShow.gridy = 5;
		panel_2.add(btnShow, gbc_btnShow);
		btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	showButtonActionPerformed(evt); 
            }
        });
		
		JButton Closebtn = new JButton("Close");
		Closebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmHierarchyView.setVisible(false);
			}
		});
		GridBagConstraints gbc_Closebtn = new GridBagConstraints();
		gbc_Closebtn.gridx = 1;
		gbc_Closebtn.gridy = 1;
		panel.add(Closebtn, gbc_Closebtn);
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
    	int a = numModule +1;
    	String s = "Hierarchy View of Module " + a;
    	LabelHirViewofModule.setText(s);
	}
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
    }
