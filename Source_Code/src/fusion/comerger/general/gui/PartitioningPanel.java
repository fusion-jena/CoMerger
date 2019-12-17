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

public class PartitioningPanel extends JPanel {
	public static String NameAddressOnt; 

    private static final long serialVersionUID = 1L;

    public PartitioningPanel() {
        super();
        setPreferredSize(new Dimension(600, 800));
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        
        String[] ListTypeComboBoxTitle = new String[] {"SeeCOnt", "AxCOnt", "PBM"};
        
        openOntologyFileChooser = new javax.swing.JFileChooser();
        saveFileChooser = new javax.swing.JFileChooser();
        ontologyFileChooserButton = new javax.swing.JButton();
        ExecuteButton = new javax.swing.JButton();
        SuggestButton = new javax.swing.JButton();
        SaveButton = new javax.swing.JButton();
        ViewGraphButton = new javax.swing.JButton();
        ViewAllGraphButton = new javax.swing.JButton();
        ViewHierarchyButton = new javax.swing.JButton();
       
        PicturePanel = new javax.swing.JPanel();                  PicturePanel.setPreferredSize(new Dimension(600, 120)); PicturePanel.setMaximumSize(new Dimension(600, 120)); PicturePanel.setMinimumSize(new Dimension(600, 120));
        OntologySelectionPanel = new javax.swing.JPanel();        OntologySelectionPanel.setPreferredSize(new Dimension(600, 50)); OntologySelectionPanel.setMaximumSize(new Dimension(600, 50)); OntologySelectionPanel.setMinimumSize(new Dimension(600, 50));
        RequiredArgumentsPanel = new javax.swing.JPanel();       RequiredArgumentsPanel.setPreferredSize(new Dimension(600, 50)); RequiredArgumentsPanel.setMaximumSize(new Dimension(600, 50)); RequiredArgumentsPanel.setMinimumSize(new Dimension(600, 50));
        RunPanel = new javax.swing.JPanel();                      RunPanel.setPreferredSize(new Dimension(600, 50));  RunPanel.setMaximumSize(new Dimension(600, 50));  RunPanel.setMinimumSize(new Dimension(600, 50));
        OutputPanel = new javax.swing.JPanel();                   OutputPanel.setPreferredSize(new Dimension(600, 100));  OutputPanel.setMaximumSize(new Dimension(600, 100)); OutputPanel.setMinimumSize(new Dimension(600, 100));
        VisualViewPanel = new javax.swing.JPanel();               VisualViewPanel.setPreferredSize(new Dimension(600, 100));   VisualViewPanel.setMaximumSize(new Dimension(600, 100));   VisualViewPanel.setMinimumSize(new Dimension(600, 100));
        
        ontologyFileLabel = new javax.swing.JLabel();
        ontologyFileTextField = new javax.swing.JTextField();
        TypeofAlgorithmLabel = new javax.swing.JLabel();        
        includeSubclassLinksCheckBox = new javax.swing.JCheckBox();
        strengthSubclassLinksLabel = new javax.swing.JLabel();
        NumModuleTextField = new javax.swing.JTextField();     NumModuleTextField.setPreferredSize(new Dimension(60, 20));   NumModuleTextField.setMaximumSize(new Dimension(60, 20));   NumModuleTextField.setMinimumSize(new Dimension(60, 20));
        NumModuleShowLabel = new javax.swing.JLabel();        
        showAllModuleCheckBox = new javax.swing.JCheckBox();
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
        
        ListTypeComboBox = new JComboBox<>(ListTypeComboBoxTitle);
        NumCHLabel= new javax.swing.JLabel();
        EmptyLabel = new javax.swing.JLabel();
        EmptyLabel2 = new javax.swing.JLabel();
        NumCHTextField = new javax.swing.JTextField();
        pic1 = new ImageIcon("fig/logo1.png");
        OutputScrollPane = new javax.swing.JScrollPane();
        PartitioningPanel.OutputPartitioningTextArea = new javax.swing.JTextArea();      
        
        openOntologyFileChooser.setDialogTitle("Open");
        saveFileChooser.setDialogTitle("Save");
        saveFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);

        setLayout(new java.awt.GridBagLayout());
        PicturePanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        PicturePanel.add(new JLabel(pic1),gridBagConstraints);
        add(PicturePanel, gridBagConstraints);
        
        setLayout(new java.awt.GridBagLayout());

        OntologySelectionPanel.setLayout(new java.awt.GridBagLayout());
        OntologySelectionPanel.setBorder(new javax.swing.border.TitledBorder("Ontology Select"));
        
        ontologyFileLabel.setText("Ontology File:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        OntologySelectionPanel.add(ontologyFileLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        OntologySelectionPanel.add(ontologyFileTextField, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        EmptyLabel2.setText("                            ");
        OntologySelectionPanel.add(EmptyLabel2, gridBagConstraints);
    
        
        ontologyFileChooserButton.setText("Browse...");
        ontologyFileChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ontologyFileChooserButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 0, 1);
        OntologySelectionPanel.add(ontologyFileChooserButton, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(OntologySelectionPanel, gridBagConstraints);
        
        RequiredArgumentsPanel.setLayout(new java.awt.GridBagLayout());
        RequiredArgumentsPanel.setBorder(new javax.swing.border.TitledBorder("Required Arguments"));
        
        TypeofAlgorithmLabel.setText("Type of Algorithm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        RequiredArgumentsPanel.add(TypeofAlgorithmLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        RequiredArgumentsPanel.add(ListTypeComboBox, gridBagConstraints);

        ListTypeComboBox.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
            	if (ListTypeComboBox.getSelectedItem().toString().equals("AxCOnt") || ListTypeComboBox.getSelectedItem().toString().equals("PBM")){
            			NumCHLabel.setEnabled(false);
            			NumCHTextField.setEnabled(false);
            	}else{
            		NumCHLabel.setEnabled(true);
        			NumCHTextField.setEnabled(true);
            	}
            }
        });
        
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        EmptyLabel.setText("                            ");
        RequiredArgumentsPanel.add(EmptyLabel, gridBagConstraints);
    
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        NumCHLabel.setText("Number of Modules");
        RequiredArgumentsPanel.add(NumCHLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        RequiredArgumentsPanel.add(NumCHTextField, gridBagConstraints);
        
        SuggestButton.setText("Optimal Number of Modules Recommender");
        SuggestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	SuggestButtonActionPerformed(evt); 
            }
        });
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        RequiredArgumentsPanel.add(SuggestButton, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(RequiredArgumentsPanel, gridBagConstraints);

        RunPanel.setLayout(new java.awt.GridBagLayout());

        RunPanel.setBorder(new javax.swing.border.TitledBorder("Run"));
       
        progressBar.setPreferredSize(new Dimension(750, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx=2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        RunPanel.add(progressBar, gridBagConstraints);

        ExecuteButton.setText("Execute");
        ExecuteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExecuteButtonActionPerformed(evt); 
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        RunPanel.add(ExecuteButton, gridBagConstraints);
               
        SaveButton.setText("Save Output");
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	JFileChooser fileChooser = new JFileChooser(".");
                fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fileChooser.setName("Save as the RDF/XML format");
                fileChooser.setToolTipText("Please input a file to save");
                int rVal = fileChooser.showSaveDialog(null);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile(); 
                    String fp = file.getAbsolutePath();
                    for (int cid=0; cid<Coordinator.KNumCH; cid++){
                    	String srcName = BuildModel.wd + BuildModel.fn1.substring(0, BuildModel.fn1.length()-4) + "_module_" + cid + ".owl"; // Read our created modules
                    	//if the user does not determine the extension, we add it to the name of file
                    	String destName = null;
                    	if (fp.contains(".") == false) {
                    			destName = fp + cid + ".owl";
                    		}else{
                    		    String []iname = fp.split("\\."); 
                    			destName = iname[0] + cid + "." + iname[1];
                    		}
                	File src = new File(srcName);
                	File target = new File(destName);

                	try {
    					Files.copy(src.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    				} catch (IOException e) {
    					e.printStackTrace();
    				}

                }
                }
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        RunPanel.add(SaveButton, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(RunPanel, gridBagConstraints);

        
        OutputPanel.setLayout(new java.awt.GridBagLayout());

        OutputPanel.setBorder(new javax.swing.border.TitledBorder("Output"));
        
        PartitioningPanel.OutputPartitioningTextArea.setEditable(false);
        PartitioningPanel.OutputPartitioningTextArea.setLineWrap(true);
        PartitioningPanel.OutputPartitioningTextArea.setCaretPosition(0);
        PartitioningPanel.OutputPartitioningTextArea.setWrapStyleWord(true);
        PartitioningPanel.OutputPartitioningTextArea.setText(" "+'\n'+" ");
        OutputScrollPane.setViewportView(PartitioningPanel.OutputPartitioningTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        //to print in JtextArea the next two line should be work
       // PrintStream printStreamPartitioner = new PrintStream(new CustomOutputStreamm(PartitioningPanel.OutputPartitioningTextArea)); // It is for printing output in textbox, not in the console
       // System.setOut(printStreamPartitioner);
        OutputPanel.add(OutputScrollPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.4;
        add(OutputPanel, gridBagConstraints); 
       
        
        VisualViewPanel.setLayout(new java.awt.GridBagLayout());
        
        VisualViewPanel.setBorder(new javax.swing.border.TitledBorder("Visual View"));
        
        
        ViewHierarchyButton.setText("View Hierarchy");
        ViewHierarchyButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        VisualViewPanel.add(ViewHierarchyButton, gridBagConstraints);
          
        ViewHierarchyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
//            	#
            	Hierarchy.Run();
            }
        });
        
        ViewGraphButton.setText("View Graph");
        ViewGraphButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        VisualViewPanel.add(ViewGraphButton, gridBagConstraints);
          
        ViewGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if (Coordinator.FinishPartitioning== true){
            		LockedGUI();
            		Coordinator.levelDetail = Integer.parseInt(JOptionPane.showInputDialog("Enter level's detail of showing ontology (1 means all, 2 means half of the nodes)"));
            		Coordinator.argVisulization = 0;
            		Visualization.Run(Coordinator.argVisulization); //0:shows that visualization called from partitioning panel
            		Un_LockedGUI();
            	}else{
            		JOptionPane.showMessageDialog(null, "Your selected ontology does not partition, please before seeing the visual output, do the partitioning job.", "Error", JOptionPane.INFORMATION_MESSAGE);
            	}
            }
        });
        
        
        ViewAllGraphButton.setText("View Whole Graph");
        ViewAllGraphButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        VisualViewPanel.add(ViewAllGraphButton, gridBagConstraints);
          
        ViewAllGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if (Coordinator.FinishPartitioning== true){
            		LockedGUI();
            		Coordinator.levelDetail = Integer.parseInt(JOptionPane.showInputDialog("Enter level's detail of showing ontology (1 means all, 2 means half of the nodes)"));
            		Visualization2.Run(); 
            		Un_LockedGUI();
            	}else{
            		JOptionPane.showMessageDialog(null, "Your selected ontology does not partition, please before seeing the visual output, do the partitioning job.", "Error", JOptionPane.INFORMATION_MESSAGE);
            	}
            }
        });
        
        
        showAllModuleCheckBox.setText("Show All Modules");
        showAllModuleCheckBox.setSelected(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        VisualViewPanel.add(showAllModuleCheckBox, gridBagConstraints);
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
        VisualViewPanel.add(NumModuleShowLabel, gridBagConstraints);

        NumModuleTextField.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        VisualViewPanel.add(NumModuleTextField, gridBagConstraints);

       
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
        VisualViewPanel.add(includeSubclassLinksCheckBox, gridBagConstraints);

        strengthSubclassLinksLabel.setText("Strength subclass links");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualViewPanel.add(strengthSubclassLinksLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        strengthSubclassLinksSpinner.setEnabled(true);
        VisualViewPanel.add(strengthSubclassLinksSpinner, gridBagConstraints);


        linkTypeLabel.setText("Link type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        VisualViewPanel.add(linkTypeLabel, gridBagConstraints);

        linkTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UniEdge", "Edge" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        linkTypeComboBox.setSelectedIndex(1);
        VisualViewPanel.add(linkTypeComboBox, gridBagConstraints);
        
        
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
        VisualViewPanel.add(includePropertyLinksCheckBox, gridBagConstraints);

        strengthPropertyLinksLabel.setText("Strength property links");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualViewPanel.add(strengthPropertyLinksLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        strengthPropertyLinksSpinner.setEnabled(true);
        VisualViewPanel.add(strengthPropertyLinksSpinner, gridBagConstraints);

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
        VisualViewPanel.add(includeDefinitionLinksCheckBox, gridBagConstraints);

        strengthDefinitionLinksLabel.setText("Strength definition links");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualViewPanel.add(strengthDefinitionLinksLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        strengthDefinitionLinksSpinner.setEnabled(false);
        VisualViewPanel.add(strengthDefinitionLinksSpinner, gridBagConstraints);

        includeDefinitionResourcesLabel.setText("Included definition resources");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualViewPanel.add(includeDefinitionResourcesLabel, gridBagConstraints);

        includeDefinitionResourcesComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Only properties", "All resources" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        includeDefinitionResourcesComboBox.setEnabled(false);
        VisualViewPanel.add(includeDefinitionResourcesComboBox, gridBagConstraints);

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
        VisualViewPanel.add(includeSubstringLinksCheckBox, gridBagConstraints);

        strengthSubstringLinksLabel.setText("Strength substring links");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualViewPanel.add(strengthSubstringLinksLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        strengthSubstringLinksSpinner.setEnabled(false);
        VisualViewPanel.add(strengthSubstringLinksSpinner, gridBagConstraints);

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
        VisualViewPanel.add(includeDistanceLinksCheckBox, gridBagConstraints);

        strengthDistanceLinksLabel.setText("Strength distance links");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualViewPanel.add(strengthDistanceLinksLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        strengthDistanceLinksSpinner.setEnabled(false);
        VisualViewPanel.add(strengthDistanceLinksSpinner, gridBagConstraints);

        maxDistanceLabel.setText("Maximum distance");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        VisualViewPanel.add(maxDistanceLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        maxDistanceSpinner.setEnabled(false);
        VisualViewPanel.add(maxDistanceSpinner, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.6;
        add(VisualViewPanel, gridBagConstraints);
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
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void ExecuteButtonActionPerformed(ActionEvent evt) {
    	
    	Cleaning_Allthings.clean(1);
    	Coordinator.FinishPartitioning = false;
    	ViewGraphButton.setEnabled(false);
    	ViewAllGraphButton.setEnabled(false);
    	ViewHierarchyButton.setEnabled(false);
    	
    	NameAddressOnt = ontologyFileTextField.getText().trim();
    	File ontologyFile = new File(NameAddressOnt);
    	if (NameAddressOnt.length() <= 0){
  		  JOptionPane.showMessageDialog(null, "Please Select your Ontology File", "Error", JOptionPane.ERROR_MESSAGE);
  		  return;
  	    } else if (!ontologyFile.exists()) {
            JOptionPane.showMessageDialog(null,"The ontology file does not exist.", "Error",JOptionPane.ERROR_MESSAGE);
            return;
  	    }
  	    
        final String StrNumCH = NumCHTextField.getText().trim();
        if (StrNumCH.length() <= 0 && ListTypeComboBox.getSelectedItem() == "SeeCOnt"){
    		  JOptionPane.showMessageDialog(null, "Please Input the number of Cluster", "Error", JOptionPane.ERROR_MESSAGE);
    		  return;
    	    }
        final int NumCH;
        if (StrNumCH.length()>0) {
        	NumCH=Integer.parseInt(StrNumCH);
        }else{
        	NumCH=0;			
        	}
        Coordinator.KNumCH = NumCH;
                                       
        if (BuildModel.analysisTest == false){
        	JOptionPane.showMessageDialog(null, "Your selected ontology is not analysis, Please at first do it", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
  
        LockedGUI();
        MainFrame.tabbedPane.setEnabledAt(2, false);
        
        if (NameAddressOnt.length() > 0 ) {
        	Thread partition = new Thread()
            {
                @Override
                public void run()
                {
                	
                	this.setName("partition");
                	String selectedType = (String) ListTypeComboBox.getSelectedItem();      //get the selected item
                    Configuration config = new Configuration();
                    config.init();
                    String fp1 = NameAddressOnt;
                    if (!fp1.startsWith("file:")) {
                        fp1 = "" + fp1;
                    }
                    Controller controllerObj = new Controller(fp1);
                    controllerObj.InitialRun(selectedType);  /////////*****main part*******////////////
                    
                    MainFrame.model1 = controllerObj.getOntModel();
                    MainFrame.tabbedPane.setEnabledAt(1, true);
                    
                    Coordinator.FinishPartitioning = true; // By this flag, the View Graph button can work
                    ViewGraphButton.setEnabled(true);
                    ViewAllGraphButton.setEnabled(true);
                    ViewHierarchyButton.setEnabled(true);
                    SaveButton.setEnabled(true);
                    Un_LockedGUI();
                    MainFrame.tabbedPane.setEnabledAt(2, true);
                }
            };
            partition.start(); 
            
        }else{
        	JOptionPane.showMessageDialog(null, "Please Enter the Parameters (ontology file and the number of clusters)", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        } 
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void SuggestButtonActionPerformed(ActionEvent evt) {
    	Cleaning_Allthings.clean(1); // 1 is the index of the "Partitioning" tab
    	
    	int optimalNumCH=1;
    	NameAddressOnt = ontologyFileTextField.getText().trim();
    	File ontologyFile = new File(NameAddressOnt);
    	if (NameAddressOnt.length() <= 0){
    		  JOptionPane.showMessageDialog(null, "Please Select your Ontology File", "Error", JOptionPane.ERROR_MESSAGE);
    		  return;
    	} else if (!ontologyFile.exists()) {
            JOptionPane.showMessageDialog(null,"The ontology file does not exist.", "Error",JOptionPane.ERROR_MESSAGE);
            return;
    	}else{
    		  LockedGUI();
    		  double start=System.currentTimeMillis();
    		  //optimalNumCH = FindOptimalCluster.FindOptimalClusterFunc(NameAddressOnt);
    		  optimalNumCH = FindOptimalClusterIntractive.FindOptimalClusterFunc(NameAddressOnt);	  
    		  double end1=System.currentTimeMillis();
    		  //System.out.println("The optimal num. of partition time---->"+(end1-start)*.001+"\t sec");

    		  SelectCH.Run();

    		  NumCHTextField.setText(String.valueOf(optimalNumCH));
    		  Un_LockedGUI();
    }

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       private void ontologyFileChooserButtonActionPerformed(ActionEvent evt) {
    	 JFileChooser fileChooser = new JFileChooser(".");
         fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
         fileChooser.setName("Open");
         fileChooser.setToolTipText("Please input a file");
         int rVal = fileChooser.showOpenDialog(null);
         if (rVal == JFileChooser.APPROVE_OPTION) {
        	 ontologyFileTextField.setText(fileChooser.getSelectedFile().toString());
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
    private javax.swing.JPanel VisualViewPanel;
    private javax.swing.JScrollPane OutputScrollPane;
    public static javax.swing.JTextArea OutputPartitioningTextArea;
    public static javax.swing.JTextField NumModuleTextField;
    private javax.swing.JLabel NumModuleShowLabel;        
    public static javax.swing.JCheckBox showAllModuleCheckBox;
    final static JProgressBar progressBar = new JProgressBar(1, 100);
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void LockedGUI(){
    	SuggestButton.setEnabled(false);
    	SaveButton.setEnabled(false);
    	ExecuteButton.setEnabled(false);
    	ViewGraphButton.setEnabled(false);
    	ViewHierarchyButton.setEnabled(false);
    	SaveButton.setEnabled(false);
    	ontologyFileChooserButton.setEnabled(false);
    	progressBar.setStringPainted(true);
        progressBar.setString("I am running. Please wait!");
        progressBar.setIndeterminate(true);
        
    }

    public static void Un_LockedGUI(){
    	SuggestButton.setEnabled(true);
    	SaveButton.setEnabled(true);
    	ExecuteButton.setEnabled(true);
    	ViewGraphButton.setEnabled(true);
    	ViewAllGraphButton.setEnabled(true);
    	ViewHierarchyButton.setEnabled(true);
    	ontologyFileChooserButton.setEnabled(true);
    	progressBar.setIndeterminate(false);
        progressBar.setString("Complete. Have fun!");
        
    }

}
