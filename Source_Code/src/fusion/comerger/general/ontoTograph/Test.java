package  fusion.comerger.general.ontoTograph;
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
import java.io.FileInputStream;
import java.io.InputStream;

import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.TypeGraph;


import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;
import org.apache.jena.ontology.OntDocumentManager;

public class Test 
{	
	public static void main(String[] args) throws Exception
	{
		 GraGra graphGrammar;
				  
		/*********** Load ontology  *************/	
		 
		String name="D:/result/cmt.owl";
		String fileName=new File(name).getName();
		int pos = fileName.lastIndexOf(".");
		if (pos > 0) {
		    fileName = fileName.substring(0, pos);
		}
		InputStream   in1 = new FileInputStream(new File( name));
		OntDocumentManager mgr = new OntDocumentManager();
    	OntModel model = ModelFactory.createOntologyModel();
 	    model.read(in1,null);	
 		model.prepare();
         if(model!=null)
        	 System.out.println("this is the model\t"+model.toString());
        /********* Load the meta-model **********/
        String fmtatOnto= "resources/metamodelOwl.ggx";  
     	graphGrammar =  new GraGra(true);		
     	graphGrammar.setFileName("/temp/"+name);		
     	graphGrammar.load(fmtatOnto);       
     	
     	if (graphGrammar == null) 			
     		System.out.println("Grammar:  " + fmtatOnto + "   inexistant! ");  
     	
     	/********* Start process  of transformation ********/
     	long begin = System.currentTimeMillis();
     	
     	Graph typeGrap=graphGrammar.getTypeGraph();
     	OntologyToGraph og=new OntologyToGraph(model,typeGrap,fileName);
     	  	
     	long end = System.currentTimeMillis();
     	float time = ((float) (end-begin)) / 1000f;
     	System.out.println("\nExecution time of transformation is : "+ time);	
     	
	}
}
