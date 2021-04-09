package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;

public class AdaptadorSucursal 
{
	public Sucursal GetOne(Sucursal s) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM sucursales WHERE BINARY nombre_sucursal=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, s.getNombreSucursal());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				AdaptadorCiudad ciudadAdapter = new AdaptadorCiudad();
				
				Ciudad ciudad = new Ciudad();
				ciudad.setCodPostal(rs.getString("cod_postal"));
				s.setCiudad(ciudadAdapter.GetOne(ciudad));
				
				s.setNombreSucursal(rs.getString("nombre_sucursal"));
				s.setDireccion(rs.getString("direccion"));
			}
			else
			{
				s = null;
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar sucursal", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return s;
	}
	
	public Collection<Sucursal> BuscarPorNombreSucursal(String nombreSucursal) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM sucursales WHERE nombre_sucursal like ? ORDER BY nombre_sucursal";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Sucursal> sucursales = new ArrayList<Sucursal>();
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, "%" + nombreSucursal + "%");
			
			rs = statement.executeQuery();
			
			if(rs!=null)
			{
				while(rs.next())
				{
					AdaptadorCiudad ciudadAdapter = new AdaptadorCiudad();
					
					Sucursal s = new Sucursal();
					
					Ciudad ciudad = new Ciudad();
					ciudad.setCodPostal(rs.getString("cod_postal"));
					s.setCiudad(ciudadAdapter.GetOne(ciudad));
					
					s.setNombreSucursal(rs.getString("nombre_sucursal"));
					s.setDireccion(rs.getString("direccion"));
					
					sucursales.add(s);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar sucursales", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return sucursales;
	}
	
	public Collection<Sucursal> FindAll() throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM sucursales ORDER BY nombre_sucursal";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Sucursal> sucursales = new ArrayList<Sucursal>();
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			
			rs = statement.executeQuery();
			
			if(rs!=null)
			{
				while(rs.next())
				{
					AdaptadorCiudad ciudadAdapter = new AdaptadorCiudad();
					
					Sucursal s = new Sucursal();
					
					Ciudad ciudad = new Ciudad();
					ciudad.setCodPostal(rs.getString("cod_postal"));
					s.setCiudad(ciudadAdapter.GetOne(ciudad));
					
					s.setNombreSucursal(rs.getString("nombre_sucursal"));
					s.setDireccion(rs.getString("direccion"));
					
					s.setCiudad(ciudad);
					
					sucursales.add(s);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar sucursales", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return sucursales;
	}
	
	public void Delete(Sucursal s) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "DELETE FROM sucursales WHERE BINARY nombre_sucursal=?";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, s.getNombreSucursal());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al eliminar sucursal", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void Insert(Sucursal s) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO sucursales (nombre_sucursal, direccion, cod_postal) VALUES (?, ?, ?)";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, s.getNombreSucursal());
			statement.setString(2, s.getDireccion());
			statement.setString(3, s.getCiudad().getCodPostal());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al agregar sucursal", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void Update(Sucursal s) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE sucursales SET nombre_sucursal=?, direccion=?, cod_postal=? WHERE nombre_sucursal=?";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, s.getNombreSucursal());
			statement.setString(2, s.getDireccion());
			statement.setString(3, s.getCiudad().getCodPostal());
			statement.setString(4, s.getId());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al actualizar sucursal", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
}