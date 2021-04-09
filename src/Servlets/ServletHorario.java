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

@WebServlet("/ServletHorario")
public class ServletHorario extends HttpServlet 
{
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
					case "buscar_por_nombre_sucursal":
						BuscarPorNombreSucursal(request, response);
						break;
					case "agregar_horario":
						AgregarHorario(request, response);
						break;
					case "editar_horario":
						if(request.getParameter("btnGuardar") != null)
						{
							ActualizarHorario(request, response);
						}
						else if(request.getParameter("btnEliminar") != null)
						{
							EliminarHorario(request, response);
						}
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
	
	public void BuscarPorNombreSucursal(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HorarioLogic hl = new HorarioLogic();
		ArrayList<Horario> horarios = (ArrayList<Horario>) hl.BuscarPorNombreSucursal(request.getParameter("nombre_sucursal"));
		
		if(horarios!= null && !horarios.isEmpty())
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Horarios.jsp");
			request.setAttribute("horarios", horarios);
			
			dispatcher.forward(request, response);
		}
		else 
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Horarios.jsp");
			request.setAttribute("modal", "no_hay_resultados");
			request.setAttribute("sucursalNoEncontrada", request.getParameter("nombre_sucursal"));
			
			dispatcher.forward(request, response);
		}
	} 
	
	public void ActualizarHorario(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Horario hor = new Horario();
		Sucursal suc = new Sucursal();
		Dia dia = new Dia();

		suc.setNombreSucursal(request.getParameter("nombre_sucursal"));
		dia.setNroDia(Integer.parseInt(request.getParameter("nro_dia")));
		
		hor.setSucursal(suc);
		hor.setDia(dia);
		
		hor.setIdHoraDesdeString(request.getParameter("id_hora_desde"));
		hor.setHoraDesdeString(request.getParameter("hora_desde") + request.getParameter("minuto_desde") + "00");
		hor.setHoraHastaString(request.getParameter("hora_hasta") + request.getParameter("minuto_hasta") + "00");

		HorarioLogic hl = new HorarioLogic();
		hl.EditarHorario(hor);
		request.setAttribute("modal", "horario_modificado");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Horarios.jsp");
		dispatcher.forward(request, response);
	} 
	
	public void AgregarHorario(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Horario hor = new Horario();
		Sucursal suc = new Sucursal();
		Dia dia = new Dia();
		
		hor.setSucursal(suc);
		hor.setDia(dia);

		suc.setNombreSucursal(request.getParameter("nombre_sucursal"));
		
		try
		{
			dia.setNroDia(Integer.parseInt(request.getParameter("nro_dia")));
			
			hor.setHoraDesdeString(request.getParameter("hora_desde") + request.getParameter("minuto_desde") + "00");
			hor.setHoraHastaString(request.getParameter("hora_hasta") + request.getParameter("minuto_hasta") + "00");
		}
		catch(NumberFormatException e)
		{
			dia.setNroDia(0);
			hor.setHoraDesde(null);
			hor.setHoraHasta(null);
		}

		HorarioLogic hl = new HorarioLogic();
		hl.AgregarHorario(hor);
		request.setAttribute("modal", "horario_agregado");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Horarios.jsp");
		dispatcher.forward(request, response);
	} 
	
	public void EliminarHorario(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Horario hor = new Horario();
		Sucursal suc = new Sucursal();
		Dia dia = new Dia();

		suc.setNombreSucursal(request.getParameter("nombre_sucursal"));
		dia.setNroDia(Integer.parseInt(request.getParameter("nro_dia")));
		
		hor.setSucursal(suc);
		hor.setDia(dia);
		
		hor.setIdHoraDesdeString(request.getParameter("id_hora_desde"));

		HorarioLogic hl = new HorarioLogic();
		hl.EliminarHorario(hor);
		request.setAttribute("modal", "horario_eliminado");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Horarios.jsp");
		dispatcher.forward(request, response);
	}
}