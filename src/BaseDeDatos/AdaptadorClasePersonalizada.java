package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entidades.Persona;

public class AdaptadorClasePersonalizada 
{
	public ArrayList<Float> BuscarPreciosClasesPersonalizadasPorPersona(Persona p)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT precio_x_clase FROM inscripciones "
				+ "NATURAL JOIN clases_personalizadas NATURAL JOIN tipos_clases_personalizadas WHERE dni=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Float> precios = new ArrayList<Float>();
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, p.getDni());
			
			rs = statement.executeQuery();
			
			if(rs!=null)
			{
				while(rs.next())
				{
					precios.add((float) rs.getDouble("precio_x_clase"));
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
		
		return precios;
	}
}
