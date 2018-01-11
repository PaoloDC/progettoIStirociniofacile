<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TirocinioFacile - Login</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>
	<!-- Latest compiled and minified JavaScript -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>

	<div class="container-fluid">
		<%@ include file="header.jsp"%>
		<br> <br> <br>
		<div class="row">
			<div class="container-fluid">
				<a class="btn btn-primary btn-lg btn-block" href="visInfAnno.jsp"
					role="button">Tirocini Conclusi Per Anno Accademico</a>
			</div>
			<br> <br> <br>
		</div>



		<div class="row">
			<div class="container-fluid">
				<a class="btn btn-primary btn-lg btn-block" href="visInfAnno.jsp"
					role="button">Tirocini Conclusi Per Azienda</a>
			</div>
			<br> <br> <br>
		</div>

		<div class="row">
			<div class="container-fluid">
				<a class="btn btn-primary btn-lg btn-block" href="visInfAnno.jsp"
					role="button">Ricerca Pagina Azienda</a>
			</div>
			<br> <br> <br>
		</div>


	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>