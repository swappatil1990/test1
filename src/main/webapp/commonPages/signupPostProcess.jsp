<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="idream2.main.core.sendMail"%>
<%@page import="idream2.main.core.Util"%>
<%
	String strEmail=request.getParameter("email");
	String strUserName=request.getParameter("username");
	String strPassword=request.getParameter("pwd");
	String strContactPerson=request.getParameter("contactPerson");
	String strContactNo=request.getParameter("contactNo");
	String strBusinessName=request.getParameter("businessName");
	String strNoOfOutlet=request.getParameter("noOfOutlet");
	Util.signUp(strEmail, strUserName, strPassword, strContactPerson, strContactNo, strBusinessName, strNoOfOutlet);
//	sendMail.sendFromCustomMail("", "test", "test body");
	response.sendRedirect("../index.jsp");
%>