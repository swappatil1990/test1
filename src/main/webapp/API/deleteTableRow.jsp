<%@page import="idream2.main.core.Command"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="org.bson.Document"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
out.clear();

String strObjectId = request.getParameter("objectDataId");
String strObjectTypeName = request.getParameter("objectTypeName");
Util.delete(strObjectId, strObjectTypeName);
out.print("true");
%>