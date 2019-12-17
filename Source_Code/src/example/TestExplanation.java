package example;
/*
 * CoMerger: Holistic Ontology Merging
 * %%
 * Copyright (C) 2019 Heinz Nixdorf Chair for Distributed Information Systems, Friedrich Schiller University Jena
 * %%
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
 
 /**
 * Author: Samira Babalou<br>
 * email: samira[dot]babalou[at]uni[dash][dot]jena[dot]de
 * Heinz-Nixdorf Chair for Distributed Information Systems<br>
 * Institute for Computer Science, Friedrich Schiller University Jena, Germany<br>
 * Date: 17/12/2019
 */

import java.util.Set;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owl.explanation.api.Explanation;
import org.semanticweb.owl.explanation.api.ExplanationGeneratorFactory;
import org.semanticweb.owl.explanation.api.ExplanationManager;
import org.semanticweb.owl.explanation.impl.blackbox.checker.InconsistentOntologyExplanationGeneratorFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owl.explanation.api.ExplanationGenerator;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

public class TestExplanation {

    public static void main(String[] args) throws Exception {
    OWLOntologyManager m=OWLManager.createOWLOntologyManager();

   OWLOntology o=m.loadOntologyFromOntologyDocument(IRI.create("http://www.cs.ox.ac.uk/isg/ontologies/UID/00793.owl"));

      // Reasoner hermit=new Reasoner(o);
        OWLReasoner owlreasoner=new Reasoner.ReasonerFactory().createReasoner(o);
        System.out.println(owlreasoner.isConsistent());

      //System.out.println(hermit.isConsistent());

        //---------------------------- Copied from example--------- 
        OWLDataFactory df = m.getOWLDataFactory();
        OWLClass testClass = df.getOWLClass(IRI.create("urn:test#testclass"));
        m.addAxiom(o, df.getOWLSubClassOfAxiom(testClass, df.getOWLNothing()));
        OWLNamedIndividual individual = df.getOWLNamedIndividual(IRI
                .create("urn:test#testindividual"));
        m.addAxiom(o, df.getOWLClassAssertionAxiom(testClass, individual));

        //----------------------------------------------------------

        Node<OWLClass> unsatisfiableClasses = owlreasoner.getUnsatisfiableClasses();
        //Node<OWLClass> unsatisfiableClasses = hermit.getUnsatisfiableClasses();
        for (OWLClass owlClass : unsatisfiableClasses) {
            System.out.println(owlClass.getIRI());
        }
        //-----------------------------
        ExplanationGeneratorFactory<OWLAxiom> genFac = ExplanationManager.createExplanationGeneratorFactory((OWLReasonerFactory) owlreasoner);
        ExplanationGenerator<OWLAxiom> gen = genFac.createExplanationGenerator(o);

        //-------------------------

        InconsistentOntologyExplanationGeneratorFactory igf = new InconsistentOntologyExplanationGeneratorFactory((OWLReasonerFactory) owlreasoner, 10000);
        //InconsistentOntologyExplanationGeneratorFactory igf = new InconsistentOntologyExplanationGeneratorFactory((OWLReasonerFactory) hermit, 10000);
        ExplanationGenerator<OWLAxiom> generator = igf.createExplanationGenerator(o);

        OWLAxiom entailment = df.getOWLClassAssertionAxiom(df.getOWLNothing(),
                individual);

        //-------------
        Set<Explanation<OWLAxiom>> expl = gen.getExplanations(entailment, 5);
        //------------

        System.out.println("Explanation "
                + generator.getExplanations(entailment, 5));
    }
}
//https://stackoverflow.com/questions/26545241/owlexplanation-with-hermit-reasoner