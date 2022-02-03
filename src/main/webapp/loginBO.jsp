<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Log In</title>
</head>
<body>
	<form action="LoginBOServlet" method="post">
		<label>Username</label> <input type="text" name="username"> <label>Password</label>
		<input type="password" name="password"> <input type="submit"
			value="log in">
	</form>
</body>
</html>