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

@WebServlet("/ServletSucursal")
public class ServletSucursal extends HttpServlet 
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
				case "editar_sucursal":
					BuscarSucursal(request, response);
					break;
				case "eliminar_sucursal":
					EliminarSucursal(request, response);
					break;
				case "registrar_sucursal":
					AgregarSucursal(request, response);
					break;
				case "actualizar_sucursal":
					EditarSucursal(request, response);
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
		SucursalLogic sl = new SucursalLogic();
		ArrayList<Sucursal> sucursales = (ArrayList<Sucursal>) sl.BuscarPorNombreSucursal(request.getParameter("nombre_sucursal"));
		
		if(sucursales!=null && !sucursales.isEmpty())
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Sucursales.jsp");
			request.setAttribute("sucursales", sucursales);
			
			dispatcher.forward(request, response);
		}
		else 
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Sucursales.jsp");
			request.setAttribute("modal", "no_hay_resultados");
			request.setAttribute("nombreSucursalNoEncontrado", request.getParameter("nombre_sucursal"));
			
			dispatcher.forward(request, response);
		}
	}
	
	public void BuscarSucursal(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Sucursal s = new Sucursal();
		s.setNombreSucursal(request.getParameter("nombre_sucursal"));
		
		SucursalLogic sl = new SucursalLogic();
		s = sl.BuscarSucursal(s);
		
		if(s != null)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrarSucursal.jsp");
			request.setAttribute("sucursal", s);
			request.setAttribute("modo", "editar");
			
			dispatcher.forward(request, response);
		}
		else 
		{
			response.sendRedirect("Sucursales.jsp");
		}
	}
	
	public void EliminarSucursal(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Sucursal s = new Sucursal();
		s.setNombreSucursal(request.getParameter("nombre_sucursal"));
		
		SucursalLogic sl = new SucursalLogic();
		sl.EliminarSucursal(s);
		request.setAttribute("modal", "sucursal_eliminada");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Sucursales.jsp");
		dispatcher.forward(request, response);
	}
	
	public void AgregarSucursal(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Sucursal s = new Sucursal();
		Ciudad ciudad = new Ciudad();
		
		s.setNombreSucursal(request.getParameter("nombre_sucursal"));
		s.setDireccion(request.getParameter("direccion"));
		ciudad.setCodPostal(request.getParameter("ciudad"));
		s.setCiudad(ciudad);
		
		SucursalLogic sl = new SucursalLogic();
		sl.AgregarSucursal(s);
		request.setAttribute("modal", "sucursal_agregada");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Sucursales.jsp");
		dispatcher.forward(request, response);
	}
	
	public void EditarSucursal(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Sucursal s = new Sucursal();
		Ciudad ciudad = new Ciudad();
		
		s.setId(request.getParameter("id"));
		s.setNombreSucursal(request.getParameter("nombre_sucursal"));
		s.setDireccion(request.getParameter("direccion"));
		ciudad.setCodPostal(request.getParameter("ciudad"));
		s.setCiudad(ciudad);
		
		SucursalLogic sl = new SucursalLogic();
		sl.ActualizarSucursal(s);
		request.setAttribute("modal", "sucursal_modificada");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Sucursales.jsp");
		dispatcher.forward(request, response);
	}
}