package BaseDeDatos;

import java.sql.*;
import Entidades.*;

public class AdaptadorUsuario
{
	public Usuario BuscarPorUsuarioYContrasenia(Usuario user)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM usuarios WHERE BINARY nombre_usuario=? AND BINARY contrasenia=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();

			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getNombreUsuario());
			statement.setString(2, user.getContrasenia());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				AdaptadorPersona personaAdapter = new AdaptadorPersona();
				AdaptadorNivelUsuario nivelAdapter = new AdaptadorNivelUsuario();
				
				Persona p = new Persona();
				NivelUsuario nivel = new NivelUsuario();
				
				p.setDni(rs.getString("dni"));
				nivel.setNroNivel(rs.getInt("nro_nivel"));				
				user.setNombreUsuario(rs.getString("nombre_usuario"));
				user.setContrasenia(rs.getString("contrasenia"));
				
				user.setPersona(personaAdapter.GetOne(p));
				user.setNivelUsuario(nivelAdapter.GetOne(nivel));
			}
			else
			{
				user = null;
			}
		}
		catch(Exception e)
		{
			user = null;
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
		
		return user;
	}
	
	public Usuario GetOne(Usuario user)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM usuarios WHERE BINARY nombre_usuario=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getNombreUsuario());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				Persona p = new Persona();
				NivelUsuario nivel = new NivelUsuario();
				
				nivel.setNroNivel(rs.getInt("nro_nivel"));

				p.setDni(rs.getString("dni"));
				
				user.setNombreUsuario(rs.getString("nombre_usuario"));
				user.setContrasenia(rs.getString("contrasenia"));
				user.setPersona(p);
				user.setNivelUsuario(nivel);
			}
			else
			{
				user = null;
			}
		}
		catch(Exception e)
		{
			user = null;
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
		
		return user;
	}

	public Usuario BuscarPorDni(Usuario user)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM usuarios WHERE BINARY dni=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getPersona().getDni());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				Persona p = new Persona();
				NivelUsuario nivel = new NivelUsuario();
				
				nivel.setNroNivel(rs.getInt("nro_nivel"));

				p.setDni(rs.getString("dni"));
				
				user.setNombreUsuario(rs.getString("nombre_usuario"));
				user.setContrasenia(rs.getString("contrasenia"));
				user.setPersona(p);
				user.setNivelUsuario(nivel);
			}
			else
			{
				user = null;
			}
		}
		catch(Exception e)
		{
			user = null;
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
		
		return user;
	}
	
	public boolean Delete(Usuario user)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "DELETE FROM usuarios WHERE BINARY nombre_usuario=?";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getNombreUsuario());
			
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
	
	public boolean Insert(Usuario user)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO usuarios (nombre_usuario, contrasenia, dni, nro_nivel) VALUES (?, ?, ?, ?)";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getNombreUsuario());
			statement.setString(2, user.getContrasenia());
			statement.setString(3, user.getPersona().getDni());
			statement.setInt(4, user.getNivelUsuario().getNroNivel());
			
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
	
	public boolean Update(Usuario user)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE usuarios SET nombre_usuario=?, contrasenia=?, dni=?, nro_nivel=? WHERE nombre_usuario=?";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getNombreUsuario());
			statement.setString(2, user.getContrasenia());
			statement.setString(3, user.getPersona().getDni());
			statement.setInt(4, user.getNivelUsuario().getNroNivel());
			statement.setString(5, user.getId());
			
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
	
	public boolean UpdatePerfil(Usuario user)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE usuarios SET nombre_usuario=?, contrasenia=? WHERE dni=?";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getNombreUsuario());
			statement.setString(2, user.getContrasenia());
			statement.setString(3, user.getPersona().getDni());
			
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
	
	public boolean CambiarContrasenia(Usuario user)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE usuarios SET contrasenia=? WHERE nombre_usuario=?";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getContrasenia());
			statement.setString(2, user.getNombreUsuario());
			
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
