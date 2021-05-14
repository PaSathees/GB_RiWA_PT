<%@page import="com.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Projects Management | GadgetBadget</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Items.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Projects Management : GadgetBadget</h1>
				<form id="formItem" name="formItem">
					Project Title: <input id="projectTitle" name="projectTitle" type="text"
								class="form-control form-control-sm">
					<br> 
					Project Type: <input id="projectType" name="projectType" type="text"
								class="form-control form-control-sm">
					<br> 
					Project Description: <input id="projectDesc" name="projectDesc" type="text"
								class="form-control form-control-sm">
					<br> 
					Project Budget: <input id="projectBudget" name="projectBudget" type="text"
								class="form-control form-control-sm">
					<br> 
					Unit Cost: <input id="unitCost" name="unitCost" type="text"
								class="form-control form-control-sm">
					<br> 
					Inventor Username: <input id="username" name="username" type="text"
								class="form-control form-control-sm">
					<br> 
					Inventor Password: <input id="password" name="password" type="password"
								class="form-control form-control-sm">
					<br> 					
					<input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary">
					<input type="hidden" id="hiddenProjectIDSave" name="hiddenProjectIDSave" value="">
				</form>
				
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>

				<div id="divProjectsGrid">
					<%
						Project projectObj = new Project();
						out.println(projectObj.readProjects());					
					%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>