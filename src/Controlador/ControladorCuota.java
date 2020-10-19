package Controlador;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entidades.*;
import Modelo.*;

@WebServlet("/ControladorCuota")
public class ControladorCuota extends HttpServlet {
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
		doGet(request, response);
	}
	
	public void ActualizarCuotas(HttpServletRequest request, HttpServletResponse response)
	{
		CuotaLogic cl = new CuotaLogic();
		cl.ActualizarCuotas();
	}
	
	public void BuscarCuotasDePersona(HttpServletRequest request, HttpServletResponse response)
	{
		CuotaLogic cl = new CuotaLogic();
		ArrayList<Cuota> cuotas = (ArrayList<Cuota>) cl.BuscarCuotasDePersona(((Usuario) request.getSession().getAttribute("usuario")).getPersona());
		request.setAttribute("cuotas", cuotas);
	} 
}
