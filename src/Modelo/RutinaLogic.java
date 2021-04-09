package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class RutinaLogic 
{	
	private AdaptadorRutina rutinaAdapter;
	
	public RutinaLogic()
	{
		rutinaAdapter = new AdaptadorRutina();
	}
	
	public void AgregarRutina(Rutina r) throws Exception
	{
		rutinaAdapter.Insert(r);
	}	
	
	public Rutina BuscarRutina(Rutina r) throws Exception
	{
		return rutinaAdapter.BuscarRutina(r);
	}	
	
	public Rutina BuscarUltimaRutinaDelUsuario(Usuario userSesion) throws Exception
	{
		return rutinaAdapter.BuscarUltimaRutinaDelUsuario(userSesion);
	}	
	
	public Collection<Rutina> BuscarRutinasDelUsuario(Usuario userSesion) throws Exception
	{
		return rutinaAdapter.BuscarRutinasDelUsuario(userSesion);
	}	
}