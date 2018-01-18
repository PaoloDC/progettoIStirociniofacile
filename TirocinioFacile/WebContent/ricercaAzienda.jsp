<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.* , it.tirociniofacile.bean.*,it.tirociniofacile.control.*"%>
<%
	ArrayList<PaginaAziendaBean> listaAziende = (ArrayList<PaginaAziendaBean>) request
			.getAttribute("listaAziende");

	int i = 0;
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

		<div class="container">
			<nav class="navbar navbar-default">
			<div class="container-fluid">
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<form action="GestioneTf" method="get">
							<input type="hidden" id="thisField" name="action"
								value="ricercaPagina">

							<li><input type="radio" name="categoria" value="ambito">
								<label for="ambitoChoice">Ambito</label></li>
							<li><input type="radio" name="categoria" value="skill">
								<label for="skillChoice">Skill</label></li>
							<li><input type="radio" name="categoria" value="localita">
								<label for="localitaChoice">Localita'</label></li>
							<li><input type="radio" name="categoria" value="nome">
								<label for="nomeChoice">Nome</label></li>
					</ul>
					<div class="navbar-form navbar-left">
						<div class="form-group">
							<input type="text" class="form-control" name="chiave"
								placeholder="Chiave">
						</div>
					</div>
					<ul class="nav navbar-nav navbar-right">

						<div class="navbar-form navbar-right">
							<button type="submit" class="btn btn-default">Cerca</button>
						</div>
						</form>
				</div>

				</ul>
			</div>
			<a href="GestioneTf?action=ricercaTuttePagine"
				class="btn btn-success btn-block">Cerca tutto <i
				class="fa fa-angle-right"></a> <!-- /.navbar-collapse -->
			<div class="container">
				<div class="row">
					<div class="panel-group">
						<%
							if (listaAziende != null) {
								while (i < listaAziende.size()) {
						%>
						<div class="col-3 col-sm-3">

							<div class="panel panel-default panel-modest"
								style="max-width: 80%; margin: 5px; max-height: 60%;">
								<div class="panel-heading"><%=listaAziende.get(i).getNomeAzienda()%></div>
								<div class="panel-body"><%=listaAziende.get(i).getDescrizione()%></div>
								<center>
									<a href="GestioneTf?action=visualizzaPagina&id=<%=listaAziende.get(i).getId()%>"><button type="submit" class="btn btn-default">Vai Alla
										Pagina</button></a>
								</center>
							</div>
						</div>
						<%
							i = i + 1;
								}
							} else {
						%>
						<center>
							<h2>Nessuna Azienda</h2>
						</center>
						<%
							}
						%>
					</div>
				</div>
			</div>
		</div>

		<!-- /.container-fluid -->
		</nav>


	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>