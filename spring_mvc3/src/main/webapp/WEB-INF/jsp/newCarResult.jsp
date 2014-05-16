<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<html>
<body>
	<h1>Create New Car</h1>
	<h3>结果(有ID表示添加成功)：</h3>
	Id   :${car.id} <br />
	Brand:${car.brand.name}<br/>
	Model:${car.model }<br />
	Price:${car.price }
</body>
</html>