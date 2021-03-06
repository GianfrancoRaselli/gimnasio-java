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
			
			if(userSesion.getNivelUsuario().getDescripcion().equals("usuario"))
			{
				response.sendRedirect("Login.jsp"); 
			}
			else if(userSesion.getNivelUsuario().getDescripcion().equals("administrador"))
			{
				if(request.getAttribute("rutina") == null)
				{
					response.sendRedirect("BuscarPersonaDeRutina.jsp");
				}
				else
				{
					Rutina r = (Rutina) request.getAttribute("rutina");
					
					if(request.getAttribute("modo")==null)
					{
						response.sendRedirect("BuscarPersonaDeRutina.jsp");
					}
					else
					{
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
<title>Registrar rutina</title>

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

<div class="alert alert-danger alert-dismissible fade show modal" role="alert" id="modalError">
  			<%
				if(modal != null)
				{
					switch(modal)
					{
					case "rutina_no_agregada":
						out.print("<h5 id='textoModalError'>La rutina no pudo ser agregada</h5>");
						break;
					case "ejercicio_no_agregado":
						out.print("<h5 id='textoModalError'>El ejercicio no pudo ser agregado</h5>");
						break;
					case "ejercicio_no_eliminado":
						out.print("<h5 id='textoModalError'>El ejercicio no pudo ser eliminado</h5>");
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
					case "no_hay_ejercicios":
						out.print("<h5 id='textoModalAdvertencia'>Agregue ejercicios</h5>");
						break;
					}
				}
			%>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
	
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
					<a style="background-color: orange" class="dropdown-item" href="BuscarPersonaDeRutina.jsp">Registrar nueva rutina</a> 
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
							<button style="color: red;" class="dropdown-item" type="submit"><i class="fas fa-sign-out-alt"></i>&nbsp;Cerrar sesi??n</button>
						</form> 
				  	</div>
				</li>
			</ul>
		  </div>
		</nav>
		
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<h2>Registrar rutina</h2>
			<h4 style="margin-left: auto"><%=r.getPersona().getNombreApellido()%>, Dni: <%=r.getPersona().getDni()%></h4>
		</nav>
		
		<%
		if(request.getAttribute("modo")=="agregarEjercicios")
		{
		%>
		
		<%
			ControladorTipoEjercicio cte = new ControladorTipoEjercicio();
			ArrayList<TipoEjercicio> tiposEjercicios = cte.BuscarTiposEjercicios();
		%>
		
		<h4 style="margin: 1%;"><i class="fas fa-info-circle"></i>&nbsp;Instrucci??n: Agregue los ejercicios que desea y luego guarde la rutina</h4>

		<div style="border-radius: 15px; border: 2px solid black; background-color: #CFCFCF; margin-top: 1%; padding: 2%">
			<form action="ServletRegistrarRutina" method="post" name="Registro">
					<input type="hidden" name="instruccion" value="agregar_ejercicio">
	
					<div class="form-group row">
						<div class="col-3">
							<label for="Ejercicio"><h5>Ejercicio</h5></label>
						</div>
						<div class="col-9">
							<select class="form-control" id="Ejercicio" name="tipo_ejercicio">
							<option selected disabled>Seleccione un ejercicio</option>
							<% 	
								if(tiposEjercicios != null)
								{
									for(TipoEjercicio tiporEjercicio: tiposEjercicios)
									{%>
									  	<option value="<%=tiporEjercicio.getCodTipoEjercicio()%>"><%=tiporEjercicio.getNombre()%></option>
									<%} 
								}
							%>
							</select>
						</div>
					</div>			
			
			
					<div class="form-group row">
						<div class="col-3">
							<label for="Tipo"><h5>Tipo</h5></label>
						</div>
						<div class="col-9">
						   	<select class="form-control tipo" id="Tipo" name="tipo">
						   		<option selected disabled>Seleccione un tipo</option>
						    	<option value="1">Repetici??n</option>
						    	<option value="2">Tiempo</option>
							</select>
						</div>
					</div>
			
					<div class="form-group row">
						<div class="col-3">
							<label for="dia" class="col-form-label"><h5>Dia</h5></label>
						</div>
						<div class="col-9">
						   	<select class="form-control" id="dia" name="dia">
						   		<option selected disabled>Seleccione un d??a</option>
						    	<option value="1">D??a 1</option>
						    	<option value="2">D??a 2</option>
						    	<option value="3">D??a 3</option>
						    	<option value="4">D??a 4</option>
						    	<option value="5">D??a 5</option>
						    	<option value="6">D??a 6</option>
						    	<option value="7">D??a 7</option>
							</select>
						</div>
					</div>	
		
					<div class="form-group row">
						<div class="col-3">
							<label for="series" class="col-form-label"><h5>Series</h5></label>
						</div>
						<div class="col-9">
						   	<input type="text" class="form-control" id="series" name="series" disabled>
						</div>
					</div>			
				
				
					<div class="form-group row">
						<div class="col-3">
							<label for="repeticiones" class="col-form-label"><h5>Repeticiones</h5></label>
						</div>
						<div class="col-9">
						   	<input type="text" class="form-control" id="repeticiones" name="repeticiones" disabled>
						</div>
					</div>
			
			
					<div class="form-group row">
						<div class="col-3">
							<label for="pesos" class="col-form-label"><h5>Pesos</h5></label>
						</div>
						<div class="col-9">
						   	<input type="text" class="form-control" id="pesos" name="pesos" disabled>
						</div>
					</div>


					<div class="form-group row">
						<div class="col-3">
							<label for="tiempo" class="col-form-label"><h5>Tiempo</h5></label>
						</div>
						<div class="col-9">
						   	<input type="text" class="form-control" id="tiempo" name="tiempo" disabled>
						</div>
					</div>
			
			
					<div class="row">
						<div>
							<button type="submit" disabled class="btn btn-success" id="btnAgregar">Agregar ejercicio</button>&nbsp;
						</div>		
						
						<div>
							<button type="reset" class="btn btn-danger" id="btnRestablecer" onclick="deshabilitar()">Restablecer valores</button>
						</div>		
					</div>	
			</form>	
     	</div>
     	
     	<%}%>
     	
     	<div style="margin-top: 1%;">
     	
			<div class="card">
				<h5 class="card-header"><%=FormateoHora.getFechaActual()%>
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
				
					<h5 class="card-title">D??a <%=i%></h5>
					
					
					<%if(!ejercicios.isEmpty())
					{%>
						<table class="tabla" style="width: 100%; text-align: center; margin-top: 1%;">
						  <tr style="background-color: #ABA6A5">
						    <th scope="col" style="border: 2px solid black">Orden</th>
						    <th scope="col" style="border: 2px solid black">Ejercicio</th>
						    <th scope="col" style="border: 2px solid black">Descripci??n</th>
						    <th scope="col" style="border: 2px solid black">Series</th>
						    <th scope="col" style="border: 2px solid black">Repeticiones</th>
						    <th scope="col" style="border: 2px solid black">Pesos</th>
						    <th scope="col" style="border: 2px solid black">Tiempo</th>
						    <%
						  	if(request.getAttribute("modo")=="agregarEjercicios")
							{%>
						    <th scope="col" style="border: 2px solid black">Acci??n</th>
						    <%}%>
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
						  	<%
						  	if(request.getAttribute("modo")=="agregarEjercicios")
							{%>
						  	<td style='border: 2px solid black'>
									<form action="ServletRegistrarRutina" method="post" name="rutina">
										<input type="hidden" name="instruccion" value="eliminar_ejercicio">
										<input type="hidden" name="dia" value="<%=ejercicio.getNroDia()%>">
										<input type="hidden" name="orden" value="<%=ejercicio.getOrden()%>">
										<button type="submit" class="btn btn-danger btn-sm" style="width: 70px; color: white">Eliminar</button></div>										
									</form>
							</td>
							<%}%>
						  	</tr>
						<%}%>
						</table>
						<br>
					<%}
					else
					{%>
						<p>No hay ejercicios</p>
					<%}
				}
				
				if(request.getAttribute("modo")=="agregarEjercicios")
				{%>
					<hr>
					<form action="ServletRegistrarRutina" method="post" name="rutina">
						<input type="hidden" name="instruccion" value="registrar_rutina">
						<button class="btn btn-success" type="submit">Guardar rutina</button>
					</form> 
				<%}%>
				</div>	
			</div>
		</div>

	<script type="text/javascript">
		var selectElement = document.querySelector('.tipo');
	
		selectElement.addEventListener('change', (event) => {
		    if(event.target.value == "1")
		    {
		    	document.getElementById('series').disabled = false;
		    	document.getElementById('repeticiones').disabled = false;
		    	document.getElementById('pesos').disabled = false;
		    	document.getElementById('tiempo').disabled = true;
		    }
		    else if(event.target.value == "2")
		    {
		    	document.getElementById('tiempo').disabled = false;
		    	document.getElementById('series').disabled = true;
		    	document.getElementById('repeticiones').disabled = true;
		    	document.getElementById('pesos').disabled = true;
		    }
		    
		    document.getElementById('btnAgregar').disabled = false;
		});	
		
		function deshabilitar() {
			document.getElementById('btnAgregar').disabled = true;
			document.getElementById('tiempo').disabled = true;
	    	document.getElementById('series').disabled = true;
	    	document.getElementById('repeticiones').disabled = true;
	    	document.getElementById('pesos').disabled = true;
		}
		
		var modalError = document.getElementById('modalError');
		var modalAdvertencia = document.getElementById('modalAdvertencia');
		var textoModalError = document.getElementById('textoModalError');
		var textoModalAdvertencia = document.getElementById('textoModalAdvertencia');
		
		if(textoModalError != null)
		{
			modalError.style.display = "block";
		}
		
		if(textoModalAdvertencia != null)
		{
			modalAdvertencia.style.display = "block";
		}
	</script>
  	<script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
	<script type="text/javascript" src="js/popper.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>
<%}}}}}
catch(Exception e)
{
	RequestDispatcher dispatcher = request.getRequestDispatcher("Errores.jsp");
	request.setAttribute("exception", e);
	dispatcher.forward(request, response);
}%>