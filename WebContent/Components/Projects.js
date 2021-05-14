/**
 * 
 */

//Hidding alerts/successes on first page loading
$(document).ready(() => {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	} $("#alertError").hide();
});

//Save button handler______________________________
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

//Update button handler______________________________
$(document).on("click", ".btnUpdate", () => {     
	$("#hiddenProjectIDSave").val($(this).data("projectid"));  
	$("#projectTitle").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#projectType").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#projectDesc").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#projectBudget").val($(this).closest("tr").find('td:eq(3)').text()); 
	$("#unitCost").val($(this).closest("tr").find('td:eq(4)').text()); 
	$("#username").val($(this).closest("tr").find('td:eq(5)').text()); 
	$("#password").val($(this).closest("tr").find('td:eq(6)').text()); 
});

//Delete button handler______________________________
$(document).on("click", ".btnRemove", () => {
	$.ajax( {
		url : "ProjectsAPI", 
		type : "DELETE", 
		data : "projectID=" + $(this).data("projectid"), 
		dataType : "text", 
		complete : (response, status) => {
						onProjectDeleteComplete(response.responseText, status);
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


onProjectDeleteComplete = (response, status) => {
	if (status == "success") {
		
		const resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Project successfully deleted"); 
			$("#alertSuccess").show();
			$("#divProjectsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data); 
			$("#alertError").show();
		}
		
	} else if (status == "error") {
		$("#alertError").text("Error while deleting project"); 
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting project.."); 
		$("#alertError").show();
	}
}
