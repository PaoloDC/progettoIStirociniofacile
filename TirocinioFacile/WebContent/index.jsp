<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	String messaggioErrore = (String) request.getAttribute("noUtente");
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


		<div class="row">
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
			<div class="col-4 col-md-4">
				<form method="post" action="GestioneTf" id="form1">
					<input type="hidden" id="thisField" name="action" value="log-in">
					<div class="form-group">
						<label for="exampleInputEmail1" id="txtErrEmail">Email</label> <input
							type="email" class="form-control" id="theEmail"
							aria-describedby="emailHelp" placeholder="Email"
							name="email">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1" id="txtErrPass">Password</label>
						<input type="password" class="form-control" id="thePass"
							placeholder="Password" name="password">
					</div>
					<button type="submit" class="btn btn-primary">Accedi</button>
				</form>
			</div>
		</div>
		<div class="col-4 col-md-4">
			<!-- usato per centrare -->
		</div>
		<div class="col-4 col-md-4">
			<div class="row">
				<%
					if (messaggioErrore != null) {
				%>
					<h3 style="color:#FF0000"><%=messaggioErrore%></h3>
				<%
					}
				%>
				<h2>Non sei registrato?</h2>
				<h4>
					Se sei uno Studente o una Azienda <a href="registrazione.jsp">clicca
						QUI</a>
				</h4>
				<h4>
					Se sei un Impiegato o il Presidente dell'area didattica <a
						href="generaCredenziali.jsp">clicca QUI</a>
				</h4>
				<h5>
					Hai dimenticato la password? <a href="recuperaPassword.jsp">clicca
						QUI</a>
				</h5>
			</div>
		</div>
		<div class="col-4 col-md-4">
			<!-- usato per centrare -->
		</div>

	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>