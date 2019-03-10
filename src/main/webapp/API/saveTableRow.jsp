<%@page import="idream2.main.core.Command"%>
<%@page import="idream2.main.core.ObjectData"%>
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

String[] strcolumns = request.getParameter("columns").split(",");
String[] strcolumnData = request.getParameter("columnData").replaceAll(",,", ",<space>,").split("~");
String strobjectType = request.getParameter("objectType");
String strParentDataId = request.getParameter("parentDataId");


Document mData = new Document();
for(int i=1;i<strcolumns.length;i++)
{
	String strData = strcolumnData[i];
	if(strData.equals("<space>"))
		strcolumnData[i]="";
	if(strData.contains("i;-"))
	{
		strData=strData.replace("i;-", "");
		if(Util.checkEmpty(strData))
		{
			strData="0";
		}
		mData.append(strcolumns[i], Integer.parseInt(strData));
	}
	else if(strData.contains("o;-"))
	{
		strData=strData.replace("o;-", "");
		if(!strData.trim().equals(""))
		{
			mData.append(strcolumns[i], new ObjectId(strData));	
		}
		else
		{
			mData.append(strcolumns[i], strData);
		}
	}
	else if(strData.contains("l;-"))
	{
		strData=strData.replace("l;-", "");
		if(!strData.trim().equals(""))
		{
			mData.append(strcolumns[i], new ObjectId(strData));	
		}
		else
		{
			mData.append(strcolumns[i], strData);
		}
	}
	else
	{
		mData.append(strcolumns[i], strData);
	}
}

ArrayList<Document> arrConnections = new ArrayList<Document>();

if(!Util.checkEmpty(strParentDataId))
{
	arrConnections.add(new Document("objectId",strParentDataId));
	mData.append("fromConnections", arrConnections);
}
String strObjecId="";
mData.append("type", strobjectType);

// IF case for Update Data and ELESE case for Insert and Connect new Data with parent
if(strcolumns[0].equals("_id") && !strcolumnData[0].equals(""))
{
	//Update Data 
	strObjecId=strcolumnData[0];
	Util.update(strcolumnData[0], context.getDataCollectionName(), mData, context);
}
else if(strcolumnData[0].equals(""))
{
	mData.append("status", new ObjectId(ObjectData.getDefaultStatus(strobjectType, context)));
	//Insert and Connect Data with parent
	strObjecId = Util.insert(context.getDataCollectionName(), mData, context);
	if(!Util.checkEmpty(strParentDataId))
	{
		Document mDataNew = new Document();
		mDataNew.append("objectId", strObjecId);
		mDataNew.append("relationName", strobjectType);
		Util.connectToObject(strParentDataId, context.getDataCollectionName(), mDataNew, context);
	}
}
out.print("true"+strObjecId);
%>