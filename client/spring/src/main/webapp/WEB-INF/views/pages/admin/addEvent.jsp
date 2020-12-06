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
					<li class="active"><a href="addEvent"><span class="glyphicon glyphicon-plus"></span> Add event<span class="sr-only">(current)</span></a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="refereeList"><span class="glyphicon glyphicon-search"></span> List of referees</a></li>
					<li><a href="addReferee"><span class="glyphicon glyphicon-plus"></span> Add referee</a></li>
				</ul>
			</div>


			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Admin dashboard</h1>

				<div class="row placeholders">

					<div class="col-sm-6 col-sm-offset-3 col-md-6 col-md-offset-3">
						<form:form method="POST" modelAttribute="eventForm" class="form" enctype="multipart/form-data">

							<h2 class="form-heading">Add new event</h2>
							<spring:bind path="title">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:input type="text" path="title" class="form-control" placeholder="Title"></form:input>
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

							<spring:bind path="description">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:textarea type="text" rows="5" cols="30" path="description" class="form-control" placeholder="Description"></form:textarea>
									<c:set var="descriptionError">
										<form:errors path="description" />
									</c:set>
									<c:if test="${not empty descriptionError}">
										<div class="alert alert-danger" role="alert" style="margin-top: 10px;">
											<form:errors path="description"></form:errors>
										</div>
									</c:if>
								</div>
							</spring:bind>

							<spring:bind path="typeOfGun">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:select path="typeOfGun" class="form-control" items="${gunTypeList}" />
									<c:set var="gunTypeError">
										<form:errors path="typeOfGun" />
									</c:set>
									<c:if test="${not empty gunTypeError}">
										<div class="alert alert-danger" role="alert" style="margin-top: 10px;">
											<form:errors path="typeOfGun"></form:errors>
										</div>
									</c:if>
								</div>
							</spring:bind>

							<spring:bind path="numberOfCompetitors">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:input type="text" path="numberOfCompetitors" class="form-control" placeholder="Number of competitors"></form:input>
									<c:set var="numberOfCompetitorsError">
										<form:errors path="numberOfCompetitors" />
									</c:set>
									<c:if test="${not empty numberOfCompetitorsError}">
										<div class="alert alert-danger" role="alert" style="margin-top: 10px;">
											<form:errors path="numberOfCompetitors"></form:errors>
										</div>
									</c:if>
								</div>
							</spring:bind>

							<c:choose>
								<c:when test="${edit}">
								
								</c:when>
								<c:otherwise>
									<spring:bind path="img">
										<div class="form-group ${status.error ? 'has-error' : ''}">
											<form:input type="file" path="img" name="img" class="form-control"></form:input>
											<c:set var="imageError">
												<form:errors path="img" />
											</c:set>
											<c:if test="${not empty imageError}">
												<div class="alert alert-danger" role="alert" style="margin-top: 10px;">
													<form:errors path="img"></form:errors>
												</div>
											</c:if>
										</div>
									</spring:bind>
								</c:otherwise>
							</c:choose>



							<spring:bind path="referee">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:select path="referee" class="form-control">
										<form:options items="${refereeList}" itemValue="id" itemLabel="fullName" />
									</form:select>

									<c:set var="refereeError">
										<form:errors path="referee" />
									</c:set>
									<c:if test="${not empty refereeError}">
										<div class="alert alert-danger" role="alert" style="margin-top: 10px;">
											<form:errors path="referee"></form:errors>
										</div>
									</c:if>
								</div>
							</spring:bind>

							<c:choose>
								<c:when test="${edit}">
									<button class="btn btn-lg btn-primary btn-block" type="submit">
										<span class="glyphicon glyphicon-plus"> Update</span>
									</button>
								</c:when>
								<c:otherwise>
									<button class="btn btn-lg btn-primary btn-block" type="submit">
										<span class="glyphicon glyphicon-plus"> Add</span>
									</button>
								</c:otherwise>
							</c:choose>

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
