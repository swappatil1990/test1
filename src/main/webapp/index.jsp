<%@page import="idream2.main.core.Util"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.MongoCollection"%>
<%@page import="com.mongodb.client.MongoDatabase"%>
<%@page import="com.mongodb.client.MongoClients"%>
<%@page import="com.mongodb.client.MongoClient"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.bson.Document"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Welcome to OpenShift</title>
</head>
<body>
<section class='container'>
          <hgroup>
          
<%
//Map<String,Object> mData=new HashMap<String,Object>();
//mData.put("name","MongoDB4");
//mData.put("info","test4");
//mData.put("contact","4444444");
//Util.update("5c4e2fd520c3130308300117","id_ObjectBasicData",mData);
Document doc= Util.print("5c4e2fd520c3130308300117","id_ObjectBasicData");
for (Map.Entry<String, Object> entry : doc.entrySet())
{
	out.println("Key : "+entry.getKey() +"  Value : "+ entry.getValue()+"</br>");
}
//util.delete("5c4e2ec420c31320687def3f","id_ObjectBasicData");

//util.insert("id_ObjectBasicData");
%>
Test
</body>
</html>
