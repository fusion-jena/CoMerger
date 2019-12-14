package fusion.comerger.general.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import fusion.comerger.algorithm.merger.genericMerge.MergeApp;
import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Cleaning_Allthings;
import fusion.comerger.general.cc.Coordinator;
import fusion.comerger.general.visualization.Visualization;

public class MergingPanel extends JPanel {
	public static String NameAddressOnt; 
	public static ArrayList<String> MergeModels;
    private static final long serialVersionUID = 1L;
    public static String numAlgorithm;

    public MergingPanel() {
        super();
        setPreferredSize(new Dimension(600, 800));
        setLayout(new BorderLayout());
        initComponents();
        MergeModels =new ArrayList<String>();
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        
        String[] MergeTypeComboBoxTitle = new String[] {"GenericMerge", "GraphMerge", "APIMerge" , "MatchingMerge"};
        String[] SimTypeComboBoxTitle = new String[] {"IC_Lin", "StructureSim", "SemSim"};
        
        openOntologyFileChooser = new javax.swing.JFileChooser();
        MergeButton = new javax.swing.JButton();
        plusbutton = new javax.swing.JButton();
        minusbutton = new javax.swing.JButton();
        ViewGraphButton = new javax.swing.JButton();
        SaveButton = new javax.swing.JButton();
        
        PicturePanel = new javax.swing.JPanel();                  PicturePanel.setPreferredSize(new Dimension(600, 120)); PicturePanel.setMaximumSize(new Dimension(600, 120)); PicturePanel.setMinimumSize(new Dimension(600, 120));
        OntologySelectionPanel = new javax.swing.JPanel();        OntologySelectionPanel.setPreferredSize(new Dimension(600, 80)); OntologySelectionPanel.setMaximumSize(new Dimension(600, 80)); OntologySelectionPanel.setMinimumSize(new Dimension(600, 80));
        MeasureSelPanel = new javax.swing.JPanel();               MeasureSelPanel.setPreferredSize(new Dimension(600, 70)); MeasureSelPanel.setMaximumSize(new Dimension(600, 70)); MeasureSelPanel.setMinimumSize(new Dimension(600, 70));
        OutputPanel = new javax.swing.JPanel();                   OutputPanel.setPreferredSize(new Dimension(600, 100));  OutputPanel.setMaximumSize(new Dimension(600, 100)); OutputPanel.setMinimumSize(new Dimension(600, 100));
        VisualViewPanel = new javax.swing.JPanel();               VisualViewPanel.setPreferredSize(new Dimension(600, 100));   VisualViewPanel.setMaximumSize(new Dimension(600, 100));   VisualViewPanel.setMinimumSize(new Dimension(600, 100));
        
        MergeTypeComboBox = new JComboBox<>(MergeTypeComboBoxTitle);
        SimTypeComboBox = new JComboBox<>(SimTypeComboBoxTitle);
        TypeOfMergeLabel = new javax.swing.JLabel();
        TypeOfSimLabel = new javax.swing.JLabel();  
        ontologyFileLabel = new javax.swing.JLabel();
        ontologyFileTextField = new javax.swing.JTextArea();   //ontologyFileTextField.setSize(new Dimension (1500,1500));    
        EmptyLabel = new javax.swing.JLabel();
        pic1 = new ImageIcon("fig/logo1.png");
        OutputScrollPane = new javax.swing.JScrollPane();
        inputScrollPane = new javax.swing.JScrollPane();
        MergingPanel.OutputPartitioningTextArea = new javax.swing.JTextArea();  
        
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
        includeSubclassLinksCheckBox = new javax.swing.JCheckBox();
        strengthSubclassLinksLabel = new javax.swing.JLabel();
        strengthSubclassLinksSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(1, 0.0, 10.0, 0.5));
        includePropertyLinksCheckBox = new javax.swing.JCheckBox();
        strengthPropertyLinksLabel = new javax.swing.JLabel();
        strengthPropertyLinksSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(1, 0.0, 10.0, 0.5));
        includeDefinitionLinksCheckBox = new javax.swing.JCheckBox();
        strengthDefinitionLinksLabel = new javax.swing.JLabel();
        strengthDefinitionLinksSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(1, 0.0, 10.0, 0.5));
        
               
        
        
        openOntologyFileChooser.setDialogTitle("Open");
    
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
        
        ontologyFileLabel.setText("Ontologies to merge");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        OntologySelectionPanel.add(ontologyFileLabel, gridBagConstraints);

        plusbutton.setText(" + ");
        plusbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlusbuttonActionPerformed(); 
            }
        });
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        OntologySelectionPanel.add(plusbutton, gridBagConstraints);
               
        minusbutton.setText(" - ");
        minusbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MinusbuttonActionPerformed(); 
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        OntologySelectionPanel.add(minusbutton, gridBagConstraints);
               

        //ontologyFileTextField.setLineWrap(true);
        ontologyFileTextField.setCaretPosition(0);
        //ontologyFileTextField.setWrapStyleWord(true);
        ontologyFileTextField.setText("");
        inputScrollPane.setViewportView(MergingPanel.ontologyFileTextField);
        
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 3.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        OntologySelectionPanel.add(inputScrollPane, gridBagConstraints);
        
               
        MergeButton.setText("Merge...");
        MergeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	MergeButtonActionPerformed(evt); 
            }
        });
 
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 0, 1);
        OntologySelectionPanel.add(MergeButton, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(OntologySelectionPanel, gridBagConstraints);
        
        
        MeasureSelPanel.setLayout(new java.awt.GridBagLayout());
        MeasureSelPanel.setBorder(new javax.swing.border.TitledBorder("Measure Selection"));

        TypeOfSimLabel.setText("Type of Similarity");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        MeasureSelPanel.add(TypeOfSimLabel, gridBagConstraints);
        
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        MeasureSelPanel.add(SimTypeComboBox, gridBagConstraints);

        SimTypeComboBox.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
            	//Noting yet
            }
        });
         
        TypeOfMergeLabel.setText("       Type of Algorithm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        MeasureSelPanel.add(TypeOfMergeLabel, gridBagConstraints);
        
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        MeasureSelPanel.add(MergeTypeComboBox, gridBagConstraints);

        MergeTypeComboBox.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
            	//Noting yet
            }
        });
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        EmptyLabel.setText("                  ");
        MeasureSelPanel.add(EmptyLabel, gridBagConstraints);
        
        

        SaveButton.setText("Save Output");
        SaveButton.setEnabled(false);
        //Write its function
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        MeasureSelPanel.add(SaveButton, gridBagConstraints);
        
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(MeasureSelPanel, gridBagConstraints);
        
        OutputPanel.setLayout(new java.awt.GridBagLayout());

        OutputPanel.setBorder(new javax.swing.border.TitledBorder("Output"));
        
        
        MergingPanel.OutputPartitioningTextArea.setEditable(false);
        MergingPanel.OutputPartitioningTextArea.setLineWrap(true);
        MergingPanel.OutputPartitioningTextArea.setCaretPosition(0);
        MergingPanel.OutputPartitioningTextArea.setWrapStyleWord(true);
        MergingPanel.OutputPartitioningTextArea.setText("Here is for Logs messages "+'\n'+"To see the visual output, please push the ''View Output'' button. ");
        OutputScrollPane.setViewportView(MergingPanel.OutputPartitioningTextArea);

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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.4;
        add(OutputPanel, gridBagConstraints); 
        
        //#
        VisualViewPanel.setLayout(new java.awt.GridBagLayout());
        
        VisualViewPanel.setBorder(new javax.swing.border.TitledBorder("Visual View"));
        
        
        ViewGraphButton.setText("View Output");
        ViewGraphButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        VisualViewPanel.add(ViewGraphButton, gridBagConstraints);
          
        ViewGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            		LockedGUI();
            		Coordinator.argVisulization = 1;
            		Visualization.Run(Coordinator.argVisulization);  //1:shows that visualization called from merging panel
            		Un_LockedGUI();
            }
        });
        
               
        linkTypeLabel.setText("Link type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        VisualViewPanel.add(linkTypeLabel, gridBagConstraints);

        linkTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UniEdge", "Edge" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        linkTypeComboBox.setSelectedIndex(1);
        VisualViewPanel.add(linkTypeComboBox, gridBagConstraints);
        

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
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void MergeButtonActionPerformed(ActionEvent evt) {
    	
    	Cleaning_Allthings.clean(1);
    	Coordinator.FinishPartitioning = false;
    	ViewGraphButton.setEnabled(false);
    	SaveButton.setEnabled(false);
    	
    	//check for the existence of ontologies
    	/*NameAddressOnt = ontologyFileTextField.getText().trim();
    	File ontologyFile = new File(NameAddressOnt);
    	if (NameAddressOnt.length() <= 0){
  		  JOptionPane.showMessageDialog(null, "Please Select your Ontology File", "Error", JOptionPane.ERROR_MESSAGE);
  		  return;
  	    } else if (!ontologyFile.exists()) {
            JOptionPane.showMessageDialog(null,"The ontology file does not exist.", "Error",JOptionPane.ERROR_MESSAGE);
            return;
  	    }
  	     */
        LockedGUI();
        
        //if (NameAddressOnt.length() > 0 ) {
        	Thread mergeProcess = new Thread()
            {
                @Override
                public void run()
                {
                	
                	this.setName("Merge Process");
                	
            		String selectedType = (String) MergeTypeComboBox.getSelectedItem();
            		try {
						numAlgorithm = selectedType;
            			MergeApp.mainA(selectedType);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	
                	Un_LockedGUI();
                    ViewGraphButton.setEnabled(true);
                    SaveButton.setEnabled(true);
                }
            };
            mergeProcess.start(); 
            
        //}
    }
    

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
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
   
    private javax.swing.JComboBox MergeTypeComboBox;
    private javax.swing.JComboBox SimTypeComboBox;
    public static javax.swing.JButton MergeButton;
    public static javax.swing.JButton plusbutton;
    public static javax.swing.JButton minusbutton;
    public static javax.swing.JButton SaveButton;
    public static javax.swing.JButton ViewGraphButton;
    //public static javax.swing.JButton ontologyFileChooserButton;
    private javax.swing.JLabel ontologyFileLabel;
    public static javax.swing.JTextArea ontologyFileTextField;
    private javax.swing.JFileChooser openOntologyFileChooser;
    private javax.swing.JPanel OntologySelectionPanel;
    private javax.swing.JPanel MeasureSelPanel;
    private javax.swing.JLabel EmptyLabel;
    private javax.swing.JLabel TypeOfMergeLabel;
    private javax.swing.JLabel TypeOfSimLabel;
    private javax.swing.ImageIcon pic1 ;
    
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
    public static javax.swing.JCheckBox includeDefinitionLinksCheckBox;
    public static javax.swing.JComboBox includeDefinitionResourcesComboBox;
    private javax.swing.JLabel includeDefinitionResourcesLabel;
    public static javax.swing.JCheckBox includeDistanceLinksCheckBox;
    public static javax.swing.JCheckBox includePropertyLinksCheckBox;
    public static javax.swing.JCheckBox includeSubclassLinksCheckBox;
    public static javax.swing.JCheckBox includeSubstringLinksCheckBox;
    public static javax.swing.JComboBox linkTypeComboBox;
    private javax.swing.JLabel linkTypeLabel;
    private javax.swing.JLabel maxDistanceLabel;
    public static javax.swing.JSpinner maxDistanceSpinner;
    
    private javax.swing.JPanel PicturePanel;
    private javax.swing.JPanel OutputPanel;
    private javax.swing.JPanel VisualViewPanel;
    private javax.swing.JScrollPane OutputScrollPane;
    private javax.swing.JScrollPane inputScrollPane;
    public static javax.swing.JTextArea OutputPartitioningTextArea;
    final static JProgressBar progressBar = new JProgressBar(1, 100);
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void LockedGUI(){
    	MergeButton.setEnabled(false);
    	plusbutton.setEnabled(false);
    	minusbutton.setEnabled(false);
    	ViewGraphButton.setEnabled(false);
    	SaveButton.setEnabled(false);
    	progressBar.setStringPainted(true);
        progressBar.setString("I am running. Please wait!");
        progressBar.setIndeterminate(true);
    }

    private static void Un_LockedGUI(){
    	MergeButton.setEnabled(true);
    	plusbutton.setEnabled(true);
    	minusbutton.setEnabled(true);
    	SaveButton.setEnabled(true);
    	ViewGraphButton.setEnabled(true);
    	progressBar.setIndeterminate(false);
        progressBar.setString("Complete. Have fun!");
    }
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   private void PlusbuttonActionPerformed(){
  	 JFileChooser fileChooser = new JFileChooser(".");
  	 fileChooser.setMultiSelectionEnabled(true);
     fileChooser.setName("Open");
     fileChooser.setToolTipText("Please input a file");
     int rVal = fileChooser.showOpenDialog(null);
     File[] ontfiles = fileChooser.getSelectedFiles();
     if (ontfiles.length > 0){ //if ontfiles.length ==0, it means the user select "cancel" button.
    	 for(int i=0; i<ontfiles.length;i++){
    		 MergeModels.add(ontfiles[i].toString());
    		 ontologyFileTextField.append(ontfiles[i].toString() + "\n");
    	 }
     }

	   // if (MergeModels.size()>1) {
		//  JOptionPane.showMessageDialog(null, "Currently, OAPT can only merge two models at a time.", "Too many models", JOptionPane.ERROR_MESSAGE);
	  //}
   }
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   private void MinusbuttonActionPerformed (){
	   String comm = "";
	   if (MergeModels.size() < 1){
		   JOptionPane.showMessageDialog(null, "You do not have any ontolgies to be delete", "Error", JOptionPane.ERROR_MESSAGE);
	   }else{
		   for (int i=0; i<MergeModels.size(); i++){
			   comm = comm +  "\n Index = " + i +" :  " + MergeModels.get(i); 
		   }
		   String stridx= JOptionPane.showInputDialog("Please enter the index of your selected ontolgy \n " + comm);
	       if (stridx != null){ //if the user select "cancel", the showInputDialog return null
	    	   int idx = Integer.parseInt(stridx);
	    	   //TO DO: it is better it implements by map
		       ArrayList<String> tempMergeModels;
		       tempMergeModels =new ArrayList<String>();
		       ontologyFileTextField.setText(null);
		       for (int i=0; i<MergeModels.size(); i++){
		    	   if (i != idx) {
		    		   tempMergeModels.add(MergeModels.get(i));
		    		   ontologyFileTextField.append(MergeModels.get(i) + "\n");
		    	   }
		       }
		       ontologyFileTextField.repaint();
		       MergeModels = tempMergeModels;
	       }
	   }
	}
}