<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	String messaggioErrore = (String) request.getAttribute("noUtente");

	String tipoUtente = (String) session.getAttribute("tipologiaAccount");
	
	if(tipoUtente != null) {
		if(tipoUtente.equals("studente")) {
			response.sendRedirect("./homeStudente.jsp");
		} else if (tipoUtente.equals("impiegato")) {
			response.sendRedirect("./approvaDocumento.jsp");
		} else if (tipoUtente.equals("presidente")) {
			response.sendRedirect("./visualizzaInformazioni.jsp");
		} else if (tipoUtente.equals("azienda")) {
			response.sendRedirect("./creaPagina.jsp");
		}
	}
%>
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
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"
		integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
		crossorigin="anonymous"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>

	<div class="container-fluid">
		<%@ include file="header.jsp"%>


		<div class="row">
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
			<div class="col-4 col-md-4">
				<form method="post" action="GestioneTf" id="form1">
					<input type="hidden" id="thisField" name="action" value="log-in">
					<div class="form-group">
						<label for="exampleInputEmail1" id="txtErrEmail">Email</label> <input
							type="email" class="form-control" id="theEmail"
							aria-describedby="emailHelp" placeholder="Email" name="email">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1" id="txtErrPass">Password</label>
						<input type="password" class="form-control" id="thePass"
							placeholder="Password" name="password">
					</div>
					<button type="submit" class="btn btn-primary">Accedi</button>
				</form>
			</div>
		</div>
		<div class="col-4 col-md-4">
			<!-- usato per centrare -->
		</div>
		<div class="col-4 col-md-4">
			<div class="row">
				<%
					if (messaggioErrore != null) {
				%>
				<h3 style="color: #FF0000"><%=messaggioErrore%></h3>
				<%
					}
				%>
				<h2>Non sei registrato?</h2>
				<h4>
					Se sei uno Studente o una Azienda <a href="registrazione.jsp">clicca
						QUI</a>
				</h4>
				<h4>
					Hai dimenticato la password? <a href="recuperaPassword.jsp">clicca
						QUI</a>
				</h4>
				<h4>
					Se un impiegato dell'ufficio tirocini ha necessità di iscriversi
					alla piattaforma, prego inviare mail all'indirizzo:
					<h4 style="color: blue;">tirociniofacile@gmail.com</h4>
				</h4>
			</div>
		</div>
		<div class="col-4 col-md-4">
			<!-- usato per centrare -->
		</div>

	</div>
	<button class="btn btn-primary" onclick="myFunction()">Print
		this page</button>
	<%@ include file="footer.jsp"%>

	<script>
		function myFunction() {
			window.print();
		}
	</script>

</body>
</html>