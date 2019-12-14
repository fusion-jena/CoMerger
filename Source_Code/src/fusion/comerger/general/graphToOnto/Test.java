package fusion.comerger.general.graphToOnto;

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
