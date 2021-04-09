package Controlador;

import java.util.ArrayList;

import Entidades.*;
import Modelo.*;

public class ControladorDia 
{
	public ArrayList<Dia> BuscarDias() throws Exception
	{
		DiaLogic dl = new DiaLogic();
		return (ArrayList<Dia>) dl.BuscarDias();
	} 
}
