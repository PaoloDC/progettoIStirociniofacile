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
	<!-- Latest compiled and minified JavaScript -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>

	<div class="container-fluid">
		<%@ include file="header.jsp"%>

		<div class="row">
			<div class="col-2 col-md-2">
				<!-- usato per centrare -->
			</div>
			<div class="col-8 col-md-8">
				<form>
					<div class="form-group">
						<label for="exampleInputEmail1">Localita'</label> <input
							type="text" class="form-control" id="exampleInputEmail1"
							aria-describedby="emailHelp" placeholder="Località">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Descrizione</label>
						<textarea class="form-control" rows="5" id="descr"></textarea>
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Ambiti (Per inserire più di un ambito usa la , Es. Web Development, Reti)</label> <input
							type="text" class="form-control" id="exampleInputPassword1"
							placeholder="Ambito">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Skill (Per inserire più di una skill usa la , Es. C++, Test)</label> <input
							type="text" class="form-control" id="exampleInputPassword1"
							placeholder="Skill Richieste">
					</div>
					<button type="submit" class="btn btn-primary">Crea</button>
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