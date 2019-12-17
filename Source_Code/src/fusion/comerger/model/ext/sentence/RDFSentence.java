
package fusion.comerger.model.ext.sentence;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
*/

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

import fusion.comerger.model.Constant;

public class RDFSentence
{

    public static boolean displayLocalName = true;
    private int ID;
    private ArrayList<Statement> statements = null;

    public RDFSentence(int id)
    {
        ID = id;
        statements = new ArrayList<Statement>(1);
    }

    public void addStatement(Statement statement)
    {
        statements.add(statement);
    }

    public void addStatements(ArrayList<Statement> stat)
    {
        for (int i = 0; i < stat.size(); i++) {
            statements.add(stat.get(i));
        }
    }

    @Override
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < statements.size(); i++) {
            Statement statement = statements.get(i);
            RDFNode object = statement.getObject();
            String objectString = null;
            if (object.isLiteral()) {
                objectString = object.toString();
            }
            if (RDFSentence.displayLocalName) {
                String sln = statement.getSubject().getLocalName();
                String pln = statement.getPredicate().getLocalName();
                buffer.append(sln + "  " + pln + "  ");
                if (objectString == null) {
                    String oln = ((Resource) object.as(Resource.class)).getLocalName();
                    buffer.append(oln);
                } else {
                    buffer.append(objectString);
                }
                buffer.append("\n");
            } else {
                String sn = statement.getSubject().toString();
                String pn = statement.getPredicate().toString();
                buffer.append(sn + "  " + pn + "  ");
                if (objectString == null) {
                    buffer.append(statement.getObject().toString());
                } else {
                    buffer.append(objectString);
                }
                buffer.append("\n");
            }
        }
        return buffer.toString();
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int id)
    {
        ID = id;
    }

    public ArrayList<String> getAllURIs()
    {
        ArrayList<String> uris = new ArrayList<String>(3 * statements.size());
        for (Iterator<Statement> i = statements.iterator(); i.hasNext();) {
            Statement statement = i.next();
            Resource subject = statement.getSubject();
            String uri = subject.toString();
            if (!uris.contains(uri)) {
                uris.add(uri);
            }
            uri = statement.getPredicate().toString();
            if (!uris.contains(uri)) {
                uris.add(uri);
            }
            RDFNode object = statement.getObject();
            uri = object.toString();
            if (!uris.contains(uri)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList<String> getVocabularyURIs()
    {
        ArrayList<String> uris = new ArrayList<String>(3 * statements.size());
        for (Iterator<Statement> i = statements.iterator(); i.hasNext();) {
            Statement statement = i.next();
            Resource subject = statement.getSubject();
            String uri = subject.toString();
            if (subject.isURIResource() && !uris.contains(uri)) {
                uris.add(uri);
            }
            uri = statement.getPredicate().toString();
            if (!uris.contains(uri)) {
                uris.add(uri);
            }
            RDFNode object = statement.getObject();
            uri = object.toString();
            if (object.isURIResource() && !uris.contains(uri)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList<String> getSubjectVocabularyURIs()
    {
        ArrayList<String> uris = new ArrayList<String>(statements.size());
        for (Iterator<Statement> i = statements.iterator(); i.hasNext();) {
            Resource subject = i.next().getSubject();
            String uri = subject.toString();
            if (subject.isURIResource() && !uris.contains(uri)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList<String> getPredicateVocabularyURIs()
    {
        ArrayList<String> uris = new ArrayList<String>(statements.size());
        for (Iterator<Statement> i = statements.iterator(); i.hasNext();) {
            String uri = i.next().getPredicate().toString();
            if (!uris.contains(uri)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList<String> getObjectVocabularyURIs()
    {
        ArrayList<String> uris = new ArrayList<String>(statements.size());
        for (Iterator<Statement> i = statements.iterator(); i.hasNext();) {
            RDFNode object = i.next().getObject();
            String uri = object.toString();
            if (object.isURIResource() && !uris.contains(uri)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList<String> getDomainVocabularyURIs()
    {
        ArrayList<String> uris = new ArrayList<String>(2 * statements.size());
        for (Iterator<Statement> i = statements.iterator(); i.hasNext();) {
            Statement statement = i.next();
            Resource subject = statement.getSubject();
            String uri = subject.toString();
            String ns = subject.getNameSpace();
            if (subject.isURIResource() && !uris.contains(uri) && 
            		!Constant.isBuiltInNs(ns)) {
                uris.add(uri);
            }
            uri = statement.getPredicate().toString();
            ns = statement.getPredicate().getNameSpace();
            if (!uris.contains(uri) && !Constant.isBuiltInNs(ns)) {
                uris.add(uri);
            }
            RDFNode object = statement.getObject();
            uri = object.toString();
            if (object.isURIResource() && !uris.contains(uri) && 
            		!Constant.isBuiltInNs(((Resource) object.as(
            				Resource.class)).getNameSpace())) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList<String> getSubjectDomainVocabularyURIs()
    {
        ArrayList<String> uris = new ArrayList<String>(statements.size());
        for (Iterator<Statement> i = statements.iterator(); i.hasNext();) {
            Resource subject = i.next().getSubject();
            String uri = subject.toString();
            String ns = subject.getNameSpace();
            if (subject.isURIResource() && !uris.contains(uri) && 
            		!Constant.isBuiltInNs(ns)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList<String> getPredicateDomainVocabularyURIs()
    {
        ArrayList<String> uris = new ArrayList<String>(statements.size());
        for (Iterator<Statement> i = statements.iterator(); i.hasNext();) {
            Resource predicate = i.next().getPredicate();
            String uri = predicate.toString();
            String ns = predicate.getNameSpace();
            if (!uris.contains(uri) && !Constant.isBuiltInNs(ns)) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList<String> getObjectDomainVocabularyURIs()
    {
        ArrayList<String> uris = new ArrayList<String>(statements.size());
        for (Iterator<Statement> i = statements.iterator(); i.hasNext();) {
            RDFNode object = i.next().getObject();
            String uri = object.toString();
            if (object.isURIResource() && !uris.contains(uri) && 
            		!Constant.isBuiltInNs(((Resource) object.as(
                            Resource.class)).getNameSpace())) {
                uris.add(uri);
            }
        }
        return uris;
    }

    public ArrayList<String> getPredicatePlusObjectVocabularyURIs()
    {
        ArrayList<String> uris = new ArrayList<String>(statements.size());
        for (Iterator<Statement> i = statements.iterator(); i.hasNext();) {
            Statement statement = i.next();
            if (statement.getObject().isURIResource()) {
                String ps = statement.getPredicate().toString();
                String os = statement.getObject().toString();
                uris.add(ps + " " + os);
            }
        }
        return uris;
    }

    public ArrayList<ArrayList<Statement>> getPaths(String sourceURI, String targetURI)
    {
        ArrayList<ArrayList<Statement>> paths = new ArrayList<ArrayList<Statement>>(0);
        for (Iterator<Statement> i = statements.iterator(); i.hasNext();) {
            Statement statement = i.next();
            String subjectURI = statement.getSubject().toString();
            if (subjectURI.equals(sourceURI)) {
                RDFNode object = statement.getObject();
                if (object.toString().equals(targetURI)) {
                    ArrayList<Statement> path = new ArrayList<Statement>(1);
                    path.add(statement);
                    paths.add(path);
                } else if (object.isAnon()) {
                    for (Iterator<ArrayList<Statement>> j = getPaths(
                            object.toString(), targetURI).iterator(); j.hasNext();) {
                        ArrayList<Statement> nextPath = j.next();
                        ArrayList<Statement> path = new ArrayList<Statement>(
                        		nextPath.size() + 1);
                        path.add(statement);
                        for (int k = 0; k < nextPath.size(); k++) {
                            path.add(nextPath.get(k));
                        }
                        paths.add(path);
                    }
                }
            }
        }
        return paths;
    }

    public ArrayList<Statement> getStatements()
    {
        return new ArrayList<Statement>(statements);
    }
}
