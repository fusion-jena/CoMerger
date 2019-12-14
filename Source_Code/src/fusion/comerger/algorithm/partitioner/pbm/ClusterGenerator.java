
package fusion.comerger.algorithm.partitioner.pbm;

import java.util.HashMap;
import java.util.Iterator;

import fusion.comerger.model.Node;
import fusion.comerger.model.NodeList;


public class ClusterGenerator
{
    private HashMap<Integer, pbmCluster> clusters = null;

    public HashMap<Integer, pbmCluster> getClusters()
    {
        return clusters;
    }

    public void initClusters(NodeList entities, HashMap<String, Link> links)
    {
        clusters = new HashMap<Integer, pbmCluster>(entities.size());
        HashMap<String, Integer> uriToClusterID = new HashMap<String, Integer>();
        for (int cid = 0, n = entities.size(); cid < n; cid++) {
            Node entity = entities.get(cid);
            pbmCluster cluster = new pbmCluster(cid);
            cluster.setCohesion(entity.getHierarchy());
            cluster.putElement(entity.toString(), entity);
            clusters.put(cid, cluster);
            uriToClusterID.put(entity.toString(), cid);
        }
        for (Iterator<Link> i = links.values().iterator(); i.hasNext();) {
            Link link = i.next();
            String uri1 = link.getURI1(), uri2 = link.getURI2();
            double similarity = link.getSimilarity();
            int cid1 = uriToClusterID.get(uri1);
            int cid2 = uriToClusterID.get(uri2);
            pbmCluster cluster1 = clusters.get(cid1);
            pbmCluster cluster2 = clusters.get(cid2);
            cluster1.putCoupling(cid2, similarity);
            cluster2.putCoupling(cid1, similarity);
        }
    }

    private double goodness(double value, int size1, int size2)
    {
        if (size1 * size2 == 1) {
            return value;
        }
        return value / Math.log(size1 * size2);
    }

    private int getBestCluster()
    {
        int cid = -1;
        double maxGoodness = 0;
        for (Iterator<pbmCluster> i = clusters.values().iterator(); i.hasNext();) {
        	pbmCluster cluster = i.next();
            int elementSize = cluster.getElements().size();
            double cohesion = cluster.getCohesion();
            double goodness = goodness(cohesion, elementSize, elementSize);
            if (maxGoodness < goodness) {
                maxGoodness = goodness;
                cid = cluster.getClusterID();
            }
        }
        return cid;
    }

    private int getRelatedCluster(int cid1)
    {
        int cid2 = -1;
        double maxGoodness = 0;
        pbmCluster cluster = clusters.get(cid1);
        int elementsSize1 = cluster.getElements().size();
        HashMap<Integer, Double> temp = cluster.getCouplings();
        for (Iterator<Integer> i = temp.keySet().iterator(); i.hasNext();) {
            Integer tempCid = i.next();
            pbmCluster tempCluster = clusters.get(tempCid);
            int elementsSize2 = tempCluster.getElements().size();
            double coupling = temp.get(tempCid);
            double goodness = goodness(coupling, elementsSize1, elementsSize2);
            if (maxGoodness < goodness) {
                maxGoodness = goodness;
                cid2 = tempCid.intValue();
            }
        }
        return cid2;
    }

    public void executePartitioning(int maxSize)
    {
        HashMap<Integer, pbmCluster> removals = new HashMap<Integer, pbmCluster>();
        exit:
        while (clusters.size() > 0) {
            int cid1 = getBestCluster();
            if (cid1 == -1) {
                break exit;
            }
            int cid2 = getRelatedCluster(cid1);
            while (cid2 == -1) {
            	pbmCluster cluster = clusters.get(cid1);
                removals.put(cid1, cluster);
                clusters.remove(cid1);
                for (Iterator<pbmCluster> i = clusters.values().iterator(); i.hasNext();) {
                	pbmCluster tempCluster = i.next();
                    tempCluster.getCouplings().remove(cid1);
                }
                cid1 = getBestCluster();
                if (cid1 == -1) {
                    break exit;
                }
                cid2 = getRelatedCluster(cid1);
            }
            pbmCluster cluster1 = clusters.get(cid1);
            pbmCluster cluster2 = clusters.get(cid2);
            for (Iterator<Node> i = cluster2.listElements(); i.hasNext();) {
                Node entity = i.next();
                cluster1.putElement(entity.toString(), entity);
            }
            for (Iterator<Integer> i = cluster2.getCouplings().keySet().iterator(); i.hasNext();) {
                Integer cid = i.next();
                if (cid.intValue() != cluster1.getClusterID()) {
                    Double tempCoupling = cluster2.getCouplings().get(cid);
                    if (cluster1.getCouplings().containsKey(cid)) {
                        double oc = cluster1.getCouplings().get(cid);
                        double nc = oc + tempCoupling;
                        cluster1.getCouplings().put(cid, new Double(nc));
                    } else {
                        cluster1.getCouplings().put(cid, tempCoupling);
                    }
                }
            }
            double coupling = cluster1.getCouplings().get(cid2);
            double cohesion = cluster1.getCohesion() + cluster2.getCohesion() + coupling;
            cluster1.setCohesion(cohesion);
            cluster1.getCouplings().remove(cid2);
            clusters.remove(cid2);
            for (Iterator<pbmCluster> i = clusters.values().iterator(); i.hasNext();) {
            	pbmCluster cluster = i.next();
                if (cluster.getClusterID() != cid1 && cluster.getClusterID() != cid2) {
                    HashMap<Integer, Double> tempTable = cluster.getCouplings();
                    double coupling1 = 0, coupling2 = 0;
                    Double temp1 = tempTable.get(cid1);
                    if (temp1 != null) {
                        coupling1 = temp1.doubleValue();
                    }
                    Double temp2 = tempTable.get(cid2);
                    if (temp2 != null) {
                        coupling2 = temp2.doubleValue();
                        tempTable.remove(cid2);
                    }
                    if (coupling1 + coupling2 != 0) {
                        tempTable.put(cid1, coupling1 + coupling2);
                    }
                }
            }
            if (cluster1.getElements().size() > maxSize / 2) {
            	pbmCluster cluster = clusters.get(cid1);
                removals.put(cid1, cluster);
                clusters.remove(cid1);
                for (Iterator<pbmCluster> i = clusters.values().iterator(); i.hasNext();) {
                	pbmCluster tempCluster = i.next();
                    tempCluster.getCouplings().remove(cid1);
                }
            }
        }
        if (removals.size() > 0) {
            for (Iterator<pbmCluster> i = removals.values().iterator(); i.hasNext();) {
            	pbmCluster cluster = i.next();
                clusters.put(cluster.getClusterID(), cluster);
            }
        }
        for (Iterator<pbmCluster> i = clusters.values().iterator(); i.hasNext();) {
        	pbmCluster cluster = i.next();
            int size = cluster.getElements().size();
            if (size == 1) {
                i.remove();
            }
        }
    }
}
