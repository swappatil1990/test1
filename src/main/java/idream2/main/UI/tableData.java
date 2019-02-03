package idream2.main.UI;

import java.util.ArrayList;
import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

import idream2.main.core.Util;

public class tableData {
	
	public static ArrayList<Document> getAdminObjectType(Map<String, Object> docRequest) throws Exception
	{
		ArrayList<Document> tableData=new ArrayList<Document>();
		String strObjectType = (String)docRequest.get("objectType");
		String strAdminTable = (String)docRequest.get("adminTable");
		String[] arrColumnNames = null;
		String[] arrColumnDisplayNames = null;
		String[] arrColumnNos = null;
		String[] arrColumnPropeties = null;
		String[] arrColumnClassName = null;
		String strTableTitle = null;
		String JSPostProcessURL = "";
		
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
		docTablePropertie.append("postProcessURL", JSPostProcessURL);
		
		tableData.add(docTablePropertie);
		tableData.add(docColumnClass);
		tableData.add(docColumnProperties);
		tableData.add(docColumnDisplayName);
		tableData.add(docColumnNos);
		
		BasicDBObject findData=new BasicDBObject();
		
		FindIterable<Document> docs = Util.findMany(strObjectType, findData);
		for (Document doc : docs) {
			tableData.add(doc);
        }
		
		return tableData;
	}
}
