package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;

public class AdaptadorDia 
{
	public Dia GetOne(Dia dia) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM dias WHERE nro_dia=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setInt(1, dia.getNroDia());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				dia.setNroDia(rs.getInt("nro_dia"));
				dia.setDescripcion(rs.getString("descripcion"));
			}
			else
			{
				dia = null;
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar dia", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return dia;
	}
	
	public Collection<Dia> FindAll() throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM dias ORDER BY nro_dia";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Dia> dias = new ArrayList<Dia>();
		
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
					Dia dia = new Dia();
					
					dia.setNroDia(rs.getInt("nro_dia"));
					dia.setDescripcion(rs.getString("descripcion"));
					
					dias.add(dia);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar dias", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return dias;
	}
}