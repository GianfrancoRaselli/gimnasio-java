<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" session="true"%>
    
<%@ page import="java.util.*, Entidades.*, Controlador.*"%>

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
		ControladorCiudad cc = new ControladorCiudad();
		cc.BuscarCiudades(request, response);
		
		ArrayList<Ciudad> ciudades  = (ArrayList<Ciudad>) request.getAttribute("ciudades");
	%>
	
	<%
		Sucursal s = null;
				
		if(request.getAttribute("modo") != null && request.getAttribute("modo").equals("editar"))
		{
			s = (Sucursal)request.getAttribute("sucursal");
		}
	%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<meta name="author" content="">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maxium-scale=1.0, minimum-scale=1.0">
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"> 
<title>Registrar sucursal</title>

<style type="text/css">
	#botonesRegistrarSucursal{
		text-align: center;
	}
	
	@media (max-width: 991px){
		#borrar{
			display: none;
		}
		#formRegistrarSucursal{
			padding: 6%;
		}
	}
	
	@media (min-width: 992px){
		#tituloRegistrarSucursal{
			font-size: 36pt;
		}
		#formRegistrarSucursal{
			padding: 3%;
		}
	}
</style>

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
		      		<a class="dropdown-item" href="Sucursales.jsp">Ver todas las sucursales</a>
		      		<a style="background-color: orange" class="dropdown-item" href="RegistrarSucursal.jsp">Registrar nueva sucursal</a>  
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
			<% 
				if(request.getAttribute("modo") != null && request.getAttribute("modo").equals("editar"))
				{
					out.print("<h2>Actualizar sucursal</h1>");	
					out.print("<h4 style='margin-left: auto'>" + "Sucursal: " + s.getNombreSucursal() + "</h4>");	
				}
				else
				{
					out.print("<h2>Registrar nueva sucursal</h2>");	
				}
			%>
		</nav>

		<div style="border-radius: 15px; border: 2px solid black; background-color: #CFCFCF; margin-top: 1%;" id="formRegistrarSucursal">
			<form action="ControladorSucursal" method="post" name="Registro">
			
			<% 
				if(request.getAttribute("modo") != null && request.getAttribute("modo").equals("editar"))
				{
					out.print("<input type='hidden' name='instruccion' value='actualizar_sucursal'>");	
					out.print("<input type='hidden' name='id' value='" + s.getNombreSucursal() + "'>");	
				}
				else
				{
					out.print("<input type='hidden' name='instruccion' value='registrar_sucursal'>");	
				}
			%>

			<div class="form-group row" style="margin-bottom: 2%">
				<label for="nombreSucursal" class="col-12 col-md-12 col-lg-3"><h5>Nombre sucursal</h5></label>
   			 	<input type="text" class="form-control col-md-12 col-lg-9" id="nombreSucursal" placeholder="Ej: Sucursal Norte" name="nombre_sucursal" required
   				value="<% if(request.getAttribute("modo") != null && request.getAttribute("modo").equals("editar") && s != null) out.print(s.getNombreSucursal()); %>">
  			</div>
  			
			<div class="form-group row" style="margin-bottom: 2%">
				<label for="direccion" class="col-3"><h5>Direccion</h5></label>
	 			<input type="text" class="form-control col-md-12 col-lg-9" id="direccion" placeholder="Ej: Maipú 458" name="direccion" required
				value="<% if(request.getAttribute("modo") != null && request.getAttribute("modo").equals("editar") && s != null) out.print(s.getDireccion()); %>">
			</div>

			 <div class="form-group row" style="margin-bottom: 3%">
					<label for="ciudad" class="col-3"><h5>Ciudad</h5></label>
				    <select id="ciudad" class="form-control col-md-12 col-lg-9" required name="ciudad">
				    <option <%if(s == null){%>selected<%}%> disabled>Seleccione una ciudad</option>
				    <% 	
						if(ciudades != null)
						{
							for(Ciudad ciudad: ciudades)
							{%>
					  			<option value="<% out.print(ciudad.getCodPostal()); %>"
								<%if(s != null && s.getCiudad().getCodPostal().equals(ciudad.getCodPostal())){%>selected<%}%>><% out.print(ciudad.getDescripcion()); %></option>
							<% } 
						}%>
					</select>
			  </div>
				
				 <div class="row" id="botonesRegistrarSucursal">
  			
	  				<div class="col-sm-0 col-md-0 col-lg-5" id="borrar">
	  				<%if(request.getAttribute("modo") != null && request.getAttribute("modo").equals("editar")){out.print("<button type='reset' class='btn btn-warning col-sm-12 col-md-12 col-lg-12'>Restablecer valores</button>");}
	  				else{out.print("<button type='reset' class='btn btn-danger col-sm-12 col-md-12 col-lg-12'>Restablecer valores</button>");}%>
	  				</div>
	  				<div class="col-sm-0 col-md-0 col-lg-2" id="borrar"></div>
	  				<div class="col-sm-12 col-md-12 col-lg-5">
	  				<%if(request.getAttribute("modo") != null && request.getAttribute("modo").equals("editar")){out.print("<button type='submit' class='btn btn-primary col-sm-12 col-md-12 col-lg-12'>Confirmar cambios</button>");}
	  				else{out.print("<button type='submit' class='btn btn-success col-sm-12 col-md-12 col-lg-12'>Agregar sucursal</button>");}%>
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
}}
%>