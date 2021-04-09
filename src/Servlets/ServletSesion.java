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
import Modelo.SesionLogic;

@WebServlet("/ServletSesion")
public class ServletSesion extends HttpServlet 
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
			String comando = request.getParameter("instruccion");
				
			switch(comando)
			{
				case "iniciar_sesion":
					IniciarSesion(request, response);
					break;
				case "cerrar_sesion":
					CerrarSesion(request, response);
					break;
			}
		}
		catch(Exception e)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Errores.jsp");
			request.setAttribute("exception", e);
			dispatcher.forward(request, response);
		}
	}
	
	public void IniciarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		Usuario user = new Usuario();
		user.setNombreUsuario(request.getParameter("nombre_usuario"));
		user.setContrasenia(request.getParameter("contrasenia"));
		
		try
		{
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
				
				response.sendRedirect("Inicio.jsp");
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("Errores.jsp");
			request.setAttribute("exception", e);
			dispatcher.forward(request, response);
		}
	}
	
	public void CerrarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException
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

		response.sendRedirect("Login.jsp");
	}
}