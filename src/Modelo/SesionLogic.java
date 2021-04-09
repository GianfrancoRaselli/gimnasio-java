package Modelo;

import BaseDeDatos.*;
import Entidades.*;
import Util.Encriptacion;

public class SesionLogic 
{
	public Usuario InicioSesion(Usuario user) throws Exception
	{
		if(user.getNombreUsuario().length()>=6 && ((user.getContraseniaEncriptada()!=null && user.getContraseniaEncriptada().length()>=8) || 
				(user.getContrasenia()!=null && user.getContrasenia().length()>=8)))
		{
			if(user.getContraseniaEncriptada() == null)
			{
				user.setContraseniaEncriptada(Encriptacion.Encriptar(user.getContrasenia()));
			}
			
			AdaptadorUsuario userAdapter = new AdaptadorUsuario();			
			return userAdapter.BuscarPorUsuarioYContrasenia(user);
		}
		else 
		{
			Exception excepcionManejada = new Exception("Error en los datos ingresados");
			throw excepcionManejada;
		}
	}
}