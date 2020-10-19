package Modelo;

import BaseDeDatos.*;
import Entidades.*;

public class SesionLogic 
{
	public Usuario InicioSesion(Usuario user)
	{
		if(user.getNombreUsuario().length() >= 6 && user.getContrasenia().length() >= 8)
		{
			AdaptadorUsuario userAdapter = new AdaptadorUsuario();			
			return userAdapter.BuscarPorUsuarioYContrasenia(user);
		}
		else 
		{
			return null;
		}
	}
}
