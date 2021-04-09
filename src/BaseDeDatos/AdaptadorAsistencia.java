package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Entidades.*;
import Util.FormateoHora;

public class AdaptadorAsistencia 
{
	public void Insert(Asistencia a) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO asistencias (dni, nombre_sucursal, fecha_hora) VALUES (?, ?, ?)";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, a.getPersona().getDni());
			statement.setString(2, a.getSucursal().getNombreSucursal());		
			statement.setString(3, FormateoHora.getFechaHoraActualEnFormatoBDD());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al insertar asistencia", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
}