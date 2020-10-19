package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.Ciudad;
import Entidades.Provincia;

public class AdaptadorCiudad
{
	public Collection<Ciudad> FindAll()
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM ciudades";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Ciudad> ciudades = new ArrayList<Ciudad>();
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
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
			e.printStackTrace();
		}
		finally
		{
			try
			{
				try 
				{
					if(rs != null) rs.close();
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				try 
				{
					if(statement != null) statement.close();
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				
				connectionPool.closeConnection(conn);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ciudades;
	}
}
