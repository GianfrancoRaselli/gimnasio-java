package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;

public class AdaptadorSucursal 
{
	public Sucursal GetOne(Sucursal s)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM sucursales WHERE BINARY nombre_sucursal=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, s.getNombreSucursal());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				Ciudad ciudad = new Ciudad();
				ciudad.setCodPostal(rs.getString("cod_postal"));
				
				s.setNombreSucursal(rs.getString("nombre_sucursal"));
				s.setDireccion(rs.getString("direccion"));
				
				s.setCiudad(ciudad);
			}
			else
			{
				s = null;
			}
		}
		catch(Exception e)
		{
			s = null;
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
		
		return s;
	}
	
	public Collection<Sucursal> BuscarPorNombreSucursal(String nombreSucursal)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM sucursales WHERE nombre_sucursal like ? ORDER BY nombre_sucursal";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Sucursal> sucursales = new ArrayList<Sucursal>();
		
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
					Sucursal s = new Sucursal();
					
					Ciudad ciudad = new Ciudad();
					ciudad.setCodPostal(rs.getString("cod_postal"));
					
					s.setNombreSucursal(rs.getString("nombre_sucursal"));
					s.setDireccion(rs.getString("direccion"));
					
					s.setCiudad(ciudad);
					
					sucursales.add(s);
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
		
		return sucursales;
	}
	
	public Collection<Sucursal> FindAll()
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM sucursales ORDER BY nombre_sucursal";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Sucursal> sucursales = new ArrayList<Sucursal>();
		
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
					Sucursal s = new Sucursal();
					
					Ciudad ciudad = new Ciudad();
					ciudad.setCodPostal(rs.getString("cod_postal"));
					
					s.setNombreSucursal(rs.getString("nombre_sucursal"));
					s.setDireccion(rs.getString("direccion"));
					
					s.setCiudad(ciudad);
					
					sucursales.add(s);
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
		
		return sucursales;
	}
	
	public boolean Delete(Sucursal s)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "DELETE FROM sucursales WHERE BINARY nombre_sucursal=?";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, s.getNombreSucursal());
			
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
	
	public boolean Insert(Sucursal s)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO sucursales (nombre_sucursal, direccion, cod_postal) VALUES (?, ?, ?)";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, s.getNombreSucursal());
			statement.setString(2, s.getDireccion());
			statement.setString(3, s.getCiudad().getCodPostal());
			
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
	
	public boolean Update(Sucursal s)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE sucursales SET nombre_sucursal=?, direccion=?, cod_postal=? WHERE nombre_sucursal=?";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, s.getNombreSucursal());
			statement.setString(2, s.getDireccion());
			statement.setString(3, s.getCiudad().getCodPostal());
			statement.setString(4, s.getId());
			
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
