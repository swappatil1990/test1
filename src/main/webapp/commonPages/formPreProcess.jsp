<%@page import="idream2.main.UI.tableData"%>
<%@page import="idream2.main.UI.tableColumnConstants"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.FindIterable"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="idream2.main.core.ObjectType"%>
<%@page import="idream2.main.core.Context"%>
<%@page import="java.util.ArrayList"%>
<%@ page import = "java.util.Map" %>
<%@ page import = "java.util.HashMap" %>
<%

	Map<String, Object> ArgMap = new HashMap<String, Object>();
	Map<String, String[]> parameters = request.getParameterMap();
	for(String parameter : parameters.keySet()) {
		ArgMap.put(parameter, (Object)parameters.get(parameter)[0]);
	}
	ArgMap.put("context", context);
	String strObjectType=request.getParameter("objectType");
	String strParentDataId=request.getParameter("parentDataId");
	
	ArrayList<Document> allDocs = null;
	try
	{
		allDocs=(ArrayList<Document>)Util.callMethod("formData", "getFormData", ArgMap);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	Document docData= ObjectType.getSchemaObjectType(strObjectType, context);
	String[] connections=null;
	if(docData!=null)
	{
		String objectConnections = docData.getString("objectConnection");
		if(!Util.checkEmpty(objectConnections))
		{
			connections=objectConnections.split(",");
		} 
		/*System.out.println("-----------------Bewfore Loop 1 ");
		ArrayList<Document> arrList= (ArrayList<Document>)docData.get("connections");
		ArrayList<String> arrConnections=new ArrayList<String>();
		System.out.println("-----------------Bewfore Loop");
		for(Document docTmp:arrList)
		{
			if(docTmp.getString("connectionType")!=null && docTmp.getString("connectionType").equals("ObjectRelation"))
			{
				arrConnections.add(docTmp.getString("connectionType")+"x,");
			}
		}
		connections = arrConnections.toArray(new String[0]);
		System.out.println("-----------------");*/
	}
%>