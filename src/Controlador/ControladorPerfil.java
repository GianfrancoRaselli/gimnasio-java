package Controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entidades.Ciudad;
import Entidades.Persona;
import Entidades.Usuario;
import Modelo.PersonaLogic;
import Modelo.SesionLogic;
import Modelo.UsuarioLogic;

@WebServlet("/ControladorPerfil")
public class ControladorPerfil extends HttpServlet 
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
		if(sesion.getAttribute("usuario") == null)
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
		else
		{
			try
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
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void EliminarUsuario(HttpServletRequest request, HttpServletResponse response)
	{
		Usuario user = new Usuario();
		user.setNombreUsuario(request.getParameter("nombre_usuario"));
		
		UsuarioLogic ul = new UsuarioLogic();
		if(ul.EliminarUsuario(user))
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
		else
		{
			
		}
	}
	
	public void EditarPerfil(HttpServletRequest request, HttpServletResponse response)
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

		if(pl.EditarPerfil(p) && ul.EditarPerfil(user))
		{
			request.setAttribute("modal", "perfil_modificado");
			
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
		else
		{
			request.setAttribute("modal", "perfil_no_modificado");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Perfil.jsp");
		try 
		{
			dispatcher.forward(request, response);
		} 
		catch (ServletException | IOException e) 
		{
			e.printStackTrace();
		}
	}
}
