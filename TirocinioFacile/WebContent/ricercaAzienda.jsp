<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.* , it.tirociniofacile.bean.*,it.tirociniofacile.control.*"%>
	<%
	ArrayList<PaginaAziendaBean> listaAziende = (ArrayList<PaginaAziendaBean>) request.getAttribute("listaAziende");
	
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

						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false">Categoria<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="#">Ambito</a></li>
								<li><a href="#">Skill</a></li>
								<li><a href="#">Localita'</a></li>
								<li><a href="#">Nome</a></li>
							</ul></li>
					</ul>
					<form class="navbar-form navbar-left">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Chiave">
						</div>
					</form>
					<ul class="nav navbar-nav navbar-right">

						<form action="GestioneRicercaTirocinio"  method="get" class="navbar-form navbar-right">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="Cerca">
							</div>
							<button   type="submit" class="btn btn-default">Cerca</button>
						</form>
				</div>
				</ul>
			</div>
			<a  href="GestioneTf?action=ricercaTuttePagine" class="btn btn-success btn-block">Cerca tutto <i class="fa fa-angle-right"></a>
			<!-- /.navbar-collapse -->
			<% if (listaAziende != null){
				while (i<listaAziende.size()) {
			%>
			
			<div class="panel-group">
				<div class="panel panel-default panel-modest" style="max-width: 30%; margin: 5px;">
					<div class="panel-heading"><%=listaAziende.get(i).getNomeAzienda() %></div>
					<div class="panel-body" ><%=listaAziende.get(i).getDescrizione() %></div>
					<center><a href ="GestioneTf?action=visualizzaPagina&id=01193670625"><button type="submit" class="btn btn-default">Vai Alla
						Pagina</button><a></a></center>
				</div>
				<%	i = i+1;
					}
				  } else{
					%>
					<div class="panel panel-default panel-modest" style="max-width: 30%; margin: 5px;">
					<div class="panel-heading">Ak Informatica</div>
					<div class="panel-body" >Ak Informatica esiste dal 1988 ed è
						bella (descrizione qua ci va)</div>
					<center><button type="submit" class="btn btn-default">Vai Alla
						Pagina</button></center>
				</div>
				<%} %>
			</div>
		</div>
			
		<!-- /.container-fluid -->
		</nav>


	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>