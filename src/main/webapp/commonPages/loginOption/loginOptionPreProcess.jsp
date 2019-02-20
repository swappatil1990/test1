<%@page import="idream2.main.core.Context"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
	String strOption = request.getParameter("option");
	if(strOption.equals("SuperAdmin"))
	{
		Context.strCollectionName="id_AdminObjectData";
		Context.strDataCollectionName="id_AdminObjectData";
	}
	else if(strOption.equals("Admin"))
	{
		Context.strCollectionName="id_AdminObjectData";
		Context.strDataCollectionName="id_schema";
	}
	else
	{
		Context.strCollectionName="id_schema";
		Context.strDataCollectionName="id_ObjectData";
	}
    String redirectURL = "../../home.jsp";
    response.sendRedirect(redirectURL);
%>
