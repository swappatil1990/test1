package idream2.main.core;

import org.bson.Document;

public class User extends Constants {
	public static void insert(String strName, String strPassword, Context context) throws Exception
	{
		//ObjectType.create("User", "Admin", "", "", 4, "", 4, true);
		Document doc=new Document();
		doc.append(FIELD_ADMINSCHEMAACCESS, "true");
		doc.append(FIELD_SUPERUIACCESS, "true");
		doc.append(FIELD_PASSWORD, strPassword);
		doc.append(FIELD_NAME, strName);
		Util.insert("id_User", doc, context);
	}
	
	public static void delete(String strName) throws Exception
	{
		//Util.delete("User", strName, "-", true);
	}
	
	public static void update(String strName, String strPassword, Document docParam) throws Exception
	{
		
	}
}
