<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add New Product</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
 	<div class="generic-container">
		<%@include file="authheader.jsp" %>

		<div class="well lead">Add New Product</div>
	 	<form:form method="POST" modelAttribute="productBean" class="form-horizontal">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="prodId">Product ID</label>
					<div class="col-md-7">
					<c:choose>
							<c:when test="${edit}">
								<form:input type="text" path= "prodId"  value="${productBean.prodId}" id="prodId" class="form-control input-sm" disabled="true"/>
							</c:when>
							<c:otherwise>
							<form:input type="text" path= "prodId"  value="${productBean.prodId}" id="prodId" class="form-control input-sm" />
								<div class="has-error">
									<form:errors path="prodId" class="help-inline"/>
								</div>
							</c:otherwise>
					</c:choose>
						
					
					</div>
				</div>
			</div>
							
			<div class="row">
			<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="name">Name</label>
					<div class="col-md-7">
						<form:input type="text" path="name"  value="${productBean.name}" id="name" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="name" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
						<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="description">Description</label>
					<div class="col-md-7">
						<form:input type="text" path="description" value="${productBean.description}" id="description" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="description" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
			
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="quantity">quantity</label>
					<div class="col-md-7">
						<form:input type="number" path="quantity" value="${productBean.quantity}"  id="quantity" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="quantity" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="price">Price</label>
					<div class="col-md-7">
						<form:input type="number" path="price"  value="${productBean.price}" id="price" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="price" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-3 control-lable" for="prodCategory.catId">Category</label>
							<div class="col-md-7">
							
								<form:select path="prodCategory.id" mutiple="false">
								<form:option value="${prodCategory.id}"  />
								<form:options items="${categories}" itemValue="id" itemLabel="type" class="form-control input-sm" />
								</form:select>
								<div class="has-error">
									<form:errors path="prodCategory.id" class="help-inline"/>
								</div>	
							</div>
						</div>
					</div>
							
			<div class="row">
				<div class="form-actions floatRight">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Update" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/products' />">Cancel</a>
						</c:when>
						<c:otherwise>
							<input type="submit" value="Add" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/products' />">Cancel</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>