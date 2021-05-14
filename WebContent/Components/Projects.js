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
	const status = validateProjectForm(); 
	
	//if validation is failed	
	if (status != true) {
		$("#alertError").text(status); 
		$("#alertError").show(); 
		return;
	}
	
	// If validation passed
	const type = ($("#hiddenProjectIDSave").val() == "") ? "POST" : "PUT";
	
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


//Client Model==================================================
validateProjectForm = () => {
	//Project title 
	if ($("#projectTitle").val().trim() == "") {
		return "Insert Project Title";
	}
	
	//Project type 
	if ($("#projectType").val().trim() == "") {
		return "Insert Project Type";
	}
	
	//Project description 
	if ($("#projectDesc").val().trim() == "") {
		return "Insert Project Description";
	}
	
	//Project budget 
	if ($("#projectBudget").val().trim() == "") {
		return "Insert Project Budget";
	}
	
	
	// is project budget a numerical value 
	const projectBudget = $("#projectBudget").val().trim(); 
	
	if (!$.isNumeric(projectBudget)) {
		return "Insert a numerical value for Project Budget";
	}
	
	// convert project budget to a decimal value
	$("#projectBudget").val(parseFloat(projectBudget).toFixed(2));
	
	
	//Unit cost 
	if ($("#unitCost").val().trim() == "") {
		return "Insert Unit Cost";
	}
	
	// is unit cost a numerical value 
	const unitCost = $("#unitCost").val().trim(); 
	
	if (!$.isNumeric(unitCost)) {
		return "Insert a numerical value for Unit Cost";
	}
	
	// convert unit cost to decimal value
	$("#unitCost").val(parseFloat(unitCost).toFixed(2));
	
	//Inventor username
	if ($("#username").val().trim() == "") {
		return "Insert Inventor Username";
	}
	
	//Inventor password 
	if ($("#password").val().trim() == "") {
		return "Insert Inventor Password";
	}	
	
	return true;
}

onProjectSaveComplete = (response, status) => {
	if (status == "success") {
		
		const resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Project successfully saved."); 
			$("#alertSuccess").show();
			$("#divProjectsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data); 
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving project]"); 
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving project.."); 
		$("#alertError").show();
	}
	
	$("#hiddenProjectIDSave").val(""); 
	$("#formProject")[0].reset();
}

