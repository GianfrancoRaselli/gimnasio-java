package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class TipoEjercicioLogic 
{
	public Collection<TipoEjercicio> BuscarTiposEjercicios()
	{
		AdaptadorTipoEjercicio tipoEjercicioAdapter = new AdaptadorTipoEjercicio();
		return tipoEjercicioAdapter.FindAll();
	}
	
	public TipoEjercicio BuscarTipoEjercicio(TipoEjercicio te)
	{
		AdaptadorTipoEjercicio tipoEjercicioAdapter = new AdaptadorTipoEjercicio();
		return tipoEjercicioAdapter.GetOne(te);
	}
}
