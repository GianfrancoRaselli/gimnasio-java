<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" session="true"%>

<%@ page import="Entidades.*, Controlador.*, java.util.*, Servlets.*"%>

	<% 
	try
	{
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		HttpSession sesion = request.getSession();
	
		if(sesion.getAttribute("usuario") == null)
		{
			response.sendRedirect("Login.jsp"); 
		}
		else
		{
			Usuario userSesion = (Usuario)sesion.getAttribute("usuario");
			
			if(userSesion.getNivelUsuario().getDescripcion().equals("usuario"))
			{
				response.sendRedirect("Login.jsp"); 
			}
			else if(userSesion.getNivelUsuario().getDescripcion().equals("administrador"))
			{
	%>
	
	<%
			ControladorCuota cc = new ControladorCuota();
			cc.ActualizarCuotas();
	%>
	
	<% 
		ControladorSucursal cs = new ControladorSucursal();
		ArrayList<Sucursal> sucursales = cs.BuscarSucursales();
	%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="author" content="">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maxium-scale=1.0, minimum-scale=1.0">
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"> 
<script src="https://kit.fontawesome.com/5520773c7b.js" crossorigin="anonymous"></script>
<title>Entrada</title>

<style type="text/css">
	.modal{
		display: none;
		position: fixed;
		width: 40%;
		height: auto;
		z-index: 1;
		top: auto;
		bottom: 0;
		left: auto;
		right: 0;
	}

	.modalContainer {
		display: none; 
		position: absolute; 
		z-index: 1;		
		padding-top: 1%;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%; 
		overflow: hidden; 
		background-color: rgb(0,0,0);
		background-color: rgba(0,0,0,0.4);
	}

	.modalContainer .modal-content {
		background-color: #fefefe;
		margin: auto;
		padding: 1.5%;
		border: 1px solid lightgray;
		width: 50%;
	}
	
	#modalExito{
		display: none;
	}
		
	#modalError{
		display: none;
	}
		
	#modalAdvertencia{
		display: none;
	}
</style>
	
</head>
<body style="background-color:#E1E1E1; padding-right: 1%; padding-left: 1%;"> 

<%
	String modal = (String)request.getAttribute("modal");
%>

<div class="alert alert-success alert-dismissible fade show modal" role="alert" id="modalExito">
  			<%
				if(modal != null)
				{
					switch(modal)
					{
						case "entrada_habilitada":
							out.print("<h5 id='textoModalExito'>Entrada habilitada</h5>");
							break;
					}
				}
			%>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>

<div class="alert alert-danger alert-dismissible fade show modal" role="alert" id="modalError">
  			<%
				if(modal != null)
				{
					switch(modal)
					{
						case "persona_no_encontrada":
							out.print("<h5 id='textoModalError'>Persona con DNI: " + request.getAttribute("dniNoEncontrado") + " inexistente</h5>");
							break;
						case "sucursal_no_encontrada":
							out.print("<h5 id='textoModalError'>Sucursal inexistente</h5>");
							break;
					}
				}
			%>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>

<div class="alert alert-warning alert-dismissible fade show modal" role="alert" id="modalAdvertencia">
  			<%
				if(modal != null)
				{
					switch(modal)
					{
						case "entrada_inhabilitada":
							out.print("<h5 id='textoModalAdvertencia'>Entrada inhabilitada - Persona con cuotas impagas</h5>");
							break;
					}
				}
			%>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
	
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		  <a class="navbar-brand" href="Inicio.jsp">Gimnasio</a>
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <div class="collapse navbar-collapse" id="navbarNavDropdown">
		    <ul class="navbar-nav">
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Personas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		      		<a class="dropdown-item" href="Personas.jsp">Ver todas las personas</a> 
					<a class="dropdown-item" href="RegistrarPersona.jsp">Registrar nueva persona</a> 
					<a class="dropdown-item" href="BuscarUsuario.jsp">Buscar usuario</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Cuotas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="BuscarPersona.jsp">Pagar cuotas</a> 
					<a class="dropdown-item" href="MisCuotas.jsp">Mis cuotas</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Rutinas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="BuscarPersonaDeRutina.jsp">Registrar nueva rutina</a> 
					<a class="dropdown-item" href="MisRutinas.jsp">Mis rutinas</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Clases personalizadas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		      		<a class="dropdown-item" href="Asistencias.jsp">Registrar asistencias</a>
					<a class="dropdown-item" href="ClasesPersonalizadas.jsp">Ver clases personalizadas</a>
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sucursales</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		      		<a class="dropdown-item" href="Sucursales.jsp">Ver todas las sucursales</a>
		      		<a class="dropdown-item" href="RegistrarSucursal.jsp">Registrar nueva sucursal</a>  
		      		<a class="dropdown-item" href="Horarios.jsp">Horarios sucursales</a>  
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Entrada</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		      		<a style="background-color: orange" class="dropdown-item" href="ValidarEntrada.jsp">Validar entrada</a> 
		        </div>
		      </li>
		    </ul>
		    <ul class="navbar-nav user">
			 	<li class="nav-item dropdown">
				  	<a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    	<i class="fas fa-user"></i>&nbsp;<%= userSesion.getNombreUsuario() %>
				    </a>
				    <div class="dropdown-menu dropdown-menu-lg-right" aria-labelledby="navbarDropdownMenuLink">
				      	<a class="dropdown-item" href="Perfil.jsp"><i class="far fa-id-card"></i>&nbsp;Perfil</a>
						<form action="ServletSesion" method="post" name="Cerrar">
							<input type="hidden" name="instruccion" value="cerrar_sesion">
							<button style="color: red;" class="dropdown-item" type="submit"><i class="fas fa-sign-out-alt"></i>&nbsp;Cerrar sesión</button>
						</form> 
				  	</div>
				</li>
			</ul>
		  </div>
		</nav>
			
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<h2>Validar entrada</h2>
		</nav>
		
		<form action="ServletEntrada" method="post" name="entrada" 
		style="padding: 3%; border-radius: 15px; border: 2px solid black; background-color: #CFCFCF; margin-top: 1%;">
		
			<input type="hidden" name="instruccion" value="validar_entrada">

			<div class="row" style="margin-top: 1%;">
				<div class="col-2"><h5>Sucursal</h5></div>
				<div class="col-10">
					<select class="form-control" name="nombre_sucursal" id="nombre_sucursal">
					<option selected disabled>Seleccione sucursal</option>
					<%
					   	for(Sucursal s: sucursales)
						{%>
							<option value="<%=s.getNombreSucursal()%>"><%=s.getNombreSucursal()%></option>
						<%} 
					%>
					</select>
				</div>
			</div>
				
			<div class="row" style="margin-top: 2%;">
				<div class="col-2"><h5>Dni cliente</h5></div>
				<div class="col-10">
					<input type="text" class="form-control" id="dni" placeholder="" name="dni" required>
				</div>
			</div>
				
			<div class="row" style="margin-top: 2%;">
				<button type="submit" class="btn btn-success">Validar entrada</button>
			</div>

		</form>
	
	<script type="text/javascript">
		var modalExito = document.getElementById('modalExito');
		var modalError = document.getElementById('modalError');
		var modalAdvertencia = document.getElementById('modalAdvertencia');
		var textoModalExito = document.getElementById('textoModalExito')
		var textoModalError = document.getElementById('textoModalError');
		var textoModalAdvertencia = document.getElementById('textoModalAdvertencia');
		
		if(textoModalExito != null)
		{
			modalExito.style.display = "block";
		}
		
		if(textoModalError != null)
		{
			modalError.style.display = "block";
		}
		
		if(textoModalAdvertencia != null)
		{
			modalAdvertencia.style.display = "block";
		}
	</script>
	<script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
	<script type="text/javascript" src="js/popper.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>
<%}}}
catch(Exception e)
{
	RequestDispatcher dispatcher = request.getRequestDispatcher("Errores.jsp");
	request.setAttribute("exception", e);
	dispatcher.forward(request, response);
}%>