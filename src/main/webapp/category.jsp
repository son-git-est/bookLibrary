<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Category</title>
</head>
<body>
	<div>
		<table>
			<tr>
				<th><Strong><a href="HomeServlet">All Categories</a></Strong></th>
			</tr>

			<c:forEach items="${categories }" var="category">
				<tr>
					<td><a href="HomeServlet?categoryId=${category.id}">${category.name }</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>