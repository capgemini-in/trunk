<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Asset Management</title>

<script>https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js</script>
<link href="cs/jquery.dataTables.min.css" rel="stylesheet"
	type="text/css" />
<script src="js/jquery-3.3.1.js" type="text/javascript"></script>
<script src="js/jquery.dataTables.min.js" type="text/javascript"></script>

<script type="text/javascript">

	document.getElementById('id01').style.display = 'block';
        $(document).ready(function () {
            $("#userTable").dataTable({
                "pagingType": "full_numbers"
            });
        });
</script>
<!--  <script>
	document.getElementById('id01').style.display = 'block';
	
	/*------------    Ajax call   -----------*/
    $(document).ready(function () {	
	$.ajax({
		url: '/list',
		data: {
			format: 'json'
		},
		error: function() {
			alert("Error");
		},
		success: function(data) {
			var userListData = JSON.parse(data);
			console.log(userListData);
			 $.each(userListData, function(i, ele) {
		     		$("#content_view").append("<tr><td>" + ele.firstName + "</td><td>" + ele.lastName + "</td><td>" + ele.emailt + "</td><td>" + ele.ssoId + "</td></tr>");
		     });
			$('#userTable').DataTable();
		},
		type: 'GET'
	});			
 });
</script>-->
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
	<div class="w3-main w3-border"
		style="margin-left: 340px; margin-right: 40px">
		<!-- Header -->
		<%@include file="Header.jsp"%>


		<div class="w3-row-padding w3-border w3-border-cyan" id="myMain">
			<br /> <b><h2 style="margin-left: 50dp">User List</h2></b> <br /> <br />
			
			<table id="userTable" class="table table-hover">
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
								<td><a href="<c:url value='/delete-user-${user.ssoId}' />"
									class="btn btn-danger custom-width">delete</a></td>
							</sec:authorize>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<sec:authorize access="hasRole('ADMIN')">
				<div class="well">
					<a href="<c:url value='/newuser'/>">Add New User</a>
				</div>
			</sec:authorize>


		</div>
		<!-- Footer -->
		<%@include file="Footer.jsp"%>
	</div>
</body>
</html>