package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class SucursalLogic 
{
	public Sucursal BuscarSucursal(Sucursal s)
	{
		AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
		return sucursalAdapter.GetOne(s);
	}
	
	public Collection<Sucursal> BuscarPorNombreSucursal(String nombreSucursal)
	{
		AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
		return sucursalAdapter.BuscarPorNombreSucursal(nombreSucursal);
	}
	
	public Collection<Sucursal> BuscarSucursales()
	{
		AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
		return sucursalAdapter.FindAll();
	}
	
	public boolean EliminarSucursal(Sucursal s)
	{
		AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
		return sucursalAdapter.Delete(s);
	}
	
	public boolean AgregarSucursal(Sucursal s)
	{
		if(s.getNombreSucursal() != "" && s.getDireccion() != "" && s.getCiudad().getCodPostal() != null)
		{
			AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
			return sucursalAdapter.Insert(s);
		}
		else
		{
			return false;
		}
	}
	
	public boolean ActualizarSucursal(Sucursal s)
	{
		if(s.getId() != "" && s.getNombreSucursal() != "" && s.getDireccion() != "" && s.getCiudad().getCodPostal() != null)
		{
			AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
			return sucursalAdapter.Update(s);
		}
		else
		{
			return false;
		}
	}
}
