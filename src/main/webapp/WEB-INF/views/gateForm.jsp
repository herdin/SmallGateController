<!DOCTYPE html>
<head>
	<title>Home</title>
	<%--
	<meta charset="utf-8">
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link href="../lib/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<script src="../lib/js/jquery-1.11.2.min.js"></script>
	<script src="../lib/js/bootstrap.min.js"></script>
	 --%>
</head>
<body>

<h3>Hello gateForm!</h3>

<div class="form-group col-md-4">
	GATE ID
	<input id="gateId" type="text" class="form-control" value=""/>	
</div>
<div class="form-group col-md-4">
	GATE DESCRIPTION
	<input id="gateDesc" type="text" class="form-control" value=""/>	
</div>
<div class="form-group col-md-4">
	AREA ID
	<input id="areaId" type="text" class="form-control" value=""/>	
</div>
<div class="form-group col-md-4">
	<input type="button" id="save" class="btn btn-default" value="save" />
</div>

<script type="text/javascript">
$(document).ready(function(){
	$("#save").click(function(){
        $.ajax({
        	url: "",
        	success:	function(result){
            				alert("success");
        				}
        });
	});	
});
</script>
</body>
</html>
