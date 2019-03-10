<%@page import="org.bson.conversions.Bson"%>
<%@page import="idream2.main.core.ObjectData"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="idream2.main.core.ObjectType"%>
<%@page import="idream2.main.core.Context"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.FindIterable"%>
<%@page import="com.mongodb.client.model.Sorts.*"%>

<aside id="sidebar-left" class="sidebar-left">
				
	<div class="sidebar-header">
		<div class="sidebar-title" style="color:white">
			Menubar
		</div>
		<div class="sidebar-toggle hidden-xs" data-toggle-class="sidebar-left-collapsed" data-target="html" data-fire-event="sidebar-left-toggle">
			<i class="fa fa-bars" aria-label="Toggle sidebar"></i>
		</div>
	</div>

	<div class="nano">
		<div class="nano-content">
			<nav id="menu" class="nav-main" role="navigation">
				<ul class="nav nav-main">
					<%
					
					FindIterable<Document> docsMenuList = ObjectType.getAllSchemaObjectTypesForMenu(context);
					
					String strSelectedMenu = request.getParameter("selectedMenu");
					for(Document docMenu:docsMenuList)
					{	
						String strURL = "";
						String strClass = "";
						
						strURL =  "href='table.jsp?objectType="+docMenu.get("name").toString()+"&selectedMenu="+docMenu.getObjectId("_id")+"&mainMenu=true&pageName="+docMenu.get("displayName").toString()+"'";
						
						if(!Util.checkEmpty(strSelectedMenu) && strSelectedMenu.contains(docMenu.getObjectId("_id").toString()))
						{
							strClass+=" nav-active";
						}
					%>
					<li class="<%=strClass%>">
						<a <%=strURL%>>
							<i class="<%=  docMenu.get("icon").toString()%>" aria-hidden="true"></i>
							<span><%= docMenu.get("displayName").toString()%></span>
						</a>
						
					</li>
					<%
					}
					%>
					
					
					
				</ul>
			</nav>
		</div>

	</div>

</aside>