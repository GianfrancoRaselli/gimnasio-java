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

@WebServlet("/ControladorPagarCuotas")
public class ControladorPagarCuotas extends HttpServlet {
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
			Usuario userSesion = (Usuario)sesion.getAttribute("usuario");
			
			if(userSesion.getNivelUsuario().getDescripcion().equals("usuario"))
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
			else if(userSesion.getNivelUsuario().getDescripcion().equals("administrador"))
			{
				try
				{
					String comando = request.getParameter("instruccion");
					
					switch(comando)
					{
						case "buscar_persona":
							BuscarPersona(request, response);
							break;
						case "pagar_cuota":
							PagarCuota(request, response);
							break;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void BuscarPersona(HttpServletRequest request, HttpServletResponse response)
	{
		Persona p = new Persona();
		p.setDni(request.getParameter("dni"));
		
		PersonaLogic pl = new PersonaLogic();
		p = pl.BuscarPersona(p);
		
		if(p != null)
		{
			CuotaLogic cl = new CuotaLogic();
			ArrayList<Cuota> cuotasImpagas = (ArrayList<Cuota>) cl.BuscarCuotasImpagas(p);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("PagarCuotas.jsp");
			request.setAttribute("persona", p);
			request.setAttribute("cuotasImpagas", cuotasImpagas);
			
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("BuscarPersona.jsp");
			request.setAttribute("personaNoEncontrada", request.getParameter("dni"));
			
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
	
	public void PagarCuota(HttpServletRequest request, HttpServletResponse response)
	{
		Cuota c = new Cuota();
		Persona p = new Persona();
	
		c.setAnio(Integer.parseInt(request.getParameter("anio")));
		c.setMes(Integer.parseInt(request.getParameter("mes")));
		p.setDni(request.getParameter("dni"));
		c.setPersona(p);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("PagarCuotas.jsp");
		
		CuotaLogic cl = new CuotaLogic();
		if(!cl.PagarCuota(c))
		{
			request.setAttribute("modal", "errorAlPagarCuota");
		}
		
		PersonaLogic pl = new PersonaLogic();
		p = pl.BuscarPersona(p);
		ArrayList<Cuota> cuotasImpagas = (ArrayList<Cuota>) cl.BuscarCuotasImpagas(p);
		
		request.setAttribute("persona", p);
		request.setAttribute("cuotasImpagas", cuotasImpagas);
		
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
