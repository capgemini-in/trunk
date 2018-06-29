<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="include.jsp"%>

<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Asset Management</title>
<script>
	document.getElementById('id01').style.display = 'block';
</script>

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
		$("#roleTable").dataTable({
			"sPaginationType" : "full_numbers",
			"bJQueryUI" : true
		});
	});
</script>
</head>
<body class="w3-light-grey w3-content" style="max-width: 1600px"
	ng-app="myApp" ng-Controller="myCtrl">

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
		<div class="w3-row-padding w3-border w3-border-deep-orange" id="myMain">
			<span class="w3-text-green">${message}</span><br />
			<div class="w3-container">
				User Role:
				<table	class="w3-table-all w3-hoverable w3-striped w3-bordered w3-border"
					id="roleTable">
					<thead>
					<tr>
						<th>User Name</th>
						<th>Role Name</th>
						<th>Action</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${users}" var="user">
						<tr>
							<td>${user[0]}</td>
							<td>${user[1]}</td>
							<td onclick="document.getElementById('id02').style.display='block'"><a href="" class="w3-margin-right"><i
									class="fa fa-edit fa-fw fa-2x w3-hover-text-orange"></i></a> <a
								href="" class=" w3-margin-right"><i
									class="fa fa-minus fa-fw fa-2x w3-hover-text-red"></i></a></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<br/>
				<br/>

	<%@include file="Footer.jsp"%>

		</div>
		<!-- Footer -->
	

	</div>
</body>

</html>