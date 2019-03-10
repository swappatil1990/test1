package idream2.main.core;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

public class ObjectType extends Constants{
	
	public static Document getSchemaObjectType(String strObjectType, Context context) throws Exception
	{
		BasicDBObject findColumns=new BasicDBObject();
		findColumns.append("type", "Objects");
		findColumns.append("name", strObjectType);
		
		Document docColumns = Util.find(context.getCollectionName(), findColumns, context);
		
		return docColumns;
	}
	
	public static FindIterable<Document> getAllSchemaObjectTypesForMenu(Context context) throws Exception
	{
		BasicDBObject findColumns=new BasicDBObject();
		findColumns.append("type", "Objects");
		findColumns.append("displayMainMenu", "true");
		FindIterable<Document> docColumns = Util.findManyWithSort(context.getCollectionName(), findColumns, "sequence", context);
		
		return docColumns;
	}
}
