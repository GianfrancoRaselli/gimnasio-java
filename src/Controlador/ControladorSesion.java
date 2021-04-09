package Controlador;

import Entidades.Usuario;
import Modelo.SesionLogic;

public class ControladorSesion 
{
	public Usuario IniciarSesion(Usuario user) throws Exception
	{
		SesionLogic login = new SesionLogic();
		return login.InicioSesion(user);
	}
}