<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Facegram</title>
		
		<style>
			<%@ include file="style/styles.css" %>
		</style>
	</head>
	<body>
		<div class="navbar">
			  <h2 class="title">Facegram</h2>
			  <form action="logout">
			  	<input type="submit" value="Logout">
			  </form>
		</div>
		<div class="content">
			<c:if test="${user != null}">
				<h2>Edit Profile</h2>
				<form class="form-editable" action="update" method="post">
					<input type="hidden" name="id" value="<c:out value="${user.id}" />" />
					
					<label>First Name:<input type="text" name=first_name value="<c:out value="${user.firstName}" />" /></label>
					<label>Last Name:<input type="text" name="last_name" value="<c:out value="${user.lastName}" />" /></label>
					<label>High School:<input type="text" name="school" value="<c:out value="${user.school}" />" /></label>
					<label>Home Town:<input type="text" name="hometown" value="<c:out value="${user.hometown}" />" /></label>
					<label>College:<input type="text" name="college" value="<c:out value="${user.college}" />" /></label>
					<label>Date of Birth:<input type = "text" name = "birthday" value ="<c:out value = "${user.birthday}" />" /></label>
					<label>Email:<input type = "text" name = "email" value ="<c:out value = "${user.email}" />" /></label>
					<label>Phone:<input type = "text" name = "phone" value ="<c:out value = "${user.phone}" />" /></label>
					<label>Password: <input type="password" name="password" value="<c:out value="${user.password}" />" /></label>
					<div class="form-actions">
						<input type="submit" value="Back" name="submit" />
						<input type="submit" value="Save" name="submit" />
					</div>
				</form>
				<form action = "pic" method = "post" enctype = "multipart/form-data">
					<label>Upload a new profile picture<input type="file" name="file"></label>
					<div class = form-actions>
						<input type="submit" value="upload" name="upload">
					</div>
				</form>
			</c:if>
		</div>
	</body>
</html>