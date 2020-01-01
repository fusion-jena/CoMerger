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
<title>Co-Merger | Compatibility</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="${pageContext.request.contextPath}/layout/styles/layout.css"
	rel="stylesheet" type="text/css" media="all">
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
							<li style="font-weight: bold;"><a
								href="${pageContext.request.contextPath}/GMR.jsp">GMRs
									Framework</a></li>
							<li><a
								href="${pageContext.request.contextPath}/queryCatalog.jsp">Query
									Catalogue</a></li>
							<li><a
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
		style="background-image: url('${pageContext.request.contextPath}/layout/images/01.png);">
		<div id="breadcrumb" class="hoc clear">
			<!-- ################################################################################################ -->
			<ul>
				<li><a href="#">Home</a></li>
				<li><a href="#">Merge</a></li>
				<li><a href="#">Compatibility</a></li>
			</ul>
			<!-- ################################################################################################ -->
		</div>
	</div>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<div class="wrapper row3">
		<main class="hoc container clear"> <!-- main body --> <!-- ################################################################################################ -->

		<!-- /////////////////////////////////////// --> <!-- ################################################################################################ -->
		<br>
		<h2>Compatibility between GMRs:</h2>
		<a href="${pageContext.request.contextPath}/requirement.jsp">show
			GMRs description</a> <br>
		<p>To determine the compatibility of GMRs together, we look for
			the scope of changes made by each GMR and the operation that they
			make. This brings four different cases. See the below:</p>
		<img src="${pageContext.request.contextPath}/layout/images/cases.png"
			alt="intraction of GMRs" style="width: 250px">
		<p>
			<b>We consider the following scopes:</b>
		</p>
		<ul>
			<li>Scope 1: classes; All classes in the merged ontology which
				is the union of the following sub-scopes:
				<ul>
					<li>Scope 1-1: only input ontologies' classes: this group
						classes only belong to the input ontologies' classes.</li>
					<li>Scope 1-2: redundant classes: this group are the classes
						origin from input ontologies which are repeated.</li>
					<li>Scope 1-3: extra classes: this group are some extra
						classes who do not belong to any input ontologies. They created
						trough the merge process.</li>
				</ul>
			</li>
			<li>Scope 2: properties; All properties in the merged ontology
				which is the union of the following sub-scopes:
				<ul>
					<li>Scope 2-1: only input ontologies' properties: this group
						properties only belong to the input ontologies' properties.</li>
					<li>Scope 2-2: redundant properties: this group are the
						properties origin from input ontologies which are repeated.</li>
					<li>Scope 2-3: extra properties: this group are some extra
						properties who do not belong to any input ontologies. They created
						trough the merge process.</li>
				</ul>
			</li>
			<li>Scope 3: instances; All instances in the merged ontology
				which is the union of the following sub-scopes:
				<ul>
					<li>Scope 3-1: only input ontologies' instances: this group
						instances only belong to the input ontologies' instances.</li>
					<li>Scope 3-2: redundant instances: this group are the
						instances origin from input ontologies which are repeated.</li>
					<li>Scope 3-3: extra instances: this group are some extra
						instances who do not belong to any input ontologies. They created
						trough the merge process.</li>
				</ul>
			</li>
		</ul>


		<p>
			<b>Scope and operation of each GMR:</b>
		</p>
		<ul>
			<li>R1: Scope 1-1, Operation: add; it adds missing input
				ontologies' classes.</li>
			<li>R2: Scope 2-1, Operation: add; it adds missing input
				ontologies' properties.</li>
			<li>R3: Scope 3-1, Operation: add; it adds missing input
				ontologies' instances-</li>
			<li>R4: Scope 1-1, 2-1, Operation: rewrite (delete + add); if
				two corresponding classes c1 and c2 from input ontologies are not
				mapped to the one class C' in the merged ontology, then two c1 and
				c2 first delete from the input ontologies, then add C'. Same as for
				corresponding properties. Indeed, R4 uses both add and delete
				operations to preserve the missing corresponding entities in the
				merged ontology.</li>
			<li>R5: Scope 2-1, Operation: add; R5 does some changes on the
				properties of the corresponding classes. R5 uses add operation for
				the missing properties of the corresponding classes. Indeed, for the
				corresponding classes, if their properties are missing in the merged
				ontology, it should be added.</li>
			<li>R6: Scope 2, Operation: add + delete; it requires to solve
				the conflict.</li>
			<li>R7: Scope 2-1, Operation: rewrite (delete + add); if any
				type of properties (such as is-a) from input ontologies are not
				represented in the same way in the merged ontology, the unmatched
				is-a should be deleted at first, then the input ontology's is-a
				should be added. It might add or delete some properties to preserve
				the same structure on the merged ontology w.r.t. the source
				ontologies.</li>
			<li>R8: Scope 1-2, Operation: delete; It deletes the redundant
				classes.</li>
			<li>R9: Scope 2-2, Operation: delete; It deletes the redundant
				properties.</li>
			<li>R10: Scope 3-2, Operation: delete; It deletes the redundant
				instances.</li>
			<li>R11: Scope 1-3, Scope 2-3, Scope 3-3, Operation: delete;
				delete the extra entities.</li>
			<li>R12: Scope 1-1, Scope 2-1, Scope 3-1, Operation: add and
				delete; It might add or delete some classes to achieve the
				entailments.</li>
			<li>R13: Scope 2-1, Operation: rewrite (add + delete); it
				requires to solve the conflict.</li>
			<li>R14: Scope 2-1, Operation: rewrite (add + delete); it
				requires to solve the conflict.</li>
			<li>R15: Scope 1-1, Operation: rewrite (add + delete); domain or
				range of properties are the type of classes, so it belongs to scope
				1-1. It might delete multiple domains or ranges (and only keep one
				of them to satisfy domain and range oneness). In another
				implementation, it might happen that some extra classes will be
				added to connect each multiple domains or range to them. For
				example, for property p with two domains c1 and c2, one solution
				would be: create a new class cc, where cc is the union of c1 and c2,
				and then set cc as the domain of property p.</li>
			<li>R16: Scope 1, Scope 2, Operation: add + delete; It might
				delete some classes to be cycle-free, where the scope of changes is
				all type of classes in the merged ontology (Scope1 = Scope 1-1,
				Scope 1-2, Scope 1-3).</li>
			<li>It might happen also that R16 do some changes also on the
				properties (delete is-a relationships to be cycle free). Scope 2 =
				Scope 2-1, Scope 2-2, Scope 2-3.</li>
			<li>R17: Scope 2, Operation: delete.</li>
			<li>R18: Scope 2, Operation: delete.</li>
			<li>R19: Scope 1, Scope 2, Operation: add + delete; It might
				change some classes or some properties (add is-a relationships to
				unconnected classes). It might delete the unconnected classes. It is
				also possible on different implementation, R19 uses add operation to
				add unconnected classes to the others.</li>
			<li>R20: Scope 2, Operation: add + delete; It makes some changes
				on the properties. It might delete the unconnected properties, or
				uses add operation to connect them.</li>
		</ul>


		<b>Based on the GMR detail of scope and operation, the summary of
			interaction between GMRs is depicted below:</b> <img
			src="${pageContext.request.contextPath}/layout/images/interaction.png"
			alt="intraction of GMRs" style="width: 250px"> <br>
	</div>
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