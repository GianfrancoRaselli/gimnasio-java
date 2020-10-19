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

@WebServlet("/ControladorEntrada")
public class ControladorEntrada extends HttpServlet {
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
					case "validar_entrada":
						ValidarEntrada(request, response);
						break;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void ValidarEntrada(HttpServletRequest request, HttpServletResponse response)
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
					RequestDispatcher dispatcher = request.getRequestDispatcher("ValidarEntrada.jsp");
					request.setAttribute("modal", "entrada_inhabilitada");
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
			else
			{
				RequestDispatcher dispatcher = request.getRequestDispatcher("ValidarEntrada.jsp");
				request.setAttribute("modal", "sucursal_no_encontrada");
				
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
		else
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("ValidarEntrada.jsp");
			request.setAttribute("modal", "persona_no_encontrada");
			request.setAttribute("dniNoEncontrado", request.getParameter("dni"));
			
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
}