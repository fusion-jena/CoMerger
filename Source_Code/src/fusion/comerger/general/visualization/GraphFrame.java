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
import fusion.comerger.general.cc.Data;
import fusion.comerger.general.gui.MainFrame;
import fusion.comerger.general.visualization.net.Arc;
import fusion.comerger.general.visualization.net.Edge;
import fusion.comerger.general.visualization.net.Network;
import fusion.comerger.general.visualization.net.Vertex;
import fusion.comerger.model.NodeList;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

import com.github.andrewoma.dexx.collection.ArrayList;

/**
 * A graph frame for showing the graph that corresponds to a network.en
 */
public class GraphFrame extends javax.swing.JFrame {
    
	private Color color = new Color (1,1,1);
	private Random ran1 = new Random(); 
    private int Rcolor = ran1.nextInt(255);
	private int Gcolor = ran1.nextInt(255);
	private int Bcolor = ran1.nextInt(255);
    private Color[] COLORS = {Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, Color.WHITE, Color.BLACK}; //Red color assign to CHs

    
	private class VertexColorer implements VertexColorFunction {
        
        private Map<edu.uci.ics.jung.graph.Vertex, Color> vertexForeColors = new HashMap<edu.uci.ics.jung.graph.Vertex, Color>();
        
        private Map<edu.uci.ics.jung.graph.Vertex, Color> vertexBackColors = new HashMap<edu.uci.ics.jung.graph.Vertex, Color>();
        
        /**
         * Sets the fore and back color for the specified vertex.
         *
         * @param vertex a vertex
         * @param foreColor the fore color
         * @param backColor the back color
         */
        public void setColor(edu.uci.ics.jung.graph.Vertex vertex, Color foreColor, Color backColor) {
            vertexForeColors.put(vertex, foreColor);
            vertexBackColors.put(vertex, backColor);
        }
        
        /**
         * Gets the fore color for the specified vertex.
         *
         * @param vertex a vertex
         * @return the fore color
         */
        public Color getForeColor(edu.uci.ics.jung.graph.Vertex vertex) {
            return vertexForeColors.get(vertex);
        }
        
        /**
         * Gets the back color for the specified vertex.
         *
         * @param vertex a vertex
         * @return the back color
         */
        public Color getBackColor(edu.uci.ics.jung.graph.Vertex vertex) {
            return vertexBackColors.get(vertex);
        }
        
        /**
         * Clears all information about the colors of the vertices.
         */
        public void clear() {
            vertexForeColors.clear();
            vertexBackColors.clear();
        }
        
    };
    
    private class EdgeLabeller implements EdgeStringer {
        
        private Map<edu.uci.ics.jung.graph.Edge, String> edgeLabels = new HashMap<edu.uci.ics.jung.graph.Edge, String>();
        
        /**
         * Gets the label associated with a particular edge.
         *
         * @param edge an edge
         * @return the label
         */
        public String getLabel(edu.uci.ics.jung.graph.Edge edge) {
            return edgeLabels.get(edge);
        }
        
        /**
         * Associates an edge with a label. This method overwrites any previous labels on this edge.
         *
         * @param edge an edge
         * @param label a label
         */
        public void setLabel(edu.uci.ics.jung.graph.Edge edge, String label) {
            edgeLabels.put(edge, label);
        }
        
        /**
         * Clears all information about the labels of the edges.
         */
        public void clear() {
            edgeLabels.clear();
        }
        
    };
    
      
    private static final long LOWER_TEMPERATURE_INTERVAL = 5000l;
    
    private static final Object[] LOWER_TEMPERATURE_DIALOG_OPTIONS = {"Yes", "Yes, and don't ask again for one minute", "No"};
    
    private static final long serialVersionUID = 1L;
       
    private MainFrame mainframe;
    
    private Graph graph = new SparseGraph();
    
    private FRLayout layout = new FRLayout(graph);
    
    private PluggableRenderer renderer = new PluggableRenderer();
    
    private VisualizationViewer visualizationViewer = new VisualizationViewer(layout, renderer);
        
    private VertexColorer vertexColors = new VertexColorer();
    
    private StringLabeller vertexLabels = StringLabeller.getLabeller(graph);
    
    private EdgeLabeller edgeLabels = new EdgeLabeller();
    
    /**
     * Creates a graph frame.
     *
     * @param mainframe the main window of the Pato GUI
     */
    public GraphFrame(MainFrame mainframe, Data data) {
        initComponents(data);
        
        this.mainframe = mainframe;
        
        renderer.setVertexColorFunction(vertexColors);
        renderer.setVertexStringer(vertexLabels);
        renderer.setEdgeStringer(edgeLabels);
        
        visualizationViewer.setBackground(Color.WHITE);
        
        // Add the viewer to the graph scroll pane
        graphScrollPane.setViewportView(visualizationViewer);
    }
    
    private void pauseVisualization() {
        visualizationViewer.suspend();
    }
    
    private void resumeVisualization() {
        layout.update();
        visualizationViewer.unsuspend();
        visualizationViewer.repaint();
    }
    
    /**
     * Removes all vertices (and edges) from the graph.
     */
    public void clearGraph() {
        pauseVisualization();
        
        // Remove all vertices from the graph
        graph.removeAllVertices();
        
        // Clear the vertex colorer
        vertexColors.clear();
        
        // Clear both string labellers
        vertexLabels.clear();
        edgeLabels.clear();
        
        //colorIndex = 0;

        resumeVisualization();
    }
    
    private void lowerTemperature() {
        if (layout.isIncremental()) {
            // Randomize all vertex positions again if the temperature has already been minimized
            if (layout.incrementsAreDone()) {
                layout.restart();
            }
            
            // Periodically ask the user for confirmation whether he wants to continue lowering the temperature
            long nextDialogTime = System.currentTimeMillis() + LOWER_TEMPERATURE_INTERVAL;
            while (!layout.incrementsAreDone()) {
                layout.advancePositions();
                if (System.currentTimeMillis() > nextDialogTime) {
                    visualizationViewer.repaint();
                    int reply = JOptionPane.showOptionDialog(this, "Do you want to continue lowering the temperature?", "Continue lowering temperature", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, LOWER_TEMPERATURE_DIALOG_OPTIONS, LOWER_TEMPERATURE_DIALOG_OPTIONS[0]);
                    if (reply == 0) {
                        nextDialogTime = System.currentTimeMillis() + LOWER_TEMPERATURE_INTERVAL;
                    } else if (reply == 1) {
                        nextDialogTime = System.currentTimeMillis() + 60000l;
                    } else {
                        break;
                    }
                }
            }
            visualizationViewer.repaint();
        }
    }
    
    /**
     * Creates a graph from the specified network.
     *
     * @param network a network
     */
    public void createGraph(Network network, String Sch, Data data) {
    	pauseVisualization();
        
        BuildModel.colorIndex++;
        if (BuildModel.colorIndex >= COLORS.length) { // We define 13 standard colors, if the number of cluster exceed more than 13, we create new randomly color
        	ran1 = new Random(); 
        	Rcolor = ran1.nextInt(255);
        	Gcolor = ran1.nextInt(255);
        	Bcolor = ran1.nextInt(255);
        	color = new Color (Rcolor, Gcolor, Bcolor);
        } else{
            color = COLORS[BuildModel.colorIndex];
        }
        

        
        // Create a vertex in the graph for each vertex in the network
        int uniqueNumber;
        Map<Vertex, edu.uci.ics.jung.graph.Vertex> vertexToJungVertex = new HashMap<Vertex, edu.uci.ics.jung.graph.Vertex>();
        for (Vertex vertex : network.getVertices()) {

            edu.uci.ics.jung.graph.Vertex jungVertex = new SparseVertex();            

                  
            graph.addVertex(jungVertex);
            
            // If another vertex with the same label already exists in the graph, append a unique number to the label, starting from 2.
            uniqueNumber = 2;
            String label = vertex.getLabel();
            while (true) {
                try {
                    vertexLabels.setLabel(jungVertex, label);
                    break;
                } catch (StringLabeller.UniqueLabelException e) {
                    label = vertex.getLabel() + " (" + uniqueNumber + ")";
                    uniqueNumber++;
                }
            }
            
            
            boolean chColor = false;
            if (Sch!= "CH"){
            	if (vertex.getLabel().equals(Sch)){
            		vertexColors.setColor(jungVertex, Color.BLACK, color.RED); //set the color of CHs with Red
            		chColor = true;
            	}
            }else{
            	for (int a=0;a<data.getNumCH();a++){
                	if (vertex.getLabel().equals(data.getCHs().get(a).getLocalName())){
                		vertexColors.setColor(jungVertex, Color.BLACK, color.RED); //set the color of CHs with Red
                		chColor = true;
                	}
                }
                 
            }
            if (chColor == false ) 	vertexColors.setColor(jungVertex, Color.BLACK, color);
            
            vertexToJungVertex.put(vertex, jungVertex); //TO DO: if is is datatype properties it should not show
    }
        
        // Create the directed edges
        for (Arc arc : network.getArcs()) {
            Vertex fromVertex = arc.getFromVertex();
            Vertex toVertex = arc.getToVertex();
            edu.uci.ics.jung.graph.Vertex fromJungVertex = vertexToJungVertex.get(fromVertex);
            edu.uci.ics.jung.graph.Vertex toJungVertex = vertexToJungVertex.get(toVertex);
            edu.uci.ics.jung.graph.Edge jungEdge = new DirectedSparseEdge(fromJungVertex, toJungVertex);
            graph.addEdge(jungEdge);
            edgeLabels.setLabel(jungEdge, arc.getLabel());
        }
        
        // Create the undirected edges
        for (Edge edge : network.getEdges()) {
            Vertex fromVertex = edge.getVertex1();
            Vertex toVertex = edge.getVertex2();
            edu.uci.ics.jung.graph.Vertex fromJungVertex = vertexToJungVertex.get(fromVertex);
            edu.uci.ics.jung.graph.Vertex toJungVertex = vertexToJungVertex.get(toVertex);
            edu.uci.ics.jung.graph.Edge jungEdge = new UndirectedSparseEdge(fromJungVertex, toJungVertex);
            graph.addEdge(jungEdge);
            edgeLabels.setLabel(jungEdge, edge.getLabel());
        }
        
        //setNextColorIndex(); I delete this line, since it calls from visualization.java
        
        resumeVisualization();
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents(Data data) {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        graphPanel = new javax.swing.JPanel();
        graphScrollPane = new javax.swing.JScrollPane();
        coolDownButton = new javax.swing.JButton();
        plusDetailButton = new javax.swing.JButton();
        minusDetailButton = new javax.swing.JButton();
        applyButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        levelLabel = new javax.swing.JLabel();
        levelTextbox = new javax.swing.JTextArea();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setTitle("Graph");
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

        graphPanel.setLayout(new java.awt.GridBagLayout());

        graphPanel.setBorder(new javax.swing.border.TitledBorder("Graph"));
        graphPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        graphPanel.add(graphScrollPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(graphPanel, gridBagConstraints);

        coolDownButton.setText("Cool down");
        coolDownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coolDownButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(coolDownButton, gridBagConstraints);

        //new:
        plusDetailButton.setText(" + ");
        plusDetailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               plusDetailButtonActionPerformed(evt, data);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(plusDetailButton, gridBagConstraints);

        minusDetailButton.setText(" - ");
        minusDetailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusDetailButtonActionPerformed(evt, data);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(minusDetailButton, gridBagConstraints);

        
        
        levelLabel.setText("Enter level of detail: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        getContentPane().add(levelLabel, gridBagConstraints);
        
//        levelTextbox.setText("               ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(levelTextbox, gridBagConstraints);
        
        
        applyButton.setText("Apply");
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt, data);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;//1
        gridBagConstraints.gridy = 1;//1
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(applyButton, gridBagConstraints);

        
        //
        
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;//1
        gridBagConstraints.gridy = 1;//1
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(closeButton, gridBagConstraints);

        pack();
    }//GEN-END:initComponents
    
    private void coolDownButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coolDownButtonActionPerformed
        lowerTemperature();
    }//GEN-LAST:event_coolDownButtonActionPerformed
    
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        layout.resize(visualizationViewer.getSize());
        layout.restart();
    }//GEN-LAST:event_formComponentResized
    
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        //mainframe.graphMenuItem.doClick();
        this.setVisible(false);
    }//GEN-LAST:event_closeButtonActionPerformed
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //mainframe.graphMenuItem.doClick();
    	this.setVisible(true);
    }//GEN-LAST:event_formWindowClosing
    
    
    private void plusDetailButtonActionPerformed(java.awt.event.ActionEvent evt, Data data) {//GEN-FIRST:event_coolDownButtonActionPerformed
    	this.setVisible(false);
    	Coordinator.levelDetail = Coordinator.levelDetail +1;
    	Visualization.Run(data, Coordinator.argVisulization);
    }
    
    private void minusDetailButtonActionPerformed(java.awt.event.ActionEvent evt, Data data) {//GEN-FIRST:event_coolDownButtonActionPerformed
    	this.setVisible(false);
    	Coordinator.levelDetail = Coordinator.levelDetail -1;
    	if (Coordinator.levelDetail <=0) Coordinator.levelDetail = 1;
    	Visualization.Run(data, Coordinator.argVisulization);

    }
    
    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt, Data data) {//GEN-FIRST:event_closeButtonActionPerformed
   		String s = levelTextbox.getText().trim();
   		if (s.length()>0){
   			try {	
   				this.setVisible(false);
   				Integer.parseInt(levelTextbox.getText().trim());
   				Coordinator.levelDetail = Integer.parseInt(levelTextbox.getText().trim());
   				if (Coordinator.levelDetail <=0) Coordinator.levelDetail = 1;
   				Visualization.Run(data, Coordinator.argVisulization);
   			}catch(Exception e) {
   				JOptionPane.showMessageDialog(graphPanel, "Please enter correct value in the text file");
   			}
   		}
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JButton applyButton;
    private javax.swing.JButton coolDownButton;
    private javax.swing.JButton plusDetailButton;
    private javax.swing.JButton minusDetailButton;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JScrollPane graphScrollPane;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JTextArea levelTextbox;
    // End of variables declaration//GEN-END:variables
    
}
