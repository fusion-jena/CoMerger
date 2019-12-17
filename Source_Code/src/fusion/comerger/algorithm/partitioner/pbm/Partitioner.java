package fusion.comerger.algorithm.partitioner.pbm;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;

import fusion.comerger.general.cc.Controller;
import fusion.comerger.model.Node;
import fusion.comerger.model.NodeList;
import fusion.comerger.model.RBGModel;
import fusion.comerger.model.ext.sentence.RDFSentence;
import fusion.comerger.model.ext.sentence.RDFSentenceGraph;
import fusion.comerger.model.ext.sentence.filter.OntologyHeaderFilter;
import fusion.comerger.model.ext.sentence.filter.PureSchemaFilter;




public class Partitioner
{
    private RBGModel rbgModel = null;
    private String ontName = null;
    private int maxSize = Integer.MAX_VALUE;
    private String tempDir = null;
    private NodeList entities = null;
    private HashMap<String, Link> links = null;
    private HashMap<Integer, pbmCluster> clusters = null;
    private ArrayList<OntModel> models = null;

    public Partitioner(RBGModel model, String name, int ms, String td)
    {
        rbgModel = model;
        ontName = name;
        maxSize = ms; 
        tempDir = td; 
    }

    public NodeList getEntities()
    {
        return entities;
    }

    public HashMap<String, Link> getLinks()
    {
        return links;
    }

    public HashMap<Integer, pbmCluster> getClusters()
    {
        return clusters;
    }

    public ArrayList<OntModel> getOntModels()
    {
        return models;
    }

    public void partition()
    {
        step1_parse();
        /* System.out.println("[Partitioning][step=1][" + entities.size() 
                + " entities have been generated]"); */

        step2_link();
        /* System.out.println("[Partitioning][step=2][" + links.size() 
                + " links have been generated]"); */

        step3_partition();
        /* System.out.println("[Partitioning][step=3][" + clusters.size()
                + " clusters have been generated]"); */

        step4_generate();
        /* System.out.println("[Partitioning][step=4][" + models.size() 
                + " blocks have been generated]"); */
    }

    private void step1_parse()
    {
        entities = new NodeList();
        for (Iterator<Node> i = rbgModel.listNamedClassNodes(); i.hasNext();) {
            entities.add(i.next());
        }
        for (Iterator<Node> i = rbgModel.listPropertyNodes(); i.hasNext();) {
            entities.add(i.next());
        }
    }

    private void step2_link()
    {
        LinkGenerator lg = new LinkGenerator();
        lg.initEntities(entities);
        lg.generateStructuralLinks1(); // hierarchical distance
        lg.generateStructuralLinks2(); // overlapped domain
        lg.combine();
        links = lg.getLinks();
    }

    private void step3_partition()
    {
        ClusterGenerator op = new ClusterGenerator();
        op.initClusters(entities, links);
        op.executePartitioning(maxSize);
        clusters = op.getClusters();
    }

    private void step4_generate()
    {
        ArrayList<pbmCluster> list = new ArrayList<pbmCluster>();
        for (Iterator<pbmCluster> i = clusters.values().iterator(); i.hasNext();) {
            list.add(i.next());
        }
        HashMap<String, Integer> uriToClusterID = new HashMap<String, Integer>();
        for (int i = 0, n = list.size(); i < n; i++) {
        	pbmCluster cluster = list.get(i);
            int clusterID = cluster.getClusterID();
            for (Iterator<Node> iter = cluster.listElements(); iter.hasNext();) {
                String uri = iter.next().toString();
                uriToClusterID.put(uri, clusterID);
            }
        }
        RDFSentenceGraph sg = new RDFSentenceGraph(rbgModel.getOntModel());
        sg.build();
        sg.filter(new OntologyHeaderFilter(sg.getOntologyURIs()));
        sg.filter(new PureSchemaFilter());
        models = new ArrayList<OntModel>(list.size());
        HashMap<Integer, Integer> clusterIDToOntModelID = new HashMap<Integer, Integer>();
        for (int i = 0, n = list.size(); i < n; i++) {
            models.add(ModelFactory.createOntologyModel());
            int cid = list.get(i).getClusterID();
            clusterIDToOntModelID.put(cid, i);
        }
        for (int i = 0, n = sg.getRDFSentences().size(); i < n; i++) {
            RDFSentence sentence = sg.getRDFSentence(i);
            ArrayList<String> uris = sentence.getSubjectDomainVocabularyURIs();
            HashMap<Integer, Object> uniqueURIs = new HashMap<Integer, Object>();
            for (int j = 0, m = uris.size(); j < m; j++) {
                Integer clusterID = uriToClusterID.get(uris.get(j));
                if (clusterID != null) {
                    uniqueURIs.put(clusterID, null);
                }
            }
            if (uniqueURIs.size() == 1) {
                Integer cid = uniqueURIs.keySet().iterator().next();
                Integer mid = clusterIDToOntModelID.get(cid);
                OntModel block = models.get(mid);
                ArrayList<Statement> statements = sentence.getStatements();
                for (int j = 0, m = statements.size(); j < m; j++) {
                    block.add(statements.get(j));
                }
            }
        }
        int idx=-1; //samira add
        for (int i = 0, n = models.size(); i < n; i++) {
            idx++;
        	int cid = list.get(i).getClusterID();
            //String filepath = tempDir + ontName + "_block_" + cid + ".rdf";
            String filepath = tempDir + ontName + "_block_" + idx + ".owl";
            File file = new File(filepath);
            if (file.exists()) {
                file.delete();
            } 
            OntModel block = models.get(i);
            try {
                FileOutputStream fos = new FileOutputStream(filepath);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                block.write(bos, "RDF/XML-ABBREV");
                bos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            block.close();
        } //end for i
        
//        Coordinator.KNumCH = idx;
        //print clusters in the output as the txt file
        idx = -1;
        for (int i = 0, n = models.size(); i < n; i++) {
         	System.out.println(); System.out.println();
         	System.out.println("Partition  "+ i); 
         	idx++;
             String filepath2 = tempDir + ontName + "_block_" + idx + ".owl";
             try (BufferedReader br = new BufferedReader(new FileReader(filepath2))) {
             	   String line = null;
             	   while ((line = br.readLine()) != null) {
             		   System.out.println(line);
                	   }
             	} catch (FileNotFoundException e) {
        				e.printStackTrace();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}      
         }
        
    }//end step4
}
