
package fusion.comerger.general.analysis;
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
import java.io.File;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import fusion.comerger.general.gui.OPATgui;

public class ConsistencyAnalyzing 
{
	
    public static String ConsistencyTest1(String filename) throws OWLOntologyCreationException 
    {   		
    	String res ="";
    	File inputfile = new File(filename);
    	OWLOntologyManager manager = OWLManager.createOWLOntologyManager(); ;
    	OWLDataFactory dataFactory = manager.getOWLDataFactory();
    	OWLOntology yourOntology = manager.loadOntologyFromOntologyDocument(inputfile);
    	IRI ontologyIRI = yourOntology.getOntologyID().getOntologyIRI();//.get();  
        
    	
    	OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
    	OWLReasoner reasoner =     (OWLReasoner) reasonerFactory.createNonBufferingReasoner(yourOntology);
//    	 res = res + "============  The consistency checker ============ \n";
    	    if(reasoner.isConsistent()){
//    	    	res = res +"\n"+ontologyIRI+ "\t PASSED the reasoning test using:\t"+reasoner.getReasonerName()+"\n";
    	    	res =  "PASSED with "+reasoner.getReasonerName();
    	    }else{
    	    	res = filename +"\t FAILED the consistency test" ;
    	    }
    	    return res;
    }
    
 //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void ConsistencyTest2() throws OWLOntologyCreationException 
    {   		
    	File inputfile = new File(OPATgui.NameAddressOnt2);
    	OWLOntologyManager manager = OWLManager.createOWLOntologyManager(); ;
    	OWLDataFactory dataFactory = manager.getOWLDataFactory();
    	OWLOntology yourOntology = manager.loadOntologyFromOntologyDocument(inputfile);
    	IRI ontologyIRI = yourOntology.getOntologyID().getOntologyIRI();//.get();  
        
    	
    	OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
    	OWLReasoner reasoner =     (OWLReasoner) reasonerFactory.createNonBufferingReasoner(yourOntology);
    	 OPATgui.ConsistencyTextArea2.append("============  The consistency checker ============ \n");
    	    if(reasoner.isConsistent()){
    	        OPATgui.ConsistencyTextArea2.append("\n"+ontologyIRI+ "\t   PASSED the reasoning test using:\t"+reasoner.getReasonerName()+"\n");
    	    }else{
    	        OPATgui.ConsistencyTextArea2.append(OPATgui.NameAddressOnt2+"\t FAILED the consistency test");
    	    }    
    }
    
}
    