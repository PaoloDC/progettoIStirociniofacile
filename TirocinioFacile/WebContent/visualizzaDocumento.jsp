<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.* , it.tirociniofacile.bean.*,it.tirociniofacile.control.*"%>
	<%
	
	DocumentoConvenzioneBean convenzione = (DocumentoConvenzioneBean) request.getAttribute("convenzione");
	DocumentoQuestionarioBean questionario = (DocumentoQuestionarioBean) request.getAttribute("questionario");
	

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TirocinioFacile - Visualizza documento </title>

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


	<div class="container">
		<%@ include file="header.jsp"%>
		<div class="row">
			<div class="col-4 col-sm-4">
				<h3><span class="glyphicon glyphicon-ok"></span> Documento da approvare</h3>
				<%
				 if(convenzione != null){		
				%>
				<br> <br> <br>
					<a href="GestioneTf?action=convalidaDocumento&id=<%=convenzione.getPartitaIva()%>&approvato=true" >
						<button  type="submit" class=" btn btn-success"  > Approva</button>
					</a>
					<a href="GestioneTf?action=eliminaDocumento&id=<%=convenzione.getPartitaIva()%>&approvato=false">
				    	<button type="submit" class="btn btn-danger"   > Elimina</button>
				    </a>
			</div>
			<div class="row">
				<div class="col-6 col-sm-6">
					<center>
						<iframe src="pdf/prova.pdf" allowTransparency frameborder="0"
							style="overflow: hidden; margin-top: 1px; width: 550px; height: 520px"></iframe>
						<br> <br> <br> <br>
					</center>

				</div>
				<%
					} else if (questionario != null) {
				%>
				<br> <br> <br>
					<a href="GestioneTf?action=convalidaDocumento&id=<%=questionario.getId()%>&approvato=true">
						<button  type="submit" class=" btn btn-success"   > Approva</button>
					</a> 
					<a href="GestioneTf?action=eliminaDocumento&id=<%=questionario.getId()%>&approvato=false">
					    <button type="submit" class="btn btn-danger"   > Elimina</button>
					</a>	
						</div>
						<div class="row">
							<div class="col-6 col-sm-6">
								<center>
									<iframe src="pdf/prova.pdf" allowTransparency frameborder="0"
										style="overflow: hidden; margin-top: 1px; width: 550px; height: 520px"></iframe>
									<br> <br> <br> <br>
								</center>
							</div>
							</div>
						</div>
			<%} else {%>
					<center>
							<h2>Nessuna Documento</h2>
						</center>
			<%} %>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html></html>