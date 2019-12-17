package fusion.comerger.general.gui;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import fusion.comerger.algorithm.partitioner.SeeCOnt.Findk.FindOptimalClusterIntractive;
import fusion.comerger.algorithm.partitioner.SeeCOnt.Findk.SelectCH;
import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Cleaning_Allthings;
import fusion.comerger.general.cc.Configuration;
import fusion.comerger.general.cc.Controller;
import fusion.comerger.general.cc.Coordinator;
import fusion.comerger.general.visualization.Hierarchy;
import fusion.comerger.general.visualization.Visualization;
import fusion.comerger.general.visualization.Visualization2;

public class ViewOptionPanel extends JPanel {
	public static String NameAddressOnt; 

    private static final long serialVersionUID = 1L;

    public ViewOptionPanel() {
        super();
        setPreferredSize(new Dimension(600, 100));
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        
        VisualOptionPanel = new javax.swing.JPanel();               VisualOptionPanel.setPreferredSize(new Dimension(600, 100));   VisualOptionPanel.setMaximumSize(new Dimension(600, 100));   VisualOptionPanel.setMinimumSize(new Dimension(600, 100));
        
              
        includeSubclassLinksCheckBox = new javax.swing.JCheckBox();
        strengthSubclassLinksLabel = new javax.swing.JLabel();
//        NumModuleTextField = new javax.swing.JTextField();     NumModuleTextField.setPreferredSize(new Dimension(60, 20));   NumModuleTextField.setMaximumSize(new Dimension(60, 20));   NumModuleTextField.setMinimumSize(new Dimension(60, 20));
//        NumModuleShowLabel = new javax.swing.JLabel();        
//        showAllModuleCheckBox = new javax.swing.JCheckBox();
        strengthSubclassLinksSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(1, 0.0, 10.0, 0.5));
        includePropertyLinksCheckBox = new javax.swing.JCheckBox();
        strengthPropertyLinksLabel = new javax.swing.JLabel();
        strengthPropertyLinksSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(1, 0.0, 10.0, 0.5));
        includeDefinitionLinksCheckBox = new javax.swing.JCheckBox();
        strengthDefinitionLinksLabel = new javax.swing.JLabel();
        strengthDefinitionLinksSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(1, 0.0, 10.0, 0.5));
        linkTypeLabel = new javax.swing.JLabel();
        linkTypeComboBox = new javax.swing.JComboBox();
        includeDefinitionResourcesLabel = new javax.swing.JLabel();
        includeDefinitionResourcesComboBox = new javax.swing.JComboBox();
        includeSubstringLinksCheckBox = new javax.swing.JCheckBox();
        strengthSubstringLinksLabel = new javax.swing.JLabel();
        strengthSubstringLinksSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(1, 0.0, 10.0, 0.5));
        includeDistanceLinksCheckBox = new javax.swing.JCheckBox();
        strengthDistanceLinksLabel = new javax.swing.JLabel();
        strengthDistanceLinksSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(1, 0.0, 10.0, 0.5));
        maxDistanceLabel = new javax.swing.JLabel();
        maxDistanceSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(10, 1, 10, 1));
        
     
//        
//        openOntologyFileChooser.setDialogTitle("Open");
//        saveFileChooser.setDialogTitle("Save");
//        saveFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
//
//        setLayout(new java.awt.GridBagLayout());
//        PicturePanel.setLayout(new java.awt.GridBagLayout());
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 0;
//        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
//        PicturePanel.add(new JLabel(pic1),gridBagConstraints);
//        add(PicturePanel, gridBagConstraints);
//        
//        setLayout(new java.awt.GridBagLayout());
//
//        OntologySelectionPanel.setLayout(new java.awt.GridBagLayout());
//        OntologySelectionPanel.setBorder(new javax.swing.border.TitledBorder("Ontology Select"));
//        
//        ontologyFileLabel.setText("Ontology File:");
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 1;
//        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
//        OntologySelectionPanel.add(ontologyFileLabel, gridBagConstraints);
//
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 1;
//        gridBagConstraints.gridy = 1;
//        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
//        gridBagConstraints.weightx = 1.0;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
//        OntologySelectionPanel.add(ontologyFileTextField, gridBagConstraints);
//        
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 2;
//        gridBagConstraints.gridy = 1;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
//        EmptyLabel2.setText("                            ");
//        OntologySelectionPanel.add(EmptyLabel2, gridBagConstraints);
//    
//        
//        ontologyFileChooserButton.setText("Browse...");
//        ontologyFileChooserButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                ontologyFileChooserButtonActionPerformed(evt);
//            }
//        });
//
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 3;
//        gridBagConstraints.gridy = 1;
//        gridBagConstraints.insets = new java.awt.Insets(1, 1, 0, 1);
//        OntologySelectionPanel.add(ontologyFileChooserButton, gridBagConstraints);
//        
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 1;
//        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
//        add(OntologySelectionPanel, gridBagConstraints);
//        
//        RequiredArgumentsPanel.setLayout(new java.awt.GridBagLayout());
//        RequiredArgumentsPanel.setBorder(new javax.swing.border.TitledBorder("Required Arguments"));
//        
//        TypeofAlgorithmLabel.setText("Type of Algorithm");
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 2;
//        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
//        RequiredArgumentsPanel.add(TypeofAlgorithmLabel, gridBagConstraints);
//
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 1;
//        gridBagConstraints.gridy = 2;
//        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
//        RequiredArgumentsPanel.add(ListTypeComboBox, gridBagConstraints);
//
//        ListTypeComboBox.addActionListener (new ActionListener () {
//            public void actionPerformed(ActionEvent e) {
//            	if (ListTypeComboBox.getSelectedItem().toString().equals("AxCOnt") || ListTypeComboBox.getSelectedItem().toString().equals("PBM")){
//            			NumCHLabel.setEnabled(false);
//            			NumCHTextField.setEnabled(false);
//            	}else{
//            		NumCHLabel.setEnabled(true);
//        			NumCHTextField.setEnabled(true);
//            	}
//            }
//        });
//        
//        
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 2;
//        gridBagConstraints.gridy = 2;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
//        EmptyLabel.setText("                            ");
//        RequiredArgumentsPanel.add(EmptyLabel, gridBagConstraints);
//    
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 3;
//        gridBagConstraints.gridy = 2;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
//        NumCHLabel.setText("Number of Modules");
//        RequiredArgumentsPanel.add(NumCHLabel, gridBagConstraints);
//
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 4;
//        gridBagConstraints.gridy = 2;
//        gridBagConstraints.weightx = 1.0;
//        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
//        RequiredArgumentsPanel.add(NumCHTextField, gridBagConstraints);
//        
//        SuggestButton.setText("Optimal Number of Modules Recommender");
//        SuggestButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            	SuggestButtonActionPerformed(evt); 
//            }
//        });
//        
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 5;
//        gridBagConstraints.gridy = 2;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
//        RequiredArgumentsPanel.add(SuggestButton, gridBagConstraints);
//        
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 2;
//        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
//        add(RequiredArgumentsPanel, gridBagConstraints);
//
//        RunPanel.setLayout(new java.awt.GridBagLayout());
//
//        RunPanel.setBorder(new javax.swing.border.TitledBorder("Run"));
//       
//        progressBar.setPreferredSize(new Dimension(750, 20));
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 3;
//        gridBagConstraints.weightx=2;
//        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
//        RunPanel.add(progressBar, gridBagConstraints);
//
//        ExecuteButton.setText("Execute");
//        ExecuteButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                ExecuteButtonActionPerformed(evt); 
//            }
//        });
//
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 1;
//        gridBagConstraints.gridy = 3;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
//        RunPanel.add(ExecuteButton, gridBagConstraints);
//               
//        SaveButton.setText("Save Output");
//        SaveButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            	JFileChooser fileChooser = new JFileChooser(".");
//                fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
//                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//                fileChooser.setName("Save as the RDF/XML format");
//                fileChooser.setToolTipText("Please input a file to save");
//                int rVal = fileChooser.showSaveDialog(null);
//                if (rVal == JFileChooser.APPROVE_OPTION) {
//                    File file = fileChooser.getSelectedFile(); 
//                    String fp = file.getAbsolutePath();
//                    for (int cid=0; cid<Coordinator.KNumCH; cid++){
//                    	String srcName = BuildModel.wd + BuildModel.fn1.substring(0, BuildModel.fn1.length()-4) + "_module_" + cid + ".owl"; // Read our created modules
//                    	//if the user does not determine the extension, we add it to the name of file
//                    	String destName = null;
//                    	if (fp.contains(".") == false) {
//                    			destName = fp + cid + ".owl";
//                    		}else{
//                    		    String []iname = fp.split("\\."); 
//                    			destName = iname[0] + cid + "." + iname[1];
//                    		}
//                	File src = new File(srcName);
//                	File target = new File(destName);
//
//                	try {
//    					Files.copy(src.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
//    				} catch (IOException e) {
//    					e.printStackTrace();
//    				}
//
//                }
//                }
//            }
//        });
//
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 2;
//        gridBagConstraints.gridy = 3;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
//        RunPanel.add(SaveButton, gridBagConstraints);
//        
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 3;
//        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
//        add(RunPanel, gridBagConstraints);
//
//        
//        OutputPanel.setLayout(new java.awt.GridBagLayout());
//
//        OutputPanel.setBorder(new javax.swing.border.TitledBorder("Output"));
//        
//        ViewOptionPanel.OutputPartitioningTextArea.setEditable(false);
//        ViewOptionPanel.OutputPartitioningTextArea.setLineWrap(true);
//        ViewOptionPanel.OutputPartitioningTextArea.setCaretPosition(0);
//        ViewOptionPanel.OutputPartitioningTextArea.setWrapStyleWord(true);
//        ViewOptionPanel.OutputPartitioningTextArea.setText(" "+'\n'+" ");
//        OutputScrollPane.setViewportView(ViewOptionPanel.OutputPartitioningTextArea);
//
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
//        gridBagConstraints.weightx = 1.0;
//        gridBagConstraints.weighty = 1.0;
//        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
//        //to print in JtextArea the next two line should be work
//       // PrintStream printStreamPartitioner = new PrintStream(new CustomOutputStreamm(PartitioningPanel.OutputPartitioningTextArea)); // It is for printing output in textbox, not in the console
//       // System.setOut(printStreamPartitioner);
//        OutputPanel.add(OutputScrollPane, gridBagConstraints);
//
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 4;
//        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
//        gridBagConstraints.weightx = 1.0;
//        gridBagConstraints.weighty = 0.4;
//        add(OutputPanel, gridBagConstraints); 
//       
        
//        VisualOptionPanel.setLayout(new java.awt.GridBagLayout());
        
//        VisualOptionPanel.setBorder(new javax.swing.border.TitledBorder("Visual View"));
        
        
//        ViewHierarchyButton.setText("View Hierarchy");
//        ViewHierarchyButton.setEnabled(false);
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 1;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
//        VisualOptionPanel.add(ViewHierarchyButton, gridBagConstraints);
//          
//        ViewHierarchyButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
////            	#
//            	Hierarchy.Run();
//            }
//        });
//        
//        ViewGraphButton.setText("View Graph");
//        ViewGraphButton.setEnabled(false);
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
//        gridBagConstraints.gridx = 1;
//        gridBagConstraints.gridy = 1;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
//        VisualOptionPanel.add(ViewGraphButton, gridBagConstraints);
//          
//        ViewGraphButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            	if (Coordinator.FinishPartitioning== true){
//            		LockedGUI();
//            		Coordinator.levelDetail = Integer.parseInt(JOptionPane.showInputDialog("Enter level's detail of showing ontology (1 means all, 2 means half of the nodes)"));
//            		Coordinator.argVisulization = 0;
//            		Visualization.Run(Coordinator.argVisulization); //0:shows that visualization called from partitioning panel
//            		Un_LockedGUI();
//            	}else{
//            		JOptionPane.showMessageDialog(null, "Your selected ontology does not partition, please before seeing the visual output, do the partitioning job.", "Error", JOptionPane.INFORMATION_MESSAGE);
//            	}
//            }
//        });
//        
//        
//        ViewAllGraphButton.setText("View Whole Graph");
//        ViewAllGraphButton.setEnabled(false);
//        gridBagConstraints = new java.awt.GridBagConstraints();
//        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
//        gridBagConstraints.gridx = 2;
//        gridBagConstraints.gridy = 1;
//        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
//        VisualOptionPanel.add(ViewAllGraphButton, gridBagConstraints);
//          
//        ViewAllGraphButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            	if (Coordinator.FinishPartitioning== true){
//            		LockedGUI();
//            		Coordinator.levelDetail = Integer.parseInt(JOptionPane.showInputDialog("Enter level's detail of showing ontology (1 means all, 2 means half of the nodes)"));
//            		Visualization2.Run(); 
//            		Un_LockedGUI();
//            	}else{
//            		JOptionPane.showMessageDialog(null, "Your selected ontology does not partition, please before seeing the visual output, do the partitioning job.", "Error", JOptionPane.INFORMATION_MESSAGE);
//            	}
//            }
//        });
        
        VisualOptionPanel.setLayout(new java.awt.GridBagLayout());
        
        showAllModuleCheckBox.setText("Show All Modules");
        showAllModuleCheckBox.setSelected(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        VisualOptionPanel.add(showAllModuleCheckBox, gridBagConstraints);
        showAllModuleCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
            	showAllModuleCheckBoxCheckBoxStateChanged(evt);
            }
        });
        
        
        NumModuleShowLabel.setText("which modules: ");
        NumModuleShowLabel.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        VisualOptionPanel.add(NumModuleShowLabel, gridBagConstraints);

        NumModuleTextField.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        VisualOptionPanel.add(NumModuleTextField, gridBagConstraints);

       
        includeSubclassLinksCheckBox.setSelected(true);
        includeSubclassLinksCheckBox.setText("Include subclass links");
        includeSubclassLinksCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                includeSubclassLinksCheckBoxStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        VisualOptionPanel.add(includeSubclassLinksCheckBox, gridBagConstraints);

        strengthSubclassLinksLabel.setText("Strength subclass links");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualOptionPanel.add(strengthSubclassLinksLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        strengthSubclassLinksSpinner.setEnabled(true);
        VisualOptionPanel.add(strengthSubclassLinksSpinner, gridBagConstraints);


        linkTypeLabel.setText("Link type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        VisualOptionPanel.add(linkTypeLabel, gridBagConstraints);

        linkTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UniEdge", "Edge" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        linkTypeComboBox.setSelectedIndex(1);
        VisualOptionPanel.add(linkTypeComboBox, gridBagConstraints);
        
        
        includePropertyLinksCheckBox.setSelected(true);
        includePropertyLinksCheckBox.setText("Include property links");
        includePropertyLinksCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                includePropertyLinksCheckBoxStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        VisualOptionPanel.add(includePropertyLinksCheckBox, gridBagConstraints);

        strengthPropertyLinksLabel.setText("Strength property links");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualOptionPanel.add(strengthPropertyLinksLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        strengthPropertyLinksSpinner.setEnabled(true);
        VisualOptionPanel.add(strengthPropertyLinksSpinner, gridBagConstraints);

        includeDefinitionLinksCheckBox.setSelected(false);
        includeDefinitionLinksCheckBox.setText("Include definition links");
        includeDefinitionLinksCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                includeDefinitionLinksCheckBoxStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        VisualOptionPanel.add(includeDefinitionLinksCheckBox, gridBagConstraints);

        strengthDefinitionLinksLabel.setText("Strength definition links");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualOptionPanel.add(strengthDefinitionLinksLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        strengthDefinitionLinksSpinner.setEnabled(false);
        VisualOptionPanel.add(strengthDefinitionLinksSpinner, gridBagConstraints);

        includeDefinitionResourcesLabel.setText("Included definition resources");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualOptionPanel.add(includeDefinitionResourcesLabel, gridBagConstraints);

        includeDefinitionResourcesComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Only properties", "All resources" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        includeDefinitionResourcesComboBox.setEnabled(false);
        VisualOptionPanel.add(includeDefinitionResourcesComboBox, gridBagConstraints);

        includeSubstringLinksCheckBox.setSelected(false);
        includeSubstringLinksCheckBox.setText("Include substring links");
        includeSubstringLinksCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                includeSubstringLinksCheckBoxStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        VisualOptionPanel.add(includeSubstringLinksCheckBox, gridBagConstraints);

        strengthSubstringLinksLabel.setText("Strength substring links");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualOptionPanel.add(strengthSubstringLinksLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        strengthSubstringLinksSpinner.setEnabled(false);
        VisualOptionPanel.add(strengthSubstringLinksSpinner, gridBagConstraints);

        includeDistanceLinksCheckBox.setSelected(false);
        includeDistanceLinksCheckBox.setText("Include distance links");
        includeDistanceLinksCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                includeDistanceLinksCheckBoxStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        VisualOptionPanel.add(includeDistanceLinksCheckBox, gridBagConstraints);

        strengthDistanceLinksLabel.setText("Strength distance links");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualOptionPanel.add(strengthDistanceLinksLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        strengthDistanceLinksSpinner.setEnabled(false);
        VisualOptionPanel.add(strengthDistanceLinksSpinner, gridBagConstraints);

        maxDistanceLabel.setText("Maximum distance");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualOptionPanel.add(maxDistanceLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        maxDistanceSpinner.setEnabled(false);
        VisualOptionPanel.add(maxDistanceSpinner, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.6;
        add(VisualOptionPanel, gridBagConstraints);
    }

    private void includeDistanceLinksCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {
        if (includeDistanceLinksCheckBox.isSelected()) {
            strengthDistanceLinksSpinner.setEnabled(true);
            maxDistanceSpinner.setEnabled(true);
        } else {
            strengthDistanceLinksSpinner.setEnabled(false);
            maxDistanceSpinner.setEnabled(false);
        }
    }

    private void includeSubstringLinksCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {
        if (includeSubstringLinksCheckBox.isSelected()) {
            strengthSubstringLinksSpinner.setEnabled(true);
        } else {
            strengthSubstringLinksSpinner.setEnabled(false);
        }
    }
    
    private void showAllModuleCheckBoxCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {
        if (showAllModuleCheckBox.isSelected()) {
        	NumModuleTextField.setEnabled(false);
        	NumModuleShowLabel.setEnabled(false);
        } else {
        	NumModuleTextField.setEnabled(true);
        	NumModuleShowLabel.setEnabled(true);
        }
    }
    
    private void includeDefinitionLinksCheckBoxStateChanged(ChangeEvent evt) {
        if (includeDefinitionLinksCheckBox.isSelected()) {
            strengthDefinitionLinksSpinner.setEnabled(true);
            includeDefinitionResourcesComboBox.setEnabled(true);
        } else {
            strengthDefinitionLinksSpinner.setEnabled(false);
            includeDefinitionResourcesComboBox.setEnabled(false);
        }
    }
    
    private void includePropertyLinksCheckBoxStateChanged(ChangeEvent evt) {
        if (includePropertyLinksCheckBox.isSelected()) {
            strengthPropertyLinksSpinner.setEnabled(true);
        } else {
            strengthPropertyLinksSpinner.setEnabled(false);
        }
    }
    
    private void includeSubclassLinksCheckBoxStateChanged(ChangeEvent evt) {
        if (includeSubclassLinksCheckBox.isSelected()) {
            strengthSubclassLinksSpinner.setEnabled(true);
        } else {
            strengthSubclassLinksSpinner.setEnabled(false);
        }
    }
    
    public static javax.swing.JButton ExecuteButton;
    public static javax.swing.JCheckBox includeDefinitionLinksCheckBox;
    public static javax.swing.JComboBox includeDefinitionResourcesComboBox;
    private javax.swing.JLabel TypeofAlgorithmLabel;
    private javax.swing.JLabel includeDefinitionResourcesLabel;
    public static javax.swing.JCheckBox includeDistanceLinksCheckBox;
    public static javax.swing.JCheckBox includePropertyLinksCheckBox;
    public static javax.swing.JCheckBox includeSubclassLinksCheckBox;
    public static javax.swing.JCheckBox includeSubstringLinksCheckBox;
    public static javax.swing.JComboBox linkTypeComboBox;
    private javax.swing.JLabel linkTypeLabel;
    private javax.swing.JLabel maxDistanceLabel;
    public static javax.swing.JSpinner maxDistanceSpinner;
    public static javax.swing.JButton ontologyFileChooserButton;
    private javax.swing.JLabel ontologyFileLabel;
    public static javax.swing.JTextField ontologyFileTextField;
    private javax.swing.JFileChooser openOntologyFileChooser;
    private javax.swing.JPanel RunPanel;
    private javax.swing.JPanel OntologySelectionPanel;
    private javax.swing.JPanel RequiredArgumentsPanel;
    private javax.swing.JFileChooser saveFileChooser;
    private javax.swing.JLabel strengthDefinitionLinksLabel;
    public static javax.swing.JSpinner strengthDefinitionLinksSpinner;
    private javax.swing.JLabel strengthDistanceLinksLabel;
    public static javax.swing.JSpinner strengthDistanceLinksSpinner;
    private javax.swing.JLabel strengthPropertyLinksLabel;
    public static javax.swing.JSpinner strengthPropertyLinksSpinner;
    private javax.swing.JLabel strengthSubclassLinksLabel;
    public static javax.swing.JSpinner strengthSubclassLinksSpinner;
    private javax.swing.JLabel strengthSubstringLinksLabel;
    public static javax.swing.JSpinner strengthSubstringLinksSpinner;
    private javax.swing.JComboBox ListTypeComboBox;
    private javax.swing.JLabel NumCHLabel;
    private javax.swing.JLabel EmptyLabel;
    private javax.swing.JLabel EmptyLabel2;
    public static javax.swing.JTextField NumCHTextField;
    public static javax.swing.JButton SuggestButton;
    public static javax.swing.JButton SaveButton;
    public static javax.swing.JButton ViewGraphButton;
    public static javax.swing.JButton ViewAllGraphButton; 
    public static javax.swing.JButton ViewHierarchyButton;
    private javax.swing.ImageIcon pic1 ;
    private javax.swing.JPanel PicturePanel;
    private javax.swing.JPanel OutputPanel;
    private javax.swing.JPanel VisualOptionPanel;
    private javax.swing.JScrollPane OutputScrollPane;
    public static javax.swing.JTextArea OutputPartitioningTextArea;
    public static javax.swing.JTextField NumModuleTextField;
    private javax.swing.JLabel NumModuleShowLabel;        
    public static javax.swing.JCheckBox showAllModuleCheckBox;
    final static JProgressBar progressBar = new JProgressBar(1, 100);
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
}
