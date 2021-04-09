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

@WebServlet("/ServletPersona")
public class ServletPersona extends HttpServlet {
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
					case "buscar_persona_a_editar":
						BuscarPersonaAEditar(request, response);
						break;
					case "eliminar_persona":
						EliminarPersona(request, response);
						break;
					case "editar_persona":
						EditarPersona(request, response);
						break;
					case "agregar_persona":
						AgregarPersona(request, response);
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
	
	public void AgregarPersona(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Persona p = new Persona();
		Ciudad ciud = new Ciudad();
		
		p.setDni(request.getParameter("dni"));
		p.setNombreApellido(request.getParameter("nombreApellido"));
		p.setTelefono(request.getParameter("telefono"));
		p.setEmail(request.getParameter("email"));
		p.setDireccion(request.getParameter("direccion"));
		try
		{
			switch(Integer.parseInt(request.getParameter("tipo")))
			{
				case 1:
					p.setTipo(Persona.Tipos.Administrativo);
					break;
				case 2:
					p.setTipo(Persona.Tipos.Entrenador);
					break;
				case 3:
					p.setTipo(Persona.Tipos.Cliente);
					break;
				default:
					p.setTipo(null);
					break;
			}
		}
		catch(NumberFormatException e)
		{
			p.setTipo(null);
		}
		ciud.setCodPostal(request.getParameter("ciudad"));;
		p.setCiudad(ciud);
		
		PersonaLogic pl = new PersonaLogic();
		pl.AgregarPersona(p);
		request.setAttribute("modal", "persona_agregada");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Personas.jsp");
		dispatcher.forward(request, response);
	}

	public void EditarPersona(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Persona p = new Persona();
		Ciudad ciud = new Ciudad();
		
		p.setId(request.getParameter("id"));
		p.setDni(request.getParameter("dni"));
		p.setNombreApellido(request.getParameter("nombreApellido"));
		p.setTelefono(request.getParameter("telefono"));
		p.setEmail(request.getParameter("email"));
		p.setDireccion(request.getParameter("direccion"));
		try
		{
			switch(Integer.parseInt(request.getParameter("tipo")))
			{
				case 1:
					p.setTipo(Persona.Tipos.Administrativo);
					break;
				case 2:
					p.setTipo(Persona.Tipos.Entrenador);
					break;
				case 3:
					p.setTipo(Persona.Tipos.Cliente);
					break;
				default:
					p.setTipo(null);
					break;
			}
		}
		catch(NumberFormatException e)
		{
			p.setTipo(null);
		}
		ciud.setCodPostal(request.getParameter("ciudad"));
		p.setCiudad(ciud);
		
		PersonaLogic pl = new PersonaLogic();
		pl.EditarPersona(p);
		request.setAttribute("modal", "persona_modificada");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Personas.jsp");
		dispatcher.forward(request, response);
	}
	
	public void BuscarPorDni(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Persona p = new Persona();
		p.setDni(request.getParameter("dni"));
		
		PersonaLogic pl = new PersonaLogic();
		p = pl.BuscarPersona(p);
		
		if(p != null)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Personas.jsp");
			request.setAttribute("persona", p);
			
			dispatcher.forward(request, response);
		}
		else 
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Personas.jsp");
			request.setAttribute("modal", "no_hay_resultados");
			request.setAttribute("dniNoEncontrado", request.getParameter("dni"));
			
			dispatcher.forward(request, response);
		}
	}
	
	public void BuscarPersonaAEditar(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Persona p = new Persona();
		p.setDni(request.getParameter("dni"));
		
		PersonaLogic pl = new PersonaLogic();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrarPersona.jsp");
		request.setAttribute("persona", pl.BuscarPersona(p));
		request.setAttribute("modo", "editar");
		
		dispatcher.forward(request, response);
	}
	
	public void EliminarPersona(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Persona p = new Persona();
		p.setDni(request.getParameter("dni"));
		
		PersonaLogic pl = new PersonaLogic();
		pl.EliminarPersona(p);
		request.setAttribute("modal", "persona_eliminada");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Personas.jsp");
		dispatcher.forward(request, response);
	}
}