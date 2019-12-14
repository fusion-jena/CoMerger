
package fusion.comerger.algorithm.matcher.gmo;

import fusion.comerger.model.Node;

public class DataLiteralSim
{
    public double getSimilarity(Node left, Node right)
    {
        return DataLiteralSim.getDataLiteralSim(left, right);
    }

    public static double getDataLiteralSim(Node left, Node right)
    {
        if (left.equals(right)) {
            return 1;
        }
        return 0;
    }
}
