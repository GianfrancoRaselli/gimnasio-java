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

@WebServlet("/ServletClasePersonalizada")
public class ServletClasePersonalizada extends HttpServlet {
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
					case "buscar_clases_personalizadas":
						BuscarClasesPersonalizadas(request, response);
						break;
					case "inscribirse":
						Inscribirse(request, response);
						break;
					case "buscar_clase_personalizada":
						BuscarClasePersonalizada(request, response);
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
	
	public void BuscarClasesPersonalizadas(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ClasePersonalizada cp = new ClasePersonalizada();
		
		try
		{
			int anio = Integer.parseInt(request.getParameter("anio"));
			cp.setNroAnio(anio);
		}
		catch(NumberFormatException e)
		{
			Exception excepcionManejada = new Exception("Error al ingresar año", e);
			throw excepcionManejada;
		}
		
		try
		{
			int mes = Integer.parseInt(request.getParameter("mes"));
			cp.setNroMes(mes);
		}
		catch(NumberFormatException e)
		{
			Exception excepcionManejada = new Exception("Error al ingresar mes", e);
			throw excepcionManejada;
		}
			
		if(!request.getParameter("tipo_clase_personalizada").equals("todos"))
		{
			TipoClasePersonalizada tcp = new TipoClasePersonalizada();
			cp.setTipoClasePersonalizada(tcp);
			try
			{
				tcp.setCodTipoClasePersonalizada(Integer.parseInt(request.getParameter("tipo_clase_personalizada")));
			}
			catch(NumberFormatException e)
			{
				Exception excepcionManejada = new Exception("Error al seleccionar tipo clase personalizada", e);
				throw excepcionManejada;
			}
		}
		
		if(!request.getParameter("sucursal").equals("todas"))
		{
			Sucursal s = new Sucursal();
			cp.setSucursal(s);
			try
			{
				s.setNombreSucursal(request.getParameter("sucursal"));
			}
			catch(Exception e)
			{
				Exception excepcionManejada = new Exception("Error al seleccionar sucursal", e);
				throw excepcionManejada;
			}
		}
		
		ClasePersonalizadaLogic cpl = new ClasePersonalizadaLogic();
		ArrayList<ClasePersonalizada> clasesPersonalizadas = (ArrayList<ClasePersonalizada>) cpl.BuscarClasesPersonalizadas(cp, ((Usuario)request.getSession().getAttribute("usuario")).getPersona());
		request.setAttribute("clasesPersonalizadas", clasesPersonalizadas);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("ClasesPersonalizadas.jsp");
		dispatcher.forward(request, response);
	}
	
	public void BuscarClasePersonalizada(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ClasePersonalizada cp = new ClasePersonalizada();
		Sucursal s = new Sucursal();
		cp.setSucursal(s);
		s.setNombreSucursal(request.getParameter("sucursal"));
		
		ClasePersonalizadaLogic cpl = new ClasePersonalizadaLogic();
		cp = cpl.BuscarClasePersonalizadaActual(cp, ((Usuario)request.getSession().getAttribute("usuario")).getPersona());
		
		if(cp!=null)
		{
			request.setAttribute("clase_personalizada", cp);
		}
		else
		{
			request.setAttribute("error", "no_hay_clase_personalizada_actual");
			request.setAttribute("sucursal", request.getParameter("sucursal"));
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Asistencias.jsp");
		dispatcher.forward(request, response);
	}
	
	public void Inscribirse(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ClasePersonalizada cp = new ClasePersonalizada();
		Sucursal s = new Sucursal();
		
		cp.setSucursal(s);
		
		s.setNombreSucursal(request.getParameter("sucursal"));
		cp.setFechaHoraDesdeString(request.getParameter("fecha_hora_desde"));
		
		ClasePersonalizadaLogic cpl = new ClasePersonalizadaLogic();
		cp = cpl.BuscarClasePersonalizada(cp, ((Usuario)request.getSession().getAttribute("usuario")).getPersona());
		
		
		if(cp != null)
		{
			if(cp.isPeriodoHabilitado())
			{
				InscripcionLogic il = new InscripcionLogic();
				
				if(cp.isUsuarioInscripto())
				{
					il.DarseDeBaja(cp, ((Usuario)request.getSession().getAttribute("usuario")).getPersona());
					cp.setUsuarioInscripto(false);
				}
				else
				{
					if(cp.isCuposDisponibles())
					{
						il.Inscribirse(cp, ((Usuario)request.getSession().getAttribute("usuario")).getPersona());
						cp.setUsuarioInscripto(true);
					}
					else
					{
						request.setAttribute("modal", "cupos_completos");
					}
				}
			}
			else
			{
				request.setAttribute("modal", "periodo_cerrado");
			}
			
			ArrayList<ClasePersonalizada> clasesPersonalizadas = new ArrayList<ClasePersonalizada>();
			clasesPersonalizadas.add(cp);
			request.setAttribute("clasesPersonalizadas", clasesPersonalizadas);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("ClasesPersonalizadas.jsp");
			dispatcher.forward(request, response);
		}
		else
		{
			Exception excepcionManejada = new Exception("No se encontró la clase personalizada");
			throw excepcionManejada;
		}
	}
}