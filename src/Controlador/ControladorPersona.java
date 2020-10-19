package Controlador;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entidades.*;
import Modelo.*;

@WebServlet("/ControladorPersona")
public class ControladorPersona extends HttpServlet {
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
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void BuscarPersonas(HttpServletRequest request, HttpServletResponse response)
	{
		PersonaLogic pl = new PersonaLogic();
		ArrayList<Persona> personas = (ArrayList<Persona>) pl.BuscarPersonas();
		request.setAttribute("personas", personas);
	}
	
	public void AgregarPersona(HttpServletRequest request, HttpServletResponse response)
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
		if(pl.AgregarPersona(p))
		{
			request.setAttribute("modal", "persona_agregada");
		}
		else
		{
			request.setAttribute("modal", "persona_no_agregada");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Personas.jsp");
		try 
		{
			dispatcher.forward(request, response);
		} 
		catch (ServletException | IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void EditarPersona(HttpServletRequest request, HttpServletResponse response)
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
		if(pl.EditarPersona(p))
		{
			request.setAttribute("modal", "persona_modificada");
		}
		else
		{
			request.setAttribute("modal", "persona_no_modificada");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Personas.jsp");
		try 
		{
			dispatcher.forward(request, response);
		} 
		catch (ServletException | IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void BuscarPorDni(HttpServletRequest request, HttpServletResponse response)
	{
		Persona p = new Persona();
		p.setDni(request.getParameter("dni"));
		
		PersonaLogic pl = new PersonaLogic();
		p = pl.BuscarPersona(p);
		
		if(p != null)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Personas.jsp");
			request.setAttribute("persona", p);
			
			try 
			{
				dispatcher.forward(request, response);
			} 
			catch (ServletException | IOException e) 
			{
				e.printStackTrace();
			}
		}
		else 
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Personas.jsp");
			request.setAttribute("modal", "no_hay_resultados");
			request.setAttribute("dniNoEncontrado", request.getParameter("dni"));
			
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
	
	public void BuscarPersonaAEditar(HttpServletRequest request, HttpServletResponse response)
	{
		Persona p = new Persona();
		p.setDni(request.getParameter("dni"));
		
		PersonaLogic pl = new PersonaLogic();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrarPersona.jsp");
		request.setAttribute("persona", pl.BuscarPersona(p));
		request.setAttribute("modo", "editar");
		
		try 
		{
			dispatcher.forward(request, response);
		} 
		catch (ServletException | IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void EliminarPersona(HttpServletRequest request, HttpServletResponse response)
	{
		Persona p = new Persona();
		p.setDni(request.getParameter("dni"));
		
		PersonaLogic pl = new PersonaLogic();
		if(pl.EliminarPersona(p))
		{
			request.setAttribute("modal", "persona_eliminada");
		}
		else
		{
			request.setAttribute("modal", "persona_no_eliminada");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Personas.jsp");
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