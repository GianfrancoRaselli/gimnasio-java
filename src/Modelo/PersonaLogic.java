package Modelo;

import java.util.Collection;

import BaseDeDatos.AdaptadorPersona;
import Entidades.Persona;

public class PersonaLogic 
{
	public Persona BuscarPersona(Persona p)
	{
		AdaptadorPersona personaAdapter = new AdaptadorPersona();
		return personaAdapter.GetOne(p);
	}
	
	public Collection<Persona> BuscarPersonas()
	{
		AdaptadorPersona personaAdapter = new AdaptadorPersona();
		return personaAdapter.FindAll();
	}
	
	public boolean EliminarPersona(Persona p)
	{
		AdaptadorPersona personaAdapter = new AdaptadorPersona();
		return personaAdapter.Delete(p);
	}
	
	public boolean AgregarPersona(Persona p)
	{
		if(p.getDni() != "" && p.getNombreApellido() != "" && p.getTelefono() != "" && p.getEmail() != "" && p.getDireccion() != "" && 
				p.getTipo() != null && p.getCiudad().getCodPostal() != null)
		{
			AdaptadorPersona personaAdapter = new AdaptadorPersona();
			return personaAdapter.Insert(p);
		}
		else
		{
			return false;
		}
	}
	
	public boolean EditarPersona(Persona p)
	{
		if(p.getId() != "" && p.getDni() != "" && p.getNombreApellido() != "" && p.getTelefono() != "" && p.getEmail() != "" && p.getDireccion() != "" && 
				p.getTipo() != null && p.getCiudad().getCodPostal() != null)
		{
			AdaptadorPersona personaAdapter = new AdaptadorPersona();
			return personaAdapter.Update(p);
		}
		else
		{
			return false;
		}
	}
	
	public boolean EditarPerfil(Persona p)
	{
		if(p.getDni() != "" && p.getNombreApellido() != "" && p.getTelefono() != "" && p.getEmail() != "" && p.getDireccion() != "" 
				&& p.getCiudad().getCodPostal() != null)
		{
			AdaptadorPersona personaAdapter = new AdaptadorPersona();
			return personaAdapter.UpdatePerfil(p);
		}
		else
		{
			return false;
		}
	}
}
