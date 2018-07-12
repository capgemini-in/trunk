	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@include file="include.jsp"%>

<html>
 
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Spring 4 MVC File Upload Example</title>
	
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
<script src="https://www.paypalobjects.com/api/checkout.js"></script>
<script type="text/javascript" src="static/js/tikslus360.js"></script>
<script type="text/javascript" src="static/js/rainbow.min.js"></script>
<link rel="stylesheet" href="static/css/normalize.css" />
<link rel="stylesheet" href="static/css/tikslus360.css" />
<link rel="stylesheet" href="static/css/github.css" />    

<style>
input { 
    /* removes default styling from input color element */
    padding: 0;
    border: none; 
    /* makes input (color swatch) 100% size of container */
    position: absolute;
    width: 100%; 
    height: 100%;
    /* mix blend mode makes the color of the swatch alter the image behind it. */
    mix-blend-mode: overlay; /* try screen, multiply or other blend modes for different effects */
    cursor: pointer;
}

/* removes default styling from input color element */
::-webkit-color-swatch {
    border: none;
}

::-webkit-color-swatch-wrapper {
    padding: 0;
}

::-moz-color-swatch,
::-moz-focus-inner {
    border: none;
}

::-moz-focus-inner {
    padding: 0;
}



.button {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
}
.button2 {background-color: #008CBA;} /* Blue */
.button3 {background-color: #f44336;} /* Red */ 
.button4 {background-color: #FF4500;} /* Orange */ 
.button5 {background-color: #C0C0C0;} /* Grey */

</style>

 <script type="text/javascript">
 
 var daytxt='carimages';
 </script>
</head>
<body class="w3-light-grey w3-content" style="max-width: 1600px"
	jng-app="myApp" kng-Controller="myCtrl">
 
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
 
 		<div class="w3-row-padding w3-border w3-border-cyan" id="myMain" >
		<div id="2div">
		<h3>Select the required Car Model</h3>
		<h4><select id="carId">
<option value="">--Select--</option>
<option value="BMW">BMW</option>
<option value="carimages">carimages</option>
</select></h4>


</div>

<!--             This is the div for the BMW and Carimages         -->
		<div id="view360" 	 style="margin-left:20px">
		<a href="#" class="autorotate">Auto rotate</a>
		</div>
		<div id="view361" style="margin-left:20px;display:none">
		<a href="#" class="autorotate">Auto rotate</a>
		</div>
		<!-- it ends here-->		
	<!--	This div for the Orange color images of BWM and Carimages  -->
		<div id="view362" style="margin-left:20px;display:none">
		<a href="#" class="autorotate">Auto rotate</a>
		</div>		
		
		<div id="view363" style="margin-left:20px;display:none">
		<a href="#" class="autorotate">Auto rotate</a>
		</div>		
<!-- it ends here-->		

		<!--	This div for the Green color images of BWM and Carimages  -->

		<div id="view364" style="margin-left:20px;display:none">
		<a href="#" class="autorotate">Auto rotate</a>
		</div>		
		
		<div id="view365" style="margin-left:20px;display:none">
		<a href="#" class="autorotate">Auto rotate</a>
		</div>		
				<!-- it ends here-->		
				
		<!--	This div for the Blue color images of BWM and Carimages  -->

		<div id="view366" style="margin-left:20px;display:none">
		<a href="#" class="autorotate">Auto rotate</a>
		</div>		
		
		<div id="view367" style="margin-left:20px;display:none">
		<a href="#" class="autorotate">Auto rotate</a>
		</div>		
				<!-- it ends here-->		

		<!--	This div for the Blue color images of BWM and Carimages  -->
		<div id="view368" style="margin-left:20px;display:none">
		<a href="#" class="autorotate">Auto rotate</a>
		</div>		
		
		<div id="view369" style="margin-left:20px;display:none">
		<a href="#" class="autorotate">Auto rotate</a>
		</div>		
				<!-- it ends here-->		

		<!--	This div for the Blue color images of BWM and Carimages  -->
		<div id="view370" style="margin-left:20px;display:none">
		<a href="#" class="autorotate">Auto rotate</a>
		</div>		
		
		<div id="view371" style="margin-left:20px;display:none">
		<a href="#" class="autorotate">Auto rotate</a>
		</div>		
				<!-- it ends here-->		
				



				
		<!--	This div for the  color images of BWM and Carimages  -->	
   <div id="StayOpen">
<a href="#" id="toggleSwitch_2"><h3>Choose a Color for Your Car</h3></a>
<div  id="colorbutton" class="colorbutton" style="display:none padding: 45px 52px;font-size: 16px margin: 4px 2px;" >
<button id="green"class="button" ></button>
<button id="blue" class="button button2"></button>
<button id="red" class="button button3"></button>
<button id="orange" class="button button4"></button>
<button id="grey"class="button button5"></button>
</div>
</div>
<br>
<br>	

<!-- JAVA Scrpit-->

		<script language="javascript">
		var tk;
		$(window).load(function(){
			
			tk = $("#view360").tikslus360({imageDir:'./images/'+daytxt,imageCount:36,imageExt:'jpg' ,canvasID:'mycar',canvasWidth:700,canvasHeight:440,autoRotate:false});
			
		});
		</script>
		<script>
		$("#carId").change(function(){
		daytxt=$(this).val();
		if(daytxt=="BMW")
			{
				alert(daytxt);
				$("#view360, #view362,#view363,#view364,#view365,#view366,#view367,#view368,#view369,#view370,#view371").hide();
				$("#view361").show().tikslus360({imageDir:'./images/'+daytxt,imageCount:36,imageExt:'jpg',canvasID:daytxt,canvasWidth:700,canvasHeight:440,autoRotate:false});
			}
			else if(daytxt=="carimages")
			{
				$("#view361, #view362,#view363,#view364,#view365,#view366,#view367,#view368,#view369,#view370,#view371").hide();
				$("#view360").show().tikslus360({imageDir:'./images/'+daytxt,imageCount:36,imageExt:'jpg',canvasID:daytxt,canvasWidth:700,canvasHeight:440,autoRotate:false});
		    		
			}
		  });
		</script>
				
<script>
    $("#orange").click(function()
	{
	if(daytxt=="carimages")
	{
	$("#view360, #view361,#view363,#view364,#view365,#view366,#view367,#view368,#view369,#view370,#view371").hide();
	$("#view362").show().tikslus360({imageDir:'./images/carimagesorange',imageCount:1,imageExt:'jpg',canvasID:daytxt,canvasWidth:700,canvasHeight:440,autoRotate:false});
	}
	else if(daytxt=="BMW")
	{
	 $("#view360").hide();
	 //<!--code for the BMW IMAGE-->
	}
	});
	
	
	//<!--this is for the green color
	$("#green").click(function()
{
if(daytxt=="carimages")
	{
		$("#view360, #view361,#view362,#view363,#view365,#view366,#view367,#view368,#view369,#view370,#view371").hide();
		$("#view364").show().tikslus360({imageDir:'./images/carimagesgreen',imageCount:1,imageExt:'jpg',canvasID:daytxt,canvasWidth:700,canvasHeight:440,autoRotate:false});
	}
	else if(daytxt=="BMW")
	{
	 $("#view360").hide();
	 <!--code for the BMW IMAGE
	}

		});
	
	
$("#blue").click(function()
{
if(daytxt=="carimages")
	{
		$("#view360, #view361,#view362,#view363,#view364,#view365,#view367,#view368,#view369,#view370,#view371").hide();
		$("#view366").show().tikslus360({imageDir:'./images/carimagesblue',imageCount:1,imageExt:'jpg',canvasID:daytxt,canvasWidth:700,canvasHeight:440,autoRotate:false});
	}
	else if(daytxt=="BMW")
	{
	 $("#view360").hide();
	 <!--code for the BMW IMAGE
	}
	});
		
$("#red").click(function()
{
if(daytxt=="carimages")
	{
		$("#view360, #view361,#view362,#view363,#view364,#view365,#view366,#view367,#view369,#view370,#view371").hide();
		$("#view368").show().tikslus360({imageDir:'./images/carimagesred',imageCount:1,imageExt:'jpg',canvasID:daytxt,canvasWidth:700,canvasHeight:440,autoRotate:false});
	}
	else if(daytxt=="BMW")
	{
	 $("#view360").hide();
	 <!--code for the BMW IMAGE
	}
	});

$("#grey").click(function()
{
if(daytxt=="carimages")
	{
		$("#view360, #view361,#view362,#view363,#view364,#view365,#view366,#view367,#view368,#view369,#view371").hide();
		$("#view370").show().tikslus360({imageDir:'./images/carimagesgry',imageCount:1,imageExt:'jpg',canvasID:daytxt,canvasWidth:700,canvasHeight:440,autoRotate:false});
	}
	else if(daytxt=="BMW")
	{
	 $("#view360").hide();
	 <!--code for the BMW IMAGE
	}
	});
</script>
<script>
     $("#StayOpen").hover(
    function() {
        $("#colorbutton").slideDown(500);
    }, function() {
        $("#colorbutton").slideUp(500);
    });

</script>
		
		</div>
    </div>
</body>
</html>
