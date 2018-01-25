<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.* , it.tirociniofacile.bean.*,it.tirociniofacile.control.*"%>
<%
	String tipo = (String) session.getAttribute("tipologiaAccount");

	if (tipo != null) {
		if (!(tipo.equals("impiegato"))) {
			response.sendRedirect("./index.jsp");
		}
	} else {
		response.sendRedirect("./index.jsp");
	}
	
	final int NUM_ELE_PAG = 4;
	ArrayList<DocumentoConvenzioneBean> listaDocumentiConvenzione = (ArrayList<DocumentoConvenzioneBean>) request
			.getAttribute("listaDocumentiConvenzione");
	ArrayList<DocumentoQuestionarioBean> listaDocumentiQuestionari = (ArrayList<DocumentoQuestionarioBean>) request
			.getAttribute("listaDocumentiQuestionari");
	int indice = 4;
	if (request.getAttribute("indice") != null) {
		indice = (int) request.getAttribute("indice");
		System.out.println("Indice= " + indice);
	} else {
		indice = 4;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TirocinioFacile - approva documento</title>

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
	<!-- scrip per far comparire il tooltip -->
	<script>
		$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>

	<div class="container-fluid">
		<%@ include file="header.jsp"%>
		<center>
			<h2>
				<span class="glyphicon glyphicon-ok"></span> Approva documenti
			</h2>
			<hr class="line">
		</center>
		<div class="container">
			<nav class="navbar navbar-default">
			<div class="container-fluid">

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">

					<div class="navbar-form navbar-left">
						<div class="form-group"></div>
					</div>

					<div class="navbar-form navbar-right"></div>
				</div>
			</div>

			<div class="row">
				<div class=" col-sm-6">

					<a
						href="GestioneTf?action=ricercaTuttiDocumentiConvenzioneAzienda&indice=4"
						class="btn btn-success btn-block" data-toggle="tooltip"
						title="Cerca tutte">Cerca 
						convenzioni azienda <span class="glyphicon glyphicon-hdd">
					</span> <i class="fa fa-angle-right">
					</a>
				</div>
				<div class=" col-sm-6">
					<a
						href="GestioneTf?action=ricercaTuttiDocumentiQuestionariAzienda&indice=4"
						class="btn btn-success btn-block" data-toggle="tooltip"
						title="Cerca tutte ">Cerca i questionari <span class="glyphicon glyphicon-hdd"> </span> <i
						class="fa fa-angle-right">
					</a>
				</div>
			</div>

			<!-- /.navbar-collapse -->

			<div class="container">
				<h3>
					<span class="glyphicon glyphicon-folder-open"></span> Documenti da approvare
				</h3>
				<div class="row">
					<div class="panel-group">
						<%
							if (listaDocumentiConvenzione != null)
								if (listaDocumentiConvenzione.size() != 0) {
									int i = 0;
									if (request.getAttribute("indice") != null) {
										indice = (int) request.getAttribute("indice");
										System.out.println("Indice= " + indice);
									}
									int indiceIniziale = ((indice - NUM_ELE_PAG));
									while (i < listaDocumentiConvenzione.size()) {
										if (i >= indiceIniziale && i < indice) {
						%>
						<div class="col-3 col-sm-3">
							<div class="panel panel-default panel-modest"
								style="max-width: 80%; margin: 5px; max-height: 60%;">
								<div class="panel-heading"><%=listaDocumentiConvenzione.get(i).getNomeAzienda()%></div>
								<div class="panel-body">
									<%=listaDocumentiConvenzione.get(i).getRappresentanteLegale()%></div>
								<center>
									<a href="GestioneTf?action=visualizzaDocumento&partitaIva=<%=listaDocumentiConvenzione.get(i).getPartitaIva()%>"><button
											type="submit" class="btn btn-info" data-toggle="tooltip" title="Apri">Visualizza
											convenzione</button></a>
								</center>
							</div>
						</div>
						<%
							} /*End if indice */
										i = i + 1;
									} /*end while size*/
								}
							if (listaDocumentiConvenzione != null)
								if (listaDocumentiConvenzione.size() > 0) {/*end if diverso da 0*/
						%>
						<div class="container">
							<!-- questo div serve a mandare alla fine della pagina il numero delle pagine -->
						</div>
						<nav aria-label="Page navigation example">
						<ul class="pagination">
							<li class="page-item">
								<%
									int numPag = 1;
											int indicePag = 1;
											int indici = NUM_ELE_PAG;
											/*Se è la prima pagina non visualizzare il precedente */
											if (indice != NUM_ELE_PAG) {
								%> <a class="page-link"
								href="GestioneTf?action=ricercaTuttiDocumentiConvenzioneAzienda&indice=<%=indice - NUM_ELE_PAG%>"
								aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
									<span class="sr-only">Previous</span>
							</a> <%
 	}
 %>
							</li>
							<%
								if (listaDocumentiConvenzione != null)
											if (listaDocumentiConvenzione.size() >= NUM_ELE_PAG) {
												if (listaDocumentiConvenzione.size() % NUM_ELE_PAG == 0) {
													numPag = listaDocumentiConvenzione.size() / NUM_ELE_PAG;
												} else {
													numPag = (listaDocumentiConvenzione.size() / NUM_ELE_PAG) + 1;

												}
											} else {
												numPag = 1;
											}
										int x = 1;
										for (x = 1; x <= numPag; x++) {
							%>
							<li class="page-item "><a class="page-link"
								href="GestioneTf?action=ricercaTuttiDocumentiConvenzioneAzienda&indice=<%=indici%>"><%=indicePag%></a></li>
							<%
								indici = indici + NUM_ELE_PAG;
											indicePag++;
										}
										/*end for*/
							%>

							<%
								//System.out.println("indice="+ indice);
										//System.out.println("indici="+ indici);
										/*Sela pagina successiva non contiene nulla */
										if (indice < indici - NUM_ELE_PAG) {
							%>
							<li class="page-item"><a class="page-link"
								href="GestioneTf?action=ricercaTuttiDocumentiConvenzioneAzienda&indice=<%=indice + NUM_ELE_PAG%>"
								aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
									class="sr-only">Next</span>
							</a></li>
							<%
								}
							%>
						</ul>
						</nav>

						<%
							} /*end if*/
						%>




						<%
							/* lista questionari*/

							if (listaDocumentiQuestionari != null)
								if (listaDocumentiQuestionari.size() != 0) {
									int i = 0;
									int indiceIniziale = ((indice - NUM_ELE_PAG));
									while (i < listaDocumentiQuestionari.size()) {
										if (i >= indiceIniziale && i < indice) {
						%>
						<div class="col-3 col-sm-3">
							<div class="panel panel-default panel-modest"
								style="max-width: 80%; margin: 5px; max-height: 60%;">
								<div class="panel-heading"><%=listaDocumentiQuestionari.get(i).getAnnoAccademico()%></div>
								<div class="panel-body">
									<%=listaDocumentiQuestionari.get(i).getMailStudente()%></div>
								<center>
									<a
										href="GestioneTf?action=visualizzaDocumento&id=<%=listaDocumentiQuestionari.get(i).getId()%>"><button
											type="submit" class="btn btn-info">Visualizza
											questionari</button></a>
								</center>
							</div>
						</div>
						<%
							} /*End if indice */
										i = i + 1;
									} /*end while size*/
								}
							if (listaDocumentiQuestionari != null)
								if (listaDocumentiQuestionari.size() > 0) {/*end if diverso da 0*/
						%>
						<div class="container">
							<!-- questo div serve a mandare alla fine della pagina il numero delle pagine -->
						</div>
						<nav aria-label="Page navigation example">
						<ul class="pagination">
							<li class="page-item">
								<%
									int numPag = 1;
											int indicePag = 1;
											int indici = NUM_ELE_PAG;
											/*Se è la prima pagina non visualizzare il precedente */
											if (indice != NUM_ELE_PAG) {
								%> <a class="page-link"
								href="GestioneTf?action=ricercaTuttiDocumentiQuestionariAzienda&indice=<%=indice - NUM_ELE_PAG%>"
								aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
									<span class="sr-only">Previous</span>
							</a> <%
 	}
 %>
							</li>
							<%
								if (listaDocumentiQuestionari != null)
											if (listaDocumentiQuestionari.size() >= NUM_ELE_PAG) {
												if (listaDocumentiQuestionari.size() % NUM_ELE_PAG == 0) {
													numPag = listaDocumentiQuestionari.size() / NUM_ELE_PAG;
												} else {
													numPag = (listaDocumentiQuestionari.size() / NUM_ELE_PAG) + 1;

												}
											} else {
												numPag = 1;
											}
										int x = 1;
										for (x = 1; x <= numPag; x++) {
							%>
							<li class="page-item "><a class="page-link"
								href="GestioneTf?action=ricercaTuttiDocumentiQuestionariAzienda&indice=<%=indici%>"><%=indicePag%></a></li>
							<%
								indici = indici + NUM_ELE_PAG;
											indicePag++;
										}
										/*end for*/
							%>

							<%
								//System.out.println("indice="+ indice);
										//System.out.println("indici="+ indici);
										/*Sela pagina successiva non contiene nulla */
										if (indice < indici - NUM_ELE_PAG) {
							%>
							<li class="page-item"><a class="page-link"
								href="GestioneTf?action=ricercaTuttiDocumentiQuestionariAzienda&indice=<%=indice + NUM_ELE_PAG%>"
								aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
									class="sr-only">Next</span>
							</a></li>
							<%
								}
							%>
						</ul>
						</nav>
						<%
							}
							if ((listaDocumentiQuestionari == null) && (listaDocumentiConvenzione == null)) {
						%>
						<center>
							<h2>Nessuna Documento</h2>
						</center>
						<%
							}/*end if*/
							if((listaDocumentiQuestionari !=null)&& (listaDocumentiQuestionari.size()==0)){

						%>
						<center>
							<h2>Nessuna Documento questionario trovato !</h2>
						</center>
						<%}  if((listaDocumentiConvenzione !=null)&& (listaDocumentiConvenzione.size()==0)){%>
						<center>
							<h2>Nessuna Documento convenzione trovato !</h2>
						</center>
						<%}%>
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