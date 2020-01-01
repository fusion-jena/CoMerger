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
<title>Co-Merger | Query Catalog</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="${pageContext.request.contextPath}/layout/styles/layout.css"
	rel="stylesheet" type="text/css" media="all">
<link href="${pageContext.request.contextPath}/dist/filepond.css"
	rel="stylesheet">
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
					<li class="active"><a class="drop"
						href="${pageContext.request.contextPath}/merging.jsp">Merger</a>
						<ul>
							<li><a
								href="${pageContext.request.contextPath}/requirement.jsp">Requirements</a></li>
							<li><a
								href="${pageContext.request.contextPath}/GMR.jsp">GMRs Framework</a></li>
							<li style="font-weight: bold;"><a
								href="${pageContext.request.contextPath}/queryCatalog.jsp">Query
									Catalogue</a></li>
									<li ><a
								href="${pageContext.request.contextPath}/cqCatalog.jsp">CQ
									Catalogue</a></li>
						</ul></li>
					<li><a
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
		<div class="inner">
			<form method="post"
				action="${pageContext.request.contextPath}/MergeServlet"
				enctype="multipart/form-data" onsubmit='showLoading();'>
				<div class="form_settings">
					<h4>List of query catalog</h4>
					Coming soon ...
					<span style="font-weight: bold; color: red; width: 100%;">${msg}</span>
				</div>


				<div class="form_settings">

					<div id="loadingmsg" style="display: none;">
						<img src="${pageContext.request.contextPath}/style/load.gif"
							border="0" />
					</div>
					<div id="loadingover" style="display: none;"></div>
				</div>

			</form>
		</div>
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