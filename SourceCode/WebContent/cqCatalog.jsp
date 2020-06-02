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
					<li><a href="#">CQ catalog</a></li>
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
				<input type="hidden" id="g-recaptcha-response"
					name="g-recaptcha-response">
				<div class="form_settings">
					<h4>Competency Questions catalog</h4>
					<table style="width: 100%;">
						<tr>
							<th></th>
							<th>Conference Domain</th>
						</tr>
						<tr>
							<td colspan="2"><b>Positive CQs</b></td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ1</td>
							<td>Which type of documents can include in the conference
								document?</td>
						</tr>
						<tr>
							<td>CQ2</td>
							<td>Who can participate in the conference?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ3</td>
							<td>Can ''Reviewer'' be a type of person in the conference
								domain?</td>
						</tr>
						<tr>
							<td>CQ4</td>
							<td>What is the role of the conference participant at the
								conference?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ5</td>
							<td>Can a publisher be related to the conference domain?</td>
						</tr>
						<tr>
							<td>CQ6</td>
							<td>Who issues the conference proceeding?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ7</td>
							<td>Who assigns the external reviewer?</td>
						</tr>
						<tr>
							<td>CQ8</td>
							<td>Does conference proceedings can have an ISBN?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ9</td>
							<td>Can an author be a conference member?</td>
						</tr>
						<tr>
							<td>CQ10</td>
							<td>Which type of contribution exists in the conference
								domain?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ11</td>
							<td>Which type of fee exists?</td>
						</tr>
						<tr>
							<td>CQ12</td>
							<td>Which events can exist at the conference?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ13</td>
							<td>Which social events can a conference have?</td>
						</tr>
						<tr>
							<td>CQ14</td>
							<td>Which organization can be at the conference?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ15</td>
							<td>Who assigns the reviewer?</td>
						</tr>
						<tr>
							<td>CQ16</td>
							<td>Can a poster be part of the contribution to the
								conference?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ17</td>
							<td>Which type of paper can exist in the conference
								document?</td>
						</tr>
						<tr>
							<td>CQ18</td>
							<td>Can the tutorial be part of the conference?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ19</td>
							<td>Which countries can participate?</td>
						</tr>
						<tr>
							<td>CQ20</td>
							<td>What can be the decision for a submitted paper to the
								conference?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ21</td>
							<td>Who can be a conference member?</td>
						</tr>
						<tr>
							<td>CQ22</td>
							<td>Which type of conference committee exists?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ23</td>
							<td>Can information of the participants be part of the conference document?</td>
						</tr>
						<tr>
							<td>CQ24</td>
							<td>Can a workshop be part of the conference?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ25</td>
							<td>Can a short paper be a contribution at the conference?</td>
						</tr>
						<tr>
							<td>CQ26</td>
							<td>Which type of session exists at the conference?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ27</td>
							<td>Does the conference has a demo session?</td>
						</tr>
						<tr>
							<td>CQ28</td>
							<td>Which audiovisual_equipment conference has?</td>
						</tr>
						<tr style="background: #F0EFE2;">
							<td>CQ29</td>
							<td>Which type of deadline exists at the conference</td>
						</tr>
						<tr>
							<td>CQ30</td>
							<td>Which type of award can be given at the conference?</td>
						</tr>
						<tr>
							<td colspan="2"><b>Negative CQs</b></td>
						</tr>
							<tr style="background: #F0EFE2;">
							<td>CQ31</td>
							<td>How should discussions on the poster held?
							</td>
						</tr>
							<tr>
							<td>CQ32</td>
							<td>Is there any information regarding the past venue of the conference?
							</td>
						</tr>
							<tr style="background: #F0EFE2;">
							<td>CQ33</td>
							<td>is there any relation between an author and a company? (Can an author employ by a company?)
							</td>
						</tr>
							<tr>
							<td>CQ34</td>
							<td>Do authors have a CV and accessible during the conference presentation?
							</td>
						</tr>
							<tr style="background: #F0EFE2;">
							<td>CQ35</td>
							<td>Is there any visa information available for the participant?
							</td>
						</tr>
					</table>
					<span style="font-weight: bold; color: red; width: 100%;">${msg}</span>
				</div>

				<br> <br>
				<div id="cqRes">
				<hh>Competency questions on the OWL restrictions conflicts: </hh>
				<table style="width: 100%;">
					<tr>
						<th></th>
						<th>Conference Domain</th>
					</tr>
					<tr style="background: #F0EFE2;">
						<td>CQ1</td>
						<td>Does a Person have an email?</td>
					</tr>
					<tr>
						<td>CQ2</td>
						<td>Who can have an email?</td>
					</tr>
					<tr style="background: #F0EFE2;">
						<td>CQ3</td>
						<td>How many emails can a Person have?</td>
					</tr>
					<tr>
						<td>CQ4</td>
						<td>Does a Conference have a name?</td>
					</tr>
					<tr style="background: #F0EFE2;">
						<td>CQ5</td>
						<td>Who can have a name?</td>
					</tr>
					<tr>
						<td>CQ6</td>
						<td>How many names can a Conference have?</td>
					</tr>
					<tr style="background: #F0EFE2;">
						<td>CQ7</td>
						<td>Has a Review only a Reviewer as author?</td>
					</tr>
					<tr>
						<td>CQ8</td>
						<td>By whom is a Review written by?</td>
					</tr>
					<tr style="background: #F0EFE2;">
						<td>CQ9</td>
						<td>What can have a title?</td>
					</tr>
					<tr>
						<td>CQ10</td>
						<td>Which range does the title have?</td>
					</tr>
				</table>
			</div>
			
				<div class="form_settings">

					<div id="loadingmsg" style="display: none;">
						<img src="${pageContext.request.contextPath}/style/load.gif"
							border="0" />
					</div>
					<div id="loadingover" style="display: none;"></div>
				</div>
				<div class="g-recaptcha"
					data-sitekey="6LfFuU4UAAAAAN-_yVxOMIBF955f-EwAI4EQmfBB"></div>


				<div id="loadingmsg" style="display: none;">
					<img
						src="${pageContext.request.contextPath}/layout/images/load.gif"
						border="0" />
				</div>
				<div id="loadingover" style="display: none;"></div>
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