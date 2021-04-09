package Servlets;

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

@WebServlet("/ServletPagarCuotas")
public class ServletPagarCuotas extends HttpServlet {
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
				Usuario userSesion = (Usuario)sesion.getAttribute("usuario");
				
				if(userSesion.getNivelUsuario().getDescripcion().equals("usuario"))
				{
					response.sendRedirect("Login.jsp");
				}
				else if(userSesion.getNivelUsuario().getDescripcion().equals("administrador"))
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
			}
		}
		catch(Exception e)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Errores.jsp");
			request.setAttribute("exception", e);
			dispatcher.forward(request, response);
		}
	}
	
	public void BuscarPersona(HttpServletRequest request, HttpServletResponse response) throws Exception
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
				
			dispatcher.forward(request, response);
		}
		else 
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("BuscarPersona.jsp");
			request.setAttribute("personaNoEncontrada", request.getParameter("dni"));
			
			dispatcher.forward(request, response);
		}
	}
	
	public void PagarCuota(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		Cuota c = new Cuota();
		Persona p = new Persona();
	
		c.setAnio(Integer.parseInt(request.getParameter("anio")));
		c.setMes(Integer.parseInt(request.getParameter("mes")));
		p.setDni(request.getParameter("dni"));
		c.setPersona(p);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("PagarCuotas.jsp");
		
		CuotaLogic cl = new CuotaLogic();
		cl.PagarCuota(c);
		
		PersonaLogic pl = new PersonaLogic();
		p = pl.BuscarPersona(p);
		
		ArrayList<Cuota> cuotasImpagas = (ArrayList<Cuota>) cl.BuscarCuotasImpagas(p);
		
		request.setAttribute("persona", p);
		request.setAttribute("cuotasImpagas", cuotasImpagas);
		
		dispatcher.forward(request, response);
	}
}