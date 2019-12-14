package fusion.comerger.algorithm.merger.holisticMerge.evaluator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.coode.xml.OWLOntologyXMLNamespaceManager;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import fusion.comerger.algorithm.merger.holisticMerge.localTest.StatisticTest;
import fusion.comerger.algorithm.merger.model.HModel;

public class HMergeEvaluation {
	public static String tick = "<img src=\"/layout/images/tick.jpg\">";
	public static String cross = "<img src=\"/layout/images/cross.jpg\">";

	/* ******************************************************************** */
	public static String[] CorrectnessOntologyURI_Namespace(HModel ontM) {
		String[] res = new String[4];
		// res [0] and res[1] are related to ONTOLOGY URI
		// res [2] and res[3] are related to ONTOLOGY NAMESPACE
		boolean existNS = false, existPre = false;
		int errorNS = 0, errorPre = 0;
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();

		OWLOntologyFormat format = m.getOntologyFormat(ontM.getOwlModel());

		OWLOntologyXMLNamespaceManager nsManager = new OWLOntologyXMLNamespaceManager(ontM.getOwlModel(), format);

		/* *** ontology URI **** */
		for (String prefix : nsManager.getPrefixes()) {
			if (prefix != null)
				existPre = true;
			if (prefix.contains("."))
				errorPre++;
		}
		if (existPre == false || errorPre > 0) {
			res[0] = cross;
			if (existPre == false)
				res[1] = "No ontology URI has been defined! <br>";
			if (errorPre > 0)
				res[1] = "Ontology URI does not define correctly! <br>";
			res[1] = "<span style=\"font-weight: bold; color: orange; width: 100%;\">" + res[1] + "</span>";
		} else {
			res[0] = tick;
			res[1] = "Ontology URI is defined correctly. <br>";
		}

		StatisticTest.result.put("Ontology_URI", res[0]);

		/* *** ontology namespace **** */
		for (String ns : nsManager.getNamespaces()) {
			if (ns != null)
				existNS = true;
			if (!ns.startsWith("http"))
				errorNS++;
			if (!ns.endsWith("#"))
				errorNS++;
		}
		if (existNS == false || errorNS > 0) {
			res[2] = cross;
			if (existNS == false)
				res[3] = "No ontology namespace has been defined! <br>";
			if (errorNS > 0)
				res[3] = "Ontology name space does not define correctly! <br>";
			res[3] = "<span style=\"font-weight: bold; color: orange; width: 100%;\">" + res[1] + "</span>";
		} else {
			res[2] = tick;
			res[3] = "Ontology name space is defined correctly. <br>";
		}

		StatisticTest.result.put("Ontology_Namespace", res[2]);
		return res;
	}

	public static String[] CorrectnessOntologyDeclaration(HModel ontM) {
		String[] res = new String[2];
		// No OWL Ontology Declaration: This means failing to declare the
		// owl:Ontology tag where the ontology metadata should be provided.
		// and check for availability
		// two criteria
		boolean ans = false;
		Set<OWLAxiom> ax = ontM.getOwlModel().getAxioms();
		if (ax.contains("owl:Ontology"))
			ans = true;
		if (ans == true) {
			res[0] = tick;
		} else {
			res[0] = cross;
		}

		if (ans == true) {
			res[1] = "ontology declartion is defined correctly.<br>";
		} else {
			res[1] = "<span style=\"font-weight: bold; color: orange; width: 100%;\"> Ontology declartion does not find!<br></span>";
		}

		StatisticTest.result.put("Ontology_Declaration", res[0]);
		return res;
	}

	public static String[] CorrectnessOntologyLicense(HModel ontM) {
		String[] res = new String[2];
		res[0] = "-";

		res[1] = "This metric evaluates the existing of the license and the compatibility of the license between the source ontologies' license";

		StatisticTest.result.put("Ontology_License", res[0]);
		return res;
	}

	public static String[] LabelUniqueness(HModel ontM) {
		String[] res = new String[2];
		Iterator<OWLClass> cl = ontM.getOwlModel().getClassesInSignature().iterator();
		Set<String> AllLabels = new HashSet<String>();
		int sum = 0;
		while (cl.hasNext()) {
			OWLClass c = cl.next();
			String s = String.valueOf(c.getIRI());
			if (AllLabels != null && AllLabels.contains(s)) {
				sum++;
				if (s.toString().startsWith("<")) {
					res[1] = res[1] + "  -> " + s.toString().substring(1, s.toString().length() - 1) + "<br>";
				} else {
					res[1] = res[1] + "  -> " + s.toString() + "<br>";
				}
			} else {
				AllLabels.add(s);
			}
		}
		if (sum > 0) {
			res[0] = cross;
			res[1] = "<span style=\"font-weight: bold; color: orange; width: 100%;\">" + res[1] + "<br></span>";
		} else {
			res[0] = tick;
			res[1] = "All labels are uniques.<br>";
		}
		StatisticTest.result.put("Label_Uniqueness", res[0]);
		return res;
	}

	public static String[] UnifyNaming(HModel ontM) {
		// All name are singular or plural
		// Prefix and suffix conventions
		// all are same: class: author, property: has_author
		// class: wine, subclass: red-wine

		// Calculate 4 criteria: (1) contains "-" and "_", (2) uppercase and
		// lowercase, (3)?
		String[] res = new String[2];
		res[1] = "";
		int underline = 0, line = 0, uppercase = 0, lowercase = 0;
		int sum = 0;
		Iterator<OWLClass> list = ontM.getOwlModel().getClassesInSignature().iterator();
		while (list.hasNext()) {
			OWLClass c = list.next();
			try {
				String className = c.getIRI().getRemainder().get();
				if (className.contains("_"))
					underline++;
				if (className.contains("-"))
					line++;

				if (Character.isUpperCase(className.charAt(0)))
					uppercase++;
				if (Character.isLowerCase(className.charAt(0)))
					lowercase++;
			} catch (IllegalStateException e) {
				// e.printStackTrace();
				System.out.println("Error during UnifyNaming evaluator");
			}
		}

		Iterator<OWLObjectProperty> listP = ontM.getOwlModel().getObjectPropertiesInSignature().iterator();
		while (listP.hasNext()) {
			OWLObjectProperty p = listP.next();
			String pName = p.getIRI().getRemainder().toString();
			if (pName.contains("_"))
				underline++;
			if (pName.contains("-"))
				line++;

			if (Character.isUpperCase(pName.charAt(0)))
				// TODO: it is not
				// correct, for
				// bioportal ontologies,
				// pname is
				// Optional.of(xxx)
				uppercase++;
			if (Character.isLowerCase(pName.charAt(0)))
				lowercase++;
		}

		if (underline != 0 && line != 0 && underline != line) {
			res[1] = "  ->The merged ontology contains both \'_' and '-' in the entities name. It includes " + underline
					+ " entities with \'_'  and " + line + " entities with '-' .<br>";
		} else {
			sum = sum + 1;
		}

		if (uppercase != 0 && lowercase != 0 && uppercase != lowercase) {
			res[1] = res[1]
					+ "  ->The entities name are started with both uppercase and lowercase in the merged ontology. It includes "
					+ uppercase + " entities with uppercase and " + lowercase + " entities with lowercase name.  <br>";
		} else {
			sum = sum + 1;
		}

		// Till now, two functions works, so it divided by two
		// sum = Math.round(((double) sum / 2) * 1000.0) / 1000.0;
		// res[0] = String.valueOf(sum);
		if (sum > 0) {
			if (sum == 1) {
				res[0] = sum + " case " + cross;
			} else {
				res[0] = sum + " cases " + cross;
			}
		} else {
			res[0] = tick;
		}

		if (sum > 0.0) {
			res[1] = "<span style=\"font-weight: bold; color: orange; width: 100%;\">" + res[1] + "<br></span>";
		} else {
			res[1] = "All naming criteria are followed in the merged ontology.<br>";
		}

		StatisticTest.result.put("Unify_Naming", res[0]);
		return res;
	}

	public static String[] EntityTypeDeclaration(HModel ontM) {
		// owl:Class, , owl:ObjectProperty or owl:DatatypeProperty
		String[] res = new String[2];
		res[1] = "";
		OWLOntology ont = ontM.getOwlModel();
		double decCount = 0.0;
		double Csize = (double) ont.getClassesInSignature().size();
		double Psize = (double) ont.getObjectPropertiesInSignature().size();
		Psize = Psize + (double) ont.getDataPropertiesInSignature().size();
		double Insize = (double) ont.getIndividualsInSignature().size();
		double Asize = (double) ont.getAnnotations().size();
		Asize = Asize + (double) ont.getAnnotationPropertiesInSignature().size();

		Set<OWLEntity> temp = new HashSet<OWLEntity>();
		Set<OWLDeclarationAxiom> AllAxDec = ont.getAxioms(AxiomType.DECLARATION);
		decCount = AllAxDec.size();

		if (decCount <= 0.0) {
			res[0] = cross;
			res[1] = "<span style=\"font-weight: bold; color: orange; width: 100%;\">No entity declration found for all entities! <br></span>";
		} else {
			res[0] = tick;
			res[1] = "Enitiies are declraed!<br>";
			// TODO: Correct it, we should find which entity does not have
			// declaration
		}

		StatisticTest.result.put("Entity_Type_Declaration", res[0]);
		return res;
	}
}
