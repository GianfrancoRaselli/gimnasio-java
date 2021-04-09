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

@WebServlet("/ServletRecuperarContrasenia")
public class ServletRecuperarContrasenia extends HttpServlet 
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
				String comando = request.getParameter("instruccion");
					
				switch(comando)
				{
					case "recuperar_contrasenia":
						RecuperarContrasenia(request, response);
						break;
					case "ingresar_codigo":
						IngresoCodigo(request, response);
						break;
					case "cambio_contrasenia":
						CambiarContrasenia(request, response);
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
	
	public void RecuperarContrasenia(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession sesion = request.getSession();
		
		UsuarioLogic ul = new UsuarioLogic();
		Usuario user = new Usuario();
		user.setNombreUsuario(request.getParameter("nombre_usuario"));
		
		user = ul.BuscarUsuario(user); 
			
		if(user != null)
		{
			RecuperarContraseniaLogic rcl = new RecuperarContraseniaLogic();
			int cod = rcl.GenerarCodigo();
				
			rcl.EnviarCorreo(cod, user.getPersona().getEmail());
					
			sesion.setAttribute("codigo", cod);
					
			RequestDispatcher dispatcher = request.getRequestDispatcher("IngresarCodigo.jsp");
			request.setAttribute("usuarioEncontrado", user);
			dispatcher.forward(request, response);
		}
		else
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("RecuperarContrasenia.jsp");
			request.setAttribute("errorUsuarioNoEncontrado", "usuario no encontrado");
			dispatcher.forward(request, response);
		}
	}
	
	public void IngresoCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		HttpSession sesion = request.getSession();
		
		if(sesion.getAttribute("codigo") != null)
		{
			int cod = (int) sesion.getAttribute("codigo");
			
			if(cod == (Integer.parseInt(request.getParameter("codigoIngresado"))))
			{
				sesion.setAttribute("codigo", null);
				sesion.setAttribute("habilitadoCambiarContrasenia", true);
					
				RequestDispatcher dispatcher = request.getRequestDispatcher("CambioContrasenia.jsp");
				request.setAttribute("nombreUsuario", request.getParameter("nombre_usuario"));
				dispatcher.forward(request, response);
			}
			else
			{
				sesion.setAttribute("codigo", null);
					
				RequestDispatcher dispatcher = request.getRequestDispatcher("RecuperarContrasenia.jsp");
				request.setAttribute("codigoIncorrecto", "codigoIncorrecto");
				dispatcher.forward(request, response);
			}
		}
		else
		{
			response.sendRedirect("Login.jsp");
		}
	}
	
	public void CambiarContrasenia(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession sesion = request.getSession();
		
		if(sesion.getAttribute("habilitadoCambiarContrasenia") != null && (boolean) sesion.getAttribute("habilitadoCambiarContrasenia"))
		{
			sesion.setAttribute("habilitadoCambiarContrasenia", false);
			
			Usuario user = new Usuario();
			user.setNombreUsuario(request.getParameter("nombre_usuario"));
			user.setContrasenia(request.getParameter("contrasenia"));
			
			UsuarioLogic ul = new UsuarioLogic();
			ul.CambiarContrasenia(user);
			
			SesionLogic login = new SesionLogic();	
			user = login.InicioSesion(user);

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
			response.sendRedirect("Login.jsp");
		}
	}
}