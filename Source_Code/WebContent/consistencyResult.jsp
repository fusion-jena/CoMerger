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
<title>Co-Merger | Consistency Result</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="${pageContext.request.contextPath}/layout/styles/layout.css"
	rel="stylesheet" type="text/css" media="all">
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
				<li><a href="#">Merge Result</a></li>
				<li><a href="#">Consistency Result</a></li>
			</ul>
			<!-- ################################################################################################ -->
		</div>
	</div>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<div class="wrapper row3">
		<main class="hoc container clear"> <!-- main body --> <!-- ################################################################################################ -->

		<!-- <h1>Ontology Merging</h1> -->
		<form method="post"
			action="${pageContext.request.contextPath}/MergingServlet"
			enctype="multipart/form-data" onsubmit='showLoading();'>
			<input type="hidden" id="g-recaptcha-response"
				name="g-recaptcha-response"> <input type="hidden"
				name="action" value="validate_captcha">
			<div id="wrapper" class="inner">
				<input type="hidden" name="selectedUserPlan" id="selectedUserPlan" />
				<input type="hidden" name="selectedConsParam" id="selectedConsParam" />
				<input name="uploadedInputFiles" id="uploadedInputFiles" type="text"
					value="${uploadedInputFiles}" style="display: none;">

				<h2>Consistency Evaluation</h2>
				<!-- /////////////////////////////////////// -->
				<br>
				<div id="idSecConsistency">
					<input type="text" value="${SecConsistency}" style="display: none;">
					<span id="consistency" onclick="ShowSection0()"
						style="cursor: pointer;"> <hh>Consistency Result</hh>
					</span> <img id="img0"
						src="${pageContext.request.contextPath}/layout/images/down.png"
						alt="show table" onclick="ShowSection0()" style="cursor: pointer;">
					<div id="section0" style="width: 100%;">
						<div class="showcell">
							<span>Test with: </span> <span style="float: right;">${res0}</span>
						</div>
						<div class="hidecell">
							<p>${res1}</p>
						</div>
						<div class="showcell">
							<span>The satisfiability test: </span> <span
								style="float: right;">${res2}</span>
						</div>
						<div class="hidecell">
							<p>${res3}</p>
						</div>
						<div class="showcell">
							<span>Number of unsatisfiable classes</span> <span
								style="float: right;">${res4}</span>
						</div>
						<div class="hidecell">
							<p>${res5}</p>
						</div>
						<div class="showcell">
							<span>Root of unsatisfiable classes: </span> <span
								style="float: right;">${res6}</span>
						</div>
						<div class="hidecell">
							<p>${res7}</p>
						</div>
						<div class="showcell">
							<span>Number of justifications: </span> <span
								style="float: right;">${res8}</span>
						</div>
						<div class="hidecell">
							<p>${res9}</p>
						</div>
						<div class="showcell">
							<span>Number of conflicting axioms: </span> <span
								style="float: right;">${res10}</span>
						</div>
						<div class="hidecell">
							<p>${res11}</p>
						</div>
						<div class="showcell">
							<span>Elapsed time: </span> <span style="float: right;"></span>
						</div>
						<div class="hidecell">
							<p>Reasoner time: ${res12} ms</p>
							<p>Rank time: ${res13} ms</p>
							<p>Plan time: ${res14} ms</p>
						</div>
					</div>
				</div>
				<!-- /////////////////////////////////////// -->
				<br> <br>
				<div id="idSecConsistency">
					<input type="text" value="${SecConsistency}" style="display: none;">
					<span id="consistency" onclick="ShowSection1()"
						style="cursor: pointer;"> <hh>Repair Plan</hh>
					</span> <img id="img1"
						src="${pageContext.request.contextPath}/layout/images/down.png"
						alt="show table" onclick="ShowSection1()" style="cursor: pointer;">
					<div id="section1" style="width: 100%; background-color: #fbf9f4;">
						<!-- <div class="showcell">
							<span>Repair plan:</span> <span style="float: right;"></span>
						</div>
						<div class="hidecell">-->
						<p>
							${res15} <br> ${res16}
						</p>
						<!-- </div> -->
					</div>
				</div>
				<!-- /*////////////////////////////////////// -->

			</div>
			<br> <br>

			<div id="idSecConsistencyTest">
				<div id="section0" style="width: 100%;">
					<div class="showcell">
						<span><h2>Consistency Test:</h2> </span>
					</div>
					<!-- <div class="hidecell"> -->
					<div style="background-color: #fbf9f4;">
						<table>
							<tr>
								<td><hh>Parameters</hh></td>
							</tr>
							<tr>
								<td><input type="radio" name="ConsRadio" value="all">
									All Unsatisfiable Classes <input type="radio" name="ConsRadio"
									value="root" checked> Only Root Classes</td>
							</tr>
							<tr>
								<td>Max Explanations <input type="number" value="5"
									name="maxExpl" min="0">
								</td>
							</tr>
							<tr>
								<td>
								<button class="btn small" type="submit" name="submit"
					value="DoRevise" onClick="processPlan()">Apply Repair Plan!</button>										
									</td>
							</tr>
						</table>
					</div>
				</div>
			</div>


			<!-- <input class="btn small" type="submit" name="DoRevise"
				value="Preview Effect of Repair Solution">-->
			<br> <br> Save your consistncy result <a
				href="${zipResultTxt}"> Download </a> <br> Your merged
			ontology: <a href="${MergedOntZip}"> Download </a> Your log file: <a
				href="${logFile}"> Download </a> <br> 
		 <br>
			<h4>References:</h4>
			<ul>
				<li><b>On Using Subjective Logic to Build Consistent Merged
						Ontologies</b>. Samira Babalou, Birgitta König-Ries. Proceedings of
					the SEMANTICS 2019 Poster and Demo Track.</li>
			</ul>
			<ul>
				<li><b>A Subjective Logic based Approach to Handling Inconsistencies in Ontology Merging</b>.
				Samira Babalou, Birgitta König-Ries.
				On the Move to Meaningful Internet Systems. OTM 2019 Conferences. Springer, Cham, 2019.
				</li>
			</ul>
			<div id="loadingmsg" style="display: none;">
				<img src="${pageContext.request.contextPath}/layout/images/load.gif"
					border="0" />
			</div>
			<div id="loadingover" style="display: none;"></div>
		</form>
	</div>
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
<script type="text/javascript" src="../js/consistencyResult.js"></script>


	<script>
		function ShowSection0() {
			var x = document.getElementById("section0");
			if (x.style.display === "none") {
				x.style.display = "block";
				document.getElementById("img0").src = '${pageContext.request.contextPath}/layout/images/down.png';
			} else {
				x.style.display = "none";
				document.getElementById("img0").src = '${pageContext.request.contextPath}/layout/images/right.png';
			}
		}
		function ShowSection1() {
			var x = document.getElementById("section1");
			if (x.style.display === "none") {
				x.style.display = "block";
				document.getElementById("img1").src = '${pageContext.request.contextPath}/layout/images/down.png';
			} else {
				x.style.display = "none";
				document.getElementById("img1").src = '${pageContext.request.contextPath}/layout/images/right.png';
			}
		}

		

		window.onload = function() {
			if ("${SecDW}" === "hide") {
				document.getElementById("idSecDW").style.display = "none";
			} else {
				document.getElementById("idSecDW").style.display = "block";
			}

			if ("${SecMapper}" === "show") {
				document.getElementById("idSecMapper").style.display = "block";
			} else {
				document.getElementById("idSecMapper").style.display = "none";
			}
			if ("${SecCompactness}" === "show") {
				document.getElementById("idSecCompactness").style.display = "block";
				document.getElementById("idSecCompactnessT").style.display = "block";
			} else {
				document.getElementById("idSecCompactness").style.display = "none";
				document.getElementById("idSecCompactnessT").style.display = "none";
			}
			if ("${SecCoverage}" === "show") {
				document.getElementById("idSecCoverage").style.display = "block";
				document.getElementById("idSecCoverageT").style.display = "block";
			} else {
				document.getElementById("idSecCoverage").style.display = "none";
				document.getElementById("idSecCoverageT").style.display = "none";
			}
			if ("${SecRedundancy}" === "show") {
				document.getElementById("idSecRedundancy").style.display = "block";
				document.getElementById("idSecRedundancyT").style.display = "block";
			} else {
				document.getElementById("idSecRedundancy").style.display = "none";
				document.getElementById("idSecRedundancyT").style.display = "none";
			}
			if ("${SecAccuracy}" === "show") {
				document.getElementById("idSecAccuracy").style.display = "block";
				document.getElementById("idSecAccuracyT").style.display = "block";
			} else {
				document.getElementById("idSecAccuracy").style.display = "none";
				document.getElementById("idSecAccuracyT").style.display = "none";
			}
			if ("${SecConsistency}" === "show") {
				document.getElementById("idSecConsistency").style.display = "block";
				document.getElementById("idSecConsistencyT").style.display = "block";
			} else {
				document.getElementById("idSecConsistency").style.display = "none";
				document.getElementById("idSecConsistencyT").style.display = "none";
			}
			if ("${SecReadability}" === "show") {
				document.getElementById("idSecReadability").style.display = "block";
				document.getElementById("idSecReadabilityT").style.display = "block";
			} else {
				document.getElementById("idSecReadability").style.display = "none";
				document.getElementById("idSecReadabilityT").style.display = "none";
			}
			if ("${SecReasoner}" === "show") {
				document.getElementById("idSecReasoner").style.display = "block";
				document.getElementById("idSecReasonerT").style.display = "block";
			} else {
				document.getElementById("idSecReasoner").style.display = "none";
				document.getElementById("idSecReasonerT").style.display = "none";
			}
		}
	</script>
</body>
</html>