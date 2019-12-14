
package fusion.comerger.algorithm.matcher.string;

import java.util.ArrayList;

import fusion.comerger.algorithm.matcher.AbstractMatcher;
import fusion.comerger.algorithm.matcher.string.EditDistance;
import fusion.comerger.algorithm.matcher.string.ISub;
import fusion.comerger.algorithm.matcher.string.Parameters;
import fusion.comerger.general.gernalAnalysis.matrix.NamedMatrix;
import fusion.comerger.general.output.Alignment;
import fusion.comerger.general.output.AlignmentSelector;
import fusion.comerger.model.Node;
import fusion.comerger.model.NodeList;
import fusion.comerger.model.RBGModel;

public class StringMatcher implements AbstractMatcher
{
    private RBGModel modelA = null,  modelB = null;
    private Alignment alignment = null;
    private Alignment classAlignment = null;
    private Alignment propertyAlignment = null;
    private Alignment instanceAlignment = null;
    private NamedMatrix classMatrix = null;
    private NamedMatrix propertyMatrix = null;
    private NamedMatrix instanceMatrix = null;

    public StringMatcher(RBGModel modelA, RBGModel modelB)
    {
        this.modelA = modelA;
        this.modelB = modelB;
    }
    
    public StringMatcher() {}

    private StringBuffer normalize(String s)
    {
        StringBuffer buffer = new StringBuffer();
        if (s == null)
        	return buffer;
        int currentState = 0;
        for (int j = 0; j < s.length(); j++) {
            char nextChar = s.charAt(j);
            switch (currentState) {
                case 0:
                    if (Character.isUpperCase(nextChar)) {
                        buffer.append(Character.toLowerCase(nextChar));
                        currentState = 1;
                    } else if (Character.isLowerCase(nextChar)) {
                        buffer.append(nextChar);
                        currentState = 1;
                    } else if (Character.isDigit(nextChar)) {
                        buffer.append(nextChar);
                        currentState = 2;
                    } else {
                        currentState = 0;
                    }
                    break;
                case 1:
                    if (Character.isUpperCase(nextChar)) {
                        buffer.append(" ");
                        buffer.append(Character.toLowerCase(nextChar));
                        currentState = 1;
                    } else if (Character.isLowerCase(nextChar)) {
                        buffer.append(nextChar);
                        currentState = 1;
                    } else if (Character.isDigit(nextChar)) {
                        buffer.append(" ");
                        buffer.append(nextChar);
                        currentState = 2;
                    } else {
                        buffer.append(" ");
                        currentState = 0;
                    }
                    break;
                case 2:
                    if (Character.isUpperCase(nextChar)) {
                        buffer.append(" ");
                        buffer.append(Character.toLowerCase(nextChar));
                        currentState = 1;
                    } else if (Character.isLowerCase(nextChar)) {
                        buffer.append(" ");
                        buffer.append(nextChar);
                        currentState = 1;
                    } else if (Character.isDigit(nextChar)) {
                        buffer.append(nextChar);
                        currentState = 2;
                    } else {
                        buffer.append(" ");
                        currentState = 0;
                    }
                    break;
            }
        }
        return buffer;
    }
    
    public double calcSimilarity(Node node1, Node node2)
    {
    	String n1 = new String(normalize(node1.getLocalName()));
        ArrayList<String> l1 = node1.getLabel();
        ArrayList<String> c1 = node1.getComment();
        
        String n2 = new String(normalize(node2.getLocalName()));
        ArrayList<String> l2 = node2.getLabel();
        ArrayList<String> c2 = node2.getComment();
        
        double s1 = 0, s2 = 0, s3 = 0, similarity = 0;
        if (Parameters.method == 0) {
            if (n1 != null && n2 != null && Parameters.localnameWeight > 0) 
                s1 = Parameters.localnameWeight * ISub.getSimilarity(n1, n2);          
            if (l1 != null && l2 != null && Parameters.labelWeight > 0) {
                double max = 0;
                for (int m = 0; m < l1.size(); m++) {
                    String t1 = new String(normalize(l1.get(m)));
                    for (int n = 0; n < l2.size(); n++) {
                        String t2 = new String(normalize(l2.get(n)));
                        double temp = ISub.getSimilarity(t1, t2);
                        if (temp > max) 
                            max = temp;
                    }
                }
                s2 = Parameters.labelWeight * max;
            }
            if (c1 != null && c2 != null && Parameters.commentWeight > 0) {
                double max = 0;
                for (int m = 0; m < c1.size(); m++) {
                    String t1 = new String(normalize(c1.get(m)));
                    for (int n = 0; n < c2.size(); n++) {
                        String t2 = new String(normalize(c2.get(n)));
                        double temp = ISub.getSimilarity(t1, t2);
                        if (temp > max) 
                            max = temp;
                    }
                }
                s3 = Parameters.commentWeight * max;
            }
            if (s1 > similarity) 
                similarity = s1;
            if (s2 > similarity) 
                similarity = s2;
            if (s3 > similarity) 
                similarity = s3;
        } else if (Parameters.method == 1) {
            if (n1 != null && n2 != null && Parameters.localnameWeight > 0) 
                s1 = Parameters.localnameWeight * EditDistance.getSimilarity(n1, n2);
            if (l1 != null && l2 != null && Parameters.labelWeight > 0) {
                double max = 0;
                for (int m = 0; m < l1.size(); m++) {
                    String t1 = new String(normalize(l1.get(m)));
                    for (int n = 0; n < l2.size(); n++) {
                        String t2 = new String(normalize(l2.get(n)));
                        double temp = ISub.getSimilarity(t1, t2);
                        if (temp > max) 
                            max = temp;
                    }
                }
                s2 = Parameters.labelWeight * max;
            }
            if (c1 != null && c2 != null && Parameters.commentWeight > 0) {
                double max = 0;
                for (int m = 0; m < c1.size(); m++) {
                    String t1 = new String(normalize(c1.get(m)));
                    for (int n = 0; n < c2.size(); n++) {
                        String t2 = new String(normalize(c2.get(n)));
                        double temp = ISub.getSimilarity(t1, t2);
                        if (temp > max)
                            max = temp;
                    }
                }
                s3 = Parameters.commentWeight * max;
            }
            if (s1 > similarity) 
                similarity = s1;
            if (s2 > similarity) 
                similarity = s2;
            if (s3 > similarity)
                similarity = s3;
        }
        return similarity;
    }

    public NamedMatrix calcSimilarity(NodeList left, NodeList right)
    {
        int numRows = left.size(), numColumns = right.size();
        NamedMatrix matrix = new NamedMatrix(numRows, numColumns);
        ArrayList<Node> rnodes = left.getList(), cnodes = right.getList();
        ArrayList<Object> rowList = new ArrayList<Object>();
        ArrayList<Object> colList = new ArrayList<Object>();
        for (int k = 0; k < rnodes.size(); ++k) {
        	rowList.add(rnodes.get(k));
        }
        for (int k = 0; k < cnodes.size(); ++k) {
        	colList.add(cnodes.get(k));
        }
        matrix.setRowList(rowList);
        matrix.setColumnList(colList);
        
        for (int i = 0; i < numRows; i++) {
            Node node1 = left.get(i);
            String n1 = new String(normalize(node1.getLocalName()));
            ArrayList<String> l1 = node1.getLabel();
            ArrayList<String> c1 = node1.getComment();
            for (int j = 0; j < numColumns; j++) {
                Node node2 = right.get(j);
                String n2 = new String(normalize(node2.getLocalName()));
                ArrayList<String> l2 = node2.getLabel();
                ArrayList<String> c2 = node2.getComment();
                double s1 = 0, s2 = 0, s3 = 0, similarity = 0;
                if (Parameters.method == 0) {
                    if (n1 != null && n2 != null && Parameters.localnameWeight > 0) {
                        s1 = Parameters.localnameWeight * ISub.getSimilarity(n1, n2);
                    }
                    if (l1 != null && l2 != null && Parameters.labelWeight > 0) {
                        double max = 0;
                        for (int m = 0; m < l1.size(); m++) {
                            String t1 = new String(normalize(l1.get(m)));
                            for (int n = 0; n < l2.size(); n++) {
                                String t2 = new String(normalize(l2.get(n)));
                                double temp = ISub.getSimilarity(t1, t2);
                                if (temp > max) {
                                    max = temp;
                                }
                            }
                        }
                        s2 = Parameters.labelWeight * max;
                    }
                    if (c1 != null && c2 != null && Parameters.commentWeight > 0) {
                        double max = 0;
                        for (int m = 0; m < c1.size(); m++) {
                            String t1 = new String(normalize(c1.get(m)));
                            for (int n = 0; n < c2.size(); n++) {
                                String t2 = new String(normalize(c2.get(n)));
                                double temp = ISub.getSimilarity(t1, t2);
                                if (temp > max) {
                                    max = temp;
                                }
                            }
                        }
                        s3 = Parameters.commentWeight * max;
                    }
                    if (s1 > similarity) {
                        similarity = s1;
                    }
                    if (s2 > similarity) {
                        similarity = s2;
                    }
                    if (s3 > similarity) {
                        similarity = s3;
                    }
                } else if (Parameters.method == 1) {
                    if (n1 != null && n2 != null && Parameters.localnameWeight > 0) {
                        s1 = Parameters.localnameWeight * EditDistance.getSimilarity(n1, n2);
                    }
                    if (l1 != null && l2 != null && Parameters.labelWeight > 0) {
                        double max = 0;
                        for (int m = 0; m < l1.size(); m++) {
                            String t1 = new String(normalize(l1.get(m)));
                            for (int n = 0; n < l2.size(); n++) {
                                String t2 = new String(normalize(l2.get(n)));
                                double temp = ISub.getSimilarity(t1, t2);
                                if (temp > max) {
                                    max = temp;
                                }
                            }
                        }
                        s2 = Parameters.labelWeight * max;
                    }
                    if (c1 != null && c2 != null && Parameters.commentWeight > 0) {
                        double max = 0;
                        for (int m = 0; m < c1.size(); m++) {
                            String t1 = new String(normalize(c1.get(m)));
                            for (int n = 0; n < c2.size(); n++) {
                                String t2 = new String(normalize(c2.get(n)));
                                double temp = ISub.getSimilarity(t1, t2);
                                if (temp > max) {
                                    max = temp;
                                }
                            }
                        }
                        s3 = Parameters.commentWeight * max;
                    }
                    if (s1 > similarity) {
                        similarity = s1;
                    }
                    if (s2 > similarity) {
                        similarity = s2;
                    }
                    if (s3 > similarity) {
                        similarity = s3;
                    }
                }
                if (similarity != 0) {
                    matrix.set(i, j, similarity);
                }
            }
        }
        return matrix;
    }

    public void match()
    {
        alignment = new Alignment();
        classAlignment = AlignmentSelector.select(matchClasses(), Parameters.threshold);
        for (int i = 0, n = classAlignment.size(); i < n; i++) {
            alignment.addMapping(classAlignment.getMapping(i));
        }
        propertyAlignment = AlignmentSelector.select(matchProperties(), Parameters.threshold);
        for (int i = 0, n = propertyAlignment.size(); i < n; i++) {
            alignment.addMapping(propertyAlignment.getMapping(i));
        }
        if (Parameters.inclInstMatch) {
            instanceAlignment = AlignmentSelector.select(
            		matchInstances(), Parameters.threshold);
            for (int i = 0, n = instanceAlignment.size(); i < n; i++) {
                alignment.addMapping(instanceAlignment.getMapping(i));
            }
        }
    }

    public Alignment getAlignment()
    {
        return alignment;
    }

    public NamedMatrix matchClasses()
    {
        NodeList nodeListA = modelA.getNamedClassNodes();
        NodeList nodeListB = modelB.getNamedClassNodes();
        classMatrix = calcSimilarity(nodeListA, nodeListB);
        return classMatrix;
    }

    public NamedMatrix matchProperties()
    {
        NodeList nodeListA = modelA.getPropertyNodes();
        NodeList nodeListB = modelB.getPropertyNodes();
        propertyMatrix = calcSimilarity(nodeListA, nodeListB);
        return propertyMatrix;
    }

    public NamedMatrix matchInstances()
    {
        NodeList nodeListA = modelA.getNamedInstanceNodes();
        NodeList nodeListB = modelB.getNamedInstanceNodes();
        instanceMatrix = calcSimilarity(nodeListA, nodeListB);
        return instanceMatrix;
    }

    public Alignment getClassAlignment()
    {
        return classAlignment;
    }

    public Alignment getPropertyAlignment()
    {
        return propertyAlignment;
    }

    public Alignment getInstanceAlignment()
    {
        return instanceAlignment;
    }

    public NamedMatrix getClassMatrix()
    {
        return classMatrix;
    }

    public NamedMatrix getPropertyMatrix()
    {
        return propertyMatrix;
    }

    public NamedMatrix getInstanceMatrix()
    {
        return instanceMatrix;
    }
}
