package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;

public class AdaptadorCiudad
{
	public Collection<Ciudad> FindAll() throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM ciudades";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Ciudad> ciudades = new ArrayList<Ciudad>();
		
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
					Ciudad ciudad = new Ciudad();
					Provincia prov = new Provincia();
					
					ciudad.setCodPostal(rs.getString("cod_postal"));
					ciudad.setDescripcion(rs.getString("descripcion"));
					
					prov.setNombreProvincia(rs.getString("nombre_provincia"));
					ciudad.setProvincia(prov);
					
					ciudades.add(ciudad);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al recuperar ciudades", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return ciudades;
	}
	
	public Ciudad GetOne(Ciudad c) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM ciudades WHERE BINARY cod_postal=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, c.getCodPostal());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				Provincia provincia = new Provincia();
				provincia.setNombreProvincia(rs.getString("nombre_provincia"));
				c.setProvincia(provincia);
				
				c.setCodPostal(rs.getString("cod_postal"));
				c.setDescripcion(rs.getString("descripcion"));
			}
			else
			{
				c = null;
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar ciudad", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return c;
	}
}