package Modelo;

import java.util.Collection;

import BaseDeDatos.AdaptadorCiudad;
import Entidades.Ciudad;

public class CiudadLogic 
{
	private AdaptadorCiudad ciudadAdapter;
	
	public CiudadLogic()
	{
		ciudadAdapter = new AdaptadorCiudad();
	}
	
	public Collection<Ciudad> BuscarCiudades() throws Exception
	{
		return ciudadAdapter.FindAll();
	}
}
