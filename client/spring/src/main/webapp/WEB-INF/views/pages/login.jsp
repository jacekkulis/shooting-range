<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="TopGun - Shooting Range placed in Lodz, Poland">
<meta name="author" content="Jacek Kulis">

<link rel="shortcut icon" type="image/x-icon" href="${contextPath}/resources/img/favicon.jpg" />

<title>Login page</title>

<link href="/resources/img/favicon.ico" rel="shortcut icon" type="image/x-icon" />

<link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet" media="screen">
<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${contextPath}/resources/css/site.css" rel="stylesheet">
<script src="${contextPath}/resources/js/jquery-3.2.0.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</head>

<body>

	<%@include file="shared/navbar.jsp" %>

	<div class="container">

		<form method="POST" action="${contextPath}/login" class="form-signin">
			<div id="messageContainer"></div>
			
			<h2 class="form-heading">Log in</h2>
			<div class="form-group ${error != null ? 'has-error' : ''}">
				<input name="username" type="text" class="form-control" placeholder="Username" autofocus /> 
				<input name="password" type="password" class="form-control" placeholder="Password" /> 
				
				<div id="alertContainer" style="margin-top: 10px;"></div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		
				<button class="btn btn-lg btn-primary btn-block" id="loginBtn" type="submit">LogIn</button>
				<h4 class="text-center">
					<a href="${contextPath}/registration">Create an account</a>
				</h4>
			</div>
			
			

		</form>

	</div>
	<!-- /container -->

	<%@include file="shared/footer.jsp" %>

	<script>
		$(document).ready(function () {
			var error = "${error}";
			var message = "${message}";
			
			if (error.length != 0){ 
				$('#alertContainer').append('<div class="alert alert-danger" role="alert">'+ error + '</div>');
				console.log(error);
			}
			else {
				$('#alertContainer').children("div").remove();
				console.log(error);
			}
			
			if (message.length != 0){ 
				$('#messageContainer').append('<div class="alert alert-success" role="alert">'+ message + '</div>');
				console.log(message);
			}
			else {
				$('#messageContainer').children("div").remove();
				console.log(message);
			}
			
		});
	</script>
</body>
</html>
