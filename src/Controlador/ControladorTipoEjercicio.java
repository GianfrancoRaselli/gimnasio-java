package Controlador;

import java.util.ArrayList;

import Entidades.*;
import Modelo.*;

public class ControladorTipoEjercicio 
{
	public ArrayList<TipoEjercicio> BuscarTiposEjercicios() throws Exception
	{
		TipoEjercicioLogic tel = new TipoEjercicioLogic();
		return (ArrayList<TipoEjercicio>) tel.BuscarTiposEjercicios();
	} 
}
