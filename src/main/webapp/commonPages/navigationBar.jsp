<%@page import="java.util.ArrayList"%>
<%@page import="idream2.main.core.Context"%>
<%

String strMainMenu=request.getParameter("mainMenu");
String strName=request.getParameter("pageName");
if(strMainMenu!=null && strMainMenu.equals("true"))
{
	context.clearHref();
	context.setHref(strName,request.getRequestURL()+"?"+request.getQueryString());
	ArrayList<String> hrefList = new ArrayList<String>();
	ArrayList<String> hrefHeaderList = new ArrayList<String>();
	hrefList.add(request.getRequestURL()+"?"+request.getQueryString());
	hrefHeaderList.add(strName);
	session.setAttribute("hrefList",hrefList);
	session.setAttribute("hrefHeaderList",hrefHeaderList);
}
else
{
	ArrayList<String> hrefList = context.arrHrefList;
	ArrayList<String> hrefHeaderList = context.arrHrefHeaderList;
	hrefList.add(request.getRequestURL()+"?"+request.getQueryString());
	hrefHeaderList.add(strName);
	session.setAttribute("hrefList",hrefList);
	session.setAttribute("hrefHeaderList",hrefHeaderList);
	context.setHref(strName,request.getRequestURL()+"?"+request.getQueryString());
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
			out.print(context.getHref());
			%>
			<li><span> </span></li>
			
		</ol>

	</div>
</header>