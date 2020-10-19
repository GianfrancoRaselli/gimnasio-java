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

@WebServlet("/ControladorRegistrarRutina")
public class ControladorRegistrarRutina extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Rutina rutinaActual = null;
	
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
			Usuario userSesion = (Usuario)sesion.getAttribute("usuario");
			
			if(userSesion.getNivelUsuario().getDescripcion().equals("usuario"))
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
			else if(userSesion.getNivelUsuario().getDescripcion().equals("administrador"))
			{
				try
				{
					String comando = request.getParameter("instruccion");
					
					switch(comando)
					{
						case "buscar_persona":
							BuscarPersona(request, response);
							break;
						case "agregar_ejercicio":
							AgregarEjercicio(request, response);
							break;
						case "eliminar_ejercicio":
							EliminarEjercicio(request, response);
							break;
						case "registrar_rutina":
							RegistrarRutina(request, response);
							break;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void BuscarPersona(HttpServletRequest request, HttpServletResponse response)
	{
		Persona p = new Persona();
		p.setDni(request.getParameter("dni"));
		
		PersonaLogic pl = new PersonaLogic();
		p = pl.BuscarPersona(p);
		
		if(p != null)
		{	
			rutinaActual = new Rutina();
			rutinaActual.setPersona(p);
			rutinaActual.setFecha(new Date());
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrarRutina.jsp");
			request.setAttribute("rutina", rutinaActual);
			request.setAttribute("modo", "agregarEjercicios");
			
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("BuscarPersonaDeRutina.jsp");
			request.setAttribute("personaNoEncontrada", request.getParameter("dni"));
			
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
		
	public void AgregarEjercicio(HttpServletRequest request, HttpServletResponse response)
	{
		if(rutinaActual != null)
		{
			Ejercicio e = new Ejercicio();
			
			TipoEjercicioLogic tel = new TipoEjercicioLogic();
			TipoEjercicio te = new TipoEjercicio();
			
			if(request.getParameter("tipo_ejercicio") != null)
			{
				te.setCodTipoEjercicio(Integer.parseInt(request.getParameter("tipo_ejercicio")));
				e.setTipoEjercicio(tel.BuscarTipoEjercicio(te));
			}
			else
			{
				e.setTipoEjercicio(null);
			}
			if(request.getParameter("tipo") != null)
			{
				switch(Integer.parseInt(request.getParameter("tipo")))
				{
					case 1:
						e.setTipo(Ejercicio.Tipos.Repeticion);
						break;
					case 2:
						e.setTipo(Ejercicio.Tipos.Tiempo);
						break;
					default:
						e.setTipo(null);
						break;
				}
			}
			else
			{
				e.setTipo(null);
			}
			if(request.getParameter("dia") != null)
			{
				switch(Integer.parseInt(request.getParameter("dia")))
				{
					case 1:
						e.setNroDia(Ejercicio.NroDias.Primero);
						e.setOrden(rutinaActual.getEjerciciosDia1().size()+1);
						break;
					case 2:
						e.setNroDia(Ejercicio.NroDias.Segundo);
						e.setOrden(rutinaActual.getEjerciciosDia2().size()+1);
						break;
					case 3:
						e.setNroDia(Ejercicio.NroDias.Tercero);
						e.setOrden(rutinaActual.getEjerciciosDia3().size()+1);
						break;
					case 4:
						e.setNroDia(Ejercicio.NroDias.Cuarto);
						e.setOrden(rutinaActual.getEjerciciosDia4().size()+1);
						break;
					case 5:
						e.setNroDia(Ejercicio.NroDias.Quinto);
						e.setOrden(rutinaActual.getEjerciciosDia5().size()+1);
						break;
					case 6:
						e.setNroDia(Ejercicio.NroDias.Sexto);
						e.setOrden(rutinaActual.getEjerciciosDia6().size()+1);
						break;
					case 7:
						e.setNroDia(Ejercicio.NroDias.Septimo);
						e.setOrden(rutinaActual.getEjerciciosDia7().size()+1);
						break;
					default:
						e.setNroDia(null);
						break;
				}
			}
			else
			{
				e.setNroDia(null);
			}
			if(request.getParameter("series") != "") e.setSeries(request.getParameter("series"));
			if(request.getParameter("repeticiones") != "") e.setRepeticiones(request.getParameter("repeticiones"));
			if(request.getParameter("pesos") != "") e.setPesos(request.getParameter("pesos"));
			if(request.getParameter("tiempo") != "") e.setTiempo(request.getParameter("tiempo"));
			
			if(e.getTipoEjercicio() != null && e.getTipo() != null && e.getNroDia() != null)
			{
				switch(e.getNroDia())
				{
					case Primero:
						rutinaActual.agregarEjercicioDia1(e);
						break;
					case Segundo:
						rutinaActual.agregarEjercicioDia2(e);
						break;
					case Tercero:
						rutinaActual.agregarEjercicioDia3(e);
						break;
					case Cuarto:
						rutinaActual.agregarEjercicioDia4(e);
						break;
					case Quinto:
						rutinaActual.agregarEjercicioDia5(e);
						break;
					case Sexto:
						rutinaActual.agregarEjercicioDia6(e);
						break;
					case Septimo:
						rutinaActual.agregarEjercicioDia7(e);
						break;
				}
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrarRutina.jsp");
				request.setAttribute("rutina", rutinaActual);
				request.setAttribute("modo", "agregarEjercicios");
					
				try 
				{
					dispatcher.forward(request, response);
				} 
				catch (ServletException | IOException ex) 
				{
					ex.printStackTrace();
				}	
			}
			else
			{
				RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrarRutina.jsp");
				request.setAttribute("rutina", rutinaActual);
				request.setAttribute("modo", "agregarEjercicios");
				request.setAttribute("modal", "ejercicio_no_agregado");
					
				try 
				{
					dispatcher.forward(request, response);
				} 
				catch (ServletException | IOException ex) 
				{
					ex.printStackTrace();
				}	
			}
		}
		else
		{
			try 
			{
				response.sendRedirect("BuscarPersonaDeRutina.jsp");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}	
	}
	
	public void EliminarEjercicio(HttpServletRequest request, HttpServletResponse response)
	{
		if(rutinaActual != null)
		{
			Ejercicio e = new Ejercicio();
			
			if(request.getParameter("dia") != null)
			{
				switch(request.getParameter("dia"))
				{
					case "Primero":
						e.setNroDia(Ejercicio.NroDias.Primero);
						break;
					case "Segundo":
						e.setNroDia(Ejercicio.NroDias.Segundo);
						break;
					case "Tercero":
						e.setNroDia(Ejercicio.NroDias.Tercero);
						break;
					case "Cuarto":
						e.setNroDia(Ejercicio.NroDias.Cuarto);
						break;
					case "Quinto":
						e.setNroDia(Ejercicio.NroDias.Quinto);
						break;
					case "Sexto":
						e.setNroDia(Ejercicio.NroDias.Sexto);
						break;
					case "Septimo":
						e.setNroDia(Ejercicio.NroDias.Septimo);
						break;
					default:
						e.setNroDia(null);
						break;
				}
			}
			else
			{
				e.setNroDia(null);
			}
			try
			{
				e.setOrden(Integer.parseInt(request.getParameter("orden")));
			}
			catch(Exception ex)
			{
				e.setOrden(0);
				ex.printStackTrace();
			}
			
			if(e.getNroDia() != null && e.getOrden() != 0)
			{
				switch(e.getNroDia())
				{
					case Primero:
						rutinaActual.eliminarEjercicioDia1(e);
						break;
					case Segundo:
						rutinaActual.eliminarEjercicioDia2(e);
						break;
					case Tercero:
						rutinaActual.eliminarEjercicioDia3(e);
						break;
					case Cuarto:
						rutinaActual.eliminarEjercicioDia4(e);
						break;
					case Quinto:
						rutinaActual.eliminarEjercicioDia5(e);
						break;
					case Sexto:
						rutinaActual.eliminarEjercicioDia6(e);
						break;
					case Septimo:
						rutinaActual.eliminarEjercicioDia7(e);
						break;
				}
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrarRutina.jsp");
				request.setAttribute("rutina", rutinaActual);
				request.setAttribute("modo", "agregarEjercicios");
					
				try 
				{
					dispatcher.forward(request, response);
				} 
				catch (ServletException | IOException ex) 
				{
					ex.printStackTrace();
				}		
			}
			else
			{
				RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrarRutina.jsp");
				request.setAttribute("rutina", rutinaActual);
				request.setAttribute("modo", "agregarEjercicios");
				request.setAttribute("modal", "ejercicio_no_eliminado");
					
				try 
				{
					dispatcher.forward(request, response);
				} 
				catch (ServletException | IOException ex) 
				{
					ex.printStackTrace();
				}	
			}
		}
		else
		{
			try 
			{
				response.sendRedirect("BuscarPersonaDeRutina.jsp");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}	
	}
	
	public void RegistrarRutina(HttpServletRequest request, HttpServletResponse response)
	{
		if(rutinaActual != null && (!rutinaActual.getEjerciciosDia1().isEmpty() || 
				!rutinaActual.getEjerciciosDia2().isEmpty() || !rutinaActual.getEjerciciosDia3().isEmpty() ||
				!rutinaActual.getEjerciciosDia4().isEmpty() || !rutinaActual.getEjerciciosDia5().isEmpty() ||
				!rutinaActual.getEjerciciosDia6().isEmpty() || !rutinaActual.getEjerciciosDia7().isEmpty()))
		{
			RutinaLogic rl = new RutinaLogic();
			
			if(rl.AgregarRutina(rutinaActual))
			{
				if(rl.AgregarEjercicios(rutinaActual))
				{
					RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrarRutina.jsp");
					request.setAttribute("rutina", rutinaActual);
					request.setAttribute("modo", "rutinaRegistrada");
						
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
					RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrarRutina.jsp");
					request.setAttribute("rutina", rutinaActual);
					request.setAttribute("modo", "agregarEjercicios");
					request.setAttribute("modal", "rutina_no_agregada");
						
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
				RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrarRutina.jsp");
				request.setAttribute("rutina", rutinaActual);
				request.setAttribute("modo", "agregarEjercicios");
				request.setAttribute("modal", "rutina_no_agregada");
					
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrarRutina.jsp");
			request.setAttribute("rutina", rutinaActual);
			request.setAttribute("modo", "agregarEjercicios");
			request.setAttribute("modal", "no_hay_ejercicios");
				
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
