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
			<th colspan="3">Action</th>

		</tr>

		<c:forEach items="${students }" var="student">
			<tr>
				<td>${student.firstName}</td>
				<td>${student.lastName}</td>
				<td>${student.email}</td>
				<td><a
					href="StudentBOServlet?action=restore&studentId=${student.id}">Restore
						Password</a></td>
				<td><a
					href="StudentBOServlet?action=update&studentId=${student.id}">Update
						Details</a></td>
				<td><a
					href="StudentBOServlet?action=delete&studentId=${student.id }">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
	<hr>
	<form action="StudentBOServlet" method="post">
		<label>First Name</label><br /> <input type="text" name="firstName"><br />
		<label>Last Name</label><br /> <input type="text" name="lastName"><br />
		<label>Email Address</label><br /> <input type="text" name="email"><br />
		<input type="submit" value="add student">

	</form>
	<hr>
	<c:if test="${student != null }">
		<form action="StudentBOUpdateServlet" method="post">
			<input type="text" name="studentId" value="${student.id }"
				hidden="true"><br /> <label>First Name</label><br /> <input
				type="text" name="firstName" value="${student.firstName }"><br />
			<label>Last Name</label><br /> <input type="text" name="lastName"
				value="${student.lastName }"><br /> <label>Student
				Email</label><br /> <input type="text" name="email"
				value="${student.email }"><br /> <input type="submit"
				value="update details">

		</form>


	</c:if>


</body>
</html>