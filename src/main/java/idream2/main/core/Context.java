package idream2.main.core;

import org.bson.Document;

public class Context extends Constants{
	public static String strUser="";
	String strPassword="";
	public static String contextId="";
	public static boolean flagLogin = true;
	public static boolean strAdminSchemaAccess=false;
	public static boolean strSuperUIAccess=false;
	public static String designation="";
	public String login(String strUser, String strPassword) throws Exception
	{
		try {
			flagLogin=false;
			Document doc = ObjectData.getData("User", strUser, "-", true);
			if(doc.get(FIELD_PASSWORD).toString().equals(strPassword))
			{
				contextId=doc.getObjectId(ID).toString();
				strAdminSchemaAccess=Boolean.parseBoolean(doc.getString(FIELD_ADMINSCHEMAACCESS));
				strSuperUIAccess=Boolean.parseBoolean(doc.getString(FIELD_SUPERUIACCESS));
				flagLogin=true;
				this.strUser=strUser;
				
				return contextId;
			}
			else {
				logout();
				return null;
			}
		} catch (Exception e) {
			logout();
			throw e;
		}
	}
	public static void logout() throws Exception
	{
		flagLogin=true;
		contextId="";
		strUser="";
		strAdminSchemaAccess=false;
		strSuperUIAccess=false;
	}
	public static boolean checkContext(String strId) throws Exception
	{
		if(contextId.equals(strId))
			return true;
		else
			return false;
	}
	public static Context getContext() throws Exception
	{
		Context cnt=new Context();
		return cnt;
	}
}
