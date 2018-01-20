<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.* , it.tirociniofacile.bean.*,it.tirociniofacile.control.*"%>
<%
	ArrayList<PaginaAziendaBean> listaAziende = (ArrayList<PaginaAziendaBean>) request.getAttribute("listaAziende");
	int indice =4;
	if(request.getAttribute("indice") != null){
		 indice =(int)  request.getAttribute("indice");
		 System.out.println("Indice= " + indice);
	}
	else{
		indice=4;
	}
	final  int NUM_ELE_PAG = 4;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TirocinioFacile - approva documento </title>

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
		<center>
			<h2><span class="glyphicon glyphicon-ok"></span> Approva documenti convenzione azienda </h2>
			<hr class = "line">
		</center>
		<div class="container">
			<nav class="navbar navbar-default">
				<div class="container-fluid">
			
					<!-- Collect the nav links, forms, and other content for toggling -->
					<div class="collapse navbar-collapse"
						id="bs-example-navbar-collapse-1">
					
						<div class="navbar-form navbar-left">
							<div class="form-group">
							
							</div>
						</div>
						
					<div class="navbar-form navbar-right">
							
				</div>
			</div>
	</div>
			
			<!-- /.navbar-collapse -->
		
			<div class="container">
				<h3><span class="glyphicon glyphicon-folder-open"></span> Documenti da approvare </h3>
				<div class="row">
					<div class="panel-group">
						<div class="col-3 col-sm-3">
							<div class="panel panel-default panel-modest" style="max-width: 80%; margin: 5px; max-height: 60%;">
								<div class="panel-heading">Nome azienda</div>
									<div class="panel-body"> Rappresentante legale</div>
										<center> 
											<a href="GestioneTf?action=visualizzaPagina&id=>"><button type="submit" class="btn btn-default">vai al documento</button></a>
										</center>
									</div>
								</div>		
								<div class="col-3 col-sm-3">
							<div class="panel panel-default panel-modest" style="max-width: 80%; margin: 5px; max-height: 60%;">
								<div class="panel-heading">Nome azienda</div>
									<div class="panel-body"> Rappresentante legale</div>
										<center> 
											<a href="GestioneTf?action=visualizzaPagina&id=>"><button type="submit" class="btn btn-default">vai al documento</button></a>
										</center>
									</div>
								</div>		
								<div class="container"><!-- questo div serve a mandare alla fine della pagina il numero delle pagine --></div>
									<nav aria-label="Page navigation example">
									  <ul class="pagination">
									    <li class="page-item">															
											 <a class="page-link" href="GestioneTf?action=ricercaTuttePagine&indice=<%=indice-NUM_ELE_PAG%>" aria-label="Previous">
											   <span aria-hidden="true">&laquo;</span>
								        	   <span class="sr-only">Previous</span>
								      		</a>
										
									    </li>
					   	 <li class="page-item "><a class="page-link" href="GestioneTf?action=ricercaTuttePagine&indice=>"></a></li>
					    	<li class="page-item">
								<a class="page-link" href="GestioneTf?action=ricercaTuttePagine&indice=" aria-label="Next">
				        		<span aria-hidden="true">&raquo;</span>
				        		<span class="sr-only">Next</span>
				     		 </a>
					    	</li>
					  </ul>
					</nav>	
						<center>
							<h2>Nessuna Azienda</h2>
						</center>
						
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