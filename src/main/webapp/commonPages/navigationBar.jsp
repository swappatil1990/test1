<%@page import="idream2.main.core.Context"%>
<%

String strMainMenu=request.getParameter("mainMenu");
String strName=request.getParameter("pageName");
if(strMainMenu!=null && strMainMenu.equals("true"))
{
	Context.clearHref();
	Context.setHref(strName,request.getRequestURL()+"?"+request.getQueryString());
}
else
{
	Context.setHref(strName,request.getRequestURL()+"?"+request.getQueryString());
}

%>
<header class="page-header">
	<h2>Administrator Portal</h2>

	<div class="right-wrapper pull-right">
		<ol class="breadcrumbs">
			<li>
				<a href="home.jsp">
					<i class="fa fa-home"></i>
				</a>
				
			</li>
			<%
			out.print(Context.getHref());
			%>
			<li><span> </span></li>
			
		</ol>

	</div>
</header>