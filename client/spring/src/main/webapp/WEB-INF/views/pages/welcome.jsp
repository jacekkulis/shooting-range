<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

<title>Welcome page</title>

<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/site.css" rel="stylesheet">
<script src="${contextPath}/resources/js/jquery-3.2.0.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<body>

	<%@include file="shared/navbar.jsp"%>

	<div class="container">

		<h2>Welcome ${pageContext.request.userPrincipal.name}</h2>


	</div>
	<!-- /container -->

	<%@include file="shared/footer.jsp"%>
</body>
</html>
