<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Asset Management</title>
<!-- <script>
	document.getElementById('id01').style.display = 'block';
</script>-->

<link
	href="<c:url value='/static/pagination/media/dataTables/demo_page.css' />"
	rel="stylesheet"></link>
<link
	href="<c:url value='/static/pagination/media/dataTables/demo_table.css' />"
	rel="stylesheet"></link>
<link
	href="<c:url value='/static/pagination/media/dataTables/demo_table_jui.css' />"
	rel="stylesheet"></link>
<link
	href="<c:url value='/static/pagination/media/themes/base/jquery-ui.css' />"
	rel="stylesheet" media="all"></link>
<link
	href="<c:url value='/static/pagination/media/themes/smoothness/jquery-ui-1.7.2.custom.css' />"
	rel="stylesheet"></link>
<script src="<c:url value='/static/pagination/scripts/jquery.js' />"
	type="text/javascript"></script>
<script
	src="<c:url value='/static/pagination/scripts/jquery.dataTables.min.js'/>"
	type="text/javascript"></script>


</head>
<body class="w3-light-grey w3-content" style="max-width: 1600px"
	ng-app="myApp" ng-Controller="myCtrl">

	<!-- Sidenav/menu -->
	<!-- Sidenav/menu -->
	<%@include file="nav.jsp"%>

	<!-- Overlay effect when opening sidenav on small screens -->
	<div class="w3-overlay w3-hide-large w3-animate-opacity"
		onclick="w3_close()" style="cursor: pointer" title="close side menu"
		id="myOverlay"></div>

	<!-- !PAGE CONTENT! -->
<div class="w3-main w3-border" style="margin-left: 340px; margin-right: 40px">
		<!-- Header -->
		<%@include file="Header.jsp"%>
		<h3 style="color: red">	${status}</h3>
			<h3 style="color: red">	${Error}</h3>
	

										<div class="w3-row-padding w3-border w3-border-cyan" id="myMain">
			<br /> <b><h2 style="margin-left: 50dp">Email Notification</h2></b> <br /> <br />
						<h3 style="color: red">${error}</h3>
													<form:form action="./sendemail" method="POST" modelAttribute="emailBean" class="form-horizontal">
													<div class="row">
					<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="reciepents">Recipents</label>
					<div class="col-md-7">	
												<form:input type="text" path="fromEmail"  value="${emailBean.fromEmail}" placeholder="From Email" id="reciepents" class="form-control input-sm" />
												<div class="has-error">
												<form:errors path="fromEmail" class="help-inline"/>
												</div>
												<form:input type="text" path="toEmail"  value="${emailBean.toEmail}" id="toEmail" placeholder="toEmail" class="form-control input-sm" />
												<form:input type="text" path="ccEmail"  value="${emailBean.ccEmail}"  placeholder="ccEmail"  id="toEmail" class="form-control input-sm" />
												<form:input type="text" path="subject"  value="${emailBean.subject}" placeholder="Enter Subject" id="subject" class="form-control input-sm" />
													
														</div>
</div>
</div>
<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="to">Message</label>
					<div class="col-md-7">
													<form:textarea path="content"  value="${emailBean.content}" rows="5" cols="67"  placeholder="Type your Message"/>	
													</div></div></div>	
													<div class="user-grids">
																<div class="user-right">
																	<input type="submit" value="Send Notification" class="btn btn-primary btn-sm"/> 
																</div>
																<div class="clear"></div>
													</div>
													</form:form>
												</div>
</div>
</body>
</html>