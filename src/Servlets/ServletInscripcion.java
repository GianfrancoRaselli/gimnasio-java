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
import Modelo.ClasePersonalizadaLogic;
import Modelo.InscripcionLogic;

@WebServlet("/ServletInscripcion")
public class ServletInscripcion extends HttpServlet {
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
					case "registrar_asistencias":
						RegistrarAsistencias(request, response);
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
	
	public void RegistrarAsistencias(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ClasePersonalizada cp = new ClasePersonalizada();
		Sucursal s = new Sucursal();
		
		cp.setSucursal(s);
		
		cp.setFechaHoraDesdeString(request.getParameter("fecha_hora_desde"));
		s.setNombreSucursal(request.getParameter("sucursal"));
		String[] inscriptos = request.getParameterValues("inscriptos");
		
		ArrayList<Persona> personasInscriptas = new ArrayList<Persona>();
		if(inscriptos != null && inscriptos.length > 0)
		{
			for(String dni: inscriptos)
			{
				Persona p = new Persona();
				p.setDni(dni);
				
				personasInscriptas.add(p);
			}
		}
		
		InscripcionLogic il = new InscripcionLogic();
		il.RegistrarAsistencias(cp, personasInscriptas);
		
		ClasePersonalizadaLogic cpl = new ClasePersonalizadaLogic();
		request.setAttribute("clase_personalizada", cpl.BuscarClasePersonalizada(cp));
		request.setAttribute("modal", "asistencias_registradas");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Asistencias.jsp");
		dispatcher.forward(request, response);
	}
}