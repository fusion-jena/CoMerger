
package fusion.comerger.algorithm.matcher.vdoc;

import java.util.HashMap;
import java.util.Iterator;

public class TokenNodeSet
{
    private HashMap<String, TokenNode> nodes = new HashMap<String, TokenNode>();

    public Iterator<TokenNode> iterator()
    {
        return nodes.values().iterator();
    }

    public void add(TokenNode node)
    {
        nodes.put(node.getURI(), node);
    }

    public TokenNode get(String uri)
    {
        return nodes.get(uri);
    }

    public boolean contains(String uri)
    {
        return nodes.containsKey(uri);
    }

    public int size()
    {
        return nodes.size();
    }

    public void show()
    {
        for (Iterator<TokenNode> iter = nodes.values().iterator(); iter.hasNext();) {
            TokenNode node = iter.next();
            System.out.println(node.getURI());
            HashMap<String, WordFrequency> t = node.getLocalName();
            if (t != null) {
                for (Iterator<WordFrequency> i = t.values().iterator(); i.hasNext();) {
                    WordFrequency wf = i.next();
                    System.out.println("->[localname]->" + wf.getWord() 
                            + ", " + wf.getFrequency());
                }
            }
            t = node.getLabel();
            if (t != null) {
                for (Iterator<WordFrequency> i = t.values().iterator(); i.hasNext();) {
                    WordFrequency wf = i.next();
                    System.out.println("->[label]->" + wf.getWord() 
                            + ", " + wf.getFrequency());
                }
            }
            t = node.getComment();
            if (t != null) {
                for (Iterator<WordFrequency> i = t.values().iterator(); i.hasNext();) {
                    WordFrequency wf = i.next();
                    System.out.println("->[comment]->" + wf.getWord() 
                            + ", " + wf.getFrequency());
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
