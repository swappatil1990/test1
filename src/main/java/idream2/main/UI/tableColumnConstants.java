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
		
		adminTable.put("id_ObjectElements", "_id,name,displayName,inputType,dataType,maxSize,status");
		adminTable.put("id_ObjectElements_Display", "Id,Element Name,Display Name,Input Type,Data Type,Max Size,Status");
		adminTableTitle.put("id_ObjectElements", "Object Element List");
		adminTable.put("id_ObjectElements_Nos", "1,2,3,4,5,6,7");
		adminTable.put("id_ObjectElements_Prop", "{'bSortable': false },null,null,null,null,null,null");
		adminTable.put("id_ObjectElements_Class", "'noEdit','','','','','',''");
		
		adminTable.put("id_CMPMenu", "_id,name,displayName,table,icon,objectId,mainMenu,status");
		adminTable.put("id_CMPMenu_Display", "Id,Menu Name,Display Name,Table,Icon,Object Id,mainMenu,Status");
		adminTableTitle.put("id_CMPMenu", "Menu List");
		adminTable.put("id_CMPMenu_Nos", "1,2,3,4,5,6,7,8");
		adminTable.put("id_CMPMenu_Prop", "{'bSortable': false },null,null,null,null,null,null,null");
		adminTable.put("id_CMPMenu_Class", "'noEdit','','','','','','',''");
		
		adminTable.put("id_Tables", "_id,name,displayName,objectType,adminTable,tableDataMethod,status");
		adminTable.put("id_Tables_Display", "Id,Table Name,Display Name,Object Type,Admin Table,Table Data Method,Status");
		adminTableTitle.put("id_Tables", "Table List");
		adminTable.put("id_Tables_Nos", "1,2,3,4,5,6,7");
		adminTable.put("id_Tables_Prop", "{'bSortable': false },null,null,null,null,null,null");
		adminTable.put("id_Tables_Class", "'noEdit','','','','','',''");
		
		adminTable.put("id_Columns", "_id,name,displayName,columnNo,columnProp,columnClass,status");
		adminTable.put("id_Columns_Display", "Id,Table Name,Display Name,Column No,Column Property,Column Class,Status");
		adminTableTitle.put("id_Columns", "Table List");
		adminTable.put("id_Columns_Nos", "1,2,3,4,5,6,7");
		adminTable.put("id_Columns_Prop", "{'bSortable': false },null,null,null,null,null,null");
		adminTable.put("id_Columns_Class", "'noEdit','','','','','',''");
		
		adminTable.put("id_Menu", "_id,name,displayName,table,icon,objectId,mainMenu,status");
		adminTable.put("id_Menu_Display", "Id,Menu Name,Display Name,Table,Icon,Object Id,mainMenu,Status");
		adminTableTitle.put("id_Menu", "Admin Menu List");
		adminTable.put("id_Menu_Nos", "1,2,3,4,5,6,7,8");
		adminTable.put("id_Menu_Prop", "{'bSortable': false },null,null,null,null,null,null,null");
		adminTable.put("id_Menu_Class", "'noEdit','','','','','','',''");
		
		adminTable.put("id_Stateflow", "_id,name,displayName,startState,connectionOnRevise,history,status");
		adminTable.put("id_Stateflow_Display", "Id,StateFlow Name,Display Name,Start State,Connection On Revise,History,Status");
		adminTableTitle.put("id_Stateflow", "Admin Menu List");
		adminTable.put("id_Stateflow_Nos", "1,2,3,4,5,6,7");
		adminTable.put("id_Stateflow_Prop", "{'bSortable': false },null,null,null,null,null,null");
		adminTable.put("id_Stateflow_Class", "'noEdit','','','','','',''");
		
		adminTable.put("schema_Manager", "_id,name,displayName,table,icon,objectId,mainMenu,status");
		adminTable.put("schema_Manager_Display", "Id,Menu Name,Display Name,Table,Icon,Object Id,mainMenu,Status");
		adminTableTitle.put("schema_Manager", "Admin Menu List");
		adminTable.put("schema_Manager_Nos", "1,2,3,4,5,6,7,8");
		adminTable.put("schema_Manager_Prop", "{'bSortable': false },null,null,null,null,null,null,null");
		adminTable.put("schema_Manager_Class", "'noEdit','','','','','','',''");
	}
}