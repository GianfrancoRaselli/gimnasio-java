package Modelo;

import BaseDeDatos.*;
import Entidades.*;
import Util.Encriptacion;

public class UsuarioLogic 
{
	private AdaptadorUsuario userAdapter;
	
	public UsuarioLogic()
	{
		userAdapter = new AdaptadorUsuario();
	}
	
	public Usuario BuscarPorDni(Usuario user) throws Exception
	{
		return userAdapter.BuscarPorDni(user);
	}
	
	public Usuario BuscarUsuario(Usuario user) throws Exception
	{
		return userAdapter.GetOne(user);
	}
	
	public void EliminarUsuario(Usuario user) throws Exception
	{
		userAdapter.Delete(user);
	}
	
	public void AgregarUsuario(Usuario user) throws Exception
	{
		if(user.getNombreUsuario().length() >= 6 && user.getContrasenia().length() >= 8 && user.getNivelUsuario().getNroNivel() != 0)
		{
			user.setContraseniaEncriptada(Encriptacion.Encriptar(user.getContrasenia()));
			userAdapter.Insert(user);
		}
		else
		{
			Exception excepcionManejada = new Exception("Error en los datos ingresados");
			throw excepcionManejada;
		}
	}
	
	public void EditarUsuario(Usuario user) throws Exception
	{
		if(user.getNombreUsuario().length() >= 6 && user.getContrasenia().length() >= 8 && user.getNivelUsuario().getNroNivel() != 0)
		{
			user.setContraseniaEncriptada(Encriptacion.Encriptar(user.getContrasenia()));
			userAdapter.Update(user);
		}
		else
		{
			Exception excepcionManejada = new Exception("Error en los datos ingresados");
			throw excepcionManejada;
		}
	}
	
	public void EditarPerfil(Usuario user) throws Exception
	{
		if(user.getNombreUsuario().length() >= 6 && user.getContrasenia().length() >= 8)
		{
			user.setContraseniaEncriptada(Encriptacion.Encriptar(user.getContrasenia()));
			userAdapter.UpdatePerfil(user);
		}
		else
		{
			Exception excepcionManejada = new Exception("Error en los datos ingresados");
			throw excepcionManejada;
		}
	}
	
	public void CambiarContrasenia(Usuario user) throws Exception
	{
		if(user.getNombreUsuario().length() >= 6 && user.getContrasenia().length() >= 8)
		{
			user.setContraseniaEncriptada(Encriptacion.Encriptar(user.getContrasenia()));
			userAdapter.CambiarContrasenia(user);
		}
		else
		{
			Exception excepcionManejada = new Exception("Error en los datos ingresados");
			throw excepcionManejada;
		}
	}
}