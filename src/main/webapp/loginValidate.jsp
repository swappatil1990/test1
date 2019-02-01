<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="idream2.main.core.Context"%>
<%@page import="idream2.main.core.User"%>

<%
try
{
	Context cont=new Context();
	String strUserName = request.getParameter("username").toString();
	String strContext=cont.login(strUserName, request.getParameter("password").toString());
	if(strContext!=null && !strContext.equals(""))
	{
		session.setAttribute("sessionUserName",strUserName);
		session.setAttribute("sessionContaxtId",strContext); 
		response.sendRedirect("commonPages/loginOption/optionPage.jsp");
	}
	else
		response.sendRedirect("index.jsp");
}
catch(Exception e)
{
	out.write(" "+e);
	//response.sendRedirect("index.jsp");
}

%>