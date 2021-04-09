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

@WebServlet("/ServletEntrada")
public class ServletEntrada extends HttpServlet {
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
					case "validar_entrada":
						ValidarEntrada(request, response);
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
	
	public void ValidarEntrada(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Persona p = new Persona();
		p.setDni(request.getParameter("dni"));
		
		PersonaLogic pl = new PersonaLogic();
		p = pl.BuscarPersona(p);
		
		if(p != null)
		{
			Sucursal s = new Sucursal();
			s.setNombreSucursal(request.getParameter("nombre_sucursal"));
			
			SucursalLogic sl = new SucursalLogic();
			s = sl.BuscarSucursal(s);
			
			if(s != null)
			{
				CuotaLogic cl = new CuotaLogic();				
				ArrayList<Cuota> cuotasImpagas = (ArrayList<Cuota>) cl.BuscarCuotasImpagas(p);
					
				if(cuotasImpagas.isEmpty())
				{
					Asistencia a = new Asistencia();
					a.setPersona(p);
					a.setSucursal(s);
						
					AsistenciaLogic al = new AsistenciaLogic();
					al.AgregarAsistencia(a);
						
					RequestDispatcher dispatcher = request.getRequestDispatcher("ValidarEntrada.jsp");
					request.setAttribute("modal", "entrada_habilitada");
						
					dispatcher.forward(request, response);
				}
				else
				{
					RequestDispatcher dispatcher = request.getRequestDispatcher("ValidarEntrada.jsp");
					request.setAttribute("modal", "entrada_inhabilitada");
					request.setAttribute("cuotasImpagas", cuotasImpagas);
						
					dispatcher.forward(request, response);
				}
			}
			else
			{
				RequestDispatcher dispatcher = request.getRequestDispatcher("ValidarEntrada.jsp");
				request.setAttribute("modal", "sucursal_no_encontrada");
				
				dispatcher.forward(request, response);
			}
		}
		else
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("ValidarEntrada.jsp");
			request.setAttribute("modal", "persona_no_encontrada");
			request.setAttribute("dniNoEncontrado", request.getParameter("dni"));
			
			dispatcher.forward(request, response);
		}
	}
}