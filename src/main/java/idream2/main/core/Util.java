package idream2.main.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.ClientSessionOptions;
import com.mongodb.TransactionOptions;;

public class Util extends Constants{
	static MongoDatabase database=null;
	static MongoClient mongoClient =null;
	static ClientSession s;
	public static void connectedDB()
	{
		if(database==null)
		{
			//mongodb://swap:wws2RZ1bJia6KYS4@mycluster-shard-00-00-ko8ql.mongodb.net:27017,mycluster-shard-00-01-ko8ql.mongodb.net:27017,mycluster-shard-00-02-ko8ql.mongodb.net:27017/test?ssl=true&replicaSet=MyCluster-shard-0&authSource=admin&retryWrites=true
			//mongodb+srv://swap:wws2RZ1bJia6KYS4@cluster0-ko8ql.mongodb.net/test?retryWrites=true
			mongoClient = MongoClients.create("mongodb+srv://swap:wws2RZ1bJia6KYS4@cluster0-ko8ql.mongodb.net/test?retryWrites=true");
			database = mongoClient.getDatabase("Project1");
			
			
		}
		//s= mongoClient.startSession();
		//s.startTransaction();
		//s.commitTransaction();
	}
	
	public static void dbClose()
	{
		//mongoClient.close();
		//database=null;
	}
	
	public static String insert(String strTable, Document doc, Context context) throws Exception
	{
		checkLogin(context);
		connectedDB();
		
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		executeEvent(strTable, "Insert", "Before", doc, "", context);
		collection.insertOne(doc);
		
		BasicDBObject find = new BasicDBObject();
		for ( String key : doc.keySet() ) {
			find.put(key, doc.get(key));
		}
		
		Document docResult = find(strTable, find, context);
		String strObjectDataId = docResult.getObjectId("_id").toString();
		
		executeEvent(strTable, "Insert", "After", doc, strObjectDataId, context);
		
		dbClose();
		return strObjectDataId;
	}
	
	public static void delete(String strDataId, String strTable, Context context) throws Exception
	{
		checkLogin(context);
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		Document doc=new Document();
		doc.append("objectName", strTable.replace("id_", "").replace("schema_", ""));
		executeEvent(strTable, "Delete", "Before", doc, strDataId, context);
		
		collection.deleteOne(new Document(ID, new ObjectId(strDataId)));
		
		executeEvent(strTable, "Delete", "After", doc, strDataId, context);
		dbClose();
	}
	
	public static void delete(String strDataId, String strElementName, String strTable, Context context) throws Exception
	{
		checkLogin(context);
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		BasicDBObject find = new BasicDBObject();
	    find.put(strElementName, new ObjectId(strDataId));
	    
		collection.deleteOne(find);
		dbClose();
	}
	
	public static String update(String strDataId, String strTable, Document mData, Context context) throws Exception
	{
		
		checkLogin(context);
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		executeEvent(strTable, "Update", "Before", mData, strDataId, context);
		
		UpdateResult resultDoc= collection.updateOne(new Document(ID, new ObjectId(strDataId)), new Document(SET, mData));
		
		executeEvent(strTable, "Update", "After", mData, strDataId, context);
		
		dbClose();
		return resultDoc.getModifiedCount()+"";
	}
	
	public static String connectToObject(String strDataId, String strTable, Document mData, Context context) throws Exception
	{
		
		checkLogin(context);
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		executeEvent(strTable, "Update", "Before", mData, strDataId, context);
		
		UpdateResult resultDoc= collection.updateOne(new Document(ID, new ObjectId(strDataId)), new Document("$push", new BasicDBObject("connections", mData)));
		
		executeEvent(strTable, "Update", "After", mData, strDataId, context);
		
		return resultDoc.getModifiedCount()+"";
	}
	
	public static String connectObjectToObject(String strParentDataId, String strDataId, String strTable, String strRelationName, Context context) throws Exception
	{
		
		checkLogin(context);
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		executeEvent(strTable, "Update", "Before", null, strDataId, context);
		
		Document mData = new Document();
		mData.append("objectId", strDataId);
		mData.append("relationName", strRelationName);
		
		UpdateResult resultDoc= collection.updateOne(new Document(ID, new ObjectId(strParentDataId)), new Document("$push", new BasicDBObject("connections", mData)));
		
		mData.append("objectId", strParentDataId);
		
		UpdateResult resultDoc2= collection.updateOne(new Document(ID, new ObjectId(strDataId)), new Document("$push", new BasicDBObject("fromConnections", mData)));
		
		executeEvent(strTable, "Update", "After", null, strDataId, context);
		
		dbClose();
		return resultDoc.getModifiedCount()+"";
	}
	
	public static String deleteObjectToObject(String strDataId, String strTable, Document mData, Context context) throws Exception
	{
		checkLogin(context);
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		executeEvent(strTable, "Update", "Before", mData, strDataId, context);
		
		UpdateResult resultDoc= collection.updateOne(new Document(ID, new ObjectId(strDataId)), new Document("$pull", new BasicDBObject("connections", mData)));
		
		executeEvent(strTable, "Update", "After", mData, strDataId, context);
		
		dbClose();
		return resultDoc.getModifiedCount()+"";
	}
	
	public static String deleteObjectFromObject(String strDataId, String strTable, Document mData, Context context) throws Exception
	{
		
		checkLogin(context);
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		executeEvent(strTable, "Update", "Before", mData, strDataId, context);
		
		UpdateResult resultDoc= collection.updateOne(new Document(ID, new ObjectId(strDataId)), new Document("$pull", new BasicDBObject("fromConnections", mData)));
		
		executeEvent(strTable, "Update", "After", mData, strDataId, context);
		
		dbClose();
		return resultDoc.getModifiedCount()+"";
	}
	
	public static String disconnectObjectObject(String strParentDataId, String strObjectId, String strTable, Context context) throws Exception
	{
		Document docSingle=new Document();
		
		docSingle.append("objectId", strObjectId);
		Util.deleteObjectToObject(strParentDataId, strTable, docSingle, context);

		docSingle.append("objectId", strParentDataId);
		Util.deleteObjectFromObject(strObjectId, strTable, docSingle, context);
		
		return "true";
	}
	
	public static void update(String strDataId, String strElementName, String strTable, Document mData, Context context) throws Exception
	{
		checkLogin(context);
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		BasicDBObject find = new BasicDBObject();
	    find.put(strElementName, new ObjectId(strDataId));
		collection.updateOne(find, new Document(SET, mData));
		dbClose();
	}
	
	public static Document print(String strDataId, String strTable, Context context) throws Exception
	{
		checkLogin(context);
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		FindIterable<Document> collectionResult = collection.find(new Document(ID, new ObjectId(strDataId)));
		dbClose();
		if(collectionResult!=null)
			return collectionResult.first();
		else
			throw new Exception("Error... Data not found in print.");
		
	}
	
	public static Document find(String strTable, BasicDBObject findCondition, Context context) throws Exception
	{
		if(context.getFlagLogin())
			checkLogin(context);
		connectedDB();
		
		MongoCollection<Document> collection = database.getCollection(strTable);
		FindIterable<Document> collectionResult = collection.find(findCondition);
		
		dbClose();
		return collectionResult.first();
	}
	
	public static FindIterable<Document> findMany(String strTable, BasicDBObject findCondition, Context context) throws Exception
	{
		checkLogin(context);
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		
		FindIterable<Document> collectionResult = collection.find(findCondition);
		dbClose();
		return collectionResult;
	}
	
	public static FindIterable<Document> findManyWithSort(String strTable, BasicDBObject findCondition, String strSort, Context context) throws Exception
	{
		checkLogin(context);
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strTable);
		Document docSort=new Document();
		docSort.append(strSort, 1);
		FindIterable<Document> collectionResult = collection.find(findCondition).sort(docSort);
		dbClose();
		return collectionResult;
	}
	
	public static void createCollection(String strCollectionName, Context context) throws Exception
	{
		checkLogin(context);
		connectedDB();
		database.createCollection(strCollectionName);
	}
	
	public static void deleteCollection(String strCollectionName, Context context) throws Exception
	{
		checkLogin(context);
		connectedDB();
		MongoCollection<Document> myCollection = database.getCollection(strCollectionName);
		myCollection.drop();
	}
	
	public static Object getNextSequence(String strCollectionName, String strId, String strColumnName, Context context) throws Exception
	{
		checkLogin(context);
		connectedDB();
		MongoCollection<Document> collection = database.getCollection(strCollectionName);
	    BasicDBObject find = new BasicDBObject();
	    find.put(FIELD_OBJECTNAME, strId);
	    BasicDBObject update = new BasicDBObject();
	    update.put(INC, new BasicDBObject(strColumnName, 1));
	    Document obj =  collection.findOneAndUpdate(find, update);
	    dbClose();
	    if(obj!=null)
	    	return obj.get(strColumnName);
	    else
	    	throw new Exception("Error... Data not found in getNextSequence.");
	}
	
	public static int getObjectDataSequence(String strObjectName, Context context) throws Exception{
		int cnt=(int) getNextSequence(COLLECTION_ID_OBJECTS, strObjectName, FIELD_CURRENTAUTONUMBER, context);
	    return cnt;
	}
	
	public static void clearAllCustomData(Context context) throws Exception
	{
		checkLogin(context);
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
				deleteCollection(str, context);
			}
		}
		
	}
	
	public static String checkLogin(Context context) throws Exception
	{
		if(context.getContextId()!=null && !context.getContextId().equals(""))
			return context.getContextId();
		else
			throw new Exception("Error... Login context not found.");
	}
	
	public static Object callMethod(String className, String methodName, Map<String, Object> arg) throws Exception
	{
		Class classRef = Class.forName("idream2.main.UI."+className);
		Method instanceMethod = classRef.getMethod(methodName, Map.class);
		
		return (Object) instanceMethod.invoke(classRef, arg);
	}
	
	public static void executeEvent(String strTableName, String strEventOn, String strEventAction, Document doc, String strObjectDataId, Context context) throws Exception
	{
		Map<String,Object> argMap=new HashMap<String,Object>();
		argMap.put("Document", doc);
		argMap.put("ObjectId", strObjectDataId);
		/*
		 * String strObjectId=""; try { strObjectId =
		 * ObjectType.getId(strTableName.replaceAll("id_", "").replaceAll("schema_",
		 * "")).toString(); } catch (Exception e) { // TODO: handle exception }
		 */
		Events.runObjectEvent(strTableName, strEventOn, strEventAction, argMap, context);
	}
	
	public static boolean checkEmpty(String str)
	{
		
		if(str==null || str.equals("") || str.equals("null"))
			return true;
		else
			return false;
	}
	
	public static String getTableURL(String strTableName, Context context) throws Exception
	{
		BasicDBObject findCondition=new BasicDBObject();
		findCondition.append("name", strTableName);
		Document doc = find("id_Tables", findCondition, context);
		return "../table.jsp?tableName="+strTableName+"&objectType="+doc.getString("objectType")+"&adminTable="+doc.getString("adminTable")+"&tableDataMethod="+doc.getString("tableDataMethod")+"&displayName="+doc.getString("displayName");
	}
	
	public static String getAdminTableURL(String strTableName, Context context) throws Exception
	{
		BasicDBObject findCondition=new BasicDBObject();
		findCondition.append("name", strTableName);
		Document doc = find("id_Tables", findCondition, context);
		return "/table.jsp?tableName="+strTableName+"&objectType="+doc.getString("objectType")+"&adminTable="+doc.getString("adminTable")+"&tableDataMethod="+doc.getString("tableDataMethod")+"&displayName="+doc.getString("displayName");
	}
	
	public static void signUp(String strEmail, String strUserName, String strPassword, String strContactPerson, String strContactNo, String strBusinessName, String strNoOfOutlet, Context context) throws Exception
	{
		context.setContextId("temp");
		Document doc=new Document();
		doc.append("userName", strUserName);
		doc.append("email", strEmail);
		doc.append("password", strPassword);
		doc.append("contactPerson", strContactPerson);
		doc.append("contactNo", strContactNo);
		doc.append("businessName", strBusinessName);
		doc.append("noOfOutlets", strNoOfOutlet);
		insert("id_signup", doc, context);
		context.setContextId("");
	}
}
