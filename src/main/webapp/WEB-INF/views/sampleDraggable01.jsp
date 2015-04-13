<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>jQuery UI Draggable - Default functionality</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css">
  <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.2.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
  
  <style>
  #draggable { width: 150px; height: 50px; padding: 0.0em; }
  </style>
  <script>
/*
http://jqueryui.com/development/



 */
  $(function() {
//    $( "#draggable" ).draggable();
    $( "#draggable" ).draggable({
      drag: function(event, ui) {
         $("#test").html("top : " + ui.position.top + " left : " + ui.position.left );
      }
    });
  });
  </script>
</head>
<body>
 
<div id="draggable" class="ui-widget-content">
  <p>Drag me around!!!</p>
</div>

<div id="test"></div>
 
</body>
</html>