/**
 * 
 */

//Hidding alerts on first page loading
$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	} $("#alertError").hide();
});

