<%@page import="idream2.main.UI.tableData"%>
<%@page import="idream2.main.core.Context"%>
<%@page import="idream2.main.UI.tableColumnConstants"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.FindIterable"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="java.util.ArrayList"%>
<%@ page import = "java.util.Map" %>
<%@ page import = "java.util.HashMap" %>
<%
	/*Document docRequest = new Document();
	Map<String, String[]> parameters = request.getParameterMap();
	for(String parameter : parameters.keySet()) {
		String[] arrValue = parameters.get(parameter);
		String strValue = "";
        //for(int i=0;i<arrValue.length;i++)
        	strValue =arrValue[0];
	    docRequest.append(parameter, strValue);
	}*/
	Map<String, Object> ArgMap = new HashMap<String, Object>();
	Map<String, String[]> parameters = request.getParameterMap();
	String strRowSelection = request.getParameter("selectRow");
	for(String parameter : parameters.keySet()) {
		ArgMap.put(parameter, (Object)parameters.get(parameter)[0]);
	}
	String strObjectType="";
	String strAdminTable="";
	String strAddMethod="";
	System.out.println("========= Inside table pre PRocess");
	String strParentDataId=request.getParameter("parentDataId");
	strObjectType=request.getParameter("objectType");
	
	String strLastUrl = Context.getLastHref();
	
	String[] arrColumnNames = null;
	String[] arrColumnDisplayNames = null;
	String strTableTitle = null;
	String strPostProcessURL = null;
	String JSColumnNames = "";
	String JSColumnClass= "";
	String JSColumnProperties = "";
	ArrayList<Document> allDocs = null;
	
	try
	{
		allDocs=(ArrayList<Document>)Util.callMethod("tableData", "getCommonTableData", ArgMap);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	System.out.println("========= s==================== 1");
	Document docTableProperties = allDocs.get(0);
	Document docColumnClass = allDocs.get(1);
	Document docColumnProperties = allDocs.get(2);
	Document docColumnDisplayName = allDocs.get(3);
	Document docColumnNos = allDocs.get(4);
	System.out.println("========= s====================2");
	arrColumnDisplayNames=new String[docColumnNos.keySet().size()];
	arrColumnNames=new String[docColumnNos.keySet().size()];
	System.out.println("========= s==================== 3");
	int cnt=0;
	for ( String key : docColumnNos.keySet() ) {
		arrColumnDisplayNames[cnt] = docColumnDisplayName.getString(key);
		arrColumnNames[cnt] = key;
		JSColumnNames += key+",";
		JSColumnClass += docColumnClass.getString(key)+",";
		JSColumnProperties += docColumnProperties.getString(key)+",";
		cnt++;
	}
	System.out.println("========= s==================== 4");
	JSColumnNames=JSColumnNames.substring(0,JSColumnNames.length()-1);
	JSColumnClass=JSColumnClass.substring(0,JSColumnClass.length()-1);
	JSColumnProperties=JSColumnProperties.substring(0,JSColumnProperties.length()-1);
	
	strTableTitle = docTableProperties.getString("tableTitle");
	strPostProcessURL = docTableProperties.getString("postProcessURL");
	System.out.println("========= s==================== 5");
%>