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
		<% if (utente == null ){
			System.out.println("Ehii");
		}
		%>

		<div class="row">
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
			<div class="col-4 col-md-4">
				<form method="post" action="GestioneTf" id="form1"
					onsubmit="return isOk();">
					<input type="hidden" id="thisField" name="action"
						value="registrazioneStudente">
					<div class="form-group">
						<label for="exampleInputEmail1" id="txtErrEmail">Email
							Istituzionale (inserire solo cio' che viene prima di
							@studenti.unisa.it)</label> <input type="text" class="form-control"
							aria-describedby="emailHelp" placeholder="Email" name="email"
							id="theEmail">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1" id="txtErrMatricola">Matricola
							(inserire solo le ultime 5 cifre)</label> <input type="text"
							class="form-control" aria-describedby="emailHelp"
							placeholder="Matricola" name="matricola" id="theMatricola">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1" id="txtErrPass">Password</label>
						<input type="password" class="form-control" id="thePass"
							placeholder="Password" name="password">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Conferma Password</label> <input
							type="password" class="form-control" id="theConfPass"
							placeholder="Conferma Password">
					</div>

					<button type="submit" class="btn btn-primary">Registrati</button>
					<label id="matchPass"></label>
				</form>
			</div>
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
		</div>


	</div>
	<%@ include file="footer.jsp"%>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/validate.js"></script>

	<script>
		function isOk() {
			if (ValidateEmailStudente(document.getElementById("theEmail"),
					document.getElementById("txtErrEmail"))
					&& ValidateMatricola(document.getElementById("theMatricola"), document
							.getElementById("txtErrMatricola"))
					&& ValidatePassword(document.getElementById("thePass"),
							document.getElementById("txtErrPass"))) {
				if (ValidatePasswordUguali(document.getElementById("thePass"),
						document.getElementById("theConfPass"), document
								.getElementById("matchPass"))) {
					return true;
				}
			}
			return false;
		}
	</script>
</body>
</html>