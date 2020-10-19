package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;

public class AdaptadorTipoEjercicio 
{
	public TipoEjercicio GetOne(TipoEjercicio te)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM tipos_ejercicios WHERE cod_tipo_ejercicio=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
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
			te = null;
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
		
		return te;
	}
	
	public Collection<TipoEjercicio> FindAll()
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM tipos_ejercicios";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<TipoEjercicio> tiposEjercicios = new ArrayList<TipoEjercicio>();
		
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
		
		return tiposEjercicios;
	}
}
