package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import Entidades.*;

public class AdaptadorCuota 
{
	public ActualizacionCuotas BuscarUltimaActualizacion()
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT max(anio) anio, max(mes) mes FROM actualizaciones_cuotas WHERE anio = (select max(anio) from actualizaciones_cuotas)";
		PreparedStatement statement = null;
		ResultSet rs = null;
		ActualizacionCuotas ac = null;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				ac = new ActualizacionCuotas();
				ac.setAnio(rs.getInt("anio"));
				ac.setMes(rs.getInt("mes"));
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
		
		return ac;
	}
	
	public boolean Insert(Cuota cuota)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO cuotas (dni, anio, mes, total) VALUES (?, ?, ?, ?)";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, cuota.getPersona().getDni());
			statement.setInt(2, cuota.getAnio());
			statement.setInt(3, cuota.getMes());
			statement.setDouble(4, cuota.getTotal());
			
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
	
	public boolean InsertActualizacionCuotas(ActualizacionCuotas ac)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO actualizaciones_cuotas (anio, mes) VALUES (?, ?)";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setInt(1, ac.getAnio());
			statement.setInt(2, ac.getMes());
			
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
	
	public Collection<Cuota> BuscarCuotasImpagas(Persona p)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT anio, mes, total FROM cuotas WHERE fecha_hora_pago IS NULL AND dni=? ORDER BY anio, mes";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Cuota> cuotasImpagas = new ArrayList<Cuota>();
		
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
					Cuota cuota = new Cuota();
					
					cuota.setPersona(p);
					cuota.setAnio(rs.getInt("anio"));
					cuota.setMes(rs.getInt("mes"));
					cuota.setTotal(rs.getDouble("total"));
					
					cuotasImpagas.add(cuota);
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
		
		return cuotasImpagas;
	}
	
	public Collection<Cuota> BuscarCuotasDePersona(Persona p)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM cuotas WHERE dni=? ORDER BY anio, mes";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Cuota> cuotas = new ArrayList<Cuota>();
		
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
					Cuota cuota = new Cuota();
					
					cuota.setPersona(p);
					cuota.setAnio(rs.getInt("anio"));
					cuota.setMes(rs.getInt("mes"));
					cuota.setTotal(rs.getDouble("total"));
					cuota.setFechaHoraPago(rs.getDate("fecha_hora_pago"));
					
					cuotas.add(cuota);
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
		
		return cuotas;
	}
	
	public boolean PagarCuota(Cuota cuota)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE cuotas SET fecha_hora_pago=? WHERE dni=? and anio=? and mes=?";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			
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
			
			statement.setString(1, anio + mes + dia + hora + min + seg);
			statement.setString(2, cuota.getPersona().getDni());
			statement.setInt(3, cuota.getAnio());
			statement.setInt(4, cuota.getMes());
			
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
