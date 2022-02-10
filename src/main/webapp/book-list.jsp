<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Book List</title>

<style>
* {
	margin: auto;
}
</style>
</head>
<body>
<span>&leftarrow; </span><a href="StudentBOServlet">Manage Students</a>

	<div id="header" style="display: flex; padding: 20px;"></div>
	<hr>
	<hr>
	<div id="body"
		style="display: grid; grid-template-columns: 1fr 2fr; align-items: start;">

		<jsp:include page="categoryBO.jsp"></jsp:include>

		<jsp:include page="contentBO.jsp"></jsp:include>
	</div>


</body>

</html>