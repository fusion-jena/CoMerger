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
<title>Co-Merger | Merge Result</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="${pageContext.request.contextPath}/layout/styles/layout.css"
	rel="stylesheet" type="text/css" media="all">
<script src="http://d3js.org/d3.v3.min.js"></script>
<script
	src="http://labratrevenge.com/d3-tip/javascripts/d3.tip.v0.6.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/barChart.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/barChartNew.js"></script>
<script
	src="https://www.google.com/recaptcha/api.js?render=6Lc5rrUUAAAAAKBNdawrac6WLfCWmtTHiqWd4vCH"></script>
</head>
<body id="top" onload="initialise();">
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
					<li><a href="#">Merger</a></li>
					<li><a href="#">Merge Result</a></li>
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
				name="g-recaptcha-response"> <input type="hidden"
				name="action" value="validate_captcha"> <input type="hidden"
				name="selectedRepair" id="selectedRepair" /> <input type="hidden"
				name="selectedUserItem" id="selectedUserItem" /> <input
				type="hidden" name="SecGMR" id="SecGMR" /> <input type="hidden"
				name="SecEval" id="SecEval" /> <input type="hidden"
				name="selectedConsParam" id="selectedConsParam" /> <input
				name="uploadedInputFiles" id="uploadedInputFiles" type="text"
				value="${uploadedInputFiles}" style="display: none;"> <input
				name="uploadedmapFiles" id="uploadedmapFiles" type="text"
				value="${uploadedmapFiles}" style="display: none;"> <input
				name="uploadedMergeFile" id="uploadedMergeFile" type="text"
				value="${uploadedMergeFile}" style="display: none;"> <input type="hidden"
				name="showGMRSectionValue" id="showGMRSectionValue"
				value="${showGMRSectionValue}" />

			<div id="showMessage" class="messageSection">
				<span style="font-weight: bold; color: green; margin: 10px;">${msg}</span>
			</div>
			<section id="idSecDW" style="width: 100%;">

				<h2>Merging Result</h2>
				Your merged ontology: <a href="${MergedOntZip}"> Download </a> <br>
				The sub-merged ontologies: <a href="${MergedSubOntZip}">Download</a>
				<br> Save your evaluation result <a href="${zipResultTxt}">
					Download </a> <br>Your log file: <a href="${logFile}">
					Download </a> <input name="SavedPreferedOnt" id="SavedPreferedOnt"
					type="text" value="${SavedPreferedOnt}" style="display: none;">

			</section>
			<!-- ###################################################################################################################### -->
			<div id="SectionGMR_container">
				<div id="SectionGMR_Class" class="showcell">
					<h2>Adjust Refinement</h2>
				</div>

				<div id="SectionGMR" class="hidecell">
					<table class="myTable">
						<tr>
							<td>
								<h2>Selecting Generic Merge Requirements</h2>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<table class="myTable">

									<tr>
										<td align="left"><small><span align="left">${guidline}</span></small></td>
									</tr>
									<tr>
										<td align="right">Maximum number of suggested compatible sets:<input
											type="number" id="numSuggestion" name="numSuggestion" min="1"
											max="1000" value="${numberOfSuggestion}">
											<button class="btn small" type="submit" name="submit"
												value="DoCompatibilityCheckAfterMerge"
												onClick="readCheckedBoxes()">check Compatibility</button>
										</td>
									</tr>
									<tr>
										<td>${conflictRes}</td>
									</tr>
								</table>
							</td>
						</tr>

						<tr>
							<td align="left"><span onclick="SelectAllR()"
								style="cursor: pointer; width: 60px;"> <font size="1"><u>[Select
											All]</u></font></span><span onclick="ClearAllR()" style="cursor: pointer;"><font
									size="1"><u>[Clear All]</u></font></span> <a
								href="${pageContext.request.contextPath}/requirement.jsp">see
									rules description</a></td>
							<td><span> <input id="localRefinement"
									idname="localRefinement" name="selectedItem"
									value="localRefinement" type="checkbox"> <b>With
										Local Refinement</b></span></td>
						</tr>
						<tr>
							<td colspan="2"><hh>Completeness</hh></td>
						</tr>
						<tr>
							<td><input ${ClassCh} id="1" idname="ClassCheck"
								name="selectedItem" value="ClassCheck" type="checkbox">R1.
								All (target) Classes Preservation</td>
							<td><input ${ProCh} id="2" idname="ProCheck"
								name="selectedItem" value="ProCheck" type="checkbox">R2.
								All (target) Properties Preservation</td>
						</tr>
						<tr>
							<td><input ${InstanceCh} id="3" idname="InstanceCheck"
								name="selectedItem" value="InstanceCheck" type="checkbox">R3.
								All (target) Instances Preservation</td>

							<td><input ${CorresCh} id="4" idname="CorresCheck"
								name="selectedItem" value="CorresCheck" type="checkbox">R4.
								Correspondences Preservation</td>
						</tr>
						<tr>
							<td><input ${CorssPropCh} id="5" idname="CorssPropCheck"
								name="selectedItem" value="CorssPropCheck" type="checkbox">R5.
								Correspondences' Property Preservation</td>

							<td><input ${ValueCh} id="6" idname="ValueCheck"
								name="selectedItem" value="ValueCheck" type="checkbox">R6.
								Property's Value Preservation</td>
						</tr>
						<tr>
							<td colspan="2"><input ${StrCh} id="7" idname="StrCheck"
								name="selectedItem" value="StrCheck" type="checkbox">R7.
								Structure Preservation</td>
						</tr>
						<tr>
							<td colspan="2"><hh>Minimality</td>
						</tr>
						<tr>
							<td><input ${ClRedCh} id="8" idname="ClRedCheck"
								name="selectedItem" value="ClRedCheck" type="checkbox">R8.
								Class Redundancy Prohibition</td>
							<td><input ${ProRedCh} id="9" idname="ProRedCheck"
								name="selectedItem" value="ProRedCheck" type="checkbox">R9.
								Property Redundancy Prohibition</td>
						</tr>
						<tr>
							<td><input ${InstRedCh} id="10" idname="InstRedCheck"
								name="selectedItem" value="InstRedCheck" type="checkbox">R10.
								Instance Redundancy Prohibition</td>
							<td><input ${ExtCh} id="11" idname="ExtCheck"
								name="selectedItem" value="ExtCheck" type="checkbox">R11.
								Extraneous Entity Prohibition</td>
						</tr>
						<tr>
							<td colspan="2"><hh>Deduction</td>
						</tr>
						<tr>
							<td colspan="2"><input ${EntCh} id="12" idname="EntCheck"
								name="selectedItem" value="EntCheck" type="checkbox">R12.
								Entailment Deduction Satisfaction</td>
						</tr>
						<tr>
							<td colspan="2"><hh>Constraint</td>
						</tr>

						<tr>
							<td><input ${TypeCh} id="13" idname="TypeCheck"
								name="selectedItem" value="TypeCheck" type="checkbox">R13.
								One Type Restriction</td>
							<td><input ${ConstValCh} id="14" idname="ConstValCheck"
								name="selectedItem" value="ConstValCheck" type="checkbox">R14.
								Property's Value Constraint</td>
						</tr>
						<tr>
							<td colspan="2"><input ${DomRangMinCh} id="15"
								idname="DomRangMinCheck" name="selectedItem"
								value="DomRangMinCheck" type="checkbox">R15. Property's
								Domain and Range Oneness</td>
						</tr>
						<tr>
							<td colspan="2"><hh>Acyclicity</td>
						</tr>
						<tr>
							<td><input ${AcyClCh} id="16" idname="AcyClCheck"
								name="selectedItem" value="AcyClCheck" type="checkbox">R16.
								Acyclicity in the Class Hierarchy</td>
							<td><input ${AcyProCh} id="17" idname="AcyProCheck"
								name="selectedItem" value="AcyProCheck" type="checkbox">R17.
								Acyclicity in the Property Hierarchy</td>
						</tr>
						<tr>
							<td colspan="2"><input ${RecProCh} id="18"
								idname="RecProCheck" name="selectedItem" value="RecProCheck"
								type="checkbox">R18. Prohibition of Properties being
								Inverses of Themselves</td>
						</tr>
						<tr>
							<td colspan="2"><hh>Connectivity</td>
						</tr>
						<tr>
							<td><input ${UnconnClCh} id="19" idname="UnconnClCheck"
								name="selectedItem" value="UnconnClCheck" type="checkbox">R19.
								Unconnected Class Prohibition</td>
							<td><input ${UnconnProCh} id="20" idname="UnconnProCheck"
								name="selectedItem" value="UnconnProCheck" type="checkbox">R20.
								Unconnected Properties Prohibition</td>
						</tr>
					</table>
					<table>
						<tr>
							<td>If you want to evaluate the merged ontology after
								refine, please select your desire evaluation criteria from the
								evaluation section <b>before press the Refine button</b>
							</td>
						</tr>
						<tr>
							<td>
								<div align="center">
									<button class="btn medium" type="submit" name="submit"
										value="DoRefine" onClick="readCheckedBoxes()">Refine</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<!-- ###################################################################################################################### -->
			<br>
			<div id="SectionEval_container">
				<div class="showcell">
					<h2>Apply Evaluation</h2>
				</div>
				<div id="SectionEval" class="hidecell">

					<table class="myTable">
						<tr>
							<td align="center" colspan="3"><span onclick="SelectAll()"
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
								Completeness &emsp;</td>
							<td><input ${MinimalityCh} id="MinimalityCheck"
								name="selectedItem" value="MinimalityCheck" type="checkbox">
								Minimality &emsp;</td>
							<td><input ${DeductionCh} id="DeductionCheck"
								name="selectedItem" value="DeductionCheck" type="checkbox">
								Deduction&emsp;</td>
						</tr>
						<tr>
							<td><input ${ConstraintCh} id="ConstraintCheck"
								name="selectedItem" value="ConstraintCheck" type="checkbox">
								Constraint &emsp;</td>
							<td><input ${AcyclicityCh} id="AcyclicityCheck"
								name="selectedItem" value="AcyclicityCheck" type="checkbox">
								Acyclicity &emsp;</td>
							<td><input ${ConnectivityCh} id="ConnectivityCheck"
								name="selectedItem" value="ConnectivityCheck" type="checkbox">
								Connectivity&emsp;</td>
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
							<td colspan="2"><input ${UsabilityProfileCh}
								id="UsabilityProfileCheck" name="selectedItem"
								value="UsabilityProfileCheck" type="checkbox"> Usability
								Profile &emsp;</td>
						</tr>
					</table>
					<table>
						<tr>
							<td>
								<div align="center">
									<button class="btn medium" type="submit" name="submit"
										value="DoEvalAfterMerge" onClick="readCheckedBoxes()">Evaluate</button>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<!-- ########################################################################################## -->
			<br>
			<div id="idSecConsistencyTest" class="showcell">
				<h2>Consistency Test</h2>
			</div>
			<div class="hidecell">
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
							<td align="center"><button class="btn medium" type="submit"
									name="submit" value="DoConsistencyChecker"
									onClick="readConsParam()">Test consistency!</button></td>
						</tr>
						<tr>
							<td><br></td>
						</tr>
					</table>
				</div>
			</div>
			<!-- ########################################################################################## -->
			<br>
			<div class="showcell">
				<h2>Query Test</h2>
			</div>
			<div class="hidecell">
				<br>
				<div align="center">
					<br>
					<button class="btn medium" type="submit" name="submit"
						value="GoQueryOne">Test with single query!</button>
					<button class="btn medium" type="submit" name="submit"
						value="GoQuerySeveral">Test with several queries!</button>
					<br>
				</div>
			</div>
			<!-- ###################################################################################################################### -->
			<!-- ////////////////////*BAR CHART*/////////////////// -->
			<h2>Evaluation Result</h2>
			<hh>Overall Result Demonstration </hh>
			<div id="barChartNew"></div>
			<div id="SecEvalResult">
				<!-- //////////////////idSecCompleteness///////////////////// -->
				${res0}
				<!-- //////////////////idSecMinimality///////////////////// -->
				${res1}
				<!-- ///////////////////idSecDeduction//////////////////// -->
				${res2}
				<!-- ///////////////////idSecConstraint//////////////////// -->
				${res3}
				<!-- ///////////////////idSecAcyclicity//////////////////// -->
				${res4}
				<!-- ///////////////////idSecConnectivity//////////////////// -->
				${res5}
				<!-- ///////////////////idSecUsabilityProfile//////////////////// -->
				${res6}
				<!-- //////////////////idSecMapper///////////////////// -->
				${res7}
				<!-- //////////////////idSecCompactness///////////////////// -->
				${res8}
				<!-- ////////////////////idSecCoverage/////////////////// -->
				${res9}

				<!-- /////////////////////////////////////// -->
			</div>

			<!-- end of merging evaluation result -->
			<div>
				<br> <br> <br> Repair selected errors (if there is
				any)
				<button class="btn small" type="submit" name="submit"
					value="DoEvalRepairErrors" onClick="processRepairPlan()">Repair
					Errors</button>
			</div>
			<br>
			<br>
			<span><br> <sup>-</sup> means it is not implemented yet
				within the system</span> <br>
			<br>
			<div id="SecReference" style="font-size: 12px;">
				<span><hh>References:</hh> <br>[1] F. Duchateau, and Z.
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
					[11] A. Rector, N. Drummond, M. Horridge, J. Rogers, H. Knublauch,
					R. Stevens, H. Wang, and C. Wroe. Owl pizzas: Practical experience
					of teaching owl-dl: Common errors & common patterns. In EKAW, pages
					63-81. Springer, 2004. <br> [12] A. Algergawy, S. Babalou, and
					B. König-Ries. A new metric to evaluate ontology modularization. In
					ESWC, 2016. [13] S. Tartir, I. B. Arpinar, M. Moore, A. P. Sheth,
					and B. Aleman-Meza. Ontoqa: Metric-based ontology quality analysis.
					KADASH, 2005. <br> [14] A. Hogan, A. Harth, A. Passant, S.
					Decker, and A. Polleres. Weaving the pedantic web. LDOW, 628, 2010.
					<br> </span>
			</div>

			<div id="loadingmsg" style="display: none;">
				<img src="${pageContext.request.contextPath}/layout/images/load.gif"
					border="0" />
			</div>
			<div id="loadingover" style="display: none;"></div>
			<div class="g-recaptcha"
				data-sitekey="6LfFuU4UAAAAAN-_yVxOMIBF955f-EwAI4EQmfBB"></div>
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

	<script type="text/javascript" src="../js/mergeResult.js"></script>
	
	<script>
		function initialise() {
			var info = "${chartInfo}";
			var res = info.split(";");
			var data = [ {
				"date" : "Completeness",
				"value" : res[0]
			}, {
				"date" : "Minimality",
				"value" : res[1]
			}, {
				"date" : "Deduction",
				"value" : res[2]
			}, {
				"date" : "Constraint",
				"value" : res[3]
			}, {
				"date" : "Acyclicity",
				"value" : res[4]
			}, {
				"date" : "Connectivity",
				"value" : res[5]
			}, {
				"date" : "Usability",
				"value" : res[6]
			} ];
			showBarChartNew(data);
			if ("${msg}" === "") {
				document.getElementById("showMessage").style.display = "none";
			}
			
			var showGMRSection = document.getElementById('showGMRSectionValue').value;
			if (showGMRSection === "")
				document.getElementById('SectionGMR_Class').setAttribute(
						"class", "showcell");
			if (showGMRSection) {
				document.getElementById('SectionGMR_Class').setAttribute(
						"class", "showcell active");
				document.getElementById('SectionGMR_Class').style.display = "block";
				document.getElementById('SectionGMR').style.display = "block";
			} else {
				document.getElementById('SectionGMR_Class').setAttribute(
						"class", "showcell");
			}
		}
	</script>
</body>
</html>