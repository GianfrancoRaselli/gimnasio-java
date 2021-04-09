package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;

public class AdaptadorTipoClasePersonalizada 
{
	public Collection<TipoClasePersonalizada> FindAll() throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM tipos_clases_personalizadas";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<TipoClasePersonalizada> tiposClasesPersonalizadas = new ArrayList<TipoClasePersonalizada>();
		
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
					TipoClasePersonalizada tipoClasePersonalizada = new TipoClasePersonalizada();
					
					tipoClasePersonalizada.setCodTipoClasePersonalizada(rs.getInt("cod_tipo_clase_personalizada"));
					tipoClasePersonalizada.setNombre(rs.getString("nombre"));
					tipoClasePersonalizada.setDescripcion(rs.getString("descripcion"));
					tipoClasePersonalizada.setPrecioPorClase(rs.getDouble("precio_x_clase"));
					
					tiposClasesPersonalizadas.add(tipoClasePersonalizada);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar los tipos de clases personalizadas", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return tiposClasesPersonalizadas;
	}
	
	public TipoClasePersonalizada GetOne(TipoClasePersonalizada tcp) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM tipos_clases_personalizadas WHERE BINARY cod_tipo_clase_personalizada=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setInt(1, tcp.getCodTipoClasePersonalizada());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				tcp.setCodTipoClasePersonalizada(rs.getInt("cod_tipo_clase_personalizada"));
				tcp.setNombre(rs.getString("nombre"));
				tcp.setDescripcion(rs.getString("descripcion"));
				tcp.setPrecioPorClase(rs.getDouble("precio_x_clase"));
			}
			else
			{
				tcp = null;
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar tipo de clase personalizada", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return tcp;
	}
}