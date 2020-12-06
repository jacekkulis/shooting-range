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

<title>Home page</title>

<link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet" media="screen">
<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${contextPath}/resources/css/site.css" rel="stylesheet">
<script src="${contextPath}/resources/js/jquery-3.2.0.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

</head>
<body>
	<%@include file="shared/navbar.jsp"%>

	<div class="container">
		<div class="jumbotron text-center moro"
			style="background-image: url('${contextPath}/resources/img/moro.jpg');">
			<h1>TOPGUN</h1>
			<p>Nr. 1 shooting range in Poland</p>
			<div class="input-group"></div>
		</div>

		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox">
				<div class="item active carouselImg">
					<img class="img-responsive center-block"
						src="${contextPath}/resources/img/gun1.jpg" alt="gun1" width="500"
						height="100">
				</div>

				<div class="item carouselImg">
					<img class="img-responsive center-block"
						src="${contextPath}/resources/img/gun2.jpg" alt="gun2" width="500"
						height="100">
				</div>

				<div class="item carouselImg">
					<img class="img-responsive center-block"
						src="${contextPath}/resources/img/gun3.jpg" alt="gun3" width="500"
						height="100">
				</div>
			</div>

			<!-- Left and right controls -->
			<a class="left carousel-control" href="#myCarousel" role="button"
				data-slide="prev"> <span
				class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a> <a class="right carousel-control" href="#myCarousel" role="button"
				data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>

		<!-- Container (Services Section) -->
		<div class="container-fluid text-center bg-grey">
		<h2>SERVICES</h2>
		<h4>WHAT DO WE OFFER?</h4>
		<br>
		<div class="row">
			<div class="col-sm-4">
				<span class="glyphicon glyphicon-ok logo-small"></span>
				<h4>SKILLS</h4>
				<p>Skills improvement</p>
			</div>
			<div class="col-sm-4">
				<span class="glyphicon glyphicon-heart logo-small"></span>
				<h4>SECURITY</h4>
				<p>Providing security</p>
			</div>
			<div class="col-sm-3">
				<span class="glyphicon glyphicon-signal logo-small"></span>
				<h4>WiFi</h4>
				<p>Free access to WiFi..</p>
			</div>
		</div>
		<br><br>
		<div class="row">

			<div class="col-sm-12">
				<span class="glyphicon glyphicon-eye-open logo-small"></span>
				<h4>Tutor</h4>
				<p>Tutor's eye ..</p>
			</div>
			<div class="col-sm-4">

			</div>
		</div>
	</div>

	</div>

	<%@include file="shared/footer.jsp"%>
</body>
</html>