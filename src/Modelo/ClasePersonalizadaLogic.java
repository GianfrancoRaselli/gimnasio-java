package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class ClasePersonalizadaLogic 
{
	private AdaptadorClasePersonalizada clasePersonalizadaAdapter;
	
	public ClasePersonalizadaLogic()
	{
		clasePersonalizadaAdapter = new AdaptadorClasePersonalizada();
	}
	
	public Collection<ClasePersonalizada> BuscarClasesPersonalizadas(ClasePersonalizada cp, Persona p) throws Exception
	{
		return clasePersonalizadaAdapter.BuscarClasesPersonalizadas(cp, p);
	} 
	
	public ClasePersonalizada BuscarClasePersonalizada(ClasePersonalizada cp, Persona p) throws Exception
	{
		return clasePersonalizadaAdapter.BuscarClasePersonalizada(cp, p);
	}
	
	public ClasePersonalizada BuscarClasePersonalizada(ClasePersonalizada cp) throws Exception
	{
		return clasePersonalizadaAdapter.BuscarClasePersonalizada(cp);
	}
	
	public ClasePersonalizada BuscarClasePersonalizadaActual(ClasePersonalizada cp, Persona p) throws Exception
	{
		return clasePersonalizadaAdapter.BuscarClasePersonalizadaActual(cp, p);
	}
}