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
<title>Co-Merger | Merging</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="${pageContext.request.contextPath}/layout/styles/layout.css"
	rel="stylesheet" type="text/css" media="all">
<link href="${pageContext.request.contextPath}/dist/filepond.css"
	rel="stylesheet">

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
					<li class="active"><a class="drop"
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
				<li><a href="#">Merger</a></li>
			</ul>
			<!-- ################################################################################################ -->
		</div>
	</div>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<div class="wrapper row3">
		<main class="hoc container clear"> <!-- main body --> <!-- ################################################################################################ -->
		<div id="showMessage" class="messageSection">
			<span style="font-weight: bold; color: red; margin: 10px;">${msg}</span>
			<span style="margin: 10px;" id="msgOldFile">Using your last
				uploaded file if you do not enter a new file</span> <br>
		</div>
		<h1>Ontology Merging</h1>
		<form method="post"
			action="${pageContext.request.contextPath}/MergingServlet"
			enctype="multipart/form-data" onsubmit='showLoading();'>

			<input type="hidden" id="g-recaptcha-response"
				name="g-recaptcha-response"> <input type="hidden"
				name="action" value="validate_captcha"> <input
				name="uploadedInputFiles" id="uploadedInputFiles" type="text"
				value="${uploadedInputFiles}" style="display: none;"> <input
				name="uploadedmapFiles" id="uploadedmapFiles" type="text"
				value="${uploadedmapFiles}" style="display: none;"> <input
				name="msg" id="msg" type="text" value="${msg}"
				style="display: none;"> <input type="hidden"
				name="selectedUserItem" id="selectedUserItem" /> <input
				type="hidden" name="SecGMR" id="SecGMR" /> <input type="hidden"
				name="SecEval" id="SecEval" />

			<table class="myTable">
				<tr>
					<td><h2>Select input ontologies</h2> Your preferred ontology
						(null means equal): <input id="PreferedOnt" name="PreferedOnt"
						style="width: 20%; display: inline-block" value="${NumPrefOnt}"
						title="Index of your prefered ontology" type="text"> <!-- type="number" step="1"
						min="0" --></td>
					<td><h2>Select their mapping files:</h2> <label
						style="color: gray;">If you do not enter an alignment
							file, it will be generated automatically. </label></td>
				</tr>
				<tr>
					<td><input name="inputFiles" type="file" multiple="multiple"
						class="filepond"></td>
					<td><input name="mapFiles" type="file" multiple="multiple"
						class="filepond"></td>
				</tr>
			</table>
			<table class="myTable">
				<tr>
					<td><h2>Selecting Measures</h2></td>
				</tr>
				<tr>
					<td>Type of Merge: <select id="MergeTypeComboBox"
						name="MergeTypeComboBox">
							<option ${HolisticMergeSel} value="HolisticMerge">Holistic
								Merger</option>
							<!--<option ${RuleMergeSel} value="RuleMerge">Rule Merger</option>
							 <option ${GraphMergeSel} value="GraphMerge">GraphMerge</option>
									<option ${APIMergeSel} value="APIMerge">APIMerge</option>
									<option ${MatchingMergeSel} value="MatchingMerge">MatchingMerge</option>
									<option ${GenericMergeSel} value="GenericMerge">GenericMerge</option>-->
					</select>
					</td>
					<td>Output: <select id="MergeOutputType"
						name="MergeOutputType">
							<option ${RDFsel} value="RDFtype">RDF/XML</option>
							<option ${OWLsel} value="OWLtype">OWL/XML</option>
					</select></td>
				</tr>
			</table>
			<!-- ###################################################################################################################### -->
			<div id="SectionGMR_container">
				<div class="showcell">
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
										<td align="right">Suggested compatible sets:<input
											type="number" id="numSuggestion" name="numSuggestion" min="1"
											max="1000" value="${numberOfSuggestion}">
											<button class="btn small" type="submit" name="submit"
												value="DoCompatibilityCheck" onClick="readCheckedBoxes()">check
												Compatibility</button>
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
							<td colspan="2"><hh>Completeness Aspect</td>
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
							<td colspan="2"><hh>Minimality Aspect</td>
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
							<td colspan="2"><hh>Deduction Aspect</td>
						</tr>
						<tr>
							<td colspan="2"><input ${EntCh} id="12" idname="EntCheck"
								name="selectedItem" value="EntCheck" type="checkbox">R12.
								Entailment Deduction Satisfaction</td>
						</tr>
						<tr>
							<td colspan="2"><hh>Constraint Aspect</td>
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
							<td colspan="2"><hh>Acyclicity Aspect</td>
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
								idnmae="RecProCheck" name="selectedItem" value="RecProCheck"
								type="checkbox">R18. Prohibition of Properties being
								Inverses of Themselves</td>
						</tr>
						<tr>
							<td colspan="2"><hh>Connectivity Aspect</td>
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
				</div>
			</div>
			<!-- ###################################################################################################################### -->
			<br>
			<div id="SectionEval_container">
				<div class="showcell">
					<h2>Adjust Evaluation</h2>
				</div>
				<div id="SectionEval" class="hidecell">

					<table class="myTable">
						<tr>
							<td>
								<h2>Selecting Evaluation Criteria</h2>
							</td>
							<td colspan="2"><span onclick="SelectAll()"
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
								Completeness Aspect &emsp;</td>
							<td><input ${MinimalityCh} id="MinimalityCheck"
								name="selectedItem" value="MinimalityCheck" type="checkbox">
								Minimality Aspect &emsp;</td>
							<td><input ${DeductionCh} id="DeductionCheck"
								name="selectedItem" value="DeductionCheck" type="checkbox">
								Deduction Aspect&emsp;</td>
						</tr>
						<tr>
							<td><input ${ConstraintCh} id="ConstraintCheck"
								name="selectedItem" value="ConstraintCheck" type="checkbox">
								Constraint Aspect &emsp;</td>
							<td><input ${AcyclicityCh} id="AcyclicityCheck"
								name="selectedItem" value="AcyclicityCheck" type="checkbox">
								Acyclicity Aspect &emsp;</td>
							<td><input ${ConnectivityCh} id="ConnectivityCheck"
								name="selectedItem" value="ConnectivityCheck" type="checkbox">
								Connectivity Aspect&emsp;</td>
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
				</div>
			</div>
			<!-- ############################################################################ -->
			<!-- <h2>Run</h2>-->
			<table>
				<tr>
					<td>
						<div align="center">
							<button class="btn medium" type="submit" name="submit"
								value="DoMerge" onClick="readCheckedBoxes()">Merge</button>
						</div>
					</td>
				</tr>
				<tr>
					<td><div>
							Your log file: <a href="${logFile}"> Download </a>
						</div></td>
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

		<!-- ################################################################################################ -->
		<!-- / main body -->
		<div class="clear"></div>
		</main>
	</div>
</body>

	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<%@include file="../shared/footer.jsp"%>

	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->

	<!-- File upload- FilePond library -->
	<!-- <script src="https://unpkg.com/filepond/dist/filepond.js"></script>-->

	<script type="text/javascript" src="../dist/filepond.js"></script>
	<script>
		
	</script>


	<script type="text/javascript" src="../js/merging.js"></script>

	<script>
		window.onload = function() {
			//if ("${SecGMR}" === "SecGMRshow") {
			//document.getElementById("SectionGMR").style.display = "block";
			//}

			//if ("${SecEval}" === "SecEvalshow") {
			//document.getElementById("SectionEval").style.display = "block";
			//}

			var a1 = "${NumPrefOnt}"
			if (a1 === null || a1 === "") {
				document.getElementById('PreferedOnt').value = "equal";
			} else {
				document.getElementById("PreferedOnt").value = "${NumPrefOnt}";
			}

			var a2 = "${numberOfSuggestion}";
			if (a2 === null || a2 === "") {
				document.getElementById('numSuggestion').value = 5;
			}

			var a3 = "${msg}";
			if (a3 === null || a3 === "") {
				document.getElementById("showMessage").style.display = "none";
			}

			if ("${uploadedInputFiles}" === ""
					|| "${uploadedInputFiles}" === null
					|| "${uploadedmapFiles}" === ""
					|| "${uploadedmapFiles}" === null) {
				document.getElementById("msgOldFile").style.display = "none";
			} else {
				document.getElementById("msgOldFile").style.display = "block";
				document.getElementById("showMessage").style.display = "block";
			}

		}
	</script>



</html>