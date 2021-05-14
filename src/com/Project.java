package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Project {
	
	User user = new User();
	
	public Connection connect() {
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");//not necessary as this way is deprecated
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:8889/GB_Project", "root", 
					"root");
			System.out.println("Connection successfully established with Project DB");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public String readProjects() {
		String output = "";
		
		try {
			//connecting to project database
			Connection connection = this.connect();
			
			if (connection == null) {
				return "Error connecting to project database";
			}					
					
			//creating prepared statement for reading
			String query = "select * from Project";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			//binding values to prepared statement				
			ResultSet rs = preparedStatement.executeQuery();	
			
			output = "[";
			
			while (rs.next()) {
				String dbProjectID = Integer.toString(rs.getInt("projectID"));
				String projectTitle = rs.getString("projectTitle");
				String projectType = rs.getString("projectType");
				String projectDesc = rs.getString("projectDesc");
				String projectBudget = Double.toString(rs.getDouble("projectBudget"));
				String unitCost = Integer.toString(rs.getInt("unitCost"));
				
				
				output += "{\"ProjectID\":\"" + dbProjectID + "\", \"ProjectTitle\":\"" + projectTitle +"\", "
						+ "\"ProjectType\":\"" + projectType + "\", \"ProjectDesc\":\"" + projectDesc + "\", "
						+ "\"ProjectBudget\":\"" + projectBudget + "\", \"UnitCost\":\"" + unitCost + "\"},";
			}				
			
			output += "]";		 		
				
			connection.close();
			
		} catch (Exception e) {
			output = "Error reading project";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String createProject(String projectTitle, String projectType, 
								String projectDesc, String projectBudget, 
								String unitCost, String username, String password) {
		
		String output = "";
		
		try {
			//connecting to project database
			Connection connection = this.connect();
			
			if (connection == null) {
				return "Error connecting to project database";
			}
			
			//check if it authenticated
			if (user.isAuthenticated(username, password)) {
				
				if (user.getUserRole(username).equalsIgnoreCase("Inventor")) {
					
					//get userID for the username
					int userID = user.getUserID(username);
					
					//creating prepared statement for insert
					String query = "insert into Project (projectTitle, projectType, projectDesc, "
							+ "projectBudget, unitCost, inventorID) values (?, ?, ?, ?, ?, ?)";
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					
					//binding values to prepared statement
					preparedStatement.setString(1, projectTitle);
					preparedStatement.setString(2, projectType);
					preparedStatement.setString(3, projectDesc);
					preparedStatement.setDouble(4, Double.parseDouble(projectBudget));
					preparedStatement.setFloat(5, Float.parseFloat(unitCost));
					preparedStatement.setInt(6, userID);					
					
					preparedStatement.execute();
					output = "Project inserted successfully";					
					
				} else {
					output = "You are not authorized to insert Project";
				}
				
			} else {
				output = "you are not authenticated";
			}	
			
			connection.close();
			
		} catch (Exception e) {
			output = "Error inserting project";
			System.err.println(e.getMessage());
		}
		
		return output;
				
	}
	
	public String updateProject(String projectID, String projectTitle, 
								String projectType, String projectDesc, 
								String projectBudget, String unitCost, 
								String username, String password) {
		
		String output = "";
		
		try {
			//connecting to project database
			Connection connection = this.connect();
			
			if (connection == null) {
				return "Error connecting to project database";
			}
			
			//check if it authenticated
			if (user.isAuthenticated(username, password)) {
				
				if (user.getUserRole(username).equalsIgnoreCase("Inventor")) {
					
					//check whether user is the inventor of the Project					
					int userID = user.getUserID(username);					
					String query = "select * from Project where projectID = ?";
					PreparedStatement preparedStatement = connection.prepareStatement(query);					
					preparedStatement.setInt(1, Integer.parseInt(projectID));
					ResultSet rs = preparedStatement.executeQuery();	
										
					if (rs.next()) {
						int inventorID = rs.getInt("inventorID");						
						if (inventorID == userID) {
							
							//creating prepared statement for update
							String query2 = "update Project set projectTitle = ?, projectType = ? , projectDesc = ?, "
									+ "projectBudget = ?, unitCost = ? where projectID = ?";
							PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
							
							//binding values to prepared statement
							preparedStatement2.setString(1, projectTitle);
							preparedStatement2.setString(2, projectType);
							preparedStatement2.setString(3, projectDesc);
							preparedStatement2.setDouble(4, Double.parseDouble(projectBudget));
							preparedStatement2.setFloat(5, Float.parseFloat(unitCost));
							preparedStatement2.setInt(6, Integer.parseInt(projectID));							
							
							preparedStatement2.execute();
							output = "Project updated successfully";					
						} else {
							output = "You are not the inventor of this Project";
						}
					} else {
						output = "Project does not exist";
					}				
					
				} else {
					output = "You are not an inventor";
				}
				
			} else {
				output = "You are not authenticated";
			}	
			
			connection.close();
			
		} catch (Exception e) {
			output = "Error updating project";
			System.err.println(e.getMessage());
		}
		
		return output;

	}
	
	public String deleteProject(String projectID) {
		String output = "";
		
		try {
			//connecting to project database
			Connection connection = this.connect();
			
			if (connection == null) {
				return "Error connecting to project database";
			}
								
			//check whether user is the inventor of the Project						
			String query = "select * from Project where projectID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);					
			preparedStatement.setInt(1, Integer.parseInt(projectID));
			ResultSet rs = preparedStatement.executeQuery();	
								
			if (rs.next()) {					
				//creating prepared statement for update
				String query2 = "delete from Project where projectID = ?";
				PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
				
				//binding values to prepared statement							
				preparedStatement2.setInt(1, Integer.parseInt(projectID));							
				
				preparedStatement2.execute();
				output = "Project deleted successfully";				
			} else {
				output = "Project does not exist";
			}			
			
			connection.close();
			
		} catch (Exception e) {
			output = "Error deleting project";
			System.err.println(e.getMessage());
		}
		
		return output;

	}
}
