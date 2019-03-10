package idream2.main.core;

import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

public class Events {
	public static void runObjectEvent(String strObjectName, String strEventOn, String strEventAction, Map<String, Object> argMap, Context context) throws Exception
	{
		BasicDBObject findCondition=new BasicDBObject();
		findCondition.append("objectName", strObjectName);
		findCondition.append("eventOn", strEventOn);
		findCondition.append("eventAction", strEventAction);
		FindIterable<Document> docs= Util.findMany("id_ObjectEvents", findCondition, context);
		for(Document doc : docs)
		{
			String strClass = (String) doc.get("class");
			String strMethod = (String) doc.get("method");
			Util.callMethod(strClass,strMethod, argMap);
		}
	}
	
	public static void runRelationEvent(String strRelationId, String strEventOn, String strEventAction, Map<String, Object> argMap, Context context) throws Exception
	{
		BasicDBObject findCondition=new BasicDBObject();
		findCondition.append("objectName", strRelationId);
		findCondition.append("eventOn", strEventOn);
		findCondition.append("eventAction", strEventAction);
		FindIterable<Document> docs= Util.findMany("id_RelationEvents", findCondition, context);
		for(Document doc : docs)
		{
			String strClass = (String) doc.get("class");
			String strMethod = (String) doc.get("method");
			Util.callMethod(strClass,strMethod, argMap);
		}
	}
}
