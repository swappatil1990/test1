<%@page import="idream2.main.core.Command"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="idream2.main.core.Context"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="com.mongodb.client.FindIterable"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
Context context=new Context();
context.createContextFromSession(session);

out.clear();

String strObjectId = request.getParameter("objectDataId");
String strObjectTypeName = request.getParameter("objectTypeName");

BasicDBObject findCondition = new BasicDBObject();
Document docSingle=new Document();
docSingle.append("$eq", strObjectId);
findCondition.append("connections.objectId", docSingle);
FindIterable<Document> docResult=Util.findMany(context.getDataCollectionName(), findCondition, context);
docSingle=new Document();
docSingle.append("objectId", strObjectId);

for(Document doc: docResult)
{
	Util.deleteObjectToObject(doc.getObjectId("_id").toString(), context.getDataCollectionName(), docSingle, context);	
}
Util.delete(strObjectId, context.getDataCollectionName(), context);
out.print("true");
%>