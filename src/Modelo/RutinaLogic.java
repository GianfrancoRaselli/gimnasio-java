package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class RutinaLogic 
{	
	public boolean AgregarRutina(Rutina r)
	{
		AdaptadorRutina rutinaAdapter = new AdaptadorRutina();
		return rutinaAdapter.Insert(r);
	}	
	
	public Rutina BuscarRutina(Rutina r)
	{
		AdaptadorRutina rutinaAdapter = new AdaptadorRutina();
		return rutinaAdapter.BuscarRutina(r);
	}
	
	public boolean AgregarEjercicios(Rutina r)
	{
		AdaptadorRutina rutinaAdapter = new AdaptadorRutina();
		return rutinaAdapter.InsertEjercicios(r);
	}	
	
	public Rutina BuscarUltimaRutinaDelUsuario(Usuario userSesion)
	{
		AdaptadorRutina rutinaAdapter = new AdaptadorRutina();
		return rutinaAdapter.BuscarUltimaRutinaDelUsuario(userSesion);
	}	
	
	public Collection<Rutina> BuscarRutinasDelUsuario(Usuario userSesion)
	{
		AdaptadorRutina rutinaAdapter = new AdaptadorRutina();
		return rutinaAdapter.BuscarRutinasDelUsuario(userSesion);
	}	
}