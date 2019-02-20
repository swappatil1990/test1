package idream2.main.core;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.BasicDBObject;

public class Context extends Constants{
	public static String strUser="";
	public static String strCollectionName="";
	public static String strDataCollectionName="";
	static ArrayList<String> arrHrefList=new ArrayList<>();
	static ArrayList<String> arrHrefHeaderList=new ArrayList<>();
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
			
			BasicDBObject findCondition=new BasicDBObject();
			findCondition.append(FIELD_NAME, strUser);
			Document doc = Util.find("id_User", findCondition);
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
	
	public static void setHref(String strHeader, String strHref)
	{
		if(!arrHrefList.contains(strHref))
		{
			arrHrefHeaderList.add(strHeader);
			arrHrefList.add(strHref);
		}
		else
		{
			int index=arrHrefList.indexOf(strHref);
			System.out.println("-------index------"+index);
			System.out.println("----------arrHrefList.size()---"+arrHrefList.size());
			
			for(int i=arrHrefList.size()-1;i>(index);i--)
			{
				arrHrefList.remove(i);
				arrHrefHeaderList.remove(i);
			}
		}
	}
	
	public static String getHref()
	{
		String strResult="";
		int cnt=0;
		for(String strTemp:arrHrefHeaderList)
		{
			strResult += "<li>"
					+ "<a href=\""+arrHrefList.get(cnt)+"\">\r\n" + 
					"			"+strTemp+"\r\n" + 
					"		</a>"
					+ "</li>";
			cnt++;
		}
		
		return strResult;
	}
	
	public static String getLastHref()
	{
		if(arrHrefList.size()>0)
			return arrHrefList.get(arrHrefList.size()-1);
		else
			return "";
	}
	
	public static void clearHref()
	{
		arrHrefHeaderList.clear();
		arrHrefList.clear();
	}
}
