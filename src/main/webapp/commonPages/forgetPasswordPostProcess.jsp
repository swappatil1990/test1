<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="idream2.main.core.sendMail"%>
<%
	String strEmail=request.getParameter("email");
	//sendMail.sendFromCustomMail(strEmail, "test", "test body");
	response.sendRedirect("../index.jsp");
%>