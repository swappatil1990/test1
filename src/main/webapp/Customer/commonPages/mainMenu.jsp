<%@page import="idream2.main.core.ObjectData"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.FindIterable"%>

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
					BasicDBObject findMenu=new BasicDBObject();
					findMenu.append("mainMenu", "true");
					FindIterable<Document> docsMenuList = Util.findMany("id_CMPMenu", findMenu);
					String strSelectedMenu = request.getParameter("selectedMenu");
					for(Document docMenu:docsMenuList)
					{	
						String strURL = "";//docMenu.get("table").toString();
						String strClass = "";
						
						
						{
							strURL =  "href='"+Util.getTableURL(docMenu.get("table").toString())+"&selectedMenu="+docMenu.getObjectId("_id")+"'";
						}
						if(!Util.checkEmpty(strSelectedMenu) && strSelectedMenu.contains(docMenu.getObjectId("_id").toString()))
						{
							strClass+=" nav-expanded nav-active";
						}
					%>
					<li class="<%=strClass%>">
						<a <%=strURL%>>
							<i class="<%=  docMenu.get("icon").toString()%>" aria-hidden="true"></i>
							<span><%= docMenu.get("displayName").toString()%></span>
						</a>
						<%
						BasicDBObject findMenuChild=new BasicDBObject();
						findMenuChild.append("objectId", docMenu.getObjectId("_id"));
						FindIterable<Document> docsMenuListChild = Util.findMany("id_CMPMenu", findMenuChild);
						for(Document docMenuChild:docsMenuListChild)
						{
							String strClassChild = "";
							if(!Util.checkEmpty(strSelectedMenu) && strSelectedMenu.contains(docMenuChild.getObjectId("_id").toString()))
							{
								strClassChild="nav-active";
							}
						%>
						<ul class="nav nav-children">
							<li class="<%=strClassChild%>">
								<a href="<%=Util.getTableURL(docMenuChild.get("table").toString())%>&selectedMenu=<%=docMenu.getObjectId("_id")+":"+docMenuChild.getObjectId("_id")%>">
									<i class="<%=  docMenuChild.get("icon").toString() %>"></i>
									<span><%= docMenuChild.get("displayName").toString() %></span>
								</a>
							</li>
						</ul>
						<%
						}
						%>
					</li>
					<%
					}
					%>
				</ul>
			</nav>
		</div>

	</div>

</aside>