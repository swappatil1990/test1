package idream2.main.UI;

import java.util.ArrayList;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

import idream2.main.core.Context;
import idream2.main.core.Util;

public class formData {
	public static ArrayList<Document> getFormData(Map<String, Object> docRequest) throws Exception
	{
		
		Context context = null;
		if(docRequest.get("context")!=null)
			context = (Context)docRequest.get("context");
		try
		{
			ArrayList<Document> formData=new ArrayList<Document>();
			String strParentDataId = (String)docRequest.get("parentDataId");
			String strObjectType = (String)docRequest.get("objectType");
			
			Document docFormPropertie=new Document();
			
			formData.add(docFormPropertie);
			
			BasicDBObject findConditionColumns = new BasicDBObject();
			findConditionColumns.append("_Id", new ObjectId(strParentDataId));
			FindIterable<Document> docFields = Util.findMany(context.getCollectionName(), findConditionColumns, context);
			
			BasicDBObject findCondition=new BasicDBObject();
			findCondition.append("_id", new ObjectId(strParentDataId));
			Document docData = Util.find(strObjectType, findCondition, context);
			
			for (Document doc : docFields) {
				String strData = (String) docData.get(doc.get("name"));
				doc.append("data", strData);
				formData.add(doc);
	        }
			
			return formData;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
