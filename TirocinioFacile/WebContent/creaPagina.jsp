<%@page import="it.tirociniofacile.bean.UtenteBean"%>
<%@page import="it.tirociniofacile.model.UtenteModel"%>
<%@page import="it.tirociniofacile.bean.ProfiloAziendaBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String tipo = (String) session.getAttribute("tipologiaAccount");

	if (tipo != null) {
		if (!(tipo.equals("azienda"))) {
			response.sendRedirect("./index.jsp");
		}
	} else {
		response.sendRedirect("./index.jsp");
	}

	UtenteBean ub = (UtenteBean) session.getAttribute("account");
	ProfiloAziendaBean pab = (ProfiloAziendaBean) ub;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TirocinioFacile - Crea Pagina Azienda</title>

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
			<div class="col-2 col-md-2">
				<!-- usato per centrare -->
			</div>
			<div class="col-8 col-md-8">
				<form action="GestioneTf?action=creaPagina" method="post"
					onsubmit="return isOk();">
					<div class="form-group">
						<label for="exampleInputEmail1">Nome Azienda</label> <input
							type="text" class="form-control" id="nomeAzienda"
							value="<%=pab.getNomeAzienda()%>" readonly="readonly">
					</div>

					<div class="form-group">
						<label for="exampleInputEmail1">Email Profilo Azienda</label> <input
							type="text" class="form-control" name="mailAzienda"
							value="<%=pab.getEmail()%>" readonly="readonly">
					</div>

					<div class="form-group">
						<label for="exampleInputEmail1" id="txtErrLoc">Località</label> <input
							type="text" class="form-control" id="theLoc" name="localita"
							aria-describedby="emailHelp" placeholder="Località">
					</div>

					<div class="form-group">
						<label for="exampleInputEmail1" id="txtErrDes">Descrizione</label>
						<textarea class="form-control" rows="5" id="theDes"
							name="descrizione" placeholder="Descrizione"></textarea>
					</div>

					<div class="form-group">
						<label for="exampleInputPassword1" id="txtErrAmb">Ambiti
							(Per inserire più di un ambito usa la , Es. Web Development,
							Reti)</label> <input type="text" class="form-control" id="theAmb"
							name="ambito" placeholder="Ambito">
					</div>

					<div class="form-group">
						<label for="exampleInputPassword1" id="txtErrSki">Skill
							(Per inserire più di una skill usa la , Es. C++, Test)</label> <input
							type="text" name="skill" class="form-control" id="theSki"
							placeholder="Skill Richieste">
					</div>

					<button type="submit" class="btn btn-primary">Crea</button>
				</form>
			</div>
			<div class="col-2 col-md-2">
				<!-- usato per centrare -->
			</div>
		</div>


	</div>
	<br>
	<br>
	<br>
	<br>
	<%@ include file="footer.jsp"%>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/validate.js"></script>

	<script>
		function isOk() {
			if (ValidateLetter(document.getElementById("theLoc"), document
					.getElementById("txtErrLoc"))
					&& ValidateAlfa(document.getElementById("theDes"), document
							.getElementById("txtErrDes"))
					&& ValidateDescrizione(document.getElementById("theDes"), document
							.getElementById("txtErrDes"))
					&& ValidateAmbSki(document.getElementById("theAmb"),
							document.getElementById("txtErrAmb"))
					&& ValidateAmbSki(document.getElementById("theSki"),
							document.getElementById("txtErrSki"))) {
				return true;
			}

			return false;
		}
	</script>


</body>
</html>