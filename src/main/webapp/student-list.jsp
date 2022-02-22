<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student List</title>

<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>

<style>
table {
	width: 90%;
	margin: 20px;
	text-align: center;
	align-content: center;
	align-items: center;
	align-content: center;
}
</style>
</head>
<body>



	<div
		class="mdl-layout mdl-js-layout mdl-layout--fixed-drawer
            mdl-layout--fixed-header">
		<header class="mdl-layout__header">

			<div class="mdl-layout__header-row">
				<span class="mdl-layout-title">Students</span>
				<div class="mdl-layout-spacer"></div>
				<div
					class="mdl-textfield mdl-js-textfield mdl-textfield--expandable
                  mdl-textfield--floating-label mdl-textfield--align-right">
					<label class="mdl-button mdl-js-button mdl-button--icon"
						for="fixed-header-drawer-exp"> <i class="material-icons">search</i>
					</label>
					<div class="mdl-textfield__expandable-holder">
						<input class="mdl-textfield__input" type="text" name="sample"
							id="fixed-header-drawer-exp">
					</div>
				</div>
			</div>
		</header>
		<div class="mdl-layout__drawer">
			<span class="mdl-layout-title">Management Portal</span>
			<nav class="mdl-navigation">
				<a class="mdl-navigation__link" href="StudentBOServlet">Students</a>
				<a class="mdl-navigation__link" href="BookBOServlet">Books</a> <a
					class="mdl-navigation__link" href="#">Orders</a>
			</nav>
		</div>
		<main class="mdl-layout__content">
			<div class="page-content" style="margin: 20px;">


				<div class="mdl-layout-title">Student List</div>
				<table class="mdl-data-table mdl-js-data-table">
					<thead>
						<tr>
							<th class="mdl-data-table__cell--non-numeric">Id</th>
							<th class="mdl-data-table__cell--non-numeric">First Name</th>
							<th class="mdl-data-table__cell--non-numeric">Last Name</th>
							<th class="mdl-data-table__cell--non-numeric">Email</th>
							<th class="mdl-data-table__cell--non-numeric"></th>
							<th class="mdl-data-table__cell--non-numeric">Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${students }" var="student">
							<tr>
								<td class="mdl-data-table__cell--non-numeric">${student.id}</td>
								<td class="mdl-data-table__cell--non-numeric">${student.firstName}</td>
								<td class="mdl-data-table__cell--non-numeric">${student.lastName}</td>
								<td class="mdl-data-table__cell--non-numeric">${student.email}</td>
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
					</tbody>
				</table>
				<hr>


				<div>
					<div class="mdl-layout-title">Add New Student</div>
					<form action="StudentBOServlet" method="post" style="margin: 20px;">
						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="firstName"
								name="firstName"> <label class="mdl-textfield__label"
								for="sample3">First Name</label>
						</div>
						<br />
						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="lastName"
								name="lastName"> <label class="mdl-textfield__label"
								for="sample3">Last Name</label>
						</div>
						<br />
						<div
							class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
							<input class="mdl-textfield__input" type="text" id="email"
								name="email"> <label class="mdl-textfield__label"
								for="sample3">Email</label>
						</div>
						<br />
						<div>
							<input
								class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect"
								type="submit" value="add" onclick="return Validate()">
						</div>

					</form>

				</div>
				<hr>


				<div>
					<c:if test="${student != null }">
						<div class="mdl-layout-title">Update Student Details</div>
						<form action="StudentBOUpdateServlet" method="post"
							style="margin: 20px;">

							<input class="mdl-textfield__input" type="text" id="sample3"
								name="studentId" value="${student.id }" hidden="true"> <br />
							<div
								class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="firstNameU"
									name="firstName" value="${student.firstName }"> <label
									class="mdl-textfield__label" for="sample3">First Name</label>
							</div>
							<br />
							<div
								class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="lastNameU"
									name="lastName" value="${student.lastName }"> <label
									class="mdl-textfield__label" for="sample3">Last Name</label>
							</div>
							<br />
							<div
								class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="emailU"
									name="email" value="${student.email }"> <label
									class="mdl-textfield__label" for="sample3">Email</label>
							</div>
							<br />
							<div>
								<input
									class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect"
									type="submit" value="update" onclick="return ValidateUpdate()">
							</div>

						</form>
					</c:if>
				</div>
				<hr>


			</div>
		</main>
	</div>



	<script>
		function Validate() {

			let firstName = document.getElementById('firstName').value;
			let lastName = document.getElementById('lastName').value;
			let email = document.getElementById('email').value;

			if (!email || !firstName || !lastName) {
				alert('Please fill in all the fields!');
				return false;

			} else {

				return true;
			}

		}

		function ValidateUpdate() {

			let firstName = document.getElementById('firstNameU').value;
			let lastName = document.getElementById('lastNameU').value;
			let email = document.getElementById('emailU').value;

			let link = document.querySelector('#firstNameU');
			let pFirstName = link.getAttribute('value');
			let link2 = document.querySelector('#lastNameU');
			let pLastName = link2.getAttribute('value');
			let link3 = document.querySelector('#emailU');
			let pEmail = link3.getAttribute('value');

			if (firstName == pFirstName && lastName == pLastName
					&& email == pEmail) {

				alert('Nothing changes!');

				return false;
			} else {

				return true;
			}
		}
	</script>






</body>
</html>