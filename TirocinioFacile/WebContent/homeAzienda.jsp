<%@page import="it.tirociniofacile.bean.UtenteBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String tipoUtente = (String) session.getAttribute("tipologiaAccount");

	if (null == tipoUtente) {
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}
	if (!tipoUtente.equals("azienda")) {
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}

	ProfiloAziendaBean ub = (ProfiloAziendaBean) session.getAttribute("account");
	if (null == ub) {
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}

	DocumentoConvenzioneBean conv = (DocumentoConvenzioneBean) session.getAttribute("convenzione");
	System.out.print("CONVENZIONE HOME AZ: " + conv);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TirocinioFacile - Home</title>

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
		<br> <br> <br>
		<script>
			$(document).ready(function() {
				$('[data-toggle="tooltip"]').tooltip();
			});
			

			
			$(document).ready(function() {
				$("#btnCrea").prop('disabled', true);
				if ($("#approvata").html() == "Convenzione Approvata.") {
					$("#btnCrea").attr("href", "http://localhost:8080/TirocinioFacile/creaPagina.jsp");
					$("#btnCrea").prop('disabled', false);
				}
			});
		</script>
		<div class="row">
			<div class="container-fluid">
				<a class="btn btn-primary btn-lg btn-block" href="#"
					data-toggle="tooltip" title="Crea pagina" role="button"
					id="btnCrea">Crea Pagina</a>
			</div>
			<br> <br> <br>
		</div>

		<%
			if (conv != null && conv.isApprovato()) {
				//qui bisogna abilitare il bottone crea pagina
		%>
		<div class="row">
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
			<div class="col-4 col-md-4">
				<br> <br> <br> <br> <br> <br>
				<h1 id="approvata">Convenzione Approvata.</h1>
			</div>

			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
		</div>

		<%
			} else {
		%>

		<div class="row">
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
			<div class="col-4 col-md-4">
				<h1>
					<b>ID:</b> Carica il documento <br>(file supportati: pdf)
				</h1>
				<form method="post" action="GestioneTf" id="form1"
					onsubmit="return isOk();" enctype="multipart/form-data">
					<input type="hidden" id="thisField" name="action"
						value="caricaDocumento"> <input type="hidden" name="email"
						value=<%=utente.getEmail()%>>

					<div class="form-group">
						<input type="file" name="file" id="theFile"
							accept="application/pdf">

					</div>
					<button type="submit" class="btn btn-primary">Carica
						Documento</button>
					<button type="button" class="btn btn-primary">Scarica</button>

				</form>
			</div>
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
		</div>

		<%
			}
		%>

		<br> <br> <br>
	</div>

	<%@ include file="footer.jsp"%>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/validate.js"></script>

	<script>
		function isOk(elem) {
			if (ValidateFile(document.getElementById("theFile"))) {
				return true;
			}
			return false;
		}
	</script>
</body>