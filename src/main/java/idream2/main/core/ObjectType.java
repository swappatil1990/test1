package idream2.main.core;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

public class ObjectType extends Constants{

	public static void addElement(String strName, String strDataType, String strDescription, String strObjectName, String defaultValue, int dataSize) throws Exception
	{
		Object ObjectId = getId(strObjectName);
		Document doc = new Document();
		doc.put(FIELD_ELEMENTNAME, strName);
		doc.put(FIELD_DATATYPE, strDataType);
		doc.put(FIELD_DESCRIPTION, strDescription);
		doc.put(FIELD_OBJECTID, ObjectId);
		doc.put(FIELD_DEFAULTVALUE, defaultValue);
		doc.put(FIELD_DATASIZE, dataSize);
		doc.put(FIELD_STATUS, VALUE_ACTIVE);
		
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_ELEMENTNAME, strName);
	    find.put(FIELD_OBJECTID, ObjectId);
		Document docCheck=Util.find(COLLECTION_ID_OBJECTELEMENTS, find);
		
		if(docCheck==null || docCheck.size()==0)
			Util.insert(COLLECTION_ID_OBJECTELEMENTS, doc);
		else
			throw new Exception("Error... Element Name is already added.");
	}
	
	public static FindIterable<Document> getElements(String strObjectName) throws Exception
	{
		Object ObjectId = getId(strObjectName);
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_OBJECTID, ObjectId);
	    FindIterable<Document> docCheck=Util.findMany(COLLECTION_ID_OBJECTELEMENTS, find);
	    return docCheck;
	}
	
	public static void removeElement(String strObjectName, String strElementName) throws Exception
	{
		Object ObjectId = getId(strObjectName);
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_ELEMENTNAME, strElementName);
	    find.put(FIELD_OBJECTID, ObjectId);
		Document docCheck=Util.find(COLLECTION_ID_OBJECTELEMENTS,find);
		if(docCheck!=null)
			Util.delete(docCheck.getObjectId(ID).toString(), COLLECTION_ID_OBJECTELEMENTS);
		else
			throw new Exception("Error... Element "+strElementName+" is not found in "+strObjectName+".");
	}
	
	public static void create(String strName, String strType, String strDescription, String strPrefix, int currentAutoNum, String strSuffix, int numLength) throws Exception
	{
		create(strName, strType, strDescription, strPrefix, currentAutoNum, strSuffix, numLength, false);
	}
	
	public static void create(String strName, String strType, String strDescription, String strPrefix, int currentAutoNum, String strSuffix, int numLength, boolean flagAdmin) throws Exception
	{
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_OBJECTNAME, strName);
		Document docCheck=Util.find(COLLECTION_ID_OBJECTS,find);
		
		if(docCheck==null)
		{
			Document doc = new Document();
			doc.put(FIELD_OBJECTNAME, strName);
			doc.put(FIELD_PARENTOBJECT, "");
			doc.put(FIELD_OBJECTTYPE, strType);
			doc.put(FIELD_DESCRIPTION, strDescription);
			doc.put(FIELD_SUFFIX, strSuffix);
			doc.put(FIELD_CURRENTAUTONUMBER, currentAutoNum);
			doc.put(FIELD_PREFIX, strPrefix);
			doc.put(FIELD_NUMBERLENGTH, numLength);
			doc.put(FIELD_STATEFLOW, "");
			doc.put(FIELD_STATUS, VALUE_ACTIVE);
			Util.insert(COLLECTION_ID_OBJECTS, doc);
			if(!flagAdmin)
			{
				Util.createCollection(PREFIX_SCHEMA+strName);
				Util.createCollection(PREFIX_SCHEMAREV+strName);
				Util.createCollection(PREFIX_SCHEMAREVOBJ+strName);
				Util.createCollection(PREFIX_HIST_SCHEMA+strName);
			}
			else
			{
				Util.createCollection(PREFIX_ID+strName);
			}
		}
		else
			throw new Exception("Error... Object "+strName+" is already exist.");
	}
	
	public static void delete(String strName) throws Exception
	{
		delete(strName, false);
	}
	public static void delete(String strName, boolean flagAdmin) throws Exception
	{
		Object DataId= getId(strName);
		Util.delete(DataId.toString(), COLLECTION_ID_OBJECTS);
		if(!flagAdmin)
		{
			Util.deleteCollection(PREFIX_SCHEMA+strName);
			Util.deleteCollection(PREFIX_SCHEMAREV+strName);
			Util.deleteCollection(PREFIX_SCHEMAREVOBJ+strName);
			Util.deleteCollection(PREFIX_HIST_SCHEMA+strName);
		}
		else
			Util.deleteCollection(PREFIX_ID+strName);
	}
	
	public static Object getId(String strObjectName) throws Exception
	{
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_OBJECTNAME, strObjectName);
	    
		Document doc=Util.find(COLLECTION_ID_OBJECTS,find);
		
		if(doc!=null)
			return doc.getObjectId(ID);
		else
			throw new Exception("Error... Wrong Object Name.");
	}
	
}
