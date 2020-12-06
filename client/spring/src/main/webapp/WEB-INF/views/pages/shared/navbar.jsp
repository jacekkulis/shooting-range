<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- NAVBAR -->
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">TOPGUN</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/home">Home</a></li>

				<sec:authorize access="!hasAnyRole('ROLE_ADMIN', 'ROLE_USER')">
					<li><a href="${pageContext.request.contextPath}/login">Login</a></li>
					<li><a href="${pageContext.request.contextPath}/registration">Register</a></li>
				</sec:authorize>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Guns<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/guns/cz75">CZ 75 KADET</a></li>
						<li><a href="${pageContext.request.contextPath}/guns/czshadow">CZ SHADOW</a></li>
						<li><a href="${pageContext.request.contextPath}/guns/kalashnikov">KALASHNIKOV AKMS</a></li>
						<li><a href="${pageContext.request.contextPath}/guns/shotgun">SHOTGUN</a></li>
						<li><a href="${pageContext.request.contextPath}/guns/glock17">GLOCK 17</a></li>
					</ul>	
				</li>

				<li><a href="${pageContext.request.contextPath}/gallery">Gallery</a></li>
				<li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li><a href="${pageContext.request.contextPath}/admin/welcome">Admin page</a></li>
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_USER')">
					<li><a href="${pageContext.request.contextPath}/user/events/1">Events</a></li>
				</sec:authorize>

			</ul>

			<sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_ADMIN')">
				<c:if test="${pageContext.request.userPrincipal.name != null}">
					<form class="navbar-form navbar-right" id="logoutForm" method="POST" action="${contextPath}/logout">
						<div class="form-group">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</div>
						<button onclick="document.forms['logoutForm'].submit()" class="btn btn-default">${pageContext.request.userPrincipal.name}| Logout</button>
					</form>
				</c:if>
			</sec:authorize>

			<!-- 
			
			<form class="navbar-form navbar-right">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Search...">
				</div>
				<button type="submit" class="btn btn-default">SEARCH</button>
			</form>
				-->
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>
<!-- END-NAVBAR -->