package Controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entidades.Usuario;
import Modelo.RecuperarContraseniaLogic;
import Modelo.SesionLogic;
import Modelo.UsuarioLogic;

@WebServlet("/ControladorRecuperarContrasenia")
public class ControladorRecuperarContrasenia extends HttpServlet 
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
		HttpSession sesion = request.getSession();
		
		if(sesion.getAttribute("usuario") != null)
		{
			Usuario user = (Usuario)sesion.getAttribute("usuario");
			
			try
			{
				if(user.getNivelUsuario().getDescripcion().equals("usuario"))
				{
					response.sendRedirect("Inicio.jsp");
				}
				else if(user.getNivelUsuario().getDescripcion().equals("administrador"))
				{
					response.sendRedirect("Personas.jsp");
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
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
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void RecuperarContrasenia(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession sesion = request.getSession();
		
		UsuarioLogic ul = new UsuarioLogic();
		Usuario user = new Usuario();
		user.setNombreUsuario(request.getParameter("nombre_usuario"));
		
		try
		{
			user = ul.BuscarUsuario(user); 
			
			if(user != null)
			{
				RecuperarContraseniaLogic rcl = new RecuperarContraseniaLogic();
				int cod = rcl.GenerarCodigo();
				
				if(rcl.EnviarCorreo(cod, user.getPersona().getEmail()))
				{
					sesion.setAttribute("codigo", cod);
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("IngresarCodigo.jsp");
					request.setAttribute("usuarioEncontrado", user);
					dispatcher.forward(request, response);					
				}
				else
				{
					RequestDispatcher dispatcher = request.getRequestDispatcher("RecuperarContrasenia.jsp");
					request.setAttribute("usuarioEncontrado", user);
					request.setAttribute("errorEnviarCorreo", "error al enviar correo");
					dispatcher.forward(request, response);
				}
			}
			else
			{
				RequestDispatcher dispatcher = request.getRequestDispatcher("RecuperarContrasenia.jsp");
				request.setAttribute("errorUsuarioNoEncontrado", "usuario no encontrado");
				dispatcher.forward(request, response);
			}
		}
		catch(Exception e)
		{
			sesion.setAttribute("codigo", null);
			e.printStackTrace();
		}
	}
	
	public void IngresoCodigo(HttpServletRequest request, HttpServletResponse response)
	{	
		HttpSession sesion = request.getSession();
		
		if(sesion.getAttribute("codigo") != null)
		{
			int cod = (int) sesion.getAttribute("codigo");
			
			try
			{
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
			catch(Exception e)
			{
				sesion.setAttribute("codigo", null);
				sesion.setAttribute("habilitadoCambiarContrasenia", false);
				e.printStackTrace();
			}
		}
		else
		{
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
	
	public void CambiarContrasenia(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession sesion = request.getSession();
		
		if(sesion.getAttribute("habilitadoCambiarContrasenia") != null && (boolean) sesion.getAttribute("habilitadoCambiarContrasenia"))
		{
			sesion.setAttribute("habilitadoCambiarContrasenia", false);
			
			Usuario user = new Usuario();
			user.setNombreUsuario(request.getParameter("nombre_usuario"));
			user.setContrasenia(request.getParameter("contrasenia"));
			
			UsuarioLogic ul = new UsuarioLogic();
			
			try
			{
				if(ul.CambiarContrasenia(user))
				{
					SesionLogic login = new SesionLogic();
					
					user = login.InicioSesion(user); 
					
					if(user != null)
					{
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
				else
				{
					RequestDispatcher dispatcher = request.getRequestDispatcher("RecuperarContrasenia.jsp");
					request.setAttribute("errorCambioContrasenia", "No se pudo cambiar la contrasenia");
					dispatcher.forward(request, response);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
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
}
