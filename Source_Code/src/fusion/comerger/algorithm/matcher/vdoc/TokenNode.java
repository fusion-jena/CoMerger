
package fusion.comerger.algorithm.matcher.vdoc;

import java.util.HashMap;

import fusion.comerger.model.Node;

public class TokenNode
{
    private Node node = null;
    private HashMap<String, WordFrequency> localname = null;
    private HashMap<String, WordFrequency> label = null;
    private HashMap<String, WordFrequency> comment = null;

    public TokenNode(Node n)
    {
        node = n;
    }

    public TokenNode(Node n, HashMap<String, WordFrequency> name, 
    		HashMap<String, WordFrequency> lbl, HashMap<String, WordFrequency> cmt)
    {
        node = n;
        if (name != null) {
            localname = name;
        }
        if (lbl != null) {
            label = lbl;
        }
        if (cmt != null) {
            comment = cmt;
        }
    }

    public Node getNode()
    {
        return node;
    }

    public String getURI()
    {
        return node.toString();
    }

    public int getNodeCategory()
    {
        return node.getCategory();
    }

    public HashMap<String, WordFrequency> getLocalName()
    {
        return localname;
    }

    public HashMap<String, WordFrequency> getLabel()
    {
        return label;
    }

    public HashMap<String, WordFrequency> getComment()
    {
        return comment;
    }

    public void setLocalName(HashMap<String, WordFrequency> name)
    {
        if (name != null) {
            localname = name;
        }
    }

    public void setLabel(HashMap<String, WordFrequency> lbl)
    {
        if (lbl != null) {
            label = lbl;
        }
    }

    public void setComment(HashMap<String, WordFrequency> cmt)
    {
        if (cmt != null) {
            comment = cmt;
        }
    }
}
