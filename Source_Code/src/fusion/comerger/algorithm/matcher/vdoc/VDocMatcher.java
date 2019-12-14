
package fusion.comerger.algorithm.matcher.vdoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import fusion.comerger.algorithm.matcher.AbstractMatcher;
import fusion.comerger.general.gernalAnalysis.matrix.BasicMatrix;
import fusion.comerger.general.gernalAnalysis.matrix.NamedMatrix;
import fusion.comerger.general.output.Alignment;
import fusion.comerger.general.output.AlignmentSelector;
import fusion.comerger.model.Node;
import fusion.comerger.model.RBGModel;

public class VDocMatcher implements AbstractMatcher
{
    private ArrayList<Object> classDocList = new ArrayList<Object>();
    private ArrayList<Object> propertyDocList = new ArrayList<Object>();
    private ArrayList<Object> instanceDocList = new ArrayList<Object>();
    private ArrayList<Object> wordList = new ArrayList<Object>();
    private int classSeparator = 0;
    private int propertySeparator = 0;
    private int instanceSeparator = 0;
    private TokenNodeSet tns1 = null,  tns2 = null;
    private NeighborSet ns1 = null,  ns2 = null;
    private RBGModel modelA = null,  modelB = null;
    private NamedMatrix cbm = null,  csm = null,  cpm = null,  com = null;
    private NamedMatrix pbm = null,  psm = null,  ppm = null,  pom = null;
    private NamedMatrix ibm = null,  ism = null,  ipm = null,  iom = null;
    private Alignment alignment = null;
    private Alignment classAlignment = null;
    private Alignment propertyAlignment = null;
    private Alignment instanceAlignment = null;
    private NamedMatrix namedClassMatrix = null;
    private NamedMatrix namedPropertyMatrix = null;
    private NamedMatrix namedInstanceMatrix = null;
    
   	
    public VDocMatcher(RBGModel modelA, RBGModel modelB)
    {
        this.modelA = modelA;
        this.modelB = modelB;
    }

    private void initWords(TokenNodeSet tns, HashMap<String, WordFrequency> words)
    {
        for (Iterator<TokenNode> iter = tns.iterator(); iter.hasNext();) {
            TokenNode node = iter.next();
            if (node.getNodeCategory() == Node.CLASS && !node.getNode().isAnon()) {
                classDocList.add(node.getNode());
            } else if (node.getNodeCategory() >= Node.OBJECTPROPERTY && 
            		node.getNodeCategory() <= Node.DATATYPEPROPERTY && 
            		!node.getNode().isAnon()) {
                propertyDocList.add(node.getNode());
            } else if (node.getNodeCategory() == Node.INSTANCE && 
            		!node.getNode().isAnon()) {
                instanceDocList.add(node.getNode());
            }
            if (Parameters.localnameWeight > 0) {
                HashMap<String, WordFrequency> t = node.getLocalName();
                for (Iterator<WordFrequency> i = t.values().iterator(); i.hasNext();) {
                    WordFrequency wf = i.next();
                    words.put(wf.getWord(), wf);
                }
            }
            if (Parameters.labelWeight > 0) {
                HashMap<String, WordFrequency> t = node.getLabel();
                for (Iterator<WordFrequency> i = t.values().iterator(); i.hasNext();) {
                    WordFrequency wf = i.next();
                    words.put(wf.getWord(), wf);
                }
            }
            if (Parameters.commentWeight > 0) {
                HashMap<String, WordFrequency> t = node.getComment();
                for (Iterator<WordFrequency> i = t.values().iterator(); i.hasNext();) {
                    WordFrequency wf = i.next();
                    words.put(wf.getWord(), wf);
                }
            }
        }
    }

    public void init()
    {
        HashMap<String, WordFrequency> words = new HashMap<String, WordFrequency>();

        VDocParser parser1 = new VDocParser();
        parser1.run(modelA);
        tns1 = parser1.getTokenNodes();
        ns1 = parser1.getNeighbors();
        initWords(tns1, words);

        classSeparator = classDocList.size();
        propertySeparator = propertyDocList.size();
        instanceSeparator = instanceDocList.size();

        VDocParser parser2 = new VDocParser();
        parser2.run(modelB);
        tns2 = parser2.getTokenNodes();
        ns2 = parser2.getNeighbors();
        initWords(tns2, words);

        for (Iterator<WordFrequency> i = words.values().iterator(); i.hasNext();) {
            String word = i.next().getWord();
            wordList.add(word);
        }
        System.gc(); 
        cbm = new NamedMatrix(wordList, classDocList);
        csm = new NamedMatrix(wordList, classDocList);
        cpm = new NamedMatrix(wordList, classDocList);
        com = new NamedMatrix(wordList, classDocList);
        pbm = new NamedMatrix(wordList, propertyDocList);
        psm = new NamedMatrix(wordList, propertyDocList);
        ppm = new NamedMatrix(wordList, propertyDocList);
        pom = new NamedMatrix(wordList, propertyDocList);
        ibm = new NamedMatrix(wordList, instanceDocList);
        ism = new NamedMatrix(wordList, instanceDocList);
        ipm = new NamedMatrix(wordList, instanceDocList);
        iom = new NamedMatrix(wordList, instanceDocList);

        HashMap<String, Neighbor> nl1 = ns1.getNeighbors();
        for (Iterator<Neighbor> iter = nl1.values().iterator(); iter.hasNext();) {
            Neighbor neighbor = iter.next();
            String uri = neighbor.getURI();
            TokenNode node = tns1.get(uri);
            if (node.getNodeCategory() == Node.CLASS && !node.getNode().isAnon()) {
                addTokenNodeToWordList(classDocList, node, node, cbm);
                if (Parameters.inclNeighbor) {
                    HashMap<String, TokenNode> sm = neighbor.getSubjects();
                    for (Iterator<TokenNode> i = sm.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(classDocList, node, n, com);
                    }
                    HashMap<String, TokenNode> pm = neighbor.getPredicates();
                    for (Iterator<TokenNode> i = pm.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(classDocList, node, n, cpm);
                    }
                    HashMap<String, TokenNode> om = neighbor.getObjects();
                    for (Iterator<TokenNode> i = om.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(classDocList, node, n, csm);
                    }
                }
            } else if (node.getNodeCategory() >= Node.OBJECTPROPERTY && 
            		node.getNodeCategory() <= Node.DATATYPEPROPERTY && 
            		!node.getNode().isAnon()) {
                addTokenNodeToWordList(propertyDocList, node, node, pbm);
                if (Parameters.inclNeighbor) {
                    HashMap<String, TokenNode> sm = neighbor.getSubjects();
                    for (Iterator<TokenNode> i = sm.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(propertyDocList, node, n, pom);
                    }
                    HashMap<String, TokenNode> pm = neighbor.getPredicates();
                    for (Iterator<TokenNode> i = pm.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(propertyDocList, node, n, ppm);
                    }
                    HashMap<String, TokenNode> om = neighbor.getObjects();
                    for (Iterator<TokenNode> i = om.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(propertyDocList, node, n, psm);
                    }
                }
            } else if (node.getNodeCategory() == Node.INSTANCE && 
            		!node.getNode().isAnon()) {
                addTokenNodeToWordList(instanceDocList, node, node, ibm);
                if (Parameters.inclNeighbor) {
                    HashMap<String, TokenNode> sm = neighbor.getSubjects();
                    for (Iterator<TokenNode> i = sm.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(instanceDocList, node, n, iom);
                    }
                    HashMap<String, TokenNode> pm = neighbor.getPredicates();
                    for (Iterator<TokenNode> i = pm.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(instanceDocList, node, n, ipm);
                    }
                    HashMap<String, TokenNode> om = neighbor.getObjects();
                    for (Iterator<TokenNode> i = om.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(instanceDocList, node, n, ism);
                    }
                }
            }
        }

        HashMap<String, Neighbor> nl2 = ns2.getNeighbors();
        for (Iterator<Neighbor> iter = nl2.values().iterator(); iter.hasNext();) {
            Neighbor neighbor = iter.next();
            String uri = neighbor.getURI();
            TokenNode node = tns2.get(uri);
            if (node.getNodeCategory() == Node.CLASS && !node.getNode().isAnon()) {
                addTokenNodeToWordList(classDocList, node, node, cbm);
                if (Parameters.inclNeighbor) {
                    HashMap<String, TokenNode> sm = neighbor.getSubjects();
                    for (Iterator<TokenNode> i = sm.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(classDocList, node, n, com);
                    }
                    HashMap<String, TokenNode> pm = neighbor.getPredicates();
                    for (Iterator<TokenNode> i = pm.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(classDocList, node, n, cpm);
                    }
                    HashMap<String, TokenNode> om = neighbor.getObjects();
                    for (Iterator<TokenNode> i = om.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(classDocList, node, n, csm);
                    }
                }
            } else if (node.getNodeCategory() >= Node.OBJECTPROPERTY && 
            		node.getNodeCategory() <= Node.DATATYPEPROPERTY && 
            		!node.getNode().isAnon()) {
                addTokenNodeToWordList(propertyDocList, node, node, pbm);
                if (Parameters.inclNeighbor) {
                    HashMap<String, TokenNode> sm = neighbor.getSubjects();
                    for (Iterator<TokenNode> i = sm.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(propertyDocList, node, n, pom);
                    }
                    HashMap<String, TokenNode> pm = neighbor.getPredicates();
                    for (Iterator<TokenNode> i = pm.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(propertyDocList, node, n, ppm);
                    }
                    HashMap<String, TokenNode> om = neighbor.getObjects();
                    for (Iterator<TokenNode> i = om.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(propertyDocList, node, n, psm);
                    }
                }
            } else if (node.getNodeCategory() == Node.INSTANCE && 
            		!node.getNode().isAnon()) {
                addTokenNodeToWordList(instanceDocList, node, node, ibm);
                if (Parameters.inclNeighbor) {
                    HashMap<String, TokenNode> sm = neighbor.getSubjects();
                    for (Iterator<TokenNode> i = sm.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(instanceDocList, node, n, iom);
                    }
                    HashMap<String, TokenNode> pm = neighbor.getPredicates();
                    for (Iterator<TokenNode> i = pm.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(instanceDocList, node, n, ipm);
                    }
                    HashMap<String, TokenNode> om = neighbor.getObjects();
                    for (Iterator<TokenNode> i = om.values().iterator(); i.hasNext();) {
                        TokenNode n = i.next();
                        addTokenNodeToWordList(instanceDocList, node, n, ism);
                    }
                }
            }
        }
    }

    private void addTokenNodeToWordList(ArrayList<Object> docList, TokenNode node,
            TokenNode n, NamedMatrix m)
    {
        int col = -1;
        for (int k = 0, size = docList.size(); k < size; k++) {
            if (docList.get(k).toString().equals(node.getNode().toString())) {
                col = k;
            }
        }
        if (col == -1) {
            return;
        }
        HashMap<String, WordFrequency> localname = n.getLocalName();
        HashMap<String, WordFrequency> label = n.getLabel();
        HashMap<String, WordFrequency> comment = n.getComment();
        if (Parameters.localnameWeight > 0 && localname != null) {
            for (Iterator<WordFrequency> i = localname.values().iterator(); i.hasNext();) {
                WordFrequency wf = i.next();
                int row = wordList.indexOf(wf.getWord());
                double value = m.get(row, col) + 
                	wf.getFrequency() * Parameters.localnameWeight;
                m.set(row, col, value);
            }
        }
        if (Parameters.labelWeight > 0 && label != null) {
            for (Iterator<WordFrequency> i = label.values().iterator(); i.hasNext();) {
                WordFrequency wf = i.next();
                int row = wordList.indexOf(wf.getWord());
                double value = m.get(row, col) + 
                	wf.getFrequency() * Parameters.labelWeight;
                m.set(row, col, value);
            }
        }
        if (Parameters.commentWeight > 0 && comment != null) {
            for (Iterator<WordFrequency> i = comment.values().iterator(); i.hasNext();) {
                WordFrequency wf = i.next();
                int row = wordList.indexOf(wf.getWord());
                double value = m.get(row, col) + 
                	wf.getFrequency() * Parameters.commentWeight;
                m.set(row, col, value);
            }
        }
    }

    private BasicMatrix calcSimilarity(int numRows, int numColumns,
            NamedMatrix bm, NamedMatrix sm, NamedMatrix pm, NamedMatrix om)
    {
        BasicMatrix b = new BasicMatrix(numRows, numColumns);
        BasicMatrix s = new BasicMatrix(numRows, numColumns);
        BasicMatrix p = new BasicMatrix(numRows, numColumns);
        BasicMatrix o = new BasicMatrix(numRows, numColumns);

        double sumColumns[] = new double[numColumns];
        double maxDoc = numColumns;
        for (int j = 0; j < numColumns; j++) {
            sumColumns[j] = bm.sumEntriesInColumn(j);
        }
        for (int i = 0; i < numRows; i++) {
            double docTimes = bm.countNonZeroEntriesInRow(i);
            for (int j = 0; j < numColumns; j++) {
                double maxTerm = sumColumns[j];
                double termTimes = bm.get(i, j);
                double weight = getTermWeight(termTimes, docTimes, maxTerm, maxDoc);
                if (weight != 0) {
                    double value = b.get(i, j) + weight;
                    b.set(i, j, value);
                }
            }
        }
        maxDoc = numColumns;
        for (int j = 0; j < numColumns; j++) {
            sumColumns[j] = sm.sumEntriesInColumn(j);
        }
        for (int i = 0; i < numRows; i++) {
            double docTimes = sm.countNonZeroEntriesInRow(i);
            for (int j = 0; j < numColumns; j++) {
                double maxTerm = sumColumns[j];
                double termTimes = sm.get(i, j);
                double weight = getTermWeight(termTimes, docTimes, maxTerm, maxDoc);
                if (weight != 0) {
                    double value = s.get(i, j) + weight;
                    s.set(i, j, value);
                }
            }
        }
        maxDoc = numColumns;
        for (int j = 0; j < numColumns; j++) {
            sumColumns[j] = pm.sumEntriesInColumn(j);
        }
        for (int i = 0; i < numRows; i++) {
            double docTimes = pm.countNonZeroEntriesInRow(i);
            for (int j = 0; j < numColumns; j++) {
                double maxTerm = sumColumns[j];
                double termTimes = pm.get(i, j);
                double weight = getTermWeight(termTimes, docTimes, maxTerm, maxDoc);
                if (weight != 0) {
                    double value = p.get(i, j) + weight;
                    p.set(i, j, value);
                }
            }
        }
        maxDoc = numColumns;
        for (int j = 0; j < numColumns; j++) {
            sumColumns[j] = om.sumEntriesInColumn(j);
        }
        for (int i = 0; i < numRows; i++) {
            double docTimes = om.countNonZeroEntriesInRow(i);
            for (int j = 0; j < numColumns; j++) {
                double maxTerm = sumColumns[j];
                double termTimes = om.get(i, j);
                double weight = getTermWeight(termTimes, docTimes, maxTerm, maxDoc);
                if (weight != 0) {
                    double value = o.get(i, j) + weight;
                    o.set(i, j, value);
                }
            }
        }

        BasicMatrix temp = b.times(Parameters.basicWeight).copyAdd(
                s.times(Parameters.subjectWeight).copyAdd(
                p.times(Parameters.predicateWeight).copyAdd(
                o.times(Parameters.objectWeight))));
        return temp.cosine();
    }

    public void match()
    {
        init();
        alignment = new Alignment();
        int numRows = wordList.size();

        int classNumColumns = classDocList.size();
        BasicMatrix classMatrix = calcSimilarity(numRows, classNumColumns,
                cbm, csm, cpm, com);
        ArrayList<Object> classRowList = new ArrayList<Object>();
        ArrayList<Object> classColumnList = new ArrayList<Object>();
        for (int i = 0, n = classDocList.size(); i < n; i++) {
            if (i < classSeparator) {
                classRowList.add(classDocList.get(i));
            } else {
                classColumnList.add(classDocList.get(i));
            }
        }
        namedClassMatrix = new NamedMatrix(classRowList, classColumnList);
        for (int i = 0, m = namedClassMatrix.numRows(); i < m; i++) {
            for (int j = 0, n = namedClassMatrix.numColumns(); j < n; j++) {
                double value = classMatrix.get(i, j + classSeparator);
                namedClassMatrix.set(i, j, value);
            }
        }
        classAlignment = AlignmentSelector.select(
        		namedClassMatrix, Parameters.threshold);
        for (int i = 0, n = classAlignment.size(); i < n; i++) {
            alignment.addMapping(classAlignment.getMapping(i));
        }
        int propertyNumColumns = propertyDocList.size();
        BasicMatrix propertyMatrix = calcSimilarity(numRows, propertyNumColumns, 
        		pbm, psm, ppm, pom);
        ArrayList<Object> propertyRowList = new ArrayList<Object>();
        ArrayList<Object> propertyColumnList = new ArrayList<Object>();
        for (int i = 0, n = propertyDocList.size(); i < n; i++) {
            if (i < propertySeparator) {
                propertyRowList.add(propertyDocList.get(i));
            } else {
                propertyColumnList.add(propertyDocList.get(i));
            }
        }
        namedPropertyMatrix = new NamedMatrix(propertyRowList, propertyColumnList);
        for (int i = 0, m = namedPropertyMatrix.numRows(); i < m; i++) {
            for (int j = 0, n = namedPropertyMatrix.numColumns(); j < n; j++) {
                double value = propertyMatrix.get(i, j + propertySeparator);
                namedPropertyMatrix.set(i, j, value);
            }
        }
        propertyAlignment = AlignmentSelector.select(
        		namedPropertyMatrix, Parameters.threshold);
        for (int i = 0, n = propertyAlignment.size(); i < n; i++) {
            alignment.addMapping(propertyAlignment.getMapping(i));
        }
        if (Parameters.inclInstMatch) {
            int instanceNumColumns = instanceDocList.size();
            BasicMatrix instanceMatrix = calcSimilarity(numRows,
                    instanceNumColumns, ibm, ism, ipm, iom);
            ArrayList<Object> instanceRowList = new ArrayList<Object>();
            ArrayList<Object> instanceColumnList = new ArrayList<Object>();
            for (int i = 0, n = instanceDocList.size(); i < n; i++) {
                if (i < instanceSeparator) {
                    instanceRowList.add(instanceDocList.get(i));
                } else {
                    instanceColumnList.add(instanceDocList.get(i));
                }
            }
            namedInstanceMatrix = new NamedMatrix(instanceRowList, instanceColumnList);
            for (int i = 0, m = namedInstanceMatrix.numRows(); i < m; i++) {
                for (int j = 0, n = namedInstanceMatrix.numColumns(); j < n; j++) {
                    double value = instanceMatrix.get(i, j + instanceSeparator);
                    namedInstanceMatrix.set(i, j, value);
                }
            }
            instanceAlignment = AlignmentSelector.select(
                    namedInstanceMatrix, Parameters.threshold);
            for (int i = 0, n = instanceAlignment.size(); i < n; i++) {
                alignment.addMapping(instanceAlignment.getMapping(i));
            }
        }
    // return alignmentSet;
    }

    private double getTermWeight(double termtimes, double doctimes,
            double maxterm, double maxdoc)
    {
        if (termtimes == 0) {
            return 0;
        }
        double tf = termtimes / maxterm;
        double idf = (1 + Math.log(maxdoc / doctimes) / Math.log(2)) / 2;
        return (tf * idf);
    }

    public Alignment getAlignment()
    {
        return alignment;
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
        return namedClassMatrix;
    }

    public NamedMatrix getPropertyMatrix()
    {
        return namedPropertyMatrix;
    }

    public NamedMatrix getInstanceMatrix()
    {
        return namedInstanceMatrix;
    }
	

	

    /* public static void main(String args[]) 
    { 
    	OntDocumentManager mgr = new OntDocumentManager(); 
    	mgr.setProcessImports(false); 
    	OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM); 
    	spec.setDocumentManager(mgr);
    	OntModel model1 = ModelFactory.createOntologyModel(spec, null); 
    	String op1 = "file:./sample/101.owl"; 
    	model1.read(op1); 
    	OntModel model2 = ModelFactory.createOntologyModel(spec, null); 
    	String op2 = "file:./sample/304.owl"; 
    	model2.read(op2); 
    	RBGModel rbgModelString1 = RBGModelFactory.createModel("VDOC_MODEL");
    	rbgModelString1.setOntModel(model1); 
    	RBGModel rbgModelString2 = RBGModelFactory.createModel("VDOC_MODEL");
    	rbgModelString2.setOntModel(model2); 
    	VDocMatcher vdocMatcher = new VDocMatcher(rbgModelString1, rbgModelString2); 
    	vdocMatcher.match();
    	Alignment align = vdocMatcher.getAlignment(); 
    	AlignmentWriter2 writer = new AlignmentWriter2(align, "./sample/falcon.rdf");
    	writer.write("onto1", "onto2", "uri1", "uri2");
    	AlignmentReader2 reader = new AlignmentReader2(op1, op2, "./sample/refalign.rdf"); 
    	Alignment ref = reader.read(); 
    	Evaluator evaluator = new Evaluator();
    	evaluator.compare(align, ref); 
    } */
}
