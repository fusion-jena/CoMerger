$(document).ready(function() {

	// check variables
	checkVariables();

})


function checkVariables() {
	/*if ("${NumPrefOnt}" === "") {
		document.getElementById('PreferedOnt').value = "equal";
	} else {
		document.getElementById("PreferedOnt").value = "${NumPrefOnt}";
	}*/
}

/** ***************************************************************** */

/** script for selecting checkboxes * */
function SelectAll() {
	$("#SectionEval_container").find("input:checkbox").prop("checked", true)
}
function ClearAll() {
	$("#SectionEval_container").find("input:checkbox").prop("checked", false)
}
function SelectAllR() {
	$("#SectionGMR_container").find("input:checkbox").prop("checked", true)

}
function ClearAllR() {
	$("#SectionGMR_container").find("input:checkbox").prop("checked", false)
}
/** ***************************************************************** */

/** code for reading the checkboxes * */
function readCheckedBoxes() {
	var selectedPlanTypes = [];

	var numSug = document.getElementsByName("numSuggestion");
	selectedPlanTypes.push(numSuggestion.value);

	var planTypes = document.getElementsByName("selectedItem");

	for (var i = 0; i < planTypes.length; i++) {
		if (planTypes[i].checked) {
			selectedPlanTypes.push(planTypes[i].value);
		}
	}

	// Set the value of selectedVehicles to comma separated

	var hiddenSelectedPlan = document.getElementById("selectedUserItem");
	hiddenSelectedPlan.value = selectedPlanTypes.join(",");

	// Submit the form using javascript
	var form = document.getElementById("selectedItem");
	form.submit();
}
/** ***************************************************************** */

/** script for click handling * */
function handleClick(cb) {

	var listString = $(cb).attr("ids");
	console.log(listString);
	var list = listString.split("_");
	console.log(list);
	// deselect all checkboxes
	ClearAllR();

	for (i = 0; i < list.length; i++) {
		var x = list[i];
		console.log(x);
		$("#" + x).prop("checked", true)
	}
	$(cb).prop("checked", true);
	console.log("Clicked, new value = " + cb.checked);
	console.log("Clicked, new value = " + cb);
}
/** ***************************************************************** */

