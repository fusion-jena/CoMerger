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
<title>Co-Merger | Merge Requirement</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="${pageContext.request.contextPath}/layout/styles/layout.css"
	rel="stylesheet" type="text/css" media="all">
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
			style="background-image: url('${pageContext.request.contextPath}/layout/images/01.png);">
			<div id="breadcrumb" class="hoc clear">
				<!-- ################################################################################################ -->
				<ul>
					<li><a href="#">Home</a></li>
					<li><a href="#">Merge</a></li>
					<li><a href="#">Requirements</a></li>
				</ul>
				<!-- ################################################################################################ -->
			</div>
		</div>
		<!-- ################################################################################################ -->
		<!-- ################################################################################################ -->
		<!-- ################################################################################################ -->
		<div class="wrapper row3">
			<main class="hoc container clear"> <!-- main body --> <!-- ################################################################################################ -->

			<!-- /////////////////////////////////////// --> <br>
			<input type="hidden" id="g-recaptcha-response"
				name="g-recaptcha-response"> <!-- /////////////////////////////////////// -->
			<!-- ################################################################################################ -->
			<div style="text-align: justify;">
				<h2>Standard for Evaluation</h2>
				<p>We drive a set of evaluation principles from [1,2,3] and
					consider them in designing our quality indicators. These works
					summarize as:
				<ul>
					<li>The work in [1] introduced four standards to provide a
						framework for determining a good evaluation for people and
						organizations involved in the evaluation society. The standards
						claim to substantiate the idea of a professional evaluation. The
						evaluation principles are (1) usefulness, (2) feasibility, (3)
						fairness, and (4) accuracy. Each principle is presented with a
						group of sub-statements. We consider all the mentioned principles
						and adapt the respective sub-statements. Note that some
						sub-statements cannot be aligned in our context, as they are
						related to human evaluation, such as human error checking.</li>
					<li>The work in [2] presented a criteria model for Semantic
						Web Services (SWS) evaluation. The model comprises five dimensions
						of evaluation: (1) performance/scalability, (2) usability/effort,
						(3) correctness, (4) coupling, and (5) supported scope and
						automation. We classify them as sub-statements within the
						introduced principles in [1] and adjust them in our context. We
						consider them as desirable sub-statements rather than as
						obligatory ones because they are introduced as criteria (not
						principle).</li>
					<li>The work in [3] established a conceptual framework for
						analyzing the characteristics of software quality. We adapt the
						respective characteristics as sub-statements within the introduced
						principles in [1].
						</p>

						<h2>Standards for the evaluation of the merged ontology:</h2>
						<p>
							Accordingly, we address the following principles and
							sub-statements for the quality evaluation of the merged ontology:
							<a href="/textdata/OMstandard.pdf"OMstandard.pdf">download</a>
						</p>
			</div>
			<br>
			<!-- /////////////////////////////////////// --> <!-- ################################################################################################ -->



			<!-- /////////////////////////////////////// --> <!-- ################################################################################################ -->

			<div style="text-align: justify;">
				<br> <span><h3>References:</h3> [1] E.
					Degeval-Gesellschaft Für Evaluation, <i>Standards für
						evaluation,</i> Die Deutsche Bibliothek - CIP, Einheitsaufnahme
					DeGEval-Gesellschaft für Evaluation e.V. Standards für Evaluation-
					ISBN 978-3-941569-06-5, 2016. <br> [2] U. Küster, B.
					König-Ries, and M. Klusch, <i>Evaluating semantic web service
						technologies: criteria, approaches and challenges,</i> in Progressive
					Concepts for Semantic Web Evolution: Applications and Developments,
					IGI Global, 2010. <br> [3] B. W. Boehm, J. R. Brown, and M.
					Lipow, <i>Quantitative evaluation of software quality,</i> in ICSE,
					1976. <br></span>
			</div>
			<div class="g-recaptcha"
				data-sitekey="6LfFuU4UAAAAAN-_yVxOMIBF955f-EwAI4EQmfBB"></div>


			<div id="loadingmsg" style="display: none;">
				<img src="${pageContext.request.contextPath}/layout/images/load.gif"
					border="0" />
			</div>
			<div id="loadingover" style="display: none;"></div>
	</form>
	<!-- /////////////////////////////////////// -->
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
			if ("${resultQuery}" === "" || "${resultQuery}" === null) {
				document.getElementById("resultQuery").style.display = "none";
				document.getElementById("resultLabel").style.display = "none";
			} else {
				document.getElementById("resultQuery").style.display = "block";
				document.getElementById("resultLabel").style.display = "block";
			}

		};
	</script>