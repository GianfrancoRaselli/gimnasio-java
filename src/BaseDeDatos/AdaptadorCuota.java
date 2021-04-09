package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;
import Util.FormateoHora;

public class AdaptadorCuota 
{
	public ActualizacionCuotas BuscarUltimaActualizacion() throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT max(anio) anio, max(mes) mes FROM actualizaciones_cuotas WHERE anio = (select max(anio) from actualizaciones_cuotas)";
		PreparedStatement statement = null;
		ResultSet rs = null;
		ActualizacionCuotas ac = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
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
			Exception excepcionManejada = new Exception("Error al buscar actualizacion de cuotas", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return ac;
	}
	
	public void Insert(Cuota cuota) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO cuotas (dni, anio, mes, total) VALUES (?, ?, ?, ?)";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, cuota.getPersona().getDni());
			statement.setInt(2, cuota.getAnio());
			statement.setInt(3, cuota.getMes());
			statement.setDouble(4, cuota.getTotal());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al insertar cuota", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void InsertCuotas(ArrayList<Cuota> cuotas) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO cuotas (dni, anio, mes, total) VALUES (?, ?, ?, ?)";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
			
		try
		{
			conn.setAutoCommit(false);
			
			statement = conn.prepareStatement(instruccion);
				
			for(Cuota cuota: cuotas)
			{
				statement.setString(1, cuota.getPersona().getDni());
				statement.setInt(2, cuota.getAnio());
				statement.setInt(3, cuota.getMes());
				statement.setDouble(4, cuota.getTotal());
				
				statement.executeUpdate();
			}
			
			conn.commit();
		}
		catch(Exception e)
		{
			conn.rollback();
				
			Exception excepcionManejada = new Exception("Error al insertar cuotas", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void InsertActualizacionCuotas(ActualizacionCuotas ac) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO actualizaciones_cuotas (anio, mes) VALUES (?, ?)";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setInt(1, ac.getAnio());
			statement.setInt(2, ac.getMes());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al insertar actualizacion de cuotas", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public Collection<Cuota> BuscarCuotasImpagas(Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT anio, mes, total FROM cuotas WHERE fecha_hora_pago IS NULL AND dni=? ORDER BY anio, mes";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Cuota> cuotasImpagas = new ArrayList<Cuota>();
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
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
			Exception excepcionManejada = new Exception("Error al buscar cuotas impagas", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return cuotasImpagas;
	}
	
	public Collection<Cuota> BuscarCuotasDePersona(Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM cuotas WHERE dni=? ORDER BY anio, mes";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Cuota> cuotas = new ArrayList<Cuota>();
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
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
					cuota.setFechaHoraPago(rs.getTimestamp("fecha_hora_pago"));
					
					cuotas.add(cuota);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar cuotas de la persona", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return cuotas;
	}
	
	public void PagarCuota(Cuota cuota) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE cuotas SET fecha_hora_pago=? WHERE dni=? and anio=? and mes=?";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, FormateoHora.getFechaHoraActualEnFormatoBDD());
			statement.setString(2, cuota.getPersona().getDni());
			statement.setInt(3, cuota.getAnio());
			statement.setInt(4, cuota.getMes());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al registrar pago de cuota", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
}