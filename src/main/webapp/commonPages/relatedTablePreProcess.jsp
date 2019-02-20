<%@page import="idream2.main.UI.tableData"%>
<%@page import="idream2.main.UI.tableColumnConstants"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.FindIterable"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="java.util.ArrayList"%>
<%@ page import = "java.util.Map" %>
<%@ page import = "java.util.HashMap" %>
<%

	
	Map<String, Object> rArgMap = new HashMap<String, Object>();
	Map<String, String[]> rParameters = request.getParameterMap();
	for(String parameter : rParameters.keySet()) {
		rArgMap.put(parameter, (Object)rParameters.get(parameter)[0]);
	}
	System.out.println("=========Start related table 2");
	
	String strRAdminTable="";
	String strRAddMethod="";
	String strRParentDataId=request.getParameter("parentDataId");
	String strRObjectType=request.getParameter("objectType");
	
		
		
		strRAddMethod="tableData:getCommonTableData";
		String strRTableName="common";
	
	System.out.println("=========Start related table 3");
	String[] arrColumnNames = null;
	String[] arrColumnDisplayNames = null;
	String strTableTitle = null;
	String strPostProcessURL = null;
	String JSColumnNames = "";
	String JSColumnClass= "";
	String JSColumnProperties = "";
	ArrayList<Document> allRDocs = null;
	try
	{
		System.out.println("========="+strRObjectType);
		System.out.println("========="+strRAdminTable);
		System.out.println("========="+strRAddMethod.split(":")[0]);
		System.out.println("========="+strRAddMethod.split(":")[1]);
		//allDocs  = tableData.getAdminObjectType(docRequest);
		allRDocs=(ArrayList<Document>)Util.callMethod(strRAddMethod.split(":")[0], strRAddMethod.split(":")[1], rArgMap);
		System.out.println("=========DONE ");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	Document docTableProperties = allRDocs.get(0);
	Document docColumnClass = allRDocs.get(1);
	Document docColumnProperties = allRDocs.get(2);
	Document docColumnDisplayName = allRDocs.get(3);
	Document docColumnNos = allRDocs.get(4);
	
	arrColumnDisplayNames=new String[docColumnNos.keySet().size()];
	arrColumnNames=new String[docColumnNos.keySet().size()];

	int cnt=0;
	for ( String key : docColumnNos.keySet() ) {
		arrColumnDisplayNames[cnt] = docColumnDisplayName.getString(key);
		arrColumnNames[cnt] = key;
		JSColumnNames += key+",";
		JSColumnClass += docColumnClass.getString(key)+",";
		JSColumnProperties += docColumnProperties.getString(key)+",";
		cnt++;
	}
	JSColumnNames=JSColumnNames.substring(0,JSColumnNames.length()-1);
	JSColumnClass=JSColumnClass.substring(0,JSColumnClass.length()-1);
	JSColumnProperties=JSColumnProperties.substring(0,JSColumnProperties.length()-1);
	
	strTableTitle = docTableProperties.getString("tableTitle");
	strPostProcessURL = docTableProperties.getString("postProcessURL");
%>


