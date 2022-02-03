<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student List</title>
<style>
table, th, td {
	border: 1px solid black;
}
</style>

</head>
<body>
	<table>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Email</th>
			<th>Password</th>
			<th colspan ="2">Action</th>

		</tr>

		<c:forEach items="${students }" var="student">
			<tr>
				<td>${student.firstName}</td>
				<td>${student.lastName}</td>
				<td>${student.email}</td>
				<td>${student.password}</td>
				<td><a href="#">Update</a></td>
				<td><a href="#">Delete</a></td>
			</tr>
		</c:forEach>
	</table>


</body>
</html>