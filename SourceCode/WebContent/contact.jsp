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
<title>Co-Merger | Contact Us</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="${pageContext.request.contextPath}/layout/styles/layout.css"
	rel="stylesheet" type="text/css" media="all">
<!-- Filepond stylesheet -->
<!-- <link href="https://unpkg.com/filepond/dist/filepond.css" rel="stylesheet"> -->
<link href="${pageContext.request.contextPath}/dist/filepond.css"
	rel="stylesheet">
<!-- <script src="${pageContext.request.contextPath}/dist/filepond.min.js"></script>
<script src="${pageContext.request.contextPath}/dist/filepond.jquery.js"></script>-->
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
						<li class="active"><a
							href="${pageContext.request.contextPath}/contact.jsp">Contact
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
					<li><a href="#">Contact Us</a></li>
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
			<table>
				<tr>
					<td style="width: 60%;">
						<h2>Get in touch</h2>
						<p>Write your feedback for us...</p>
				</tr>
				<tr>
					<td>
						<h2>Email</h2> <b>tool.comerger[at]gmail[dot]com</b>
					</td>
				</tr>
				<tr>
					<td style="width: 40%;">
						<h2>Address</h2> Friedrich-Schiller-University Jena <br />
						Institute of Computer Science <br /> Heinz-Nixdorf-Professur for
						Distributed Information Systems <br /> Ernst-Abbe-Platz 1-4 <br />
						D-07743 Jena <br />

					</td>
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