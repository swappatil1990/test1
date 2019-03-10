<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="idream2.main.core.Context"%>
<%
Context context=new Context();
context.createContextFromSession(session);

session.invalidate();
context.logout();
response.sendRedirect("index.jsp");
%>