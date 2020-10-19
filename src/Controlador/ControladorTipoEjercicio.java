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

@WebServlet("/ControladorTipoEjercicio")
public class ControladorTipoEjercicio extends HttpServlet {
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

	public void BuscarTiposEjercicios(HttpServletRequest request, HttpServletResponse response)
	{
		TipoEjercicioLogic tel = new TipoEjercicioLogic();
		ArrayList<TipoEjercicio> tiposEjercicios = (ArrayList<TipoEjercicio>) tel.BuscarTiposEjercicios();
		request.setAttribute("tiposEjercicios", tiposEjercicios);
	} 
}