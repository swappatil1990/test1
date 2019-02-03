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
					
					FindIterable<Document> docsMenuList = Util.findMany("id_Menu", findMenu);
					
					for(Document docMenu:docsMenuList)
					{	
					%>
					<li class="nav-active">
						<a href="<%= docMenu.get("url").toString() %>">
							<i class="<%=  docMenu.get("icon").toString() %>" aria-hidden="true"></i>
							<span><%= docMenu.get("displayName").toString() %></span>
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