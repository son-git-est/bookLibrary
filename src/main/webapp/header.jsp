<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Header</title>
</head>
<body>
	<div style="display: flex; width: 50%;">
		<h1>Coding Mentor Library</h1>
	</div>
	<div style="flex-basis: 100px;">
		<h3>Hello ${sessionScope.me.lastName}</h3>

		<c:if test="${not empty sessionScope.me}">
			<a href="LoginServlet?command=logout">Log Out</a> | <a
				href="CartServlet?action=VIEW"> Cart: </a>${sessionScope.cart.books.size()} | 
				<a href="OrderServlet">Order Logs</a>
		</c:if>

		<c:if test="${empty sessionScope.me}">
			<a href="login.jsp">Log In</a>
		</c:if>
	</div>
</body>
</html>