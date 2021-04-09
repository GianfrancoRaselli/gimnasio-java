<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" session="true"%>
    
<%@ page import="java.util.*, Entidades.*, Controlador.*, Servlets.*, Util.*"%>

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

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<meta name="author" content="">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maxium-scale=1.0, minimum-scale=1.0">
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"> 
<script src="https://kit.fontawesome.com/5520773c7b.js" crossorigin="anonymous"></script>
<title>Clases personalizadas</title>
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
</style>
</head>

<body style="background-color:#E1E1E1; padding-right: 1%; padding-left: 1%;">

<%
	String modal = (String)request.getAttribute("modal");
%>

<div class="alert alert-danger alert-dismissible fade show modal" role="alert" id="modalError">
  			<%
				if(modal != null)
				{
					switch(modal)
					{
					case "periodo_cerrado":
						out.print("<h5 id='textoModalError'>El período de inscripción ya terminó</h5>");
						break;
					case "cupos_completos":
						out.print("<h5 id='textoModalError'>No quedan cupos disponibles en esta clase personalizada</h5>");
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
		        <a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Clases personalizadas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		      		<a class="dropdown-item" href="Asistencias.jsp">Registrar asistencias</a>
					<a style="background-color: orange" class="dropdown-item" href="ClasesPersonalizadas.jsp">Ver clases personalizadas</a>
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
		        <a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Clases personalizadas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a style="background-color: orange" class="dropdown-item" href="ClasesPersonalizadas.jsp">Ver clases personalizadas</a>
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
			<h2>Clases personalizadas</h2>
		</nav>
		
			<%
			ControladorTipoClasePersonalizada ctcp = new ControladorTipoClasePersonalizada();
			ArrayList<TipoClasePersonalizada> tiposClasesPersonalizadas = ctcp.BuscarTiposClasesPersonalizadas();
			%>
			
			<% 
			ControladorSucursal cs = new ControladorSucursal();
			ArrayList<Sucursal> sucursales = cs.BuscarSucursales();
			%>
		
     	<div style="padding: 1%;">
		
		<div class="row">
			
			<div class="col-5">
			<form action="ServletClasePersonalizada" method="post" name="busqueda">
				<input type='hidden' name='instruccion' value='buscar_clases_personalizadas'>
				
				<div class="form-group">
					<label><h5>Año</h5></label>
	    			<input type="text" class="form-control" placeholder="Ej: 1990" name="anio" required value="">
 
					<label style="margin-top: 5%;"><h5>Mes</h5></label>
				    <select class="form-control" required name="mes">
				    	<option value="1">Enero</option>
				    	<option value="2">Febrero</option>
				    	<option value="3">Marzo</option>
				    	<option value="4">Abril</option>
				    	<option value="5">Mayo</option>
				    	<option value="6">Junio</option>
				    	<option value="7">Julio</option>
				    	<option value="8">Agosto</option>
				    	<option value="9">Septiembre</option>
				    	<option value="10">Octubre</option>
				    	<option value="11">Noviembre</option>
				    	<option value="12">Diciembre</option>
					</select>

					<label style="margin-top: 5%;"><h5>Sucursal</h5></label>
				    <select class="form-control" required name="sucursal">
				    	<option selected value="todas">Todas las sucursales</option>
				    	<% 	
						if(sucursales != null)
						{
							for(Sucursal sucursal: sucursales)
							{%>
					  			<option value="<%=sucursal.getNombreSucursal()%>"><%=sucursal.getNombreSucursal()%></option>
							<% } 
						}%>
					</select>

					<label style="margin-top: 5%;"><h5>Tipo clase personalizada</h5></label>
				    <select class="form-control" required name="tipo_clase_personalizada">
				    	<option selected value="todos">Todos los tipos de clases personalizadas</option>
				    	<% 	
						if(tiposClasesPersonalizadas != null)
						{
							for(TipoClasePersonalizada tipoClasePersonalizada: tiposClasesPersonalizadas)
							{%>
					  			<option value="<%=tipoClasePersonalizada.getCodTipoClasePersonalizada()%>"><%=tipoClasePersonalizada.getNombre()%></option>
							<%} 
						}%>
					</select>
					
					<button style="margin-top: 5%;" type="submit" class="btn btn-primary btn-large btn-block">Buscar</button>
			  	</div>
			</form>
			</div>
			
			<div class="col-7" style="border-left: 2px black solid;">
			
			<% 
				ArrayList<ClasePersonalizada> clasesPersonalizadas = (ArrayList<ClasePersonalizada>) request.getAttribute("clasesPersonalizadas");
			
				if(clasesPersonalizadas != null)
				{
					if(!clasesPersonalizadas.isEmpty())
					{
						for(ClasePersonalizada cp: clasesPersonalizadas)
						{%>
							<div class="card" style="margin-bottom: 1%;">
							  <h5 class="card-header"><%=cp.getTipoClasePersonalizada().getNombre()%></h5>
							  <div class="card-body">
							    <p class="card-text">Sucursal:&nbsp;<%=cp.getSucursal().getNombreSucursal()%></p>
							    <p class="card-text">Fecha y hora desde:&nbsp;<%=FormateoHora.getFechaHora(cp.getFechaHoraDesde())%></p>
							    <p class="card-text">Fecha y hora hasta:&nbsp;<%=FormateoHora.getFechaHora(cp.getFechaHoraHasta())%></p>
							    <%if(cp.getTipoClasePersonalizada().getDescripcion()!=null){%><p class="card-text">Descripción:&nbsp;<%=cp.getTipoClasePersonalizada().getDescripcion()%></p><%}%>
							    <p class="card-text">Precio:&nbsp;$<%=cp.getTipoClasePersonalizada().getPrecioPorClase()%></p>
							    <p class="card-text">Capacidad:&nbsp;<%=cp.getCapacidadMaxima()%></p>
							    <%if(cp.isPeriodoHabilitado())
							    {%>
							    	<%if(cp.isUsuarioInscripto())
							    	{%>
								    	<p class="card-text"><b>Inscripto</b></p>
								    	<form action="ServletClasePersonalizada" method="post" name="inscribirse">
								    		<input type='hidden' name='instruccion' value='inscribirse'>
								    		<input type='hidden' name='sucursal' value='<%=cp.getSucursal().getNombreSucursal()%>'>
								    		<input type='hidden' name='fecha_hora_desde' value='<%=FormateoHora.getFechaHoraEnFormatoBDD(cp.getFechaHoraDesde())%>'>
								    		<button type="submit" class="btn btn-danger btn-block">Darse de baja</button>
								    	</form>
							    	<%}
							    	else
							    	{%>
							    		<form action="ServletClasePersonalizada" method="post" name="inscribirse">
								    		<input type='hidden' name='instruccion' value='inscribirse'>
								    		<input type='hidden' name='sucursal' value='<%=cp.getSucursal().getNombreSucursal()%>'>
								    		<input type='hidden' name='fecha_hora_desde' value='<%=FormateoHora.getFechaHoraEnFormatoBDD(cp.getFechaHoraDesde())%>'>
								    		<button type="submit" class="btn btn-success btn-large btn-block">Inscribirse</button>
								    	</form>
							    	<%}
							    }%>
							  </div>
							</div>
						<%}		
					}
					else
					{%>
						<h5><i class="fas fa-angle-right"></i>&nbsp;Cambie los parámetros de búsqueda</h5>
					<%}
				}
				else
				{%>
					<h5><i class="fas fa-angle-right"></i>&nbsp;Cambie los parámetros de búsqueda</h5>
				<%}%>
			
			</div>
			
			</div>
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