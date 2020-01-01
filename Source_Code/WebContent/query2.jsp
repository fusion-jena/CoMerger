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
									<td>Query category</td>
									<td><select id="text-one">
											<option selected value="empty">-- Select a template
												--</option>
											<option value="beverages">Classes</option>
											<option value="base">Properties</option>
											<option value="snacks">Individuals</option>
									</select></td>
								</tr>
								<tr>
									<td>Load template</td>
									<td><select id="text-two">
											<option>-- Please choose from above --</option>
									</select></td>
								</tr>
								<tr>
									<td colspan="2"><br></td>
								</tr>
								<tr>
									<td colspan="2"><textarea id="queryStringM"
											name="queryStringM" title="Edit your query here"
											style="width: 100%; font-size: 10px; height: 120px;">${queryStringM}</textarea></td>
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
											style="width: 100%; font-size: 10px; height: 300px;">${resultQueryM}</textarea></td>
								</tr>
							</table></td>
						<td><table class="myTable">
								<tr>
									<td colspan="2"><h2>on Input Ontologies</h2></td>
								</tr>
								<tr>
									<td>Query category</td>
									<td><select id="text-one">
											<option selected value="empty">-- Select a template
												--</option>
											<option value="beverages">Classes</option>
											<option value="base">Properties</option>
											<option value="snacks">Individuals</option>
									</select></td>
								</tr>
								<tr>
									<td>Load template</td>
									<td><select id="text-two">
											<option>-- Please choose from above --</option>
									</select></td>
								</tr>
								<tr>
									<td>Query on ontology</td>
									<td><select id="SelOntQ" name="SelOntQ"
										style="width: 150px;">${ListOntsQ}
									</select></td>
								</tr>
								<tr>
									<td colspan="2"><textarea id="queryStringO"
											name="queryStringO" title="Edit your query here"
											style="width: 100%; font-size: 10px; height: 120px;">${queryStringO}</textarea></td>
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
											style="width: 100%; font-size: 10px; height: 300px;">${resultQueryO}</textarea></td>
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