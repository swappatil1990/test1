package idream2.main.core;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

public class ObjectData extends Constants{
	public static void insert(String strObjectName, String strDataName, String strVersion, Document doc) throws Exception
	{
		Object objectId = ObjectType.getId(strObjectName);
		Document docCheck = getDocument(strObjectName, strDataName, strVersion);
		
		if(docCheck==null || docCheck.size()==0)
		{
			Document basicDoc = new Document();
			basicDoc.put(FIELD_OBJECTTYPEID, objectId);
			basicDoc.put(FIELD_NAME, strDataName);
			basicDoc.put(FIELD_VERSION, strVersion);
			basicDoc.put(FIELD_STATUS, VALUE_ACTIVE);
			Util.insert(COLLECTION_ID_OBJECTBASICDATA, basicDoc);
			
			Document docId = getDocument(strObjectName, strDataName, strVersion);
			
			doc.append(FIELD_OBJECTID, docId.getObjectId(ID));
			
			Util.insert(PREFIX_SCHEMA+strObjectName, doc);
		}
		else
			throw new Exception("Error... Object Data is already added for same Object Type, Data Name and Version.");
	}
	
	public static void delete(String strObjectName, String strDataName, String strVersion) throws Exception
	{
		Document docCheck = getDocument(strObjectName, strDataName, strVersion);
		
		if(docCheck!=null)
		{
			Util.delete(docCheck.getObjectId(ID).toString(), COLLECTION_ID_OBJECTBASICDATA);
			Util.delete(docCheck.getObjectId(ID).toString(), FIELD_OBJECTID, PREFIX_SCHEMA+strObjectName);
			Util.delete(docCheck.getObjectId(ID).toString(), FIELD_OBJECTID, PREFIX_SCHEMAREV+strObjectName);
			Util.delete(docCheck.getObjectId(ID).toString(), FIELD_OBJECTID, PREFIX_SCHEMAREVOBJ+strObjectName);
			Util.delete(docCheck.getObjectId(ID).toString(), FIELD_OBJECTID, PREFIX_HIST_SCHEMA+strObjectName);
		}
		else
			throw new Exception("Error... Object Data is not available for given Object Type, Data Name and Version.");
	}
	
	public static void update(String strObjectName, String strDataName, String strVersion, Document doc) throws Exception
	{
		Document docCheck = getDocument(strObjectName, strDataName, strVersion);
		
		if(docCheck!=null)
		{
			Util.update(docCheck.getObjectId(ID).toString(), FIELD_OBJECTID,PREFIX_SCHEMA+strObjectName, doc);
		}
		else
			throw new Exception("Error... Object Data is not available for given Object Type, Data Name and Version.");
	}
	
	public static Document getData(String strObjectName, String strDataName, String strVersion) throws Exception
	{
		
		Document docCheck = getDocument(strObjectName, strDataName, strVersion);
		
		BasicDBObject findData = new BasicDBObject();
		findData.put(FIELD_OBJECTID, docCheck.getObjectId(ID));
		Document docData=Util.find(PREFIX_SCHEMA+strObjectName, findData);
		docCheck.putAll(docData);
		return docCheck;
	}
	
	public static ObjectId getObjectTypeId(String strDataId) throws Exception
	{
		BasicDBObject findData = new BasicDBObject();
		findData.put(ID, new ObjectId(strDataId));
		Document docData=Util.find(COLLECTION_ID_OBJECTBASICDATA, findData);
		
		return docData.getObjectId(FIELD_OBJECTTYPEID);
	}
	
	public static Document getDocument(String strObjectName, String strDataName, String strVersion) throws Exception
	{
		Object objectId = ObjectType.getId(strObjectName);
		if(objectId!=null)
		{
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_OBJECTTYPEID, objectId);
	    find.put(FIELD_NAME, strDataName);
	    find.put(FIELD_VERSION, strVersion);
		
	    return Util.find(COLLECTION_ID_OBJECTBASICDATA, find);
		}
		else
			throw new Exception("Error... Wrong object Name.");
	}
	
	public static String getId(String strObjectName, String strDataName, String strVersion) throws Exception
	{
		return getDocument(strObjectName, strDataName, strVersion).getObjectId(ID).toString();
	}
	
	public static void getRelated()
	{
		
	}
	
	public static void promote()
	{
		
	}
	
	public static void demote()
	{
		
	}
	
	public static void nextVersion()
	{
		
	}
}
