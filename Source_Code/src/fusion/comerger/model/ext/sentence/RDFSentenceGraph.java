
package fusion.comerger.model.ext.sentence;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

import fusion.comerger.model.Constant;
import fusion.comerger.model.ext.sentence.filter.RDFSentenceFilter;
import fusion.comerger.model.ext.sentence.recognition.IndividualRecognition;

public class RDFSentenceGraph
{
    /* Whether process imports */
    public static boolean processImports = false;

    /* All the RDF sentences */
    private ArrayList<RDFSentence> RDFSentences = null;

    /* All the vocabularies */
    private ArrayList<Vocabulary> vocabularies = null;

    /* Hash the URIs to vocabularies */
    //private HashMap<String, Vocabulary> URIToVocabulary = null;
    private LinkedHashMap<String, Vocabulary> URIToVocabulary = null;//new samira

    /* The Jena OntModel */
    private OntModel ontModel = null;

    /* The ConceptRecognition */
    private IndividualRecognition conceptRecog = null;

    /* All the URIs of ontologies */
    private ArrayList<String> ontologyURIs = null;

    /* Internal use only */
    private LinkedHashMap<String, ArrayList<String>> sIDToOIDs = null; //new samira LinkedHashMap
    private LinkedHashMap<String, ArrayList<String>> oIDToIDs = null;
    private LinkedHashMap<String, ArrayList<Statement>> sID_oIDToStmts = null;

    public RDFSentenceGraph(String URL)
    {
        OntDocumentManager ontDocumentManager = new OntDocumentManager();
        ontDocumentManager.setProcessImports(RDFSentenceGraph.processImports);
        OntModelSpec ontModelSpec = new OntModelSpec(OntModelSpec.RDFS_MEM);
        ontModelSpec.setDocumentManager(ontDocumentManager);
        ontModel = ModelFactory.createOntologyModel(ontModelSpec);
        ontModel.read(URL);
    }

    public RDFSentenceGraph(OntModel model)
    {
        ontModel = model;
    }

    public RDFSentenceGraph(InputStream inputStream)
    {
        OntDocumentManager ontDocumentManager = new OntDocumentManager();
        ontDocumentManager.setProcessImports(RDFSentenceGraph.processImports);
        OntModelSpec ontModelSpec = new OntModelSpec(OntModelSpec.RDFS_MEM);
        ontModelSpec.setDocumentManager(ontDocumentManager);
        ontModel = ModelFactory.createOntologyModel(ontModelSpec);
        ontModel.read(inputStream, null);
    }

    public void setConceptRecognition(IndividualRecognition cr)
    {
        conceptRecog = cr;
    }

    public void build()
    {
        init(ontModel.listStatements());
        buildRDFSentences(ontModel.listStatements());
        // ontModel.close();
        // ontModel = null;
    }

    private void init(Iterator<?> statements)
    {
        RDFSentences = new ArrayList<RDFSentence>();
        vocabularies = new ArrayList<Vocabulary>();
        URIToVocabulary = new LinkedHashMap<String, Vocabulary>();
        ontologyURIs = new ArrayList<String>();

        sIDToOIDs = new LinkedHashMap<String, ArrayList<String>>();
        oIDToIDs = new LinkedHashMap<String, ArrayList<String>>();
        sID_oIDToStmts = new LinkedHashMap<String, ArrayList<Statement>>();

        ArrayList<Statement> rdfTypeStmts = new ArrayList<Statement>();
        while (statements.hasNext()) {
            Statement statement = (Statement) statements.next();
            RDFNode subject = statement.getSubject();
            RDFNode predicate = statement.getPredicate();
            RDFNode object = statement.getObject();
            String subjectURI = subject.toString();
            String predicateURI = predicate.toString();
            String objectURI = object.toString();

            if (!URIToVocabulary.containsKey(subjectURI)) {
                Vocabulary vocabulary = new Vocabulary(subjectURI, vocabularies.size());
                vocabularies.add(vocabulary);
                URIToVocabulary.put(subjectURI, vocabulary);
                if (subject.isURIResource()) {
                    if (Constant.isBuiltInNs(((Resource) subject.as(
                            Resource.class)).getNameSpace())) {
                        vocabulary.setType(Vocabulary.BuiltinVocabulary);
                    } else {
                        vocabulary.setType(Vocabulary.DomainVocabulary);
                    }
                } else {
                    vocabulary.setType(Vocabulary.AnonymousResource);
                }
            }
            if (!URIToVocabulary.containsKey(predicateURI)) {
                Vocabulary vocabulary = new Vocabulary(predicateURI, vocabularies.size());
                vocabularies.add(vocabulary);
                URIToVocabulary.put(predicateURI, vocabulary);
                if (Constant.isBuiltInNs(((Resource) predicate.as(
                        Resource.class)).getNameSpace())) {
                    vocabulary.setType(Vocabulary.BuiltinVocabulary);
                } else {
                    vocabulary.setType(Vocabulary.DomainVocabulary);
                }
            }
            if (!URIToVocabulary.containsKey(objectURI)) {
                Vocabulary vocabulary = new Vocabulary(objectURI, vocabularies.size());
                vocabularies.add(vocabulary);
                URIToVocabulary.put(objectURI, vocabulary);
                if (object.isURIResource()) {
                    if (Constant.isBuiltInNs(((Resource) object.as(
                            Resource.class)).getNameSpace())) {
                        vocabulary.setType(Vocabulary.BuiltinVocabulary);
                    } else {
                        vocabulary.setType(Vocabulary.DomainVocabulary);
                    }
                } else if (object.isAnon()) {
                    vocabulary.setType(Vocabulary.AnonymousResource);
                } else {
                    vocabulary.setType(Vocabulary.Literal);
                }
            }

            if (subject.isAnon() || object.isAnon()) {
                String sID = String.valueOf(URIToVocabulary.get(subjectURI).getID());
                String oID = String.valueOf(URIToVocabulary.get(objectURI).getID());
                String s_o = sID + "_" + oID;
                ArrayList<Statement> s_oStatements = sID_oIDToStmts.get(s_o);
                if (s_oStatements == null) {
                    s_oStatements = new ArrayList<Statement>(1);
                    sID_oIDToStmts.put(s_o, s_oStatements);
                }
                s_oStatements.add(statement);

                if (subject.isAnon()) {
                    ArrayList<String> oIDs = sIDToOIDs.get(sID);
                    if (oIDs == null) {
                        oIDs = new ArrayList<String>(1);
                        sIDToOIDs.put(sID, oIDs);
                    }
                    if (!oIDs.contains(oID)) {
                        oIDs.add(oID);
                    }
                }
                if (object.isAnon()) {
                    ArrayList<String> sIDs = oIDToIDs.get(oID);
                    if (sIDs == null) {
                        sIDs = new ArrayList<String>(1);
                        oIDToIDs.put(oID, sIDs);
                    }
                    if (!sIDs.contains(sID)) {
                        sIDs.add(sID);
                    }
                }
            }

            if (predicateURI.equals(Constant.RDF_NS + "type")) {
                String ns = ((Resource) object.as(Resource.class)).getNameSpace();
                if (conceptRecog != null) {
                    rdfTypeStmts.add(statement);
                } else if (ns != null && !Constant.isBuiltInNs(ns) || 
                		objectURI.equals(Constant.OWL_NS + "Thing")) {
                    URIToVocabulary.get(subjectURI).setIndividual();
                }
                if (objectURI.equals(Constant.OWL_NS + "Ontology")) {
                    ontologyURIs.add(subjectURI);
                }
            }
        }

        if (conceptRecog != null) {
            for (Iterator<String> conceptURIs = conceptRecog.getIndividualURIs(
                    rdfTypeStmts); conceptURIs.hasNext();) {
                URIToVocabulary.get(conceptURIs.next()).setIndividual();
            }
            rdfTypeStmts = null;
            conceptRecog = null;
        }
    }

    private void buildRDFSentences(Iterator<?> statements)
    {
        nextStatement:
        while (statements.hasNext()) {
            Statement statement = (Statement) statements.next();
            RDFNode subject = statement.getSubject();
            RDFNode object = statement.getObject();
            String subjectURI = subject.toString();

            if (object.isLiteral()) {
                Vocabulary vocabulary = URIToVocabulary.get(subjectURI);
                vocabulary.addLiteralStatement(statement);
            }
            if (subject.isURIResource() && !object.isAnon()) {
                RDFSentence sentence = new RDFSentence(RDFSentences.size());
                sentence.addStatement(statement);
                RDFSentences.add(sentence);
                continue nextStatement;
            }
        }

        while (sID_oIDToStmts.size() > 0) {
            RDFSentence sentence = new RDFSentence(RDFSentences.size());
            RDFSentences.add(sentence);
            ArrayList<String> bnodeIDs = new ArrayList<String>();
            String initSID_OID = sID_oIDToStmts.keySet().iterator().next();
            int _Index = initSID_OID.indexOf('_');
            String initSID = initSID_OID.substring(0, _Index);
            if (vocabularies.get(Integer.parseInt(initSID)).isAnonymous()) {
                bnodeIDs.add(initSID);
            }
            String initOID = initSID_OID.substring(_Index + 1);
            if (vocabularies.get(Integer.parseInt(initOID)).isAnonymous()) {
                bnodeIDs.add(initOID);
            }
            while (bnodeIDs.size() > 0) {
                String bnodeID = bnodeIDs.remove(0);
                ArrayList<String> os = sIDToOIDs.remove(bnodeID);
                if (os != null) {
                    for (Iterator<String> oIDs = os.iterator(); oIDs.hasNext();) {
                        String oID = oIDs.next();
                        if (!bnodeIDs.contains(oID) && (sIDToOIDs.containsKey(oID) || 
                        		oIDToIDs.containsKey(oID))) {
                            bnodeIDs.add(oID);
                        }
                        String s_o = bnodeID + "_" + oID;
                        ArrayList<Statement> statementsToBeAdded = sID_oIDToStmts.remove(s_o);
                        if (statementsToBeAdded != null) {
                            sentence.addStatements(statementsToBeAdded);
                        }
                    }
                }
                ArrayList<String> ss = oIDToIDs.remove(bnodeID);
                if (ss != null) {
                    for (Iterator<String> sIDs = ss.iterator(); sIDs.hasNext();) {
                        String sID = sIDs.next();
                        if (!bnodeIDs.contains(sID) && (sIDToOIDs.containsKey(sID) || 
                        		oIDToIDs.containsKey(sID))) {
                            bnodeIDs.add(sID);
                        }
                        String s_o = sID + "_" + bnodeID;
                        ArrayList<Statement> statementsToBeAdded = sID_oIDToStmts.remove(s_o);
                        if (statementsToBeAdded != null) {
                            sentence.addStatements(statementsToBeAdded);
                        }
                    }
                }
            }
        }
        sIDToOIDs = null;
        oIDToIDs = null;
        sID_oIDToStmts = null;
    }

    public void filter(RDFSentenceFilter RDFSentenceFilter)
    {
        RDFSentences = RDFSentenceFilter.filter(RDFSentences);
        for (int i = 0; i < RDFSentences.size(); i++) {
            RDFSentences.get(i).setID(i);
        }
    }

    public ArrayList<ArrayList<RDFSentence>> calculateRelatedRDFSentences()
    {
        ArrayList<ArrayList<RDFSentence>> relatedRDFSentences = 
        	new ArrayList<ArrayList<RDFSentence>>(vocabularies.size());
        for (int i = 0; i < vocabularies.size(); i++) {
            relatedRDFSentences.add(new ArrayList<RDFSentence>(0));
        }
        for (Iterator<RDFSentence> sentences = RDFSentences.iterator(); sentences.hasNext();) {
            RDFSentence sentence = sentences.next();
            for (Iterator<String> uris = 
            		sentence.getDomainVocabularyURIs().iterator(); uris.hasNext();) {
                String uri = uris.next();
                int id = URIToVocabulary.get(uri).getID();
                relatedRDFSentences.get(id).add(sentence);
            }
        }
        return relatedRDFSentences;
    }

    public ArrayList<LinkedHashMap<String, ArrayList<ArrayList<Statement>>>> calculatePaths()
    {
        ArrayList<LinkedHashMap<String, ArrayList<ArrayList<Statement>>>> result = 
        		new ArrayList<LinkedHashMap<String, ArrayList<ArrayList<Statement>>>>(
        				vocabularies.size());
        for (int i = 0; i < vocabularies.size(); i++) {
            result.add(new LinkedHashMap<String, ArrayList<ArrayList<Statement>>>(1));
        }
        for (Iterator<RDFSentence> sentences = RDFSentences.iterator(); sentences.hasNext();) {
            RDFSentence sentence = sentences.next();
            for (Iterator<String> subjectURIs = sentence.getSubjectDomainVocabularyURIs().
            		iterator(); subjectURIs.hasNext();) {
                String subjectURI = subjectURIs.next();
                int subjectID = URIToVocabulary.get(subjectURI).getID();
                for (Iterator<String> objectURIs = sentence.getObjectDomainVocabularyURIs().
                		iterator(); objectURIs.hasNext();) {
                    String objectURI = objectURIs.next();
                    ArrayList<ArrayList<Statement>> newPaths = sentence.getPaths(
                    		subjectURI, objectURI);
                    LinkedHashMap<String, ArrayList<ArrayList<Statement>>> objectURIToPaths = 
                    		result.get(subjectID);
                    ArrayList<ArrayList<Statement>> paths = objectURIToPaths.get(objectURI);
                    if (paths == null) {
                        objectURIToPaths.put(objectURI, newPaths);
                    } else {
                        for (int i = 0; i < newPaths.size(); i++) {
                            paths.add(newPaths.get(i));
                        }
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<RDFSentence> getRDFSentences()
    {
        return RDFSentences;
    }

    public int getNumberOfRDFSentences()
    {
        return RDFSentences.size();
    }

    public ArrayList<Vocabulary> getVocabularies()
    {
        return vocabularies;
    }

    public int getNumberOfVocabularies()
    {
        return vocabularies.size();
    }

    public RDFSentence getRDFSentence(int ID)
    {
        return RDFSentences.get(ID);
    }

    public Vocabulary getVocabulary(int ID)
    {
        return vocabularies.get(ID);
    }

    public Vocabulary getVocabulary(String vocabularyURI)
    {
        return URIToVocabulary.get(vocabularyURI);
    }

    public int getVocabularyID(String vocabularyURI)
    {
        return URIToVocabulary.get(vocabularyURI).getID();
    }

    public ArrayList<String> getOntologyURIs()
    {
        return ontologyURIs;
    }

    public void printRDFSentences()
    {
        for (int i = 0; i < RDFSentences.size(); i++) {
            RDFSentence sentence = RDFSentences.get(i);
            System.out.println(i);
            System.out.println(sentence.toString());
        }
    }

    public void printRDFSentencesToFile(String fileURL)
    {
        PrintWriter sentencesWriter = null;
        try {
            sentencesWriter = new PrintWriter(fileURL);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        sentencesWriter.println("size#" + String.valueOf(RDFSentences.size()));
        for (int i = 0; i < RDFSentences.size(); i++) {
            RDFSentence sentence = RDFSentences.get(i);
            sentencesWriter.println(sentence.toString());
            sentencesWriter.println("------");
        }
        sentencesWriter.close();
    }

    public static ArrayList<String> getRDFSentencesFromFile(String fileURL)
    {
        ArrayList<String> sentences = null;
        try {
            BufferedReader sentencesFile = new BufferedReader(new FileReader(fileURL));
            String line = sentencesFile.readLine();
            int size = Integer.parseInt(line.substring(line.indexOf('#') + 1));
            sentences = new ArrayList<String>(size);
            line = sentencesFile.readLine();
            StringBuffer buffer = new StringBuffer();
            while (line != null) {
                if (line.equals("")) {
                } else if (line.equals("------")) {
                    sentences.add(buffer.toString());
                    buffer = new StringBuffer();
                } else {
                    buffer.append(line + "\n");
                }
                line = sentencesFile.readLine();
            }
            sentencesFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return sentences;
    }
}
