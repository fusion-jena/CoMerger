
/** script for reading check boxes * */
function readCheckedBoxes() {
	var planTypes = document.getElementsByName("selectedItem");
	var selectedPlanTypes = [];
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

function SelectAll() {
	$("#SectionEval_container").find("input:checkbox").prop("checked", true)
}
function ClearAll() {
	$("#SectionEval_container").find("input:checkbox").prop("checked", false)
}
