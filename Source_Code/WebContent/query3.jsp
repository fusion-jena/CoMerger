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
				<p>Holistic Multiple Ontologies Merger</p>
			</div>
			<!-- ################################################################################################ -->
			<nav id="mainav" class="fl_right">
				<ul class="clear">
					<li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
					<li><a class="drop"
						href="${pageContext.request.contextPath}/merging.jsp">Merger</a>
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
						</ul></li>
					<li class="active"><a
						href="${pageContext.request.contextPath}/mergingEval.jsp">Evaluator</a>
					</li>
					<li><a href="${pageContext.request.contextPath}/aboutUs.jsp">About
							Us</a></li>
					<li><a href="${pageContext.request.contextPath}/contact.jsp">Contact
							Us</a></li>
					<li><a href="${pageContext.request.contextPath}/help.jsp">User
							Help</a></li>
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
		<form method="post"
			action="${pageContext.request.contextPath}/MergingServlet"
			enctype="multipart/form-data" onsubmit='showLoading();'>
			<input type="hidden" id="g-recaptcha-response"
				name="g-recaptcha-response"> <input type="hidden"
				name="action" value="validate_captcha"> <input
				name="uploadedInputFiles" id="uploadedInputFiles" type="text"
				value="${uploadedInputFiles}" style="display: none;"> <input
				name="uploadedMapFile" id="uploadedMapFile" type="text"
				value="${uploadedMapFile}" style="display: none;"> <input
				name="uploadedMergeFile" id="uploadedMergeFile" type="text"
				value="${uploadedMergeFile}" style="display: none;">
			<div class="form_settings">
				<h2>Run Query</h2>
				<a href="${pageContext.request.contextPath}/queryCatalog.jsp">
					See query catalog </a>
				<table class="myTable">
					<tr>
						<td colspan="2">Write one query, it runs on the merged ontologies and all
							source ontologies.</td>
					</tr>
					<tr>
						<td>Query category </td><td> <select id="text-one">
								<option selected value="empty">-- Select a template --</option>
								<option value="beverages">Classes</option>
								<option value="base">Properties</option>
								<option value="snacks">Individuals</option>
						</select>
						</td>
					</tr>
					<tr>
						<td >Load template </td> <td> <select id="text-two"
							onchange="showfile(this);">
								<option>-- Please choose from above --</option>
						</select>
						</td>
					</tr>
					<tr>
						<td colspan="2"><textarea id="queryStringAll" name="queryStringAll"
								title="Edit your query here"
								style="width: 100%; font-size: 10px; height: 120px;">${queryStringAll}</textarea></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><button class="btn small" type="submit" name="submit"
								value="DoQueryAll">Run Query</button></td>
					</tr>
				</table>
				<table id="queryOutput" class="myTable">
					<tr>
						<td>
							<table>
								<tr>
									<td>
										<!-- <h2>Result on merged ontology</h2> <textarea
											id="resultQueryM" name="resultQueryM"
											style="width: 100%; font-size: 10px; height: 300px;">${resultQueryM}</textarea> -->
										${resultQueryM}
									</td>
									<td>
										<!-- <h2>Result on 1st ontology</h2> <textarea
											id="resultQueryO1" name="resultQueryO1"
											style="width: 100%; font-size: 10px; height: 300px;">${resultQueryO1}</textarea>
										<h2>Result on 2nd ontology</h2> <textarea id="resultQueryO2"
											name="resultQueryO2"
											style="width: 100%; font-size: 10px; height: 300px;">${resultQueryO2}</textarea>-->
										${resultQueryO}
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<span style="font-weight: bold; color: red; width: 100%;">${msg}</span>
			</div>


			<div class="form_settings">
				<div>
					Your log file: <a href="${logFile}"> Download </a>
				</div>
				<div id="loadingmsg" style="display: none;">
					<img
						src="${pageContext.request.contextPath}/layout/images/load.gif"
						border="0" /> <input name="resultFile" id="resultFile"
						type="text" value="${resultFile}" style="display: none;">
					<input name="inputFile" id="inputFile" type="text"
						value="${inputFile}" style="display: none;">
				</div>
				<div id="loadingover" style="display: none;"></div>
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
				document.getElementById("queryOutput").style.display = "none";
			}

		};
	</script>
	<script>
		function showfile(sel) {
			files = [
					"PREFIX  myont: <http://www.co-ode.org/ontologies/cmt/cmt.owl#>   PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>    	  PREFIX  owl:  <http://www.w3.org/2002/07/owl#>Select DISTINCT ?class    	  WHERE { ?class a owl:Class}",
					"PREFIX  myont: <http://www.co-ode.org/ontologies/cmt/cmt.owl#>   PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>    	  PREFIX  owl:  <http://www.w3.org/2002/07/owl#>Select DISTINCT ?class    	  WHERE { ?class a owl:Class}",
					" display file c.txt", "display file d.txt", ];
			srcfile = files[sel.selectedIndex];
			if (srcfile != undefined && srcfile != "") {
				document.getElementById('queryStringAll').innerHTML = srcfile;
			}
		}
	</script>
	<!-- 	<script>
			var carsAndModels = {};
			carsAndModels['CQ'] = [ 'V70', 'XC60', 'XC90' ];
			carsAndModels['PQ'] = [ 'Golf', 'Polo', 'Scirocco', 'Touareg' ];
			carsAndModels['IQ'] = [ 'M6', 'X5', 'Z3' ];

			function ChangeQueryListM() {
				var carList = document.getElementById("QueryGroupM");
				var modelList = document.getElementById("ListTemplateM");
				var selCar = carList.options[carList.selectedIndex].value;
				while (modelList.options.length) {
					modelList.remove(0);
				}
				var cars = carsAndModels[selCar];
				if (cars) {
					var i;
					for (i = 0; i < cars.length; i++) {
						var car = new Option(cars[i], i);
						modelList.options.add(car);
					}
				}
			}

			function ChangeQueryListO() {
				var carList = document.getElementById("QueryGroupO");
				var modelList = document.getElementById("ListTemplateO");
				var selCar = carList.options[carList.selectedIndex].value;
				while (modelList.options.length) {
					modelList.remove(0);
				}
				var cars = carsAndModels[selCar];
				if (cars) {
					var i;
					for (i = 0; i < cars.length; i++) {
						var car = new Option(cars[i], i);
						modelList.options.add(car);
					}
				}
			}
		</script> -->
	<script>
		$(function() {
			$("#text-one").change(
					function() {
						$("#text-two").load(
								"${pageContext.request.contextPath}/textdata/"
										+ $(this).val() + ".txt");
					});
		});
	</script>

	<script src="${pageContext.request.contextPath}/dist/filepond.js"></script>
</body>
</html>