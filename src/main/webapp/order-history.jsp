<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order</title>

</head>
<body>

	<c:if test="${orders != null }">

		<c:forEach items="${orders}" var="order">
			<p>Order ID: ${order.id }</p>
			<p>Order Date: ${order.submitDate}</p>
			<a href="OrderServlet?orderId=${order.id}">View Order Details</a>
			<hr>
		</c:forEach>
	</c:if>

	<c:if test="${orderDetails != null }">

		<c:forEach items="${orderDetails}" var="orderDetail">
			<p>Order ID: ${orderDetail.orderId }</p>
			<p>Book ID: ${orderDetail.bookid }</p>
			<p>
				Book Title: <a href="HomeServlet?bookId=${orderDetail.bookid}">${orderDetail.bookTitle}</a>
			</p>

			<hr>
		</c:forEach>
	</c:if>

</body>
</html>