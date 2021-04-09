package Controlador;

import java.util.ArrayList;

import Entidades.*;
import Modelo.*;

public class ControladorCiudad 
{
	public ArrayList<Ciudad> BuscarCiudades() throws Exception
	{
		CiudadLogic cl = new CiudadLogic();
		return (ArrayList<Ciudad>) cl.BuscarCiudades();
	}
}