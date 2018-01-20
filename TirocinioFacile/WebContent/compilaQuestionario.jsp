<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
							<table>
								<tr>
									<th>Giudizio sull'Esperienza</th>
								</tr>
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
									<td>A.2 L'esperienza di tirocinio &egrave; stata utile dal
										punto di vista professionale?</td>
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
									<td>A.3 L'esperienza di tirocinio &egrave; stata utile per
										la sua crescita personale?</td>
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
									<td>A.4 L'accoglienza a lei riservata in azienda &egrave;
										stata buona?</td>
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
								<tr>
									<th>Giudizio sull'Azienda</th>
								</tr>
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
									<td>B.5 Il tempo impiegato &egrave; stato adeguato per lo
										svolgimento del progetto?</td>
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
								<tr>
									<th>Giudizio sull'Universit&agrave;</th>
								</tr>
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