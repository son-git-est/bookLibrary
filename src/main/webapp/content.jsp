<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Page Body</title>

<style>
.pagination {
	display: flex;
	width: 200px;
	list-style-type: none;
}
</style>
</head>
<body>
	<div>
		<div>
			<ul class="pagination">


				<c:if test="${currentPage > 1 }">
					<li class="page-item"><a class="page-link"
						href="HomeServlet?currentPage=${currentPage - 1}&categoryId=${categoryId}">Previous</a></li>

					<li class="page-item"><a class="page-link"
						href="HomeServlet?currentPage=${currentPage - 1}&categoryId=${categoryId}">${currentPage - 1}</a></li>
				</c:if>

				<li class="page-item"><Strong>${currentPage}</Strong></li>

				<c:if test="${currentPage < totalPages }">
					<li class="page-item"><a class="page-link"
						href="HomeServlet?currentPage=${currentPage + 1}&categoryId=${categoryId}">${currentPage + 1}</a></li>

					<li class="page-item"><a class="page-link"
						href="HomeServlet?currentPage=${currentPage + 1}&categoryId=${categoryId}">Next</a></li>
				</c:if>

			</ul>
		</div>

		<div style="width: 600px">
			<c:if test="${not empty books}">
				<c:forEach items="${books}" var="book">
				Book Title: <a href="HomeServlet?bookId=${book.id}">${book.name}</a>
					<br />
					In Stock: ${book.stock}<br />
					ID: ${book.id }<br />

				</c:forEach>
			</c:if>

			<c:if test="${not empty book}">
				<form method="get" action="CartServlet">
					<input hidden="true" name="bookId" value="${book.id}"> <input
						hidden="true" name="action" value="ADD">

					<h3>Title: ${book.name}</h3>
					<br />
					<p><Strong>Description:</Strong><br/>${book.description}</p>
					<br />
					<p><Strong>Stock: </Strong>${book.stock}</p>
					<br /> <input type="submit" value="Add To Cart"
						<c:if test="${me == null}">disabled</c:if>>
				</form>
			</c:if>

			
		</div>


	</div>
</body>
</html>