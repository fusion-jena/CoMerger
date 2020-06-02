
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

public class Neighbor
{
    private String uri = "";
    private HashMap<String, TokenNode> subjects = new HashMap<String, TokenNode>();
    private HashMap<String, TokenNode> predicates = new HashMap<String, TokenNode>();
    private HashMap<String, TokenNode> objects = new HashMap<String, TokenNode>();

    public Neighbor(String u)
    {
        uri = u;
    }

    public void setUri(String u)
    {
        uri = u;
    }

    public void addSubject(TokenNode node)
    {
        subjects.put(node.getURI(), node);
    }

    public void addPredicate(TokenNode node)
    {
        predicates.put(node.getURI(), node);
    }

    public void addObject(TokenNode node)
    {
        objects.put(node.getURI(), node);
    }

    public String getURI()
    {
        return uri;
    }

    public HashMap<String, TokenNode> getSubjects()
    {
        return subjects;
    }

    public HashMap<String, TokenNode> getPredicates()
    {
        return predicates;
    }

    public HashMap<String, TokenNode> getObjects()
    {
        return objects;
    }
}
