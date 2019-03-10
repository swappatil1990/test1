<%@page import="idream2.main.core.Context"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
	String strOption = request.getParameter("option");
	if(strOption.equals("SuperAdmin"))
	{
		session.setAttribute("strCollectionName", "id_AdminObjectData");
		session.setAttribute("strDataCollectionName", "id_AdminObjectData");
		//Context.strCollectionName="id_AdminObjectData";
		//Context.strDataCollectionName="id_AdminObjectData";
	}
	else if(strOption.equals("Admin"))
	{
		session.setAttribute("strCollectionName", "id_AdminObjectData");
		session.setAttribute("strDataCollectionName", "id_schema");
		//Context.strCollectionName="id_AdminObjectData";
		//Context.strDataCollectionName="id_schema";
	}
	else
	{
		session.setAttribute("strCollectionName", "id_schema");
		session.setAttribute("strDataCollectionName", "id_ObjectData");
		//Context.strCollectionName="id_schema";
		//Context.strDataCollectionName="id_ObjectData";
	}
    String redirectURL = "../../home.jsp?&mainMenu=true&pageName=Home";
    response.sendRedirect(redirectURL);
%>
