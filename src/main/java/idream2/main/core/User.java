package idream2.main.core;

import org.bson.Document;

public class User extends Constants {
	public static void insert(String strName, String strPassword, Document docParam) throws Exception
	{
		ObjectType.create("User", "Admin", "", "", 4, "", 4, true);
		Document doc=new Document();
		doc.append(FIELD_ADMINSCHEMAACCESS, "true");
		doc.append(FIELD_SUPERUIACCESS, "true");
		doc.append(FIELD_PASSWORD, strPassword);
		doc.putAll(docParam);
		
		ObjectData.insert("User", strName, "-", doc, true);
	}
	
	public static void delete(String strName) throws Exception
	{
		ObjectData.delete("User", strName, "-", true);
	}
	
	public static void update(String strName, String strPassword, Document docParam) throws Exception
	{
		Document doc=new Document();
		doc.append(FIELD_ADMINSCHEMAACCESS, "true");
		doc.append(FIELD_SUPERUIACCESS, "true");
		doc.append(FIELD_PASSWORD, strPassword);
		doc.putAll(docParam);
		ObjectData.update("User", strName, "-", docParam, true);
	}
}
