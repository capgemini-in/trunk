<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@include file="include.jsp"%>

<html>
 
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Spring 4 MVC File Upload Example</title>
    
   <%--  <link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet" type="text/css"></link>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet" type="text/css"></link> --%>
</head>
<body class="w3-light-grey w3-content" style="max-width: 1600px"
	ng-app="myApp" ng-Controller="myCtrl">
 
 <%@include file="nav.jsp"%>
 
 	<div class="w3-overlay w3-hide-large w3-animate-opacity"
		onclick="w3_close()" style="cursor: pointer" title="close side menu"
		id="myOverlay"></div>

	<!-- !PAGE CONTENT! -->
<div class="w3-main w3-border" style="margin-left: 340px; margin-right: 40px ;" >
		<!-- Header -->
		<%@include file="Header.jsp"%>
 <br>
 <br>
 
 		<div class="w3-row-padding w3-border w3-border-cyan" id="myMain">
			<br /> <b><h2 style="margin-left: 50dp"></h2></b> <br /> <br />
        
        <form:form method="POST" action="./singleUpload?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data" modelAttribute="fileBucket">
        
       <%--  <form:form method="POST" modelAttribute="fileBucket" enctype="multipart/form-data" class="form-horizontal"> --%>
         
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="file">Upload a Excel file</label>
                    <div class="col-md-7">
                        <form:input type="file" path="file" id="file" class="form-control input-sm"/>
                        <div class="has-error">
                            <form:errors path="file" class="help-inline"/>
                        </div>
                    </div>
                </div>
            </div>
     
            <div class="row">
                <div class="form-actions floatRight">
                <center>    <input type="submit" value="Upload" class="btn btn-primary btn-sm"> </center>
                </div>
				<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
            </div>
        </form:form>
        
    </div>
</body>
</html>
