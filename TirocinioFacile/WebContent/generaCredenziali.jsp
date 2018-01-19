<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String msg = (String) request.getAttribute("mailCorretta");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TirocinioFacile - Genera Credenziali</title>

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

		<br> <br> <br>
		<div class="row">
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
			<div class="col-4 col-md-4">
				<form action="GestioneTf?action=generaCredenziali" method="post">
					<div class="form-group">
						<label for="exampleInputEmail1">Email</label> <input
							class="form-control" id="exampleInputEmail1" name="email"
							aria-describedby="emailHelp" placeholder="Enter email">
					</div>
					<button type="submit" class="btn btn-primary">Invia</button>
					<%
						if (msg != null) {
					%>
					<label><%=msg%></label>
					<%
						}
					%>
				</form>
			</div>
		</div>

		<div class="row">
			<div class="col-4 col-md-4">
				<!-- usato per centrare -->
			</div>
			<div class="col-4 col-md-4">
				<h2>Solo un ultimo passo...</h2>
				<h4>Inviaci la tua email, provvederemo a fornirti le
					credenziali per accedere tramite essa</h4>
			</div>
		</div>


	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>