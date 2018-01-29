<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.* , it.tirociniofacile.bean.*,it.tirociniofacile.control.*"%>
<%
	String tipo = (String) session.getAttribute("tipologiaAccount");

	if (tipo != null) {
		if (!(tipo.equals("presidente")) && !(tipo.equals("studente"))) {
			response.sendRedirect("./index.jsp");
		}
	} else {
		response.sendRedirect("./index.jsp");
	}

	PaginaAziendaBean pagina = (PaginaAziendaBean) request.getAttribute("pagina");
	ArrayList<DocumentoQuestionarioBean> listaDoc = (ArrayList<DocumentoQuestionarioBean>) request.getAttribute("commSugg");
	//System.out.println("lista doc=" + listaDoc);
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
			<div class="col-8 col-sm-8" style="margin-left: 180px">
				<div class="panel panel-default panel-modest"
					style="max-width: 100%; margin-top: 5px; max-height: 100%;">
					<div class="panel-heading">
						<center>
							<h1 style="color: orange">
								<span class="glyphicon glyphicon-home"></span>
								<%=pagina.getNomeAzienda()%>
							</h1>
						</center>
					</div>
					<div class="panel-body"
						style="max-width: 100%; margin: 5px; max-height: 100%;">


						<h2 style="color: blue">
							<span class="glyphicon glyphicon-pencil"></span> Descrizione
						</h2>
						<h4>
							<p>
								<%=pagina.getDescrizione()%></p>
						</h4>

						<h2 style="color: blue">
							<span class="glyphicon glyphicon-map-marker"></span> Indirizzo
						</h2>
						<h4>
							<p><%=pagina.getLocalita()%></p>
						</h4>

						<h2 style="color: blue">
							<span class="glyphicon glyphicon-star"></span> Skill Richieste
						</h2>
						<h4>
							<p><%=pagina.getSkill()%></p>
						</h4>

						<h2 style="color: blue">
							<span class="glyphicon glyphicon-briefcase"></span> Ambito
						</h2>
						<h4>
							<p>
								<%=pagina.getAmbito()%></p>
						</h4>
					</div>
				</div>
			</div>
			<div class="container">
				<div class="row">
					<div class="col-8 col-sm-8" style="margin-left: 180px">
						<div class="panel panel-default panel-modest"
							style="max-width: 100%; margin-top: 5px; max-height: 100%;">
							<div class="panel-heading">
								<center>
									<h1 style="color: orange">
										<span class="glyphicon glyphicon-pushpin"></span> Note
									</h1>
								</center>
							</div>
							<div class="panel-body"
								style="max-width: 100%; margin: 5px; max-height: 100%;">
								<%
									if ((listaDoc != null)&&(listaDoc.size()>0)) {
								%>
								<div class="panel-heading">
									<h2 style="color: blue">
										<span class="glyphicon glyphicon-bullhorn"></span>
										Suggerimenti
									</h2>
								</div>
								<div class="panel-body"
									style="max-width: 100%; margin: 5px; max-height: 100%;">
									<%
										for (int i = 0; i < listaDoc.size(); i++) {
									%>
									<h4><%=listaDoc.get(i).getSuggerimenti()%></h4>
									<%
										}
									%>
								</div>
								<div class="panel-heading">
									<h2 style="color: blue">
										<span class="glyphicon glyphicon-comment"></span> Commenti
									</h2>
								</div>
								<div class="panel-body"
									style="max-width: 100%; margin: 5px; max-height: 100%;">
									<%
										for (int i = 0; i < listaDoc.size(); i++) {
									%>
									<h4><%=listaDoc.get(i).getCommenti()%></h4>
									<%
										}
									%>
									<%
										}
									%>
									<%
									if (listaDoc.size()==0) {
								%>
									<div class="panel-heading">
										<h2 style="color: blue">
											<span class="glyphicon glyphicon-bullhorn"></span>
											Suggerimenti
										</h2>
									</div>
									<div class="panel-body"
										style="max-width: 100%; margin: 5px; max-height: 100%;">


										<h4>Nessun suggerimento ancora</h4>

									</div>
									<div class="panel-heading">
										<h2 style="color: blue">
											<span class="glyphicon glyphicon-comment"></span> Commenti
										</h2>
									</div>
									<div class="panel-body"
										style="max-width: 100%; margin: 5px; max-height: 100%;">

										<h4>Nessun commento ancora</h4>

										<%
										}
									%>


									</div>
								</div>
							</div>
						</div>
						<div class="container">
							<div class="row">
								<div class="col-8 col-sm-8" style="margin-left: 180px">
									<div class="panel panel-default panel-modest"
										style="max-width: 100%; margin-top: 5px; max-height: 100%;">
										<div class="panel-heading">
											<center>
												<h1 style="color: orange">
													<span class="glyphicon glyphicon-stats"></span> Medie
												</h1>
											</center>
										</div>
										<div class="panel-body"
											style="max-width: 100%; margin: 5px; max-height: 100%;">
											<%
											if ((listaDoc != null)&&(listaDoc.size()>0)) {
										%>
											<div class="panel-heading">
												<h2 style="color: blue">
													<span class="glyphicon glyphicon-thumbs-up"></span>
													Giudizio azienda
												</h2>
											</div>
											<div class="panel-body"
												style="max-width: 100%; margin: 5px; max-height: 100%;">

												<%
												for (int i = 0; i < listaDoc.size(); i++) {
											%>
												<h4><%=listaDoc.get(i).getGiudizioAzienda()%></h4>
												<%
												}
											%>
											</div>
											<div class="panel-heading">
												<h2 style="color: blue">
													<span class="glyphicon glyphicon-education"></span>
													Giudizio università
												</h2>
											</div>
											<div class="panel-body"
												style="max-width: 100%; margin: 5px; max-height: 100%;">

												<%
												for (int i = 0; i < listaDoc.size(); i++) {
											%>
												<h4><%=listaDoc.get(i).getGiudizioEsperienza()%></h4>
												<%
												}
											%>
											</div>
											<div class="panel-heading">
												<h1 style="color: blue">
													<span class="glyphicon glyphicon-book"></span>
													Giudizio esperienza
												</h1>
											</div>
											<div class="panel-body"
												style="max-width: 100%; margin: 5px; max-height: 100%;">
												<%
												for (int i = 0; i < listaDoc.size(); i++) {
											%>
												<h4><%=listaDoc.get(i).getGiudizioUniversita()%></h4>
												<%
												}
											}
											%>
												<%
											if (listaDoc.size()==0) {
										%>
												<div class="panel-heading">
													<h2 style="color: blue">
														<span class="glyphicon glyphicon-thumbs-up"></span>
														Giudizio Azienda
													</h2>
												</div>
												<div class="panel-body"
													style="max-width: 100%; margin: 5px; max-height: 100%;">
													<h4>Nessun giudizio</h4>
												</div>
												<div class="panel-heading">
													<h1 style="color: blue">
														<span class="glyphicon glyphicon-education"></span>
														Giudizio Università
													</h1>
												</div>
												<div class="panel-body"
													style="max-width: 100%; margin: 5px; max-height: 100%;">
													<h4>Nessun giudizio</h4>
												</div>
												<div class="panel-heading">
													<h1 style="color: blue">
														<span class="glyphicon glyphicon-book"></span> Giudizio
														esperienza Studenti
													</h1>
												</div>
												<div class="panel-body"
													style="max-width: 100%; margin: 5px; max-height: 100%;">

													<h4>Nessun giudizio</h4>
											<% }			 %>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<br> <br> <br>
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