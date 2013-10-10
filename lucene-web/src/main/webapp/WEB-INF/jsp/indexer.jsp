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
<script src="js/jquery.js"></script>
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

.input-group{
	margin-top:20px;
}

.action {
text-align: right;
}

.action a{
	 margin:20px 0 auto auto;
}
</style>
</head>
<body>
	<jsp:include page="common/head.jsp"/>
	
	
	<div class="container">
		<div class="starter-template">
			<form:form method="post" commandName="indexer"
				action="lancerIndex.html">
				<div class="input-group input-group-lg">
					<span class="input-group-addon">Projet :</span>
					<form:input path="projectName" cssClass="form-control input-lg" />
				</div>
				<div class="input-group input-group-lg">
					<span class="input-group-addon">Repertoire à indexer</span>
					<form:input path="dir" cssClass="form-control input-lg" />
				</div>
				<div class="action"><a class="btn btn-primary btn-lg actSubmit">Indexer</a></div>
			</form:form>
		</div>
	</div>
	<script type="text/javascript">
$(document).ready(function(){
	
	$( ".actSubmit" ).click(function() {
		$('#indexer').submit()
		});
});
	
	</script>
</body>
</html>