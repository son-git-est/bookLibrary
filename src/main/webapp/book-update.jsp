<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Book Details</title>
</head>
<body>
	<a href="BookBOServlet">Manage Books</a>

	<hr>
	<form action="BookServlet" method="post">

		<label>Book Id</label><br /> <input disabled="disabled" type="text"
			name="bookId" value="${book.id }"><br /> <label>Book
			Title</label><br /> <input type="text" name="bookName" value="${book.name }"><br />
		<label>Book Caterogy Id</label><br /> <input type="text"
			name="categoryId" value="${book.categoryId }"><br /> <label>Book
			Caterogy</label><br /> <input type="text" name="category"
			value="${book.category }"><br /> <label>Book
			Description</label><br /> <input type="text" name="description"
			value="${book.description }"><br /> <label>Book Sock</label><br />
		<input type="text" name="stock" value="${book.stock }"><br />

		<input type="submit" value="update book details">





	</form>
</body>
</html>