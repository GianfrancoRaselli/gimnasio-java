package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;

public class AdaptadorHorario 
{
	public Collection<Horario> FindAll() throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM horarios ORDER BY nombre_sucursal, nro_dia";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Horario> horarios = new ArrayList<Horario>();
		AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
		AdaptadorDia diaAdapter = new AdaptadorDia();
		
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
					Horario horario = new Horario();
					Sucursal sucursal = new Sucursal();
					Dia dia = new Dia();
					
					sucursal.setNombreSucursal(rs.getString("nombre_sucursal"));
					dia.setNroDia(rs.getInt("nro_dia"));
					
					horario.setHoraDesde(rs.getTimestamp("hora_desde"));
					horario.setHoraHasta(rs.getTimestamp("hora_hasta"));
	
					horario.setSucursal(sucursalAdapter.GetOne(sucursal));
					horario.setDia(diaAdapter.GetOne(dia));
					
					horarios.add(horario);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar horarios", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return horarios;
	}
	
	public Collection<Horario> BuscarPorNombreSucursal(String nombreSucursal) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM horarios WHERE nombre_sucursal LIKE ? ORDER BY nombre_sucursal, nro_dia";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Horario> horarios = new ArrayList<Horario>();
		AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
		AdaptadorDia diaAdapter = new AdaptadorDia();
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, "%" + nombreSucursal + "%");
			
			rs = statement.executeQuery();
			
			if(rs!=null)
			{
				while(rs.next())
				{
					Horario horario = new Horario();
					Sucursal sucursal = new Sucursal();
					Dia dia = new Dia();
					
					sucursal.setNombreSucursal(rs.getString("nombre_sucursal"));
					dia.setNroDia(rs.getInt("nro_dia"));
					
					horario.setHoraDesde(rs.getTimestamp("hora_desde"));
					horario.setHoraHasta(rs.getTimestamp("hora_hasta"));
	
					horario.setSucursal(sucursalAdapter.GetOne(sucursal));
					horario.setDia(diaAdapter.GetOne(dia));
					
					horarios.add(horario);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar horarios de sucursal", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return horarios;
	}
	
	public void Delete(Horario h) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "DELETE FROM horarios WHERE BINARY nombre_sucursal=? and nro_dia=? and hora_desde=?";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, h.getSucursal().getNombreSucursal());
			statement.setInt(2, h.getDia().getNroDia());
			statement.setString(3, h.getIdHoraDesdeString());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al eliminar horario", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void Insert(Horario h) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO horarios (nombre_sucursal, nro_dia, hora_desde, hora_hasta) VALUES (?, ?, ?, ?)";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, h.getSucursal().getNombreSucursal());
			statement.setInt(2, h.getDia().getNroDia());
			statement.setString(3, h.getHoraDesdeString());
			statement.setString(4, h.getHoraHastaString());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al agregar horario", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void Update(Horario h) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE horarios SET hora_desde=?, hora_hasta=? WHERE BINARY nombre_sucursal=? and nro_dia=? and hora_desde=?";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, h.getHoraDesdeString());
			statement.setString(2, h.getHoraHastaString());
			statement.setString(3, h.getSucursal().getNombreSucursal());
			statement.setInt(4, h.getDia().getNroDia());
			statement.setString(5, h.getIdHoraDesdeString());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al actualizar horario", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
}