package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidades.*;

public class AdaptadorPrecioGimnasio 
{
	public PrecioGimnasio GetPrecioActual()
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT fecha_hora_desde, precio FROM precios_gimnasio EXCEPT " + 
				"(SELECT pg.fecha_hora_desde, pg.precio FROM precios_gimnasio pg, precios_gimnasio prgim " + 
				"WHERE pg.fecha_hora_desde < prgim.fecha_hora_desde)";
		PreparedStatement statement = null;
		ResultSet rs = null;
		PrecioGimnasio pg = null;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				pg = new PrecioGimnasio();
				pg.setFechaHoraDesde(rs.getDate("fecha_hora_desde"));
				pg.setPrecio(rs.getDouble("precio"));
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
		
		return pg;
	}
}
