package idream2.main.core;

import org.bson.Document;

public class Command {
	public static void main(String args[])
	{
		Command c=new Command();
		System.out.println("   Output : "+run(""));
	}
	
	public static String run(String cmd)
	{
		String output = "";
		try
		{
			String arrData[] = cmd.split("~");
			String arrOperation[] = null;
			if(arrData.length>0)
				arrOperation = arrData[0].split(" ");
			
			String arrParamData[] = null;
			if(arrData.length>1)
			arrParamData = arrData[1].split(";");
			
			output="Command> "+cmd+"\n";
			if(arrOperation[0].trim().equals("add"))
			{
				if(arrOperation[1].trim().equals("objectType"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "add objectType~strName; strType; strDescription; strPrefix; currentAutoNum; strSuffix; numLength\n         Ex: add objectType~Employee;Admin;-;-;4;-;4";
					}
					else
					{
						ObjectType.create(arrParamData[0].trim(), arrParamData[1].trim(), arrParamData[2].trim(), arrParamData[3].trim(), Integer.parseInt(arrParamData[4].trim()), arrParamData[5].trim(), Integer.parseInt(arrParamData[6].trim()));
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("objectData"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "add objectData~strObjectName; strDataName; strVersion; doc\n         Ex: ";
					}
					else
					{
						Document doc = Document.parse(arrParamData[3].trim());
						ObjectData.insert(arrParamData[0].trim(), arrParamData[1].trim(), arrParamData[2].trim(), doc);
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("objectElement"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "add objectElement~strName; strDataType; strDescription; strObjectName; defaultValue; dataSize\n         Ex: ";
					}
					else
					{
						ObjectType.addElement(arrParamData[0].trim(), arrParamData[1].trim(), arrParamData[2].trim(), arrParamData[3].trim(),arrParamData[4].trim(), Integer.parseInt(arrParamData[5].trim()));
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("relationType"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "add relationType~strName; strType; strDescription; strRelationType\n         Ex: ";
					}
					else
					{
						RelationType.create(arrParamData[0].trim(), arrParamData[1].trim(), arrParamData[2].trim(), arrParamData[3].trim());
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("relationData"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "add relationData~strFromO; strFromN; strFromV; strToO; strToN; strToV; strRelationName; doc\n         Ex: ";
					}
					else
					{
						Document doc = Document.parse(arrParamData[7].trim());
						RelationData.connect(arrParamData[0].trim(), arrParamData[1].trim(), arrParamData[2].trim(), arrParamData[3].trim(),arrParamData[4].trim(), arrParamData[5].trim(), arrParamData[6].trim(), doc);
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("relationtElement"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "add relationtElement~strName; strDataType; strDescription; strRelationName; defaultValue; dataSize\n         Ex: ";
					}
					else
					{
						RelationType.addElement(arrParamData[0].trim(), arrParamData[1].trim(), arrParamData[2].trim(), arrParamData[3].trim(),arrParamData[4].trim(), Integer.parseInt(arrParamData[5].trim()));
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("help"))
				{
					output = output + "       > " + "add objectType \nadd objectData \nadd objectElement \nadd relationType \nadd relationData \nadd relationtElement";
				}
			}
			else if(arrOperation[0].trim().equals("delete"))
			{
				if(arrOperation[1].trim().equals("objectType"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "delete objectType~strName\n         Ex: delete objectType~Employee";
					}
					else
					{
						ObjectType.delete(arrParamData[0].trim());
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("objectData"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "delete objectData~strObjectName; strDataName; strVersion\n         Ex: ";
					}
					else
					{
						ObjectData.delete(arrParamData[0].trim(), arrParamData[1].trim(), arrParamData[2].trim());
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("objectElement"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "delete objectElement~strObjectName; strElementName\n         Ex: ";
					}
					else
					{
						ObjectType.removeElement(arrParamData[0].trim(), arrParamData[1].trim());
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("relationType"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "delete relationType~strName\n         Ex: ";
					}
					else
					{
						RelationType.delete(arrParamData[0].trim());
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("relationData"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "delete relationData~strFromO; strFromN; strFromV; strToO; strToN; strToV; strRelationName\n         Ex: ";
					}
					else
					{
						RelationData.disconnect(arrParamData[0].trim(), arrParamData[1].trim(), arrParamData[2].trim(), arrParamData[3].trim(),arrParamData[4].trim(), arrParamData[5].trim(), arrParamData[6].trim());
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("relationtElement"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "delete relationtElement~strRelationName; strElementName\n         Ex: ";
					}
					else
					{
						RelationType.removeElement(arrParamData[0].trim(), arrParamData[1].trim());
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("help"))
				{
					output = output + "       > " + "delete objectType \ndelete objectData \ndelete objectElement \ndelete relationType \ndelete relationData \ndelete relationtElement";
				}
			}
			else if(arrOperation[0].trim().equals("update"))
			{
				if(arrOperation[1].trim().equals("objectType"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "update objectType\n         Ex: ";
					}
					else
					{
						//ObjectType.update(arrParamData[0].trim());
						output = output + "       > " + "Error... Method not available";
					}
				}
				else if(arrOperation[1].trim().equals("objectData"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "update objectData~strObjectName; strDataName; strVersion; doc\n         Ex: ";
					}
					else
					{
						Document doc = Document.parse(arrParamData[3].trim());
						ObjectData.update(arrParamData[0].trim(), arrParamData[1].trim(), arrParamData[2].trim(), doc);
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("objectElement"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "update objectElement\n         Ex: ";
					}
					else
					{
						//ObjectType.update(arrParamData[0].trim(); arrParamData[1].trim());
						output = output + "       > " + "Error... Method not available";
					}
				}
				else if(arrOperation[1].trim().equals("relationType"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "update relationType\n         Ex: ";
					}
					else
					{
						//RelationType.update(arrParamData[0].trim());
						output = output + "       > " + "Error... Method not available";
					}
				}
				else if(arrOperation[1].trim().equals("relationData"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "update relationData~strFromO; strFromN; strFromV; strToO; strToN; strToV; strRelationName; doc\n         Ex: ";
					}
					else
					{
						Document doc = Document.parse(arrParamData[3].trim());
						RelationData.update(arrParamData[0].trim(), arrParamData[1].trim(), arrParamData[2].trim(), arrParamData[3].trim(),arrParamData[4].trim(), arrParamData[5].trim(), arrParamData[6].trim(), doc);
						output = output + "       > " + "Done";
					}
				}
				else if(arrOperation[1].trim().equals("relationtElement"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "update relationtElement\n         Ex: ";
					}
					else
					{
						//RelationType.update(arrParamData[0].trim(), arrParamData[1].trim());
						output = output + "       > " + "Error... Method not available";
					}
				}
				else if(arrOperation[1].trim().equals("help"))
				{
					output = output + "       > " + "update objectType \nupdate objectData \nupdate objectElement \nupdate relationType \nupdate relationData \nupdate relationtElement";
				}
			}
			else if(arrOperation[0].trim().equals("print"))
			{
				if(arrOperation[1].trim().equals("objectType"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "print objectType\n         Ex: ";
					}
					else
					{
						//ObjectType.print(arrParamData[0].trim());
						output = output + "       > " + "Error... Method not available";
					}
				}
				else if(arrOperation[1].trim().equals("objectData"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "print objectData\n         Ex: ";
					}
					else
					{
						//ObjectData.print(arrParamData[0].trim(), arrParamData[1].trim(), arrParamData[2].trim(), doc);
						output = output + "       > " + "Error... Method not available";
					}
				}
				else if(arrOperation[1].trim().equals("objectElement"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "print objectElement\n         Ex: ";
					}
					else
					{
						//ObjectType.print(arrParamData[0].trim(), arrParamData[1].trim());
						output = output + "       > " + "Error... Method not available";
					}
				}
				else if(arrOperation[1].trim().equals("relationType"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "print relationType\n         Ex: ";
					}
					else
					{
						//RelationType.print(arrParamData[0].trim());
						output = output + "       > " + "Error... Method not available";
					}
				}
				else if(arrOperation[1].trim().equals("relationData"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "print relationData\n         Ex: ";
					}
					else
					{
						//RelationData.print(arrParamData[0].trim(), arrParamData[1].trim(), arrParamData[2].trim(), arrParamData[3].trim(),arrParamData[4].trim(), arrParamData[5].trim(), arrParamData[6].trim(), doc);
						output = output + "       > " + "Error... Method not available";
					}
				}
				else if(arrOperation[1].trim().equals("relationtElement"))
				{
					if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
					{
						output = output + "       > " + "print relationtElement\n         Ex: ";
					}
					else
					{
						//RelationType.print(arrParamData[0].trim(), arrParamData[1].trim());
						output = output + "       > " + "Error... Method not available";
					}
				}
				else if(arrOperation[1].trim().equals("help"))
				{
					output = output + "       > " + "print objectType \nprint objectData \nprint objectElement \nprint relationType \nprint relationData \nprint relationtElement";
				}
			}
			else if(arrOperation[0].trim().equals("clearAll"))
			{
				if(arrOperation.length>2 && arrOperation[2].trim().equals("help"))
				{
					output = output + "       > " + "clearAll; <no extra arg required>";
				}
				else
				{
					output = output + "       > " + "Error... Required admin access only";
				}
			}
			else if(arrOperation[0].trim().equals("help"))
			{
				output = output + "       > " + "add \ndelete \nupdate \nprint \nclearAll";
			}
			
		}
		catch (Exception e) {
			System.out.println("Error... "+e);
			output = output + "       > " + e;
		}
		return output;
	}
}
