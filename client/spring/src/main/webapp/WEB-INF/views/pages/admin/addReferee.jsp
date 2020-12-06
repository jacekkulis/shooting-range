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

<title>Administrator dashboard</title>

<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${contextPath}/resources/css/adminsite.css" rel="stylesheet">
<script src="${contextPath}/resources/js/jquery-3.2.0.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<body>

	<%@include file="../shared/navbar.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li><a href="welcome">Overview</a></li>
					<li><a href="userList"><span class="glyphicon glyphicon-search"></span> List of users</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="eventList"><span class="glyphicon glyphicon-search"></span> List of events</a></li>
					<li><a href="addEvent"><span class="glyphicon glyphicon-plus"></span> Add event</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="refereeList"><span class="glyphicon glyphicon-search"></span> List of referees</a></li>
					<li class="active"><a href="addReferee"><span class="glyphicon glyphicon-plus"></span> Add referee <span class="sr-only">(current)</span></a></li>
				</ul>
			</div>


			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Admin dashboard</h1>

				<div class="row placeholders">

					<div class="col-sm-6 col-sm-offset-3 col-md-6 col-md-offset-3">
						<form:form method="POST" modelAttribute="refereeForm" class="form">
							<h2 class="form-heading">Add new referee</h2>
							<spring:bind path="name">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:input type="text" path="name" class="form-control" placeholder="Name"></form:input>
									<c:set var="nameError">
										<form:errors path="name" />
									</c:set>
									<c:if test="${not empty nameError}">
										<div class="alert alert-danger" role="alert" style="margin-top: 10px;">
											<form:errors path="name"></form:errors>
										</div>
									</c:if>
								</div>
							</spring:bind>

							<spring:bind path="surname">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:input type="text" path="surname" class="form-control" placeholder="Surname"></form:input>
									<c:set var="surnameError">
										<form:errors path="surname" />
									</c:set>
									<c:if test="${not empty surnameError}">
										<div class="alert alert-danger" role="alert" style="margin-top: 10px;">
											<form:errors path="surname"></form:errors>
										</div>
									</c:if>
								</div>
							</spring:bind>

							<spring:bind path="title">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:select type="title" path="title" class="form-control" items="${titleList}" />
									<c:set var="titleError">
										<form:errors path="title" />
									</c:set>
									<c:if test="${not empty titleError}">
										<div class="alert alert-danger" role="alert" style="margin-top: 10px;">
											<form:errors path="title"></form:errors>
										</div>
									</c:if>
								</div>
							</spring:bind>

							<button class="btn btn-lg btn-primary btn-block" type="submit">
								<span class="glyphicon glyphicon-plus"></span>
							</button>
						</form:form>
					</div>
					<!-- /form -->
				</div>
				<!-- /placeholders -->
			</div>
			<!-- /main -->
		</div>
	</div>
	<!-- /container -->

	<div class="row placeholders">
		<%@include file="../shared/footer.jsp"%>
	</div>

</body>
</html>
