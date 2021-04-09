package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class DiaLogic 
{
	private AdaptadorDia diaAdapter;
	
	public DiaLogic()
	{
		diaAdapter = new AdaptadorDia();
	}
	
	public Collection<Dia> BuscarDias() throws Exception
	{
		return diaAdapter.FindAll();
	}
}