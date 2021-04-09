package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class TipoEjercicioLogic 
{
	private AdaptadorTipoEjercicio tipoEjercicioAdapter;
	
	public TipoEjercicioLogic()
	{
		tipoEjercicioAdapter = new AdaptadorTipoEjercicio();
	}
	
	public Collection<TipoEjercicio> BuscarTiposEjercicios() throws Exception
	{
		return tipoEjercicioAdapter.FindAll();
	}
	
	public TipoEjercicio BuscarTipoEjercicio(TipoEjercicio te) throws Exception
	{
		return tipoEjercicioAdapter.GetOne(te);
	}
}
