package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProjectsAPI
 */
@WebServlet("/ProjectsAPI")
public class ProjectsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Project projectObj = new Project();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectsAPI() {
        super();        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output = projectObj.createProject(request.getParameter("projectTitle"), 
												request.getParameter("projectType"),	
												request.getParameter("projectDesc"),	
												request.getParameter("projectBudget"),	
												request.getParameter("unitCost"),
												request.getParameter("username"),
												request.getParameter("password"));
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map params = getParamsAsMap(request);
		
		String output = projectObj.updateProject(params.get("projectID").toString(),
													params.get("projectTitle").toString(),
													params.get("projectType").toString(),
													params.get("projectDesc").toString(),
													params.get("projectBudget").toString(),
													params.get("unitCost").toString(),
													params.get("username").toString(),
													params.get("password").toString());				
				
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map params = getParamsAsMap(request);
		
		String output = projectObj.deleteProject(params.get("projectID").toString());				
				
		response.getWriter().write(output);
	}
	
	// To Convert request parameters to a Map 
	private static Map getParamsAsMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>(); 
		try 
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			
			String[] params = queryString.split("&"); 
			for (String param : params) 
			{
				String[] p = param.split("="); map.put(p[0], p[1]);
			}
		} catch (Exception e) { 
				
		} 
		return map;
	}

}
