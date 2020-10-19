package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import Entidades.*;

public class AdaptadorAsistencia 
{
	public boolean Insert(Asistencia a)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO asistencias (dni, nombre_sucursal, fecha_hora) VALUES (?, ?, ?)";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, a.getPersona().getDni());
			statement.setString(2, a.getSucursal().getNombreSucursal());
			
			Calendar c = Calendar.getInstance();
			
			String anio = String.valueOf(c.get(Calendar.YEAR)); 
			
			String mes;
			if(c.get(Calendar.MONTH) + 1 < 10)
			{
				mes = "0" + String.valueOf(c.get(Calendar.MONTH) + 1);
			}
			else
			{
				mes = String.valueOf(c.get(Calendar.MONTH) + 1);
			}
			
			String dia;
			if(c.get(Calendar.DAY_OF_MONTH) < 10)
			{
				dia = "0" + String.valueOf(c.get(Calendar.DAY_OF_MONTH));
			}
			else
			{
				dia = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
			}
			
			String hora;
			if(c.get(Calendar.HOUR_OF_DAY) < 10)
			{
				hora = "0" + String.valueOf(c.get(Calendar.HOUR_OF_DAY));
			}
			else
			{
				hora = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
			}
			
			String min;
			if(c.get(Calendar.MINUTE) < 10)
			{
				min = "0" + String.valueOf(c.get(Calendar.MINUTE));
			}
			else
			{
				min = String.valueOf(c.get(Calendar.MINUTE));
			}
			
			String seg;
			if(c.get(Calendar.SECOND) < 10)
			{
				seg = "0" + String.valueOf(c.get(Calendar.SECOND));
			}
			else
			{
				seg = String.valueOf(c.get(Calendar.SECOND));
			}
			
			statement.setString(3, anio + mes + dia + hora + min + seg);
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			devolucion = false;
			e.printStackTrace();
		}
		finally
		{
			try
			{
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
		
		return devolucion;
	}
}
