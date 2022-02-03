<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Account</title>
<link rel="stylesheet" type="text/css" href="style/style.css"
	title="style" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
	crossorigin="anonymous" />
</head>
<body>

	<div id="main">
		<div id="site_content">
			<div id="content">

				<h1>Sign In</h1>

				<p>Please enter your account details here:</p>
				<form action="LoginServlet" method="post">
					<div class="form_settings">
						<p>
							<span>User Name</span><input class="contact" type="text"
								name="username" value="" required />
						</p>
						<p>
							<span>Password</span><input class="contact" type="password"
								name="password" value="" required />
						</p>
						<p style="padding-top: 15px">
							<span>&nbsp;</span><input class="submit" type="submit"
								name="contact_submitted" value="let's go" />

						</p>
					</div>
				</form>
				
				<span><a href="#">Forgot your password?</a></span>

			</div>
		</div>
	</div>

</body>


</html>