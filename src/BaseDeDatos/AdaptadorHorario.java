package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;

public class AdaptadorHorario 
{
	public Collection<Horario> FindAll()
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM horarios ORDER BY nombre_sucursal, nro_dia";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Horario> horarios = new ArrayList<Horario>();
		AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
		AdaptadorDia diaAdapter = new AdaptadorDia();
		
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
					Horario horario = new Horario();
					Sucursal sucursal = new Sucursal();
					Dia dia = new Dia();
					
					sucursal.setNombreSucursal(rs.getString("nombre_sucursal"));
					dia.setNroDia(rs.getInt("nro_dia"));
					
					horario.setHoraDesde(rs.getTime("hora_desde"));
					horario.setHoraHasta(rs.getTime("hora_hasta"));
	
					horario.setSucursal(sucursalAdapter.GetOne(sucursal));
					horario.setDia(diaAdapter.GetOne(dia));
					
					horarios.add(horario);
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
		
		return horarios;
	}
	
	public Collection<Horario> BuscarPorNombreSucursal(String nombreSucursal)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM horarios WHERE nombre_sucursal LIKE ? ORDER BY nombre_sucursal, nro_dia";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Horario> horarios = new ArrayList<Horario>();
		AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
		AdaptadorDia diaAdapter = new AdaptadorDia();
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
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
					
					horario.setHoraDesde(rs.getTime("hora_desde"));
					horario.setHoraHasta(rs.getTime("hora_hasta"));
	
					horario.setSucursal(sucursalAdapter.GetOne(sucursal));
					horario.setDia(diaAdapter.GetOne(dia));
					
					horarios.add(horario);
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
		
		return horarios;
	}
	
	public boolean Delete(Horario h)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "DELETE FROM horarios WHERE BINARY nombre_sucursal=? and nro_dia=? and hora_desde=?";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, h.getSucursal().getNombreSucursal());
			statement.setInt(2, h.getDia().getNroDia());
			statement.setString(3, h.getIdHoraDesde().toString());
			
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
	
	public boolean Insert(Horario h)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO horarios (nombre_sucursal, nro_dia, hora_desde, hora_hasta) VALUES (?, ?, ?, ?)";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, h.getSucursal().getNombreSucursal());
			statement.setInt(2, h.getDia().getNroDia());
			statement.setString(3, h.getHoraDesde().toString());
			statement.setString(4, h.getHoraHasta().toString());
			
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
	
	public boolean Update(Horario h)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE horarios SET hora_desde=?, hora_hasta=? WHERE BINARY nombre_sucursal=? and nro_dia=? and hora_desde=?";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, h.getHoraDesde().toString());
			statement.setString(2, h.getHoraHasta().toString());
			statement.setString(3, h.getSucursal().getNombreSucursal());
			statement.setInt(4, h.getDia().getNroDia());
			statement.setString(5, h.getIdHoraDesde().toString());
			
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