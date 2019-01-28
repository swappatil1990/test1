package idream2.main.core;

import java.util.Map;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Util {
	
	public static MongoDatabase connectedDB()
	{
		MongoClient mongoClient = MongoClients.create("mongodb://swap:wws2RZ1bJia6KYS4@mycluster-shard-00-00-ko8ql.mongodb.net:27017,mycluster-shard-00-01-ko8ql.mongodb.net:27017,mycluster-shard-00-02-ko8ql.mongodb.net:27017/test?ssl=true&replicaSet=MyCluster-shard-0&authSource=admin&retryWrites=true");
		MongoDatabase database = mongoClient.getDatabase("mydb");
		return database;
	}
	
	public static void insert(String strTable, Map<String, Object> mData)
	{
		MongoDatabase database = connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		Document doc = new Document();
		for (Map.Entry<String, Object> entry : mData.entrySet())
		{
			doc.append(entry.getKey() , entry.getValue());
		}
		collection.insertOne(doc);
	}
	
	public static void delete(String strDataId, String strTable)
	{
		MongoDatabase database = connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		collection.deleteOne(new Document("_id", new ObjectId(strDataId)));
	}
	
	public static void update(String strDataId, String strTable, Map<String, Object> mData)
	{
		MongoDatabase database = connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		BasicDBObject updateQuery = new BasicDBObject();
		for (Map.Entry<String, Object> entry : mData.entrySet())
		{
			updateQuery.append(entry.getKey() , entry.getValue());
		}
		
		collection.updateOne(new Document("_id", new ObjectId(strDataId)), new Document("$set", updateQuery));
	}
	
	public static Document print(String strDataId, String strTable)
	{
		MongoDatabase database = connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		FindIterable<Document> collectionResult = collection.find(new Document("_id", new ObjectId(strDataId)));
		
		return collectionResult.first();
	}
	
	public static void createCollection(String strCollectionName)
	{
		MongoDatabase database = connectedDB();
		database.createCollection(strCollectionName);
	}
	
	public static void deleteCollection(String strCollectionName)
	{
		MongoDatabase database = connectedDB();
		MongoCollection<Document> myCollection = database.getCollection(strCollectionName);
		myCollection.drop();
	}
}
