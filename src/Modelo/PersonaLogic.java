package Modelo;

import java.util.Collection;

import BaseDeDatos.AdaptadorPersona;
import Entidades.Persona;

public class PersonaLogic 
{
	private AdaptadorPersona personaAdapter;
	
	public PersonaLogic()
	{
		personaAdapter = new AdaptadorPersona();
	}
	
	public Persona BuscarPersona(Persona p) throws Exception
	{
		return personaAdapter.GetOne(p);
	}
	
	public Collection<Persona> BuscarPersonas() throws Exception
	{
		return personaAdapter.FindAll();
	}
	
	public void EliminarPersona(Persona p) throws Exception
	{
		personaAdapter.Delete(p);
	}
	
	public void AgregarPersona(Persona p) throws Exception
	{
		if(p.getDni() != "" && p.getNombreApellido() != "" && p.getTelefono() != "" && p.getEmail() != "" && p.getDireccion() != "" && 
				p.getTipo() != null && p.getCiudad().getCodPostal() != null)
		{
			personaAdapter.Insert(p);
		}
		else
		{
			Exception excepcionManejada = new Exception("Error en los datos ingresados");
			throw excepcionManejada;
		}
	}
	
	public void EditarPersona(Persona p) throws Exception
	{
		if(p.getId() != "" && p.getDni() != "" && p.getNombreApellido() != "" && p.getTelefono() != "" && p.getEmail() != "" && p.getDireccion() != "" && 
				p.getTipo() != null && p.getCiudad().getCodPostal() != null)
		{
			personaAdapter.Update(p);
		}
		else
		{
			Exception excepcionManejada = new Exception("Error en los datos ingresados");
			throw excepcionManejada;
		}
	}
	
	public void EditarPerfil(Persona p) throws Exception
	{
		if(p.getDni() != "" && p.getNombreApellido() != "" && p.getTelefono() != "" && p.getEmail() != "" && p.getDireccion() != "" 
				&& p.getCiudad().getCodPostal() != null)
		{
			personaAdapter.UpdatePerfil(p);
		}
		else
		{
			Exception excepcionManejada = new Exception("Error en los datos ingresados");
			throw excepcionManejada;
		}
	}
}