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

@WebServlet("/ControladorSucursal")
public class ControladorSucursal extends HttpServlet 
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
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void BuscarPorNombreSucursal(HttpServletRequest request, HttpServletResponse response)
	{
		SucursalLogic sl = new SucursalLogic();
		ArrayList<Sucursal> sucursales = (ArrayList<Sucursal>) sl.BuscarPorNombreSucursal(request.getParameter("nombre_sucursal"));
		
		if(!sucursales.isEmpty())
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("Sucursales.jsp");
			request.setAttribute("sucursales", sucursales);
			
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("Sucursales.jsp");
			request.setAttribute("modal", "no_hay_resultados");
			request.setAttribute("nombreSucursalNoEncontrado", request.getParameter("nombre_sucursal"));
			
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
	
	public void BuscarSucursal(HttpServletRequest request, HttpServletResponse response)
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
			try 
			{
				response.sendRedirect("Sucursales.jsp");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void BuscarSucursales(HttpServletRequest request, HttpServletResponse response)
	{
		SucursalLogic sl = new SucursalLogic();
		ArrayList<Sucursal> sucursales = (ArrayList<Sucursal>) sl.BuscarSucursales();
		request.setAttribute("sucursales", sucursales);
	}
	
	public void EliminarSucursal(HttpServletRequest request, HttpServletResponse response)
	{
		Sucursal s = new Sucursal();
		s.setNombreSucursal(request.getParameter("nombre_sucursal"));
		
		SucursalLogic sl = new SucursalLogic();
		if(sl.EliminarSucursal(s))
		{
			request.setAttribute("modal", "sucursal_eliminada");
		}
		else
		{
			request.setAttribute("modal", "sucursal_no_eliminada");
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
	
	public void AgregarSucursal(HttpServletRequest request, HttpServletResponse response)
	{
		Sucursal s = new Sucursal();
		Ciudad ciudad = new Ciudad();
		
		s.setNombreSucursal(request.getParameter("nombre_sucursal"));
		s.setDireccion(request.getParameter("direccion"));
		ciudad.setCodPostal(request.getParameter("ciudad"));
		s.setCiudad(ciudad);
		
		SucursalLogic sl = new SucursalLogic();
		if(sl.AgregarSucursal(s))
		{
			request.setAttribute("modal", "sucursal_agregada");
		}
		else
		{
			request.setAttribute("modal", "sucursal_no_agregada");
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
	
	public void EditarSucursal(HttpServletRequest request, HttpServletResponse response)
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
