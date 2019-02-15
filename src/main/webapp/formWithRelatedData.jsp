<!doctype html>
<html class="fixed">
	<head>

		<!-- Basic -->
		<meta charset="UTF-8">

		<title>i-Dream</title>
		<meta name="keywords" content="HTML5 Admin Template" />
		<meta name="description" content="JSOFT Admin - Responsive HTML5 Template">
		<meta name="author" content="JSOFT.net">

		<!-- Mobile Metas -->
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

		<!-- Web Fonts  -->
		<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800|Shadows+Into+Light" rel="stylesheet" type="text/css">

		<!-- Vendor CSS -->
		<link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.css" />
		<link rel="stylesheet" href="assets/vendor/font-awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="assets/vendor/magnific-popup/magnific-popup.css" />
		<link rel="stylesheet" href="assets/vendor/bootstrap-datepicker/css/datepicker3.css" />

		<!-- Specific Table Page Vendor CSS -->
		<link rel="stylesheet" href="assets/vendor/select2/select2.css" />
		<link rel="stylesheet" href="assets/vendor/jquery-datatables-bs3/assets/css/datatables.css" />


		<!-- Specific Form Page Vendor CSS -->
		<link rel="stylesheet" href="assets/vendor/jquery-ui/css/ui-lightness/jquery-ui-1.10.4.custom.css" />
		<link rel="stylesheet" href="assets/vendor/bootstrap-multiselect/bootstrap-multiselect.css" />
		<link rel="stylesheet" href="assets/vendor/morris/morris.css" />

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
		<section class="body">

			<!-- start: header -->
			<%@ include file = "commonPages/header.jsp" %>
			<!-- end: header -->
			
			<div class="inner-wrapper">
				<!-- start: sidebar -->
				<%@ include file = "commonPages/mainMenu.jsp" %>
				<!-- end: sidebar -->

				<section role="main" class="content-body">
					<!-- start: navigation-->
					<%@ include file = "commonPages/navigationBar.jsp" %>
					<!-- end: navigation-->
					
					<%@ include file = "commonPages/formPreProcess.jsp" %>
						
					<!-- start: page -->
					<div class="row">
							<div class="col-lg-12">
								<section class="panel">
									<header class="panel-heading">
										<div class="panel-actions">
											<a href="#" class="fa fa-caret-down"></a>
											<a href="#" class="fa fa-times"></a>
										</div>
						
										<h2 class="panel-title">Form Elements</h2>
									</header>
									<div class="panel-body">
										<form class="form-horizontal form-bordered" method="get">
											<%
											if(allDocs.size()>1)
											{
												for(int cnt=1;cnt<allDocs.size();cnt++)
												{
													Document doc = allDocs.get(cnt);
													String strFieldName = doc.getString("name");
													String strDisplayName = doc.getString("displayName");
													String strInputType = doc.getString("inputType");
													String strEditType = doc.getString("editType");
													String strData = doc.getString("data");
													%>
													<div class="form-group">
														<label class="col-md-3 control-label" for="inputReadOnly"><%=strDisplayName %></label>
														<div class="col-md-6">
															<input type="<%=strInputType %>" value="<%=strData %>" id="<%=strFieldName %>" class="form-control" readonly="<%=strEditType %>">
														</div>
													</div>
													<% 
												}
											}
											%>
										</form>
									</div>
								</section>
							</div>
						</div>
						
						<section class="panel">
							<header class="panel-heading">
								<div class="panel-actions">
									<a href="#" class="fa fa-caret-down"></a>
									<a href="#" class="fa fa-times"></a>
								</div>
						
								<h2 class="panel-title">Related Data</h2>
							</header>
							<div class="panel-body">
								<div class="tabs">
									<ul class="nav nav-tabs">
										<li>
											<a href="#popular" data-toggle="tab">Popular</a>
										</li>
										<li class="active">
											<a href="#recent" data-toggle="tab">Recent</a>
										</li>
									</ul>
									<div class="tab-content">
										
										<div id="recent" class="tab-pane active">
											<div class="row">
												<div class="col-sm-6">
													<div class="mb-md">
														<button id="addToTable" class="btn btn-primary">Add <i class="fa fa-plus"></i></button>
													</div>
												</div>
											</div>
											
											<%@ include file = "commonPages/relatedTablePreProcess.jsp" %>
											
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
													
													
													for(int cntDocData=5; cntDocData < allRDocs.size(); cntDocData++)
													{
														Document docdata = allRDocs.get(cntDocData);
													%>
													<tr class="gradeX">
														<%
														String strDataId=docdata.getObjectId("_id").toString();
														String strNestedUrl=docTableProperties.getString("nestedURL").replace("parentDataId=", "parentDataId="+strDataId);
														for(String strColumnName: arrColumnNames)
														{
															if(strColumnName.equals("_id"))
															{
															%>
																<td class="<%=docColumnClass.get(strColumnName).toString().replaceAll("'", "")%>"><a href="<%=strNestedUrl%>"><%=  docdata.get(strColumnName)%></a></td>
															<%
															}
															else
															{
																%>
																<td class="<%=docColumnClass.get(strColumnName).toString().replaceAll("'", "")%>"><%=  docdata.get(strColumnName)%></td>
																<%
															}
														} 
														%>
														<td class="actions">
															<a href="#" class="hidden on-editing save-row"><i class="fa fa-save"></i></a>
															<a href="#" class="hidden on-editing cancel-row"><i class="fa fa-times"></i></a>
															<a href="#" class="on-default edit-row"><i class="fa fa-pencil"></i></a>
															<a href="#" class="on-default remove-row"><i class="fa fa-trash-o"></i></a>
														</td>
													</tr>
													<%
													}
													%>
												</tbody>
											</table>
										</div>
										<div id="popular" class="tab-pane">
											<p>Popular</p>
											<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitat.</p>
										</div>
									</div>
								</div>
								
							</div>
						</section>
					<!-- end: page -->
				</section>
				
				
			</div>
		</section>

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
		
		<!-- Vendor -->
		<script src="assets/vendor/jquery/jquery.js"></script>
		<script src="assets/vendor/jquery-browser-mobile/jquery.browser.mobile.js"></script>
		<script src="assets/vendor/bootstrap/js/bootstrap.js"></script>
		<script src="assets/vendor/nanoscroller/nanoscroller.js"></script>
		<script src="assets/vendor/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
		<script src="assets/vendor/magnific-popup/magnific-popup.js"></script>
		<script src="assets/vendor/jquery-placeholder/jquery.placeholder.js"></script>
		
		<!-- Specific Page Table Vendor -->
		<script src="assets/vendor/select2/select2.js"></script>
		<script src="assets/vendor/jquery-datatables/media/js/jquery.dataTables.js"></script>
		<script src="assets/vendor/jquery-datatables-bs3/assets/js/datatables.js"></script>
		
		
		<!-- Specific Page Vendor -->
		<script src="assets/vendor/jquery-ui/js/jquery-ui-1.10.4.custom.js"></script>
		<script src="assets/vendor/jquery-ui-touch-punch/jquery.ui.touch-punch.js"></script>
		<script src="assets/vendor/jquery-appear/jquery.appear.js"></script>
		<script src="assets/vendor/bootstrap-multiselect/bootstrap-multiselect.js"></script>
		<script src="assets/vendor/jquery-easypiechart/jquery.easypiechart.js"></script>
		<script src="assets/vendor/flot/jquery.flot.js"></script>
		<script src="assets/vendor/flot-tooltip/jquery.flot.tooltip.js"></script>
		<script src="assets/vendor/flot/jquery.flot.pie.js"></script>
		<script src="assets/vendor/flot/jquery.flot.categories.js"></script>
		<script src="assets/vendor/flot/jquery.flot.resize.js"></script>
		<script src="assets/vendor/jquery-sparkline/jquery.sparkline.js"></script>
		<script src="assets/vendor/raphael/raphael.js"></script>
		<script src="assets/vendor/morris/morris.js"></script>
		<script src="assets/vendor/gauge/gauge.js"></script>
		<script src="assets/vendor/snap-svg/snap.svg.js"></script>
		<script src="assets/vendor/liquid-meter/liquid.meter.js"></script>
		<script src="assets/vendor/jqvmap/jquery.vmap.js"></script>
		<script src="assets/vendor/jqvmap/data/jquery.vmap.sampledata.js"></script>
		<script src="assets/vendor/jqvmap/maps/jquery.vmap.world.js"></script>
		<script src="assets/vendor/jqvmap/maps/continents/jquery.vmap.africa.js"></script>
		<script src="assets/vendor/jqvmap/maps/continents/jquery.vmap.asia.js"></script>
		<script src="assets/vendor/jqvmap/maps/continents/jquery.vmap.australia.js"></script>
		<script src="assets/vendor/jqvmap/maps/continents/jquery.vmap.europe.js"></script>
		<script src="assets/vendor/jqvmap/maps/continents/jquery.vmap.north-america.js"></script>
		<script src="assets/vendor/jqvmap/maps/continents/jquery.vmap.south-america.js"></script>
		
		<!-- Theme Base, Components and Settings -->
		<script src="assets/javascripts/theme.js"></script>
		
		<!-- Theme Custom -->
		<script src="assets/javascripts/theme.custom.js"></script>
		
		<!-- Theme Initialization Files -->
		<script src="assets/javascripts/theme.init.js"></script>


		<script>
		debugger;
		var nullColumn = [<%=JSColumnProperties%>];
		var blankColumn = [<%=JSColumnClass%>];
		var tableColumns = '<%=JSColumnNames%>';
		var objectType = "<%=strObjectType%>";
		var parentDataId = "<%=strParentDataId%>";
		</script>
		<!-- Examples Table -->
		<script src="assets/javascripts/tables/examples.datatables.editable.js"></script>
		
		
		<!-- Examples Form -->
		<script src="assets/javascripts/dashboard/examples.dashboard.js"></script>
	</body>
</html>