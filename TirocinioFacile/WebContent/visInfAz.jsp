<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.* , it.tirociniofacile.bean.*,it.tirociniofacile.control.*"%>
<%
	String tipo = (String) session.getAttribute("tipologiaAccount");

	if (tipo == null || !(tipo.equals("presidente"))) {
		response.sendRedirect("./index.jsp");
	}

	ArrayList<PaginaAziendaBean> listaAzienda = (ArrayList<PaginaAziendaBean>) session
			.getAttribute("listaAziende");

	int numQuest = -1;
	if (request.getAttribute("numeroQuestionari") != null) {
		numQuest = (int) request.getAttribute("numeroQuestionari");
		//System.out.println("Entrato" + numQuest);
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
		<br> <br> <br>
		<center>
			<h2 >
				<span class="glyphicon glyphicon-eye-open"></span> Visualizza numero tirocini conclusi
			</h2>
			<hr class="line">
		</center>
		<div class="row">

			<div class="container">
				<h3>Azienda:</h3>
				<%
					if (numQuest == -1) {
				%>
				<div class="dropdown">
					<button class="btn btn-default dropdown-toggle" type="button"
						id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="true">
						- <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
						<%
							if (listaAzienda != null && listaAzienda.size() != 0) {
								//System.out.println("Ma non è vuoto");
						%>
						<%
							for (int i = 0; i < listaAzienda.size(); i++) {
						%>
						<li><a
							href="GestioneTf?action=visualizzaInformazioniPerAzienda&azienda=
							<%=listaAzienda.get(i).getNomeAzienda()%>"><%=listaAzienda.get(i).getNomeAzienda()%></a></li>
						<%
							}
						%>
						<%
							} else {
									//System.out.println("poopasdpaosidpasoi");
						%>
						<li><a>Nessuna Azienda</a></li>
						<%
							}
						%>
					</ul>
				</div>
				<%
					} else {
				%>
				<div class="dropdown">
					<button class="btn btn-default dropdown-toggle" type="button"
						id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="true">
						<%=request.getParameter("azienda")%>
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
						<%
							if (listaAzienda != null && listaAzienda.size() != 0) {
						%>
						<%
							for (int i = 0; i < listaAzienda.size(); i++) {
						%>
						<li><a
							href="GestioneTf?action=visualizzaInformazioniPerAzienda&azienda=
							<%=listaAzienda.get(i).getNomeAzienda()%>"><%=listaAzienda.get(i).getNomeAzienda()%></a></li>
						<%
							}
						%>
						<%
							} else { //System.out.println("poopasdpaosidpasoi");
						%>
						<li><a href="#">Nessuna Azienda</a></li>
						<%
							}
						%>
					</ul>
				</div>
				<%
					}
				%>
			</div>
			<br> <br> <br>
		</div>

		<div class="row">
			<div class="col-2 col-md-2">
				<!-- usato per centrare -->
			</div>
			<div class="col-8 col-md-8">
				<h2>Il numero di tirocini conclusi per questa azienda e':</h2>
				<center>
					<%
						if (numQuest == -1) {
					%>
					<h2>Seleziona un'azienda!</h2>
					<%
						} else {
					%>
					<h2><%=numQuest%></h2>
					<%
						}
					%>
				</center>
			</div>
			<div class="col-2 col-md-2">
				<!-- usato per centrare -->
			</div>
			<br> <br> <br>
		</div>

	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>