package fusion.comerger.general.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class EvaluationPanel extends JPanel
{
	public static String NameAddressOnt; 
    double RichValue = 0;
    double richThreshold = 0.5;

    private static final long serialVersionUID = 1L;

    public EvaluationPanel() {
        super();
        setPreferredSize(new Dimension(600, 800));
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        
        Result1_Details= new JTextArea();   
        Result2_Details= new JTextArea();   
        Result3_Details= new JTextArea();   
        AvgTextBox1 = new JTextField();
        AvgTextBox2 = new javax.swing.JTextField();
        AvgTextBox3 = new javax.swing.JTextField();
        AvgLabel1 = new javax.swing.JLabel();
        AvgLabel2 = new javax.swing.JLabel();
        AvgLabel3 = new javax.swing.JLabel();
        DetailLabel1 = new javax.swing.JLabel();
        DetailLabel2 = new javax.swing.JLabel();
        DetailLabel3 = new javax.swing.JLabel();

        PicturePanel = new javax.swing.JPanel();                  PicturePanel.setPreferredSize(new Dimension(800, 120)); PicturePanel.setMaximumSize(new Dimension(800, 120)); PicturePanel.setMinimumSize(new Dimension(800, 120));
        AvgEvaluationResultPanel = new javax.swing.JPanel();        AvgEvaluationResultPanel.setPreferredSize(new Dimension(600, 50)); AvgEvaluationResultPanel.setMaximumSize(new Dimension(600, 50)); AvgEvaluationResultPanel.setMinimumSize(new Dimension(600, 50));
        DetailEvaluationResultPanel = new javax.swing.JPanel();        DetailEvaluationResultPanel.setPreferredSize(new Dimension(600, 500)); DetailEvaluationResultPanel.setMaximumSize(new Dimension(600, 500)); DetailEvaluationResultPanel.setMinimumSize(new Dimension(600, 500));
        
        pic1 = new ImageIcon("fig/logo2.png");
        ResultScrollPane1 = new javax.swing.JScrollPane();
        ResultScrollPane2 = new javax.swing.JScrollPane();
        ResultScrollPane3 = new javax.swing.JScrollPane();
        EvaluationPanel.OutputPartitioningTextArea = new javax.swing.JTextArea();

        setLayout(new java.awt.GridBagLayout());
        PicturePanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        PicturePanel.add(new JLabel(pic1),gridBagConstraints);
        add(PicturePanel, gridBagConstraints);
        
        
        setLayout(new java.awt.GridBagLayout());

        AvgEvaluationResultPanel.setLayout(new java.awt.GridBagLayout());
        AvgEvaluationResultPanel.setBorder(new javax.swing.border.TitledBorder("Evaluation Result"));
              
        
        AvgLabel1.setText("Average Size:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        AvgEvaluationResultPanel.add(AvgLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        AvgEvaluationResultPanel.add(AvgTextBox1, gridBagConstraints);
        
        AvgLabel2.setText("         Average Coupling:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        AvgEvaluationResultPanel.add(AvgLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        AvgEvaluationResultPanel.add(AvgTextBox2, gridBagConstraints);

        AvgLabel3.setText("    Average Cohesion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        AvgEvaluationResultPanel.add(AvgLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        AvgEvaluationResultPanel.add(AvgTextBox3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(AvgEvaluationResultPanel, gridBagConstraints);


        DetailEvaluationResultPanel.setLayout(new java.awt.GridBagLayout());
        DetailEvaluationResultPanel.setBorder(new javax.swing.border.TitledBorder("Details Evaluation Result"));
              
        
        DetailLabel1.setText(" Details Size            ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        DetailEvaluationResultPanel.add(DetailLabel1, gridBagConstraints);

        DetailLabel2.setText(" Details Coupling        ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        DetailEvaluationResultPanel.add(DetailLabel2, gridBagConstraints);

        DetailLabel3.setText(" Details Cohesion        ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        DetailEvaluationResultPanel.add(DetailLabel3, gridBagConstraints);

        
        Result1_Details.setEditable(false);
        Result1_Details.setLineWrap(true);
        Result1_Details.setCaretPosition(0);
        Result1_Details.setWrapStyleWord(true);
        //Result1_Details.setText(" "+'\n'+" ");
        Result1_Details.setText("Your selected ontology does not partition");
        ResultScrollPane1.setViewportView(Result1_Details);

        gridBagConstraints = new java.awt.GridBagConstraints();
        //gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DetailEvaluationResultPanel.add(ResultScrollPane1, gridBagConstraints);

              
        Result2_Details.setEditable(false);
        Result2_Details.setLineWrap(true);
        Result2_Details.setCaretPosition(0);
        Result2_Details.setWrapStyleWord(true);
        //Result2_Details.setText(" "+'\n'+" ");
        Result2_Details.setText("Your selected ontology does not partition");
        ResultScrollPane2.setViewportView(Result2_Details);

        gridBagConstraints = new java.awt.GridBagConstraints();
        //gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DetailEvaluationResultPanel.add(ResultScrollPane2, gridBagConstraints);

        
        Result3_Details.setEditable(false);
        Result3_Details.setLineWrap(true);
        Result3_Details.setCaretPosition(0);
        Result3_Details.setWrapStyleWord(true);
        //Result3_Details.setText(" "+'\n'+" ");
        Result3_Details.setText("Your selected ontology does not partition");
        ResultScrollPane3.setViewportView(Result3_Details);

        gridBagConstraints = new java.awt.GridBagConstraints();
        //gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DetailEvaluationResultPanel.add(ResultScrollPane3, gridBagConstraints);

    
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(DetailEvaluationResultPanel, gridBagConstraints);
    }
       
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private javax.swing.JPanel AvgEvaluationResultPanel;
    private javax.swing.JPanel DetailEvaluationResultPanel;
    private javax.swing.ImageIcon pic1 ;
    private javax.swing.JPanel PicturePanel;
    private javax.swing.JScrollPane ResultScrollPane1;
    private javax.swing.JScrollPane ResultScrollPane2;
    private javax.swing.JScrollPane ResultScrollPane3;
    public static javax.swing.JTextArea OutputPartitioningTextArea;
    private javax.swing.JLabel AvgLabel1;
    private javax.swing.JLabel AvgLabel2;
    private javax.swing.JLabel AvgLabel3;
    private javax.swing.JLabel DetailLabel1;
    private javax.swing.JLabel DetailLabel2;
    private javax.swing.JLabel DetailLabel3;
       
    public static  JTextArea Result1_Details;
    public static JTextArea Result2_Details;
    public static JTextArea Result3_Details;
    public static JTextField AvgTextBox1 ;
    public static JTextField AvgTextBox2 ;
    public static JTextField AvgTextBox3 ;

}
