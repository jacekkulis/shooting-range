<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
					<li class="active"><a href="refereeList"><span class="glyphicon glyphicon-search"></span> List of referees<span class="sr-only">(current)</span></a></li>
					<li><a href="addReferee"><span class="glyphicon glyphicon-plus"></span> Add referee </a></li>
				</ul>
			</div>


			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Admin dashboard</h1>

				<div class="row placeholders">
					<h2 class="sub-header">List of referees</h2>
					<div class="table-responsive">
						<table class="table table-hover table-bordered">
							<thead>
								<tr>
									<th>#</th>
									<th>Name</th>
									<th>Surname</th>
									<th>Title</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${refereeList}" var="referee">
									<tr>
										<td>${referee.id}</td>
										<td>${referee.name}</td>
										<td>${referee.surname}</td>
										<td>${referee.title}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
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
