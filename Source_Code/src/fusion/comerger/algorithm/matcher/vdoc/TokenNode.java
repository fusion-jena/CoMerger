
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
