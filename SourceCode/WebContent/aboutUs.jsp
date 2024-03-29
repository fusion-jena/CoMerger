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
<title>Co-Merger | About Us</title>
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
						<li class="active"><a
							href="${pageContext.request.contextPath}/aboutUs.jsp">About
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
									href="${pageContext.request.contextPath}/OMstandard.jsp">Standard
										for Evaluation</a></li>
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
					<li><a href="#">About Us</a></li>
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
			<table class="form_settings">
				<tr>
					<td style="width: 100%"><table>
							<tr>
								<td style="width: 25%"><img
									src="${pageContext.request.contextPath}/layout/images/sa.jpg"
									alt="Samira Babalou" style="width: 250px"></td>
								<td style="width: 75%">
									<table>
										<tr>
											<td style="text-align: justify;"><h2>Samira Babalou</h2>
												<h4>Ph.D. student</h4> Samira Babalou is a current Ph.D.
												student at the Heinz-Nixdorf Chair for Distributed
												Information Systems in the computer science institute at
												Friedrich-Schiller-University Jena. <br> Her
												interesting research topics include semantic web, ontology
												engineering, and data integration. <a
												href="http://fusion.cs.uni-jena.de/fusion/members/samira-babalou/">
													Read more ... </a></td>
										</tr>
										<tr>
											<td>Email: samira.babalou[-at-]uni-jena.de</td>
										</tr>
										<tr>
											<td>Address: Leutragraben 1, Jentower, R17N01, 07743
												Jena, Germany</td>
										</tr>
									</table>
								</td>
						</table></td>
				</tr>
				<tr>
					<td style="width: 100%"><table>
							<tr>
								<td style="width: 25%"><img
									src="${pageContext.request.contextPath}/layout/images/el.jpg"
									alt="Elena Grygorova" style="width: 250px"></td>
								<td style="width: 75%">
									<table>
										<tr>
											<td style="text-align: justify;"><h2>Elena
													Grygorova</h2>
												<h4>Master student</h4> Elena Grygorova is a master student
												in computer science at Friedrich-Schiller-University Jena.
												She currently writes her project work in the Semantic Web
												domain at the Heinz-Nixdorf Chair for Distributed
												Information Systems.</td>
										</tr>
										<tr>
											<td>Email: elena.grygorova [-at-]uni-jena.de</td>
										</tr>
										<tr>
											<td>Address: Leutragraben 1, Jentower, R17N01, 07743
												Jena, Germany</td>
										</tr>
									</table>
								</td>
						</table></td>
				</tr>
				<tr>
					<td style="width: 100%"><table>
							<tr>
								<td style="width: 25%"><img
									src="${pageContext.request.contextPath}/layout/images/br.PNG"
									alt="Prof. Dr. Birgitta K�nig-Rie" style="width: 250px"></td>
								<td style="width: 75%">
									<table>
										<tr>
											<td style="text-align: justify;"><h2>Prof. Dr.
													Birgitta K�nig-Ries</h2>
												<h4>Professor</h4> Birgitta K�nig-Ries holds the
												Heinz-Nixdorf Chair for Distributed Information Systems at
												Friedrich-Schiller-University Jena. Before joining FSU, she
												was working with the TU Munich, Universit�t Karlsruhe (this
												is where she obtained both her PhD and her diploma degrees),
												Florida International University, and the University of
												Louisiana at Lafayette. <br> Her main research area
												includes: portal technology, context-aware, adaptive
												portals, semantic web, semantic web services, and data
												management for (ecological) research. <a
												href="http://fusion.cs.uni-jena.de/fusion/members/birgitta-konig-ries/">
													Read more ... </a></td>
										</tr>
										<tr>
											<td>Email: birgitta.koenig-ries[-at-]uni-jena.de</td>
										</tr>
										<tr>
											<td>Address: Ernst-Abbe-Platz 2, Room: 3238, 07743 Jena,
												Germany</td>
										</tr>
									</table>
								</td>
						</table></td>
				</tr>
			</table>
			<div id="publication" name="publication">
				<h2>Publication</h2>
				<ul>
					<li><b>A Subjective Logic based Approach to Handling
							Inconsistencies in Ontology Merging</b>. Samira Babalou, Birgitta
						K�nig-Ries. In OTM Confederated International Conferences On the
						Move to Meaningful Internet Systems. Springer, Cham, 2019</li>
					<li><b>On Using Subjective Logic to Build Consistent
							Merged Ontologies</b>. Samira Babalou, Birgitta K�nig-Ries.
						Proceedings of the SEMANTICS 2019 Poster and Demo Track.</li>
					<li><b>GMRs: Reconciliation of Generic Merge Requirements
							in Ontology Integration</b>. Samira Babalou, Birgitta K�nig-Ries.
						Proceedings of the SEMANTICS 2019 Poster and Demo Track.</li>
					<li><b>Holistic Multiple Ontologies Merging</b>. Samira
						Babalou. Proceedings of the EKAW Doctoral Consortium 2018
						co-located with the 21st International Conference on Knowledge
						Engineering and Knowledge Management (EKAW 2018).</li>
					<li><b>Why the mapping process in ontology integration
							deserves attention</b>. Samira Babalou, Alsayed Algergawy, Birger
						Lantow, Birgitta K�nig-Ries. Proceedings of the 19th International
						Conference on Information Integration and Web-based Applications &
						Services</li>
					<li><b>An Ontology-based Scientific Data Integration
							Workflow</b>. Samira Babalou, Alsayed Algergawy, Birgitta K�nig-Ries.
						29th GI-Workshop Grundlagen von Datenbanken, 2017</li>
					<li><b>A Particle Swarm-Based Approach for Semantic
							Similarity Computation</b>. Samira Babalou, Alsayed Algergawy,
						Birgitta K�nig-Ries. In OTM Confederated International Conferences
						On the Move to Meaningful Internet Systems (pp. 161-179).
						Springer, Cham. 2017.
				</ul>
			</div>
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