<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.* , it.tirociniofacile.bean.*,it.tirociniofacile.control.*"%>

<%
	UtenteBean tipoUtente = (UtenteBean) session.getAttribute("account");
	String tipo = (String) session.getAttribute("tipologiaAccount");

	if (tipo != null) {
		if (!(tipo.equals("azienda")) && !(tipo.equals("studente"))) {
			response.sendRedirect("./index.jsp");
		}
	} else {
		response.sendRedirect("./index.jsp");
	}

	ArrayList<String> questionariStudente = (ArrayList<String>) session.getAttribute("questionariStudente");
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

	<center>
		<%@ include file="header.jsp"%>

		<%
			if(questionariStudente != null)
			if (questionariStudente.size() == 0) {
		%>
		<div class="row">
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
			<div class="col-4 col-md-4">
				<br> <br> <br> <br> <br> <br>
				<h1>Nessun documento da caricare.</h1>
			</div>

			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
		</div>

		<%
			} else {
				for (int i = 0; i < questionariStudente.size(); i++) {
					String[] all = questionariStudente.get(i).split(";");
					String id = all[0];
					String annoAccademico = all[1];
					String nomeAzienda = all[2];
					System.out.println("id " + id + ", anno: " + annoAccademico + " nome azienda: " + nomeAzienda);
		%>


		<%
			if (i % 2 == 0) {
		%>
		<div class="col-12 col-md-12" style="background: #d3d3d3;">
			<%
				} else {
			%>
			<div class="col-12 col-md-12">
				<%
					}
				%>

				<form class="form-horizontal" method="post" action="GestioneTf"
					id="form1" onsubmit="return isOk();" enctype="multipart/form-data">

					<div class="row">
						<div class="col-4 col-md-4">
							<h4 name="id" value="<%=id%>">
								ID:
								<%=id%>

								Nome Azienda:
								<%=nomeAzienda%>

								Anno Accademico:
								<%=annoAccademico%>
							</h4>
						</div>
						<div class="col-4 col-md-4">
							<input type="file" name="file" id="theFile"
								accept="application/pdf">
						</div>
						<button type="submit" class="btn btn-primary">Carica
							Documento</button>
					</div>

					<input type="hidden" id="thisField" name="action"
						value="caricaDocumento"> <input type="hidden" name="email"
						value="<%=utente.getEmail()%>">

				</form>
			</div>
			<br>
			<%
				}
				}
			%>
		
	</center>
	<%@ include file="footer.jsp"%>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/validate.js"></script>

	<script>
		function isOk() {
			if (ValidateFile(document.getElementById("theFile"))) {
				return true;
			}
			return false;
		}
	</script>
</body>
</html>