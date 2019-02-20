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

String[] strcolumns = request.getParameter("columns").split(",");
String[] strcolumnData = request.getParameter("columnData").replaceAll(",,", ",<space>,").split(",");
String strobjectType = request.getParameter("objectType");
String strParentDataId = request.getParameter("parentDataId");
System.out.println("====strParentDataId ====="+strParentDataId);
Document mData = new Document();
for(int i=1;i<strcolumns.length;i++)
{
	System.out.println(strcolumns[i]+" : "+strcolumnData[i]+";");
	if(strcolumnData[i].equals("<space>"))
		strcolumnData[i]="";
	mData.append(strcolumns[i], strcolumnData[i]);
}
System.out.println("strParentDataId:"+strParentDataId+";");
ArrayList<Document> arrConnections = new ArrayList<Document>();

if(!Util.checkEmpty(strParentDataId))
{
	arrConnections.add(new Document("objectId",strParentDataId));
	mData.append("fromConnections", arrConnections);
}
String strObjecId="";
mData.append("type", strobjectType);
if(strcolumns[0].equals("_id") && !strcolumnData[0].equals(""))
{
	strObjecId=strcolumnData[0];
	Util.update(strcolumnData[0], Context.strDataCollectionName, mData);
}
else if(strcolumnData[0].equals(""))
{
	strObjecId = Util.insert(Context.strDataCollectionName, mData);
	if(!Util.checkEmpty(strParentDataId))
	{
		Document mDataNew = new Document();
		mDataNew.append("objectId", strObjecId);
		Util.connectToObject(strParentDataId, Context.strDataCollectionName, mDataNew);
	}
}
out.print("true"+strObjecId);
%>