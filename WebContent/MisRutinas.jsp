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
<title>Mis rutinas</title>

<style type="text/css">
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
		        <a style="color: orange" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Rutinas</a>
		      	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					<a style="background-color: orange" class="dropdown-item" href="RegistrarRutina.jsp">Registrar nueva rutina</a> 
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
			<h2>Mis rutinas</h2>
		</nav>
		
     	<div style="margin-top: 1%;">
     	
     		<%
     		if((request.getAttribute("rutina") == null))
			{
				ControladorRutina cr = new ControladorRutina();
				cr.BuscarUltimaRutinaDelUsuario(request, response);
			}
     		
     		if(request.getAttribute("rutina") != null)
			{
     			Rutina r = (Rutina) request.getAttribute("rutina");
     			
     			ControladorRutina cr = new ControladorRutina();
				cr.BuscarRutinasDelUsuario(request, response);
     			ArrayList<Rutina> rutinas = (ArrayList<Rutina>) request.getAttribute("rutinas");
			%>
			
			<form action="ControladorRutina" method="post" name="rutina" style="margin-bottom: 1%; padding-left: 1%;">
				<input type="hidden" name="instruccion" value="buscar_rutina">
				
				<div class="row">
					<select class="form-control col-4" id="fechaHora" name="fecha_hora">
				    	<% 	
							if(rutinas != null)
							{
								for(Rutina rut: rutinas)
								{
									String anio = String.valueOf(rut.getFecha().getYear()+1900); 
								
									String mes;
									if(rut.getFecha().getMonth()+1 < 10)
									{
										mes = "0" + String.valueOf(rut.getFecha().getMonth()+1);
									}
									else
									{
										mes = String.valueOf(rut.getFecha().getMonth()+1);
									}
									
									String dia;
									if(rut.getFecha().getDate() < 10)
									{
										dia = "0" + String.valueOf(rut.getFecha().getDate());
									}
									else
									{
										dia = String.valueOf(rut.getFecha().getDate());
									}
									
									String hora;
									if(rut.getHora().getHours()+3 < 10)
									{
										hora = "0" + String.valueOf(rut.getHora().getHours()+3);
									}
									else
									{
										hora = String.valueOf(rut.getHora().getHours()+3);
									}
									
									String min;
									if(rut.getHora().getMinutes() < 10)
									{
										min = "0" + String.valueOf(rut.getHora().getMinutes());
									}
									else
									{
										min = String.valueOf(rut.getHora().getMinutes());
									}
									
									String seg;
									if(rut.getHora().getSeconds() < 10)
									{
										seg = "0" + String.valueOf(rut.getHora().getSeconds());
									}
									else
									{
										seg = String.valueOf(rut.getHora().getSeconds());
									}
									
									String clave = anio + mes + dia + hora + min + seg;%>
														
							  		<option value="<%=clave%>" <%if((r.getFecha().equals(rut.getFecha())) && (r.getHora().equals(rut.getHora()))){%>selected<%}%>>
										<%if(rut.getFecha().getDate()<10){%>0<%}%><%=rut.getFecha().getDate()%>/<%if(rut.getFecha().getMonth()+1<10){%>0<%}%><%=rut.getFecha().getMonth()+1%>/<%=rut.getFecha().getYear()+1900%>&nbsp;-
										<%if(rut.getHora().getHours()+3<10){%>0<%}%><%=rut.getHora().getHours()+3%>:<%if(rut.getHora().getMinutes()<10){%>0<%}%><%=rut.getHora().getMinutes()%>:<%if(rut.getHora().getSeconds()<10){%>0<%}%><%=rut.getHora().getSeconds()%>				
									</option>
								<% } 
							}%>
					</select>&nbsp;&nbsp;
					<button type="submit" class="btn btn-success col-2" id="btnAgregar">Buscar</button>
				</div>
			</form>
			
			<div class="card">
				<h5 class="card-header">
					<%if(r.getFecha().getDate()<10){%>0<%}%><%=r.getFecha().getDate()%>/<%if(r.getFecha().getMonth()+1<10){%>0<%}%><%=r.getFecha().getMonth()+1%>/<%=r.getFecha().getYear()+1900%>&nbsp;-
					<%if(r.getHora().getHours()+3<10){%>0<%}%><%=r.getHora().getHours()+3%>:<%if(r.getHora().getMinutes()<10){%>0<%}%><%=r.getHora().getMinutes()%>:<%if(r.getHora().getSeconds()<10){%>0<%}%><%=r.getHora().getSeconds()%>				
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
						<p>No hay ejercicios</p>
					<%}
				}%>
				</div>	
			</div>
			<%
			}
     		else
     		{%>
     			<p>No tiene rutinas registradas</p>
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
<% 
}
%>