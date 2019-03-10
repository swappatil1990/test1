<%@page import="idream2.main.core.Command"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="idream2.main.core.Context"%>
<%@page import="org.bson.Document"%>
<%@page import="org.bson.types.ObjectId"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
Context context=new Context();
context.createContextFromSession(session);

out.clear();

String strobjectType = request.getParameter("objectType");
String strParentDataId = request.getParameter("parentDataId");
String strSelectedDataId = request.getParameter("selectedDataId");

String strObjectId = Util.connectObjectToObject(strParentDataId, strSelectedDataId, context.getDataCollectionName(), strobjectType, context);

out.print("true"+strObjectId);
%>