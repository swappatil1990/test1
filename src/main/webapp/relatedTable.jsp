<%@page import="idream2.main.core.ObjectData"%>
<%@page import="idream2.main.core.Util"%>
<%@page import="idream2.main.UI.tableColumnConstants"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.FindIterable"%>

<!doctype html>
<html class="fixed">
	<head>

		<!-- Basic -->
		<meta charset="UTF-8">

		<title>i-Dream</title>
		<meta name="keywords" content="HTML5 Admin Template" />
		<meta name="description" content="Porto Admin - Responsive HTML5 Template">
		<meta name="author" content="okler.net">

		<!-- Mobile Metas -->
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

		<!-- Web Fonts  -->
		<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800|Shadows+Into+Light" rel="stylesheet" type="text/css">

		<!-- Vendor CSS -->
		<link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.css" />
		<link rel="stylesheet" href="assets/vendor/font-awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="assets/vendor/magnific-popup/magnific-popup.css" />
		<link rel="stylesheet" href="assets/vendor/bootstrap-datepicker/css/datepicker3.css" />

		<!-- Specific Page Vendor CSS -->
		<link rel="stylesheet" href="assets/vendor/select2/select2.css" />
		<link rel="stylesheet" href="assets/vendor/jquery-datatables-bs3/assets/css/datatables.css" />

		<!-- Theme CSS -->
		<link rel="stylesheet" href="assets/stylesheets/theme.css" />

		<!-- Skin CSS -->
		<link rel="stylesheet" href="assets/stylesheets/skins/default.css" />

		<!-- Theme Custom CSS -->
		<link rel="stylesheet" href="assets/stylesheets/theme-custom.css">

		<!-- Head Libs -->
		<script src="assets/vendor/modernizr/modernizr.js"></script>

	</head>
	<body>
			<%
			Context contextInner=new Context();
			contextInner.createContextFromSession(session);

			%>
			
					<%@ include file = "commonPages/tablePreProcess.jsp" %>
											
					<!-- start: page -->
					<section class="panel">
							<%
							if(Util.checkEmpty(strParentDataId))
							{
							%>
							<header class="panel-heading">
								<div class="panel-actions">
									<a href="#" class="fa fa-caret-down"></a>
									<a href="#" class="fa fa-times"></a>
								</div>
								
								<h2 class="panel-title"><%=strTableTitle %></h2>
							</header>
							<%
							}
							%>
							<div class="panel-body">
								<div class="row">
									<div class="col-sm-6">
										<div class="mb-md">
										
										<%
											String strParam=request.getQueryString()+"&objectType="+strObjectType+"&selectRow=single&pageType=Search&prevUrl=";
											
										%>
											<form action="searchTable.jsp?<%=strParam%>" method="post">
												<button id="addToTable" class="btn btn-primary">Add <i class="fa fa-plus"></i></button>
												<%
												if(!Util.checkEmpty(strParentDataId))
												{
												%>
													<button type="submit" class="btn btn-primary">Add Existing <i class="fa fa-check"></i></button>
												<%
												}
												%>
											</form>
										</div>
									</div>
								</div>
								
								<table class="table table-bordered table-striped mb-none" id="datatable-editable">
									<thead>
										<tr>
											
											<%
											for(String strColumnDisplay : arrColumnDisplayNames)
											{
												%>
													<th><%=strColumnDisplay %></th>
												<%
											}
											%>
											<th>Actions</th>
										</tr>
									</thead>
									<tbody>
										<%
										
										
										for(int cntDocData=6; cntDocData < allDocs.size(); cntDocData++)
										{
											Document docdata = allDocs.get(cntDocData);
										%>
										<tr class="gradeX">
											<%
											String strDataId=docdata.getObjectId("_id").toString();
											String strNestedUrl=docTableProperties.getString("nestedURL").replace("parentDataId=", "parentDataId="+strDataId);
											for(String strColumnName: arrColumnNames)
											{
												if(!Util.checkEmpty(strParentDataId) && strColumnName.equals("_id"))
												{
												%>
													<td class="<%=docColumnClass.get(strColumnName).toString().replaceAll("'", "")%>"><a  href="#" onClick="clickOnRelatedURL('<%=strNestedUrl%>');"><%=  docdata.get(strColumnName)%></a></td>
												<%
												}
												else if(strColumnName.equals("_id"))
												{
												%>
													<td class="<%=docColumnClass.get(strColumnName).toString().replaceAll("'", "")%>"><a href="<%=strNestedUrl%>"><%=  docdata.get(strColumnName)%></a></td>
												<%
												}
												else
												{
													if(docColumnClass.get(strColumnName).equals("'list'"))
													{
														
														String strObjectDataName = "";
														try
														{
															strObjectDataName = ObjectData.getSchemaObjectDataName(docdata.getObjectId(strColumnName).toString(), contextInner);
														}
														catch(Exception e)
														{
															
														}
														%>
														<td class="<%=docColumnClass.get(strColumnName).toString().replaceAll("'", "")%>"><%= strObjectDataName %></td>
														<%
													}
													else if(docColumnClass.get(strColumnName).equals("'object'"))
													{
														
														String strObjectDataName = "";
														try
														{
															strObjectDataName = ObjectData.getObjectDataName(docdata.getObjectId(strColumnName).toString(), contextInner);
														}
														catch(Exception e)
														{
															
														}
														%>
														<td class="<%=docColumnClass.get(strColumnName).toString().replaceAll("'", "")%>"><%= strObjectDataName %></td>
														<%
													}
													else
													{
														%>
														<td class="<%=docColumnClass.get(strColumnName).toString().replaceAll("'", "")%>"><%=  docdata.get(strColumnName)%></td>
														<%
													}
												}
											} 
											%>
											<td class="actions">
												<a href="#" class="hidden on-editing save-row"><i class="fa fa-save"></i></a>
												<a href="#" class="hidden on-editing cancel-row"><i class="fa fa-times"></i></a>
												<a href="#" class="on-default edit-row"><i class="fa fa-pencil"></i></a>
												<a href="#" class="on-default delete-row"><i class="fa fa-trash-o"></i></a>
												<%
													if(!Util.checkEmpty(strParentDataId))
													{
												%>
												<a href="#" class="on-default remove-row"><i class="fa fa-unlink"></i></a>
												<%
													}
												%>
											</td>
										</tr>
										<%
										}
										%>
									</tbody>
								</table>
							</div>
						</section>
					<!-- end: page -->
				

	<div id="dialog" class="modal-block mfp-hide">
			<section class="panel">
				<header class="panel-heading">
					<h2 class="panel-title">Are you sure?</h2>
				</header>
				<div class="panel-body">
					<div class="modal-wrapper">
						<div class="modal-text">
							<p>Are you sure that you want to delete this row?</p>
						</div>
					</div>
				</div>
				<footer class="panel-footer">
					<div class="row">
						<div class="col-md-12 text-right">
							<button id="dialogConfirm" class="btn btn-primary">Confirm</button>
							<button id="dialogCancel" class="btn btn-default">Cancel</button>
						</div>
					</div>
				</footer>
			</section>
		</div>
		
		<div id="dialog" class="modal-block mfp-hide">
			<section class="panel">
				<header class="panel-heading">
					<h2 class="panel-title">Are you sure?</h2>
				</header>
				<div class="panel-body">
					<div class="modal-wrapper">
						<div class="modal-text">
							<p>Are you sure that you want to delete this row?</p>
						</div>
					</div>
				</div>
				<footer class="panel-footer">
					<div class="row">
						<div class="col-md-12 text-right">
							<button id="dialogConfirm" class="btn btn-primary">Confirm</button>
							<button id="dialogCancel" class="btn btn-default">Cancel</button>
						</div>
					</div>
				</footer>
			</section>
		</div>
		
		<div id="dialogRemove" class="modal-block mfp-hide">
			<section class="panel">
				<header class="panel-heading">
					<h2 class="panel-title">Are you sure?</h2>
				</header>
				<div class="panel-body">
					<div class="modal-wrapper">
						<div class="modal-text">
							<p>Are you sure that you want to remove this row?</p>
						</div>
					</div>
				</div>
				<footer class="panel-footer">
					<div class="row">
						<div class="col-md-12 text-right">
							<button id="dialogRemoveConfirm" class="btn btn-primary">Confirm</button>
							<button id="dialogRemoveCancel" class="btn btn-default">Cancel</button>
						</div>
					</div>
				</footer>
			</section>
		</div>
		
		<!-- Vendor -->
		<script src="assets/vendor/jquery/jquery.js"></script>
		<script src="assets/vendor/jquery-browser-mobile/jquery.browser.mobile.js"></script>
		<!--  <script src="assets/vendor/bootstrap/js/bootstrap.js"></script>-->
		<!--  <script src="assets/vendor/nanoscroller/nanoscroller.js"></script>-->
		<script src="assets/vendor/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
		<script src="assets/vendor/magnific-popup/magnific-popup.js"></script>
		<script src="assets/vendor/jquery-placeholder/jquery.placeholder.js"></script>
		
		<!-- Specific Page Vendor -->
		<script src="assets/vendor/select2/select2.js"></script>
		<script src="assets/vendor/jquery-datatables/media/js/jquery.dataTables.js"></script>
		<script src="assets/vendor/jquery-datatables-bs3/assets/js/datatables.js"></script>
		
		<!-- Theme Base, Components and Settings -->
		<script src="assets/javascripts/theme.js"></script>
		
		<!-- Theme Custom -->
		<script src="assets/javascripts/theme.custom.js"></script>
		
		<!-- Theme Initialization Files -->
		<script src="assets/javascripts/theme.init.js"></script>


		<!-- Examples -->
		<script>
		
		var nullColumn = [<%=JSColumnProperties%>];
		var blankColumn = [<%=JSColumnClass%>];
		var tableColumns = '<%=JSColumnNames%>';
		var tableColumnData = [<%=JSColumnData%>];
		var objectType = "<%=strObjectType%>";
		var parentDataId = "<%=strParentDataId%>";
		var columnOptionsWithValues = [<%=JSColumnOptionsWithValues%>];
		

		function clickOnRelatedURL(url)
		{
			debugger;
			parent.location.href=url;
		}
		</script>
		<script src="assets/javascripts/tables/examples.datatables.editable.js"></script>
		<script src="assets/javascripts/tables/examples.datatables.ajax.js"></script>
		
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="select2.min.js"></script>
		<script>
			$("#country").select2( {
				placeholder: "Select Country",
				allowClear: true
				} );
		</script>
	</body>
</html>