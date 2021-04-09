package BaseDeDatos;

import java.sql.*;
import Entidades.*;

public class AdaptadorUsuario
{
	public Usuario BuscarPorUsuarioYContrasenia(Usuario user) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM usuarios WHERE BINARY nombre_usuario=? AND BINARY contrasenia=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
			
		try
		{
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getNombreUsuario());
			statement.setString(2, user.getContraseniaEncriptada());
			
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
				user.setContraseniaEncriptada(rs.getString("contrasenia"));
				
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
			Exception excepcionManejada = new Exception("Error al buscar usuario", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return user;
	}
	
	public Usuario GetOne(Usuario user) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM usuarios WHERE BINARY nombre_usuario=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getNombreUsuario());
			
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
				user.setContraseniaEncriptada(rs.getString("contrasenia"));
				
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
			Exception excepcionManejada = new Exception("Error al buscar usuario", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return user;
	}

	public Usuario BuscarPorDni(Usuario user) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM usuarios WHERE BINARY dni=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getPersona().getDni());
			
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
				user.setContraseniaEncriptada(rs.getString("contrasenia"));
				
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
			Exception excepcionManejada = new Exception("Error al buscar usuario", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return user;
	}
	
	public void Delete(Usuario user) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "DELETE FROM usuarios WHERE BINARY nombre_usuario=?";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getNombreUsuario());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al eliminar usuario", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void Insert(Usuario user) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO usuarios (nombre_usuario, contrasenia, dni, nro_nivel) VALUES (?, ?, ?, ?)";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getNombreUsuario());
			statement.setString(2, user.getContraseniaEncriptada());
			statement.setString(3, user.getPersona().getDni());
			statement.setInt(4, user.getNivelUsuario().getNroNivel());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al agregar usuario", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void Update(Usuario user) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE usuarios SET nombre_usuario=?, contrasenia=?, dni=?, nro_nivel=? WHERE nombre_usuario=?";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getNombreUsuario());
			statement.setString(2, user.getContraseniaEncriptada());
			statement.setString(3, user.getPersona().getDni());
			statement.setInt(4, user.getNivelUsuario().getNroNivel());
			statement.setString(5, user.getId());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al actualizar usuario", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void UpdatePerfil(Usuario user) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE usuarios SET nombre_usuario=?, contrasenia=? WHERE dni=?";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getNombreUsuario());
			statement.setString(2, user.getContraseniaEncriptada());
			statement.setString(3, user.getPersona().getDni());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al actualizar perfil", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void CambiarContrasenia(Usuario user) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE usuarios SET contrasenia=? WHERE nombre_usuario=?";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, user.getContraseniaEncriptada());
			statement.setString(2, user.getNombreUsuario());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al cambiar contraseña", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
}