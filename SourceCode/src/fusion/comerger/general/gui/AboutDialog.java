
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
import java.awt.event.ActionEvent;

import javax.swing.JDialog;

public class AboutDialog extends JDialog {
    
    private static final long serialVersionUID = 1L;
    
    public AboutDialog() {
         initComponents();
    }
    
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        authorLabel = new javax.swing.JLabel();
        authorValueLabel = new javax.swing.JLabel();
        versionLabel = new javax.swing.JLabel();
        versionValueLabel = new javax.swing.JLabel();
        licensePanel = new javax.swing.JPanel();
        licenseScrollPane = new javax.swing.JScrollPane();
        licenseEditorPane = new javax.swing.JEditorPane();
        licenseEditorPane.setContentType("text/html");
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About");
        setModal(true);
        mainPanel.setLayout(new java.awt.GridBagLayout());

        mainPanel.setPreferredSize(new java.awt.Dimension(400, 400));
        titleLabel.setText("OPT");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        mainPanel.add(titleLabel, gridBagConstraints);

        authorLabel.setText("Authors:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        mainPanel.add(authorLabel, gridBagConstraints);

        authorValueLabel.setText("Alsayed Algergawy and Samira Babalou");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        mainPanel.add(authorValueLabel, gridBagConstraints);

        versionLabel.setText("Revision:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        mainPanel.add(versionLabel, gridBagConstraints);

        versionValueLabel.setText("V01");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(versionValueLabel, gridBagConstraints);

        licensePanel.setLayout(new java.awt.GridBagLayout());

        licensePanel.setBorder(new javax.swing.border.TitledBorder("License and credits"));
        licenseEditorPane.setEditable(false);
       // licenseEditorPane.setText("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">\n<HTML>\n  <HEAD>\n    <TITLE>License and credits</TITLE>\n  </HEAD>\n  <BODY>\n    <P>\n      OPT (Ontology Partitioning Tool)<BR>\n      Copyright (C) 2016 by Alsayed Algergawy and Samira Babalou <BR>\n      <A HREF=\"mailto:alsayed.algergawy@uni-jena.de\">alsayed.algergawy@uni-jena.de</A>\n    </P>\n\n    <P>\n      This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.\n    </P>\n\n    <P>\n      This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.\n    </P>\n\n    <P>\n      You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA\n    </P>\n\n    <P>\n      This product includes software developed by The Apache Software Foundation (<A HREF=\"http://www.apache.org/\">http://www.apache.org/</A>).\n    </P>\n\n    <P>\n      This product includes Islands (<A HREF=\"http://vlado.fmf.uni-lj.si/pub/networks/\">http://vlado.fmf.uni-lj.si/pub/networks/</A>) by Matjaz Zaversnik, licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 2.0 License.\n    </P>\n\n    <P>\n      This product includes JUNG (<A HREF=\"http://jung.sourceforge.net/\">http://jung.sourceforge.net/</A>), licensed under Berkeley Software Distribution License.\n    </P>\n\n    <P>\n      This product includes JUnit (<A HREF=\"http://www.junit.org/\">http://www.junit.org/</A>), licensed under the Common Public License Version 1.0.\n    </P>\n\n    <P>\n      This product includes Sesame (<A HREF=\"http://www.openrdf.org/\">http://www.openrdf.org/</A>), licensed under the GNU Lesser General Public License.\n    </P>\n\n    <P>\n      This product includes Sigma (<A HREF=\"http://sourceforge.net/projects/sigmakee/\">http://sourceforge.net/projects/sigmakee/</A>), licensed under the GNU General Public License.\n    </P>\n  </BODY>\n</HTML>");
        licenseScrollPane.setViewportView(licenseEditorPane);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        licensePanel.add(licenseScrollPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        mainPanel.add(licensePanel, gridBagConstraints);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(closeButton, gridBagConstraints);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
    }
    
    public void setVisible(boolean b) {
        if (b) {
            licenseScrollPane.getHorizontalScrollBar().setValue(0);
            licenseScrollPane.getVerticalScrollBar().setValue(0);
        }
        super.setVisible(b);
    }
    
    private void closeButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }
    
    private javax.swing.JLabel authorLabel;
    private javax.swing.JLabel authorValueLabel;
    private javax.swing.JButton closeButton;
    private javax.swing.JEditorPane licenseEditorPane;
    private javax.swing.JPanel licensePanel;
    private javax.swing.JScrollPane licenseScrollPane;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel versionLabel;
    private javax.swing.JLabel versionValueLabel;
 
    
}
