package idream2.main.core;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class Util extends Constants{
	static MongoDatabase database=null;
	public static void connectedDB()
	{
		if(database==null)
		{
			//mongodb://swap:wws2RZ1bJia6KYS4@mycluster-shard-00-00-ko8ql.mongodb.net:27017,mycluster-shard-00-01-ko8ql.mongodb.net:27017,mycluster-shard-00-02-ko8ql.mongodb.net:27017/test?ssl=true&replicaSet=MyCluster-shard-0&authSource=admin&retryWrites=true
			//mongodb+srv://swap:wws2RZ1bJia6KYS4@cluster0-ko8ql.mongodb.net/test?retryWrites=true
			MongoClient mongoClient = MongoClients.create("mongodb+srv://swap:wws2RZ1bJia6KYS4@cluster0-ko8ql.mongodb.net/test?retryWrites=true");
			database = mongoClient.getDatabase("mydb");
		}
	}
	
	public static void insert(String strTable, Document doc) throws Exception
	{
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		collection.insertOne(doc);
	}
	
	public static void delete(String strDataId, String strTable)
	{
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		collection.deleteOne(new Document(ID, new ObjectId(strDataId)));
	}
	
	public static void delete(String strDataId, String strElementName, String strTable)
	{
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		BasicDBObject find = new BasicDBObject();
	    find.put(strElementName, new ObjectId(strDataId));
		collection.deleteOne(find);
	}
	
	public static void update(String strDataId, String strTable, Document mData) throws Exception
	{
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		collection.updateOne(new Document(ID, new ObjectId(strDataId)), new Document(SET, mData));
	}
	
	public static void update(String strDataId, String strElementName, String strTable, Document mData) throws Exception
	{
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		BasicDBObject find = new BasicDBObject();
	    find.put(strElementName, new ObjectId(strDataId));
		collection.updateOne(find, new Document(SET, mData));
	}
	
	public static Document print(String strDataId, String strTable) throws Exception
	{
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
		connectedDB();
		
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		FindIterable<Document> collectionResult = collection.find(findCondition);
		if(collectionResult!=null)
			return collectionResult.first();
		else
			throw new Exception("Error... Data not found in find.");
	}
	
	public static FindIterable<Document> findMany(String strTable, BasicDBObject findCondition) throws Exception
	{
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		FindIterable<Document> collectionResult = collection.find(findCondition);
		
		if(collectionResult!=null)
			return collectionResult;
		else
			throw new Exception("Error... Data not found in find.");
	}
	
	public static void createCollection(String strCollectionName)
	{
		connectedDB();
		database.createCollection(strCollectionName);
	}
	
	public static void deleteCollection(String strCollectionName)
	{
		connectedDB();
		MongoCollection<Document> myCollection = database.getCollection(strCollectionName);
		myCollection.drop();
	}
	
	public static Object getNextSequence(String strCollectionName, String strId, String strColumnName) throws Exception
	{
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
	
	public static void clearAllCustomData()
	{
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
}
