<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<script>
	var $j = jQuery.noConflict();

	$j(function() {

		$j("#datepicker").datepicker({
			changeYear : true,
			yearRange : "1900:2000",
			setDate: new Date(1996, 11, 15),
			defaultDate: new Date(1996, 11, 15)
		});
	});
</script>
</head>

<body>
	<!-- Latest compiled and minified JavaScript -->





	<div class="container-fluid">
		<%@ include file="header.jsp"%>

		<div class="row">
			<div class="col-2 col-md-2">
				<!-- usato per centrare -->
			</div>
			<div class="col-8 col-md-8">
				<form action="GestioneTf?action=registrazioneAzienda" method="post"
					onsubmit="return isOk();">
					<div class="col-6 col-sm-6">
						<div class="form-group">
							<label for="txtErrEmail">Email</label> <input type="text"
								class="form-control" id="theEmail" name="email"
								aria-describedby="emailHelp" placeholder="Enter email">
						</div>
						<div class="form-group">
							<label id="txtErrPass" for="exampleInputPassword1">Password</label>
							<input type="password" class="form-control" id="thePass"
								placeholder="Password" name="password">
						</div>
						<div class="form-group">
							<label id="txtErrConfPass" for="exampleInputPassword1">Conferma
								Password</label> <input type="password" class="form-control"
								id="theConfPass" placeholder="Password">
						</div>
						<div class="form-group">
							<label id="txtErrNomeAzienda" for="exampleInputEmail1">Nome
								Azienda</label> <input type="text" class="form-control" id="theAzienda"
								placeholder="Nome Azienda" name="nomeazienda">
						</div>
						<div class="form-group">
							<label id="txtErrCitta" for="exampleInputPassword1">Citta</label>
							<input type="text" class="form-control" id="theCity"
								placeholder="Citta" name="citta">
						</div>
					</div>
					<div class="col-6 col-sm-6">
						<div class="form-group">
							<label id="txtErrPartitaIva" for="exampleInputEmail1">Partita
								Iva</label> <input type="text" class="form-control" id="thePartitaIva"
								aria-describedby="emailHelp" placeholder="Partita Iva"
								name="piva">
						</div>
						<div class="form-group">
							<label id="txtErrSedeLegale" for="exampleInputPassword1">Sede
								Legale</label> <input type="text" class="form-control"
								id="theSedeLegale" placeholder="Sede" name="sedeLegale">
						</div>
						<div class="form-group">
							<label id="txtErrLuogoDiNascita" for="exampleInputPassword1">Luogo
								Di Nascita del rappresentante</label> <input type="text"
								class="form-control" id="theLuogoDiNascita"
								placeholder="Luogo Nascita" name="luogoDiNascitaRappLegale">
						</div>

					</div>
					<div class="col-6 col-sm-6">
						<div class="form-group">
							<label id="txtErrRappresentanteLegale"
								for="exampleInputPassword1">Rappresentante Legale</label> <input
								type="text" class="form-control" id="theRappresentanteLegale"
								placeholder="Rappresentante" name="rappLegale">
						</div>

						<div class="form-group">
							<label id="txtErrData">Data Nascita Rappr Legale</label> <input
								type="text" id="datepicker" value="11/15/1996">
						</div>
						<button type="submit" class="btn btn-primary">Genera La
							Convenzione</button>
					</div>
				</form>

			</div>
			<div class="col-2 col-md-2">
				<!-- usato per centrare -->
			</div>
		</div>


	</div>

	<%@ include file="footer.jsp"%>



	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/validate.js"></script>
	<script>
		function isOk() {
			if (ValidateEmail(document.getElementById("theEmail"), document.getElementById("txtErrEmail"))
					&& ValidatePassword(document.getElementById("thePass"), document
					.getElementById("txtErrPass"))
					&& ValidatePasswordUguali(document
							.getElementById("thePass"), document
							.getElementById("theConfPass"), document
							.getElementById("txtErrConfPass"))
					&& ValidateLetter(document.getElementById("theAzienda"),
							document.getElementById("txtErrNomeAzienda"))
					&& ValidateLetter(document.getElementById("theCity"),
							document.getElementById("txtErrCitta"))
					&& ValidateLetter(document.getElementById("theSedeLegale"),
							document.getElementById("txtErrSedeLegale"))
					&& ValidateLetter(document
							.getElementById("theLuogoDiNascita"), document
							.getElementById("txtErrLuogoDiNascita"))
					&& ValidateLetter(
							document.getElementById("theRappresentanteLegale"),
							document
									.getElementById("txtErrRappresentanteLegale"))
					&& ValidatePartitaIva(document
							.getElementById("thePartitaIva"), document
							.getElementById("txtErrPartitaIva"))
					&& ValidateGiornoRapp(document.getElementById("theGiorno"),
							document.getElementById("txtErrData"))

			) {
				return true;
			}
			return false;
		}
	</script>
</body>
</html>