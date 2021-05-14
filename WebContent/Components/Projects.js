/**
 * 
 */

//Hidding alerts/successes on first page loading
$(document).ready(() => {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	} $("#alertError").hide();
});

//Save projects handler______________________________
$(document).on("click", "#btnSave", () => {
	
	//hidding alerts/successes if any
	$("#alertSuccess").text(""); 
	$("#alertSuccess").hide(); 
	$("#alertError").text(""); 
	$("#alertError").hide();
	
	// Validating the project form
	var status = validateItemForm(); 
	
	//if validation is failed	
	if (status != true) {
		$("#alertError").text(status); 
		$("#alertError").show(); 
		return;
	}
	
	// If validation passed
	var type = ($("#hiddenProjectIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax( {
		url : "ProjectsAPI", 
		type : type, 
		data : $("#formProject").serialize(), 
		dataType : "text", 
		complete : (response, status) => {
    					onProjectSaveComplete(response.responseText, status);
					}
	});
});