<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
      <script type="text/javascript" src="js/jquery.js"></script>
      <script type="text/javascript">
          var add_student = function() {
        	  var item_num = $('#students').attr('item_num');
        	  var html = '<div><span>学生# </span>' +
        	  'id(数字):<input type="text" name="students[#].id" />' +
        	  'name:<input type="text" name="students[#].name" />' +
        	  'school:<input type="text" name="students[#].school" />' +
        	  'age(数字):<input type="text" name="students[#].age" />' +
        	  '</div>';
        	  $('#students').append(html.replace(new RegExp("#","gm"),item_num));
        	  $('#students').attr('item_num', parseInt(item_num) + 1);
          };
      </script>
  </head>
  <body>
	<form action="cplx_post" method="post">
	    名称:<input type="text" name="namea" /> <!-- 这里不能设置为form.name -->
	  <div id="students" item_num="1">
	      <!-- 这里好奇怪，如果button的id和onclick里面的函数名相同的话，就会js出错 -->
	      <button type="button" id="add_studentxxx" onclick="add_student()">添加学生</button>
	      <div>
		     <span>学生0 </span>
		     id(数字):<input type="text" name="students[0].id" />
		     name:<input type="text" name="students[0].name" />
		     school:<input type="text" name="students[0].school" />
		     age(数字):<input type="text" name="students[0].age" />
	      </div> 
	  </div>
	  <input type="submit"/>
	</form>
  </body>
</html>