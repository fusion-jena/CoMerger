
package fusion.comerger.algorithm.matcher.vdoc;
/* 
 * This package is downloaded from the FALCON-AO tool, 
 * available in http://ws.nju.edu.cn/falcon-ao/
 * For more information of this method, please refer to
 * Hu, W. and Qu, Y., 2008. Falcon-AO: A practical ontology matching system. Journal of web semantics, 6(3), pp.237-239.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
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
