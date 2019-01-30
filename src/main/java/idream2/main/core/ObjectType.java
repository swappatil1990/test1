package idream2.main.core;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

public class ObjectType {

	public static void addElement(String strName, String strDataType, String strDescription, String strObjectName, String defaultValue, int dataSize) throws Exception
	{
		Object ObjectId = getId(strObjectName);
		Document doc = new Document();
		doc.put("elementName", strName);
		doc.put("dataType", strDataType);
		doc.put("description", strDescription);
		doc.put("objectId", ObjectId);
		doc.put("defaultValue", defaultValue);
		doc.put("dataSize", dataSize);
		doc.put("status", "Active");
		
		BasicDBObject find = new BasicDBObject();
	    find.put("elementName", strName);
	    find.put("objectId", ObjectId);
		Document docCheck=Util.find("id_ObjectElements",find);
		
		if(docCheck==null || docCheck.size()==0)
			Util.insert("id_ObjectElements", doc);
		else
			throw new Exception("Error... Element Name is already added.");
	}
	
	public static FindIterable<Document> getElements(String strObjectName)
	{
		Object ObjectId = getId(strObjectName);
		BasicDBObject find = new BasicDBObject();
	    find.put("objectId", ObjectId);
	    FindIterable<Document> docCheck=Util.findMany("id_ObjectElements",find);
	    return docCheck;
	}
	
	public static void removeElement(String strObjectName, String strElementName) throws Exception
	{
		Object ObjectId = getId(strObjectName);
		BasicDBObject find = new BasicDBObject();
	    find.put("elementName", strElementName);
	    find.put("objectId", ObjectId);
		Document docCheck=Util.find("id_ObjectElements",find);
		if(docCheck!=null)
			Util.delete(docCheck.getObjectId("_id").toString(), "id_ObjectElements");
		else
			throw new Exception("Error... Element "+strElementName+" is not found in "+strObjectName+".");
	}
	
	public static void create(String strName, String strType, String strDescription, String strPrefix, int currentAutoNum, String strSuffix, int numLength) throws Exception
	{
		BasicDBObject find = new BasicDBObject();
	    find.put("objectName", strName);
		Document docCheck=Util.find("id_Objects",find);
		
		if(docCheck==null)
		{
			Document doc = new Document();
			doc.put("objectName", strName);
			doc.put("parentObject", "");
			doc.put("objectType", strType);
			doc.put("description", strDescription);
			doc.put("prefix", strPrefix);
			doc.put("currentAutoNumber", currentAutoNum);
			doc.put("suffix", strSuffix);
			doc.put("numberLength", numLength);
			doc.put("stateflow", "");
			doc.put("status", "Active");
			Util.insert("id_Objects", doc);
			
			Util.createCollection("schema_"+strName);
			Util.createCollection("schemaRev_"+strName);
			Util.createCollection("schemaRevObj_"+strName);
			Util.createCollection("schemaHist_"+strName);
		}
		else
			throw new Exception("Error... Object "+strName+" is already exist.");
	}
	
	public static void delete(String strName)
	{
		Object DataId= getId(strName);
		Util.delete(DataId.toString(), "id_Objects");
		Util.deleteCollection("schema_"+strName);
		Util.deleteCollection("schemaRev_"+strName);
		Util.deleteCollection("schemaRevObj_"+strName);
		Util.deleteCollection("schemaHist_"+strName);
	}
	
	public static Object getId(String strObjectName)
	{
		BasicDBObject find = new BasicDBObject();
	    find.put("objectName", strObjectName);
	    
		Document doc=Util.find("id_Objects",find);
		return doc.getObjectId("_id");
	}
	
}
