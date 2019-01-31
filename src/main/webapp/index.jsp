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
  		httpGet("http://localhost:8080/SampleApp/executeCommand.jsp?command="+document.getElementById("queryTextbox").value);
	}
  	function clearBox()
  	{
  		document.getElementById("outputBox").value="";
	}
  </script>
</head>
<body>
<section class='container'>
         <input type="text" value="" name="queryTextbox" id="queryTextbox" style="width:700px">
         <input type="button" value="Run" onclick="executeCommand(this)">
         <br/><br/>
         <textarea id="outputBox" cols=150 rows="30" readonly>
         </textarea><input type="button" value="Clear" onclick="clearBox()">
<%
//Map<String,Object> mData=new HashMap<String,Object>();
//mData.put("name","MongoDB4");
//mData.put("info","test4");
//mData.put("contact","4444444");
//Util.update("5c4e2fd520c3130308300117","id_ObjectBasicData",mData);
//Document doc= Util.print("5c4e2fd520c3130308300117","id_ObjectBasicData");
//for (Map.Entry<String, Object> entry : doc.entrySet())
//{
//	out.println("Key : "+entry.getKey() +"  Value : "+ entry.getValue()+"</br>");
//}
//util.delete("5c4e2ec420c31320687def3f","id_ObjectBasicData");

//util.insert("id_ObjectBasicData");
try
{
	//ObjectType.create("Employee", "Admin", "-", "", 1, "", 4);
	//ObjectType.delete("Manager");
	//int cnt=Util.getObjectDataSequence("Student");
	//out.print("Cnt : "+cnt+"</br>");
	//bjectType.delete("Employee");
	//out.print("id : "+"</br>");
	//ObjectType.addElement("ManContact", "Admin", "", "Manager", "", 5);
	//ObjectType.removeElement("Employee", "EmpContact");
	//ObjectType.delete("Student");
	Document doc=new Document();
	doc.append("contact", "4444");
	doc.append("address", "Nashik");
	doc.append("gender", "Male");
	doc.append("state", "maha3");
	//ObjectData.insert("Employee","Rahul","1", doc);
	//ObjectData.update("Manager","Amol","1", doc);
	//ObjectData.delete("Manager","Amol","1");
	//Document docxx = ObjectData.getData("Manager","Amol","1");
	//out.println(" : "+docxx);
	//BasicDBObject find = new BasicDBObject();
    //find.put("objectName", "Manager");
	//Document docCheck=Util.find("id_Objects",find);
	//out.println("docCheck 2 : "+docCheck);
	
	//RelationType.addElement("ManagerEmp", "String", "", "ManagerEmp", "", 10);
	//RelationType.create("ManagerEmp", "Admin", "", "float");
	//RelationType.delete("ManagerEmp");
	//RelationType.addToObjects("ManagerEmp", "Employee");
	//RelationType.addFromObjects("ManagerEmp", "Manager");
	//RelationType.removeToObjects("ManagerEmp", "Employee");
	//RelationType.removeFromObjects("ManagerEmp", "Manager");
	//Document docRel=new Document();
	//docRel.append("qty", "2");
	//RelationData.connect("Manager","Amol","1", "Employee","Rahul","1", "ManagerEmp", docRel);
	//RelationData.disconnect("Manager","Amol","1", "Employee","Rahul","1", "ManagerEmp");
	
	//RelationData.update("Manager","Amol","1", "Employee","Rahul","1", "ManagerEmp", docRel);
	//Util.clearAllCustomData();
}
catch(Exception e)
{
	out.print("Error: "+e);
}
%>
</body>
</html>
