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

function readConsParam() {
	var planTypes = document.getElementsByName("ConsRadio");
	var selectedParam = [];
	for (var i = 0; i < planTypes.length; i++) {
		if (planTypes[i].checked)
			selectedParam.push(planTypes[i].value);
	}
	var y = document.getElementsByName("maxExpl");
	for (j = 0; j < y.length; j++) {
		selectedParam.push(y[j].value);
	}

	// Set the value of selectedVehicles to comma separated

	var hiddenSelectedPlan = document.getElementById("selectedConsParam");
	hiddenSelectedPlan.value = selectedParam.join(",");

	// Submit the form using javascript
	var form = document.getElementById("ConsRadio");
	form.submit();
}
/** ***************************************************************** */
function handleClick(cb) {

	var listString = $(cb).attr("ids");
	console.log(listString);
	var list = listString.split("_");
	console.log(list);
	// deselect all checkboxer
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
function processRepairPlan() {
	var planTypes = document.getElementsByName("repairs");
	var selectedPlanTypes = [];
	for (var i = 0; i < planTypes.length; i++) {
		if (planTypes[i].checked) {
			selectedPlanTypes.push(planTypes[i].value);
		}
	}

	var hiddenSelectedPlan = document.getElementById("selectedRepair");
	hiddenSelectedPlan.value = selectedPlanTypes.join(",");

	// Submit the form using javascript
	var form = document.getElementById("repairs");
	form.submit();
}
/** ***************************************************************** */


