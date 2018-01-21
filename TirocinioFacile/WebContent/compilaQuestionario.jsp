<%@page import="it.tirociniofacile.model.PaginaAziendaModel"%>
<%@page import="it.tirociniofacile.bean.PaginaAziendaBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.tirociniofacile.bean.UtenteBean"%>
<%@page import="it.tirociniofacile.model.UtenteModel"%>
<%@page import="it.tirociniofacile.bean.ProfiloStudenteBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	//da passare nella sessione il profilo studente bean e la lista delle aziende
	UtenteModel um = new UtenteModel();
	UtenteBean ub = um.caricaAccount("paolo@studenti.unisa.it", "paolo");
	ProfiloStudenteBean psb = (ProfiloStudenteBean) ub;

	PaginaAziendaModel pabmodel = new PaginaAziendaModel();
	ArrayList<PaginaAziendaBean> listaAzienda = pabmodel.ricerca();
	System.out.println(listaAzienda);
	
	request.setAttribute("mailStudente", psb.getEmail());

	/*
	ProfiloStudenteBean psb = (ProfiloStudenteBean) session.getAttribute("studente");	
	ArrayList<PaginaAziendaBean> listaAzienda = 
		(ArrayList<PaginaAziendaBean>) session.getAttribute("listaAziende");
	*/
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tirocinio Facile - Compila Questionario Valutazione</title>

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

		<div>
			<div class="col-2 col-md-2">
				<!-- usato per centrare -->
			</div>
			<div>
				<form action="GestioneTf?action=compilaQuestionario" method="post">
					<div class="col-8 col-sm-8">
						<div>
						<input type="hidden" name = "mailStudente" value="<%=psb.getEmail() %>">
							<div id="parte1" style="background: #42d9f4">
								<label id="titoloParte">PARTE I : INFORMAZIONI SUL
									LAUREATO</label>
								<div class="form-group">
									<label> 1. Cognome </label> <input name="cognome"
										placeholder="Cognome"> <label> 2. Nome </label> <input
										name="nome" placeholder="Nome">
								</div>
								<div class="form-group">
									<label> 3. Telefono 1 </label> <input name="telefono1"
										placeholder="Telefono 1"> <label> 4. Telefono
										2 </label> <input name="telefono2" placeholder="Telefono 2">
								</div>
								<div class="form-group">
									<label> 5. E-mail </label> <input name="email"
										placeholder="Email"> <label> 6. Comune di
										Residenza </label> <input name="comune"
										placeholder="Comune di Residenza">
								</div>
								<div class="form-group">
									<label> 7. Provincia </label> <input name="provincia"
										placeholder="Provincia"> <label> 8. Anno
										accademico di immatricolazione </label> <input name="annoimm"
										placeholder="Anno accademico di immatricolazione">

								</div>
								<div class="form-group">
									<label> 9. CdL di Immatricolazione </label> <input
										name="cdlimm" placeholder="CdL di Immatricolazione"> <label>
										10. Matricola </label> <input name="matricola" readonly="readonly"
										value="<%=psb.getMatricola()%>">
								</div>
								<div class="form-group">
									<label> 11. Azienda/Laboratorio Interno ospitante il
										tirocinante </label> 
										
									<select name="azienda">
									<% if(listaAzienda != null){
										for (int i=0; i < listaAzienda.size(); i++) { %>
									
										<option value="<%=listaAzienda.get(i).getId() + "," + listaAzienda.get(i).getNomeAzienda()%>">
											<%=listaAzienda.get(i).getNomeAzienda()%>
										</option>
									<% }} %>
									</select>
						

								</div>
								<div class="form-group">
									<label> 12. Comune dell'Azienda / Laboratorio </label> <input
										name="comuneazienda"
										placeholder="Comune dell'Azienda / Laboratorio"> <label>
										13. Provincia </label> <input name="provazienda"
										placeholder="Provincia">
								</div>
								<div class="form-group">
									<label> 14. Sesso </label> <select name="sesso">
										<option value="Maschio">Maschio</option>
										<option value="Fermmina">Fermmina</option>
									</select> <label> 15. Data di nascita </label> <input name="datanascita"
										placeholder="Data di nascita">
								</div>
								<div class="form-group">
									<label> 16. Data </label> 
									<input name="data" placeholder="Giorno"> / 
									<input name="data" placeholder="Mese"> / 
									<input name="data" placeholder="Anno">
								</div>
							</div>
							<div id="parte2" style="background: #5bf441">
								<label id="titoloParte">PARTE II : INFORMAZIONI SULLO
									STAGE / TIROCINIO</label>
								<div class="form-group">

									<fieldset>
										<legend>1. Come &egrave; avvenuta la scelta dello
											stage?</legend>
										<input type="radio" name="parte2dom1"
											value="Tramite indicazioni da parte del tutor interno"
											checked="checked"> Tramite indicazioni da parte del
										tutor interno <br> <input type="radio" name="parte2dom1"
											value="Tramite indicazioni da parte dell'azienda">
										Tramite indicazioni da parte dell'azienda <br> <input
											type="radio" name="parte2dom1"
											value="In base ai propri interessi/motivazioni"> In
										base ai propri interessi/motivazioni <br> <input
											type="radio" name="parte2dom1"
											value="In base al proprio curriculum"> In base al
										proprio curriculum <br> <input type="radio"
											name="parte2dom1" value="altro"> Altro, Specificare
										<input name="parte2dom1altro">
									</fieldset>

									<fieldset>
										<legend>2. Lo stage &egrave; stato svolto:</legend>
										<input type="radio" disabled="disabled" /> all'interno <br>
										<input type="radio" checked="checked" readonly="readonly" />
										all'esterno <br>
									</fieldset>

									<fieldset>
										<legend>3. Qual &egrave; stato il ruolo del tutor
											aziendale durante lo stage?</legend>

										<input type="radio" name="parte2dom3"
											value="Definire e strutturare il progetto di stage"
											checked="checked"> Definire e strutturare il progetto
										di stage <br> <input type="radio" name="parte2dom3"
											value="Supervisionare lo svolgimento dello stage, risolvendo eventuali difficoltà incontrate dal tirocinante">
										Supervisionare lo svolgimento dello stage, risolvendo
										eventuali difficoltà incontrate dal tirocinante <br> <input
											type="radio" name="parte2dom3"
											value="Garantire il raggiungimento degli obiettivi formativi contenuti nel Progetto di stage">
										Garantire il raggiungimento degli obiettivi formativi
										contenuti nel Progetto di stage <br> <input type="radio"
											name="parte2dom3" value="Nessun ruolo"> Nessun ruolo
										<br> <input type="radio" name="parte2dom3" value="altro">
										Altro, Specificare <input name="parte2dom3altro">
									</fieldset>

									<fieldset name="parte2dom4">
										<legend>4. Qual &egrave; stato il ruolo del tutor
											universitario durante lo stage?</legend>

										<input type="radio" name="parte2dom4"
											value="Definire e strutturare il progetto di stage"
											checked="checked" checked="checked"> Definire e
										strutturare il progetto di stage <br> <input type="radio"
											name="parte2dom4"
											value="Supervisionare lo svolgimento dello stage, risolvendo eventuali difficoltà incontrate dal tirocinante">
										Supervisionare lo svolgimento dello stage, risolvendo
										eventuali difficoltà incontrate dal tirocinante <br> <input
											type="radio" name="parte2dom4"
											value="Garantire il raggiungimento degli obiettivi formativi contenuti nel Progetto di stage">
										Garantire il raggiungimento degli obiettivi formativi
										contenuti nel Progetto di stage <br> <input type="radio"
											name="parte2dom4" value="Nessun ruolo"> Nessun ruolo
										<br> <input type="radio" name="parte2dom4" value="altro">
										Altro, Specificare <input name="parte2dom4altro">
									</fieldset>
									<fieldset>
										<legend>5. In che misura lei possedeva le seguenti
											caratteristiche al momento dell'inizio dello stage?</legend>
										<table>
											<tr>
												<td>Capacit&agrave; relazionali e di comunicazione</td>
												<td><select name="cinque">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
											<tr>
												<td>Capacit&agrave; di lavorare in gruppo</td>
												<td><select name="cinque">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
											<tr>
												<td>Iniziativa / Autonomia</td>
												<td><select name="cinque">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
											<tr>
												<td>Abilit&agrave; nell'uso degli strumenti e tecniche
													specifiche</td>
												<td><select name="cinque">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
											<tr>
												<td>Conoscenza di base</td>
												<td><select name="cinque">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
											<tr>
												<td>Conoscenze linguistiche</td>
												<td><select name="cinque">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
											<tr>
												<td>Conoscenze tecniche</td>
												<td><select name="cinque">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
										</table>
									</fieldset>
									<fieldset>
										<legend>6. Quali delle precedenti caratteristiche lei
											ritiene di aver potenziato maggiormente al termine dello
											stage?</legend>
										<table>
											<tr>
												<td>Capacit&agrave; relazionali e di comunicazione</td>
												<td><select name="sei">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
											<tr>
												<td>Capacit&agrave; di lavorare in gruppo</td>
												<td><select name="sei">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
											<tr>
												<td>Iniziativa / Autonomia</td>
												<td><select name="sei">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
											<tr>
												<td>Abilit&agrave; nell'uso degli strumenti e tecniche
													specifiche</td>
												<td><select name="sei">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
											<tr>
												<td>Conoscenza di base</td>
												<td><select name="sei">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
											<tr>
												<td>Conoscenze linguistiche</td>
												<td><select name="sei">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
											<tr>
												<td>Conoscenze tecniche</td>
												<td><select name="sei">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
														<option value="5">5</option>
												</select></td>
											</tr>
										</table>
									</fieldset>


								</div>

							</div>



							<div id="parte3" style="background: #f4cb42">
								<label id="titoloParte">PARTE III : GRADO DI
									SODDISFAZIONE DEL TIROCINANTE</label>
								<fieldset>
									<legend>Giudizio sull'Esperienza</legend>

									<table>
										<tr>
											<td>A.1 L'esperienza di tirocinio ha arricchito il suo
												bagaglio di conoscenza?</td>
											<td><select name="a1">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
										<tr>
											<td>A.2 L'esperienza di tirocinio &egrave; stata utile
												dal punto di vista professionale?</td>
											<td><select name="a2">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
										<tr>
											<td>A.3 L'esperienza di tirocinio &egrave; stata utile
												per la sua crescita personale?</td>
											<td><select name="a3">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
										<tr>
											<td>A.4 L'accoglienza a lei riservata in azienda
												&egrave; stata buona?</td>
											<td><select name="a4">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
										<tr>
											<td>A.5 Come valuta l'esperienza rispetto alle sue
												aspettative iniziali?</td>
											<td><select name="a5">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>

									</table>
								</fieldset>
								<fieldset>
									<legend>Giudizio sull'Azienda</legend>

									<table>
										<tr>
											<td>B.1 Il tutor aziendale l'ha seguito accuratamente
												durante il tirocinio?</td>
											<td><select name="b1">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
										<tr>
											<td>B.2 Il personale l'ha messo in condizione di rendere
												al meglio?</td>
											<td><select name="b2">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
										<tr>
											<td>B.3 Il suo lavoro &egrave; stato preso in seria
												considerazione?</td>
											<td><select name="b3">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
										<tr>
											<td>B.4 Il ritmo di lavoro &egrave; stato adeguato?</td>
											<td><select name="b4">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
										<tr>
											<td>B.5 Il tempo impiegato &egrave; stato adeguato per
												lo svolgimento del progetto?</td>
											<td><select name="b5">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
									</table>
								</fieldset>
								<fieldset>
									<legend>Giudizio sull'Universit&agrave;</legend>
									<table>
										<tr>
											<td>C.1 Il tempo impiegato per espletare le
												attivit&agrave; burocratiche per dare inizio
												dell'attivit&agrave; di tirocinio &egrave; stato adeguato?</td>
											<td><select name="c1">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
										<tr>
											<td>C.2 L'azienda era nel settore di suo gradimento?</td>
											<td><select name="c2">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
										<tr>
											<td>C.3 La gestione dei tirocini le &egrave; sembrata
												efficiente?</td>
											<td><select name="c3">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
										<tr>
											<td>C.4 La preparazione fornita dal Corso di Studi
												&egrave; stata adeguata ad affrontare l'attivita' proposta?</td>
											<td><select name="c4">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
										<tr>
											<td>C.5 Le informazioni che le sono state fornite per
												affrontare il tirocinio sono state adeguate?</td>
											<td><select name="c5">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
										<tr>
											<td>C.6 I compiti e le responsabilit&agrave; del tutor
												interno vanno aumentati?</td>
											<td><select name="c6">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
											</select></td>
										</tr>
									</table>
								</fieldset>
							</div>

							<div class="form-group">
								<label for="commenti">Commenti</label>
								<textarea class="form-control" rows="5" cols="15"
									name="commenti" placeholder="Commenti"></textarea>
							</div>
							<div class="form-group">
								<label for="suggerimenti">Suggerimenti</label>
								<textarea rows="5" cols="15" class="form-control"
									name="suggerimenti" placeholder="Suggerimenti"></textarea>
							</div>
							<button type="submit" class="btn btn-primary">Invia
								Questionario</button>

							<br> <br> <br> <br> <br> <br> <br>
							<br>
						</div>
					</div>
				</form>


			</div>
			<div class="col-2 col-md-2">
				<!-- usato per centrare -->
			</div>
		</div>


	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>