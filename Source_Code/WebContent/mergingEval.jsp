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
<title>Co-Merger | Merge Evaluator</title>
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
				<li><a href="#">Merge Evaluator</a></li>
			</ul>
			<!-- ################################################################################################ -->
		</div>
	</div>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<div class="wrapper row3">
		<main class="hoc container clear"> <!-- main body --> <!-- ################################################################################################ -->
		<div id="showMessage" class="messageSection">
			<span style="font-weight: bold; color: red; margin: 10px;">${msg}</span>
			<span style="margin: 10px;" id="msgOldFile">Using your last
				uploaded file if you do not enter a new file</span> <br>
		</div>
		<h1>Ontology Merging Evaluation</h1>
		<form method="post"
			action="${pageContext.request.contextPath}/MergingServlet"
			enctype="multipart/form-data" onsubmit='showLoading();'>
			<input type="hidden" id="g-recaptcha-response"
				name="g-recaptcha-response"> <input type="hidden"
				name="action" value="validate_captcha">
			<input type="hidden" name="selectedConsParam" id="selectedConsParam" />

			<input name="uploadedInputFiles" id="uploadedInputFiles" type="text"
				value="${uploadedInputFiles}" style="display: none;"> <input
				name="uploadedmapFiles" id="uploadedmapFiles" type="text"
				value="${uploadedmapFiles}" style="display: none;"> <input
				name="uploadedMergeFile" id="uploadedMergeFile" type="text"
				value="${uploadedMergeFile}" style="display: none;"> <input
				type="hidden" name="selectedUserItem" id="selectedUserItem" />

			<table class="myTable">
				<tr>
					<td><h2>Select input ontologies</h2> Your preferred ontology
						(0 means equal): <input id="PreferedOnt" name="PreferedOnt"
						style="width: 30%; display: inline-block" type="text"
						value="${NumPrefOnt}" title="Index of your prefered ontology"
						required></td>
					<td><h2>Select their mapping files</h2> <label
						style="color: gray;">If you do not enter an alignment
							file, it will be generated automatically. </label></td>
				</tr>
				<tr>
					<td><input name="inputFiles" type="file" multiple="multiple"
						accept=".owl,.rdf" class="filepond"></td>
					<td><input type="file" name="mapFiles" id="mapFiles"
						multiple="multiple" accept=".rdf" class="filepond"></td>
				</tr>
			</table>
			<table class="myTable">
				<tr>
					<td><h2>Enter the merged ontology:</h2></td>
				</tr>
				<tr>
					<td><input type="file" name="mergedFile" id="mergedFile"
						class="filepond"></td>
				</tr>
			</table>
			<div id="SectionEval_container">
				<table class="myTable">
					<tr>
						<td>
							<h2>Selecting Evaluation Criteria</h2>
						</td>
						<td colspan="2"><span onclick="SelectAll()"
							style="cursor: pointer; width: 60px;"> <font size="1"><u>[Select
										All]</u></font></span><span onclick="ClearAll()" style="cursor: pointer;"><font
								size="1"><u>[Clear All]</u></font></span></td>
					</tr>
					<tr>
						<td colspan="2"><hh>GMR aspect evaluation<hh></td>
					</tr>
					<tr>
						<td><input ${CompletenessCh} id="CompletenessCheck"
							name="selectedItem" value="CompletenessCheck" type="checkbox">
							Completeness Aspect &emsp;</td>
						<td><input ${MinimalityCh} id="MinimalityCheck"
							name="selectedItem" value="MinimalityCheck" type="checkbox">
							Minimality Aspect &emsp;</td>
						<td><input ${DeductionCh} id="DeductionCheck"
							name="selectedItem" value="DeductionCheck" type="checkbox">
							Deduction Aspect&emsp;</td>
					</tr>
					<tr>
						<td><input ${ConstraintCh} id="ConstraintCheck"
							name="selectedItem" value="ConstraintCheck" type="checkbox">
							Constraint Aspect &emsp;</td>
						<td><input ${AcyclicityCh} id="AcyclicityCheck"
							name="selectedItem" value="AcyclicityCheck" type="checkbox">
							Acyclicity Aspect &emsp;</td>
						<td><input ${ConnectivityCh} id="ConnectivityCheck"
							name="selectedItem" value="ConnectivityCheck" type="checkbox">
							Connectivity Aspect&emsp;</td>
					</tr>
					<tr>
						<td colspan="3"><hh>Gernarl evaluation<hh></td>
					</tr>
					<tr>
						<td><input ${MapperCh} id="MapperCheck" name="selectedItem"
							value="MapperCheck" type="checkbox"> Mapping Analyzer
							&emsp;</td>
						<td><input ${CompactnessCh} id="CompactnessCheck"
							name="selectedItem" value="CompactnessCheck" type="checkbox">
							Compactness &emsp;</td>
						<td><input ${CoverageCh} id="CoverageCheck"
							name="selectedItem" value="CoverageCheck" type="checkbox">
							Coverage&emsp;</td>
					</tr>
					<tr>
						<td colspan="2"><input ${UsabilityProfileCh} id="UsabilityProfileCheck"
							name="selectedItem" value="UsabilityProfileCheck" type="checkbox">
							Usability Profile &emsp;</td>
					</tr>
				</table>
			</div>
			<div class="g-recaptcha"
				data-sitekey="6LfFuU4UAAAAAN-_yVxOMIBF955f-EwAI4EQmfBB"></div>

			<table class="myTable">
				<tr>
					<td><h2>Consistency Test:</h2></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td><input type="radio" name="ConsRadio" value="all">
						All Unsatisfiable Classes</td>
					<td><input type="radio" name="ConsRadio" value="root" checked>
						Only Root Classes</td>
					<td>Max Explanations <input type="number" value="5"
						name="maxExpl" min="0">
					</td>
					<td align="center"><button class="btn small" type="submit" name="submit"
							value="DoDirectConsistency" onClick="readConsParam()">Test
							Consistency</button></td>
				</tr>
			</table>

			<table>
				<tr align="center">
					<td><button class="btn medium" type="submit" name="submit"
							value="DoMergeEval" onClick="readCheckedBoxes()">Evaluate
							Merge result</button></td>
				</tr>
				<tr align="center">
					<td><button class="btn small" type="submit" name="submit"
							value="GoQueryOne" onClick="readCheckedBoxes()">Test
							with single query!</button>
						<button class="btn small" type="submit" name="submit"
							value="GoQuerySeveral" onClick="readCheckedBoxes()">Test
							with several queries!</button></td>
				</tr>
			</table>
			<div>
				Your log file: <a href="${logFile}"> Download </a>
			</div>
			<div id="loadingmsg" style="display: none;">
				<img src="${pageContext.request.contextPath}/layout/images/load.gif"
					border="0" />
			</div>
			<div id="loadingover" style="display: none;"></div>


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


	<!-- File upload- FilePond library -->
	<!-- <script src="https://unpkg.com/filepond/dist/filepond.js"></script>-->
	<script src="${pageContext.request.contextPath}/dist/filepond.js"></script>
	<script type="text/javascript" src="../js/mergingEval.js"></script>
	<script>
		window.onload = function() {
			if ("${NumPrefOnt}" === "") {
				document.getElementById('PreferedOnt').value = "equal";
			} else {
				document.getElementById("PreferedOnt").value = "${NumPrefOnt}";
			}

			if ("${msg}" === "") {
				document.getElementById("showMessage").style.display = "none";
			}

			if ("${uploadedInputFiles}" === null
					|| "${uploadedmapFiles}" === null
					|| "${uploadedMergeFile}" === null
					|| "${uploadedInputFiles}" === ""
					|| "${uploadedmapFiles}" === ""
					|| "${uploadedMergeFile}" === "") {
				document.getElementById("msgOldFile").style.display = "none";
			} else {
				document.getElementById("msgOldFile").style.display = "block";
				document.getElementById("showMessage").style.display = "block";
			}
		}
	</script>
</body>
</html>