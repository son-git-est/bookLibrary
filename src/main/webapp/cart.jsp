<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart</title>

</head>
<body>
	<a href="HomeServlet">Go Home</a>
	<form method="get" action="CartServlet">
		<div style="width: 600px">
			<c:if test="${cart != null }">
				<c:forEach items="${cart.getBooks()}" var="book">
					<p>Book ID: ${book.id }</p>
					<p>
						Book Title: <a href="HomeServlet?bookId=${book.id}">${book.name}</a>
					</p>

					<a href="CartServlet?action=REMOVE&bookId=${book.id}"> ---
						Remove book</a>
				</c:forEach>

			</c:if>

		</div>
		<c:if test="${cart.getBooks().size() > 0 }">
			<input hidden="true" name="action" value="CHECKOUT">
			<input type="submit" value="check out">
		</c:if>
	</form>

</body>
</html>