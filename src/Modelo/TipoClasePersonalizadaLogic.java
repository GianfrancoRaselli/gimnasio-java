package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class TipoClasePersonalizadaLogic 
{
	private AdaptadorTipoClasePersonalizada tipoClasePersonalizadaAdapter;
	
	public TipoClasePersonalizadaLogic()
	{
		tipoClasePersonalizadaAdapter = new AdaptadorTipoClasePersonalizada();
	}
	
	public Collection<TipoClasePersonalizada> BuscarTiposClasesPersonalizadas() throws Exception
	{
		return tipoClasePersonalizadaAdapter.FindAll();
	}
}
