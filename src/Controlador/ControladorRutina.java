package Controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entidades.*;
import Modelo.*;

@WebServlet("/ControladorRutina")
public class ControladorRutina extends HttpServlet {
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
					case "buscar_rutina":
						BuscarRutina(request, response);
						break;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void BuscarRutina(HttpServletRequest request, HttpServletResponse response)
	{
		Rutina r = new Rutina();
		r.setPersona(((Usuario) request.getSession().getAttribute("usuario")).getPersona());
		r.setStringFechaHora(request.getParameter("fecha_hora"));
		
		RutinaLogic rl = new RutinaLogic();
		Rutina rutina = rl.BuscarRutina(r);;
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("MisRutinas.jsp");
		request.setAttribute("rutina", rutina);
		
		try 
		{
			dispatcher.forward(request, response);
		} 
		catch (ServletException | IOException e) 
		{
			e.printStackTrace();
		}
	} 
	
	public void BuscarUltimaRutinaDelUsuario(HttpServletRequest request, HttpServletResponse response)
	{
		RutinaLogic rl = new RutinaLogic();
		Rutina rutina = rl.BuscarUltimaRutinaDelUsuario((Usuario)request.getSession().getAttribute("usuario"));
		request.setAttribute("rutina", rutina);
	} 
	
	public void BuscarRutinasDelUsuario(HttpServletRequest request, HttpServletResponse response)
	{
		RutinaLogic rl = new RutinaLogic();
		ArrayList<Rutina> rutinas = (ArrayList<Rutina>) rl.BuscarRutinasDelUsuario((Usuario)request.getSession().getAttribute("usuario"));
		request.setAttribute("rutinas", rutinas);
	}
}