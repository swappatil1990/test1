package idream2.main.core;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.bson.Document;

import com.mongodb.BasicDBObject;

public class Context extends Constants{
	public String strUser="";
	public String strCollectionName="";
	public String strDataCollectionName="";
	public String strSuperAdminCollectionName="id_AdminObjectData";
	ArrayList<String> arrHrefList=new ArrayList<>();
	ArrayList<String> arrHrefHeaderList=new ArrayList<>();
	String strPassword="";
	public String contextId="";
	public boolean flagLogin = true;
	public boolean strAdminSchemaAccess=false;
	public boolean strSuperUIAccess=false;
	public String designation="";
	
	public String login(String strUser, String strPassword) throws Exception
	{
		try {
			flagLogin=false;
			
			BasicDBObject findCondition=new BasicDBObject();
			findCondition.append(FIELD_NAME, strUser);
			Document doc = Util.find("id_User", findCondition, this);
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
	public void logout() throws Exception
	{
		flagLogin=true;
		contextId="";
		strUser="";
		strAdminSchemaAccess=false;
		strSuperUIAccess=false;
	}
	public boolean checkContext(String strId) throws Exception
	{
		if(contextId.equals(strId))
			return true;
		else
			return false;
	}
	public Context getContext() throws Exception
	{
		Context cnt=new Context();
		return cnt;
	}
	
	public void setHref(String strHeader, String strHref)
	{
		if(!arrHrefList.contains(strHref))
		{
			arrHrefHeaderList.add(strHeader);
			arrHrefList.add(strHref);
		}
		else
		{
			int index=arrHrefList.indexOf(strHref);
			
			for(int i=arrHrefList.size()-1;i>(index);i--)
			{
				arrHrefList.remove(i);
				arrHrefHeaderList.remove(i);
			}
		}
	}
	
	public String getHref()
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
	
	public String getLastHref()
	{
		if(arrHrefList.size()>0)
			return arrHrefList.get(arrHrefList.size()-1);
		else
			return "";
	}
	
	public void clearHref()
	{
		arrHrefHeaderList.clear();
		arrHrefList.clear();
	}
	
	public String getDataCollectionName()
	{
		return strDataCollectionName;
	}
	
	public String getSuperAdminCollectionName()
	{
		return strSuperAdminCollectionName;
	}
	
	public String getCollectionName()
	{
		return strCollectionName;
	}
	
	public String getUserName()
	{
		return strUser;
	}
	
	public boolean getFlagLogin()
	{
		return flagLogin;
	}
	
	public String getContextId()
	{
		return contextId;
	}
	
	public void setContextId(String strContextId)
	{
		contextId = strContextId;
	}
	
	public void createContextFromSession(HttpSession session) throws Exception
	{
		try
		{
			login(session.getAttribute("sessionUserName").toString(), session.getAttribute("sessionUserPass").toString());
			strCollectionName = session.getAttribute("strCollectionName").toString();
			strDataCollectionName = session.getAttribute("strDataCollectionName").toString();
		}
		catch(Exception e)
		{
			throw new Exception("Error in Login Context.....");
		}
		//Util.checkSessions();
	}
}
