package fusion.comerger.algorithm.partitioner.hierClust;
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


import java.util.*;

import org.semanticweb.owlapi.model.OWLClass;

//import opt.general.model.impl.NodeImpl;
/**
 * Set of clusters.
 */
public class ClusterSet {

    private Set<Cluster> allClusters = new HashSet<Cluster>();

    public Cluster findClusterByElement(OWLClass e) {
        Cluster cluster = null;
        for(Cluster c : allClusters) {
            if( c.contains(e) ) {
                cluster = c;
                break;
            }
        }
        return cluster;
    }

    public List<Cluster> getAllClusters() {
        return new ArrayList<Cluster>(allClusters);
    }

    public boolean add(Cluster c) {
        return allClusters.add(c);
    }

    public boolean remove(Cluster c) {
        return allClusters.remove(c);
    }

    public int size() {
        return allClusters.size();
    }


    public ClusterSet copy(List<Cluster> list) {
        ClusterSet clusterSet = new ClusterSet();
        for( int i=0;i<list.size();i++) {
            Cluster c= list.get(i);
            Cluster clusterCopy = c.copy();
            clusterSet.add(clusterCopy);
        }
        return clusterSet;
    }
    
    public boolean found(Cluster c)
    {
    	if(allClusters.contains(c))
    		return true;
    	else
    		return false;
    }


}


