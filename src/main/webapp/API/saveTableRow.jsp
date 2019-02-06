<%@page import="idream2.main.core.Command"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="org.bson.Document"%>
<%@page import="org.bson.types.ObjectId"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
out.clear();

String[] strcolumns = request.getParameter("columns").split(",");
String[] strcolumnData = request.getParameter("columnData").replaceAll(",,", ",<space>,").split(",");
String strobjectType = request.getParameter("objectType");
String strParentDataId = request.getParameter("parentDataId");
Document mData = new Document();
for(int i=1;i<strcolumns.length;i++)
{
	System.out.println(strcolumns[i]+" : "+strcolumnData[i]+";");
	if(strcolumnData[i].equals("<space>"))
		strcolumnData[i]="";
	mData.append(strcolumns[i], strcolumnData[i]);
}
System.out.println("strParentDataId:"+strParentDataId+";");
if(!Util.checkEmpty(strParentDataId))
	mData.append("objectId", new ObjectId(strParentDataId));
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