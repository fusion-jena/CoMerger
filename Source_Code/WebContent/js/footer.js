$(document).ready(function() {
	/** script for style of hide and show gray sections * */
	var coll = document.getElementsByClassName("showcell");
	var i;

	for (i = 0; i < coll.length; i++) {
		coll[i].addEventListener("click", function() {
			this.classList.toggle("active");
			var content = this.nextElementSibling;
			if (content.style.display === "block") {
				content.style.display = "none";
			} else {
				content.style.display = "block";
			}
		});
	}

	// file upload
	fileUploadPreperation();
	
})

function fileUploadPreperation() {
	console.log("uplaoding ontologies files");
	const
	inputElement = document.querySelector('input[type="file"]');
	const
	pond = FilePond.create(inputElement);

	FilePond.parse(document.body);
	FilePond.setOptions({
		server : '../../MergingServlet'
	});
}

function showLoading() {
	document.getElementById('loadingmsg').style.display = 'block';
	document.getElementById('loadingover').style.display = 'block';
}

window.onscroll = function() {
	scrollFunction()
};
function scrollFunction() {

	var bt = $("#myBtn");

	if (bt != null) {
		if (document.body.scrollTop > 20
				|| document.documentElement.scrollTop > 20) {
			bt.show();
		} else {
			bt.hide();
		}
	}

}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
	document.body.scrollTop = 0;
	document.documentElement.scrollTop = 0;
}