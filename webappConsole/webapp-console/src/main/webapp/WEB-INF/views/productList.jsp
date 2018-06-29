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

<script type="text/javascript">
	$(document).ready(function() {
		$("#productTable").dataTable({
			"sPaginationType" : "full_numbers",
			"bJQueryUI" : true
		});
	});
</script>
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


		<div class="w3-row-padding w3-border w3-border-cyan" id="myMain">
			<br /> <b><h2 style="margin-left: 50dp">Product List</h2></b> <br /> <br />
<table id="productTable" class="table table-hover">
	    		<thead>
		      		<tr>
				        <th>ProductId</th>
				        <th>Name</th>
				        <th>Description</th>
				        <th>Category</th>
				        <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
				        	<th width="100"></th>
				        </sec:authorize>
				        <sec:authorize access="hasRole('ADMIN')">
				        	<th width="100"></th>
				        </sec:authorize>
				        
					</tr>
		    	</thead>
	    		<tbody>
				<c:forEach items="${productList}" var="prod">
					<tr>
						<td>${prod.prodId}</td>
						<td>${prod.name}</td>
						<td>${prod.description}</td>
						<td>${prod.prodCategory.type}</td>
					    <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
							<td><a href="<c:url value='/editProduct${prod.prodId}' />" class="btn btn-success custom-width">edit</a></td>
				        </sec:authorize>
				        <sec:authorize access="hasRole('ADMIN')">
							<td><a href="<c:url value='/deleteProduct-${prod.prodId}' />" class="btn btn-danger custom-width">delete</a></td>
        				</sec:authorize>
					</tr>
				</c:forEach>
	    		</tbody>
	    	</table>
	<sec:authorize   access="hasRole('ADMIN')" >
		 	<div class="well">
		 		<a href="<c:url value='/newproduct'/>" >Add New Product</a>
		 	</div>
	 	</sec:authorize>


</div>
<!-- Footer -->
	<%@include file="Footer.jsp"%>
	</div>


<!-- ..............this is the part of userlist page.............. -->
	<%-- <div class="generic-container">
		<%@include file="authheader.jsp" %>	
		<div class="panel panel-default">
			  <!-- Default panel contents -->
		  	<div class="panel-heading"><span class="lead">List of Users </span></div>
		
		</div>
	
   	</div> --%>
</body>
</html>