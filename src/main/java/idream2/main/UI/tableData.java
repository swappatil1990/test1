package idream2.main.UI;

import java.util.ArrayList;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

import idream2.main.core.Util;

public class tableData {
	
	public static ArrayList<Document> getAdminObjectType(Map<String, Object> docRequest) throws Exception
	{
		try
		{
			System.out.println("========= Inside getAdminObjectType");
			ArrayList<Document> tableData=new ArrayList<Document>();
			String strParentDataId = (String)docRequest.get("parentDataId");
			String strTableName = (String)docRequest.get("tableName");
			
			
			BasicDBObject findCondition=new BasicDBObject();
			findCondition.append("name", strTableName);
			Document docTable = Util.find("id_Tables", findCondition);
			String strObjectType ="";
			String strAdminTable = "";
			String strDisplayName = "";
			if(docTable==null)
			{
				strObjectType ="id_ObjectElements";
				strAdminTable = "true";
				strDisplayName = "test";
			}
			else
			{
				strObjectType = docTable.getString("objectType");
				strAdminTable = docTable.getString("adminTable");
				strDisplayName = docTable.getString("displayName");
			}
			
			
			
			String[] arrColumnNames = null;
			String[] arrColumnDisplayNames = null;
			String[] arrColumnNos = null;
			String[] arrColumnPropeties = null;
			String[] arrColumnClassName = null;
			String strTableTitle = null;
			String JSPostProcessURL = "";
			String nestedURL = "formWithRelatedData.jsp?parentDataId=&objectType="+strObjectType+"&adminTable="+strAdminTable+"";
			//
			Document docColumnDisplayName=new Document();
			Document docTablePropertie=new Document();
			Document docColumnNos=new Document();
			Document docColumnClass=new Document();
			Document docColumnProperties=new Document();
			
			if(strAdminTable.equals("true"))
			{
				String strSearchKey="";
				if(tableColumnConstants.adminTable.containsKey(strObjectType))
				{
					strSearchKey=strObjectType;
				}
				else
				{
					strSearchKey="common";
				}
				arrColumnNames = ((String)tableColumnConstants.adminTable.get(strSearchKey)).split(",");
				arrColumnDisplayNames = ((String)tableColumnConstants.adminTable.get(strSearchKey+"_Display")).split(",");
				strTableTitle = ((String)tableColumnConstants.adminTableTitle.get(strSearchKey));
				arrColumnNos = ((String)tableColumnConstants.adminTable.get(strSearchKey+"_Nos")).split(",");
				arrColumnPropeties = ((String)tableColumnConstants.adminTable.get(strSearchKey+"_Prop")).split(",");
				arrColumnClassName = ((String)tableColumnConstants.adminTable.get(strSearchKey+"_Class")).split(",");
			}
			else
			{
				BasicDBObject findConditionColumns=new BasicDBObject();
				findConditionColumns.append("objectId", docTable.getObjectId("_id"));
				FindIterable<Document> docColumns = Util.findMany("id_Columns", findConditionColumns);
				
				int cnt=0;
				for(Document docColumn:docColumns)
				{
					cnt++;
				}
				arrColumnNames = new String[cnt];
				arrColumnDisplayNames = new String[cnt];
				arrColumnNos = new String[cnt];
				arrColumnPropeties = new String[cnt];
				arrColumnClassName = new String[cnt];
				cnt=0;
				for(Document docColumn:docColumns)
				{
					arrColumnNames[cnt] = docColumn.getString("name");
					arrColumnDisplayNames[cnt] = docColumn.getString("displayName");
					arrColumnNos[cnt] = docColumn.getString("columnNo");
					arrColumnPropeties[cnt] = docColumn.getString("columnProp");
					arrColumnClassName[cnt] = docColumn.getString("columnClass");
					cnt++;
				}
				
				
				strTableTitle = strDisplayName;
			}
			int cnt = 0;
			for(String strColumnName : arrColumnNames)
			{
				docColumnDisplayName.append(strColumnName, arrColumnDisplayNames[cnt]);
				docColumnNos.append(strColumnName, arrColumnNos[cnt]);
				docColumnClass.append(strColumnName, arrColumnClassName[cnt]);
				docColumnProperties.append(strColumnName, arrColumnPropeties[cnt]);
				cnt++;
			}
			
			docTablePropertie.append("tableTitle", strTableTitle);
			docTablePropertie.append("nestedURL", nestedURL);
			docTablePropertie.append("postProcessURL", JSPostProcessURL);
			
			tableData.add(docTablePropertie);
			tableData.add(docColumnClass);
			tableData.add(docColumnProperties);
			tableData.add(docColumnDisplayName);
			tableData.add(docColumnNos);
			
			System.out.println("========= strParentDataId");
			
			BasicDBObject findData=new BasicDBObject();
			if(!Util.checkEmpty(strParentDataId))
				findData.append("objectId", new ObjectId(strParentDataId));
			FindIterable<Document> docs = Util.findMany(strObjectType, findData);
			System.out.println("========= 1");
			for (Document doc : docs) {
				tableData.add(doc);
	        }
			System.out.println("========= 2");
			return tableData;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static ArrayList<Document> getElementData(Map<String, Object> docRequest) throws Exception
	{
		ArrayList<Document> tableData=new ArrayList<Document>();
		String strObjectType = (String)docRequest.get("objectType");
		String strAdminTable = (String)docRequest.get("adminTable");
		String strParentDataId = (String)docRequest.get("parentDataId");
		String[] arrColumnNames = null;
		String[] arrColumnDisplayNames = null;
		String[] arrColumnNos = null;
		String[] arrColumnPropeties = null;
		String[] arrColumnClassName = null;
		String strTableTitle = null;
		String JSPostProcessURL = "";
		String nestedURL = "";
		//
		Document docColumnDisplayName=new Document();
		Document docTablePropertie=new Document();
		Document docColumnNos=new Document();
		Document docColumnClass=new Document();
		Document docColumnProperties=new Document();
		
		if(strAdminTable.equals("true"))
		{
			arrColumnNames = ((String)tableColumnConstants.adminTable.get(strObjectType)).split(",");
			arrColumnDisplayNames = ((String)tableColumnConstants.adminTable.get(strObjectType+"_Display")).split(",");
			strTableTitle = ((String)tableColumnConstants.adminTableTitle.get(strObjectType));
			arrColumnNos = ((String)tableColumnConstants.adminTable.get(strObjectType+"_Nos")).split(",");
			arrColumnPropeties = ((String)tableColumnConstants.adminTable.get(strObjectType+"_Prop")).split(",");
			arrColumnClassName = ((String)tableColumnConstants.adminTable.get(strObjectType+"_Class")).split(",");
		}
		int cnt = 0;
		for(String strColumnName : arrColumnNames)
		{
			docColumnDisplayName.append(strColumnName, arrColumnDisplayNames[cnt]);
			docColumnNos.append(strColumnName, arrColumnNos[cnt]);
			docColumnClass.append(strColumnName, arrColumnClassName[cnt]);
			docColumnProperties.append(strColumnName, arrColumnPropeties[cnt]);
			cnt++;
		}
		
		docTablePropertie.append("tableTitle", strTableTitle);
		docTablePropertie.append("nestedURL", nestedURL);
		docTablePropertie.append("postProcessURL", JSPostProcessURL);
		
		tableData.add(docTablePropertie);
		tableData.add(docColumnClass);
		tableData.add(docColumnProperties);
		tableData.add(docColumnDisplayName);
		tableData.add(docColumnNos);
		
		BasicDBObject findData=new BasicDBObject();
		if(!Util.checkEmpty(strParentDataId))
			findData.append("objectId", new ObjectId(strParentDataId));
		
		FindIterable<Document> docs = Util.findMany(strObjectType, findData);
		for (Document doc : docs) {
			tableData.add(doc);
        }
		
		return tableData;
	}
}
