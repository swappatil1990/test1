package idream2.main.core;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

public class RelationData extends Constants{
	
	public static void connect(String strFromObjectId, String strToOnjectId, String strRelationName, Document doc) throws Exception
	{
		ObjectId relationTypeId = RelationType.getId(strRelationName);
		ObjectId fromObject = ObjectData.getObjectTypeId(strFromObjectId);
		ObjectId toObject = ObjectData.getObjectTypeId(strToOnjectId);
		boolean fromObj = RelationType.checkObjectExistInRelation(relationTypeId, fromObject, DIRECTION_FROM);
		boolean toObj = RelationType.checkObjectExistInRelation(relationTypeId, toObject, DIRECTION_TO);
		if(fromObj && toObj)
		{
			Document docCheck=getDocument(strFromObjectId, strToOnjectId, strRelationName);
			
			if(docCheck==null)
			{
				
				Document docBasic=new Document();
				docBasic.append(FIELD_FROMOBJECTID, new ObjectId(strFromObjectId));
				docBasic.append(FIELD_TOOBJECTID, new ObjectId(strToOnjectId));
				docBasic.append(FIELD_RELATIONTYPEID, relationTypeId);
				docBasic.putAll(doc);
				Util.insert(PREFIX_SCHEMA_REL+strRelationName, docBasic);
			}
			else
				throw new Exception("Error... Same connection is already exist in both data.");
		}
		else
			throw new Exception("Error... Object Id not a part on given relationship.");
	}
	
	public static void connect(String strFromO, String strFromN, String strFromV, String strToO, String strToN, String strToV, String strRelationName, Document doc) throws Exception
	{
		String strFromObjectId = ObjectData.getId(strFromO, strFromN, strFromV);
		String strToObjectId = ObjectData.getId(strToO, strToN, strToV);
		
		connect(strFromObjectId, strToObjectId, strRelationName, doc);
	}
	
	public static void disconnect(String strFromObjectId, String strToOnjectId, String strRelationName) throws Exception
	{
		Document docCheck=getDocument(strFromObjectId, strToOnjectId, strRelationName);
		
		if(docCheck!=null)
		{
			Util.delete(docCheck.getObjectId(ID).toString(), PREFIX_SCHEMA_REL+strRelationName);
			Util.delete(docCheck.getObjectId(ID).toString(), FIELD_OBJECTID, PREFIX_HIST_SCHEMA_REL+strRelationName);
		}
		else
			throw new Exception("Error... Connection not exist in given data.");
	}
	
	public static void disconnect(String strFromO, String strFromN, String strFromV, String strToO, String strToN, String strToV, String strRelationName) throws Exception
	{
		String strFromObjectId = ObjectData.getId(strFromO, strFromN, strFromV);
		String strToObjectId = ObjectData.getId(strToO, strToN, strToV);
		
		disconnect(strFromObjectId, strToObjectId, strRelationName);
	}
	
	public static Document getDocument(String strFromObjectId, String strToOnjectId, String strRelationName) throws Exception
	{
		Object relationTypeId = RelationType.getId(strRelationName);
		BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_FROMOBJECTID, new ObjectId(strFromObjectId));
	    find.put(FIELD_TOOBJECTID,  new ObjectId(strToOnjectId));
	    find.put(FIELD_RELATIONTYPEID, relationTypeId);
	    
		return Util.find(PREFIX_SCHEMA_REL+strRelationName, find);
	}
	
	public static void update(String strFromObjectId, String strToOnjectId, String strRelationName, Document doc) throws Exception
	{
		Document docCheck=getDocument(strFromObjectId, strToOnjectId, strRelationName);
		
		if(docCheck!=null)
		{
			Object relationTypeId = RelationType.getId(strRelationName);
			Document docBasic=new Document();
			docBasic.append(FIELD_FROMOBJECTID, new ObjectId(strFromObjectId));
			docBasic.append(FIELD_TOOBJECTID, new ObjectId(strToOnjectId));
			docBasic.append(FIELD_RELATIONTYPEID, relationTypeId);
			docBasic.putAll(doc);
			Util.update(docCheck.getObjectId(ID).toString(), PREFIX_SCHEMA_REL+strRelationName, docBasic);
		}
		else
			throw new Exception("Error... Same connection is already exist in both data.");
	}
	
	public static void update(String strFromO, String strFromN, String strFromV, String strToO, String strToN, String strToV, String strRelationName, Document doc) throws Exception
	{
		String strFromObjectId = ObjectData.getId(strFromO, strFromN, strFromV);
		String strToObjectId = ObjectData.getId(strToO, strToN, strToV);
		
		update(strFromObjectId, strToObjectId, strRelationName, doc);
	}
}
