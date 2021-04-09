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

@WebServlet("/ServletRutina")
public class ServletRutina extends HttpServlet {
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
					case "buscar_rutina":
						BuscarRutina(request, response);
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
	
	public void BuscarRutina(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Rutina r = new Rutina();
		r.setPersona(((Usuario) request.getSession().getAttribute("usuario")).getPersona());
		r.setFechaHoraString(request.getParameter("fecha_hora"));
		
		RutinaLogic rl = new RutinaLogic();
		Rutina rutina;
		rutina = rl.BuscarRutina(r);
		request.setAttribute("rutina", rutina);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("MisRutinas.jsp");
		dispatcher.forward(request, response);
	} 
}