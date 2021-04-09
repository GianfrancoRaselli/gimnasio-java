package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class SucursalLogic 
{
	private AdaptadorSucursal sucursalAdapter;
	
	public SucursalLogic()
	{
		sucursalAdapter = new AdaptadorSucursal();
	}
	
	public Sucursal BuscarSucursal(Sucursal s) throws Exception
	{
		return sucursalAdapter.GetOne(s);
	}
	
	public Collection<Sucursal> BuscarPorNombreSucursal(String nombreSucursal) throws Exception
	{
		return sucursalAdapter.BuscarPorNombreSucursal(nombreSucursal);
	}
	
	public Collection<Sucursal> BuscarSucursales() throws Exception
	{
		return sucursalAdapter.FindAll();
	}
	
	public void EliminarSucursal(Sucursal s) throws Exception
	{
		sucursalAdapter.Delete(s);
	}
	
	public void AgregarSucursal(Sucursal s) throws Exception
	{
		if(s.getNombreSucursal() != "" && s.getDireccion() != "" && s.getCiudad().getCodPostal() != null)
		{
			sucursalAdapter.Insert(s);
		}
		else
		{
			Exception excepcionManejada = new Exception("Error en los datos ingresados");
			throw excepcionManejada;
		}
	}
	
	public void ActualizarSucursal(Sucursal s) throws Exception
	{
		if(s.getId() != "" && s.getNombreSucursal() != "" && s.getDireccion() != "" && s.getCiudad().getCodPostal() != null)
		{
			sucursalAdapter.Update(s);
		}
		else
		{
			Exception excepcionManejada = new Exception("Error en los datos ingresados");
			throw excepcionManejada;
		}
	}
}