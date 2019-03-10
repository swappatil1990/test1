package idream2.main.core;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

public class ObjectData extends Constants{
	public static FindIterable<Document> getAllObjectData(String strTypeName, Context context) throws Exception
	{
		BasicDBObject findColumn=new BasicDBObject();
		findColumn.append("type", strTypeName);
		FindIterable<Document> docs = Util.findMany(context.getDataCollectionName(), findColumn, context);
		return docs;
	}
	public static FindIterable<Document> getAllSchemaObjectData(String strTypeName, Context context) throws Exception
	{
		BasicDBObject findColumn=new BasicDBObject();
		findColumn.append("type", strTypeName);
		FindIterable<Document> docs = Util.findMany(context.getCollectionName(), findColumn, context);
		return docs;
	}
	
	public static Document getObjectData(String strObjectId, Context context) throws Exception
	{
		BasicDBObject findColumn=new BasicDBObject();
		findColumn.append("_id", new ObjectId(strObjectId));
		Document docColumn = Util.find(context.getDataCollectionName(), findColumn, context);
		
		return docColumn;
	}
	
	public static String getObjectDataName(String strObjectId, Context context) throws Exception
	{
		BasicDBObject findColumn=new BasicDBObject();
		findColumn.append("_id", new ObjectId(strObjectId));
		Document docColumn = Util.find(context.getDataCollectionName(), findColumn, context);
		
		return docColumn.getString("name");
	}
	
	public static String getSchemaObjectDataName(String strObjectId, Context context) throws Exception
	{
		BasicDBObject findColumn=new BasicDBObject();
		findColumn.append("_id", new ObjectId(strObjectId));
		Document docColumn = Util.find(context.getCollectionName(), findColumn, context);
		
		return docColumn.getString("name");
	}
	
	public static String getSuperSchemaObjectDataName(String strObjectId, Context context) throws Exception
	{
		BasicDBObject findColumn=new BasicDBObject();
		findColumn.append("_id", new ObjectId(strObjectId));
		Document docColumn = Util.find(context.getSuperAdminCollectionName(), findColumn, context);
		
		return docColumn.getString("name");
	}
	
	public static Document getObjectData(String strObjectType, String strObjectName, String strVersion, Context context) throws Exception
	{
		BasicDBObject findColumn=new BasicDBObject();
		findColumn.append("name", strObjectName);
		findColumn.append("type", strObjectType);
		Document docColumn = Util.find(context.getDataCollectionName(), findColumn, context);
		
		return docColumn;
	}
	
	public static Document getSchemaObjectData(String strObjectType, String strObjectName, String strVersion, Context context) throws Exception
	{
		BasicDBObject findColumn=new BasicDBObject();
		findColumn.append("name", strObjectName);
		findColumn.append("type", strObjectType);
		Document docColumn = Util.find(context.getCollectionName(), findColumn, context);
		
		return docColumn;
	}
	
	public static Document getObjectData(String strObjectId, String strType, Context context) throws Exception
	{
		BasicDBObject findColumn=new BasicDBObject();
		findColumn.append("_id", new ObjectId(strObjectId));
		findColumn.append("type", strType);
		Document docColumn = Util.find(context.getDataCollectionName(), findColumn, context);
		
		return docColumn;
	}
	
	public static Document getSchemaObjectData(String strObjectId, Context context) throws Exception
	{
		BasicDBObject findColumn=new BasicDBObject();
		findColumn.append("_id", new ObjectId(strObjectId));
		Document docColumn = Util.find(context.getCollectionName(), findColumn, context);
		
		return docColumn;
	}
	
	public static Document getSuperSchemaObjectData(String strObjectType, String strObjectName, String strVersion, Context context) throws Exception
	{
		BasicDBObject findColumn=new BasicDBObject();
		findColumn.append("name", strObjectName);
		findColumn.append("type", strObjectType);
		Document docColumn = Util.find(context.getSuperAdminCollectionName(), findColumn, context);
		
		return docColumn;
	}
	
	public static String getDefaultStatus(String strobjectType, Context context) throws Exception
	{
		
		String strStateFlowId = getSchemaObjectData("Objects", strobjectType, "", context).getObjectId("stateFlow").toString();
		Document doc = getSchemaObjectData(strStateFlowId, context);
		ArrayList<Document> arrColumnDoc=null;
		if(doc!=null)
		{
			arrColumnDoc = (ArrayList<Document>) doc.get("connections");
			
			for(Document docTemp: arrColumnDoc)
			{
				Document docColumn =ObjectData.getSchemaObjectData(docTemp.getString("objectId"), context);
				if(docColumn.getObjectId("startState").toString().equals(getSuperSchemaObjectData("List_Items", "true", "", context).getObjectId("_id").toString()))
				{
					return docColumn.getObjectId("_id").toString();
				}
			}
		}
		
		return "";
	}
}
