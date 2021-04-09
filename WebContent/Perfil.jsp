<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" session="true"%>

<%@ page import="Entidades.*, Controlador.*, java.util.*, Servlets.*, Util.*"%>

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
	%>
	
	<%
			ControladorCiudad cc = new ControladorCiudad();
			ArrayList<Ciudad> ciudades = cc.BuscarCiudades();
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
<title>Perfil</title>

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
</style>

</head>

<body style="background-color:#E1E1E1; padding-right: 1%; padding-left: 1%;">

<%
	String modal = (String)request.getAttribute("modal");
%>

<div class="alert alert-success alert-dismissible fade show modal" role="alert" id="modalExito" style="background-color: #83FF83; color: black;">
  			<%
				if(modal != null)
				{
					switch(modal)
					{
					case "perfil_modificado":
						out.print("<h5 id='textoModalExito'>Perfil modificado correctamente</h5>");
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
					case "perfil_no_modificado":
						out.print("<h5 id='textoModalError'>El perfil no pudo ser modificado</h5>");
						break;
					}
				}
			%>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
	
	<%if(userSesion.getNivelUsuario().getDescripcion().equals("administrador"))
	{%>
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
				  	<a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    	<i class="fas fa-user"></i>&nbsp;<%= userSesion.getNombreUsuario() %>
				    </a>
				    <div class="dropdown-menu dropdown-menu-lg-right" aria-labelledby="navbarDropdownMenuLink">
				      	<a style="background-color: orange" class="dropdown-item" href="Perfil.jsp"><i class="far fa-id-card"></i>&nbsp;Perfil</a>
						<form action="ServletSesion" method="post" name="Cerrar">
							<input type="hidden" name="instruccion" value="cerrar_sesion">
							<button style="color: red;" class="dropdown-item" type="submit"><i class="fas fa-sign-out-alt"></i>&nbsp;Cerrar sesión</button>
						</form> 
				  	</div>
				</li>
			</ul>
		  </div>
		</nav>
		<%}
		else if(userSesion.getNivelUsuario().getDescripcion().equals("usuario"))
		{%>
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		  <a class="navbar-brand" href="Inicio.jsp">Gimnasio</a>
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <div class="collapse navbar-collapse" id="navbarNavDropdown">
		    <ul class="navbar-nav">
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Cuotas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="MisCuotas.jsp">Mis cuotas</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Rutinas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="MisRutinas.jsp">Mis rutinas</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Clases personalizadas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="ClasesPersonalizadas.jsp">Ver clases personalizadas</a>
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sucursales</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		      		<a class="dropdown-item" href="Horarios.jsp">Horarios sucursales</a>  
		        </div>
		      </li>
		    </ul>
		    <ul class="navbar-nav user">
			 	<li class="nav-item dropdown">
				  	<a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    	<i class="fas fa-user"></i>&nbsp;<%= userSesion.getNombreUsuario() %>
				    </a>
				    <div class="dropdown-menu dropdown-menu-lg-right" aria-labelledby="navbarDropdownMenuLink">
				      	<a style="background-color: orange" class="dropdown-item" href="Perfil.jsp"><i class="far fa-id-card"></i>&nbsp;Perfil</a>
						<form action="ServletSesion" method="post" name="Cerrar">
							<input type="hidden" name="instruccion" value="cerrar_sesion">
							<button style="color: red;" class="dropdown-item" type="submit"><i class="fas fa-sign-out-alt"></i>&nbsp;Cerrar sesión</button>
						</form> 
				  	</div>
				</li>
			</ul>
		  </div>
		</nav>
		<%}%>
		
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<h2>Perfil</h2>
			<h4 style="margin-left: auto">Dni: <%=userSesion.getPersona().getDni()%></h4>
		</nav>
		
	 <div style="margin-top: 1%; padding: 1%; border-radius: 15px; border: 2px solid black; background-color: #CFCFCF;">	
		<form action="ServletPerfil" method="post" name="usuario">
		
		  <input type="hidden" name="instruccion" value="editar_perfil">
		  <input type="hidden" name="dni" value="<%=userSesion.getPersona().getDni()%>">
		  
		  <div class="form-row">
		    <div class="form-group col-md-6">
		      <label for="usuario"><h4>Usuario:</h4></label>
		      <input type="text" class="form-control" id="usuario" name="usuario" value="<%= userSesion.getNombreUsuario() %>" disabled>
		    </div>
		    <div class="form-group col-md-6">
		      <label for="contrasenia"><h4>Contraseña:</h4></label>
		      <input type="password" class="form-control" id="contrasenia" name="contrasenia" value="<%= Encriptacion.Desencriptar(userSesion.getContraseniaEncriptada()) %>" disabled>
		    </div>
		  </div>
		 	<div class="form-row">
			  <div class="form-group col-md-6">
			    <label for="nombreApellido"><h4>Nombre y Apellido:</h4></label>
			    <input type="text" class="form-control" id="nombreApellido" name="nombreApellido"  value="<%= userSesion.getPersona().getNombreApellido() %>" disabled>
			  </div>
			  <div class="form-group col-md-6">
			    <label for="email"><h4>EMail:</h4></label>
			    <input type="text" class="form-control" id="email" name="email" value="<%= userSesion.getPersona().getEmail() %>" disabled>
			  </div>
			</div>
		  <div class="form-row">
			  <div class="form-group col-md-6">
			    <label for="telefono"><h4>Teléfono:</h4></label>
			    <input type="text" class="form-control" id="telefono" name="telefono" value="<%= userSesion.getPersona().getTelefono() %>" disabled>
			  </div>
			  <div class="form-group col-md-6" >
			    <label for="direccion"><h4>Dirección:</h4></label>
			    <input type="text" class="form-control" id="direccion" name="direccion" value="<%= userSesion.getPersona().getDireccion() %>" disabled>
			  </div>
		  </div>
		  <div class="form-row">
			  	<div class="form-group col-md-6">
				    <label for="ciudad"><h4>Ciudad:</h4></label>
				    <select id="ciudad" class="form-control" name="ciudad" disabled>
					    <% 	
							if(ciudades != null)
							{
								for(Ciudad ciudad: ciudades)
								{%>
						  			<option value="<%=ciudad.getCodPostal()%>"
									<%if(userSesion.getPersona().getCiudad().getCodPostal().equals(ciudad.getCodPostal())){%>selected<%}%>><%=ciudad.getDescripcion() %></option>
								<% } 
							}%>
					</select>
			  	</div>
			  	<div class="form-group col-md-6" style="margin-top: auto;">
					<button type="submit" class="btn btn-success" disabled id="btn-guardar">Guardar cambios</button>
					<button type="button" class="btn btn-primary" onclick="habilitarEdicion()" id="btn-edicion">Habilitar edición</button>
					<button type="button" class="btn btn-danger" onclick="abrirElimnar('<%=userSesion.getNombreUsuario()%>')">Eliminar usuario</button>
				</div>
		  </div>
		</form>
	</div>
	
	<div class="modalContainer" id="modalEliminar">
		<div class="modal-content" id="modalContentEliminar">
			<h4 style="margin-bottom: 3%;">¿Seguro que desea eliminar usuario?</h4>
			<div>
				<button type="button" class="btn btn-secondary" onclick="cerrarEliminar()">Cancelar</button> 
				<form action="ServletPerfil" method="post" name="usuario" style="display: inline-block;">
					<input type="hidden" name="instruccion" value="eliminar_usuario">
					<input type="hidden" name="nombre_usuario" id="inputEliminarUsuario">
					<button type="submit" class="btn btn-danger">Eliminar</button>
				</form>
			</div>
		</div>
	</div>
		
	<script type="text/javascript">	
		var modalEliminar = document.getElementById('modalEliminar');
		var modalExito = document.getElementById('modalExito');
		var modalError = document.getElementById('modalError');
	
		var textoModalExito = document.getElementById('textoModalExito')
		var textoModalError = document.getElementById('textoModalError');
		
		var nombreUsuario = document.getElementById('usuario');
		var contrasenia = document.getElementById('contrasenia');
		var email = document.getElementById('email');
		var telefono = document.getElementById('telefono');
		var direccion = document.getElementById('direccion');
		var nombreApellido = document.getElementById('nombreApellido');
		var ciudad = document.getElementById('ciudad');
		
		var btn_guardar = document.getElementById('btn-guardar');
		var btn_edicion = document.getElementById('btn-edicion');
		
		if(textoModalExito != null)
		{
			modalExito.style.display = "block";
		}
		
		if(textoModalError != null)
		{
			modalError.style.display = "block";
		}
		
		function habilitarEdicion() {
			nombreUsuario.disabled = false;
			contrasenia.disabled = false;
			nombreApellido.disabled = false;
			email.disabled = false;
			telefono.disabled = false;
			direccion.disabled = false;
			ciudad.disabled = false;
			btn_guardar.disabled = false;
			btn_edicion.disabled = true;
		}
		
		function abrirElimnar(usuario) {
			modalEliminar.style.display = "block";
			document.getElementById('inputEliminarUsuario').value = usuario;
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
<%}}
catch(Exception e)
{
	RequestDispatcher dispatcher = request.getRequestDispatcher("Errores.jsp");
	request.setAttribute("exception", e);
	dispatcher.forward(request, response);
}%>