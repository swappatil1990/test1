<%@page import="idream2.main.core.Command"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="idream2.main.core.Context"%>
<%@page import="org.bson.Document"%>
<%@page import="org.bson.types.ObjectId"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
out.clear();

String strobjectType = request.getParameter("objectType");
String strParentDataId = request.getParameter("parentDataId");
String strSelectedDataId = request.getParameter("selectedDataId");
System.out.println("-@@@@@@@@@@@------------------strParentDataId :"+strParentDataId);
System.out.println("-------------------strSelectedDataId :"+strSelectedDataId);

String strObjectId = Util.connectObjectToObject(strParentDataId, strSelectedDataId, Context.strCollectionName);

out.print("true"+strObjectId);
%>