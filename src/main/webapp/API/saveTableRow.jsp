<%@page import="idream2.main.core.Command"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="org.bson.Document"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
out.clear();

String[] strcolumns = request.getParameter("columns").split(",");
String[] strcolumnData = request.getParameter("columnData").replaceAll(",,", ",<space>,").split(",");
String strobjectType = request.getParameter("objectType");
Document mData = new Document();
for(int i=1;i<strcolumns.length;i++)
{
	if(strcolumnData[i].equals("<space>"))
		strcolumnData[i]="";
	mData.append(strcolumns[i], strcolumnData[i]);
}
String strObjecId="";
if(strcolumns[0].equals("_id") && !strcolumnData[0].equals(""))
{
	strObjecId=strcolumnData[0];
	Util.update(strcolumnData[0], strobjectType, mData);
}
else if(strcolumnData[0].equals(""))
{
	strObjecId = Util.insert(strobjectType, mData);
}
out.print("true"+strObjecId);
%>