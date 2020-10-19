<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" session="true"%>

<%@ page import="Entidades.*, Controlador.*, java.util.*"%>

	<% 
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
			else
			{
	%>
	
	<% 
		ArrayList<Sucursal> sucursales = new ArrayList<Sucursal>();
	
		if((request.getAttribute("sucursales") == null))
		{
			ControladorSucursal cs = new ControladorSucursal();
			cs.BuscarSucursales(request, response);
		}

		sucursales = (ArrayList<Sucursal>) request.getAttribute("sucursales");
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
<title>Sucursales</title>

<style type="text/css">
	@media (max-width: 991px){
		
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
		
		.tabla{
			margin-top: 1%;
			width: 100%;
			text-align: center;
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

<div class="alert alert-success alert-dismissible fade show" role="alert" id="modalExito">
  			<%
				if(modal != null)
				{
					switch(modal)
					{
					case "sucursal_agregada":
						out.print("<h5 id='textoModalExito'>Sucursal agregada correctamente</h5>");
						break;
					case "sucursal_modificada":
						out.print("<h5 id='textoModalExito'>Sucursal modificada correctamente</h5>");
						break;
					case "sucursal_eliminada":
						out.print("<h5 id='textoModalExito'>Sucursal eliminada correctamente</h5>");
						break;
					}
				}
			%>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>

<div class="alert alert-danger alert-dismissible fade show" role="alert" id="modalError">
  			<%
				if(modal != null)
				{
					switch(modal)
					{
					case "sucursal_no_agregada":
						out.print("<h5 id='textoModalError'>La Sucursal no pudo ser agregada</h5>");
						break;
					case "sucursal_no_modificada":
						out.print("<h5 id='textoModalError'>La Sucursal no pudo ser modificada</h5>");
						break;
					case "sucursal_no_eliminada":
						out.print("<h5 id='textoModalError'>La Sucursal no pudo ser eliminada</h5>");
						break;
					}
				}
			%>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>

<div class="alert alert-warning alert-dismissible fade show" role="alert" id="modalAdvertencia">
  			<%
				if(modal != null)
				{
					switch(modal)
					{
					case "no_hay_resultados":
						out.print("<h5 id='textoModalAdvertencia'>No existe sucursal: " + request.getAttribute("nombreSucursalNoEncontrado") + "</h5>");
						break;
					}
				}
			%>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
	
	<div class="modalContainer" id="modalEliminar">
		<div class="modal-content" id="modalContentEliminar">
			<h4 style="margin-bottom: 3%;">¿Seguro que desea eliminar la sucursal: <div id="sucursalEliminar" style="display: inline-block;"></div>?</h4>
			<div>
				<button type="button" class="btn btn-secondary" onclick="cerrarEliminar()">Cancelar</button> 
				<form action="ControladorSucursal" method="post" name="sucursales" style="display: inline-block;">
					<input type="hidden" name="instruccion" value="eliminar_sucursal">
					<input type="hidden" name="nombre_sucursal" id="inputEliminarSucursal">
					<button type="submit" class="btn btn-danger">Eliminar</button>
				</form>
			</div>
		</div>
	</div>
	
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		  <a class="navbar-brand">Administrador</a>
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
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Cuotas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="PagarCuota.jsp">Pagar cuota</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Rutinas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="RegistrarRutina.jsp">Registrar nueva rutina</a> 
					<a class="dropdown-item" href="Rutinas.jsp">Ver rutinas</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Clases personalizadas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="">Ver todas las clases personalizadas</a> 
					<a class="dropdown-item" href="">Agregar nueva clase personalizada</a>
					<a class="dropdown-item" href="">Registrarse a una clase personalizada</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sucursales</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		      		<a style="background-color: orange" class="dropdown-item" href="Sucursales.jsp">Ver todas las sucursales</a>
		      		<a class="dropdown-item" href="RegistrarSucursal.jsp">Registrar nueva sucursal</a>  
		      		<a class="dropdown-item" href="Horarios.jsp">Horarios sucursales</a>  
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Entrada</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		      		<a class="dropdown-item" href="">Validar entrada</a> 
		        </div>
		      </li>
		    </ul>
		  </div>
		  <div style="margin-right: auto">
			  <div class="collapse navbar-collapse" id="navbarNavDropdown">
			  	<ul class="navbar-nav">
			  		<li class="nav-item dropdown">
				    	<a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				       		<%= userSesion.getNombreUsuario() %>
				        </a>
				       	<div class="dropdown-menu dropdown-menu-lg-right" aria-labelledby="navbarDropdownMenuLink">
				        	<a class="dropdown-item" href="Perfil.jsp">Perfil</a>
							<form action="ControladorSesion" method="post" name="Cerrar">
								<input type="hidden" name="instruccion" value="cerrar_sesion">
								<button style="color: red;" class="dropdown-item" type="submit">Cerrar sesión</button>
							</form> 
				   		</div>
					</li>
			  	</ul>
			  </div>
			</div>
		</nav>
			
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<h2>Sucursales</h2>&nbsp;
			<div style="background-color: #E3FBE3;"><a href="RegistrarSucursal.jsp" class="btn btn-outline-success" style="margin-top: auto; margin-bottom: auto;">Registrar nueva sucursal</a></div>
			<div style="margin-left: auto">
				<form class="form-inline my-2 my-lg-0" action="ControladorSucursal" method="post" name="busqueda_por_nombre_sucursal">
					<input type="hidden" name="instruccion" value="buscar_por_nombre_sucursal">
			     	<input class="form-control mr-sm-2" type="search" placeholder="Buscar sucursal" aria-label="Search" name="nombre_sucursal">
			     	<div style="background-color: #E3FBE3;"><button class="btn btn-outline-success my-2 my-sm-0" type="submit">Buscar</button></div>
		    	</form>
	    	</div>
		</nav>
		
		<div style="text-align: center; margin-top: 1%;">
		<table class="tabla">
		  <tr style="background-color: #ABA6A5">
		    <th scope="col" style="border: 2px solid black">Nombre sucursal</th>
		    <th scope="col" style="border: 2px solid black">Dirección</th>
		    <th scope="col" style="border: 2px solid black">Acción</th>
		  </tr>
		  <% 	
		  if(sucursales != null)
		  {
			  for(Sucursal s: sucursales)
			  {%>
			  	<tr style='background-color: #E3E3E3'>
			  	<td style='border: 2px solid black'><%=s.getNombreSucursal()%></td>
			  	<td style='border: 2px solid black'><%=s.getDireccion()%></td>
			  	<td style='border: 2px solid black'>
				  	<div class="row" style="margin: auto;">
					  	<form action="ControladorSucursal" method="post" name="sucursales">
							<input type="hidden" name="instruccion" value="editar_sucursal">
							<input type="hidden" name="nombre_sucursal" value="<%=s.getNombreSucursal()%>">
							<div style="background-color: #C9DFEA;"><button type="submit" class="btn btn-outline-primary btn-sm" style="width: 70px; color: black;">Editar</button></div>
						</form>
						
						&nbsp;
						<div style="background-color: #FFD5D3;"><button type="button" class="btn btn-outline-danger btn-sm" style="width: 70px; color: black;" onclick="abrirElimnar('<%=s.getNombreSucursal()%>')">Eliminar</button></div>
						&nbsp;
						 
						<form action="ControladorHorario" method="post" name="sucursales">
							<input type="hidden" name="instruccion" value="buscar_por_nombre_sucursal">
							<input type="hidden" name="nombre_sucursal" value="<%=s.getNombreSucursal()%>">
							<div style="background-color: #EFF9A4;"><button type="submit" class="btn btn-outline-warning btn-sm" style="width: 70px; color: black;">Horarios</button></div>
						</form>
					</div>
				</td>
			  	<tr>
			<%}
		  }
		  %>
		</table>
		</div>
	
	<script type="text/javascript">
		var modalEliminar = document.getElementById('modalEliminar');
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
		
		function abrirElimnar(nombreSucursal) {
			modalEliminar.style.display = "block";
			document.getElementById('sucursalEliminar').innerHTML = nombreSucursal;
			document.getElementById('inputEliminarSucursal').value = nombreSucursal;
		}
		
		function cerrarEliminar() {
			modalEliminar.style.display = "none";
		}
	</script>
	<script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
	<script type="text/javascript" src="js/popper.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>
<%
}}
%>