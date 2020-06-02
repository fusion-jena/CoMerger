
package fusion.comerger.algorithm.matcher.vdoc;
/* 
 * This package is downloaded from the FALCON-AO tool, 
 * available in http://ws.nju.edu.cn/falcon-ao/
 * For more information of this method, please refer to
 * Hu, W. and Qu, Y., 2008. Falcon-AO: A practical ontology matching system. Journal of web semantics, 6(3), pp.237-239.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import fusion.comerger.model.Node;
import fusion.comerger.model.RBGModel;

public class VDocParser 
{
    private TokenNodeSet tokenNodes = new TokenNodeSet();
    private NeighborSet neighbors = new NeighborSet();
    private Tokenizer tokenizer = new Tokenizer();
    private Stopword stopword  ;//new Stopword();

    
	public void run(RBGModel model)
    {
        parseNamedClasses(model);
        parseProperties(model);
        parseInstances(model);

        if (Parameters.inclBlankNode) {
            parseBlankNodes(model);
        }
        if (Parameters.inclNeighbor) {
            parseNeighbors(model);

        }
    }

    private void parseNamedClasses(RBGModel model)
    {
    	stopword = new Stopword(); 
        for (Iterator<Node> iter = model.listNamedClassNodes(); iter.hasNext();) {
            Node node = iter.next();
            String n = node.getLocalName();
            HashMap<String, WordFrequency> localname = new HashMap<String, WordFrequency>();
            if (n != null) {
                setWordTimes(n, localname);
            }
            ArrayList<String> l = node.getLabel();
            HashMap<String, WordFrequency> label = new HashMap<String, WordFrequency>();
            if (l != null) {
                for (int i = 0; i < l.size(); i++) {
                    setWordTimes(l.get(i), label);
                }
            }
            ArrayList<String> c = node.getComment();
            HashMap<String, WordFrequency> comment = new HashMap<String, WordFrequency>();
            if (c != null) {
                for (int i = 0; i < c.size(); i++) {
                    setWordTimes(c.get(i), comment);
                }
            }
            tokenNodes.add(new TokenNode(node, localname, label, comment));
            neighbors.addNeighbor(new Neighbor(node.toString()));
        }
    }

    private void parseProperties(RBGModel model)
    {
        for (Iterator<Node> iter = model.listPropertyNodes(); iter.hasNext();) {
            Node node = iter.next();
            String n = node.getLocalName();
            HashMap<String, WordFrequency> localname = new HashMap<String, WordFrequency>();
            if (n != null) {
                setWordTimes(n, localname);
            }
            ArrayList<String> l = node.getLabel();
            HashMap<String, WordFrequency> label = new HashMap<String, WordFrequency>();
            if (l != null) {
                for (int i = 0; i < l.size(); i++) {
                    setWordTimes(l.get(i), label);
                }
            }
            ArrayList<String> c = node.getComment();
            HashMap<String, WordFrequency> comment = new HashMap<String, WordFrequency>();
            if (c != null) {
                for (int i = 0; i < c.size(); i++) {
                    setWordTimes(c.get(i), comment);
                }
            }
            tokenNodes.add(new TokenNode(node, localname, label, comment));
            neighbors.addNeighbor(new Neighbor(node.toString()));
        }
    }

    private void parseInstances(RBGModel model)
    {
        for (Iterator<Node> iter = model.listNamedInstanceNodes(); iter.hasNext();) {
            Node node = iter.next();
            String n = node.getLocalName();
            HashMap<String, WordFrequency> localname = new HashMap<String, WordFrequency>();
            if (n != null) {
                setWordTimes(n, localname);
            }
            ArrayList<String> l = node.getLabel();
            HashMap<String, WordFrequency> label = new HashMap<String, WordFrequency>();
            if (l != null) {
                for (int i = 0; i < l.size(); i++) {
                    setWordTimes(l.get(i), label);
                }
            }
            ArrayList<String> c = node.getComment();
            HashMap<String, WordFrequency> comment = new HashMap<String, WordFrequency>();
            if (c != null) {
                for (int i = 0; i < c.size(); i++) {
                    setWordTimes(c.get(i), comment);
                }
            }
            tokenNodes.add(new TokenNode(node, localname, label, comment));
            neighbors.addNeighbor(new Neighbor(node.toString()));
        }
    }

    private void setWordTimes(String s, HashMap<String, WordFrequency> hm)
    {
        String str[] = stopword.removeStopword(tokenizer.split(s));
        if (str != null && str.length != 0) {
            int size = str.length;
            for (int i = 0; i < size; i++) {
                if (hm.containsKey(str[i])) {
                    WordFrequency wf = hm.get(str[i]);
                    wf.setFrequency(wf.getFrequency() + 1);
                } else {
                    hm.put(str[i], new WordFrequency(str[i], 1));
                }
            }
        }
    }

    private void parseBlankNodes(RBGModel model)
    {
        ArrayList<Node> bnode = new ArrayList<Node>();
        ArrayList<Node> relatedTriples = new ArrayList<Node>();
        for (Iterator<Node> iter = model.listStmtNodes(); iter.hasNext();) {
            Node node = iter.next();
            Node subject = node.getSubject();
            if (subject.isAnon()) {
                if (!bnode.contains(subject.toString())) {
                    bnode.add(subject);
                }
                if (!relatedTriples.contains(node)) {
                    relatedTriples.add(node);
                }
            }
        }
        for (int i = 0, n = bnode.size(); i < n; i++) {
            Node b = (Node) bnode.get(i);
            HashMap<String, WordFrequency> hmn = new HashMap<String, WordFrequency>();
            HashMap<String, WordFrequency> hml = new HashMap<String, WordFrequency>();
            HashMap<String, WordFrequency> hmc = new HashMap<String, WordFrequency>();
            labelOfBlankNode(b, relatedTriples, hmn, hml, hmc, Parameters.initBlankNode);
            tokenNodes.add(new TokenNode(b, hmn, hml, hmc));

        }
    }

    private void labelOfBlankNode(Node b, ArrayList<Node> list,
    		HashMap<String, WordFrequency> hmn, HashMap<String, WordFrequency> hml, 
    		HashMap<String, WordFrequency> hmc, double v)
    {
        for (int i = 0, n = list.size(); i < n; i++) {
            Node stmt = list.get(i);
            String t = stmt.getSubject().toString();
            if (b.toString().equals(t) == true) {
                Node predicate = stmt.getPredicate();
                if (predicate.isAnon()) {
                    labelOfBlankNode(predicate, list, hmn, hml, hmc, v / Parameters.iterValue);
                } else {
                    TokenNode node = tokenNodes.get(predicate.toString());
                    if (node != null) {
                        HashMap<String, WordFrequency> localname = node.getLocalName();
                        if (localname != null) {
                            for (Iterator<WordFrequency> iter = 
                            		localname.values().iterator(); iter.hasNext();) {
                                WordFrequency wf = iter.next();
                                if (hmn.containsKey(wf.getWord())) {
                                    WordFrequency temp = hmn.get(wf.getWord());
                                    temp.setFrequency(temp.getFrequency() + v);
                                } else {
                                    WordFrequency temp = new WordFrequency(wf.getWord(), v);
                                    hmn.put(temp.getWord(), temp);
                                }
                            }
                        }
                        HashMap<String, WordFrequency> label = node.getLabel();
                        if (label != null) {
                            for (Iterator<WordFrequency> iter = 
                            		label.values().iterator(); iter.hasNext();) {
                                WordFrequency wf = iter.next();
                                if (hml.containsKey(wf.getWord())) {
                                    WordFrequency temp = hml.get(wf.getWord());
                                    temp.setFrequency(temp.getFrequency() + v);
                                } else {
                                    WordFrequency temp = new WordFrequency(wf.getWord(), v);
                                    hml.put(temp.getWord(), temp);
                                }
                            }
                        }
                        HashMap<String, WordFrequency> comment = node.getComment();
                        if (comment != null) {
                            for (Iterator<WordFrequency> iter = 
                            		comment.values().iterator(); iter.hasNext();) {
                                WordFrequency wf = (WordFrequency) iter.next();
                                if (hmc.containsKey(wf.getWord())) {
                                    WordFrequency temp = hmc.get(wf.getWord());
                                    temp.setFrequency(temp.getFrequency() + v);
                                } else {
                                    WordFrequency temp = new WordFrequency(wf.getWord(), v);
                                    hmc.put(temp.getWord(), temp);
                                }
                            }
                        }
                    }
                }
                Node object = stmt.getObject();
                if (!object.isLiteral()) {
                    if (object.isAnon()) {
                        labelOfBlankNode(object, list, hmn, hml, hmc, v / Parameters.iterValue);
                    } else {
                        TokenNode node = tokenNodes.get(object.toString());
                        if (node != null) {
                            HashMap<String, WordFrequency> localname = node.getLocalName();
                            if (localname != null) {
                                for (Iterator<WordFrequency> iter = 
                                		localname.values().iterator(); iter.hasNext();) {
                                    WordFrequency wf = iter.next();
                                    if (hmn.containsKey(wf.getWord())) {
                                        WordFrequency temp = hmn.get(wf.getWord());
                                        temp.setFrequency(temp.getFrequency() + v);
                                    } else {
                                        WordFrequency temp = new WordFrequency(wf.getWord(), v);
                                        hmn.put(temp.getWord(), temp);
                                    }
                                }
                            }
                            HashMap<String, WordFrequency> label = node.getLabel();
                            if (label != null) {
                                for (Iterator<WordFrequency> iter = 
                                		label.values().iterator(); iter.hasNext();) {
                                    WordFrequency wf = iter.next();
                                    if (hml.containsKey(wf.getWord())) {
                                        WordFrequency temp = hml.get(wf.getWord());
                                        temp.setFrequency(temp.getFrequency() + v);
                                    } else {
                                        WordFrequency temp = new WordFrequency(wf.getWord(), v);
                                        hml.put(temp.getWord(), temp);
                                    }
                                }
                            }
                            HashMap<String, WordFrequency> comment = node.getComment();
                            if (comment != null) {
                                for (Iterator<WordFrequency> iter = 
                                		comment.values().iterator(); iter.hasNext();) {
                                    WordFrequency wf = iter.next();
                                    if (hmc.containsKey(wf.getWord())) {
                                        WordFrequency temp = hmc.get(wf.getWord());
                                        temp.setFrequency(temp.getFrequency() + v);
                                    } else {
                                        WordFrequency temp = new WordFrequency(wf.getWord(), v);
                                        hmc.put(temp.getWord(), temp);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void parseNeighbors(RBGModel model)
    {
        for (Iterator<Node> iter = model.listStmtNodes(); iter.hasNext();) {
            Node stmt = iter.next();
            Node subject = stmt.getSubject();
            Node predicate = stmt.getPredicate();
            Node object = stmt.getObject();

            TokenNode s = tokenNodes.get(subject.toString());
            TokenNode p = tokenNodes.get(predicate.toString());
            TokenNode o = null;
            if (!object.isLiteral()) {
                o = tokenNodes.get(object.toString());
            }
            if (s != null) {
                Neighbor neighbor = neighbors.getNeighbor(s.getURI());
                if (neighbor != null) {
                    if (p != null) {
                        neighbor.addPredicate(p);
                    }
                    if (o != null) {
                        neighbor.addObject(o);
                    }
                }
            }
            if (p != null) {
                Neighbor neighbor = neighbors.getNeighbor(p.getURI());
                if (neighbor != null) {
                    if (s != null) {
                        neighbor.addSubject(s);
                    }
                    if (o != null) {
                        neighbor.addObject(o);
                    }
                }
            }
            if (o != null) {
                Neighbor neighbor = neighbors.getNeighbor(o.getURI());
                if (neighbor != null) {
                    if (s != null) {
                        neighbor.addSubject(s);
                    }
                    if (p != null) {
                        neighbor.addPredicate(p);
                    }
                }
            }
        }
    }

    public TokenNodeSet getTokenNodes()
    {
        return tokenNodes;
    }

    public NeighborSet getNeighbors()
    {
        return neighbors;
    }

    public void show()
    {
        tokenNodes.show();
        neighbors.show();
    }
}
