<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>

<style>
* {
	margin: auto;
}

</style>
</head>
<body>
	<div id="header" style="display: flex; padding: 20px;">
		<jsp:include page="header.jsp"></jsp:include>
	</div>
	<hr>
	<hr>
	<div id="body" style="display: grid; grid-template-columns: 1fr 2fr;  align-items: start; ">

		<jsp:include page="category.jsp"></jsp:include>

		<jsp:include page="content.jsp"></jsp:include>
	</div>
	
	
</body>

</html>