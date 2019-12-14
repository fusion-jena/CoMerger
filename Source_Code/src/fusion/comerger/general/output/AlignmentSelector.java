
package fusion.comerger.general.output;

import fusion.comerger.general.gernalAnalysis.matrix.*;
import fusion.comerger.model.Node;

public class AlignmentSelector
{
    public static Alignment select(NamedMatrix temp, double threshold)
    {
        Alignment as = new Alignment();
        NamedMatrix matrix = new NamedMatrix(temp);
        int numRows = matrix.numRows(), numColumns = matrix.numColumns();
        if (numRows <= numColumns) {
            for (int i = 0; i < numRows; i++) {
                double max = 0;
                int row = -1, col = -1;
                for (int m = 0; m < numRows; m++) {
                    for (int n = 0; n < numColumns; n++) {
                        double value = matrix.get(m, n);
                        if (value > max) {
                            max = value;
                            row = m;
                            col = n;
                        }
                    }
                }
                if (max > threshold && row != -1 && col != -1) {
                    Node node1 = (Node) matrix.getRow(row);
                    Node node2 = (Node) matrix.getColumn(col);
                    as.addMapping(new Mapping(node1, node2, max));
                    for (int n = 0; n < numColumns; n++) {
                        matrix.set(row, n, 0);
                    }
                    for (int m = 0; m < numRows; m++) {
                        matrix.set(m, col, 0);
                    }
                } else {
                    break;
                }
            }
        } else {
            for (int j = 0; j < numColumns; j++) {
                double max = 0;
                int row = -1, col = -1;
                for (int m = 0; m < numColumns; m++) {
                    for (int n = 0; n < numRows; n++) {
                        double value = matrix.get(n, m);
                        if (value > max) {
                            max = value;
                            row = n;
                            col = m;
                        }
                    }
                }
                if (max > threshold && row != -1 && col != -1) {
                    Node node1 = (Node) matrix.getRow(row);
                    Node node2 = (Node) matrix.getColumn(col);
                    as.addMapping(new Mapping(node1, node2, max));
                    for (int m = 0; m < numColumns; m++) {
                        matrix.set(row, m, 0);
                    }
                    for (int n = 0; n < numRows; n++) {
                        matrix.set(n, col, 0);
                    }
                } else {
                    break;
                }
            }
        }
        return as;
    }
}
