<%@page import="idream2.main.core.RelationData"%>
<%@page import="idream2.main.core.ObjectType"%>
<%@page import="idream2.main.core.RelationType"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="idream2.main.core.ObjectData"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.MongoCollection"%>
<%@page import="com.mongodb.client.MongoDatabase"%>
<%@page import="com.mongodb.client.MongoClients"%>
<%@page import="com.mongodb.client.MongoClient"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="com.mongodb.client.model.Filters"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Welcome to OpenShift</title>
  <script>
	function httpGet(theUrl)
	{
	    var xmlHttp = new XMLHttpRequest();
	    xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
	    xmlHttp.send( null );
	    
	    document.getElementById("outputBox").value = document.getElementById("outputBox").value +"\n"+ xmlHttp.responseText;
	}
  	function executeCommand(x)
  	{
  		httpGet( "http://localhost:8080/SampleApp/executeCommand.jsp?command="+document.getElementById("queryTextbox").value);
	}
  	function clearBox()
  	{
  		document.getElementById("outputBox").value="";
	}
  </script>
</head>
<body>
         <input type="text" value="" name="queryTextbox" id="queryTextbox" style="width:700px">
         <input type="button" value="Run" onclick="executeCommand(this)">
         <br/><br/>
         <textarea id="outputBox" cols=150 rows="30" readonly>
         </textarea><input type="button" value="Clear" onclick="clearBox()">

</body>
</html>
