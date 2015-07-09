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

<h3>Hello sampleForm!</h3>

<form role="form" action="/sgc/web/test/02" method="get">
	<div class="form-group col-xs-6">
		<label for="id">GATE ID</label>
		<input id="gateId" type="text" class="form-control" value=""/>	
	</div>
	<div class="form-group col-xs-6">
		<label for="gateDesc">GATE DESCRIPTION</label>
		<input id="gateDesc" type="text" class="form-control" value=""/>	
	</div>
	<div class="form-group col-xs-6">
		<label for="areaId">AREA ID</label>
		<select id="areaId" class="form-control">
			<option>1</option>
			<option>2</option>
			<option>3</option>
			<option>4</option>
		</select>
	</div>
	
	<%--
	<div class="form-group">
		<label for="id">ID</label>
		<input type="email" class="form-control" id="exampleInputEmail1" placeholder="이메일을 입력하세요">
	</div>
	<div class="form-group">
		<label for="exampleInputPassword1">암호</label>
		<input type="password" class="form-control" id="exampleInputPassword1" placeholder="암호">
	</div>
	<div class="form-group">
		<label for="exampleInputFile">파일 업로드</label>
		<input type="file" id="exampleInputFile">
		<p class="help-block">여기에 블록레벨 도움말 예제</p>
	</div>
	<div class="checkbox">
		<label>
			<input type="checkbox"> 입력을 기억합니다
		</label>
	</div> --%>
	<div class="form-group col-xs-6">
		<button type="submit" class="btn btn-default">save</button>
	</div>
</form>

</body>
</html>
