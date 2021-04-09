<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" session="true"%>

<%@ page import="Entidades.*, Controlador.*, Servlets.*, Util.*"%>

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
			
			if(userSesion.getNivelUsuario().getDescripcion().equals("usuario"))
			{
				response.sendRedirect("Inicio.jsp");
			}
			else if(userSesion.getNivelUsuario().getDescripcion().equals("administrador"))
			{
				response.sendRedirect("Inicio.jsp");
			}
		}
		else
		{
	%>

	<%
		String nombreUsuario = "";			
		String contrasenia = "";
			
		Cookie[] cookies = request.getCookies();
			
		if(cookies != null)
		{
			Boolean usuarioEncontrado = false;
			Boolean contraseniaEncontrada = false;
			
			for(Cookie cookieTemp: cookies)
			{
				if(("nombre_usuario".equals(cookieTemp.getName())) && (cookieTemp.getValue() != null))
				{
					nombreUsuario = cookieTemp.getValue(); 
					usuarioEncontrado = true;
				}
				if(("contrasenia".equals(cookieTemp.getName())) && (cookieTemp.getValue() != null))
				{
					contrasenia = cookieTemp.getValue(); 
					contraseniaEncontrada = true;
				}
				if(usuarioEncontrado && contraseniaEncontrada)	
				{
					break;
				}
			}	
			
			if((nombreUsuario != "") && (contrasenia != ""))
			{
				Usuario user = new Usuario();
				user.setNombreUsuario(nombreUsuario);
				user.setContraseniaEncriptada(contrasenia);
				
				ControladorSesion cl = new ControladorSesion();
				user = cl.IniciarSesion(user);
				
				if(user != null)
				{
					sesion.setAttribute("usuario", user);
							
					Cookie CookieNombreUsuario = new Cookie("nombre_usuario", user.getNombreUsuario());
					Cookie CookieContrasenia = new Cookie("contrasenia", user.getContraseniaEncriptada());
					CookieNombreUsuario.setMaxAge(60*60*24*30);
					CookieContrasenia.setMaxAge(60*60*24*30);
					response.addCookie(CookieNombreUsuario);
					response.addCookie(CookieContrasenia);
							
					response.sendRedirect("Inicio.jsp");
				}
				else
				{
					usuarioEncontrado = false;
					contraseniaEncontrada = false;
					for(Cookie cookieTemp: cookies)
					{
						if("nombre_usuario".equals(cookieTemp.getName()))
						{
							cookieTemp.setValue("");
							response.addCookie(cookieTemp);
							usuarioEncontrado = true;
						}
						if("contrasenia".equals(cookieTemp.getName()))
						{
							cookieTemp.setValue("");
							response.addCookie(cookieTemp);
							contraseniaEncontrada = true;
						}
						if(usuarioEncontrado && contraseniaEncontrada)	
						{
							break;
						}
					}
				}
			}
		}
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
<title>Inicio sesión</title>

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
	
	.login {
		margin: 20px auto;
		width: 300px;
	}
	
	.login-screen {
		background-color: #FFF;
		padding: 20px;
		border-radius: 5px
	}
	
	.app-title {
		text-align: center;
		color: #777;
	}
	
	.login-form {
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

		<form action="ServletSesion" method="post" name="login" onsubmit="return validar();">
			<input type="hidden" name="instruccion" value="iniciar_sesion">
			<div class="login">
				<div class="login-screen">
					<div class="app-title">
						<h1>Inicio sesión</h1>
					</div>
		
					<div class="login-form">
						<div class="control-group">
							<input type="text" class="login-field" value="" placeholder="Nombre de usuario" id="login-name" name="nombre_usuario" required minlength="6">
							<label class="login-field-icon fui-user" for="login-name" id="errorNombreUsuario" style="color: red; display: none">Mínimo 6 caracteres</label>
						</div>
		
						<div class="control-group">
							<input type="password" class="login-field" value="" placeholder="Contraseña" id="login-pass" name="contrasenia" required minlength="8">
							<label class="login-field-icon fui-lock" for="login-pass" id="errorContrasenia" style="color: red; display: none">Mínimo 8 caracteres</label>
						</div>
						
						<button type="submit" class="btn btn-primary btn-large btn-block">Iniciar sesión</button>
						
						<div style="margin-top: 4%;"><a href="RecuperarContrasenia.jsp">¿Olvidaste tu contraseña?</a></div>
					</div>
					
					<% 
						if(request.getAttribute("errorInicioSesion") != null)
						{%>
							<div id="errorInicioSesion" style="color: red; margin-top: 8%; text-align: center;">
								<p>Usuario y/o contraseña incorrectos</p>
							</div>							
						<%}
					%>
				</div>
			</div>
		</form>
		
	<script type="text/javascript">
		function validar()
		{
			var nombreUsuario = document.getElementById('login-name').value;
			var contrasenia = document.getElementById('login-pass').value;
			var errorNombreUsuario = document.getElementById('errorNombreUsuario');
			var errorContrasenia = document.getElementById('errorContrasenia');
			
			if(nombreUsuario.length < 6)
			{
				errorNombreUsuario.style.display = "block";
			}
			else
			{
				errorNombreUsuario.style.display = "none";
			}
			
			if(contrasenia.length < 8)
			{
				errorContrasenia.style.display = "block";
			}
			else
			{
				errorContrasenia.style.display = "none";
			}
			
			if(nombreUsuario.length < 6 || contrasenia.length < 8)
			{
				return false;
			}
			else
			{
				return true;
			}
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