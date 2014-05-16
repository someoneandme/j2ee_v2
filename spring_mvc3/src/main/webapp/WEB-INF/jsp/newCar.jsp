<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<html>
<body>
	<h1>Create New Car</h1>
 
	<form method="post" action="new_car">
 
        Brand:<select name="brand">
        <c:forEach items="${brands}" var="brand">
            <option value="${brand.id }"/>${brand.name }
        </c:forEach>
        </select><br />
        
		Model: <input type="text" name="model"/><br />
		Price: <input type="text" name="price"/><br />

		<input type="submit" value="Submit">
	</form>
</body>
</html>