<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<%--<link rel="shortcut icon" href="../../assets/ico/favicon.png"> --%>

	<title>Navbar Template for Bootstrap</title>
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<%--<link href="navbar.css" rel="stylesheet"> --%>

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<%--[if lt IE 9]>
	<script src="../../assets/js/html5shiv.js"></script>
	<script src="../../assets/js/respond.min.js"></script>
	<![endif]--%>
</head>

 <body>

   <div class="container">

     <!-- Static navbar -->
     <%--<div class="navbar navbar-default"> --%>
     <div class="nav nav-tabs">
       <div class="navbar-header">
         <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
         </button>
         <a class="navbar-brand" href="#">Small Gate Controller</a>
       </div>
       <div class="navbar-collapse collapse">
         <ul class="nav navbar-nav">
         	<li class="active">
         		<a href="#" id="area">Area</a></li>
         	<li><a href="#" id="gate">Gate</a></li>
         	<li><a href="#" id="group">Group</a></li>
			<li><a href="#" id="card">Card</a></li>
			
			<li class="dropdown">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
             <ul class="dropdown-menu">
               <li><a href="#">Action</a></li>
               <li><a href="#">Another action</a></li>
               <li><a href="#">Something else here</a></li>
               <li class="divider"></li>
               <li class="dropdown-header">Nav header</li>
               <li><a href="#">Separated link</a></li>
               <li><a href="#">One more separated link</a></li>
             </ul>
           </li>
         </ul>
         <%--
         <ul class="nav navbar-nav navbar-right">
           <li class="active"><a href="./">Default</a></li>
           <li><a href="../navbar-static-top/">Static top</a></li>
           <li><a href="../navbar-fixed-top/">Fixed top</a></li>
         </ul>
          --%>
       </div><!--/.nav-collapse -->
     </div>

     <!-- Main component for a primary marketing message or call to action -->
     <div class="jumbotron" id="content">
       <h1>Welcome,</h1>
       <p>This example is a quick exercise to illustrate how the default, static navbar and fixed to top navbar work. It includes the responsive CSS and HTML, so it also adapts to your viewport and device.</p>
       <p>
         <a class="btn btn-lg btn-primary" href="#">View managing docs &raquo;</a>
       </p>
     </div>

   </div> <!-- /container -->


   <!-- Bootstrap core JavaScript
   ================================================== -->
   <!-- Placed at the end of the document so the pages load faster -->
<script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#area").click(function(){
		$("#content").load("areaForm");
	});
	$("#gate").click(function(){
		$("#content").load("gateForm");
	});
	$("#group").click(function(){
		$("#content").load("groupForm");
	});
	$("#card").click(function(){
		$("#content").load("cardForm");
	});
});
</script>
</body>
</html>
