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
<title>Co-Merger | User Help</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="${pageContext.request.contextPath}/layout/styles/layout.css"
	rel="stylesheet" type="text/css" media="all">
<!-- Filepond stylesheet -->
<!-- <link href="https://unpkg.com/filepond/dist/filepond.css" rel="stylesheet"> -->
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
					<li><a href="#">User Help</a></li>
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
				name="g-recaptcha-response">
			<table id="queryOutput" class="myTable">
				<tr>
					<td><h2>User Help</h2></td>
				</tr>
				<tr>
					<td><b>Get the tutorial of the tool in </b><a
						href="/textdata/UserHelp.pdf"UserHelp.pdf">PDF</a></td>
				</tr>
				<tr>
					<td><br></td>
				</tr>
				<tr>
					<td><hh>More Information</hh></td>
				</tr>
				<tr>
					<td>Watch the demo of the tool, to learn more about the tool's
						functionality: <a href="/textdata/demo.mp4"demo.mp4">download</a>
					</td>
				</tr>
				<tr>
					<td>Get the source code and see the tutorial for a local run
						from <a href="https://github.com/fusion-jena/CoMerger">GitHub</a>
					</td>
				</tr>
				<tr>
					<td>Download a sample test ontology or their ready mappings
						from <a
						href="https://github.com/fusion-jena/CoMerger/tree/master/Ontology_Merging_Datasets/d1">here</a>
					</td>
				</tr>
				<tr>
					<td>Visit <a
						href="${pageContext.request.contextPath}/requirement.jsp">Requirements</a>,
						<a href="${pageContext.request.contextPath}/GMR.jsp">GRMs
							framework</a>, <a
						href="${pageContext.request.contextPath}/queryCatalog.jsp">Query
							Catalogue</a>, <a
						href="${pageContext.request.contextPath}/cqCatalog.jsp">CQ
							Catalogue </a> pages for more information.
					</td>
				</tr>
				<tr>
					<td>Check the <a
						href="${pageContext.request.contextPath}/aboutUs.jsp">publication</a>
						of the tool to get familiar with the underlying methodology
					</td>
				</tr>
				<tr>
					<td><br></td>
				</tr>
				<tr>
					<td><hh>Want to help:</hh> <br>Report an error or suggest
						a new feature by contacting us.</td>
				</tr>
				<tr>
					<td><hh>Need more help?</hh> <br>Contact us: <br>Email:
						<b>tool.comerger[at]gmail[dot]com</b></td>
				</tr>
			</table>
			<div class="g-recaptcha"
				data-sitekey="6LfFuU4UAAAAAN-_yVxOMIBF955f-EwAI4EQmfBB"></div>


			<div id="loadingmsg" style="display: none;">
				<img src="${pageContext.request.contextPath}/layout/images/load.gif"
					border="0" />
			</div>
			<div id="loadingover" style="display: none;"></div>
	</form>
	</main>
	</div>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<%@include file="../shared/footer.jsp"%>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->

</body>
</html>