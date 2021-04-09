package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Entidades.*;

public class AdaptadorPrecioGimnasio 
{
	public PrecioGimnasio GetPrecioActual() throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT fecha_hora_desde, precio FROM precios_gimnasio EXCEPT " + 
				"(SELECT pg.fecha_hora_desde, pg.precio FROM precios_gimnasio pg, precios_gimnasio prgim " + 
				"WHERE pg.fecha_hora_desde < prgim.fecha_hora_desde)";
		PreparedStatement statement = null;
		ResultSet rs = null;
		PrecioGimnasio pg = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				pg = new PrecioGimnasio();
				pg.setFechaHoraDesde(rs.getTimestamp("fecha_hora_desde"));
				pg.setPrecio(rs.getDouble("precio"));
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar precio actual", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return pg;
	}
}