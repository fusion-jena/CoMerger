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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;

import fusion.comerger.general.analysis.AnalyzingTest;
import fusion.comerger.general.analysis.CompareTest;
import fusion.comerger.general.cc.BuildModel;
import fusion.comerger.general.cc.Cleaning_Allthings;
import fusion.comerger.general.cc.Configuration;
import fusion.comerger.general.cc.Controller;


public class AnalysisPanel extends JPanel {
	public static String NameAddressOnt1;
	public static String NameAddressOnt2; 
    boolean analysisTest = false;
    double RichValue = 0;
    double richThreshold = 0.5;
    public static final JTabbedPane outputPane = new JTabbedPane();
    private static final long serialVersionUID = 1L;

    public AnalysisPanel() {
        super();
        setPreferredSize(new Dimension(600, 800));
        setLayout(new BorderLayout());
        
        initComponents();
    }
    
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
       
        openOntologyFileChooser1 = new javax.swing.JFileChooser();
        openOntologyFileChooser2 = new javax.swing.JFileChooser();
        ontologyFileChooserButton1 = new javax.swing.JButton();
        ontologyFileChooserButton2 = new javax.swing.JButton();
        AnalysisButton = new javax.swing.JButton();
        CompareButton = new javax.swing.JButton();
        PicturePanel = new javax.swing.JPanel();                  PicturePanel.setPreferredSize(new Dimension(600, 120)); PicturePanel.setMaximumSize(new Dimension(600, 120)); PicturePanel.setMinimumSize(new Dimension(600, 120));
        OntologySelectionPanel = new javax.swing.JPanel();        OntologySelectionPanel.setPreferredSize(new Dimension(600, 80)); OntologySelectionPanel.setMaximumSize(new Dimension(600, 80)); OntologySelectionPanel.setMinimumSize(new Dimension(600, 80));
        RunPanel = new javax.swing.JPanel();                      RunPanel.setPreferredSize(new Dimension(600, 110));  RunPanel.setMaximumSize(new Dimension(600, 110));  RunPanel.setMinimumSize(new Dimension(600, 110));
        RunPanel_1 = new javax.swing.JPanel();                    RunPanel_1.setPreferredSize(new Dimension(600, 40));  RunPanel_1.setMaximumSize(new Dimension(600, 40));  RunPanel_1.setMinimumSize(new Dimension(600, 40));
        RunPanel_2 = new javax.swing.JPanel();                    RunPanel_2.setPreferredSize(new Dimension(600, 40));  RunPanel_2.setMaximumSize(new Dimension(600, 40));  RunPanel_2.setMinimumSize(new Dimension(600, 40));
        OutputPanel = new javax.swing.JPanel();                   OutputPanel.setPreferredSize(new Dimension(600, 100));  OutputPanel.setMaximumSize(new Dimension(600, 100)); OutputPanel.setMinimumSize(new Dimension(600, 100));
        outputPane.setSize(new Dimension(800, 220)); 			  outputPane.setPreferredSize(new Dimension(800, 220)); outputPane.setMaximumSize(new Dimension(800, 220)); outputPane.setMinimumSize(new Dimension(800, 220));
        ontologyFileLabel1 = new javax.swing.JLabel();
        ontologyFileLabel2 = new javax.swing.JLabel();
        ontologyFileTextField1 = new javax.swing.JTextField();     
        ontologyFileTextField2 = new javax.swing.JTextField();
        EmptyLabel1 = new javax.swing.JLabel();     
        EmptyLabel2 = new javax.swing.JLabel();
        pic1 = new ImageIcon("fig/logo3.png");
        GeneralScrollPane1 = new javax.swing.JScrollPane();       GeneralScrollPane1.setPreferredSize(new Dimension(750, 320));GeneralScrollPane1.setSize(new Dimension(750, 320)); GeneralScrollPane1.setMaximumSize(new Dimension(750, 320)); GeneralScrollPane1.setMinimumSize(new Dimension(750, 320));
        GeneralScrollPane2 = new javax.swing.JScrollPane();       GeneralScrollPane2.setPreferredSize(new Dimension(750, 320));GeneralScrollPane2.setSize(new Dimension(750, 320)); GeneralScrollPane2.setMaximumSize(new Dimension(750, 320)); GeneralScrollPane2.setMinimumSize(new Dimension(750, 320));
        ConsistencyScrollPane1 = new javax.swing.JScrollPane();   ConsistencyScrollPane1.setPreferredSize(new Dimension(750, 320)); ConsistencyScrollPane1.setSize(new Dimension(750, 320)); ConsistencyScrollPane1.setMaximumSize(new Dimension(750, 320)); ConsistencyScrollPane1.setMinimumSize(new Dimension(750, 320));
        ConsistencyScrollPane2 = new javax.swing.JScrollPane();   ConsistencyScrollPane2.setPreferredSize(new Dimension(750, 320)); ConsistencyScrollPane2.setSize(new Dimension(750, 320)); ConsistencyScrollPane2.setMaximumSize(new Dimension(750, 320)); ConsistencyScrollPane2.setMinimumSize(new Dimension(750, 320));
        RichnessScrollPane1 = new javax.swing.JScrollPane();      RichnessScrollPane1.setPreferredSize(new Dimension(750, 320));RichnessScrollPane1.setSize(new Dimension(750, 320)); RichnessScrollPane1.setMaximumSize(new Dimension(750, 320)); RichnessScrollPane1.setMinimumSize(new Dimension(750, 320));
        RichnessScrollPane2 = new javax.swing.JScrollPane();      RichnessScrollPane2.setPreferredSize(new Dimension(750, 320));RichnessScrollPane2.setSize(new Dimension(750, 320)); RichnessScrollPane2.setMaximumSize(new Dimension(750, 320)); RichnessScrollPane2.setMinimumSize(new Dimension(750, 320));
        GeneralTextArea1 = new javax.swing.JTextArea();          //GeneralTextArea.setPreferredSize(new Dimension(750, 320));GeneralTextArea.setSize(new Dimension(750, 320)); GeneralTextArea.setMaximumSize(new Dimension(750, 320)); GeneralTextArea.setMinimumSize(new Dimension(750, 320));
        GeneralTextArea2 = new javax.swing.JTextArea();
        ConsistencyTextArea1 = new javax.swing.JTextArea();
        ConsistencyTextArea2 = new javax.swing.JTextArea();
        RichnessTextArea1 = new javax.swing.JTextArea();
        RichnessTextArea2 = new javax.swing.JTextArea();
      
        openOntologyFileChooser1.setDialogTitle("Open");
        openOntologyFileChooser2.setDialogTitle("Open");
        
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
        
        ontologyFileLabel1.setText("Ontology File 1:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        OntologySelectionPanel.add(ontologyFileLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        OntologySelectionPanel.add(ontologyFileTextField1, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        EmptyLabel1.setText("                   ");
        OntologySelectionPanel.add(EmptyLabel1, gridBagConstraints);
    
        
        ontologyFileChooserButton1.setText("Browse...");
        ontologyFileChooserButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ontologyFileChooserButtonActionPerformed1(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 0, 1);
        OntologySelectionPanel.add(ontologyFileChooserButton1, gridBagConstraints);

        ontologyFileLabel2.setText("Ontology File 2:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        OntologySelectionPanel.add(ontologyFileLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        OntologySelectionPanel.add(ontologyFileTextField2, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        EmptyLabel2.setText("                   ");
        OntologySelectionPanel.add(EmptyLabel2, gridBagConstraints);
    
        
        ontologyFileChooserButton2.setText("Browse...");
        ontologyFileChooserButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ontologyFileChooserButtonActionPerformed2(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 0, 1);
        OntologySelectionPanel.add(ontologyFileChooserButton2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(OntologySelectionPanel, gridBagConstraints);
        
    
        RunPanel.setLayout(new java.awt.GridBagLayout());
        RunPanel.setBorder(new javax.swing.border.TitledBorder("Run"));
       
        
        progressBar.setPreferredSize(new Dimension(800, 20));
        //progressBar.setBorder(new javax.swing.border.TitledBorder(""));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        //gridBagConstraints.weightx=6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        RunPanel_1.add(progressBar, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        RunPanel.add(RunPanel_1,gridBagConstraints);
        
        RunPanel_2.setLayout(new java.awt.GridBagLayout());
        
        AnalysisButton.setText("Analysis");
        AnalysisButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	AnalysisButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weightx=0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        RunPanel_2.add(AnalysisButton, gridBagConstraints);

        CompareButton.setText("Compare");
        CompareButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	CompareButtonButtonActionPerformed(evt); 
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weightx=0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        RunPanel_2.add(CompareButton, gridBagConstraints);
           
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        RunPanel.add(RunPanel_2,gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(RunPanel, gridBagConstraints);
        
        
        
        OutputPanel.setLayout(new java.awt.GridBagLayout());

        JPanel GeneralPanel1 = new JPanel();
    	JPanel ConsistencyPanel1 = new JPanel();
    	JPanel RichnessPanel1 = new JPanel();
    	JPanel GeneralPanel2 = new JPanel();
    	JPanel ConsistencyPanel2 = new JPanel();
    	JPanel RichnessPanel2 = new JPanel();
    	
    	
    	outputPane.addTab("General Info Ont1",  GeneralPanel1);
        outputPane.addTab("Consistency Ont1", ConsistencyPanel1);
        outputPane.addTab("Richness Ont1", RichnessPanel1);
        outputPane.addTab("General Info Ont2", GeneralPanel2);
        outputPane.addTab("Consistency Ont2", ConsistencyPanel2);
        outputPane.addTab("Richness Ont2", RichnessPanel2);
        
        outputPane.setEnabledAt(3, false);
        outputPane.setEnabledAt(4, false);
        outputPane.setEnabledAt(5, false);
        
        GeneralTextArea1.setEditable(false);
        GeneralTextArea1.setLineWrap(true);
        GeneralTextArea1.setCaretPosition(0);
        GeneralTextArea1.setWrapStyleWord(true);
        GeneralScrollPane1.setViewportView(GeneralTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 6;
        gridBagConstraints.weighty = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        GeneralPanel1.add(GeneralScrollPane1, gridBagConstraints);
        
        ConsistencyTextArea1.setEditable(false);
        ConsistencyTextArea1.setLineWrap(true);
        ConsistencyTextArea1.setCaretPosition(0);
        ConsistencyTextArea1.setWrapStyleWord(true);
        ConsistencyScrollPane1.setViewportView(ConsistencyTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        //gridBagConstraints.weightx = 1.0;
        //gridBagConstraints.weighty = 1.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        ConsistencyPanel1.add(ConsistencyScrollPane1, gridBagConstraints);

        RichnessTextArea1.setEditable(false);
        RichnessTextArea1.setLineWrap(true);
        RichnessTextArea1.setCaretPosition(0);
        RichnessTextArea1.setWrapStyleWord(true);
        RichnessScrollPane1.setViewportView(RichnessTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        //gridBagConstraints.weightx = 3d;
        //gridBagConstraints.weighty = 3d;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        RichnessPanel1.add(RichnessScrollPane1, gridBagConstraints);
        
                
        GeneralTextArea2.setEditable(false);
        GeneralTextArea2.setLineWrap(true);
        GeneralTextArea2.setCaretPosition(0);
        GeneralTextArea2.setWrapStyleWord(true);
        GeneralScrollPane2.setViewportView(GeneralTextArea2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 6;
        gridBagConstraints.weighty = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        GeneralPanel2.add(GeneralScrollPane2, gridBagConstraints);
        
        ConsistencyTextArea2.setEditable(false);
        ConsistencyTextArea2.setLineWrap(true);
        ConsistencyTextArea2.setCaretPosition(0);
        ConsistencyTextArea2.setWrapStyleWord(true);
        ConsistencyScrollPane2.setViewportView(ConsistencyTextArea2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        //gridBagConstraints.weightx = 1.0;
        //gridBagConstraints.weighty = 1.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        ConsistencyPanel2.add(ConsistencyScrollPane2, gridBagConstraints);

        RichnessTextArea2.setEditable(false);
        RichnessTextArea2.setLineWrap(true);
        RichnessTextArea2.setCaretPosition(0);
        RichnessTextArea2.setWrapStyleWord(true);
        RichnessScrollPane2.setViewportView(RichnessTextArea2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        //gridBagConstraints.weightx = 3d;
        //gridBagConstraints.weighty = 3d;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        RichnessPanel2.add(RichnessScrollPane2, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 4;
        gridBagConstraints.weighty = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        OutputPanel.add(outputPane, gridBagConstraints);
        
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;//4
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 4;
        gridBagConstraints.weighty = 4;
        add(OutputPanel, gridBagConstraints);  
               
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void AnalysisButtonActionPerformed(ActionEvent evt) {
    	
    	Cleaning_Allthings.clean(0);
    	
    	NameAddressOnt1 = ontologyFileTextField1.getText().trim();                              
    	File ontologyFile = new File(NameAddressOnt1); 
    	if (NameAddressOnt1.length() <= 0){
  		  JOptionPane.showMessageDialog(null, "Please Select your Ontology File", "Error", JOptionPane.ERROR_MESSAGE);
  		  return;
  	    }else if (!ontologyFile.exists()) {
            JOptionPane.showMessageDialog(null,"The ontology file does not exist.", "Error",JOptionPane.ERROR_MESSAGE);
            return;
    	}else
        if (NameAddressOnt1.length() > 0  ) {
        	LockedGUI();
            // Build the model
            if (Controller.CheckBuildModel == false) {
         		BuildModel.BuildModelOnt(AnalysisPanel.NameAddressOnt1);
         		Controller.CheckBuildModel =true;
         	}
            
            // Add this selected ontology to the next tab
            PartitioningPanel.ontologyFileTextField.setText(NameAddressOnt1);
            
            AnalyzingTest AT=new AnalyzingTest();
            AT.AnalyzingTestRun();
            analysisTest = true;
            BuildModel.analysisTest = true;
            Un_LockedGUI();  
        }
    

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
private void CompareButtonButtonActionPerformed(ActionEvent evt) {
	
	Cleaning_Allthings.clean(0);
	NameAddressOnt1 = ontologyFileTextField1.getText().trim();
	NameAddressOnt2 = ontologyFileTextField2.getText().trim();
	File ontologyFile1 = new File(NameAddressOnt1);
	File ontologyFile2 = new File(NameAddressOnt2);
	
	if (ontologyFileTextField1.getText().length() <= 0 ){
		  JOptionPane.showMessageDialog(null, "Please Select your first Ontology", "Error", JOptionPane.ERROR_MESSAGE);
		  return;
	    } else if (ontologyFileTextField2.getText().length() <= 0){
  		  JOptionPane.showMessageDialog(null, "Please Select your second Ontology", "Error", JOptionPane.ERROR_MESSAGE);
  		  return;
  	    } else if (!ontologyFile1.exists() ) {
              JOptionPane.showMessageDialog(null,"The first ontology file does not exist.", "Error",JOptionPane.ERROR_MESSAGE);
              return;
        } else if (!ontologyFile2.exists()) {
            JOptionPane.showMessageDialog(null,"The second ontology file does not exist.", "Error",JOptionPane.ERROR_MESSAGE);
            return;
  	    }
	            	               	
    if (NameAddressOnt1.length() > 0 && NameAddressOnt2.length() > 0) {
    	
    	// Add one of the two candidate ontologies to the next tab
        PartitioningPanel.ontologyFileTextField.setText(NameAddressOnt1);
        Thread cmp = new Thread()
        {
            @Override
            public void run()
            {
            	Cleaning_Allthings.clean(3); 
            	
                Configuration config = new Configuration();
                config.init();
                String fp1 = NameAddressOnt1, fp2 = NameAddressOnt2;
              /*  if (!fp1.startsWith("file:")) {
                    fp1 = "file:" + fp1;
                }
                if (!fp2.startsWith("file:")) {
                    fp2 = "file:" + fp2;
                }*/
                
                
                CompareTest.compareFunc(fp1 ,fp2);
                outputPane.setEnabledAt(3, true);
                outputPane.setEnabledAt(4, true);
                outputPane.setEnabledAt(5, true);
            }
        };
        cmp.start();
    }
}	
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void ontologyFileChooserButtonActionPerformed1(ActionEvent evt) {
    	 JFileChooser fileChooser = new JFileChooser(".");
         fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
         fileChooser.setName("Open");
         fileChooser.setToolTipText("Please input a file");
         int rVal = fileChooser.showOpenDialog(null);
         if (rVal == JFileChooser.APPROVE_OPTION) {
        	 ontologyFileTextField1.setText(fileChooser.getSelectedFile().toString());
         }
    }
    
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void ontologyFileChooserButtonActionPerformed2(ActionEvent evt) {
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setName("Open");
		fileChooser.setToolTipText("Please input a file");
		int rVal = fileChooser.showOpenDialog(null);
		if (rVal == JFileChooser.APPROVE_OPTION) {
		ontologyFileTextField2.setText(fileChooser.getSelectedFile().toString());
		}
	}
      
    public static javax.swing.JButton ontologyFileChooserButton1;
    public static javax.swing.JButton ontologyFileChooserButton2;
    private javax.swing.JLabel ontologyFileLabel1;
    private javax.swing.JLabel ontologyFileLabel2;
    public static javax.swing.JTextField ontologyFileTextField1;
    public static javax.swing.JTextField ontologyFileTextField2;
    private javax.swing.JFileChooser openOntologyFileChooser1;    
    private javax.swing.JFileChooser openOntologyFileChooser2;
    private javax.swing.JPanel RunPanel;
    private javax.swing.JPanel RunPanel_1;
    private javax.swing.JPanel RunPanel_2;
    private javax.swing.JPanel OntologySelectionPanel;
    private javax.swing.JLabel EmptyLabel1;
    private javax.swing.JLabel EmptyLabel2;
    public static javax.swing.JButton AnalysisButton;
    public static javax.swing.JButton CompareButton;
    private javax.swing.ImageIcon pic1 ;
    private javax.swing.JPanel PicturePanel;
    private javax.swing.JPanel OutputPanel;
    private javax.swing.JScrollPane GeneralScrollPane1;
    private javax.swing.JScrollPane GeneralScrollPane2;
    private javax.swing.JScrollPane ConsistencyScrollPane1;
    private javax.swing.JScrollPane ConsistencyScrollPane2;
    private javax.swing.JScrollPane RichnessScrollPane1;
    private javax.swing.JScrollPane RichnessScrollPane2;
    public static javax.swing.JTextArea GeneralTextArea1;
    public static javax.swing.JTextArea GeneralTextArea2;
    public static javax.swing.JTextArea ConsistencyTextArea1;
    public static javax.swing.JTextArea ConsistencyTextArea2;
    public static javax.swing.JTextArea RichnessTextArea1;
    public static javax.swing.JTextArea RichnessTextArea2;
    final static JProgressBar progressBar = new JProgressBar(1, 100);
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void LockedGUI(){
    	AnalysisButton.setEnabled(false);
    	CompareButton.setEnabled(false);
    	ontologyFileChooserButton1.setEnabled(false);
    	ontologyFileChooserButton2.setEnabled(false);
    	progressBar.setStringPainted(true);
        progressBar.setString("I am running. Please wait!");
        progressBar.setIndeterminate(true);
        
    }

    public static void Un_LockedGUI(){
    	AnalysisButton.setEnabled(true);
    	CompareButton.setEnabled(true);
    	ontologyFileChooserButton1.setEnabled(true);
    	ontologyFileChooserButton2.setEnabled(true);
    	progressBar.setIndeterminate(false);
        progressBar.setString("Complete. Have fun!");
        
    }

}
