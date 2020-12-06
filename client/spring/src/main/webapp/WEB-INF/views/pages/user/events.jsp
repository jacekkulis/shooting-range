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

<title>Events</title>

<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${contextPath}/resources/css/adminsite.css" rel="stylesheet">
<link href="${contextPath}/resources/css/events.css" rel="stylesheet">
<script src="${contextPath}/resources/js/jquery-3.2.0.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<body>

	<%@include file="../shared/navbar.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-9 col-sm-offset-1 col-md-10 col-md-offset-1 main">
				<h1 class="page-header">Events</h1>

				<ul class="event-list">
					<c:forEach items="${eventList}" var="event">
						<li>
							<img src="data:image/png;base64,${event.base64}"/>
							<div class="info">
								<h2 class="title">${event.title}</h2>
								<p class="desc">${event.description}</p>

								<ul>
									<li style="width: 33%;"><span class="fa fa-male"></span> ${event.numberOfCompetitors} </li>
									<li style="width: 34%;"><span class=""></span> ${event.typeOfGun}</li>
									<li style="width: 33%;"><span class="glyphicon glyphicon-user"></span> ${event.referee.name} ${event.referee.surname}</li>
								</ul>
							</div>
						</li>
					</c:forEach>
				</ul>
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
