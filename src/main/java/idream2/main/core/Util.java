package idream2.main.core;

import java.lang.reflect.Method;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.result.UpdateResult;

public class Util extends Constants{
	static MongoDatabase database=null;
	static MongoClient mongoClient =null;
	public static void connectedDB()
	{
		if(database==null)
		{
			//mongodb://swap:wws2RZ1bJia6KYS4@mycluster-shard-00-00-ko8ql.mongodb.net:27017,mycluster-shard-00-01-ko8ql.mongodb.net:27017,mycluster-shard-00-02-ko8ql.mongodb.net:27017/test?ssl=true&replicaSet=MyCluster-shard-0&authSource=admin&retryWrites=true
			//mongodb+srv://swap:wws2RZ1bJia6KYS4@cluster0-ko8ql.mongodb.net/test?retryWrites=true
			mongoClient = MongoClients.create("mongodb+srv://swap:wws2RZ1bJia6KYS4@cluster0-ko8ql.mongodb.net/test?retryWrites=true");
			database = mongoClient.getDatabase("mydb");
		}
	}
	
	public static String insert(String strTable, Document doc) throws Exception
	{
		checkLogin();
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		collection.insertOne(doc);
		
		BasicDBObject find = new BasicDBObject();
		for ( String key : doc.keySet() ) {
			find.put(key, doc.get(key));
		}
		
		Document docResult = find(strTable, find);
		System.out.println("docResult.getObjectId(\"_id\"): "+docResult.getObjectId("_id"));
		
		return docResult.getObjectId("_id").toString();
	}
	
	public static void delete(String strDataId, String strTable) throws Exception
	{
		checkLogin();
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		collection.deleteOne(new Document(ID, new ObjectId(strDataId)));
	}
	
	public static void delete(String strDataId, String strElementName, String strTable) throws Exception
	{
		checkLogin();
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		BasicDBObject find = new BasicDBObject();
	    find.put(strElementName, new ObjectId(strDataId));
		collection.deleteOne(find);
	}
	
	public static String update(String strDataId, String strTable, Document mData) throws Exception
	{
		
		checkLogin();
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		UpdateResult resultDoc= collection.updateOne(new Document(ID, new ObjectId(strDataId)), new Document(SET, mData));
		
		return resultDoc.getModifiedCount()+"";
	}
	
	public static void update(String strDataId, String strElementName, String strTable, Document mData) throws Exception
	{
		checkLogin();
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		BasicDBObject find = new BasicDBObject();
	    find.put(strElementName, new ObjectId(strDataId));
		collection.updateOne(find, new Document(SET, mData));
	}
	
	public static Document print(String strDataId, String strTable) throws Exception
	{
		checkLogin();
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		FindIterable<Document> collectionResult = collection.find(new Document(ID, new ObjectId(strDataId)));
		if(collectionResult!=null)
			return collectionResult.first();
		else
			throw new Exception("Error... Data not found in print.");
		
	}
	
	public static Document find(String strTable, BasicDBObject findCondition) throws Exception
	{
		if(Context.flagLogin)
			checkLogin();
		connectedDB();
		
		MongoCollection<Document> collection = database.getCollection(strTable);
		FindIterable<Document> collectionResult = collection.find(findCondition);
		
		return collectionResult.first();
	}
	
	public static FindIterable<Document> findMany(String strTable, BasicDBObject findCondition) throws Exception
	{
		checkLogin();
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		FindIterable<Document> collectionResult = collection.find(findCondition);
		return collectionResult;
	}
	
	public static void createCollection(String strCollectionName) throws Exception
	{
		checkLogin();
		connectedDB();
		database.createCollection(strCollectionName);
	}
	
	public static void deleteCollection(String strCollectionName) throws Exception
	{
		checkLogin();
		connectedDB();
		MongoCollection<Document> myCollection = database.getCollection(strCollectionName);
		myCollection.drop();
	}
	
	public static Object getNextSequence(String strCollectionName, String strId, String strColumnName) throws Exception
	{
		checkLogin();
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strCollectionName);
	    BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_OBJECTNAME, strId);
	    BasicDBObject update = new BasicDBObject();
	    update.put(INC, new BasicDBObject(strColumnName, 1));
	    Document obj =  collection.findOneAndUpdate(find, update);
	    if(obj!=null)
	    	return obj.get(strColumnName);
	    else
	    	throw new Exception("Error... Data not found in getNextSequence.");
	}
	
	public static int getObjectDataSequence(String strObjectName) throws Exception{
		int cnt=(int) getNextSequence(COLLECTION_ID_OBJECTS, strObjectName, FIELD_CURRENTAUTONUMBER);
	    return cnt;
	}
	
	public static void clearAllCustomData() throws Exception
	{
		checkLogin();
		connectedDB();
		MongoIterable<String> collectionList = database.listCollectionNames();
		for(String str:collectionList)
		{
			if(str.indexOf("id_")>-1)
			{
				BasicDBObject document = new BasicDBObject();
				//document.append("dataType", "Admin");
				database.getCollection(str).deleteMany(document);
			}
			else
			{
				deleteCollection(str);
			}
		}
		
	}
	
	public static String checkLogin() throws Exception
	{
		if(Context.contextId!=null && !Context.contextId.equals(""))
			return Context.contextId;
		else
			throw new Exception("Error... Login context not found.");
	}
	
	public static Object callMethod(String className, String methodName, Map<String, Object> arg) throws Exception
	{
		Class classRef = Class.forName("idream2.main.UI."+className);
		Method instanceMethod = classRef.getMethod(methodName, Map.class);
		
		return (Object) instanceMethod.invoke(classRef, arg);
	}
}
