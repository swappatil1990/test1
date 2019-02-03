package idream2.main.UI;

import java.util.Map;

import idream2.main.core.ObjectType;

public class adminActionMethods {
	public static Object insertObjectType(Map<String, Object> docRequest) throws Exception
	{
		return (Object)ObjectType.create((String)docRequest.get("name"), (String)docRequest.get("type"), (String)docRequest.get("description"), (String)docRequest.get("prefix"), (int)docRequest.get("currentAutoNumber"), (String)docRequest.get("suffix"), (int)docRequest.get("numberLength"));
	}
}
