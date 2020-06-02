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
				name="g-recaptcha-response">
			<h2>Merge Requirements Catalogue</h2>
			how to <a href="${pageContext.request.contextPath}/GMR.jsp">select
				a set of homogeneous requirements </a> <br>
			GMR Implementation: <a href="/textdata/EvaluationDetail.pdf"EvaluationDetail.pdf">download
				the documentation</a> <!-- /////////////////////////////////////// --> <!-- ################################################################################################ -->
			<div style="text-align: justify;">
				<h2>GMR: Generic Merge Requirement</h2>
				<p>Ontology merging approaches differ in the set of criteria
					that they aim to fulfill, i.e., what are the requirements they
					expect the merged ontology to meet. A classification and a list of
					Generic Merge Requirement (GMR) extracts from the literature is
					presented here.</p>
				<h2>GMR Classification:</h2>
				<p>This is advisable to classify GMRs into some dimensions as
					they refer to different ontology perspectives. As a result, it
					allows users or systems with an interest in a given aspect easily
					identify the group of GMRs in which they might be interested.</p>

				<ul>
					<li><b><font color="#0415AD">Integrity: </font> </b>
						<p>This dimension refers to the degree of knowledge coverage
							in the merge process via (i) completeness aspect, and to the
							amount of knowledge redundancy by (ii) minimality aspect.</p></li>

					<li><b><font color="#0415AD">Model Properties: </font> </b>
						<p>Within this dimension, the principles of creating a new
							build ontology model are investigated. In this dimension, (i)
							acyclicity, and (ii) connectivity satisfaction aspects are taken
							into account.</p></li>

					<li><b><font color="#0415AD">Logic Properties: </font> </b>
						<p>The inference of the expected knowledge with involved
							constraints is analyzed in this dimension. This includes (i)
							deduction, and (ii) constraints satisfaction aspect.</p></li>
				</ul>
			</div>
			<br>
			<!-- /////////////////////////////////////// --> <!-- ################################################################################################ -->

			<img src="${pageContext.request.contextPath}/layout/images/dim.png"
				alt="GMRs classification" style="width: 90%; margin: 50px;"> <br>
			<div style="text-align: justify;">
				<ul>
					<h2>Completeness:</h2>
					<p>This aspect refers to knowledge preservation and coverage.
						It includes following GMRs:</p>
					<li><b><font color="#0415AD">R1-</font>Class preservation</b>
						<p>Each class in the (all/target) input ontologies should have
							a mapped class in the merged ontology [1,3,6,7,10].</p></li>
					<li><b><font color="#0415AD">R2-</font> Property
							preservation </b>
						<p>Each property from the (all/target) input ontologies is
							explicitly in or implied by the merged ontology [7,10].</p></li>
					<li><b><font color="#0415AD">R3-</font> Instance
							preservation </b>
						<p>All instances of (all/target) input ontologies should be
							preserved in the merged ontology [7,10].</p></li>
					<li><b><font color="#0415AD">R4-</font> Correspondences
							preservation </b>
						<p>If two entities of the input ontologies are corresponding,
							both should map to the same merged entity in the merged ontology
							[6,7,10]. Note that, corresponding can be as equality, similarity
							or is-a correspondences. In each case, the same type of alignment
							should be preserved.</p></li>
					<li><b><font color="#0415AD">R5-</font> Correspondences'
							property preservation </b>
						<p>If any of the corresponding entities from the input
							ontologies has a certain property, the merged entity should also
							have this property [6,7].</p></li>
					<li><b><font color="#0415AD">R6-</font> Property's value
							preservation </b>
						<p>Properties' values from the (all/target) input ontologies
							should be preserved in the merged ontology [6,7]. In case of
							conflicts a resolution strategy is required.</p></li>
					<li><b><font color="#0415AD">R7-</font> Structure
							preservation </b>
						<p>If two entities are connected via a certain property in an
							input ontology, their mapped entities in the merged ontology
							should be connected via the respective mapped property [1], thus
							preserving the input ontologies' structures in the merged
							ontology.</p></li>
					<hr>
					<h2>Minimality</h2>
					<p>This aspect refers to the knowledge redundancy and
						controlling of semantic overlap, in the following GMRs:</p>
					<li><b><font color="#0415AD">R8-</font> Class redundancy
							prohibition </b>
						<p>A class from the (all/target) input ontologies should have
							at most one mapping in the merged ontology [1,3,4,7,10].</p></li>
					<li><b><font color="#0415AD">R9-</font> Property
							redundancy prohibition </b>
						<p>A property from the (all/target) input ontologies should
							have at most one mapping in the merged ontology [4].</p></li>
					<li><b><font color="#0415AD">R10-</font> Instance
							redundancy prohibition </b>
						<p>An instance from the (all/target) input ontologies should
							have at most one mapping in the merged ontology.</p></li>
					<li><b><font color="#0415AD">R11-</font> Extraneous entity
							prohibition </b>
						<p>No additional entities other than input ontologies'
							entities should be added in the merged result [7].</p></li>
					<hr>
					<h2>Deduction</h2>
					<p>This aspect refers to the deduction satisfaction in the
						merged ontology with the R12:</p>
					<li><b><font color="#0415AD">R12-</font> Entailments
							deduction satisfaction </b>
						<p>The merged ontology should be able to entail all
							entailments of (all/target) input ontologies. As the semantic
							consequences of the integration, it can include more entailments
							but it should at least not miss knowledge from the input
							ontologies [2]. (for the definition of the entail, cf. [2])</p></li>
					<hr>
					<h2>Constraints</h2>
					<p>This aspect reflects the satisfaction of the ontology
						constraint with the following GMRs:</p>
					<li><b><font color="#0415AD">R13-</font> One type
							restriction </b>
						<p>Two corresponding entities should follow the same data type
							[7]; e.g., if the range of ``authorId'' in one of the input
							ontology is String and in the other one is Integer, then the
							range of the merged entity ``authorId'' in the merged ontology
							cannot have both types.</p></li>
					<li><b><font color="#0415AD">R14-</font> Property value's
							constraint </b>
						<p>If the (all/target) input ontologies place some restriction
							on a property's values (e.g., in terms of cardinality or by
							enumerating possible values) this should be preserved without
							conflict in the merged ontology [7].</p></li>
					<li><b><font color="#0415AD">R15-</font> Property's domain
							and range oneness</b>
						<p>The merge process should not result in multiple domains or
							ranges defined for a single property. This rule is recast from
							the ontology modelling issues in [8].</p></li>
					<hr>
					<h2>Acyclicity</h2>
					<p>This aspect refers to the controlling of the chain problem
						in the merged ontology, which includes following GMRs:</p>
					<li><b><font color="#0415AD">R16-</font> Acyclicity in the
							class hierarchy </b>
						<p>A cycle of is-a relationships implies equality of all of
							the classes in the cycle, since is-a is transitive. Therefore,
							the merge process should not produce a cycle in the class
							hierarchy [1,3,5,7,8,10].</p></li>
					<li><b><font color="#0415AD">R17-</font> Acyclicity in the
							property hierarchy</b>
						<p>The merge process should not produce a cycle between
							properties with respect to the is-subproperty-of relationship
							[8].</p></li>
					<li><b><font color="#0415AD">R18-</font> Prohibition of
							properties being inverses of themselves </b>
						<p>The merged process should not cause an inverse recursive
							definition on the properties [8].</p></li>
					<hr>
					<h2>Connectivity</h2>
					<p>This aspect refers to the hierarchy connection satisfaction,
						with the following GMRs:</p>
					<li><b><font color="#0415AD">R19-</font> Unconnected class
							prohibition </b>
						<p>The merge process should not make the classes unconnected
							[3,7]. Every class that had some connections in the input
							ontologies before the merge process, should not be unconnected
							after merge process in the merged ontology.</p></li>
					<li><b><font color="#0415AD">R20-</font> Unconnected
							property prohibition </b>
						<p>The merge process should not make the properties
							unconnected [6,8]. Every property that had some connections in
							the input ontologies before the merge process, should not be
							unconnected after merge process in the merged ontology.</p></li>
				</ul>
			</div>


			<!-- /////////////////////////////////////// --> <!-- ################################################################################################ -->

			<div style="text-align: justify;">
				<br> <span><h3>References:</h3> [1] F. Duchateau, and Z.
					Bellahsene. <i>Measuring the quality of an integrated schema.</i>
					COER 2010.<br> [2] E.Jiménez-Ruiz, et al.<i>Ontology
						integration using mappings: Towards getting the right logical
						consequences.</i> ESWC, 2009. <br> [3] S. P. Ju, H. E. Esquivel,
					et al. <i>Creado-a methodology to create domain ontologies
						using parameter-based ontology merging techniques.</i> In MICAI, 2011.
					<br> [4] M. Mahfoudh, et al. <i>A benchmark for ontologies
						merging assessment.</i>KSEM 2016. <br> [5] N. F. Noy, et al. <i>Ontology
						development 101: A guide to creating your first ontology. </i> 2001.<br>
					[6] N. F. Noy and M. A. Musen. <i>The prompt suite: interactive
						tools for ontology merging and mapping.</i> IJHCS, 59(6):983-1024,
					2003. <br> [7] R. A. Pottinger and P. A. Bernstein. <i>Merging
						models based on given correspondences.</i> In VLDB, pages 862-873,
					2003. <br> [8] M. Poveda-Villalón, A. Gómez-Pérez, and M.
					C.Suárez-Figueroa. <i>Oops!(ontology pitfall scanner!): An
						on-line tool for ontology evaluation.</i> IJSWIS, 2014. <br> [9]
					S. Raunich and E. Rahm. <i>Towards a benchmark for ontology
						merging. </i> In OTM, 2012. [10] S. Raunich and E. Rahm. <i>Target-driven
						merging of taxonomies with atom.</i> Inf. Syst., 42:1-14, 2014. <br>
				</span>
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