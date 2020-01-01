package fusion.comerger.model;
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
import java.util.ArrayList;
import java.util.Iterator;


public class NodeList {

	 private ArrayList<Node> list = null;

	    public NodeList()
	    {
	        list = new ArrayList<Node>();
	    }

	    public NodeList(int capacity)
	    {
	        list = new ArrayList<Node>(capacity);
	    }

	    public int size()
	    {
	        return list.size();
	    }

	    public Node get(int i)
	    {
	        return list.get(i);
	    }

	    public void set(int index, Node node)
	    {
	        list.set(index, node);
	    }

	    public void add(Node node)
	    {
	        list.add(node);
	    }

	    public boolean contains(Node node)
	    {
	        return list.contains(node);
	    }

	    public int indexOf(Node node)
	    {
	        return list.indexOf(node);
	    }

	    public Node remove(int i)
	    {
	        return list.remove(i);
	    }

	    public boolean remove(Node node)
	    {
	        return list.remove(node);
	    }

	    public Iterator<Node> iterator()
	    {
	        return list.iterator();
	    }

	    public ArrayList<Node> getList()
	    {
	        return list;
	    }
}
