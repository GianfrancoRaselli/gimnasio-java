package Controlador;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Modelo.*;
import Entidades.*;

@WebServlet("/ControladorSesion")
public class ControladorSesion extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession sesion = request.getSession();
		
		try
		{
			if(sesion.getAttribute("usuario") != null)
			{
				Usuario user = (Usuario)sesion.getAttribute("usuario");
				
				if(user.getNivelUsuario().getDescripcion().equals("usuario"))
				{
					response.sendRedirect("Inicio.jsp");
				}
				else if(user.getNivelUsuario().getDescripcion().equals("administrador"))
				{
					response.sendRedirect("Personas.jsp");
				}
			}
			else
			{
				response.sendRedirect("Login.jsp");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String comando = request.getParameter("instruccion");
			
			switch(comando)
			{
				case "iniciar_sesion":
					Usuario u = new Usuario();
					u.setNombreUsuario(request.getParameter("nombre_usuario"));
					u.setContrasenia(request.getParameter("contrasenia"));
						
					IniciarSesion(request, response, u);
					break;
				case "cerrar_sesion":
					CerrarSesion(request, response);
					break;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void IniciarSesion(HttpServletRequest request, HttpServletResponse response, Usuario user)
	{
		SesionLogic login = new SesionLogic();
		
		try
		{
			user = login.InicioSesion(user); 
			
			if(user != null)
			{
				HttpSession sesion = request.getSession();
				sesion.setAttribute("usuario", user);
				
				Cookie CookieNombreUsuario = new Cookie("nombre_usuario", user.getNombreUsuario());
				Cookie CookieContrasenia = new Cookie("contrasenia", user.getContrasenia());
				CookieNombreUsuario.setMaxAge(60*60*24*30);
				CookieContrasenia.setMaxAge(60*60*24*30);
				response.addCookie(CookieNombreUsuario);
				response.addCookie(CookieContrasenia);
				
				if(user.getNivelUsuario().getDescripcion().equals("usuario"))
				{
					response.sendRedirect("Inicio.jsp");
				}
				else if(user.getNivelUsuario().getDescripcion().equals("administrador"))
				{
					response.sendRedirect("Personas.jsp");
				}
			}
			else
			{
				RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
				request.setAttribute("errorInicioSesion", "usuario no encontrado");
				dispatcher.forward(request, response);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void CerrarSesion(HttpServletRequest request, HttpServletResponse response)
	{
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

		try 
		{
			response.sendRedirect("Login.jsp");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
