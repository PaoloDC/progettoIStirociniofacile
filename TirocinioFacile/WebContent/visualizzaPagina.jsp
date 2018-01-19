<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.* , it.tirociniofacile.bean.*,it.tirociniofacile.control.*"%>
<%
	PaginaAziendaBean pagina = (PaginaAziendaBean) request.getAttribute("pagina");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TirocinioFacile - Visualizza pagina</title>

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
	<%
		if (pagina != null) {
	%>
	<div class="container">
		<%@ include file="header.jsp"%>
		<div class="row">
			<div class="col-4 col-sm-4">
				<img src="logo_documento.png" width="40%" height="40%">
				<h2><%=pagina.getNomeAzienda()%></h2>
			</div>

			<div class="col-8 col-sm-8">
				<h1>
					<span class="glyphicon glyphicon-list-alt"></span> Tutte le
					informazione sull'azienda
				</h1>
				<h2>
					<span class="glyphicon glyphicon-home"></span> Indirizzo
				</h2>
				<h3>
					<p><%=pagina.getLocalita()%></p>
				</h3>
				<h2>
					<span class="glyphicon glyphicon-star"></span> Skill Richieste
				</h2>
				<h3>
					<p><%=pagina.getSkill()%></p>
				</h3>
				<h2>
					<span class="glyphicon glyphicon-folder-open"></span> Ambito
				</h2>
				<h3>
					<p>
						<%=pagina.getAmbito()%></p>
				</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-4 col-sm-4"></div>
			<div class="col-4 col-sm-4">
				<h3>Commenti e Suggerimenti</h3>
			</div>
			<div class="col-4 col-sm-4"></div>
		</div>
	</div>

	<%
		} else {
	%>
	<div class="container">
		<div class="row">
			<div class="col-2 col-sm-2"></div>
			<br> <br> <br> <br> <br> <br> <br>
			<br> <br> <br> <br> <br>
			<div class="col-8 col-sm-8">
				<h2>
					Ops non esiste questa pagina! Torna alla <a href="index.jsp">home</a>
				</h2>
			</div>
			<div class="col-2 col-sm-2"></div>
		</div>
	</div>
	<%
		}
	%>
	<%@ include file="footer.jsp"%>
</body>
</html>