package fusion.comerger.general.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.hp.hpl.jena.assembler.assemblers.AssemblerGroup.Frame;

import fusion.comerger.algorithm.partitioner.SeeCOnt.CClustering;
import fusion.comerger.algorithm.partitioner.SeeCOnt.EvaluationSeeCOnt;
import fusion.comerger.algorithm.partitioner.SeeCOnt.Findk.ShowTree;
import fusion.comerger.general.visualization.Visualization;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;

public class SelectCHWindows {

	public static JFrame frmSelectIntractiveCh;
	public static JScrollPane CurrentCHScrollPane ;
	public static JScrollPane TreeScrollPane;
	public static JScrollPane ListScrollPane;
	public static JTextArea NumCHLabel;
	public static JTextArea CHLabel;
	public static JLabel StatusLabel;
	private JList<String> ItemList ;
	public static DefaultListModel<String> ListArrayCH = new DefaultListModel<>();
	public static List<String> selectedValuesList;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectCHWindows window = new SelectCHWindows();
					window.frmSelectIntractiveCh.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SelectCHWindows() {
		initialize();
	}
	public static void Show(){
		frmSelectIntractiveCh.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSelectIntractiveCh = new JFrame();
		frmSelectIntractiveCh.setTitle("Select Intractive CH");
		frmSelectIntractiveCh.setBounds(100, 100, 615, 726);
		frmSelectIntractiveCh.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmSelectIntractiveCh.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 85, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		panel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblCurrentCh = new JLabel("Current Numbr of CH :");
		GridBagConstraints gbc_lblCurrentCh = new GridBagConstraints();
		gbc_lblCurrentCh.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentCh.gridx = 0;
		gbc_lblCurrentCh.gridy = 0;
		panel_1.add(lblCurrentCh, gbc_lblCurrentCh);
		
		NumCHLabel = new JTextArea();
		GridBagConstraints gbc_NumCHLabel = new GridBagConstraints();
		gbc_NumCHLabel.insets = new Insets(0, 0, 5, 0);
		gbc_NumCHLabel.fill = GridBagConstraints.BOTH;
		gbc_NumCHLabel.gridx = 1;
		gbc_NumCHLabel.gridy = 0;
		panel_1.add(NumCHLabel, gbc_NumCHLabel);
		
		JLabel lblCurrentChs = new JLabel("Current CHs :");
		GridBagConstraints gbc_lblCurrentChs = new GridBagConstraints();
		gbc_lblCurrentChs.insets = new Insets(0, 0, 0, 5);
		gbc_lblCurrentChs.gridx = 0;
		gbc_lblCurrentChs.gridy = 1;
		panel_1.add(lblCurrentChs, gbc_lblCurrentChs);
		
		CurrentCHScrollPane = new JScrollPane();
		GridBagConstraints gbc_CurrentCHScrollPane = new GridBagConstraints();
		gbc_CurrentCHScrollPane.fill = GridBagConstraints.BOTH;
		gbc_CurrentCHScrollPane.gridx = 1;
		gbc_CurrentCHScrollPane.gridy = 1;
		panel_1.add(CurrentCHScrollPane, gbc_CurrentCHScrollPane);
		
		CHLabel = new JTextArea();
		CurrentCHScrollPane.setViewportView(CHLabel);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 2;
		panel.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblH = new JLabel("Hierarchy View");
		GridBagConstraints gbc_lblH = new GridBagConstraints();
		gbc_lblH.insets = new Insets(0, 0, 5, 5);
		gbc_lblH.gridx = 0;
		gbc_lblH.gridy = 0;
		panel_2.add(lblH, gbc_lblH);
		
		JLabel lblNewLabel_2 = new JLabel("Order of Classes");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 0;
		panel_2.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		TreeScrollPane = new JScrollPane();
		GridBagConstraints gbc_TreeScrollPane = new GridBagConstraints();
		gbc_TreeScrollPane.fill = GridBagConstraints.BOTH;
		gbc_TreeScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_TreeScrollPane.gridx = 0;
		gbc_TreeScrollPane.gridy = 1;
		panel_2.add(TreeScrollPane, gbc_TreeScrollPane);
		
//		JTextArea TreeTextArea = new JTextArea();
		JTree TreeTextArea= new JTree();
		TreeScrollPane.setViewportView(TreeTextArea);
		
		ListScrollPane = new JScrollPane();
		GridBagConstraints gbc_ListScrollPane = new GridBagConstraints();
		gbc_ListScrollPane.fill = GridBagConstraints.BOTH;
		gbc_ListScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_ListScrollPane.gridx = 1;
		gbc_ListScrollPane.gridy = 1;
		panel_2.add(ListScrollPane, gbc_ListScrollPane);
		
//		ListScrollPane = new javax.swing.JScrollPane(ItemList);

		ItemList = new JList<>(ListArrayCH);
	    ItemList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	        
		JTextArea ListTextArea = new JTextArea();
//		ListScrollPane.setViewportView(ListTextArea);//?
		ListScrollPane.setViewportView(ItemList);		 
		
				
		JButton ApplyTreeButton = new JButton("Apply Selected CHs from Hierarchy");
		GridBagConstraints gbc_ApplyTreeButton = new GridBagConstraints();
		gbc_ApplyTreeButton.insets = new Insets(0, 0, 0, 5);
		gbc_ApplyTreeButton.gridx = 0;
		gbc_ApplyTreeButton.gridy = 2;
		panel_2.add(ApplyTreeButton, gbc_ApplyTreeButton);
		ApplyTreeButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                ApplyTreeButtonActionPerformed(evt); 
	            }
	        });
		 
		JButton ApplyListButton = new JButton("Apply Seletcted CHs from List");
		GridBagConstraints gbc_ApplyListButton = new GridBagConstraints();
		gbc_ApplyListButton.gridx = 1;
		gbc_ApplyListButton.gridy = 2;
		panel_2.add(ApplyListButton, gbc_ApplyListButton);
		ApplyListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplyListButtonActionPerformed(evt); 
            }
        });
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Status", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 3;
		panel.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		StatusLabel = new JLabel("Click on Apply button to partition the ontology by new selected CH");
		GridBagConstraints gbc_StatusLabel = new GridBagConstraints();
		gbc_StatusLabel.gridwidth = 2;
		gbc_StatusLabel.insets = new Insets(0, 0, 5, 5);
		gbc_StatusLabel.gridx = 0;
		gbc_StatusLabel.gridy = 0;
		panel_3.add(StatusLabel, gbc_StatusLabel);
		
		JButton GraphButton = new JButton("View Ontology Graph");
		GridBagConstraints gbc_GraphButton = new GridBagConstraints();
		gbc_GraphButton.insets = new Insets(0, 0, 0, 5);
		gbc_GraphButton.gridx = 0;
		gbc_GraphButton.gridy = 1;
		panel_3.add(GraphButton, gbc_GraphButton);
		GraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	GraphButtonActionPerformed(evt); 
            }
        });
        
		
		JButton btnNewButton_3 = new JButton("Close");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSelectIntractiveCh.setVisible(false);
			}
		});
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.gridx = 1;
		gbc_btnNewButton_3.gridy = 1;
		panel_3.add(btnNewButton_3, gbc_btnNewButton_3);
	}

	private void ApplyTreeButtonActionPerformed(ActionEvent evt) {
    	selectedValuesList = new ArrayList<String>();
    	String n = null;
    	TreePath[] nodes = ShowTree.tree.getSelectionPaths ();    
	
    	if (nodes == null){
		    	  JOptionPane.showMessageDialog(null, "Please Select your CH", "Error", JOptionPane.ERROR_MESSAGE);
		      }else{	      
		      	n = nodes.length + " ";
		    	NumCHLabel.setText(n);
		    	CHLabel.setText(null);
		    	String allCH=null;
		    	
			      for (int i = 0; i < nodes.length; i ++)
			      {
			          TreePath treePath = nodes[i];
			          DefaultMutableTreeNode node =(DefaultMutableTreeNode)treePath.getLastPathComponent ();
			          String aw = node.getUserObject ().toString();
			          selectedValuesList.add(aw);
			          if (allCH != null ) {
			       			allCH = allCH + " , " + aw;
			       		} else{
			       			allCH = aw;
			       		}
			      }
			      CHLabel.setText(allCH);
		      

			      CClustering cc= new CClustering ();
			      cc.StepsCClustering(1); //call from Apply button of adding CH (as an interactive process)
	       	
			      //write finish successfully
//			      StatusLabel.setText("Partitioning has been done by your "+ Coordinator.KNumCH + " cluster heads successfully.");
	       	
			      //do evaluation for these new clustering
			      EvaluationSeeCOnt.Evaluation_SeeCont();
	       	
			      OPATgui.NumCHTextField.setText(n);
		      } 
   }
	private void ApplyListButtonActionPerformed(ActionEvent evt) {
    	selectedValuesList = ItemList.getSelectedValuesList();
    	if (selectedValuesList.size() > 0) {
	    	//Show info 
	    	String n = selectedValuesList.size() + " ";
	       	NumCHLabel.setText(n);
	       	CHLabel.setText(null);
	       	String allCH=null;
	       	for (int i=0; i<selectedValuesList.size();i++){
	       		if (allCH != null ) {
	       			allCH = allCH + " , " + selectedValuesList.get(i);
	       		} else{
	       			allCH = selectedValuesList.get(i);
	       		}
	       	}
	       	CHLabel.setText(allCH);
	       	
   
	       	//Run SeeCONT with these new CHs
	       	//CentralClustering SeeCOntObj= new CentralClustering ();
	       	//SeeCOntObj.SeeCOntAlogorithm(1); //call from Apply button of adding CH (as an interactive process)
	       	//LinkedHashMap<Integer, Cluster> Clusters;
	       	//Clusters = SeeCOntObj.SeeCOntAlogorithm(1); //call from Apply button of adding CH (as an interactive process)
	       	
	       	
	       	CClustering cc= new CClustering ();
	       	cc.StepsCClustering(1); //call from Apply button of adding CH (as an interactive process)
	       	
	       	//write finish successfully
	       	StatusLabel.setText("Partitioning has been done by your "+ Coordinator.KNumCH + " cluster heads successfully.");
	       	
	       	//do evaluation for these new clustering
	       	EvaluationSeeCOnt.Evaluation_SeeCont();
	       	
	       	OPATgui.NumCHTextField.setText(n);
    	} else{
  		  JOptionPane.showMessageDialog(null, "Please Select your CH", "Error", JOptionPane.ERROR_MESSAGE);
    	}
   }
	private void GraphButtonActionPerformed(ActionEvent evt) {
    	Coordinator.argVisulization = 2;
    	Visualization.Run(Coordinator.argVisulization); //2 means call from CH selection panel
	  }
}
