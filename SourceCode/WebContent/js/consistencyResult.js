
function processPlan() {
			var planTypes = document.getElementsByName("ch1");
			var selectedPlanTypes = [];
			for (var i = 0; i < planTypes.length; i++) {
				if (planTypes[i].checked) {
					selectedPlanTypes.push(planTypes[i].value);
					var offer = "offer";
					offer = offer.concat(i);
					var x2 = document.getElementsByName(offer);
					for (k = 0; k < x2.length; k++) {
						if (x2[k].checked) {
							selectedPlanTypes.push(x2[k].value);
						}
					}
					var rewriteText = "rewriteText";
					rewriteText = rewriteText.concat(i);
					var y = document.getElementsByName(rewriteText);
					for (j = 0; j < y.length; j++) {
						selectedPlanTypes.push(y[j].value);
					}
				}
			}

			// Set the value of selectedVehicles to comma separated 

			var hiddenSelectedPlan = document
					.getElementById("selectedUserPlan");
			hiddenSelectedPlan.value = selectedPlanTypes.join(",");

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

			var hiddenSelectedPlan = document
					.getElementById("selectedConsParam");
			hiddenSelectedPlan.value = selectedParam.join(",");

			// Submit the form using javascript
			var form = document.getElementById("ch1");
			form.submit();
		}