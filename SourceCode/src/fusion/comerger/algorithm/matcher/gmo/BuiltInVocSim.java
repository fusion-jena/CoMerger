
package fusion.comerger.algorithm.matcher.gmo;
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
import fusion.comerger.model.Constant;
import fusion.comerger.model.Node;

public class BuiltInVocSim
{
    public static final double[][] classMatrix = {
        {1.0, 0.2, 0.2, 0.2, 0.3, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {1.0, 1.0, 0.2, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {1.0, 0.2, 1.0, 0.3, 0.1, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {1.0, 0.0, 0.1, 1.0, 0.1, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {0.6, 0.0, 0.1, 0.1, 1.0, 0.4, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {0.0, 0.0, 0.0, 0.0, 0.4, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {0.7, 0.2, 0.2, 0.2, 0.3, 0.3, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0}, // rdf:Property
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0},
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0},
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0},
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0},
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0}
    };
    
    public static final double[][] propertyMatrix = {
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
    };
    
    public static String owlObjectProperty = Constant.OWL_NS + "ObjectProperty";
    public static String owlTransitiveProperty = Constant.OWL_NS + "TransitiveProperty";
    public static String owlSymmetricProperty = Constant.OWL_NS + "SymmetricProperty";
    public static String owlInverseFunctionalProperty = Constant.OWL_NS + "InverseFunctionalProperty";
    public static String owlFunctionalProperty = Constant.OWL_NS + "FunctionalProperty";
    public static String owlDatatypeProperty = Constant.OWL_NS + "DatatypeProperty";
    public static String rdfsProperty = Constant.RDFS_NS + "Property";
    public static String rdfsResource = Constant.RDFS_NS + "Resource";
    public static String rdfsClass = Constant.RDFS_NS + "Class";
    public static String owlClass = Constant.OWL_NS + "Class";
    public static String owlThing = Constant.OWL_NS + "Thing";
    public static String owlNothing = Constant.OWL_NS + "Nothing";
    public static String ClassNames[] = {owlObjectProperty,
        owlTransitiveProperty, owlSymmetricProperty,
        owlInverseFunctionalProperty, owlFunctionalProperty,
        owlDatatypeProperty, rdfsProperty, rdfsResource, rdfsClass,
        owlClass, owlThing, owlNothing
    };
    
    public static String owlSomeValuesFrom = Constant.OWL_NS + "someValuesFrom";
    public static String owlAllValuesFrom = Constant.OWL_NS + "allValuesFrom";
    public static String owlCardinality = Constant.OWL_NS + "cardinality";
    public static String owlMinCardinality = Constant.OWL_NS + "minCardinality";
    public static String owlMaxCardinality = Constant.OWL_NS + "maxCardinality";
    public static String owlHasValue = Constant.OWL_NS + "hasValue";
    public static String owlOnProperty = Constant.OWL_NS + "onProperty";
    public static String owlComplementOf = Constant.OWL_NS + "complementOf";
    public static String rdfsDomain = Constant.RDFS_NS + "domain";
    public static String rdfsRange = Constant.RDFS_NS + "range";
    public static String rdfsSubPropertyOf = Constant.RDFS_NS + "subPropertyOf";
    public static String rdfsSubClassOf = Constant.RDFS_NS + "subClassOf";
    public static String rdfType = Constant.RDF_NS + "type";
    public static String PropertyNames[] = {owlSomeValuesFrom,
        owlAllValuesFrom, owlCardinality, owlMinCardinality,
        owlMaxCardinality, owlHasValue, owlOnProperty, owlComplementOf,
        rdfsDomain, rdfsRange, rdfsSubPropertyOf, rdfsSubClassOf, rdfType
    };

    public static double getSimilarity(Node left, Node right)
    {
        return BuiltInVocSim.getSimilarity(left.toString(), right.toString());
    }

    public static double getSimilarity(String uri1, String uri2)
    {
        if (uri1.equals(uri2)) {
            if (uri1.equals(Constant.RDF_NS + "iType")) {
                return 0.1;
            }
            if (uri1.equals(Constant.RDFS_NS + "subClassOf")) {
                return 0.2;
            }
            return 1;
        }
        int index1 = -1, index2 = -1;
        for (int i = 0; i < PropertyNames.length; i++) {
            if (PropertyNames[i].equals(uri1)) {
                index1 = i;
            }
            if (PropertyNames[i].equals(uri2)) {
                index2 = i;
            }
        }
        if (index1 >= 0 && index2 >= 0) {
            return propertyMatrix[index1][index2];
        }
        index1 = -1;
        index2 = -1;
        for (int i = 0; i < ClassNames.length; i++) {
            if (ClassNames[i].equals(uri1)) {
                index1 = i;
            }
            if (ClassNames[i].equals(uri2)) {
                index2 = i;
            }
        }
        if (index1 >= 0 && index2 >= 0) {
            return classMatrix[index1][index2];
        }
        return 0;
    }
}
