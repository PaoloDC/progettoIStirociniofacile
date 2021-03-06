<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<% 
	
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
<title>TirocinioFacile - Registrazione</title>

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


		<div class="row">
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
			<div class="col-4 col-md-4">
				<h2>Sei una Azienda che vuole convenzionarsi e mostrarsi agli
					studenti di UNISA informatica?</h2>
				<center>
					<a href="registraProfiloAzienda.jsp"><button type="submit" class="btn btn-primary" ">Registrati
						Subito</button></a>
				</center>
			</div>
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
			<div class="col-4 col-md-4">
				<h2>Sei uno Studente che vuole iniziare l'iter per il proprio
					tirocinio esterno?</h2>
				<center>
					<a href="registraProfiloStudente.jsp"><button type="submit"
							class="btn btn-primary">Registrati Subito</button></a>
				</center>
			</div>
			<br>
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
				<div class="col-4 col-md-4">
			<br>
				<div class="col-4 col-md-4">
					<!-- usato per centrare -->
				</div>
			</div>
		</div>
		<%@ include file="footer.jsp"%>
</body>
</html>