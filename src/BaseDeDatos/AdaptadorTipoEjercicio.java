package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;

public class AdaptadorTipoEjercicio 
{
	public TipoEjercicio GetOne(TipoEjercicio te) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM tipos_ejercicios WHERE cod_tipo_ejercicio=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setInt(1, te.getCodTipoEjercicio());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				te.setCodTipoEjercicio(rs.getInt("cod_tipo_ejercicio"));
				te.setNombre(rs.getString("Nombre"));
				te.setDescripcion(rs.getString("descripcion"));
			}
			else
			{
				te = null;
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar tipo ejercicio", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return te;
	}
	
	public Collection<TipoEjercicio> FindAll() throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM tipos_ejercicios";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<TipoEjercicio> tiposEjercicios = new ArrayList<TipoEjercicio>();
		
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
					TipoEjercicio tipoEjercicio = new TipoEjercicio();
					
					tipoEjercicio.setCodTipoEjercicio(rs.getInt("cod_tipo_ejercicio"));
					tipoEjercicio.setNombre(rs.getString("nombre"));
					tipoEjercicio.setDescripcion(rs.getString("descripcion"));
					
					tiposEjercicios.add(tipoEjercicio);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar tipos ejercicios", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return tiposEjercicios;
	}
}
