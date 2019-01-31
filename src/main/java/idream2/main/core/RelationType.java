package idream2.main.core;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

public class RelationType extends Constants {
	
	public static void addElement(String strName, String strDataType, String strDescription, String strRelationName, String defaultValue, int dataSize) throws Exception
	{
		Object ObjectId = getId(strRelationName);
		Document doc = new Document();
		doc.put(FIELD_ELEMENTNAME, strName);
		doc.put(FIELD_DATATYPE, strDataType);
		doc.put(FIELD_DESCRIPTION, strDescription);
		doc.put(FIELD_RELATIONID, ObjectId);
		doc.put(FIELD_DEFAULTVALUE, defaultValue);
		doc.put(FIELD_DATASIZE, dataSize);
		doc.put(FIELD_STATUS, VALUE_ACTIVE);
		
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_ELEMENTNAME, strName);
	    find.put(FIELD_RELATIONID, ObjectId);
		Document docCheck=Util.find(COLLECTION_ID_RELATIONELEMENTS,find);
		
		if(docCheck==null || docCheck.size()==0)
			Util.insert(COLLECTION_ID_RELATIONELEMENTS, doc);
		else
			throw new Exception("Error... Element Name is already added.");
	}
	
	public static FindIterable<Document> getElements(String strObjectName) throws Exception
	{
		Object ObjectId = getId(strObjectName);
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_RELATIONID, ObjectId);
	    FindIterable<Document> docCheck=Util.findMany(COLLECTION_ID_RELATIONELEMENTS,find);
	    return docCheck;
	}
	
	public static void removeElement(String strRelationName, String strElementName) throws Exception
	{
		Object ObjectId = getId(strRelationName);
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_ELEMENTNAME, strElementName);
	    find.put(FIELD_RELATIONID, ObjectId);
		Document docCheck=Util.find(COLLECTION_ID_RELATIONELEMENTS,find);
		if(docCheck!=null)
			Util.delete(docCheck.getObjectId(ID).toString(), COLLECTION_ID_RELATIONELEMENTS);
		else
			throw new Exception("Error... Element "+strElementName+" is not found in "+strRelationName+".");
	}
	
	public static void create(String strName, String strType, String strDescription, String strRelationType) throws Exception
	{
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_RELATIONNAME, strName);
		Document docCheck=Util.find(COLLECTION_ID_RELATIONS,find);
		
		if(docCheck==null)
		{
			Document doc = new Document();
			doc.put(FIELD_RELATIONNAME, strName);
			doc.put(FIELD_PARENTOBJECT, "");
			doc.put(FIELD_OBJECTTYPE, strType);
			doc.put(FIELD_DESCRIPTION, strDescription);
			doc.put(FIELD_RELATIONTYPE, "");
			doc.put(FIELD_STATUS, VALUE_ACTIVE);
			Util.insert(COLLECTION_ID_RELATIONS, doc);
			
			Util.createCollection(PREFIX_SCHEMA_REL+strName);
			Util.createCollection(PREFIX_HIST_SCHEMA_REL+strName);
		}
		else
			throw new Exception("Error... Object "+strName+" is already exist.");
	}
	
	public static void delete(String strName) throws Exception
	{
		Object DataId= getId(strName);
		Util.delete(DataId.toString(), COLLECTION_ID_RELATIONS);
		Util.deleteCollection(PREFIX_SCHEMA_REL+strName);
		Util.deleteCollection(PREFIX_HIST_SCHEMA_REL+strName);
	}
	
	public static ObjectId getId(String strRelationName) throws Exception
	{
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_RELATIONNAME, strRelationName);
	    
		Document doc=Util.find(COLLECTION_ID_RELATIONS,find);
		if(doc!=null)
			return doc.getObjectId(ID);
		else
			throw new Exception("Error... Wrong Relation Name.");
	}
	
	public static void addObjects(String strName, String strObjectName, String strDirection) throws Exception
	{
		Object RelationId = getId(strName);
		Object ObjectId = ObjectType.getId(strObjectName);
		Document doc = new Document();
		doc.put(FIELD_RELATIONID, RelationId);
		doc.put(FIELD_OBJECTID, ObjectId);
		doc.put(FIELD_STATUS, VALUE_ACTIVE);
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_RELATIONID, RelationId);
	    find.put(FIELD_OBJECTID, ObjectId);
		Document docCheck=Util.find("id_Relation"+strDirection+"Objects", find);
		if(docCheck==null )
			Util.insert("id_Relation"+strDirection+"Objects", doc);
		else
			throw new Exception("Error... Object Name is already added in "+strDirection+" Relation Objects.");
	}
	
	public static void removeObjects(String strName, String strObjectName, String strDirection) throws Exception
	{
		Object RelationId = getId(strName);
		Object ObjectId = ObjectType.getId(strObjectName);
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_RELATIONID, RelationId);
	    find.put(FIELD_OBJECTID, ObjectId);
		Document docCheck=Util.find("id_Relation"+strDirection+"Objects", find);
		if(docCheck!=null )
			Util.delete(docCheck.getObjectId(ID).toString(),"id_Relation"+strDirection+"Objects");
		else
			throw new Exception("Error... Object Name is not exist in "+strDirection+" Relation Objects.");
	}
	
	public static void addToObjects(String strName, String strObjectName) throws Exception
	{
		addObjects(strName, strObjectName, DIRECTION_TO);
	}
	
	public static void addFromObjects(String strName, String strObjectName) throws Exception
	{
		addObjects(strName, strObjectName, DIRECTION_FROM);
	}
	
	public static void removeToObjects(String strName, String strObjectName) throws Exception
	{
		removeObjects(strName, strObjectName, DIRECTION_TO);
	}
	
	public static void removeFromObjects(String strName, String strObjectName) throws Exception
	{
		removeObjects(strName, strObjectName, DIRECTION_FROM);
	}
	
	public static boolean checkObjectExistInRelation(ObjectId relationTypeId, ObjectId objectTypeId, String strDirection) throws Exception
	{
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_RELATIONID, relationTypeId);
	    find.put(FIELD_OBJECTID, objectTypeId);
		Document docCheck=Util.find("id_Relation"+strDirection+"Objects", find);
		if(docCheck==null)
			return false;
		else
			return true;
				
	}
}
