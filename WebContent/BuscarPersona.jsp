<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" session="true"%>
    
<%@ page import="Entidades.*, Controlador.*, Servlets.*"%>

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
<title>Buscar persona</title>

<style type="text/css">
	* {
		box-sizing: border-box;
	}
	
	*:focus {
		outline: none;
	}
	
	.buscar {
		margin: auto;
		width: 350px;
	}
	
	.buscar-screen {
		background-color: #FFF;
		padding: 20px;
		border-radius: 5px
	}
	
	.app-title {
		text-align: center;
		color: black;
	}
	
	.buscar-form {
		text-align: center;
	}
	
	.control-group {
		margin-bottom: 10px;
	}
	
	input {
		text-align: center;
		background-color: #ECF0F1;
		border: 2px solid transparent;
		border-radius: 3px;
		font-size: 16px;
		font-weight: 200;
		padding: 10px 0;
		width: 250px;
		transition: border .5s;
	}
	
	input:focus {
		border: 2px solid #3498DB;
		box-shadow: none;
	}
	
	.btn {
		border: 2px solid transparent;
		background: #3498DB;
		color: #ffffff;
		font-size: 16px;
  		line-height: 25px;
		padding: 10px 0;
		text-decoration: none;
		text-shadow: none;
	 	border-radius: 3px;
		box-shadow: none;
		transition: 0.25s;
		display: block;
		width: 250px;
		margin: 0 auto;
	}
	
	.btn:hover {	
		background-color: #2980B9;
	}
</style>

</head>

<body style="background-color:#E1E1E1; padding-right: 1%; padding-left: 1%;">

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
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Entrada</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		      		<a class="dropdown-item" href="ValidarEntrada.jsp">Validar entrada</a> 
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
			<h2>Buscar persona</h2>
	    </nav>

		<form action="ServletPagarCuotas" method="post" name="persona" style="margin-top: 3%;">
			<input type="hidden" name="instruccion" value="buscar_persona">
			
			<div class="buscar">
				<div class="buscar-screen">
					<div class="app-title">
						<h3><strong>Buscar persona a pagar cuotas</strong></h3>
					</div>
		
					<div class="buscar-form">
						<div class="control-group">
							<label><h6>Ingrese DNI: </h6></label>
							<input type="text" placeholder="DNI" name="dni">
						</div>

						<button type="submit" class="btn btn-primary btn-large btn-block">Buscar persona</button>
					</div>
					
					<% 
						if(request.getAttribute("personaNoEncontrada") != null)
						{%>
							<div style="color: red; margin-top: 5%; text-align: center;">
								<p>Persona no encontrada</p>
							</div>							
						<%}
					%>
				</div>
			</div>
		</form>

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