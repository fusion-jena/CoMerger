package fusion.comerger.general.graphToOnto;
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
import java.io.FileWriter;
import agg.xt_basis.GraGra;


import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;
public class Test 
{	
	public static void main(String[] args) throws Exception
	{		   
		//*********** Load the graph which corresponds to the ontology
		
		//System.out.println( "Enter the path of your graph");	
		//Scanner sc  = new Scanner(System.in);
		String fileName="ontologyOnGGX.ggx";
		//fileName= sc.nextLine();	
		
		GraGra gragra;	
		gragra =  new GraGra(true);		
		gragra.setFileName("fileName");		
		gragra.load(fileName);
		gragra.setName("GRammar");
					
		if (gragra == null) 			
			System.out.println("Grammar:  " + fileName + "   inexistant! ");		
				
		// create the owl file 		
		String fileOGName =fileName.substring(0,fileName.indexOf("."))+".owl";
		
		new File(fileOGName); 	
				
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);		 	
	
		/**** Transformation *****/ 
     	//start the process of transformation  
     	long begin = System.currentTimeMillis();
     	
     	//new GraphToOntology(model, gragra);
     	
     	long end = System.currentTimeMillis();
     	float time = ((float) (end-begin)) / 1000f;
     	System.out.println( "\nExecution time of transformation is : "+ time);
     	
     	// save the result of the transformation 
 		FileWriter fw = new FileWriter(fileOGName);
 		System.out.println("The OWL ontology is saved in " + fileOGName);
 		
 		model.write( fw, "RDF/XML-ABBREV"); 
	}
}
