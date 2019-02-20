package idream2.main.UI;

import java.util.ArrayList;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

import idream2.main.core.Context;
import idream2.main.core.Util;

public class tableData {
	
	public static ArrayList<Document> getCommonTableData(Map<String, Object> docRequest)
	{
		try
		{
			System.out.println("========= Inside getAdminObjectType");
			ArrayList<Document> tableData=new ArrayList<Document>();
			String strParentDataId = (String)docRequest.get("parentDataId");
			String strTableName = (String)docRequest.get("tableName");
			String strSelectedMenu = (String)docRequest.get("selectedMenu");
			String strPageType = (String)docRequest.get("pageType");
			BasicDBObject findCondition=new BasicDBObject();
			findCondition.append("name", strTableName);
			Document docTable = Util.find("id_Tables", findCondition);
			String strObjectType =(String)docRequest.get("objectType");
			String strDisplayName = "";
			if(docTable==null)
			{
				//strObjectType ="id_ObjectElements";
				//strAdminTable = "true";
				//strDisplayName = "test";
			}
			else
			{
				strObjectType = docTable.getString("objectType");
				strDisplayName = docTable.getString("displayName");
			}
			
			
			
			String[] arrColumnNames = null;
			String[] arrColumnDisplayNames = null;
			String[] arrColumnNos = null;
			String[] arrColumnPropeties = null;
			String[] arrColumnClassName = null;
			String strTableTitle = null;
			String JSPostProcessURL = "";
			String nestedURL = "formWithRelatedData.jsp?parentDataId=&objectType="+strObjectType+"&selectedMenu="+strSelectedMenu;
			//
			Document docColumnDisplayName=new Document();
			Document docTablePropertie=new Document();
			Document docColumnNos=new Document();
			Document docColumnClass=new Document();
			Document docColumnProperties=new Document();
			
		
			String strSearchKey="common";
			

			BasicDBObject findColumns=new BasicDBObject();
			findColumns.append("type", "Objects");
			findColumns.append("name", strObjectType);
			
			Document docColumns = Util.find(Context.strCollectionName, findColumns);
			ArrayList<Document> arrColumnDoc=null;
			if(docColumns!=null)
			{
				arrColumnDoc = (ArrayList<Document>) docColumns.get("connections");
			}
			if(docColumns==null || arrColumnDoc==null)
			{
				arrColumnNames = ((String)tableColumnConstants.adminTable.get(strSearchKey)).split(",");
				arrColumnDisplayNames = ((String)tableColumnConstants.adminTable.get(strSearchKey+"_Display")).split(",");
				strTableTitle = ((String)tableColumnConstants.adminTableTitle.get(strSearchKey));
				arrColumnNos = ((String)tableColumnConstants.adminTable.get(strSearchKey+"_Nos")).split(",");
				arrColumnPropeties = ((String)tableColumnConstants.adminTable.get(strSearchKey+"_Prop")).split(",");
				arrColumnClassName = ((String)tableColumnConstants.adminTable.get(strSearchKey+"_Class")).split(",");
				
				
				int cnt = 0;
				for(String strColumnName : arrColumnNames)
				{
					docColumnDisplayName.append(strColumnName, arrColumnDisplayNames[cnt]);
					docColumnNos.append(strColumnName, arrColumnNos[cnt]);
					docColumnClass.append(strColumnName, arrColumnClassName[cnt]);
					docColumnProperties.append(strColumnName, arrColumnPropeties[cnt]);
					cnt++;
				}
			}
			else
			{
				
				System.out.println("------------------------arrColumnDoc "+arrColumnDoc);
				System.out.println("------------------------arrColumnDoc Size "+arrColumnDoc.size());
				int arrayCount = arrColumnDoc.size()+1;
				arrColumnNames = new String[arrayCount];
				arrColumnDisplayNames = new String[arrayCount];
				strTableTitle = strObjectType;
				arrColumnNos = new String[arrayCount];
				arrColumnPropeties = new String[arrayCount];
				arrColumnClassName = new String[arrayCount];
				
				int cnt=0;
				arrColumnNames[cnt]="id";
				arrColumnDisplayNames[cnt]="id";
				arrColumnNos[cnt]=""+(cnt+1);
				arrColumnPropeties[cnt]="{'bSortable': false }";
				arrColumnClassName[cnt]="'noEdit'";
				
				docColumnClass.append("_id", "'noEdit'");
				docColumnProperties.append("_id", "{'bSortable': false }");
				docColumnDisplayName.append("_id", "id");
				docColumnNos.append("_id", cnt+"");
				cnt++;
				for(Document docTemp: arrColumnDoc)
				{
					BasicDBObject findColumn=new BasicDBObject();
					findColumn.append("_id", new ObjectId(docTemp.getString("objectId")));
					Document docColumn = Util.find(Context.strCollectionName, findColumn);
					
					docColumnClass.append(docColumn.getString("name"), "''");
					docColumnProperties.append(docColumn.getString("name"), "null");
					docColumnDisplayName.append(docColumn.getString("name"), docColumn.getString("displayName"));
					docColumnNos.append(docColumn.getString("name"), cnt+"");
				}
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
			if(!Util.checkEmpty(strParentDataId) && (strPageType==null || !strPageType.equals("Search")))
			{
				findData.append("_id", new ObjectId(strParentDataId));
				Document doc = Util.find(Context.strDataCollectionName, findData);
				ArrayList<Document> arrDocs = (ArrayList<Document>) doc.get("connections");
				if(arrDocs!=null)
				for(Document docTemp : arrDocs)
				{
					BasicDBObject findRelatedData=new BasicDBObject();
					findRelatedData.append("_id", new ObjectId(docTemp.getString("objectId")));
					findRelatedData.append("type", strObjectType);
					System.out.println("========= docTemp.getString(\"objectId\") "+docTemp.getString("objectId"));
					System.out.println("========= strObjectType "+strObjectType);
					Document docResult = Util.find(Context.strDataCollectionName, findRelatedData);
					if(docResult!=null)
						tableData.add(docResult);
				}
			}
			else
			{
				System.out.println("========= strObjectType Data insert start ");
				findData.append("type", strObjectType);
				FindIterable<Document> docs = Util.findMany(Context.strDataCollectionName, findData);
				for (Document doc : docs) {
					tableData.add(doc);
		        }
				System.out.println("========= strObjectType Data insert End ");
			}
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
