<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="/">
<title>Asset Management</title>
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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

<script type="text/javascript">
	$(document).ready(function() {
		$("#userTable").dataTable({
			"sPaginationType" : "full_numbers",
			"bJQueryUI" : true
		});
	});
</script>
</head>
<body class="w3-light-grey w3-content"
	style="max-width: 1600px" ng-app="myApp" ng-Controller="myCtrl">

	<!-- Sidenav/menu -->
	<!-- Sidenav/menu -->
	<%@include file="nav.jsp"%>
	<!-- Overlay effect when opening sidenav on small screens -->
	<div class="w3-overlay w3-hide-large w3-animate-opacity"
		onclick="w3_close()" style="cursor: pointer" title="close side menu"
		id="myOverlay"></div>

	<!-- !PAGE CONTENT! -->
	<div class="w3-main w3-border"
		style="margin-left: 340px; margin-right: 40px">
		<!-- Header -->
		<%@include file="Header.jsp"%>
		<div class="w3-row-padding w3-border w3-border-cyan" id="myMain">
			<br /> <b><h2 style="margin-left: 50dp">User List</h2></b> <br /> <br />
					<table id="userTable" class="display">
						<thead>
							<tr>
								<th>Firstname</th>
								<th>Lastname</th>
								<th>Email</th>
								<th>SSO ID</th>
								<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
									<th width="100"></th>
								</sec:authorize>
								<sec:authorize access="hasRole('ADMIN')">
									<th width="100"></th>
								</sec:authorize>

							</tr>
						</thead>
						<!-- <tbody id="content_view" style="font-size: 14px">
				</tbody>-->
						<tbody>
							<c:forEach items="${users}" var="user">
								<tr>
									<td>${user.firstName}</td>
									<td>${user.lastName}</td>
									<td>${user.email}</td>
									<td>${user.ssoId}</td>
									<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
										<td><a href="<c:url value='/edit-user-${user.ssoId}' />"
											class="btn btn-success custom-width">edit</a></td>
									</sec:authorize>
									<sec:authorize access="hasRole('ADMIN')">
										<td><a
											href="<c:url value='/delete-user-${user.ssoId}' />"
											class="btn btn-danger custom-width">delete</a></td>
									</sec:authorize>
								</tr>
							</c:forEach>
						</tbody>
						</div>
					</table>
					<sec:authorize access="hasRole('ADMIN')">
						<div class="well">
							<a href="<c:url value='/newuser'/>">Add New User</a>
						</div>
					</sec:authorize>
			<!-- Footer -->
			<%@include file="Footer.jsp"%>
		</div>
</body>
</html>