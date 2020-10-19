package Modelo;

import java.util.Collection;

import BaseDeDatos.AdaptadorCiudad;
import Entidades.Ciudad;

public class CiudadLogic 
{
	public Collection<Ciudad> BuscarCiudades()
	{
		AdaptadorCiudad ciudadAdapter = new AdaptadorCiudad();
		return ciudadAdapter.FindAll();
	}
}
