<%@page import="it.tirociniofacile.bean.UtenteBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	UtenteBean ub = (UtenteBean) session.getAttribute("account");
	if (null == ub) {
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
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
	<!-- Latest compiled and minified JavaScript -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>


	<div class="container-fluid">
		<%@ include file="header.jsp"%>
		<br> <br> <br>
		<script>
			$(document).ready(function() {
				$('[data-toggle="tooltip"]').tooltip();
			});
		</script>
		<div class="row">
			<div class="container-fluid">
				<a class="btn btn-primary btn-lg btn-block"
					href="ricercaAzienda.jsp" data-toggle="tooltip" title="Apri pagina"
					role="button">Ricerca Pagina Azienda</a>
			</div>
			<br> <br> <br>
		</div>

		<div class="row">
			<div class="container-fluid">
				<a class="btn btn-primary btn-lg btn-block"
					href="GestioneTf?action=ricercaTuttePagine&compila=true"
					data-toggle="tooltip" title="Apri pagina" role="button">Compila
					Questionario Valutazione Azienda</a>
			</div>

			<br> <br> <br>
		</div>

		<div class="row">
			<div class="container-fluid">
				<%
					if (null != ub) {
				%>
				<a class="btn btn-primary btn-lg btn-block" data-toggle="tooltip"
					title="Apri pagina"
					href="GestioneTf?action=ricercaQuestionariNonApprovatiPerStudente&mailStudente=<%=ub.getEmail()%>"
					role="button"> Carica Questionario per Approvazione</a>
				<%
					} else {
				%>
				<a class="btn btn-primary btn-lg btn-block" data-toggle="tooltip"
					title="Apri pagina" href="/index.jsp" role="button"> Carica
					Questionario per Approvazione</a>
				<%
					}
				%>
			</div>
			<br> <br> <br>
		</div>

	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>