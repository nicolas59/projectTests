<%@ page isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Réaliser une recherhe</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<style type="text/css">
body {
	padding-top: 50px;
}
.starter-template {
	padding: 40px 15px;
}

.starter-template h1 {
	text-align: center;
}

.resultat{
	margin-top:50px;
}
</style>
</head>
<body>
	<jsp:include page="common/head.jsp"/>
	<div class="container">
		<div class="starter-template">
			<form:form method="post" commandName="recherche"
				action="lancerRecherche.html">
				<div class="input-group input-group-lg">
					<span class="input-group-addon">Rechercher :</span>
					<form:input path="words" cssClass="form-control input-lg" />
				</div>
			</form:form>
			<c:if test="${results != null}">
				<div class="panel panel-default resultat">
					<!-- Default panel contents -->
					<div class="panel-heading">Résultats</div>
					<div class="panel-body">
						Mot recherche : ${word}
					</div>

					<!-- Table -->
					<table class="table">
						<c:forEach items="${results}" var="item">
							<tr>
								<td><c:out value="${item.name}"></c:out></td>
								<td><a href="file:///<c:out value="${item.path}"/>">Télécharger</a></td>
							</tr>
						</c:forEach>

					</table>
				</div>
			</c:if>
		</div>
	</div>
	<script src="//code.jquery.com/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>