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
		ArrayList<Horario> horarios = new ArrayList<Horario>();
		
		if(request.getAttribute("horario") == null)
		{
			if(request.getAttribute("horarios") == null)
			{
				ControladorHorario ch = new ControladorHorario();
				horarios = ch.BuscarHorarios();
			}
			else
			{
				horarios = (ArrayList<Horario>) request.getAttribute("horarios");
			}
		}
		else
		{
			horarios.add((Horario) request.getAttribute("horario"));
		}
		
		
		ControladorSucursal cs = new ControladorSucursal();
		ArrayList<Sucursal> sucursales = cs.BuscarSucursales();
		
		
		ControladorDia cd = new ControladorDia();
		ArrayList<Dia> dias = cd.BuscarDias();
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
<title>Horarios</title>

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

<div class="alert alert-success alert-dismissible fade show modal" role="alert" id="modalExito" style="background-color: #83FF83; color: black;">
  			<%
				if(modal != null)
				{
					switch(modal)
					{
					case "horario_agregado":
						out.print("<h5 id='textoModalExito'>Horario agregado correctamente</h5>");
						break;
					case "horario_modificado":
						out.print("<h5 id='textoModalExito'>Horario modificado correctamente</h5>");
						break;
					case "horario_eliminado":
						out.print("<h5 id='textoModalExito'>Horario eliminado correctamente</h5>");
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
					case "horario_no_agregado":
						out.print("<h5 id='textoModalError'>El horario no pudo ser agregado</h5>");
						break;
					case "horario_no_modificado":
						out.print("<h5 id='textoModalError'>El horario no pudo ser modificado</h5>");
						break;
					case "horario_no_eliminado":
						out.print("<h5 id='textoModalError'>El horario no pudo ser eliminado</h5>");
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
						out.print("<h5 id='textoModalAdvertencia'>No existe sucursal: " + request.getAttribute("sucursalNoEncontrada") + "</h5>");
						break;
					}
				}
			%>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>

	<div class="modalContainer" id="modalAgregar">
		<div class="modal-content" id="modalContentAgregar">
			<h2 style="margin-bottom: 3%; text-align: center;">Agregar horario</h2>
			<div>
				<form action="ServletHorario" method="post" name="personas">
					<input type="hidden" name="instruccion" value="agregar_horario">
						
						<div class="row" style="margin-top: 2%;">
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
							<div class="col-2"><h5>Día</h5></div>
							<div class="col-10">
								<select class="form-control" name="nro_dia" id="nro_dia">
									<option selected disabled>Seleccione día</option>
							    	<%
							    		for(Dia d: dias)
										{%>
								  			<option value="<%=d.getNroDia()%>"><%=d.getDescripcion()%></option>
										<%} 
									%>
								</select>
							</div>
						</div>
						
						<div class="row" style="margin-top: 2%;">
							<div class="col-2"><h5>Apertura</h5></div>
							<div class="col-5">
								<select class="form-control" name="hora_desde" id="hora_desde">
									<option selected disabled>Seleccione hora desde</option>
							    	<%
							    		for(int i = 0; i < 24; i++)
										{%>
								  			<option value="<%if(i<10){out.print("0"+i);}else{out.print(i);}%>"><%if(i<10){out.print("0"+i);}else{out.print(i);}%></option>
										<%} 
									%>
								</select>
							</div>
							<div class="col-5">
								<select class="form-control" name="minuto_desde" id="minuto_desde">
									<option selected disabled>Seleccione minuto desde</option>
							    	<%
							    		for(int i = 0; i <= 45; i = i + 15)
										{%>
											<option value="<%if(i<10){out.print("0"+i);}else{out.print(i);}%>"><%if(i<10){out.print("0"+i);}else{out.print(i);}%></option>
										<%} 
									%>
								</select>
							</div>
						</div>
						
						<div class="row" style="margin-top: 2%;">
							<div class="col-2"><h5>Cierre</h5></div>
							<div class="col-5">	
								<select class="form-control" name="hora_hasta" id="hora_hasta">
									<option selected disabled>Seleccione hora hasta</option>
							    	<%
							    		for(int i = 0; i < 24; i++)
										{%>
								  			<option value="<%if(i<10){out.print("0"+i);}else{out.print(i);}%>"><%if(i<10){out.print("0"+i);}else{out.print(i);}%></option>
										<%} 
									%>
								</select>
							</div>
							<div class="col-5">	
								<select class="form-control" name="minuto_hasta" id="minuto_hasta">
									<option selected disabled>Seleccione minuto hasta</option>
							    	<%
							    		for(int i = 0; i <= 45; i = i + 15)
										{%>
								  			<option value="<%if(i<10){out.print("0"+i);}else{out.print(i);}%>"><%if(i<10){out.print("0"+i);}else{out.print(i);}%></option>
										<%} 
									%>
								</select>
							</div>
						</div>
					
					<div class="row" style="margin-top: 4%;">
						<div class="col-2" style="margin-left: auto;"><button type="button" class="btn btn-secondary" onclick="cerrarAgregar()">Cancelar</button></div>
						<div class="col-2" style="margin-right: auto;"><button type="submit" class="btn btn-success">Agregar</button></div>
					</div>
				</form>
			</div>
		</div>
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
		        <a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sucursales</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		      		<a class="dropdown-item" href="Sucursales.jsp">Ver todas las sucursales</a>
		      		<a class="dropdown-item" href="RegistrarSucursal.jsp">Registrar nueva sucursal</a>  
		      		<a style="background-color: orange" class="dropdown-item" href="Horarios.jsp">Horarios sucursales</a>  
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
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Clases personalizadas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="ClasesPersonalizadas.jsp">Ver clases personalizadas</a>
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sucursales</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		      		<a style="background-color: orange" class="dropdown-item" href="Horarios.jsp">Horarios sucursales</a>  
		        </div>
		      </li>
		    </ul>
		    <ul class="navbar-nav user">
			 	<li class="nav-item dropdown">
				  	<a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    	<i class="fas fa-user"></i>&nbsp;<%=userSesion.getNombreUsuario()%>
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
			<h2>Horarios</h2>&nbsp;
			<%if(userSesion.getNivelUsuario().getDescripcion().equals("administrador"))
			{%>
				<div style="background-color: #E3FBE3;"><button class="btn btn-outline-success" onclick="abrirAgregar()" style="margin-top: auto; margin-bottom: auto;">Agregar horario</button></div>
			<%}%>
			<div style="margin-left: auto">
				<form class="form-inline my-2 my-lg-0" action="ServletHorario" method="post" name="busqueda_por_nombre_sucursal">
					<input type="hidden" name="instruccion" value="buscar_por_nombre_sucursal">
			     	<input class="form-control mr-sm-2" type="search" placeholder="Buscar por sucursal" aria-label="Search" name="nombre_sucursal">
			     	<div style="background-color: #E3FBE3;"><button class="btn btn-outline-success my-2 my-sm-0" type="submit">Buscar</button></div>
		    	</form>
	    	</div>
		</nav>
		
		<div style="margin-top: 1%;">
			<% 
				String sucursalActual = "";
				String sucursalAnterior = "";
				int diaActual = 0;
				
				if(horarios != null)
				{
					for(Horario horario: horarios)
					{
						if(userSesion.getNivelUsuario().getDescripcion().equals("administrador"))
						{%>
							<form action="ServletHorario" method="post" name="horario">
							
							<input type="hidden" name="instruccion" value="editar_horario">
							<input type="hidden" name="nombre_sucursal" value="<%=horario.getSucursal().getNombreSucursal()%>">
							<input type="hidden" name="nro_dia" value="<%=horario.getDia().getNroDia()%>">
							<input type="hidden" name="id_hora_desde" value="<%=FormateoHora.getHoraEnFormatoBDD(horario.getHoraDesde())%>">
							
							<%if(!horario.getSucursal().getNombreSucursal().equals(sucursalActual) && sucursalActual.equals(""))
							{%>
								<div class="card">
								  <h5 class="card-header"><%out.print(horario.getSucursal().getNombreSucursal()+" - "+
										  							  horario.getSucursal().getDireccion()+" - "+
										  							  horario.getSucursal().getCiudad().getDescripcion()+" - "+
										  							  horario.getSucursal().getCiudad().getProvincia().getNombreProvincia());%></h5>
								  <div class="card-body">
							<%}
							
							if(!horario.getSucursal().getNombreSucursal().equals(sucursalActual) && !sucursalActual.equals(""))
							{%>
								</div>
								</div>
								<div class="card" style="margin-top: 1%;">
								  <h5 class="card-header"><%out.print(horario.getSucursal().getNombreSucursal()+" - "+
										  							  horario.getSucursal().getDireccion()+" - "+
										  							  horario.getSucursal().getCiudad().getDescripcion()+" - "+
										  							  horario.getSucursal().getCiudad().getProvincia().getNombreProvincia());%></h5>
								  <div class="card-body">
							<%}
							
							if(!horario.getSucursal().getNombreSucursal().equals(sucursalActual) || horario.getDia().getNroDia() != diaActual)
							{
								diaActual = horario.getDia().getNroDia();
								if(horario.getSucursal().getNombreSucursal().equals(sucursalActual))
								{%>
									<hr>
								<%}
								else
								{
									sucursalActual = horario.getSucursal().getNombreSucursal();
								}%>
								<h5 class="card-title"><%=horario.getDia().getDescripcion() %></h5>
							<%}%>
							
							<div class="card-text row" style="padding: 1%;">
								De
								<div class="col-2">
									<select class="form-control" name="hora_desde" id="hora_desde">
								    	<%
								    		for(int i = 0; i < 24; i++)
											{%>
									  			<option value="<%if(i<10){out.print("0"+i);}else{out.print(i);}%>"
												<%if(FormateoHora.getHoraInt(horario.getHoraDesde()) == i){%>selected<%}%>><%if(i<10){out.print("0"+i);}else{out.print(i);}%></option>
											<%} 
										%>
									</select>
								</div>
								:
								<div class="col-2">
									<select class="form-control" name="minuto_desde" id="minuto_desde">
								    	<%
								    		for(int i = 0; i <= 45; i = i + 15)
											{%>
									  			<option value="<%if(i<10){out.print("0"+i);}else{out.print(i);}%>"
												<%if(FormateoHora.getMinutoInt(horario.getHoraDesde()) == i){%>selected<%}%>><%if(i<10){out.print("0"+i);}else{out.print(i);}%></option>
											<%} 
										%>
									</select>
								</div>
								a
								<div class="col-2">	
									<select class="form-control" name="hora_hasta" id="hora_hasta">
								    	<%
								    		for(int i = 0; i < 24; i++)
											{%>
									  			<option value="<%if(i<10){out.print("0"+i);}else{out.print(i);}%>"
												<%if(FormateoHora.getHoraInt(horario.getHoraHasta()) == i){%>selected<%}%>><%if(i<10){out.print("0"+i);}else{out.print(i);}%></option>
											<%} 
										%>
									</select>
								</div>
								:
								<div class="col-2">	
									<select class="form-control" name="minuto_hasta" id="minuto_hasta">
								    	<%
								    		for(int i = 0; i <= 45; i = i + 15)
											{%>
									  			<option value="<%if(i<10){out.print("0"+i);}else{out.print(i);}%>"
												<%if(FormateoHora.getMinutoInt(horario.getHoraHasta()) == i){%>selected<%}%>><%if(i<10){out.print("0"+i);}else{out.print(i);}%></option>
											<%} 
										%>
									</select>
								</div>
								
								<div class="col-1">	
									<button type="submit" class="btn btn-success" id="btnGuardar" name="btnGuardar">Guardar</button>
								</div>
								<div class="col-1">	
									<button type="submit" class="btn btn-danger" name="btnEliminar">Eliminar</button>
								</div>
							</div>
						</form>
						<%}
						else if(userSesion.getNivelUsuario().getDescripcion().equals("usuario"))
						{
							if(!horario.getSucursal().getNombreSucursal().equals(sucursalActual) && sucursalActual.equals(""))
							{%>
								<div class="card">
								  <h5 class="card-header"><%out.print(horario.getSucursal().getNombreSucursal()+" - "+
										  							  horario.getSucursal().getDireccion()+" - "+
										  							  horario.getSucursal().getCiudad().getDescripcion()+" - "+
										  							  horario.getSucursal().getCiudad().getProvincia().getNombreProvincia());%></h5>
								  <div class="card-body">
							<%}
							
							if(!horario.getSucursal().getNombreSucursal().equals(sucursalActual) && !sucursalActual.equals(""))
							{%>
								</div>
								</div>
								<div class="card" style="margin-top: 1%;">
								  <h5 class="card-header"><%out.print(horario.getSucursal().getNombreSucursal()+" - "+
										  							  horario.getSucursal().getDireccion()+" - "+
										  							  horario.getSucursal().getCiudad().getDescripcion()+" - "+
										  							  horario.getSucursal().getCiudad().getProvincia().getNombreProvincia());%></h5>
								  <div class="card-body">
							<%}
							
							if(!horario.getSucursal().getNombreSucursal().equals(sucursalActual) || horario.getDia().getNroDia() != diaActual)
							{
								diaActual = horario.getDia().getNroDia();
								if(horario.getSucursal().getNombreSucursal().equals(sucursalActual))
								{%>
									<hr>
								<%}
								else
								{
									sucursalActual = horario.getSucursal().getNombreSucursal();
								}%>
								<h5 class="card-title"><%=horario.getDia().getDescripcion() %></h5>
							<%}%>
							
							<div class="card-text row" style="padding: 1%;">
								<h6><i class="fas fa-angle-right"></i>&nbsp;De&nbsp;<%=FormateoHora.getHora(horario.getHoraDesde())%>&nbsp;hs.&nbsp;</h6>
								<h6>a&nbsp;<%=FormateoHora.getHora(horario.getHoraHasta())%>&nbsp;hs.</h6>
							</div>
					 	<%}
					}%>
					</div>
					</div>
				<%}
			%>
		</div>
	
	<script type="text/javascript">
		var modalAgregar = document.getElementById('modalAgregar');
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
		
		function abrirAgregar() {
			modalAgregar.style.display = "block";
		}
		
		function cerrarAgregar() {
			modalAgregar.style.display = "none";
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