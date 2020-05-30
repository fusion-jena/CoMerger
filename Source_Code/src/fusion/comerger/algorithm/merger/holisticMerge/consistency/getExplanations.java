package fusion.comerger.algorithm.merger.holisticMerge.consistency;
import java.io.File;
import java.util.Set;
import java.util.logging.Level;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.HermiT.Reasoner.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import com.clarkparsia.owlapi.explanation.BlackBoxExplanation;
import com.clarkparsia.owlapi.explanation.HSTExplanationGenerator;

import fusion.comerger.algorithm.merger.holisticMerge.MyLogging;

public class getExplanations {
	public static void main(String[] args) throws Exception {
	
		String test = "C:\\Users\\Samira\\Desktop\\HolisticDataSet\\d6\\mergedFiles_consistency\\d6v2.owl";
		getExplanations.run( test);
	
	}
    public static void run(String ontName) throws Exception {
    	// First, we create an OWLOntologyManager object. The manager will load and 
        // save ontologies. 
        OWLOntologyManager manager=OWLManager.createOWLOntologyManager();
        // We will create several things, so we save an instance of the data factory
        OWLDataFactory dataFactory=manager.getOWLDataFactory();
        // set the directory to search for ontologies
		// there is a bug in the original AutoIRIMapper
//		AutoIRIMapperFixed aim = new AutoIRIMapperFixed(new File("/home/emmeda/Research/impera/cn/etc/dlreasoning/ontologies"), true);
//		manager.addIRIMapper(aim);

		// create individuals ontology
//		String individualsIRIString = "http://carpenoctem.das-lab.net/research/ALICA/individuals.owl";
//		IRI individualIri = IRI.create(individualsIRIString);
		
        // We use the OWL API to load the ontology. 
//        OWLOntology ontology=manager.loadOntology(individualIri);
        //##################################################################
        File file = new File(ontName);
		OWLOntologyManager tempManager = OWLManager.createOWLOntologyManager();
        OWLOntologyLoaderConfiguration loadingConfig = new OWLOntologyLoaderConfiguration();
        loadingConfig = loadingConfig.setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
		OWLOntology ontology = tempManager.loadOntologyFromOntologyDocument(new FileDocumentSource(file),loadingConfig);

        //#######################################################################
        
        // Now we can start and create the reasoner. Since explanation is not natively supported by 
        // HermiT and is realised in the OWL API, we need to instantiate HermiT 
        // as an OWLReasoner. This is done via a ReasonerFactory object. 
        ReasonerFactory factory = new ReasonerFactory();
        // We don't want HermiT to thrown an exception for inconsistent ontologies because then we 
        // can't explain the inconsistency. This can be controlled via a configuration setting.  
        Configuration configuration=new Configuration();
        configuration.throwInconsistentOntologyException=false;
        // The factory can now be used to obtain an instance of HermiT as an OWLReasoner. 
        OWLReasoner reasoner=factory.createReasoner(ontology, configuration);
        // Now we instantiate the explanation classes
        BlackBoxExplanation exp=new BlackBoxExplanation(ontology, factory, reasoner);
        HSTExplanationGenerator multExplanator=new HSTExplanationGenerator(exp);
        // Now we can get explanations for the unsatisfiability. 
        Set<Set<OWLAxiom>> explanations;
        // Let us confirm that the ontology is inconsistent
        reasoner=factory.createReasoner(ontology, configuration);
        System.out.println("Is the changed ontology consistent? "+reasoner.isConsistent());
        // Ok, here we go. Let's see why the ontology is inconsistent. 
        System.out.println("Computing explanations for the inconsistency...");
        factory=new Reasoner.ReasonerFactory() {
            protected OWLReasoner createHermiTOWLReasoner(org.semanticweb.HermiT.Configuration configuration,OWLOntology ontology) {
                // don't throw an exception since otherwise we cannot compte explanations 
                configuration.throwInconsistentOntologyException=false;
                return new Reasoner(configuration,ontology);
            }
        };
        exp=new BlackBoxExplanation(ontology, factory, reasoner);
        multExplanator=new HSTExplanationGenerator(exp);
        // Now we can get explanations for the inconsistency 
        explanations=multExplanator.getExplanations(dataFactory.getOWLThing());
        // Let us print them. Each explanation is one possible set of axioms that cause the 
        // unsatisfiability. 
        for (Set<OWLAxiom> explanation : explanations) {
            System.out.println("------------------");
            System.out.println("Axioms causing the inconsistency: ");
            for (OWLAxiom causingAxiom : explanation) {
                System.out.println(causingAxiom);
            }
            System.out.println("------------------");
        }
    }
}

