
package fusion.comerger.general.gernalAnalysis.similarBlock;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import fusion.comerger.algorithm.matcher.string.StringMatcher;
import fusion.comerger.algorithm.partitioner.SeeCOnt.Cluster;
import fusion.comerger.general.output.Alignment;
import fusion.comerger.general.output.AlignmentWriter2;
import fusion.comerger.general.output.Mapping;
import fusion.comerger.model.Node;
import fusion.comerger.model.RBGModel;

public class BlockMatcher
{
    private double matrix[][] = null;
    private Alignment anchors = null;
    private ArrayList<BlockMapping> blockMappings = null;
    private RBGModel rbgModelA = null, rbgModelB = null;
    private String name1 = null, name2 = null;
    private HashMap<Integer, Cluster> clusters1 = null, clusters2 = null;
    private String tempDir = null;

    public Alignment getAnchors()
    {
        return anchors;
    }

    public ArrayList<BlockMapping> getBlockMappings()
    {
        return blockMappings;
    }

    public BlockMatcher(RBGModel modelA, RBGModel modelB, String n1, String n2,
            HashMap<Integer, Cluster> c1, HashMap<Integer, Cluster> c2, String dir)
    {
        rbgModelA = modelA;
        rbgModelB = modelB;
        name1 = n1;
        name2 = n2;
        clusters1 = c1;
        clusters2 = c2;
        tempDir = dir;
    }

    public void blockMatch()
    {
        step1_anchor();
//         System.out.println("[Block matching][step=1][" + anchors.size() 
//                + " anchors have been generated]"); 

        step2_match();
//         System.out.println("[Block matching][step=2][" + blockMappings.size() 
//                + " block mappings have been generated]"); 
    }

    public void step1_anchor()
    {
        anchors = new Alignment();
        StringMatcher matcher = new StringMatcher(rbgModelA, rbgModelB);
        matcher.match();
        Alignment cas = matcher.getClassAlignment();
        Alignment pas = matcher.getPropertyAlignment();
        for (int i = 0, n = cas.size(); i < n; i++) {
            anchors.addMapping(cas.getMapping(i));
        }
        for (int i = 0, n = pas.size(); i < n; i++) {
            anchors.addMapping(pas.getMapping(i));
        }
        String filepath = tempDir + "anchors.rdf";
        AlignmentWriter2 aw2 = new AlignmentWriter2(anchors, filepath);
        aw2.write(name1, name2, name1, name2);
    }

    public void step2_match()
    {
        HashMap<String, Mapping> temp = new HashMap<String, Mapping>(anchors.size());
        for (int i = 0, n = anchors.size(); i < n; i++) {
            Mapping anchor = anchors.getMapping(i);
            String uri1 = anchor.getEntity1().toString();
            String uri2 = anchor.getEntity2().toString();
            String key = uri1 + ":" + uri2;
            temp.put(key, anchor);
        }
        int size1 = clusters1.size();
        ArrayList<Cluster> temp1 = new ArrayList<Cluster>(size1);
        for (Iterator<Cluster> i = clusters1.values().iterator(); i.hasNext();) {
            Cluster cluster = i.next();
            temp1.add(cluster);
        }
        int size2 = clusters2.size();
        ArrayList<Cluster> temp2 = new ArrayList<Cluster>(size2);
        for (Iterator<Cluster> i = clusters2.values().iterator(); i.hasNext();) {
            Cluster cluster = i.next();
            temp2.add(cluster);
        }
        int anchorInClusters1[] = new int[size1];
        for (int i = 0; i < size1; i++) {
            Cluster cluster1 = temp1.get(i);
            HashMap<String, Node> elements1 = cluster1.getElements();
            for (Iterator<Node> i1 = elements1.values().iterator(); i1.hasNext();) {
                String uri1 = i1.next().toString();
                for (int j = 0; j < anchors.size(); j++) {
                    Mapping anchor = anchors.getMapping(j);
                    if (anchor.getEntity1().toString().equals(uri1)) {
                        anchorInClusters1[i]++;
                    }
                }
            }
        }
        int anchorInClusters2[] = new int[size2];
        for (int i = 0; i < size2; i++) {
            Cluster cluster2 = temp2.get(i);
            HashMap<String, Node> elements2 = cluster2.getElements();
            for (Iterator<Node> i2 = elements2.values().iterator(); i2.hasNext();) {
                String uri2 = i2.next().toString();
                for (int j = 0; j < anchors.size(); j++) {
                    Mapping anchor = anchors.getMapping(j);
                    if (anchor.getEntity2().toString().equals(uri2)) {
                        anchorInClusters2[i]++;
                    }
                }
            }
        }
        matrix = new double[size1][size2];
        for (int i = 0; i < size1; i++) {
            Cluster cluster1 = temp1.get(i);
            HashMap<String, Node> elements1 = cluster1.getElements();
            for (int j = 0; j < size2; j++) {
                Cluster cluster2 = temp2.get(j);
                HashMap<String, Node> elements2 = cluster2.getElements();
                for (Iterator<Node> i1 = elements1.values().iterator(); i1.hasNext();) {
                    String uri1 = i1.next().toString();
                    for (Iterator<Node> i2 = elements2.values().iterator(); i2.hasNext();) {
                        String uri2 = i2.next().toString();
                        String key = uri1 + ":" + uri2;
                        Mapping anchor = temp.get(key);
                        if (anchor != null) {
                            matrix[i][j] += anchor.getSimilarity();
                        }
                    }
                }
                matrix[i][j] = matrix[i][j] / (anchorInClusters1[i] + anchorInClusters2[j]);
            }
        }
        blockMappings = new ArrayList<BlockMapping>();
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                double sim = matrix[i][j];
                if (sim > Parameters.threshold) {
                    int cid1 = temp1.get(i).getClusterID();
                    int cid2 = temp2.get(j).getClusterID();
//                    String s1 = tempDir + name1.substring(0, name1.length() - 4) + "_Module_" + cid1 + ".owl";
//                    String s2 = tempDir + name2.substring(0, name2.length() - 4) + "_Module_" + cid2 + ".owl";
                    String s1 = name1.substring(0, name1.length() - 4) + "_Module_" + cid1 + ".owl";
                    String s2 = name2.substring(0, name2.length() - 4) + "_Module_" + cid2 + ".owl";
                    BlockMapping bm = new BlockMapping(s1, s2, sim);
                    blockMappings.add(bm);
                }
            }
        }
        String fp = tempDir + "bm.owl";
        BlockMappingWriter2 bmw = new BlockMappingWriter2(blockMappings, fp);
        bmw.write(name1, name2, name1, name2);
    }
}
