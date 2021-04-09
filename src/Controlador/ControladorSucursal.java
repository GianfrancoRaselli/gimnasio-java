package Controlador;

import java.util.ArrayList;

import Entidades.*;
import Modelo.*;

public class ControladorSucursal 
{
	public ArrayList<Sucursal> BuscarSucursales() throws Exception
	{
		SucursalLogic sl = new SucursalLogic();
		return (ArrayList<Sucursal>) sl.BuscarSucursales();
	}
}
