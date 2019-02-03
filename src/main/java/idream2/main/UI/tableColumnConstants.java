package idream2.main.UI;

import java.util.HashMap;
import java.util.Map;

public class tableColumnConstants {
	
	public static Map<String, String> adminTable = new HashMap<String,String>();
	public static Map<String, String> adminTableTitle = new HashMap<String,String>();
	static
    {
		adminTable.put("id_Objects", "_id,objectName,description,suffix,currentAutoNumber,prefix,numberLength,stateflow,status");
		adminTable.put("id_Objects_Display", "Id,Object Name,Description,Suffix,Current Auto Number,Prefix,Number Length,Stateflow,Status");
		adminTable.put("id_Objects_Nos", "1,2,3,4,5,6,7,8,9");
		adminTable.put("id_Objects_Prop", "{'bSortable': false },null,null,null,null,null,null,null,null");
		adminTable.put("id_Objects_Class", "'noEdit','','','','','','','',''");
		adminTableTitle.put("id_Objects", "Object Type List");
		
		adminTable.put("id_Relations", "_id,relationName,relationType,description,status");
		adminTable.put("id_Relations_Display", "Id,Relation Name,Relation Type,Description,Status");
		adminTableTitle.put("id_Relations", "Relation Type List");
		adminTable.put("id_Relations_Nos", "1,2,3,4,5");
		adminTable.put("id_Relations_Prop", "{'bSortable': false },null,null,null,null");
		adminTable.put("id_Relations_Class", "'noEdit','','','',''");
		
		adminTable.put("id_User", "_id,name,adminSchemaAccess,superUIAccess,password");
		adminTable.put("id_User_Display", "Id,Name,Schema A,UI A,Password");
		adminTableTitle.put("id_User", "User List");
		adminTable.put("id_User_Nos", "1,2,3,4,5");
		adminTable.put("id_User_Prop", "{'bSortable': false},null,null,null,null");
		adminTable.put("id_User_Class", "'noEdit','','','',''");
		
		adminTable.put("id_RelationEvents", "_id,relationName,eventOn,eventAction,class,method,status");
		adminTable.put("id_RelationEvents_Display", "Id,Relation Name,Event On,Event Action,Class,Method,Status");
		adminTableTitle.put("id_RelationEvents", "Relation Event List");
		adminTable.put("id_RelationEvents_Nos", "1,2,3,4,5,6,7");
		adminTable.put("id_RelationEvents_Prop", "{'bSortable': false },null,null,null,null,null,null");
		adminTable.put("id_RelationEvents_Class", "'noEdit','','','','','',''");
		
		adminTable.put("id_ObjectEvents", "_id,objectName,eventOn,eventAction,class,method,status");
		adminTable.put("id_ObjectEvents_Display", "Id,Object Name,Event On,Event Action,Class,Method,Status");
		adminTableTitle.put("id_ObjectEvents", "Object Event List");
		adminTable.put("id_ObjectEvents_Nos", "1,2,3,4,5,6,7");
		adminTable.put("id_ObjectEvents_Prop", "{'bSortable': false },null,null,null,null,null,null");
		adminTable.put("id_ObjectEvents_Class", "'noEdit','','','','','',''");
	}
}