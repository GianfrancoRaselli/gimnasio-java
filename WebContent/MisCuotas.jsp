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
		
			ControladorCuota cc = new ControladorCuota();
			cc.ActualizarCuotas();
				
			ArrayList<Cuota> misCuotas = cc.BuscarCuotasDePersona(userSesion.getPersona());
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
<title>Mis cuotas</title>
	
</head>

<body style="background-color:#E1E1E1; padding-right: 1%; padding-left: 1%;"> 

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
		        <a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Cuotas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="BuscarPersona.jsp">Pagar cuotas</a> 
					<a style="background-color: orange" class="dropdown-item" href="MisCuotas.jsp">Mis cuotas</a> 
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
		        <a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Cuotas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a style="background-color: orange" class="dropdown-item" href="MisCuotas.jsp">Mis cuotas</a> 
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
		<%}%>
			
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<h2>Mis cuotas</h2>
		</nav>
		
		<div style="margin-top: 1%;">	
			<% 
				if(misCuotas != null)
				{
					if(!misCuotas.isEmpty())
					{
						for(Cuota c: misCuotas)
						{%>
							<div class="card" style="margin-bottom: 1%;">
							  <h5 class="card-header">Año:&nbsp;<%=c.getAnio()%>&nbsp;-&nbsp;Mes:&nbsp;<%=c.getMes()%></h5>
							  <div class="card-body">
							    <p class="card-text">Total:&nbsp;$<%=c.getTotal()%></p>
							    <%if(c.getFechaHoraPago() != null)
							    {%>
							    	<div class="alert alert-success" role="alert">
									  Pagada
									</div>
								<%}
								else
								{%>
									<div class="alert alert-warning" role="alert">
									  Sin pagar
									</div>
								<%}%>
							  </div>
							</div>
						<%}
					}
					else
					{%>
						<h5><i class="fas fa-angle-right"></i>&nbsp;No se ha registrado ninguna cuota</h5>
					<%}
				}
			%>
		</div>

		<script type="text/javascript">
		var modalError = document.getElementById('modalError');
		var textoModalError = document.getElementById('textoModalError');
		
		if(textoModalError != null)
		{
			modalError.style.display = "block";
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