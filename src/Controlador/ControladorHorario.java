package Controlador;

import java.io.IOException;
import java.sql.Time;
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

@WebServlet("/ControladorHorario")
public class ControladorHorario extends HttpServlet 
{
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
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void BuscarHorarios(HttpServletRequest request, HttpServletResponse response)
	{
		HorarioLogic hl = new HorarioLogic();
		ArrayList<Horario> horarios = (ArrayList<Horario>) hl.BuscarHorarios();
		request.setAttribute("horarios", horarios);
	} 
	
	public void BuscarPorNombreSucursal(HttpServletRequest request, HttpServletResponse response)
	{
		HorarioLogic hl = new HorarioLogic();
		ArrayList<Horario> horarios = (ArrayList<Horario>) hl.BuscarPorNombreSucursal(request.getParameter("nombre_sucursal"));
		
		if(!horarios.isEmpty())
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Horarios.jsp");
			request.setAttribute("horarios", horarios);
			
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("Horarios.jsp");
			request.setAttribute("modal", "no_hay_resultados");
			request.setAttribute("sucursalNoEncontrada", request.getParameter("nombre_sucursal"));
			
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
	
	public void ActualizarHorario(HttpServletRequest request, HttpServletResponse response)
	{
		Horario hor = new Horario();
		Sucursal suc = new Sucursal();
		Dia dia = new Dia();
		

		suc.setNombreSucursal(request.getParameter("nombre_sucursal"));
		dia.setNroDia(Integer.parseInt(request.getParameter("nro_dia")));
		
		hor.setSucursal(suc);
		hor.setDia(dia);
		
		int idHoraDesde = Integer.parseInt(request.getParameter("id_hora_desde"));
		int idMinutoDesde = Integer.parseInt(request.getParameter("id_minuto_desde"));	
		@SuppressWarnings("deprecation")
		Time idHora = new Time(idHoraDesde, idMinutoDesde, 0);
		hor.setIdHoraDesde(idHora);
		
		int horaDesde = Integer.parseInt(request.getParameter("hora_desde"));
		int minutoDesde = Integer.parseInt(request.getParameter("minuto_desde"));	
		@SuppressWarnings("deprecation")
		Time hDesde = new Time(horaDesde, minutoDesde, 0);
		hor.setHoraDesde(hDesde);

		int horaHasta = Integer.parseInt(request.getParameter("hora_hasta"));
		int minutoHasta = Integer.parseInt(request.getParameter("minuto_hasta"));	
		@SuppressWarnings("deprecation")
		Time hHasta = new Time(horaHasta, minutoHasta, 0);
		hor.setHoraHasta(hHasta);

		HorarioLogic hl = new HorarioLogic();
		if(hl.EditarHorario(hor))
		{
			request.setAttribute("modal", "horario_modificado");
		}
		else
		{
			request.setAttribute("modal", "horario_no_modificado");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Horarios.jsp");
		try 
		{
			dispatcher.forward(request, response);
		} 
		catch (ServletException | IOException e) 
		{
			e.printStackTrace();
		}
	} 
	
	public void AgregarHorario(HttpServletRequest request, HttpServletResponse response)
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
			
			int horaDesde = Integer.parseInt(request.getParameter("hora_desde"));
			int minutoDesde = Integer.parseInt(request.getParameter("minuto_desde"));	
			@SuppressWarnings("deprecation")
			Time hDesde = new Time(horaDesde, minutoDesde, 0);
			hor.setHoraDesde(hDesde);
	
			int horaHasta = Integer.parseInt(request.getParameter("hora_hasta"));
			int minutoHasta = Integer.parseInt(request.getParameter("minuto_hasta"));	
			@SuppressWarnings("deprecation")
			Time hHasta = new Time(horaHasta, minutoHasta, 0);
			hor.setHoraHasta(hHasta);
		}
		catch(NumberFormatException e)
		{
			dia.setNroDia(0);
			hor.setHoraDesde(null);
			hor.setHoraHasta(null);
			e.printStackTrace();
		}

		HorarioLogic hl = new HorarioLogic();
		if(hl.AgregarHorario(hor))
		{
			request.setAttribute("modal", "horario_agregado");
		}
		else
		{
			request.setAttribute("modal", "horario_no_agregado");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Horarios.jsp");
		try 
		{
			dispatcher.forward(request, response);
		} 
		catch (ServletException | IOException e) 
		{
			e.printStackTrace();
		}
	} 
	
	public void EliminarHorario(HttpServletRequest request, HttpServletResponse response)
	{
		Horario hor = new Horario();
		Sucursal suc = new Sucursal();
		Dia dia = new Dia();
		

		suc.setNombreSucursal(request.getParameter("nombre_sucursal"));
		dia.setNroDia(Integer.parseInt(request.getParameter("nro_dia")));
		
		hor.setSucursal(suc);
		hor.setDia(dia);
		
		int horaDesde = Integer.parseInt(request.getParameter("id_hora_desde"));
		int minutoDesde = Integer.parseInt(request.getParameter("id_minuto_desde"));
		@SuppressWarnings("deprecation")
		Time hora = new Time(horaDesde, minutoDesde, 0);
		hor.setIdHoraDesde(hora);

		HorarioLogic hl = new HorarioLogic();
		if(hl.EliminarHorario(hor))
		{
			request.setAttribute("modal", "horario_eliminado");
		}
		else
		{
			request.setAttribute("modal", "horario_no_eliminado");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Horarios.jsp");
		try 
		{
			dispatcher.forward(request, response);
		} 
		catch (ServletException | IOException e) 
		{
			e.printStackTrace();
		}
	} 
	
	public void BuscarHorariosDeSucursal(HttpServletRequest request, HttpServletResponse response)
	{
		Sucursal s = new Sucursal();
		Ciudad ciudad = new Ciudad();
		
		s.setId(request.getParameter("id"));
		s.setNombreSucursal(request.getParameter("nombre_sucursal"));
		s.setDireccion(request.getParameter("direccion"));
		ciudad.setCodPostal(request.getParameter("ciudad"));
		s.setCiudad(ciudad);
		
		SucursalLogic sl = new SucursalLogic();
		if(sl.ActualizarSucursal(s))
		{
			request.setAttribute("modal", "sucursal_modificada");
		}
		else
		{
			request.setAttribute("modal", "sucursal_no_modificada");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Sucursales.jsp");
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
