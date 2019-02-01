<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="idream2.main.core.Context"%>
<%

session.invalidate();
Context.logout();
response.sendRedirect("index.jsp");
%>