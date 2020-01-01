package fusion.comerger.algorithm.partitioner.hierClust;
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
import org.semanticweb.owlapi.model.OWLClass;

//import opt.general.model.impl.NodeImpl;



public class Link
{
    private OWLClass node1 = null,  node2 = null;
    private double similarity = 0;

    public Link(OWLClass ver1, OWLClass ver2, double similarity)
    {
        this.node1 = ver1;
        this.node2 = ver2;
        this.similarity = similarity;
    }

    public OWLClass getVertex1()
    {
        return node1;
    }

    public OWLClass getVertex2()
    {
        return node2;
    }

    public double getSimilarity()
    {
        return similarity;
    }

    public void setSimilarity(double similarity)
    {
        this.similarity = similarity;
    }
}
