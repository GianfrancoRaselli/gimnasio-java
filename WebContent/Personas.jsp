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
			else if(userSesion.getNivelUsuario().getDescripcion().equals("administrador"))
			{
	%>
	
	<% 
		ArrayList<Persona> personas = new ArrayList<Persona>();
	
		if((request.getAttribute("personas") == null) && (request.getAttribute("persona") == null))
		{
			ControladorPersona cp = new ControladorPersona();
			cp.BuscarPersonas(request, response);
		}

		if(request.getAttribute("persona") == null)
		{
			personas = (ArrayList<Persona>) request.getAttribute("personas");
		}
		else
		{
			personas.add((Persona) request.getAttribute("persona"));
		}
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
<title>Personas</title>

<style type="text/css">
	#navbar {
		position: fixed;
		z-index: 100;
		width: 100%;
		top: 0;
	}
		
	@media (min-width: 991.5px) {
		.user {
			margin-left: auto;
		}
	}

	@media (max-width: 991.5px) {
		.user {
			float: left;
		}
	}
	
	#content{
		 margin-top: 56px;
	}
	
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
		padding-top: 70px;
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
</style>	
</head>

<body style="background-color:#E1E1E1;"> 

<%
	String modal = (String)request.getAttribute("modal");
%>

<div class="alert alert-success alert-dismissible fade show modal" role="alert" id="modalExito" style="background-color: #83FF83; color: black;">
  			<%
				if(modal != null)
				{
					switch(modal)
					{
					case "persona_agregada":
						out.print("<h5 id='textoModalExito'>Persona agregada correctamente</h5>");
						break;
					case "persona_modificada":
						out.print("<h5 id='textoModalExito'>Persona modificada correctamente</h5>");
						break;
					case "persona_eliminada":
						out.print("<h5 id='textoModalExito'>Persona eliminada correctamente</h5>");
						break;
					case "usuario_agregado":
						out.print("<h5 id='textoModalExito'>Usuario agregado correctamente</h5>");
						break;
					case "usuario_modificado":
						out.print("<h5 id='textoModalExito'>Usuario modificado correctamente</h5>");
						break;
					case "usuario_eliminado":
						out.print("<h5 id='textoModalExito'>Usuario eliminado correctamente</h5>");
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
					case "persona_no_agregada":
						out.print("<h5 id='textoModalError'>La persona no pudo ser agregada</h5>");
						break;
					case "persona_no_modificada":
						out.print("<h5 id='textoModalError'>La persona no pudo ser modificada</h5>");
						break;
					case "persona_no_eliminada":
						out.print("<h5 id='textoModalError'>La persona no pudo ser eliminada</h5>");
						break;
					case "usuario_no_agregado":
						out.print("<h5 id='textoModalError'>El usuario no pudo ser agregado</h5>");
						break;
					case "usuario_no_modificado":
						out.print("<h5 id='textoModalError'>El usuario no pudo ser modificado</h5>");
						break;
					case "usuario_no_eliminado":
						out.print("<h5 id='textoModalError'>El usuario no pudo ser eliminado</h5>");
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
					case "no_hay_resultados":
						out.print("<h5 id='textoModalAdvertencia'>No existe persona con DNI: " + request.getAttribute("dniNoEncontrado") + "</h5>");
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
			<h4 style="margin-bottom: 3%;">¿Seguro que desea eliminar la persona con DNI: <div id="dniEliminar" style="display: inline-block;"></div>?</h4>
			<div>
				<button type="button" class="btn btn-secondary" onclick="cerrarEliminar()">Cancelar</button> 
				<form action="ControladorPersona" method="post" name="personas" style="display: inline-block;">
					<input type="hidden" name="instruccion" value="eliminar_persona">
					<input type="hidden" name="dni" id="inputEliminarPersona">
					<button type="submit" class="btn btn-danger">Eliminar</button>
				</form>
			</div>
		</div>
	</div>
	
	<div id="navbar">
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		  <a class="navbar-brand">Administrador</a>
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <div class="collapse navbar-collapse" id="navbarNavDropdown">
		    <ul class="navbar-nav">
		      <li class="nav-item dropdown">
		        <a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Personas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		      		<a style="background-color: orange" class="dropdown-item" href="Personas.jsp">Ver todas las personas</a> 
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
					<a class="dropdown-item" href="">Ver todas las clases personalizadas</a> 
					<a class="dropdown-item" href="">Agregar nueva clase personalizada</a>
					<a class="dropdown-item" href="">Registrarse a una clase personalizada</a> 
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
						<form action="ControladorSesion" method="post" name="Cerrar">
							<input type="hidden" name="instruccion" value="cerrar_sesion">
							<button style="color: red;" class="dropdown-item" type="submit"><i class="fas fa-sign-out-alt"></i>&nbsp;Cerrar sesión</button>
						</form> 
				  	</div>
				</li>
			</ul>
		  </div>
		</nav>
	</div>
		
	<div id="content">
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<h2>Personas</h2>&nbsp;
			<div style="background-color: #E3FBE3;"><a href="RegistrarPersona.jsp" class="btn btn-outline-success" style="margin-top: auto; margin-bottom: auto;">Registrar nueva persona</a></div>
			<div style="margin-left: auto">
				<form class="form-inline my-2 my-lg-0" action="ControladorPersona" method="post" name="busqueda_por_dni">
					<input type="hidden" name="instruccion" value="buscar_por_dni">
			     	<input class="form-control mr-sm-2" type="search" placeholder="Buscar por DNI" aria-label="Search" name="dni">
			     	<div style="background-color: #E3FBE3;"><button class="btn btn-outline-success my-2 my-sm-0" type="submit">Buscar</button></div>
		    	</form>
	    	</div>
		</nav>
		
		<div style="padding-right: 1%; padding-left: 1%;">
			<table class="tabla" style="text-align: center;">
			  <tr style="background-color: #ABA6A5">
			    <th scope="col" style="border: 2px solid black">Dni</th>
			    <th scope="col" style="border: 2px solid black">Nombre y apellido</th>
			    <th scope="col" style="border: 2px solid black">Teléfono</th>
			    <th scope="col" style="border: 2px solid black">Email</th>
			    <th scope="col" style="border: 2px solid black">Dirección</th>
			    <th scope="col" style="border: 2px solid black">Tipo</th>
			    <th scope="col" style="border: 2px solid black">Acción</th>
			  </tr>
			  <% 	
			  if(personas != null)
			  {
				  for(Persona p: personas)
				  {%>
				  	<tr style='background-color: #E3E3E3'>
				  	<td style='border: 2px solid black'><%out.print(p.getDni());%></td>
				  	<td style='border: 2px solid black'><%out.print(p.getNombreApellido());%></td>
				  	<td style='border: 2px solid black'><%out.print(p.getTelefono());%></td>
				  	<td style='border: 2px solid black'><%out.print(p.getEmail());%></td>
				  	<td style='border: 2px solid black'><%out.print(p.getDireccion());%></td>
				  	<td style='border: 2px solid black'><%out.print(p.getTipo().name());%></td>
				  	<td style='border: 2px solid black'>
					  	<div class="row" style="margin: auto;">
						  	<form action="ControladorPersona" method="post" name="personas">
								<input type="hidden" name="instruccion" value="buscar_persona_a_editar">
								<input type="hidden" name="dni" value="<%=p.getDni()%>">
								<div style="background-color: #C9DFEA;"><button type="submit" class="btn btn-outline-primary btn-sm" style="width: 70px; color: black;">Editar</button></div>
							</form>
							
							&nbsp;
							<div style="background-color: #FFD5D3;"><button type="button" class="btn btn-outline-danger btn-sm" style="width: 70px; color: black;" onclick="abrirEliminar('<%=p.getDni()%>')">Eliminar</button></div>
							&nbsp;
							 
							<form action="ControladorUsuario" method="post" name="usuario">
								<input type="hidden" name="instruccion" value="buscar_por_dni">
								<input type="hidden" name="dni" value="<%=p.getDni()%>">
								<div style="background-color: #EFF9A4;"><button type="submit" class="btn btn-outline-warning btn-sm" style="width: 70px; color: black;">Usuario</button></div>
							</form>
						</div>
					</td>
				  	</tr>
				<%}
			  }
			  %>
			</table>
		</div>
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
		
		function abrirEliminar(dni) {
			modalEliminar.style.display = "block";
			document.getElementById('dniEliminar').innerHTML = dni;
			document.getElementById('inputEliminarPersona').value = dni;
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