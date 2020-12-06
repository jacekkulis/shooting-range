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
<meta name="description"
	content="TopGun - Shooting Range placed in Lodz, Poland">
<meta name="author" content="Jacek Kulis">

<link rel="shortcut icon" type="image/x-icon"
	href="${contextPath}/resources/img/favicon.jpg" />

<title>Contact page</title>

<link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet"
	media="screen">
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/site.css" rel="stylesheet">
<script src="${contextPath}/resources/js/jquery-3.2.0.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

</head>
<body>
	<%@include file="shared/navbar.jsp"%>

	<div class="container">
		<!-- Container (Contact Section) -->
		<div id="contact" class="container-fluid1 bg-grey">
			<h2 class="text-center">CONTACT</h2>
			<div class="row">
				<div class="col-sm-5">
					<p>Contact us and we'll get back to you within 24 hours.</p>
					<p>
						<span class="glyphicon glyphicon-map-marker"></span> Lodz, POLAND
					</p>
					<p>
						<span class="glyphicon glyphicon-phone"></span> 111 111 111
					</p>
					<p>
						<span class="glyphicon glyphicon-envelope"></span>
						topgun@topgun.com
					</p>
				</div>
				<div class="col-sm-7 slideanim">
					<div class="row">
						<div class="col-sm-6 form-group">
							<input class="form-control" id="name" name="name" placeholder="Name" type="text" required>
						</div>
						<div class="col-sm-6 form-group">
							<input class="form-control" id="email" name="email" placeholder="Email" type="email" required>
						</div>
					</div>
					<textarea class="form-control" id="comments" name="comments" placeholder="Comment" rows="5"></textarea>
					<br>
					<div class="row">
						<div class="col-sm-12 form-group">
							<button class="btn btn-default pull-right" type="submit">Send</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Add Google Maps -->
		<div id="googleMap" style="height: 400px; width: 100%;"></div>
		<script>
			function myMap() {
				var myCenter = new google.maps.LatLng(51.745967, 19.454970);
				var mapProp = {
					center : myCenter,
					zoom : 17,
					scrollwheel : false,
					draggable : false,
					mapTypeId : google.maps.MapTypeId.ROADMAP
				};
				var map = new google.maps.Map(document
						.getElementById("googleMap"), mapProp);
				var marker = new google.maps.Marker({
					position : myCenter
				});
				marker.setMap(map);
			}
		</script>
		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDqVxaWMEq2c2DJzvsYFRLreR8hvFH5nOo&callback=myMap"></script>
		<!--
			To use this code on your website, get a free API key from Google.
			Read more at: https://www.w3schools.com/graphics/google_maps_basic.asp
			-->

	</div>

	<%@include file="shared/footer.jsp"%>
</body>
</html>