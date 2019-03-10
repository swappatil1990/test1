package idream2.main.UI;

import java.util.ArrayList;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

import idream2.main.core.Context;
import idream2.main.core.ObjectData;
import idream2.main.core.ObjectType;
import idream2.main.core.Util;

public class tableData {
	
	public static ArrayList<Document> getCommonTableData(Map<String, Object> docRequest)
	{
		Context context = null;
		if(docRequest.get("context")!=null)
			context = (Context)docRequest.get("context");
		try
		{
			ArrayList<Document> tableData=new ArrayList<Document>();
			String strParentDataId = (String)docRequest.get("parentDataId");
			String strTableName = (String)docRequest.get("tableName");
			String strSelectedMenu = (String)docRequest.get("selectedMenu");
			String strPageType = (String)docRequest.get("pageType");
			BasicDBObject findCondition=new BasicDBObject();
			findCondition.append("name", strTableName);
			Document docTable = Util.find("id_Tables", findCondition, context);
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
			Document docColumnData=new Document();
		
			String strSearchKey="common";
			

			Document docColumns = ObjectType.getSchemaObjectType(strObjectType, context);
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
				docColumnData.append("_id", "");
				docColumnProperties.append("_id", "{'bSortable': false }");
				docColumnDisplayName.append("_id", "id");
				docColumnNos.append("_id", cnt+"");
				cnt++;
				for(Document docTemp: arrColumnDoc)
				{
					Document docColumn = ObjectData.getSchemaObjectData(docTemp.getString("objectId"), context);
					String strColumnClassData = "";
					try
					{
						strColumnClassData = ObjectData.getSuperSchemaObjectDataName(docColumn.getObjectId("inputType").toString(), context);
					}
					catch (Exception e) {
						// TODO: handle exception
					}
					
					docColumnClass.append(docColumn.getString("name"), "'"+strColumnClassData+"'");
					docColumnProperties.append(docColumn.getString("name"), "null");
					docColumnDisplayName.append(docColumn.getString("name"), docColumn.getString("displayName"));
					docColumnNos.append(docColumn.getString("name"),  docColumn.getString("sequence"));
					if(strColumnClassData.equals("list"))
					{
						String strOptionList = "<option></option>";
						String strOptionListWithValues = "";
						try
						{
							//Document doc=ObjectData.getObjectData("Lists", docColumn.getString("listName"), "");
							Document doc=ObjectData.getSchemaObjectData("Lists", ObjectData.getSchemaObjectDataName(docColumn.getObjectId("listName").toString(), context), "", context);
							ArrayList<Document> arrDocs = (ArrayList<Document>) doc.get("connections");
							
							for(Document docTmp : arrDocs)
							{
								Document docResult = ObjectData.getSchemaObjectData(docTmp.getString("objectId"), context);
								if(docResult!=null)
								{
									strOptionList += "<option>"+docResult.getString("name")+"</option>";
									strOptionListWithValues +=docResult.getObjectId("_id").toString()+":"+docResult.getString("name")+",";
								}
							}
						}
						catch (Exception e) {
							// TODO: handle exception
						}
						docColumnData.append(docColumn.getString("name"), strOptionList);
						docColumnData.append(docColumn.getString("name")+"Arr", strOptionListWithValues);
					}
					else if(strColumnClassData.equals("object"))
					{
						String strOptionList ="<option></option>";
						String strOptionListWithValues ="";
						try
						{
							FindIterable<Document> arrDocs=ObjectData.getAllObjectData(ObjectData.getSuperSchemaObjectDataName(docColumn.getObjectId("objectName").toString(), context), context);
							//ArrayList<Document> arrDocs = (ArrayList<Document>) doc.get("connections");
							
							
							for(Document docTmp : arrDocs)
							{
								strOptionList += "<option>"+docTmp.getString("name")+"</option>";
								strOptionListWithValues += docTmp.getObjectId("_id").toString()+":"+docTmp.getString("name")+",";
							}
						}
						catch (Exception e) {
							// TODO: handle exception
						}
						docColumnData.append(docColumn.getString("name"), strOptionList);
						docColumnData.append(docColumn.getString("name")+"Arr", strOptionListWithValues);
					}
					else
					{
						docColumnData.append(docColumn.getString("name"), "");
					}
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
			tableData.add(docColumnData);
			
			if(!Util.checkEmpty(strParentDataId) && (strPageType==null || !strPageType.equals("Search")))
			{
				Document doc = ObjectData.getObjectData(strParentDataId, context);
				
				ArrayList<Document> arrDocs = (ArrayList<Document>) doc.get("connections");
				if(arrDocs!=null)
				for(Document docTemp : arrDocs)
				{
					Document docResult = ObjectData.getObjectData(docTemp.getString("objectId"), strObjectType, context);
					
					if(docResult!=null)
						tableData.add(docResult);
				}
			}
			else
			{
				FindIterable<Document> docs = ObjectData.getAllObjectData(strObjectType, context);
				for (Document doc : docs) {
					tableData.add(doc);
		        }
			}
			return tableData;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
