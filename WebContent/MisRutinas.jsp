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
<title>Mis rutinas</title>

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
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Cuotas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="BuscarPersona.jsp">Pagar cuotas</a> 
					<a class="dropdown-item" href="MisCuotas.jsp">Mis cuotas</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Rutinas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="BuscarPersonaDeRutina.jsp">Registrar nueva rutina</a> 
					<a style="background-color: orange" class="dropdown-item" href="MisRutinas.jsp">Mis rutinas</a> 
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
		        <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Cuotas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="MisCuotas.jsp">Mis cuotas</a> 
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Rutinas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a style="background-color: orange" class="dropdown-item" href="MisRutinas.jsp">Mis rutinas</a> 
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
			<h2>Mis rutinas</h2>
		</nav>
		
     	<div style="margin-top: 1%;">
     	
     		<%
     			Rutina r = null;

	     		if((request.getAttribute("rutina") == null))
				{
					ControladorRutina cr = new ControladorRutina();
					r = cr.BuscarUltimaRutinaDelUsuario(userSesion);
				}
	     		else
	     		{
	     			r = (Rutina) request.getAttribute("rutina");
	     		}
	     		
	     		if(r != null)
				{
	     			ControladorRutina cr = new ControladorRutina();
	     			ArrayList<Rutina> rutinas = cr.BuscarRutinasDelUsuario(userSesion);
			%>
			
			<form action="ServletRutina" method="post" name="rutina" style="margin-bottom: 1%; padding-left: 1%;">
				<input type="hidden" name="instruccion" value="buscar_rutina">
				
				<div class="row">
					<select class="form-control col-4" id="fechaHora" name="fecha_hora">
				    	<% 	
							if(rutinas != null)
							{
								for(Rutina rut: rutinas)
								{%>						
							  		<option value="<%=FormateoHora.getFechaHoraEnFormatoBDD(rut.getFechaHora())%>"<%if(r.getFechaHora().equals(rut.getFechaHora())){%>selected<%}%>>
										<%=FormateoHora.getFechaHora(rut.getFechaHora())%>				
									</option>
								<% } 
							}%>
					</select>&nbsp;&nbsp;
					<button type="submit" class="btn btn-success col-2" id="btnAgregar">Buscar</button>
				</div>
			</form>
			
			<div class="card">
				<h5 class="card-header">
					<%=FormateoHora.getFechaHora(r.getFechaHora())%>				
				</h5>
							
				<div class="card-body">
					
				<% 
				ArrayList<Ejercicio> ejercicios = null;
				
				for(int i = 1; i <= 7; i++)
				{
					switch(i)
					{
						case 1:
							ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia1();
							break;
						case 2:
							ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia2();
							break;
						case 3:
							ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia3();
							break;
						case 4:
							ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia4();
							break;
						case 5:
							ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia5();
							break;
						case 6:
							ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia6();
							break;
						case 7:
							ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia7();
							break;
					}
				%>
				
					<h5 class="card-title">Día <%=i%></h5>
					
					
					<%if(!ejercicios.isEmpty())
					{%>
						<table class="tabla" style="width: 100%; text-align: center; margin-top: 1%;">
						  <tr style="background-color: #ABA6A5">
						    <th scope="col" style="border: 2px solid black">Orden</th>
						    <th scope="col" style="border: 2px solid black">Ejercicio</th>
						    <th scope="col" style="border: 2px solid black">Descripción</th>
						    <th scope="col" style="border: 2px solid black">Series</th>
						    <th scope="col" style="border: 2px solid black">Repeticiones</th>
						    <th scope="col" style="border: 2px solid black">Pesos</th>
						    <th scope="col" style="border: 2px solid black">Tiempo</th>
						  </tr>
						<%for(Ejercicio ejercicio: ejercicios)
						{%>	
							<tr style='background-color: #E3E3E3'>
						  	<td style='border: 2px solid black'><%=ejercicio.getOrden()%></td>
						  	<td style='border: 2px solid black'><%=ejercicio.getTipoEjercicio().getNombre()%></td>
						  	<td style='border: 2px solid black'><%if(ejercicio.getTipoEjercicio().getDescripcion()!=null){out.print(ejercicio.getTipoEjercicio().getDescripcion());}else{out.print("-");}%></td>
						  	<td style='border: 2px solid black'><%if(ejercicio.getSeries()!=null){out.print(ejercicio.getSeries());}else{out.print("-");}%></td>
						  	<td style='border: 2px solid black'><%if(ejercicio.getRepeticiones()!=null){out.print(ejercicio.getRepeticiones());}else{out.print("-");}%></td>
						  	<td style='border: 2px solid black'><%if(ejercicio.getPesos()!=null){out.print(ejercicio.getPesos());}else{out.print("-");}%></td>
						  	<td style='border: 2px solid black'><%if(ejercicio.getTiempo()!=null){out.print(ejercicio.getTiempo());}else{out.print("-");}%></td>
						  	</tr>
						<%}%>
						</table>
						<br>
					<%}
					else
					{%>
						<h6><i class="fas fa-angle-right"></i>&nbsp;No hay ejercicios</h6>
					<%}
				}%>
				</div>	
			</div>
			<%
			}
     		else
     		{%>
     			<h5><i class="fas fa-angle-right"></i>&nbsp;No tiene rutinas registradas</h5>
     		<%}
			%>
		</div>

	<script type="text/javascript">
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