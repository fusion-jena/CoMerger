
package fusion.comerger.general.output;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.ArrayList;

import fusion.comerger.model.Node;

public class Alignment
{
    private ArrayList<Mapping> collection = null;

    public Alignment()
    {
        collection = new ArrayList<Mapping>();
    }

    public void addMapping(Mapping map)
    {
        collection.add(map);
    }

    public Mapping getMapping(int index)
    {
        if (index >= 0 && index < size()) {
            return collection.get(index);
        } else {
            System.err.println("getMappingError: Index is out of bound.");
            return null;
        }
    }

    public double getSimilarity(Node left, Node right)
    {
        Mapping map = contains(left, right);
        if (map == null) {
            return 0;
        } else {
            return map.getSimilarity();
        }
    }

    public void setSimilarity(Node left, Node right, double sim)
    {
        Mapping map = contains(left, right);
        if (map == null) {
            System.err.println("setSimilarityError: Cannot find such mapping.");
        } else {
            map.setSimilarity(sim);
        }
    }

    public boolean removeMapping(int index)
    {
        if (index >= 0 && index < size()) {
            collection.remove(index);
            return true;
        } else {
            System.err.println("removeMappingError: Index is out of bound.");
            return false;
        }
    }

    public boolean removeMapping(Node left, Node right)
    {
        for (int i = 0, n = size(); i < n; i++) {
            Mapping map = collection.get(i);
            if (map.getEntity1().equals(left) && 
            		map.getEntity2().equals(right)) {
                collection.remove(i);
                return true;
            }
        }
        System.err.println("removeMappingError: Cannot find such mapping.");
        return false;
    }

    public Mapping contains(Node left, Node right)
    {
        for (int i = 0, n = size(); i < n; i++) {
            Mapping map = collection.get(i);
            if (map.getEntity1().equals(left) && 
            		map.getEntity2().equals(right)) {
                return map;
            }
        }
        return null;
    }

    public Alignment cut(double threshold)
    {
        for (int i = 0; i < size(); i++) {
            Mapping align = collection.get(i);
            if (align.getSimilarity() <= threshold) {
                removeMapping(i);
                i--;
            }
        }
        return this;
    }

    public int size()
    {
        return collection.size();
    }

    public int size(double threshold)
    {
        int count = 0;
        for (int i = 0, n = size(); i < n; i++) {
            Mapping map = collection.get(i);
            if (map.getSimilarity() > threshold) {
                count++;
            }
        }
        return count;
    }

    public void show()
    {
        for (int i = 0, n = size(); i < n; i++) {
            Mapping map = collection.get(i);
            System.out.println("entity1=" + map.getEntity1().toString());
            System.out.println("entity2=" + map.getEntity2().toString());
            System.out.println("similarity=" + map.getSimilarity());
            System.out.println("relation=" + map.getRelation() + "\n");
        }
    }
}
