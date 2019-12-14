
package fusion.comerger.algorithm.matcher.vdoc;

import java.util.HashMap;
import java.util.Iterator;

public class NeighborSet
{
    private HashMap<String, Neighbor> neighbors = new HashMap<String, Neighbor>();

    public void addNeighbor(Neighbor neighbor)
    {
        neighbors.put(neighbor.getURI(), neighbor);
    }

    public Neighbor getNeighbor(String uri)
    {
        return neighbors.get(uri);
    }

    public HashMap<String, Neighbor> getNeighbors()
    {
        return neighbors;
    }

    public int size()
    {
        return neighbors.size();
    }

    public void show()
    {
        Iterator<Neighbor> iter = neighbors.values().iterator();
        while (iter.hasNext()) {
            Neighbor neighbor = iter.next();
            System.out.println(neighbor.getURI());
            Iterator<TokenNode> i1 = neighbor.getSubjects().values().iterator();
            while (i1.hasNext()) {
                TokenNode node = i1.next();
                System.out.println("->[s]->" + node.getURI());
            }
            Iterator<TokenNode> i2 = neighbor.getPredicates().values().iterator();
            while (i2.hasNext()) {
                TokenNode node = i2.next();
                System.out.println("->[p]->" + node.getURI());
            }
            Iterator<TokenNode> i3 = neighbor.getObjects().values().iterator();
            while (i3.hasNext()) {
                TokenNode node = i3.next();
                System.out.println("->[o]->" + node.getURI());
            }
            System.out.println();
        }
    }
}
