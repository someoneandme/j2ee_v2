<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <body>
    <p>form结果</p>
	<p>name:${name}</p>
	<p>提交学生数:${students_num }</p>
	<table border="1">
	<tr>
	    <td>id</td><td>name</td><td>school</td><td>age</td>
	</tr>
	<c:forEach items="${students}" var="student">
	    <tr>
	        <td>${student.id }</td><td>${student.name }</td>
	        <td>${student.school }</td><td>${student.age }</td>
	    </tr>
	</c:forEach>
	</table>
  </body>
</html>