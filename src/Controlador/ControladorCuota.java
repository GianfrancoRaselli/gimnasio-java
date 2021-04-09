package Controlador;

import java.util.ArrayList;

import Entidades.*;
import Modelo.*;

public class ControladorCuota 
{
	public void ActualizarCuotas() throws Exception
	{
		CuotaLogic cl = new CuotaLogic();
		cl.ActualizarCuotas();
	}
	
	public ArrayList<Cuota> BuscarCuotasDePersona(Persona p) throws Exception
	{
		CuotaLogic cl = new CuotaLogic();
		return (ArrayList<Cuota>) cl.BuscarCuotasDePersona(p);
	} 
}