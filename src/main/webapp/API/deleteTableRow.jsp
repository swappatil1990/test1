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
String strObjectTypeName = request.getParameter("objectTypeName");

BasicDBObject findCondition = new BasicDBObject();
Document docSingle=new Document();
docSingle.append("objectId", strObjectId);
findCondition.append("connections", docSingle);
FindIterable<Document> docResult=Util.findMany(Context.strDataCollectionName, findCondition);
for(Document doc: docResult)
{
	Util.deleteObjectToObject(doc.getObjectId("_id").toString(), Context.strDataCollectionName, docSingle);	
}
Util.delete(strObjectId, Context.strDataCollectionName);
out.print("true");
%>