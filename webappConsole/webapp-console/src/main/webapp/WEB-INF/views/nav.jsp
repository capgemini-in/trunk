<!-- Sidenav/menu -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<nav
	class="w3-sidenav w3-text-white w3-collapse w3-blue w3-animate-left"
	style="z-index: 3; width: 300px;" id="mySidenav">
	<br>
	<p class="w3-right w3-hide" onClick="openchat()" id="myChatLogo">
		<i class="fa fa-comments fa-fw w3-margin-right"></i>
	</p>
	<div class="w3-container">
		<a href="#" onclick="w3_close()"
			class="w3-hide-large w3-right w3-jumbo w3-padding" title="close menu">
			<i class="fa fa-remove"></i>
		</a><img src="<c:url value="/images/logo.jpg" />" style="width: 100%; height: 15%" class="w3-round"> 
 
		
		
		<br>
		<br>


		<hr style="width: 100%; border: 3px solid white" class="w3-round">

		<h3>
			<b>Capgemini India </b>
		</h3>
		<p class=""></p>
	</div>
	<sec:authorize access="hasRole('ADMIN')">
	<div class="w3-dropdown-hover">
		<a href ="<c:url value='/products'/>" 
			class="w3-padding w3-text-white w3-border w3-round-xxlarge" id="0"><i
			class="fa fa-diamond fa-fw w3-margin-right"></i>Master</a>
		<div id="myAdmin" style="margin-left:10%;width:80%"
			class="w3-dropdown-content w3-cyan w3-animate-zoom w3-right w3-padding w3-medium">
				<a
				href="<c:url value='/products'/>" onclick="w3_close()"
				class="w3-padding w3-border w3-round-xxlarge" id="3"><i
				class="fa fa-suitcase fa-fw w3-margin-right"></i>Product</a>
				<a  onclick="w3_close()"
				class="w3-padding w3-border w3-round-xxlarge" id="2"><i
				class="fa fa-street-view fa-fw w3-margin-right"></i>Customer</a> 
				<a
			 onclick="w3_close()"
				class="w3-padding w3-border w3-round-xxlarge" id="4"><i
				class="fa fa-rss fa-fw w3-margin-right"></i>Services</a>
		</div>
	</div>
</sec:authorize>

	<sec:authorize   access="hasRole('ADMIN')" >		 	
	<div class="w3-dropdown-hover">
		<a href="/pocwebapp/list" onclick="w3_close()"
			class="w3-text-white w3-padding w3-border w3-round-xxlarge" id="33"><i
			class="fa fa-users fa-fw w3-margin-right"></i>User Management</a>
		<div style="margin-left:10%;width:80%"
			class="w3-dropdown-content w3-cyan  w3-animate-zoom w3-right w3-padding w3-medium">
	<a href ="<c:url value='/list' />" class="w3-text-white w3-padding w3-border w3-round-xxlarge" id="34"><i
				class="fa fa-user-circle-o fa-fw w3-margin-right"></i>User</a>

				<a href ="<c:url value='/userpermission' />"
				class="w3-text-white w3-padding w3-border w3-round-xxlarge" id="35"><i
				class="fa fa-tree fa-fw w3-margin-right"></i>User Hierarchy</a>
				 <a href ="<c:url value='/userpermission' />"
				 class="w3-text-white w3-padding w3-border w3-round-xxlarge" id="36"><i
				class="fa fa-user-secret fa-fw w3-margin-right"></i>Role</a> 
				<a href ="<c:url value='/usermenuright' />"
				class="w3-text-white w3-padding w3-border w3-round-xxlarge" id="37"><i
				class="fa fa-tv fa-fw w3-margin-right"></i>Screen Permission</a>
				<a href ="<c:url value='/singleUpload' />" class="w3-text-white w3-padding w3-border w3-round-xxlarge" id="41"><i
				class="fa fa-user-circle-o fa-fw w3-margin-right"></i> Master Data Upload</a>
		</div>
	</div>
		 	</sec:authorize>
	

	<!--  <div class="w3-dropdown-hover">
   <a href="#search"  class="w3-padding w3-border w3-round-xxlarge" id="6"><i class="fa fa-user fa-fw w3-margin-right"></i>IT admin</a>
  
  
    <div id="myAdmin" class="w3-dropdown-content w3-deep-orange w3-animate-zoom w3-right w3-padding w3-medium">
	  <a href="/Gionee/POC/userMachineAccess" onclick="w3_close()" class="w3-padding w3-border w3-round-xxlarge" id="7"><i class="fa fa-wrench fa-fw w3-margin-right"></i>User Machine Access</a>
   <a href="/Gionee/POC/admin" onclick="w3_close()" class="w3-padding w3-border w3-round-xxlarge" id="8"><i class="fa fa-diamond fa-fw w3-margin-right"></i>User management</a>
   
   
    <a href="/Gionee/POC/userScreen" onclick="w3_close()" class="w3-padding w3-border w3-round-xxlarge" id="12"><i class="fa fa-bolt fa-fw w3-margin-right"></i>User Role</a>
 
   <a href="/Gionee/POC/screen" onclick="w3_close()" class="w3-padding w3-border w3-round-xxlarge" id="9"><i class="fa fa-superpowers fa-fw w3-margin-right"></i>Role Screen Permissions</a>
  
     
    </div>
   </div> -->
	<a href="#search" onclick="w3_close()"
		class="w3-text-white w3-padding w3-border w3-round-xxlarge" id="40"><i
		class="fa fa-search fa-fw w3-margin-right"></i>SEARCH</a> <a href="#about"
		onclick="w3_close()"
		class="w3-text-white w3-padding w3-border w3-round-xxlarge" id="41"><i
		class="fa fa-male fa-fw w3-margin-right"></i>ABOUT</a> <a href="#contact"
		onclick="w3_close()"
		class="w3-text-white w3-padding w3-border w3-round-xxlarge" id="42"><i
		class="fa fa-envelope fa-fw w3-margin-right"></i>CONTACT</a> 
		<a href ="<c:url value='/logout' />" 
		class="w3-text-white w3-padding w3-border w3-round-xxlarge" ><i
		class="fa fa-sign-out fa-fw w3-margin-right"></i>Log Out</a>

</nav>
<div id="confirmPopup" class="w3-right floating" style="display: none">
	<%-- <%@ include file="chatWindow.jsp"%> --%>
</div>
<script>
	var sesAttr =
<%=request.getSession().getAttribute("sessionVar")%>
	if (sesAttr == 1 & '${userName}' != "admin") {
		var x = document.getElementById("myChatLogo");
		if (x.className.indexOf("w3-show") == -1) {
			x.className += " w3-show";
		} else {
			x.className = x.className.replace(" w3-show", "");
		}

	}

	if ('${userName}' == "admin") {
		var x = document.getElementById("myAdminLogo");
		if (x.className.indexOf("w3-show") == -1) {
			x.className += " w3-show";
		} else {
			x.className = x.className.replace(" w3-show", "");
		}

	} else {
		var x = document.getElementById("myOtherLogo");
		if (x.className.indexOf("w3-show") == -1) {
			x.className += " w3-show";
		} else {
			x.className = x.className.replace(" w3-show", "");
		}

	}

	function openchat() {
<%request.getSession().setAttribute("sessionVar", 1);%>
	$('#qnimate').addClass('popup-box-on');
		document.getElementById("confirmPopup").style.display = "block";
		$.ajax({url : "/LAPP_Rest_App/rest/lappChat/setChatStatus/${UserId}",
			success : function() {

			}
		});
	}
</script>
