package idream2.main.UI;

import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

import idream2.main.core.Constants;
import idream2.main.core.Util;

public class adminActionMethods extends Constants {
	public static Object beforeInsertObjectType(Map<String, Object> docRequest) throws Exception
	{
		Document doc=(Document)docRequest.get("Document");
		Util.createCollection(PREFIX_SCHEMA+doc.get("objectName").toString());
		Util.createCollection(PREFIX_SCHEMAREV+doc.get("objectName").toString());
		Util.createCollection(PREFIX_SCHEMAREVOBJ+doc.get("objectName").toString());
		Util.createCollection(PREFIX_HIST_SCHEMA+doc.get("objectName").toString());
		
		return null;
	}
	
	public static Object beforeDeleteObjectType(Map<String, Object> docRequest) throws Exception
	{
		String strObjectId=(String)docRequest.get("ObjectId");
		
		BasicDBObject find = new BasicDBObject();
		find.append("_id", new ObjectId(strObjectId));
		Document doc = Util.find("id_Objects", find);
		
		System.out.println("Inside beforeDeleteObjectType"+doc.get("objectName").toString());
		
		Util.deleteCollection(PREFIX_SCHEMA+doc.get("objectName").toString());
		Util.deleteCollection(PREFIX_SCHEMAREV+doc.get("objectName").toString());
		Util.deleteCollection(PREFIX_SCHEMAREVOBJ+doc.get("objectName").toString());
		Util.deleteCollection(PREFIX_HIST_SCHEMA+doc.get("objectName").toString());
		
		return null;
	}
	
	public static Object beforeInsertUser(Map<String, Object> docRequest) throws Exception
	{
		return null;
	}
	
	public static Object afterInsertUser(Map<String, Object> docRequest) throws Exception
	{
		return null;
	}
}
