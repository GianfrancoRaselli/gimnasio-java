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

@WebServlet("/ControladorNivelUsuario")
public class ControladorNivelUsuario extends HttpServlet 
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

	public void BuscarNivelesUsuarios(HttpServletRequest request, HttpServletResponse response)
	{
		NivelUsuarioLogic nul = new NivelUsuarioLogic();
		ArrayList<NivelUsuario> nivelesUsuarios = (ArrayList<NivelUsuario>) nul.BuscarNivelesUsuarios();
		request.setAttribute("niveles_usuarios", nivelesUsuarios);
	} 
}
