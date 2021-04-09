package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entidades.*;
import Modelo.*;

@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
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
				case "buscar_por_dni":
					BuscarPorDni(request, response);
					break;
				case "buscar_usuario":
					BuscarUsuario(request, response);
					break;
				case "eliminar_usuario":
					EliminarUsuario(request, response);
					break;
				case "editar_usuario":
					EditarUsuario(request, response);
					break;
				case "agregar_usuario":
					AgregarUsuario(request, response);
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
	
	public void AgregarUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		Usuario user = new Usuario();
		NivelUsuario nivel = new NivelUsuario();
		Persona p = new Persona();
		
		user.setNombreUsuario(request.getParameter("nombre_usuario"));
		user.setContrasenia(request.getParameter("contrasenia"));
		p.setDni(request.getParameter("dni"));
		try
		{
			nivel.setNroNivel(Integer.parseInt(request.getParameter("nivel_usuario")));
		}
		catch(NumberFormatException e)
		{
			nivel.setNroNivel(0);
		}
		user.setPersona(p);
		user.setNivelUsuario(nivel);
		
		UsuarioLogic ul = new UsuarioLogic();
		ul.AgregarUsuario(user);
		request.setAttribute("modal", "usuario_agregado");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Personas.jsp");
		dispatcher.forward(request, response);
	}

	public void EditarUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		Usuario user = new Usuario();
		NivelUsuario nivel = new NivelUsuario();
		Persona p = new Persona();
		
		user.setId(request.getParameter("id"));
		user.setNombreUsuario(request.getParameter("nombre_usuario"));
		user.setContrasenia(request.getParameter("contrasenia"));
		p.setDni(request.getParameter("dni"));
		try
		{
			nivel.setNroNivel(Integer.parseInt(request.getParameter("nivel_usuario")));
		}
		catch(NumberFormatException e)
		{
			nivel.setNroNivel(0);
		}
		user.setPersona(p);
		user.setNivelUsuario(nivel);
		
		UsuarioLogic ul = new UsuarioLogic();
		ul.EditarUsuario(user);
		request.setAttribute("modal", "usuario_modificado");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Personas.jsp");
		dispatcher.forward(request, response);
	}
	
	public void BuscarPorDni(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Usuario user = new Usuario();
		Persona p = new Persona();
		p.setDni(request.getParameter("dni"));
		user.setPersona(p);
		
		UsuarioLogic ul = new UsuarioLogic();
		user = ul.BuscarPorDni(user);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Usuario.jsp");
		request.setAttribute("usuario", user);
		request.setAttribute("dni", request.getParameter("dni"));
		dispatcher.forward(request, response);
	}
	
	public void BuscarUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Usuario user = new Usuario();
		user.setNombreUsuario(request.getParameter("nombre_usuario"));
		
		UsuarioLogic ul = new UsuarioLogic();
		user = ul.BuscarUsuario(user);
		
		RequestDispatcher dispatcher = null;
		
		if(user != null)
		{
			dispatcher = request.getRequestDispatcher("Usuario.jsp");
			request.setAttribute("usuario", user);
			request.setAttribute("dni", user.getPersona().getDni());
		}
		else
		{
			dispatcher = request.getRequestDispatcher("BuscarUsuario.jsp");
			request.setAttribute("usuarioNoEncontrado", "Usuario no encpntrado");
		}
		
		dispatcher.forward(request, response);
	}
	
	public void EliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Usuario user = new Usuario();
		user.setNombreUsuario(request.getParameter("nombre_usuario"));
		
		UsuarioLogic ul = new UsuarioLogic();
		ul.EliminarUsuario(user);
		request.setAttribute("modal", "usuario_eliminado");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Personas.jsp");
		dispatcher.forward(request, response);
	}
}