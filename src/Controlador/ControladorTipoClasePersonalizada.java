package Controlador;

import java.util.ArrayList;

import Entidades.*;
import Modelo.*;

public class ControladorTipoClasePersonalizada 
{
	public ArrayList<TipoClasePersonalizada> BuscarTiposClasesPersonalizadas() throws Exception
	{
		TipoClasePersonalizadaLogic tcpl = new TipoClasePersonalizadaLogic();
		return (ArrayList<TipoClasePersonalizada>) tcpl.BuscarTiposClasesPersonalizadas();
	} 
}
