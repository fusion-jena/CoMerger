package fusion.comerger.algorithm.merger.holisticMerge.evaluator;
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
public class GuiOutput {

	public static String createCompletenessGui(String[] res) {
		String gui = "";
		gui += "<div id=\"idSecCompleteness\"><br> " + "<hh>Completeness	Aspect</hh> "
				+ "	<div class=\"showcell\">" + "	<span>Class preservation</span> <span style=\"float: right;\">"
				+ res[0] + "</span>" + "	</div>	<div class=\"hidecell\">	<p>"
				+ "			Each class in the (all/target) input ontologies should have a		mapped class in the merged ontology [1,3,6,7,10]. <br>"
				+ res[1]
				+ "	</p>	</div>	<div class=\"showcell\">		<span>Property preservation</span> <span style=\"float: right;\">"
				+ res[2] + "</span>	</div>	<div class=\"hidecell\">		"
				+ "<p>	Each property from the (all/target) input ontologies is	explicitly in or implied by the merged ontology [7,10]. <br>"
				+ res[3] + "</p></div> <div class=\"showcell\">"
				+ "	<span>Instance preservation</span> <span style=\"float: right;\">" + res[4]
				+ "</span></div>	<div class=\"hidecell\">	<p>	All instances of (all/target) input ontologies should be preserved in the merged ontology [7,10]. <br>"
				+ res[5] + "</p></div>	<div class=\"showcell\">"
				+ "<span>Correspondences preservation</span> <span	style=\"float: right;\">" + res[6] + "</span></div>"
				+ "	<div class=\"hidecell\">	<p>If two entities of the input ontologies are corresponding, both	should map to the same merged entity in the merged ontology[6,7,10]. "
				+ "Note that, corresponding can be as equality, similarity	or is-a correspondences. In each case, the same type of alignment should be preserved. <br>"
				+ res[7] + "</p>	</div>	<div class=\"showcell\">"
				+ "<span>Correspondences' property preservation</span> <span	style=\"float: right;\">" + res[8]
				+ "</span>	</div>	<div class=\"hidecell\">	<p>If any of the corresponding entities from the input ontologies "
				+ "has a certain property, the merged entity should also have this	property [6,7]. <br>" + res[9]
				+ "</p></div><div class=\"showcell\"><span>Value preservation</span> <span style=\"float: right;\">"
				+ res[10]
				+ "</span></div><div class=\"hidecell\"><p>Properties' values from the (all/target) input ontologies should	be preserved in the merged ontology [6,7]. In case of conflicts a resolution strategy is required. <br>"
				+ res[11]
				+ "</p>	</div>	<div class=\"showcell\"><span>Structure preservation</span> <span style=\"float: right;\">"
				+ res[12]
				+ "</span>	</div><div class=\"hidecell\">	<p>If two entities are connected via a certain property in an input"
				+ "	ontology, their mapped entities in the merged ontology should be connected via the respective mapped property [1], thus preserving	the input ontologies' structures in the merged ontology. <br>"
				+ res[13] + "</p></div></div>";
		return gui;
	}

	public static String createMinimalityGui(String[] res) {
		String gui = "";
		gui += "<div id=\"idSecMinimality\">" + "	<br> " + "<hh>Minimality	Aspect</hh>		"
				+ "		<div class=\"showcell\">" + "		<span>Class redundancy prohibition</span> <span"
				+ "				style=\"float: right;\">" + res[0] + "</span>		</div>"
				+ "		<div class=\"hidecell\">			<p>"
				+ "				A class from the (all/target) input ontologies should have at"
				+ "				most one mapping in the merged ontology [1,3,4,7,10]. <br>" + res[1]
				+ "</p>		</div>"
				+ "		<div class=\"showcell\">			<span>Property redundancy prohibition</span> <span"
				+ "				style=\"float: right;\">" + res[2] + "</span>		</div>"
				+ "		<div class=\"hidecell\">			<p>"
				+ "				A property from the (all/target) input ontologies should have at	most one mapping in the merged ontology [4]. <br>"
				+ res[3] + "</p>" + "	</div>		<div class=\"showcell\">"
				+ "			<span>Instance redundancy prohibition</span> <span		style=\"float: right;\">" + res[4]
				+ "</span>	</div>"
				+ "		<div class=\"hidecell\">			<p>	An instance from the (all/target) input ontologies should have at	most one mapping in the merged ontology. <br>"
				+ res[5] + "</p>	</div>"
				+ "		<div class=\"showcell\">	<span>Extraneous entity prohibition</span> <span	style=\"float: right;\">"
				+ res[6] + "</span>"
				+ "	</div>	<div class=\"hidecell\">	<p>		No additional entities other than input ontologies' entities"
				+ "	should be added in the merged result [7]. <br>" + res[7] + "</p>	</div>	</div>";
		return gui;
	}

	public static String createDeductionGui(String[] res) {
		String gui = "";
		gui += "<div id=\"idSecDeduction\">" + "<br> " + " <hh>Deduction " + "Aspect</hh>		"
				+ "		<div class=\"showcell\">			<span>Entailments deduction satisfaction</span> <span"
				+ "	style=\"float: right;\">" + res[0] + "</span>		</div>	<div class=\"hidecell\">	<p>"
				+ "	The merged ontology should be able to entail all entailments of	(all/target) input ontologies. As the semantic consequences of"
				+ "	the integration, it can include more entailments but it should at	least not miss knowledge from the input ontologies [2]. <br>"
				+ res[1] + "</p></div></div>";
		return gui;
	}

	public static String createConstraintGui(String[] res) {
		String gui = "";
		gui += "<div id=\"idSecConstraint\">" + "	<br> " + "<hh>Constraint" + "	Aspect</hh>	"
				+ "	<div class=\"showcell\">		<span>One type restriction</span> <span style=\"float: right;\">"
				+ res[0] + "</span>	</div>	<div class=\"hidecell\">	<p>"
				+ "Two corresponding entities should follow the same data type [7];	e.g., if the range of ``authorId'' in one of the input ontology	is String and in the other one is Integer, then the range of the merged entity ``authorId'' in the merged ontology cannot have	both types. <br>"
				+ res[1] + "</p>	</div>	<div class=\"showcell\">	<span>Property's Value constraint </span> <span"
				+ "	style=\"float: right;\">" + res[2]
				+ "</span>	</div>	<div class=\"hidecell\">	<p>If the (all/target) input ontologies place some restriction on a property's values (e.g., in terms of cardinality or by	enumerating possible values) this should be preserved without"
				+ "	conflict in the merged ontology [7]. <br>" + res[3]
				+ "</p>	</div>	<div class=\"showcell\"><span>Property's domain and range oneness</span> <span"
				+ "	style=\"float: right;\">" + res[4] + "</span>	</div>	<div class=\"hidecell\"><p>"
				+ "The merge process should not result in multiple domains or ranges defined for a single property. This rule is recast from the ontology modelling issues in [8] <br>"
				+ res[5] + "</p>	</div></div>";
		return gui;
	}

	public static String createAcyclicityGui(String[] res) {
		String gui = "";
		gui += "<div id=\"idSecAcyclicity\"> <br> " + "<hh>Acyclicity	Aspect</hh>	"
				+ "<div class=\"showcell\">	<span>Acyclicity in the class hierarchy</span> <span style=\"float: right;\">"
				+ res[0] + "</span></div>	<div class=\"hidecell\">	"
				+ "<p>A cycle of is-a relationships implies equality of all of the	classes in the cycle, since is-a is transitive. Therefore, the merge process should not produce a cycle in the class hierarchy[1,3,5,7,8,10]. <br>"
				+ res[1] + "</p>	</div><div class=\"showcell\"><span>Acyclicity in the property hierarchy</span> "
				+ "<span	style=\"float: right;\">" + res[2] + "</span></div><div class=\"hidecell\">"
				+ "<p>The merge process should not produce a cycle between properties with respect to the is-subproperty-of relationship [8]. <br>"
				+ res[3]
				+ "</p></div><div class=\"showcell\"><span>Prohibition of properties being inverses of themselves </span> "
				+ "<span style=\"float: right;\">" + res[4] + "</span>	</div>	<div class=\"hidecell\">"
				+ "<p>	The merged process should not cause an inverse recursive definition on the properties [8]. <br>"
				+ res[5] + "</p></div></div>";
		return gui;
	}

	public static String createConnectivityGui(String[] res) {
		String gui = "";
		gui += "<div id=\"idSecConnectivity\">" + "<br> <hh>Connectivity"
				+ "		Aspect</hh>			<div class=\"showcell\">"
				+ "			<span>Unconnected class prohibition</span> <span" + "			style=\"float: right;\">"
				+ res[0] + "</span>		</div>		<div class=\"hidecell\">		<p>"
				+ "The merge process should not make the classes unconnected [3,7].	Every class that had some connections in the input ontologies before the merge process, should not be unconnected after merge process in the merged ontology. <br>"
				+ res[1] + "</p>	</div>	<div class=\"showcell\">"
				+ "	<span>Unconnected property prohibition</span> <span	style=\"float: right;\">" + res[2]
				+ "</span>	</div>	<div class=\"hidecell\"><p>The merge process should not make the properties unconnected"
				+ "	[6,8]. Every property that had some connections in the input "
				+ "ontologies before the merge process, should not be unconnected"
				+ "	after merge process in the merged ontology. <br>" + res[3] + "</p>	</div>	</div>";
		return gui;
	}

	public static String createMapperGui(String[] res) {
		String gui = "";
		gui += "<div id=\"idSecMapper\">" + "		<br> " + " <hh>Mapping"
				+ "			Information</hh>			<div id=\"section1\">"
				+ "			<div style=\"background: #f7f7f7; padding: 5px;\">"
				+ "				<font color=\"black\"> <span style=\"padding-left: 8em;\">"
				+ "				</span> <span style=\"padding-left: 12em;\">Classes </span> <span"
				+ "					style=\"padding-left: 8em;\">Properties </span> <span"
				+ "					style=\"padding-left: 8em;\">Individuals </span> "
				+ "				</font>			</div>" + "			<div class=\"showcell\">			"
				+ "<span>Equal entities &nbsp;&nbsp;&nbsp;</span> <span"
				+ "					style=\"padding-left: 12em;\">" + res[1] + "</span> <span"
				+ "					style=\"padding-left: 12em;\">" + res[3] + "</span> <span"
				+ "					style=\"padding-left: 12em;\">" + res[5] + "</span>"
				+ "			</div>			<div class=\"hidecell\">"
				+ "				<p>	It shows the number of equal classes, properties, individuals, "
				+ "and annotations from the mapping file.<br> " + res[4]
				+ "</p>		</div>			"
				+ "			<br>" + "			<div class=\"showcell\">"
				+ "				<span>Number of is-a mapping<sup>-</sup></span> <span"
				+ "		style=\"float: right;\">" + res[9] + "</span>"
				+ "			</div>			<div class=\"hidecell\">"
				+ "				<p>It shows the number of is-a mapping.</p>			</div>"
				+ "			<div class=\"showcell\">				<span>Type of mapping<sup>-</sup></span> <span"
				+ "					style=\"float: right;\">" + res[11] + "</span>"
				+ "			</div>			<div class=\"hidecell\">"
				+ "				<p>It shows the type of mapping.</p>			</div>" + "		</div> </div>";
		return gui;
	}

	public static String createCompactnessGui(String[] res) {
		String gui = "";
		gui += "<div id=\"idSecCompactness\">" + "	<br> <hh>Compactness</hh>"
				+ "	<div id=\"section2\">			<div style=\"background: #f7f7f7; padding: 5px;\">"
				+ "				<font color=\"black\"> <span style=\"padding-left: 8em;\">"
				+ "				</span> <span style=\"padding-left: 10em;\">Classes </span> <span"
				+ "					style=\"padding-left: 10em;\">Properties </span> <span"
				+ "				style=\"padding-left: 10em;\">Individuals </span>"
				+ "				</font>			</div>			<div class=\"showcell\">"
				+ "		<span>Absolute size &nbsp;&nbsp;&nbsp;&nbsp;</span> <span	style=\"padding-left: 10em;\">"
				+ res[1] + "</span> <span	style=\"padding-left: 12em;\">" + res[3]
				+ "</span> <span			style=\"padding-left: 12em;\">" + res[5]
				+ "</span>		</div>		<div class=\"hidecell\">"
				+ "	<p>The absolute size metric represents the exact number of"
				+ "				entities existing in your merged ontology [9]. It shows the"
				+ "					absolute number of Classes, Properties (ObjectProperty and"
				+ "				DatatypeProperty), Individuals, and entity Annotations.</p>"
				+ "		</div>		<div class=\"showcell\">"
				+ "			<span>Relative size &nbsp;&nbsp;&nbsp;&nbsp;</span> <span		style=\"padding-left: 10em;\">"
				+ res[9] + "</span> <span			style=\"padding-left: 12em;\">" + res[11]
				+ "</span> <span				style=\"padding-left: 12em;\">" + res[13]
				+ "</span>		</div>		<div class=\"hidecell\">			<p>	Relative size shows the ratio of the number of entities in the"
				+ "		merged ontology by the sum of the entity in all input		ontologies. <br>" + res[10]
				+ " <br>" + res[12] + " <br>" + res[14] + "<br>" + res[16]
				+ "</p>		</div>		<div class=\"showcell\">"
				+ "	<span>Distributed size</span> <span style=\"padding-left: 10em;\">" + res[17]
				+ "</span>			<span style=\"padding-left: 12em;\">" + res[19]
				+ "</span> <span			style=\"padding-left: 12em;\">" + res[21]
				+ "</span>			</div>			<div class=\"hidecell\">"
				+ "	<p>Distributed size [13] is evaluated:</p>			<ul>"
				+ "		<li>Class distribution: is defined as the average number of"
				+ "		subclasses per class.</li>			<li>Property distribution: is defined as the ratio of the"
				+ "					number of properties divided by the sum of the number of"
				+ "					subclasses plus the number of properties. This metric reflects"
				+ "		the diversity of properties and placement of properties in the			ontology.</li>"
				+ "		<li>Individual distribution: is represented how many		individuals are distributed across classes. This is the ratio"
				+ "	between the number of classes that have instances divided by"
				+ "		the total number of classes.</li>"
				+ "		<li>Annotation distribution: is reflected the number of"
				+ "		annotations per classes.</li>" + "		</ul>" + "		</div>		</div>	</div>";
		return gui;
	}

	public static String createCoverageGui(String[] res) {
		String gui = "";
		gui += "<div id=\"idSecCoverage\">" + "	<br> <hh>Coverage</hh>" + "		<div id=\"section3\">"
				+ "			<div style=\"background: #f7f7f7; padding: 10px;\">"
				+ "		<font color=\"black\"> <span style=\"padding-left: 5em;\">"
				+ "				</span> <span style=\"padding-left: 12em;\">Classes </span> <span"
				+ "					style=\"padding-left: 10em;\">Properties </span> <span"
				+ "					style=\"padding-left: 8em;\">Individuals </span>"
				+ "				</font>			</div>" + "			<div class=\"showcell\">"
				+ "		<span>Full coverage &nbsp;&nbsp;&nbsp;&nbsp;</span> <span"
				+ "					style=\"padding-left: 10em;\">" + res[1] + "</span> <span"
				+ "					style=\"padding-left: 12em;\">" + res[2] + "</span> <span"
				+ "					style=\"padding-left: 12em;\">" + res[3]
				+ "</span>			</div>			<div class=\"hidecell\">"
				+ "				<p>The full coverage metric shows the percentage of entities"
				+ "					presented in all input ontologies that are covered by the merged"
				+ "					ontology [1,9].</p>" + res[4]
				+ "			</div>";
		return gui;
	}

	public static String createUsabilityProfileGui(String[] res) {
		String gui = "";
		gui += "<div id=\"idSecUsabilityProfile\">" + "<br> <hh>Usability"
				+ "	Profile</hh>			<div id=\"section7\">" + "		<div class=\"showcell\">"
				+ "		<span>Correctness of ontology URI</span> <span		style=\"float: right;\">" + res[0]
				+ "</span>	</div>"
				+ "	<div class=\"hidecell\">	<p>It checks the correctness of ontology URI [8].<br>" + res[1]
				+ "</p>		</div>		<div class=\"showcell\">"
				+ "	<span>Correctness of ontology namespace</span> <span style=\"float: right;\">" + res[2]
				+ "</span>		</div>	<div class=\"hidecell\">			<p>"
				+ "It checks the correctness of ontology namespace [8].<br>" + res[3] + "</p>		</div>"
				+ "		<div class=\"showcell\">" + "	<span>Correctness of ontology declaration </span> <span"
				+ "		style=\"float: right;\">" + res[4] + "</span>	</div>" + "<div class=\"hidecell\">" + res[5]
				+ " <br>	</div>	<div class=\"showcell\">"
				+ "		<span>Correctness of ontology licence</span> <span		style=\"float: right;\">" + res[6]
				+ "</span>		</div>		<div class=\"hidecell\">" + res[7]
				+ " <br>	</div>	<div class=\"showcell\">"
				+ "	<span>Label uniqueness</span> <span style=\"float: right;\">" + res[8]
				+ "</span>		</div>		<div class=\"hidecell\">" + res[9]
				+ " <br>	</div>		<div class=\"showcell\">"
				+ "	<span>Unify naming</span> <span style=\"float: right;\">" + res[10]
				+ "</span>		</div>		<div class=\"hidecell\">"
				+ "		<p>			<ul>Using same naming criteria in the ontology calculates the "
				+ "ontology elements are named following the same convention within	the whole ontology [8,5]. It checks:"
				+ "<li>The same capitalization for concept names (upper case	and lower case)</li>"
				+ "<li>Singular or plural of entities</li>	<li>Prefix and suffix conventions: do the ontology use a "
				+ "different prefix for classes and properties? e.g. author class	and has_author property.</li>"
				+ "</ul>" + res[11] + "<br>		</p>		</div>		<div class=\"showcell\">"
				+ "	<span>Entity Type declaration</span> <span style=\"float: right;\">" + res[12]
				+ "</span>		</div>		<div class=\"hidecell\">		<p>"
				+ "This means checks existence of a declaration of the owl:Ontology	tag where the ontology metadata should be provided [8].<br>"
				+ res[13] + "</p>			</div>	</div>	</div>";
		return gui;
	}

}
