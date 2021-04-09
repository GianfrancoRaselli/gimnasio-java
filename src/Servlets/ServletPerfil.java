package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entidades.*;
import Modelo.*;

@WebServlet("/ServletPerfil")
public class ServletPerfil extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			HttpSession sesion = request.getSession();
			
			if(sesion.getAttribute("usuario") != null)
			{
				response.sendRedirect("Inicio.jsp");
			}
			else
			{
				response.sendRedirect("Login.jsp");
			}
		}
		catch(Exception e)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Errores.jsp");
			request.setAttribute("exception", e);
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			HttpSession sesion = request.getSession();
			if(sesion.getAttribute("usuario") == null)
			{
				response.sendRedirect("Login.jsp");
			}
			else
			{
				String comando = request.getParameter("instruccion");
					
				switch(comando)
				{
				case "eliminar_usuario":
					EliminarUsuario(request, response);
					break;
				case "editar_perfil":
					EditarPerfil(request, response);
					break;
				}
			}
		}
		catch(Exception e)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Errores.jsp");
			request.setAttribute("exception", e);
			dispatcher.forward(request, response);
		}
	}
	
	public void EliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		Usuario user = new Usuario();
		user.setNombreUsuario(request.getParameter("nombre_usuario"));
		
		UsuarioLogic ul = new UsuarioLogic();
		
		ul.EliminarUsuario(user);
			
		HttpSession sesion = request.getSession();
		sesion.invalidate();
				
		Cookie[] cookies = request.getCookies();
		if(cookies != null)
		{
			Boolean usuarioEncontrado = false;
			Boolean contraseniaEncontrada = false;
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
	
		response.sendRedirect("Login.jsp");
	}
	
	public void EditarPerfil(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Usuario user = new Usuario();
		Persona p = new Persona();
		Ciudad ciudad = new Ciudad();
		UsuarioLogic ul = new UsuarioLogic();
		PersonaLogic pl = new PersonaLogic();
		
		user.setNombreUsuario(request.getParameter("usuario"));
		user.setContrasenia(request.getParameter("contrasenia"));
		user.setPersona(p);
		
		p.setDni(request.getParameter("dni"));
		p.setNombreApellido(request.getParameter("nombreApellido"));
		p.setTelefono(request.getParameter("telefono"));
		p.setEmail(request.getParameter("email"));
		p.setDireccion(request.getParameter("direccion"));
		
		ciudad.setCodPostal(request.getParameter("ciudad"));
		p.setCiudad(ciudad);

		pl.EditarPerfil(p);
		ul.EditarPerfil(user);
			
		request.setAttribute("modal", "perfil_modificado");
			
		SesionLogic login = new SesionLogic();
		user = login.InicioSesion(user);
				
		if(user != null)
		{
			HttpSession sesion = request.getSession();
			sesion.setAttribute("usuario", user);
					
			Cookie CookieNombreUsuario = new Cookie("nombre_usuario", user.getNombreUsuario());
			Cookie CookieContrasenia = new Cookie("contrasenia", user.getContraseniaEncriptada());
			CookieNombreUsuario.setMaxAge(60*60*24*30);
			CookieContrasenia.setMaxAge(60*60*24*30);
			response.addCookie(CookieNombreUsuario);
			response.addCookie(CookieContrasenia);
		}
		else
		{
			Exception excepcionManejada = new Exception("Error al iniciar sesión con los nuevos datos");
			throw excepcionManejada;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Perfil.jsp");
		dispatcher.forward(request, response);
	}
}