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
				if(request.getAttribute("usuario") == null)
				{
					response.sendRedirect("BuscarUsuario.jsp"); 
				}
				else
				{
					Usuario user = (Usuario)request.getAttribute("usuario");
					String dniUser = (String)request.getAttribute("dni");
	%>
	
	<%
		ControladorNivelUsuario cnu = new ControladorNivelUsuario();
		cnu.BuscarNivelesUsuarios(request, response);
		
		ArrayList<NivelUsuario> nivelesUsuarios  = (ArrayList<NivelUsuario>) request.getAttribute("niveles_usuarios");
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
<title>Usuario</title>
</head>
<body style="background-color:#E1E1E1; padding-right: 1%; padding-left: 1%;">
	
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
		      		<a class="dropdown-item" href="Personas.jsp">Ver todas las personas</a> 
					<a class="dropdown-item" href="RegistrarPersona.jsp">Registrar nueva persona</a> 
					<a style="background-color: orange" class="dropdown-item" href="BuscarUsuario.jsp">Buscar usuario</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Cuotas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item"></a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Rutinas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item">Agregar nueva rutina</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Clases personalizadas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item">Ver todas las clases personalizadas</a> 
					<a class="dropdown-item">Agregar nueva clase personalizada</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sucursales</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item">Ver todas las sucursales</a> 
					<a class="dropdown-item">Agregar nueva sucursal</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		       		<%= userSesion.getNombreUsuario() %>
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<form action="ControladorSesion" method="post" name="Cerrar">
						<input type="hidden" name="instruccion" value="cerrar_sesion">
						<button class="dropdown-item" type="submit">Cerrar sesión</button>
					</form> 
		        </div>
		      </li>
		    </ul>
		  </div>
		</nav>
		
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<h2>Usuario</h2>&nbsp; 
				<%if(user == null){%>
					<h2 style="color: red;">no registrado</h2>
			 	<%}%>
			<h4 style="margin-left: auto">Dni: <%= dniUser %></h4>
		</nav>
	
	<div style="padding: 3%; border-radius: 15px; border: 2px solid black; background-color: #CFCFCF; margin-top: 1%;">
	<form action="ControladorUsuario" method="post" name="usuario" style="margin-top: 2%;">
	
		<%if(user != null)
		{%>
			<input type="hidden" name="instruccion" value="editar_usuario">
			<input type="hidden" name="id" value="<%out.print(user.getNombreUsuario());%>">
		<%}
		else
		{%>
			<input type="hidden" name="instruccion" value="agregar_usuario">
		<%}%>
		
		<input type="hidden" name="dni" value="<% out.print(dniUser); %>">
		
	  <div class="form-group row" style="margin-bottom: 2%">
	    <label for="nombreUsuario" class="col-md-2 col-form-label"><h5>Nombre usuario</h5></label>
	    <div class="col-md-10">
	      <input type="text" class="form-control" id="nombreUsuario" name="nombre_usuario" value="<% if(user!=null)out.print(user.getNombreUsuario()); %>" required minlength="6">
	    </div>
	  </div>
	  
	  <div class="form-group row" style="margin-bottom: 2%">
	    <label for="Contrasenia" class="col-md-2 col-form-label"><h5>Contraseña</h5></label>
	    <div class="col-md-10">
	      <input type="password" class="form-control" id="Contrasenia" name="contrasenia" value="<% if(user!=null)out.print(user.getContrasenia()); %>" required minlength="8">
	    </div>
	  </div>
	  
	  <fieldset class="form-group" style="margin-bottom: 3%">
	    <div class="row">
	      <legend class="col-form-label col-md-2 pt-0"><h5>Nivel usuario</h5></legend>
	      <div class="col-md-10">
	      <div class="form-check">
	      <%for(NivelUsuario nivel: nivelesUsuarios)
	    	{%>
	          <input class="form-check-input" type="radio" name="nivel_usuario" id="<%out.print(nivel.getNroNivel());%>" value="<%out.print(nivel.getNroNivel());%>" 
	          <%if(user != null && user.getNivelUsuario().getNroNivel()==nivel.getNroNivel()){ %>checked<%} %>>
	          <label class="form-check-label" for="<%out.print(nivel.getNroNivel());%>">
	            <% out.print(nivel.getDescripcion()); %>
	          </label><br>
	        <%} %>
	      </div>
	      </div>
	    </div>
	  </fieldset>
	  
	  <div class="form-group row">
	    <div class="col-sm-auto">
	      <%if(user == null)
	      {%>
	      	<button type="submit" class="btn btn-success">Agregar usuario</button>
	      <%}
	      else
	      {%>
	      	<button type="submit" class="btn btn-primary">Confirmar cambios</button>
	      <%}%>
	    </div>
	    
	    <div class="col-sm-auto">
	    <%if(user != null)
	    {%>
			<form action="ControladorUsuario" method="post" name="usuario" style="display: inline-block;">
				<input type="hidden" name="instruccion" value="eliminar_usuario">
				<input type="hidden" name="nombre_usuario" value="<%out.print(user.getNombreUsuario());%>">
				<button type="submit" class="btn btn-danger">Eliminar usuario</button>
			</form>
		<%}%>
		</div>
	  </div>
	  
	</form>
	</div>
 	
	<script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
	<script type="text/javascript" src="js/popper.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>
<% 
}}}
%>