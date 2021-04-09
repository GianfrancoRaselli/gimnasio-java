<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" session="true"%>
    
<%@ page import="Entidades.*, Controlador.*, Servlets.*"%>

	<% 
	try
	{
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		HttpSession sesion = request.getSession();
	
		if(sesion.getAttribute("usuario") != null)
		{
			Usuario userSesion = (Usuario)sesion.getAttribute("usuario");
			
			if(userSesion.getNivelUsuario().getDescripcion().equals("administrador"))
			{
				response.sendRedirect("Personas.jsp");
			}
			else if(userSesion.getNivelUsuario().getDescripcion().equals("usuario"))
			{
				response.sendRedirect("Inicio.jsp");
			}
		}
		else if(request.getAttribute("usuarioEncontrado") == null)
		{
			response.sendRedirect("Login.jsp");
		}
		else if(request.getAttribute("usuarioEncontrado") != null)
		{
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
<title>Recuperar contraseña</title>

<style type="text/css">
	* {
		box-sizing: border-box;
	}
	
	*:focus {
		outline: none;
	}
	
	body {
		font-family: Arial;
		background-color: #3498DB;
		padding: 50px;
	}
	
	.recover {
		margin: 20px auto;
		width: 350px;
	}
	
	.recover-screen {
		background-color: #FFF;
		padding: 20px;
		border-radius: 5px
	}
	
	.app-title {
		text-align: center;
		color: #777;
	}
	
	.recover-form {
		text-align: center;
	}
	
	.control-group {
		margin-bottom: 10px;
	}
	
	input {
		text-align: center;
		background-color: #ECF0F1;
		border: 2px solid transparent;
		border-radius: 3px;
		font-size: 16px;
		font-weight: 200;
		padding: 10px 0;
		width: 250px;
		transition: border .5s;
	}
	
	input:focus {
		border: 2px solid #3498DB;
		box-shadow: none;
	}
	
	.btn {
		border: 2px solid transparent;
		background: #3498DB;
		color: #ffffff;
		font-size: 16px;
  		line-height: 25px;
		padding: 10px 0;
		text-decoration: none;
		text-shadow: none;
	 	border-radius: 3px;
		box-shadow: none;
		transition: 0.25s;
		display: block;
		width: 250px;
		margin: 0 auto;
	}
	
	.btn:hover {	
		background-color: #2980B9;
	}
</style>

</head>

<body style="padding-right: 1%; padding-left: 1%;">

<% Usuario userEncontrado = (Usuario)request.getAttribute("usuarioEncontrado"); %>

		<form action="ServletRecuperarContrasenia" method="post" name="login">
			<input type="hidden" name="instruccion" value="ingresar_codigo">
			<input type="hidden" name="nombre_usuario" value="<%= userEncontrado.getNombreUsuario()%>">
			
			<div class="recover">
				<div class="recover-screen">
					<div class="app-title">
						<h3><strong>Recuperar contraseña</strong></h3>
					</div>
					
					<div style="text-align: center; margin-bottom: 3%;">
						 Ingrese el codigo enviado al correo: <%= userEncontrado.getPersona().getEmail() %>
					</div>
					
					<div style="text-align: center; margin-bottom: 3%;">
						 Advertencia: tiene un solo intento
					</div>
		
					<div class="recover-form">
						<div class="control-group">
							<input type="text" class="login-field" value="" placeholder="Código" id="login-name" name="codigoIngresado" required>
						</div>

						<button type="submit" class="btn btn-primary btn-large btn-block">Ingresar</button>
					</div>
					
					<div style="text-align: center; margin-top: 6%;">
						<a href="Login.jsp">Volver a iniciar sesión</a>
					</div>
				</div>
			</div>
		</form>

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