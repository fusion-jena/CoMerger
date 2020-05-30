<!DOCTYPE html>
<!--
Template Name: Corklow
Author: <a href="http://www.os-templates.com/">OS Templates</a>
Author URI: http://www.os-templates.com/
Licence: Free to use under our free template licence terms
Licence URI: http://www.os-templates.com/template-terms
-->
<html>
<head>
<title>Co-Merger | Query</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="${pageContext.request.contextPath}/layout/styles/layout.css"
	rel="stylesheet" type="text/css" media="all">
<link href="${pageContext.request.contextPath}/dist/filepond.css"
	rel="stylesheet">
<script
	src="https://www.google.com/recaptcha/api.js?render=6Lc5rrUUAAAAAKBNdawrac6WLfCWmtTHiqWd4vCH"></script>
</head>
<body id="top">
	<form method="post"
		action="${pageContext.request.contextPath}/MergingServlet"
		enctype="multipart/form-data" onsubmit='showLoading();'>
		<!-- ################################################################################################ -->
		<!-- ################################################################################################ -->
		<!-- ################################################################################################ -->
		<div class="wrapper row0">
			<div id="topbar" class="hoc clear">
				<!-- ################################################################################################ -->
				<div id="logo" class="fl_left">
					<h1>
						<a href="${pageContext.request.contextPath}/index.jsp">Co-Merger</a>
					</h1>
					<p>Multiple Ontologies Merger</p>
				</div>
				<!-- ################################################################################################ -->
				<nav id="mainav" class="fl_right">
					<ul class="clear">
						<li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
						<li><button class="btn menu" type="submit" name="submit"
								value="GoMerger">Merger</button></li>
						<li><button class="btn menu" type="submit" name="submit"
								value="GoEvaluator">Evaluator</button></li>
						<li><a href="${pageContext.request.contextPath}/aboutUs.jsp">About
								Us</a></li>
						<li><a href="${pageContext.request.contextPath}/contact.jsp">Contact
								Us</a></li>
						<li><a class="drop"
							href="${pageContext.request.contextPath}/help.jsp">User Help</a>
							<ul>
								<li><a
									href="${pageContext.request.contextPath}/requirement.jsp">Requirements</a></li>
								<li><a href="${pageContext.request.contextPath}/GMR.jsp">GMRs
										Framework</a></li>
								<li><a
									href="${pageContext.request.contextPath}/queryCatalog.jsp">Query
										Catalogue</a></li>
								<li><a
									href="${pageContext.request.contextPath}/cqCatalog.jsp">CQ
										Catalogue</a></li>
											<li><a
									href="${pageContext.request.contextPath}/OMstandard.jsp">Standard for Evaluation</a></li>
							</ul></li>
					</ul>
				</nav>
				<!-- ################################################################################################ -->
			</div>
		</div>
		<!-- ################################################################################################ -->
		<!-- ################################################################################################ -->
		<!-- ################################################################################################ -->
		<div class="wrapper bgded overlay"
			style="background-image: url('${pageContext.request.contextPath}/layout/images/01.png');">
			<div id="breadcrumb" class="hoc clear">
				<!-- ################################################################################################ -->
				<ul>
					<li><a href="#">Home</a></li>
					<li><a href="#">Merger</a></li>
				</ul>
				<!-- ################################################################################################ -->
			</div>
		</div>
		<!-- ################################################################################################ -->
		<!-- ################################################################################################ -->
		<!-- ################################################################################################ -->
		<div class="wrapper row3">
			<main class="hoc container clear"> <!-- main body --> <!-- ################################################################################################ -->

			<input type="hidden" id="g-recaptcha-response"
				name="g-recaptcha-response"> <input type="hidden"
				name="action" value="validate_captcha"> <input
				name="uploadedInputFiles" id="uploadedInputFiles" type="text"
				value="${uploadedInputFiles}" style="display: none;"> <input
				name="uploadedMapFiles" id="uploadedMapFiles" type="text"
				value="${uploadedMapFiles}" style="display: none;"> <input
				name="uploadedMergeFile" id="uploadedMergeFile" type="text"
				value="${uploadedMergeFile}" style="display: none;"> <input
				name="ListOntsQ" id="ListOntsQ" type="text" value="${ListOntsQ}"
				style="display: none;">

			<div class="form_settings">
				<h2>Run Query</h2>
				<a href="${pageContext.request.contextPath}/queryCatalog.jsp">
					See query catalog </a>
				<table style="width: 100%;">
					<tr>
						<td><table class="myTable">
								<tr>
									<td colspan="2"><h2>on Merged Ontology</h2></td>
								</tr>
								<tr>
									<td>Load template</td>
									<td><select id="template"
										onchange="showQueryTemplateOM(this);">
										<option>Select one template from the list</option>
											<option>Retrieve all classes</option>
											<option>Retrieve subclass relations between classes</option>
											<option>Retrieve subclasses of a specific class</option>
											<option>Check the subclass relations of two classes</option>
											<option>Retrieve SubCalss and SuperClass of a class</option>
											<option>Retrieve only SubClasses of a class</option>
											<option>Retrieve only SuperClasses of a class</option>
											<option>Check whether a specific element exists</option>
											<option>Retrieve all subProperty of a property</option>
											<option>Retrieve all individuals of a class</option>
									</select></td>
								</tr>
								<tr>
									<td colspan="2"><lable id="queryStringDescriptionOM"
											style="color: blue;"></lable></td>
								</tr>
								<tr>
									<td colspan="2"><br></td>
								</tr>
								<tr>
									<td colspan="2"><textarea id="queryStringM"
											name="queryStringM" title="Edit your query here"
											style="width: 100%; font-size: 14px; height: 120px;">${queryStringM}</textarea></td>
								</tr>
								<tr>
									<td colspan="2" align="center"><button class="btn small"
											type="submit" name="submit" value="DoQueryM">Run
											Query</button></td>
								</tr>
								<tr>
									<td colspan="2" id="resultLabelM"><h2>Your result</h2></td>
								</tr>
								<tr>
									<td colspan="2"><textarea id="resultQueryM"
											name="resultQueryM"
											style="width: 100%; font-size: 14px; height: 300px;">${resultQueryM}</textarea></td>
								</tr>
							</table></td>
						<td><table class="myTable">
								<tr>
									<td colspan="2"><h2>on Source Ontologies</h2></td>
								</tr>
								<tr>
									<td>Load template</td>
									<td><select id="template"
										onchange="showQueryTemplateOS(this);">
											<option>Select one template from the list</option>
											<option>Retrieve all classes</option>
											<option>Retrieve subclass relations between classes</option>
											<option>Retrieve subclasses of a specific class</option>
											<option>Check the subclass relations of two classes</option>
											<option>Retrieve SubCalss and SuperClass of a class</option>
											<option>Retrieve only SubClasses of a class</option>
											<option>Retrieve only SuperClasses of a class</option>
											<option>Check whether a specific element exists</option>
											<option>Retrieve all subProperty of a property</option>
											<option>Retrieve all individuals of a class</option>
									</select></td>
								</tr>
								<tr>
									<td>Query on ontology</td>
									<td><select id="SelOntQ" name="SelOntQ"
										style="width: 150px;">${ListOntsQ}
									</select></td>
								</tr>
								<tr>
									<td colspan="2"><lable id="queryStringDescriptionOS"
											style="color: blue;"></lable></td>
								</tr>
								<tr>
									<td colspan="2"><textarea id="queryStringO"
											name="queryStringO" title="Edit your query here"
											style="width: 100%; font-size: 14px; height: 120px;">${queryStringO}</textarea></td>
								</tr>
								<tr>
									<td colspan="2" align="center"><button class="btn small"
											type="submit" name="submit" value="DoQueryO">Run
											Query</button></td>
								</tr>
								<tr>
									<td colspan="2" id="resultLabelO"><h2>Your result</h2></td>
								</tr>
								<tr>
									<td colspan="2"><textarea id="resultQueryO"
											name="resultQueryO"
											style="width: 100%; font-size: 14px; height: 300px;">${resultQueryO}</textarea></td>
								</tr>
							</table></td>
					</tr>
				</table>
				<span style="font-weight: bold; color: red; width: 100%;">${msg}</span>
				<div>
					Your log file: <a href="${logFile}"> Download </a>
				</div>
			</div>


			<div class="form_settings">

				<div id="loadingmsg" style="display: none;">
					<img
						src="${pageContext.request.contextPath}/layout/images/load.gif"
						border="0" /> <input name="resultFile" id="resultFile"
						type="text" value="${resultFile}" style="display: none;">
					<input name="inputFiles" id="inputFiles" type="text"
						value="${inputFiles}" style="display: none;">
				</div>
				<div id="loadingover" style="display: none;"></div>
			</div>
			<div class="g-recaptcha"
				data-sitekey="6LfFuU4UAAAAAN-_yVxOMIBF955f-EwAI4EQmfBB"></div>


			<div id="loadingmsg" style="display: none;">
				<img src="${pageContext.request.contextPath}/layout/images/load.gif"
					border="0" />
			</div>
	</form>
	<!-- ################################################################################################ -->
	<!-- / main body -->
	<div class="clear"></div>
	</main>
	</div>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<%@include file="../shared/footer.jsp"%>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->


	<script>
		window.onload = function() {
			if ("${resultQueryM}" === "" || "${resultQueryM}" === null) {
				document.getElementById("resultQueryM").style.display = "none";
				document.getElementById("resultLabelM").style.display = "none";
			} else {
				document.getElementById("resultQueryM").style.display = "block";
				document.getElementById("resultLabelM").style.display = "block";
			}
			if ("${resultQueryO}" === "" || "${resultQueryO}" === null) {
				document.getElementById("resultQueryO").style.display = "none";
				document.getElementById("resultLabelO").style.display = "none";
			} else {
				document.getElementById("resultQueryO").style.display = "block";
				document.getElementById("resultLabelO").style.display = "block";
			}

		};
	</script>
	<script>
		function showQueryTemplateOS(sel) {
			pref = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>&#13;&#10;PREFIX owl: <http://www.w3.org/2002/07/owl#>&#13;&#10;PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>&#13;&#10;PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>&#13;&#10;";
			q1 = pref
					+ "Select DISTINCT ?class  &#13;&#10;   WHERE { ?class a owl:Class}";
			q2 = pref
					+ "SELECT ?subject ?object &#13;&#10;  	WHERE { ?subject rdfs:subClassOf ?object }";
			q3 = pref
					+ "SELECT ?allSubClass  &#13;&#10;     WHERE {?allSubClass rdfs:subClassOf   <http://merged#sample>}";
			q4 = pref
					+ "ASK { <http://merged#child> rdfs:subClassOf <http://merged#father> }";
			q5 = pref
					+ "SELECT ?class WHERE  { { &#13;&#10;    SELECT ?class WHERE {  <http://ont#sample> rdfs:subClassOf ?class} &#13;&#10;	} UNION {&#13;&#10;		    SELECT ?class WHERE {?class rdfs:subClassOf    <http://ont#sample> }&#13;&#10; } }";
			q6 = pref
					+ "SELECT ?class  WHERE {  ?class rdfs:subClassOf    <http://ont#sample>}";
			q7 = pref
					+ "SELECT ?class WHERE {?class rdfs:subClassOf <http://ont#sample>}";
			q8 = pref + "ASK {  <http://ont#sample> rdf:type  owl:Class }";
			q9 = pref
					+ "SELECT ?property WHERE{ ?property  rdfs:subPropertyOf <http://ont#sample> }";
			q10 = pref
					+ "SELECT ?individual WHERE{ ?individual  rdf:type <http://ont#sample> }";

			files = [ "", q1, q2, q3, q4, q5, q6, q7, q8, q9, q10 ];
			description = [
					"",
					"This query returns all classes, requiring some prefix definitions. You can replace variable ?class with any desired name.",
					"This query returns the subclass relationship between the classes. You can replace variables ?subject and ?object with any desired names.",
					"This query returns all subclasses of a specific class. You must replace 'http://merged#sample' with a specific class in your ontology.",
					"This query checks whether two classes have a subclass relations. You must replace 'http://merged#child' and 'http://merged#father' with the specific classes in your ontologies.",
					"This query returns all SubClasses and SuperClasses of a specific class. You must replace 'http://ont#sample' with the specific class in your ontology.",
					"This query returns only SubClasses of a specific class. You must replace 'http://ont#sample' with the specific class in your ontology.",
					"This query returns only SuperClasses of a specific class. You must replace 'http://ont#sample' with the specific class in your ontology.",
					"This query asks whether a specific element exists in the ontology and its type is OWL-CLASS. You must replace 'http://ont#sample' with the specific class in your ontology.",
					"This query returns all subProperty of a specific property. You must replace 'http://ont#sample' with the specific property in your ontology.",
					"This query return all individuals of a specific class. You must replace 'http://ont#sample' with the specific class in your ontology.", ];

			srcfile = files[sel.selectedIndex];
			if (srcfile != undefined && srcfile != "") {
				document.getElementById('queryStringO').innerHTML = srcfile;
			}

			srcDesc = description[sel.selectedIndex];
			if (srcDesc != undefined && srcDesc != "") {
				document.getElementById('queryStringDescriptionOS').innerHTML = srcDesc;
			}
		}
	</script>
	<script>
		function showQueryTemplateOM(sel) {
			pref = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>&#13;&#10;PREFIX owl: <http://www.w3.org/2002/07/owl#>&#13;&#10;PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>&#13;&#10;PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>&#13;&#10;";
			q1 = pref
					+ "Select DISTINCT ?class  &#13;&#10;   WHERE { ?class a owl:Class}";
			q2 = pref
					+ "SELECT ?subject ?object &#13;&#10;  	WHERE { ?subject rdfs:subClassOf ?object }";
			q3 = pref
					+ "SELECT ?allSubClass  &#13;&#10;     WHERE {?allSubClass rdfs:subClassOf   <http://merged#sample>}";
			q4 = pref
					+ "ASK { <http://merged#child> rdfs:subClassOf <http://merged#father> }";
			q5 = pref
					+ "SELECT ?class WHERE  { { &#13;&#10;    SELECT ?class WHERE {  <http://ont#sample> rdfs:subClassOf ?class} &#13;&#10;	} UNION {&#13;&#10;		    SELECT ?class WHERE {?class rdfs:subClassOf    <http://ont#sample> }&#13;&#10; } }";
			q6 = pref
					+ "SELECT ?class  WHERE {  ?class rdfs:subClassOf    <http://ont#sample>}";
			q7 = pref
					+ "SELECT ?class WHERE {?class rdfs:subClassOf <http://ont#sample>}";
			q8 = pref + "ASK {  <http://ont#sample> rdf:type  owl:Class }";
			q9 = pref
					+ "SELECT ?property WHERE{ ?property  rdfs:subPropertyOf <http://ont#sample> }";
			q10 = pref
					+ "SELECT ?individual WHERE{ ?individual  rdf:type <http://ont#sample> }";

			files = [ "", q1, q2, q3, q4, q5, q6, q7, q8, q9, q10 ];
			description = [
					"",
					"This query returns all classes, requiring some prefix definitions. You can replace variable ?class with any desired name.",
					"This query returns the subclass relationship between the classes. You can replace variables ?subject and ?object with any desired names.",
					"This query returns all subclasses of a specific class. You must replace 'http://merged#sample' with a specific class in your ontology.",
					"This query checks whether two classes have a subclass relations. You must replace 'http://merged#child' and 'http://merged#father' with the specific classes in your ontologies.",
					"This query returns all SubClasses and SuperClasses of a specific class. You must replace 'http://ont#sample' with the specific class in your ontology.",
					"This query returns only SubClasses of a specific class. You must replace 'http://ont#sample' with the specific class in your ontology.",
					"This query returns only SuperClasses of a specific class. You must replace 'http://ont#sample' with the specific class in your ontology.",
					"This query asks whether a specific element exists in the ontology and its type is OWL-CLASS. You must replace 'http://ont#sample' with the specific class in your ontology.",
					"This query returns all subProperty of a specific property. You must replace 'http://ont#sample' with the specific property in your ontology.",
					"This query return all individuals of a specific class. You must replace 'http://ont#sample' with the specific class in your ontology.", ];

			srcfile = files[sel.selectedIndex];
			if (srcfile != undefined && srcfile != "") {
				document.getElementById('queryStringM').innerHTML = srcfile;
			}

			srcDesc = description[sel.selectedIndex];
			if (srcDesc != undefined && srcDesc != "") {
				document.getElementById('queryStringDescriptionOM').innerHTML = srcDesc;
			}
		}
	</script>

	<script src="${pageContext.request.contextPath}/dist/filepond.js"></script>

</body>
</html>