package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class DiaLogic 
{
	public Collection<Dia> BuscarDias()
	{
		AdaptadorDia diaAdapter = new AdaptadorDia();
		return diaAdapter.FindAll();
	}
}
