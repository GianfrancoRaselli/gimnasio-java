package Modelo;

import BaseDeDatos.*;
import Entidades.*;

public class UsuarioLogic 
{
	public Usuario BuscarPorDni(Usuario user)
	{
		AdaptadorUsuario userAdapter = new AdaptadorUsuario();
		AdaptadorPersona personaAdapter = new AdaptadorPersona();
		AdaptadorNivelUsuario nivelAdapter = new AdaptadorNivelUsuario();
		
		user = userAdapter.BuscarPorDni(user);
		
		if(user != null)
		{
			user.setNivelUsuario(nivelAdapter.GetOne(user.getNivelUsuario()));
			user.setPersona(personaAdapter.GetOne(user.getPersona()));
		}
		else
		{
			return null;
		}
		
		return user;
	}
	
	public Usuario BuscarUsuario(Usuario user)
	{
		if(user.getNombreUsuario().length() >= 6)
		{
			AdaptadorUsuario userAdapter = new AdaptadorUsuario();
			AdaptadorPersona personaAdapter = new AdaptadorPersona();
			AdaptadorNivelUsuario nivelAdapter = new AdaptadorNivelUsuario();
				
			user = userAdapter.GetOne(user);
				
			if(user != null)
			{
				user.setNivelUsuario(nivelAdapter.GetOne(user.getNivelUsuario()));
				user.setPersona(personaAdapter.GetOne(user.getPersona()));
			}
			else
			{
				return null;
			}
		}
		else 
		{
			return null;
		}
		
		return user;
	}
	
	public boolean EliminarUsuario(Usuario user)
	{
		AdaptadorUsuario userAdapter = new AdaptadorUsuario();
		return userAdapter.Delete(user);
	}
	
	public boolean AgregarUsuario(Usuario user)
	{
		if(user.getNombreUsuario() != "" && user.getContrasenia() != "" && user.getNivelUsuario().getNroNivel() != 0)
		{
			AdaptadorUsuario userAdapter = new AdaptadorUsuario();
			return userAdapter.Insert(user);
		}
		else
		{
			return false;
		}
	}
	
	public boolean EditarUsuario(Usuario user)
	{
		if(user.getNombreUsuario() != "" && user.getContrasenia() != "" && user.getNivelUsuario().getNroNivel() != 0)
		{
			AdaptadorUsuario userAdapter = new AdaptadorUsuario();
			return userAdapter.Update(user);
		}
		else
		{
			return false;
		}
	}
	
	public boolean EditarPerfil(Usuario user)
	{
		if(user.getNombreUsuario() != "" && user.getContrasenia() != "")
		{
			AdaptadorUsuario userAdapter = new AdaptadorUsuario();
			return userAdapter.UpdatePerfil(user);
		}
		else
		{
			return false;
		}
	}
	
	public boolean CambiarContrasenia(Usuario user)
	{
		if(user.getNombreUsuario() != "" && user.getContrasenia() != "")
		{
			AdaptadorUsuario userAdapter = new AdaptadorUsuario();
			return userAdapter.CambiarContrasenia(user);
		}
		else
		{
			return false;
		}
	}
}
