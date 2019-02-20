<%@page import="idream2.main.core.Command"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="idream2.main.core.Context"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="com.mongodb.client.FindIterable"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
out.clear();

String strObjectId = request.getParameter("objectDataId");
String strParentDataId = request.getParameter("parentDataId");
String strObjectTypeName = request.getParameter("objectTypeName");

BasicDBObject findCondition = new BasicDBObject();

String strResult = Util.disconnectObjectObject(strParentDataId,strObjectId, Context.strDataCollectionName);
out.print(strResult);
%>