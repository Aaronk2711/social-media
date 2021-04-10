<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
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
		<h1 align="left" class="headers">Current User:</h1>
		<div class="content">
			<table border="1">
				<c:forEach var="user" items="${users}">
					 <tr>
					 	<td rowspan="2" class="main-pfp"><img src="data:image/jpg;base64,${user.pfp64}" class="main-pfp-image"/></td>
						  <td colspan="2" class="content name-and-dob">Name: <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></td>
						  <td colspan="2" class="content highschool-and-email">High School: <c:out value="${user.school}"/><br>College: <c:out value="${user.college}"/></td>
					 </tr>
					 <tr>
						  <td colspan="3" class="content hometown-and-phone">Home Town: <c:out value="${user.hometown}"/></td>
						  <td colspan="3" class="content name-and-dob">DOB: <c:out value="${user.birthday}"/></td>
					 </tr>
					 <tr>
					 	  <td colspan="3" class="content highschool-and-email">Email Address: <c:out value="${user.email}"/></td>
						  <td colspan="3" class="content hometown-and-phone">Phone Number: <c:out value="${user.phone}"/></td>
					 </tr>
					 <tr>
						  <td rowspan="3" class="update-profile"><a class="header-button" href="${pageContext.request.contextPath}/edit?id=${user.id}" class="button">Update Profile</a></td>
						  <td colspan="4" class="login">Last Login Date: <c:out value="${lastLogTime}"/></td>  
					 </tr>
					 <tr>
					 	  <td colspan="6" class="modified">Last Modification Date: <c:out value = "${lastModTime}"/></td>
					 </tr>
				</c:forEach>
			</table>
		</div>
		<h3 class="sub-title">Other Users</h3>
		<div class="left-people content">
			<c:forEach var="user" items="${leftUsers}">
				<table border="1">
					 <tr>
						  <td rowspan="2" class="pfp"><img src="data:image/jpg;base64,${user.pfp64}" class="pfp-image"/></td>
						  <td colspan="1" class="name-and-dob">Name: <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></td>
						  <td colspan="1" class="highschool-and-email">High School: <c:out value="${user.school}"/><br>College: <c:out value="${user.college}"/></td>
						  <td colspan="1" class="hometown-and-phone">Home Town: <c:out value="${user.hometown}"/></td>
					 </tr>
					 <tr>
						  <td colspan="1" class="name-and-dob">DOB: <c:out value="${user.birthday}"/></td>
						  <td colspan="1" class="highschool-and-email">Email Address: <c:out value="${user.email}"/></td>
						  <td colspan="1" class="hometown-and-phone">Phone Number: <c:out value="${user.phone}"/></td>
					 </tr>
				</table>
			</c:forEach>
		</div>
		<div class="right-people content">
			<c:forEach var="user" items="${rightUsers}">
				<table border="1">
					 <tr>
						  <td rowspan="2" class="pfp"><img src="data:image/jpg;base64,${user.pfp64}" class="pfp-image"/></td>
						  <td colspan="1" class="name-and-dob">Name: <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></td>
						  <td colspan="1" class="highschool-and-email">High School: <c:out value="${user.school}"/><br>College: <c:out value="${user.college}"/></td>
						  <td colspan="1" class="hometown-and-phone">Home Town: <c:out value="${user.hometown}"/></td>
					 </tr>
					 <tr>
						  <td colspan="1" class="name-and-dob">DOB: <c:out value="${user.birthday}"/></td>
						  <td colspan="1" class="highschool-and-email">Email Address: <c:out value="${user.email}"/></td>
						  <td colspan="1" class="hometown-and-phone">Phone Number: <c:out value="${user.phone}"/></td>
					 </tr>
				</table>
			</c:forEach>
		</div>
	</body>
</html>