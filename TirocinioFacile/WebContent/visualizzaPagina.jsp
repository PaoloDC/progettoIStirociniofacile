<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.* , it.tirociniofacile.bean.*,it.tirociniofacile.control.*"%>
	<%
	
	PaginaAziendaBean  pagina = (PaginaAziendaBean) request.getAttribute("pagina");

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
<%if (pagina != null) {%>
	<div class="container">
		<%@ include file="header.jsp"%>
		<div class="row">
			<div class="col-4 col-sm-4">
				<img src="logo_documento.png" width="40%" height="40%">
				<h2><%=pagina.getNomeAzienda() %></h2>
			</div>

			<div class="col-8 col-sm-8">
				<h3>Indirizzo: <%=pagina.getLocalita() %></h3>
				<h3>Sede: </h3>
				<h3>Skill Richieste: <%= pagina.getSkill() %></h3>
				<h3>Ambito: <%=pagina.getAmbito() %></h3>
			</div>
		</div>
<%} %>
		<div class="row">
			<div class="col-4 col-sm-4"></div>
			<div class="col-4 col-sm-4">
				<h3>Commenti e Suggerimenti</h3>
			</div>
			<div class="col-4 col-sm-4"></div>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>